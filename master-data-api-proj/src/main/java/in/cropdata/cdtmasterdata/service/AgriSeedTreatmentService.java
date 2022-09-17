package in.cropdata.cdtmasterdata.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.constants.DBConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriSeedTreatmentInfDto;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.AgriSeedTreatment;
import in.cropdata.cdtmasterdata.model.AgriSeedTreatmentMissing;
import in.cropdata.cdtmasterdata.repository.AgriSeedTreatmentMissingRepository;
import in.cropdata.cdtmasterdata.repository.AgriSeedTreatmentRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AgriSeedTreatmentService {

	@Autowired
	private AgriSeedTreatmentRepository agriSeedTreatmentRepository;
	
	@Autowired
	private AgriSeedTreatmentMissingRepository agriSeedTreatmentMissingRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<AgriSeedTreatmentInfDto> getAllAgriSeedTreatment() {
		try {
			List<AgriSeedTreatmentInfDto> list = agriSeedTreatmentRepository.getAgriSeedTreatmentList();

			return list;
		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriSeedTreatment
	
	public Page<AgriSeedTreatmentInfDto> getAllAgriSeedTreatmentPaginated(int page, int size, String  searchText, int isValid,String missing,String commodityId,String varietyId,String name,String filter) {

		try {
			searchText = "%"+searchText+"%";
			
//		System.out.println("searchText--> " + searchText);
			Page<AgriSeedTreatmentInfDto> seedTreatmentList;

			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").ascending());
			
			if("1".equals(filter)) {
				if("".equals(commodityId)) {
					commodityId = null;
				}
				if("".equals(varietyId)) {
					varietyId = null;
				}
				if("".equals(name)) {
					name = null;
				}
				seedTreatmentList = agriSeedTreatmentRepository.getAgriSeedTreatmentByMultiSearchFilters(sortedByIdDesc, commodityId,varietyId,name);
				
			}else 
			if("0".equals(missing)) {
			if (isValid == 0) {
				seedTreatmentList = agriSeedTreatmentRepository.getAgriSeedTreatmentInvalidated(sortedByIdDesc,searchText);
			} else {
				seedTreatmentList = agriSeedTreatmentRepository.getAgriSeedTreatment(sortedByIdDesc,searchText);
			}
			}else {
				if (isValid == 0) {
					seedTreatmentList = agriSeedTreatmentRepository.getAgriSeedTreatmentMissingInvalidated(sortedByIdDesc,searchText);
				} else {
					seedTreatmentList = agriSeedTreatmentRepository.getAgriSeedTreatmentMissing(sortedByIdDesc,searchText);
				}	
			}

			return seedTreatmentList;
		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriSeedTreatmentPaginated

	public ResponseMessage addAgriSeedTreatment(AgriSeedTreatment agriSeedTreatment) {

		try {
			agriSeedTreatment = agriSeedTreatmentRepository.save(agriSeedTreatment);

			approvalUtil.addRecord(DBConstants.TBL_AGRI_SEED_TREATMENT, agriSeedTreatment.getId());

			return responseMessageUtil.sendResponse(true, "Agri-SeedTreatment" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// addAgriSeedTreatment

	public ResponseMessage updateAgriSeedTreatmentById(int id, AgriSeedTreatment agriSeedTreatment) {
		try {
			Optional<AgriSeedTreatment> foundSeedTreatment = agriSeedTreatmentRepository.findById(id);

			if (foundSeedTreatment.isPresent()) {

				agriSeedTreatment.setId(id);
				agriSeedTreatment = agriSeedTreatmentRepository.save(agriSeedTreatment);

				approvalUtil.updateRecord(DBConstants.TBL_AGRI_SEED_TREATMENT, agriSeedTreatment.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-SeedTreatment" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-SeedTreatment" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateAgriSeedTreatmentById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<AgriSeedTreatment> foundSeedTreatment = agriSeedTreatmentRepository.findById(id);

			if (foundSeedTreatment.isPresent()) {

				AgriSeedTreatment agriSeedTreatment = foundSeedTreatment.get();
				agriSeedTreatment.setStatus(APIConstants.STATUS_APPROVED);

				agriSeedTreatment = agriSeedTreatmentRepository.save(agriSeedTreatment);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_SEED_TREATMENT, agriSeedTreatment.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-SeedTreatment" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-SeedTreatment" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<AgriSeedTreatment> foundSeedTreatment = agriSeedTreatmentRepository.findById(id);
			if (foundSeedTreatment.isPresent()) {

				AgriSeedTreatment agriSeedTreatment = foundSeedTreatment.get();
				agriSeedTreatment.setStatus(APIConstants.STATUS_ACTIVE);

				agriSeedTreatment = agriSeedTreatmentRepository.save(agriSeedTreatment);

				approvalUtil.finalApprove(DBConstants.TBL_AGRI_SEED_TREATMENT, agriSeedTreatment.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-SeedTreatment" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-SeedTreatment" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteAgriSeedTreatmentById(int id) {

		try {
			Optional<AgriSeedTreatment> foundSeedTreatment = agriSeedTreatmentRepository.findById(id);

			if (foundSeedTreatment.isPresent()) {

				AgriSeedTreatment agriSeedTreatment = foundSeedTreatment.get();
				agriSeedTreatment.setStatus(APIConstants.STATUS_DELETED);

				agriSeedTreatment = agriSeedTreatmentRepository.save(agriSeedTreatment);

				approvalUtil.delete(DBConstants.TBL_AGRI_SEED_TREATMENT, agriSeedTreatment.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-SeedTreatment" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-SeedTreatment" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteAgriSeedTreatmentById

	public ResponseMessage rejectById(int id) {

		try {
			Optional<AgriSeedTreatment> foundSeedTreatment = agriSeedTreatmentRepository.findById(id);

			if (foundSeedTreatment.isPresent()) {

				AgriSeedTreatment agriSeedTreatment = foundSeedTreatment.get();
				agriSeedTreatment.setStatus(APIConstants.STATUS_REJECTED);

				agriSeedTreatment = agriSeedTreatmentRepository.save(agriSeedTreatment);

				approvalUtil.delete(DBConstants.TBL_AGRI_SEED_TREATMENT, agriSeedTreatment.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-SeedTreatment" + APIConstants.RESPONSE_REJECT_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-SeedTreatment" + APIConstants.RESPONSE_REJECT_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

	public AgriSeedTreatment findAgriSeedTreatmentById(int id) {

		try {
			Optional<AgriSeedTreatment> foundSeedTreatment = agriSeedTreatmentRepository.findById(id);

			if (foundSeedTreatment.isPresent()) {
				return foundSeedTreatment.get();
			} else {
				throw new DoesNotExistException("Agri-SeedTreatment" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findAgriSeedTreatmentById
	
	@Transactional
	public ResponseMessage moveToMaster(int id) {
		try {
			Optional<AgriSeedTreatmentMissing> foundMissingCommodity = agriSeedTreatmentMissingRepository.findById(id);

			if (foundMissingCommodity.isPresent()) {
				AgriSeedTreatment agriSeedTreatment = new AgriSeedTreatment();
				AgriSeedTreatmentMissing agriSeedTreatmentMissing = foundMissingCommodity.get();
				
				agriSeedTreatment.setCommodityId(agriSeedTreatmentMissing.getCommodityId());
				agriSeedTreatment.setVarietyId(agriSeedTreatmentMissing.getVarietyId());
				agriSeedTreatment.setUomId(agriSeedTreatmentMissing.getUomId());
				agriSeedTreatment.setName(agriSeedTreatmentMissing.getName());
				agriSeedTreatment.setDose(agriSeedTreatmentMissing.getDose());
				agriSeedTreatment.setStatus(agriSeedTreatmentMissing.getStatus());
				
				AgriSeedTreatment savedAgriSeedTreatment = agriSeedTreatmentRepository.save(agriSeedTreatment);
				
				agriSeedTreatmentMissingRepository.deleteById(id);


				approvalUtil.addRecord(DBConstants.TBL_AGRI_SEED_TREATMENT, savedAgriSeedTreatment.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-SeedTreatment-Missing" + APIConstants.RESPONSE_MOVE_TO_MASTER_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-SeedTreatment-Missing" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}
	
}// AgriSeedTreatmentService
