/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.service.product;

import com.nitsoft.ecommerce.database.model.entity.Product;
import com.nitsoft.ecommerce.database.model.entity.ProductCategory;
import com.nitsoft.ecommerce.database.model.entity.ProductCategoryId;
import java.util.List;

import com.nitsoft.util.Constant;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author acer
 */
public interface ProductService {
    //get all product
    Iterable<Product> findAllProduct();
    //get product by id
    Product getProductById(long companyId, long productId);
    //get product by id
//    List<Object[]> getProductById(long productId);
    // get by company id
    Page<Product> getAll(int pageNumber, int pageSize);
    //get by company id category id
    List<Product> getByCompanyIdAndCategoryId(long companyId, long categoryId, int pageNumber, int pageSize);
    //get filter
    Page<Product> doFilterSearchSortPagingProduct(long comId, long catId, long attrId, String searchKey, double mnPrice, double mxPrice, int minRank, int maxRank, int sortKey, boolean isAscSort, int pSize, int pNumber);
    //get list product by id
    Iterable<Product> getProductsById(long companyId, List<Long> productIds);
    //save product
    Product save(Product product);
    //update product
    Product update(Product product);

    void saveProductCategory(ProductCategoryId product);

    void deleteProductCategory(ProductCategory product);

    void uploadImage(MultipartFile multipartFile);

    List<String> getImages(Long id, List<Constant.IMAGE_TYPE> imageTypes);
}
