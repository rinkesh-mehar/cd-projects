package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.dto.interfaces.FarmerLanguageInfo;
import in.cropdata.cdtmasterdata.model.FarmerLanguage;

public interface FarmerLanguageRepository extends JpaRepository<FarmerLanguage, Integer> {

	@Query(value = "SELECT FL.ID,FL.Name,FL.Status FROM farmer_language FL\n" + 
			"			where FL.Name like :searchText",
			countQuery = "SELECT FL.ID,FL.Name,FL.Status FROM farmer_language FL\n" + 
					"					where FL.Name like :searchText", nativeQuery = true)
	Page<FarmerLanguageInfo> findAllWithSearch(Pageable sortedByIdDesc, String searchText);
}
