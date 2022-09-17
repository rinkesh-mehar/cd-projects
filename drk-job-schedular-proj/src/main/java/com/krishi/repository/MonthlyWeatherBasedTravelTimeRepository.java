package com.krishi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.krishi.entity.MonthlyWeatherBasedTravelTime;

/**
 * @author CDT-Ujwal
 *
 */

@Repository
public interface MonthlyWeatherBasedTravelTimeRepository extends JpaRepository<MonthlyWeatherBasedTravelTime, Integer> {

}
