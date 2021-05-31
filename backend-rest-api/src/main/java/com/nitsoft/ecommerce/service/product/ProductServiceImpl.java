package com.nitsoft.ecommerce.service.product;

import com.nitsoft.ecommerce.database.model.Product;
import com.nitsoft.ecommerce.database.model.ProductCategory;
import com.nitsoft.ecommerce.database.model.ProductCategoryId;
import com.nitsoft.ecommerce.repository.ProductCategoryIdMappingRepo;
import com.nitsoft.ecommerce.repository.ProductRepository;

import java.util.List;

import com.nitsoft.util.TransformUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryIdMappingRepo productCategoryIdMappingRepo;

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
}
