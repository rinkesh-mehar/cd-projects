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
import in.cropdata.cdtmasterdata.dto.interfaces.AgriPhenophaseDurationInfDto;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.AgriPhenophaseDuration;
import in.cropdata.cdtmasterdata.model.AgriPhenophaseDurationMissing;
import in.cropdata.cdtmasterdata.model.vo.AgriPhenophaseDurationVO;
import in.cropdata.cdtmasterdata.repository.AgriPhenophaseDurationMissingRepository;
import in.cropdata.cdtmasterdata.repository.AgriPhenophaseDurationRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;
import in.cropdata.toolsuite.sdk.FileManagerUtil;
import in.cropdata.toolsuite.sdk.dto.DirectoryResponseDTO;
import in.cropdata.toolsuite.sdk.dto.FileUploadResponseDTO;

@Service
public class AgriPhenophaseDurationService {

	@Autowired
	private AgriPhenophaseDurationRepository agriphenophaseDurationRepository;
	
	@Autowired
	private AgriPhenophaseDurationMissingRepository agriPhenophaseDurationMissingRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	@Autowired
	private FileManagerUtil fileManagerUtil;

	public List<AgriPhenophaseDurationInfDto> getAllAgriphenophaseDuration() {

//		String fileManagerUrl = fileManagerUtil.fileManagerUrl + "get-file?id=";

		List<AgriPhenophaseDurationInfDto> phenoDuratList = agriphenophaseDurationRepository
				.getAgriPhenophaseDuration();

		return phenoDuratList;
	}// getAllAgriphenophaseDuration
	
	public Page<AgriPhenophaseDurationInfDto> getAllAgriPhenophaseDurationPaginated(int page, int size, String  searchText, int isValid,String missing) {

		try {
			searchText = "%" + searchText + "%";

			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());

			Page<AgriPhenophaseDurationInfDto> phenophaseDurationList;

			if("0".equals(missing)) {
			if (isValid == 0) {
				phenophaseDurationList = agriphenophaseDurationRepository.getAgriPhenophaseDurationListInvalidated(sortedByIdDesc, searchText);
			} else {
				phenophaseDurationList = agriphenophaseDurationRepository.getAgriPhenophaseDurationList(sortedByIdDesc, searchText);
			}
			}else {
				if (isValid == 0) {
					phenophaseDurationList = agriphenophaseDurationRepository.getAgriPhenophaseDurationMissingListInvalidated(sortedByIdDesc, searchText);
				} else {
					phenophaseDurationList = agriphenophaseDurationRepository.getAgriPhenophaseDurationMissingList(sortedByIdDesc, searchText);
				}
			}

			return phenophaseDurationList;
		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriPhenophaseDurationPaginated	

//	public Page<AgriPhenophaseDuration> getAllAgriPhenophaseDurationPaginated(int page, int size) {
//
//		Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
//
//		Page<AgriPhenophaseDuration> phenoDuratList = agriphenophaseDurationRepository.findAll(sortedByIdDesc);
//
//		for (AgriPhenophaseDuration phenoDura : phenoDuratList) {
//			
//			Optional<GeoState> foundState = geoStateRepository.findById(phenoDura.getStateId());
//			if(foundState.isPresent()) {
//				GeoState objState = foundState.get();
//				phenoDura.setState(objState.getName());
//			}
//			Optional<AgriCommodity> foundCommodity = commodityRepositoy.findById(phenoDura.getCommodityId());
//			if (foundCommodity.isPresent()) {
//				AgriCommodity objCommodity = foundCommodity.get();
//				phenoDura.setCommodity(objCommodity.getName());
//			}
//			Optional<AgriPhenophase> foundPhenophase = agriPhenophaseRepository.findById(phenoDura.getPhenophaseId());
//			if (foundPhenophase.isPresent()) {
//				AgriPhenophase objPhenophaes = foundPhenophase.get();
//				phenoDura.setPhenophase(objPhenophaes.getName());
//			}
//			Optional<AgriSeason> foundSeason = agriSeasonRepository.findById(phenoDura.getSeasonId());
//			if (foundSeason.isPresent()) {
//				AgriSeason objSeason = foundSeason.get();
//				phenoDura.setSeason(objSeason.getName());
//			}
//			Optional<AgriVariety> foundVariety = agriVarietyRepository.findById(phenoDura.getVarietyId());
//			if(foundVariety.isPresent()) {
//				AgriVariety objVariety = foundVariety.get();
//				phenoDura.setVariety(objVariety.getName());
//			}
//		}
//
//		return phenoDuratList;
//	}// getAllPhenophaseDurationPaginated

	public ResponseMessage addAgriphenophaseDuration(AgriPhenophaseDuration agriPhenophaseDuration) {

		try {
			agriPhenophaseDuration = agriphenophaseDurationRepository.save(agriPhenophaseDuration);

			approvalUtil.addRecord(DBConstants.TBL_AGRI_PHENOPHASE_DURATION, agriPhenophaseDuration.getId());

			return responseMessageUtil.sendResponse(true,
					"Agri-Phenophase-Duration" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAgriphenophaseDuration

//	public ResponseMessage updateAgriphenophaseDurationById(int id, AgriPhenophaseDuration agriPhenophaseDuration) {
//		try {
//			Optional<AgriPhenophaseDuration> foundPhenoDura = agriphenophaseDurationRepository.findById(id);
//
//			if (foundPhenoDura.isPresent()) {
//
//				agriPhenophaseDuration.setId(id);
//				agriPhenophaseDuration = agriphenophaseDurationRepository.save(agriPhenophaseDuration);
//
//				approvalUtil.updateRecord(DBConstants.TBL_AGRI_PHENOPHASE_DURATION, agriPhenophaseDuration.getId());
//
//				return responseMessageUtil.sendResponse(true,
//						"Agri-Phenophase-Duration" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
//			} else {
//				return responseMessageUtil.sendResponse(false, "",
//						"Agri-Phenophase-Duration" + APIConstants.RESPONSE_UPDATE_ERROR + id);
//			}
//		} catch (Exception e) {
//			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
//		}
//	}// updateAgriphenophaseDurationById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<AgriPhenophaseDuration> foundPhenoDura = agriphenophaseDurationRepository.findById(id);

			if (foundPhenoDura.isPresent()) {

				AgriPhenophaseDuration phenophaseDuration = foundPhenoDura.get();

				phenophaseDuration.setStatus(APIConstants.STATUS_APPROVED);

				phenophaseDuration = agriphenophaseDurationRepository.save(phenophaseDuration);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_PHENOPHASE_DURATION, phenophaseDuration.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Phenophase-Duration" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Phenophase-Duration" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<AgriPhenophaseDuration> foundPhenoDura = agriphenophaseDurationRepository.findById(id);
			if (foundPhenoDura.isPresent()) {

				AgriPhenophaseDuration phenophaseDuration = foundPhenoDura.get();
				phenophaseDuration.setStatus(APIConstants.STATUS_ACTIVE);
				phenophaseDuration = agriphenophaseDurationRepository.save(phenophaseDuration);

				approvalUtil.finalApprove(DBConstants.TBL_AGRI_PHENOPHASE_DURATION, phenophaseDuration.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Phenophase-Duration" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Phenophase-Duration" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteAgriphenophaseDurationById(int id) {
		try {
			Optional<AgriPhenophaseDuration> foundPhenoDura = agriphenophaseDurationRepository.findById(id);
			if (foundPhenoDura.isPresent()) {

				AgriPhenophaseDuration phenophaseDuration = foundPhenoDura.get();
				phenophaseDuration.setStatus(APIConstants.STATUS_DELETED);

				phenophaseDuration = agriphenophaseDurationRepository.save(phenophaseDuration);

				approvalUtil.delete(DBConstants.TBL_AGRI_PHENOPHASE_DURATION, phenophaseDuration.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Phenophase-Duration" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Phenophase-Duration" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteAgriphenophaseDurationById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<AgriPhenophaseDuration> foundPhenoDura = agriphenophaseDurationRepository.findById(id);
			if (foundPhenoDura.isPresent()) {

				AgriPhenophaseDuration phenophaseDuration = foundPhenoDura.get();
				phenophaseDuration.setStatus(APIConstants.STATUS_REJECTED);

				phenophaseDuration = agriphenophaseDurationRepository.save(phenophaseDuration);

				approvalUtil.delete(DBConstants.TBL_AGRI_PHENOPHASE_DURATION, phenophaseDuration.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Phenophase-Duration" + APIConstants.RESPONSE_REJECT_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Phenophase-Duration" + APIConstants.RESPONSE_REJECT_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

	public AgriPhenophaseDuration findAgriphenophaseDurationById(int id) {
		try {
			Optional<AgriPhenophaseDuration> foundPhenoDura = agriphenophaseDurationRepository.findById(id);
			if (foundPhenoDura.isPresent()) {
				AgriPhenophaseDuration agriPhenophaseDuration = foundPhenoDura.get();
				return agriPhenophaseDuration;
			} else {
				throw new DoesNotExistException("Agri-Phenophase-Duration" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findAgriphenophaseDurationById

	public ResponseMessage addAgriphenophaseDuration(AgriPhenophaseDurationVO agriPhenophaseDurationVo) {
		try {

			ObjectMapper mapper = new ObjectMapper();
			AgriPhenophaseDuration agriPhenophaseDuration = null;
			try {
				agriPhenophaseDuration = mapper.readValue(agriPhenophaseDurationVo.getData(),
						AgriPhenophaseDuration.class);

				agriPhenophaseDuration = agriphenophaseDurationRepository.save(agriPhenophaseDuration);

				// ----------image save-------------------------
				MultipartFile image = agriPhenophaseDurationVo.getImage();
				System.err.println("IMAGE: " + image);
				System.err.println("Body: " + agriPhenophaseDurationVo.getData());
				FileUploadResponseDTO uploadResponse = null;
				if (image != null) {
					String pathKey = "AgriPhenophaseDurationImages";
					Map<String, Object> pathKeyResponse = fileManagerUtil.isPathKeyExist(pathKey);
					System.out.println("PATHKEY; " + pathKeyResponse.entrySet());
					if (pathKeyResponse != null ) {
						boolean result = (boolean) pathKeyResponse.get("exist");
						if (result) {
							uploadResponse = uploadFile(pathKey, image, agriPhenophaseDuration.getId() + ".jpeg");
						} else {
							String moduleName = "master-data";
							String dirPath = pathKey;
							DirectoryResponseDTO createDirResp = fileManagerUtil.createDirectory(moduleName, pathKey,
									dirPath, null);
							System.out.println("CreateDirResp: " + createDirResp);
							if (createDirResp != null && createDirResp.isSuccess()) {
								uploadResponse = uploadFile(pathKey, image, agriPhenophaseDuration.getId() + ".jpeg");
							}
						}
					}
				} // image not null


				if (uploadResponse != null) {
					agriPhenophaseDuration.setImageId(uploadResponse.getId());
					agriPhenophaseDuration.setImageUrl(uploadResponse.getPublicUrl());
					agriPhenophaseDuration = agriphenophaseDurationRepository.save(agriPhenophaseDuration);
				}

				// ----------image save-------------------------

				approvalUtil.addRecord(DBConstants.TBL_AGRI_PHENOPHASE_DURATION, agriPhenophaseDuration.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Phenophase-Duration" + APIConstants.RESPONSE_ADD_SUCCESS, "");
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
	}
	
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

	public ResponseMessage updateAgriphenophaseDurationById(int id, AgriPhenophaseDurationVO agriPhenophaseDurationVo) {
		try {

			ObjectMapper mapper = new ObjectMapper();
			AgriPhenophaseDuration agriPhenophaseDuration = null;
			try {
				agriPhenophaseDuration = mapper.readValue(agriPhenophaseDurationVo.getData(),
						AgriPhenophaseDuration.class);

				Optional<AgriPhenophaseDuration> foundPhenoDura = agriphenophaseDurationRepository.findById(id);

				if (foundPhenoDura.isPresent()) {

					agriPhenophaseDuration.setId(id);
					agriPhenophaseDuration.setImageId(foundPhenoDura.get().getImageId());
					agriPhenophaseDuration = agriphenophaseDurationRepository.save(agriPhenophaseDuration);

					// ----------image save-------------------------
					MultipartFile image = agriPhenophaseDurationVo.getImage();
					System.err.println("IMAGE: " + image);
					System.err.println("Body: " + agriPhenophaseDurationVo.getData());
					FileUploadResponseDTO uploadResponse = null;
					if (image != null) {
						String pathKey = "AgriPhenophaseDurationImages";
						Map<String, Object> pathKeyResponse = fileManagerUtil.isPathKeyExist(pathKey);
						System.out.println("PATHKEY; " + pathKeyResponse.entrySet());
						if (pathKeyResponse != null ) {
							boolean result = (boolean) pathKeyResponse.get("exist");
							if (result) {
								uploadResponse = uploadFile(pathKey, image, agriPhenophaseDuration.getId() + ".jpeg");
							} else {
								String moduleName = "master-data";
								String dirPath = pathKey;
								DirectoryResponseDTO createDirResp = fileManagerUtil.createDirectory(moduleName,
										pathKey, dirPath, null);
								System.out.println("CreateDirResp: " + createDirResp);
								if (createDirResp != null && createDirResp.isSuccess()) {
									uploadResponse = uploadFile(pathKey, image, agriPhenophaseDuration.getId() + ".jpeg");
								}
							}
						}
					} // image not null
						// ----------image save-------------------------

					
					if (uploadResponse != null) {
						agriPhenophaseDuration.setImageId(uploadResponse.getId());
						agriPhenophaseDuration.setImageUrl(uploadResponse.getPublicUrl());
						agriPhenophaseDuration = agriphenophaseDurationRepository.save(agriPhenophaseDuration);

					}

					approvalUtil.updateRecord(DBConstants.TBL_AGRI_PHENOPHASE_DURATION, agriPhenophaseDuration.getId());

					return responseMessageUtil.sendResponse(true,
							"Agri-Phenophase-Duration" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
				} else {
					return responseMessageUtil.sendResponse(false, "",
							"Agri-Phenophase-Duration" + APIConstants.RESPONSE_UPDATE_ERROR + id);
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
	
	@Transactional
	public ResponseMessage moveToMaster(int id) {
		try {
			Optional<AgriPhenophaseDurationMissing> foundMissingCommodity = agriPhenophaseDurationMissingRepository.findById(id);

			if (foundMissingCommodity.isPresent()) {
				AgriPhenophaseDuration agriPhenophaseDuration = new AgriPhenophaseDuration();
				AgriPhenophaseDurationMissing regionalCommodityMissing = foundMissingCommodity.get();
				agriPhenophaseDuration.setStateCode(regionalCommodityMissing.getStateCode());
				agriPhenophaseDuration.setSeasonId(regionalCommodityMissing.getSeasonId());
				agriPhenophaseDuration.setCommodityId(regionalCommodityMissing.getCommodityId());
				agriPhenophaseDuration.setVarietyId(regionalCommodityMissing.getVarietyId());
				agriPhenophaseDuration.setPhenophaseId(regionalCommodityMissing.getPhenophaseId());
				agriPhenophaseDuration.setPhenophaseStart(regionalCommodityMissing.getPhenophaseStart());
				agriPhenophaseDuration.setPhenophaseEnd(regionalCommodityMissing.getPhenophaseEnd());
				agriPhenophaseDuration.setImageId(regionalCommodityMissing.getImageId());
				agriPhenophaseDuration.setImageUrl(regionalCommodityMissing.getImageUrl());
				agriPhenophaseDuration.setStatus(regionalCommodityMissing.getStatus());
				AgriPhenophaseDuration savedAgriPhenophaseDuration = agriphenophaseDurationRepository.save(agriPhenophaseDuration);
				
				agriPhenophaseDurationMissingRepository.deleteById(id);


				approvalUtil.addRecord(DBConstants.TBL_AGRI_PHENOPHASE_DURATION, savedAgriPhenophaseDuration.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Phenophase-Duration-Missing" + APIConstants.RESPONSE_MOVE_TO_MASTER_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Phenophase-Duration-Missing" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}

}
