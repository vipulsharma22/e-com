package com.nitsoft.ecommerce.service.product;

import com.google.common.collect.Lists;
import com.nitsoft.ecommerce.database.model.entity.Image;
import com.nitsoft.ecommerce.database.model.entity.Product;
import com.nitsoft.ecommerce.database.model.entity.ProductCategory;
import com.nitsoft.ecommerce.database.model.entity.ProductCategoryId;
import com.nitsoft.ecommerce.repository.ImageRepository;
import com.nitsoft.ecommerce.repository.ProductCategoryIdMappingRepo;
import com.nitsoft.ecommerce.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

import com.nitsoft.util.Constant;
import com.nitsoft.util.ImageUtil;
import com.nitsoft.util.TransformUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryIdMappingRepo productCategoryIdMappingRepo;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    @Qualifier("redisStringTemplate")
    RedisTemplate redisTemplate;

    // currently this method is implement just for testing
    @Override
    public Iterable<Product> findAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(long companyId, long productId) {
        return productRepository.findById(productId).orElse(null);
    }

    public Page<Product> getAll(int pageNumber, int pageSize) {
        return productRepository.findAll(PageRequest.of(pageNumber, pageSize));
    }

    @Override
    public List<Product> getByCompanyIdAndCategoryId(long companyId, long categoryId, int pageNumber, int pageSize) {
        Page<ProductCategoryId> page = productCategoryIdMappingRepo.findByCategoryIdAndDeletedFalse(categoryId, PageRequest.of(pageNumber,pageSize));
        List<Long> productIds = TransformUtil.extract(page.getContent(), ProductCategoryId::getProductId);
        return productRepository.findByIdInAndDeletedFalse(productIds);
    }

    @Override
    public Page<Product> doFilterSearchSortPagingProduct(long comId, long catId, long attrId, String searchKey, double mnPrice, double mxPrice, int minRank, int maxRank, int sortKey, boolean isAscSort, int pSize, int pNumber) {
//        return productRepository.findAll(new ProductSpecification(comId, catId, attrId, searchKey, mnPrice, mxPrice, minRank, maxRank, sortKey, isAscSort), PageRequest.of(pNumber, pSize));
        return null;
    }

    @Override
    public Iterable<Product> getProductsById(long companyId, List<Long> productIds) {
        return productRepository.findByIdInAndDeletedFalse(productIds);
    }

    @Override
    public Product save(Product product) {
        product.setId(null);
        return productRepository.save(product);
    }

    @Override
    public Product update(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void saveProductCategory(ProductCategoryId productCategoryId) {
//        productRepository.saveProductCategory(product.getProductId(), product.getCategoryId());
    }

    @Override
    public void deleteProductCategory(ProductCategory product) {
//        productRepository.deleteProductCategory(product.getProductId());
    }

    @Override
    public void uploadImage(MultipartFile multipartFile){
        try{
            Image image = new Image();
            image.setImage(multipartFile.getBytes());
            image.setProductId(Long.valueOf(multipartFile.getName()));
            image.setImageType(Constant.IMAGE_TYPE.valueOf(multipartFile.getOriginalFilename()).name());
            imageRepository.save(image);//set in postgres
            redisTemplate.opsForValue().set(getKeyName(multipartFile.getName(),multipartFile.getOriginalFilename()),
                    ImageUtil.encodeToString(multipartFile.getBytes()));//set in redis
        }catch (Exception ex){

        }
    }

    @Override
    public List<String> getImages(Long id, List<Constant.IMAGE_TYPE> imageTypes){
        if(CollectionUtils.isEmpty(imageTypes)){
            imageTypes = Lists.newArrayList(Constant.IMAGE_TYPE.values());
        }
        return imageTypes.stream().map(t ->{
            String encodedString =  (String)redisTemplate.opsForValue().get(getKeyName(String.valueOf(id),t.name()));
            if(encodedString == null){
                encodedString = ImageUtil.encodeToString(imageRepository.findByProductIdAndImageType(id,t.name()).getImage());
                redisTemplate.opsForValue().set(getKeyName(String.valueOf(id),t.name()),encodedString);
            }
            return encodedString;
        }).collect(Collectors.toList());
    }

    private String getKeyName(String productId, String imageType){
        return "IMAGE_" + productId + "-" + imageType;
    }
}
