package com.krishi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.krishi.entity.AgrochemicalBrand;

@NoRepositoryBean
public interface GenericRepository<T> extends JpaRepository<T, Integer> {

}
