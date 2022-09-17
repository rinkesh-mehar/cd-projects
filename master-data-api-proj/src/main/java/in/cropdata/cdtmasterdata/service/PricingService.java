package in.cropdata.cdtmasterdata.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.*;

import in.cropdata.cdtmasterdata.exceptions.InvalidDataException;
import in.cropdata.cdtmasterdata.model.*;
import in.cropdata.cdtmasterdata.repository.CommodityBandRepository;
import in.cropdata.cdtmasterdata.repository.PricingBCSlopeRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.exception.DataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.constants.DBConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.PricingMspGroupInfo;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.repository.PricingMspGroupRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PricingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PricingService.class);

    @Autowired
    private PricingMspGroupRepository pricingMspGroupRepository;

    @Autowired
    private ResponseMessageUtil responseMessageUtil;

    @Autowired
    private AclHistoryUtil approvalUtil;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    @Autowired
    private CommodityBandRepository commodityBandRepository;

    @Autowired
    private PricingBCSlopeRepository pricingBCSlopeRepository;


    public List<PricingMspGroupInfo> getAllPricingMspGroupList() {
        try {
            return pricingMspGroupRepository.getAllPricingMspGroup();
        } catch (Exception e) {
            throw e;
        }
    }// getAllPricingMspGroupList

    public ResponseMessage addPricing(PricingMaster pricingMaster) {

        try {


            final List<Integer> bandIdList = commodityBandRepository.findAllId();

            LOGGER.info("insertion query started at -> {}", LocalTime.now());


            /** Adding MSP and MFP constants */

            if ("MspMfp".equals(pricingMaster.getScreen())) {

                StringBuilder insertQuery = new StringBuilder();
                insertQuery.append("INSERT INTO cdt_master_data.pricing_master_mbep (StateCode, RegionID,\n" +
                        "CommodityID, VarietyID, GradeID, Msp, Mfp) VALUES ");

                for (AgriVariety varietyId : pricingMaster.getVarietyId()) {
                    for (Integer bandId : bandIdList) {

                        insertQuery.append("(" + pricingMaster.getStateCode() + "," + pricingMaster.getRegionId()
                                + "," + pricingMaster.getCommodityId()
                                + "," + varietyId.getId() + "," + bandId + "," + pricingMaster.getMsp()
                                + "," + pricingMaster.getMfp() + "),");
                    }
                }
                final String finalInsertQuery = insertQuery.substring(0, insertQuery.length() - 1)
                        .concat("ON DUPLICATE KEY UPDATE Msp=values(Msp),\n" +
                                "                        Mfp=values(Mfp);");

                if ((executeQuery(finalInsertQuery)) < 1) {
                    return responseMessageUtil.sendResponse(false,
                            "Insertion Of Record Failed For MSP Pricing", "");
                }
            }

            /** Adding Mbep Pmp Spread constants */

            else if ("MbepPmpSpread".equals(pricingMaster.getScreen())) {

                StringBuilder insertQuery = new StringBuilder();
                insertQuery.append("INSERT INTO cdt_master_data.pricing_master_mbep (StateCode, RegionID,\n" +
                        "CommodityID, VarietyID, GradeID, Mbep, Pmp, PriceSpread) VALUES ");

                for (AgriVariety varietyId : pricingMaster.getVarietyId()) {
                    for (Integer bandId : bandIdList) {
                        insertQuery.append("(" + pricingMaster.getStateCode() + "," + pricingMaster.getRegionId()
                                + "," + pricingMaster.getCommodityId() + "," + varietyId.getId()
                                + "," + bandId + "," + pricingMaster.getMbep()
                                + "," + pricingMaster.getPmp() + "," + pricingMaster.getPriceSpread() + "),");
                    }
                }

                final String finalInsertQuery = insertQuery.substring(0, insertQuery.length() - 1)
                        .concat("ON DUPLICATE KEY UPDATE Mbep=values(Mbep),\n" +
                                "                        Pmp=values(Pmp),\n" +
                                "                        PriceSpread=values(PriceSpread);");

                if ((executeQuery(finalInsertQuery)) < 1) {
                    return responseMessageUtil.sendResponse(false,
                            "Insertion Of Record Failed Fo MBEP and PMP Pricing", "");
                }
            }

            /** Adding pricing constants */

            else if ("constants".equals(pricingMaster.getScreen())) {

                StringBuilder insertQuery = new StringBuilder();
                insertQuery.append("INSERT INTO cdt_master_data.pricing_master_mbep (StateCode, RegionID,\n" +
                        "CommodityID, VarietyID, GradeID, MarginConstant, BuyerConstant, SellerConstant) VALUES ");

                for (AgriVariety varietyId : pricingMaster.getVarietyId()) {
                    for (Integer bandId : bandIdList) {
                        insertQuery.append("(" + pricingMaster.getStateCode() + "," + pricingMaster.getRegionId()
                                + "," + pricingMaster.getCommodityId() + "," + varietyId.getId()
                                + "," + bandId + "," + pricingMaster.getMarginConstant()
                                + "," + pricingMaster.getBuyerConstant() + "," + pricingMaster.getSellerConstant() + "),");
                    }
                }

                final String finalInsertQuery = insertQuery.substring(0, insertQuery.length() - 1)
                        .concat("ON DUPLICATE KEY UPDATE MarginConstant=values(MarginConstant),\n" +
                                "                        BuyerConstant=values(BuyerConstant),\n" +
                                "                        SellerConstant=values(SellerConstant);");

                if ((executeQuery(finalInsertQuery)) < 1) {
                    return responseMessageUtil.sendResponse(false,
                            "Insertion Of Record Failed For Constants Pricing", "");
                }
            }

            LOGGER.info("insertion query ended at -> {}", LocalTime.now());

            approvalUtil.addRecord(DBConstants.TBL_PRICING_MSP_GROUP, pricingMaster.getId());

            return responseMessageUtil.sendResponse(true, "Pricing" + APIConstants.RESPONSE_ADD_SUCCESS,
                    "");

        } catch (Exception e) {

            return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
        }

    }// addAllAgriPalntHealthIndex

    @Transactional
    public int executeQuery(String insertQuery) {

        try {
            return jdbcTemplate.update(insertQuery);

        } catch (Exception e) {
            String message = null;
            if (e instanceof SQLException) {
                if (!ignoreSQLException(((SQLException) e).getSQLState())) {

                    e.printStackTrace(System.err);
                    message += "SQLState: " + ((SQLException) e).getSQLState() + "\n";

                    message += "Error Code: " + ((SQLException) e).getErrorCode() + "\n";

                    message += "Message: " + e.getMessage();

                    LOGGER.error("ERROR :->: {}", message);
                }
            }
            throw new RuntimeException(e.getCause());
        }
    }

    public static boolean ignoreSQLException(String sqlState) {

        if (sqlState == null) {
            System.out.println("The SQL state is not defined!");
            LOGGER.error("The SQL state is not defined!");
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


    public List<MSP> getCommodityAndVarietyListByStateCode(String stateCode) {
        try {
            LOGGER.info("Getting parameter list..");

            return namedJdbcTemplate.query("SELECT CommodityID, VarietyID, StateCode FROM\n" +
                            "cdt_master_data.regional_variety WHERE StateCode IN (" + stateCode + ");",
                    new BeanPropertyRowMapper<>(MSP.class));
        } catch (DataException e) {
            throw new DoesNotExistException(e.getMessage());
        }
    }

    public List<MSP> getRegionsByStateCode(String stateCode) {
        try {
            LOGGER.info("Getting parameter list..");

            return namedJdbcTemplate.query("SELECT sr.RegionId, r.StateCode\n" +
                            "FROM cdt_master_data.geo_subregion AS sr\n" +
                            "         INNER JOIN cdt_master_data.geo_region AS r ON r.RegionID = sr.RegionID\n" +
                            "WHERE r.StateCode IN (" + stateCode + ")\n" +
                            "GROUP BY r.RegionID, StateCode;",
                    new BeanPropertyRowMapper<>(MSP.class));
        } catch (DataException e) {
            throw new DoesNotExistException(e.getMessage());
        }
    }

    public Map<String, Object> getAllPricingMspPaginated(Integer page) {
        try {
            Map<String, Object> responseMap = new HashMap<>();

            MapSqlParameterSource sqlParams = new MapSqlParameterSource();

            page = page - 1;
            Integer s = page * 250;

            sqlParams.addValue("s", s);

            List<MSP> contentData = namedJdbcTemplate.query("select GROUP_CONCAT(DISTINCT GS.Name) as States, GROUP_CONCAT(DISTINCT PM.StateCode) as StateCodes,\n" +
                            "       AC.Name AS Commodity, PM.CommodityID, PM.Msp, PM.Status\n" +
                            "FROM cdt_master_data.pricing_master PM\n" +
                            "inner join cdt_master_data.geo_state GS on (GS.StateCode = PM.StateCode)\n" +
                            "inner join cdt_master_data.agri_commodity AC on (AC.ID = PM.CommodityID)\n" +
                            "Group By Commodity, Msp, PM.Status limit :s, 250",
                    sqlParams, new BeanPropertyRowMapper<>(MSP.class));

            responseMap.put("content", contentData);
            responseMap.put("size", 20);
            responseMap.put("totalNoOfRecords", contentData.size());

            return responseMap;
        } catch (DataAccessException ex) {
            throw new InvalidDataException(ex.getMessage());
        }
    }

    /**
     * Getting all state and region from pricing_master Table
     */
    public List<PricingMspGroupInfo> getAllStateAndRegion() {
        try {

            return pricingMspGroupRepository.getAllStateAndRegion();

        } catch (DataAccessException ex) {
            throw new InvalidDataException(ex.getMessage());
        }
    }

    /**
     * @param commodityID
     * @param regionID
     * @param stateCode
     * @param flag
     * @return List of MSP and MFP
     * @apiNote Getting MSP and MFP from pricing_master
     */
    public List<PricingMspGroupInfo> getMspMfp(final Integer stateCode, final Integer regionID,
                                               final Integer commodityID, final Integer flag)
    {
        try {
            List<PricingMspGroupInfo> responseList = new ArrayList<>();

            if (flag == 1) {

                responseList = pricingMspGroupRepository.getCommodityByStateAndRegion(stateCode, regionID);
            } else if (flag == 2) {

                responseList = pricingMspGroupRepository.getMspByStateRegionCommodity(stateCode, regionID, commodityID);
            }

            return responseList;

        } catch (DataAccessException ex) {
            throw new InvalidDataException(ex.getMessage());
        }
    }

    /**
     * @param commodityID
     * @param regionID
     * @param stateCode
     * @param flag
     * @return List of Constants
     * @apiNote Getting Margin, Buyer and Seller constants from pricing_master
     */
    public List<PricingMspGroupInfo> getConstants(final Integer stateCode, final Integer regionID,
                                                  final Integer commodityID, final Integer flag)
    {
        try {
            List<PricingMspGroupInfo> responseList = new ArrayList<>();

            if (flag == 1) {

                responseList = pricingMspGroupRepository.getCommodityByStateAndRegion(stateCode, regionID);
            } else if (flag == 2) {

                responseList = pricingMspGroupRepository.getConstants(stateCode, regionID, commodityID);
            }

            return responseList;

        } catch (DataAccessException ex) {
            throw new InvalidDataException(ex.getMessage());
        }
    }

    /**
     * @param commodityID
     * @param regionID
     * @param stateCode
     * @param flag
     * @return List of PMP and MBEP
     * @apiNote Getting PMP and MBEP from pricing_master
     */
    public List<PricingMspGroupInfo> getMbepAndPmp(final Integer stateCode, final Integer regionID,
                                                   final Integer commodityID, final Integer flag)
    {
        try {
            List<PricingMspGroupInfo> responseList = new ArrayList<>();

            if (flag == 1) {

                responseList = pricingMspGroupRepository.getCommodityByStateAndRegion(stateCode, regionID);
            } else if (flag == 2) {

                responseList = pricingMspGroupRepository.getMbepAndPmp(stateCode, regionID, commodityID);
            }

            return responseList;

        } catch (DataAccessException ex) {
            throw new InvalidDataException(ex.getMessage());
        }
    }

    /**
     * @param commodityID
     * @param regionID
     * @param stateCode
     * @param flag
     * @return List of Price Spread
     * @apiNote Getting Price Spread from pricing_master
     */
    public List<PricingMspGroupInfo> getPriceSpread(final Integer stateCode, final Integer regionID,
                                                    final Integer commodityID, final Integer flag)
    {
        try {
            List<PricingMspGroupInfo> responseList = new ArrayList<>();

            if (flag == 1) {

                responseList = pricingMspGroupRepository.getCommodityByStateAndRegion(stateCode, regionID);
            } else if (flag == 2) {

                responseList = pricingMspGroupRepository.getPriceSpread(stateCode, regionID, commodityID);
            }

            return responseList;

        } catch (DataAccessException ex) {
            throw new InvalidDataException(ex.getMessage());
        }
    }

    public MSP getMspByStateMSPCommodity(Integer commodityId, String stateCode, Double msp) {
        try {

            MapSqlParameterSource sqlParams = new MapSqlParameterSource();

            List<MSP> mspOFCommodityState = namedJdbcTemplate.query("SELECT GROUP_CONCAT(DISTINCT PM.StateCode) AS StateCode, PM.Msp, PM.CommodityID\n" +
                            "FROM cdt_master_data.pricing_master PM\n" +
                            "WHERE PM.CommodityID = " + commodityId + "\n" +
                            "    AND PM.StateCode IN (" + stateCode + ")\n" +
                            "    AND PM.Msp = " + msp + " Group By PM.Msp, PM.CommodityID",
                    sqlParams, new BeanPropertyRowMapper<>(MSP.class));

            return mspOFCommodityState.get(0);

        } catch (DataAccessException ex) {
            throw new InvalidDataException(ex.getMessage());
        }
    }

    public List<PricingMspGroupInfo> getVariety(Integer stateCode, Integer commodityID) {
        return pricingMspGroupRepository.getVariety(stateCode, commodityID);
    }

    public List<PricingMspGroupInfo> getRegion(Integer stateCode) {
        return pricingMspGroupRepository.getRegion(stateCode);
    }


    public List<Map<String, Object>> getAllCommodities() {

        try {

            return pricingMspGroupRepository.getAllCommodities();

        } catch (Exception e) {
            throw e;
        }
    }

    public ResponseMessage updatePricingMspGroupById(int id, PricingMaster pricingMaster) {

        try {
            Optional<PricingMaster> foundVareity = pricingMspGroupRepository.findById(id);

            if (foundVareity.isPresent()) {
                pricingMaster.setId(id);

                pricingMaster = pricingMspGroupRepository.save(pricingMaster);

                approvalUtil.updateRecord(DBConstants.TBL_PRICING_MSP_GROUP, pricingMaster.getId());

                return responseMessageUtil.sendResponse(true,
                        "Pricing-Msp-Group" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
            } else {
                return responseMessageUtil.sendResponse(false, "",
                        "Pricing-Msp-Group" + APIConstants.RESPONSE_UPDATE_ERROR + id);
            }
        } catch (Exception e) {
            return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
        }
    }// updatePricingMspGroupById

    public ResponseMessage primaryApproveById(int id) {

        try {
            Optional<PricingMaster> foundPricingMspGroup = pricingMspGroupRepository.findById(id);

            if (foundPricingMspGroup.isPresent()) {

                PricingMaster pricingMaster = foundPricingMspGroup.get();
                pricingMaster.setStatus(APIConstants.STATUS_APPROVED);

                pricingMaster = pricingMspGroupRepository.save(pricingMaster);

                approvalUtil.primaryApprove(DBConstants.TBL_PRICING_MSP_GROUP, pricingMaster.getId());

                return responseMessageUtil.sendResponse(true,
                        "Pricing-Msp-Group" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");

            } else {
                return responseMessageUtil.sendResponse(false, "",
                        "Pricing-Msp-Group" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
            }
        } catch (Exception e) {
            return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
        }
    }// primaryApproveById

    public ResponseMessage finalApproveById(int id) {

        try {
            Optional<PricingMaster> foundPricingMspGroup = pricingMspGroupRepository.findById(id);

            if (foundPricingMspGroup.isPresent()) {

                PricingMaster pricingMaster = foundPricingMspGroup.get();
                pricingMaster.setStatus(APIConstants.STATUS_ACTIVE);

                pricingMaster = pricingMspGroupRepository.save(pricingMaster);

                approvalUtil.finalApprove(DBConstants.TBL_PRICING_MSP_GROUP, pricingMaster.getId());

                return responseMessageUtil.sendResponse(true,
                        "Pricing-Msp-Group" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
            } else {
                return responseMessageUtil.sendResponse(false, "",
                        "Pricing-Msp-Group" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
            }
        } catch (Exception e) {
            return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
        }
    }// finalApproverById

    public ResponseMessage deletePricingMspGroupById(int id) {
        try {
            Optional<PricingMaster> found = pricingMspGroupRepository.findById(id);
            if (found.isPresent()) {

                PricingMaster pricingMaster = found.get();
                pricingMaster.setStatus(APIConstants.STATUS_DELETED);

                pricingMaster = pricingMspGroupRepository.save(pricingMaster);

                approvalUtil.delete(DBConstants.TBL_PRICING_MSP_GROUP, pricingMaster.getId());

                return responseMessageUtil.sendResponse(true,
                        "Pricing-Msp-Group" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
            } else {

                return responseMessageUtil.sendResponse(false, "",
                        "Pricing-Msp-Group" + APIConstants.RESPONSE_DELETE_ERROR + id);
            }
        } catch (Exception e) {
            return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
        }
    }// deletePricingMspGroupById

    public PricingMaster findPricingMspGroupById(int id) {
        try {
            Optional<PricingMaster> foundPalntHealthIndex = pricingMspGroupRepository.findById(id);
            if (foundPalntHealthIndex.isPresent()) {
                return foundPalntHealthIndex.get();
            } else {
                throw new DoesNotExistException("Pricing-Msp-Group" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
            }
        } catch (Exception e) {
            throw e;
        }
    }// findPricingMspGroupById

    public ResponseMessage rejectById(int id) {
        try {
            Optional<PricingMaster> foundPricingMspGroup = pricingMspGroupRepository.findById(id);

            if (foundPricingMspGroup.isPresent()) {

                PricingMaster pricingMaster = foundPricingMspGroup.get();
                pricingMaster.setStatus(APIConstants.STATUS_REJECTED);
                pricingMaster = pricingMspGroupRepository.save(pricingMaster);

                approvalUtil.finalApprove(DBConstants.TBL_PRICING_MSP_GROUP, pricingMaster.getId());

                return responseMessageUtil.sendResponse(true,
                        "Pricing-Msp-Group" + APIConstants.RESPONSE_REJECT_SUCCESS, "");
            } else {
                return responseMessageUtil.sendResponse(false, "", "c" + APIConstants.RESPONSE_REJECT_ERROR);
            }
        } catch (Exception e) {
            return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
        }
    }// rejectById

   /* public List<Integer> listOfPages(String screen) {
        try {
            List<Integer> listOfPageNo = new ArrayList<>();

            if (screen.equals("mbep-pmp-spread")) {

                Integer totalCount = pricingMspGroupRepository.totalNoOfPages(24, 2, 21);

                for (int i = 1; i <= totalCount; i++) {
                    listOfPageNo.add(i);
                }
            }

            return listOfPageNo;

        } catch (Exception ex) {
            throw new DoesNotExistException("No Records Found!");
        }
    }*/

    public ResponseEntity<Resource> getMbepPmpSpreadInExcelFile(Integer stateCode, Integer regionID, Integer commodityID) {

        List<PricingMspGroupInfo> mbepPmpList = pricingMspGroupRepository.getMbepAndPmp(stateCode, regionID, commodityID);
        String filename = "Mbep_Pmp_Spread_Price.xlsx";
        /** Getting excel data */
        InputStreamResource file = new InputStreamResource(this.exportMbepPmpSpreadData(mbepPmpList));
        /** Return response */
        return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"").body(file);
    }

    private ByteArrayInputStream exportMbepPmpSpreadData(List<PricingMspGroupInfo> mbepPmpList) {
        LOGGER.info("generating excel file for Mbep Pmp...");
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {

            final String[] headers = {"State Name", "Region Name", "Commodity Name", "Variety", "Grade", "MBEP", "PMP",
                    "Spread", "Benchmark", "Status"};

            Sheet sheet = workbook.createSheet("Mbep-Pmp-Data");
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            // Header
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < headers.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(headers[col]);
                cell.setCellStyle(headerCellStyle);
            }

            int rowIdx = 1;
            for (PricingMspGroupInfo mbepPmp : mbepPmpList) {
                Row row = sheet.createRow(rowIdx++);
                int columnCount = 0;
                row.createCell(columnCount++).setCellValue(mbepPmp.getStateName() == null ? "" : mbepPmp.getStateName());
                row.createCell(columnCount++).setCellValue(mbepPmp.getRegionName() == null ? "" : mbepPmp.getRegionName());
                row.createCell(columnCount++).setCellValue(mbepPmp.getCommodityName() == null ? "" : mbepPmp.getCommodityName());
                row.createCell(columnCount++).setCellValue(mbepPmp.getVarietyName() == null ? "" : mbepPmp.getVarietyName());
                row.createCell(columnCount++).setCellValue(mbepPmp.getGradeName() == null ? "" : mbepPmp.getGradeName());
                row.createCell(columnCount++).setCellValue(mbepPmp.getMbep() == null ? "" : mbepPmp.getMbep());
                row.createCell(columnCount++).setCellValue(mbepPmp.getPmp() == null ? "" : mbepPmp.getPmp());
                row.createCell(columnCount++).setCellValue(mbepPmp.getPriceSpread() == null ? "" : mbepPmp.getPriceSpread());
                row.createCell(columnCount++).setCellValue(mbepPmp.getIsBenchmark() == null ? "" : mbepPmp.getIsBenchmark());
                row.createCell(columnCount++).setCellValue(mbepPmp.getStatus() == null ? "" : mbepPmp.getStatus());
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException ex) {
            throw new InvalidDataException("Failed to export data to Excel file: " + ex.getMessage());
        }
    }

    public ResponseEntity<Resource> getConstantInExcelFile(Integer stateCode, Integer regionID, Integer commodityID) {

        List<PricingMspGroupInfo> constantsList = pricingMspGroupRepository.getConstants(stateCode, regionID, commodityID);
        String filename = "Constant_Price.xlsx";
        /** Getting excel data */
        InputStreamResource file = new InputStreamResource(this.exportConstantData(constantsList));
        /** Return response */
        return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"").body(file);
    }

    private ByteArrayInputStream exportConstantData(List<PricingMspGroupInfo> mbepPmpList) {
        LOGGER.info("generating excel file for Constant...");
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {

            final String[] headers = {"State Name", "Region Name", "Commodity Name", "Variety", "Grade", "Margin Constant", "Buyer Constant",
                    "Seller Constant", "Benchmark", "Status"};

            Sheet sheet = workbook.createSheet("Constant-Data");
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            // Header
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < headers.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(headers[col]);
                cell.setCellStyle(headerCellStyle);
            }

            int rowIdx = 1;
            for (PricingMspGroupInfo mbepPmp : mbepPmpList) {
                Row row = sheet.createRow(rowIdx++);
                int columnCount = 0;
                row.createCell(columnCount++).setCellValue(mbepPmp.getStateName() == null ? "" : mbepPmp.getStateName());
                row.createCell(columnCount++).setCellValue(mbepPmp.getRegionName() == null ? "" : mbepPmp.getRegionName());
                row.createCell(columnCount++).setCellValue(mbepPmp.getCommodityName() == null ? "" : mbepPmp.getCommodityName());
                row.createCell(columnCount++).setCellValue(mbepPmp.getVarietyName() == null ? "" : mbepPmp.getVarietyName());
                row.createCell(columnCount++).setCellValue(mbepPmp.getGradeName() == null ? "" : mbepPmp.getGradeName());
                row.createCell(columnCount++).setCellValue(mbepPmp.getMarginConstant() == null ? "" : mbepPmp.getMarginConstant());
                row.createCell(columnCount++).setCellValue(mbepPmp.getBuyerConstant() == null ? "" : mbepPmp.getBuyerConstant());
                row.createCell(columnCount++).setCellValue(mbepPmp.getSellerConstant() == null ? "" : mbepPmp.getSellerConstant());
                row.createCell(columnCount++).setCellValue(mbepPmp.getIsBenchmark() == null ? "" : mbepPmp.getIsBenchmark());
                row.createCell(columnCount++).setCellValue(mbepPmp.getStatus() == null ? "" : mbepPmp.getStatus());
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException ex) {
            throw new InvalidDataException("Failed to export data to Excel file: " + ex.getMessage());
        }
    }

    public ResponseEntity<Resource> getMspMfpInExcelFile(Integer stateCode, Integer regionID, Integer commodityID) {

        List<PricingMspGroupInfo> constantsList = pricingMspGroupRepository.getMspByStateRegionCommodity(stateCode, regionID, commodityID);
        String filename = "Msp_Mfp_Price.xlsx";
        /** Getting excel data */
        InputStreamResource file = new InputStreamResource(this.exportMspMfpData(constantsList));
        /** Return response */
        return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"").body(file);
    }

    private ByteArrayInputStream exportMspMfpData(List<PricingMspGroupInfo> mbepPmpList) {
        LOGGER.info("generating excel file for Constant...");
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {

            final String[] headers = {"State Name", "Region Name", "Commodity Name", "Variety", "Grade", "MSP", "MFP",
                    "Benchmark", "Status"};

            Sheet sheet = workbook.createSheet("Constant-Data");
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            // Header
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < headers.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(headers[col]);
                cell.setCellStyle(headerCellStyle);
            }

            int rowIdx = 1;
            for (PricingMspGroupInfo mbepPmp : mbepPmpList) {
                Row row = sheet.createRow(rowIdx++);
                int columnCount = 0;
                row.createCell(columnCount++).setCellValue(mbepPmp.getStateName() == null ? "" : mbepPmp.getStateName());
                row.createCell(columnCount++).setCellValue(mbepPmp.getRegionName() == null ? "" : mbepPmp.getRegionName());
                row.createCell(columnCount++).setCellValue(mbepPmp.getCommodityName() == null ? "" : mbepPmp.getCommodityName());
                row.createCell(columnCount++).setCellValue(mbepPmp.getVarietyName() == null ? "" : mbepPmp.getVarietyName());
                row.createCell(columnCount++).setCellValue(mbepPmp.getGradeName() == null ? "" : mbepPmp.getGradeName());
                row.createCell(columnCount++).setCellValue(String.valueOf(mbepPmp.getMsp()) == null ? "" : String.valueOf(mbepPmp.getMsp()));
                row.createCell(columnCount++).setCellValue(mbepPmp.getMfp() == null ? "" : mbepPmp.getMfp());
                row.createCell(columnCount++).setCellValue(mbepPmp.getIsBenchmark() == null ? "" : mbepPmp.getIsBenchmark());
                row.createCell(columnCount++).setCellValue(mbepPmp.getStatus() == null ? "" : mbepPmp.getStatus());
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException ex) {
            throw new InvalidDataException("Failed to export data to Excel file: " + ex.getMessage());
        }
    }

    public Page<PricingMspGroupInfo> getBuyerConstantPaginated(int page, int size, String searchText) {
        try {
            searchText = "%" + searchText + "%";
            Pageable sortedByIdAsc = PageRequest.of(page, size, Sort.by("id").ascending());

            return pricingBCSlopeRepository.getBuyerConstantPaginated(sortedByIdAsc, searchText);
        } catch (Exception e) {
            throw e;
        }
    }

    public List<PricingMspGroupInfo> getBuyerConstant() {
        try {

            return pricingBCSlopeRepository.getBuyerConstant();
        } catch (Exception e) {
            throw e;
        }
    }

    public ResponseMessage addBuyerConstant(PricingBcSlopeRange pricingBcSlopeRange) {

        try {

            Optional<PricingBcSlopeRange> foundPricingBcSlopeRange = pricingBCSlopeRepository.findBySlopeMinAndSlopeMaxAndBuyerConstant(pricingBcSlopeRange.getSlopeMin(), pricingBcSlopeRange.getSlopeMax(), pricingBcSlopeRange.getBuyerConstant());

            foundPricingBcSlopeRange.ifPresent(pricingBuyerConstant -> pricingBcSlopeRange.setId(pricingBuyerConstant.getId()));
            pricingBCSlopeRepository.save(pricingBcSlopeRange);

//            approvalUtil.addRecord(DBConstants.TBL_REGIONAL_Terrain, regionalTerrain.getId());

            return responseMessageUtil.sendResponse(true, "Pricing Buyer Constant" + APIConstants.RESPONSE_ADD_SUCCESS, "");

        } catch (Exception e) {
            return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
        }
    }

    public PricingBcSlopeRange findBuyerConstantById(int id) {

        try {
            Optional<PricingBcSlopeRange> foundPricingBcSlopeRange = pricingBCSlopeRepository.findById(id);

            if (foundPricingBcSlopeRange.isPresent()) {
                return foundPricingBcSlopeRange.get();
            } else {
                throw new DoesNotExistException("Buyer Constant" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public ResponseMessage updateBuyerConstantById(int id, PricingBcSlopeRange pricingBcSlopeRange) {
        try {
            Optional<PricingBcSlopeRange> foundPricingBcSlopeRange = pricingBCSlopeRepository.findById(id);

            if (foundPricingBcSlopeRange.isPresent()) {

                pricingBcSlopeRange.setId(id);
                pricingBcSlopeRange = pricingBCSlopeRepository.save(pricingBcSlopeRange);

//                approvalUtil.updateRecord(DBConstants.TBL_REGIONAL_Terrain, regionTerrain.getId());

                return responseMessageUtil.sendResponse(true,
                        "Buyer Constant" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
            } else {
                return responseMessageUtil.sendResponse(false, "",
                        "Buyer Constant" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
            }
        } catch (Exception e) {
            return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
        }
    }

}
