package in.cropdata.cdtmasterdata.service;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.constants.DBConstants;
import in.cropdata.cdtmasterdata.dto.AgriFieldActivityDTO;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriFieldActivityInfDto;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.AgriCommodity;
import in.cropdata.cdtmasterdata.model.AgriFieldActivity;
import in.cropdata.cdtmasterdata.model.AgriFieldActivityMissing;
import in.cropdata.cdtmasterdata.repository.AgriFieldActivityMissingRepository;
import in.cropdata.cdtmasterdata.repository.AgriFieldActivityRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;
import in.cropdata.toolsuite.sdk.FileManagerUtil;
import in.cropdata.toolsuite.sdk.dto.DirectoryResponseDTO;
import in.cropdata.toolsuite.sdk.dto.FileUploadResponseDTO;

@Service
public class AgriFieldActivityService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AgriFieldActivityService.class);
	
	@Autowired
	private AgriFieldActivityRepository agriFieldActivityRepository;
	
	@Autowired
	private AgriFieldActivityMissingRepository agriFieldActivityMissingRepository;
	
	@Autowired
    private FileManagerUtil fileManagerUtil;

//	@Autowired
//	private GeoRegionRepository geoRegionRepository;
//
//	@Autowired
//	private AgriCommodityRepositoy commodityRepositoy;
//
//	@Autowired
//	private AgriSeasonRepository agriSeasonRepository;
//
//	@Autowired
//	private AgriPhenophaseRepository agriPhenophaseRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<AgriFieldActivityInfDto> getAllAgriFieldActivity() {
		try {
			List<AgriFieldActivityInfDto> list = agriFieldActivityRepository.getAgriFieldActivityList();

			return list;
		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriFieldActivity
	
	public Page<AgriFieldActivityInfDto> getAllAgriFieldActivityPaginated(int page, int size, String  searchText, int isValid,String missing) {

		try {
			searchText = "%"+searchText+"%";
			
//		System.out.println("searchText--> " + searchText);

			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());

			Page<AgriFieldActivityInfDto> fieldActivityList;

			if("0".equals(missing)) {
			if (isValid == 0) {
				fieldActivityList = agriFieldActivityRepository.getAgriFieldActivityInvalidated(sortedByIdDesc,searchText);
			} else {
				fieldActivityList = agriFieldActivityRepository.getAgriFieldActivity(sortedByIdDesc,searchText);
			}
			}else {
				if (isValid == 0) {
					fieldActivityList = agriFieldActivityRepository.getAgriFieldActivityMissingInvalidated(sortedByIdDesc,searchText);
				} else {
					fieldActivityList = agriFieldActivityRepository.getAgriFieldActivityMissing(sortedByIdDesc,searchText);
				}
			}

			return fieldActivityList;
		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriFieldActivityPaginated

	public ResponseMessage addAgriFieldActivity(AgriFieldActivityDTO agriFieldActivityDTO) {
		AgriFieldActivity agriFieldActivity = null;
		try {
			
			ObjectMapper objectMapper = new ObjectMapper();
			agriFieldActivity = objectMapper.readValue(agriFieldActivityDTO.getData(), AgriFieldActivity.class);
			System.out.println("agriFieldActivityDTO.getImageFile() : " + agriFieldActivityDTO.getImageFile());
            if (agriFieldActivityDTO.getImageFile() != null)
            {
                FileUploadResponseDTO imageFile = this.getImageFile(agriFieldActivityDTO.getImageFile());
                agriFieldActivity.setImageUrl(imageFile.getPublicUrl());
            }
            
			agriFieldActivity = agriFieldActivityRepository.save(agriFieldActivity);

			approvalUtil.addRecord(DBConstants.TBL_AGRI_FIELD_ACTIVITY, agriFieldActivity.getId());

			return responseMessageUtil.sendResponse(true, "Agri-FieldActivity" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception e) {
			LOGGER.error("Server Error : ", e);
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAgriFieldActivity

	public ResponseMessage updateAgriFieldActivityById(int id, AgriFieldActivityDTO agriFieldActivityDTO) {
		AgriFieldActivity agriFieldActivity = null;
		try {
			Optional<AgriFieldActivity> foundVareity = agriFieldActivityRepository.findById(id);
			if (foundVareity.isPresent()) {
				ObjectMapper objectMapper = new ObjectMapper();
				agriFieldActivity = objectMapper.readValue(agriFieldActivityDTO.getData(), AgriFieldActivity.class);
				System.out.println("agriFieldActivityDTO.getImageFile() : " + agriFieldActivityDTO.getImageFile());
	            if (agriFieldActivityDTO.getImageFile() != null)
	            {
	                FileUploadResponseDTO imageFile = this.getImageFile(agriFieldActivityDTO.getImageFile());
	                agriFieldActivity.setImageUrl(imageFile.getPublicUrl());
	            }
				agriFieldActivity.setId(id);
				agriFieldActivityRepository.save(agriFieldActivity);

				approvalUtil.updateRecord(DBConstants.TBL_AGRI_FIELD_ACTIVITY, agriFieldActivity.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-FieldActivity" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-FieldActivity" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateAgriFieldActivityById

	public ResponseMessage primaryApproveById(int id) {

		try {
			Optional<AgriFieldActivity> foundFieldActivity = agriFieldActivityRepository.findById(id);

			if (foundFieldActivity.isPresent()) {

				AgriFieldActivity fieldActivity = foundFieldActivity.get();
				fieldActivity.setStatus(APIConstants.STATUS_APPROVED);
				fieldActivity = agriFieldActivityRepository.save(fieldActivity);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_FIELD_ACTIVITY, fieldActivity.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-FieldActivity" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-FieldActivity" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<AgriFieldActivity> foundFieldActivity = agriFieldActivityRepository.findById(id);
			if (foundFieldActivity.isPresent()) {

				AgriFieldActivity fieldActivity = foundFieldActivity.get();
				fieldActivity.setStatus(APIConstants.STATUS_ACTIVE);

				fieldActivity = agriFieldActivityRepository.save(fieldActivity);

				approvalUtil.finalApprove(DBConstants.TBL_AGRI_FIELD_ACTIVITY, fieldActivity.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-FieldActivity" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-FieldActivity" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteAgriFieldActivityById(int id) {
		try {
			Optional<AgriFieldActivity> foundFieldActivity = agriFieldActivityRepository.findById(id);
			if (foundFieldActivity.isPresent()) {

				AgriFieldActivity fieldActivity = foundFieldActivity.get();
				fieldActivity.setStatus(APIConstants.STATUS_DELETED);

				fieldActivity = agriFieldActivityRepository.save(fieldActivity);

				approvalUtil.delete(DBConstants.TBL_AGRI_FIELD_ACTIVITY, fieldActivity.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-FieldActivity" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-FieldActivity" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// deleteAgriFieldActivityById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<AgriFieldActivity> foundFieldActivity = agriFieldActivityRepository.findById(id);
			if (foundFieldActivity.isPresent()) {

				AgriFieldActivity fieldActivity = foundFieldActivity.get();
				fieldActivity.setStatus(APIConstants.STATUS_REJECTED);

				fieldActivity = agriFieldActivityRepository.save(fieldActivity);

				approvalUtil.delete(DBConstants.TBL_AGRI_FIELD_ACTIVITY, fieldActivity.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-FieldActivity" + APIConstants.RESPONSE_REJECT_SUCCESS + id, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-FieldActivity" + APIConstants.RESPONSE_REJECT_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// rejectById

	public AgriFieldActivity findAgriFieldActivityById(int id) {

		try {
			Optional<AgriFieldActivity> foundFieldActivity = agriFieldActivityRepository.findById(id);

			if (foundFieldActivity.isPresent()) {
				return foundFieldActivity.get();

			} else {
				throw new DoesNotExistException("Agri-FieldActivity" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findAgriFieldActivityById
	
	@Transactional
	public ResponseMessage moveToMaster(int id) {
		try {
			Optional<AgriFieldActivityMissing> foundAgriFieldActivityMissing = agriFieldActivityMissingRepository.findById(id);

			if (foundAgriFieldActivityMissing.isPresent()) {
				AgriFieldActivity agriFieldActivity = new AgriFieldActivity();
				AgriFieldActivityMissing regionalCommodityMissing = foundAgriFieldActivityMissing.get();
				
				agriFieldActivity.setSeasonId(regionalCommodityMissing.getSeasonId());
				agriFieldActivity.setCommodityId(regionalCommodityMissing.getCommodityId());
				agriFieldActivity.setPhenophaseId(regionalCommodityMissing.getPhenophaseId());
				agriFieldActivity.setName(regionalCommodityMissing.getName());
				agriFieldActivity.setDescription(regionalCommodityMissing.getDescription());
				agriFieldActivity.setStatus(regionalCommodityMissing.getStatus());

				AgriFieldActivity savedAgriFieldActivity = agriFieldActivityRepository.save(agriFieldActivity);
				
				agriFieldActivityMissingRepository.deleteById(id);


				approvalUtil.addRecord(DBConstants.TBL_AGRI_FIELD_ACTIVITY, savedAgriFieldActivity.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-FieldActivity-Missing" + APIConstants.RESPONSE_MOVE_TO_MASTER_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-FieldActivity-Missing" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}
	
	private FileUploadResponseDTO getImageFile(MultipartFile file) throws IOException
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
            String pathKey = "agri_field_activity";
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
	
}// AgriFieldActivityService
