package in.cropdata.toolsuite.drk.dao.agdata;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class AgDataDao {

	private static final Logger logger = LoggerFactory.getLogger(AgDataDao.class);

	@Autowired
	JdbcTemplate masterDataJdbcTemplate;

	public String getData() {
		String resultJson = emptyResponse();

		String query = "SELECT ac.Name as Commodity, agc.Name as GeneralCommodity, acc.Name as CommodityClass,ahc.HSCode,gu.Name as Uom,ahc.Description\n"
				+ "FROM agri_hs_code ahc\n"
				+ "INNER JOIN cdt_master_data.agri_commodity ac ON ac.ID = ahc.CommodityID\n"
				+ "INNER JOIN cdt_master_data.agri_general_commodity agc ON agc.ID = ahc.GeneralCommodityID\n"
				+ "INNER JOIN cdt_master_data.general_uom gu ON gu.ID = ahc.UomID \n"
				+ "INNER JOIN cdt_master_data.agri_commodity_class acc ON acc.ID = ahc.CommodityClassID\n"
				+ "where ahc.Status='Active'";

		ObjectMapper mapper = new ObjectMapper();

		List<Map<String, Object>> ls = masterDataJdbcTemplate.queryForList(query);

		try {
			resultJson = mapper.writeValueAsString(ls);
		} catch (JsonProcessingException e) {
			logger.error("Failed to convert ResultSet to JSON", e);

		} // if query not null and valid

		return resultJson;
	}

	private String emptyResponse() {
		return "{}";
	}

	public String getData(long unixTimestamp) {

		String resultJson = emptyResponse();

		String query = "SELECT ac.Name as Commodity, agc.Name as GeneralCommodity, acc.Name as CommodityClass, ahc.HSCode,gu.Name as Uom,ahc.Description\n"
				+ "FROM cdt_master_data.agri_hs_code ahc\n"
				+ "INNER JOIN cdt_master_data.agri_commodity ac ON ac.ID = ahc.CommodityID\n"
				+ "INNER JOIN cdt_master_data.agri_general_commodity agc ON agc.ID = ahc.GeneralCommodityID\n"
				+ "INNER JOIN cdt_master_data.agri_commodity_class acc ON acc.ID = ahc.CommodityClassID\n"
				+ "INNER JOIN cdt_master_data.general_uom gu ON gu.ID = ahc.UomID \n"
				+ "where ahc.Status='Active'\n" + "AND (UNIX_TIMESTAMP(ahc.CreatedAt) > '" + unixTimestamp
				+ "' or UNIX_TIMESTAMP(ahc.UpdatedAt) > '" + unixTimestamp + "')\n"
				+ "OR (UNIX_TIMESTAMP(ac.CreatedAt) > '" + unixTimestamp + "' or UNIX_TIMESTAMP(ac.UpdatedAt) > '"
				+ unixTimestamp + "')\n" + "OR (UNIX_TIMESTAMP(agc.CreatedAt) > '" + unixTimestamp
				+ "' or UNIX_TIMESTAMP(agc.UpdatedAt) > '" + unixTimestamp + "')\n"
				+ "OR (UNIX_TIMESTAMP(acc.CreatedAt) > '" + unixTimestamp + "' or UNIX_TIMESTAMP(acc.UpdatedAt) > '"
				+ unixTimestamp + "')";

		ObjectMapper mapper = new ObjectMapper();

		List<Map<String, Object>> ls = masterDataJdbcTemplate.queryForList(query);

		try {
			resultJson = mapper.writeValueAsString(ls);
		} catch (JsonProcessingException e) {
			logger.error("Failed to convert ResultSet to JSON", e);

		} // if query not null and valid

		return resultJson;

	}

}
