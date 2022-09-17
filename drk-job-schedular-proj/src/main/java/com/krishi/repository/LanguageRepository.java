package com.krishi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import com.krishi.entity.Commodity;
import com.krishi.entity.Language;


public interface LanguageRepository
  extends JpaRepository<Language, Integer> , QueryByExampleExecutor<Language> {
}