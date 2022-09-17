package in.cropdata.cdtmasterdata.region.dao;

import in.cropdata.cdtmasterdata.model.GeoRegion;
import in.cropdata.cdtmasterdata.region.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class ClusterDao {

	private static final Logger logger = LoggerFactory.getLogger(ClusterDao.class);

	@Autowired
	private JdbcTemplate jdbcTemplet;

	public long getMaxID() {
		String sql = "select ifnull(max(RegionID), 0) from geo_region";
		return jdbcTemplet.queryForObject(sql, Long.class);
	}

	public List<RegionOutputModel> getDetials(String subRegionIds) {
		String sql = "select b.name StateName, group_concat(distinct c.name  SEPARATOR  '^* ') DistrictName, group_concat(distinct a.name SEPARATOR  '^* ') VillageName, "
				+ "group_concat(distinct a.SubregionId SEPARATOR  '^* ') SubregionId, count(distinct b.name ) StateCount,count(distinct c.name) DistrictCount , "
				+ "count(distinct a.subRegionID) TotalSubRegion, count(distinct a.VillageCode20) VillageCount "
				+ "from geo_village a , geo_state b, geo_district c, geo_subregion d where a.SubregionId = d.subRegionID and d.subRegionID in ( "
				+ subRegionIds + " ) "
				+ "and a.statecode = b.statecode and a.districtCode = c.districtcode and b.statecode = c.statecode "
				+ "group by b.name order by b.name ";

		List<RegionModel> subRegionDetails = jdbcTemplet.query(sql,
				(rs, rowNum) -> new RegionModel(rs.getString("StateName"), rs.getString("DistrictName"),
						rs.getString("VillageName"), rs.getString("SubregionId"), rs.getInt("StateCount"),
						rs.getInt("DistrictCount"), rs.getInt("TotalSubRegion"), rs.getInt("VillageCount")));

		List<RegionOutputModel> regionOutputModelList = new ArrayList<>();
		int stateCount = 0;
		int distCount = 0;
		int subregionCount = 0;
		int villageCount = 0;

		for (RegionModel regionModel : subRegionDetails) {
			RegionOutputModel regionOutputModel = new RegionOutputModel();
			regionOutputModel.setState(regionModel.getState());
			regionOutputModel.setDistrict(Arrays.asList(regionModel.getDistrict().split("\\^\\*")));
			regionOutputModel.setVillage(Arrays.asList(
					regionModel.getVillage().substring(0, regionModel.getVillage().length() - 1).split("\\^\\*")));
			regionOutputModel.setSubRegion(Arrays.asList(
					regionModel.getSubRegion().substring(0, regionModel.getSubRegion().length() - 1).split("\\^\\*")));
			stateCount = Integer.valueOf(regionModel.getStateCount()) + stateCount;
			distCount = Integer.valueOf(regionModel.getDistrictCount()) + distCount;
			subregionCount = Integer.valueOf(regionModel.getTotalSubRegion()) + subregionCount;
			villageCount = Integer.valueOf(regionModel.getVillageCount()) + villageCount;
			regionOutputModel.setStateCount(stateCount);
			regionOutputModel.setDistrictCount(distCount);
			regionOutputModel.setTotalSubRegion(subregionCount);
			regionOutputModel.setVillageCount(villageCount);
			regionOutputModelList.add(regionOutputModel);
		}
		return regionOutputModelList;
	}

	public ResponseModel generateRegion(Integer regionId, CreateRegion regions) {
		logger.info("rData respose in {}", regionId);
		logger.info("subregions " + regions.get_subregions());
		ResponseModel response = new ResponseModel();
		try {
		/*	String sql = "select StateCode from  geo_state where name = '" + regions.getStateName() + "'";
			int stateCode = jdbcTemplet.queryForObject(sql, Integer.class);*/
			String sql = "update geo_state set status ='Active' where StateCode= " + regions.getState() + " and name = '"
					+ regions.getState() + "'";
			int count = jdbcTemplet.update(sql);

			if (count >= 0) {
				sql = "insert into geo_region (RegionID,SourceID,StateCode,DistrictCode,Name,RlLatitude,RlLongitude,Description," +
						"Onboarded,Address,Pin,PhoneNumber,ContactPerson,Status) " +
						"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				count = jdbcTemplet.update(sql, regionId,regions.getSourceId() , regions.getState(),regions.getDistrict(),
						regions.get_regionName(),regions.getLat(),regions.getLog(), regions.get_desc(), true,
						regions.getAddress(),regions.getPin(),regions.getMobileNo(),regions.getContactPerson(),"Active");
				if (count > 0) {
					logger.debug("Max RegionID -> {}", regionId);
					sql = "update geo_subregion set RegionID = " + regionId
							+ " , Status =  'Active' where subregionId in ( "
							+ regions.get_subregions().toString().replaceAll("[\\[\\]]", "") + " )";
					count = jdbcTemplet.update(sql);
					if (count > 0) {
						sql = "update geo_village set RegionID = " + regionId
								+ " ,Status = 'Active'  where subregionId in ( "
								+ regions.get_subregions().toString().replaceAll("[\\[\\]]", "") + " )";
						count = jdbcTemplet.update(sql);

						if (count > 0) {
							sql = "select DISTINCT(districtCode) DistrictCode from geo_village where subregionId in ( "
									+ regions.get_subregions().toString().replaceAll("[\\[\\]]", "") + " )";
							List<Integer> distCodes = jdbcTemplet.query(sql, (rs, rowNum) -> rs.getInt("DistrictCode"));
							sql = "update geo_district set status='Active', RegionID = " + regionId
									+ " where districtCode in (" + distCodes.toString().replaceAll("[\\[\\]]", "")
									+ ")";
							count = jdbcTemplet.update(sql);

							sql = "select DISTINCT(tehsilCode) tehsilCode from geo_village where subregionId in ( "
									+ regions.get_subregions().toString().replaceAll("[\\[\\]]", "") + " )";
							List<Integer> tehsilCodes = jdbcTemplet.query(sql, (rs, rowNum) -> rs.getInt("tehsilCode"));
							sql = "update geo_tehsil set status='Active', RegionID = " + regionId
									+ " where tehsilCode in (" + tehsilCodes.toString().replaceAll("[\\[\\]]", "")
									+ ")";
							count = jdbcTemplet.update(sql);

							sql = "select DISTINCT PanchayatCode from geo_village where SubRegionId in ("
									+ regions.get_subregions().toString().replaceAll("[\\[\\]]", "") + " )";
							List<Integer> panchayatCodes = jdbcTemplet.query(sql,
									(rs, rowNum) -> rs.getInt("PanchayatCode"));
							sql = "update geo_panchayat set Status = 'Active', RegionID = " + regionId
									+ " where PanchayatCode in ( "
									+ panchayatCodes.toString().replaceAll("[\\[\\]]", "") + ")";
							count = jdbcTemplet.update(sql);

							logger.debug("Region onboarding done..! {}", count);
							response.setMsg("Region Onboarded");
							response.setSuccess(true);
						}
					} else {
						response.setErrorCode("ERR-CUST-01");
						response.setErrorMsg("Unable to Map Region to Sub-Regions");
						response.setSuccess(false);
					}
				} else {
					response.setErrorCode("ERR-CUST-02");
					response.setErrorMsg("Unable to create Region");
					response.setSuccess(false);
				}

			} else {
				response.setErrorCode("ERR-CUST-03");
				response.setErrorMsg("Invalid State");
				response.setSuccess(false);
			}
		} catch (Exception e) {
			response.setErrorCode("ERR-CUST-04");
			response.setErrorMsg("Unable to create Region " + e.getMessage().split(":")[1]);
			response.setSuccess(false);
		}
		return response;
	}

	public List<ClusterListModel> getList() {
		String sql = "select distinct a.Regionid RegionID, a.Name RegionName, group_concat(distinct b.subregionid) SubRegionId, group_concat(distinct c.name) StateName, "
				+ " group_concat(distinct d.name) DistrictName, group_concat(distinct b.name) VillageName from geo_region a, geo_village b, geo_state c, geo_district d "
				+ " where a.regionID = b.regionID and a.statecode = c.statecode and b.districtcode = d.districtcode group by a.RegionID, a.Name order by a.RegionID ";
		return jdbcTemplet.query(sql,
				(rs, rowNum) -> new ClusterListModel(rs.getInt("RegionID"), rs.getString("RegionName"),
						rs.getString("SubRegionId"), rs.getString("StateName"), rs.getString("DistrictName"),
						rs.getString("VillageName")));
	}

	public ResponseModel regionAction(Map<String, Object> data, List<ClusterCsvModel> csvModelList) {
		logger.info("data  " + data);
		ResponseModel response = new ResponseModel();
		boolean isDone = false;
		if (data.get("_act").equals("del")) {
			/* StateCode */
			String sqlQuery = "select StateCode from geo_region where Status='Active' and  regionID= "
					+ data.get("_id");
			@SuppressWarnings("unused")
			int stateCode = jdbcTemplet.queryForObject(sqlQuery, Integer.class);
			/* DistrictCode */
			sqlQuery = "select distinct DistrictCode distCode from geo_village where status='Active' and regionId= "
					+ data.get("_id");
			List<Integer> distCodes = jdbcTemplet.query(sqlQuery, (rs, rowNum) -> rs.getInt("distCode"));
			/* TehsilCode */
			sqlQuery = "select distinct tehsilCode tehCode from geo_village where status='Active' and regionId= "
					+ data.get("_id");
			List<Integer> tehsilCodes = jdbcTemplet.query(sqlQuery, (rs, rowNum) -> rs.getInt("tehCode"));
			/* PanchayatCode */
			sqlQuery = "select DISTINCT PanchayatCode from geo_panchayat where status='Active' and regionId= "
					+ data.get("_id");
			List<Integer> panchayatCodes = jdbcTemplet.query(sqlQuery, (rs, rowNum) -> rs.getInt("PanchayatCode"));

			sqlQuery = "update geo_subregion set regionID = 0 ,status='Inactive' where regionID =" + data.get("_id");
			int count = jdbcTemplet.update(sqlQuery);
			if (count > 0) {
				sqlQuery = "update geo_village set regionID = 0,status='Inactive' where regionID =" + data.get("_id");
				count = jdbcTemplet.update(sqlQuery);
				if (count > 0) {
					sqlQuery = "delete from geo_region where regionID =" + data.get("_id");
					count = jdbcTemplet.update(sqlQuery);
					if (count > 0) {

						sqlQuery = "update geo_district set status='Inactive' where DistrictCode in ( "
								+ distCodes.toString().replaceAll("[\\[\\]]", "") + ")";
						count = jdbcTemplet.update(sqlQuery);

						sqlQuery = "update geo_tehsil set status='Inactive' where TehsilCode in ( "
								+ tehsilCodes.toString().replaceAll("[\\[\\]]", "") + ")";
						count = jdbcTemplet.update(sqlQuery);

						sqlQuery = "update geo_panchayat set Status='Inactive' where PanchayatCode in ( "
								+ panchayatCodes.toString().replaceAll("[\\[\\]]", "") + ")";
						count = jdbcTemplet.update(sqlQuery);

						isDone = true;
					}
				} else {

					sqlQuery = "update geo_subregion set regionID = " + data.get("_id") + " where regionID ="
							+ data.get("_id");
					count = jdbcTemplet.update(sqlQuery);
				}
			} else {
				sqlQuery = "update geo_subregion set regionID = " + data.get("_id") + " where regionID ="
						+ data.get("_id");
				count = jdbcTemplet.update(sqlQuery);
			}
		} else if (data.get("_act").equals("edit")) {
			logger.debug("data " + data);
			String stateCodeSql = null;
			String mmpkSql = null;
			String rowsSql = null;
			String columnsSql = null;
			String graphMlSql = null;

			String regionDetailsSql = null;
			logger.info("regionName : " + data.get("regionName"));

			logger.info("edit mode" + data.get("imageId") + data.get("fileUrl"));

			if (data.get("regionName") != null) {
				String sql = "update geo_region set Name=? where RegionID= ?;";
				this.jdbcTemplet.update(sql, data.get("regionName"), data.get("_regionId"));
			}

			if (data.get("imageId") != null && data.get("fileUrl") != null) {
				logger.info("inside not null");
				try {
					String sql = "update geo_region set ImageID=?,FileUrl= ? where RegionID=?;";
					logger.debug("Sql for Image -> {}", sql);
					jdbcTemplet.update(sql, data.get("imageId"), data.get("fileUrl"), data.get("_regionId"));
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage());
				}

			}

			if (data.get("_id") != null && !data.get("_id").toString().equalsIgnoreCase("null")) {
				stateCodeSql = "stateCode = " + data.get("_id") + ",";
			} else {
				stateCodeSql = "";
			}
			if (data.get("districtCode") != null || data.get("address") != null
					&& data.get("phoneNumber") != null || data.get("contactPerson") != null || data.get("rlLatitude") != null || data.get("rlLongitude") != null){
				regionDetailsSql = "districtCode = " + data.get("districtCode") + "," + "address = '" + data.get("address") + "'," +
						"pin = " + data.get("pin") + "," +  "phoneNumber = '" + data.get("phoneNumber") + "'," + "contactPerson = '" +
						data.get("contactPerson") + "'," + "RlLatitude = " +  data.get("rlLatitude") + "," + "RlLongitude = " + data.get("rlLongitude") + "," ;
			}
			/** it use to find mmpkId and mmpkUrl it is empty or not */
			if ((data.get("mmpkFileUrl") != null && !data.get("mmpkFileUrl").equals(""))) {
				mmpkSql = "MmpkUrl = '" + data.get("mmpkFileUrl") + "',";
			} else {
				mmpkSql = "";
			}
			if((data.get("graphMlUrl") != null && !data.get("graphMlUrl").equals(""))){
				graphMlSql = "GraphMlUrl = '" + data.get("graphMlUrl") + "',";
			} else {
				graphMlSql ="GraphMlUrl = null,";
			}

			if (data.get("totalRow") != null && !(data.get("totalRow").equals("")) && data.get("totalColumn") != null
					&& !(data.get("totalColumn").equals(""))) {
				rowsSql = " MapRows =" + data.get("totalRow") + ",";
				columnsSql = " MapColumns=" + data.get("totalColumn");
			} else {
				if (mmpkSql.contains(",")) {
					mmpkSql = mmpkSql.replace(",", "");
				}
				rowsSql = "";
				columnsSql = "";
			}
			if (graphMlSql.equals("") && mmpkSql.equals("") && (columnsSql.equals("") && rowsSql.equals(""))) {
				if (stateCodeSql.contains(",")) {
					stateCodeSql = stateCodeSql.replace(",", "");
				}
			}
			int count = 0;
			Integer regionId = (Integer) data.get("_regionId");

			if (data.get("_id") != null || (data.get("mmpkFileUrl") != null && !data.get("mmpkFileUrl").equals(""))
					|| (data.get("totalRow") != null && !(data.get("totalRow").equals("")))
					|| (data.get("totalColumn") != null && !(data.get("totalColumn").equals("")))
					|| csvModelList.size() > 0) {
				String sql = "update geo_region set " + stateCodeSql + regionDetailsSql + graphMlSql + mmpkSql + rowsSql  + columnsSql
						+ "  where regionId= " + data.get("_regionId");
				logger.debug("Sql for region -> {}", sql);
				count = jdbcTemplet.update(sql);

				/*
				 * if (count > 0) { isDone = true; }
				 */
			} else {
				response.setSuccess(false);
				response.setErrorMsg("Please Insert Region details");
				return response;
			}
			/** After read csv file data it is insert in database */

			if (csvModelList != null) {
				String sqlQuery = "select count(RegionID) from  cdt_master_data.geo_subregion_metadata where RegionID = "
						+ data.get("_regionId");

				logger.info("csvList exist sqlQuery -----> {}", sqlQuery);
				Long counts = jdbcTemplet.queryForObject(sqlQuery, Long.class);

				String sqlQueryForRegion = "select count(RegionID) from  cdt_master_data.geo_region_rings where RegionID = "
						+ data.get("_regionId");
				Long regionCount = jdbcTemplet.queryForObject(sqlQueryForRegion, Long.class);
				if (counts > 0) {
					jdbcTemplet.batchUpdate("DELETE FROM cdt_master_data.geo_subregion_metadata WHERE RegionID = "
							+ data.get("_regionId"));

				}
				if (regionCount > 0){
					jdbcTemplet.batchUpdate("DELETE FROM cdt_master_data.geo_region_rings where regionID = "
							+ data.get("_regionId"));
				}
				String csvDataSql = "INSERT INTO cdt_master_data.geo_subregion_metadata"
						+ "(RegionID,SubRegionID,isInRegion,ColNo,RowNo,IsRlOffice,RingNumber) values(?,?,?,?,?,?,?)";

				String csvDataSqlForRegion = "INSERT INTO cdt_master_data.geo_region_rings"
						+ "(RegionID, SubRegionID,Bcss,RingNumber) values(?,?,?,?)";

				for (ClusterCsvModel csvModel : csvModelList)
				{
					count = jdbcTemplet.update(csvDataSql, regionId,csvModel.getSubRegionID(), csvModel.getIsInRegion(), csvModel.getColNo(),
							csvModel.getRowNo(), csvModel.getIsRlOffice(), csvModel.getRingNumber());

					count = jdbcTemplet.update(csvDataSqlForRegion,regionId, csvModel.getSubRegionID(),csvModel.getBcss(),csvModel.getRingNumber());
				}
			}
		}
		response.setSuccess(true);
		response.setErrorMsg("Successfully update Region Details");
		return response;

	}

	public List<StateModel> getState() {
		String sql = "select Distinct name stateName,stateCode from geo_state";
		return jdbcTemplet.query(sql,
				(rs, rowNum) -> new StateModel(rs.getString("stateName"), rs.getInt("stateCode")));
	}

	public List<DistrictModel> getDistrict(){
		String sql = "select Distinct name as districtName, districtCode from geo_district";
		return jdbcTemplet.query(sql,
				(rs, rowNum) -> new DistrictModel(rs.getString("districtName"), rs.getInt("districtCode")));
	}
	public ResponseModel getRegionImage(int id) {
		ResponseModel response = new ResponseModel();
		/* String sql = "select ImageId from geo_region where regionId=" + id; */
		StringBuilder regionData = new StringBuilder();
		String fileSql = "select FileUrl, MmpkUrl,GraphMlUrl,StateCode,DistrictCode,RlLatitude,RlLongitude,Name,Description, " +
				" Address,Pin,PhoneNumber,ContactPerson from geo_region where regionId=" + id;
		GeoRegion geoRegionUrl = jdbcTemplet.queryForObject(fileSql,
				(rs, rowNum) -> new GeoRegion(rs.getString("FileUrl"), rs.getString("MmpkUrl"), rs.getString("GraphMlUrl"),
						rs.getInt("StateCode"), rs.getInt("DistrictCode"), rs.getFloat("RlLatitude"),
						rs.getFloat("RlLongitude"), rs.getString("Name"), rs.getString("Description"),
						rs.getString("Address"), rs.getString("Pin"), rs.getString("PhoneNumber"),
						rs.getString("ContactPerson")));
//		geoRegion.setFileUrl(geoRegionUrl.getFileUrl());
//		geoRegion.setMmpkUrl(geoRegionUrl.getMmpkUrl());
		regionData.append(geoRegionUrl.getFileUrl() + " , " + geoRegionUrl.getMmpkUrl());
		response.setSuccess(true);
//		response.setMsg(String.valueOf(regionData));
		response.setMsg(geoRegionUrl);
		return response;
	}

	public Integer getRegionId(Integer regionId) {
		String sql = "select count(*) from cdt_master_data.geo_region where RegionID='" + regionId + "';";
		return jdbcTemplet.queryForObject(sql, Integer.class);
	}

	public Integer getMappedSubregionWithRegionId(String subRegionIds) {

		String sql = "select count(RegionID) from geo_subregion where SubRegionID in (" + subRegionIds + ") ;";
		logger.info("SQL " + sql);
		return this.jdbcTemplet.queryForObject(sql, Integer.class);

	}

	public Integer getSubregions(String subRegionIds) {

		String sql = "select count(*) from geo_subregion where SubRegionID in (" + subRegionIds + ");";
		logger.info("SQL " + sql);
		Integer subregionsIds = this.jdbcTemplet.queryForObject(sql, Integer.class);
		logger.info("subregionsIds " + subregionsIds);
		return subregionsIds;

	}

	public Integer generateSubRegion(List<RegionSubRegionModel> subregions) {
		Integer count = null;
		String sql = "INSERT INTO cdt_master_data.geo_subregion (SubRegionID,Quadkey) " +
				"values(?,?)";
		for (RegionSubRegionModel subRegion: subregions) {
			count = jdbcTemplet.update(sql,subRegion.getSubRegionId(), subRegion.getQuadKey());
		}
		return count;
	}
	public String getQuadKeys(String quadKeys){
		String sql = "SELECT group_concat(SubRegionID) as subregionIds FROM cdt_master_data.geo_subregion_quadkey_map where Quadkey in  (" + quadKeys + ");";
		logger.info("SQL " + sql);
		return this.jdbcTemplet.queryForObject(sql, String.class);
	}

	public List<RegionSubRegionModel> getSubRegionsByQuadKey(String subregions) {
		String sql = "SELECT SubRegionID as subRegionId , Quadkey as quadKey FROM cdt_master_data.geo_subregion_quadkey_map where Quadkey in (" + subregions + ");";
		logger.info("quadKeys " + sql);
		return this.jdbcTemplet.query(sql, (rs, rowNum) -> new RegionSubRegionModel(rs.getString("subRegionId"),
				rs.getString("quadKey")));
	}

}