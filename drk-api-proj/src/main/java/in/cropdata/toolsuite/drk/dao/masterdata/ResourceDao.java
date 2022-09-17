package in.cropdata.toolsuite.drk.dao.masterdata;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import in.cropdata.toolsuite.drk.repository.tileassignment.DrkrishSourceRepository;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import in.cropdata.toolsuite.drk.constants.ErrorConstants;
import in.cropdata.toolsuite.drk.dto.PmpDiscoveryDTO;
import in.cropdata.toolsuite.drk.dto.PmpResponse;
import in.cropdata.toolsuite.drk.dto.QualitySpread;
import in.cropdata.toolsuite.drk.dto.QualitySpreadDTO;
import in.cropdata.toolsuite.drk.exceptions.DataNotFoundException;
import in.cropdata.toolsuite.drk.exceptions.DbException;

@Repository
public class ResourceDao {

	private static final Logger logger = LoggerFactory.getLogger(ResourceDao.class);
	private static DecimalFormat df2 = new DecimalFormat("#.##");
	@Autowired
	JdbcTemplate masterDataJdbcTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private DrkrishSourceRepository drkrishSourceRepository;

	public PmpResponse getPmpDescovery() {
		PmpResponse pmp = new PmpResponse();
//		String sql = "SELECT RegionID as regionId,CommodityID as commodityId,VarietyID as varietyId,BandID as bandId,Pmp as pmp,Status as status FROM cdt_master_data.pricing_master_mbep where Status IN ('Active','Deleted','Inactive') and CreatedAt < DATE_SUB(NOW(), INTERVAL 24 HOUR) or UpdatedAt < DATE_SUB(NOW(), INTERVAL 24 HOUR); ";

		String sql = "SELECT pmn.RegionID as regionId,pmn.CommodityID as commodityId,pmn.VarietyID as varietyId,pmn.BandID as bandId,ab.Name as band, pmn.Pmp as pmp,pmn.Status as status FROM cdt_master_data.pricing_master_mbep pmn\n"
				+ "inner join cdt_master_data.agri_band ab on ab.ID= pmn.BandID\n"
				+ "where pmn.Status IN ('Active','Deleted','Inactive') and pmn.CreatedAt > DATE_SUB(NOW(), INTERVAL 24 HOUR) or pmn.UpdatedAt > DATE_SUB(NOW(), INTERVAL 24 HOUR);";
		List<PmpDiscoveryDTO> query = this.masterDataJdbcTemplate.query(sql,
				(rs, rowNum) -> new PmpDiscoveryDTO(rs.getInt("regionId"), rs.getInt("commodityId"),
						rs.getInt("varietyId"), rs.getInt("bandId"), rs.getString("band"), rs.getString("pmp"),
						rs.getString("status")));

//		String pmpDiscovery = getDataForSQL(sql);
		pmp.setSuccess(true);
		pmp.setError("");
		pmp.setMessage("Pmp Discovery");
		pmp.setData(query);
		logger.info("pmp " + pmp);
		return pmp;
	}

	public ResponseEntity<?> getQualitySpread(QualitySpread qualitySpread) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		String date = simpleDateFormat.format(new Date());
		java.util.Date date = simpleDateFormat.parse(qualitySpread.getDate());
		long unixTime = date.getTime() / 1000L;
		String sql = "select pmp from cdt_master_data.pricing_pmp_log where RegionID =" + qualitySpread.getRegionId()
				+ " and CommodityID =" + qualitySpread.getCommodityId() + " and VarietyID = "
				+ qualitySpread.getVarietyId() + " and BandID in(" + qualitySpread.getPrevBandId() + ","
				+ qualitySpread.getCurBandId() + ") and CreatedAt between '" + qualitySpread.getDate()
				+ " 00:00:00' and '" + qualitySpread.getDate() + " 23:59:59'" + " order by CreatedAt DESC limit 2;";

		Map<String, Object> dataMap = new HashMap<>();
		Map<String, Object> mapData = new HashMap<>();
		List<Object> list = new ArrayList<Object>();
		List<Map<String, Object>> queryForList = this.masterDataJdbcTemplate.queryForList(sql);
		if (queryForList.size() == 1 || queryForList.isEmpty()) {
			throw new DataNotFoundException("No Update Found");
		}
		QualitySpreadDTO res = new QualitySpreadDTO();
		res.setSuccess(true);
		res.setError("");
		res.setMessage("QualitySpread");

		for (Map<String, Object> map : queryForList) {
			list.addAll(map.values());

		}

		@SuppressWarnings("unchecked")
		List<String> data = (List<String>) (List<?>) list;
		Double curPmp = (Double) Double.valueOf(data.get(0));
		Double prePmp = (Double) Double.valueOf(data.get(1));
		Double finalResult = curPmp - prePmp;
		System.out.println(df2.format(finalResult));
		dataMap.put("bandPriceSpread", df2.format(finalResult));
		res.setData(dataMap);
		return ResponseEntity.status(HttpStatus.OK).body(res);

	}

	public String getDataForResource(long unixTimestamp, int page, List<Map<String, Object>> apiDetails, String resource) {
		try {
		Integer rpp = 5000;

		page = page > 1 ? ((page -1) * 5000) : 0;

		if(apiDetails!= null && !apiDetails.isEmpty()) {

	        resource = resource.replace("<REGIONID>", String.valueOf(apiDetails.get(0).get("regionID")));
	        resource = resource.replace("<STATECODE>", String.valueOf(apiDetails.get(0).get("StateCode")));
	        resource = resource.replace("<SOURCEID>", String.valueOf(apiDetails.get(0).get("SourceID")));	
		}
		
		resource = resource.replace("<UNIXTIMESTAMP>", String.valueOf(unixTimestamp));
        resource = resource.replace("<ROWSTART>", String.valueOf(page));
        resource = resource.replace("<RECORDOFFSET>", String.valueOf(rpp));
        
		List<Map<String,Object>> queryForList = jdbcTemplate.queryForList(resource);
		
		return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(queryForList);
		
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		
//		String alias = "";
//		String query = "";
//		String source = jdbcTemplate.queryForObject("select Name from cdt_master_data.drkrishi_source where ApiKey = '"+ apiKey +"'", String.class);

//		return getDataForSQL(query.trim());
	}


	public String getDataForAgriOtaResource(long unixTimestamp, int page, List<Map<String, Object>> apiDetails, String resource) {
		try {
		Integer rpp = 500000;

		page = page > 1 ? ((page -1) * 500000) : 0;

		if(apiDetails!= null && !apiDetails.isEmpty()) {

	        resource = resource.replace("<REGIONID>", String.valueOf(apiDetails.get(0).get("regionID")));
	        resource = resource.replace("<STATECODE>", String.valueOf(apiDetails.get(0).get("StateCode")));
	        resource = resource.replace("<SOURCEID>", String.valueOf(apiDetails.get(0).get("SourceID")));
		}

		resource = resource.replace("<UNIXTIMESTAMP>", String.valueOf(unixTimestamp));
        resource = resource.replace("<ROWSTART>", String.valueOf(page));
        resource = resource.replace("<RECORDOFFSET>", String.valueOf(rpp));

		List<Map<String,Object>> queryForList = jdbcTemplate.queryForList(resource);

		return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(queryForList);

		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	public List<Map<String, Object>> isApiKeyExists(String apiKey) {

		String findApiKeyQuery = "SELECT \n"
				+ "	group_concat(distinct(s.ID)) as SourceID,\n"
				+ "    group_concat(distinct(r.RegionID)) as RegionID,\n"
				+ "    group_concat(distinct(r.StateCode)) as StateCode\n"
				+ "FROM cdt_master_data.drkrishi_source as s\n"
				+ "INNER JOIN cdt_master_data.geo_region r on r.SourceID = s.ID\n"
				+ "WHERE ApiKey='" + apiKey + "' limit 1";
		try {
			return masterDataJdbcTemplate.queryForList(findApiKeyQuery);
			
		}catch(EmptyResultDataAccessException e) {
               return new ArrayList<>(); 			
		}
		

	}

	public boolean isApiKeyExistsCDT(String apiKey) {

		String findApiKeyQuery = "select count(*) from cdt_master_data.drkrishi_source ds where ds.ApiKey = '" + apiKey + "' ";
		boolean isApiKeyExists = false;

		Integer count = masterDataJdbcTemplate.queryForObject(findApiKeyQuery, Integer.class);
		if (count != null && count > 0) {
			isApiKeyExists = true;
		}

		return isApiKeyExists;
	}
	
	public Map<String, Object> validateResource(String resouce) {
		Map<String, Object> responseMap = new HashMap<>();
		
		try {
			String query = "select api_select_query\n"
					+ "from cdt_master_data.gstm_sync\n"
					+ "where endpoint_name = '"+resouce+"' limit 1";
			
			responseMap.put("status", true);
			responseMap.put("selectQuery", masterDataJdbcTemplate.queryForObject(query, String.class));
			return responseMap;
		} catch (Exception e) {
			e.printStackTrace();
			responseMap.put("status", false);
			responseMap.put("selectQuery", "");
			responseMap.put("error", "Something Wrong With Resource. Please Try Again Later");
			return responseMap;
		}
	}


	public String errorResponse() {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrorCode(ErrorConstants.INVALID_RESOURCE);
		errorResponse.setError("Invalid resource name");
		ObjectMapper mapper = new ObjectMapper();
		String error = "";
		try {
			error = mapper.writeValueAsString(errorResponse);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return error;
	}

	class ErrorResponse {
		String error;
		String errorCode;

		/**
		 * @return the error
		 */
		public String getError() {
			return error;
		}

		/**
		 * @param error the error to set
		 */
		public void setError(String error) {
			this.error = error;
		}

		/**
		 * @return the errorCode
		 */
		public String getErrorCode() {
			return errorCode;
		}

		/**
		 * @param errorCode the errorCode to set
		 */
		public void setErrorCode(String errorCode) {
			this.errorCode = errorCode;
		}

	}

//	private String getRemedialQuery() {
//		String query = "SELECT ID,BrandName,CompanyID,StressTypeID,AgrchemicalTypeID,Status,RemedialStatus,\n"
//				+ "			GROUP_CONCAT( bioticStressID separator ',') as BioticStressID,\n"
//				+ "			GROUP_CONCAT( StressCommodityID separator ',') as StressCommodityID,\n"
//				+ "			GROUP_CONCAT( BioticStressTypeId separator ',') as BioticStressTypeId,\n"
//				+ "			GROUP_CONCAT( bioticStressName separator ',') as BioticStressName,\n"
//				+ "			GROUP_CONCAT( ScientificName separator ',') as ScientificName,\n"
//				+ "			GROUP_CONCAT( StartPhenophaseID separator ',') as StartPhenophaseID, \n"
//				+ "         GROUP_CONCAT( EndPhenophaseID separator ',') as EndPhenophaseID,\n"
//				+ "			GROUP_CONCAT(distinct agrochemicalID separator ',') as AgrochemicalID,\n"
//				+ "			GROUP_CONCAT(distinct AgrochemicalTypeID separator ',') as AgrochemicalTypeID,\n"
//				+ "			GROUP_CONCAT(distinct agrochemicalName separator ',') as AgrochemicalName,\n"
//				+ "			GROUP_CONCAT(distinct WaitingPeriod separator ',') as WaitingPeriod,\n"
//				+ "			GROUP_CONCAT(distinct CommodityID separator ',') as CommodityIdForAgrochemical,\n"
//				+ "			GROUP_CONCAT(distinct AgroStressTypeID separator ',') as AgroStressTypeID FROM (\n"
//				+ "			SELECT ARM.ID as ID,ARM.BrandName as BrandName,ARM.CompanyID as CompanyID,ARM.StressTypeID,"
//				+ "         ARM.AgrochemicalTypeID as AgrchemicalTypeID,ARM.Status,ARM.RemedialStatus,\n"
//				+ "			ABSS.ID as bioticStressID,ABSS.CommodityID as StressCommodityID,ABSS.StressTypeID as BioticStressTypeId,\n"
//				+ "			ABSS.Name as bioticStressName,ABSS.ScientificName,ABSS.StartPhenophaseID,ABSS.EndPhenophaseID,\n"
//				+ "			AA.ID as agrochemicalID,AA.AgrochemicalTypeID,AA.Name as agrochemicalName,AA.WaitingPeriod,\n"
//				+ "			AA.CommodityID,AA.StressTypeID as AgroStressTypeID FROM agri_remedial_measures ARM\n"
//				+ "			LEFT JOIN agri_remedial_measures_stress ARMS ON (ARM.ID = ARMS.RemedialMeasuresID)\n"
//				+ "			LEFT JOIN agri_remedial_measures_agrochemical ARMA ON (ARM.ID = ARMA.RemedialMeasureID)\n"
//				+ "			LEFT JOIN agri_commodity_stress ABSS ON (ARMS.StressID = ABSS.ID)\n"
//				+ "			LEFT JOIN agri_agrochemical AA ON (ARMA.AgrochemicalID = AA.ID)"
//				+ "         where ARM.Status IN ('Active','Deleted')"
//				+ ") as temp group by ID,BrandName,CompanyID,StressTypeID,agrchemicalTypeID,Status,RemedialStatus";
//
//		return query;
//	}

	private String emptyResponse() {
		return "{}";
	}

	private String getDataForSQL(String query) {
		String resultJson = emptyResponse();

		if (validateQuery(query)) {

//			if (query.contains("FROM agri_remedial_measures ARM")) {
//
//				List<AgriRemedialMeasuresDto> list = getRemedialMeasuresList();
//
//				Gson gson = new Gson();
//				resultJson = gson.toJson(list);
//
//			} else {
			List<Map<String, Object>> ls = masterDataJdbcTemplate.queryForList(query);
			logger.info("Query: {}", query);
			try {
				resultJson = objectMapper.writeValueAsString(ls);
			} catch (JsonProcessingException e) {
				logger.error("Failed to convert ResultSet to JSON", e);
			}
//			}

		} // if query not null and valid
		else {
			logger.info("Query Validation Failed");
		}

		return resultJson;
	}
//
//	private List<AgriRemedialMeasuresInfDto> prepareRemidiealList() {
//
//		List<AgriRemedialMeasuresInfDto> prepareRemedialList = jdbcTemplate.query(getRemedialQuery(),
//				new ResultSetExtractor<List<AgriRemedialMeasuresInfDto>>() {
//					@Override
//					public List<AgriRemedialMeasuresInfDto> extractData(ResultSet rs)
//							throws SQLException, DataAccessException {
//
//						List<AgriRemedialMeasuresInfDto> list = new ArrayList<AgriRemedialMeasuresInfDto>();
//
//						while (rs.next()) {
//
//							AgriRemedialMeasuresInfDto e = new AgriRemedialMeasuresInfDto();
//
//							e.setId(rs.getInt("ID"));
//							e.setBrandName(rs.getString("BrandName"));
//							e.setCompanyId(rs.getInt("CompanyID"));
//							e.setStressTypeId(rs.getInt("StressTypeID"));
//							e.setAgrchemicalTypeId(rs.getInt("AgrchemicalTypeID"));
//							e.setBioticStressId(rs.getString("BioticStressID"));
//							e.setStatus(rs.getString("Status"));
//							e.setRemedialStatus(rs.getString("RemedialStatus"));
//							e.setStressCommodityId(rs.getString("StressCommodityID"));
//							e.setBioticStressTypeId(rs.getString("BioticStressTypeId"));
//							e.setBioticStressName(rs.getString("BioticStressName"));
//							e.setScientificName(rs.getString("ScientificName"));
//							e.setStartPhenophaseId(rs.getString("StartPhenophaseID"));
//							e.setEndPhenophaseId(rs.getString("EndPhenophaseID"));
//							e.setAgrochemicalId(rs.getString("AgrochemicalID"));
//							e.setAgrochemicalTypeId(rs.getString("AgrochemicalTypeID"));
//							e.setAgrochemicalName(rs.getString("AgrochemicalName"));
//							e.setWaitingPeriod(rs.getString("WaitingPeriod"));
//							e.setCommodityIdForAgrochemical(rs.getString("CommodityIdForAgrochemical"));
//							e.setAgroStressTypeId(rs.getString("AgroStressTypeID"));
//
//							list.add(e);
//						}
//						return list;
//					}
//				});
//		return prepareRemedialList;
//
//	}
//
//	private List<AgriRemedialMeasuresDto> getRemedialMeasuresList() {
//
//		List<AgriRemedialMeasuresInfDto> list = prepareRemidiealList();
//
//		List<AgriRemedialMeasuresDto> remedialMeasuresList = new ArrayList<>();
//
//		if (list.size() > 0 && !list.isEmpty()) {
//
//			for (AgriRemedialMeasuresInfDto dto : list) {
//
//				if (dto != null) {
//
//					AgriRemedialMeasuresDto remedialMeasuresDto = new AgriRemedialMeasuresDto();
//
//					remedialMeasuresDto.setId(dto.getId());
//					remedialMeasuresDto.setStateCode(dto.getStateCode());
//					remedialMeasuresDto.setStressTypeId(dto.getStressTypeId());
//					remedialMeasuresDto.setCompanyId(dto.getCompanyId());
//					remedialMeasuresDto.setBrandName(dto.getBrandName());
//					remedialMeasuresDto.setAgrochemicalTypeId(dto.getAgrchemicalTypeId());
//					remedialMeasuresDto.setStatus(dto.getStatus());
//					remedialMeasuresDto.setRemedialStatus(dto.getRemedialStatus());
//
//					// below code for bioticStress
//
//					String[] bioticStressIdArr = {};
//					String[] stressCommodityIdArr = {};
//					String[] bioticStressTypeIdArr = {};
//					String[] bioticStressNameArr = {};
//					String[] scientificNameArr = {};
//					String[] startArr = {};
//					String[] endArr = {};
////					String[] symptonsArr = {};
//
//					if (dto.getBioticStressId() != null) {
//						bioticStressIdArr = dto.getBioticStressId().split(",");
//					}
//					if (dto.getStressCommodityId() != null) {
//						stressCommodityIdArr = dto.getStressCommodityId().split(",");
//					}
//					if (dto.getBioticStressTypeId() != null) {
//						bioticStressTypeIdArr = dto.getBioticStressTypeId().split(",");
//					}
//					if (dto.getBioticStressName() != null) {
//						bioticStressNameArr = dto.getBioticStressName().replace("\n", " ").split(",");
//					}
//					if (dto.getScientificName() != null) {
//						scientificNameArr = dto.getScientificName().replace("\n", " ").split(",");
//					}
//					if (dto.getStartPhenophaseId() != null) {
//						startArr = dto.getStartPhenophaseId().split(",");
//					}
//					if (dto.getEndPhenophaseId() != null) {
//						endArr = dto.getEndPhenophaseId().split(",");
//					}
////					if (dto.getSymptoms() != null) {
////						symptonsArr = dto.getSymptoms().replace("\n", " ").replace("\r", " ").split(",");
////					}
//
//					List<AgriBioticStress> bioticStressList = new ArrayList<>();
//
//					for (int i = 0; i < bioticStressIdArr.length; i++) {
//
//						AgriBioticStress stress = new AgriBioticStress();
//
//						if (bioticStressIdArr[i] != null) {
//							stress.setId(Integer.parseInt(bioticStressIdArr[i]));
//						}
//
//						if (stressCommodityIdArr[i] != null) {
//							stress.setCommodityId(Integer.parseInt(stressCommodityIdArr[i]));
//						}
//						if (bioticStressTypeIdArr[i] != null) {
//							stress.setStressTypeId(Integer.parseInt(bioticStressTypeIdArr[i]));
//						}
//						if (bioticStressNameArr[i] != null) {
//							stress.setName(bioticStressNameArr[i]);
//						}
//						if (scientificNameArr[i] != null) {
//							stress.setScientificName(scientificNameArr[i]);
//						}
//						if (startArr[i] != null) {
//							stress.setStart(Integer.parseInt(startArr[i]));
//						}
//						if (endArr[i] != null) {
//							stress.setEnd(Integer.parseInt(endArr[i]));
//						}
////						if (symptonsArr[i] != null) {
////							stress.setSymptoms(symptonsArr[i]);
////						}
//
//						bioticStressList.add(stress);
//
//					}
//
//					remedialMeasuresDto.setBioticStressList(bioticStressList);
//
//					// below code for agrochemical
//
//					String[] agrochemicalIdArr = {};
//					String[] agrochemicalTypeIdArr = {};
//					String[] agrochemicalNameArr = {};
//					String[] waitingPeriodArr = {};
//					String[] commodityIdArr = {};
//					String[] agroStressTypeIdArr = {};
//
//					if (dto.getAgrochemicalId() != null) {
//						agrochemicalIdArr = dto.getAgrochemicalId().split(",");
//					}
//					if (dto.getAgrochemicalTypeId() != null) {
//						agrochemicalTypeIdArr = dto.getAgrochemicalTypeId().split(",");
//					}
//					if (dto.getAgrochemicalName() != null) {
//						agrochemicalNameArr = dto.getAgrochemicalName().replace("\n", " ").split(",");
//					}
//					if (dto.getWaitingPeriod() != null) {
//						waitingPeriodArr = dto.getWaitingPeriod().split(",");
//					}
//					if (dto.getCommodityIdForAgrochemical() != null) {
//						commodityIdArr = dto.getCommodityIdForAgrochemical().split(",");
//					}
//					if (dto.getAgroStressTypeId() != null) {
//						agroStressTypeIdArr = dto.getAgroStressTypeId().split(",");
//					}
//
//					List<AgriAgrochemicalMaster> agrochemicalList = new ArrayList<>();
//
//					for (int i = 0; i < agrochemicalIdArr.length; i++) {
//
//						AgriAgrochemicalMaster agrochemical = new AgriAgrochemicalMaster();
//
//						if (agrochemicalIdArr[i] != null) {
//							agrochemical.setId(Integer.parseInt(agrochemicalIdArr[i]));
//						}
//						if (agrochemicalTypeIdArr[i] != null) {
//							agrochemical.setAgrochemicalTypeId(Integer.parseInt(agrochemicalTypeIdArr[i]));
//						}
//						if (commodityIdArr[i] != null) {
//							agrochemical.setCommodityId(Integer.parseInt(commodityIdArr[i]));
//						}
//						if (waitingPeriodArr[i] != null) {
//							agrochemical.setWaitingPeriod(Integer.parseInt(waitingPeriodArr[i]));
//						}
//						if (agrochemicalNameArr[i] != null) {
//							agrochemical.setName(agrochemicalNameArr[i]);
//						}
//						if (agroStressTypeIdArr[i] != null) {
//							agrochemical.setStressTypeId(Integer.parseInt(agroStressTypeIdArr[i]));
//						}
//
//						agrochemicalList.add(agrochemical);
//					}
//
//					remedialMeasuresDto.setAgrochemicalList(agrochemicalList);
//
//					remedialMeasuresList.add(remedialMeasuresDto);
//
//				}
//			}
//		}
//
//		return remedialMeasuresList;
//	}

	private boolean validateQuery(String query) {
		if (query != null) {
			if (query.toLowerCase().startsWith("select") && query.contains(",")
					&& query.toLowerCase().contains("from")) {
				return true;
			}
		}
		return false;
	}

	public String getVillageDatabyTehsil(int tehsilCode, long unixTimestamp) {
		String query = "SELECT gv.VillageCode as ID,gv.StateCode,gv.DistrictCode,gv.TehsilCode,gv.PanchayatCode,gv.VillageCode,gv.RegionID,gv.SubRegionID, concat(gv.Name,' - ',gp.Name,' - ',gt.Name) as Name,gv.PIN,gv.Latitude,gv.Longitude,gv.Status \n"
				+ "from geo_village gv\n" + "INNER JOIN geo_tehsil gt ON gv.TehsilCode = gt.TehsilCode\n"
				+ "INNER JOIN geo_panchayat gp ON gv.PanchayatCode = gp.PanchayatCode\n" + " where gv.TehsilCode="
				+ tehsilCode + " AND gv.Status IN ('Active','Deleted') ";// RegionTileID,
		if (unixTimestamp > 0) {
			query += " AND UNIX_TIMESTAMP(CreatedAt) > " + unixTimestamp + " or UNIX_TIMESTAMP(UpdatedAt) > "
					+ unixTimestamp;
		}
		return getDataForSQL(query);
	}

	public String getVillageDatabyDistrict(int districtCode, long unixTimestamp) {
		String query = "SELECT gv.VillageCode as ID,gv.StateCode,gv.DistrictCode,gv.TehsilCode,gv.PanchayatCode,gv.VillageCode,gv.RegionID,gv.SubRegionID, \n"
				+ "		concat(gv.Name,' - ',gp.Name,' - ',gt.Name) as Name,gv.PIN,gv.Latitude,gv.Longitude,gv.Status \n"
				+ " from geo_village gv \n" + " INNER JOIN geo_tehsil gt ON gv.TehsilCode = gt.TehsilCode\n"
				+ " INNER JOIN geo_panchayat gp ON gv.PanchayatCode = gp.PanchayatCode\n" + " where gv.DistrictCode="
				+ districtCode + " AND gv.Status IN ('Active','Deleted') ";// RegionTileID,
		if (unixTimestamp > 0) {
			query += " AND UNIX_TIMESTAMP(CreatedAt) > " + unixTimestamp + " or UNIX_TIMESTAMP(UpdatedAt) > "
					+ unixTimestamp;
		}
		return getDataForSQL(query);
	}

	public String getVillageDatabyPanchayat(int panchayatCode, long unixTimestamp) {
		String query = "SELECT gv.VillageCode as ID,gv.StateCode,gv.DistrictCode,gv.TehsilCode,gv.PanchayatCode,gv.VillageCode,gv.RegionID,gv.SubRegionID, \n"
				+ "		concat(gv.Name,' - ',gp.Name,' - ',gt.Name) as Name,gv.PIN,gv.Latitude,gv.Longitude,gv.Status\n"
				+ " from geo_village gv \n" + " INNER JOIN geo_tehsil gt ON gv.TehsilCode = gt.TehsilCode\n"
				+ " INNER JOIN geo_panchayat gp ON gv.PanchayatCode = gp.PanchayatCode\n" + " where gv.PanchayatCode="
				+ panchayatCode + " AND gv.Status IN ('Active','Deleted') ";// RegionTileID,
		if (unixTimestamp > 0) {
			query += " AND UNIX_TIMESTAMP(CreatedAt) > " + unixTimestamp + " or UNIX_TIMESTAMP(UpdatedAt) > "
					+ unixTimestamp;
		}
		return getDataForSQL(query);
	}

	public String getPanchayatDataByDistrict(int districtCode, long unixTimestamp) {
		String query = "SELECT PanchayatCode as ID,StateCode,DistrictCode,TehsilCode,PanchayatCode,Name,Status from geo_panchayat where DistrictCode="
				+ districtCode + " AND  Status IN ('Active','Deleted') ";
		if (unixTimestamp > 0) {
			query += " AND UNIX_TIMESTAMP(CreatedAt) > " + unixTimestamp + " or UNIX_TIMESTAMP(UpdatedAt) > "
					+ unixTimestamp;
		}
		return getDataForSQL(query);
	}

	public String getPanchayatDataByTehsil(int tehsilCode, long unixTimestamp) {
		String query = "SELECT PanchayatCode as ID,StateCode,DistrictCode,TehsilCode,PanchayatCode,Name,Status from geo_panchayat where TehsilCode="
				+ tehsilCode + " AND  Status IN ('Active','Deleted') ";
		if (unixTimestamp > 0) {
			query += " AND UNIX_TIMESTAMP(CreatedAt) > " + unixTimestamp + " or UNIX_TIMESTAMP(UpdatedAt) > "
					+ unixTimestamp;
		}
		return getDataForSQL(query);
	}

	public boolean isUpdateFound(String table, Long unixTimestamp) {

		boolean _updateFound = false;

		try {
			int isInsert = 0;
			String _SQL = "select count(*) _count from " + table + "  where CreatedAt > from_unixtime('" + unixTimestamp
					+ "') or UpdatedAt >  from_unixtime('" + unixTimestamp + "')";
			System.out.println(_SQL);

			isInsert = masterDataJdbcTemplate.queryForObject(_SQL, Integer.class);

			if (isInsert > 0) {
				_updateFound = true;
			}
		} catch (Exception e) {
			throw new DbException("Unable To find Update " + e.getMessage());
		}
		return _updateFound;
	}

	public boolean isUpdateFound(long unixTimestamp) {

		String[] _tables = { "flipbook_symptoms", "agri_commodity_plant_part" };

		boolean _updateFound = false;
		try {
			for (String _tab : _tables) {
				int isInsert = 0;

				/*
				 * String _SQL = "select count(*) as _count from (select * from " + _tab +
				 * " where CreatedAt > from_unixtime('" + unixTimestamp +
				 * "') or UpdatedAt >  from_unixtime('" + unixTimestamp +
				 * "') limit 200) as t limit 200;";
				 */
				String _SQL = "select count(*) _count from " + _tab + " where CreatedAt > from_unixtime('"
						+ unixTimestamp + "') or UpdatedAt >  from_unixtime('" + unixTimestamp + "')";
				System.out.println(_SQL);
				isInsert = masterDataJdbcTemplate.queryForObject(_SQL, Integer.class);
				logger.info("isInsert " + isInsert);
				if (isInsert > 0) {
					_updateFound = true;
					break;
				}
			}
		} catch (Exception e) {
			throw new DbException("Unable To find Update " + e.getMessage());
		}

		return _updateFound;
	}

	public List<String> getImagesForFlipbookSymptoms() {
		try {

			String _SQL = "SELECT ID,GenericImage FROM flipbook_symptoms where Status = 'Active'";

			List<String> list = masterDataJdbcTemplate.query(_SQL, new RowMapper<String>() {

				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("ID").concat("##")
							.concat(rs.getString("GenericImage") == null ? "null" : rs.getString("GenericImage"));
				}
			});
			return list;
		} catch (Exception e) {
			throw e;
		}
	}

	public List<String> getImagesForCommodityPlantPart() {
		try {

			String _SQL = "SELECT ID,FileUrl FROM agri_commodity_plant_part where Status = 'Active'";

			List<String> list = masterDataJdbcTemplate.query(_SQL, new RowMapper<String>() {

				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("ID").concat("##")
							.concat(rs.getString("FileUrl") == null ? "null" : rs.getString("FileUrl"));
				}
			});
			return list;
		} catch (Exception e) {
			throw e;
		}
	}

	public int getSubRegionIDByVillageCode(int villageCode) {
		String _sql = "select cdtgv.SubRegionID from cdt_master_data.geo_village cdtgv where cdtgv.VillageCode=?;";
		return this.masterDataJdbcTemplate.queryForObject(_sql, new Object[] { villageCode }, Integer.class);
	}

	public int getRegionIdByVillageCode(int villageCode) {
		String sql = "select cdtgv.RegionID from cdt_master_data.geo_village cdtgv where cdtgv.VillageCode=?;";
		return this.masterDataJdbcTemplate.queryForObject(sql, new Object[] { villageCode }, Integer.class);
	}

	public int getStateCodeByVillageCode(int villageCode) {
		String sql = "select StateCode from cdt_master_data.geo_village where VillageCode=?;";
		return this.masterDataJdbcTemplate.queryForObject(sql, new Object[] { villageCode }, Integer.class);
	}

	public List<Map<String,Object>> getGstmSync() {
		String sql ="SELECT gs.id,gs.schema_name,gs.endpoint_name,gs.screen_id,gs.role_id,gs.label_name,gs.url,gs.field_mapping,\n"
				+ "gs.zipping_level,gs.select_query,gs.insert_query,gs.download_in_android,\n"
				+ "gs.status,gs.conversion_function,gs.sync_from_ts,gs.priority FROM cdt_master_data.gstm_sync gs where gs.`system`= 'DRK'";
		return this.masterDataJdbcTemplate.queryForList(sql);
	}

}
