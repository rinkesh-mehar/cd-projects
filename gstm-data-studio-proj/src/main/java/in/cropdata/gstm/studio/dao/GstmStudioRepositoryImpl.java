package in.cropdata.gstm.studio.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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
import in.cropdata.gstm.studio.exceptions.DbException;
import in.cropdata.gstm.studio.model.DropDownFilters;
import in.cropdata.gstm.studio.model.ParameterData;
import in.cropdata.gstm.studio.model.RangeColorCode;

/**
 * Common Repository for fetching state and parameter filters and other common
 * data.
 * 
 * @since 1.0
 * @author PranaySK
 */

@Repository
public class GstmStudioRepositoryImpl implements GstmStudioRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(GstmStudioRepositoryImpl.class);

	@Autowired
	@Qualifier("gstmNamedJdbcTemplate")
	private NamedParameterJdbcTemplate gstmNamedJdbcTemplate;

	@Autowired
	@Qualifier("gstmDataJdbcTemplate")
	private JdbcTemplate gstmDataJdbcTemplate;

	/**
	 * This method is used to get all <b>State</b> details used for filters in view
	 * page.
	 * 
	 * @return <code>List</code> the response in list of
	 *         <code>{@link DropDownFilters}</code>
	 * 
	 * @throws DbException
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	@Override
	public List<DropDownFilters> getAllStates() {
		try {
			LOGGER.info("getting states...");
			return gstmNamedJdbcTemplate.query(
					"select ID, lower(Name) Name from cdt_master_data.geo_state "
							/* where Status = 'Active' */ + "order by Name",
					new BeanPropertyRowMapper<DropDownFilters>(DropDownFilters.class));

		} catch (DataAccessException ex) {
			throw new DbException(ErrorConstants.DATA_FETCH_ERROR, ex.getMessage());
		}
	}

	/**
	 * This method is used to get all <b>Districts</b> details used for filters in
	 * view page.
	 * 
	 * @param stateCode the <b>State Code</b> for which <code>District</code> data
	 *                  to be fetched
	 * 
	 * @return <code>List</code> the response in list of
	 *         <code>{@link DropDownFilters}</code>
	 * 
	 * @throws DbException
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	@Override
	public List<DropDownFilters> getDistricts(final Integer stateCode) {
		try {
			LOGGER.info("getting districts...");
			MapSqlParameterSource sqlParams = new MapSqlParameterSource();
			sqlParams.addValue("stateCode", stateCode);

			return gstmNamedJdbcTemplate.query(
					"select ID, lower(Name) Name from cdt_master_data.geo_district where "
							/* Status = 'Active' and */ + " StateCode = :stateCode order by Name",
					sqlParams, new BeanPropertyRowMapper<DropDownFilters>(DropDownFilters.class));

		} catch (DataAccessException ex) {
			throw new DbException(ErrorConstants.DATA_FETCH_ERROR, ex.getMessage());
		}
	}

	/**
	 * This method is used to get all <b>Platform</b> details used for filters in
	 * view page.
	 * 
	 * @return <code>List</code> the response in list of
	 *         <code>{@link DropDownFilters}</code>
	 * 
	 * @throws DbException
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	@Override
	public List<DropDownFilters> getAllPlatforms() {
		try {
			LOGGER.info("getting platforms...");
			return gstmNamedJdbcTemplate.query(
					"SELECT distinct(fp.ID) as ID, fp.Name FROM filter_master as fm\n"
							+ "INNER JOIN filter_platform as fp on fp.ID = fm.PlatformID WHERE 1 order by fp.Name",
					new BeanPropertyRowMapper<DropDownFilters>(DropDownFilters.class));

		} catch (DataAccessException ex) {
			throw new DbException(ErrorConstants.DATA_FETCH_ERROR, ex.getMessage());
		}
	}

	/**
	 * This method is used to get all <b>Data type</b> details used for filters in
	 * view page.
	 * 
	 * @param platformId the <b>Platform Id</b> for which <code>Data Types</code>
	 *                   data to be fetched
	 * 
	 * @return <code>List</code> the response in list of
	 *         <code>{@link DropDownFilters}</code>
	 * 
	 * @throws DbException
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	@Override
	public List<DropDownFilters> getAllDataTypes(final Integer platformId) {
		try {
			LOGGER.info("getting datatypes...");
			MapSqlParameterSource sqlParams = new MapSqlParameterSource();
			sqlParams.addValue("platformId", platformId);

			return gstmNamedJdbcTemplate.query(
					"SELECT distinct(fd.ID) as ID, fd.Name FROM filter_master as fm\n"
							+ "INNER JOIN filter_datatype as fd on fd.ID = fm.DataTypeID "
							+ "WHERE 1 and fm.PlatformID = :platformId order by fd.Name",
					sqlParams, new BeanPropertyRowMapper<DropDownFilters>(DropDownFilters.class));

		} catch (DataAccessException ex) {
			throw new DbException(ErrorConstants.DATA_FETCH_ERROR, ex.getMessage());
		}
	}

	/**
	 * This method is used to get all <b>Category</b> details used for filters in
	 * view page.
	 * 
	 * @param dataTypeId the <b>Data Type Id</b> for which <code>Category</code>
	 *                   data to be fetched
	 * 
	 * @return <code>List</code> the response in list of
	 *         <code>{@link DropDownFilters}</code>
	 * 
	 * @throws DbException
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	@Override
	public List<DropDownFilters> getDataTypeCategory(final Integer dataTypeId) {
		try {
			LOGGER.info("getting category...");
			MapSqlParameterSource sqlParams = new MapSqlParameterSource();
			sqlParams.addValue("dataTypeId", dataTypeId);

			return gstmNamedJdbcTemplate.query(
					"SELECT distinct(fc.ID) as ID, fc.Name FROM filter_master as fm\n"
							+ "INNER JOIN filter_category as fc on fc.ID = fm.CategoryID "
							+ "WHERE 1 and fm.DataTypeID = :dataTypeId order by fc.Name",
					sqlParams, new BeanPropertyRowMapper<DropDownFilters>(DropDownFilters.class));

		} catch (DataAccessException ex) {
			throw new DbException(ErrorConstants.DATA_FETCH_ERROR, ex.getMessage());
		}
	}

	/**
	 * This method is used to get all <b>Sub Category</b> details used for filters
	 * in view page.
	 * 
	 * @param categoryId the <b>Category Id</b> for which <code>Sub Category</code>
	 *                   data to be fetched
	 * 
	 * @return <code>List</code> the response in list of
	 *         <code>{@link DropDownFilters}</code>
	 * 
	 * @throws DbException
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	@Override
	public List<DropDownFilters> getSubCategory(final Integer categoryId) {
		try {
			LOGGER.info("getting sub-category...");
			MapSqlParameterSource sqlParams = new MapSqlParameterSource();
			sqlParams.addValue("categoryId", categoryId);

			return gstmNamedJdbcTemplate.query(
					"SELECT distinct(fc.ID) as ID, fc.Name FROM filter_master as fm\n"
							+ "INNER JOIN filter_category as fc on fc.ID = fm.SubCategoryID "
							+ "WHERE 1 and fm.CategoryID = :categoryId order by fc.Name",
					sqlParams, new BeanPropertyRowMapper<DropDownFilters>(DropDownFilters.class));

		} catch (DataAccessException ex) {
			throw new DbException(ErrorConstants.DATA_FETCH_ERROR, ex.getMessage());
		}
	}

	/**
	 * This method is used to get all <b>Parameter</b> details used for filters
	 * 
	 * @param categoryId the <b>Sub Category Id</b> for which
	 *                   <code>Parameters</code> data to be fetched
	 * 
	 * @return <code>List</code> the response in list of
	 *         <code>{@link DropDownFilters}</code>
	 * 
	 * @throws DbException
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	@Override
	public List<DropDownFilters> getSubCategoryParameters(final Integer subCategoryId) {
		try {
			LOGGER.info("getting parameters...");
			MapSqlParameterSource sqlParams = new MapSqlParameterSource();
			sqlParams.addValue("subCategoryId", subCategoryId);

			return gstmNamedJdbcTemplate.query(
					"SELECT distinct(fp.ID) as ID, fp.Name FROM filter_master as fm\n"
							+ "INNER JOIN filter_parameters as fp on fp.ID = fm.ParameterID "
							+ "WHERE 1 and fm.SubCategoryID = :subCategoryId order by fp.Name",
					sqlParams, new BeanPropertyRowMapper<DropDownFilters>(DropDownFilters.class));

		} catch (DataAccessException ex) {
			throw new DbException(ErrorConstants.DATA_FETCH_ERROR, ex.getMessage());
		}
	}

	/**
	 * This method is used to get <b>Parameter Configuration</b> details for given
	 * parameter.
	 * 
	 * @param parameterId the parameterId for which the {@link ParameterData} to be
	 *                    fetched
	 * 
	 * @return <code>ParameterData</code> the parameter configuration data in
	 *         {@link ParameterData}
	 * 
	 * @throws DataNotFoundException
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	public ParameterData getParameterConfigData(final Integer parameterId) {
		try {
			MapSqlParameterSource sqlParams = new MapSqlParameterSource();
			sqlParams.addValue("parameterId", parameterId);

			String paramConfigSql = "select fp.ColumnName as RefrenceFieldName, fp.AggregationMethod, fp.OrderingMethod, fp.Template, fp.Model\n"
					+ "from filter_parameters as fp where fp.ID = :parameterId ";

			return gstmNamedJdbcTemplate.queryForObject(paramConfigSql, sqlParams,
					new BeanPropertyRowMapper<ParameterData>(ParameterData.class));

		} catch (Exception ex) {
			throw new DataNotFoundException(ErrorConstants.DATA_FETCH_ERROR,
					"Parameter Model name does not exist for given parameter -> " + ex.getMessage());
		}
	}

	/**
	 * This method is used to prepare <b>Case Statement</b> for getting the colors
	 * for each of the fetched tiles.
	 * 
	 * @param parameterId    the <b>Parameter Id</b> for which the
	 *                       <code>{@link RangeColorCode}</code> data to be fetched
	 * @param paramAliasName the parameter alias name to be used in case statement
	 * 
	 * @return <code>String</code> the color case statement
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	public String getColorCaseSql(final Integer parameterId, final String paramAliasName) {
		try {
			StringBuilder caseSql = new StringBuilder("");
			MapSqlParameterSource sqlParams = new MapSqlParameterSource();
			sqlParams.addValue("parameterId", parameterId);

			String colorRangeSql = "select br.BandID, br.RangeMinValue, br.RangeMaxValue, cc.ColorCode "
					+ "from band_range as br \n"
					+ "inner join band_color_code as cc on cc.ID = br.BandID and br.Status = 'Active' "
					+ "where br.ParameterID = :parameterId ";
			List<RangeColorCode> colorCodes = gstmNamedJdbcTemplate.query(colorRangeSql, sqlParams,
					new BeanPropertyRowMapper<RangeColorCode>(RangeColorCode.class));

			if (!colorCodes.isEmpty()) {
				caseSql.append("\n( case ");
				for (RangeColorCode rangeColorCode : colorCodes) {
					caseSql.append("when ").append(paramAliasName).append(" between ")
							.append(rangeColorCode.getRangeMinValue()).append(" and ")
							.append(rangeColorCode.getRangeMaxValue()).append(" then '")
							.append(rangeColorCode.getColorCode()).append("'\n");
				}
				caseSql.append("else 'red' end ) Color, ");
			}

			return caseSql.toString();

		} catch (Exception ex) {
			throw new DataNotFoundException(ErrorConstants.DATA_FETCH_ERROR,
					"Band Range and Color codes not configured for " + paramAliasName + " -> " + ex.getMessage());
		}
	}

	/**
	 * This method is used to prepare <b>Coordinate condition Statement</b> for
	 * setting the condition clause in data fetching query.
	 * 
	 * @param coordinates the coordinates of map viewport
	 * 
	 * @return <code>String</code> the coordinate clause statement
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	public String getCoordinatesClause(final Map<String, BigDecimal> coordinates) {
		LOGGER.info("coordinates -> {}", coordinates);
		StringBuilder coordinateClause = new StringBuilder("");

		if (!(coordinates.get("ltX") == null && coordinates.get("ltY") == null && coordinates.get("rbX") == null
				&& coordinates.get("rbY") == null)) {
			coordinateClause = coordinateClause.append("\nand (( tile.MinX between ").append(coordinates.get("ltX"))
					.append(" and ").append(coordinates.get("rbX")).append(" AND tile.MaxY between ")
					.append(coordinates.get("rbY")).append(" and ").append(coordinates.get("ltY"))
					.append(" ) \nOR ( tile.MaxX between ").append(coordinates.get("ltX")).append(" and ")
					.append(coordinates.get("rbX")).append(" and tile.MinY between ").append(coordinates.get("rbY"))
					.append(" and ").append(coordinates.get("ltY")).append(" ))");
		}

		return coordinateClause.toString();
	}

	/**
	 * This method is used to get <b>Season ID</b>.
	 * 
	 * @return <code>ParameterData</code> the parameter configuration data in
	 *         {@link ParameterData}
	 * 
	 * @throws DataNotFoundException
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	public Integer getSeasonId() {
		try {
			String paramConfigSql = "SELECT distinct season_.SeasonID as SeasonID FROM ( "
					+ "select SeasonID, SeasonStartWeek, SeasonEndWeek, \n"
					+ "if(SeasonEndWeek < SeasonStartWeek, SeasonEndWeek + 52, SeasonEndWeek) as CalcSeasonEndWeek\n"
					+ "from cdt_master_data.regional_season ) as season_ \n"
					+ "where if(week(current_date) < 5, week(current_date) + 52, week(current_date)) \n"
					+ "between season_.SeasonStartWeek and season_.CalcSeasonEndWeek and season_.SeasonID < 4 ";

			return gstmDataJdbcTemplate.queryForObject(paramConfigSql, (rs, rowNum) -> rs.getInt("SeasonID"));

		} catch (Exception ex) {
			throw new DataNotFoundException(ErrorConstants.DATA_FETCH_ERROR,
					"Error while fetching Season ID -> " + ex.getMessage());
		}
	}

}
