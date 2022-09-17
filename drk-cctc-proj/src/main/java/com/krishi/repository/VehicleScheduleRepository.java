package com.krishi.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.entity.VehicleSchedule;

public interface VehicleScheduleRepository extends JpaRepository<VehicleSchedule, Integer> {

	List<VehicleSchedule> findByVillageIdAndVisitDateBetween(Integer villageId, Date date, Date date2);

	List<VehicleSchedule> findByVillageIdAndVisitDateIn(Integer villageId, List<Date> scheduleDates);

}
