package in.cropdata.cdtmasterdata.util;

import in.cropdata.cdtmasterdata.dao.ApiUtilDao;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ApiUtilService {

	private static final Logger logger = LoggerFactory.getLogger(ApiUtilService.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private ApiUtilDao apiUtilDao;

	public String downloadExcelFormat(String tableName) {

		final String excelName = tableName;
		List<String> columnNames = getColumnNamesForTable(tableName);
		if (columnNames.isEmpty()) {
			throw new RuntimeException(tableName + " does not exist in database.");
		}
		logger.info("Creating excel Sheet for table : " + tableName + " with Columns: " + columnNames);

		try (final XSSFWorkbook workbook = new XSSFWorkbook()) {

//			final Workbook workbook = new XSSFWorkbook();

			Sheet sheet = workbook.createSheet(excelName);
//	    sheet.setColumnWidth(0, 6000);
//	    sheet.setColumnWidth(1, 4000);

			Row header = sheet.createRow(0);

			CellStyle headerStyle = workbook.createCellStyle();
//	    headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
//			headerStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
//			headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			XSSFFont font = workbook.createFont();
			font.setFontName("Arial");
//	    font.setFontHeightInPoints((short) 16);
			font.setBold(true);
			headerStyle.setFont(font);

			int i = 0;
			for (String columnName : columnNames) {
				Cell headerCell = header.createCell(i++);
				headerCell.setCellValue(columnName);
				headerCell.setCellStyle(headerStyle);

				// set text format for hscode column
				if ("HsCodeID.agri_hs_code.HSCode".equals(columnName)) {
					XSSFDataFormat format = workbook.createDataFormat();
					XSSFCellStyle style = workbook.createCellStyle();
					style.setDataFormat(format.getFormat("@"));
					sheet.setDefaultColumnStyle(i, style);
				}

				sheet.autoSizeColumn(i);
			} // for all columns

			File tempFile = File.createTempFile(excelName, ".xlsx");
			FileOutputStream outputStream = new FileOutputStream(tempFile);
			workbook.write(outputStream);
//			workbook.close();

			logger.info("Excel sheet created, transfering with response...");
//	    httpServletResponse.reset();
//	    InputStream in = new FileInputStream(tempFile);
//	    httpServletResponse.setContentType("application/vnd.ms-excel");
//	    httpServletResponse.addHeader("content-disposition", "attachment; filename=" + excelName + ".xlsx");
//	    ServletOutputStream outstream = httpServletResponse.getOutputStream();
//	    IOUtils.copyLarge(in, outstream);
//	    outstream.flush();
//	    

			return tempFile.getAbsolutePath();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}// downloadExcelFormat

	public ResponseMessage readExcel(MultipartFile excelFile) {
		ResponseMessage response = new ResponseMessage();
		response.setSuccess(true);
		String tableName = FilenameUtils.removeExtension(excelFile.getOriginalFilename());
		try {
			String tableSQL = "SELECT count(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = N'" + tableName + "'";
			int tableCnt = jdbcTemplate.queryForObject(tableSQL, Integer.class);
			if (tableCnt <= 0) {
				response.setSuccess(false);
				response.setError("Excel file name changed, unable to INSERT records.");
				return response;
			}
		} catch (Exception e) {
			response.setSuccess(false);
			response.setError(e.getLocalizedMessage());
			return response;
		}

		try (InputStream excelFileStream = excelFile.getInputStream();
			 XSSFWorkbook workbook = new XSSFWorkbook(excelFileStream);) {

			XSSFSheet sheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = sheet.iterator();
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

			Map<Integer, String[]> fKeys = new HashMap<>();
			Map<Integer, String> dataLabelMap = new HashMap<>();
			Map<String, Integer> paramDataMap = new HashMap<>();
			Map<String, String> idMap = new HashMap<>();

			int rowCount = 0;
			int columnCount = 0;
			StringBuilder insertQuery = null;
			for (Row row : sheet) {
				if (rowCount == 0) {
					columnCount = row.getLastCellNum();
					Row headerRow = iterator.next();
					Iterator<Cell> cellIteratorHeader = headerRow.iterator();
					insertQuery = new StringBuilder("INSERT INTO " + tableName + " (");
					while (cellIteratorHeader.hasNext()) {
						Cell currentCell = cellIteratorHeader.next();
						if (currentCell.getCellType() == CellType.STRING) {

							String columnHeader = currentCell.getStringCellValue();
							if (columnHeader != null && !columnHeader.isBlank()) {
								logger.debug("checking if header contains . : {}", columnHeader.contains("."));
								if (columnHeader.contains(".")) {
									String[] headerData = columnHeader.split("\\.");
									logger.debug("Header Data: {}", Arrays.toString(headerData));
									fKeys.put(currentCell.getColumnIndex(), headerData);
									insertQuery.append(headerData[0] + ",");
								} else {
									dataLabelMap.put(currentCell.getColumnIndex(), columnHeader);
									insertQuery.append(columnHeader + ",");
								}
							} // if header not null
						} // if string value
					} // while header row cells

					if ("agri_district_commodity_stress".equals(tableName)) {
						System.out.println("agri_district_commodity_stress param data is  ->" + paramDataMap);
						insertQuery.append("Phenophases,");
					}

					insertQuery.replace(insertQuery.lastIndexOf(","), insertQuery.lastIndexOf(",") + 1, ") VALUES");

				} else {// else data rows
					insertQuery.append(" (");
					for (int cn = 0; cn < columnCount; cn++) {
						/**
						 * If the cell is missing from the file, generate a blank one (Works by
						 * specifying a MissingCellPolicy)
						 */
						Cell currentCell = row.getCell(cn, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
						logger.info("Evaluate CellType: {}", evaluator.evaluateFormulaCell(currentCell));
						String cellValue = null;
						if (currentCell != null) {

							int cellIndex = currentCell.getColumnIndex();
							logger.info("Foreign Keys: {}", fKeys.keySet());
							Set<Integer> foreignKeys = fKeys.keySet();
							boolean found = foreignKeys.stream().anyMatch(n -> n == cellIndex);
							logger.info("Data of fKeys : {}", found);
							logger.info("Row: {} \t Cell Index: {} \t TYPE: {} \t Value: {}", row.getRowNum(),
									cellIndex, currentCell.getCellType(), cellValue);

							switch (currentCell.getCellType()) {

							case BOOLEAN:
								cellValue = "" + currentCell.getBooleanCellValue();
								break;

							case NUMERIC:
								if (DateUtil.isCellDateFormatted(currentCell)) {
									SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
									cellValue = String.valueOf(dateFormat.format(currentCell.getDateCellValue()));
								} else if (currentCell.toString().contains(".")){
									cellValue = String.valueOf(new BigDecimal(currentCell.toString()));
								} else {
									cellValue = String.valueOf(new BigDecimal(currentCell.toString()).intValue());
								}
								break;

							case STRING:
								cellValue = "" + currentCell.getStringCellValue();
								break;

							case BLANK:
								if (found) {
									String respError = "Data does not exist for Row:" + (row.getRowNum() + 1)
											+ " \n Column:" + (currentCell.getColumnIndex() + 1);
									response.setError("Record Insertion Failed...!!! \n" + respError);
									response.setSuccess(false);
									return response;
								} else {
									cellValue = null;
								}
								break;

							case ERROR:
								cellValue = null;
								break;
							// CELL_TYPE_FORMULA will never occur
							case FORMULA:
								cellValue = null;
								break;
							default:
								cellValue = null;
								break;
							}

							if (cellValue != null && found) {
								String[] fKeyDependents = fKeys.get(cellIndex);
								logger.info("Getting FKeys value for {} - {}", fKeyDependents[0], cellValue);

								if ("agri_district_commodity_stress".equals(tableName)) {

									 if ("agri_commodity".equals(fKeyDependents[1])) {
										cellValue = this.getForeginID(fKeyDependents, cellValue, idMap);
										paramDataMap.put("CommodityID", Integer.valueOf(cellValue));

									} else if ("StartPhenophaseID".equals(fKeyDependents[0])) {
										cellValue = this.getForeginID(fKeyDependents, cellValue, idMap);
										paramDataMap.put("phenophaseStart", Integer.valueOf(cellValue));

									} else if ("EndPhenophaseID".equals(fKeyDependents[0])) {
										cellValue = this.getForeginID(fKeyDependents, cellValue, idMap);
										paramDataMap.put("phenophaseEnd", Integer.valueOf(cellValue));

									} else
										cellValue = getForeginID(fKeyDependents, cellValue, idMap);

								} else if ("agri_variety".equals(tableName)) {

									if ("agri_commodity".equals(fKeyDependents[1])) {

										cellValue = this.getSQCForeignIdDataForAgriVariety(fKeyDependents, cellValue, null);
										paramDataMap.put("CommodityID", Integer.valueOf(cellValue));
									}
									if ("agri_hs_code".equals(fKeyDependents[1])) {
										Integer commodityId = paramDataMap.get("CommodityID");
										cellValue = this.getSQCForeignIdDataForAgriVariety(fKeyDependents, cellValue, commodityId);

										paramDataMap.clear();
									}
								} else {
									if (fKeyDependents[0].equalsIgnoreCase("CommodityID")) {
										cellValue = getForeginID(fKeyDependents, cellValue, idMap);
										idMap.put("CommodityID", cellValue);
									} else if (fKeyDependents[0].equalsIgnoreCase("StressID")) {
										cellValue = getForeginID(fKeyDependents, cellValue, idMap);
									} else
										cellValue = getForeginID(fKeyDependents, cellValue, idMap);
								}

								if (cellValue.length() > 20) {
									response.setError("Record Insertion Failed...!!! \n " + cellValue);
									response.setSuccess(false);
									return response;
								}
							} // if found in foreignKeys
						} // if currentCell not null

						if (cellValue == null) {
							insertQuery.append("DEFAULT,");
						} else {
							insertQuery.append("'" + cellValue + "',");
						}

					} // for all columns

					if ("agri_district_commodity_stress".equals(tableName)) {

						List<String> phenophasePK = apiUtilDao.getAgriPhenophaseID(paramDataMap.get("phenophaseStart"),
								paramDataMap.get("phenophaseEnd"), paramDataMap.get("CommodityID"));

						String phenophases = apiUtilDao.getPhenophase(phenophasePK, paramDataMap.get("CommodityID"));

						insertQuery.append("'" + phenophases + "',");
					}
					insertQuery.replace(insertQuery.lastIndexOf(","), insertQuery.lastIndexOf(","), ")");
				} // else for data rows
				rowCount++;
			} // for all rows

			insertQuery.replace(insertQuery.lastIndexOf(","), insertQuery.lastIndexOf(",") + 1, ";");
			logger.info("Generated SQL: {}", insertQuery);

			if (rowCount == 1) {

				response.setError("Excel is Empty!");
				response.setSuccess(false);
				logger.error("Unable to Insert records.");

				return response;
			}

			int updateCount = insertRecordsInDB(insertQuery.toString());

			if (updateCount > 0) {
				logger.info("{} - Records inserted successfully.", updateCount);
				response.setMessage(updateCount + " - Records Inserted Successfully.");
				response.setSuccess(true);
			} else {
				response.setError("Record Insertion Failed...!!!");
				response.setSuccess(false);
				logger.error("Unable to Insert records.");
			}
		} catch (FileNotFoundException e) {
			response.setError(e.getMessage());
			response.setMessage(e.getMessage());
			response.setSuccess(false);
			logger.error("Record Insertion Failed...!!! FILE NOT FOUND", e);
		} catch (IOException e) {
			response.setError(e.getMessage());
			response.setMessage(e.getMessage());
			response.setSuccess(false);
			logger.error("Record Insertion Failed...!!! IOException ", e);
		} catch (RuntimeException e) {
			response.setError(e.getMessage());
			response.setMessage(e.getMessage());
			response.setSuccess(false);
			logger.error("Record Insertion Failed...!!! RuntimeException", e);
		} catch (Exception e) {
			response.setError(e.getMessage());
			response.setMessage(e.getMessage());
			response.setSuccess(false);
			logger.error("Record Insertion Failed...!!! Exception", e);
		}
		return response;
	}// readExcel

	@Transactional
	private int insertRecordsInDB(String inserQuery) {
		int updateCount = 0;
		try {
			updateCount = jdbcTemplate.update(inserQuery);
			return updateCount;
		} catch (Exception e) {
			String message = null;
			if (e instanceof SQLException) {
				if (ignoreSQLException(((SQLException) e).getSQLState()) == false) {

					e.printStackTrace(System.err);
					message += "SQLState: " + ((SQLException) e).getSQLState() + "\n";

					message += "Error Code: " + ((SQLException) e).getErrorCode() + "\n";

					message += "Message: " + e.getMessage();

					logger.error("ERROR :->: {}", message);
				}
			} // if SQLException
			if (message != null) {
				throw new RuntimeException(message);
			} else {
				throw new RuntimeException(e.getCause());
			}
		} // catch
	}// insertRecordsInDB

	public static boolean ignoreSQLException(String sqlState) {

		if (sqlState == null) {
			System.out.println("The SQL state is not defined!");
			logger.error("The SQL state is not defined!");
			return false;
		}

		// X0Y32: Jar file already exists in schema
		if (sqlState.equalsIgnoreCase("X0Y32"))
			return true;

		// 42Y55: Table already exists in schema
		if (sqlState.equalsIgnoreCase("42Y55"))
			return true;

		return false;
	}// ignoreSQLException

	private String getForeginID(String[] fkeyDependents, String cellValue, Map<String, String> idMap) {
		try {
			String sql = "";

			if ("geo_region".equals(fkeyDependents[1])) {
				sql = "SELECT RegionID FROM " + fkeyDependents[1] + " WHERE " + fkeyDependents[2] + "='" + cellValue
						+ "' and Status = 'Active';";
			} else if ("geo_subregion".equals(fkeyDependents[1])) {
				sql = "SELECT SubRegionID FROM " + fkeyDependents[1] + " WHERE " + fkeyDependents[2] + "='" + cellValue
						+ "' ;";
			} else if ("geo_state".equals(fkeyDependents[1])) {
				sql = "SELECT StateCode FROM " + fkeyDependents[1] + " WHERE " + fkeyDependents[2] + "='" + cellValue
						+ "' and Status = 'Active' ;";
			} else if ("geo_district".equals(fkeyDependents[1])) {
				sql = "SELECT DistrictCode FROM " + fkeyDependents[1] + " WHERE " + fkeyDependents[2] + "='" + cellValue
						+ "' and Status = 'Active' ;";
			} else if ("agri_commodity_stress".equals(fkeyDependents[1])) {
				sql = "select acs.ID from agri_district_commodity_stress acs\n" +
						"    inner join agri_stress ass on acs.StressID = ass.ID\n" +
						"where acs.CommodityID = "+ idMap.get("CommodityID") +" and ass.Name = '"+ cellValue +"';";
			} else {
				sql = "SELECT ID FROM " + fkeyDependents[1] + " WHERE " + fkeyDependents[2] + "='" + cellValue + "' ;";
			}
			logger.info("QUERY: {}", sql);
			return jdbcTemplate.queryForObject(sql, String.class);

		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			return String.format("Data Does not exist for %1$s - %2$s", fkeyDependents[0], cellValue);
		}
	}// getForeginID

//	private String getSQCForeignIdData(String[] fKeyDependents, String cellValue, Integer stateCode,
//			Integer commodityId) {
//		try {
//			String sql = "";
//
//			if ("geo_state".equals(fKeyDependents[1])) {
//				sql = "SELECT StateCode FROM " + fKeyDependents[1] + " WHERE " + fKeyDependents[2] + "='" + cellValue
//						+ "' ";
//			} else if ("agri_commodity".equals(fKeyDependents[1])) {
//				sql = "SELECT DISTINCT rc.CommodityID FROM regional_commodity rc "
//						+ "inner join agri_commodity ac on ac.ID = rc.CommodityID and ac.Status = 'Active' "
//						+ "where rc.StateCode = " + stateCode + " and ac.Name = '" + cellValue + "' ";
//
//			} else if ("agri_variety".equals(fKeyDependents[1])) {
//				sql = "SELECT DISTINCT rv.VarietyID FROM regional_variety rv "
//						+ "inner join agri_variety av on av.ID = rv.VarietyID and av.Status = 'Active' "
//						+ "where rv.StateCode = " + stateCode + " and rv.CommodityID = " + commodityId
//						+ " and av.Name = '" + cellValue + "' ";
//
//			} else {
//				sql = "SELECT ID FROM " + fKeyDependents[1] + " WHERE " + fKeyDependents[2] + "='" + cellValue + "' ";
//			}
//			logger.info("QUERY: {}", sql);
//			return jdbcTemplate.queryForObject(sql, String.class);
//
//		} catch (Exception e) {
//			logger.error(e.getLocalizedMessage(), e);
//			return String.format("Data Does not exist for %1$s - %2$s", fKeyDependents[0], cellValue);
//		}
//	}// getForeginID

	private String getSQCForeignIdDataForAgriVariety(String[] fKeyDependents, String cellValue, Integer commodityId) {
		try {
			String sql = "";

			if ("agri_hs_code".equals(fKeyDependents[1])) {
				sql = "select ahc.id from cdt_master_data.agri_hs_code ahc\n" +
						"                       inner join cdt_master_data.agri_commodity ac on ahc.CommodityID = ac.ID\n" +
						"where ahc.HSCode = "+ cellValue +" and ahc.CommodityID = "+ commodityId +"";
			} else if ("agri_commodity".equals(fKeyDependents[1])) {
				sql = "select distinct ac.ID from cdt_master_data.agri_commodity ac\n" +
						"where ac.Name = '"+ cellValue +"' and ac.Status = 'Active'";

			} else {
				sql = "SELECT ID FROM " + fKeyDependents[1] + " WHERE " + fKeyDependents[2] + "='" + cellValue + "' ";
			}
			logger.info("QUERY: {}", sql);
			return jdbcTemplate.queryForObject(sql, String.class);

		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			return String.format("Data Does not exist for %1$s - %2$s", fKeyDependents[0], cellValue);
		}
	}// getForeginID
	public List<String> getColumnNamesForTable(String tableName) {
		List<String> columnNames = new ArrayList<>();
		columnNames.clear();
		if ("agri_allied_activity".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
		} else if ("agri_commodity_agrochemical".equals(tableName)) {
			columnNames.clear();
			columnNames.add("CommodityID.agri_commodity.Name");
			columnNames.add("StressTypeID.agri_stress_type.Name");
			columnNames.add("AgrochemicalTypeID.agri_agrochemical_type.Name");
			columnNames.add("AgrochemicalID.agri_agrochemical.Name");
			columnNames.add("Name");
			columnNames.add("CIBRCApproved");
			columnNames.add("WaitingPeriod");
		} else if ("agri_agrochemical_application_type".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
		} else if ("agri_agrochemical_brand".equals(tableName)) {
			columnNames.clear();
			columnNames.add("AgrochemicalID.agri_commodity_agrochemical.Name");
			columnNames.add("CompanyID.general_company.Name");
			columnNames.add("Name");
			columnNames.add("AgrochemicalStatus");
		} else if ("agri_agrochemical_stress".equals(tableName)) {
			columnNames.clear();
			columnNames.add("AgrochemicalID.agri_commodity_agrochemical.Name");
			columnNames.add("StressID.agri_commodity_stress.Name");
		} else if ("agri_agrochemical_type".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
		} else if ("agri_commodity".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
			columnNames.add("ScientificName");
		} else if ("agri_commodity_class".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
		} else if ("agri_commodity_phenophase".equals(tableName)) {
			columnNames.clear();
			columnNames.add("CommodityID.agri_commodity.Name");
			columnNames.add("PhenophaseID.agri_phenophase.Name");
		} else if ("agri_commodity_plant_part".equals(tableName)) {
			columnNames.clear();
			columnNames.add("CommodityID.agri_commodity.Name");
			columnNames.add("PhenophaseID.agri_phenophase.Name");
			columnNames.add("PlantPartID.agri_plant_part.Name");
		} else if ("agri_conducive_weather".equals(tableName)) {
			columnNames.clear();
			columnNames.add("CommodityID.agri_commodity.Name");
			columnNames.add("StressID.agri_stress.Name");
			columnNames.add("StressTypeID.agri_stress_type.Name");
			columnNames.add("PrimaryWeatherParameterID.general_weather_params.Name");
			columnNames.add("SecondaryWeatherParameterID.general_weather_params.Name");
			columnNames.add("PrimarySpecificationAverage");
			columnNames.add("PrimarySpecificationLower");
			columnNames.add("PrimarySpecificationUpper");
			columnNames.add("PrimaryStressDurationFuture");
			columnNames.add("PrimaryStressDurationPast");
			columnNames.add("SecondarySpecificationAverage");
			columnNames.add("SecondarySpecificationLower");
			columnNames.add("SecondarySpecificationUpper");
			columnNames.add("SecondaryStressDurationFuture");
			columnNames.add("SecondaryStressDurationPast");
		} else if ("agri_disposal_method".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
		} else if ("agri_dose_factors".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
		} else if ("agri_ecosystem".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
		} else if ("agri_farm_machinery".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
		} else if ("agri_favourable_weather".equals(tableName)) {
			columnNames.clear();
			columnNames.add("CommodityID.agri_commodity.Name");
			columnNames.add("PhenophaseID.agri_phenophase.Name");
			columnNames.add("WeatherParameterID.general_weather_params.Name");
			columnNames.add("SpecificationAverage");
			columnNames.add("SpecificationLower");
			columnNames.add("SpecificationUpper");
		} else if ("agri_fertilizer".equals(tableName)) {
			columnNames.clear();
			columnNames.add("CommodityID.agri_commodity.Name");
			columnNames.add("DoseFactorID.agri_dose_factors.Name");
//	    columnNames.add("RegionID.geo_region.Name");
			columnNames.add("SeasonID.agri_season.Name");
			columnNames.add("StateCode.geo_state.Name");
			columnNames.add("UomID.general_uom.Name");
			columnNames.add("Dose");
			columnNames.add("Note");
			columnNames.add("Name");
		} else if ("agri_field_activity".equals(tableName)) {
			columnNames.clear();
			columnNames.add("SeasonID.agri_season.Name");
			columnNames.add("CommodityID.agri_commodity.Name");
			columnNames.add("PhenophaseID.agri_phenophase.Name");
			columnNames.add("Name");
			columnNames.add("Description");
		} else if ("agri_general_commodity".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
		} else if ("agri_health".equals(tableName)) {
			columnNames.clear();
			columnNames.add("CommodityID.agri_commodity.Name");
			columnNames.add("PhenophaseID.agri_phenophase.Name");
			columnNames.add("HealthParameterID.agri_health_parameter.Name");
			columnNames.add("Specification");
		} else if ("agri_health_parameter".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
		} else if ("agri_hs_code".equals(tableName)) {
			columnNames.clear();
			columnNames.add("CommodityID.agri_commodity.Name");
			columnNames.add("CommodityClassID.agri_commodity_class.Name");
			columnNames.add("GeneralCommodityID.agri_general_commodity.Name");
			columnNames.add("HSCode");
			columnNames.add("UomID.general_uom.Name");
			columnNames.add("Description");
		} else if ("agri_irrigation_method".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
		} else if ("agri_meteorological_weeks".equals(tableName)) {
			columnNames.clear();
			columnNames.add("WeekNo");
			columnNames.add("StartDay");
			columnNames.add("StartMonth");
			columnNames.add("EndDay");
			columnNames.add("EndMonth");
		} else if ("agri_phenophase".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
		} else if ("agri_phenophase_duration".equals(tableName)) {
			columnNames.clear();
			columnNames.add("CommodityID.agri_commodity.Name");
			columnNames.add("PhenophaseID.agri_phenophase.Name");
			columnNames.add("SeasonID.agri_season.Name");
			columnNames.add("StateCode.geo_state.Name");
			columnNames.add("VarietyID.agri_variety.Name");
			columnNames.add("PhenophaseStart");
			columnNames.add("PhenophaseEnd");
		} else if ("agri_plant_health_index".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
			columnNames.add("Value");
		} else if ("agri_plant_part".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
		} else if ("agri_quantity_loss_chart".equals(tableName)) {
			columnNames.clear();
			columnNames.add("CommodityID.agri_commodity.Name");
			columnNames.add("PhenophaseID.agri_phenophase.Name");
			columnNames.add("StressID.agri_stress.Name");
			columnNames.add("MinBandValue");
			columnNames.add("MaxBandValue");
			columnNames.add("MinQuantityCorrectionPercent");
			columnNames.add("MaxQuantityCorrectionPercent");
		} else if ("agri_stress_control_measures".equals(tableName)) {
			columnNames.clear();
			columnNames.add("CommodityID.agri_commodity.Name");
			columnNames.add("StressID.agri_stress.Name");
			columnNames.add("StressSeverityID.agri_stress_severity.Name");
			columnNames.add("StressControlMeasureID.agri_control_measures.Name");
		} else if ("agri_season".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
		} else if ("agri_seed_source".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
		} else if ("agri_seed_treatment".equals(tableName)) {
			columnNames.clear();
			columnNames.add("CommodityID.agri_commodity.Name");
			columnNames.add("VarietyID.agri_variety.Name");
			columnNames.add("Name");
			columnNames.add("Dose");
			columnNames.add("UomID.general_uom.Name");
		} else if ("agri_source_of_water".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
		} else if ("agri_standard_quantity_chart".equals(tableName)) {
			columnNames.clear();
			columnNames.add("StateCode.geo_state.Name");
			columnNames.add("CommodityID.agri_commodity.Name");
			columnNames.add("VarietyID.agri_variety.Name");
			columnNames.add("StandardQuantityPerAcre");
			columnNames.add("StandardPositiveVariancePerAcre");
			columnNames.add("StandardPositiveVariancePercent");
			columnNames.add("StandardNegativeVariancePerAcre");
			columnNames.add("StandardNegativeVariancePercent");
		} else if ("agri_district_commodity_stress".equals(tableName)) {
			columnNames.clear();
			columnNames.add("StateCode.geo_state.Name");
			columnNames.add("DistrictCode.geo_district.Name");
			columnNames.add("SeasonID.agri_season.Name");
			columnNames.add("CommodityID.agri_commodity.Name");
			columnNames.add("VarietyID.agri_variety.Name");
			columnNames.add("StartPhenophaseID.agri_phenophase.Name");
			columnNames.add("EndPhenophaseID.agri_phenophase.Name");
			columnNames.add("StressTypeID.agri_stress_type.Name");
			columnNames.add("StressID.agri_stress.Name");
		} else if ("agri_control_measures".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
//		} else if ("agri_stress_control_recommendation".equals(tableName)) {
//			columnNames.clear();
//			columnNames.add("CommodityID.agri_commodity.Name");
//			columnNames.add("StressControlMeasureID.agri_stress_control_measures.Name");
//			columnNames.add("StressTypeID.agri_stress_type.Name");
//			columnNames.add("StressID.agri_commodity_stress.Name");
//			columnNames.add("Instructions");
//			columnNames.add("AgrochemicalID.agri_commodity_agrochemical.Name");
//			columnNames.add("DosePerHectare");
//			columnNames.add("PerHectareUomID.general_uom.Name");
//			columnNames.add("WaterPerHectare");
//			columnNames.add("PerHectareWaterUomID.general_uom.Name");
//			columnNames.add("DosePerAcre");
//			columnNames.add("PerAcreUomID.general_uom.Name");
//			columnNames.add("WaterPerAcre");
//			columnNames.add("PerAcreWaterUomID.general_uom.Name");
//			columnNames.add("AgrochemApplicationID.agri_agrochemical_application_type.Name");
//			columnNames.add("AgroChemicalInstructions");
		} else if ("agri_stress_severity".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
			columnNames.add("Value");
		} else if ("agri_commodity_stress_stage".equals(tableName)) {
			columnNames.clear();
			columnNames.add("CommodityID.agri_commodity.Name");
			columnNames.add("StartPhenophaseID.agri_phenophase.Name");
			columnNames.add("EndPhenophaseID.agri_phenophase.Name");
			columnNames.add("StressID.agri_stress.Name");
			columnNames.add("StageID.agri_stage.Name");
			columnNames.add("Description");
		} else if ("agri_stress_type".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
		} else if ("agri_variety".equals(tableName)) {
			columnNames.clear();
			columnNames.add("CommodityID.agri_commodity.Name");
			columnNames.add("HsCodeID.agri_hs_code.HSCode");
//			columnNames.add("MspGroupID.pricing_msp_group.Name");
			columnNames.add("DomesticRestrictions");
			columnNames.add("InternationalRestrictions");
			columnNames.add("Name");
			columnNames.add("VarietyCode");
		} else if ("agri_variety_quality".equals(tableName)) {
			columnNames.clear();
			columnNames.add("StateCode.geo_state.Name");
			columnNames.add("RegionID.geo_region.Name");
			columnNames.add("CommodityID.agri_commodity.Name");
			columnNames.add("VarietyID.agri_variety.Name");
			columnNames.add("CurrentQualityBandID.agri_band.Name");
			columnNames.add("EstimatedQualityBandID.agri_band.Name");
//			columnNames.add("CurrentQualityBand");
			columnNames.add("AllowableVarianceInQualityBandID.agri_band.Name");
		} else if ("agri_variety_stress".equals(tableName)) {
			columnNames.clear();
			columnNames.add("StateCode.geo_state.Name");
			columnNames.add("CommodityID.agri_commodity.Name");
			columnNames.add("VarietyID.agri_variety.Name");
			columnNames.add("StressTypeID.agri_stress_type.Name");
			columnNames.add("Description");
		} else if ("agri_variety_stress_resistant_stress".equals(tableName)) {
			columnNames.clear();
			columnNames.add("StressID.agri_commodity_stress.Name");
			columnNames.add("VarietyStressID.agri_variety_stress.Name");
		} else if ("agri_variety_stress_susceptible_stress".equals(tableName)) {
			columnNames.clear();
			columnNames.add("StressID.agri_commodity_stress.Name");
			columnNames.add("VarietyStressID.agri_variety_stress.Name");
		} else if ("agri_variety_stress_tolerant_stress".equals(tableName)) {
			columnNames.clear();
			columnNames.add("StressID.agri_commodity_stress.Name");
			columnNames.add("VarietyStressID.agri_variety_stress.Name");
//	} else if ("conducive_weather_model".equals(tableName)) {
//	    columnNames.clear();
//	    columnNames.add("SeasonID.agri_season.Name");
//	    columnNames.add("CommodityID.agri_commodity.Name");
//	    columnNames.add("StressTypeID.agri_stress_type.Name");
//	    columnNames.add("StressID.agri_commodity_stress.Name");
//	    columnNames.add("StressStageID.agri_stress_stage.Name");
//	    columnNames.add("WeatherParamID.general_weather_params.Name");
//	    columnNames.add("MinValue");
//	    columnNames.add("MaxValue");
//	    columnNames.add("InFuture");
//	    columnNames.add("InPast");
		} else if ("farmer_education".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
		} else if ("farmer_enrollment_place".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
		} else if ("farmer_farm_ownership_type".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
		} else if ("farmer_govt_department".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
		} else if ("farmer_govt_official_designation".equals(tableName)) {
			columnNames.clear();
			columnNames.add("DepartmentID.farmer_govt_department.Name");
			columnNames.add("Name");
		} else if ("farmer_id_proof".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
		} else if ("farmer_income_source".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
		} else if ("farmer_insurance_company".equals(tableName)) {
			columnNames.clear();
			columnNames.add("InsuranceTypeID.farmer_insurance_type.Name");
			columnNames.add("Name");
		} else if ("farmer_insurance_type".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
		} else if ("farmer_language".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
		} else if ("farmer_loan_purpose".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
		} else if ("farmer_loan_source".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
		} else if ("farmer_marital_status".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
		} else if ("farmer_mobile_type".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
		} else if ("farmer_proxy_relation_type".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
		} else if ("farmer_vip_designation".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
		} else if ("farmer_vip_status".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
		} else if ("general_bank".equals(tableName)) {
			columnNames.clear();
			columnNames.add("BankCategoryID.general_bank_category.Name");
			columnNames.add("Name");
		} else if ("general_bank_branch".equals(tableName)) {
			columnNames.clear();
			columnNames.add("BankID.general_bank.Name");
			columnNames.add("IFSCCode");
			columnNames.add("Name");
		} else if ("general_bank_category".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
		} else if ("general_calling_status".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
		} else if ("general_company".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
			columnNames.add("Description");
		} else if ("general_drop_reason".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
		} else if ("general_mode_of_payment".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
		} else if ("general_poi_type".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
		} else if ("general_type_of_service".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
		} else if ("general_uom".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
			columnNames.add("Description");
		} else if ("general_weather_params".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
			columnNames.add("Label");
			columnNames.add("UnitID.general_uom.Name");
		} else if ("regional_bank".equals(tableName)) {
			columnNames.clear();
			columnNames.add("StateCode.geo_state.Name");
			columnNames.add("BankID.general_bank.Name");
		} else if ("regional_commodity".equals(tableName)) {
			columnNames.clear();
			columnNames.add("StateCode.geo_state.Name");
			columnNames.add("SeasonID.agri_season.Name");
			columnNames.add("CommodityID.agri_commodity.Name");
//	    columnNames.add("RegionID.geo_region.Name");
			columnNames.add("HarvestRelaxation");
			columnNames.add("MaxRigtsInLot");
			columnNames.add("MinLotSize");
			columnNames.add("TargetValue");
		} else if ("regional_language".equals(tableName)) {
			columnNames.clear();
			columnNames.add("StateCode.geo_state.Name");
			columnNames.add("LanguageID.farmer_language.Name");
//	    columnNames.add("RegionID.geo_region.Name");
//	} else if ("regional_logistic_hub".equals(tableName)) {
//	    columnNames.clear();
//	    columnNames.add("RegionID.geo_region.Name");
//	    columnNames.add("Address");
//	} else if ("regional_logistic_hub_commodity".equals(tableName)) {
//	    columnNames.clear();
//	    columnNames.add("LogisticHubID.regional_logistic_hub.Name");
//	    columnNames.add("RegionalCommodityID.regional_commodity.Name");
		} else if ("regional_season".equals(tableName)) {
			columnNames.clear();
//	    columnNames.add("RegionID.geo_region.Name");
			columnNames.add("StateCode.geo_state.Name");
			columnNames.add("SeasonID.agri_season.Name");
			columnNames.add("StartWeek");
			columnNames.add("EndWeek");
		} else if ("regional_stress".equals(tableName)) {
			columnNames.clear();
//	    columnNames.add("RegionID.geo_region.Name");
			columnNames.add("StateCode.geo_state.Name");
			columnNames.add("StressID.agri_commodity_stress.Name");
		} else if ("regional_variety".equals(tableName)) {
			columnNames.clear();
			columnNames.add("StateCode.geo_state.Name");
//		    columnNames.add("RegionID.geo_region.Name");
			columnNames.add("SeasonID.agri_season.Name");
			columnNames.add("CommodityID.agri_commodity.Name");
			columnNames.add("VarietyID.agri_variety.Name");
			columnNames.add("SowingWeekStart");
			columnNames.add("SowingWeekEnd");
			columnNames.add("HarvestWeekStart");
			columnNames.add("HarvestWeekEnd");
		} else if ("regional_weather".equals(tableName)) {
			columnNames.clear();
			columnNames.add("RegionID.geo_region.Name");
			columnNames.add("WeatherParamID.general_weather_params.Name");
			columnNames.add("WeatherParamValue");
			columnNames.add("WeekNumber");
		} else if ("agri_yield_correction_criteria".equals(tableName)) {
			columnNames.clear();
			columnNames.add("Name");
		} else if ("rl_users".equals(tableName)) {
			columnNames.clear();
			columnNames.add("RegionID");
			columnNames.add("Name");
			columnNames.add("Email");
		} else if ("regional_panchayat_map".equals(tableName)){
			columnNames.clear();
			columnNames.add("RegionID");
			columnNames.add("SubRegionID");
			columnNames.add("VillageCode");
		}else if ("agri_quality_chart".equals(tableName)){
			columnNames.clear();
			columnNames.add("Name");
			columnNames.add("CommodityID.agri_commodity.Name");
			columnNames.add("PhenophaseID.agri_phenophase.Name");
			columnNames.add("HealthParameterID.agri_health_parameter.Name");
			columnNames.add("GradeI");
			columnNames.add("GradeII");
			columnNames.add("GradeIII");
			columnNames.add("MinGradeI");
			columnNames.add("MaxGradeI");
			columnNames.add("MinGradeII");
			columnNames.add("MaxGradeII");
			columnNames.add("MinGradeIII");
			columnNames.add("MaxGradeIII");
		}

		return columnNames;
	}// getColumnNamesForTable

	public Map<String, Object> updateStatus(ActionVO actionVO) {

		Map<String, Object> actionMap = new HashMap<>();

		String ids = actionVO.getIds();// Arrays.toString(actionVO.getIds());
//		ids  = ids.replace("[", "(");
//		ids  = ids.replace("]", ")");
		String query = "UPDATE " + actionVO.getTableName() + " SET Status = '" + actionVO.getStatus()
				+ "' WHERE  ID IN (" + ids + ")";
		int updateCount = updateStatusInDB(query);
		String message = updateCount + " records are marked as '" + actionVO.getStatus() + "'.";

		actionMap.put("success", true);
		actionMap.put("message", message);
		return actionMap;
	}// updateStatus

	private int updateStatusInDB(String query) {
		int updateCount = 0;
		try {
			updateCount = jdbcTemplate.update(query);
			return updateCount;
		} catch (Exception e) {
			String message = null;
			if (e instanceof SQLException) {
				if (ignoreSQLException(((SQLException) e).getSQLState()) == false) {

					e.printStackTrace(System.err);
					message += "SQLState: " + ((SQLException) e).getSQLState() + "\n";

					message += "Error Code: " + ((SQLException) e).getErrorCode() + "\n";

					message += "Message: " + e.getMessage();

					logger.error("ERROR :->: " + message);
				}
			} // if SQLException
			if (message != null) {
				throw new RuntimeException(message);
			} else {
				throw new RuntimeException(e.getCause());
			}
		} // catch
	}// updateStatusInDB

}// ApiUtilService
