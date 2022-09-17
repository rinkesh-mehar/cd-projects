package in.cropdata.gstm.studio.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.cropdata.gstm.studio.constants.ErrorConstants;
import in.cropdata.gstm.studio.dao.GstmStudioRepositoryImpl;
import in.cropdata.gstm.studio.dao.MapCacheDao;
import in.cropdata.gstm.studio.exceptions.DataNotFoundException;
import in.cropdata.gstm.studio.exceptions.InvalidDataException;
import in.cropdata.gstm.studio.model.ParameterData;
import in.cropdata.gstm.studio.model.Region;

/**
 * Service to process the cache data and perform necessary operations.
 * 
 * @since 1.0
 * @author PranaySK
 */

@Service
public class GstmStudioCacheService {

	private static final Logger LOGGER = LoggerFactory.getLogger(GstmStudioCacheService.class);

	@Autowired
	private GstmStudioRepositoryImpl repository;

	@Autowired
	private MapCacheDao cacheDao;

	/**
	 * This method is used to get map and analytics data for the given parameters.
	 * 
	 * @param parameterId the <b>Parameter Id</b> for which the data to be fetched
	 * @param zoomLevel   the <b>Zoom Level</b> for which the data to be fetched
	 * @param tileId      the tileId of map pointer
	 * @param latitude    the latitude coordinate of map pointer
	 * @param longitude   the longitude coordinate of map pointer
	 * @param coordinates the coordinates of map
	 * 
	 * @return <code>String</code> the response in <code>GEO-JSON</code> and
	 *         <code>Analytics</code> data
	 * 
	 * @throws InvalidDataException
	 * @throws DataNotFoundException
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	public String getMapData(final Integer parameterId, String zoomLevel, final Integer tileId,
			final BigDecimal latitude, final BigDecimal longitude, final Map<String, BigDecimal> coordinates,
			Integer week) {
		try {
			if (week>0) {
				
			}else {
				Date d1 = new Date();
				Calendar cl = Calendar.getInstance();
				cl.setTime(d1);
				Integer weekOfMonth = cl.WEEK_OF_MONTH;
				week = weekOfMonth;
			}
			LOGGER.info("MAP zoomLevel -> {} ::: parameterId -> {}", zoomLevel, parameterId);
			if (zoomLevel != null && parameterId != null) {
				/** sanitizing input value */
				zoomLevel = (!zoomLevel.startsWith("0") && Integer.parseInt(zoomLevel) < 10) ? "0" + zoomLevel
						: zoomLevel;
				/** getting season id for current date */
				Integer seasonId = repository.getSeasonId();
				System.out.println("seasuin id" + seasonId);
				/** getting parameter configuration details */
				ParameterData parameterData = repository.getParameterConfigData(parameterId);
				LOGGER.info("MAP parameterData -> {}", parameterData);
				/** getting color case clause */
				String caseClause = repository.getColorCaseSql(parameterId, parameterData.getRefrenceFieldName());
				/** getting coordinate clause */
				String coordinateClause = repository.getCoordinatesClause(coordinates);

				return cacheDao.getMapData(zoomLevel, parameterData, seasonId, coordinateClause, caseClause, week);

			} else {
				throw new InvalidDataException(ErrorConstants.INVALID_DATA,
						"Invalid request data! ZoomLevel and ParameterId can not be null!");
			}

		} catch (Exception ex) {
			throw new DataNotFoundException(ErrorConstants.DATA_FETCH_ERROR,
					"Error while fetching map data -> " + ex.getMessage());
		}
	}

	/**
	 * This method is used to get region data for the given parameters.
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
	public String getRegionData(Integer parameterId, String zoomLevel, Integer tileId, BigDecimal latitude,
			BigDecimal longitude, Map<String, BigDecimal> coordinates) {
		try {
			LOGGER.info("Region zoomLevel -> {} ::: parameterId -> {}", zoomLevel, parameterId);
			if (zoomLevel != null && parameterId != null) {
				/** sanitizing input value */
				zoomLevel = (!zoomLevel.startsWith("0") && Integer.parseInt(zoomLevel) < 10) ? "0" + zoomLevel
						: zoomLevel;
				ObjectMapper mapper = new ObjectMapper();
				/** fetching region data */
				Region region = cacheDao.getRegionDetails(zoomLevel, latitude, longitude);
				/** preparing region data JSON string */
				String regionDataStr = mapper.writeValueAsString(region);

				LOGGER.info("Returning region data as JS content...");
				return "var regionData = ".concat(regionDataStr).concat(";");

			} else {
				throw new InvalidDataException(ErrorConstants.INVALID_DATA,
						"Invalid request data! ZoomLevel and ParameterId can not be null!");
			}

		} catch (Exception ex) {
			throw new DataNotFoundException(ErrorConstants.DATA_FETCH_ERROR,
					"Error while fetching region data -> " + ex.getMessage());
		}
	}

}
