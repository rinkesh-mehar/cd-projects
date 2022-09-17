package com.drk.tools.repository;

import com.drk.tools.model.BmImageMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository class to fetch data from DB.
 * 
 * @author PranaySK
 * @since 1.0
 */

@Repository
public class BmImageRepository {

	private static final Logger logger = LoggerFactory.getLogger(BmImageRepository.class);

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	/**
	 * This method is used to get <b>Commodity Id</b>.
	 * 
	 * @param commodityName the <code>commodityName</code>.
	 * 
	 * @return <code>commodityId</code> the response as commodityId in
	 *         <code>Integer</code>.
	 * 
	 * @throws DataAccessException
	 */
	public Integer getCommodityId(String commodityName) {
		Integer commodityId = null;
		try {
			MapSqlParameterSource sqlParams = new MapSqlParameterSource();
			sqlParams.addValue("commodityName", commodityName);

			String commodityQuery = "select agc.ID from agri_commodity agc where agc.Name = :commodityName ";

			commodityId = namedParameterJdbcTemplate.queryForObject(commodityQuery, sqlParams, Integer.class);

		} catch (DataAccessException ex) {
			logger.error("Error while getting CommodityId -> {}", ex.getMessage());
		}
		return commodityId;
	}

	/**
	 * This method is used to get <b>Commodity Id</b>.
	 * 
	 * @param phenophaseName the <code>phenophaseName</code>.
	 * 
	 * @return <code>phenophaseId</code> the response as phenophaseId in
	 *         <code>Integer</code>.
	 * 
	 * @throws DataAccessException
	 */
	public Integer getPhenophaseId(String phenophaseName) {
		Integer phenophaseId = null;
		try {
			MapSqlParameterSource sqlParams = new MapSqlParameterSource();
			sqlParams.addValue("phenophaseName", phenophaseName);

			String phenophaseQuery = "select agp.ID from agri_phenophase agp where agp.Name = :phenophaseName ";

			phenophaseId = namedParameterJdbcTemplate.queryForObject(phenophaseQuery, sqlParams, Integer.class);

		} catch (DataAccessException ex) {
			logger.error("Error while getting PhenophaseId -> {} -> Phenophase name is -> {}", ex.getMessage(), phenophaseName);
		}
		return phenophaseId;
	}

	/**
	 * This method is used to get <b>Plant Part Id</b>.
	 * 
	 * @param plantPartName the <code>Plant Part Name</code>.
	 * 
	 * @return <code>plantPartId</code> the response as plantPartId in
	 *         <code>Integer</code>.
	 * 
	 * @throws DataAccessException
	 */
	public Integer getPlantPartId(String plantPartName) {
		Integer plantPartId = null;
		try {
			MapSqlParameterSource sqlParams = new MapSqlParameterSource();
			sqlParams.addValue("plantPartName", plantPartName);

			String plantPartQuery = "select agpp.ID from agri_plant_part agpp where agpp.Name = :plantPartName ";

			plantPartId = namedParameterJdbcTemplate.queryForObject(plantPartQuery, sqlParams, Integer.class);

		} catch (DataAccessException ex) {
			logger.error("Error while getting PlantPartId -> {}  plantPartName not exist -> {}", ex.getMessage(), plantPartName);
		}
		return plantPartId;
	}

	/**
	 * This method is used to get <b>Stress Type Id</b>.
	 * 
	 * @param stressTypeName the <code>Stress Type Name</code>.
	 * 
	 * @return <code>stressTypeId</code> the response as stressTypeId in
	 *         <code>Integer</code>.
	 * 
	 * @throws DataAccessException
	 */
	public Integer getStressTypeId(String stressTypeName) {
		Integer stressTypeId = null;
		try {
			MapSqlParameterSource sqlParams = new MapSqlParameterSource();
			sqlParams.addValue("stressTypeName", stressTypeName);

			String stressTypeQuery = "select agst.ID from agri_stress_type agst where agst.Name = :stressTypeName ";

			stressTypeId = namedParameterJdbcTemplate.queryForObject(stressTypeQuery, sqlParams, Integer.class);

		} catch (DataAccessException ex) {
			logger.error("Error while getting StressType Id -> {}", ex.getMessage());
		}
		return stressTypeId;
	}

	/**
	 * This method is used to get <b>Stress Id</b>.
	 * 
	 * @param stressName   the <code>stressName</code>
	 * @param commodityId  the <code>commodityId</code>
	 * @param stressTypeId the <code>stressTypeId</code>
	 * 
	 * @return <code>stressId</code> the response as stressId in
	 *         <code>Integer</code>.
	 * 
	 * @throws DataAccessException
	 */
	public Integer getStressId(String stressName, Integer commodityId, Integer stressTypeId) {
		Integer stressId = null;
		try {
			MapSqlParameterSource sqlParams = new MapSqlParameterSource();
			sqlParams.addValue("stressName", stressName);
			sqlParams.addValue("commodityId", commodityId);
			/* sqlParams.addValue("stressTypeId", stressTypeId); */

			String stressQuery = "select ass.ID from agri_commodity_stress acs \n" +
					"inner join agri_stress ass on (ass.ID = acs.StressID) where ass.Name = :stressName "
					+ " and acs.CommodityID = :commodityId ";
			/* + "and ags.StressTypeID = :stressTypeId "; */

			logger.info("query for fetching stress id is -> select ass.ID from agri_commodity_stress acs\n" +
					"inner join agri_stress ass on (ass.ID = acs.StressID) where ass.Name = '{}'\n" +
					"and acs.CommodityID = {};" , stressName, commodityId);

			stressId = namedParameterJdbcTemplate.queryForObject(stressQuery, sqlParams, Integer.class);

		} catch (DataAccessException ex) {
			logger.error("Error while getting Stress Id -> {} stress name is -> {}", ex.getMessage(), stressName);
		}
		return stressId;
	}

	/**
	 * This method is used to get <b>Stress Stage Id</b>.
	 * 
	 * @param stressStageName the <code>stressStageName</code>
	 * @param commodityId     the <code>commodityId</code>
	 * @param stressTypeId    the <code>stressTypeId</code>
	 * @param stressId        the <code>stressId</code>
	 * 
	 * @return <code>stressStageId</code> the response as stressStageId in
	 *         <code>Integer</code>.
	 * 
	 * @throws DataAccessException
	 */
//	public Integer getStressStageId(String stressStageName, Integer commodityId, Integer stressTypeId,
//			Integer stressId) {
//		Integer stressStageId = null;
//		try {
//			MapSqlParameterSource sqlParams = new MapSqlParameterSource();
//			sqlParams.addValue("stressStageName", stressStageName);
//			sqlParams.addValue("commodityId", commodityId);
//			sqlParams.addValue("stressId", stressId);
//			/*
//			 * sqlParams.addValue("stressTypeId", stressTypeId);
//			 */
//
//			String stressStageQuery = "select distinct ass.ID from agri_stage ass\n" +
//					"              inner join agri_commodity_stress_stage acss on(acss.StageID = ass.ID)\n" +
//					"              inner join agri_stress  ast on(ast.ID = acss.StressID)\n" +
//					"where ass.Name = :stressStageName and acss.CommodityID = :commodityId and acss.StressID = :stressId";
//			/* + "and agss.StressTypeID = :stressTypeId "; */
//
//			logger.info("query for fetching stress Stage Id is -> select distinct ass.ID from agri_stage ass\n" +
//					"inner join agri_commodity_stress_stage acss on(acss.StageID = ass.ID)\n" +
//					"inner join agri_stress  ast on(ast.ID = acss.StressID)\n" +
//					"where ass.Name = {} and acss.CommodityID = {} and acss.StressID = {}" , stressStageName, commodityId, stressId);
//
//			stressStageId = namedParameterJdbcTemplate.queryForObject(stressStageQuery, sqlParams, Integer.class);
//
//		} catch (DataAccessException ex) {
//			logger.error("Error while getting StressStage Id -> {}", ex.getMessage());
//		}
//		return stressStageId;
//	}

	/**
	 * This method is used to get metadata from DB.
`	 *
	 * @return <code>List</code> the response as list of
	 *         <code>BmImageMetadata</code>.
	 * 
	 * @throws DataAccessException
	 */
	public List<BmImageMetadata> getBmImageMetadata() {
		List<BmImageMetadata> metadataList = null;
		try {
			String stressStageQuery = "select agsc.ID, agsc.CommodityID, agsc.PhenophaseID, agsc.PlantPartID,\n"
					+ "agsc.StressTypeID, agsc.StressID, agsc.StressStageID from samik_agri_stress_combination agsc";

			metadataList = namedParameterJdbcTemplate.query(stressStageQuery,
					new BeanPropertyRowMapper<BmImageMetadata>(BmImageMetadata.class));

		} catch (DataAccessException ex) {
			logger.error("Error while getting metadata list -> {}", ex.getMessage());
		}

		return metadataList;
	}

}
