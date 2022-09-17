package com.krishi.fls.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.fls.entity.FarmerLoanInfo;

public interface FarmerLoanInfoRepository extends JpaRepository<FarmerLoanInfo, String> {

}
