package com.nitsoft.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface AbstractRepo<S, U extends Serializable> extends JpaRepository<S, U> {

}

