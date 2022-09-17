package in.cropdata.cdtmasterdata.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.constants.DBConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriVarietyStressInfDto;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.AgriCommodity;
import in.cropdata.cdtmasterdata.model.AgriDistrictCommodityStress;
import in.cropdata.cdtmasterdata.model.AgriStress;
import in.cropdata.cdtmasterdata.model.AgriStressType;
import in.cropdata.cdtmasterdata.model.AgriVariety;
import in.cropdata.cdtmasterdata.model.AgriVarietyResistantStress;
import in.cropdata.cdtmasterdata.model.AgriVarietyStress;
import in.cropdata.cdtmasterdata.model.AgriVarietyStressMissing;
import in.cropdata.cdtmasterdata.model.AgriVarietySusceptibleStress;
import in.cropdata.cdtmasterdata.model.AgriVarietyTolerantStress;
import in.cropdata.cdtmasterdata.repository.AgriCommodityRepositoy;
import in.cropdata.cdtmasterdata.repository.AgriDistrictCommodityStressRepository;
import in.cropdata.cdtmasterdata.repository.AgriStressRepository;
import in.cropdata.cdtmasterdata.repository.AgriStressTypeRepository;
import in.cropdata.cdtmasterdata.repository.AgriVarietyRepository;
import in.cropdata.cdtmasterdata.repository.AgriVarietyResistantStressRepository;
import in.cropdata.cdtmasterdata.repository.AgriVarietyStressMissingRepository;
import in.cropdata.cdtmasterdata.repository.AgriVarietyStressRepository;
import in.cropdata.cdtmasterdata.repository.AgriVarietySusceptibleStressRepository;
import in.cropdata.cdtmasterdata.repository.AgriVarietyTolerantStressRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AgriVarietyStressService {

	@Autowired
	private AgriVarietyStressRepository agriVarietyStressRepository;
	
	@Autowired
	private AgriVarietyStressMissingRepository agriVarietyStressMissingRepository;
	
	@Autowired
	private AgriStressRepository stressRepository;

	@Autowired
	private AgriCommodityRepositoy agriCommodityRepository;

	@Autowired
	private AgriVarietyRepository agriVarietyRepository;

	@Autowired
	private AgriStressTypeRepository agriStressTypeRepository;

	@Autowired
	private AgriVarietyResistantStressRepository agriVarietyResistantStressRepository;

	@Autowired
	private AgriVarietyTolerantStressRepository agriVarietyTolerantStressRepository;

	@Autowired
	private AgriVarietySusceptibleStressRepository agriVarietySusceptibleStressRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<AgriVarietyStress> getAllAgriVarietyStress() {
		List<AgriVarietyStress> list = new ArrayList<AgriVarietyStress>();
		try {

			List<AgriVarietyStress> list1 = agriVarietyStressRepository.findAll();

			for (AgriVarietyStress agriVarietyStress : list1) {
				agriVarietyStress = getData(agriVarietyStress);
			} // for

			return list;

		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriVarietyStress

	private AgriVarietyStress getData(AgriVarietyStress agriVarietyStress) {

		Optional<AgriCommodity> commodity = agriCommodityRepository.findById(agriVarietyStress.getCommodityId());
		if (commodity.isPresent()) {
			agriVarietyStress.setCommodity(commodity.get().getName());
		}

		Optional<AgriVariety> variety = agriVarietyRepository.findById(agriVarietyStress.getVarietyId());
		if (variety.isPresent()) {
			agriVarietyStress.setVariety(variety.get().getName());
		}

		Optional<AgriStressType> stressType = agriStressTypeRepository.findById(agriVarietyStress.getStressTypeId());
		if (stressType.isPresent()) {
			agriVarietyStress.setStressType(stressType.get().getName());
		}

		List<AgriVarietyResistantStress> resistantStressList = agriVarietyResistantStressRepository
				.findByVarietyStressID(agriVarietyStress.getId());
		List<AgriVarietySusceptibleStress> susceptibleStressList = agriVarietySusceptibleStressRepository
				.findByVarietyStressID(agriVarietyStress.getId());
		List<AgriVarietyTolerantStress> tolerantStressList = agriVarietyTolerantStressRepository
				.findByVarietyStressID(agriVarietyStress.getId());

		List<AgriStress> resStressList = new ArrayList<AgriStress>();
		List<AgriStress> susStressList = new ArrayList<AgriStress>();
		List<AgriStress> tolStressList = new ArrayList<AgriStress>();

		if (resistantStressList != null && resistantStressList.size() > 0) {
			for (AgriVarietyResistantStress restStress : resistantStressList) {
				Optional<AgriStress> stress = stressRepository.findById(restStress.getStressID());
				if (stress.isPresent()) {
					resStressList.add(stress.get());
				}
			} // for resStress
		}

		agriVarietyStress.setResistantStress(resStressList);

		if (susceptibleStressList != null && susceptibleStressList.size() > 0) {
			for (AgriVarietySusceptibleStress restStress : susceptibleStressList) {
				Optional<AgriStress> stress = stressRepository.findById(restStress.getStressID());
				if (stress.isPresent()) {
					susStressList.add(stress.get());
				}
			} // for resStress
		}

		agriVarietyStress.setSusceptibleStress(susStressList);

		if (tolerantStressList != null && tolerantStressList.size() > 0) {
			for (AgriVarietyTolerantStress restStress : tolerantStressList) {
				Optional<AgriStress> stress = stressRepository.findById(restStress.getStressID());
				if (stress.isPresent()) {
					tolStressList.add(stress.get());
				}
			} // for resStress
		}

		agriVarietyStress.setTolerantStress(tolStressList);

		return agriVarietyStress;
	}// getData

	public Page<AgriVarietyStressInfDto> getAllAgriVarietyStressPaginated(int page, int size, String searchText, int isValid,String missing) {

		try {
			searchText = "%" + searchText + "%";
//			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").ascending());
			Pageable sortedByIdDesc = PageRequest.of(page, size);

			Page<AgriVarietyStressInfDto> agriVarietyStressInfDtos;

			if("0".equals(missing)) {
			if (isValid == 0) {
				agriVarietyStressInfDtos = agriVarietyStressRepository.getAgrivarietyStressInvalidated(sortedByIdDesc, searchText);
			} else {
				agriVarietyStressInfDtos = agriVarietyStressRepository.getAgrivarietyStress(sortedByIdDesc, searchText);
			}
			}else {
				if (isValid == 0) {
					agriVarietyStressInfDtos = agriVarietyStressRepository.getAgrivarietyStressMissingInvalidated(sortedByIdDesc, searchText);
				} else {
					
					agriVarietyStressInfDtos = agriVarietyStressRepository.getAgrivarietyStressMissing(sortedByIdDesc, searchText);
				}
			}

			return agriVarietyStressInfDtos;
		} catch (Exception e) {
			throw e;
		}
	}//getAllAgriCommodityPeginated
	

	public ResponseMessage addAgriVarietyStress(AgriVarietyStress agriVarietyStress) {

		try {

			AgriVarietyStress agriVarietyStressSaved = agriVarietyStressRepository.save(agriVarietyStress);

			List<AgriVarietyResistantStress> resistantStressList = new ArrayList<AgriVarietyResistantStress>();
			List<AgriVarietySusceptibleStress> susceptibleStressList = new ArrayList<AgriVarietySusceptibleStress>();
			List<AgriVarietyTolerantStress> tolerantStressList = new ArrayList<AgriVarietyTolerantStress>();

			if (agriVarietyStress.getResistantStress().size() > 0
					&& !agriVarietyStress.getResistantStress().isEmpty()) {

				for (AgriStress stress : agriVarietyStress.getResistantStress()) {

					if (stress != null && stress.getId() > 0) {

						AgriVarietyResistantStress resStress = new AgriVarietyResistantStress();
						resStress.setVarietyStressID(agriVarietyStressSaved.getId());
						resStress.setStressID(stress.getId());
						resistantStressList.add(resStress);

					} // if id greater than 0
				} // for
				agriVarietyResistantStressRepository.saveAll(resistantStressList);
			} // if

			if (agriVarietyStress.getSusceptibleStress().size() > 0
					&& !agriVarietyStress.getResistantStress().isEmpty()) {

				for (AgriStress stress : agriVarietyStress.getSusceptibleStress()) {

					if (stress != null && stress.getId() > 0) {

						AgriVarietySusceptibleStress resStress = new AgriVarietySusceptibleStress();
						resStress.setVarietyStressID(agriVarietyStress.getId());
						resStress.setStressID(stress.getId());
						susceptibleStressList.add(resStress);

					} // if id greater than 0
				}
				agriVarietySusceptibleStressRepository.saveAll(susceptibleStressList);
			}
			if (agriVarietyStress.getTolerantStress().size() > 0 && !agriVarietyStress.getTolerantStress().isEmpty()) {

				for (AgriStress stress : agriVarietyStress.getTolerantStress()) {

					if (stress != null && stress.getId() > 0) {

						AgriVarietyTolerantStress resStress = new AgriVarietyTolerantStress();
						resStress.setVarietyStressID(agriVarietyStress.getId());
						resStress.setStressID(stress.getId());
						tolerantStressList.add(resStress);
					}
				}
				agriVarietyTolerantStressRepository.saveAll(tolerantStressList);
			}

			approvalUtil.addRecord(DBConstants.TBL_AGRI_VARIETY_STRESS, agriVarietyStress.getId());

			return responseMessageUtil.sendResponse(true, "Agri-VarietyStress" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAllAgriVarietyStress

	public ResponseMessage updateAgriVarietyStressById(int id, AgriVarietyStress agriVarietyStress) {
		try {
			Optional<AgriVarietyStress> foundVareity = agriVarietyStressRepository.findById(id);

			if (foundVareity.isPresent()) {

				AgriVarietyStress agriVarietyUpdate = foundVareity.get();

				if (agriVarietyStress.getCommodityId() > 0) {
					agriVarietyUpdate.setCommodityId(agriVarietyStress.getCommodityId());
				}
				if (agriVarietyStress.getRegionId() > 0) {
					agriVarietyUpdate.setRegionId(agriVarietyStress.getRegionId());
				}
				if (agriVarietyStress.getStateCode() > 0) {
					agriVarietyUpdate.setStateCode(agriVarietyStress.getStateCode());
				}
				if (agriVarietyStress.getDescription() != null) {
					agriVarietyUpdate.setDescription(agriVarietyStress.getDescription());
				}
				if (agriVarietyStress.getStressTypeId() > 0) {
					agriVarietyUpdate.setStressTypeId(agriVarietyStress.getStressTypeId());
				}
				if (agriVarietyStress.getVarietyId() > 0) {
					agriVarietyUpdate.setVarietyId(agriVarietyStress.getVarietyId());
				}
				if (agriVarietyStress.getUpdatedAt() != null) {
					agriVarietyUpdate.setUpdatedAt(agriVarietyStress.getUpdatedAt());
				}
				if (agriVarietyStress.getStatus() != null) {
					agriVarietyUpdate.setStatus(agriVarietyStress.getStatus());
				}

				List<AgriVarietyResistantStress> resStressList = agriVarietyResistantStressRepository
						.findByVarietyStressID(id);
				List<AgriVarietySusceptibleStress> susStressList = agriVarietySusceptibleStressRepository
						.findByVarietyStressID(id);
				List<AgriVarietyTolerantStress> tolStressList = agriVarietyTolerantStressRepository
						.findByVarietyStressID(id);

				if (resStressList.size() > 0 && !resStressList.isEmpty()) {
					agriVarietyResistantStressRepository.deleteAll(resStressList);
				}
				if (susStressList.size() > 0 && !susStressList.isEmpty()) {
					agriVarietySusceptibleStressRepository.deleteAll(susStressList);
				}
				if (tolStressList.size() > 0 && !tolStressList.isEmpty()) {
					agriVarietyTolerantStressRepository.deleteAll(tolStressList);
				}

				List<AgriVarietyResistantStress> resistantStressList = new ArrayList<AgriVarietyResistantStress>();
				List<AgriVarietySusceptibleStress> susceptibleStressList = new ArrayList<AgriVarietySusceptibleStress>();
				List<AgriVarietyTolerantStress> tolerantStressList = new ArrayList<AgriVarietyTolerantStress>();

				if (agriVarietyStress.getResistantStress().size() > 0
						&& !agriVarietyStress.getResistantStress().isEmpty()) {

					for (AgriStress stress : agriVarietyStress.getResistantStress()) {

						if (stress != null && stress.getId() > 0) {

							AgriVarietyResistantStress resStress = new AgriVarietyResistantStress();
							resStress.setVarietyStressID(id);
							resStress.setStressID(stress.getId());
							resistantStressList.add(resStress);

						} // if id greater than 0
					} // for
					agriVarietyResistantStressRepository.saveAll(resistantStressList);
				} // if

				if (agriVarietyStress.getSusceptibleStress().size() > 0
						&& !agriVarietyStress.getResistantStress().isEmpty()) {

					for (AgriStress stress : agriVarietyStress.getSusceptibleStress()) {

						if (stress != null && stress.getId() > 0) {

							AgriVarietySusceptibleStress resStress = new AgriVarietySusceptibleStress();
							resStress.setVarietyStressID(id);
							resStress.setStressID(stress.getId());
							susceptibleStressList.add(resStress);

						} // if id greater than 0
					}
					agriVarietySusceptibleStressRepository.saveAll(susceptibleStressList);
				}
				if (agriVarietyStress.getTolerantStress().size() > 0
						&& !agriVarietyStress.getTolerantStress().isEmpty()) {

					for (AgriStress stress : agriVarietyStress.getTolerantStress()) {

						if (stress != null && stress.getId() > 0) {

							AgriVarietyTolerantStress resStress = new AgriVarietyTolerantStress();
							resStress.setVarietyStressID(id);
							resStress.setStressID(stress.getId());
							tolerantStressList.add(resStress);
						}
					}
					agriVarietyTolerantStressRepository.saveAll(tolerantStressList);
				}

				agriVarietyStressRepository.save(agriVarietyUpdate);

				approvalUtil.updateRecord(DBConstants.TBL_AGRI_VARIETY_STRESS, agriVarietyStress.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-VarietyStress" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-VarietyStress" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (

		Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateAgriVarietyStressById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<AgriVarietyStress> foundAgriVariety = agriVarietyStressRepository.findById(id);

			if (foundAgriVariety.isPresent()) {

				AgriVarietyStress verietyStress = foundAgriVariety.get();
				verietyStress.setStatus(APIConstants.STATUS_APPROVED);

				verietyStress = agriVarietyStressRepository.save(verietyStress);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_VARIETY_STRESS, verietyStress.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-VarietyStress" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-VarietyStress" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<AgriVarietyStress> foundAgriVariety = agriVarietyStressRepository.findById(id);

			if (foundAgriVariety.isPresent()) {

				AgriVarietyStress verietyStress = foundAgriVariety.get();
				verietyStress.setStatus(APIConstants.STATUS_ACTIVE);

				verietyStress = agriVarietyStressRepository.save(verietyStress);

				approvalUtil.finalApprove(DBConstants.TBL_AGRI_VARIETY_STRESS, verietyStress.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-VarietyStress" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-VarietyStress" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteAgriVarietyStressById(int id) {
		try {
			Optional<AgriVarietyStress> foundAgriVariety = agriVarietyStressRepository.findById(id);

			if (foundAgriVariety.isPresent()) {

				AgriVarietyStress verietyStress = foundAgriVariety.get();
				verietyStress.setStatus(APIConstants.STATUS_DELETED);

				verietyStress = agriVarietyStressRepository.save(verietyStress);

				approvalUtil.delete(DBConstants.TBL_AGRI_VARIETY_STRESS, verietyStress.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-VarietyStress" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-VarietyStress" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteAgriVarietyStressById]

	public ResponseMessage rejectById(int id) {
		try {
			Optional<AgriVarietyStress> foundAgriVariety = agriVarietyStressRepository.findById(id);

			if (foundAgriVariety.isPresent()) {

				AgriVarietyStress verietyStress = foundAgriVariety.get();
				verietyStress.setStatus(APIConstants.STATUS_REJECTED);

				verietyStress = agriVarietyStressRepository.save(verietyStress);

				approvalUtil.delete(DBConstants.TBL_AGRI_VARIETY_STRESS, verietyStress.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-VarietyStress" + APIConstants.RESPONSE_REJECT_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-VarietyStress" + APIConstants.RESPONSE_REJECT_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

	public AgriVarietyStress findAgriVarietyStressById(int id) {

		try {
			Optional<AgriVarietyStress> foundVarietyStress = agriVarietyStressRepository.findById(id);
			if (foundVarietyStress.isPresent()) {

				AgriVarietyStress agriVarietyStress = foundVarietyStress.get();
				agriVarietyStress = getData(agriVarietyStress);

				return agriVarietyStress;
			} else {
				throw new DoesNotExistException("Agri-VarietyStress" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findAgriVarietyStressById
	
	@Transactional
	public ResponseMessage moveToMaster(int id) {
		try {
			Optional<AgriVarietyStressMissing> foundAgriVarietyStressMissing = agriVarietyStressMissingRepository.findById(id);

			if (foundAgriVarietyStressMissing.isPresent()) {
				AgriVarietyStress agriVarietyStress = new AgriVarietyStress();
				AgriVarietyStressMissing agriVarietyStressMissing = foundAgriVarietyStressMissing.get();
				
				agriVarietyStress.setStateCode(agriVarietyStressMissing.getStateCode());
				agriVarietyStress.setRegionId(agriVarietyStressMissing.getRegionId());
				agriVarietyStress.setCommodityId(agriVarietyStressMissing.getCommodityId());
				agriVarietyStress.setStressTypeId(agriVarietyStressMissing.getStressTypeId());
				agriVarietyStress.setVarietyId(agriVarietyStressMissing.getVarietyId());
				agriVarietyStress.setDescription(agriVarietyStressMissing.getDescription());
				agriVarietyStress.setStatus(agriVarietyStressMissing.getStatus());
				
				AgriVarietyStress savedAgriVarietyStress = agriVarietyStressRepository.save(agriVarietyStress);
				
				agriVarietyStressMissingRepository.deleteById(id);


				approvalUtil.addRecord(DBConstants.TBL_AGRI_VARIETY_STRESS, savedAgriVarietyStress.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-VarietyStress-Missing" + APIConstants.RESPONSE_MOVE_TO_MASTER_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-VarietyStress-Missing" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}
	
}// AgriVarietyStressService