package com.krishi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.entity.HealthQuestionnaire;

public interface HealthQuestionnaireRepository extends JpaRepository<HealthQuestionnaire, Integer> {

}
