package com.krishi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.krishi.entity.BankBranch;
import com.krishi.model.DataInsertionModel;

/**
 * @author CDT - Pranay
 */

public interface BankBranchRepository extends JpaRepository<BankBranch, Integer> {

	@Query(value = "select gb.id as id, gb.bank_id as bankId, gb.name as name, gb.ifsc as ifsc "
			+ "from general_bank_branch gb inner join district dist on dist.district_code = gb.district_id\n"
			+ "inner join region r on r.state_id = dist.state_id\n"
			+ "where r.id = ?1 order by gb.name", nativeQuery = true)
	List<DataInsertionModel> getBankBranchData(Integer regionId);
}
