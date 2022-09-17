/**
 * 
 */
package com.krishi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.krishi.entity.TaskTypeSpecifications;

/**
 * @author CDT-Ujwal
 *
 */
@Repository
public interface TaskTypeSpecificationRepository extends JpaRepository<TaskTypeSpecifications, Integer> {

}
