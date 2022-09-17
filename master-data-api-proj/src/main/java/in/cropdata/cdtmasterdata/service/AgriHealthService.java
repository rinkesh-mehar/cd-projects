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
import in.cropdata.cdtmasterdata.dto.interfaces.AgriHealthInfDto;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriStandardQuantityChartInfDto;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.AgriCommodity;
import in.cropdata.cdtmasterdata.model.AgriHealth;
import in.cropdata.cdtmasterdata.model.AgriHealthMissing;
import in.cropdata.cdtmasterdata.model.AgriHealthParameter;
import in.cropdata.cdtmasterdata.model.AgriPhenophase;
import in.cropdata.cdtmasterdata.model.AgriStandardQuantityChart;
import in.cropdata.cdtmasterdata.model.AgriStandardQuantityChartMissing;
import in.cropdata.cdtmasterdata.repository.AgriCommodityRepositoy;
import in.cropdata.cdtmasterdata.repository.AgriHealthMissingRepository;
import in.cropdata.cdtmasterdata.repository.AgriHealthParameterRepository;
import in.cropdata.cdtmasterdata.repository.AgriHealthRepository;
import in.cropdata.cdtmasterdata.repository.AgriPhenophaseRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AgriHealthService {
	
	@Autowired
	private AgriHealthRepository agriHealthRepository;
	
	@Autowired
	private AgriCommodityRepositoy commodityRepositoy;

	@Autowired
	private AgriPhenophaseRepository agriPhenophaseRepository;
	
	@Autowired
	private AgriHealthParameterRepository agriHealthParameterRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;
	
	@Autowired
	private AgriHealthMissingRepository agriHealthMissingRepository;

	public List<AgriHealth> getAllAgriAgriHealth() {

		try {
			List<AgriHealth> HealthList = agriHealthRepository.findAll();

			for (AgriHealth Health : HealthList) {

				Health = getData(Health);

			} // for
			return HealthList;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public Page<AgriHealthInfDto> getAllAgriHealthPaginated(int page, int size, String searchText, int isValid,
			String missing) {

		try {
			searchText = "%" + searchText + "%";

//		System.out.println("searchText--> " + searchText);
			Page<AgriHealthInfDto> healthList;
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			if ("0".equals(missing)) {
				if (isValid == 0) {
					healthList = agriHealthRepository.getAgriHealthInvalidated(sortedByIdDesc, searchText);
				} else {
					healthList = agriHealthRepository.getAgriHealth(sortedByIdDesc, searchText);
				}
			} else {
				if (isValid == 0) {
					healthList = agriHealthRepository.getAgriHealthMissingInvalidated(sortedByIdDesc, searchText);
				} else {
					healthList = agriHealthRepository.getAgriHealthMissing(sortedByIdDesc, searchText);
				}
			}

			return healthList;
		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriHealthPaginated

	private AgriHealth getData(AgriHealth health) {
		try {
			Optional<AgriCommodity> foundCommodity = commodityRepositoy.findById(health.getCommodityId());

			if (foundCommodity.isPresent()) {
				AgriCommodity commodity = foundCommodity.get();
				health.setCommodity(commodity.getName());
			}

			Optional<AgriPhenophase> foundPhenophase = agriPhenophaseRepository
					.findById(health.getPhenophaseId());

			if (foundPhenophase.isPresent()) {
				AgriPhenophase phenophase = foundPhenophase.get();
				health.setPhenophase(phenophase.getName());
			}
			
			Optional<AgriHealthParameter> foundHealthParameter = agriHealthParameterRepository.findById(health.getHealthParameterId());

			if(foundHealthParameter.isPresent()) {
				AgriHealthParameter healthParameter = foundHealthParameter.get();
				health.setHealthParameter(healthParameter.getName());
			}
			return health;

		} catch (Exception e) {
			throw e;
		}

	}

	public ResponseMessage addAgriHealth(AgriHealth agriHealth) {

		try {

			agriHealth = agriHealthRepository.save(agriHealth);

			approvalUtil.addRecord(DBConstants.TBL_AGRI_HEALTH, agriHealth.getId());

			return responseMessageUtil.sendResponse(true, "Agri-Health" + APIConstants.RESPONSE_ADD_SUCCESS,
					"");

		} catch (Exception e) {

			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAgriHealth

	public ResponseMessage updateAgriHealthById(int id, AgriHealth agriHealth) {

		try {
			Optional<AgriHealth> foundHealth = agriHealthRepository.findById(id);

			if (foundHealth.isPresent()) {

				AgriHealth update = foundHealth.get();

				if (agriHealth.getCommodityId() > 0) {
					update.setCommodityId(agriHealth.getCommodityId());
				}

				if (agriHealth.getPhenophaseId() > 0) {
					update.setPhenophaseId(agriHealth.getPhenophaseId());
				}
				
				if (agriHealth.getHealthParameterId() > 0) {
					update.setHealthParameterId(agriHealth.getHealthParameterId());
				}
				
				if (agriHealth.getSpecification() != null) {
					update.setSpecification(agriHealth.getSpecification());
				}
				
				if (agriHealth.getStatus() != null) {
					update.setStatus(agriHealth.getStatus());
				}
				
				agriHealth = agriHealthRepository.save(update);

				approvalUtil.updateRecord(DBConstants.TBL_AGRI_HEALTH, agriHealth.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Health" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Health" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// updateAgriHealthById

	public ResponseMessage primaryApproveById(int id) {

		try {
			Optional<AgriHealth> foundHealth = agriHealthRepository.findById(id);

			if (foundHealth.isPresent()) {

				AgriHealth agriHealth = foundHealth.get();
				agriHealth.setStatus(APIConstants.STATUS_APPROVED);

				agriHealth = agriHealthRepository.save(agriHealth);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_HEALTH, agriHealth.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Health" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Health" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {

		try {
			Optional<AgriHealth> foundHealth = agriHealthRepository.findById(id);

			if (foundHealth.isPresent()) {

				AgriHealth agriHealth = foundHealth.get();
				agriHealth.setStatus(APIConstants.STATUS_ACTIVE);

				agriHealth = agriHealthRepository.save(agriHealth);

				approvalUtil.finalApprove(DBConstants.TBL_AGRI_HEALTH, agriHealth.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Health" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Health" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproverById

	public ResponseMessage deleteAgriHealthById(int id) {
		try {
			Optional<AgriHealth> foundHealth = agriHealthRepository.findById(id);
			if (foundHealth.isPresent()) {

				AgriHealth agriHealth = foundHealth.get();
				agriHealth.setStatus(APIConstants.STATUS_DELETED);

				agriHealth = agriHealthRepository.save(agriHealth);

				approvalUtil.delete(DBConstants.TBL_AGRI_HEALTH, agriHealth.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Health" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-Health" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteAgriHealthById

	public AgriHealth findAgriHealthById(int id) {
		try {
			Optional<AgriHealth> foundHealth = agriHealthRepository.findById(id);
			if (foundHealth.isPresent()) {
				return foundHealth.get();
			} else {
				throw new DoesNotExistException("Agri-Health" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findAgriHealthById
	
	@Transactional
	public ResponseMessage moveToMaster(int id) {

		try {
			Optional<AgriHealthMissing> foundAgriHealthMissing = agriHealthMissingRepository.findById(id);

			if (foundAgriHealthMissing.isPresent()) {
				AgriHealth agriHealth = new AgriHealth();
				AgriHealthMissing agriHealthMissing = foundAgriHealthMissing.get();
				
				agriHealth.setId(agriHealthMissing.getId());
				agriHealth.setCommodityId(agriHealthMissing.getCommodityId());
				agriHealth.setPhenophaseId(agriHealthMissing.getPhenophaseId());
				agriHealth.setHealthParameterId(agriHealthMissing.getHealthParameterId());
				agriHealth.setSpecification(agriHealthMissing.getSpecification());
				agriHealth.setStatus(agriHealthMissing.getStatus());
				
				AgriHealth agriHelth = agriHealthRepository.save(agriHealth);
//				
				agriHealthMissingRepository.deleteById(id);

				approvalUtil.addRecord(DBConstants.TBL_AGRI_HEALTH, agriHelth.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Health-Missing" + APIConstants.RESPONSE_MOVE_TO_MASTER_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Health-Missing" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
		
	}
	public ResponseMessage rejectAgriHealthById(int id) {
		try {
			Optional<AgriHealth> foundHealth = agriHealthRepository.findById(id);
			if (foundHealth.isPresent()) {

				AgriHealth agriHealth = foundHealth.get();
				agriHealth.setStatus(APIConstants.STATUS_REJECTED);

				agriHealth = agriHealthRepository.save(agriHealth);

				approvalUtil.delete(DBConstants.TBL_AGRI_HEALTH, agriHealth.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Health" + APIConstants.RESPONSE_REJECT_SUCCESS + id, "");
			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-Health" + APIConstants.RESPONSE_REJECT_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}
}
