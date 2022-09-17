package com.krishi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.entity.CallingStatus;

/**
 * @author CDT - Pranay
 */

public interface CallingStatusRepository extends JpaRepository<CallingStatus, Integer> {

}
