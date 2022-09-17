/**
 * 
 */
package in.cropdata.farmers.app.gstmTransitory.repository;



import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.cropdata.farmers.app.DTO.CaseCropInfoDTO;
import in.cropdata.farmers.app.gstmTransitory.dto.CaseDetailsDTO;
import in.cropdata.farmers.app.gstmTransitory.entity.CaseDetailsEntity;

/**
 * @author cropdata-Aniket Naik
 *
 */
@Repository
public interface CaseDetailRepository  extends JpaRepository<CaseDetailsEntity,Integer>{
	
	@Query(value="SELECT SeasonID\n"
			+ "FROM cdt_master_data.regional_season\n"
			+ "WHERE StateCode = ?1\n"
			+ "and if(?2 < 5, ?2 + 52, ?2) between SeasonStartWeek and \n"
			+ " if(SeasonEndWeek < SeasonStartWeek, SeasonEndWeek + 52, SeasonEndWeek)",nativeQuery= true)
	public Integer getCurrentSeasionId(Integer stateCode,Integer sowingWeek);
	
	
//	@Query(value="SELECT \n"
//			+ "pd.PhenophaseID \n"
//			+ "FROM gstm_transitory.case_details as cd\n"
//			+ "INNER JOIN cdt_master_data.zonal_phenophase_duration as pd \n"
//			+ "on  pd.ZonalVarietyID = cd.ZonalVarietyID \n"
//			+ "inner join cdt_master_data.zonal_variety zv on cd.ZonalCommodityID=zv.ZonalCommodityID \n"
//			+ "and cd.CurrentSowingWeek between  zv.SowingWeekStart and zv.SowingWeekEnd\n"
//			+ "WHERE cd.StateCode=?1\n"
//			+ "AND cd.ZonalCommodityID=?2\n"
//			+ "AND cd.ZonalVarietyID=?3\n"
//			+ "and datediff(curdate(), if(curdate() > STR_TO_DATE(concat(Year(curdate()), cd.CurrentSowingWeek, ' Wednesday'), '%X %V %W'),\n"
//			+ " STR_TO_DATE(concat(Year(curdate()), cd.CurrentSowingWeek, ' Wednesday'), '%X %V %W'), STR_TO_DATE(concat(Year(curdate())-1, cd.CurrentSowingWeek, ' Wednesday'), \n"
//			+ " '%X %V %W'))) \n"
//			+ "order by (pd.EndDas - datediff(curdate(), cd.CorrectedSowingDate))\n"
//			+ "limit 1",nativeQuery = true)
//	public Integer getPhenophaseId(Integer stateCode, Integer zonalCommodityId, Integer zonalVarietyId);
//	
	
	

	@Query(value = "select\n" + 
			"	zv.CommodityID, zv.ID as ZonalVarietyID, cd.corrected_sowing_week as CurrentSowingWeek, \n" + 
			"	ac.Name as commodityName, av.Name as varietyName,zv.ZonalCommodityID,zv.VarietyID,cd.sowing_date as SowingDate \n" + 
			"	from \n" + 
			"	drkrishi_ts.case_crop_info cd \n" + 
			"	inner join cdt_master_data.zonal_variety zv on cd.variety_id=zv.varietyID and cd.acz_id=zv.AczID \n" + 
			"    and cd.corrected_sowing_week \n" + 
			"    between zv.SowingWeekStart and zv.SowingWeekEnd \n" + 
			"	inner join cdt_master_data.agri_commodity ac on ac.ID = zv.CommodityID \n" + 
			"	inner join cdt_master_data.agri_variety av on av.ID = zv.VarietyID \n" + 
			"	where cd.case_id = ?1", nativeQuery = true)
	CaseDetailsDTO findCaseDetailsByID(String caseID);

	CaseDetailsEntity findByCaseID(String caseID);
	
	@Query(value = "select cd.KmlUrl from gstm_transitory.case_details cd where FarmID = ?1 and CaseID = ?2", nativeQuery = true)
    Map<String,String> getKmlUrlByFarmIdAndCaseID(String farmID, String caseID);

}
