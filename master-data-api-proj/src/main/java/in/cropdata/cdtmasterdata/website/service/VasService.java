package in.cropdata.cdtmasterdata.website.service;

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
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.AlreadyExistException;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.exceptions.InvalidDataException;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;
import in.cropdata.cdtmasterdata.website.dto.VasDTO;
import in.cropdata.cdtmasterdata.website.model.Vas;
import in.cropdata.cdtmasterdata.website.model.vo.VasVO;
import in.cropdata.cdtmasterdata.website.repository.VasRepository;
import in.cropdata.toolsuite.sdk.FileManagerUtil;
import in.cropdata.toolsuite.sdk.dto.DirectoryResponseDTO;
import in.cropdata.toolsuite.sdk.dto.FileUploadResponseDTO;

@Service
public class VasService {

	private static final Logger LOGGER = LoggerFactory.getLogger(VasService.class);

	@Autowired
	private VasRepository vasRepository;

	private static final String SERVER_ERROR_MSG = "Server Error : ";
	
	@Autowired
	ResponseMessageUtil responseMessageUtil;
	
	@Autowired
    private FileManagerUtil fileManagerUtil;

	public List<VasVO> getVasList() {
		try {
			LOGGER.info("getting all vas info...");
			return vasRepository.getVasList();
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No VAS Data Found!");
		}
	}
	
	public Page<VasVO> getVasListByPagenation(Integer page, Integer size, String searchText){
		try {
			LOGGER.info("getting all vas from service....");
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			return vasRepository.getVasListByPagenation(sortedByIdDesc, searchText);
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No VAS Data Found For Searched Text -> " + searchText);
		}
	}
	
	public ResponseMessage addVas(VasDTO vasDTO) {
		try {
			LOGGER.info("Add Vas From Service...");
//			position.setStatus(APIConstants.STATUS_INACTIVE);
			
			ObjectMapper objectMapper = new ObjectMapper();
			Vas vas = objectMapper.readValue(vasDTO.getData(), Vas.class);
            if (vasDTO.getLogoFile() != null)
            {
                FileUploadResponseDTO manageFile = this.getManageFile(vasDTO.getLogoFile());
                vas.setLogo(manageFile.getPublicUrl());
            }
            vasRepository.save(vas);
			return responseMessageUtil.sendResponse(true, "VAS" + APIConstants.RESPONSE_ADD_SUCCESS, "");
		}catch (InvalidDataException e) {
			return responseMessageUtil.sendResponse(false, "",e.getMessage());
		}catch (AlreadyExistException e) {
			return responseMessageUtil.sendResponse(false, "",e.getMessage());
		} catch (Exception e) {
			LOGGER.error(SERVER_ERROR_MSG, e);
			return responseMessageUtil.sendResponse(false, "", "Server Error: " + e.getMessage());
		}
	}
	
	public Vas getVasById(Integer id) {

		try {

			LOGGER.info("getting vas by Id...");
			return vasRepository.findById(id).orElse(null);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("VAS Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
		}

	}

	public ResponseMessage updateVas(Integer id, VasDTO vasDTO) {
		try {
			
			Optional<Vas> optionalVas = vasRepository.findById(id);

			if (optionalVas.isPresent()) {
				
				ObjectMapper objectMapper = new ObjectMapper();
				Vas vas = objectMapper.readValue(vasDTO.getData(), Vas.class);
	            
				if (vasDTO.getLogoFile() != null)
	            {
	                FileUploadResponseDTO manageFile = this.getManageFile(vasDTO.getLogoFile());
	                vas.setLogo(manageFile.getPublicUrl());
	            }else {
	            	vas.setLogo(optionalVas.get().getLogo());
	            }
				vas.setId(id);
				vasRepository.save(vas);
				
				return responseMessageUtil.sendResponse(true, "VAS" + APIConstants.RESPONSE_UPDATE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"VAS Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		}catch (InvalidDataException e) {
			return responseMessageUtil.sendResponse(false, "",e.getMessage());
		}catch (AlreadyExistException e) {
			return responseMessageUtil.sendResponse(false, "",e.getMessage());
		} catch (Exception ex) {
			return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + ex.getMessage());
		}
	}

	
	public ResponseMessage activeVas(int id) {
		try {
			Optional<Vas> optionalVas = vasRepository.findById(id);
			if (optionalVas.isPresent()) {

				vasRepository.activeVas(id);
				return responseMessageUtil.sendResponse(true, "VAS" + APIConstants.RESPONSE_ACTIVE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"VAS" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
	
	public ResponseMessage deactiveVas(int id) {
		try {
			Optional<Vas> optionalVas = vasRepository.findById(id);
			if (optionalVas.isPresent()) {

				vasRepository.deactiveVas(id);
				return responseMessageUtil.sendResponse(true, "VAS" + APIConstants.RESPONSE_DEACTIVE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"VAS" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
	
	public ResponseMessage deleteVas(int id) {
		try {
			Optional<Vas> optionalVas = vasRepository.findById(id);
			if (optionalVas.isPresent()) {

				vasRepository.deleteVas(id);
				return responseMessageUtil.sendResponse(true, "VAS" + APIConstants.RESPONSE_DELETE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"VAS" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
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
	            String pathKey = "vas";
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
