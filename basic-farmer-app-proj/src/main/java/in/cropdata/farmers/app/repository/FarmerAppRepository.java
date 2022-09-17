/**
 * 
 */
package in.cropdata.farmers.app.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.NonUniqueResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import in.cropdata.farmers.app.gstmTransitory.entity.CaseDetailsEntity;

/**
 * @author cropdata-Aniket Naik
 *
 */
@Repository
public class FarmerAppRepository {

	private static final Logger log = LoggerFactory.getLogger(FarmerAppRepository.class);

	@Autowired
//	@Qualifier("farmersAppJdbcTemplate")
	private JdbcTemplate drkJdbcTemplate;

	@Autowired
//	@Qualifier("gstmTransitoryJdbcTemplate")
	private JdbcTemplate gstmTransitoryJdbcTemplate;

	public CaseDetailsEntity getCaseDetails(String caseId) {

		CaseDetailsEntity caseDetailsList = null;

		String sql = "Select fc.id as caseID,f.id as farmerID, fc.farm_id as farmID, gv.RegionID as regionID, cci.variety_id as varietyID\n"
				+ ",v.commodityID as commodityID, fab.StateCode as StateCode,fab.DistrictCode ,f.village_id as VillageCode,\n"
				+ "cci.corrected_sowing_week as CurrentSowingWeek, cci.corrected_sowing_year as CurrentSowingYear, gv.SubRegionID,\n"
				+ "STR_TO_DATE(concat(cci.corrected_sowing_year,cci.corrected_sowing_week, ' Wednesday'), '%X%V %W')  as CorrectedSowingDate,\n"
				+ "cci.corrected_sowing_week as sowingWeek, cci.corrected_sowing_year as SowingYear,cci.harvest_week as harvestWeek,\n"
				+ "cci.harvest_year as harvestYear,fc.crop_type as CropTypeID\n"
				+ "from drkrishi_ts.farm_case fc\n" + "inner join drkrishi_ts.farmer_farm ff on ff.ID = fc.farm_id\n"
				+ "left join drkrishi_ts.farmer f on f.ID =ff.farmer_id\n"
				+ "left join drkrishi_ts.farmer_address_book fab on fab.id = f.address_id\n"
				+ "left join cdt_master_data.geo_village gv on gv.VillageCode = f.Village_id\n"
				+ "left join drkrishi_ts.case_crop_info cci on cci.case_id = fc.id\n"
				+ "left join cdt_master_data.agri_variety  v on cci.variety_id=v.id "
				+ "where fc.ID = '" + caseId + "' LIMIT 1";

		log.info("getCaseDetails query is {}", sql);

		try {
			caseDetailsList = drkJdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(CaseDetailsEntity.class));

		} catch  (EmptyResultDataAccessException e) {
//			e.printStackTrace();
			return null;
		}

		return caseDetailsList;
	}

	public List<Map<String, Object>> getCurrentPhenophase(Integer stateCode, Integer seasionId, Integer CommodityId,
			Integer varietyId) {

		List<Map<String, Object>> phenophaseID = null;

		String sql = "SELECT \n" + "	pd.PhenophaseID \n" + "FROM gstm_transitory.case_details as cd\n"
				+ "INNER JOIN cdt_master_data.agri_phenophase_duration as pd ON\n" + "	pd.StateCode=cd.StateCode \n"
				+ "    and pd.SeasonID=cd.SeasonID\n" + "	and pd.CommodityID=cd.CommodityID \n"
				+ "    and pd.VarietyID=cd.VarietyID\n" + "WHERE cd.StateCode=" + stateCode + " \n" + "AND cd.SeasonID="
				+ seasionId + " \n" + "AND cd.CommodityID=" + CommodityId + " \n" + "AND cd.VarietyID=" + varietyId
				+ " \n"
				+ "and datediff(curdate(), cd.CorrectedSowingDate) between pd.PhenophaseStart and pd.PhenophaseEnd\n"
				+ "and cd.Expired = 'No'\n"
				+ "order by (pd.PhenophaseEnd - datediff(curdate(), cd.CorrectedSowingDate))\n" + "limit 1";

		log.info(" PhenophaseID qurey " + sql);

		try {
			phenophaseID = gstmTransitoryJdbcTemplate.queryForList(sql);
		} catch (EmptyResultDataAccessException | NonUniqueResultException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return phenophaseID;
	}

	public Map<String, Object> getLatLongByCaseId(String caseId) {

		Map<String, Object> caseLatLong = null;

		String sql = "SELECT latitude AS Latitude, longitude AS Longitude FROM\n"
				+ "drkrishi_ts.farmer_case_kmlpoints v  WHERE v.case_id = " + caseId + " limit 1";

		try {

			caseLatLong = drkJdbcTemplate.queryForMap(sql);

		} catch (EmptyResultDataAccessException e) {
			log.error(e.getMessage());
		}

		return caseLatLong;
	}

	public String getTnC() {
		String query = "select tc.TermAndCondition from gstm_transitory.farmer_term_condition tc where tc.LanguageCode = 'EN'";

		String termAndCondition = new String();

		try {
			termAndCondition = drkJdbcTemplate.queryForObject(query, String.class);
		} catch (EmptyResultDataAccessException e) {
			log.error(e.getMessage());
		}
		return termAndCondition;
	}

	public Integer getZonalCommodityIDWithSowingWeek(Integer aczID, Integer commodityID, Integer sowingWeek) {

		try {
			String query = "select zc.ID from cdt_master_data.zonal_commodity zc where zc.AczID = " + aczID
					+ " and zc.CommodityID = " + commodityID + " and " + "(" + sowingWeek
					+ " between zc.SowingWeekStart and zc.SowingWeekEnd)";

			return drkJdbcTemplate.queryForObject(query, Integer.class);

		} catch (EmptyResultDataAccessException e) {
			return 0;
		}

	}

	public Integer getZonalVarietyIDWithSowingWeek(Integer aczID, Integer zonalCommodityID, Integer varietyID, Integer sowingWeek) {

		try {
			String query = "select zv.ID from cdt_master_data.zonal_variety as zv where zv.AczID= " + aczID
					+ " and zv.ZonalCommodityID =" + zonalCommodityID + "   and zv.VarietyID = " + varietyID + "\n"
					+ " and " + sowingWeek + " between SowingWeekstart and SowingWeekEnd";

			return drkJdbcTemplate.queryForObject(query, Integer.class);
		} catch (EmptyResultDataAccessException e) {
//			e.printStackTrace();
			return 0;
		}

	}
	
	public Integer getZonalCommodityIDWithoutSowingWeek(Integer aczID, Integer commodityID) {

		try {
			String query = "select zc.ID from cdt_master_data.zonal_commodity zc where zc.AczID = " + aczID+ " and zc.CommodityID = " + commodityID + " limit 1 "; 

			return drkJdbcTemplate.queryForObject(query, Integer.class);

		} catch (EmptyResultDataAccessException e) {
			return 0;
		}

	}

	public Integer getZonalVarietyIDWithoutSowingWeek(Integer aczID, Integer zonalCommodityID, Integer varietyID) {

		try {
			String query = "select zv.ID from cdt_master_data.zonal_variety as zv where zv.AczID= " + aczID
					+ " and zv.ZonalCommodityID =" + zonalCommodityID + "   and zv.VarietyID = " + varietyID + " limit 1 ";
					
			return drkJdbcTemplate.queryForObject(query, Integer.class);
		} catch (EmptyResultDataAccessException e) {
//			e.printStackTrace();
			return 0;
		}

	}
	
	
	public Map<String,Object> getCurrentSowingWeekAndYear(Date sowingDate){
		
		try {
			String query = "SELECT \n"
					+ "    amw.Week AS currentSowingWeek,\n"
					+ "    YEAR('"+sowingDate+"') AS currentSowingYear\n"
					+ "FROM\n"
					+ "    cdt_master_data.agri_meteorological_weeks amw\n"
					+ "WHERE\n"
					+ "    amw.Day = DAY('"+sowingDate+"')\n"
					+ "	AND amw.Month = MONTH('"+sowingDate+"')";
			
			 return drkJdbcTemplate.queryForMap(query);
		}catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public Integer getCurrentDas(String sowingDate) {

		try {
			String query = "select DATEDIFF(curdate(),'" + sowingDate + "') as CurrentDas from dual";
					
			return drkJdbcTemplate.queryForObject(query, Integer.class);
		} catch (EmptyResultDataAccessException e) {
//			e.printStackTrace();
			throw e;
		}

	}

	public Integer getEndDas(Integer zonalVarietyId) {

		try {
			String query = "select max(zpd.EndDas) as EndDas from cdt_master_data.zonal_phenophase_duration zpd where zpd.ZonalVarietyID=" + zonalVarietyId;
					
			return drkJdbcTemplate.queryForObject(query, Integer.class);
		} catch (EmptyResultDataAccessException e) {
//			e.printStackTrace();
			throw e;
		}

	}
	
}
