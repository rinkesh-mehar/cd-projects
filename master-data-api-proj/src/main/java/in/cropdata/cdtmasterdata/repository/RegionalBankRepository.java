package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.dto.interfaces.RegionalBankInfDto;
import in.cropdata.cdtmasterdata.model.RegionalBank;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegionalBankRepository extends JpaRepository<RegionalBank, Integer> {

	

	@Query(value = "select RB.ID,RB.BankID,RB.StateCode,RB.Status, GB.Name as Bank, GS.Name as State, RB.IsValid, RB.ErrorMessage from regional_bank RB  \n" +
			"			Left Join general_bank GB on(RB.BankID = GB.ID) \n" + 
			"			Left Join geo_state GS on(RB.StateCode = GS.StateCode)\n" + 
			"			where GB.Name like :searchText\n" + 
			"			OR GS.Name like :searchText",
			countQuery = "select RB.ID,RB.BankID,RB.StateCode,RB.Status, GB.Name as Bank, GS.Name as State, RB.IsValid, RB.ErrorMessage from regional_bank RB  \n" +
					"			Left Join general_bank GB on(RB.BankID = GB.ID) \n" + 
					"			Left Join geo_state GS on(RB.StateCode = GS.StateCode)\n" + 
					"			where GB.Name like :searchText\n" + 
					"			OR GS.Name like :searchText", nativeQuery = true)
	Page<RegionalBankInfDto> getRegionalBank(Pageable pageable, String searchText);
	
	@Query(value = "select RB.ID,RB.BankID,RB.StateCode,RB.Status, GB.Name as Bank, GS.Name as State from regional_bank_missing RB  \n" + 
			"			Left Join general_bank GB on(RB.BankID = GB.ID) \n" + 
			"			Left Join geo_state GS on(RB.StateCode = GS.StateCode)\n" + 
			"			where GB.Name like :searchText\n" + 
			"			OR GS.Name like :searchText",
			countQuery = "select RB.ID,RB.BankID,RB.StateCode,RB.Status, GB.Name as Bank, GS.Name as State from regional_bank_missing RB  \n" + 
					"			Left Join general_bank GB on(RB.BankID = GB.ID) \n" + 
					"			Left Join geo_state GS on(RB.StateCode = GS.StateCode)\n" + 
					"			where GB.Name like :searchText\n" + 
					"			OR GS.Name like :searchText", nativeQuery = true)
	Page<RegionalBankInfDto> getRegionalBankMissing(Pageable pageable, String searchText);

	@Query(value = "select RB.ID,RB.BankID,RB.StateCode,RB.Status, GB.Name as Bank, GS.Name as State, RB.IsValid, RB.ErrorMessage  from regional_bank RB  \n" +
			"			Left Join general_bank GB on(RB.BankID = GB.ID) \n" +
			"			Left Join geo_state GS on(RB.StateCode = GS.StateCode)\n" +
			"			where RB.IsValid = 0 and (GB.Name like :searchText\n" +
			"			OR GS.Name like :searchText)",
			countQuery = "select RB.ID,RB.BankID,RB.StateCode,RB.Status, GB.Name as Bank, GS.Name as State, RB.IsValid, RB.ErrorMessage from regional_bank RB  \n" +
					"			Left Join general_bank GB on(RB.BankID = GB.ID) \n" +
					"			Left Join geo_state GS on(RB.StateCode = GS.StateCode)\n" +
					"			where RB.IsValid = 0 and (GB.Name like :searchText\n" +
					"			OR GS.Name like :searchText)", nativeQuery = true)
	Page<RegionalBankInfDto> getRegionalBankInvalidated(Pageable pageable, String searchText);
	
	@Query(value = "select RB.ID,RB.BankID,RB.StateCode,RB.Status, GB.Name as Bank, GS.Name as State, RB.IsValid  from regional_bank_missing RB  \n" +
			"			Left Join general_bank GB on(RB.BankID = GB.ID) \n" +
			"			Left Join geo_state GS on(RB.StateCode = GS.StateCode)\n" +
			"			where RB.IsValid = 0 and (GB.Name like :searchText\n" +
			"			OR GS.Name like :searchText)",
			countQuery = "select RB.ID,RB.BankID,RB.StateCode,RB.Status, GB.Name as Bank, GS.Name as State, RB.IsValid from regional_bank_missing RB  \n" +
					"			Left Join general_bank GB on(RB.BankID = GB.ID) \n" +
					"			Left Join geo_state GS on(RB.StateCode = GS.StateCode)\n" +
					"			where RB.IsValid = 0 and (GB.Name like :searchText\n" +
					"			OR GS.Name like :searchText)", nativeQuery = true)
	Page<RegionalBankInfDto> getRegionalBankMissingInvalidated(Pageable pageable, String searchText);

	Optional<RegionalBank> findByBankIdAndStateCode(int bankId, int stateCode);
}
