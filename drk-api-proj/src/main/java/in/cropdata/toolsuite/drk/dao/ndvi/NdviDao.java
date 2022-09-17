package in.cropdata.toolsuite.drk.dao.ndvi;

import org.springframework.stereotype.Repository;

@Repository
public class NdviDao {
	
//	@Autowired
//	private JdbcTemplate jdbcTemplate;
	
//	public int getNdviData(int caseId,int year,int week) {
//		String sql = "SELECT Ndvi,year(`date`) as Year,week(`date`) as Week FROM gstm_ndvi.kml_timeseries where CaseID = ?1 and week(`date`) = date(?) and year(`date`) = date(?)";
//		Object[] params = { caseId,year,week };
//		return jdbcTemplate.queryForObject(sql, params, Integer.class);
//	}// getNdviData
	

//	public Ndvi getNdviData(int caseId, int year,int week) {
//
//		String sql = "SELECT Ndvi,year(`date`) as Year,week(`date`) as Week FROM gstm_ndvi.kml_timeseries where CaseID = ?1 and week(`date`) = date(?) and year(`date`) = date(?)";
//
//		List<Ndvi> ndviList = jdbcTemplate.query(sql,
//				new RowMapper<Ndvi>() {
//
//					@Override
//					public Ndvi mapRow(ResultSet rs, int rowNum) throws SQLException {
//						Ndvi ndviObject = new Ndvi();
//						ndviObject.setSuccess(rs.getBoolean("Success"));
//						ndviObject.setFileUrl(rs.getString("FileUrl"));
//						ndviObject.setYear(rs.getDate("Year"));
//						ndviObject.setWeek(rs.getDate("Week"));
//						
//						return ndviObject;
//
//					}
//
//				});
//
//		return ndviList.get(0);
//	}//getNdviData
	
}
