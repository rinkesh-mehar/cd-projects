package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.dto.interfaces.AgriQualityChartInfDto;
import in.cropdata.cdtmasterdata.model.AgriQualityChart;

public interface AgriQualityChartRepository extends JpaRepository<AgriQualityChart, Integer> {
	
	@Query(value = "SELECT AQC.ID,AQC.CommodityID,AQC.PhenophaseID,AQC.HealthParameterID,AQC.GradeI,AQC.GradeII,AQC.GradeIII,AQC.MingradeI,\n" +
			"	AQC.MaxgradeI,AQC.MingradeII,AQC.MaxgradeII,AQC.MingradeIII,AQC.MaxgradeIII,AQC.Status,AC.Name as Commodity,AP.Name as Phenophase,\n" +
			"	AHP.Name as HealthParameter FROM agri_quality_chart AQC \n" + 
			"	LEFT JOIN agri_commodity AC ON(AC.ID = AQC.CommodityID)\n" + 
			"	LEFT JOIN agri_phenophase AP ON(AP.ID = AQC.PhenophaseID)\n" + 
			"	LEFT JOIN agri_health_parameter AHP ON(AHP.ID = AQC.HealthParameterID)\n" + 
			"	 where AC.Name like :searchText\n" + 
			"	 OR AP.Name like :searchText\n" + 
			"	 OR AHP.Name like :searchText\n" + 
			"	 OR AQC.GradeI like :searchText\n" + 
			"	 OR AQC.GradeII like :searchText\n" + 
			"	 OR AQC.GradeIII like :searchText\n" + 
			"	 OR AQC.MingradeI like :searchText\n" +
			"	 OR AQC.MaxgradeI like :searchText\n" +
			"	 OR AQC.MingradeII like :searchText\n" +
			"	 OR AQC.MaxgradeII like :searchText\n" +
			"	 OR AQC.MingradeIII like :searchText\n" +
			"	 OR AQC.MaxgradeIII like :searchText",
			countQuery = "SELECT AQC.ID,AQC.CommodityID,AQC.PhenophaseID,AQC.HealthParameterID,AQC.GradeI,AQC.GradeII,AQC.GradeIII,AQC.MingradeI,\n" +
					"	AQC.MaxgradeI,AQC.MingradeII,AQC.MaxgradeII,AQC.MingradeIII,AQC.MaxgradeIII,AQC.Status,AC.Name as Commodity,AP.Name as Phenophase,\n" +
					"	AHP.Name as HealthParameter FROM agri_quality_chart AQC \n" + 
					"	LEFT JOIN agri_commodity AC ON(AC.ID = AQC.CommodityID)\n" + 
					"	LEFT JOIN agri_phenophase AP ON(AP.ID = AQC.PhenophaseID)\n" + 
					"	LEFT JOIN agri_health_parameter AHP ON(AHP.ID = AQC.HealthParameterID)\n" + 
					"	 where AC.Name like :searchText\n" + 
					"	 OR AP.Name like :searchText\n" + 
					"	 OR AHP.Name like :searchText\n" + 
					"	 OR AQC.GradeI like :searchText\n" + 
					"	 OR AQC.GradeII like :searchText\n" + 
					"	 OR AQC.GradeIII like :searchText\n" + 
					"	 OR AQC.MingradeI like :searchText\n" +
					"	 OR AQC.MaxgradeI like :searchText\n" +
					"	 OR AQC.MingradeII like :searchText\n" +
					"	 OR AQC.MaxgradeII like :searchText\n" +
					"	 OR AQC.MingradeIII like :searchText\n" +
					"	 OR AQC.MaxgradeIII like :searchText"
			, nativeQuery = true)
	Page<AgriQualityChartInfDto> getAgriQualityChartList(Pageable sortedByIdDesc, String searchText);
	
	@Query(value = "SELECT AQC.ID,AQC.CommodityID,AQC.PhenophaseID,AQC.HealthParameterID,AQC.GradeI,AQC.GradeII,AQC.GradeIII,AQC.MingradeI,\n" +
			"	AQC.MaxgradeI,AQC.MingradeII,AQC.MaxgradeII,AQC.MingradeIII,AQC.MaxgradeIII,AQC.Status,AC.Name as Commodity,AP.Name as Phenophase,\n" +
			"	AHP.Name as HealthParameter FROM agri_quality_chart_missing AQC \n" + 
			"	LEFT JOIN agri_commodity AC ON(AC.ID = AQC.CommodityID)\n" + 
			"	LEFT JOIN agri_phenophase AP ON(AP.ID = AQC.PhenophaseID)\n" + 
			"	LEFT JOIN agri_health_parameter AHP ON(AHP.ID = AQC.HealthParameterID)\n" + 
			"	 where AC.Name like :searchText\n" + 
			"	 OR AP.Name like :searchText\n" + 
			"	 OR AHP.Name like :searchText\n" + 
			"	 OR AQC.GradeI like :searchText\n" + 
			"	 OR AQC.GradeII like :searchText\n" + 
			"	 OR AQC.GradeIII like :searchText\n" + 
			"	 OR AQC.MingradeI like :searchText\n" +
			"	 OR AQC.MaxgradeI like :searchText\n" +
			"	 OR AQC.MingradeII like :searchText\n" +
			"	 OR AQC.MaxgradeII like :searchText\n" +
			"	 OR AQC.MingradeIII like :searchText\n" +
			"	 OR AQC.MaxgradeIII like :searchText",
			countQuery = "SELECT AQC.ID,AQC.CommodityID,AQC.PhenophaseID,AQC.HealthParameterID,AQC.GradeI,AQC.GradeII,AQC.GradeIII,AQC.MingradeI,\n" +
					"	AQC.MaxgradeI,AQC.MingradeII,AQC.MaxgradeII,AQC.MingradeIII,AQC.MaxgradeIII,AQC.Status,AC.Name as Commodity,AP.Name as Phenophase,\n" +
					"	AHP.Name as HealthParameter FROM agri_quality_chart_missing AQC \n" + 
					"	LEFT JOIN agri_commodity AC ON(AC.ID = AQC.CommodityID)\n" + 
					"	LEFT JOIN agri_phenophase AP ON(AP.ID = AQC.PhenophaseID)\n" + 
					"	LEFT JOIN agri_health_parameter AHP ON(AHP.ID = AQC.HealthParameterID)\n" + 
					"	 where AC.Name like :searchText\n" + 
					"	 OR AP.Name like :searchText\n" + 
					"	 OR AHP.Name like :searchText\n" + 
					"	 OR AQC.GradeI like :searchText\n" + 
					"	 OR AQC.GradeII like :searchText\n" + 
					"	 OR AQC.GradeIII like :searchText\n" + 
					"	 OR AQC.MingradeI like :searchText\n" +
					"	 OR AQC.MaxgradeI like :searchText\n" +
					"	 OR AQC.MingradeII like :searchText\n" +
					"	 OR AQC.MaxgradeII like :searchText\n" +
					"	 OR AQC.MingradeIII like :searchText\n" +
					"	 OR AQC.MaxgradeIII like :searchText"
			, nativeQuery = true)
	Page<AgriQualityChartInfDto> getAgriQualityChartListMissing(Pageable sortedByIdDesc, String searchText);

	
	
}
