package in.cropdata.gstm.studio.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.cropdata.gstm.studio.constants.ErrorConstants;
import in.cropdata.gstm.studio.dao.GstmStudioRepositoryImpl;
import in.cropdata.gstm.studio.dao.PviRepository;
import in.cropdata.gstm.studio.dao.PyarRepository;
import in.cropdata.gstm.studio.exceptions.DataNotFoundException;
import in.cropdata.gstm.studio.exceptions.InvalidDataException;
import in.cropdata.gstm.studio.model.DropDownFilters;
import in.cropdata.gstm.studio.model.ParameterData;

/**
 * Service to process the data received from request and perform necessary
 * operations.
 * 
 * @since 1.0
 * @author PranaySK
 */

@Service
public class GstmStudioService {

	private static final Logger LOGGER = LoggerFactory.getLogger(GstmStudioService.class);

	@Autowired
	private GstmStudioRepositoryImpl repository;

	@Autowired
	private PyarRepository pyarRepository;

	@Autowired
	private PviRepository pviRepository;

	/**
	 * This method is used to get the State and District filter data.
	 * 
	 * @param stateCode the state code for which the district data to be fetched
	 * @param flag      the flag to decide whether State or District data to be
	 *                  fetched
	 * 
	 * @return <code>List</code> the response data in list of
	 *         <code>{@link DropDownFilters}</code>
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	public List<DropDownFilters> getStatesDistricts(final Integer stateCode, final Integer flag) {
		LOGGER.info("States or Districts flag -> {}", flag);
		if (flag == 0) {
			LOGGER.info("Get states");
			return repository.getAllStates();
		} else {
			LOGGER.info("Get districts");
			return repository.getDistricts(stateCode);
		}
	}

	/**
	 * This method is used to get the parameter filter data.
	 * 
	 * @param platformId    the platform Id for which the data types to be fetched
	 * @param dataTypeId    the data type Id for which the category to be fetched
	 * @param categoryId    the category Id for which the sub category to be fetched
	 * @param subCategoryId the sub category Id for which the parameters to be
	 *                      fetched
	 * @param flag          the flag to decide which filter data to be fetched
	 * 
	 * @return <code>List</code> the response data in list of
	 *         <code>{@link DropDownFilters}</code>
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	public List<DropDownFilters> getAllParamFilters(final Integer platformId, final Integer dataTypeId,
			final Integer categoryId, final Integer subCategoryId, final Integer flag) {
		LOGGER.info("Filter flag -> {}", flag);
		if (flag == 0) {
			LOGGER.info("Get platforms");
			return repository.getAllPlatforms();
		} else if (flag == 1) {
			LOGGER.info("Get data types");
			return repository.getAllDataTypes(platformId);
		} else if (flag == 2) {
			LOGGER.info("Get category");
			return repository.getDataTypeCategory(dataTypeId);
		} else if (flag == 3) {
			LOGGER.info("Get sub category");
			return repository.getSubCategory(categoryId);
		} else if (flag == 4) {
			LOGGER.info("Get parameters");
			return repository.getSubCategoryParameters(subCategoryId);
		} else {
			LOGGER.info("Get platforms default");
			return repository.getAllPlatforms();
		}
	}

	/**
	 * This method is used to get full analytics data for the given parameters.
	 * 
	 * @param parameterId the <b>Parameter Id</b> for which the data to be fetched
	 * @param zoomLevel   the <b>Zoom Level</b> for which the data to be fetched
	 * @param tileId      the tileId of map pointer
	 * @param latitude    the latitude coordinate of map pointer
	 * @param longitude   the longitude coordinate of map pointer
	 * @param coordinates the coordinates of map
	 * 
	 * @return <code>String</code> the response in <code>Analytics</code> data
	 *         String
	 * 
	 * @throws InvalidDataException
	 * @throws DataNotFoundException
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	public String getAnalyticsData(Integer parameterId, String zoomLevel, Integer tileId, BigDecimal latitude,
			BigDecimal longitude, Map<String, BigDecimal> coordinates) {
		try {
			LOGGER.info("zoomLevel -> {} ::: parameterId -> {}", zoomLevel, parameterId);
			if (zoomLevel != null && parameterId != null) {
				zoomLevel = (!zoomLevel.startsWith("0") && Integer.parseInt(zoomLevel) < 10) ? "0" + zoomLevel
						: zoomLevel;
				/** getting season id for current date */
				Integer seasonId = repository.getSeasonId();
				/** getting parameter configuration details */
				ParameterData parameterData = repository.getParameterConfigData(parameterId);
				LOGGER.info("parameterData -> {}", parameterData);
				/** getting color case clause */
				String caseClause = repository.getColorCaseSql(parameterId, parameterData.getRefrenceFieldName());
				/** getting coordinate clause for full analytics data to be fetched */
				String coordinateClause = repository.getCoordinatesClause(coordinates);
				/** model name to decide which model data to be processed and returned */
				String modelName = parameterData.getModel();
				parameterData.setSeasonId(seasonId);
				String response = null;

				if ("PYAR".equals(modelName)) {

					response = pyarRepository.getAnalyticsData(zoomLevel, parameterId, latitude, longitude,
							parameterData, coordinateClause, caseClause);

				} else if ("PVI".equals(modelName)) {

					response = pviRepository.getAnalyticsData(zoomLevel, parameterId, latitude, longitude,
							parameterData, coordinateClause, caseClause);
				}

				return response;

			} else {
				throw new InvalidDataException(ErrorConstants.INVALID_DATA,
						"Invalid request data! ZoomLevel and ParameterId can not be null!");
			}

		} catch (Exception ex) {
			throw new DataNotFoundException(ErrorConstants.DATA_FETCH_ERROR,
					"Error while fetching analytics data -> " + ex.getMessage());
		}
	}

	/**
	 * This method is used to get brief analytics data for the given parameters.
	 * 
	 * @param parameterId the <b>Parameter Id</b> for which the data to be fetched
	 * @param zoomLevel   the <b>Zoom Level</b> for which the data to be fetched
	 * @param tileId      the tileId of map pointer
	 * @param latitude    the latitude coordinate of map pointer
	 * @param longitude   the longitude coordinate of map pointer
	 * @param coordinates the coordinates of map
	 * 
	 * @return <code>String</code> the response in <code>Brief Analytics</code> data
	 *         String
	 * 
	 * @throws InvalidDataException
	 * @throws DataNotFoundException
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	public String getBriefAnalyticsData(Integer parameterId, String zoomLevel, Integer tileId, BigDecimal latitude,
			BigDecimal longitude, Map<String, BigDecimal> coordinates) {
		try {
			LOGGER.info("Brief Analytics zoomLevel -> {} ::: parameterId -> {}", zoomLevel, parameterId);
			if (zoomLevel != null && parameterId != null && tileId != null) {
				zoomLevel = (!zoomLevel.startsWith("0") && Integer.parseInt(zoomLevel) < 10) ? "0" + zoomLevel
						: zoomLevel;
				/** getting season id for current date */
				Integer seasonId = repository.getSeasonId();
				/** getting parameter configuration details */
				ParameterData parameterData = repository.getParameterConfigData(parameterId);
				LOGGER.info("Brief Analytics parameterData -> {}", parameterData);
				/** getting color case clause */
				String caseClause = repository.getColorCaseSql(parameterId, parameterData.getRefrenceFieldName());
				/** model name to decide which model data to be processed and returned */
				String modelName = parameterData.getModel();
				String response = null;

				if ("PYAR".equals(modelName)) {

					response = pyarRepository.getBriefAnalyticsData(zoomLevel, tileId, caseClause, seasonId);

				} else if ("PVI".equals(modelName)) {

					response = pviRepository.getBriefAnalyticsData(zoomLevel, tileId, caseClause, seasonId);
				}

				return response;

			} else {
				throw new InvalidDataException(ErrorConstants.INVALID_DATA,
						"Invalid request data! ZoomLevel and ParameterId and TileID can not be null!");
			}

		} catch (Exception ex) {
			throw new DataNotFoundException(ErrorConstants.DATA_FETCH_ERROR,
					"Error while fetching brief analytics data -> " + ex.getMessage());
		}
	}

}
