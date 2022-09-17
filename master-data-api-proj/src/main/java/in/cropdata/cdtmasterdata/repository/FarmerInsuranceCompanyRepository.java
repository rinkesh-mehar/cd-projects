package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.dto.interfaces.FarmerInsuranceCompanyInfDto;
import in.cropdata.cdtmasterdata.model.FarmerInsuranceCompany;

public interface FarmerInsuranceCompanyRepository extends JpaRepository<FarmerInsuranceCompany, Integer> {
	
	@Query(value = "SELECT FIC.ID,FIC.Name,FIC.InsuranceTypeID,FIC.Status, FIT.Name as InsuranceType, FIC.IsValid, FIC.ErrorMessage FROM farmer_insurance_company FIC\n" +
			"Left Join farmer_insurance_type FIT ON(FIT.ID = FIC.InsuranceTypeID)\n" + 
			"					Where FIC.Name like :searchText\n" + 
			"					OR FIT.Name like :searchText",
			countQuery = "SELECT FIC.ID,FIC.Name,FIC.InsuranceTypeID,FIC.Status, FIT.Name as InsuranceType, FIC.IsValid, FIC.ErrorMessage FROM farmer_insurance_company FIC\n" +
					"Left Join farmer_insurance_type FIT ON(FIT.ID = FIC.InsuranceTypeID)\n" + 
					"					Where FIC.Name like :searchText\n" + 
					"					OR FIT.Name like :searchText", nativeQuery = true)
	Page<FarmerInsuranceCompanyInfDto> findAllWithSearch(Pageable sortedByIdDesc, String searchText);

	@Query(value = "SELECT FIC.ID,FIC.Name,FIC.InsuranceTypeID,FIC.Status, FIT.Name as InsuranceType, FIC.IsValid, FIC.ErrorMessage FROM farmer_insurance_company FIC\n" +
			"Left Join farmer_insurance_type FIT ON(FIT.ID = FIC.InsuranceTypeID)\n" +
			"					Where FIC.IsValid = 0 and (FIC.Name like :searchText\n" +
			"					OR FIT.Name like :searchText)",
			countQuery = "SELECT FIC.ID,FIC.Name,FIC.InsuranceTypeID,FIC.Status, FIT.Name as InsuranceType, FIC.IsValid, FIC.ErrorMessage FROM farmer_insurance_company FIC\n" +
					"Left Join farmer_insurance_type FIT ON(FIT.ID = FIC.InsuranceTypeID)\n" +
					"					Where FIC.IsValid = 0 and (FIC.Name like :searchText\n" +
					"					OR FIT.Name like :searchText)", nativeQuery = true)
	Page<FarmerInsuranceCompanyInfDto> findAllWithSearchInvalidated(Pageable sortedByIdDesc, String searchText);

}
