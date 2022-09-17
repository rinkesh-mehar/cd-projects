package in.cropdata.farmers.app.repository.DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import in.cropdata.farmers.app.model.FarmCase;
import in.cropdata.farmers.app.utils.JwtTokenUtil;

/**
 * @author RinkeshKM
 * @project BasicFarmersAppAPI
 * @created 15/03/2021 - 2:54 PM
 */

@Repository
public class FarmerAppDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(FarmerAppDAO.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    JwtTokenUtil jwtTokenUtil;
    
   

    public Map<String, Object> findCaseById(String selectIrrigationSource, String selectFarmerFarmOwnership,
                                            String irrigationSourceQuery/*, String farmerFarmOwnershipQuery*/,
                                            HttpServletRequest request, String id, String caseID)
    {
        String query = "select\n" +
                "    cci.id AS ID, cci.case_id AS caseID, cci.farmer_given_yield AS farmerGivenYield, f.id as farmerID, ff.id AS farmID, cci.crop_area as cropArea,COALESCE(DATE_FORMAT(cci.sowing_date, '%Y-%m-%d'),STR_TO_DATE(concat(Year(curdate()), cci.corrected_sowing_week, ' Wednesday'), '%X %V %W')) as sowingDate,cci.corrected_sowing_week as sowingWeek,gcs.ID as caseStatusID,gcs.Name as caseStatus,\n" +
                "    cci.corrected_sowing_year as sowingYear, cci.farmer_given_yield as quantity, av.CommodityID as commodityID,cci.advisory_type AS advisoryType,\n" +
                "    ac.Name AS commodityName, cci.variety_id as varietyID, av.Name AS varietyName, cci.irrigation_source_id as irrigationSourceID, cci.season_id as seasonID,\n"
                + "                   " + selectIrrigationSource
                + " fc.crop_type AS cropTypeID, act.Name AS cropTypeName, cci.advisory_type AS advisoryType " + selectFarmerFarmOwnership + "\n"
                + "            from drkrishi_ts.case_crop_info cci\n"
                +" inner join cdt_master_data.agri_variety av on ( av.ID = cci.variety_id )\n"
                + "              inner join cdt_master_data.agri_commodity ac on (ac.ID= av.CommodityID)\n"
                  + irrigationSourceQuery
                + "              inner join drkrishi_ts.farm_case fc on (fc.ID = cci.case_id)\n"
                + "              inner join cdt_master_data.agri_crop_type act on (act.ID = fc.crop_type)\n"
                + "              inner join drkrishi_ts.farmer_farm ff on ( ff.id = fc.farm_id)\n"
                + "              inner join drkrishi_ts.farmer f on (f.ID = ff.farmer_id)\n"
                + "				left  join cdt_master_data.geo_acz z on cci.acz_id=z.ID\n"
                + "             left JOIN cdt_master_data.general_case_status gcs on gcs.ID = cci.case_status"
                + "                where cci.ID = '"+id+"' and f.auth_token = '" + jwtTokenUtil.getAuthToken(request) + "'"
                		+ " and cci.case_id = '"+caseID+"'";

        LOGGER.info("final query for fetching case by id is -> {}", query); 

        return jdbcTemplate.queryForMap(query);
    }
    
           
    
	public List<Map<String,Object>> getCaseByFarmerID(String farmerID) {

		String query = "select fc.id as caseId from drkrishi_ts.farm_case fc \n"
				+ "               inner join drkrishi_ts.farmer_farm ff on fc.farm_id = ff.id\n"
				+ "               inner join drkrishi_ts.farmer f on ff.farmer_id = f.id\n"
				+ "               where f.id = '"+farmerID+"'";
		
		LOGGER.info("{}", query);
		return jdbcTemplate.queryForList(query);

	}
	
	 
    public List<Map<String,Object>> getZonalStressByCase() {
    	
    	String query = "SELECT \n"
    			+ "    d.CaseID AS case_id,\n"
    			+ "     fs.id AS symptom_id,\n"
    			+ "     fs.GenericImage AS url,\n"
    			+ "     cd.FarmerID AS farmer_id,\n"
    			+ "     ass.Name AS stress_name,\n"
    			+ "     fs.Symptom AS Description,\n"
    			+ "     fdt.DeviceToken,\n"
    			+ "    ANY_VALUE(fs.CommodityID) AS CommodityID\n"
    			+ "FROM\n"
    			+ "    cdt_master_data.dcc_daily_case_stress d\n"
    			+ "         INNER JOIN\n"
    			+ "     cdt_master_data.dcc_daily_zonal_stress zs ON zs.id = d.dailyzonalstressID\n"
    			+ "         INNER JOIN\n"
    			+ "     cdt_master_data.zonal_variety zv ON zv.ZonalCommodityID = zs.ZonalCommodityID\n"
    			+ "        INNER JOIN\n"
    			+ "     cdt_master_data.flipbook_symptoms fs ON fs.stressID = zs.StressID AND zv.CommodityID = fs.CommodityID\n"
    			+ "         INNER JOIN\n"
    			+ "     gstm_transitory.case_details cd ON cd.CaseID = d.CaseID\n"
    			+ "         INNER JOIN\n"
    			+ "     drkrishi_ts.farmer_device_tokens fdt ON fdt.FarmerID = cd.FarmerID\n"
    			+ "         INNER JOIN\n"
    			+ "     cdt_master_data.agri_stress ass ON zs.StressID = ass.ID\n"
    			+ " WHERE\n"
    			+ "     d.CalendarWeek = WEEK(CURRENT_DATE())\n"
    			+ "        AND d.calendarYear = YEAR(CURRENT_DATE())\n"
    			+ "         AND IsNotified = 'No'\n"
    			+ " GROUP BY d.CaseID , fs.id , fs.GenericImage , cd.FarmerID , fdt.DeviceToken";
		try {
			return jdbcTemplate.queryForList(query);
		} catch (DataAccessException ex) {
			return new ArrayList<>();
		}
    }
    
    
     
    
	public void updateDccDailyStressCase(List<String> caseID) {

		String query = "update cdt_master_data.dcc_daily_case_stress set IsNotified='Yes' \n" + "where CaseID in(?)";
		    jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, caseID.get(i));
			}

			@Override
			public int getBatchSize() {

				return caseID.size();
			}     
		});
	}
	
	  public List<Map<String,Object>> getDeliveryScheduledRecords() {
	    	
	    	String query = "SELECT \n" + 
	    			"    r.id AS rightId,\n" + 
	    			"    f.primary_mob_number AS mobileNo,\n" + 
	    			"    lw.RegionID AS regionId,\n" + 
	    			"    tq.DeviceToken AS deviceToken,\n" + 
	    			"    lw.latitude AS lhLatitude,\n" + 
	    			"    lw.longitude AS lhLongitude,\n" + 
	    			"    lw.Zl20TileID AS lhZl20TileId,\n" + 
	    			"    r.estimated_travel_time AS estimatedTravelTime,\n" + 
	    			"    DATE_FORMAT(lh.min_time, '%H:%i') AS minTime,\n" + 
	    			"    tq.Status AS status\n" + 
	    			"FROM\n" + 
	    			"    drkrishi_ts.rights r\n" + 
	    			"        INNER JOIN\n" + 
	    			"    logistic_hub.rights rr ON r.id = rr.right_id\n" + 
	    			"        AND version_number = 1\n" + 
	    			"        INNER JOIN\n" + 
	    			"    logistic_hub.lh_shift_schedule lh ON CAST(r.logistic_hub_id AS UNSIGNED) = lh.lh_id\n" + 
	    			"        AND lh.shift = r.shift_id\n" + 
	    			"        INNER JOIN\n" + 
	    			"    drkrishi_ts.farm_case fc ON r.case_id = fc.id\n" + 
	    			"        INNER JOIN\n" + 
	    			"    drkrishi_ts.farmer_farm ff ON ff.id = fc.farm_id\n" + 
	    			"        INNER JOIN\n" + 
	    			"    drkrishi_ts.farmer f ON ff.farmer_id = f.id\n" + 
	    			"        INNER JOIN\n" + 
	    			"    drkrishi_ts.farmer_device_tokens tq ON tq.FarmerID = f.id\n" + 
	    			"        INNER JOIN\n" + 
	    			"    logistic_hub.lh_warehouse lw ON CAST(r.logistic_hub_id AS UNSIGNED) = lw.id\n" + 
	    			"WHERE\n" + 
	    			"    r.delivery_date = CURDATE()\n" + 
	    			"        AND TIME(DATE_ADD(NOW(), INTERVAL 2 HOUR)) BETWEEN lh.min_time AND lh.max_time\n" + 
	    			"        AND (tq.status IS NULL\n" + 
	    			"        OR tq.status = 'Notified')\n" + 
	    			"        AND rr.right_status IN (3 , 4, 5, 6)";
			try {
				return jdbcTemplate.queryForList(query);
			} catch (DataAccessException ex) {
				return new ArrayList<>();
			}
	    }
	  
		public Map<String, Object> fingRightsDetails(String caseID) {
			String query = "SELECT \n" + 
					"    r.stage AS stage,\n" + 
					"    r.current_quantity AS currentQuantity,\n" + 
					"    r.estimated_quantity AS estimatedQuantity,\n" + 
					"    r.allowable_variance_qty_pos AS allowableVarianceQtyPos,\n" + 
					"    r.allowable_variance_qty_neg AS allowableVarianceQtyNeg,\n" + 
					"    r.current_quality AS currentQuality,\n" + 
					"    r.allowable_variance_quality AS allowableVarianceQuality,\n" + 
					"    r.mbep AS mbep\n" + 
					"FROM\n" + 
					"    drkrishi_ts.rights r\n" + 
					"        INNER JOIN\n" + 
					"    (SELECT \n" + 
					"        rights.id, MAX(rights.version_number) AS version_number\n" + 
					"    FROM\n" + 
					"        drkrishi_ts.rights rights\n" + 
					"    GROUP BY rights.id) temp_rights ON (r.id = temp_rights.id\n" + 
					"        AND r.version_number = temp_rights.version_number)\n" + 
					"WHERE\n" + 
					"    r.case_id = '" + caseID + "' GROUP BY r.stage , r.current_quantity , r.estimated_quantity , r.allowable_variance_qty_pos , r.allowable_variance_qty_neg , r.current_quality , r.allowable_variance_quality , r.mbep";

			LOGGER.info("final query for fetching rights details by id is -> {}", query);

			return jdbcTemplate.queryForMap(query);
		}
  
}
