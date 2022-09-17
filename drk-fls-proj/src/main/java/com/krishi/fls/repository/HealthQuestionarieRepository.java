package com.krishi.fls.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.krishi.fls.entity.HealthQuestionnaire;

public interface HealthQuestionarieRepository extends JpaRepository<HealthQuestionnaire, Integer> {
	public List<HealthQuestionnaire> getHealthQuestionnaireByCommodityIdIn(@Param("commodityIds") Set<Integer> commodityIds);

	public List<HealthQuestionnaire> getHealthQuestionnaireByAczIdInAndVarietyIdIn(@Param("aczIds") Set<Integer> aczIds, @Param("varietyIds") Set<Integer> vaIntegers);
}
