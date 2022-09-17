package in.cropdata.gstm.studio.dao;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import in.cropdata.gstm.studio.constants.ErrorConstants;
import in.cropdata.gstm.studio.exceptions.DataNotFoundException;
import in.cropdata.gstm.studio.exceptions.DbException;
import in.cropdata.gstm.studio.model.MapData;
import in.cropdata.gstm.studio.model.ParameterData;
import in.cropdata.gstm.studio.model.Region;
import in.cropdata.gstm.studio.model.RegionDetails;
import in.cropdata.gstm.studio.service.GeoJsonOperation;

/**
 * Memory Cache repository to fetch the data from MySQL Memory cache.
 * 
 * @since 1.0
 * @author PranaySK
 */

@Repository
public class MapCacheDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(MapCacheDao.class);

	@Autowired
	@Qualifier("mapDataJdbcTemplate")
	private JdbcTemplate mapDataJdbcTemplate;

	@Autowired
	private GeoJsonOperation geoJsonOperation;

	@Autowired
	private GstmStudioMapRepository mapRepository;

	/**
	 * This method is used to get the no of tiles present in cached data.
	 * 
	 * @param zoomLevel        the <b>Zoom Level</b> for which the data to be
	 *                         fetched
	 * @param coordinateClause the coordinate clause for adding boundary box
	 * 
	 * @return <code>Integer</code> the no of tiles present in cached data
	 * 
	 * @throws DataNotFoundException
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	public Integer getAvailableTilesInMapCache(final String zoomLevel, final String coordinateClause) {
		try {
			String mapTilesCountSql = "select count(tile.TileID) as TileCount from zl" + zoomLevel
					+ " as tile where 1 ";
			LOGGER.info("mapTilesCountSql -> {}", mapTilesCountSql);

			return mapDataJdbcTemplate.queryForObject(mapTilesCountSql, (rs, rowNum) -> rs.getInt("TileCount"));

		} catch (Exception ex) {
			throw new DataNotFoundException(ErrorConstants.DATA_FETCH_ERROR,
					"TileCount Data Not Found in cache -> " + ex.getMessage());
		}
	}

	/**
	 * This method is used to get <b>Region</b> data.
	 * 
	 * @param zoomLevel the <b>Zoom Level</b> for which <code>{@link Region}</code>
	 *                  details to be fetched
	 * @param latitude  the latitude coordinate of map pointer
	 * @param longitude the longitude coordinate of map pointer
	 * 
	 * @return <code>Region</code> the {@link Region} data
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	public Region getRegionDetails(final String zoomLevel, final BigDecimal latitude, final BigDecimal longitude) {
		Region region = new Region();
		RegionDetails regionDetails = new RegionDetails();
		try {
			/** getting region details */
			regionDetails = mapRepository.getRegionDetailsFromCache(zoomLevel, latitude, longitude);

		} catch (Exception ex) {
			throw new DataNotFoundException(ErrorConstants.DATA_FETCH_ERROR,
					"Data Not Found while fetching region details -> " + ex.getMessage());
		}

		LOGGER.info("regionData -> {}", regionDetails);

		region.setIn(regionDetails.getIn());
		region.setDistrict(regionDetails.getDistrict() == null ? "" : regionDetails.getDistrict());
		region.setState(regionDetails.getState() == null ? "" : regionDetails.getState());
		region.setYears(Arrays.asList(2021));
		region.setLevel("zl" + zoomLevel);

		return region;
	}

	/**
	 * This method is used to prepare the <b>Map Data Response</b> using the given
	 * input values.
	 * 
	 * @param zoomLevel        the <b>Zoom Level</b> for which the data to be
	 *                         fetched
	 * @param parameterData    the parameter configuration data
	 * @param seasonId         the current season id
	 * @param coordinateClause the coordinate clause for adding boundary box
	 * @param caseClause       the color case clause to add colors based on range
	 * 
	 * @return <code>String</code> the response in <code>GEO-JSON</code> String
	 * 
	 * @throws DbException
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	public String getMapData(final String zoomLevel, final ParameterData parameterData, final Integer seasonId,
			final String coordinateClause, final String caseClause, final Integer week) {
		try {
			String modelName = parameterData.getModel();
			List<MapData> mapDataList = null;
			String geoJsonStr = "";
			/** getting map data from cache */
			mapDataList = this.getModelWiseMapDataFromCache(modelName, zoomLevel, coordinateClause, week);

			if (mapDataList == null || mapDataList.isEmpty()) {
				/** getting map data from DB */
				mapDataList = this.getModelWiseMapDataFromDb(modelName, zoomLevel, seasonId, coordinateClause,
						caseClause, week);
				/** loading map data in cache */
				boolean isLoaded = this.loadMapDataInCache(zoomLevel, modelName, mapDataList);
				LOGGER.info("map data loaded in cache -> {}", isLoaded);
				/** getting map data from cache */
				mapDataList = this.getModelWiseMapDataFromCache(modelName, zoomLevel, coordinateClause, week);
			}
			/** preparing GEO-JSON String of map data */
			geoJsonStr = geoJsonOperation.convertMapDataToGeoJson(mapDataList);
			/** returning GEO-JSON String of map data */
			LOGGER.info("Returning map data as JS content...");
			return "var mapdata2 = ".concat(geoJsonStr).concat(";");

		} catch (Exception ex) {
			throw new DbException(ErrorConstants.DATA_PREP_ERROR,
					"Error while preparing map data -> " + ex.getMessage());
		}
	}

	/**
	 * This method is used to fetch the <b>Map Data</b> from cache.
	 * 
	 * @param modelName        the model for which the data to be fetched
	 * @param zoomLevel        the <b>Zoom Level</b> for which the data to be
	 *                         fetched
	 * @param coordinateClause the coordinate clause for adding boundary box
	 * 
	 * @return <code>List</code> the response in list of {@link MapData}
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	private List<MapData> getModelWiseMapDataFromCache(final String modelName, final String zoomLevel,
			String coordinateClause, Integer week) {
		LOGGER.info("Fetching map data from Cache...");
		List<MapData> mapDataList = null;
		/** fetch all map data from cache for ZL 7 to 9 */
		if (Integer.valueOf(zoomLevel) < 10) {
			coordinateClause = "";
		}
		/** fetch model wise map data from cache */
		if ("PYAR".equals(modelName)) {
			mapDataList = this.getPyarMapDataFromCache(zoomLevel, coordinateClause);

		} else if ("PVI".equals(modelName)) {
			mapDataList = this.getPviMapDataFromCache(zoomLevel, coordinateClause);
		} else if ("RRFV".equals(modelName)) {
			mapDataList = this.getPrfvMapDataFromCache(zoomLevel, coordinateClause, week);
		}

		return mapDataList;
	}

	/**
	 * This method is used to fetch the <b>Map Data</b> from DB.
	 * 
	 * @param modelName        the model for which the data to be fetched
	 * @param zoomLevel        the <b>Zoom Level</b> for which the data to be
	 *                         fetched
	 * @param seasonId         the current season id
	 * @param coordinateClause the coordinate clause for adding boundary box
	 * @param caseClause       the color case clause to add colors based on range
	 * 
	 * @return <code>List</code> the response in list of {@link MapData}
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	private List<MapData> getModelWiseMapDataFromDb(final String modelName, final String zoomLevel,
			final Integer seasonId, String coordinateClause, final String caseClause, final Integer week) {
		LOGGER.info("Map data not found in Cache. So fetching from DB...");
		List<MapData> mapDataList = null;
		/** fetch all map data from DB to load in cache for ZL 7 to 12 */
		if (Integer.valueOf(zoomLevel) < 13) {
			coordinateClause = "";
		}
		/** fetch model wise map data from DB */
		if ("PYAR".equals(modelName)) {
			mapDataList = mapRepository.getPyarMapDataFromDb(zoomLevel, seasonId, coordinateClause, caseClause);

		} else if ("PVI".equals(modelName)) {
			mapDataList = mapRepository.getPviMapDataFromDb(zoomLevel, seasonId, coordinateClause, caseClause);
		} else if ("RRFV".equals(modelName)) {
			mapDataList = mapRepository.getRrfvMapDataFromDb(zoomLevel, seasonId, coordinateClause, caseClause, week);
		}

		return mapDataList;
	}

	/**
	 * This method is used to update the <b>Map Data</b> in cache.
	 *
	 * @param mapDataList the map data to be updated
	 * @param insertSql   the query to be executed for updating the map data
	 * 
	 * @return <code>List</code> the response in list of {@link MapData}
	 * 
	 * @throws DbException
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	private boolean loadMapDataInCache(final String zoomLevel, final String modelName,
			final List<MapData> mapDataList) {
		try {
			LOGGER.info("Loading map data from DB in cache...");
			int count = 0;
			/** Preparing queries to update model wise map data in cache */
			if ("PYAR".equals(modelName)) {
				String pyarMapDataInsert = "INSERT INTO zl" + zoomLevel
						+ " (TileID, MinX, MinY, MaxX, MaxY, PyarColor) VALUES (?, ?, ?, ?, ?, ?) "
						+ "ON DUPLICATE KEY UPDATE MinX = values(MinX), MinY = values(MinY), MaxX = values(MaxX), MaxY = values(MaxY), PyarColor = values(PyarColor)";

				count = this.loadMapDataInCache(mapDataList, pyarMapDataInsert);

			} else if ("PVI".equals(modelName)) {
				String pviMapDataInsert = "INSERT INTO zl" + zoomLevel
						+ " (TileID, MinX, MinY, MaxX, MaxY, PviColor) VALUES (?, ?, ?, ?, ?, ?) "
						+ "ON DUPLICATE KEY UPDATE MinX = values(MinX), MinY = values(MinY), MaxX = values(MaxX), MaxY = values(MaxY), PviColor = values(PviColor)";

				count = this.loadMapDataInCache(mapDataList, pviMapDataInsert);
			} else if ("RRFV".equals(modelName)) {
				String rrfvMapDataInsert = "INSERT INTO zl" + zoomLevel
						+ " (TileID, MinX, MinY, MaxX, MaxY, RrfvColor) VALUES (?, ?, ?, ?, ?, ?) "
						+ "ON DUPLICATE KEY UPDATE MinX = values(MinX), MinY = values(MinY), MaxX = values(MaxX), MaxY = values(MaxY), RrfvColor = values(RrfvColor)";

				count = this.loadMapDataInCache(mapDataList, rrfvMapDataInsert);
			}

			return (count > 0);

		} catch (Exception ex) {
			throw new DbException(ErrorConstants.DATA_PREP_ERROR,
					"Error while updating map data in cache -> " + ex.getMessage());
		}
	}

	/**
	 * This method is used to update the <b>Map Data</b> in cache.
	 *
	 * @param mapDataList the map data to be updated
	 * @param insertSql   the query to be executed for updating the map data
	 * 
	 * @return <code>List</code> the response in list of {@link MapData}
	 * 
	 * @throws DataNotFoundException
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	private int loadMapDataInCache(final List<MapData> mapDataList, final String insertSql) {
		LOGGER.info("Map data insert query -> {}", insertSql);
		int count = 0;
		int rowCount = 0;
		/** updating map data in cache */
		for (MapData mapData : mapDataList) {
			count = mapDataJdbcTemplate.update(insertSql, mapData.getTileId(), mapData.getMinX(), mapData.getMinY(),
					mapData.getMaxX(), mapData.getMaxY(), mapData.getColor());
			rowCount++;
		}
		LOGGER.info("no of processed rows -> {}", rowCount);

		return count;
	}

	/**
	 * This method is used to get the <b>PYAR Map Data</b>.
	 * 
	 * @param zoomLevel        the <b>Zoom Level</b> for which the data to be
	 *                         fetched
	 * @param coordinateClause the coordinate clause for adding boundary box
	 * 
	 * @return <code>List</code> the response in list of {@link MapData}
	 * 
	 * @throws DataNotFoundException
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	public List<MapData> getPyarMapDataFromCache(final String zoomLevel, final String coordinateClause) {
		try {
			String mapCacheDataSql = "select TileID, MinX, MinY, MaxX, MaxY, PyarColor as Color from zl" + zoomLevel
					+ " as tile where 1 " + coordinateClause;
			LOGGER.info("mapCacheDataSql -> {}", mapCacheDataSql);

			return mapDataJdbcTemplate.query(mapCacheDataSql, new BeanPropertyRowMapper<MapData>(MapData.class));

		} catch (Exception ex) {
			throw new DataNotFoundException(ErrorConstants.DATA_FETCH_ERROR,
					"Data Not Found in Cache while fetching Pyar Map details -> " + ex.getMessage());
		}
	}

	/**
	 * This method is used to get the <b>PVI Map Data</b>.
	 * 
	 * @param zoomLevel        the <b>Zoom Level</b> for which the data to be
	 *                         fetched
	 * @param coordinateClause the coordinate clause for adding boundary box
	 * 
	 * @return <code>List</code> the response in list of {@link MapData}
	 * 
	 * @throws DataNotFoundException
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	public List<MapData> getPviMapDataFromCache(final String zoomLevel, final String coordinateClause) {
		try {
			String mapCacheDataSql = "select TileID, MinX, MinY, MaxX, MaxY, PviColor as Color from zl" + zoomLevel
					+ " as tile where 1 " + coordinateClause;
			LOGGER.info("mapCacheDataSql -> {}", mapCacheDataSql);

			return mapDataJdbcTemplate.query(mapCacheDataSql, new BeanPropertyRowMapper<MapData>(MapData.class));

		} catch (Exception ex) {
			throw new DataNotFoundException(ErrorConstants.DATA_FETCH_ERROR,
					"Data Not Found while fetching PVI Map details -> " + ex.getMessage());
		}
	}

	/**
	 * This method is used to get the <b>PVI Map Data</b>.
	 * 
	 * @param zoomLevel        the <b>Zoom Level</b> for which the data to be
	 *                         fetched
	 * @param coordinateClause the coordinate clause for adding boundary box
	 * 
	 * @return <code>List</code> the response in list of {@link MapData}
	 * 
	 * @throws DataNotFoundException
	 * 
	 * @since 1.0
	 * @author VivekGajbhiye
	 */
	public List<MapData> getPrfvMapDataFromCache(final String zoomLevel, final String coordinateClause,
			final Integer week) {
		try {
//			String mapCacheDataSql = "select TileID, MinX, MinY, MaxX, MaxY, RrfvColor as Color from zl" + zoomLevel
//					+ " as tile where 1 " + coordinateClause;

			String trimmedZoomLevel = zoomLevel.replaceAll("^0+(?!$)", "");

			String mapCacheDataSql = "select vr.TileID, tile.MinX, tile.MinY,tile.MaxX, tile.MaxY, bcc.ColorCode as Color\n"
					+ "from gstm_studio.value_risk_sd_zl" + zoomLevel + "_d as vr \n" + "inner join gstm_cone.tile_zl"
					+ trimmedZoomLevel + " as tile on tile.TileID = vr.TileID \n"
					+ "inner join gstm_studio.band_range as br on vr.RvInRsSecondary between br.RangeMinValue and br.RangeMaxValue and br.ParameterID=88\n"
					+ "inner join gstm_studio.band_color_code as bcc on bcc.ID = br.BandID\n" + "where vr.Week = "
					+ week + "  \n" + "order by vr.TileID;";

			LOGGER.info("mapCacheDataSql -> {}", mapCacheDataSql);

			return mapDataJdbcTemplate.query(mapCacheDataSql, new BeanPropertyRowMapper<MapData>(MapData.class));

		} catch (Exception ex) {
			throw new DataNotFoundException(ErrorConstants.DATA_FETCH_ERROR,
					"Data Not Found while fetching RRFV Map details -> " + ex.getMessage());
		}
	}

}
