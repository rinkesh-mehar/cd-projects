package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.dto.interfaces.AgriHealthInfDto;
import in.cropdata.cdtmasterdata.model.AgriHealth;

public interface AgriHealthRepository extends JpaRepository<AgriHealth, Integer> {
	
	@Query(value = "Select AH.ID,AH.CommodityID,AH.PhenophaseID,AH.HealthParameterID,AH.Specification,AH.Status, AC.Name as Commodity, AP.Name as Phenophase,\n" + 
			"			AHP.Name as HealthParameter, AH.IsValid, AH.ErrorMessage From agri_health AH \n" +
			"			Left Join agri_commodity AC on(AH.CommodityID = AC.ID)\n" + 
			"			Left Join agri_phenophase AP on(AH.PhenophaseID = AP.ID)\n" + 
			"			Left Join agri_health_parameter AHP on(AH.HealthParameterID = AHP.ID)\n" + 
			"			where AHP.Name like :searchText\n" + 
			"			OR AC.Name like :searchText\n" + 
			"			OR AP.Name like :searchText\n" + 
			"			OR AH.Specification like :searchText",
			countQuery = "Select AH.ID,AH.CommodityID,AH.PhenophaseID,AH.HealthParameterID,AH.Specification,AH.Status, AC.Name as Commodity, AP.Name as Phenophase,\n" + 
					"			AHP.Name as HealthParameter, AH.IsValid, AH.ErrorMessage From agri_health AH \n" +
					"			Left Join agri_commodity AC on(AH.CommodityID = AC.ID)\n" + 
					"			Left Join agri_phenophase AP on(AH.PhenophaseID = AP.ID)\n" + 
					"			Left Join agri_health_parameter AHP on(AH.HealthParameterID = AHP.ID)\n" + 
					"			where AHP.Name like :searchText\n" + 
					"			OR AC.Name like :searchText\n" + 
					"			OR AP.Name like :searchText\n" + 
					"			OR AH.Specification like :searchText", nativeQuery = true)
	Page<AgriHealthInfDto> getAgriHealth(Pageable pageable, String searchText);

	@Query(value = "Select AH.ID,AH.CommodityID,AH.PhenophaseID,AH.HealthParameterID,AH.Specification,AH.Status, AC.Name as Commodity, AP.Name as Phenophase,\n" +
			"			AHP.Name as HealthParameter, AH.IsValid, AH.ErrorMessage From agri_health AH \n" +
			"			Left Join agri_commodity AC on(AH.CommodityID = AC.ID)\n" +
			"			Left Join agri_phenophase AP on(AH.PhenophaseID = AP.ID)\n" +
			"			Left Join agri_health_parameter AHP on(AH.HealthParameterID = AHP.ID)\n" +
			"			where AH.IsValid = 0 and (AHP.Name like :searchText\n" +
			"			OR AC.Name like :searchText\n" +
			"			OR AP.Name like :searchText\n" +
			"			OR AH.Specification like :searchText)",
			countQuery = "Select AH.ID,AH.CommodityID,AH.PhenophaseID,AH.HealthParameterID,AH.Specification,AH.Status, AC.Name as Commodity, AP.Name as Phenophase,\n" +
					"			AHP.Name as HealthParameter, AH.IsValid, AH.ErrorMessage From agri_health AH \n" +
					"			Left Join agri_commodity AC on(AH.CommodityID = AC.ID)\n" +
					"			Left Join agri_phenophase AP on(AH.PhenophaseID = AP.ID)\n" +
					"			Left Join agri_health_parameter AHP on(AH.HealthParameterID = AHP.ID)\n" +
					"			where AH.IsValid = 0 and (AHP.Name like :searchText\n" +
					"			OR AC.Name like :searchText\n" +
					"			OR AP.Name like :searchText\n" +
					"			OR AH.Specification like :searchText)", nativeQuery = true)
	Page<AgriHealthInfDto> getAgriHealthInvalidated(Pageable pageable, String searchText);
	
	
	
	
	@Query(value = "Select AH.ID,AH.CommodityID,AH.PhenophaseID,AH.HealthParameterID,AH.Specification,AH.Status, AC.Name as Commodity, AP.Name as Phenophase,\n" +
			"			AHP.Name as HealthParameter, AH.IsValid From agri_health_missing AH \n" +
			"			Left Join agri_commodity AC on(AH.CommodityID = AC.ID)\n" +
			"			Left Join agri_phenophase AP on(AH.PhenophaseID = AP.ID)\n" +
			"			Left Join agri_health_parameter AHP on(AH.HealthParameterID = AHP.ID)\n" +
			"			where AH.IsValid = 0 and (AHP.Name like :searchText\n" +
			"			OR AC.Name like :searchText\n" +
			"			OR AP.Name like :searchText\n" +
			"			OR AH.Specification like :searchText)",
			countQuery = "Select AH.ID,AH.CommodityID,AH.PhenophaseID,AH.HealthParameterID,AH.Specification,AH.Status, AC.Name as Commodity, AP.Name as Phenophase,\n" +
					"			AHP.Name as HealthParameter, AH.IsValid From agri_health_missing AH \n" +
					"			Left Join agri_commodity AC on(AH.CommodityID = AC.ID)\n" +
					"			Left Join agri_phenophase AP on(AH.PhenophaseID = AP.ID)\n" +
					"			Left Join agri_health_parameter AHP on(AH.HealthParameterID = AHP.ID)\n" +
					"			where AH.IsValid = 0 and (AHP.Name like :searchText\n" +
					"			OR AC.Name like :searchText\n" +
					"			OR AP.Name like :searchText\n" +
					"			OR AH.Specification like :searchText)", nativeQuery = true)
	Page<AgriHealthInfDto> getAgriHealthMissingInvalidated(Pageable sortedByIdDesc, String searchText);

	
	@Query(value = "Select AH.ID,AH.CommodityID,AH.PhenophaseID,AH.HealthParameterID,AH.Specification,AH.Status, AC.Name as Commodity, AP.Name as Phenophase,\n" + 
			"			AHP.Name as HealthParameter, AH.IsValid From agri_health_missing AH \n" +
			"			Left Join agri_commodity AC on(AH.CommodityID = AC.ID)\n" + 
			"			Left Join agri_phenophase AP on(AH.PhenophaseID = AP.ID)\n" + 
			"			Left Join agri_health_parameter AHP on(AH.HealthParameterID = AHP.ID)\n" + 
			"			where AHP.Name like :searchText\n" + 
			"			OR AC.Name like :searchText\n" + 
			"			OR AP.Name like :searchText\n" + 
			"			OR AH.Specification like :searchText",
			countQuery = "Select AH.ID,AH.CommodityID,AH.PhenophaseID,AH.HealthParameterID,AH.Specification,AH.Status, AC.Name as Commodity, AP.Name as Phenophase,\n" + 
					"			AHP.Name as HealthParameter, AH.IsValid From agri_health_missing AH \n" +
					"			Left Join agri_commodity AC on(AH.CommodityID = AC.ID)\n" + 
					"			Left Join agri_phenophase AP on(AH.PhenophaseID = AP.ID)\n" + 
					"			Left Join agri_health_parameter AHP on(AH.HealthParameterID = AHP.ID)\n" + 
					"			where AHP.Name like :searchText\n" + 
					"			OR AC.Name like :searchText\n" + 
					"			OR AP.Name like :searchText\n" + 
					"			OR AH.Specification like :searchText", nativeQuery = true)
	Page<AgriHealthInfDto> getAgriHealthMissing(Pageable sortedByIdDesc, String searchText);

}
