package in.cropdata.farmers.app.drk.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DrkrishiDaoRepository {
	
	private static final Logger logger = LoggerFactory.getLogger(DrkrishiDaoRepository.class);

	@Autowired
	@Qualifier("drkrishiDataJdbcTemplate")
	JdbcTemplate drkrishiDataJdbcTemplate;
	
	public List<Map<String, Object>> getCaseListByFarmer(String farmerID) {
		
		String sql = " select cci.id as ID, cci.case_id as caseID, ff.farmer_id as farmerID, cci.variety_id as varietyID, av.name as varietyName, cci.crop_area as cropArea, \n" + 
				"cci.corrected_sowing_week as sowingWeek, cci.sowing_year as sowingYear, av.CommodityID as commodityID, ac.name as commodityName, cci.case_status as caseStatusID, \n" + 
				"fc.crop_type as cropTypeID, act.name as cropTypeName, gcs.name as caseStatus, \n" + 
				"COALESCE(DATE_FORMAT(cci.sowing_date, '%Y-%m-%d'),DATE_FORMAT(STR_TO_DATE(concat(cci.sowing_year,cci.corrected_sowing_week, ' Wednesday'), '%X%V %W'), '%Y-%m-%d') ) as sowingDate \n" + 
				"from drkrishi_ts.case_crop_info cci  \n" + 
				"inner join drkrishi_ts.farm_case fc on fc.id = cci.case_id \n" + 
				"inner join drkrishi_ts.farmer_farm ff on ff .id = fc.farm_id \n" + 
				"left join cdt_master_data.general_case_status gcs on gcs.ID = cci.case_status \n" + 
				"inner join cdt_master_data.agri_variety av on av.ID = cci.variety_id \n" + 
				"inner join cdt_master_data.agri_commodity ac on ac.ID = av.CommodityID \n" + 
				"inner join cdt_master_data.agri_crop_type act on act.ID = fc.crop_type \n" + 
				"where ff.farmer_id = '"+farmerID+"' order by fc.CreatedAt desc  ";

		System.out.println(sql);
		return drkrishiDataJdbcTemplate.queryForList(sql);
	}
	
public Map<String,Object> getCurrentSowingWeekAndYear(String sowingDate){
		
		try {
			String query = "SELECT \n"
					+ "    amw.Week AS currentSowingWeek,\n"
					+ "    YEAR('"+sowingDate+"') AS currentSowingYear\n"
					+ "FROM\n"
					+ "    cdt_master_data.agri_meteorological_weeks amw\n"
					+ "WHERE\n"
					+ "    amw.Day = DAY('"+sowingDate+"')\n"
					+ "	AND amw.Month = MONTH('"+sowingDate+"')";
			
			 return drkrishiDataJdbcTemplate.queryForMap(query);
		}catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

}
