package in.cropdata.cdtmasterdata.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.dto.interfaces.RegionalStressInfDto;
import in.cropdata.cdtmasterdata.model.RegionalStress;

public interface RegionalStressRepository extends JpaRepository<RegionalStress, Integer> {

	@Query(value = "SELECT RS.ID,RS.StateCode,RS.RegionID,RS.StressID,RS.UpdatedAt,\n"
			+ "RS.CreatedAt,RS.Status, GR.Name as Region,ACS.Name as Stress,GS.Name as State\n"
			+ "FROM regional_stress RS \n" + "LEFT JOIN geo_region GR ON (RS.RegionID = GR.RegionID)\n"
			+ "LEFT JOIN geo_state GS ON (RS.StateCode = GS.StateCode)\n"
			+ "LEFT JOIN agri_commodity_stress ACS on (ACS.ID = RS.StressID)", nativeQuery = true)
	List<RegionalStressInfDto> getRegionalStressList();

	@Query(value = "SELECT RS.ID,RS.StateCode,RS.RegionID,RS.StressID,RS.UpdatedAt,\n" + 
			"			RS.CreatedAt,RS.Status, GR.Name as Region,ACS.Name as Stress,GS.Name as State, RS.IsValid, RS.ErrorMessage\n" +
			"			FROM regional_stress RS  LEFT JOIN geo_region GR ON (RS.RegionID = GR.RegionID)\n" + 
			"			LEFT JOIN geo_state GS ON (RS.StateCode = GS.StateCode)\n" + 
			"			LEFT JOIN agri_stress ACS on (ACS.ID = RS.StressID)\n" +
			"			where ACS.Name like :searchText\n" +
			"			OR GR.Name like :searchText\n" + 
			"			OR GS.Name like :searchText", 
			countQuery = "SELECT RS.ID,RS.StateCode,RS.RegionID,RS.StressID,RS.UpdatedAt,\n" + 
					"			RS.CreatedAt,RS.Status, GR.Name as Region,ACS.Name as Stress,GS.Name as State\n" +
					"			FROM regional_stress RS  LEFT JOIN geo_region GR ON (RS.RegionID = GR.RegionID)\n" + 
					"			LEFT JOIN geo_state GS ON (RS.StateCode = GS.StateCode)\n" + 
					"			LEFT JOIN agri_stress ACS on (ACS.ID = RS.StressID)\n" +
					"			where ACS.Name like :searchText\n" +
					"			OR GR.Name like :searchText\n" + 
					"			OR GS.Name like :searchText", nativeQuery = true)
	Page<RegionalStressInfDto> getRegionalStress(Pageable pageable, String searchText);
	
	@Query(value = "SELECT RS.ID,RS.StateCode,RS.RegionID,RS.StressID,RS.UpdatedAt,\n" + 
			"			RS.CreatedAt,RS.Status, GR.Name as Region,ACS.Name as Stress,GS.Name as State\n" +
			"			FROM regional_stress_missing RS  LEFT JOIN geo_region GR ON (RS.RegionID = GR.RegionID)\n" + 
			"			LEFT JOIN geo_state GS ON (RS.StateCode = GS.StateCode)\n" + 
			"			LEFT JOIN agri_stress ACS ON (RS.StressID = ACS.ID)\n" +
			"			where ACS.Name like :searchText\n" +
			"			OR GR.Name like :searchText\n" + 
			"			OR GS.Name like :searchText", 
			countQuery = "SELECT RS.ID,RS.StateCode,RS.RegionID,RS.StressID,RS.UpdatedAt,\n" + 
					"			RS.CreatedAt,RS.Status, GR.Name as Region,ACS.Name as Stress,GS.Name as State\n" +
					"			FROM regional_stress_missing RS  LEFT JOIN geo_region GR ON (RS.RegionID = GR.RegionID)\n" + 
					"			LEFT JOIN geo_state GS ON (RS.StateCode = GS.StateCode)\n" + 
					"			LEFT JOIN agri_stress ACS ON (RS.StressID = ACS.ID)\n" +
					"			where ACS.Name like :searchText\n" +
					"			OR GR.Name like :searchText\n" + 
					"			OR GS.Name like :searchText", nativeQuery = true)
	Page<RegionalStressInfDto> getRegionalStressMissing(Pageable pageable, String searchText);

	@Query(value = "SELECT RS.ID,RS.StateCode,RS.RegionID,RS.StressID,RS.UpdatedAt,\n" +
			"			RS.CreatedAt,RS.Status, GR.Name as Region,ACS.Name as Stress,GS.Name as State, RS.IsValid, RS.ErrorMessage\n" +
			"			FROM regional_stress RS  LEFT JOIN geo_region GR ON (RS.RegionID = GR.RegionID)\n" +
			"			LEFT JOIN geo_state GS ON (RS.StateCode = GS.StateCode)\n" +
			"			LEFT JOIN agri_stress ACS on (ACS.ID = RS.StressID)\n" +
			"			where RS.IsValid = 0 and (ACS.Name like :searchText\n" +
			"			OR GR.Name like :searchText\n" +
			"			OR GS.Name like :searchText)",
			countQuery = "SELECT RS.ID,RS.StateCode,RS.RegionID,RS.StressID,RS.UpdatedAt,\n" +
					"			RS.CreatedAt,RS.Status, GR.Name as Region,ACS.Name as Stress,GS.Name as State\n" +
					"			FROM regional_stress RS  LEFT JOIN geo_region GR ON (RS.RegionID = GR.RegionID)\n" +
					"			LEFT JOIN geo_state GS ON (RS.StateCode = GS.StateCode)\n" +
					"			LEFT JOIN agri_stress ACS on (ACS.ID = RS.StressID)\n" +
					"			where RS.IsValid = 0 and (ACS.Name like :searchText\n" +
					"			OR GR.Name like :searchText\n" +
					"			OR GS.Name like :searchText)", nativeQuery = true)
	Page<RegionalStressInfDto> getRegionalStressInvalidated(Pageable pageable, String searchText);
	
	@Query(value = "SELECT RS.ID,RS.StateCode,RS.RegionID,RS.StressID,RS.UpdatedAt,\n" +
			"			RS.CreatedAt,RS.Status, GR.Name as Region,ACS.Name as Stress,GS.Name as State \n" +
			"			FROM regional_stress_missing RS  LEFT JOIN geo_region GR ON (RS.RegionID = GR.RegionID)\n" +
			"			LEFT JOIN geo_state GS ON (RS.StateCode = GS.StateCode)\n" +
			"			LEFT JOIN agri_stress ACS on (ACS.ID = RS.StressID)\n" +
			"			where RS.IsValid = 0 and (ACS.Name like :searchText\n" +
			"			OR GR.Name like :searchText\n" +
			"			OR GS.Name like :searchText)",
			countQuery = "SELECT RS.ID,RS.StateCode,RS.RegionID,RS.StressID,RS.UpdatedAt,\n" +
					"			RS.CreatedAt,RS.Status, GR.Name as Region,ACS.Name as Stress,GS.Name as State \n" +
					"			FROM regional_stress_missing RS  LEFT JOIN geo_region GR ON (RS.RegionID = GR.RegionID)\n" +
					"			LEFT JOIN geo_state GS ON (RS.StateCode = GS.StateCode)\n" +
					"			LEFT JOIN agri_stress ACS on (ACS.ID = RS.StressID)\n" +
					"			where RS.IsValid = 0 and (ACS.Name like :searchText\n" +
					"			OR GR.Name like :searchText\n" +
					"			OR GS.Name like :searchText)", nativeQuery = true)
	Page<RegionalStressInfDto> getRegionalStressMissingInvalidated(Pageable pageable, String searchText);

}
