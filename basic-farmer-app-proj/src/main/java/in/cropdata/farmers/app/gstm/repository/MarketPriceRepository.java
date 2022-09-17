/**
 * 
 */
package in.cropdata.farmers.app.gstm.repository;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author cropdata-Aniket Naik
 *
 */
@Repository
public class MarketPriceRepository {

	private static final Logger log = LoggerFactory.getLogger(MarketPriceRepository.class);

	@Autowired
	@Qualifier("gstmJdbcTemplate")
	private JdbcTemplate gstmJdbcTemplate;

	public List<Map<String, Object>> getMarketData(Integer districtCode, Integer commodityId, String arrivalDate) {

		String sqlQuery = "select pwm.StateCode, lower(gs.Name) stateName, pwm.districtCode, lower(gd.Name) districtName,\n"
				+ "			           pwm.commodityID,lower(ac.Name) commodityName, \n"
				+ "			           pwm.pricingAgriVarietyID,\n"
				+ "			           lower(pav.VarietyName) marketVarietyName, \n"
				+ "			           pwm.MarketID, lower(am.Name) marketName,\n"
				+ "			           lower(pav.VarietyName) varietyName,\n"
				+ "			           pwm.maxPrice,pwm.modalPrice,pwm.minPrice,date_format(pwm.ArrivalDate, '%d-%b-%y') arrivalDate,\n"
				+ "			           ifnull(pwm.MinPriceChangePercent,0.0) as minPriceChangePercentage,\n"
				+ "				       ifnull(pwm.MaxPriceChangePercent,0.0) as maxPriceChangePercentage,\n"
				+ "				       ifnull(pwm.ModalPriceChangePercent,0.0) as modalPriceChangePercentage \n"
				+ "			           from gstm_data_models.pvi_daily_market_price pwm\n"
				+ "			           inner join cdt_master_data.geo_state gs on gs.StateCode = pwm.StateCode\n"
				+ "			           inner join cdt_master_data.geo_district gd on gd.DistrictCode = pwm.DistrictCode\n"
				+ "			           inner join cdt_master_data.agri_commodity ac on ac.ID = pwm.CommodityID\n"
				+ "			           inner join  cdt_master_data.pricing_agri_variety pav on pav.ID = pwm.PricingAgriVarietyID\n"
				+ "			           inner join  cdt_master_data.agri_market am on am.ID = pwm.MarketID \n"
				+ "			           where pwm.CommodityID= " + commodityId + " and pwm.DistrictCode = " + districtCode + " \n"
				+ "			           and DATE(pwm.ArrivalDate)  between DATE('" + arrivalDate + "' - INTERVAL 2 DAY) and  DATE('" + arrivalDate + "')  \n"
				+ "			           order by stateName asc, districtName asc, pwm.ArrivalDate desc \n"
				+ "			           limit 5";
		
		log.info("Market price query {}", sqlQuery);

		List<Map<String, Object>> ls = gstmJdbcTemplate.queryForList(sqlQuery);
		log.info("ls {}", ls);
		return ls;
	}
	
	
	
	   
	
	public String getlatestArrivalDate(Integer districtCode, Integer commodityId) {

//		String query = "SELECT "
//				+ " max(p.ArrivalDate) AS arrivalDate \n"
//				+ "FROM\n"
//				+ " gstm_data_models.pvi_daily_market_price p\n"
//				+ "WHERE\n"
//				+ " p.CommodityID = "+commodityId+" \n"
//				+ " AND p.DistrictCode = "+districtCode+" \n"
//				+ "ORDER BY p.ArrivalDate DESC\n"
//				+ "LIMIT 1";

		try {

			String query = "select MAX(p.ArrivalDate) AS ArrivalDate\n"
					+ "from gstm_data_models.pvi_daily_market_price p\n" + " where p.DistrictCode = " + districtCode
					+ " and p.CommodityID= " + commodityId + "\n" + " group by  p.DistrictCode,p.CommodityID";

			log.info(" Latest Arrival Date query {}", query);

			return gstmJdbcTemplate.queryForObject(query, String.class);
		} catch (DataAccessException e) {
			return null;
		}
	}
	
	
	
	public String getNeighbourDistrictByCode(Integer districtCode) {

		String query = "select concat(d.DistrictCode,\",\",d.NeighbourDistricts) as NeighbourDistricts \n"
				+ " from cdt_master_data.geo_district d where d.DistrictCode= " + districtCode + "";
		
		log.info("getNeighbourDistrictByCode {}", query);
		try {
			return gstmJdbcTemplate.queryForObject(query, String.class);
		} catch (DataAccessException e) {
			return null;
		}
	}
	
	
	
	
	
	
	
	
	
}
