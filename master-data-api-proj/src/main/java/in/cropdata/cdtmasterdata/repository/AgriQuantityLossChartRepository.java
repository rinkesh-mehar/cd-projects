package in.cropdata.cdtmasterdata.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.dto.interfaces.AgriQuantityLossChartInfDto;
import in.cropdata.cdtmasterdata.model.AgriQuantityLossChart;

public interface AgriQuantityLossChartRepository extends JpaRepository<AgriQuantityLossChart, Integer> {

	@Query(value = "SELECT AGLC.ID as QuantityLossChartId,AGLC.Status,\n" +
			"AGLC.maxBandValue as maxBandValue, AGLC.MinBandValue as MinBandValue,\n" +
			"					ACS.Name as Stress, AC.Name as Commodity, AP.Name as Phenophase \n" +
			"					FROM agri_quantity_loss_chart AGLC \n" +
			"					LEFT JOIN agri_commodity_stress ACS on (ACS.ID = AGLC.StressID)\n" +
			"					LEFT JOIN agri_commodity AC ON (AGLC.CommodityID = AC.ID)\n" +
			"					LEFT JOIN agri_phenophase AP ON (AGLC.PhenophaseID = AP.ID)\n"
			, nativeQuery = true)
	List<AgriQuantityLossChartInfDto> getQuantityLossChartData();

	@Query(value = "SELECT AGLC.ID as Id,AGLC.Status,AGLC.MinQuantityCorrectionPercent as MinQuantityCorrectionPercent,AGLC.MaxQuantityCorrectionPercent as  MaxQuantityCorrectionPercent,\n" + 
			"								AGLC.maxBandValue as maxBandValue,\n" + 
			"			                    AGLC.MinBandValue as MinBandValue,\n" + 
			"			                    agri_stress.Name as Stress, AC.Name as Commodity, AP.Name as Phenophase, AGLC.IsValid, AGLC.ErrorMessage\n" + 
			"			                    FROM agri_quantity_loss_chart AGLC\n" + 
			"			                    LEFT JOIN agri_commodity_stress ACS on (ACS.ID = AGLC.StressID)\n" + 
			"                                Left Join agri_stress agri_stress on(ACS.StressID = agri_stress.id)\n" + 
			"			                    LEFT JOIN agri_commodity AC ON (AGLC.CommodityID = AC.ID)\n" + 
			"			                    LEFT JOIN agri_phenophase AP ON (AGLC.PhenophaseID = AP.ID)\n" + 
			"			                    where AC.Name like :searchText\n" + 
			"			                    OR AP.Name like :searchText\n" + 
			"			                    OR agri_stress.Name like :searchText\n" + 
			"			                    OR AGLC.MinQuantityCorrectionPercent like :searchText\n" + 
			"			                    OR AGLC.MaxQuantityCorrectionPercent like :searchText\n" + 
			"			                    OR AGLC.maxBandValue like :searchText\n" + 
			"			                    OR AGLC.MinBandValue like :searchText",
			countQuery = "SELECT AGLC.ID as Id,AGLC.Status,AGLC.MinQuantityCorrectionPercent as MinQuantityCorrectionPercent,AGLC.MaxQuantityCorrectionPercent as  MaxQuantityCorrectionPercent,\n" + 
					"								AGLC.maxBandValue as maxBandValue,\n" + 
					"			                    AGLC.MinBandValue as MinBandValue,\n" + 
					"			                    agri_stress.Name as Stress, AC.Name as Commodity, AP.Name as Phenophase, AGLC.IsValid, AGLC.ErrorMessage\n" + 
					"			                    FROM agri_quantity_loss_chart AGLC\n" + 
					"			                    LEFT JOIN agri_commodity_stress ACS on (ACS.ID = AGLC.StressID)\n" + 
					"                                Left Join agri_stress agri_stress on(ACS.StressID = agri_stress.id)\n" + 
					"			                    LEFT JOIN agri_commodity AC ON (AGLC.CommodityID = AC.ID)\n" + 
					"			                    LEFT JOIN agri_phenophase AP ON (AGLC.PhenophaseID = AP.ID)\n" + 
					"			                    where AC.Name like :searchText\n" + 
					"			                    OR AP.Name like :searchText\n" + 
					"			                    OR agri_stress.Name like :searchText\n" + 
					"			                    OR AGLC.MinQuantityCorrectionPercent like :searchText\n" + 
					"			                    OR AGLC.MaxQuantityCorrectionPercent like :searchText\n" + 
					"			                    OR AGLC.maxBandValue like :searchText\n" + 
					"			                    OR AGLC.MinBandValue like :searchText", nativeQuery = true)
	Page<AgriQuantityLossChartInfDto> getAgriQuantityChartLoss(Pageable sortedByIdDesc, String searchText);
	
	
	@Query(value = "SELECT AGLC.ID as Id,AGLC.Status,AGLC.MinQuantityCorrectionPercent as MinQuantityCorrectionPercent,AGLC.\n" +
			"					 MaxQuantityCorrectionPercent as  MaxQuantityCorrectionPercent,AGLC.maxBandValue as maxBandValue,\n" +
			"                    AGLC.MinBandValue as MinBandValue,\n" +
			"                    ACS.Name as Stress, AC.Name as Commodity, AP.Name as Phenophase, AGLC.IsValid\n" +
			"                    FROM agri_quantity_loss_chart_missing AGLC\n" +
			"                    LEFT JOIN agri_commodity_stress ACS on (ACS.ID = AGLC.StressID)\n" +
			"                    LEFT JOIN agri_commodity AC ON (AGLC.CommodityID = AC.ID)\n" +
			"                    LEFT JOIN agri_phenophase AP ON (AGLC.PhenophaseID = AP.ID)\n" +
			"                    where AC.Name like :searchText\n" +
			"                    OR AP.Name like :searchText\n" +
			"                    OR ACS.Name like :searchText\n" +
			"                    OR AGLC.MinQuantityCorrectionPercent like :searchText\n" +
			"                    OR AGLC.MaxQuantityCorrectionPercent like :searchText\n" +
			"                    OR AGLC.maxBandValue like :searchText\n" +
			"                    OR AGLC.MinBandValue like :searchText",
			countQuery = "SELECT AGLC.ID as Id,AGLC.Status,AGLC.MinQuantityCorrectionPercent as MinQuantityCorrectionPercent,AGLC.\n" +
					"		MaxQuantityCorrectionPercent as  MaxQuantityCorrectionPercent,AGLC.maxBandValue as maxBandValue,\n" +
					"                    AGLC.MinBandValue as MinBandValue,\n" +
					"                    ACS.Name as Stress, AC.Name as Commodity, AP.Name as Phenophase, AGLC.IsValid \n" +
					"                    FROM agri_quantity_loss_chart_missing AGLC\n" +
					"                    LEFT JOIN agri_commodity_stress ACS on (ACS.ID = AGLC.StressID)\n" +
					"                    LEFT JOIN agri_commodity AC ON (AGLC.CommodityID = AC.ID)\n" +
					"                    LEFT JOIN agri_phenophase AP ON (AGLC.PhenophaseID = AP.ID)\n" +
					"                    where AC.Name like :searchText\n" +
					"                    OR AP.Name like :searchText\n" +
					"                    OR ACS.Name like :searchText\n" +
					"                    OR AGLC.MinQuantityCorrectionPercent like :searchText\n" +
					"                    OR AGLC.MaxQuantityCorrectionPercent like :searchText\n" +
					"                    OR AGLC.maxBandValue like :searchText\n" +
					"                    OR AGLC.MinBandValue like :searchText", nativeQuery = true)
	Page<AgriQuantityLossChartInfDto> getAgriQuantityChartMissingLoss(Pageable sortedByIdDesc, String searchText);


	@Query(value = "SELECT \n" + 
			"    AGLC.ID AS Id,\n" + 
			"    AGLC.Status,\n" + 
			"    AGLC.MinQuantityCorrectionPercent AS MinQuantityCorrectionPercent,\n" + 
			"    AGLC.MaxQuantityCorrectionPercent AS MaxQuantityCorrectionPercent,\n" + 
			"    AGLC.maxBandValue AS maxBandValue,\n" + 
			"    AGLC.MinBandValue AS MinBandValue,\n" + 
			"    agri_stress.Name AS Stress,\n" + 
			"    AC.Name AS Commodity,\n" + 
			"    AP.Name AS Phenophase,\n" + 
			"    AGLC.IsValid,\n" + 
			"    AGLC.ErrorMessage\n" + 
			"FROM\n" + 
			"    agri_quantity_loss_chart AGLC\n" + 
			"        LEFT JOIN\n" + 
			"    agri_commodity_stress ACS ON (ACS.ID = AGLC.StressID)\n" + 
			"        LEFT JOIN\n" + 
			"    agri_stress agri_stress ON (ACS.StressID = agri_stress.id)\n" + 
			"        LEFT JOIN\n" + 
			"    agri_commodity AC ON (AGLC.CommodityID = AC.ID)\n" + 
			"        LEFT JOIN\n" + 
			"    agri_phenophase AP ON (AGLC.PhenophaseID = AP.ID)\n" + 
			"WHERE\n" + 
			"    AGLC.IsValid = 0\n" + 
			"        AND (AC.Name LIKE :searchText OR AP.Name LIKE :searchText\n" + 
			"        OR agri_stress.Name LIKE :searchText\n" + 
			"        OR AGLC.MinQuantityCorrectionPercent LIKE :searchText\n" + 
			"        OR AGLC.MaxQuantityCorrectionPercent LIKE :searchText\n" + 
			"        OR AGLC.maxBandValue LIKE :searchText\n" + 
			"        OR AGLC.MinBandValue LIKE :searchText)",
			countQuery = "SELECT \n" + 
					"    AGLC.ID AS Id,\n" + 
					"    AGLC.Status,\n" + 
					"    AGLC.MinQuantityCorrectionPercent AS MinQuantityCorrectionPercent,\n" + 
					"    AGLC.MaxQuantityCorrectionPercent AS MaxQuantityCorrectionPercent,\n" + 
					"    AGLC.maxBandValue AS maxBandValue,\n" + 
					"    AGLC.MinBandValue AS MinBandValue,\n" + 
					"    agri_stress.Name AS Stress,\n" + 
					"    AC.Name AS Commodity,\n" + 
					"    AP.Name AS Phenophase,\n" + 
					"    AGLC.IsValid,\n" + 
					"    AGLC.ErrorMessage\n" + 
					"FROM\n" + 
					"    agri_quantity_loss_chart AGLC\n" + 
					"        LEFT JOIN\n" + 
					"    agri_commodity_stress ACS ON (ACS.ID = AGLC.StressID)\n" + 
					"        LEFT JOIN\n" + 
					"    agri_stress agri_stress ON (ACS.StressID = agri_stress.id)\n" + 
					"        LEFT JOIN\n" + 
					"    agri_commodity AC ON (AGLC.CommodityID = AC.ID)\n" + 
					"        LEFT JOIN\n" + 
					"    agri_phenophase AP ON (AGLC.PhenophaseID = AP.ID)\n" + 
					"WHERE\n" + 
					"    AGLC.IsValid = 0\n" + 
					"        AND (AC.Name LIKE :searchText OR AP.Name LIKE :searchText\n" + 
					"        OR agri_stress.Name LIKE :searchText\n" + 
					"        OR AGLC.MinQuantityCorrectionPercent LIKE :searchText\n" + 
					"        OR AGLC.MaxQuantityCorrectionPercent LIKE :searchText\n" + 
					"        OR AGLC.maxBandValue LIKE :searchText\n" + 
					"        OR AGLC.MinBandValue LIKE :searchText)", nativeQuery = true)
	Page<AgriQuantityLossChartInfDto> getAgriQuantityChartLossInvalidated(Pageable sortedByIdDesc, String searchText);

	
	

	
	@Query(value = "SELECT AGLC.ID as Id,AGLC.Status,AGLC.MinQuantityCorrectionPercent as MinQuantityCorrectionPercent,AGLC.\n" +
			"					 MaxQuantityCorrectionPercent as  MaxQuantityCorrectionPercent,AGLC.maxBandValue as maxBandValue,\n" +
			"                    AGLC.MinBandValue as MinBandValue,\n" +
			"                    ACS.Name as Stress, AC.Name as Commodity, AP.Name as Phenophase, AGLC.IsValid \n" +
			"                    FROM agri_quantity_loss_chart_missing AGLC\n" +
			"                    LEFT JOIN agri_commodity_stress ACS on (ACS.ID = AGLC.StressID)\n" +
			"                    LEFT JOIN agri_commodity AC ON (AGLC.CommodityID = AC.ID)\n" +
			"                    LEFT JOIN agri_phenophase AP ON (AGLC.PhenophaseID = AP.ID)\n" +
			"                    where AGLC.IsValid = 0 and (AC.Name like :searchText\n" +
			"                    OR AP.Name like :searchText\n" +
			"                    OR ACS.Name like :searchText\n" +
			"                    OR AGLC.MinQuantityCorrectionPercent like :searchText\n" +
			"                    OR AGLC.MaxQuantityCorrectionPercent like :searchText\n" +
			"                    OR AGLC.maxBandValue like :searchText\n" +
			"                    OR AGLC.MinBandValue like :searchText)",
			countQuery = "SELECT AGLC.ID as Id,AGLC.Status,AGLC.MinQuantityCorrectionPercent as MinQuantityCorrectionPercent,AGLC.\n" +
					"		MaxQuantityCorrectionPercent as  MaxQuantityCorrectionPercent,AGLC.maxBandValue as maxBandValue,\n" +
					"                    AGLC.MinBandValue as MinBandValue,\n" +
					"                    ACS.Name as Stress, AC.Name as Commodity, AP.Name as Phenophase, AGLC.IsValid, AGLC.ErrorMessage \n" +
					"                    FROM agri_quantity_loss_chart_missing AGLC\n" +
					"                    LEFT JOIN agri_commodity_stress ACS on (ACS.ID = AGLC.StressID)\n" +
					"                    LEFT JOIN agri_commodity AC ON (AGLC.CommodityID = AC.ID)\n" +
					"                    LEFT JOIN agri_phenophase AP ON (AGLC.PhenophaseID = AP.ID)\n" +
					"                    where AGLC.IsValid = 0 and (AC.Name like :searchText\n" +
					"                    OR AP.Name like :searchText\n" +
					"                    OR ACS.Name like :searchText\n" +
					"                    OR AGLC.MinQuantityCorrectionPercent like :searchText\n" +
					"                    OR AGLC.MaxQuantityCorrectionPercent like :searchText\n" +
					"                    OR AGLC.maxBandValue like :searchText\n" +
					"                    OR AGLC.MinBandValue like :searchText)", nativeQuery = true)
	Page<AgriQuantityLossChartInfDto> getAgriQuantityChartLossMissingInvalidated(Pageable sortedByIdDesc,String searchText);

	

}
