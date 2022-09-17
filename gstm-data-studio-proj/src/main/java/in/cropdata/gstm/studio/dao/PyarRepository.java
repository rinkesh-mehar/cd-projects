package in.cropdata.gstm.studio.dao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import in.cropdata.gstm.studio.constants.ErrorConstants;
import in.cropdata.gstm.studio.exceptions.DataNotFoundException;
import in.cropdata.gstm.studio.exceptions.DbException;
import in.cropdata.gstm.studio.model.Aggregate;
import in.cropdata.gstm.studio.model.AnalyticsData;
import in.cropdata.gstm.studio.model.AnalyticsParameters;
import in.cropdata.gstm.studio.model.Fields;
import in.cropdata.gstm.studio.model.FocusCrop;
import in.cropdata.gstm.studio.model.ParameterData;
import in.cropdata.gstm.studio.model.RegionProfile;
import in.cropdata.gstm.studio.model.Values;
import in.cropdata.gstm.studio.util.GstmStudioUtil;

/**
 * Repository for processing and sending fetched map and analytics data for
 * PYaR.
 * 
 * @since 1.0
 * @author PranaySK
 */

@Repository
public class PyarRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(PyarRepository.class);

	@Autowired
	private MapCacheDao cacheDao;

	@Autowired
	private GstmStudioUtil studioUtil;

	@Autowired
	@Qualifier("gstmNamedJdbcTemplate")
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	public PyarRepository(NamedParameterJdbcTemplate namedJdbcTemplate) {
		this.namedJdbcTemplate = namedJdbcTemplate;
	}

	/**
	 * This method is used to prepare the <b>Analytics Data Response</b> using the
	 * given input values.
	 * 
	 * @param zoomLevel        the <b>Zoom Level</b> for which the data to be
	 *                         fetched
	 * @param parameterId      the <b>Parameter Id</b> for which the data to be
	 *                         fetched
	 * @param latitude         the latitude coordinate of map pointer
	 * @param longitude        the longitude coordinate of map pointer
	 * @param parameterData    the parameter configuration data
	 * @param coordinateClause the coordinate clause for adding coordinate condition
	 * @param caseClause       the color case clause for adding color in map data
	 * 
	 * @return <code>String</code> the response in <code>Analytics</code> data
	 *         String
	 * 
	 * @throws DbException
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	public String getAnalyticsData(final String zoomLevel, final Integer parameterId, final BigDecimal latitude,
			final BigDecimal longitude, final ParameterData parameterData, final String coordinateClause,
			final String caseClause) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			/** getting analytics data list */
			List<AnalyticsParameters> analyticsDataList = this.getAnalyticsParams(zoomLevel, coordinateClause,
					caseClause);
			/** getting String response by processing analytics data */
			String analyticsDataStr = mapper.writeValueAsString(
					this.prepareAnalyticsResponse(zoomLevel, analyticsDataList, parameterData, latitude, longitude));

			LOGGER.info("Returning analytics data as JS content...");
			return "var analyticsData = ".concat(analyticsDataStr).concat(";");

		} catch (JsonProcessingException ex) {
			throw new DbException(ErrorConstants.DATA_PREP_ERROR,
					"Error while preparing analytics data -> " + ex.getMessage());
		}
	}

	/**
	 * This method is used to prepare the <b>Brief Analytics Data</b> response using
	 * the given input values.
	 * 
	 * @param zoomLevel  the <b>Zoom Level</b> for which the data to be fetched
	 * @param tileId     the tile id for which the brief analytics data to be
	 *                   fetched
	 * @param caseClause the <b>Parameter Id</b> for which the data to be fetched
	 * 
	 * @return <code>String</code> the response in <code>Analytics</code> data
	 *         String
	 * 
	 * @throws DbException
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	public String getBriefAnalyticsData(final String zoomLevel, final Integer tileId, final String caseClause,
			final Integer seasonId) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			/** getting brief analytics data list */
			List<AnalyticsParameters> briefAnalyticsParamList = this.getBriefAnalyticsParams(zoomLevel, caseClause,
					tileId, seasonId);
			/** getting String response by processing brief analytics data */
			String analyticsDataStr = mapper
					.writeValueAsString(this.prepareBriefAnalyticsResponse(briefAnalyticsParamList));

			LOGGER.info("Returning brief analytics data as JS content...");
			return "var briefAnalyticsData = ".concat(analyticsDataStr).concat(";");

		} catch (JsonProcessingException ex) {
			throw new DbException(ErrorConstants.DATA_PREP_ERROR,
					"Error while preparing brief analytics data -> " + ex.getMessage());
		}
	}

	/**
	 * This method is used to prepare the <b>Brief Analytics Data</b> response using
	 * the given input values.
	 * 
	 * @param zoomLevel         the <b>Zoom Level</b> for which
	 *                          <code>{@link AnalyticsData}</code> to be fetched
	 * @param analyticsDataList the List of <code>{@link AnalyticsParameters}</code>
	 * @param parameterData     the the parameter configuration data
	 *                          <code>{@link ParameterData}</code>
	 * @param latitude          the latitude coordinate of map pointer
	 * @param longitude         the longitude coordinate of map pointer
	 * 
	 * @return <code>AnalyticsData</code> the analytics response data in
	 *         {@link AnalyticsData} object
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	private AnalyticsData prepareAnalyticsResponse(final String zoomLevel,
			final List<AnalyticsParameters> analyticsDataList, final ParameterData parameterData,
			final BigDecimal latitude, final BigDecimal longitude) {

		BigDecimal totalProduction = new BigDecimal(0);
		List<BigDecimal> avgPyarList = new ArrayList<>();
		List<BigDecimal> avgIrrigationList = new ArrayList<>();
		List<BigDecimal> avgNsaTgaPerList = new ArrayList<>();
		List<BigDecimal> avgAverageLhSizeList = new ArrayList<>();
		List<FocusCrop> focusCrops = new ArrayList<>();
		List<FocusCrop> riskFreeCrops = new ArrayList<>();
		String seasonName = "";

		/** preparing separate lists for data processing */
		for (AnalyticsParameters params : analyticsDataList) {
			totalProduction = totalProduction.add(params.getProduction());
			avgPyarList.add(params.getPyar());
			avgIrrigationList.add(params.getIrrigation());
			avgNsaTgaPerList.add(params.getNsaTgaPer());
			avgAverageLhSizeList.add(params.getAverageLhSize());

			FocusCrop focusCrop = new FocusCrop();
			focusCrop.setCropName(params.getCropName());
			focusCrop.setColor(params.getColor());
			focusCrop.setValue(params.getPyar().setScale(2, RoundingMode.HALF_UP).toString());
			focusCrops.add(focusCrop);
			riskFreeCrops.add(focusCrop);
			seasonName = params.getSeasonName();
		}

		/** processing data by applying aggregations */
		double avgPyar = avgPyarList.stream().mapToDouble(BigDecimal::doubleValue).average().getAsDouble();
		double avgIrrigation = avgIrrigationList.stream().mapToDouble(BigDecimal::doubleValue).average().getAsDouble();
		double avgNsaTgaPer = avgNsaTgaPerList.stream().mapToDouble(BigDecimal::doubleValue).average().getAsDouble();
		double avgAverageLhSize = avgAverageLhSizeList.stream().mapToDouble(BigDecimal::doubleValue).average()
				.getAsDouble();
		String orderingMethod = parameterData.getOrderingMethod();
		String template = parameterData.getTemplate();

		LOGGER.info(
				"avgPyar -> {} ::: avgIrrigation -> {} ::: avgNsaTgaPer -> {} ::: avgAverageLhSize -> {} ::: totalProduction -> {}",
				avgPyar, avgIrrigation, avgNsaTgaPer, avgAverageLhSize, totalProduction);
		LOGGER.info("orderingMethod -> {}", orderingMethod);
		/** ordering focused crops */
		if ("ASC".equals(orderingMethod)) {
			riskFreeCrops.sort(Comparator.comparing(FocusCrop::getValue));
		} else {
			riskFreeCrops.sort(Comparator.comparing(FocusCrop::getValue).reversed());
		}

		AnalyticsData data = new AnalyticsData();
		RegionProfile regionProfile = new RegionProfile();
		Fields fields = new Fields();
		Values values = new Values();
		Aggregate aggregate = new Aggregate();

		/** setting labels */
		fields.setAvgIrrigation("Irrigated Area");
		fields.setAvgNsaTgaPer("NSA/TGA Ratio");
		fields.setAvgAverageLhSize("Avg. LH Size");
		fields.setTotalProduction("Production");

		/** setting label values */
		values.setAvgIrrigation(studioUtil.doubleToInt(avgIrrigation) + " %");
		values.setAvgNsaTgaPer(studioUtil.doubleToInt(avgNsaTgaPer) + " %");
		values.setAvgAverageLhSize(studioUtil.roundToPlaces(avgAverageLhSize, 2) + " Ha");
		values.setTotalProduction(studioUtil.getProductionWithUnit(zoomLevel, totalProduction));

		/** setting total average value for PYaR */
		aggregate.setLabel("Average PYaR");
		aggregate.setValue(studioUtil.doubleToInt(avgPyar) + " %");

		/** setting region profile details */
		regionProfile.setFields(fields);
		regionProfile.setValues(values);
		regionProfile.setAggregate(aggregate);

		/** setting region details */
		data.setTemplate(template);
		data.setSeason(seasonName);
		data.setRegion(cacheDao.getRegionDetails(zoomLevel, latitude, longitude));
		data.setRegionProfile(regionProfile);
		data.setFocusCrops(focusCrops.size() < 10 ? focusCrops : focusCrops.subList(0, 10));
		data.setRiskFreeCrops(riskFreeCrops.size() < 10 ? riskFreeCrops : riskFreeCrops.subList(0, 10));

		return data;
	}

	/**
	 * This method is used to prepare the <b>AnalyticsData Response</b> using the
	 * given input values.
	 * 
	 * @param analyticsDataList the list of {@link AnalyticsParameters}
	 * 
	 * @return <code>AnalyticsData</code> the analytics response data in
	 *         {@link AnalyticsData}
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	private AnalyticsData prepareBriefAnalyticsResponse(List<AnalyticsParameters> analyticsDataList) {
		List<BigDecimal> avgPyarList = new ArrayList<>();
		List<BigDecimal> avgIrrigationList = new ArrayList<>();
		List<BigDecimal> avgAverageLhSizeList = new ArrayList<>();
		List<FocusCrop> focusCrops = new ArrayList<>();
		String seasonName = "";

		/** preparing separate lists for data processing */
		for (AnalyticsParameters params : analyticsDataList) {
			avgPyarList.add(params.getPyar());
			avgIrrigationList.add(params.getIrrigation());
			avgAverageLhSizeList.add(params.getAverageLhSize());

			FocusCrop focusCrop = new FocusCrop();
			focusCrop.setCropName(params.getCropName());
			focusCrop.setColor(params.getColor());
			focusCrop.setValue(params.getPyar().setScale(2, RoundingMode.HALF_UP).toString());
			focusCrops.add(focusCrop);
			seasonName = params.getSeasonName();
		}

		/** processing data by applying aggregations */
		double avgPyar = avgPyarList.stream().mapToDouble(BigDecimal::doubleValue).average().getAsDouble();
		double avgIrrigation = avgIrrigationList.stream().mapToDouble(BigDecimal::doubleValue).average().getAsDouble();
		double avgAverageLhSize = avgAverageLhSizeList.stream().mapToDouble(BigDecimal::doubleValue).average()
				.getAsDouble();

		LOGGER.info("avgPyar -> {} ::: avgIrrigation -> {} ::: avgAverageLhSize -> {}", avgPyar, avgIrrigation,
				avgAverageLhSize);

		AnalyticsData data = new AnalyticsData();
		RegionProfile regionProfile = new RegionProfile();
		Fields fields = new Fields();
		Values values = new Values();
		Aggregate aggregate = new Aggregate();

		/** setting labels */
		fields.setAvgIrrigation("Irrigated Area");
		fields.setAvgAverageLhSize("Avg. LH Size");

		/** setting label values */
		values.setAvgIrrigation(studioUtil.doubleToInt(avgIrrigation) + " %");
		values.setAvgAverageLhSize(studioUtil.roundToPlaces(avgAverageLhSize, 2) + " Ha");

		/** setting total average value for PYaR */
		aggregate.setLabel("Average PYaR");
		aggregate.setValue(studioUtil.doubleToInt(avgPyar) + " %");

		/** setting region profile details */
		regionProfile.setFields(fields);
		regionProfile.setValues(values);
		regionProfile.setAggregate(aggregate);

		/** setting region details */
		data.setSeason(seasonName);
		data.setRegionProfile(regionProfile);
		data.setFocusCrops(focusCrops.size() < 3 ? focusCrops : focusCrops.subList(0, 3));

		return data;
	}

	/**
	 * This method is used to get <b>Analytics</b> details for the given parameters.
	 * 
	 * @param zoomLevel        the <b>Zoom Level</b> for which
	 *                         {@link AnalyticsParameters} data to be fetched
	 * @param coordinateClause the coordinate clause for adding coordinate condition
	 * @param caseClause       the color case clause for adding color in map data
	 * 
	 * @return <code>List</code> the response in list of {@link AnalyticsParameters}
	 * 
	 * @throws DataNotFoundException
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	public List<AnalyticsParameters> getAnalyticsParams(final String zoomLevel, final String coordinateClause,
			final String caseClause) {
		try {
			LOGGER.info("Fetching analytics data...");
			String analyticsDataSql = "select SeasonID, SeasonName, Irrigation, NsaTgaPer, AverageLhSize, Production, PYaR,"
					+ caseClause
					+ " CropCount, Crop, CropName from ( select value_risk_sd_.SeasonID, season_.Name as SeasonName,\n"
					+ "AVG( IFNULL(value_risk_sd_.PyarPrimary, IFNULL(value_risk_sd_.PyarSurvey, value_risk_sd_.RrfvSecondary)) ) as PYaR,\n"
					+ "AVG( IFNULL(area_nsd_.IrrigationPrimary, IFNULL(area_nsd_.IrrigationSurvey, area_nsd_.IrrigationSecondary)) ) as Irrigation,\n"
					+ "AVG( IFNULL(area_nsd_.AverageLhSizePrimary, IFNULL(area_nsd_.AverageLhSizeSurvey, area_nsd_.AverageLhSizeSecondary)) ) as AverageLhSize,\n"
					+ "AVG( IFNULL(area_nsd_.NsaTgaPerPrimary, IFNULL(area_nsd_.NsaTgaPerSurvey, area_nsd_.NsaTgaPerSecondary)) ) as NsaTgaPer,\n"
					+ "SUM( IFNULL(area_sd_.ProductionPrimary, IFNULL(area_sd_.ProductionSurvey, area_sd_.ProductionSecondary)) ) as Production,\n"
					+ "IFNULL(crop.CropPrimary, IFNULL(crop.CropSurvey, crop.CropSecondary)) as Crop,\n"
					+ "COUNT(IFNULL(crop.CropPrimary, IFNULL(crop.CropSurvey, crop.CropSecondary))) as CropCount, agc.Name as CropName\n"
					+ "from value_risk_sd_zl" + zoomLevel + " as value_risk_sd_ inner join gstm_cone_old.tile_zl"
					+ zoomLevel + " as tile on tile.TileID = value_risk_sd_.TileID \ninner join crop_zl" + zoomLevel
					+ " as crop on crop.TileID = value_risk_sd_.TileID and crop.SeasonID = value_risk_sd_.SeasonID\n"
					+ "inner join cdt_master_data.agri_commodity as agc on agc.ID = IFNULL(crop.CropPrimary, IFNULL(crop.CropSurvey, crop.CropSecondary))\n"
					+ "inner join area_sd_zl" + zoomLevel
					+ " as area_sd_ on area_sd_.TileID = value_risk_sd_.TileID and area_sd_.SeasonID = value_risk_sd_.SeasonID\n"
					+ "inner join area_nsd_zl" + zoomLevel
					+ " as area_nsd_ on area_nsd_.TileID = value_risk_sd_.TileID \n"
					+ "inner join cdt_master_data.agri_season as season_ on season_.ID = value_risk_sd_.SeasonID\n"
					+ "where 1 and value_risk_sd_.SeasonID = 1 " + coordinateClause
					+ "\ngroup by value_risk_sd_.SeasonID, Crop, CropName order by CropCount desc ) Analytics";

			LOGGER.info("full analyticsDataSql -> {}", analyticsDataSql);

			return namedJdbcTemplate.query(analyticsDataSql,
					new BeanPropertyRowMapper<AnalyticsParameters>(AnalyticsParameters.class));

		} catch (Exception ex) {
			throw new DataNotFoundException(ErrorConstants.DATA_FETCH_ERROR,
					"Data Not Found while fetching PYAR Analytics details -> " + ex.getMessage());
		}
	}

	/**
	 * This method is used to get <b>Brief Analytics</b> details for the given
	 * parameters.
	 * 
	 * @param zoomLevel        the <b>Zoom Level</b> for which
	 *                         {@link AnalyticsParameters} data to be fetched
	 * @param coordinateClause the coordinate clause for adding coordinate condition
	 * @param caseClause       the color case clause for adding color in map data
	 * 
	 * @return <code>List</code> the response in list of {@link AnalyticsParameters}
	 * 
	 * @throws DataNotFoundException
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	private List<AnalyticsParameters> getBriefAnalyticsParams(String zoomLevel, final String caseClause, Integer tileId,
			final Integer seasonId) {
		try {
			LOGGER.info("Fetching brief analytics data...");
			String analyticsDataSql = "select SeasonName, Irrigation, AverageLhSize, Pyar," + caseClause
					+ " Crop, CropName from ( select season_.Name as SeasonName,\n"
					+ "AVG( IFNULL(value_risk_sd_.PyarPrimary, IFNULL(value_risk_sd_.PyarSurvey, value_risk_sd_.RrfvSecondary)) ) as Pyar,\n"
					+ "AVG( IFNULL(area_nsd_.IrrigationPrimary, IFNULL(area_nsd_.IrrigationSurvey, area_nsd_.IrrigationSecondary)) ) as Irrigation,\n"
					+ "AVG( IFNULL(area_nsd_.AverageLhSizePrimary, IFNULL(area_nsd_.AverageLhSizeSurvey, area_nsd_.AverageLhSizeSecondary)) ) as AverageLhSize,\n"
					+ "IFNULL(crop.CropPrimary, IFNULL(crop.CropSurvey, crop.CropSecondary)) as Crop,\n"
					+ "COUNT(IFNULL(crop.CropPrimary, IFNULL(crop.CropSurvey, crop.CropSecondary))) as CropCount, agc.Name as CropName\n"
					+ "from value_risk_sd_zl" + zoomLevel + " as value_risk_sd_ inner join gstm_cone_old.tile_zl"
					+ zoomLevel + " as tile on tile.TileID = value_risk_sd_.TileID \ninner join crop_zl" + zoomLevel
					+ " as crop on crop.TileID = value_risk_sd_.TileID and crop.SeasonID = value_risk_sd_.SeasonID\n"
					+ "inner join cdt_master_data.agri_commodity as agc on agc.ID = IFNULL(crop.CropPrimary, IFNULL(crop.CropSurvey, crop.CropSecondary))\n"
					+ "inner join area_sd_zl" + zoomLevel
					+ " as area_sd_ on area_sd_.TileID = value_risk_sd_.TileID and area_sd_.SeasonID = value_risk_sd_.SeasonID\n"
					+ "inner join area_nsd_zl" + zoomLevel
					+ " as area_nsd_ on area_nsd_.TileID = value_risk_sd_.TileID \n"
					+ "inner join cdt_master_data.agri_season as season_ on season_.ID = value_risk_sd_.SeasonID\n"
					+ "where 1 and value_risk_sd_.SeasonID = " + seasonId + " and tile.TileID = " + tileId
					+ "\ngroup by value_risk_sd_.SeasonID, Crop, CropName order by CropCount desc ) Analytics";

			LOGGER.info("brief analyticsDataSql -> {}", analyticsDataSql);

			return namedJdbcTemplate.query(analyticsDataSql,
					new BeanPropertyRowMapper<AnalyticsParameters>(AnalyticsParameters.class));

		} catch (Exception ex) {
			throw new DataNotFoundException(ErrorConstants.DATA_FETCH_ERROR,
					"Data Not Found while fetching PYAR Brief Analytics details -> " + ex.getMessage());
		}
	}

}
