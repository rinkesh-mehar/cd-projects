package com.drk.tools.util;

import com.drk.tools.config.AppConfig;
import com.drk.tools.model.BmImageDetails;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class to read the data from excel sheet.
 *
 * @author PranaySK
 * @since 1.0
 */

@Component
public class ExcelReader {

    private static final Logger logger = LoggerFactory.getLogger(ExcelReader.class);

    @Autowired
    private AppConfig properties;

    @Autowired
    private ImageReader imageReader;

    public List<BmImageDetails> readBmImageDetails() throws FileNotFoundException {
        logger.debug("Excel reading started...");
        BmImageDetails bmImageDetails = null;
        Resource bmImage = null;
        Map<String, Resource> bmImages = null;
        List<BmImageDetails> bmDetailsList = null;
        List<String> bmDetails = null;

        final File dir = new File(properties.getExcelPath());

        if (dir.isDirectory()) {

            for (final File excelFile : dir.listFiles(EXCEL_FILTER)) {

                if (excelFile.exists()) {

                    String excelPathAndFileName = excelFile.getAbsolutePath();

                    logger.debug("excel file name is -> {}", excelPathAndFileName);

                    try (FileInputStream file = new FileInputStream(
                            new File(excelPathAndFileName)))
                    {

                        /** Create Workbook instance holding reference to .xlsx file */
                        XSSFWorkbook workbook = new XSSFWorkbook(file);

                        /** Get first/desired sheet from the workbook */
                        Sheet sheet = workbook.getSheetAt(0);

                        /** Iterate through each rows one by one */
                        bmDetailsList = new ArrayList<>();
                        String dataValue = null;

                        for (Row row : sheet) {
                            /** Skip the headers row */
                            if (row.getRowNum() == 0) {
                                continue;
                            }

                            bmImageDetails = new BmImageDetails();
                            bmDetails = new ArrayList<>();
                            bmImages = new HashMap<>();

                            /** For each row, iterate through all the columns */
                            for (Cell cell : row) {
                                CellType cellType = cell.getCellType();

                                switch (cell.getColumnIndex()) {
                                    case 4:
                                        if (cellType == CellType.STRING) {
                                            dataValue = cell.getStringCellValue();
                                            bmImage = imageReader.readBmImage(dataValue);
                                            bmDetails.add(dataValue);
                                            bmImages.put("img6", bmImage);
                                        } else if (cellType == CellType.BLANK) {
                                            dataValue = null;
                                            bmDetails.add(dataValue);
                                        }
                                        logger.debug("img6 name and value -> {} --> {}", dataValue,
                                                (dataValue == null) ? null : bmImage);
                                        break;
                                    case 5:
                                        if (cellType == CellType.STRING) {
                                            dataValue = cell.getStringCellValue();
                                            bmImage = imageReader.readBmImage(dataValue);
                                            bmDetails.add(dataValue);
                                            bmImages.put("img7", bmImage);
                                        } else if (cellType == CellType.BLANK) {
                                            dataValue = null;
                                            bmDetails.add(dataValue);
                                        }
                                        logger.debug("img7 name and value -> {} --> {}", dataValue,
                                                (dataValue == null) ? null : bmImage);
                                        break;
                                    case 6:
                                        if (cellType == CellType.STRING) {
                                            dataValue = cell.getStringCellValue();
                                            bmImage = imageReader.readBmImage(dataValue);
                                            bmDetails.add(dataValue);
                                            bmImages.put("img8", bmImage);
                                        } else if (cellType == CellType.BLANK) {
                                            dataValue = null;
                                            bmDetails.add(dataValue);
                                        }
                                        logger.debug("img8 name and value -> {} --> {}", dataValue,
                                                (dataValue == null) ? null : bmImage);
                                        break;
                                    case 7:
                                        if (cellType == CellType.STRING) {
                                            dataValue = cell.getStringCellValue();
                                            bmImage = imageReader.readBmImage(dataValue);
                                            bmDetails.add(dataValue);
                                            bmImages.put("img9", bmImage);
                                        } else if (cellType == CellType.BLANK) {
                                            dataValue = null;
                                            bmDetails.add(dataValue);
                                        }
                                        logger.debug("img9 name and value -> {} --> {}", dataValue,
                                                (dataValue == null) ? null : bmImage);
                                        break;
                                    case 8:
                                        if (cellType == CellType.STRING) {
                                            dataValue = cell.getStringCellValue();
                                            bmImage = imageReader.readBmImage(dataValue);
                                            bmDetails.add(dataValue);
                                            bmImages.put("img10", bmImage);
                                        } else if (cellType == CellType.BLANK) {
                                            dataValue = null;
                                            bmDetails.add(dataValue);
                                        }
                                        logger.debug("img10 name and value -> {} --> {}", dataValue,
                                                (dataValue == null) ? null : bmImage);
                                        break;
                                    default:
                                        if (cellType == CellType.STRING) {
                                            dataValue = cell.getStringCellValue();
                                            if (cell.getStringCellValue().equals("##")) {
                                                dataValue = null;
                                            }
                                            bmDetails.add(dataValue);
                                        } else if (cellType == CellType.BLANK) {
                                            dataValue = null;
                                            bmDetails.add(dataValue);
                                        }
                                        logger.debug("default data value -> {}", dataValue);
                                        break;
                                }
                            }
                            /** set the required values */
                            bmImageDetails.setCommodity(bmDetails.get(0));
//                            bmImageDetails.setPhenophase(bmDetails.get(1));
                            bmImageDetails.setPlantPart(bmDetails.get(1));
                            bmImageDetails.setStressType(bmDetails.get(2));
                            bmImageDetails.setStress(bmDetails.get(3));
//                            bmImageDetails.setStressStage(bmDetails.get(5));

                            /** set the image values */
                            bmImageDetails.setImages(bmImages);
                            logger.debug("rownum -> {} and bmImageDetails -> {}", row.getRowNum(), bmImageDetails);

                            /** set the values in list to be returned */
                            bmDetailsList.add(bmImageDetails);
                        }
                        workbook.close();

                    } catch (Exception e) {
                        logger.error("Error while reading excel data -> {}", e.getMessage());
                    }
                } else {
                    throw new FileNotFoundException("Excel File Not Found");
                }
                break;
            }
        }

        return bmDetailsList;
    }

    /**
     * array of supported extensions
     */
    private static final String[] EXTENSIONS = new String[]{"xlsx"};

    /**
     * filter to identify images based on their extensions
     */
    private static final FilenameFilter EXCEL_FILTER = ((dir, name) -> {
        for (final String ext : EXTENSIONS) {
            if (name.endsWith("." + ext)) {
                return true;
            }
        }
        return false;
    });
}
