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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.dto.PlatformMasterDTO;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.AlreadyExistException;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.exceptions.InvalidDataException;
import in.cropdata.cdtmasterdata.model.PlatFormMaster;
import in.cropdata.cdtmasterdata.model.vo.PlatformMasterVO;
import in.cropdata.cdtmasterdata.repository.PlatFormRepository;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;
import in.cropdata.toolsuite.sdk.FileManagerUtil;
import in.cropdata.toolsuite.sdk.dto.DirectoryResponseDTO;
import in.cropdata.toolsuite.sdk.dto.FileUploadResponseDTO;

@Service
public class PlatformMasterService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PlatformMasterService.class);

	@Autowired
	private PlatFormRepository platformMasterRepository;

	private static final String SERVER_ERROR_MSG = "Server Error : ";
	
	@Autowired
	ResponseMessageUtil responseMessageUtil;
	
	@Autowired
    private FileManagerUtil fileManagerUtil;

	public List<PlatformMasterVO> getPlatformMasterList() {
		try {
			LOGGER.info("getting all Platform info...");
			return platformMasterRepository.getPlatformMasterList();
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Platform Data Found!");
		}
	}
	
	public Page<PlatformMasterVO> getPlatformMasterListByPagenation(Integer page, Integer size, String searchText){
		try {
			LOGGER.info("getting all Platform from service....");
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			return platformMasterRepository.getPlatformMasterListByPagenation(sortedByIdDesc, searchText);
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Platform Data Found For Searched Text -> " + searchText);
		}
	}
	
	public ResponseMessage addPlatformMaster(PlatformMasterDTO platformMasterDTO) {
		try {
			LOGGER.info("Add Platform From Service...");
//			position.setStatus(APIConstants.STATUS_INACTIVE);
			
			ObjectMapper objectMapper = new ObjectMapper();
			PlatFormMaster platformMaster = objectMapper.readValue(platformMasterDTO.getData(), PlatFormMaster.class);
            if (platformMasterDTO.getLogoFile() != null)
            {
                FileUploadResponseDTO manageFile = this.getManageFile(platformMasterDTO.getLogoFile());
                platformMaster.setLogo(manageFile.getPublicUrl());
            }
            platformMasterRepository.save(platformMaster);
			return responseMessageUtil.sendResponse(true, "Platform" + APIConstants.RESPONSE_ADD_SUCCESS, "");
		}catch (InvalidDataException e) {
			return responseMessageUtil.sendResponse(false, "",e.getMessage());
		}catch (AlreadyExistException e) {
			return responseMessageUtil.sendResponse(false, "",e.getMessage());
		} catch (Exception e) {
			LOGGER.error(SERVER_ERROR_MSG, e);
			return responseMessageUtil.sendResponse(false, "", "Server Error: " + e.getMessage());
		}
	}
	
	public PlatFormMaster getPlatformMasterById(Integer id) {

		try {

			LOGGER.info("getting Platform by Id...");
			return platformMasterRepository.findById(id).orElse(null);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("Platform Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
		}

	}

	public ResponseMessage updatePlatformMaster(Integer id, PlatformMasterDTO platformMasterDTO) {
		try {
			
			Optional<PlatFormMaster> optionalPlatformMaster = platformMasterRepository.findById(id);

			if (optionalPlatformMaster.isPresent()) {
				
				ObjectMapper objectMapper = new ObjectMapper();
				PlatFormMaster platformMaster = objectMapper.readValue(platformMasterDTO.getData(), PlatFormMaster.class);
	            
				if (platformMasterDTO.getLogoFile() != null)
	            {
	                FileUploadResponseDTO manageFile = this.getManageFile(platformMasterDTO.getLogoFile());
	                platformMaster.setLogo(manageFile.getPublicUrl());
	            }else {
	            	platformMaster.setLogo(optionalPlatformMaster.get().getLogo());
	            }
				platformMaster.setId(id);
				platformMasterRepository.save(platformMaster);
				
				return responseMessageUtil.sendResponse(true, "Platform" + APIConstants.RESPONSE_UPDATE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Platform Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		}catch (InvalidDataException e) {
			return responseMessageUtil.sendResponse(false, "",e.getMessage());
		}catch (AlreadyExistException e) {
			return responseMessageUtil.sendResponse(false, "",e.getMessage());
		} catch (Exception ex) {
			return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + ex.getMessage());
		}
	}

	
	public ResponseMessage activePlatformMaster(int id) {
		try {
			Optional<PlatFormMaster> optionalPlatformMaster = platformMasterRepository.findById(id);
			if (optionalPlatformMaster.isPresent()) {

				platformMasterRepository.activePlatformMaster(id);
				return responseMessageUtil.sendResponse(true, "Platform" + APIConstants.RESPONSE_ACTIVE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Platform" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
	
	public ResponseMessage deactivePlatformMaster(int id) {
		try {
			Optional<PlatFormMaster> optionalPlatformMaster = platformMasterRepository.findById(id);
			if (optionalPlatformMaster.isPresent()) {

				platformMasterRepository.deactivePlatformMaster(id);
				return responseMessageUtil.sendResponse(true, "Platform" + APIConstants.RESPONSE_DEACTIVE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Platform" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
	
	public ResponseMessage deletePlatformMaster(int id) {
		try {
			Optional<PlatFormMaster> optionalPlatformMaster = platformMasterRepository.findById(id);
			if (optionalPlatformMaster.isPresent()) {

				platformMasterRepository.deletePlatformMaster(id);
				return responseMessageUtil.sendResponse(true, "Platform" + APIConstants.RESPONSE_DELETE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Tool" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
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
	            String pathKey = "platform";
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
	                    String moduleName = "cropdata-portal";
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
}
