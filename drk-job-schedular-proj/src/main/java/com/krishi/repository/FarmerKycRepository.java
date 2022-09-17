package com.krishi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.entity.FarmerKyc;

public interface FarmerKycRepository extends JpaRepository<FarmerKyc, String> {
	List<FarmerKyc> findByFarmerId(String farmerID);
}
