package com.nitsoft.ecommerce.repository;

import com.nitsoft.ecommerce.database.model.Company;

public interface CompanyRepository extends AbstractRepo<Company, Integer> {

    Company findByIdAndDeletedFalse(long companyId);
    
}
