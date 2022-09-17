package in.cropdata.cdtmasterdata.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.model.AgriPlantHealthIndex;
import in.cropdata.cdtmasterdata.model.vo.AgriPlantHealthIndexForPhenophaseVo;
import in.cropdata.cdtmasterdata.model.vo.AgriPlantHealthIndexForVarietyVo;
import in.cropdata.cdtmasterdata.model.vo.AgriPlantHealthIndexVo;

public interface AgriPlantHealthIndexRepository extends JpaRepository<AgriPlantHealthIndex, Integer> {
	
	@Query(value = "SELECT ID as VarietyId, Name as VarietyName FROM cdt_master_data.agri_variety where CommodityID = ?1 ORDER BY Name", nativeQuery = true)
	List<AgriPlantHealthIndexForVarietyVo> getVarietyListByCommodity(int commodityId);
	
	@Query(value = "SELECT DISTINCT\n" + 
			"    AP.ID AS PhenophaseId, AP.Name AS Phenophase\n" + 
			"FROM\n" + 
			"    agri_phenophase_duration APD\n" + 
			"        INNER JOIN\n" + 
			"    agri_phenophase AP ON (APD.PhenophaseID = AP.ID)\n" + 
			"WHERE\n" + 
			"    CommodityID = ?1 AND VarietyID = ?2\n" + 
			"ORDER BY AP.Name", nativeQuery = true)
	List<AgriPlantHealthIndexForPhenophaseVo> getPhenophaseListByCommodityAndVariety(int commodityId,int varietyId);
	
	@Query(value = "SELECT APHI.ID,\n" + 
			"    APHI.Name,\n" + 
			"    GS.Name AS State,\n" + 
			"    AC.Name AS Commodity,\n" + 
			"    AV.Name As Variety,\n" + 
			"    AP.Name as Phenophase,\n" + 
			"    APHI.NormalValue,\n" + 
			"    APHI.IdealValue,\n" + 
			"    APHI.Status\n" + 
			"FROM\n" + 
			"    cdt_master_data.agri_plant_health_index APHI\n" + 
			"        Inner JOIN\n" + 
			"    geo_state GS ON (APHI.StateCode = GS.StateCode)\n" + 
			"        Inner JOIN\n" + 
			"    agri_commodity AC ON (APHI.CommodityID = AC.ID)\n" + 
			"    Inner JOIN\n" + 
			"    agri_variety AV ON (APHI.VarietyID = AV.ID)\n" + 
			"     Inner JOIN\n" + 
			"    agri_phenophase AP ON (APHI.PhenophaseID = AP.ID)\n" + 
			"    where APHI.ID like :searchText\n" + 
			"    OR APHI.Name like :searchText\n" + 
			"    OR GS.Name like :searchText\n" + 
			"    OR AC.Name like :searchText\n" + 
			"    OR AV.Name like :searchText\n" + 
			"    OR AP.Name like :searchText\n" + 
			"    OR APHI.NormalValue like :searchText\n" + 
			"    OR APHI.IdealValue like :searchText\n" + 
			"    OR APHI.Status like :searchText", countQuery = "SELECT APHI.ID,\n" + 
					"    APHI.Name,\n" + 
					"    GS.Name AS State,\n" + 
					"    AC.Name AS Commodity,\n" + 
					"    AV.Name As Variety,\n" + 
					"    AP.Name as Phenophase,\n" + 
					"    APHI.NormalValue,\n" + 
					"    APHI.IdealValue,\n" + 
					"    APHI.Status\n" + 
					"FROM\n" + 
					"    cdt_master_data.agri_plant_health_index APHI\n" + 
					"        Inner JOIN\n" + 
					"    geo_state GS ON (APHI.StateCode = GS.StateCode)\n" + 
					"        Inner JOIN\n" + 
					"    agri_commodity AC ON (APHI.CommodityID = AC.ID)\n" + 
					"    Inner JOIN\n" + 
					"    agri_variety AV ON (APHI.VarietyID = AV.ID)\n" + 
					"     Inner JOIN\n" + 
					"    agri_phenophase AP ON (APHI.PhenophaseID = AP.ID)\n" + 
					"    where APHI.ID like :searchText\n" + 
					"    OR APHI.Name like :searchText\n" + 
					"    OR GS.Name like :searchText\n" + 
					"    OR AC.Name like :searchText\n" + 
					"    OR AV.Name like :searchText\n" + 
					"    OR AP.Name like :searchText\n" + 
					"    OR APHI.NormalValue like :searchText\n" + 
					"    OR APHI.IdealValue like :searchText\n" + 
					"    OR APHI.Status like :searchText", nativeQuery = true)
	Page<AgriPlantHealthIndexVo> getAgriPlantHealthIndexPagenatedList(Pageable pageable, String searchText);
	
	@Query(value = "SELECT APHI.ID,\n" + 
			"    APHI.Name,\n" + 
			"    GS.Name AS State,\n" + 
			"    AC.Name AS Commodity,\n" + 
			"    AV.Name As Variety,\n" + 
			"    AP.Name as Phenophase,\n" + 
			"    APHI.NormalValue,\n" + 
			"    APHI.IdealValue,\n" + 
			"    APHI.Status\n" + 
			"FROM\n" + 
			"    cdt_master_data.agri_plant_health_index_missing APHI\n" + 
			"        Inner JOIN\n" + 
			"    geo_state GS ON (APHI.StateCode = GS.StateCode)\n" + 
			"        Inner JOIN\n" + 
			"    agri_commodity AC ON (APHI.CommodityID = AC.ID)\n" + 
			"    Inner JOIN\n" + 
			"    agri_variety AV ON (APHI.VarietyID = AV.ID)\n" + 
			"     Inner JOIN\n" + 
			"    agri_phenophase AP ON (APHI.PhenophaseID = AP.ID)\n" + 
			"    where APHI.ID like :searchText\n" + 
			"    OR APHI.Name like :searchText\n" + 
			"    OR GS.Name like :searchText\n" + 
			"    OR AC.Name like :searchText\n" + 
			"    OR AV.Name like :searchText\n" + 
			"    OR AP.Name like :searchText\n" + 
			"    OR APHI.NormalValue like :searchText\n" + 
			"    OR APHI.IdealValue like :searchText\n" + 
			"    OR APHI.Status like :searchText", countQuery = "SELECT APHI.ID,\n" + 
					"    APHI.Name,\n" + 
					"    GS.Name AS State,\n" + 
					"    AC.Name AS Commodity,\n" + 
					"    AV.Name As Variety,\n" + 
					"    AP.Name as Phenophase,\n" + 
					"    APHI.NormalValue,\n" + 
					"    APHI.IdealValue,\n" + 
					"    APHI.Status\n" + 
					"FROM\n" + 
					"    cdt_master_data.agri_plant_health_index_missing APHI\n" + 
					"        Inner JOIN\n" + 
					"    geo_state GS ON (APHI.StateCode = GS.StateCode)\n" + 
					"        Inner JOIN\n" + 
					"    agri_commodity AC ON (APHI.CommodityID = AC.ID)\n" + 
					"    Inner JOIN\n" + 
					"    agri_variety AV ON (APHI.VarietyID = AV.ID)\n" + 
					"     Inner JOIN\n" + 
					"    agri_phenophase AP ON (APHI.PhenophaseID = AP.ID)\n" + 
					"    where APHI.ID like :searchText\n" + 
					"    OR APHI.Name like :searchText\n" + 
					"    OR GS.Name like :searchText\n" + 
					"    OR AC.Name like :searchText\n" + 
					"    OR AV.Name like :searchText\n" + 
					"    OR AP.Name like :searchText\n" + 
					"    OR APHI.NormalValue like :searchText\n" + 
					"    OR APHI.IdealValue like :searchText\n" + 
					"    OR APHI.Status like :searchText", nativeQuery = true)
	Page<AgriPlantHealthIndexVo> getAgriPlantHealthIndexPagenatedListMissing(Pageable pageable, String searchText);

}
