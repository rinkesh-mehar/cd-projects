package com.krishi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.krishi.entity.Bank;
import com.krishi.model.DataInsertionModel;

/**
 * @author CDT - Pranay
 */

public interface BankRepository extends JpaRepository<Bank, Integer> {

	@Query(value = "select id, name from general_bank", nativeQuery = true)
	List<DataInsertionModel> getBankData();
}
