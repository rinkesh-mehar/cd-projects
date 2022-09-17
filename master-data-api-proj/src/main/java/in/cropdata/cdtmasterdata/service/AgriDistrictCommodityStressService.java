package in.cropdata.cdtmasterdata.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.imageio.ImageIO;

import in.cropdata.cdtmasterdata.dao.ApiUtilDao;
import in.cropdata.cdtmasterdata.repository.AgriStressRepository;
import org.imgscalr.Scalr;
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
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriDistrictCommodityStressInfDto;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriCommodityStressStageInfDto;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.AgriDistrictCommodityStress;
import in.cropdata.cdtmasterdata.model.vo.AgriDistrictCommodityStressVO;
import in.cropdata.cdtmasterdata.repository.AgriDistrictCommodityStressRepository;
import in.cropdata.cdtmasterdata.repository.FlipbookSymptomsRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;
import in.cropdata.toolsuite.sdk.FileManagerUtil;
import in.cropdata.toolsuite.sdk.dto.DirectoryResponseDTO;
import in.cropdata.toolsuite.sdk.dto.FileUploadResponseDTO;

@Service
public class AgriDistrictCommodityStressService {
	
	
	private static final Logger log = LoggerFactory.getLogger(AgriDistrictCommodityStressService.class);


	@Autowired
	private AgriDistrictCommodityStressRepository agriDistrictCommodityStressRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	@Autowired
	private FlipbookSymptomsRepository flipbookSymptomsRepository;

	@Autowired
	private FileManagerUtil fileManagerUtil;

	@Autowired
	private AgriStressRepository agriStressRepository;

	@Autowired
	private ApiUtilDao apiUtilDao;

	public List<AgriDistrictCommodityStress> getDistrictCommodityStress() {
		try {
			List<AgriDistrictCommodityStress> List = agriDistrictCommodityStressRepository.findAll();

			return List;
		} catch (Exception e) {
			throw e;
		}

	}// getAllAgriStress

	public Page<AgriDistrictCommodityStressInfDto> getDistrictCommodityStressPaginatedList(int page, int size, String searchText, Integer isValid) {

		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
//			String fileManagerUrl = fileManagerUtil.fileManagerUrl + "get-file?id=";
			Page<AgriDistrictCommodityStressInfDto> StressList;
			if (isValid == 0) {
				StressList = agriDistrictCommodityStressRepository.getDistrictCommodityStressPaginatedListWithInvalidated(sortedByIdDesc, searchText);
			} else {
				StressList = agriDistrictCommodityStressRepository.getDistrictCommodityStressPaginatedList(sortedByIdDesc, searchText);
			}
			log.debug("District Commodity Stress List :"+StressList);

			return StressList;
		} catch (Exception e) {
			throw e;
		}

	}// getAllStressPaginated

//	public ResponseMessage addAgriStress(AgriStress agriStress) {
//
//		try {
//			agriStress = agriStressRepository.save(agriStress);
//			Set<AgriStressSymptom> agriStressSymptomList = new HashSet<AgriStressSymptom>();
//			if (agriStress.getSymptomsList().size() > 0 && !agriStress.getSymptomsList().isEmpty()) {
////				flipbookSymptomsRepository.saveAll(agriStress.getSymptomsList());
//				for (FlipbookSymptoms flipbookSymptoms : agriStress.getSymptomsList()) {
//					AgriStressSymptom agriStressSymptom = new AgriStressSymptom();
//					agriStressSymptom.setSymptomId(flipbookSymptoms.getId());
//					agriStressSymptom.setStressId(agriStress.getId());
//					agriStressSymptomList.add(agriStressSymptom);
//				}
//				agriStressSymptomRepository.saveAll(agriStressSymptomList);
//			}
//
//			approvalUtil.addRecord(DBConstants.TBL_AGRI__STRESS, agriStress.getId());
//
//			return responseMessageUtil.sendResponse(true, "Agri-Stress" + APIConstants.RESPONSE_ADD_SUCCESS, "");
//
//		} catch (Exception e) {
//			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
//		}
//
//	}// addAllAgriStress

//	public ResponseMessage updateAgriStressById(int id, AgriStress agriStress) {
//		try {
//			Optional<AgriStress> foundVareity = agriStressRepository.findById(id);
//			if (foundVareity.isPresent()) {
//
//				agriStress.setId(id);
//				agriStressRepository.save(agriStress);
//
//				try {
//					Set<AgriStressSymptom> agriStressSymptomList = new HashSet<AgriStressSymptom>();
//					if (agriStress.getSymptomsList().size() > 0 && !agriStress.getSymptomsList().isEmpty()) {
//						for (FlipbookSymptoms flipbookSymptoms : agriStress.getSymptomsList()) {
//							AgriStressSymptom agriStressSymptom = new AgriStressSymptom();
//							agriStressSymptom.setSymptomId(flipbookSymptoms.getId());
//							agriStressSymptom.setStressId(agriStress.getId());
//							agriStressSymptomList.add(agriStressSymptom);
//						}
//						agriStressSymptomRepository.saveAll(agriStressSymptomList);
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//					// TODO: handle exception
//				}
//
//				approvalUtil.updateRecord(DBConstants.TBL_AGRI__STRESS, agriStress.getId());
//
//				return responseMessageUtil.sendResponse(true, "Agri-Stress" + APIConstants.RESPONSE_UPDATE_SUCCESS + id,
//						"");
//
//			} else {
//
//				return responseMessageUtil.sendResponse(false, "",
//						"Agri-Stress" + APIConstants.RESPONSE_UPDATE_ERROR + id);
//			}
//		} catch (Exception e) {
//			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
//		}
//	}// updateAgriStressById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<AgriDistrictCommodityStress> foundStress = agriDistrictCommodityStressRepository.findById(id);

			if (foundStress.isPresent()) {

				AgriDistrictCommodityStress Stress = foundStress.get();
				
				Stress.setStatus(APIConstants.STATUS_APPROVED);

				Stress = agriDistrictCommodityStressRepository.save(Stress);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_DISTRICT_COMMODITY_STRESS, Stress.getId());

				return responseMessageUtil.sendResponse(true,
						"District Commodity Stress" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"District Commodity Stress" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<AgriDistrictCommodityStress> foundStress = agriDistrictCommodityStressRepository.findById(id);

			if (foundStress.isPresent()) {

				AgriDistrictCommodityStress Stress = foundStress.get();
				
				Stress.setStatus(APIConstants.STATUS_ACTIVE);

				Stress = agriDistrictCommodityStressRepository.save(Stress);

				approvalUtil.finalApprove(DBConstants.TBL_AGRI_DISTRICT_COMMODITY_STRESS, Stress.getId());

				return responseMessageUtil.sendResponse(true,
						"District Commodity Stress" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"District Commodity Stress" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteAgriStressById(int id) {
		try {
			Optional<AgriDistrictCommodityStress> foundStress = agriDistrictCommodityStressRepository.findById(id);
			if (foundStress.isPresent()) {

				AgriDistrictCommodityStress Stress = foundStress.get();
				
				Stress.setStatus(APIConstants.STATUS_REJECTED);

				Stress = agriDistrictCommodityStressRepository.save(Stress);

				approvalUtil.delete(DBConstants.TBL_AGRI_DISTRICT_COMMODITY_STRESS, Stress.getId());

				return responseMessageUtil.sendResponse(true, "District Commodity Stress" + APIConstants.RESPONSE_DELETE_SUCCESS + id,
						"");

			} else {

				return responseMessageUtil.sendResponse(false, "",
						"District Commodity Stress" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteAgriStressById
	
	public ResponseMessage rejectById(int id) {
		try {
			Optional<AgriDistrictCommodityStress> foundStress = agriDistrictCommodityStressRepository.findById(id);
			if (foundStress.isPresent()) {

				AgriDistrictCommodityStress stress = foundStress.get();
				stress.setStatus(APIConstants.STATUS_REJECTED);

				stress = agriDistrictCommodityStressRepository.save(stress);

				approvalUtil.delete(DBConstants.TBL_AGRI_DISTRICT_COMMODITY_STRESS, stress.getId());

				return responseMessageUtil.sendResponse(true,
						"District Commodity Stress" + APIConstants.RESPONSE_REJECT_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"District Commodity Stress" + APIConstants.RESPONSE_REJECT_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

	public AgriDistrictCommodityStress findAgriStressById(int id) {
		try {
			Optional<AgriDistrictCommodityStress> foundStress = agriDistrictCommodityStressRepository.findById(id);
			if (foundStress.isPresent()) {
				AgriDistrictCommodityStress agriCommodityStress = foundStress.get();
				return agriCommodityStress;
			} else {
				throw new DoesNotExistException("District Commodity Stress" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findAgriStressById

//	private AgriStress getData(AgriStress agriStress) {
//
//		List<AgriStressSymptom> agriStressSymptomList = agriStressSymptomRepository
//				.findAllByStressId(agriStress.getId());
//
//		Set<FlipbookSymptoms> symptomsList = new HashSet<>();
//
//		for (AgriStressSymptom agriStressSymptom : agriStressSymptomList) {
//
//			Optional<FlipbookSymptoms> symptoms = flipbookSymptomsRepository.findById(agriStressSymptom.getSymptomId());
//			if (symptoms.isPresent()) {
//				symptomsList.add(symptoms.get());
//			}
//		}
//		agriStress.setSymptomsList(symptomsList);
//
//		return agriStress;
//	}//getData

	public List<AgriDistrictCommodityStressInfDto> getAllAgriStressByStressType(int commodityId,int stressTypeId) {
		return agriDistrictCommodityStressRepository.findAllByStressTypeId(commodityId,stressTypeId);
	}
	
	public List<AgriDistrictCommodityStressInfDto> getAllStressByCommodityId(int commodityId,int phenophaseId){
		return agriDistrictCommodityStressRepository.findByCommodityId(commodityId,phenophaseId);
	}
	

	public List<AgriCommodityStressStageInfDto> getStressByCommodityId(int commodityId) {
		return agriDistrictCommodityStressRepository.getStressByCommodityId(commodityId);
	}

//	public List<FlipbookSymptoms> getAllFlipbookSymptoms() {
//		return flipbookSymptomsRepository.findAll();
//	}

	@Transactional
	public ResponseMessage addAgriDistrictCommodityStress(AgriDistrictCommodityStressVO agriDistrictCommodityStressVO) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			AgriDistrictCommodityStress agriCommodityStress = null;
			try {
				agriCommodityStress = mapper.readValue(agriDistrictCommodityStressVO.getData(), AgriDistrictCommodityStress.class);

//				Integer stressId = agriStressRepository.findIDByName(agriCommodityStress.getName());
//
//				if (stressId == null) {
//					throw new DoesNotExistException("Please Enter Valid Stress Name");
//				}
//				agriCommodityStress.setStressId(agriStressRepository.findIDByName(agriCommodityStress.getName()));

				List<String> phenophasePK = apiUtilDao.getAgriPhenophaseID(agriCommodityStress.getStartPhenophaseId(),
						agriCommodityStress.getEndPhenophaseId(), agriCommodityStress.getCommodityId());

				if (phenophasePK.size() != 2) {
					return responseMessageUtil.sendResponse(false, "", "Please Enter Valid Start And End Phenophase");
				}

				String phenophases = apiUtilDao.getPhenophase(phenophasePK, agriCommodityStress.getCommodityId());

				agriCommodityStress.setPhenophases(phenophases);

				agriCommodityStress = agriDistrictCommodityStressRepository.save(agriCommodityStress);
				// ----------img save---------------------
				MultipartFile image = agriDistrictCommodityStressVO.getImage();
				System.err.println("IMAGE: " + image);
				System.err.println("Body: " + agriDistrictCommodityStressVO.getData());
				FileUploadResponseDTO uploadResponse = null;

				if (image != null) {
					String pathKey = "AgriStressImages";
					Map<String, Object> pathKeyResponse = fileManagerUtil.isPathKeyExist(pathKey);
					System.out.println("PATHKEY; " + pathKeyResponse.entrySet());
					if (pathKeyResponse != null) {
						boolean result = (boolean) pathKeyResponse.get("exist");
						if (result) {
							uploadResponse = uploadFile(pathKey, image, agriCommodityStress.getId() + ".jpeg");
						} else {
							String moduleName = "master-data";
							String dirPath = pathKey;
							DirectoryResponseDTO createDirResp = fileManagerUtil.createDirectory(moduleName, pathKey,
									dirPath, null);
							System.out.println("CreateDirResp: " + createDirResp);
							if (createDirResp != null && createDirResp.isSuccess()) {
								uploadResponse = uploadFile(pathKey, image, agriCommodityStress.getId() + ".jpeg");
							}
						}
					} // image not null

					if (uploadResponse != null) {
						agriCommodityStress.setImageId(uploadResponse.getId());
						agriCommodityStress.setImageURL(uploadResponse.getPublicUrl());
						agriCommodityStress = agriDistrictCommodityStressRepository.save(agriCommodityStress);
					}
				}
				// ------------img save-------------------

				approvalUtil.addRecord(DBConstants.TBL_AGRI_DISTRICT_COMMODITY_STRESS, agriCommodityStress.getId());

				return responseMessageUtil.sendResponse(true, "District Commodirt Stress" + APIConstants.RESPONSE_ADD_SUCCESS, "");
			} catch (IOException e) {
				e.printStackTrace();
				return responseMessageUtil.sendResponse(false, "",
						"Server Error : JSON Parse Error -> Failed to Parse the Data");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAllAgriPlantPart

	private FileUploadResponseDTO uploadFile(String pathKey, MultipartFile image, String fileName) {
//		resizeImage(image);
		FileUploadResponseDTO fileUploadResp = null;

		try {
			 fileUploadResp = fileManagerUtil.uploadFile(pathKey, false, fileName, true, image,
					null);
			System.out.println("FileUploadResp: " + fileUploadResp);
			return fileUploadResp;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}// uploadFile(String pathKey, MultipartFile image)

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

	@Transactional
	public ResponseMessage updateAgriDistrictCommodityStressById(int id, AgriDistrictCommodityStressVO agriDistrictCommodityStressVO) {

		try {

			ObjectMapper mapper = new ObjectMapper();
			AgriDistrictCommodityStress agriCommodityStress = null;
			try {

				agriCommodityStress = mapper.readValue(agriDistrictCommodityStressVO.getData(), AgriDistrictCommodityStress.class);
//				if (_id != null) {
//					agriStress.setImageId(_id);
//				}

				Optional<AgriDistrictCommodityStress> foundVareity = agriDistrictCommodityStressRepository.findById(id);
				if (foundVareity.isPresent()) {

//					Integer stressId = agriStressRepository.findIDByName(agriCommodityStress.getName());
//
//					if (stressId == null) {
//						throw new DoesNotExistException("Please Enter Valid Stress Name");
//					}
//					agriCommodityStress.setStressId(agriStressRepository.findIDByName(agriCommodityStress.getName()));

					agriCommodityStress.setId(id);
					AgriDistrictCommodityStress dbExist = foundVareity.get();
					agriCommodityStress.setImageId(dbExist.getImageId());
					agriCommodityStress.setImageURL(dbExist.getImageURL());

					agriCommodityStress.setPhenophases(agriDistrictCommodityStressRepository.calculatePhenophase(agriCommodityStress.getStateCode(),
							agriCommodityStress.getSeasonId(),
							agriCommodityStress.getCommodityId(),
							agriCommodityStress.getVarietyId(),
							agriCommodityStress.getStressId()));

					agriCommodityStress = agriDistrictCommodityStressRepository.save(agriCommodityStress);

					// ---------file save----------

					MultipartFile image = agriDistrictCommodityStressVO.getImage();
					System.err.println("IMAGE: " + image);
					System.err.println("Body: " + agriDistrictCommodityStressVO.getData());
					FileUploadResponseDTO uploadResponse = null;
					if (image != null) {
						String pathKey = "AgriStressImages";
						Map<String, Object> pathKeyResponse = fileManagerUtil.isPathKeyExist(pathKey);
						System.out.println("PATHKEY; " + pathKeyResponse.entrySet());
						if (pathKeyResponse != null) {
							boolean result = (boolean) pathKeyResponse.get("exist");
							if (result) {
								uploadResponse = uploadFile(pathKey, image, agriCommodityStress.getId() + ".jpeg");
							} else {
								String moduleName = "master-data";
								String dirPath = pathKey;
								DirectoryResponseDTO createDirResp = fileManagerUtil.createDirectory(moduleName,
										pathKey, dirPath, null);
								System.out.println("CreateDirResp: " + createDirResp);
								if (createDirResp != null && createDirResp.isSuccess()) {
									uploadResponse = uploadFile(pathKey, image, agriCommodityStress.getId() + ".jpeg");
								}
							}
						}
					} // image not null

					if (uploadResponse != null) {
						agriCommodityStress.setImageId(uploadResponse.getId());
						agriCommodityStress.setImageURL(uploadResponse.getPublicUrl());
						agriCommodityStress = agriDistrictCommodityStressRepository.save(agriCommodityStress);
					}

					// ---------file save

					approvalUtil.updateRecord(DBConstants.TBL_AGRI_DISTRICT_COMMODITY_STRESS, agriCommodityStress.getId());

					return responseMessageUtil.sendResponse(true,
							"District Commodity Stress" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
				} else {

					return responseMessageUtil.sendResponse(false, "",
							"District Commodity Stress" + APIConstants.RESPONSE_UPDATE_ERROR + id);
				}
			} catch (IOException e) {
				e.printStackTrace();
				return responseMessageUtil.sendResponse(false, "",
						"Server Error : JSON Parse Error -> Failed to Parse the Data");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}

}