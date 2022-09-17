package in.cropdata.gstm.studio.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import in.cropdata.gstm.studio.constants.ErrorConstants;
import in.cropdata.gstm.studio.exceptions.DataNotFoundException;
import in.cropdata.gstm.studio.model.MapData;
import in.cropdata.gstm.studio.model.RegionDetails;

/**
 * Repository for fetching map data from DB.
 * 
 * @since 1.0
 * @author PranaySK
 */

@Repository
public class GstmStudioMapRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(GstmStudioMapRepository.class);

	@Autowired
	@Qualifier("gstmDataJdbcTemplate")
	private JdbcTemplate gstmDataJdbcTemplate;

	@Autowired
	@Qualifier("gstmNamedJdbcTemplate")
	private NamedParameterJdbcTemplate gstmNamedJdbcTemplate;

	@Autowired
	@Qualifier("mapNamedJdbcTemplate")
	private NamedParameterJdbcTemplate mapNamedJdbcTemplate;

	/**
	 * This method is used to get <b>Region Details</b> for level 7 to 12.
	 * 
	 * @param zoomLevel the <b>Zoom Level</b> for which
	 *                  <code>{@link RegionDetails}</code> details to be fetched
	 * @param latitude  the latitude coordinate of map pointer
	 * @param longitude the longitude coordinate of map pointer
	 * 
	 * @return <code>RegionDetails</code> the {@link RegionDetails}
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	public RegionDetails getRegionDetailsFromCache(final String zoomLevel, final BigDecimal latitude,
			final BigDecimal longitude) {
		LOGGER.info("latitude -> {} and longitude -> {}", latitude, longitude);
		RegionDetails regionDetails = new RegionDetails();
		MapSqlParameterSource sqlParams = new MapSqlParameterSource();
		sqlParams.addValue("latitude", latitude);
		sqlParams.addValue("longitude", longitude);
		final String STATE_INNER_JOIN_WITH_DIST = "inner join geo_state gs on gs.StateCode = gd.StateCode WHERE 1 ";
		try {
			String regionNameQuery = "select 'India' as `in`";

			if (latitude != null && longitude != null) {
				final String VILLAGE_LAT_LNG_CLAUSE = "and ( :latitude between gv.MinY and gv.MaxY and :longitude between gv.MinX and gv.MaxX ) limit 1 ";
				if (Integer.valueOf(zoomLevel) < 10) {
					regionNameQuery = "SELECT distinct gs.StateName as `in` FROM geo_village gv "
							+ "inner join geo_district gd on gd.DistrictCode = gv.DistrictCode \n"
							+ STATE_INNER_JOIN_WITH_DIST + VILLAGE_LAT_LNG_CLAUSE;

				} else if (Integer.valueOf(zoomLevel) >= 10 && Integer.valueOf(zoomLevel) <= 12) {
					regionNameQuery = "SELECT distinct gd.DistrictName as `in`, gs.StateName as State FROM geo_village gv "
							+ "inner join geo_district gd on gd.DistrictCode = gv.DistrictCode \n"
							+ STATE_INNER_JOIN_WITH_DIST + VILLAGE_LAT_LNG_CLAUSE;

				} else {
					return this.getRegionDetails(zoomLevel, latitude, longitude);
				}
			}

			LOGGER.info("regionNameQuery -> {}", regionNameQuery);
			regionDetails = mapNamedJdbcTemplate.queryForObject(regionNameQuery, sqlParams,
					new BeanPropertyRowMapper<RegionDetails>(RegionDetails.class));

		} catch (DataAccessException ex) {
			LOGGER.info("Error - Data not found at village level! Fetching data from tehsil level -> {}",
					ex.getMessage());
			try {
				/** latitude & longitude search in tehsil level */
				String tehsilNameQuery = "SELECT distinct gt.TehsilName as `in`, gd.DistrictName as District, gs.StateName as State "
						+ "FROM geo_tehsil gt inner join geo_district gd on gd.DistrictCode = gt.DistrictCode \n"
						+ STATE_INNER_JOIN_WITH_DIST
						+ "and ( :latitude between gt.MinY and gt.MaxY and :longitude between gt.MinX and gt.MaxX ) limit 1";

				LOGGER.info("tehsilNameQuery -> {}", tehsilNameQuery);
				regionDetails = mapNamedJdbcTemplate.queryForObject(tehsilNameQuery, sqlParams,
						new BeanPropertyRowMapper<RegionDetails>(RegionDetails.class));

			} catch (DataAccessException dae) {
				LOGGER.info("Error - Data not found at tehsil level! Fetching data from district level -> {}",
						dae.getMessage());
				try {
					/** latitude & longitude search in district level */
					String districtNameQuery = "SELECT distinct gd.DistrictName as `in`, gs.StateName as State FROM geo_district gd "
							+ STATE_INNER_JOIN_WITH_DIST
							+ "and ( :latitude between gd.MinY and gd.MaxY and :longitude between gd.MinX and gd.MaxX ) limit 1";

					LOGGER.info("districtNameQuery -> {}", districtNameQuery);
					regionDetails = mapNamedJdbcTemplate.queryForObject(districtNameQuery, sqlParams,
							new BeanPropertyRowMapper<RegionDetails>(RegionDetails.class));

				} catch (DataAccessException daex) {
					LOGGER.info("Error - Data not found at district level! Setting default value -> {}",
							daex.getMessage());
					regionDetails.setIn("India");
				}
			}
		}

		return regionDetails;
	}

	/**
	 * This method is used to get <b>Region Details</b> for level 13 and 14.
	 * 
	 * @param zoomLevel the <b>Zoom Level</b> for which
	 *                  <code>{@link RegionDetails}</code> details to be fetched
	 * @param latitude  the latitude coordinate of map pointer
	 * @param longitude the longitude coordinate of map pointer
	 * 
	 * @return <code>RegionDetails</code> the {@link RegionDetails}
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	private RegionDetails getRegionDetails(final String zoomLevel, final BigDecimal latitude,
			final BigDecimal longitude) {
		RegionDetails regionDetails = new RegionDetails();
		MapSqlParameterSource sqlParams = new MapSqlParameterSource();
		sqlParams.addValue("latitude", latitude);
		sqlParams.addValue("longitude", longitude);
		final String STATE_INNER_JOIN_WITH_DIST = "inner join geo_state gs on gs.StateCode = gd.StateCode WHERE 1 ";
		try {
			String villageNameQuery = "select 'India' as `in`";

			if (latitude != null && longitude != null && Integer.valueOf(zoomLevel) >= 13) {
				villageNameQuery = "SELECT distinct gv.VillageName as `in`, gd.DistrictName as District, gs.StateName as State "
						+ "FROM geo_village gv inner join geo_district gd on gd.DistrictCode = gv.DistrictCode \n"
						+ STATE_INNER_JOIN_WITH_DIST
						+ "and ( :latitude between gv.MinY and gv.MaxY and :longitude between gv.MinX and gv.MaxX ) limit 1 ";
			}

			LOGGER.info("villageNameQuery -> {}", villageNameQuery);
			regionDetails = mapNamedJdbcTemplate.queryForObject(villageNameQuery, sqlParams,
					new BeanPropertyRowMapper<RegionDetails>(RegionDetails.class));

		} catch (DataAccessException ex) {
			LOGGER.info("Error - Data not found at village level! Fetching data from tehsil level -> {}",
					ex.getMessage());
			try {
				/** latitude & longitude search in tehsil level */
				String tehsilNameQuery = "SELECT distinct gt.TehsilName as `in`, gd.DistrictName as District, gs.StateName as State "
						+ "FROM geo_tehsil gt inner join geo_district gd on gd.DistrictCode = gt.DistrictCode \n"
						+ STATE_INNER_JOIN_WITH_DIST
						+ "and ( :latitude between gt.MinY and gt.MaxY and :longitude between gt.MinX and gt.MaxX ) limit 1 ";

				LOGGER.info("tehsilNameQuery -> {}", tehsilNameQuery);
				regionDetails = mapNamedJdbcTemplate.queryForObject(tehsilNameQuery, sqlParams,
						new BeanPropertyRowMapper<RegionDetails>(RegionDetails.class));

			} catch (DataAccessException dae) {
				LOGGER.info("Error - Data not found at tehsil level! Fetching data from district level-> {}",
						dae.getMessage());
				try {
					/** latitude & longitude search in district level */
					String districtNameQuery = "SELECT distinct gd.DistrictName as `in`, gs.StateName as State FROM geo_district gd \n"
							+ STATE_INNER_JOIN_WITH_DIST
							+ "and ( :latitude between gd.MinY and gd.MaxY and :longitude between gd.MinX and gd.MaxX ) limit 1 ";

					LOGGER.info("districtNameQuery -> {}", districtNameQuery);
					regionDetails = mapNamedJdbcTemplate.queryForObject(districtNameQuery, sqlParams,
							new BeanPropertyRowMapper<RegionDetails>(RegionDetails.class));

				} catch (DataAccessException daex) {
					LOGGER.info("Error - Data not found at district level! Setting default value -> {}",
							daex.getMessage());
					regionDetails.setIn("India");
				}
			}
		}

		return regionDetails;
	}

	/**
	 * This method is used to get all <b>Map Data</b> for PYaR.
	 * 
	 * @param zoomLevel        the <b>Zoom Level</b> for which
	 *                         <code>{@link MapData}</code> data to be fetched
	 * @param seasonId         the current season id
	 * @param coordinateClause the coordinate clause for adding coordinate condition
	 * @param caseClause       the color case clause for adding color in map data
	 * 
	 * @return <code>List</code> the response in list of {@link MapData}
	 * 
	 * @throws DataNotFoundException
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	public List<MapData> getPyarMapDataFromDb(String zoomLevel, final Integer seasonId, final String coordinateClause,
			final String caseClause) {
		try {
			String mapDataSql = "";
			if (Integer.valueOf(zoomLevel) < 13) {
				mapDataSql = "select TileID, " + caseClause + "MinX, MinY, MaxX, MaxY from ( "
						+ "select vr.TileID, IFNULL(vr.PyarPrimary, IFNULL(vr.PyarSurvey, vr.PyarSecondary)) as Pyar,\n"
						+ "tile.MinX, tile.MinY, tile.MaxX, tile.MaxY from value_risk_sd_zl" + zoomLevel + " as vr \n"
						+ "inner join gstm_cone_old.tile_zl" + zoomLevel + " as tile on tile.TileID = vr.TileID \n"
						+ "where 1 and vr.SeasonID = " + seasonId + " " + coordinateClause
						+ " order by vr.TileID ) MapData";

			} else if (Integer.valueOf(zoomLevel) == 13) {
				mapDataSql = "SELECT TileID, " + caseClause + "MinX, MinY, MaxX, MaxY from ( "
						+ "SELECT vr.TileID, IFNULL(vr.PyarPrimary, IFNULL(vr.PyarSurvey, vr.PyarSecondary)) as Pyar,\n"
						+ "tile.MinX, tile.MinY, tile.MaxX, tile.MaxY \n"
						+ "FROM gstm_studio.value_risk_sd_zl13 as vr INNER JOIN gstm_cone_old.tile_zl13 as tile on tile.TileId = vr.TileID \n"
						+ "WHERE vr.SeasonID = " + seasonId
						+ " and tile.TilePID IN ( SELECT vr12.TileID FROM gstm_studio.value_risk_sd_zl12 as vr12 \n"
						+ "INNER JOIN gstm_cone_old.tile_zl12 as tile12 on tile12.TileId = vr12.TileID WHERE vr12.seasonID = "
						+ seasonId + " and tile12.TilePID IN ( \n"
						+ "SELECT vr11.TileID FROM gstm_studio.value_risk_sd_zl11 vr11 "
						+ "INNER JOIN gstm_cone_old.tile_zl11 as tile11 on tile11.TileID = vr11.TileID \n"
						+ "WHERE 1 AND vr11.SeasonID = " + seasonId + " " + coordinateClause
						+ " ) ) order by vr.TileID ) MapData";

			} else if (Integer.valueOf(zoomLevel) > 13) {
				mapDataSql = "SELECT TileID, " + caseClause + "MinX, MinY, MaxX, MaxY from ( \n"
						+ "SELECT vr.TileID, IFNULL(vr.PyarPrimary, IFNULL(vr.PyarSurvey, vr.PyarSecondary)) as Pyar, tile.MinX, tile.MinY, tile.MaxX, tile.MaxY \n"
						+ "FROM gstm_studio.value_risk_sd_zl" + zoomLevel + " as vr INNER JOIN gstm_cone_old.tile_zl"
						+ zoomLevel + " as tile on tile.TileId = vr.TileID\n" + "WHERE vr.seasonID = " + seasonId
						+ " and tile.TilePID IN ( \n"
						+ "SELECT vr13.TileID FROM gstm_studio.value_risk_sd_zl13 as vr13 INNER JOIN gstm_cone_old.tile_zl13 as tile13 on tile13.TileId = vr13.TileID\n"
						+ "WHERE vr13.seasonID = " + seasonId + " and tile13.TilePID IN ( \n"
						+ "SELECT vr12.TileID FROM gstm_studio.value_risk_sd_zl12 as vr12 "
						+ "INNER JOIN gstm_cone_old.tile_zl12 as tile12 on tile12.TileId = vr12.TileID\n"
						+ "WHERE vr12.seasonID = " + seasonId + " and tile12.TilePID IN ( \n"
						+ "SELECT vr11.TileID FROM gstm_studio.value_risk_sd_zl11 vr11 INNER JOIN gstm_cone_old.tile_zl11 as tile11 on tile11.TileID = vr11.TileID\n"
						+ "WHERE 1 AND vr11.SeasonID = " + seasonId + " " + coordinateClause + " ) ) ) "
						+ "order by vr.TileID ) MapData";

			}

			LOGGER.info("Pyar mapDataSql -> {}", mapDataSql);

			return gstmDataJdbcTemplate.query(mapDataSql, new BeanPropertyRowMapper<MapData>(MapData.class));

		} catch (Exception ex) {
			throw new DataNotFoundException(ErrorConstants.DATA_FETCH_ERROR,
					"Data Not Found while fetching Pyar Map details -> " + ex.getMessage());
		}
	}

	/**
	 * This method is used to get all <b>Map Data</b> for PVI.
	 * 
	 * @param zoomLevel        the <b>Zoom Level</b> for which
	 *                         <code>{@link MapData}</code> data to be fetched
	 * @param seasonId         the current season id
	 * @param coordinateClause the coordinate clause for adding coordinate condition
	 * @param caseClause       the color case clause for adding color in map data
	 * 
	 * @return <code>List</code> the response in list of {@link MapData}
	 * 
	 * @throws DataNotFoundException
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	public List<MapData> getPviMapDataFromDb(final String zoomLevel, final Integer seasonId,
			final String coordinateClause, final String caseClause) {
		try {
			String mapDataSql = "select TileID, " + caseClause
					+ "MinX, MinY, MaxX, MaxY from ( select pricing_.TileID, "
					+ "IFNULL(pricing_.PviPrimary, IFNULL(pricing_.PviSurvey, pricing_.PviSecondary)) as Pvi,\n"
					+ "tile.MinX, tile.MinY, tile.MaxX, tile.MaxY from pricing_zl" + zoomLevel + " as pricing_ \n"
					+ "inner join gstm_cone_old.tile_zl" + zoomLevel + " as tile on tile.TileID = pricing_.TileID \n"
					+ "where 1 " + coordinateClause + " order by pricing_.TileID ) MapData";
			LOGGER.info("PVI mapDataSql -> {}", mapDataSql);

			return gstmDataJdbcTemplate.query(mapDataSql, new BeanPropertyRowMapper<MapData>(MapData.class));

		} catch (Exception ex) {
			throw new DataNotFoundException(ErrorConstants.DATA_FETCH_ERROR,
					"Data Not Found while fetching PVI Map details -> " + ex.getMessage());
		}
	}

	/**
	 * This method is used to get all <b>Map Data</b> for PYaR.
	 * 
	 * @param zoomLevel        the <b>Zoom Level</b> for which
	 *                         <code>{@link MapData}</code> data to be fetched
	 * @param seasonId         the current season id
	 * @param coordinateClause the coordinate clause for adding coordinate condition
	 * @param caseClause       the color case clause for adding color in map data
	 * 
	 * @return <code>List</code> the response in list of {@link MapData}
	 * 
	 * @throws DataNotFoundException
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	public List<MapData> getRrfvMapDataFromDb(String zoomLevel, final Integer seasonId, final String coordinateClause,
			final String caseClause,final Integer week) {

		System.out.println(" zoom level  " + zoomLevel);

		String trimmedZoomLevel = zoomLevel.replaceAll("^0+(?!$)", "");
		
		try {
			String mapDataSql = "";
			if (Integer.valueOf(zoomLevel) < 13) {
				mapDataSql = "select TileID, " + caseClause + "MinX, MinY, MaxX, MaxY from ( "
						+ "select vr.TileID, IFNULL(vr.RrfvPrimary, IFNULL(vr.RrfvSurvey, vr.RrfvSecondary)) as Rrfv,\n"
						+ "tile.MinX, tile.MinY, tile.MaxX, tile.MaxY from value_risk_sd_zl" + zoomLevel + "_d as vr \n"
						+ "inner join gstm_cone.tile_zl" + trimmedZoomLevel + " as tile on tile.TileID = vr.TileID \n"
						+ "where 1 and vr.Week = " + week + " " + coordinateClause
						+ " order by vr.TileID ) MapData";

			} else if (Integer.valueOf(zoomLevel) == 13) {
				mapDataSql = "SELECT TileID, " + caseClause + "MinX, MinY, MaxX, MaxY from ( "
						+ "SELECT vr.TileID, IFNULL(vr.RrfvPrimary, IFNULL(vr.RrfvSurvey, vr.RrfvSecondary)) as Rrfv,\n"
						+ "tile.MinX, tile.MinY, tile.MaxX, tile.MaxY \n"
						+ "FROM gstm_studio.value_risk_sd_zl13_d as vr INNER JOIN gstm_cone.tile_zl13 as tile on tile.TileId = vr.TileID \n"
						+ "WHERE vr.Week = " + week
						+ " and tile.TilePID IN ( SELECT vr12.TileID FROM gstm_studio.value_risk_sd_zl12_d as vr12 \n"
						+ "INNER JOIN gstm_cone.tile_zl12 as tile12 on tile12.TileId = vr12.TileID WHERE vr12.Week = "
						+ week + " and tile12.TilePID IN ( \n"
						+ "SELECT vr11.TileID FROM gstm_studio.value_risk_sd_zl11_d vr11 "
						+ "INNER JOIN gstm_cone.tile_zl11 as tile11 on tile11.TileID = vr11.TileID \n"
						+ "WHERE 1 AND vr11.Week = " + week + " " + coordinateClause
						+ " ) ) order by vr.TileID ) MapData";

			} else if (Integer.valueOf(zoomLevel) > 13) {
				mapDataSql = "SELECT TileID, " + caseClause + "MinX, MinY, MaxX, MaxY from ( \n"
						+ "SELECT vr.TileID, IFNULL(vr.RrfvPrimary, IFNULL(vr.RrfvSurvey, vr.RrfvSecondary)) as Rrfv, tile.MinX, tile.MinY, tile.MaxX, tile.MaxY \n"
						+ "FROM gstm_studio.value_risk_sd_zl" + zoomLevel + "_d as vr INNER JOIN gstm_cone.tile_zl"
						+ zoomLevel + " as tile on tile.TileId = vr.TileID\n" + "WHERE vr.Week = " + week
						+ " and tile.TilePID IN ( \n"
						+ "SELECT vr13.TileID FROM gstm_studio.value_risk_sd_zl13_d as vr13 INNER JOIN gstm_cone.tile_zl13 as tile13 on tile13.TileId = vr13.TileID\n"
						+ "WHERE vr13.Week = " + week + " and tile13.TilePID IN ( \n"
						+ "SELECT vr12.TileID FROM gstm_studio.value_risk_sd_zl12_d as vr12 "
						+ "INNER JOIN gstm_cone.tile_zl12 as tile12 on tile12.TileId = vr12.TileID\n"
						+ "WHERE vr12.Week = " + week + " and tile12.TilePID IN ( \n"
						+ "SELECT vr11.TileID FROM gstm_studio.value_risk_sd_zl11_d vr11 INNER JOIN gstm_cone.tile_zl11 as tile11 on tile11.TileID = vr11.TileID\n"
						+ "WHERE 1 AND vr11.Week = " + week + " " + coordinateClause + " ) ) ) "
						+ "order by vr.TileID ) MapData";

			}

			LOGGER.info("Rrfv mapDataSql -> {}", mapDataSql);

			return gstmDataJdbcTemplate.query(mapDataSql, new BeanPropertyRowMapper<MapData>(MapData.class));

		} catch (Exception ex) {
			throw new DataNotFoundException(ErrorConstants.DATA_FETCH_ERROR,
					"Data Not Found while fetching Rrfv Map details -> " + ex.getMessage());
		}
	}

}
