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

import in.cropdata.farmers.app.DTO.RecommendationDetailsDTO;

@Repository
public class RecommendationListDao {
	
	@Autowired
    @Qualifier(value = "masterDataJdbcTemplate")
    private JdbcTemplate jdbcTemplate;
	
	public Map<String, Object> getRecommendationList(String caseID){
	
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate.getDataSource()).withProcedureName("get_recommendation_list_by_case").returningResultSet("recommendationListFromProc" , new RowMapper<RecommendationDetailsDTO>() {
     		 
            @Override
            public RecommendationDetailsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            	RecommendationDetailsDTO recommendationDetailsDTO = new RecommendationDetailsDTO();
            	
            	recommendationDetailsDTO.setStressControlMeasureID(rs.getInt("StressControlMeasureID"));
            	recommendationDetailsDTO.setControlMeasure(rs.getString("ControlMeasure"));
            	recommendationDetailsDTO.setRecommendationID(rs.getInt("RecommendationID"));
            	recommendationDetailsDTO.setRecommendation(rs.getString("Recommendation"));
            	recommendationDetailsDTO.setStressID(rs.getInt("StressID"));
            	recommendationDetailsDTO.setStressName(rs.getString("StressName"));
            	recommendationDetailsDTO.setSymptomID(rs.getInt("SymptomID"));
                 
                return recommendationDetailsDTO;
            }
        });
		
		SqlParameterSource inParam = new MapSqlParameterSource().addValue("CaseIDInp", caseID);
		
		Map<String, Object> outParam = jdbcCall.execute(inParam);
		return outParam;
		
	}

}
