/**
 * 
 */
package com.krishi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.krishi.entity.VillageToRlDistance;

/**
 * @author CDT-Ujwal
 *
 */
@Repository
public interface VillageToRlDistanceRepository extends JpaRepository<VillageToRlDistance, Integer> {

}
