package in.cropdata.farmers.app.repository.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import in.cropdata.farmers.app.DTO.StressDetailsDTO;

@Repository
public class StressListDao {
	
	@Autowired
    @Qualifier(value = "masterDataJdbcTemplate")
    private JdbcTemplate jdbcTemplate;
	
	public Map<String, Object> getStressList(String caseID){
	
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate.getDataSource()).withProcedureName("get_dcc_stress_list_by_case").returningResultSet("stressListFromProc" , new RowMapper<StressDetailsDTO>() {
      		 
            @Override
            public StressDetailsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            	StressDetailsDTO stressDetailsDTO = new StressDetailsDTO();
            	
            	stressDetailsDTO.setCaseID(rs.getString("case_id"));
            	stressDetailsDTO.setSymptomID(rs.getInt("symptom_id"));
            	stressDetailsDTO.setGenericImage(rs.getString("url"));
            	stressDetailsDTO.setStressName(rs.getString("stress_name"));
            	stressDetailsDTO.setSymptom(rs.getString("description"));
            	stressDetailsDTO.setCommodityID(rs.getInt("commodity_id"));
            	stressDetailsDTO.setReported("Yes".equals(rs.getString("reported"))?true:false);
                 
                return stressDetailsDTO;
            }
        });
    	
    	SqlParameterSource inParam = new MapSqlParameterSource().addValue("CaseIDInp", caseID);
		
		Map<String, Object> outParam = jdbcCall.execute(inParam);
		return outParam;
		
	}

}
