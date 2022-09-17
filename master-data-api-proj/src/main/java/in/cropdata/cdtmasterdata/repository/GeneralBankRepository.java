package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.dto.interfaces.GeneralBankInfDto;
import in.cropdata.cdtmasterdata.model.GeneralBank;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GeneralBankRepository extends JpaRepository<GeneralBank, Integer> {
	
	
	@Query(value = "SELECT GB.ID,GB.Name,GB.BankCategoryID,GB.Status, GBC.Name as BankCategory, GB.IsValid, GB.ErrorMessage FROM general_bank GB \n" +
			"			Left Join general_bank_category GBC on (GB.BankCategoryID = GBC.ID)\n" + 
			"			where GB.Name like :searchText\n" + 
			"			OR GBC.Name like :searchText",
			countQuery = "SELECT GB.ID,GB.Name,GB.BankCategoryID,GB.Status, GBC.Name as BankCategory, GB.IsValid, GB.ErrorMessage FROM general_bank GB \n" +
					"			Left Join general_bank_category GBC on (GB.BankCategoryID = GBC.ID)\n" + 
					"			where GB.Name like :searchText\n" + 
					"			OR GBC.Name like :searchText", nativeQuery = true)
	Page<GeneralBankInfDto> findAllByBankList(Pageable pageable, String searchText);

	@Query(value = "SELECT GB.ID,GB.Name,GB.BankCategoryID,GB.Status, GBC.Name as BankCategory, GB.IsValid, GB.ErrorMessage FROM general_bank GB \n" +
			"			Left Join general_bank_category GBC on (GB.BankCategoryID = GBC.ID)\n" +
			"			where GB.IsValid = 0 and (GB.Name like :searchText\n" +
			"			OR GBC.Name like :searchText)",
			countQuery = "SELECT GB.ID,GB.Name,GB.BankCategoryID,GB.Status, GBC.Name as BankCategory, GB.IsValid, GB.ErrorMessage FROM general_bank GB \n" +
					"			Left Join general_bank_category GBC on (GB.BankCategoryID = GBC.ID)\n" +
					"			where GB.IsValid = 0 and (GB.Name like :searchText\n" +
					"			OR GBC.Name like :searchText)", nativeQuery = true)
	Page<GeneralBankInfDto> findAllByBankListInvalidated(Pageable pageable, String searchText);


	@Query(value = "SELECT GB.ID, GB.Name, GB.BankCategoryID, GB.Status, GBC.Name BankCategory\n" +
			"FROM cdt_master_data.general_bank GB\n" +
			"LEFT JOIN cdt_master_data.general_bank_category GBC ON (GB.BankCategoryID = GBC.ID)\n" +
			"ORDER BY GB.Name", nativeQuery = true)
	List<GeneralBankInfDto> generalBankList();

	@Query(value = "SELECT GB.ID, GB.Name, GB.BankCategoryID, GB.Status, GBC.Name BankCategory\n" +
			"FROM cdt_master_data.general_bank GB\n" +
			"LEFT JOIN cdt_master_data.general_bank_category GBC ON (GB.BankCategoryID = GBC.ID)\n" +
			"WHERE GB.Status = 'Active' ORDER BY GB.Name", nativeQuery = true)
	List<GeneralBankInfDto> getAllActiveBank();

	Optional<GeneralBank> findByName(String bankName);

}
