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
import in.cropdata.cdtmasterdata.website.dto.ToolsDTO;
import in.cropdata.cdtmasterdata.website.model.Tools;
import in.cropdata.cdtmasterdata.website.model.vo.ToolsVO;
import in.cropdata.cdtmasterdata.website.repository.ToolsRepository;
import in.cropdata.toolsuite.sdk.FileManagerUtil;
import in.cropdata.toolsuite.sdk.dto.DirectoryResponseDTO;
import in.cropdata.toolsuite.sdk.dto.FileUploadResponseDTO;

@Service
public class ToolsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ToolsService.class);

	@Autowired
	private ToolsRepository toolsRepository;

	private static final String SERVER_ERROR_MSG = "Server Error : ";
	
	@Autowired
	ResponseMessageUtil responseMessageUtil;
	
	@Autowired
    private FileManagerUtil fileManagerUtil;

	public List<ToolsVO> getToolsList() {
		try {
			LOGGER.info("getting all tools info...");
			return toolsRepository.getToolsList();
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Tools Data Found!");
		}
	}
	
	public Page<ToolsVO> getToolsListByPagenation(Integer page, Integer size, String searchText){
		try {
			LOGGER.info("getting all tools from service....");
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			return toolsRepository.getToolsListByPagenation(sortedByIdDesc, searchText);
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Tools Data Found For Searched Text -> " + searchText);
		}
	}
	
	public ResponseMessage addTools(ToolsDTO toolsDTO) {
		try {
			LOGGER.info("Add Tool From Service...");
//			position.setStatus(APIConstants.STATUS_INACTIVE);
			
			ObjectMapper objectMapper = new ObjectMapper();
            Tools tool = objectMapper.readValue(toolsDTO.getData(), Tools.class);
            if (toolsDTO.getLogoFile() != null)
            {
                FileUploadResponseDTO manageFile = this.getManageFile(toolsDTO.getLogoFile());
                tool.setLogo(manageFile.getPublicUrl());
            }
            toolsRepository.save(tool);
			return responseMessageUtil.sendResponse(true, "Tool" + APIConstants.RESPONSE_ADD_SUCCESS, "");
		}catch (InvalidDataException e) {
			return responseMessageUtil.sendResponse(false, "",e.getMessage());
		}catch (AlreadyExistException e) {
			return responseMessageUtil.sendResponse(false, "",e.getMessage());
		} catch (Exception e) {
			LOGGER.error(SERVER_ERROR_MSG, e);
			return responseMessageUtil.sendResponse(false, "", "Server Error: " + e.getMessage());
		}
	}
	
	public Tools getToolsById(Integer id) {

		try {

			LOGGER.info("getting Tool by Id...");
			return toolsRepository.findById(id).orElse(null);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("Tool Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
		}

	}

	public ResponseMessage updateTools(Integer id, ToolsDTO toolsDTO) {
		try {
			
			Optional<Tools> optionalTools = toolsRepository.findById(id);

			if (optionalTools.isPresent()) {
				
				ObjectMapper objectMapper = new ObjectMapper();
	            Tools tool = objectMapper.readValue(toolsDTO.getData(), Tools.class);
				
	            System.out.println("toolsDTO.getLogoFile() : " + toolsDTO.getLogoFile());
	            
				if (toolsDTO.getLogoFile() != null)
	            {
	                FileUploadResponseDTO manageFile = this.getManageFile(toolsDTO.getLogoFile());
	                tool.setLogo(manageFile.getPublicUrl());
	            }else {
	            	tool.setLogo(optionalTools.get().getLogo());
	            }
				tool.setId(id);
				toolsRepository.save(tool);
				
				return responseMessageUtil.sendResponse(true, "Tool" + APIConstants.RESPONSE_UPDATE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Tool Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		}catch (InvalidDataException e) {
			return responseMessageUtil.sendResponse(false, "",e.getMessage());
		}catch (AlreadyExistException e) {
			return responseMessageUtil.sendResponse(false, "",e.getMessage());
		} catch (Exception ex) {
			return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + ex.getMessage());
		}
	}

	
	public ResponseMessage activeTools(int id) {
		try {
			Optional<Tools> optionalTools = toolsRepository.findById(id);
			if (optionalTools.isPresent()) {

				toolsRepository.activeTools(id);
				return responseMessageUtil.sendResponse(true, "Tool" + APIConstants.RESPONSE_ACTIVE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Tool" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
	
	public ResponseMessage deactiveTools(int id) {
		try {
			Optional<Tools> optionalTools = toolsRepository.findById(id);
			if (optionalTools.isPresent()) {

				toolsRepository.deactiveTools(id);
				return responseMessageUtil.sendResponse(true, "Tool" + APIConstants.RESPONSE_DEACTIVE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Tool" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
	
	public ResponseMessage deleteTools(int id) {
		try {
			Optional<Tools> optionalTools = toolsRepository.findById(id);
			if (optionalTools.isPresent()) {

				toolsRepository.deleteTools(id);
				return responseMessageUtil.sendResponse(true, "Tool" + APIConstants.RESPONSE_DELETE_SUCCESS + id,
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
	            String pathKey = "crop-tools";
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
