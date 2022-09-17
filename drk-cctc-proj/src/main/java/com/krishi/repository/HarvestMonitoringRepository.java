package com.krishi.repository;

import com.krishi.entity.HarvestMonitoringTechnicalCalling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HarvestMonitoringRepository extends JpaRepository<HarvestMonitoringTechnicalCalling, Integer>{

}
