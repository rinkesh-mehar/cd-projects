package com.krishi.repository;

import com.krishi.entity.TaskSpot;
import com.krishi.entity.TaskSpotStress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskSpotRepository extends JpaRepository<TaskSpot, String> {

}
