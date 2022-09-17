package com.drk.tools.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.drk.tools.config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.drk.tools.exceptions.DbException;
import com.drk.tools.model.ClsoQuestions;
import com.drk.tools.model.DropDownList;
import com.drk.tools.model.ImageDetails;
import com.drk.tools.model.OutputStatus;
import com.drk.tools.model.SymptomsModel;

@Component
public class FlipbookDAO {

	private static final Logger log = LoggerFactory.getLogger(FlipbookDAO.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private AppConfig appConfig;

	public Map<String, List<?>> getchBenchMarkImages(ClsoQuestions data) {
		List<DropDownList> imageIds = null;
		List<SymptomsModel> questions = null;
		Map<String, List<?>> listMap = new HashMap<>();
		StringBuilder clause = new StringBuilder();
		if (data.getStressId() > 0) {
			clause.append(" and StressID = " + data.getStressId());
		}
//		if (data.getStressTypeId() == 2) {
//			clause.append(" and StressStageID = " + data.getStressStageId());
//		}

		String imgIdQuery = "select ID, replace(ifnull(ImageUrl, ' '), '<BASEURL>', '"+ appConfig.blobEnvUrl +"') as ImageUrl from flipbook_benchmark_images where commodityID = "
				+ data.getCommodityId() + " and plantPartID = "
				+ data.getPlantPartId() + " and stressTypeID = " + data.getStressTypeId() + clause;
		log.info("imgId Query -> {}", imgIdQuery);
		imageIds = jdbcTemplate.query(imgIdQuery,
				(rs, rowNum) -> new DropDownList(rs.getInt("ID"), rs.getString("ImageUrl")));
		if (!imageIds.isEmpty()) {
			String questionQuery = "select SymptomID, Symptom, group_concat(replace(ifnull(ImageID, ' '), '<BASEURL>', '"+ appConfig.blobEnvUrl +"')) as ImageID,  replace(ifnull(GenericImage, ' '), '<BASEURL>', '"+ appConfig.blobEnvUrl + "') as GenericImage from ( "
					+ "select a.ID SymptomID, Symptom, ImageID, GenericImage from flipbook_symptoms a, flipbook_symptom_images b "
					+ "where a.Status = 'Active' and b.Status = 'Active' and commodityID = " + data.getCommodityId()
					+ " and plantPartID = " + data.getPlantPartId() + " and stressTypeID = " + data.getStressTypeId()
					+ clause + " and a.ID = b.SymptomID order by SymptomID, ImageID) b group by SymptomID, Symptom ";
			log.info("GenericImage Query >> {}", questionQuery);
			questions = jdbcTemplate.query(questionQuery, (rs, rowNum) -> new SymptomsModel(rs.getInt("SymptomID"),
					rs.getString("Symptom"), rs.getString("ImageID"), rs.getString("GenericImage")));
		}
		listMap.put("_imgID", imageIds);
		listMap.put("_que", questions);

		return listMap;
	}

	public OutputStatus tagData(Map<String, String> data, String ids) {
		int count = 0;
		int flipbookSymptomId = 0;
		OutputStatus outputStatus = new OutputStatus();
		String clause = "";
//		if (data.get("stressTypeId").equals("5")) {
//			clause = " and StressStageID = " + data.get("StressStageID");
//		}

		String addQue = "insert into flipbook_symptoms (CommodityID, PlantPartID, StressTypeID, StressID, Symptom, GenericImage) "
				+ "values(?,?,?,?,?,?)";
		try {
			String imageUrl = (ids.split(",")[0]).replaceAll("'", "");

			imageUrl = imageUrl.substring(imageUrl.indexOf("/flipbook"));

			StringBuilder updateImageUrl = new StringBuilder(imageUrl);
			if (updateImageUrl.length()>0) {
				updateImageUrl.insert(0, "<BASEURL>");
			}

			count = jdbcTemplate.update(addQue, data.get("commodityId"),
					data.get("plantPartId"), data.get("stressTypeId"), data.get("stressId"),
					data.get("symptom"), updateImageUrl);

			if (count == 1) {
				String symptomToImage = "select max(Id) from flipbook_symptoms where CommodityID = "
						+ data.get("commodityId")
						+ " and StressID = " + data.get("stressId") + clause + " and PlantPartID = "
						+ data.get("plantPartId");
				log.info("Inside Tag {}", symptomToImage);

				flipbookSymptomId = jdbcTemplate.queryForObject(symptomToImage, Integer.class);

				if (flipbookSymptomId >= 1) {
					String addImage = "insert into flipbook_symptom_images (SymptomID, ImageID) values (?,?)";
					String[] images = ids.split(",");
					for (int j = 0; j < images.length; j++) {

						String image = images[j].replaceAll("[']", "");

						image = image.substring(image.indexOf("/flipbook"));

						StringBuilder updatedImage = new StringBuilder(image);
						if (updatedImage.length()>0) {
							updatedImage.insert(0, "<BASEURL>");
						}

						count = jdbcTemplate.update(addImage, flipbookSymptomId, updatedImage);
					}
					log.info("No of rows updated -> {}", count);
				}

				String tagIdQuery = "update flipbook_benchmark_images set isTagged = true where imageID in ( " + ids
						+ " )";
				jdbcTemplate.execute(tagIdQuery);
				outputStatus.setErrCode(" ");
				outputStatus.setStatus(true);
				outputStatus.setMsg("Image Tagging Done");
			}
		} catch (Exception ex) {
			log.error("Error in tagData -> {}", ex.getMessage());
			try {
				String rollBackQuery = "update flipbook_symptoms set Status='Deleted' where CommodityID = ? and PlantPartID = ?\n" +
						"and StressTypeID = ? and StressID = ? and Symptom = ?";
				jdbcTemplate.update(rollBackQuery, data.get("commodityId"),
						data.get("plantPartId"), data.get("stressTypeId"), data.get("stressId"), data.get("symptom"));

				rollBackQuery = "update flipbook_symptom_images set Status='Deleted' where SymptomID = ? and ImageID in ( " + ids + ")";
				int ecnt = jdbcTemplate.update(rollBackQuery, flipbookSymptomId);
				rollBackQuery = "update flipbook_benchmark_images set isTagged = false where imageID in ( " + ids
						+ " )";
				ecnt = jdbcTemplate.update(rollBackQuery);
				log.info("No of rows updated for rollback -> {}", ecnt);
			} catch (Exception e1) {
				log.error("Error while rollback -> ", e1);
			}
			outputStatus.setErrCode(ex.getMessage());
			outputStatus.setStatus(false);
			outputStatus.setMsg("Failed to Tag Image!");
		}

		return outputStatus;
	}

	public OutputStatus tagImageToSymptom(Map<String, String> data, String ids) {
		int count = 0;
		int symptomId = 0;
		OutputStatus opStatus = new OutputStatus();
		try {
			String symptomExists = "select count(Id) id from flipbook_symptoms where id = " + data.get("_symptomID")
					+ " and Symptom= '" + data.get("_symptom") + "'";
			symptomId = jdbcTemplate.queryForObject(symptomExists, Integer.class);
			if (symptomId > 0) {
				String addImage = "insert into flipbook_symptom_images (SymptomID, ImageID) values (?,?)";
				String[] images = ids.split(",");
				for (int j = 0; j < images.length; j++) {
					count = jdbcTemplate.update(addImage, data.get("_symptomID"), images[j].replaceAll("[']", ""));
				}
				log.info("No of rows updated -> {}", count);
			}
			String tagIdQuery = "update flipbook_benchmark_images set isTagged = true where imageID in ( " + ids + " )";
			jdbcTemplate.execute(tagIdQuery);
			opStatus.setErrCode(" ");
			opStatus.setStatus(true);
			opStatus.setMsg("Image Tagging Done");
		} catch (Exception e) {
			String rollBackQuery = "update flipbook_symptom_images set Status = 'Deleted' where SymptomID = ? and ImageID in ( " + ids + ")";
			try {
				int ecnt = jdbcTemplate.update(rollBackQuery, symptomId);
				rollBackQuery = "update flipbook_benchmark_images set isTagged = false where imageID in ( " + ids
						+ " )";
				ecnt = jdbcTemplate.update(rollBackQuery);
				log.info("No of rows updated for rollback -> {}", ecnt);
			} catch (Exception re) {
				log.error("Error in Rollback -> {}", re.getMessage());
			}
			opStatus.setErrCode(e.getMessage());
			opStatus.setStatus(false);
			opStatus.setMsg("Failed to Tag Image!");
		}
		return opStatus;
	}

	public OutputStatus editSymptom(Map<String, String> data) {
		int count = 0;
		int symptomId = 0;
		OutputStatus opStatus = new OutputStatus();
		try {
			String symptomExists = "select count(Id) id from flipbook_symptoms where id = " + data.get("_symptomID");
			symptomId = jdbcTemplate.queryForObject(symptomExists, Integer.class);
			if (symptomId > 0) {
				String addImage = "update flipbook_symptoms set Symptom =? where ID =?";
				count = jdbcTemplate.update(addImage, data.get("symptomDesc"), data.get("_symptomID"));
				log.info("No of rows updated -> {}", count);
				opStatus.setMessage("Symptom Updated Successfully");
				opStatus.setStatus(true);
			}
		} catch (Exception e) {
			opStatus.setError(e.getMessage());
			opStatus.setStatus(false);
			opStatus.setMessage("Failed to Update Symptom!");
		}
		return opStatus;
	}

	public boolean unTagImage(String symptomId, String imgId) {
		boolean isDelete = false;
		try {
			int count = jdbcTemplate.update("update flipbook_symptom_images set Status = 'Deleted' where SymptomID = ? and ImageID = ?",
					symptomId, imgId);
			if (count > 0) {
				count = jdbcTemplate.update("update flipbook_benchmark_images set isTagged = false where ImageID = ?",
						imgId);
				if (count > 0) {
					isDelete = true;
				}
			}
		} catch (Exception e) {
			log.error("Error in untaging Image -> {}", e.getMessage());
		}
		return isDelete;
	}

	public boolean removeSypmtom(String symptomId) {
		boolean isDelete = false;
		try {
			int count = jdbcTemplate.update("update flipbook_symptoms set Status = 'Deleted' where ID = ?", symptomId);
			if (count > 0) {
				count = jdbcTemplate.update("update flipbook_symptom_images set Status = 'Deleted' where SymptomID = ?", symptomId);
				if (count > 0) {
					isDelete = true;
				}
			}
		} catch (Exception e) {
			log.error("Error in Sysmptom Deletion -> {}", e.getMessage());
		}
		return isDelete;
	}

	public boolean isGeneric(String symptomId, String imageName) {
		boolean isUpdated = false;
		String sql = "Update flipbook_symptoms set GenericImage = ? where id = ? ";
		int count = jdbcTemplate.update(sql, imageName, symptomId);
		if (count > 0) {
			isUpdated = true;
		}

		return isUpdated;
	}

	public Map<String, List<DropDownList>> getList() {
		Map<String, List<DropDownList>> dropDownListMap = new HashMap<>();
		String commodityQuery = "select ac.Name as CommodityName, fbi.CommodityID as ID from cdt_master_data.flipbook_benchmark_images fbi\n" +
				"inner join agri_commodity ac on fbi.CommodityID=ac.ID where ac.Status = 'Active'\n" +
				"group by fbi.CommodityID order by ac.Name";
		try {
			log.info("Commodity Query -> {}", commodityQuery);
			List<DropDownList> commodityList = jdbcTemplate.query(commodityQuery,
					(rs, rowNum) -> new DropDownList(rs.getInt("ID"), rs.getString("CommodityName")));
			dropDownListMap.put("_cmd", commodityList);

		} catch (Exception e) {
			log.error("Error in getList -> {}", e.getMessage());
		}
		return dropDownListMap;
	}

	public Map<String, List<DropDownList>> getDropdownData(int commodityId, int phenophaseId, int plantPartId,
			int stressTypeId, int stressId, int flag) {
		Map<String, List<DropDownList>> dropDownListMap = new HashMap<>();
		try {
			/*if (flag == 1) {
				String phenophase = "select fbi.PhenophaseID as ID,ap.Name as PhenophaseName from cdt_master_data.flipbook_benchmark_images fbi\n" +
						"inner join cdt_master_data.agri_phenophase ap on fbi.PhenophaseID=ap.ID\n" +
						"where fbi.CommodityID="+commodityId+" and ap.Status = 'Active'  group by fbi.PhenophaseID order by ap.Name";
				log.info("Phenophase Query -> {}", phenophase);
				List<DropDownList> phenophaseList = jdbcTemplate.query(phenophase,
						(rs, rowNum) -> new DropDownList(rs.getInt("ID"), rs.getString("PhenophaseName")));
				dropDownListMap.put("_phn", phenophaseList);

			} else */if (flag == 1 /*&& stressTypeId != 4*/) {
				String plantPart = "select fbi.PlantPartID as ID,am.Name as PlantPartName from cdt_master_data.flipbook_benchmark_images fbi\n" +
						"inner join agri_commodity_plant_part app on app.CommodityID=fbi.CommodityID \n" +
						"inner join agri_plant_part am on fbi.PlantPartID=am.ID\n" +
						"where fbi.CommodityID="+commodityId+" and am.Status = 'Active'\n" +
						"group by  fbi.PlantPartID  order by am.Name";
				log.info("PlantPart Query -> {}", plantPart);
				List<DropDownList> plantPartList = jdbcTemplate.query(plantPart,
						(rs, rowNum) -> new DropDownList(rs.getInt("ID"), rs.getString("PlantPartName")));
				dropDownListMap.put("_pltp", plantPartList);

			} else if (flag == 2) {
//				String clause = " and st.ID in (4) ";
//				if (plantPartId != 45) {
//					clause = " and st.ID not in (4) ";
//				}
				String stressType =  "select fbi.StressTypeID as ID, ast.Name as StressType from cdt_master_data.flipbook_benchmark_images fbi\n" +
						"inner join agri_stress_type ast on ast.ID=fbi.StressTypeID\n" +
						"where fbi.CommodityID="+commodityId+" and fbi.PlantPartID="+plantPartId+" and ast.Status = 'Active' \n" +
						"group by  fbi.StressTypeID order by ast.Name";
				log.info("StressType Query -> {}", stressType);
				List<DropDownList> stressTypeList = jdbcTemplate.query(stressType,
						(rs, rowNum) -> new DropDownList(rs.getInt("ID"), rs.getString("stressType")));
				dropDownListMap.put("_stp", stressTypeList);

			} else if (flag == 3) {
				String stress = "select fbi.StressID as ID, ass.Name as Stress from cdt_master_data.flipbook_benchmark_images fbi\n" +
						"inner join agri_stress ass on fbi.StressID=ass.ID and ass.StressTypeID=fbi.StressTypeID \n" +
						"where fbi.CommodityID="+commodityId+" and PlantPartID="+plantPartId+" and fbi.StressTypeID="+stressTypeId+"\n" +
						"and ass.Status = 'Active' group by  fbi.StressID order by ass.Name";
				log.info("Stress Query -> {}", stress);
				List<DropDownList> stressStageList = jdbcTemplate.query(stress,
						(rs, rowNum) -> new DropDownList(rs.getInt("ID"), rs.getString("Stress")));
				dropDownListMap.put("_str", stressStageList);

			} /*else if (flag == 5) {
				String stressStage = "select distinct (agri_stage.Name) StressStage, agri_stage.ID from agri_stage agri_stage\n" +
						"inner join agri_commodity_stress_stage acss on(acss.StageID = agri_stage.ID)\n" +
						"inner join agri_stress  agri_stress on(agri_stress.ID = acss.StressID) where acss.CommodityID =" + commodityId + " and find_in_set(" + phenophaseId
						+ " , acss.Phenophases) and agri_stress.StressTypeId = " + stressTypeId + " and acss.StressID = " + stressId
						+ " and acss.Status = 'Active' order by agri_stage.name";
				log.info("StressStage Query -> {}", stressStage);
				List<DropDownList> stressStageList = jdbcTemplate.query(stressStage,
						(rs, rowNum) -> new DropDownList(rs.getInt("ID"), rs.getString("StressStage")));
				dropDownListMap.put("_strStg", stressStageList);
			}*/
		} catch (Exception ex) {
			log.error("Error while getting dropdown data -> {}", ex.getMessage());
		}

		return dropDownListMap;
	}

	/**
	 * This method is used to check updates done in last 24 hours (if any).
	 * 
	 * @param tableName the table name in which updates has to be checked
	 * 
	 * @return the boolean value {@code true} if update found {@code false}
	 *         otherwise
	 * 
	 * @author PranaySK
	 */
	public boolean isImageUpdateFound(String tableName) {
		boolean isUpdateFound = false;
		try {
			int updates = 0;
			String countQuery = "select count(*) updates from " + tableName
					+ " where CreatedAt > DATE_SUB(CURDATE(), INTERVAL 1 DAY) OR UpdatedAt > DATE_SUB(CURDATE(), INTERVAL 1 DAY)";
			log.info("countQuery -> {}", countQuery);
			updates = jdbcTemplate.queryForObject(countQuery, Integer.class);
			if (updates > 0) {
				log.info("No of files updated in last 24 hours -> {}", updates);
				isUpdateFound = true;
			}

		} catch (DataAccessException daex) {
			throw new DbException("Unable to find image updates due to -> " + daex.getMessage());
		}

		return isUpdateFound;
	}

	/**
	 * This method is used to get the updated image urls from the
	 * {@code flipbook_symptoms} table.
	 * 
	 * @param flag the flag to decide whether all data or last 24 hour data needs to
	 *             be fetched. {@value 1} fetch last 24 hours data {@value 0} fetch
	 *             all data
	 * 
	 * @return the list of {@link ImageDetails}
	 * 
	 * @author PranaySK
	 */
	public List<ImageDetails> getSymptomImages(int flag, int stateCode) {
		String clause = "";
		/** To get the updated image details from last 24 hours */
		if (flag == 1) {
			clause = " and (fs.CreatedAt > DATE_SUB(CURDATE(), INTERVAL 1 DAY) OR fs.UpdatedAt > DATE_SUB(CURDATE(), INTERVAL 1 DAY)) ";
		}
		String imgQuery = "select fs.ID, replace(ifnull(fs.GenericImage, ' '), '<BASEURL>', '"+ appConfig.blobEnvUrl +"') as ImageUrl\n" +
				"from flipbook_symptoms fs\n" +
				"    inner join zonal_commodity zc on zc.CommodityID = fs.CommodityID\n" +
				"    inner join geo_acz ga on ga.ID = zc.AczID where fs.GenericImage is not NULL and ga.StateCode = "+stateCode+ clause +"\n" +
				"group by fs.ID, fs.GenericImage order by fs.id";
		log.info("img Query -> {}", imgQuery);
		return jdbcTemplate.query(imgQuery,
				(rs, rowNum) -> new ImageDetails(rs.getInt("ID"), rs.getString("ImageUrl")));
	}


	/**
	 * This method is used to get the all for flipbook updated image urls from the
	 * {@code flipbook_symptoms} table.
	 *
	 * @return the list of {@link ImageDetails}
	 *
	 * @author RinkeshKM
	 */
	public List<ImageDetails> getAllFlipbookSymptomImages() {

		String imgQuery = "select fs.ID, replace(ifnull(fs.GenericImage, ' '), '<BASEURL>', '"+ appConfig.blobEnvUrl +"') as ImageUrl\n" +
				"from flipbook_symptoms fs\n" +
				"group by fs.ID, fs.GenericImage order by fs.id";
		log.info("img Query -> {}", imgQuery);
		return jdbcTemplate.query(imgQuery,
				(rs, rowNum) -> new ImageDetails(rs.getInt("ID"), rs.getString("ImageUrl")));
	}

	/**
	 * This method is used to get region ids form regional_commodity table
	 * {@code regional_commodity} table.
	 *
	 * @return the list of {@link List<Integer>} RegionIds
	 *
	 * @author RinkeshKM
	 */
	public List<Integer> getRegionalID() {

		String regionQuery = "select distinct RegionID from cdt_master_data.regional_commodity order by RegionID asc" ;
		log.info("region Query -> {}", regionQuery);
		return jdbcTemplate.queryForList(regionQuery, Integer.class);
	}

	/**
	 * This method is used to get region ids form geo_acz table
	 * {@code geo_acz} table.
	 *
	 * @return the list of {@link List<Integer>} StateCodes
	 *
	 * @author RinkeshKM
	 */
	public List<Integer> getStateID() {

		String regionQuery = "select distinct StateCode from cdt_master_data.geo_acz order by StateCode asc" ;
		log.info("region Query -> {}", regionQuery);
		return jdbcTemplate.queryForList(regionQuery, Integer.class);
	}

	/**
	 * This method is used to get the updated image urls from the
	 * {@code agri_commodity_plant_part} table.
	 * 
	 * @param flag the flag to decide whether all data or last 24 hour data needs to
	 *             be fetched. {@value 1} fetch last 24 hours data {@value 0} fetch
	 *             all data
	 * 
	 * @return the list of {@link ImageDetails}
	 * 
	 * @author PranaySK
	 */
	public List<ImageDetails> getCommPlantPartImages(int flag, int stateCode) {
		String clause = "";
		/** To get the updated image details from last 24 hours */
		if (flag == 1) {
			clause = " and (acpp.CreatedAt > DATE_SUB(CURDATE(), INTERVAL 1 DAY) OR acpp.UpdatedAt > DATE_SUB(CURDATE(), INTERVAL 1 DAY)) ";
		}
		String imgQuery = "select acpp.ID, replace(ifnull(acpp.FileUrl, ' '), '<BASEURL>', '"+ appConfig.blobEnvUrl +"') as ImageUrl from agri_commodity_plant_part acpp \n" +
				"inner join zonal_commodity zc on zc.CommodityID = acpp.CommodityID\n" +
				"inner join geo_acz ga on ga.ID = zc.AczID where acpp.FileUrl is not NULL and ga.StateCode = "+stateCode + clause + " group by acpp.ID, acpp.FileUrl order by acpp.ID";
		log.info("img Query -> {}", imgQuery);
		return jdbcTemplate.query(imgQuery,
				(rs, rowNum) -> new ImageDetails(rs.getInt("ID"), rs.getString("ImageUrl")));
	}

}
