package in.cropdata.toolsuite.drk.service.masterdata;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.Date;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.cropdata.toolsuite.drk.constants.ErrorConstants;
import in.cropdata.toolsuite.drk.exceptions.tileassignment.InvalidApiKeyException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.cropdata.toolsuite.drk.dao.masterdata.ResourceDao;
import in.cropdata.toolsuite.drk.dto.PmpResponse;
import in.cropdata.toolsuite.drk.dto.QualitySpread;
import in.cropdata.toolsuite.drk.exceptions.DataNotFoundException;
import in.cropdata.toolsuite.drk.exceptions.InvalidDataException;
import in.cropdata.toolsuite.drk.exceptions.tileassignment.DirectoryCreationFailedException;
import in.cropdata.toolsuite.drk.exceptions.tileassignment.PathKeyCheckFailedException;
import in.cropdata.toolsuite.drk.model.masterdata.ExistResponse;
import in.cropdata.toolsuite.drk.model.masterdata.OutputStatus;
import in.cropdata.toolsuite.drk.properties.AppProperties;
import in.cropdata.toolsuite.sdk.FileManagerUtil;

@Service
public class ResourceService {

	private static final Logger logger = LoggerFactory.getLogger(ResourceService.class);

	@Autowired
	private ResourceDao resourceDao;

	@Autowired
	private ResourceLoader resourceLoader;

	@Autowired
	JdbcTemplate masterDataJdbcTemplate;

	@Autowired
	private AppProperties appProperties;

	@Autowired
	FileManagerUtil fileManagerUtil;
	
	
	public List<Map<String,Object>> getGstmSync() {
		return resourceDao.getGstmSync();
	
	
	}

	public PmpResponse getPmpDiscovery() {
		return this.resourceDao.getPmpDescovery();
	}

	public ResponseEntity<?> getQualitySpread(QualitySpread qualitySpread) throws ParseException {

		return this.resourceDao.getQualitySpread(qualitySpread);

	}

	public String getDataForResource(long unixTimestamp, int page,  String apiKey, String selectQuery) {

		List<Map<String, Object>> apiDetails = resourceDao.isApiKeyExists(apiKey);
		
		if (apiDetails!= null && !apiDetails.isEmpty()) {
			return resourceDao.getDataForResource(unixTimestamp, page, apiDetails, selectQuery);
		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}

	}

	public String getDataForAgiotaResource(long unixTimestamp, int page,  String apiKey, String selectQuery) {

		List<Map<String, Object>> apiDetails = resourceDao.isApiKeyExists(apiKey);

		if (apiDetails!= null && !apiDetails.isEmpty()) {
			return resourceDao.getDataForAgriOtaResource(unixTimestamp, page, apiDetails, selectQuery);
		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}

	}

	public String getErrorResponse() {
		return resourceDao.errorResponse();
	}

	public String getVillageDataByTehsil(int tehsilCode, long unixTimestamp) {
		return resourceDao.getVillageDatabyTehsil(tehsilCode, unixTimestamp);
	}

	public String getVillageDataByDistrict(int districtCode, long unixTimestamp) {
		return resourceDao.getVillageDatabyDistrict(districtCode, unixTimestamp);
	}

	public String getVillageDataByPanchayat(int panchayatCode, long unixTimestamp) {
		return resourceDao.getVillageDatabyPanchayat(panchayatCode, unixTimestamp);
	}

	public String getPanchayatDataByDistrict(int districtCode, long unixTimestamp) {
		return resourceDao.getPanchayatDataByDistrict(districtCode, unixTimestamp);
	}

	public String getPanchayatDataByTehsil(int tehsilCode, long unixTimestamp) {
		return resourceDao.getPanchayatDataByTehsil(tehsilCode, unixTimestamp);
	}

	public void getMediaDataForResource(HttpServletResponse httpServletResponse, String resource, long unixTimestamp) {

		String table = "";
		OutputStatus os = new OutputStatus();
		RestTemplate restTemplate = new RestTemplate();
		boolean update = false;
		String fileName = "";

		switch (resource) {

		case "stress":
			fileName = "agriStressImages_compressed";
			table = "agri_commodity_stress";
			update = resourceDao.isUpdateFound(table, unixTimestamp);
			break;

		case "stress-symptoms":
			fileName = "stressSymptomsImages_compressed";
			table = "flipbook_symptoms";
			update = resourceDao.isUpdateFound(table, unixTimestamp);
			break;

		case "commodity-plant-part":
			fileName = "commodityPlantPartImages_compressed";
			table = "agri_commodity_plant_part";
			update = resourceDao.isUpdateFound(table, unixTimestamp);
			break;

		default:
			throw new InvalidDataException("Invalid resource url...");
		}

		String directoryURL = appProperties.getFileManagerURL() + "is-file-exist?fileName=" + fileName;
		ExistResponse result = restTemplate.getForObject(directoryURL, ExistResponse.class);
		ResponseEntity<OutputStatus> res = new ResponseEntity<OutputStatus>(os, HttpStatus.FORBIDDEN);

		if (update || !result.isExist()) {
			os = zipMediaData(resource);

		} else {
			os.setMsg("Existing File Sent");
			os.setId(result.getMetadata().get("_id").toString());
			String publicUrl = result.getMetadata().get("publicUrl").toString();
			os.setPublicUrl(publicUrl);
		}
		res = new ResponseEntity<OutputStatus>(os, HttpStatus.OK);

//		String zipURL = appProperties.getFileManagerURL() + "get-file?id=" + res.getBody().getId();
		String zipURL = res.getBody().getPublicUrl();
		httpServletResponse.setHeader("Location", zipURL);
		httpServletResponse.setStatus(302);

	}

	/**
	 * This method is used to get ZIP file of images from module
	 * <b><i>master-data</i></b> which will be available for download.
	 * 
	 * @param httpServletResponse the {@link HttpServletResponse} for setting the
	 *                            file availability in HTTP header
	 * @param resource            the resource name of which the file is to be
	 *                            searched
	 * @param unixTimestamp       the time-stamp for searching the file
	 * 
	 * @return void
	 * 
	 * @author PranaySK
	 */
	public void getMediaForResource(HttpServletResponse httpServletResponse, String resource, long unixTimestamp) {
		OutputStatus os = new OutputStatus();
		String fileName = "";
		/** Check for time-stamp in milliseconds */
		if (Long.toString(unixTimestamp).length() > 10) {
			unixTimestamp = Instant.ofEpochMilli(unixTimestamp).getEpochSecond();
		}

		switch (resource) {

		case "stress":
			fileName = "agriStressImages_compressed";
			break;

		case "stress-symptoms":
			fileName = "stressSymptomsImages_compressed";
			break;

		case "commodity-plant-part":
			fileName = "commodityPlantPartImages_compressed";
			break;

		default:
			throw new InvalidDataException("Invalid resource url...");
		}

		Map<String, Object> searchQuery = new HashMap<>();
		Map<String, Object> timeStampMap = new HashMap<>();
		fileName = "demo/master-data/zip-files/".concat(fileName).concat("_").concat(LocalDate.now().toString());
		String moduleName = "master-data";
		timeStampMap.put("$gte", unixTimestamp);
		searchQuery.put("fileExtension", "zip");
		searchQuery.put("fileName", fileName);
		searchQuery.put("createdAt", timeStampMap);
		logger.info("[getMediaForResource] Search query -> {}", searchQuery);

		Map<String, Object> fileResponseMap = fileManagerUtil.isZipFileExist(null, fileName, moduleName);
		logger.info("[getMediaForResource] fileResponseMap -> {}", fileResponseMap);

		if ("true".equals(fileResponseMap.get("success").toString())) {
			@SuppressWarnings("unchecked")
			Map<String, Object> fileMetadataMap = (Map<String, Object>) fileResponseMap.get("metadata");
			logger.info("[getMediaForResource] fileMetadataMap -> {}", fileMetadataMap);
			os.setMsg("Existing File Sent");
			os.setId(fileMetadataMap.get("_id").toString());
			os.setPublicUrl(fileMetadataMap.get("publicUrl").toString());
			httpServletResponse.setStatus(302);
		} else {
			os.setMsg("Zip File Not Found!");
			os.setId(null);
			os.setPublicUrl(null);
			httpServletResponse.setStatus(404);
		}

		ResponseEntity<OutputStatus> res = new ResponseEntity<>(os, HttpStatus.OK);
		httpServletResponse.setHeader("Location", res.getBody().getPublicUrl());
	}

	// Compression API
	public OutputStatus zipMediaData(String resource) {

		boolean _res = false;
		URI uri = null;
		OutputStatus _op = new OutputStatus();
		RestTemplate restTemplate = new RestTemplate();
		Map<String, String> params = new HashMap<String, String>();
		String FILE_COMPRESSION_URL = appProperties.getFileManagerURL() + "zip-dir";

		if (resource.equals("stress")) {

			uri = UriComponentsBuilder.fromUriString(FILE_COMPRESSION_URL)
					.queryParam("fileName", "agriStressImages_compressed").buildAndExpand(params).toUri();

			uri = UriComponentsBuilder.fromUri(uri).queryParam("moduleName", "master-data")
					.queryParam("pathKey", "AgriStressImages").build().toUri();

		} else if (resource.equals("commodity-plant-part")) {

			uri = UriComponentsBuilder.fromUriString(FILE_COMPRESSION_URL)
					.queryParam("fileName", "commodityPlantPartImages_compressed").buildAndExpand(params).toUri();

			uri = UriComponentsBuilder.fromUri(uri).queryParam("moduleName", "master-data")
					.queryParam("pathKey", "CommodityPlantPartImages").build().toUri();

		} else if (resource.equals("stress-symptoms")) {

			uri = UriComponentsBuilder.fromUriString(FILE_COMPRESSION_URL)
					.queryParam("fileName", "stressSymptomsImages_compressed").buildAndExpand(params).toUri();

			uri = UriComponentsBuilder.fromUri(uri).queryParam("moduleName", "master-data")
					.queryParam("pathKey", "AgriPhenophaseDurationImages").build().toUri();
		}
		try {
			String str = restTemplate.getForObject(uri, String.class);
			ObjectMapper _map = new ObjectMapper();
			Map<String, Object> _m = _map.readValue(str, Map.class);
			_op.setMsg(_m.get("_id").toString());
			_op.setId(_m.get("_id").toString());
			_op.setTimestamp(Instant.now().getEpochSecond());
			_op.setStatus(true);
			_op.setErrCode(" ");

		} catch (Exception e) {

			_op.setMsg(e.getMessage());
			_op.setStatus(true);
			_op.setErrCode(e.getLocalizedMessage());
		}
		return _op;
	}

	public void getMediaFile(HttpServletResponse httpServletResponse, HttpServletRequest request, long unixTimestamp) {
		try {
			String zipURL = null;
			/** Check for time-stamp in milliseconds */
			if (Long.toString(unixTimestamp).length() > 10) {
				unixTimestamp = Instant.ofEpochMilli(unixTimestamp).getEpochSecond();
			}

//			Resource resource = null;
			Map<String, Object> zipFileExist = fileManagerUtil.isZipFileExist(appProperties.getMediaFileId(), null,
					"master-data");
//			String contentType = "application/octet-stream";
			if (zipFileExist != null && zipFileExist.get("exist") != null) {
				@SuppressWarnings("unchecked")
				Map<String, Object> fileMetadataMap = (Map<String, Object>) zipFileExist.get("metadata");
				zipURL = fileMetadataMap.get("publicUrl").toString();

				httpServletResponse.setHeader("Location", zipURL);
				httpServletResponse.setStatus(302);

				// Load file as Resource
//				resource = resourceLoader.getResource(zipURL);
			} else {
				httpServletResponse.setStatus(404);
			}

			/*
			 * return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
			 * .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
			 * resource.getFilename() + "\"") .body(resource);
			 */

		} catch (Exception e) {
			throw new RuntimeException("Internel traversing error " + e.getMessage());
		}
	}

	public void getMediaFile(HttpServletResponse httpServletResponse, long unixTimestamp) {
		try {
			String zipURL = null;
			Map<String, Object> zipFileExist = null;
			String fileName = "media";
			if (unixTimestamp > 0) {
				/** Check for time-stamp in milliseconds */
				LocalDate inputDate = LocalDate
						.ofInstant(Long.toString(unixTimestamp).length() > 10 ? Instant.ofEpochMilli(unixTimestamp)
								: Instant.ofEpochSecond(unixTimestamp), ZoneId.of("Asia/Kolkata"));
				LocalDate todayDate = LocalDate.now();
				logger.info("todayDate -> {} and inputDate -> {}", todayDate, inputDate);
				Period duration = Period.between(inputDate, todayDate);
				int daysDiff = duration.getDays();
				logger.info("daysDiff -> {}", daysDiff);

				if (daysDiff > 1) {
					zipFileExist = fileManagerUtil.isZipFileExist(null, fileName, "master-data");
				} else {
					fileName += "_" + LocalDate.now();
					zipFileExist = fileManagerUtil.isZipFileExist(null, fileName, "master-data");
				}
			} else {
				zipFileExist = fileManagerUtil.isZipFileExist(null, fileName, "master-data");
			}

			if (zipFileExist != null && zipFileExist.get("exist") != null) {
				@SuppressWarnings("unchecked")
				Map<String, Object> fileMetadataMap = (Map<String, Object>) zipFileExist.get("metadata");
				zipURL = fileMetadataMap.get("publicUrl").toString();
				logger.info("zipURL -> {}", zipURL);
				/** Load file as Resource */
				httpServletResponse.setHeader("Location", zipURL);
				httpServletResponse.setStatus(302);

			} else
				httpServletResponse.setStatus(404);

		} catch (Exception ex) {
			throw new DataNotFoundException("Unable to download Media file - Not Found! " + ex.getMessage());
		}
	}

	public ResponseEntity<?> getMediaDataForPlantPartAndStress(HttpServletResponse httpServletResponse,
			HttpServletRequest request, long unixTimestamp) {

		List<Map<String, Object>> urlsData = new ArrayList<>();
		try {
//			logger.info("Removing existing files...");
//			File mediaDir = new File("/home/media");
//			FileUtils.deleteDirectory(mediaDir);
//			mediaDir.mkdirs();
//			logger.info("Generating new file structure...");
//			mediaDir = new File("/home/media/commodityplantpart/");
//			mediaDir.mkdirs();
//			mediaDir = new File("/home/media/symptoms/");
//			mediaDir.mkdirs();
//			mediaDir = new File("/home/media");

			/** Check for time-stamp in milliseconds */
			if (Long.toString(unixTimestamp).length() > 10) {
				unixTimestamp = Instant.ofEpochMilli(unixTimestamp).getEpochSecond();
			}
			RestTemplate restTemplate = new RestTemplate();
			OutputStatus os = new OutputStatus();
			boolean update = false;
			String fileName = "media";

			String directoryURL = appProperties.getFileManagerURL() + "is-file-exist?fileName=" + fileName;
			ExistResponse result = restTemplate.getForObject(directoryURL, ExistResponse.class);
			ResponseEntity<OutputStatus> res = new ResponseEntity<OutputStatus>(os, HttpStatus.FORBIDDEN);
			logger.info("Checking Update found");
			update = resourceDao.isUpdateFound(unixTimestamp);

			if (update || !result.isExist()) {
				logger.info("update found");
				List<String> flipbookSymptomsImages = resourceDao.getImagesForFlipbookSymptoms();
				List<String> commodityPlantPartImages = resourceDao.getImagesForCommodityPlantPart();
				List<String> symtomsUrls = new ArrayList<>();
				if (flipbookSymptomsImages != null && !flipbookSymptomsImages.isEmpty()) {

					for (String str : flipbookSymptomsImages) {

						String[] s = str.split("##");

						if (!s[1].equals("null")) {
							String imageURL = s[1];
							logger.info("Downloading Image: " + imageURL);
							symtomsUrls.add(imageURL);

						}

					}

					Map<String, Object> searchQuery = new HashMap<>();
					Map<String, Object> urlsMap = new HashMap<>();

					String urlsArr[] = symtomsUrls.stream().toArray(String[]::new);
					urlsMap.put("$in", urlsArr);
					searchQuery.put("publicUrl", urlsMap);

					logger.info("urlsArr " + Arrays.toString(urlsArr));

					logger.info("searchQuery " + searchQuery);

					Map<String, Object> symtomsMap = fileManagerUtil.fileSearchAndGetFile("flipbook", searchQuery, 1,
							"zip-files");
					urlsData.add(symtomsMap);
					logger.info("fileSearchAndGetFile " + symtomsMap);
					logger.info("Complete");

				}

				List<String> commodityPlantPartUrls = new ArrayList<>();
				if (commodityPlantPartImages != null && !commodityPlantPartImages.isEmpty()) {

					for (String str : commodityPlantPartImages) {

						String[] s = str.split("##");

						if (!s[1].equals("null")) {

							String imageURL = s[1];
							logger.info("Downloading Image: " + imageURL);
							commodityPlantPartUrls.add(imageURL);

						}
					}
					Map<String, Object> searchQuery = new HashMap<>();
					Map<String, Object> urlsMap = new HashMap<>();

					String urlsArr[] = commodityPlantPartUrls.stream().toArray(String[]::new);
					urlsMap.put("$in", urlsArr);
					searchQuery.put("publicUrl", urlsMap);

					logger.info("urlsArr " + Arrays.toString(urlsArr));

					logger.info("searchQuery " + searchQuery);

					Map<String, Object> commodityMap = fileManagerUtil.fileSearchAndGetFile("master-data", searchQuery,
							1, "zip-files");
					urlsData.add(commodityMap);
					logger.info("fileSearchAndGetFile " + commodityMap);
					logger.info("Complete");
				}

				logger.info("Download completed");

			} else {
				os.setMsg("Existing File Sent");
				os.setId(result.getMetadata().get("_id").toString());
				os.setPublicUrl(result.getMetadata().get("publicUrl").toString());
				logger.info("No update found: sending existing file with ID: " + os.getId());

			}
//			res = new ResponseEntity<OutputStatus>(os, HttpStatus.OK);
////			String zipURL = appProperties.getFileManagerURL() + "get-file?id=" + res.getBody().getId();
//			String zipURL = res.getBody().getPublicUrl();
//			logger.info("Zip URL: " + zipURL);
//			httpServletResponse.setHeader("Location", zipURL);
//			httpServletResponse.setStatus(302);
//
//			// Load file as Resource
//			Resource resource = new FileSystemResource("/home/media.zip");
//
//			// Try to determine file's content type
//			String contentType = null;
//			try {
//				contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
//			} catch (IOException ex) {
//				logger.info("Could not determine file type.");
//				logger.error("File Download Error", ex);
//			}
//
//			// Fallback to the default content type if type could not be determined
//			if (contentType == null) {
//				contentType = "application/octet-stream";
//			}
//
//			return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
//					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//					.body(resource);

		} catch (Exception e) {
			throw new RuntimeException("Internel traversing error");
		}
		return ResponseEntity.status(HttpStatus.OK).body(urlsData);
	}

	private ResponseEntity<Map> uploadZipFile() {
		try {

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			// updating rest template timeout
			HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
			httpRequestFactory.setConnectionRequestTimeout(300000);
			httpRequestFactory.setConnectTimeout(300000);
			httpRequestFactory.setReadTimeout(300000);
			RestTemplate rst = new RestTemplate(httpRequestFactory);

			String pathKeyExistUrl = appProperties.getFileManagerURL() + "/is-path-key-exist";
			String createDirUrl = appProperties.getFileManagerURL() + "create-directory";

			HttpHeaders headersPath = new HttpHeaders();
			headersPath.set("Accept", MediaType.ALL_VALUE);

			UriComponentsBuilder builderPath = UriComponentsBuilder.fromHttpUrl(pathKeyExistUrl).queryParam("pathKey",
					"master-data-zip-files");
			HttpEntity<?> entityPath = new HttpEntity<>(headersPath);

			ResponseEntity<Map> responsePath = null;
			try {
				responsePath = rst.exchange(builderPath.toUriString(), HttpMethod.GET, entityPath, Map.class);
			} catch (Exception e) {
				throw new PathKeyCheckFailedException("Failed to check PathKey Availability: " + e.getMessage());
			}

			if (responsePath != null && responsePath.getStatusCode() == (HttpStatus.OK)) {

				Map<String, Object> body = responsePath.getBody();
				boolean pathExist = Boolean.valueOf((boolean) body.get("exist"));

				if (!pathExist) {

					HttpHeaders headersDir = new HttpHeaders();
					headersDir.set("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
					UriComponentsBuilder builderDir = UriComponentsBuilder.fromHttpUrl(createDirUrl)
							.queryParam("moduleName", "master-data").queryParam("dirPath", "zip-files")
							.queryParam("pathKey", "master-data-zip-files");
					HttpEntity<?> entityDir = new HttpEntity<>(headersDir);

					try {
						ResponseEntity<String> responseDir = rst.exchange(builderDir.toUriString(), HttpMethod.POST,
								entityDir, String.class);
					} catch (Exception e) {
						throw new DirectoryCreationFailedException("Failed to create Directory: " + e.getMessage());
					} // catch
				} // path not exist
			} else {
				throw new PathKeyCheckFailedException(
						"Failed to check PathKey Availability: FileManager failed to response.");
			}

			Map<String, String> params = new HashMap<String, String>();
			String UPLOADFILE_URL = appProperties.getFileManagerURL() + "upload";

			URI uploadUri = UriComponentsBuilder.fromUriString(UPLOADFILE_URL).buildAndExpand(params).toUri();

			uploadUri = UriComponentsBuilder.fromUri(uploadUri).queryParam("moduleName", "master-data")
					.queryParam("pathKey", "master-data-zip-files").queryParam("overwrite", true).build().toUri();

			MultiValueMap<String, Object> _map = new LinkedMultiValueMap<String, Object>();

			_map.add("file", new FileSystemResource("/home/media.zip"));

			logger.info("Uploading Zip File.");
			HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(_map,
					headers);

			ResponseEntity<Map> _sR = rst.exchange(uploadUri, HttpMethod.POST, request, Map.class);
			logger.info("File uploaded Successfully..");

			// delete file from local after uploading.
			File oldMedia = new File("/home/media.zip");
			if (oldMedia.exists()) {
				if (!oldMedia.delete()) {
					oldMedia.deleteOnExit();
				}
			}
			return _sR;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage());
		}
	}

	public static void mkZipDir(String sourceDirPath, String zipFilePath) throws IOException {
		try {
			Path p = Files.createFile(Paths.get(zipFilePath));
			try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(p))) {
				Path pp = Paths.get(sourceDirPath);
				Files.walk(pp).filter(path -> !Files.isDirectory(path)).forEach(path -> {
					ZipEntry zipEntry = new ZipEntry(pp.relativize(path).toString());
					try {
						zs.putNextEntry(zipEntry);
						Files.copy(path, zs);
						zs.closeEntry();
					} catch (IOException e) {
						logger.error(e.getMessage(), e);
					}
				});
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void getMediaDataCron() {
		// check media.zip exist or not
		File mediaZip = new File("/home/media.zip");
		logger.info("deleted old file: " + mediaZip);
		if (mediaZip.exists()) {
			// compare DB update time with file creation time.
			BasicFileAttributes attr;
			try {
				attr = Files.readAttributes(mediaZip.toPath(), BasicFileAttributes.class);
				boolean updateFoundInDB = checkDBUpdates(attr.creationTime());
				if (updateFoundInDB) {
					readImagesAndPrepareZip();
				} // if updateFound in DB
			} catch (IOException e) {
				logger.error("file read error ", e);
			}

		} else {// not exist
			readImagesAndPrepareZip();
		}
	}// getMediaCron

	private boolean checkDBUpdates(FileTime fileTime) {
		boolean updateFoundInDB = false;
		// "flipbook_symptoms", "agri_commodity_plant_part"
		String query = "SELECT UPDATE_TIME FROM INFORMATION_SCHEMA.PARTITIONS WHERE TABLE_SCHEMA = 'cdt_master_data' AND TABLE_NAME ='flipbook_symptoms'";

		Date updatedAt = masterDataJdbcTemplate.queryForObject(query, Date.class);
		if (updatedAt.getTime() > fileTime.toMillis()) {
			logger.info("Upate found in 'flipbook_symptoms' table");
			return true;
		}
		query = "SELECT UPDATE_TIME FROM INFORMATION_SCHEMA.PARTITIONS WHERE TABLE_SCHEMA = 'cdt_master_data' AND TABLE_NAME ='agri_commodity_plant_part'";
		updatedAt = masterDataJdbcTemplate.queryForObject(query, Date.class);
		if (updatedAt.getTime() > fileTime.toMillis()) {
			logger.info("Upate found in 'agri_commodity_plant_part' table");
			return true;
		}
		return updateFoundInDB;
	}// checkDBUpdates

	private void readImagesAndPrepareZip() {
		try {
			logger.info("Removing existing files...");
			File mediaDir = new File("/home/media");
			FileUtils.deleteDirectory(mediaDir);
			mediaDir.mkdirs();
			logger.info("Generating new file structure...");
			mediaDir = new File("/home/media/commodityplantpart/");
			mediaDir.mkdirs();
			mediaDir = new File("/home/media/symptoms/");
			mediaDir.mkdirs();
			mediaDir = new File("/home/media");

			RestTemplate restTemplate = new RestTemplate();

			List<String> flipbookSymptomsImages = resourceDao.getImagesForFlipbookSymptoms();
			List<String> commodityPlantPartImages = resourceDao.getImagesForCommodityPlantPart();

			logger.info("Downloading flipbook symptoms Images ");
			if (flipbookSymptomsImages != null && !flipbookSymptomsImages.isEmpty()) {

				for (String str : flipbookSymptomsImages) {

					String[] s = str.split("##");

					if (!s[1].equals("null")) {

//						String imageURL = appProperties.getFileManagerURL() + "get-file?id=" + s[1];
						String imageURL = s[1];
						logger.info("Downloading Image: " + imageURL);
						try {

							Resource imageResponse = restTemplate.getForObject(imageURL, Resource.class);
							String filePath = "/home/media/symptoms/" + s[0] + ".jpeg";
							File temp = new File(filePath);
							org.apache.commons.io.IOUtils.copy(imageResponse.getInputStream(),
									new FileOutputStream(temp));

						} catch (HttpStatusCodeException exception) {
							exception.printStackTrace();
							logger.error(exception.getMessage(), exception);
						} catch (Exception e) {
							e.printStackTrace();
							logger.error(e.getMessage(), e);
						}
					}
				}
			}

			logger.info("Downloading commodity plant part Images ");
			if (commodityPlantPartImages != null && !commodityPlantPartImages.isEmpty()) {

				for (String str : commodityPlantPartImages) {

					String[] s = str.split("##");

					if (!s[1].equals("null")) {

//						String imageURL = appProperties.getFileManagerURL() + "get-file?id=" + s[1];
						String imageURL = s[1];
						logger.info("Downloading Image: " + imageURL);
						try {
							Resource imageResponse = restTemplate.getForObject(imageURL, Resource.class);
							String filePath = "/home/media/commodityplantpart/" + s[0] + ".jpeg";
							File temp = new File(filePath);
							org.apache.commons.io.IOUtils.copy(imageResponse.getInputStream(),
									new FileOutputStream(temp));

						} catch (HttpStatusCodeException exception) {
							exception.printStackTrace();
							logger.error(exception.getMessage(), exception);
						} catch (Exception e) {
							e.printStackTrace();
							logger.error(e.getMessage(), e);
						}
					}
				}
			}

			logger.info("Download completed");

			if (mediaDir.isDirectory()) {

				if (mediaDir.list().length > 0) {
					File mediaZip = new File("/home/media.zip");
					logger.info("deleted old file: " + mediaZip);
					if (mediaZip.exists()) {
						mediaZip.delete();
					}

					logger.info("Generating new zip file");

					mkZipDir("/home/media", "/home/media.zip");
					logger.info("zip file generated.");
				} // if directory not empty
			} // if directory

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("Zip Generation error", e);
		}
	}// readImagesAndPrepareZip

}