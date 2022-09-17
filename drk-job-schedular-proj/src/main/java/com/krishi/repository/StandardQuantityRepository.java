package com.krishi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.krishi.entity.StandardQuantityChart;

/**
 * @author CDT-Rinkesh
 *
 */
@Repository
public interface StandardQuantityRepository extends JpaRepository<StandardQuantityChart, Integer>{

}
