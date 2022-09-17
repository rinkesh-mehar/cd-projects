package in.cropdata.cdtmasterdata.service;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import in.cropdata.cdtmasterdata.dto.AgriCommodityAlias;
import in.cropdata.cdtmasterdata.dto.AgroCommodityDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.constants.DBConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriCommodityInfo;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriFieldActivityInfDto;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.AgriCommodity;
import in.cropdata.cdtmasterdata.repository.AgriCommodityRepositoy;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;
import in.cropdata.cdtmasterdata.website.model.ProductsAndServices;
import in.cropdata.cdtmasterdata.website.service.ProductsAndServicesService;
import in.cropdata.toolsuite.sdk.FileManagerUtil;
import in.cropdata.toolsuite.sdk.dto.DirectoryResponseDTO;
import in.cropdata.toolsuite.sdk.dto.FileUploadResponseDTO;

@Service
public class AgriCommodityService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AgriCommodityService.class);

	@Autowired
	private AgriCommodityRepositoy agriCommodityRepositoy;

	@Autowired
	private AclHistoryUtil approvalUtil;
	
	@Autowired
    private FileManagerUtil fileManagerUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<AgriCommodityInfo> getAllAgriCommodities() {
		try {
			return agriCommodityRepositoy.getAllCommodity();
		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriCommodities

	public Page<AgriCommodityInfo> getAllAgriCommodityPeginated(int page, int size, String searchText) {

		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());

			Page<AgriCommodityInfo> commodityList = agriCommodityRepositoy.findAllWithSearch(sortedByIdDesc,
					searchText);

			return commodityList;

		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriCommodityPeginated

	public ResponseMessage addAgriCommodity(AgroCommodityDTO agroCommodityDTO) {
		AgriCommodity agriCommodity = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			agriCommodity = objectMapper.readValue(agroCommodityDTO.getData(), AgriCommodity.class);
            if (agroCommodityDTO.getLogoFile() != null)
            {
                FileUploadResponseDTO manageFile = this.getManageFile(agroCommodityDTO.getLogoFile());
                agriCommodity.setLogo(manageFile.getPublicUrl());
            }
			agriCommodity = agriCommodityRepositoy.save(agriCommodity);

			approvalUtil.addRecord(DBConstants.TBL_AGRI_COMMODITY, agriCommodity.getId());

			return responseMessageUtil.sendResponse(true, "Agri-Commodity" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (DataIntegrityViolationException e) {
			LOGGER.error("DataIntegrityViolationException : ", e);
			return responseMessageUtil.sendResponse(false, "", "Duplicate entry for commodity " + agriCommodity.getName());
		}catch (Exception e) {
			LOGGER.error("Server Error : ", e);
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAgriCommodity

	public ResponseMessage updateAgriCommodityById(int id, AgroCommodityDTO agroCommodityDTO) {
		AgriCommodity agriCommodity = null;
		try {
			Optional<AgriCommodity> foundCommodity = agriCommodityRepositoy.findById(id);

			if (foundCommodity.isPresent()) {

				ObjectMapper objectMapper = new ObjectMapper();
				agriCommodity = objectMapper.readValue(agroCommodityDTO.getData(), AgriCommodity.class);
			
	            
				if (agroCommodityDTO.getLogoFile() != null)
	            {
	                FileUploadResponseDTO manageFile = this.getManageFile(agroCommodityDTO.getLogoFile());
	                agriCommodity.setLogo(manageFile.getPublicUrl());
	            }else {
	            	agriCommodity.setLogo(foundCommodity.get().getLogo());
	            }
				agriCommodity.setId(id);
				agriCommodityRepositoy.save(agriCommodity);

				approvalUtil.addRecord(DBConstants.TBL_AGRI_COMMODITY, agriCommodity.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Commodity" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-Commodity" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (DataIntegrityViolationException e) {
			LOGGER.error("DataIntegrityViolationException : ", e);
			return responseMessageUtil.sendResponse(false, "", "Duplicate entry for commodity " + agriCommodity.getName());
		}catch (Exception e) {
			LOGGER.error("Server Error : ", e);
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateAgriCommodityById

	public ResponseMessage primaryApproveById(int id) {

		try {
			Optional<AgriCommodity> foundCommodity = agriCommodityRepositoy.findById(id);

			if (foundCommodity.isPresent()) {

				AgriCommodity commodity = foundCommodity.get();

				commodity.setStatus(APIConstants.STATUS_APPROVED);

				commodity = agriCommodityRepositoy.save(commodity);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_COMMODITY, commodity.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Commodity" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");

			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-Commodity" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {

		try {
			Optional<AgriCommodity> foundCommodity = agriCommodityRepositoy.findById(id);

			if (foundCommodity.isPresent()) {

				AgriCommodity commodity = foundCommodity.get();

				commodity.setStatus(APIConstants.STATUS_ACTIVE);

				commodity = agriCommodityRepositoy.save(commodity);

				approvalUtil.finalApprove(DBConstants.TBL_AGRI_COMMODITY, commodity.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Commodity" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");

			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-Commodity" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteAgriCommodityById(int id) {

		try {
			Optional<AgriCommodity> foundCommodity = agriCommodityRepositoy.findById(id);

			if (foundCommodity.isPresent()) {

				AgriCommodity commodity = foundCommodity.get();
				commodity.setStatus(APIConstants.STATUS_DELETED);

				commodity = agriCommodityRepositoy.save(commodity);

				approvalUtil.delete(DBConstants.TBL_AGRI_COMMODITY, commodity.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Commodity" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");

			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-Commodity" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// deleteAgriCommodityById

	public ResponseMessage rejectById(int id) {

		try {
			Optional<AgriCommodity> foundCommodity = agriCommodityRepositoy.findById(id);

			if (foundCommodity.isPresent()) {

				AgriCommodity commodity = foundCommodity.get();
				commodity.setStatus(APIConstants.STATUS_REJECTED);

				commodity = agriCommodityRepositoy.save(commodity);

				approvalUtil.delete(DBConstants.TBL_AGRI_COMMODITY, commodity.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Commodity" + APIConstants.RESPONSE_REJECT_SUCCESS + id, "");

			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-Commodity" + APIConstants.RESPONSE_REJECT_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// rejectById

	public AgriCommodity findAgriCommodityById(int id) {

		try {
			Optional<AgriCommodity> foundCommodity = agriCommodityRepositoy.findById(id);
			if (foundCommodity.isPresent()) {
				return foundCommodity.get();

			} else {
				throw new DoesNotExistException("Agri-Commodity" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findAgriCommodityById

	public List<Map<String, Object>> findCommodityBySeasonId(int seasonId) {

		try {
			List<Map<String, Object>> CommodityList = agriCommodityRepositoy.getCommodityBySeasonId(seasonId);

			return CommodityList;

		} catch (Exception e) {
			throw e;
		}
	}

	public List<Map<String, Object>> findCommodityByStateCodeAndSeasonId(int stateCode, int seasonId) {

		try {
			List<Map<String, Object>> CommodityList = agriCommodityRepositoy
					.getCommodityByStateCodeAndSeasonId(stateCode, seasonId);

			return CommodityList;

		} catch (Exception e) {
			throw e;
		}
	}

	public List<Map<String, Object>> getCommoditiesByStateCode(int stateCode) {
		try {

			List<Map<String, Object>> commodityList = agriCommodityRepositoy.getCommoditiesByStateCode(stateCode);

			if (!commodityList.isEmpty()) {
				return commodityList;
			} else {
				throw new DoesNotExistException(
						"Agri-Commodity :" + APIConstants.RESPONSE_NO_RECORD_FOUND + "getCommoditiesByStateCode");
			}

		} catch (Exception ex) {
			throw ex;
		}
	}

	public List<AgriFieldActivityInfDto> findPhenophaseByCommodity(int commodityId) {

		try {
			List<AgriFieldActivityInfDto> phenophaseList = agriCommodityRepositoy.getPhenophaseByCommodity(commodityId);

			return phenophaseList;

		} catch (Exception e) {
			throw e;
		}
	}

	public Map<String, Object> getCommodityAlias() {
		try {
			Map<String, Object> responseMap = new HashMap<>();

			responseMap.put("Commodity", agriCommodityRepositoy.getCommodities());
			responseMap.put("CommodityAlias", agriCommodityRepositoy.getCommodityAlias());

			return responseMap;

		} catch (Exception ex) {
			throw ex;
		}
	}

	public List<Object> getCommodityAliasWithPage(int page, int size) {
		try {

			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("Alias").ascending());
			List<Object> responseMap = new ArrayList<>();
			Map<String, List<AgriCommodityInfo>> objectMap = new HashMap<>();
			AgriCommodityAlias agriCommodityAlias = new AgriCommodityAlias();
			agriCommodityAlias
					.setAgriCommodityAliasDtoList(agriCommodityRepositoy.getCommodityAliasWithPage(sortedByIdDesc));
			agriCommodityAlias.setAgriCommodityInfos(agriCommodityRepositoy.getCommodityOfPage());
			objectMap.put("Commodity", agriCommodityAlias.getAgriCommodityInfos());

			responseMap.add(objectMap);
			responseMap.add(agriCommodityAlias.getAgriCommodityAliasDtoList());
			return responseMap;

		} catch (Exception ex) {
			throw ex;
		}
	}

	public ResponseMessage tagCommodityAlias(Map<String, Object> commodityAlias) {
		try {
			Integer commodityId = Integer.valueOf(commodityAlias.get("commodityId").toString());
			Integer aliasId = Integer.valueOf(commodityAlias.get("aliasId").toString());

			int updateCount = 0;
			if (aliasId <= 0) {
				String aliasName = commodityAlias.get("aliasName").toString();

				if (agriCommodityRepositoy.checkCommodityAlias(commodityId, aliasName) > 0) {

					return responseMessageUtil.sendResponse(false, "",
							"Agri-Commodity Alias" + APIConstants.RESPONSE_ALREADY_EXIST + aliasName);
				} else {
					updateCount = agriCommodityRepositoy.addCommodityAlias(commodityId, aliasName);
				}
			} else {
				updateCount = agriCommodityRepositoy.tagCommodityAlias(commodityId, aliasId);
			}

			return responseMessageUtil.sendResponse(true, "Agri-Commodity Alias" + APIConstants.RESPONSE_ADD_SUCCESS,
					"");

		} catch (Exception ex) {
			throw ex;
		}
	}
	
	private FileUploadResponseDTO getManageFile(MultipartFile file) throws IOException
    {
		LocalDateTime localDate = LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String formattedDate = localDate.format(myFormatObj);
		String afterFormat=  formattedDate.replace("-","");
		String formatDate = afterFormat.replace(":","");
		String currentDate = formatDate.replace(" ","");

        String image = Objects.requireNonNull(file.getOriginalFilename());
		String i = image.substring(0, image.indexOf(".")).replaceAll("[^a-zA-Z0-9]", " ").replace(" ", "");
		LOGGER.info(" i :: {}", i);
        FileUploadResponseDTO fileUploadResponse = null;
        String[] SUPPORTED_EXTN = {".jpg", ".png", ".jpeg", ".svg"};
        String fileExtension = Objects.requireNonNull(image);
		String currentImage = i.concat("-").concat(currentDate).concat(".").concat(image.substring(image.lastIndexOf(".")+1));
        LOGGER.info(" file :: {}", fileExtension);
        if (Arrays.stream(SUPPORTED_EXTN).anyMatch(fileExtension.toLowerCase()::contains))
        {
            String pathKey = "agri-commodity";
            Map<String, Object> pathKeyResponse = fileManagerUtil.isPathKeyExist(pathKey);
            if (pathKeyResponse != null)
            {
                boolean result = (boolean) pathKeyResponse.get("exist");
                LOGGER.info("result :: {}",  result);
                if (result)
                {
                    LOGGER.info("inside if condition result is true ::  ");
                    fileUploadResponse = this.fileManagerUtil.uploadFile(pathKey, false,
							currentImage, true, file, null);
                } else
                {
                    String moduleName = "master-data";
                    String dirPath = pathKey;
                    LOGGER.info("inside else condition result is false ::  ");
                    DirectoryResponseDTO createDirResp = this.fileManagerUtil.createDirectory(moduleName,
                            pathKey, dirPath, null);
                    if (createDirResp != null && createDirResp.isSuccess())
                    {
                        fileUploadResponse = this.fileManagerUtil.uploadFile(pathKey, false,
								currentImage, true, file, null);
                        LOGGER.info("Create directory");
                    }
                }
            }
        } else {
            throw new NoSuchFileException("File Format Not supported!");
        }

        return fileUploadResponse;
    }

}// AgriCommodityService
