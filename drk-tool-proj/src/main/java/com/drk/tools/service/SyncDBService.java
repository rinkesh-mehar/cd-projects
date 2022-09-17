/**
 * 
 */
package com.drk.tools.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import in.cropdata.toolsuite.sdk.dto.DirectoryResponseDTO;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.drk.tools.config.AppConfig;
import com.drk.tools.constants.ApplicationConstants;
import com.drk.tools.constants.ErrorConstants;
import com.drk.tools.dto.ResponseMessage;
import com.drk.tools.exceptions.DataNotFoundException;
import com.drk.tools.exceptions.DbException;
import com.drk.tools.exceptions.DoesNotExistException;
import com.drk.tools.exceptions.FileUploadFailedException;
import com.drk.tools.exceptions.InvalidTypeException;
import com.drk.tools.model.ExistResponse;
import com.drk.tools.model.MetaData;
import com.drk.tools.model.OutputStatus;
import com.drk.tools.repository.SyncDBDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import in.cropdata.toolsuite.sdk.FileManagerUtil;
import in.cropdata.toolsuite.sdk.dto.FileUploadResponseDTO;

/**
 * @author cropdata-kunal
 *
 */
@Service
public class SyncDBService {

	Logger LOGGER = org.slf4j.LoggerFactory.getLogger(SyncDBService.class);

	@Autowired
	SyncDBDao syncDBDao;

	@Autowired
	private MetaData _metaData;

	@Autowired
	private AppConfig appConfig;

	@Autowired
	FileManagerUtil fileManagerUtil;

	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
    AppConfig properties;
	
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FlipbookService flipbookService;
    
    public static final int LOCATION_TRACKING_START_REGISTRATION = 6;
    public static final int LOCATION_TRACKING_STOP = 8;

	public ResponseEntity<Boolean> getData(String type) {
		ResponseEntity<Boolean> res = new ResponseEntity<>(false, HttpStatus.EXPECTATION_FAILED);
		try {
			boolean isSchemacreate = syncDBDao.sqliteSchemaCreator(type);
			boolean isInsert = false;
			if (isSchemacreate) {
				isInsert = syncDBDao.insertData(type);
				if (isInsert) {
					res = new ResponseEntity<>(true, HttpStatus.OK);
				} else {
					throw new DbException("Failure in Pulling Data from Masters");
				}
			} else {
				throw new DbException("Failed To create Schema");
			}
		} catch (Exception e) {
			throw new DbException(e.getMessage());
		}
		return res;
	}

	public static void mkZipDir(String sourceDirPath, String zipFilePath) throws IOException {
		try {
			File file = new File(sourceDirPath);

			FileOutputStream fos = new FileOutputStream(zipFilePath);
			ZipOutputStream zos = new ZipOutputStream(fos);

			zos.putNextEntry(new ZipEntry(file.getName()));

			byte[] bytes = Files.readAllBytes(Paths.get(sourceDirPath));
			zos.write(bytes, 0, bytes.length);
			zos.closeEntry();
			zos.close();

		} catch (FileNotFoundException ex) {
			System.err.format("The file %s does not exist", sourceDirPath);
		} catch (IOException ex) {
			System.err.println("I/O error: " + ex);
		} catch (Exception e) {
			throw new DoesNotExistException("Local sourceDirPath or zipFilePath is not correct");
		}
	}

	public FileUploadResponseDTO sendFileToUri(String type, Long currentSyncTime) {

		try {
			_metaData.setSyncTime(currentSyncTime);
			String convertMetaData;
			File file = null;
			FileUploadResponseDTO uploadFile = null;

			try {
				convertMetaData = objectMapper.writeValueAsString(_metaData);
			} catch (JsonProcessingException e1) {
				throw new RuntimeException("json conversion failed : " + e1.getMessage());
			}

			if (type.equals("quantycalc")) {
				final String pathKey = "drKrishiTools";

				file = new File(appConfig.getHomeDir() + "/zip/" + ApplicationConstants.SQLITE_QC_ZIP);

				try {

					Map<String, Object> pathKeyResponse = fileManagerUtil.isPathKeyExist(pathKey);
					if (pathKeyResponse != null)
					{
//						boolean result = (Boolean) pathKeyResponse.get("exist");
						if (pathKeyResponse.get("exist").equals("true"))
						{
							LOGGER.info("path key present and uploading zip file..");
							uploadFile = fileManagerUtil.uploadFile("drKrishiTools", false, file.getName(), true,
									fileManagerUtil.getMultipartFile(file), convertMetaData);
						} else
						{
							LOGGER.info("path key dose not exists creating directory..");
							String moduleName = "ts";
							String dirPath = pathKey;
							DirectoryResponseDTO createDirResp = this.fileManagerUtil.createDirectory(moduleName,
									pathKey, dirPath, null);
							if (createDirResp != null && createDirResp.isSuccess())
							{
								uploadFile = fileManagerUtil.uploadFile("drKrishiTools", false, file.getName(), true,
										fileManagerUtil.getMultipartFile(file), convertMetaData);
							}
						}
					}
				} catch (IOException e) {
					throw new RuntimeException("file upload failed " + e.getMessage());
				}

			} else if (type.equals("flipbook")) {

				final String pathKey = "drKrishiTools";

				file = new File(appConfig.getHomeDir() + "/zip/" + ApplicationConstants.SQLITE_FLIP_ZIP);
				LOGGER.info("absolute path of file is  " + file.getAbsolutePath());

				try {
					Map<String, Object> pathKeyResponse = fileManagerUtil.isPathKeyExist(pathKey);
					if (pathKeyResponse != null)
					{
//						boolean result = (boolean) pathKeyResponse.get("exist");
						if (pathKeyResponse.get("exist").equals("true"))
						{
							LOGGER.info("path key present and uploading zip file..");
							uploadFile = fileManagerUtil.uploadFile("drKrishiTools", false, file.getName(), true,
									fileManagerUtil.getMultipartFile(file), convertMetaData);
						} else
						{
							LOGGER.info("path key dose not exists creating directory..");
							String moduleName = "ts";
							String dirPath = pathKey;
							DirectoryResponseDTO createDirResp = this.fileManagerUtil.createDirectory(moduleName,
									pathKey, dirPath, null);
							if (createDirResp != null && createDirResp.isSuccess())
							{
								uploadFile = fileManagerUtil.uploadFile("drKrishiTools", false, file.getName(), true,
										fileManagerUtil.getMultipartFile(file), convertMetaData);
							}
						}
					}
				} catch (IOException e) {
					throw new RuntimeException("file upload failed " + e.getMessage());
				}

			} else if (type.equals("flipbook-images")) {

//			file = new File(appConfig.getHomeDir() + "/zip/" + ApplicationConstants.SQLITE_FLIP_IMG_ZIP);
//
//			try {
//				uploadFile = fileManagerUtil.uploadFile("drKrishiTools", false, file.getName(), true,
//						fileManagerUtil.getMultipartFile(file), convertMetaData);
//			} catch (IOException e) {
//				throw new RuntimeException("file upload faild " + e.getMessage());
//			}

			}

			return uploadFile;

		} catch (RuntimeException e) {
			throw new FileUploadFailedException(ErrorConstants.FILE_UPLOAD_FAILED,
					"file upload failed..." + e.getMessage());
		}
	}

	public OutputStatus getExistingData(ExistResponse backupResponse, boolean flafForNewUser) {

		OutputStatus _op = new OutputStatus();
		Map convertValue = objectMapper.convertValue(backupResponse, HashMap.class);
		OutputStatus convertValue2 = objectMapper.convertValue(convertValue, OutputStatus.class);
		convertValue2.setMessage("Existing File Sent");
		convertValue2.setID(backupResponse.getMetadata().get("_id").toString());
		convertValue2.setPublicUrl(backupResponse.getMetadata().get("publicUrl").toString());
		if (flafForNewUser) {
			convertValue2.setStatus(true);
		} else {
			convertValue2.setStatus(false);
		}
		convertValue2.setErrorCode("NA");
		convertValue2.setTimestamp(Long.parseLong(backupResponse.getMetadata().get("syncTime").toString()));

		return convertValue2;
	}

	/* File Collection to Folder */
//	public Map<String, Object> searchAndGetFile() {
//		log.info("Preparing search query");
//		Map<String, Object> searchQuery = new HashMap<>();
//		Map<String, Object> extensionMap = new HashMap<>();
//		extensionMap.put("$in", new String[] { "jpeg" });
//		searchQuery.put("fileExtension", extensionMap);
//		log.info("search query : {}", searchQuery);
//
//		Map<String, Object> searchAndGetFile = fileManagerUtil.searchAndGetFile("flipbook", searchQuery, 1598957943);
//		log.info("SearchResponse :-> {}", searchAndGetFile);
//		return searchAndGetFile;
//	}

	public ResponseEntity<Map<String, Object>> serverUpload(String type, String syncTime) throws IOException {

		OutputStatus _op = new OutputStatus();
		FileUploadResponseDTO _sR = null;
		Map convertValue2 = objectMapper.convertValue(_op, HashMap.class);
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String, Object>>(convertValue2,
				HttpStatus.FORBIDDEN);

		if (type == null || type.isEmpty()) {
			throw new InvalidTypeException(ErrorConstants.INVALID_TYPE, "type cannot be null or empty...");
		} else if (!type.equals("quantycalc") && !type.equals("flipbook") && !type.equals("flipbook-images")) {
			throw new InvalidTypeException(ErrorConstants.INVALID_TYPE,
					"Invalid type...Please give proper value for type...");
		}

		try {
			Long currentSyncTime = Instant.now().getEpochSecond();
			File _file = null;
			File _zipFile = null;
			Map<String, Object> fileExist = null;
			Map<String, Object> searchAndGetFile = null;

			if (type.equals("quantycalc")) {

				_file = new File(appConfig.getHomeDir() + "/db/" + ApplicationConstants.SQLITE_QC_DB);
				_zipFile = new File(appConfig.getHomeDir() + "zip/" + ApplicationConstants.SQLITE_QC_DB);

//				fileExist = fileManagerUtil.isFileExist(null, type);

			} else if (type.equals("flipbook")) {

				_file = new File(appConfig.getHomeDir() + "/db/" + ApplicationConstants.SQLITE_FLIP_DB);
				_zipFile = new File(appConfig.getHomeDir() + "zip/" + ApplicationConstants.SQLITE_FLIP_DB);

				fileExist = fileManagerUtil.isFileExist(null, type);

			} else if (type.equals("flipbook-images")) {

				Map<String, Object> responseMap = new HashMap<>();
				Map<String, Object> convertValue = null;

				try {
					String zipURL = "https://cdtuatts.blob.core.windows.net/cdtuatts/demo/master-data/zip-files/symptoms.zip";
//					long unixTimestamp = Long.parseLong(syncTime);
//					String zipURL = null;
//					Map<String, Object> zipFileExist = null;
//					String fileName = "media";
//					if (unixTimestamp > 0) {
//						/** Check for time-stamp in milliseconds */
//						LocalDate inputDate = LocalDate
//								.ofInstant(Long.toString(unixTimestamp).length() > 10 ? Instant.ofEpochMilli(unixTimestamp)
//										: Instant.ofEpochSecond(unixTimestamp), ZoneId.of("Asia/Kolkata"));
//						LocalDate todayDate = LocalDate.now();
//						log.info("todayDate -> {} and inputDate -> {}", todayDate, inputDate);
//						Period duration = Period.between(inputDate, todayDate);
//						int daysDiff = duration.getDays();
//						log.info("daysDiff -> {}", daysDiff);
//
//						if (daysDiff > 1) {
//							zipFileExist = fileManagerUtil.isZipFileExist(null, fileName, "master-data");
//						} else {
//							fileName += "_" + LocalDate.now();
//							zipFileExist = fileManagerUtil.isZipFileExist(null, fileName, "master-data");
//						}
//					} else {
//						zipFileExist = fileManagerUtil.isZipFileExist(null, fileName, "master-data");
//					}
//
//					if (zipFileExist != null && zipFileExist.get("exist").equals(true)) {
//						@SuppressWarnings("unchecked")
//						Map<String, Object> fileMetadataMap = (Map<String, Object>) zipFileExist.get("metadata");
//						zipURL = fileMetadataMap.get("publicUrl").toString();
//						log.info("zipURL -> {}", zipURL);
//						/** Load file as Resource */
//
//						responseMap.put("status", true);
//						responseMap.put("publicUrl", zipURL);
//						responseMap.put("message", "Flipbook Images File Sent");
//
//					} else {
//						responseMap.put("status", false);
//						responseMap.put("errorCode", ErrorConstants.NO_UPDATE_FOUND);
//					}
//					FileUploadResponseDTO zipUploadResponse = flipbookService.getAllFlipbookSymptom();
					responseMap.put("status", true);
//					responseMap.put("publicUrl", zipUploadResponse.getPublicUrl());
					responseMap.put("publicUrl", appConfig.getZipFileUrlAllSymptom());
					responseMap.put("message", "Flipbook Images File Sent");

					convertValue = objectMapper.convertValue(responseMap, HashMap.class);
					return new ResponseEntity<Map<String, Object>>(convertValue, HttpStatus.OK);
				} catch (Exception ex) {
					throw new DataNotFoundException("Unable to download Media file - Not Found! " + ex.getMessage());
				}
			}

			_file.mkdirs();
			_zipFile.mkdirs();

			Map<String, Object> convertValue = null;
			ExistResponse result = objectMapper.convertValue(fileExist, ExistResponse.class);

			if (!type.equals("flipbook-images")) {

//				if (fileExist.get("exist").equals(false)) {
					LOGGER.info("serverUpload() --> File Does Not Exist");
					_sR = generateAndUploadFile(type, currentSyncTime, _file, _zipFile, _sR, _op, convertValue, res);

					_op.setPublicUrl(_sR.getPublicUrl());

					_op.setMessage("File Created and Sent SuccessFully");
					_op.setTimestamp(currentSyncTime);
					_op.setStatus(true);

					convertValue = objectMapper.convertValue(_op, HashMap.class);
					res = new ResponseEntity<>(convertValue, HttpStatus.OK);

//				} else {
					LOGGER.info("serverUpload() --> File Exists");
					// Check File Exist Or Not
//					if (result.isExist() && !syncTime.equals("0")) {
						LOGGER.info("serverUpload() --> File Exists --> SyncTime > 0 ");
						if ((Long.parseLong(syncTime) + (appConfig.getHours() * 60 * 60)) < currentSyncTime) {
							LOGGER.info("serverUpload() --> File Exists --> SyncTime is less than Current Time");
							if (syncDBDao.isUpdateFound(syncTime, type)) {

								_sR = generateAndUploadFile(type, currentSyncTime, _file, _zipFile, _sR, _op,
										convertValue, res);

								_op.setPublicUrl(_sR.getPublicUrl());

								_op.setMessage("File Created and Sent SuccessFully");
								_op.setTimestamp(currentSyncTime);
								_op.setStatus(true);

								convertValue = objectMapper.convertValue(_op, HashMap.class);
								res = new ResponseEntity<Map<String, Object>>(convertValue, HttpStatus.OK);

							} else {
								LOGGER.info("Update not found in tables");
								_op.setPublicUrl(result.getMetadata().get("publicUrl").toString());
								_op.setMessage("No Update Found in Database Tables, Existing File Sent");
								_op.setErrorCode(ErrorConstants.NO_UPDATE_FOUND);
								_op.setTimestamp(currentSyncTime);
								_op.setStatus(false);

								convertValue = objectMapper.convertValue(_op, HashMap.class);
								res = new ResponseEntity<Map<String, Object>>(convertValue, HttpStatus.OK);
							}
						} else {
							LOGGER.info("serverUpload() --> File Exists --> Sync Time is Greater than Current Time");
							_op.setPublicUrl(result.getMetadata().get("publicUrl").toString());
							_op.setMessage("Existing File Sent");
							_op.setErrorCode(ErrorConstants.NO_UPDATE_FOUND);
							_op.setTimestamp(currentSyncTime);
							_op.setStatus(false);

							convertValue = objectMapper.convertValue(_op, HashMap.class);
							res = new ResponseEntity<Map<String, Object>>(convertValue, HttpStatus.OK);
						}

//					} else if (result.isExist() && syncTime.equals("0")) {
						LOGGER.info("serverUpload() --> File Exists --> Sync time is 0 Generating new file and uploading it");

						if (syncDBDao.isUpdateFound(syncTime, type)) {

							_sR = generateAndUploadFile(type, currentSyncTime, _file, _zipFile, _sR, _op,
									convertValue, res);

							_op.setPublicUrl(_sR.getPublicUrl());

							_op.setMessage("File Created and Sent SuccessFully");
							_op.setTimestamp(currentSyncTime);
							_op.setStatus(true);

							convertValue = objectMapper.convertValue(_op, HashMap.class);
							res = new ResponseEntity<Map<String, Object>>(convertValue, HttpStatus.OK);

						} else {

							_op.setPublicUrl(result.getMetadata().get("publicUrl").toString());
							_op.setMessage("Existing File Sent");
							// _op.setErrorCode(ErrorConstants.NO_UPDATE_FOUND);
							_op.setTimestamp(currentSyncTime);
							_op.setStatus(true);

							convertValue = objectMapper.convertValue(_op, HashMap.class);
							res = new ResponseEntity<Map<String, Object>>(convertValue, HttpStatus.OK);
						}
//					}
//				}
			}

		} catch (FileUploadFailedException e) {
			throw new FileUploadFailedException(ErrorConstants.FILE_UPLOAD_FAILED, "File Upload Failed..");
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return res;
	}

	private FileUploadResponseDTO generateAndUploadFile(String type, Long currentSyncTime, File _file, File _zipFile,
			FileUploadResponseDTO _sR, OutputStatus _op, Map<String, Object> convertValue,
			ResponseEntity<Map<String, Object>> res) {

		if (_file.exists()) {
			_file.delete();
		}
		if (_zipFile.exists()) {
			_zipFile.delete();
		}

		// Create New File
		getData(type);

		try {

			if (type.equals("quantycalc")) {

				mkZipDir(appConfig.getHomeDir() + "/db/" + ApplicationConstants.SQLITE_QC_DB,
						appConfig.getHomeDir() + "zip/" + ApplicationConstants.SQLITE_QC_ZIP);

			} else if (type.equals("flipbook")) {

				mkZipDir(appConfig.getHomeDir() + "/db/" + ApplicationConstants.SQLITE_FLIP_DB,
						appConfig.getHomeDir() + "zip/" + ApplicationConstants.SQLITE_FLIP_ZIP);

			} /*
				 * else if (type.equals("flipbook-images")) { mkZipDir(appConfig.getHomeDir() ,
				 * appConfig.getHomeDir() ); }
				 */

		} catch (IOException e) {
			LOGGER.error("failed to create zip ----> " + e.getMessage());
			e.printStackTrace();
		}

		_sR = sendFileToUri(type, currentSyncTime);

		if (_sR != null) {

			_op.setPublicUrl(_sR.getPublicUrl());

			_op.setMessage("File Created and Sent SuccessFully");
			_op.setTimestamp(currentSyncTime);
			_op.setStatus(true);

			convertValue = objectMapper.convertValue(_op, HashMap.class);
			res = new ResponseEntity<Map<String, Object>>(convertValue, HttpStatus.OK);

		}

		return _sR;

	}

	public Map<String, Object> uploadimages(String syncTime) {

		Map<String, Object> responseMap = new HashMap<String, Object>();

		try {

			List<String> imageUrls = syncDBDao.getAllImages();

			for (String url : imageUrls) {

				URL u = new URL(url);
				String fileName = u.getFile();
				String destName = "appConfig.getHomeDir()" + "/images/" + fileName.substring(fileName.lastIndexOf("/"));
				InputStream is = u.openStream();
				OutputStream os = new FileOutputStream(destName);

				byte[] b = new byte[2048];
				int length;

				while ((length = is.read(b)) != -1) {
					os.write(b, 0, length);
				}
				is.close();
				os.close();
			}

			responseMap.put("message", "cron run successfully to upload images");
			responseMap.put("status", true);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return responseMap;
	}

	public Map<String, Object> getAllRegion() {

		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<Map<String, Object>> getAllRegion = null;

		try {

			getAllRegion = syncDBDao.getAllRegion();

			responseMap.put("regionList", getAllRegion);

			if (getAllRegion == null || getAllRegion.size() == 0) {
				responseMap.put("message", "No data found for regions");
				responseMap.put("status", false);
			} else {
				responseMap.put("message", "Region List Delivered Successfully");
				responseMap.put("status", true);
			}

		} catch (Exception e) {
			responseMap.put("message", e.getMessage());
			responseMap.put("status", false);
		}
		return responseMap;
	}

	public Map<String, Object> getAllVillagesByRegion(Integer regionId) {

		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<Map<String, Object>> getAllVillagesByRegion = null;

		try {

			getAllVillagesByRegion = syncDBDao.getAllVillagesByRegion(regionId);

			responseMap.put("villageList", getAllVillagesByRegion);

			if (getAllVillagesByRegion == null || getAllVillagesByRegion.size() == 0) {
				responseMap.put("message", "No data found for Villages");
				responseMap.put("status", false);
			} else {
				responseMap.put("message", "Village List Delivered Successfully");
				responseMap.put("status", true);
			}

		} catch (Exception e) {
			responseMap.put("message", e.getMessage());
			responseMap.put("status", false);
		}
		return responseMap;
	}

	public Map<String, Object> getAllRoles() {

		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<Map<String, Object>> getAllRoles = null;

		try {

			getAllRoles = syncDBDao.getAllRoles();

			responseMap.put("roleList", getAllRoles);

			if (getAllRoles == null || getAllRoles.size() == 0) {
				responseMap.put("message", "No data found for Roles");
				responseMap.put("status", false);
			} else {
				responseMap.put("message", "Role List Delivered Successfully");
				responseMap.put("status", true);
			}

		} catch (Exception e) {
			responseMap.put("message", e.getMessage());
			responseMap.put("status", false);
		}
		return responseMap;
	}

	public Map<String, Object> getScoutByRole(Integer roleId) {

		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<Map<String, Object>> getScoutByRole = null;

		try {

			getScoutByRole = syncDBDao.getScoutByRole(roleId);

			responseMap.put("scoutList", getScoutByRole);

			if (getScoutByRole == null || getScoutByRole.size() == 0) {
				responseMap.put("message", "No data found for Scouts");
				responseMap.put("status", false);
			} else {
				responseMap.put("message", "Scout List Delivered Successfully");
				responseMap.put("status", true);
			}

		} catch (Exception e) {
			responseMap.put("message", e.getMessage());
			responseMap.put("status", false);
		}
		return responseMap;
	}

	public Map<String, Object> getAllFarmersByVillage(Integer villageId) {

		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<Map<String, Object>> getAllFarmersByVillage = null;
		List<Map<String, Object>> getAllPointOfInterestByVillage = null;
		Map<String, Object> getFarmerLatLong = null;
		List<Map<String, Object>> farmerMapList = new ArrayList<>();

		try {

			getAllFarmersByVillage = syncDBDao.getAllFarmersByVillage(villageId);
			getAllPointOfInterestByVillage = syncDBDao.getAllPointOfInterestByVillage(villageId);

			for (Map<String, Object> map : getAllFarmersByVillage) {

				Map<String, Object> newMap = new HashMap();
				newMap.put("ID", (String) map.get("ID"));
				newMap.put("farmerName", map.get("name"));
				newMap.put("farmerMobile", map.get("mobileNumber"));

				getFarmerLatLong = syncDBDao.getFarmerLatLong((String) map.get("ID"));

				if (getFarmerLatLong != null && !getFarmerLatLong.isEmpty()) {

//					map.put("Latitude", getFarmerLatLong.get("Latitude"));
//					map.put("Longitude", getFarmerLatLong.get("Longitude"));

					newMap.put("latitude", getFarmerLatLong.get("Latitude"));
					newMap.put("longitude", getFarmerLatLong.get("Longitude"));
				} else {
					newMap.put("latitude", 0.0);
					newMap.put("longitude", 0.0);
				}

				farmerMapList.add(newMap);
			}

			responseMap.put("farmerList", farmerMapList);
			responseMap.put("pointOfInterestList", getAllPointOfInterestByVillage);

			if ((getAllFarmersByVillage == null || getAllFarmersByVillage.size() == 0)
					&& (getAllPointOfInterestByVillage != null && getAllPointOfInterestByVillage.size() > 0)) {
				responseMap.put("message", "No data found for Farmers, POI List Delivered");
				responseMap.put("status", true);
			} else if ((getAllFarmersByVillage != null && getAllFarmersByVillage.size() > 0)
					&& (getAllPointOfInterestByVillage == null || getAllPointOfInterestByVillage.size() == 0)) {
				responseMap.put("message", "No data found for POI, Farmer List Delivered");
				responseMap.put("status", true);
			} else if ((getAllFarmersByVillage == null || getAllFarmersByVillage.size() == 0)
					&& (getAllPointOfInterestByVillage == null || getAllPointOfInterestByVillage.size() == 0)) {
				responseMap.put("message", "No data found for Farmers and POI");
				responseMap.put("status", false);
			} else {
				responseMap.put("message", "Farmer And POI List Delivered Successfully");
				responseMap.put("status", true);
			}

		} catch (Exception e) {
			responseMap.put("FarmerList", getAllFarmersByVillage);
			responseMap.put("PointOfInterestList", getAllPointOfInterestByVillage);
			responseMap.put("message", e.getMessage());
			responseMap.put("status", false);
		}
		return responseMap;
	}

//	public Map<String, Object> getAllPointOfInterestByVillage(Integer villageId) {
//
//		Map<String, Object> responseMap = new HashMap<String, Object>();
//		List<Map<String, Object>> getAllPointOfInterestByVillage = null;
//
//		try {
//
//			getAllPointOfInterestByVillage = syncDBDao.getAllPointOfInterestByVillage(villageId);
//
//			responseMap.put("PointOfInterestList", getAllPointOfInterestByVillage);
//
//			if (getAllPointOfInterestByVillage == null || getAllPointOfInterestByVillage.size() == 0) {
//				responseMap.put("message", "No data found for PointOfInterest");
//				responseMap.put("status", false);
//			} else {
//				responseMap.put("message", "PointOfInterest List Delivered Successfully");
//				responseMap.put("status", true);
//			}
//
//		} catch (Exception e) {
//			responseMap.put("message", e.getMessage());
//			responseMap.put("status", false);
//		}
//		return responseMap;
//	}

	public boolean matchAppKey(String appKey) {
		try {
			String key = syncDBDao.getAppKey();
			if (key != null && key.equals(appKey)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public Map<String, Object> getVillagesByScout(Integer scoutId, Integer regionId) {

		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<Map<String, Object>> villagesByScout = null;

		try {

			villagesByScout = syncDBDao.villagesByScout(scoutId, regionId);

			responseMap.put("villageList", villagesByScout);

			if (villagesByScout == null || villagesByScout.size() == 0) {
				responseMap.put("message", "No data found for Villages");
				responseMap.put("status", false);
			} else {
				responseMap.put("message", "Village List Delivered Successfully");
				responseMap.put("status", true);
			}

		} catch (Exception e) {
			responseMap.put("message", e.getMessage());
			responseMap.put("status", false);
		}
		return responseMap;
	}

	public Map<String, Object> getFarmerLatLong(String farmerId) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		Map<String, Object> getFarmerLatLong = new HashMap<String, Object>();
		HttpEntity<?> requestEntity = null;

		try {

			getFarmerLatLong = syncDBDao.getFarmerLatLong(farmerId);

			responseMap.put("farmerLatLng", getFarmerLatLong);

			if (getFarmerLatLong == null || getFarmerLatLong.size() == 0) {
				
				String apiUrl = properties.getBfaNotificationApiUrl() + "?apiKey=" + properties.getBfaApiKey()+"&type=" + LOCATION_TRACKING_START_REGISTRATION + "&farmerId=" + farmerId;
                HttpHeaders headers = new HttpHeaders();
                headers.add("appKey", properties.getBfaAppKey());

           

                requestEntity = new HttpEntity<>(headers);
                LOGGER.info("Api request -> {}", requestEntity);

//                responseEntity = restTemplate.postForEntity(apiUrl, requestEntity, String.class);
                LOGGER.info("Api Url -> {}", apiUrl);
                Object resp = restTemplate.postForEntity(apiUrl, requestEntity, Object.class);
                LOGGER.info("Api response -> {} --> {}", resp);
//                logger.info("Api response -> {} --> {}", responseEntity.getStatusCode(), responseEntity.getBody());
                
				responseMap.put("message", "No data found for Farmer's LatLong");
				responseMap.put("status", false);
				
			} else {
				
				responseMap.put("message", "Farmer's LatLong List Delivered Successfully");
				responseMap.put("status", true);
				
			}

		} catch (Exception e) {
			responseMap.put("message", e.getMessage());
			responseMap.put("status", false);
		}
		return responseMap;
	}

	public Map<String, Object> getPanchayatByScout(Integer scoutId) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<Map<String, Object>> panchayatByScout = null;

		try {

			Map<String, Object> getRoleByScoutId = syncDBDao.getRoleByScoutId(scoutId);

			if (getRoleByScoutId != null) {

				boolean isPRS = getRoleByScoutId.get("code").equals("PRS");

				if (isPRS) {

					panchayatByScout = syncDBDao.getPanchayatByScout(scoutId);

					if (panchayatByScout != null && panchayatByScout.size() > 0) {
						responseMap.put("panchayatList", panchayatByScout);
						responseMap.put("message", "Panchayat List Delivered Successfully");

					} else {
						responseMap.put("message", "No data found for Panchayat");
						responseMap.put("status", false);
					}
				} else {

					panchayatByScout = syncDBDao.villagesByScout(scoutId, null);

					if (panchayatByScout != null && panchayatByScout.size() > 0) {
						responseMap.put("panchayatList", panchayatByScout);
						responseMap.put("message", "Villages List Delivered Successfully");

					} else {
						responseMap.put("message", "No data found for Villages");
						responseMap.put("status", false);
					}

				}
			} else {
				responseMap.put("message", "Scout ID is required");
				responseMap.put("status", false);
			}

		} catch (Exception e) {
			responseMap.put("message", e.getMessage());
			responseMap.put("status", false);
		}
		return responseMap;
	}
	
	
	public ResponseMessage deleteFarmarLatLong() {

		ResponseMessage responseMessage = new ResponseMessage();
		try {

			// Time Before 30 Minutes from current time
			long beforeTime = System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(30);

			LOGGER.info("time -> {}", new Timestamp(beforeTime));

			List<Map<String, Object>> farmarLatLongList = syncDBDao
					.getFarmarLatLongListByBeforeTime(new Timestamp(beforeTime));

			LOGGER.info("farmarLatLongList Size -> {}", farmarLatLongList.size());

			if (farmarLatLongList != null && !farmarLatLongList.isEmpty() && farmarLatLongList.size() > 0) {

				for (Map<String, Object> map : farmarLatLongList) {
					LOGGER.info("farmarLatLong -> {}", map);
//					this.sendStopNotification(String.valueOf(map.get("FarmerID")));

					Integer id = Integer.parseInt(String.valueOf(map.get("ID")));
					syncDBDao.deleteFarmarLatLongById(id);
				}

			} else {

				LOGGER.info("No data found in farmer_location table ...");

			}

			responseMessage.setMessage(
					"cron run and records deleted successfully. " + farmarLatLongList.size() + " record affected.");
			responseMessage.setSuccess(true);

		} catch (Exception e) {
			responseMessage.setError("Failed to run cron..." + e.getMessage());
			responseMessage.setSuccess(false);
			e.printStackTrace();
		}

		return responseMessage;
	}
	
	 public void sendStopNotification(String farmerId){

		 HttpEntity<?> requestEntity = null;
		 try {
			 String apiUrl = properties.getBfaNotificationApiUrl() + "?apiKey=" + properties.getBfaApiKey()+"&type=" + LOCATION_TRACKING_STOP + "&farmerId=" + farmerId;
	         HttpHeaders headers = new HttpHeaders();
	         headers.add("appKey", properties.getBfaAppKey());

	    

	         requestEntity = new HttpEntity<>(headers);
	         LOGGER.info("Api request -> {}", requestEntity);

//	         responseEntity = restTemplate.postForEntity(apiUrl, requestEntity, String.class);
	         LOGGER.info("Api Url -> {}", apiUrl);
	         Object resp = restTemplate.postForEntity(apiUrl, requestEntity, Object.class);
	         LOGGER.info("Api response -> {} --> {}", resp);
//	         logger.info("Api response -> {} --> {}", responseEntity.getStatusCode(), responseEntity.getBody());
		} catch (Exception e) {
			LOGGER.error("Server Error : ", e);
		}
		 
	 }
}
