package in.cropdata.cdtmasterdata.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriCommodityPlantPartInfDto;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.AgriCommodityPlantPart;
import in.cropdata.cdtmasterdata.model.AgriCommodityPlantPartMissing;
import in.cropdata.cdtmasterdata.model.AgriPhenophase;
import in.cropdata.cdtmasterdata.model.vo.AgriPlantPartVO;
import in.cropdata.cdtmasterdata.repository.AgriCommodityPlantPartMissingRepository;
import in.cropdata.cdtmasterdata.repository.AgriCommodityPlantPartRepository;
import in.cropdata.cdtmasterdata.repository.AgriPhenophaseRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;
import in.cropdata.toolsuite.sdk.FileManagerUtil;
import in.cropdata.toolsuite.sdk.dto.DirectoryResponseDTO;
import in.cropdata.toolsuite.sdk.dto.FileUploadResponseDTO;

@Service
public class AgriCommodityPlantPartService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AgriCommodityPlantPartService.class);

	@Autowired
	private AgriCommodityPlantPartRepository agriCommodityPlantPartRepository;
	
	@Autowired
	private AgriCommodityPlantPartMissingRepository agriCommodityPlantPartMissingRepository;
	
	@Autowired
	private AgriPhenophaseRepository agriPhenophaseRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	@Autowired
	private FileManagerUtil fileManagerUtil;

	public List<AgriCommodityPlantPartInfDto> getAllAgriCommodityPlantPart() {

		try {
			List<AgriCommodityPlantPartInfDto> list = agriCommodityPlantPartRepository.getCommodityPlantPartList();

			return list;
		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriPlantPart

	public Page<AgriCommodityPlantPartInfDto> getAllCommodityPlantPartPaginated(int page, int size, String searchText, int isValid,String missing) {

		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());

			Page<AgriCommodityPlantPartInfDto> commodityPartList;

			if("0".equals(missing)) {
				System.err.println("Inside if..");
			if (isValid == 0) {
				commodityPartList = agriCommodityPlantPartRepository.getCommodityPlantPartListInvalidated(sortedByIdDesc, searchText);
			} else {
				commodityPartList = agriCommodityPlantPartRepository.getCommodityPlantPartList(sortedByIdDesc, searchText);
			}
			}else {
				System.err.println("Inside else..");
				if (isValid == 0) {
					commodityPartList = agriCommodityPlantPartRepository.getCommodityPlantPartMissingListInvalidated(sortedByIdDesc, searchText);
				} else {
					commodityPartList = agriCommodityPlantPartRepository.getCommodityPlantPartMissingList(sortedByIdDesc, searchText);
				}
			}

			return commodityPartList;

		} catch (Exception e) {
			throw e;
		}
	}

	public ResponseMessage addAgriCommodityPlantPart(AgriPlantPartVO plantPartVo) {
		AgriCommodityPlantPart plantPart = null;
		try {

			ObjectMapper mapper = new ObjectMapper();
			
			try {
				plantPart = mapper.readValue(plantPartVo.getData(), AgriCommodityPlantPart.class);

				plantPart = agriCommodityPlantPartRepository.save(plantPart);

				// -------------------image save-----------------------
				MultipartFile image = plantPartVo.getImage();
				System.err.println("IMAGE: " + image);
				System.err.println("Body: " + plantPartVo.getData());
				FileUploadResponseDTO uploadResponse = null;
				if (image != null) {
					String pathKey = "CommodityPlantPartImages";
					Map<String, Object> pathKeyResponse = fileManagerUtil.isPathKeyExist(pathKey);
					System.out.println("PATHKEY; " + pathKeyResponse.entrySet());
					if (pathKeyResponse != null ) {
						boolean result = (boolean) pathKeyResponse.get("exist");
						if (result) {
							uploadResponse = uploadFile(pathKey, image, plantPart.getId() + ".jpeg");
						} else {
							String moduleName = "master-data";
							String dirPath = pathKey;
							DirectoryResponseDTO createDirResp = fileManagerUtil.createDirectory(moduleName, pathKey,
									dirPath, null);
							System.out.println("CreateDirResp: " + createDirResp);
							if (createDirResp != null && createDirResp.isSuccess()) {
								uploadResponse = uploadFile(pathKey, image, plantPart.getId() + ".jpeg");
							}
						}
					}
					if (uploadResponse != null) {
						plantPart.setImageId(uploadResponse.getId());
						plantPart.setImageURL(uploadResponse.getPublicUrl());
						plantPart = agriCommodityPlantPartRepository.save(plantPart);

					}
				} // image not null

				// -------------------image save-----------------------

				approvalUtil.addRecord(DBConstants.TBL_AGRI_COMMODITY_PLANT_PART, plantPart.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Commodity-PlantPart" + APIConstants.RESPONSE_ADD_SUCCESS, "");
			} catch (IOException e) {
				e.printStackTrace();
				return responseMessageUtil.sendResponse(false, "",
						"Server Error : JSON Parse Error -> Failed to Parse the Data");
			}

		}catch (DataIntegrityViolationException e) {
			LOGGER.error("DataIntegrityViolationException : ", e);
			return responseMessageUtil.sendResponse(false, "", "Duplicate entry for commodity plant part");
		} catch (Exception e) {
			e.printStackTrace();
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAllAgriPlantPart

	private void resizeImage(MultipartFile image) {
		try {
			File file = image.getResource().getFile();
			FileInputStream fileInputStream = new FileInputStream(file);
			BufferedImage img = ImageIO.read(fileInputStream); // load image
			BufferedImage scaledImg = Scalr.resize(img, 320);
			ImageIO.write(scaledImg, "jpg", file);
			fileInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// resizeImage

	private FileUploadResponseDTO uploadFile(String pathKey, MultipartFile image, String fileName) {
//		resizeImage(image);
		try {
			FileUploadResponseDTO fileUploadResp = fileManagerUtil.uploadFile(pathKey, false, fileName, true, image,
					null);
			System.out.println("FileUploadResp: " + fileUploadResp);
			return fileUploadResp;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}// uploadFile(String pathKey, MultipartFile image)

	public ResponseMessage updateAgriCommodityPlantPartById(int id, AgriPlantPartVO plantPartVo) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			AgriCommodityPlantPart plantPart = null;
			try {
			    //uncomment
//				plantPart = mapper.readValue(plantPartVo.getData(), AgriCommodityPlantPart.class);

				Optional<AgriCommodityPlantPart> foundcommodityplant = agriCommodityPlantPartRepository.findById(id);
				if (foundcommodityplant.isPresent()) {
				    //uncomment
//					plantPart.setId(id);
//					plantPart.setImageId(foundcommodityplant.get().getImageId());
//					plantPart = agriCommodityPlantPartRepository.save(plantPart);
				    plantPart = foundcommodityplant.get();//temp must be deleted after uncomment
					
					// ---------------------image save--------------------------------
					MultipartFile image = plantPartVo.getImage();
					System.err.println("IMAGE: " + image);
					System.err.println("Body: " + plantPartVo.getData());
					FileUploadResponseDTO uploadResponse = null;
					if (image != null) {
					    	
						String pathKey = "CommodityPlantPartImages";
						Map<String, Object> pathKeyResponse = fileManagerUtil.isPathKeyExist(pathKey);
						System.out.println("PATHKEY; " + pathKeyResponse.entrySet());
						if (pathKeyResponse != null ) {
							boolean result = (boolean) pathKeyResponse.get("exist");
							if (result) {
								uploadResponse = uploadFile(pathKey, image, plantPart.getId() + ".jpeg");
							} else {
								String moduleName = "master-data";
								String dirPath = pathKey;
								DirectoryResponseDTO createDirResp = fileManagerUtil.createDirectory(moduleName,
										pathKey, dirPath, null);
								System.out.println("CreateDirResp: " + createDirResp);
								if (createDirResp != null && createDirResp.isSuccess()) {
									uploadResponse = uploadFile(pathKey, image, plantPart.getId() + ".jpeg");
								}
							}
						}
						if (uploadResponse != null) {
						    	
							plantPart.setImageId(uploadResponse.getId());
							plantPart.setImageURL(uploadResponse.getPublicUrl());
							plantPart = agriCommodityPlantPartRepository.save(plantPart);

						}

					} // image not null

					// --------------------image save--------------------------------

					approvalUtil.updateRecord(DBConstants.TBL_AGRI_COMMODITY_PLANT_PART, plantPart.getId());

					return responseMessageUtil.sendResponse(true,
							"Agri-Commodity-PlantPart" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");

				} else {
					return responseMessageUtil.sendResponse(false, "",
							"Agri-Commodity-PlantPart" + APIConstants.RESPONSE_UPDATE_ERROR + id);
				}

			} catch (Exception e) {//IO
				e.printStackTrace();
				return responseMessageUtil.sendResponse(false, "",
						"Server Error : JSON Parse Error -> Failed to Parse the Data");
			}

		}catch (DataIntegrityViolationException e) {
			LOGGER.error("DataIntegrityViolationException : ", e);
			return responseMessageUtil.sendResponse(false, "", "Duplicate entry for commodity plant part");
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}

	public ResponseMessage updateAgriCommodityPlantPartById(int id, AgriCommodityPlantPart agriCommodityPlantPart) {

		try {
			Optional<AgriCommodityPlantPart> foundcommodityplant = agriCommodityPlantPartRepository.findById(id);

			if (foundcommodityplant.isPresent()) {

				agriCommodityPlantPart.setId(id);
				agriCommodityPlantPart = agriCommodityPlantPartRepository.save(agriCommodityPlantPart);

				approvalUtil.updateRecord(DBConstants.TBL_AGRI_COMMODITY_PLANT_PART, agriCommodityPlantPart.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Commodity-PlantPart" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Commodity-PlantPart" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (DataIntegrityViolationException e) {
			LOGGER.error("DataIntegrityViolationException : ", e);
			return responseMessageUtil.sendResponse(false, "", "Duplicate entry for commodity plant part");
		}catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateAgriPlantPartById

	public ResponseMessage primaryApproveById(int id) {

		try {
			Optional<AgriCommodityPlantPart> foundCommodityPlantPart = agriCommodityPlantPartRepository.findById(id);

			if (foundCommodityPlantPart.isPresent()) {

				AgriCommodityPlantPart commodityPlantPart = foundCommodityPlantPart.get();

				commodityPlantPart.setStatus(APIConstants.STATUS_APPROVED);

				commodityPlantPart = agriCommodityPlantPartRepository.save(commodityPlantPart);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_COMMODITY_PLANT_PART, commodityPlantPart.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Commodity-PlantPart" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");

			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-Commodity-PlantPart" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<AgriCommodityPlantPart> foundCommodityPlantPart = agriCommodityPlantPartRepository.findById(id);
			if (foundCommodityPlantPart.isPresent()) {

				AgriCommodityPlantPart commodityPlantPart = foundCommodityPlantPart.get();

				commodityPlantPart.setStatus(APIConstants.STATUS_ACTIVE);

				commodityPlantPart = agriCommodityPlantPartRepository.save(commodityPlantPart);

				approvalUtil.finalApprove(DBConstants.TBL_AGRI_COMMODITY_PLANT_PART, commodityPlantPart.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Commodity-PlantPart" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");

			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-Commodity-PlantPart" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteAgriCommodityPlantPartById(int id) {

		try {
			Optional<AgriCommodityPlantPart> foundCommdityPlantPart = agriCommodityPlantPartRepository.findById(id);
			if (foundCommdityPlantPart.isPresent()) {

				AgriCommodityPlantPart commpodityPlantPart = foundCommdityPlantPart.get();
				commpodityPlantPart.setStatus(APIConstants.STATUS_DELETED);

				commpodityPlantPart = agriCommodityPlantPartRepository.save(commpodityPlantPart);

				approvalUtil.delete(DBConstants.TBL_AGRI_COMMODITY_PLANT_PART, commpodityPlantPart.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Commodity-PlantPart" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");

			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-Commodity-PlantPart" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteAgriCommodityPlantPartById

	public ResponseMessage rejectById(int id) {

		try {
			Optional<AgriCommodityPlantPart> foundCommdityPlantPart = agriCommodityPlantPartRepository.findById(id);
			if (foundCommdityPlantPart.isPresent()) {

				AgriCommodityPlantPart commpodityPlantPart = foundCommdityPlantPart.get();
				commpodityPlantPart.setStatus(APIConstants.STATUS_REJECTED);

				commpodityPlantPart = agriCommodityPlantPartRepository.save(commpodityPlantPart);

				approvalUtil.delete(DBConstants.TBL_AGRI_COMMODITY_PLANT_PART, commpodityPlantPart.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Commodity-PlantPart" + APIConstants.RESPONSE_REJECT_SUCCESS + id, "");

			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-Commodity-PlantPart" + APIConstants.RESPONSE_REJECT_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

	public AgriCommodityPlantPart findAgriCommodityPlantPartById(int id) {

		try {
			Optional<AgriCommodityPlantPart> foundPlantPart = agriCommodityPlantPartRepository.findById(id);
			if (foundPlantPart.isPresent()) {
				AgriCommodityPlantPart plantPart = foundPlantPart.get();
				Optional<AgriPhenophase> phenophase = agriPhenophaseRepository.findById(plantPart.getPhenophaseId());
				plantPart.setPhenophase(phenophase.get().getName());
				if (plantPart.getImageId() != null) {
					String imageURL = plantPart.getImageURL();
					plantPart.setImageURL(imageURL);
				}
				return plantPart;
			} else {
				throw new DoesNotExistException("Agri-Commodity-PlantPart" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findAgriPlantPartById
	
	@Transactional
	public ResponseMessage moveToMaster(int id) {
		try {
			Optional<AgriCommodityPlantPartMissing> foundAgriCommodityPlantPartMissing = agriCommodityPlantPartMissingRepository.findById(id);

			if (foundAgriCommodityPlantPartMissing.isPresent()) {
				AgriCommodityPlantPart agriCommodityPlantPart = new AgriCommodityPlantPart();
				AgriCommodityPlantPartMissing regionalAgriCommodityPlantPartMissing = foundAgriCommodityPlantPartMissing.get();
				
				agriCommodityPlantPart.setCommodityId(regionalAgriCommodityPlantPartMissing.getCommodityId());
				agriCommodityPlantPart.setPhenophaseId(regionalAgriCommodityPlantPartMissing.getPhenophaseId());
				agriCommodityPlantPart.setPlantPartId(regionalAgriCommodityPlantPartMissing.getPlantPartId());
				agriCommodityPlantPart.setImageId(regionalAgriCommodityPlantPartMissing.getImageId());
				agriCommodityPlantPart.setImageURL(regionalAgriCommodityPlantPartMissing.getImageURL());
				agriCommodityPlantPart.setStatus(regionalAgriCommodityPlantPartMissing.getStatus());
				
				AgriCommodityPlantPart savedAgriCommodityPlantPart = agriCommodityPlantPartRepository.save(agriCommodityPlantPart);
				
				agriCommodityPlantPartMissingRepository.deleteById(id);


				approvalUtil.addRecord(DBConstants.TBL_AGRI_COMMODITY_PLANT_PART, savedAgriCommodityPlantPart.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Commodity-PlantPart-Missing" + APIConstants.RESPONSE_MOVE_TO_MASTER_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Commodity-PlantPart-Missing" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}
}// AgriPlantPartService
