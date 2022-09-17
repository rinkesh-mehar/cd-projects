package in.cropdata.gstm.studio.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder.In;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.gstm.studio.model.DropDownFilters;
import in.cropdata.gstm.studio.service.GstmStudioCacheService;
import in.cropdata.gstm.studio.service.GstmStudioService;

/**
 * Controller exposed to get required data for processing.
 * 
 * @since 1.0
 * @author PranaySK
 */

@RestController
@RequestMapping("/data")
public class GstmStudioController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GstmStudioController.class);

	@Autowired
	private GstmStudioService studioService;

	@Autowired
	private GstmStudioCacheService cacheService;

	/**
	 * This API is used to test the application status.
	 * 
	 * @return <code>String</code> the welcome text
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	@GetMapping("/test")
	public String getWelcomeText() {
		return "Welcome!";
	}

	/**
	 * This API is used to get the State and District filter data.
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
	@GetMapping("/st")
	public List<DropDownFilters> getStatesOrDistricts(@RequestParam("st") Integer stateCode,
			@RequestParam("f") Integer flag) {
		LOGGER.info("Getting States or Districts...");
		return studioService.getStatesDistricts(stateCode, flag);
	}

	/**
	 * This API is used to get the parameters filter data.
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
	@GetMapping("/filters")
	public List<DropDownFilters> getAllPlatforms(@RequestParam("plf") Integer platformId,
			@RequestParam("dt") Integer dataTypeId, @RequestParam("ct") Integer categoryId,
			@RequestParam("sct") Integer subCategoryId, @RequestParam("f") Integer flag) {
		LOGGER.info("Get filters called...");
		return studioService.getAllParamFilters(platformId, dataTypeId, categoryId, subCategoryId, flag);
	}

	/**
	 * This API is used to get map data for the given parameters.
	 * 
	 * @param zoomLevel    the <b>Zoom Level</b> for which the data to be fetched
	 * @param parameterId  the <b>Parameter Id</b> for which the data to be fetched
	 * @param tileId       the tileId of map pointer
	 * @param latitude     the latitude coordinate of map pointer
	 * @param longitude    the longitude coordinate of map pointer
	 * @param leftTopX     the left top longitude coordinate on map viewport
	 * @param leftTopY     the left top latitude coordinate on map viewport
	 * @param rightBottomX the right bottom longitude coordinate on map viewport
	 * @param rightBottomY the right bottom latitude coordinate on map viewport
	 * 
	 * @return <code>String</code> the response in <code>GEO-JSON</code> String
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	@GetMapping("/map")
	public String getMapData(@RequestParam("zl") String zoomLevel, @RequestParam("p") Integer parameterId,
			 @RequestParam(name = "w",required = false,defaultValue = "0") Integer week,
			@RequestParam("tid") Integer tileId, @RequestParam("lat") BigDecimal latitude,
			@RequestParam("lng") BigDecimal longitude, @RequestParam("ltX") BigDecimal leftTopX,
			@RequestParam("ltY") BigDecimal leftTopY, @RequestParam("rbX") BigDecimal rightBottomX,
			@RequestParam("rbY") BigDecimal rightBottomY) {

		Map<String, BigDecimal> coordinates = new HashMap<>();
		coordinates.put("ltX", leftTopX);
		coordinates.put("ltY", leftTopY);
		coordinates.put("rbX", rightBottomX);
		coordinates.put("rbY", rightBottomY);

		return cacheService.getMapData(parameterId, zoomLevel, tileId, latitude, longitude, coordinates,week);
	}

	/**
	 * This API is used to get analytics data for the given parameters.
	 * 
	 * @param zoomLevel    the <b>Zoom Level</b> for which the data to be fetched
	 * @param parameterId  the <b>Parameter Id</b> for which the data to be fetched
	 * @param tileId       the tileId of map pointer
	 * @param latitude     the latitude coordinate of map pointer
	 * @param longitude    the longitude coordinate of map pointer
	 * @param leftTopX     the left top longitude coordinate on map viewport
	 * @param leftTopY     the left top latitude coordinate on map viewport
	 * @param rightBottomX the right bottom longitude coordinate on map viewport
	 * @param rightBottomY the right bottom latitude coordinate on map viewport
	 * 
	 * @return <code>String</code> the response in <code>Analytics</code> data
	 *         String
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	@GetMapping("/analytics")
	public String getAnalyticsData(@RequestParam("zl") String zoomLevel, @RequestParam("p") Integer parameterId,
			@RequestParam("tid") Integer tileId, @RequestParam("lat") BigDecimal latitude,
			@RequestParam("lng") BigDecimal longitude, @RequestParam("ltX") BigDecimal leftTopX,
			@RequestParam("ltY") BigDecimal leftTopY, @RequestParam("rbX") BigDecimal rightBottomX,
			@RequestParam("rbY") BigDecimal rightBottomY) {

		Map<String, BigDecimal> coordinates = new HashMap<>();
		coordinates.put("ltX", leftTopX);
		coordinates.put("ltY", leftTopY);
		coordinates.put("rbX", rightBottomX);
		coordinates.put("rbY", rightBottomY);

		return studioService.getAnalyticsData(parameterId, zoomLevel, tileId, latitude, longitude, coordinates);
	}

	/**
	 * This API is used to get brief analytics data for the given parameters.
	 * 
	 * @param zoomLevel    the <b>Zoom Level</b> for which the data to be fetched
	 * @param parameterId  the <b>Parameter Id</b> for which the data to be fetched
	 * @param tileId       the tileId of map pointer
	 * @param latitude     the latitude coordinate of map pointer
	 * @param longitude    the longitude coordinate of map pointer
	 * @param leftTopX     the left top longitude coordinate on map viewport
	 * @param leftTopY     the left top latitude coordinate on map viewport
	 * @param rightBottomX the right bottom longitude coordinate on map viewport
	 * @param rightBottomY the right bottom latitude coordinate on map viewport
	 * 
	 * @return <code>String</code> the response in <code>Analytics</code> data
	 *         String
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	@GetMapping("/analytics/brief")
	public String getBriefAnalyticsData(@RequestParam("zl") String zoomLevel, @RequestParam("p") Integer parameterId,
			@RequestParam("tid") Integer tileId, @RequestParam("lat") BigDecimal latitude,
			@RequestParam("lng") BigDecimal longitude, @RequestParam("ltX") BigDecimal leftTopX,
			@RequestParam("ltY") BigDecimal leftTopY, @RequestParam("rbX") BigDecimal rightBottomX,
			@RequestParam("rbY") BigDecimal rightBottomY) {

		Map<String, BigDecimal> coordinates = new HashMap<>();
		coordinates.put("ltX", leftTopX);
		coordinates.put("ltY", leftTopY);
		coordinates.put("rbX", rightBottomX);
		coordinates.put("rbY", rightBottomY);

		return studioService.getBriefAnalyticsData(parameterId, zoomLevel, tileId, latitude, longitude, coordinates);
	}

	/**
	 * This API is used to get region details for the given parameters.
	 * 
	 * @param zoomLevel    the <b>Zoom Level</b> for which the data to be fetched
	 * @param parameterId  the <b>Parameter Id</b> for which the data to be fetched
	 * @param tileId       the tileId of map pointer
	 * @param latitude     the latitude coordinate of map pointer
	 * @param longitude    the longitude coordinate of map pointer
	 * @param leftTopX     the left top longitude coordinate on map viewport
	 * @param leftTopY     the left top latitude coordinate on map viewport
	 * @param rightBottomX the right bottom longitude coordinate on map viewport
	 * @param rightBottomY the right bottom latitude coordinate on map viewport
	 * 
	 * @return <code>String</code> the response in <code>Analytics</code> data
	 *         String
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	@GetMapping("/analytics/region")
	public String getRegionData(@RequestParam("zl") String zoomLevel, @RequestParam("p") Integer parameterId,
			@RequestParam("tid") Integer tileId, @RequestParam("lat") BigDecimal latitude,
			@RequestParam("lng") BigDecimal longitude, @RequestParam("ltX") BigDecimal leftTopX,
			@RequestParam("ltY") BigDecimal leftTopY, @RequestParam("rbX") BigDecimal rightBottomX,
			@RequestParam("rbY") BigDecimal rightBottomY) {

		Map<String, BigDecimal> coordinates = new HashMap<>();
		coordinates.put("ltX", leftTopX);
		coordinates.put("ltY", leftTopY);
		coordinates.put("rbX", rightBottomX);
		coordinates.put("rbY", rightBottomY);

		return cacheService.getRegionData(parameterId, zoomLevel, tileId, latitude, longitude, coordinates);
	}

}
