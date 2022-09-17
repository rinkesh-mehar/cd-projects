package in.cropdata.toolsuite.drk.service.tileassignment;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.cropdata.toolsuite.drk.DrkApplication;
import in.cropdata.toolsuite.drk.dao.ndvi.NDVIDaoInf;
import in.cropdata.toolsuite.drk.dao.pr.BenchmarkSpotRepository;
import in.cropdata.toolsuite.drk.dao.pr.TempBenchmarkRepository;
import in.cropdata.toolsuite.drk.dto.tileassignment.BenchMarkSpotInfDto;
import in.cropdata.toolsuite.drk.dto.tileassignment.BenchmarkCasewiseSpotDto;
import in.cropdata.toolsuite.drk.dto.tileassignment.BenchmarkImageResponseDTO;
import in.cropdata.toolsuite.drk.dto.tileassignment.BenchmarkVO;
import in.cropdata.toolsuite.drk.exceptions.InvalidDataException;
import in.cropdata.toolsuite.drk.exceptions.tileassignment.DirectoryCreationFailedException;
import in.cropdata.toolsuite.drk.exceptions.tileassignment.ImageUploadFailedException;
import in.cropdata.toolsuite.drk.exceptions.tileassignment.PathKeyCheckFailedException;
import in.cropdata.toolsuite.drk.model.pr.BenchmarkSpots;
import in.cropdata.toolsuite.drk.model.pr.TempBenchmarkSpots;
import in.cropdata.toolsuite.drk.model.tileassignment.BenchmarkImage;
import in.cropdata.toolsuite.drk.model.tileassignment.BenchmarkNDVIZL13;
import in.cropdata.toolsuite.drk.properties.AppProperties;
import in.cropdata.toolsuite.drk.repository.tileassignment.BenchmarkImageRepository;
import in.cropdata.toolsuite.drk.repository.tileassignment.BenchmarkNDVIZL13Repository;
import in.cropdata.toolsuite.sdk.FileManagerUtil;
import in.cropdata.toolsuite.sdk.dto.DirectoryResponseDTO;
import in.cropdata.toolsuite.sdk.dto.FileUploadResponseDTO;

@Service
public class BenchmarkService {

	private static final Logger logger = LoggerFactory.getLogger(BenchmarkService.class);

	@Autowired
	private BenchmarkImageRepository benchmarkImageRepository;

//	@Autowired
	private NDVIDaoInf ndviDaoInf;

	@Autowired
	private BenchmarkSpotRepository benchmarkSpotRepository;

	@Autowired
	private TempBenchmarkRepository tempBenchmarkRepository;

	@Autowired
	private BenchmarkNDVIZL13Repository benchmarkNDVIZL13Repository;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	FileManagerUtil fileManagerUtil;

	public ResponseEntity<Map<String, Object>> addBenchmarkImage(BenchmarkImage metadata, MultipartFile image) {

		if (image != null && image.getOriginalFilename() != null && !(image.getOriginalFilename().endsWith(".jpeg")
                || image.getOriginalFilename().endsWith(".jpg")) ) {
			throw new InvalidDataException("Invalid Image. (Only jpeg/jpg images are accepted.)");
		}
//		String fileManagerUrl = "http://" + appProperties.getFileManagerServiceId();
		String dirPath = "";// File.separator + DrkApplication.flipbookModuleName + File.separator;
		Map<String, Object> pathKeyResponse = null;

//		String fileUploadUrl = "";
//		String pathKeyExistUrl = "";
//		String createDirUrl = "";
//		
//		if (fileManagerUrl != null) {
//			if (fileManagerUrl.endsWith("/")) {
//
//				fileUploadUrl = fileManagerUrl + "upload";
//				pathKeyExistUrl = fileManagerUrl + "is-path-key-exist";
//				createDirUrl = fileManagerUrl + "create-directory";
//			} else {
//				fileUploadUrl = fileManagerUrl + "/upload";
//				pathKeyExistUrl = fileManagerUrl + "/is-path-key-exist";
//				createDirUrl = fileManagerUrl + "/create-directory";
//			}
//		} // fileManager not null

		if (validateMetadata(metadata)) {
			String pathKey = metadata.getCommodityId() + "-"
					+ metadata.getStressTypeId() + "-" + metadata.getStressId();
			try {
				pathKeyResponse = fileManagerUtil.isPathKeyExist(pathKey);
				logger.info("pathKeyRespnse : " + pathKeyResponse);
			} catch (Exception e) {
				logger.error("Failed to check PathKey Availability: " + e.getMessage());
				throw new PathKeyCheckFailedException("Failed to check PathKey Availability: " + e.getMessage());
			}

			if (pathKeyResponse != null) {

				boolean pathExist = (boolean) pathKeyResponse.get("exist");
				logger.info("Path Key exist: " + pathExist);
				if (!pathExist) {

					BenchmarkVO benchmarkVO;
					logger.info("Metadata: " + metadata);
//					if (metadata.getStressStageId() != null && metadata.getStressStageId() > 0) {
//						logger.info("fetching values by StressStage : {} commodity id : {} stress id : {}",
//								metadata.getStressStageId(), metadata.getCommodityId(), metadata.getStressId());
//						benchmarkVO = benchmarkImageRepository.getMetaDataByStressStageID (
//								metadata.getStressStageId(), metadata.getCommodityId(), metadata.getStressId());
//						if (benchmarkVO == null) {
//							throw new InvalidDataException(
//									"StressStage does not exist with ID : " + metadata.getStressStageId());
//						}
//					} else {
						logger.info("fetching values by Stress : {} commodity id : {}",
								metadata.getStressId(), metadata.getCommodityId());
						benchmarkVO = benchmarkImageRepository.getData(metadata.getStressId(), metadata.getCommodityId());
						if (benchmarkVO == null) {
							throw new InvalidDataException("Stress does not exist with ID : " + metadata.getStressId());
						}
//					}
					logger.info("Retrived data from DB : " + benchmarkVO);
//					

					dirPath += sanitize(benchmarkVO.getCommodityName()) + File.separator
//							+ sanitize(benchmarkVO.getPhenophaseName()) + File.separator
							+ sanitize(benchmarkVO.getStressTypeName()) + File.separator
							+ sanitize(benchmarkVO.getStressName());

//					if (benchmarkVO.getStrssStageName() != null ) {
//						dirPath += File.separator + sanitize(benchmarkVO.getStrssStageName());
//					}

					logger.info("DirPath: " + dirPath);
					try {
						Map<String, Object> meta = objectMapper.convertValue(metadata, Map.class);
						logger.info("Metadata: -> "+meta);
						DirectoryResponseDTO createDirectoryResponse = fileManagerUtil.createDirectory(

								DrkApplication.flipbookModuleName, pathKey, dirPath, meta);

//								DrkApplication.flipbookModuleName, pathKey, dirPath, meta);

						logger.info("Dir create Response: " + createDirectoryResponse);
					} catch (Exception e) {
						logger.error("Failed to create Directory: " + e);
						throw new DirectoryCreationFailedException("Failed to create Directory: " + e.getMessage());
					} // catch
				} // path not exist
			} else {
				logger.error("Path Key check Failed");
				throw new PathKeyCheckFailedException(
						"Failed to check PathKey Availability: FileManager failed to response.");
			}

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);

			LinkedMultiValueMap<String, String> imageHeaderMap = new LinkedMultiValueMap<>();
			imageHeaderMap.add("Content-disposition", "form-data; name=file; filename=" + image.getOriginalFilename());
			imageHeaderMap.add("Content-type", image.getContentType());
			try {
				String meta = objectMapper.writeValueAsString(metadata);
				FileUploadResponseDTO uploadFile = fileManagerUtil.uploadFile(pathKey, true,
						image.getOriginalFilename(), true, image, meta);
				if (uploadFile != null) {
					String _id = uploadFile.getId();
					String imageUrl = uploadFile.getPublicUrl().substring(uploadFile.getPublicUrl().indexOf("/flipbook"));

					StringBuilder stringBuilder = new StringBuilder(imageUrl);
					if (stringBuilder.length()>0) {
						stringBuilder.insert(0, "<BASEURL>");
					}

					metadata.setImageId(_id);
					metadata.setImageUrl(stringBuilder.toString());
					benchmarkImageRepository.save(metadata);
					logger.info("Benchmark Image added with id : " + _id);
				}
			} catch (IOException e) {
				logger.error("failed to Upload benchmark image.", e);
				throw new ImageUploadFailedException("failed to Upload benchmark image : " + e.getMessage());
			}
		} // validated metadata
		else {
			throw new InvalidDataException("Please enter mandatoris fields");
		}

		Map<String, Object> response = new HashMap<>();
		response.put("success", true);
		response.put("message", "Benchmark Imaged Added Successfully");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	private String sanitize(String dirName) {
		// replace all special characters except '-' and ' ' empty space
		dirName = dirName.replaceAll("[^A-Za-z0-9\\-]+", "-").replaceAll("[\\-]+", "-").toLowerCase();
		if (dirName.endsWith("-")) {
			dirName = dirName.substring(0, dirName.lastIndexOf("-"));
		}
		return dirName;
	}// sanitize

	private boolean validateMetadata(BenchmarkImage metadata) {

	    logger.info("Checking metadata: "+metadata);
		if (metadata != null) {
//	    if (metadata.getStateId() == 0 && metadata.getStateName() == null) {
//		throw new InvalidDataException("stateId and stateName are mandatory.");
//	    }
//	    if (metadata.getRegionId() == 0) {
//		throw new InvalidDataException("regionId is mandatory.");

//	    }

//			if (metadata.getCaseId() == null) {// && metadata.getPhenophaseName() == null
//				throw new InvalidDataException("caseId is mandatory.");
//			}
			if (metadata.getCommodityId() == null) {// && metadata.getCommodityName() == null
				throw new InvalidDataException("commodityId is mandatory.");
			}
//	    if (metadata.getVarietyId() == 0 && metadata.getVarietyName() == null) {
//		throw new InvalidDataException("varietyId and varietyName are mandatory.");
//	    }
//			if (metadata.getPhenophaseId() == null) {// && metadata.getPhenophaseName() == null
//				throw new InvalidDataException("phenophaseId is mandatory.");
//			}

			if (metadata.getStressTypeId() == null) {// && metadata.getStressTypeName() == null
				throw new InvalidDataException("stressTypeId is mandatory.");
			}
			if (metadata.getStressId() == null) {// && metadata.getStressName() == null
				throw new InvalidDataException("stressId is mandatory.");
			}

//	    if (metadata.getStressStageId() == 0 && metadata.getStressStageName() == null) {
//		throw new InvalidDataException("stressStageID and stressStageName are mandatory.");
//	    }
		} else {
			throw new InvalidDataException("metadata is required to process this request.");
		}
		return true;
	}

	public ResponseEntity<Map<String, Object>> addBenchmarkSpotData(List<BenchmarkCasewiseSpotDto> data) {

		try {
			Map<String, Object> map = new HashMap<String, Object>();

			if (data != null && data.size() > 0) {
				List<TempBenchmarkSpots> tempList = new ArrayList<TempBenchmarkSpots>();
				for (BenchmarkCasewiseSpotDto benchmarkSpot : data) {
					TempBenchmarkSpots spot = new TempBenchmarkSpots();
					spot.setSpotId(benchmarkSpot.getSpotID());
					spot.setCaseId(benchmarkSpot.getCaseID());
					spot.setRegionId(benchmarkSpot.getRegionID());
					spot.setSubregionId(benchmarkSpot.getSubRegionID());
					tempList.add(spot);
				} // for

				tempBenchmarkRepository.saveAll(tempList);

				map.put("message", "Benchmark spots data has been processed.");
				map.put("status", true);

				return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);

			} else {
				throw new InvalidDataException("Request body cannot be empty.");
			}

		} catch (Exception e) {
			throw e;
		}
	}

	public ResponseEntity<Map<String, Object>> deleteBenchmarkSpotData() {

		try {

			Map<String, Object> map = new HashMap<String, Object>();

			SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -7);
			Date sevenDaysOldDate = cal.getTime();

			List<BenchmarkSpots> deleteList = benchmarkSpotRepository.getAllOldSpotData(sdf.format(sevenDaysOldDate));

			if (deleteList != null && deleteList.size() > 0) {

				benchmarkSpotRepository.deleteInBatch(deleteList);

				map.put("message", "Data Deleted Successfully with Total Deleted Records = " + deleteList.size());
				map.put("status", 1);
			} else {

				map.put("message", "No Record Older than 7 days Found ");
				map.put("status", 0);
			}

			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);

		} catch (Exception e) {
			throw e;
		}
	}

	public ResponseEntity<Map<String, Object>> addBenchmarkSpotNDVI(int subRegionId, int commodityId,
			int phenophaseId) {

		try {

			Map<String, Object> map = new HashMap<String, Object>();

			BenchMarkSpotInfDto getBenchmarkSpot = benchmarkSpotRepository.getBenchmarkSpotList(subRegionId,
					commodityId, phenophaseId);

			if (getBenchmarkSpot != null) {

//				List<String> spotList = Arrays.asList(getBenchmarkSpot.getSpotId().split(","));
//
//				List<Integer> newSpotList = spotList.stream().map(s -> Integer.parseInt(s))
//						.collect(Collectors.toList());

				Long averageNDVI = ndviDaoInf.getAverageNDVI(getBenchmarkSpot.getSpotID());

				BenchmarkNDVIZL13 ndvi = new BenchmarkNDVIZL13();
				ndvi.setRegionId(getBenchmarkSpot.getRegionID());
				ndvi.setSubRegionId(subRegionId);
				ndvi.setCommodityId(commodityId);
				ndvi.setPhenophaseId(phenophaseId);
				ndvi.setBenchmarkNDVI(averageNDVI);

				BenchmarkNDVIZL13 ndviZL13 = benchmarkNDVIZL13Repository.save(ndvi);

				map.put("message", "Data Inserted Successfully with ID = " + ndviZL13.getId());
				map.put("status", 1);

			} else {
				map.put("message", "No Record Found for Benchmark Spot");
				map.put("status", 0);
			}

			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);

		} catch (Exception e) {
			throw e;
		}
	}

}
