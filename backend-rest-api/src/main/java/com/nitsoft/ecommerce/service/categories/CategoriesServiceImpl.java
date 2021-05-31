/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.service.categories;

import com.nitsoft.ecommerce.database.model.Category;
import com.nitsoft.ecommerce.repository.CategoryRepository;
import com.nitsoft.ecommerce.repository.specification.CategorySpecifications;
import com.nitsoft.ecommerce.service.AbstractBaseService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * @author tungn
 */
@Service
public class CategoriesServiceImpl extends AbstractBaseService implements CategoriesService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private CategorySpecifications categorySpecifications;


    @Override
    public Category saveOrUpdate(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> saveOrUpdate(List<Category> categories) {
        return (List<Category>) categoryRepository.saveAll(categories);
    }

    @Override
    public Category getActiveById(long categoryId) {
        return categoryRepository.findByIdAndStatus(categoryId, 1);
    }

    @Override
    public List<Category> getAllActiveByIdsAndCompanyId(List<Long> categoryIds, long companyId) {
        return categoryRepository.findAllByIdInAndCompanyIdAndStatus(categoryIds, companyId, 1);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<Category> getAllActiveWithFilterSearchSort(long companyId, String keyword, int pageNumber, int pageSize, int sortCase, boolean ascSort) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        // create specification
        Specification spec = categorySpecifications.doFilterSearchSort(companyId, keyword, sortCase, ascSort);
        return categoryRepository.findAll(spec, pageable);
    }

}
