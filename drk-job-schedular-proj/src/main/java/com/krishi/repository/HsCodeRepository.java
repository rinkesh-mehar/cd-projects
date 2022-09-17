package com.krishi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.krishi.entity.HsCode;

/**
 * @author CDT-Rinkesh
 *
 */
@Repository
public interface HsCodeRepository extends JpaRepository<HsCode, Integer> {

}
