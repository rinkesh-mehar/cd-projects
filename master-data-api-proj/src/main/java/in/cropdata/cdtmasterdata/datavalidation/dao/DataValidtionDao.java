package in.cropdata.cdtmasterdata.datavalidation.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

@Repository
public class DataValidtionDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Map<String, Object> getDataValidation(){
	
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate.getDataSource()).withProcedureName("DataValidation");
		
		Map<String, Object> out = jdbcCall.execute();
		return out;
		
	}//getDataValidation

}//DataValidtionDao
