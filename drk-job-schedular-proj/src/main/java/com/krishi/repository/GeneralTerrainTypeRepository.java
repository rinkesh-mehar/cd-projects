/**
 * 
 */
package com.krishi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.krishi.entity.GeneralTerrainType;

/**
 * @author CDT-Ujwal
 *
 */
@Repository
public interface GeneralTerrainTypeRepository extends JpaRepository<GeneralTerrainType, Integer> {

}
