package com.krishi.fls.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.fls.entity.BankDetails;

public interface BankDetailsRepository extends JpaRepository<BankDetails, Integer> {

	List<BankDetails> findByCaseIdIn(Set<String> caseId);

}
