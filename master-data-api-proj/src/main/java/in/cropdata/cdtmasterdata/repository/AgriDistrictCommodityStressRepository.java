package in.cropdata.cdtmasterdata.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.dto.interfaces.AgriDistrictCommodityStressInfDto;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriCommodityStressStageInfDto;
import in.cropdata.cdtmasterdata.model.AgriDistrictCommodityStress;

public interface AgriDistrictCommodityStressRepository extends JpaRepository<AgriDistrictCommodityStress, Integer> {
	
	
	
//	String queryForAgriStress = "SELECT AC.Name as Commodity,AST.Name as StressType,ABST.ID,ABST.Name,ABST.ScientificName,ABST.CommodityID,\n"
//			+ "ABST.StressTypeID,ABST.StartPhenophaseId,ABST.EndPhenophaseId,concat(:url,+ABST.ImageID) as ImageURL, \n"
//			+ "		(SELECT name FROM agri_phenophase where ID= ABST.StartPhenophaseID) as StartPhenophase , \n"
//			+ "		(SELECT name FROM agri_phenophase where ID= ABST.EndPhenophaseID) as EndPhenophase ,\n"
//			+ "		ABST.CreatedAt,ABST.UpdatedAt,ABST.Status FROM agri_commodity_stress ABST \n"
//			+ "        LEFT JOIN agri_commodity AC ON (AC.ID = ABST.CommodityID)\n"
//			+ "		LEFT JOIN agri_stress_type AST ON (AST.ID = ABST.StressTypeID)\n"
//			+ " 			 where ABST.Name like :searchText ";
//
//	String queryCountForAgriStress = "SELECT count(ABST.ID)" + " FROM agri_commodity_stress ABST \n"
//			+ "LEFT JOIN agri_commodity AC ON (AC.ID = ABST.CommodityID) \n"
////			+ "LEFT JOIN agri_phenophase AP ON (AP.ID = ABST.PhenophaseID) \n"
//			+ "LEFT JOIN agri_stress_type AST ON (AST.ID = ABST.StressTypeID) \n"
//			+ " where ABST.Name like :searchText ";
//
//@Query(value = queryForAgriStress, countQuery = queryCountForAgriStress, nativeQuery = true)
//	Page<AgriStressInfDto> getStressList(Pageable pageable, String url, String searchText);
	
	
	@Query(value = "Select ADCS.ID,ADCS.CommodityID,ADCS.StressTypeID,ADCS.StartPhenophaseID,ADCS.EndPhenophaseID,\n" + 
			"					agri_stress.Name,ADCS.Status,\n" + 
			"					AC.Name as Commodity,AST.Name as StressType,\n" + 
			"					gs.Name as StateName, gd.Name as DistrictName, ags.Name as SeasonName, av.Name as VarietyName,\n" + 
			"					ADCS.FileUrl as ImageURL ,\n" + 
			"					ASP.Name as StartPhenophase,\n" + 
			"					AEP.Name as EndPhenophase, ADCS.IsValid, ADCS.ErrorMessage\n" + 
			"					from agri_district_commodity_stress ADCS left join agri_commodity AC on(AC.ID = ADCS.CommodityID)\n" + 
			"					left join agri_stress_type AST on(AST.ID = ADCS.StressTypeID)\n" + 
			"					left join agri_phenophase ASP on(ASP.ID = ADCS.StartPhenophaseID)\n" + 
			"					left join agri_phenophase AEP on(AEP.ID = ADCS.EndPhenophaseID)\n" + 
			"					Inner join geo_state gs on ADCS.StateCode = gs.StateCode\n" + 
			"					Inner join geo_district gd on ADCS.DistrictCode = gd.DistrictCode\n" + 
			"					Inner join agri_season ags on  ADCS.SeasonID = ags.ID\n" + 
			"					Inner join agri_variety av on ADCS.VarietyID = av.ID\n" + 
			"				 	Inner join agri_stress agri_stress on(agri_stress.ID = ADCS.StressID)\n" + 
			"					where AC.Name like :searchText\n" + 
			"					or AST.Name like :searchText\n" + 
			"					or ASP.Name like :searchText\n" + 
			"					or AEP.Name like :searchText\n" + 
			"					or agri_stress.Name like :searchText\n" + 
			"                    or ags.Name like :searchText\n" + 
			"                    or gs.Name like :searchText\n" + 
			"                    or gd.Name like :searchText\n" + 
			"                    or av.Name like :searchText",
			countQuery = "Select count(ADCS.ID) as Count \n" + 
					"										from agri_district_commodity_stress ADCS left join agri_commodity AC on(AC.ID = ADCS.CommodityID) \n" + 
					"										left join agri_stress_type AST on(AST.ID = ADCS.StressTypeID)\n" + 
					"										left join agri_phenophase ASP on(ASP.ID = ADCS.StartPhenophaseID) \n" + 
					"										left join agri_phenophase AEP on(AEP.ID = ADCS.EndPhenophaseID)\n" + 
					"										Inner join geo_state gs on ADCS.StateCode = gs.StateCode\n" + 
					"										Inner join geo_district gd on ADCS.DistrictCode = gd.DistrictCode\n" + 
					"										Inner join agri_season ags on  ADCS.SeasonID = ags.ID\n" + 
					"										Inner join agri_variety av on ADCS.VarietyID = av.ID\n" + 
					"									 	Inner join agri_stress agri_stress on(agri_stress.ID = ADCS.StressID)\n" + 
					"										where AC.Name like :searchText\n" + 
					"										or AST.Name like :searchText\n" + 
					"										or ASP.Name like :searchText\n" + 
					"										or AEP.Name like :searchText\n" + 
					"										or agri_stress.Name like :searchText \n" + 
					"					                    or ags.Name like :searchText\n" + 
					"					                    or gs.Name like :searchText\n" + 
					"					                    or gd.Name like :searchText\n" + 
					"					                    or av.Name like :searchText", nativeQuery = true)
	Page<AgriDistrictCommodityStressInfDto> getDistrictCommodityStressPaginatedList(Pageable pageable,String searchText);


	@Query(value = "Select ADCS.ID,ADCS.CommodityID,ADCS.StressTypeID,ADCS.StartPhenophaseID,ADCS.EndPhenophaseID,\n" + 
			"					agri_stress.Name,ADCS.Status,\n" + 
			"					AC.Name as Commodity,AST.Name as StressType,\n" + 
			"					gs.Name as StateName, gd.Name as DistrictName, ags.Name as SeasonName, av.Name as varietyName,\n" + 
			"					ADCS.FileUrl as ImageURL ,\n" + 
			"					ASP.Name as StartPhenophase,\n" + 
			"					AEP.Name as EndPhenophase, ADCS.IsValid, ADCS.ErrorMessage\n" + 
			"					from agri_district_commodity_stress ADCS left join agri_commodity AC on(AC.ID = ADCS.CommodityID)\n" + 
			"					left join agri_stress_type AST on(AST.ID = ADCS.StressTypeID)\n" + 
			"					left join agri_phenophase ASP on(ASP.ID = ADCS.StartPhenophaseID)\n" + 
			"					left join agri_phenophase AEP on(AEP.ID = ADCS.EndPhenophaseID)\n" + 
			"					Inner join geo_state gs on ADCS.StateCode = gs.StateCode\n" + 
			"					Inner join geo_district gd on ADCS.DistrictCode = gd.DistrictCode\n" + 
			"					Inner join agri_season ags on  ADCS.SeasonID = ags.ID\n" + 
			"					Inner join agri_variety av on ADCS.VarietyID = av.ID\n" + 
			"				 	Inner join agri_stress agri_stress on(agri_stress.ID = ADCS.StressID)\n" + 
			"					where  ADCS.IsValid = 0 and (AC.Name like :searchText\n" + 
			"					or AST.Name like :searchText\n" + 
			"					or ASP.Name like :searchText\n" + 
			"					or AEP.Name like :searchText\n" + 
			"					or agri_stress.Name like :searchText)",
			countQuery = "Select count(ADCS.ID) as Count\n" + 
					"								from agri_district_commodity_stress ADCS left join agri_commodity AC on(AC.ID = ADCS.CommodityID) \n" + 
					"								left join agri_stress_type AST on(AST.ID = ADCS.StressTypeID) \n" + 
					"								left join agri_phenophase ASP on(ASP.ID = ADCS.StartPhenophaseID) \n" + 
					"								left join agri_phenophase AEP on(AEP.ID = ADCS.EndPhenophaseID)\n" + 
					"								Inner join geo_state gs on ADCS.StateCode = gs.StateCode\n" + 
					"								Inner join geo_district gd on ADCS.DistrictCode = gd.DistrictCode\n" + 
					"								Inner join agri_season ags on  ADCS.SeasonID = ags.ID\n" + 
					"								Inner join agri_variety av on ADCS.VarietyID = av.ID\n" + 
					"							 	Inner join agri_stress agri_stress on(agri_stress.ID = ADCS.StressID)\n" + 
					"								where  ADCS.IsValid = 0 and (AC.Name like :searchText\n" + 
					"								or AST.Name like :searchText\n" + 
					"								or ASP.Name like :searchText\n" + 
					"								or AEP.Name like :searchText\n" + 
					"								or agri_stress.Name like :searchText)", nativeQuery = true)
	Page<AgriDistrictCommodityStressInfDto> getDistrictCommodityStressPaginatedListWithInvalidated(Pageable pageable, String searchText);


	@Query(value = "select agri_stress.ID , agri_stress.Name from agri_stress agri_stress\n" + 
			"			Inner Join agri_commodity_stress ACS ON (agri_stress.ID = ACS.StressID)\n" + 
			"			Inner Join agri_stress_type AST ON (agri_stress.StressTypeID = AST.ID)\n" + 
			"			Left Join agri_commodity AC ON (ACS.CommodityID = AC.ID)\n" + 
			"			where ACS.CommodityID = ?1  and agri_stress.StressTypeID = ?2  and ACS.Status='Active'\n" + 
			"			order by agri_stress.Name", nativeQuery = true)
	List<AgriDistrictCommodityStressInfDto> findAllByStressTypeId(int commodityID, int stressTypeId);
	
	//StartPhenophaseID and EndPhenophaseID
	@Query(value = "select ID,CommodityID,StartPhenophaseID,EndPhenophaseID\n" + 
			"from agri_commodity_stress where CommodityID=2 and status='Active'", nativeQuery = true)
	List<AgriDistrictCommodityStress> findCommodityByStartPhenophaseAndEndPhenophase(int startPhenophaseId, int endPhenophaseId);
	//StartPhenophaseID and EndPhenophaseID
	
	List<AgriDistrictCommodityStress> findByCommodityIdAndStressTypeId(int commodityId, int stressTypeId);

	@Query(value = "SELECT distinct ASS.ID as StressID, ASS.Name as Stress,AC.ID as CommodityID, AC.Name as Commodity\n"
			+ "       FROM agri_commodity_stress ASS \n" + "LEFT JOIN agri_commodity AC ON (ASS.CommodityID = AC.ID)\n"
			+ "       where ASS.CommodityID = ?1 and ASS.Status='Active'", nativeQuery = true)
	List<AgriCommodityStressStageInfDto> getStressByCommodityId(int commodityId);

	@Query(value = "select distinct ass.ID,acs.CommodityID,ass.StressTypeID,ads.StartPhenophaseID,ads.EndPhenophaseID,ass.Name,ads.ImageID,ads.FileUrl  as ImageUrl,acs.Status\n" + 
			"				from agri_commodity_stress acs\n" + 
			"			left join agri_stress ass on (ass.ID = acs.StressID)\n" + 
			"            inner join cdt_master_data.agri_district_commodity_stress ads on (acs.CommodityID=ads.CommodityID and acs.StressID=ads.StressID)\n" + 
			"			where acs.CommodityID= ?1 and ?2 between ads.StartPhenophaseID and ads.EndPhenophaseID and acs.status = 'Active' order by ass.name", nativeQuery = true)
	List<AgriDistrictCommodityStressInfDto> findByCommodityId(int commodityId,int phenophaseId);


//	@Query(value = "select distinct Phenophases from agri_commodity_stress where CommodityID=?1 and StartPhenophaseID=?2 and EndPhenophaseID=?3 and ID =?4", nativeQuery = true)
	@Query(value = "select distinct Phenophases from agri_district_commodity_stress where CommodityID=? and StartPhenophaseID=? and EndPhenophaseID=? and StressID =?", nativeQuery = true)
	String getPhenophaseByCommodityStartEndPhenophase(int commodityId, int startPhenophase, int endPhenophase, int stressId);
	
	@Query(value = "select agri_stress.ID , agri_stress.Name from agri_stress agri_stress\n" + 
			"Inner Join agri_commodity_stress ACS ON (agri_stress.ID = ACS.StressID)\n" + 
			"Inner Join agri_stress_type AST ON (agri_stress.StressTypeID = AST.ID)\n" + 
			"Left Join agri_commodity AC ON (ACS.CommodityID = AC.ID)\n" + 
			"where ACS.CommodityID = ?1  and agri_stress.StressTypeID = ?2  and ACS.Status='Active'\n" + 
			"order by agri_stress.ID", nativeQuery = true)
	List<AgriDistrictCommodityStressInfDto> getStressByCommodityIdAndStressTypeId(int commodityId,int stressTypeId);

	@Query(value = "SELECT\n" +
			"(select\n" +
			"    group_concat(distinct c.PhenophaseID)\n" +
			"    from cdt_master_data.agri_phenophase_duration as c\n" +
			"             inner join cdt_master_data.agri_phenophase as ap on ap.ID = c.PhenophaseID\n" +
			"    WHERE c.StateCode = s.StateCode and c.SeasonID = s.SeasonID and\n" +
			"          c.CommodityID = s.CommodityID and c.VarietyID = s.VarietyID and\n" +
			"          (c.PhenophaseStart between min(d1.PhenophaseStart) and max(d2.PhenophaseEnd) or\n" +
			"          c.PhenophaseEnd between min(d1.PhenophaseStart) and max(d2.PhenophaseEnd))\n" +
			") as phenophases\n" +
			"FROM cdt_master_data.agri_district_commodity_stress as s\n" +
			"         INNER JOIN cdt_master_data.agri_phenophase_duration as d1 on\n" +
			"             (d1.StateCode = s.StateCode\n" +
			"                  and d1.SeasonID = s.SeasonID\n" +
			"                  and d1.CommodityID = s.CommodityID\n" +
			"                  and d1.VarietyID = s.VarietyID\n" +
			"                  and d1.PhenophaseID = s.StartPhenophaseID)\n" +
			"         INNER JOIN cdt_master_data.agri_phenophase_duration as d2 on\n" +
			"                  (d2.StateCode = s.StateCode and d2.SeasonID = s.SeasonID\n" +
			"                  and d2.CommodityID = s.CommodityID\n" +
			"                  and d2.VarietyID = s.VarietyID\n" +
			"                  and d2.PhenophaseID = s.EndPhenophaseID)\n" +
			"WHERE s.StateCode = ?1 and s.SeasonId = ?2 and s.CommodityID = ?3 and s.VarietyID = ?4 and StressID=?5 \n" +
			"group by s.StateCode, s.SeasonId, s.CommodityID, s.VarietyID, s.StressID;", nativeQuery = true)
	String calculatePhenophase(Integer stateCode, Integer SeasonId, Integer commodityID, Integer varietyID, Integer stressID);
}