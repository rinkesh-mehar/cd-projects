package in.cropdata.cdtmasterdata.region.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import in.cropdata.cdtmasterdata.exceptions.AlreadyExistException;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.properties.AppProperties;
import in.cropdata.cdtmasterdata.region.dao.ClusterDao;
import in.cropdata.cdtmasterdata.region.model.*;
import in.cropdata.toolsuite.sdk.FileManagerUtil;
import in.cropdata.toolsuite.sdk.dto.FileUploadResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class ClusterService {

	private static final Logger logger = LoggerFactory.getLogger(ClusterService.class);

	@Autowired
	private ClusterDao dao;

	@Autowired
	private RestTemplate restTemplet;

	@Autowired
	private FileManagerUtil fileManagerUtil;

	@Autowired
	private AppProperties appProperties;

	public List<RegionOutputModel> getDetails(Integer regionId, String quadKeys) {
		Integer countRegion = this.dao.getRegionId(regionId);
		logger.info("regionId " + regionId);
		List<RegionOutputModel> detials = null;

		if (countRegion >= 1) {
			throw new AlreadyExistException(regionId + " Already Exist");
		} else {
			String getSubRegions = this.dao.getQuadKeys(quadKeys);

//			if (this.dao.getSubregions(regions) > 1) {
			if (getSubRegions != null) {
				Integer mappedSubregionWithRegionId = this.dao.getMappedSubregionWithRegionId(getSubRegions);

//				Integer mappedSubregionWithRegionId = this.dao.getMappedSubregionWithRegionId(quadKeys);
				logger.info("mappedSubregionWithRegionId {}" , mappedSubregionWithRegionId);
//				Integer subreginsExist = this.dao.getSubreginsExist(regions);
				if (mappedSubregionWithRegionId == 0) {

					this.dao.generateSubRegion(this.dao.getSubRegionsByQuadKey(quadKeys));
					detials = dao.getDetials(getSubRegions);
				} else {
					throw new AlreadyExistException(
							regionId + " Already Mapped subregions with " + mappedSubregionWithRegionId);
				}
			} else {
				throw new DoesNotExistException("Subregions Not Found");
			}

		}
		return detials;

	}

	public ResponseModel generateRegion(Integer regionId, CreateRegion regions) {
		ResponseModel response = new ResponseModel();
//		PostApllicationModel request = new PostApllicationModel();

//		long maxRegionId = dao.getMaxID();
		logger.debug("Region ID -> {}", regionId); // getRegionID
		logger.debug("SubRegions -> {}", regions);
//		request.setRegionID(regionId);
//		request.setSubregion(regions.get_subregions());
//		logger.debug("Request -> {}", request);

//		String rServiceUrl = appProperties.getRApiURL() + "post/region-onboarding";
//		logger.info("rServiceUrl {}", rServiceUrl);
//		RresponseModel result = restTemplet.postForObject(rServiceUrl, request, RresponseModel.class);
//		logger.info("regionID {}", regionId);
		regions.set_subregions(Collections.singletonList(dao.getQuadKeys(regions.get_subregions().toString().replaceAll("[\\[\\]]", ""))));
		response = dao.generateRegion(regionId, regions);

		return response;
	}

	public List<ClusterListModel> getList() {
		return dao.getList();
	}

	/**
	 * It is use for read tile details and read csv,mmpk file
	 *
	 * @param fileWrapper wrap tile details and files
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ResponseModel regionAction(MmpkUploadWrapper fileWrapper) {
		ResponseModel response = new ResponseModel();
		try {
//			MultipartFile file = fileWrapper.getFile();
			String imgId = null;
			String publicUrl = null;
			if (fileWrapper.getImage() != null) {
				Map<String, Object> pathKeyResponse = this.fileManagerUtil.isPathKeyExist("regions");
				if (pathKeyResponse != null && pathKeyResponse.get("exist") != null) {
					boolean result = (boolean) pathKeyResponse.get("exist");
					if (result) {

						FileUploadResponseDTO _fileUploadResp = this.fileManagerUtil.uploadFile("regions", false,
								fileWrapper.getImage().getOriginalFilename(), true, fileWrapper.getImage(), null);
						if (_fileUploadResp != null && _fileUploadResp.isSuccess()) {
							imgId = _fileUploadResp.getId();
							publicUrl = _fileUploadResp.getPublicUrl();
							logger.info("publicUrl >>>>>>>>>>>" + publicUrl);
						}
					}
				}

			}

			List<ClusterCsvModel> csvModelList = null;
			logger.debug("FILE: {}", fileWrapper.getCsvFile());
			logger.debug("Body: {}", fileWrapper.getData());
			FileUploadResponseDTO uploadResponse = null;
			ClusterCsvModel clusterCsvModel = null;

//                    String fileExtension = Objects.requireNonNull(file.getOriginalFilename())
//                            .substring(file.getOriginalFilename().lastIndexOf('.'));

			if (fileWrapper.getCsvFile() != null) {
				csvModelList = this.readCSVFile(fileWrapper.getCsvFile());
				if (csvModelList != null) {
					clusterCsvModel = new ClusterCsvModel();
					ObjectMapper objectMapper = new ObjectMapper();
					clusterCsvModel = objectMapper.readValue(fileWrapper.getData(), ClusterCsvModel.class);
					boolean flag = this.csvValidation(csvModelList, clusterCsvModel);
					if (!flag) {
						response.setSuccess(false);
						response.setErrorMsg("Rows And Columns not match as given csv file rows");
						return response;
					}
				}
			}
			/*
			 * if (fileWrapper.getMmpkFile() != null) { String pathKey =
			 * appProperties.getMmpkPathKey();// mmpkPathKey Map<String, Object>
			 * pathKeyResponse = fileManagerUtil.isPathKeyExist(pathKey);
			 * logger.debug("PATHKEY: {}", pathKeyResponse.entrySet()); if (pathKeyResponse
			 * != null) { boolean result = (boolean) pathKeyResponse.get("exist"); if
			 * (result) { uploadResponse = uploadFile(pathKey, fileWrapper.getMmpkFile()); }
			 * else { String moduleName = "gstm"; String dirPath = pathKey;
			 * DirectoryResponseDTO createDirResp =
			 * fileManagerUtil.createDirectory(moduleName, pathKey, dirPath, null);
			 * logger.debug("CreateDirResp: {}", createDirResp); if (createDirResp != null
			 * && createDirResp.isSuccess()) { uploadResponse = uploadFile(pathKey,
			 * fileWrapper.getMmpkFile()); } } } }
			 */

			// file not null

			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> data = null;
			try {
				data = mapper.readValue(fileWrapper.getData(), Map.class);
				if (imgId != null && publicUrl != null) {
					data.put("imageId", imgId);
					data.put("fileUrl", publicUrl);
				}

				logger.info("data >>>>>>>>>>>>>>>>>.. " + data);
				/*
				 * if (uploadResponse != null) { data.put("_mmpkID", uploadResponse.getId());
				 * data.put("mmpkUrl", uploadResponse.getPublicUrl()); }
				 */

				return dao.regionAction(data, csvModelList);

			} catch (IOException e) {
				logger.error("IO Exception -> {}", e.getMessage());
			}
		} catch (Exception e) {
			logger.debug("Error -> {}", e.getMessage());
		}
		response.setSuccess(false);
		response.setErrorMsg("Region details not update");
		return response;
	}

	public List<StateModel> getState() {
		return dao.getState();
	}
	public List<DistrictModel> getDistrict(){
		return dao.getDistrict();
	}

	public ResponseModel getRegionImage(int id) {
		return dao.getRegionImage(id);
	}

	private FileUploadResponseDTO uploadFile(String pathKey, MultipartFile image) {
		try {
			// uploadFile(String pathKey, boolean renameToId, String fileName, boolean
			// overwrite, MultipartFile file, String metadata)
			FileUploadResponseDTO fileUploadResp = fileManagerUtil.uploadFile(pathKey, false, null, true, image, null);
			logger.debug("FileUploadResp: {}", fileUploadResp);
			return fileUploadResp;
		} catch (Exception e) {
			logger.error("IO Exception -> {}", e.getMessage());
			return null;
		}
	}// uploadFile(String pathKey, MultipartFile image)

	/**
	 * It is use for read data from csv file and put in list
	 *
	 * @param csvFile hold csv file
	 * @return List
	 */
	private List<ClusterCsvModel> readCSVFile(MultipartFile csvFile) {
		List<Object> csvModelList = null;
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(csvFile.getInputStream()))) {

			CsvToBean<Object> csvToBean = new CsvToBeanBuilder<>(reader).withType(ClusterCsvModel.class)
					.withIgnoreLeadingWhiteSpace(true).build();
			logger.info("csvToBean {}", csvToBean);
			csvModelList = csvToBean.parse();

			logger.info("csv data {}", csvModelList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (List<ClusterCsvModel>) (Object) csvModelList;
	}

	/**
	 * It is use for apply validation base on IsRegion field
	 *
	 * @param csvModelList
	 * @param clusterCsvModel
	 * @return
	 */
	private boolean csvValidation(List<ClusterCsvModel> csvModelList, ClusterCsvModel clusterCsvModel) {
		if (csvModelList.size() == (clusterCsvModel.getTotalRow() * clusterCsvModel.getTotalColumn())) {

			/*
			 * for (ClusterCsvModel csvModel : csvModelList) {
			 *//** Validate csv file filed base on isInRegionId *//*
			 * if (csvModel.getIsInRegion() == 1) { if
			 * (csvModel.getDataX() == null ||
			 * csvModel.getDataY() == null ||
			 * csvModel.getDataZ() == null) { return false; } }
			 * }
			 */
			return true;
		} else {
			return false;
		}
	}

}