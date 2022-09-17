package in.cropdata.gstm.studio.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import in.cropdata.gstm.studio.constants.ErrorConstants;
import in.cropdata.gstm.studio.exceptions.DbException;

/**
 * Memory Cache repository to fetch the data from MySQL Memory cache.
 * 
 * @since 1.0
 * @author PranaySK
 */

@Repository
public class CacheLoadingRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(CacheLoadingRepository.class);

	@Autowired
	@Qualifier("mapDataJdbcTemplate")
	private JdbcTemplate mapDataJdbcTemplate;

	@Autowired
	private GstmStudioRepositoryImpl gstmRepository;

	/**
	 * This method is used to drop the existing tables from cache. Then table
	 * structure will be created and the data will be loaded in cache from master
	 * DB.
	 * 
	 * @return void
	 * 
	 * @throws DbException
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	public void loadGstmData() {
		try {
			LOGGER.info("Hello Pranay! Started loading data in cache...");
			String dropTableSql = "drop table if exists geo_village";
			mapDataJdbcTemplate.execute(dropTableSql);
			LOGGER.info("village dropTableSql -> {}", dropTableSql);

			dropTableSql = "drop table if exists geo_tehsil";
			mapDataJdbcTemplate.execute(dropTableSql);
			LOGGER.info("tehsil dropTableSql -> {}", dropTableSql);

			dropTableSql = "drop table if exists geo_district";
			mapDataJdbcTemplate.execute(dropTableSql);
			LOGGER.info("district dropTableSql -> {}", dropTableSql);

			dropTableSql = "drop table if exists geo_state";
			mapDataJdbcTemplate.execute(dropTableSql);
			LOGGER.info("state dropTableSql -> {}", dropTableSql);

			dropTableSql = "drop table if exists zl07";
			mapDataJdbcTemplate.execute(dropTableSql);
			LOGGER.info("zl07 dropTableSql -> {}", dropTableSql);

			dropTableSql = "drop table if exists zl08";
			mapDataJdbcTemplate.execute(dropTableSql);
			LOGGER.info("zl08 dropTableSql -> {}", dropTableSql);

			dropTableSql = "drop table if exists zl09";
			mapDataJdbcTemplate.execute(dropTableSql);
			LOGGER.info("zl09 dropTableSql -> {}", dropTableSql);

			dropTableSql = "drop table if exists zl10";
			mapDataJdbcTemplate.execute(dropTableSql);
			LOGGER.info("zl10 dropTableSql -> {}", dropTableSql);

			dropTableSql = "drop table if exists zl11";
			mapDataJdbcTemplate.execute(dropTableSql);
			LOGGER.info("zl11 dropTableSql -> {}", dropTableSql);

			dropTableSql = "drop table if exists zl12";
			mapDataJdbcTemplate.execute(dropTableSql);
			LOGGER.info("zl12 dropTableSql -> {}", dropTableSql);

			dropTableSql = "drop table if exists zl13";
			mapDataJdbcTemplate.execute(dropTableSql);
			LOGGER.info("zl13 dropTableSql -> {}", dropTableSql);

			dropTableSql = "drop table if exists zl14";
			mapDataJdbcTemplate.execute(dropTableSql);
			LOGGER.info("zl14 dropTableSql -> {}", dropTableSql);
			LOGGER.info("Existing tables dropped...");

			this.createTableStructure();

		} catch (Exception ex) {
			throw new DbException(ErrorConstants.DATA_PREP_ERROR,
					"Error while dropping the existing tables from cache -> " + ex.getMessage());
		}
	}

	/**
	 * This method is used to create table structure in cache DB.
	 * 
	 * @return the count
	 * 
	 * @throws DbException
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	private void createTableStructure() {
		/** Taking out the common parts from the queries */
		final String COORDINATE_COLUMNS = " `MinX` decimal(11,8) DEFAULT NULL, `MinY` decimal(11,8) DEFAULT NULL, \n"
				+ "`MaxX` decimal(11,8) DEFAULT NULL, `MaxY` decimal(11,8) DEFAULT NULL, \n";
		final String COLOR_COLUMNS = " `PyarColor` varchar(7) DEFAULT NULL, `PviColor` varchar(7) DEFAULT NULL, \n"
				+ "`SfiColor` varchar(7) DEFAULT NULL, \n";
		final String TILE_ID_PRIMARY_KEY = " PRIMARY KEY (`TileID`), \n";
		final String DB_ENGINE_CLAUSE = ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";

		try {
			String createTableSql = "CREATE TABLE `geo_village` ( `VillageCode` int NOT NULL DEFAULT '0', \n"
					+ "`VillageName` varchar(255) DEFAULT NULL, `DistrictCode` int NOT NULL DEFAULT '0', \n"
					+ COORDINATE_COLUMNS + "KEY `geo_village_VillageCode_IDX` (`VillageCode`) USING BTREE, \n"
					+ "KEY `geo_village_DistrictCode_IDX` (`DistrictCode`) USING BTREE, \n"
					+ "KEY `geo_village_MinX_IDX` (`MinX`) USING BTREE, \n"
					+ "KEY `geo_village_MinY_IDX` (`MinY`) USING BTREE, \n"
					+ "KEY `geo_village_MaxX_IDX` (`MaxX`) USING BTREE, \n"
					+ "KEY `geo_village_MaxY_IDX` (`MaxY`) USING BTREE \n" + DB_ENGINE_CLAUSE;
			mapDataJdbcTemplate.execute(createTableSql);
			LOGGER.info("village createTableSql -> {} ", createTableSql);

			createTableSql = "CREATE TABLE `geo_tehsil` (`TehsilName` varchar(255) DEFAULT NULL, \n"
					+ "`DistrictCode` int NOT NULL DEFAULT '0', " + COORDINATE_COLUMNS
					+ "KEY `geo_tehsil_DistrictCode_IDX` (`DistrictCode`) USING BTREE, \n"
					+ "KEY `geo_tehsil_MinX_IDX` (`MinX`) USING BTREE, \n"
					+ "KEY `geo_tehsil_MinY_IDX` (`MinY`) USING BTREE, \n"
					+ "KEY `geo_tehsil_MaxX_IDX` (`MaxX`) USING BTREE, \n"
					+ "KEY `geo_tehsil_MaxY_IDX` (`MaxY`) USING BTREE \n" + DB_ENGINE_CLAUSE;
			mapDataJdbcTemplate.execute(createTableSql);
			LOGGER.info("tehsil createTableSql -> {} ", createTableSql);

			createTableSql = "CREATE TABLE `geo_district` ( `DistrictName` varchar(255) DEFAULT NULL, \n"
					+ "`DistrictCode` int NOT NULL DEFAULT '0', `StateCode` int NOT NULL DEFAULT '0', \n"
					+ COORDINATE_COLUMNS + "KEY `geo_district_DistrictCode_IDX` (`DistrictCode`) USING BTREE, \n"
					+ "KEY `geo_district_StateCode_IDX` (`StateCode`) USING BTREE, \n"
					+ "KEY `geo_district_MinX_IDX` (`MinX`) USING BTREE, \n"
					+ "KEY `geo_district_MinY_IDX` (`MinY`) USING BTREE, \n"
					+ "KEY `geo_district_MaxX_IDX` (`MaxX`) USING BTREE, \n"
					+ "KEY `geo_district_MaxY_IDX` (`MaxY`) USING BTREE \n" + DB_ENGINE_CLAUSE;
			mapDataJdbcTemplate.execute(createTableSql);
			LOGGER.info("district createTableSql -> {} ", createTableSql);

			createTableSql = "CREATE TABLE `geo_state` ( `StateName` varchar(255) DEFAULT NULL, \n"
					+ "`StateCode` int NOT NULL DEFAULT '0', \n"
					+ "KEY `geo_state_StateCode_IDX` (`StateCode`) USING BTREE \n" + DB_ENGINE_CLAUSE;
			mapDataJdbcTemplate.execute(createTableSql);
			LOGGER.info("state createTableSql -> {} ", createTableSql);

			createTableSql = "CREATE TABLE `zl07` ( `TileID` bigint(20) NOT NULL, \n" + COORDINATE_COLUMNS
					+ COLOR_COLUMNS + "PRIMARY KEY (`TileID`) \n" + DB_ENGINE_CLAUSE;
			mapDataJdbcTemplate.execute(createTableSql);
			LOGGER.info("zl07 createTableSql -> {} ", createTableSql);

			createTableSql = "CREATE TABLE `zl08` ( `TileID` bigint(20) NOT NULL, \n" + COORDINATE_COLUMNS
					+ COLOR_COLUMNS + "PRIMARY KEY (`TileID`) \n" + DB_ENGINE_CLAUSE;
			mapDataJdbcTemplate.execute(createTableSql);
			LOGGER.info("zl08 createTableSql -> {} ", createTableSql);

			createTableSql = "CREATE TABLE `zl09` ( `TileID` bigint(20) NOT NULL, \n" + COORDINATE_COLUMNS
					+ COLOR_COLUMNS + TILE_ID_PRIMARY_KEY + "KEY `zl09_MinX_IDX` (`MinX`) USING BTREE, \n"
					+ "KEY `zl09_MinY_IDX` (`MinY`) USING BTREE, KEY `zl09_MaxX_IDX` (`MaxX`) USING BTREE, \n"
					+ "KEY `zl09_MaxY_IDX` (`MaxY`) USING BTREE \n" + DB_ENGINE_CLAUSE;
			mapDataJdbcTemplate.execute(createTableSql);
			LOGGER.info("zl09 createTableSql -> {} ", createTableSql);

			createTableSql = "CREATE TABLE `zl10` ( `TileID` bigint(20) NOT NULL, \n" + COORDINATE_COLUMNS
					+ COLOR_COLUMNS + TILE_ID_PRIMARY_KEY + "KEY `zl10_MinX_IDX` (`MinX`) USING BTREE, \n"
					+ "KEY `zl10_MinY_IDX` (`MinY`) USING BTREE, KEY `zl10_MaxX_IDX` (`MaxX`) USING BTREE, \n"
					+ "KEY `zl10_MaxY_IDX` (`MaxY`) USING BTREE \n" + DB_ENGINE_CLAUSE;
			mapDataJdbcTemplate.execute(createTableSql);
			LOGGER.info("zl10 createTableSql -> {} ", createTableSql);

			createTableSql = "CREATE TABLE `zl11` ( `TileID` bigint(20) NOT NULL, \n" + COORDINATE_COLUMNS
					+ COLOR_COLUMNS + TILE_ID_PRIMARY_KEY + "KEY `zl11_MinX_IDX` (`MinX`) USING BTREE, \n"
					+ "KEY `zl11_MinY_IDX` (`MinY`) USING BTREE, KEY `zl11_MaxX_IDX` (`MaxX`) USING BTREE, \n"
					+ "KEY `zl12_MaxY_IDX` (`MaxY`) USING BTREE \n" + DB_ENGINE_CLAUSE;
			mapDataJdbcTemplate.execute(createTableSql);
			LOGGER.info("zl11 createTableSql -> {} ", createTableSql);

			createTableSql = "CREATE TABLE `zl12` ( `TileID` bigint(20) NOT NULL, \n" + COORDINATE_COLUMNS
					+ COLOR_COLUMNS + TILE_ID_PRIMARY_KEY + "KEY `zl12_MinX_IDX` (`MinX`) USING BTREE, \n"
					+ "KEY `zl12_MinY_IDX` (`MinY`) USING BTREE, KEY `zl12_MaxX_IDX` (`MaxX`) USING BTREE, \n"
					+ "KEY `zl12_MaxY_IDX` (`MaxY`) USING BTREE \n" + DB_ENGINE_CLAUSE;
			mapDataJdbcTemplate.execute(createTableSql);
			LOGGER.info("zl12 createTableSql -> {} ", createTableSql);

			createTableSql = "CREATE TABLE `zl13` ( `TileID` bigint(20) NOT NULL, \n" + COORDINATE_COLUMNS
					+ COLOR_COLUMNS + TILE_ID_PRIMARY_KEY + "KEY `zl13_MinX_IDX` (`MinX`) USING BTREE, \n"
					+ "KEY `zl13_MinY_IDX` (`MinY`) USING BTREE, KEY `zl13_MaxX_IDX` (`MaxX`) USING BTREE, \n"
					+ "KEY `zl13_MaxY_IDX` (`MaxY`) USING BTREE \n" + DB_ENGINE_CLAUSE;
			mapDataJdbcTemplate.execute(createTableSql);
			LOGGER.info("zl13 createTableSql -> {} ", createTableSql);

			createTableSql = "CREATE TABLE `zl14` ( `TileID` bigint(20) NOT NULL, \n" + COORDINATE_COLUMNS
					+ COLOR_COLUMNS + TILE_ID_PRIMARY_KEY + "KEY `zl14_MinX_IDX` (`MinX`) USING BTREE, \n"
					+ "KEY `zl14_MinY_IDX` (`MinY`) USING BTREE, KEY `zl14_MaxX_IDX` (`MaxX`) USING BTREE, \n"
					+ "KEY `zl14_MaxY_IDX` (`MaxY`) USING BTREE \n" + DB_ENGINE_CLAUSE;
			mapDataJdbcTemplate.execute(createTableSql);
			LOGGER.info("zl14 createTableSql -> {} ", createTableSql);
			LOGGER.info("Tables created...");

			this.loadData();

		} catch (Exception ex) {
			throw new DbException(ErrorConstants.DATA_PREP_ERROR,
					"Error while creating tables in cache -> " + ex.getMessage());
		}
	}

	/**
	 * This method is used to insert data in cache tables by fetching the data from
	 * master DB.
	 * 
	 * @return the count of last added rows
	 * 
	 * @throws DbException
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	private int loadData() {
		try {
			/** Taking out the common parts from the queries */
			final String SELECT_MAP_DATA_SQL = "select TileID, MinX, MinY, MaxX, MaxY, ( case when Pyar between 0.00 and 5.00 then '#36a605' \n"
					+ "when Pyar between 5.01 and 10.00 then '#4fe10f' \n"
					+ "when Pyar between 10.01 and 16.00 then '#95ff0b' \n"
					+ "when Pyar between 16.01 and 20.00 then '#f4ff00' \n"
					+ "when Pyar between 20.01 and 25.00 then '#ffd100' \n"
					+ "when Pyar between 25.01 and 30.00 then '#ff9c00' \n"
					+ "when Pyar between 30.01 and 40.00 then '#e87e0c' \n"
					+ "when Pyar between 40.01 and 50.00 then '#e84f0c' \n"
					+ "when Pyar between 50.01 and 60.00 then '#d02915' \n"
					+ "when Pyar between 60.01 and 100.00 then '#a01a0a' else 'red' end ) PyarColor \n"
					+ "from ( select vr.TileID, IFNULL(vr.PyarPrimary, IFNULL(vr.PyarSurvey, vr.RrfvSecondary)) as Pyar, \n";
			final String WHERE_CLAUSE = "where 1 and vr.SeasonID = " + gstmRepository.getSeasonId()
					+ " order by vr.TileID ) MapData \n";
			final String DUPLICATE_KEY_CLAUSE = "ON DUPLICATE KEY UPDATE MinX = values(MinX), MinY = values(MinY), MaxX = values(MaxX), "
					+ "MaxY = values(MaxY), PyarColor = values(PyarColor) ";
			int count = 0;

			String insertSql = "insert into geo_village (VillageName, DistrictCode, MinX, MinY, MaxX, MaxY) \n"
					+ "SELECT gv.Name as VillageName, gv.DistrictCode, tile.MinX, tile.MinY, tile.MaxX, tile.MaxY \n"
					+ "from cdt_master_data.geo_village gv \n"
					+ "inner join cdt_master_data.geo_village_zl14_mapping gvzm on gvzm.VillageCode = gv.VillageCode \n"
					+ "inner join gstm_cone_old.tile_zl14 tile on tile.TileID = gvzm.Zl14TileID \n"
					+ "where gv.VillageTileID is not NULL and gv.Name <> '0' ";
			count = mapDataJdbcTemplate.update(insertSql);
			LOGGER.info("village insertSql -> {} ::: insert count -> {} ", insertSql, count);

			insertSql = "insert into geo_tehsil (TehsilName, DistrictCode, MinX, MinY, MaxX, MaxY) \n"
					+ "SELECT gt.Name as TehsilName, gt.DistrictCode, tile.MinX, tile.MinY, tile.MaxX, tile.MaxY \n"
					+ "from cdt_master_data.geo_tehsil gt \n"
					+ "inner join cdt_master_data.geo_tehsil_zl12_mapping gtzm on gtzm.TehsilCode = gt.TehsilCode \n"
					+ "inner join gstm_cone_old.tile_zl12 tile on tile.TileID = gtzm.Zl12TileID \n"
					+ "where gt.TehsilCode is not NULL and gt.Name <> '0' ";
			count = mapDataJdbcTemplate.update(insertSql);
			LOGGER.info("tehsil insertSql -> {} ::: insert count -> {} ", insertSql, count);

			insertSql = "insert into geo_district (DistrictName, DistrictCode, StateCode, MinX, MinY, MaxX, MaxY) \n"
					+ "SELECT gd.Name as DistrictName, gd.DistrictCode, gd.StateCode, tile.MinX, tile.MinY, tile.MaxX, tile.MaxY \n"
					+ "from cdt_master_data.geo_district gd \n"
					+ "inner join cdt_master_data.geo_district_zl09_mapping gdzm on gdzm.DistrictCode = gd.DistrictCode \n"
					+ "inner join gstm_cone_old.tile_zl09 tile on tile.TileID = gdzm.Zl09TileID \n"
					+ "where gd.DistrictCode is not NULL and gd.Name <> '0' ";
			count = mapDataJdbcTemplate.update(insertSql);
			LOGGER.info("district insertSql -> {} ::: insert count -> {} ", insertSql, count);

			insertSql = "insert into geo_state (StateName, StateCode) \n"
					+ "SELECT gs.Name as StateName, gs.StateCode from cdt_master_data.geo_state gs "
					+ "where gs.StateCode is not NULL and gs.Name <> '0' ";
			count = mapDataJdbcTemplate.update(insertSql);
			LOGGER.info("state insertSql -> {} ::: insert count -> {} ", insertSql, count);

			insertSql = "INSERT into zl09 (TileID, MinX, MinY, MaxX, MaxY, PyarColor) \n" + SELECT_MAP_DATA_SQL
					+ "tile.MinX, tile.MinY, tile.MaxX, tile.MaxY from gstm_studio.value_risk_sd_zl09_d as vr \n"
					+ "inner join gstm_cone_old.tile_zl09 as tile on tile.TileID = vr.TileID \n" + WHERE_CLAUSE
					+ DUPLICATE_KEY_CLAUSE;
			count = mapDataJdbcTemplate.update(insertSql);
			LOGGER.info("zl09 insertSql -> {} ::: insert count -> {} ", insertSql, count);

			insertSql = "INSERT into zl10 (TileID, MinX, MinY, MaxX, MaxY, PyarColor) \n" + SELECT_MAP_DATA_SQL
					+ "tile.MinX, tile.MinY, tile.MaxX, tile.MaxY from gstm_studio.value_risk_sd_zl10_d as vr \n"
					+ "inner join gstm_cone_old.tile_zl10 as tile on tile.TileID = vr.TileID \n" + WHERE_CLAUSE
					+ DUPLICATE_KEY_CLAUSE;
			count = mapDataJdbcTemplate.update(insertSql);
			LOGGER.info("zl10 insertSql -> {} ::: insert count -> {} ", insertSql, count);

			insertSql = "INSERT into zl11 (TileID, MinX, MinY, MaxX, MaxY, PyarColor) \n" + SELECT_MAP_DATA_SQL
					+ "tile.MinX, tile.MinY, tile.MaxX, tile.MaxY from gstm_studio.value_risk_sd_zl11_d as vr \n"
					+ "inner join gstm_cone_old.tile_zl11 as tile on tile.TileID = vr.TileID \n" + WHERE_CLAUSE
					+ DUPLICATE_KEY_CLAUSE;
			count = mapDataJdbcTemplate.update(insertSql);
			LOGGER.info("zl11 insertSql -> {} ::: insert count -> {} ", insertSql, count);

			insertSql = "INSERT into zl12 (TileID, MinX, MinY, MaxX, MaxY, PyarColor) \n" + SELECT_MAP_DATA_SQL
					+ "tile.MinX, tile.MinY, tile.MaxX, tile.MaxY from gstm_studio.value_risk_sd_zl12_d as vr \n"
					+ "inner join gstm_cone_old.tile_zl12 as tile on tile.TileID = vr.TileID \n" + WHERE_CLAUSE
					+ DUPLICATE_KEY_CLAUSE;
			count = mapDataJdbcTemplate.update(insertSql);
			LOGGER.info("zl12 insertSql -> {} ::: insert count -> {} ", insertSql, count);

			insertSql = "INSERT into zl13 (TileID, MinX, MinY, MaxX, MaxY, PyarColor) \n" + SELECT_MAP_DATA_SQL
					+ "tile.MinX, tile.MinY, tile.MaxX, tile.MaxY from gstm_studio.value_risk_sd_zl13_d as vr \n"
					+ "inner join gstm_cone_old.tile_zl13 as tile on tile.TileID = vr.TileID \n" + WHERE_CLAUSE
					+ DUPLICATE_KEY_CLAUSE;
			count = mapDataJdbcTemplate.update(insertSql);
			LOGGER.info("zl13 insertSql -> {} ::: insert count -> {} ", insertSql, count);

			insertSql = "INSERT into zl14 (TileID, MinX, MinY, MaxX, MaxY, PyarColor) \n" + SELECT_MAP_DATA_SQL
					+ "tile.MinX, tile.MinY, tile.MaxX, tile.MaxY from gstm_studio.value_risk_sd_zl14_d as vr \n"
					+ "inner join gstm_cone_old.tile_zl14 as tile on tile.TileID = vr.TileID \n" + WHERE_CLAUSE
					+ DUPLICATE_KEY_CLAUSE;
			count = mapDataJdbcTemplate.update(insertSql);
			LOGGER.info("zl14 insertSql -> {} ::: insert count -> {} ", insertSql, count);
			LOGGER.info("Data insertion in cache tables completed...");

			return count;

		} catch (Exception ex) {
			throw new DbException(ErrorConstants.DATA_PREP_ERROR,
					"Error while inserting data in cache -> " + ex.getMessage());
		}
	}

}
