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
import in.cropdata.cdtmasterdata.website.dto.ProductsAndServicesDTO;
import in.cropdata.cdtmasterdata.website.model.ProductsAndServices;
import in.cropdata.cdtmasterdata.website.model.vo.ProductsAndServicesVO;
import in.cropdata.cdtmasterdata.website.repository.ProductsAndServicesRepository;
import in.cropdata.toolsuite.sdk.FileManagerUtil;
import in.cropdata.toolsuite.sdk.dto.DirectoryResponseDTO;
import in.cropdata.toolsuite.sdk.dto.FileUploadResponseDTO;

@Service
public class ProductsAndServicesService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductsAndServicesService.class);

	@Autowired
	private ProductsAndServicesRepository productsAndServicesRepository;

	private static final String SERVER_ERROR_MSG = "Server Error : ";
	
	@Autowired
	ResponseMessageUtil responseMessageUtil;
	
	@Autowired
    private FileManagerUtil fileManagerUtil;

	public List<ProductsAndServicesVO> getProductsAndServicesList() {
		try {
			LOGGER.info("getting all Products And Services info...");
			return productsAndServicesRepository.getProductsAndServicesList();
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Products And Services Data Found!");
		}
	}
	
	public Page<ProductsAndServicesVO> getProductsAndServicesListByPagenation(Integer page, Integer size, String searchText){
		try {
			LOGGER.info("getting all Products And Services from service....");
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			return productsAndServicesRepository.getProductsAndServicesListByPagenation(sortedByIdDesc, searchText);
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Products And Services Data Found For Searched Text -> " + searchText);
		}
	}
	
	public ResponseMessage addProductsAndServices(ProductsAndServicesDTO productsAndServicesDTO) {
		try {
			LOGGER.info("Add Products And Services From Service...");
//			position.setStatus(APIConstants.STATUS_INACTIVE);
			
			ObjectMapper objectMapper = new ObjectMapper();
			ProductsAndServices productsAndServices = objectMapper.readValue(productsAndServicesDTO.getData(), ProductsAndServices.class);
            if (productsAndServicesDTO.getLogoFile() != null)
            {
                FileUploadResponseDTO manageFile = this.getManageFile(productsAndServicesDTO.getLogoFile());
                productsAndServices.setLogo(manageFile.getPublicUrl());
            }
            productsAndServicesRepository.save(productsAndServices);
			return responseMessageUtil.sendResponse(true, "Products And Services" + APIConstants.RESPONSE_ADD_SUCCESS, "");
		}catch (InvalidDataException e) {
			return responseMessageUtil.sendResponse(false, "",e.getMessage());
		}catch (AlreadyExistException e) {
			return responseMessageUtil.sendResponse(false, "",e.getMessage());
		} catch (Exception e) {
			LOGGER.error(SERVER_ERROR_MSG, e);
			return responseMessageUtil.sendResponse(false, "", "Server Error: " + e.getMessage());
		}
	}
	
	public ProductsAndServices getProductsAndServicesById(Integer id) {

		try {

			LOGGER.info("getting Products And Services by Id...");
			return productsAndServicesRepository.findById(id).orElse(null);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("Products And Services Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
		}

	}

	public ResponseMessage updateProductsAndServices(Integer id, ProductsAndServicesDTO productsAndServicesDTO) {
		try {
			
			Optional<ProductsAndServices> optionalProductsAndServices = productsAndServicesRepository.findById(id);

			if (optionalProductsAndServices.isPresent()) {
				
				ObjectMapper objectMapper = new ObjectMapper();
				ProductsAndServices productsAndServices = objectMapper.readValue(productsAndServicesDTO.getData(), ProductsAndServices.class);
			
	            
				if (productsAndServicesDTO.getLogoFile() != null)
	            {
	                FileUploadResponseDTO manageFile = this.getManageFile(productsAndServicesDTO.getLogoFile());
	                productsAndServices.setLogo(manageFile.getPublicUrl());
	            }else {
	            	productsAndServices.setLogo(optionalProductsAndServices.get().getLogo());
	            }
				productsAndServices.setId(id);
				productsAndServicesRepository.save(productsAndServices);
				
				return responseMessageUtil.sendResponse(true, "Products And Services" + APIConstants.RESPONSE_UPDATE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Products And Services Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		}catch (InvalidDataException e) {
			return responseMessageUtil.sendResponse(false, "",e.getMessage());
		}catch (AlreadyExistException e) {
			return responseMessageUtil.sendResponse(false, "",e.getMessage());
		} catch (Exception ex) {
			return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + ex.getMessage());
		}
	}

	
	public ResponseMessage activeProductsAndServices(int id) {
		try {
			Optional<ProductsAndServices> optionalProductsAndServices = productsAndServicesRepository.findById(id);
			if (optionalProductsAndServices.isPresent()) {

				productsAndServicesRepository.activeProductsAndServices(id);
				return responseMessageUtil.sendResponse(true, "Products And Services" + APIConstants.RESPONSE_ACTIVE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Products And Services" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
	
	public ResponseMessage deactiveProductsAndServices(int id) {
		try {
			Optional<ProductsAndServices> optionalProductsAndServices = productsAndServicesRepository.findById(id);
			if (optionalProductsAndServices.isPresent()) {

				productsAndServicesRepository.deactiveProductsAndServices(id);
				return responseMessageUtil.sendResponse(true, "Products And Services" + APIConstants.RESPONSE_DEACTIVE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Products And Services" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
	
	public ResponseMessage deleteProductsAndServices(int id) {
		try {
			Optional<ProductsAndServices> optionalProductsAndServices = productsAndServicesRepository.findById(id);
			if (optionalProductsAndServices.isPresent()) {

				productsAndServicesRepository.deleteProductsAndServices(id);
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
	            String pathKey = "products-and-services";
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
