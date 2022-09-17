package in.cropdata.cdtmasterdata.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.constants.DBConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriCommodityInfo;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriPlantPartInfo;
import in.cropdata.cdtmasterdata.exceptions.AlreadyExistException;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.AgriPlantPart;
import in.cropdata.cdtmasterdata.repository.AgriPlantPartRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;
import in.cropdata.cdtmasterdata.website.dto.DepartmentDto;

@Service
public class AgriPlantPartService {

	@Autowired
	private AgriPlantPartRepository agriPlantPartRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<AgriPlantPart> getAllAgriPlantPart() {
		try {
			List<AgriPlantPart> list = agriPlantPartRepository.findAll(Sort.by("name"));

			return list;
		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriPlantPart

//	public Page<AgriPlantPartInfo> getAllAgriPlantPartPeginated(int page, int size , String searchText) {
//		try {
//			searchText = "%"+searchText+"% ";
//			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").ascending());
//			Page<AgriPlantPartInfo> plantPartList = agriPlantPartRepository.findAllByNameIgnoreCaseContaining(sortedByIdDesc,searchText);
//
//			return plantPartList;
//		} catch (Exception e) {
//			throw e;
//		}
//	}// getAllAgriPlantPartPeginated
	
	public Page<AgriPlantPartInfo> getAllAgriPlantPartPeginated(int page, int size, String searchText) {

		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());


			Page<AgriPlantPartInfo> commodityList = agriPlantPartRepository
					.findAllWithSearch(sortedByIdDesc, searchText);

			return commodityList;

		} catch (Exception e) {
			throw e;
		}
	}//getAllAgriCommodityPeginated

	public ResponseMessage addAgriPlantPart(AgriPlantPart agriPlantPart) {

		try {

			Integer count = agriPlantPartRepository.findAlreadyExistPlantPartForAddMode(agriPlantPart.getName());
			if(count == 0) {
				agriPlantPart = agriPlantPartRepository.save(agriPlantPart);
		}else if(count > 0) {
			
			throw new AlreadyExistException("Duplicate entry for plant Part " + agriPlantPart.getName());
		}
			

			approvalUtil.addRecord(DBConstants.TBL_AGRI_PLANT_PART, agriPlantPart.getId());

			return responseMessageUtil.sendResponse(true, "Agri-PlantPart" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		}catch (AlreadyExistException e) {
			return responseMessageUtil.sendResponse(false, "",e.getMessage());
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAgriPlantPart

	public ResponseMessage updateAgriPlantPartById(int id, AgriPlantPart agriPlantPart) {
		try {
			Optional<AgriPlantPart> foundVareity = agriPlantPartRepository.findById(id);

			if (foundVareity.isPresent()) {
				
				Integer count = agriPlantPartRepository.findAlreadyExistPlantPartForEditMode(id,agriPlantPart.getName());
				if(count == 0) {
					agriPlantPart.setId(id);
					agriPlantPart = agriPlantPartRepository.save(agriPlantPart);
			}else if(count > 0) {
				
				throw new AlreadyExistException("Duplicate entry for plant Part " + agriPlantPart.getName());
			}

				

				approvalUtil.updateRecord(DBConstants.TBL_AGRI_PLANT_PART, agriPlantPart.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-PlantPart" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-PlantPart" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		}catch (AlreadyExistException e) {
			return responseMessageUtil.sendResponse(false, "",e.getMessage());
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateAgriPlantPartById

	public ResponseMessage primaryApproveById(int id) {
		try {

			Optional<AgriPlantPart> foundPlantPart = agriPlantPartRepository.findById(id);

			if (foundPlantPart.isPresent()) {

				AgriPlantPart plantPart = foundPlantPart.get();
				plantPart.setStatus(APIConstants.STATUS_APPROVED);

				plantPart = agriPlantPartRepository.save(plantPart);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_PLANT_PART, plantPart.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-PlantPart" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-PlantPart" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<AgriPlantPart> foundPlantPart = agriPlantPartRepository.findById(id);

			if (foundPlantPart.isPresent()) {

				AgriPlantPart plantPart = foundPlantPart.get();
				plantPart.setStatus(APIConstants.STATUS_ACTIVE);

				plantPart = agriPlantPartRepository.save(plantPart);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_PLANT_PART, plantPart.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-PlantPart" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-PlantPart" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteAgriPlantPartById(int id) {
		try {
			Optional<AgriPlantPart> foundPlantPart = agriPlantPartRepository.findById(id);
			if (foundPlantPart.isPresent()) {

				AgriPlantPart plantPart = foundPlantPart.get();
				plantPart.setStatus(APIConstants.STATUS_DELETED);

				plantPart = agriPlantPartRepository.save(plantPart);

				approvalUtil.delete(DBConstants.TBL_AGRI_PLANT_PART, plantPart.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-PlantPart" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-PlantPart" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// deleteAgriPlantPartById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<AgriPlantPart> foundPlantPart = agriPlantPartRepository.findById(id);
			if (foundPlantPart.isPresent()) {

				AgriPlantPart plantPart = foundPlantPart.get();
				plantPart.setStatus(APIConstants.STATUS_REJECTED);

				plantPart = agriPlantPartRepository.save(plantPart);

				approvalUtil.delete(DBConstants.TBL_AGRI_PLANT_PART, plantPart.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-PlantPart" + APIConstants.RESPONSE_REJECT_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-PlantPart" + APIConstants.RESPONSE_REJECT_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// rejectById

	public AgriPlantPart findAgriPlantPartById(int id) {
		try {
			Optional<AgriPlantPart> foundPlantPart = agriPlantPartRepository.findById(id);
			if (foundPlantPart.isPresent()) {
				return foundPlantPart.get();
			} else {
				throw new DoesNotExistException("Agri-PlantPart" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findAgriPlantPartById
}// AgriPlantPartService
