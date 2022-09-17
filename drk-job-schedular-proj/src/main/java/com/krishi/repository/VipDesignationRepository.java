package com.krishi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.krishi.entity.VipDesignation;

public interface VipDesignationRepository extends JpaRepository<VipDesignation, Integer> {

}
