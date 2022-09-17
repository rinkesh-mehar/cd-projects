package com.krishi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.entity.Language;

public interface LanguageRepository extends JpaRepository<Language, Integer>{

	List<Language> findByStatus(Integer status);

}
