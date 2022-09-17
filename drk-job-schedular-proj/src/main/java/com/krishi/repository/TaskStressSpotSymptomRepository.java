package com.krishi.repository;

import com.krishi.entity.TaskStressSpotSymptoms;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface TaskStressSpotSymptomRepository extends JpaRepository<TaskStressSpotSymptoms, String> {

    Optional<TaskStressSpotSymptoms> findByTaskSpotStressSymptomIdInAndSymptomId(List<String> taskSpotStressSymptomId, Integer symptomId);

    Optional<TaskStressSpotSymptoms> findByTaskSpotStressSymptomIdAndSymptomId(String taskSpotStressSymptomId, Integer symptomId);
}
