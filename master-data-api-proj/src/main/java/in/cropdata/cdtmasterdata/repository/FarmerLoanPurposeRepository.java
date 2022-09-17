package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.model.FarmerLoanPurpose;

public interface FarmerLoanPurposeRepository extends JpaRepository<FarmerLoanPurpose, Integer> {
	
	@Query(value="select ID,Name,Status from farmer_loan_purpose\n" + 
			"where ID  like :searchText OR Name like :searchText OR  Status like :searchText",countQuery = "select ID,Name,Status from farmer_loan_purpose\n" + 
			"where ID  like :searchText OR Name like :searchText OR  Status like :searchText",nativeQuery = true)
	Page<FarmerLoanPurpose> getFarmerLoanPurposeListByPagenation(Pageable sortedByIdDesc, String searchText);

}
