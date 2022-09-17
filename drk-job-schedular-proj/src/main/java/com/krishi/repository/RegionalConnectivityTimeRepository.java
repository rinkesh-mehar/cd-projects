/**
 * 
 */
package com.krishi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.krishi.entity.RegionalConnectivityTime;

/**
 * @author CDT-Ujwal
 *
 */
@Repository
public interface RegionalConnectivityTimeRepository extends JpaRepository<RegionalConnectivityTime, Integer>{

}
