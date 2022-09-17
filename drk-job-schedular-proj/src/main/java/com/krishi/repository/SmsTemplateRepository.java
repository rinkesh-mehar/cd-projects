package com.krishi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.entity.SmsTemplate;

public interface SmsTemplateRepository extends JpaRepository<SmsTemplate, Integer> {

	SmsTemplate findByNameAndIsActive(String string, Boolean b);

}
