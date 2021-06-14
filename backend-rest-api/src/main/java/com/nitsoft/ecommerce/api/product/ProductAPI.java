package com.nitsoft.ecommerce.api.product;

import com.nitsoft.ecommerce.api.APIName;
import com.nitsoft.ecommerce.api.controller.AbstractBaseController;
import com.nitsoft.ecommerce.api.request.model.CreateProductModel;
import com.nitsoft.ecommerce.api.request.model.ImageRequest;
import com.nitsoft.ecommerce.api.request.model.ListProductModel;
import com.nitsoft.ecommerce.api.response.model.APIResponse;
import com.nitsoft.ecommerce.api.response.model.PagingResponseModel;
import com.nitsoft.ecommerce.api.response.util.APIStatus;
import com.nitsoft.ecommerce.database.model.entity.Category;
import com.nitsoft.ecommerce.database.model.entity.Product;
import com.nitsoft.ecommerce.database.model.entity.ProductCategoryId;
import com.nitsoft.ecommerce.exception.ApplicationException;
import com.nitsoft.ecommerce.repository.CategoryRepository;
import com.nitsoft.ecommerce.repository.ImageRepository;
import com.nitsoft.ecommerce.repository.ProductCategoryIdMappingRepo;
import com.nitsoft.ecommerce.service.ProductAttributeDetailService;
import com.nitsoft.ecommerce.service.product.ProductServiceImpl;
import com.nitsoft.util.Constant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(value = "products")
@RestController
@RequestMapping(APIName.PRODUCTS)
public class ProductAPI extends AbstractBaseController {

    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private ProductAttributeDetailService productAttributeService;

    @Autowired
    private ProductCategoryIdMappingRepo productCategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ImageRepository imageRepository;

    @GetMapping
    @ApiOperation(value = "get product by company id", notes = "")
    public ResponseEntity<APIResponse> getAllProducts(
            @RequestParam(required = false, defaultValue = Constant.DEFAULT_PAGE_NUMBER) Integer pageNumber,
            @RequestParam(required = false, defaultValue = Constant.DEFAULT_PAGE_SIZE) Integer pageSize) {

        Page<Product> products = productService.getAll(pageNumber, pageSize);
        return responseUtil.successResponse(products.getContent());
    }

    @ApiOperation(value = "get products by product id", notes = "")
    @RequestMapping(path = APIName.PRODUCT_BY_ID, method = RequestMethod.GET, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> getProductById(HttpServletRequest request,
            @PathVariable Long productId,
            @PathVariable(value = "company_id") Long companyId) {
        Product p = productService.getProductById(companyId, productId);
        if (p != null) {
            List<ProductCategoryId> listProductCate = productCategoryRepository.findByProductIdAndDeletedFalse(productId);
            List<Map<String, Object>> listCate = new ArrayList<>();
            for (ProductCategoryId result : listProductCate) {
                Map<String, Object> category = new HashMap();
                //find category name with categoryId
                Category cate = categoryRepository.findByIdAndDeletedFalse(result.getCategoryId());
                if (cate != null) {
                    category.put("text", cate.getName());
                    category.put("id", cate.getId());
                }
                //add category name to list String
                listCate.add(category);
            }
            Map<String, Object> result = new HashMap();
            result.put("product", p);
            result.put("list_category", listCate);

            return responseUtil.successResponse(result);
        } else {
            throw new ApplicationException(APIStatus.GET_PRODUCT_ERROR);
        }
    }

    @ApiOperation(value = "get list product by product ids", notes = "")
    @RequestMapping(path = APIName.PRODUCT_BY_IDS, method = RequestMethod.POST, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> getListProductByIds(
            @PathVariable Long companyId,
            @RequestBody List<Long> productIds) {

        if (productIds != null && !productIds.isEmpty()) {
            List<Product> products = (List<Product>) productService.getProductsById(companyId, productIds);
            if (products != null) {
                return responseUtil.successResponse(products);
            } else {
                throw new ApplicationException(APIStatus.INVALID_PARAMETER);
            }
        } else {
            throw new ApplicationException(APIStatus.INVALID_PARAMETER);
        }
    }

    @ApiOperation(value = "get products by category id", notes = "")
    @RequestMapping(value = APIName.PRODUCTS_BY_CATEGORY, method = RequestMethod.GET, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> getProductByCategoryId(
            @PathVariable("companyId") Long companyId,
            @RequestParam Long categoryId,
            @RequestParam(required = false, defaultValue = Constant.DEFAULT_PAGE_NUMBER) Integer pageNumber,
            @RequestParam(required = false, defaultValue = Constant.DEFAULT_PAGE_SIZE) Integer pageSize) {

        List<Product> products = productService.getByCompanyIdAndCategoryId(companyId, categoryId, pageNumber, pageSize);
        return responseUtil.successResponse(products);
    }

    @ApiOperation(value = "filter product list", notes = "")
    @RequestMapping(value = APIName.PRODUCTS_FILTER_LIST, method = RequestMethod.POST, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> getProductFilterList(HttpServletRequest request,
            @RequestBody ListProductModel listProduct) {
        try {
            Page<Product> products = productService.doFilterSearchSortPagingProduct(listProduct.getCompanyId(), listProduct.getCategoryId(), listProduct.getAttributeId(), listProduct.getSearchKey(), listProduct.getMinPrice(), listProduct.getMaxPrice(), listProduct.getMinRank(), listProduct.getMaxRank(), listProduct.getSortCase(), listProduct.getAscSort(), listProduct.getPageSize(), listProduct.getPageNumber());
            PagingResponseModel finalRes = new PagingResponseModel(products.getContent(), products.getTotalElements(), products.getTotalPages(), products.getNumber());
            return responseUtil.successResponse(finalRes);
        } catch (Exception ex) {
            throw new ApplicationException(APIStatus.GET_LIST_PRODUCT_ERROR);
        }
    }

    @ApiOperation(value = "create product", notes = "")
    @RequestMapping(value = APIName.PRODUCT_CREATE, method = RequestMethod.POST, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> createProduct(@RequestBody CreateProductModel productRequest) {
        Product product = new Product();
        product.setBrowsingName(productRequest.getBrowsingName());
        product.setCompanyId(productRequest.getCompanyId());
        product.setDefaultImage(productRequest.getDefaultImage());
        product.setDescription(productRequest.getDescription());
        product.setIsStockControlled(productRequest.getIsStockControlled());
        product.setListPrice(productRequest.getListPrice());
        product.setName(productRequest.getName());
        product.setOverview(productRequest.getOverview());
        product.setQuantity(productRequest.getQuantity());
        product.setRank(productRequest.getRank());
        product.setSalePrice(productRequest.getSalePrice());
        product.setSku(productRequest.getSku());
        product.setStatus(Constant.STATUS.ACTIVE_STATUS.getValue());
        //create product
        productService.save(product);
        //create product categories
        for (Long categoriesId : productRequest.getListCategoriesId()) {
            ProductCategoryId productCategoryId = new ProductCategoryId();
            productCategoryId.setCategoryId(categoriesId);
            productCategoryId.setProductId(product.getId());
            productService.saveProductCategory(productCategoryId);
        }
        return responseUtil.successResponse(product);

    }

    @ApiOperation(value = "delete product list", notes = "")
    @RequestMapping(value = APIName.PRODUCTS_DELETE, method = RequestMethod.POST, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> deleteProduct(HttpServletRequest request,
            @RequestBody List<Long> ids,
            @PathVariable(value = "company_id") Long companyId) {
        try {
            for (Long id : ids) {
                Product product = productService.getProductById(companyId, id);
                if (product != null) {
                    product.setStatus(Constant.STATUS.DELETED_STATUS.getValue());
                    product.setDeleted(true);
                    productService.update(product);
                    List<ProductCategoryId> listProductCate = productCategoryRepository.findByProductIdAndDeletedFalse(id);
                    for (ProductCategoryId result : listProductCate) {
                        result.setDeleted(true);
                        productCategoryRepository.save(result);
                    }
                }
            }
            return responseUtil.successResponse(null);
        } catch (Exception ex) {
            throw new ApplicationException(APIStatus.DELETE_PRODUCT_ERROR);
        }
    }

    @ApiOperation(value = "update product", notes = "")
    @RequestMapping(value = APIName.PRODUCTS_UPDATE, method = RequestMethod.POST, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> updateProduct(HttpServletRequest request,
            @RequestBody CreateProductModel productRequest) {
        try {
            Product product = productService.getProductById(productRequest.getCompanyId(), productRequest.getProductId());
            if (product != null) {
                product.setBrowsingName(productRequest.getBrowsingName());
                product.setDefaultImage(productRequest.getDefaultImage());
                product.setDescription(productRequest.getDescription());
                product.setIsStockControlled(productRequest.getIsStockControlled());
                product.setListPrice(productRequest.getListPrice());
                product.setName(productRequest.getName());
                product.setOverview(productRequest.getOverview());
                product.setQuantity(productRequest.getQuantity());
                product.setRank(productRequest.getRank());
                product.setSalePrice(productRequest.getSalePrice());
                product.setSku(productRequest.getSku());
                productService.update(product);
                List<ProductCategoryId> listProductCate = productCategoryRepository.findByProductIdAndDeletedFalse(productRequest.getProductId());
                for (ProductCategoryId result : listProductCate) {
                    result.setDeleted(true);
                    productCategoryRepository.save(result);
                }
                for (Long categoriesId : productRequest.getListCategoriesId()) {
                    ProductCategoryId productCategoryId = new ProductCategoryId();
                    productCategoryId.setCategoryId(categoriesId);
                    productCategoryId.setProductId(product.getId());
                    productCategoryRepository.save(productCategoryId);
                }
                return responseUtil.successResponse(product);
            } else {
                throw new ApplicationException(APIStatus.GET_PRODUCT_ERROR);
            }

        } catch (Exception ex) {
            throw new ApplicationException(APIStatus.UPDATE_PRODUCT_ERROR);
        }
    }

    //@ApiOperation(value = "delete product list", notes = "")
    @RequestMapping(value = APIName.PRODUCTS_IMAGE_UPLOAD, method = RequestMethod.POST, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> uploadImage(@RequestParam("imageFile") MultipartFile file) {
        try {
            productService.uploadImage(file);
            return responseUtil.successResponse(null);
        } catch (Exception ex) {
            throw new ApplicationException(APIStatus.DELETE_PRODUCT_ERROR);
        }
    }

    //@ApiOperation(value = "delete product list", notes = "")
    @RequestMapping(value = APIName.PRODUCTS_IMAGE, method = RequestMethod.POST, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> getImage(ImageRequest imageRequest) {
        try {
            return responseUtil.successResponse(productService.getImages(imageRequest.getProductId(),imageRequest.getImageTypes()));
        } catch (Exception ex) {
            throw new ApplicationException(APIStatus.DELETE_PRODUCT_ERROR);
        }
    }
}
