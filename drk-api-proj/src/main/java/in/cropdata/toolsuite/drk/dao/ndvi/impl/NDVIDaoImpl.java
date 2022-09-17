package in.cropdata.toolsuite.drk.dao.ndvi.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import in.cropdata.toolsuite.drk.dao.ndvi.NDVIDaoInf;

public class NDVIDaoImpl implements NDVIDaoInf {

	@Autowired
	@Qualifier("ndviDataSource")
	JdbcTemplate gstmNdviJdbcTemplate;

	@Override
	public Long getAverageNDVI(String spotList) {
		String sql = "select avg(ndvi) from ndvi_details_zl20 where TileId IN (" + spotList + ")";
		return gstmNdviJdbcTemplate.queryForObject(sql, Long.class);
	}

}
