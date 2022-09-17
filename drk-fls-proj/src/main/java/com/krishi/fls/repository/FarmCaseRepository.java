package com.krishi.fls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.krishi.fls.entity.FarmCase;

import java.util.List;
import java.util.Set;

public interface FarmCaseRepository extends JpaRepository<FarmCase, String> {
	
	/*
	 * @Query(name="select f from FarmCase f where f.id in (:caseId)") public
	 * List<FarmCase> findbyCaseId(@Param("caseId")List<String> caseId);
	 */
	

	@Query("select f from FarmCase f where f.id=:caseID" )
	public FarmCase getBycaseID(@Param(value = "caseID") String caseID);


	@Query(value = "select DISTINCT ff.farmer_id,\n" +
			"\tgbb.id as branch_id,\n" +
			"    gbb.name as branchName,\n" +
			"    gb.name as bankName,\n" +
			"    fba.account_name,\n" +
			"    fba.acc_number,\n" +
			"    fba.passbook_image_url,\n" +
			"    fba.cancelled_cheque_url,\n" +
			"\tconcat(gb.name,' - ', gbb.name,' - ', fba.account_name,' - ', fba.acc_number,' - ', gbb.ifsc) as bank_info\n" +
			"from farmer_bank_account as fba\n" +
			"inner join farm_case as fc on fc.id = fba.case_id\n" +
			"inner join farmer_farm as ff on ff.id = fc.farm_id\n" +
			"inner join general_bank_branch as gbb on gbb.id = fba.bank_branch_id\n" +
			"inner join general_bank as gb on gb.id = gbb.bank_id\n" +
			"where ff.farmer_id in (?1) and fba.status = 'Active'\n" +
			"group by ff.farmer_id,gbb.id, gb.name, gbb.name, fba.account_name, fba.acc_number, gbb.ifsc,fba.passbook_image_url,\n" +
			"fba.cancelled_cheque_url ", nativeQuery = true)
	List<Object[]> getFarmerBankDetails(List<String> farmerIds);
}
