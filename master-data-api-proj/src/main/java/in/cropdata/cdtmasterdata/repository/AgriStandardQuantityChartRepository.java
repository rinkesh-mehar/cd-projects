package in.cropdata.cdtmasterdata.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.dto.interfaces.AgriStandardQuantityChartInfDto;
import in.cropdata.cdtmasterdata.model.AgriStandardQuantityChart;

public interface AgriStandardQuantityChartRepository extends JpaRepository<AgriStandardQuantityChart, Integer> {

	@Query(value = "select ASQC.ID,ASQC.StateCode,ASQC.CommodityID,ASQC.VarietyID,ASQC.StandardQuantityPerAcre,ASQC.StandardPositiveVariancePercent,ASQC.StandardPositiveVariancePerAcre,\n"
			+ "ASQC.StandardNegativeVariancePercent,ASQC.StandardNegativeVariancePerAcre,ASQC.Status, GS.Name as State, AC.Name as Commodity, AV.Name as Variety from agri_standard_quantity_chart ASQC\n"
			+ "Left Join geo_state GS on (ASQC.StateCode = GS.StateCode)\n"
			+ "Left Join agri_commodity AC on (ASQC.CommodityID = AC.ID)\n"
			+ "Left Join agri_variety AV on (ASQC.VarietyID = AV.ID)", nativeQuery = true)
	List<AgriStandardQuantityChartInfDto> getStandardQuantityChartList();

	@Query(value = "Select ASQC.ID,ASQC.StateCode,ASQC.CommodityID,ASQC.VarietyID,ASQC.StandardQuantityPerAcre,ASQC.StandardPositiveVariancePercent,ASQC.StandardPositiveVariancePerAcre, \n"
			+ "ASQC.StandardNegativeVariancePercent,ASQC.StandardNegativeVariancePerAcre,ASQC.Status, GS.Name as State, AC.Name as Commodity, AV.Name as Variety, ASQC.IsValid, ASQC.ErrorMessage From agri_standard_quantity_chart ASQC \n"
			+ "Left Join geo_state GS on (ASQC.StateCode = GS.StateCode) \n"
			+ "Left Join agri_commodity AC on (ASQC.CommodityID = AC.ID) \n"
			+ "Left Join agri_variety AV on (ASQC.VarietyID = AV.ID)\n" + "where AV.Name like :searchText\n"
			+ "OR GS.Name like :searchText\n" + "OR AC.Name like :searchText\n"
			+ "OR ASQC.StandardQuantityPerAcre like :searchText\n"
			+ "OR ASQC.StandardPositiveVariancePercent like :searchText\n"
			+ "OR ASQC.StandardPositiveVariancePerAcre like :searchText\n"
			+ "OR ASQC.StandardNegativeVariancePercent like :searchText\n"
			+ "OR ASQC.StandardNegativeVariancePerAcre like :searchText", countQuery = "Select ASQC.ID,ASQC.StateCode,ASQC.CommodityID,ASQC.VarietyID,ASQC.StandardQuantityPerAcre,ASQC.StandardPositiveVariancePercent,ASQC.StandardPositiveVariancePerAcre, \n"
					+ "ASQC.StandardNegativeVariancePercent,ASQC.StandardNegativeVariancePerAcre,ASQC.Status, GS.Name as State, AC.Name as Commodity, AV.Name as Variety, ASQC.IsValid, ASQC.ErrorMessage From agri_standard_quantity_chart ASQC \n"
					+ "Left Join geo_state GS on (ASQC.StateCode = GS.StateCode) \n"
					+ "Left Join agri_commodity AC on (ASQC.CommodityID = AC.ID) \n"
					+ "Left Join agri_variety AV on (ASQC.VarietyID = AV.ID)\n" + "where AV.Name like :searchText\n"
					+ "OR GS.Name like :searchText\n" + "OR AC.Name like :searchText\n"
					+ "OR ASQC.StandardQuantityPerAcre like :searchText\n"
					+ "OR ASQC.StandardPositiveVariancePercent like :searchText\n"
					+ "OR ASQC.StandardPositiveVariancePerAcre like :searchText\n"
					+ "OR ASQC.StandardNegativeVariancePercent like :searchText\n"
					+ "OR ASQC.StandardNegativeVariancePerAcre like :searchText", nativeQuery = true)
	Page<AgriStandardQuantityChartInfDto> getStandardQuantityChartList(Pageable pageable, String searchText);

	@Query(value = "Select ASQC.ID,ASQC.StateCode,ASQC.CommodityID,ASQC.VarietyID,ASQC.StandardQuantityPerAcre,ASQC.StandardPositiveVariancePercent,ASQC.StandardPositiveVariancePerAcre, \n"
			+ "ASQC.StandardNegativeVariancePercent,ASQC.StandardNegativeVariancePerAcre,ASQC.Status, GS.Name as State, AC.Name as Commodity, AV.Name as Variety, ASQC.IsValid, ASQC.ErrorMessage From agri_standard_quantity_chart ASQC \n"
			+ "Left Join geo_state GS on (ASQC.StateCode = GS.StateCode) \n"
			+ "Left Join agri_commodity AC on (ASQC.CommodityID = AC.ID) \n"
			+ "Left Join agri_variety AV on (ASQC.VarietyID = AV.ID)\n" + "where ASQC.IsValid = 0 and (AV.Name like :searchText\n"
			+ "OR GS.Name like :searchText\n" + "OR AC.Name like :searchText\n"
			+ "OR ASQC.StandardQuantityPerAcre like :searchText\n"
			+ "OR ASQC.StandardPositiveVariancePercent like :searchText\n"
			+ "OR ASQC.StandardPositiveVariancePerAcre like :searchText\n"
			+ "OR ASQC.StandardNegativeVariancePercent like :searchText\n"
			+ "OR ASQC.StandardNegativeVariancePerAcre like :searchText)", countQuery = "Select ASQC.ID,ASQC.StateCode,ASQC.CommodityID,ASQC.VarietyID,ASQC.StandardQuantityPerAcre,ASQC.StandardPositiveVariancePercent,ASQC.StandardPositiveVariancePerAcre, \n"
					+ "ASQC.StandardNegativeVariancePercent,ASQC.StandardNegativeVariancePerAcre,ASQC.Status, GS.Name as State, AC.Name as Commodity, AV.Name as Variety, ASQC.IsValid, ASQC.ErrorMessage From agri_standard_quantity_chart ASQC \n"
					+ "Left Join geo_state GS on (ASQC.StateCode = GS.StateCode) \n"
					+ "Left Join agri_commodity AC on (ASQC.CommodityID = AC.ID) \n"
					+ "Left Join agri_variety AV on (ASQC.VarietyID = AV.ID)\n" + "where ASQC.IsValid = 0 and (AV.Name like :searchText\n"
					+ "OR GS.Name like :searchText\n" + "OR AC.Name like :searchText\n"
					+ "OR ASQC.StandardQuantityPerAcre like :searchText\n"
					+ "OR ASQC.StandardPositiveVariancePercent like :searchText\n"
					+ "OR ASQC.StandardPositiveVariancePerAcre like :searchText\n"
					+ "OR ASQC.StandardNegativeVariancePercent like :searchText\n"
					+ "OR ASQC.StandardNegativeVariancePerAcre like :searchText)", nativeQuery = true)
	Page<AgriStandardQuantityChartInfDto> getStandardQuantityChartListInvalidated(Pageable pageable, String searchText);

	@Query(value = "SELECT SQC.ID,SQC.StateCode,SQC.CommodityID,SQC.VarietyID FROM cdt_master_data.agri_standard_quantity_chart SQC"
			+ " where SQC.StateCode=?1 and  SQC.CommodityID = ?2 and SQC.VarietyID = ?3", nativeQuery = true)
	AgriStandardQuantityChartInfDto verifyData(int stateCode, int commodityID, int VarietyID);

	
	
	
	@Query(value = "Select ASQC.ID,ASQC.StateCode,ASQC.CommodityID,ASQC.VarietyID,ASQC.StandardQuantityPerAcre,ASQC.StandardPositiveVariancePercent,ASQC.StandardPositiveVariancePerAcre, \n"
			+ "ASQC.StandardNegativeVariancePercent,ASQC.StandardNegativeVariancePerAcre,ASQC.Status, GS.Name as State, AC.Name as Commodity, AV.Name as Variety, ASQC.IsValid From agri_standard_quantity_chart_missing ASQC \n"
			+ "Left Join geo_state GS on (ASQC.StateCode = GS.StateCode) \n"
			+ "Left Join agri_commodity AC on (ASQC.CommodityID = AC.ID) \n"
			+ "Left Join agri_variety AV on (ASQC.VarietyID = AV.ID)\n" + "where ASQC.IsValid = 0 and (AV.Name like :searchText\n"
			+ "OR GS.Name like :searchText\n" + "OR AC.Name like :searchText\n"
			+ "OR ASQC.StandardQuantityPerAcre like :searchText\n"
			+ "OR ASQC.StandardPositiveVariancePercent like :searchText\n"
			+ "OR ASQC.StandardPositiveVariancePerAcre like :searchText\n"
			+ "OR ASQC.StandardNegativeVariancePercent like :searchText\n"
			+ "OR ASQC.StandardNegativeVariancePerAcre like :searchText)", countQuery = "Select ASQC.ID,ASQC.StateCode,ASQC.CommodityID,ASQC.VarietyID,ASQC.StandardQuantityPerAcre,ASQC.StandardPositiveVariancePercent,ASQC.StandardPositiveVariancePerAcre, \n"
					+ "ASQC.StandardNegativeVariancePercent,ASQC.StandardNegativeVariancePerAcre,ASQC.Status, GS.Name as State, AC.Name as Commodity, AV.Name as Variety, ASQC.IsValid From agri_standard_quantity_chart_missing ASQC \n"
					+ "Left Join geo_state GS on (ASQC.StateCode = GS.StateCode) \n"
					+ "Left Join agri_commodity AC on (ASQC.CommodityID = AC.ID) \n"
					+ "Left Join agri_variety AV on (ASQC.VarietyID = AV.ID)\n" + "where ASQC.IsValid = 0 and (AV.Name like :searchText\n"
					+ "OR GS.Name like :searchText\n" + "OR AC.Name like :searchText\n"
					+ "OR ASQC.StandardQuantityPerAcre like :searchText\n"
					+ "OR ASQC.StandardPositiveVariancePercent like :searchText\n"
					+ "OR ASQC.StandardPositiveVariancePerAcre like :searchText\n"
					+ "OR ASQC.StandardNegativeVariancePercent like :searchText\n"
					+ "OR ASQC.StandardNegativeVariancePerAcre like :searchText)", nativeQuery = true)
	Page<AgriStandardQuantityChartInfDto> getStandardQuantityChartMissingListInvalidated(Pageable sortedByIdDesc,String searchText);
	
	
	@Query(value = "Select ASQC.ID,ASQC.StateCode,ASQC.CommodityID,ASQC.VarietyID,ASQC.StandardQuantityPerAcre,ASQC.StandardPositiveVariancePercent,ASQC.StandardPositiveVariancePerAcre, \n"
			+ "ASQC.StandardNegativeVariancePercent,ASQC.StandardNegativeVariancePerAcre,ASQC.Status, GS.Name as State, AC.Name as Commodity, AV.Name as Variety, ASQC.IsValid From agri_standard_quantity_chart_missing ASQC \n"
			+ "Left Join geo_state GS on (ASQC.StateCode = GS.StateCode) \n"
			+ "Left Join agri_commodity AC on (ASQC.CommodityID = AC.ID) \n"
			+ "Left Join agri_variety AV on (ASQC.VarietyID = AV.ID)\n" + "where AV.Name like :searchText\n"
			+ "OR GS.Name like :searchText\n" + "OR AC.Name like :searchText\n"
			+ "OR ASQC.StandardQuantityPerAcre like :searchText\n"
			+ "OR ASQC.StandardPositiveVariancePercent like :searchText\n"
			+ "OR ASQC.StandardPositiveVariancePerAcre like :searchText\n"
			+ "OR ASQC.StandardNegativeVariancePercent like :searchText\n"
			+ "OR ASQC.StandardNegativeVariancePerAcre like :searchText", countQuery = "Select ASQC.ID,ASQC.StateCode,ASQC.CommodityID,ASQC.VarietyID,ASQC.StandardQuantityPerAcre,ASQC.StandardPositiveVariancePercent,ASQC.StandardPositiveVariancePerAcre, \n"
					+ "ASQC.StandardNegativeVariancePercent,ASQC.StandardNegativeVariancePerAcre,ASQC.Status, GS.Name as State, AC.Name as Commodity, AV.Name as Variety, ASQC.IsValid From agri_standard_quantity_chart_missing ASQC \n"
					+ "Left Join geo_state GS on (ASQC.StateCode = GS.StateCode) \n"
					+ "Left Join agri_commodity AC on (ASQC.CommodityID = AC.ID) \n"
					+ "Left Join agri_variety AV on (ASQC.VarietyID = AV.ID)\n" + "where AV.Name like :searchText\n"
					+ "OR GS.Name like :searchText\n" + "OR AC.Name like :searchText\n"
					+ "OR ASQC.StandardQuantityPerAcre like :searchText\n"
					+ "OR ASQC.StandardPositiveVariancePercent like :searchText\n"
					+ "OR ASQC.StandardPositiveVariancePerAcre like :searchText\n"
					+ "OR ASQC.StandardNegativeVariancePercent like :searchText\n"
					+ "OR ASQC.StandardNegativeVariancePerAcre like :searchText", nativeQuery = true) 
	Page<AgriStandardQuantityChartInfDto> getStandardQuantityChartMissingList(Pageable sortedByIdDesc,String searchText);
}
