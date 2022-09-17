package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.dto.interfaces.FarmerLoanSourceInfo;
import in.cropdata.cdtmasterdata.model.FarmerLoanSource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FarmerLoanSourceRepository extends JpaRepository<FarmerLoanSource, Integer> {
	
	@Query(value = "SELECT FLS.ID,FLS.Name,FLS.Status FROM farmer_loan_source FLS where FLS.Name like :searchText",
			countQuery = "SELECT FLS.ID,FLS.Name,FLS.Status FROM farmer_loan_source FLS where FLS.Name like :searchText", nativeQuery = true)
	Page<FarmerLoanSourceInfo> findAllWithSearch(Pageable sortedByIdDesc, String searchText);

	Optional<String> findByName (String name);
}
