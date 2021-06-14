package com.nitsoft.ecommerce.repository;

import com.nitsoft.ecommerce.database.model.entity.Company;

public interface CompanyRepository extends AbstractRepo<Company, Integer> {

    Company findByIdAndDeletedFalse(long companyId);
    
}
