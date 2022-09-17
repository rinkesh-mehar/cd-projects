/**
 * 
 */
package in.cropdata.toolsuite.drk.dao.ndvi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import in.cropdata.toolsuite.drk.dto.ndvi.CaseNdvi;

/**
 * @author admin-pc
 *
 */
@Repository
public class CaseNdviDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<CaseNdvi> getBI(CaseNdvi caseNdvi) {

		List<CaseNdvi> emptyList = new ArrayList<>();
//		String sqlLi = "SELECT CaseID,NdviUrl,Week,Year FROM gstm_ndvi_new.kml_timeseries";
		String sqlLi = "SELECT CaseID,NdviUrl,Week,Year FROM gstm_ndvi_new.kml_timeseries where CaseID='"
				+ caseNdvi.getCaseId() + "' and Week='" + caseNdvi.getWeek() + "' and Year='" + caseNdvi.getYear()
				+ "'";

		List<CaseNdvi> query = this.jdbcTemplate.query(sqlLi, (rs, rowNum) -> new CaseNdvi(rs.getString("CaseID"),
				rs.getString("NdviUrl"), rs.getInt("Week"), rs.getInt("Year")));

		String sqlLi1 = "SELECT CaseID,NdviUrl,Week,Year FROM gstm_ndvi_new.kml_timeseries where CaseID='"
				+ caseNdvi.getCaseId() + "' and Week='" + caseNdvi.getWeek() + "'" + -1 + " and Year='"
				+ caseNdvi.getYear() + "'";
		List<CaseNdvi> query1 = this.jdbcTemplate.query(sqlLi1, (rs, rowNum) -> new CaseNdvi(rs.getString("CaseID"),
				rs.getString("NdviUrl"), rs.getInt("Week"), rs.getInt("Year")));

		String sqlLi2 = "SELECT CaseID,NdviUrl,Week,Year FROM gstm_ndvi_new.kml_timeseries where CaseID='"
				+ caseNdvi.getCaseId() + "' and Week='" + caseNdvi.getWeek() + "'" + -2 + " and Year='"
				+ caseNdvi.getYear() + "'";
		List<CaseNdvi> query2 = this.jdbcTemplate.query(sqlLi2, (rs, rowNum) -> new CaseNdvi(rs.getString("CaseID"),
				rs.getString("NdviUrl"), rs.getInt("Week"), rs.getInt("Year")));

		String sqlLi3 = "SELECT CaseID,NdviUrl,Week,Year FROM gstm_ndvi_new.kml_timeseries where CaseID='"
				+ caseNdvi.getCaseId() + "' and Week='" + caseNdvi.getWeek() + "'" + -3 + " and Year='"
				+ caseNdvi.getYear() + "'";
		List<CaseNdvi> query3 = this.jdbcTemplate.query(sqlLi3, (rs, rowNum) -> new CaseNdvi(rs.getString("CaseID"),
				rs.getString("NdviUrl"), rs.getInt("Week"), rs.getInt("Year")));

		if (query.size() > 0) {
			return query;
		} else if (query1.size() > 0) {
			return query1;
		} else if (query2.size() > 0) {
			return query2;
		} else if (query3.size() > 0) {
			return query3;
		} else {

			CaseNdvi caseN = new CaseNdvi();
			caseN.setCaseId("");
			caseN.setNdviUrl("");
			caseN.setWeek(1);
			caseN.setYear(2020);
			emptyList.add(caseN);
			return emptyList;
		}
	}

	public List<CaseNdvi> getndviList(CaseNdvi caseNdvi) {
		String sql = "SELECT CaseID,NdviUrl,Week,Year FROM gstm_ndvi_new.kml_timeseries where Week='"
				+ caseNdvi.getWeek() + "'-8 and Year='" + caseNdvi.getYear() + "'";
		return this.jdbcTemplate.query(sql, (rs, rowNum) -> new CaseNdvi(rs.getString("CaseID"),
				rs.getString("NdviUrl"), rs.getInt("Week"), rs.getInt("Year")));

	}

}
