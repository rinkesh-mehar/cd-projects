package com.krishi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.krishi.entity.FarmerBankAccount;



public interface FarmerBankAccountRepository  extends JpaRepository<FarmerBankAccount, Integer>{

	/** replace farmerId to caseId - As per discussion in 21/08/2021*/
//	FarmerBankAccount findByFarmerId(String farmerId);
	FarmerBankAccount findByCaseId(String caseId);
	
	@Query("select f from  FarmerBankAccount f where f.pennydropDate is NULL And f.isPennydropped=0")
	List<FarmerBankAccount> findForpennyDrop(); 
	
	
	FarmerBankAccount findAllById(String Id);
}
