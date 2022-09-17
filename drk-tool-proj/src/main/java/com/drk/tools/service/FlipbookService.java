package com.drk.tools.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import com.drk.tools.config.FileProperties;
import com.drk.tools.constants.ApplicationConstants;
import com.drk.tools.constants.ErrorConstants;
import com.drk.tools.dao.BmImageDao;
import com.drk.tools.dto.ResponseMessage;
import com.drk.tools.exceptions.FileStorageException;
import com.drk.tools.model.*;
import com.drk.tools.util.ExcelReader;
import com.drk.tools.util.ResponseMessageUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import com.drk.tools.config.AppConfig;
import com.drk.tools.exceptions.DataUploadFailedExcpetion;
import com.drk.tools.repository.FlipbookDAO;
import com.drk.tools.util.FileUtils;

import in.cropdata.toolsuite.sdk.FileManagerUtil;
import in.cropdata.toolsuite.sdk.dto.FileUploadResponseDTO;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FlipbookService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlipbookService.class);

    private Path _zipFileStorageLocation;

    @Autowired
    private ExcelReader excelReader;

    @Autowired
    private BmImageDao bmImageDao;

    @Autowired
    FileProperties fileProperties;

    @Autowired
    AppConfig properties;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FlipbookDAO flipDao;

    @Autowired
    private FileManagerUtil fileManagerUtil;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private FileUtils fileUtils;

    @Autowired
    private ResponseMessageUtil responseMessageUtil;

    public OutputStatus tagImage(final Map<String, String> data) {
        StringBuilder imgIds = new StringBuilder();
        OutputStatus outputStatus = new OutputStatus();

        data.forEach((key, value) -> {
            if ((value == null ? "" : value).equals("true")) {
                imgIds.append("'" + key + "',");
            }
        });

        if (imgIds.toString().equals("") || imgIds.length() < 1) {
            outputStatus.setErrCode("Please Select at least One Image ");
            outputStatus.setStatus(false);
            outputStatus.setMsg("Image Tag Error!");
        } else {
            String ids = imgIds.deleteCharAt(imgIds.length() - 1).toString();
            LOGGER.info("Inside Tag ids {}", ids);
            outputStatus = flipDao.tagData(data, ids);
        }

        return outputStatus;
    }

    public OutputStatus tagImageToSymptom(final Map<String, String> data) {
        StringBuilder imgIds = new StringBuilder();
        OutputStatus outputStatus = new OutputStatus();

        data.forEach((key, value) -> {
            if ((value == null ? "" : value).equals("true")) {
                imgIds.append("'" + key + "',");
            }
        });

        if (imgIds.toString().equals("") || imgIds.length() < 1) {
            outputStatus.setErrCode("Please Select at least One Image ");
            outputStatus.setStatus(false);
            outputStatus.setMsg("Image Tag Error !");
        } else {
            String ids = imgIds.deleteCharAt(imgIds.length() - 1).toString();
            outputStatus = flipDao.tagImageToSymptom(data, ids);
        }

        return outputStatus;
    }

    public boolean untagImage(final String symptomId, final String imgId) {
        return flipDao.unTagImage(symptomId, imgId);
    }

    public boolean removeSymptom(final String symptomId, final String symptom) {
        LOGGER.info("Remove symptom -> {}", symptom);
        return flipDao.removeSypmtom(symptomId);
    }

    public Map<String, List<DropDownList>> getDropDownList() {
        return flipDao.getList();
    }

    public Map<String, List<?>> fetchBenchMarkImages(final ClsoQuestions data) {
        return flipDao.getchBenchMarkImages(data);
    }

    /* File Collection to Folder */
    public Map<String, Object> searchAndGetFile() {
        LOGGER.info("Preparing search query");
        Map<String, Object> searchQuery = new HashMap<>();
        Map<String, Object> extensionMap = new HashMap<>();
        extensionMap.put("$in", new String[]{"jpeg", "db"});
        searchQuery.put("fileExtension", extensionMap);
        LOGGER.info("search query : {}", searchQuery);

        Map<String, Object> searchAndGetFile = fileManagerUtil.searchAndGetFile("flipbook", searchQuery, 0);
        LOGGER.info("SearchResponse :-> {}", searchAndGetFile);
        return searchAndGetFile;
    }

    public boolean isGeneric(final String symptomId, final String imageName) {
        return flipDao.isGeneric(symptomId, imageName);
    }

    public Map<String, List<DropDownList>> getDropdownData(final int commodityId, final int stressTypeId,
                                                           final int phenophaseId, final int stress, final int plantPartId, final int flag)
    {
        return flipDao.getDropdownData(commodityId, phenophaseId, plantPartId, stressTypeId, stress, flag);
    }

    /**
     * This method is used to create a ZIP file of images in module
     * <b><i>master-data</i></b> and upload it to Azure blob.
     *
     * @param lastZipTime   the last 24 hour time in minutes
     * @param pathKey       the path key for which the images ZIP file to be created
     * @param directoryName the directory name for which the images ZIP file to be
     *                      created
     * @param fileName      the file name of requested resource to be created
     * @return the response map object
     * @author PranaySK
     */
    public Map<String, Object> zipAgriImages(long lastZipTime, String pathKey, String directoryName, String fileName) {
        Map<String, Object> responseMap = new HashMap<>();
        try {

            responseMap = this.getZipFileDetails(lastZipTime, pathKey, directoryName, fileName);
            LOGGER.info("Response Map -> {}", responseMap);

        } catch (Exception ex) {
            LOGGER.error("Error while preparing zip file for -> {} ", pathKey, ex);
            responseMap.put("status", false);
            responseMap.put("timestamp", Instant.now().getEpochSecond());
            responseMap.put("msg",
                    "Zip file not found for {" + pathKey + "} Something went wrong! -> " + ex.getMessage());
        }

        return responseMap;
    }

    /**
     * This method is used to create a ZIP file of images in module
     * <b><i>master-data</i></b> and upload it to Azure blob. Then update the
     * uploaded file name with given file name which will be used for searching the
     * file.
     *
     * @param lastZipTime   the last 24 hour time in minutes
     * @param pathKey       the path key for which the images ZIP file to be created
     * @param directoryName the directory name in which the images ZIP file to be
     *                      created
     * @param fileName      the file name of requested ZIP file to be created
     * @return <code>Map</code> the map containing uploaded ZIP file details
     * @author PranaySK
     */
    private Map<String, Object> getZipFileDetails(long lastZipTime, String pathKey, String directoryName,
                                                  String fileName)
    {
        Map<String, Object> responseMap = new HashMap<>();
        final String STATUS = "status";
        final String TIMESTAMP = "timestamp";
        final String MSG = "msg";
        try {
            LOGGER.info("[getZipFileDetails] Preparing search query...");
            Map<String, Object> searchQuery = new HashMap<>();
            Map<String, Object> extensionMap = new HashMap<>();
            String moduleName = "ts";
            String target = "zip-files";
            extensionMap.put("$in", new String[]{"jpg", "jpeg"});
            searchQuery.put("fileExtension", extensionMap);
            searchQuery.put("pathKey", pathKey);
            searchQuery.put("dirPath", directoryName);
            LOGGER.info("[getZipFileDetails] Search query -> {}", searchQuery);

            Map<String, Object> fileResponseMap = fileManagerUtil.searchAndGetFile(moduleName, searchQuery, lastZipTime,
                    target);
            LOGGER.info("[getZipFileDetails] fileResponseMap -> {}", fileResponseMap);

            String uploadedFileId = fileResponseMap.get("_id").toString();

            Map<String, Object> fileMetadata = new HashMap<>();
            fileName = "ts/zip-files/" + fileName + "_" + LocalDate.now();
            fileMetadata.put("moduleName", moduleName);
            fileMetadata.put("fileName", fileName);
            fileResponseMap = fileManagerUtil.updateZipFileMetadata(uploadedFileId, fileMetadata);
            LOGGER.info("[getZipFileDetails] updated file response -> {}", fileResponseMap);

            responseMap.put(STATUS, true);
            responseMap.put(TIMESTAMP, Instant.now().getEpochSecond());
            responseMap.put("id", uploadedFileId);
            responseMap.put(MSG,
                    "Zip file uploaded for {" + pathKey + "} in Azure Blob with id {" + uploadedFileId + "}");

        } catch (Exception ex) {
            LOGGER.error("[getZipFileDetails] Error while preparing zip file for -> {} ", pathKey, ex);
            responseMap.put(STATUS, false);
            responseMap.put(TIMESTAMP, Instant.now().getEpochSecond());
            responseMap.put(MSG, "Zip file not found for {" + pathKey + " and " + fileName
                    + "} Something went wrong! -> " + ex.getMessage());
        }

        return responseMap;
    }

    /**
     * This method is used to create a ZIP file of images in
     * <b><i>master-data</i></b> module and write it in disk storage. This will be
     * called using <i>CRON-JOB Scheduler</i> on a daily basis. There will be 2 ZIP
     * files i.e., a) file containing last 24 hours updates and b) file containing
     * all image files.
     *
     * @param lastZipTime the timestamp for creating ZIP files
     * @return the response map
     * @author PranaySK
     */
    public Map<String, Object> zipMediaFiles(final Long lastZipTime) {
        Map<String, Object> responseMap = new HashMap<>();
        final String SUCCESS = "success";
        final String MESSAGE = "message";
        final String ERROR = "error";
        try {
            String baseZipDirPath = appConfig.getZipFilesDir(); // path.dir.zip
            /** check if directory is present, create otherwise */
            FileUtils.checkDirExists(baseZipDirPath);
            String baseMediaDirPath = appConfig.getMediaFilesDir(); // path.dir.media
            if (lastZipTime > 0) {  // TODO changes not implemented for 24 hours updates
                /**
                 * Check for updates in table data in last 24 hours. If updates found, get the
                 * updated images to make it as a ZIP for that 24 hours
                 */
                boolean isSymWritten = false;
                boolean isComPPWritten = false;
                List<ImageDetails> imageDetails = new ArrayList<>();
                if (flipDao.isImageUpdateFound("flipbook_symptoms")) {
                    List<ImageDetails> symptomImages = flipDao.getSymptomImages(1, 0);
                    imageDetails.addAll(symptomImages);
                    isSymWritten = fileUtils.writeImgFilesOnDisk(baseMediaDirPath + "symptoms/", symptomImages);
                    LOGGER.info("Symptom images written on disk -> {}", isSymWritten);
                } else {
                    LOGGER.info("No new updates found in symptoms!");
                }

                if (flipDao.isImageUpdateFound("agri_commodity_plant_part")) {
                    List<ImageDetails> plantPartImages = flipDao.getCommPlantPartImages(1, 0);
                    imageDetails.addAll(plantPartImages);
                    isComPPWritten = fileUtils.writeImgFilesOnDisk(baseMediaDirPath + "commodityplantpart/", plantPartImages);
                    LOGGER.info("Plant part images written on disk -> {}", isComPPWritten);
                } else {
                    LOGGER.info("No new updates found in commodity plant part!");
                }

                if (isSymWritten || isComPPWritten) {
                    String zipFileNameWithDir = baseZipDirPath.concat("media_").concat(LocalDate.now().toString())
                            .concat(".zip");
                    FileUtils.checkAndDeleteExistingFile(zipFileNameWithDir);
                    File mediaFile = fileUtils.createZipOfDirFiles(baseMediaDirPath, zipFileNameWithDir, imageDetails);
                    LOGGER.info("uploaded media file response -> [{}] --- [{}]", mediaFile.getName(),
                            mediaFile.getAbsolutePath());
                    FileUploadResponseDTO responseDTO = fileUtils.uploadUserZipFileWithPath(mediaFile);
                    LOGGER.info("uploaded file url -> [{}] --- [{}]", responseDTO.getId(), responseDTO.getPublicUrl());
                    responseMap.put(SUCCESS, "true");
                    responseMap.put(MESSAGE, "Zip file created for " + LocalDate.now());
                } else {
                    responseMap.put(SUCCESS, "false");
                    responseMap.put(ERROR,
                            "Unable to create Zip file for " + LocalDate.now() + "! No new updates found!");
                }

            } else {
                /**
                 * Get all the images from table and make it as a ZIP for all images.
                 */
                List<Integer> stateCodes = flipDao.getStateID();

                for (Integer stateCode: stateCodes) {
                    /**Generating Symptom Images */
                    List<ImageDetails> symptomImages = flipDao.getSymptomImages(0, stateCode);
                    if (symptomImages != null && !symptomImages.isEmpty()) {
                        boolean isWritten = fileUtils.writeImgFilesOnDisk(baseMediaDirPath + "symptoms/", symptomImages);
                        LOGGER.info("Symptom images written on disk -> {}", isWritten);

                        /** Creating zip file and uploading to Azure Blob Storage For Symptom Images*/
                        responseMap = fileUtils.createPartitionedZip("media_symptom_images_", "symptoms", symptomImages, stateCode);
                        LOGGER.info("Zip File Creation Status For Symptom Images {}",  responseMap.get("message"));
                    }

                    List<ImageDetails> plantPartImages = flipDao.getCommPlantPartImages(0, stateCode);
                    if (plantPartImages != null && !plantPartImages.isEmpty()) {
                        boolean isWritten = fileUtils.writeImgFilesOnDisk(baseMediaDirPath + "commodity_plant_part/", plantPartImages);
                        LOGGER.info("Plant part images written on disk -> {}", isWritten);

                        responseMap = fileUtils.createPartitionedZip("media_plant_part_images_", "commodity_plant_part", plantPartImages, stateCode);
                        LOGGER.info("Zip File Creation Status For Plant Part {}",  responseMap.get("message"));

                    }
                }

                /** Creating Zip of All the Symptom file */
                FileUploadResponseDTO allFlipbookSymptomUploadResponse = this.getAllFlipbookSymptom();
                LOGGER.info("all symptom upload image url {}", allFlipbookSymptomUploadResponse.getPublicUrl());
                LOGGER.info("all symptom upload image id {}", allFlipbookSymptomUploadResponse.getId());

//                if (isWritten) {
//                    String zipFileNameWithDir = baseZipDirPath.concat("media.zip");
//                    FileUtils.checkAndDeleteExistingFile(zipFileNameWithDir);
//                    File allMediaFile = fileUtils.createZipOfDir(baseMediaDirPath, zipFileNameWithDir);
//                    LOGGER.info("uploaded media file response -> [{}] --- [{}]", allMediaFile.getName(),
//                            allMediaFile.getAbsolutePath());
//                    FileUploadResponseDTO responseDTO = fileUtils.uploadUserZipFileWithPath(allMediaFile);
//                    LOGGER.info("uploaded file url -> [{}] --- [{}]", responseDTO.getId(), responseDTO.getPublicUrl());
//                    responseMap.put(SUCCESS, "true");
//                    responseMap.put(MESSAGE, "Zip file created for all images");
//                } else {
//                    responseMap.put(SUCCESS, "false");
//                    responseMap.put(ERROR, "Unable to create Zip file for all images");
//                }


            }
        } catch (Exception ex) {
            throw new DataUploadFailedExcpetion("Failed to upload the zip file to blob - " + ex.getMessage());
        }

        return responseMap;
    }

    public FileUploadResponseDTO getAllFlipbookSymptom() {

        LOGGER.info("---Started Working On Get All Flipbook Symptom Images");

        List<ImageDetails> symptomImages = flipDao.getAllFlipbookSymptomImages();
        String baseMediaDirPath = appConfig.getMediaFilesDir(); // path.dir.media

        FileUploadResponseDTO  responseMap = null;

        try{
            if (symptomImages != null && !symptomImages.isEmpty()) {
                boolean isWritten = fileUtils.writeImgFilesOnDisk(baseMediaDirPath + "all-symptoms/", symptomImages);
                LOGGER.info("Symptom images written on disk -> {}", isWritten);

                /** Creating zip file and uploading to Azure Blob Storage For Symptom Images*/
                responseMap = fileUtils.generateZipAndUploadFlipbookSymptomAll("symptoms",
                        "all-symptoms", symptomImages);
            } else {
                LOGGER.warn("No data present for all symptom images in cron of all flipbook symptom");
            }
        } catch (Exception e){
            e.printStackTrace();
            LOGGER.warn("Unable To Generate Zip of All Flipbook Symptom Images");
        }

        return responseMap;
    }

    public ResponseMessage uploadZipFile(MultipartFile zipFile) throws IOException, ZipException {
        Map<String, Object> resultMap = new HashMap<>();

        try {

            if (zipFile.isEmpty()) {
                LOGGER.error("Unable To Fetch Zip File");
                return responseMessageUtil.sendResponse(false,"","Please Provide Zip File");
            }

            createDirectory();

            LOGGER.info("Creating new zip file...");

            FileUtils.checkAndDeleteExistingFile(this.fileProperties.getZipExtract().substring(0, this.fileProperties.getZipExtract().lastIndexOf("/")).concat(File.separator).concat(zipFile.getOriginalFilename()));

            Path zipFilePath = Files.createFile(Paths.get(this.fileProperties.getZipExtract().substring(0, this.fileProperties.getZipExtract().lastIndexOf("/")).concat(File.separator).concat(zipFile.getOriginalFilename())));

            File temp = new File(String.valueOf(zipFilePath));
            IOUtils.copy(zipFile.getInputStream(), new FileOutputStream(temp));

            String[] SUPPORTED_EXTN = {"application/zip", "application/x-zip-compressed"};

            try {
                ZipFile _unzip = new ZipFile(this.fileProperties.getZipExtract().substring(0, this.fileProperties.getZipExtract().lastIndexOf("/")).concat(File.separator).concat(zipFile.getOriginalFilename()));
                if (Arrays.stream(SUPPORTED_EXTN).noneMatch(Objects.requireNonNull(zipFile.getContentType())::contains)){
                    LOGGER.error("Please Provide Valid File Format i.e ZIP. You Provided " + zipFile.getContentType() + " file ");
                    return responseMessageUtil.sendResponse(false, "", "Please Provide Valid File Format i.e ZIP. You Provided " + zipFile.getContentType() + " file ");
                }
                _unzip.extractAll(this.fileProperties.getZipExtract());
            } catch (ZipException ze) {
                LOGGER.error("exception occurred while decompressing " + zipFile.getOriginalFilename() + " file "
                        + ze.getMessage());
                throw new ZipException("Zip Extraction Failed" + ze.getMessage());
            }

            /** Reading and processing images and excel file to upload images to bench_mark_images*/
            resultMap = this.readAndProcessBmImageData();

            File dir = new File(properties.getExtractAllPath());
            File excelDir = new File(properties.getExcelPath());
            File zip = new File(properties.getExtractAllPath().substring(0, properties.getExtractAllPath().lastIndexOf("/")).concat(File.separator).concat(zipFile.getOriginalFilename()));

            if (dir.isDirectory()) {
                for (final File imgFile : dir.listFiles()) {
                    imgFile.delete();
                }
            }

            if (excelDir.isDirectory()) {
                File[] files = excelDir.listFiles();
                for (int i = 0; i < files.length; i++) {
                    File file = files[i];
                    file.delete();
                }
            }

            FileUtils.checkAndDeleteExistingFile(excelDir);
            FileUtils.checkAndDeleteExistingFile(dir);
            FileUtils.checkAndDeleteExistingFile(zip);

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        return responseMessageUtil.sendResponse((Boolean) resultMap.get(ApplicationConstants.SUCCESS),
                (Boolean) resultMap.get(ApplicationConstants.SUCCESS) ? (String) resultMap.get(ApplicationConstants.MESSAGE) : "",
                (Boolean) resultMap.get(ApplicationConstants.SUCCESS) ? "" : (String) resultMap.get(ApplicationConstants.MESSAGE));

    }


    public Map<String, Object> readAndProcessBmImageData() {

        Map<String, Object> resultMap = new HashMap<>();
        try {
            /** Read the data from excel sheet */
            LOGGER.warn("Excel reading Start Time -> {}", LocalTime.now());
            List<BmImageDetails> bmDetails = excelReader.readBmImageDetails();
            /* List<BmImageDetails> bmDetails = excelReader.readBmImageDetailsForRow(28); */
            LOGGER.warn("Excel reading End Time -> {}", LocalTime.now());
            LOGGER.info("bmDetails -> {}", bmDetails);

            PostBmImageResponse response = null;
            BmImageDetailIds detailIds = null;
            ResponseEntity<String> responseEntity = null;
            int count = 0;

            LOGGER.warn("Processing Start Time -> {}", LocalTime.now());

            for (BmImageDetails bmImageDetails : bmDetails) {
                /** DAO to fetch the IDs related to names from DB using Repository */
                detailIds = bmImageDao.getBmDetailIds(bmImageDetails);
                LOGGER.debug("detailIds -> {}", detailIds);
                LOGGER.debug("bmImageDetails -> {}", bmImageDetails);

                if (!bmImageDetails.getImages().isEmpty()) {

                    LOGGER.debug("Calling Add BmImage Api...");
                    for (Map.Entry<String, Resource> img : bmImageDetails.getImages().entrySet()) {

                        responseEntity = this.callAddBmImageApi(detailIds, img);

                        if (responseEntity != null && HttpStatus.OK.equals(responseEntity.getStatusCode())) {
                            response = mapper.readValue(responseEntity.getBody(), PostBmImageResponse.class);
                            LOGGER.debug("Api success response -> {}", response);

                            resultMap.put("Total Row Updated", bmDetails.size());
                            resultMap.put(ApplicationConstants.SUCCESS, true);
                            resultMap.put(ApplicationConstants.MESSAGE, "Benchmark images uploaded successfully!");
                        } else {
                            resultMap.put(ApplicationConstants.SUCCESS, false);
                            resultMap.put(ApplicationConstants.MESSAGE, "Failed to upload Benchmark images!");
                        }
                        count++;
                    }
                    LOGGER.debug("counter -> {}", count);
                } else {
                    LOGGER.error("Image not present for commodity = {}, plantPart = {}, stressType = {}, stress = {}",
                            bmImageDetails.getCommodity(), bmImageDetails.getPlantPart(), bmImageDetails.getStressType(), bmImageDetails.getStress());
                }
            }
            LOGGER.warn("Processing End Time -> {}", LocalTime.now());

        } catch (Exception e) {
            LOGGER.error("Error occurred -> {}", e.getMessage());
            resultMap.put(ApplicationConstants.SUCCESS, false);
            resultMap.put(ApplicationConstants.MESSAGE, "Error occurred -> " + e.getLocalizedMessage());
        }

        return resultMap;
    }

    private ResponseEntity<String> callAddBmImageApi(BmImageDetailIds detailIds, Map.Entry<String, Resource> img) {
        ResponseEntity<String> responseEntity = null;
        MultiValueMap<String, Object> map = null;
        HttpEntity<MultiValueMap<String, Object>> requestEntity = null;
        Resource image = null;

        try {
            String apiUrl = properties.getBmApiUrl() + "?apiKey=" + properties.getApiKey();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            /**
             * Combine the image and data in a request to call '/add-benchmark-image' API
             */
            image = img.getValue();
            LOGGER.debug("image for -> {} --> {}", img.getKey(), image);
            /** if image is present then send the API request */
            if (image != null) {
                map = new LinkedMultiValueMap<>();
                map.add("image", image);
                map.add("metadata", mapper.writeValueAsString(detailIds));

                requestEntity = new HttpEntity<>(map, headers);
                LOGGER.info("Api request -> {}", requestEntity);

                responseEntity = restTemplate.postForEntity(apiUrl, requestEntity, String.class);
                LOGGER.info("Api response -> {} --> {}", responseEntity.getStatusCode(), responseEntity.getBody());
            }

        } catch (Exception e) {
            LOGGER.error("Error while calling API -> {}", e.getMessage());
        }
        return responseEntity;
    }

    public void createDirectory() {
        this._zipFileStorageLocation = Paths.get(fileProperties.getZipExtract()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this._zipFileStorageLocation);
        } catch (Exception e) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", e);
        }
    }

    public OutputStatus editSymptom(Map<String, String> data) {
        OutputStatus outputStatus = new OutputStatus();

        if (data.get("symptomDesc").equals("") || data.get("symptomDesc") == null) {
            outputStatus.setErrCode(ErrorConstants.FAIL_TO_UPDATE);
            outputStatus.setStatus(false);
            outputStatus.setError("Symptom Update Error !");
        } else {
            outputStatus = flipDao.editSymptom(data);
        }
        return outputStatus;
    }
}
