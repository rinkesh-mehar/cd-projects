package com.krishi.repository;

import com.krishi.entity.TaskStressSpotSymptomImages;
import com.krishi.entity.TaskStressSpotSymptoms;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface TaskStressSpotSymptomImagesRepository extends JpaRepository<TaskStressSpotSymptomImages, String> {

    Optional<TaskStressSpotSymptomImages> findByTaskSpotStressSymptomIdAndSide(String taskStressSpotStressSymptomId, String side);
}
