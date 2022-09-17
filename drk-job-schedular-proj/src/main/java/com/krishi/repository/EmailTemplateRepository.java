package com.krishi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.entity.EmailTemplate;

public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Integer> {

	EmailTemplate findByNameAndIsActive(String templateName, boolean b);

}
