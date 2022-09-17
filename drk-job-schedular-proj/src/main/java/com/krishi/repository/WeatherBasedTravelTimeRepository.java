package com.krishi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.krishi.entity.WeatherBasedTravelTime;

/**
 * @author CDT-Ujwal
 *
 */

@Repository
public interface WeatherBasedTravelTimeRepository extends JpaRepository<WeatherBasedTravelTime, Integer> {

}
