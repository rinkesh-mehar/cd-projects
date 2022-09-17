package in.cropdata.cdtmasterdata.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.constants.DBConstants;
import in.cropdata.cdtmasterdata.dto.AgriAgrochemicalDTO;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriAgrochemicalInfDto;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriDistrictCommodityStressInfDto;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.AgriCommodityAgrochemical;
import in.cropdata.cdtmasterdata.model.AgriAgrochemicalStress;
import in.cropdata.cdtmasterdata.model.AgriDistrictCommodityStress;
import in.cropdata.cdtmasterdata.model.AgriStress;
import in.cropdata.cdtmasterdata.repository.AgriAgroChemicalTypeRepository;
import in.cropdata.cdtmasterdata.repository.AgriCommodityAgrochemicalRepository;
import in.cropdata.cdtmasterdata.repository.AgriAgrochemicalStressRepository;
import in.cropdata.cdtmasterdata.repository.AgriCommodityRepositoy;
import in.cropdata.cdtmasterdata.repository.AgriDistrictCommodityStressRepository;
import in.cropdata.cdtmasterdata.repository.AgriStressRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AgriCommodityAgrochemicalService {

	@Autowired
	private AgriCommodityAgrochemicalRepository agriCommodityAgrochemicalRepository;

	@Autowired
	private AgriAgroChemicalTypeRepository agriAgroChemicalTypeRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private AgriDistrictCommodityStressRepository stressRepository;
	
	@Autowired
	private AgriStressRepository agriStressRepository;

	@Autowired
	private AgriCommodityRepositoy agriCommodityRepositoy;

	@Autowired
	private AgriAgrochemicalStressRepository agriAgrochemicalStressRepository;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<AgriAgrochemicalDTO> getAllAgriAgrochemicalMaster() {
		try {
			List<AgriAgrochemicalDTO> agriAgrochemicalMasterList = agriCommodityAgrochemicalRepository.getAgriAgrochemicalList();

//			System.out.println(agriAgrochemicalMasterList);
//			for (AgriAgrochemicalMaster agriAgrochemicalMaster : agriAgrochemicalMasterList) {
//				Optional<AgriAgroChemicalType> foundAgroChemicalType = agriAgroChemicalTypeRepository
//						.findById(agriAgrochemicalMaster.getAgrochemicalTypeId());
//
//				if (foundAgroChemicalType.isPresent()) {
//					AgriAgroChemicalType agroChemicalType = foundAgroChemicalType.get();
//					agriAgrochemicalMaster.setAgrochemicalType(agroChemicalType.getName());
//				}
//
//				Optional<AgriCommodity> foundCommodity = agriCommodityRepositoy
//						.findById(agriAgrochemicalMaster.getCommodityId());
//
//				if(foundCommodity.isPresent()) {
//					AgriCommodity agricommodity = foundCommodity.get();
//					agriAgrochemicalMaster.setCommodity(agricommodity.getName());
//				}
//			} // for
			return agriAgrochemicalMasterList;
		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriAgrochemicalMaster
	
	public Page<AgriAgrochemicalInfDto> getAllAgrochemicalPaginated(int page, int size, String  searchText, int isValid) {

		try {
			searchText = "%"+searchText+"%";
			
//		System.out.println("searchText--> " + searchText);
		
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").ascending());
			Page<AgriAgrochemicalInfDto> agroList;
			if (isValid == 0) {
				agroList = agriCommodityAgrochemicalRepository.getAllAgrochemicalInvalidated(sortedByIdDesc,searchText);
			} else {
				agroList = agriCommodityAgrochemicalRepository.getAllAgrochemical(sortedByIdDesc,searchText);
			}

			return agroList;
		} catch (Exception e) {
			throw e;
		}
	}// getAllAgrochemicalPaginated

	public ResponseMessage addAgriAgrochemicalMaster(AgriCommodityAgrochemical agriCommodityAgrochemical) {

		try {
			AgriCommodityAgrochemical agriCommodityAgrochemicalSaved = agriCommodityAgrochemicalRepository
					.save(agriCommodityAgrochemical);

			// -------------Save StressNameList----------------------------
			List<AgriAgrochemicalStress> stressNameList = new ArrayList<>();
			if (agriCommodityAgrochemical.getStressNameList().size() > 0
					&& !agriCommodityAgrochemical.getStressNameList().isEmpty()) {
				for (AgriStress stress : agriCommodityAgrochemical.getStressNameList()) {
					if (stress != null && stress.getId() > 0) {
						AgriAgrochemicalStress resStress = new AgriAgrochemicalStress();
						resStress.setAgrochemicalId(agriCommodityAgrochemicalSaved.getId());
						resStress.setStressID(stress.getId());
						stressNameList.add(resStress);
					} // if id greater than 0
				} // for
				agriAgrochemicalStressRepository.saveAll(stressNameList);
			} // if

			// ---------------Save StressNameList--------------------------

			approvalUtil.addRecord(DBConstants.TBL_AGRI_COMMODITY_AGROCHEMICAL, agriCommodityAgrochemical.getId());

			return responseMessageUtil.sendResponse(true, "Agri-AgrochemicalMaster" + APIConstants.RESPONSE_ADD_SUCCESS,
					"");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAllAgriAgrochemicalMaster

	public ResponseMessage updateAgriAgrochemicalMasterById(int id, AgriCommodityAgrochemical agriCommodityAgrochemical) {

		try {
			Optional<AgriCommodityAgrochemical> foundVareity = agriCommodityAgrochemicalRepository.findById(id);
			if (foundVareity.isPresent()) {

				agriCommodityAgrochemical.setId(id);
				agriCommodityAgrochemicalRepository.save(agriCommodityAgrochemical);
				// -------------Save StressNameList----------------------------
				List<AgriAgrochemicalStress> stressNameList = new ArrayList<AgriAgrochemicalStress>();
				if (agriCommodityAgrochemical.getStressNameList().size() > 0
						&& !agriCommodityAgrochemical.getStressNameList().isEmpty()) {
					for (AgriStress stress : agriCommodityAgrochemical.getStressNameList()) {
						if (stress != null && stress.getId() > 0) {
							AgriAgrochemicalStress resStress = new AgriAgrochemicalStress();
							resStress.setAgrochemicalId(agriCommodityAgrochemical.getId());
							resStress.setStressID(stress.getId());
							stressNameList.add(resStress);
						} // if id greater than 0
					} // for
					agriAgrochemicalStressRepository.saveAll(stressNameList);
				} // if

				approvalUtil.updateRecord(DBConstants.TBL_AGRI_COMMODITY_AGROCHEMICAL, agriCommodityAgrochemical.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-AgrochemicalMaster" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-AgrochemicalMaster" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateAgriAgrochemicalMasterById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<AgriCommodityAgrochemical> foundAgroChemical = agriCommodityAgrochemicalRepository.findById(id);
			if (foundAgroChemical.isPresent()) {

				AgriCommodityAgrochemical agriCommodityAgrochemical = foundAgroChemical.get();
				agriCommodityAgrochemical.setStatus(APIConstants.STATUS_APPROVED);

				agriCommodityAgrochemical = agriCommodityAgrochemicalRepository.save(agriCommodityAgrochemical);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_COMMODITY_AGROCHEMICAL, agriCommodityAgrochemical.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-AgrochemicalMaster" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-AgrochemicalMaster" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// primaryApproveById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<AgriCommodityAgrochemical> foundAgroChemical = agriCommodityAgrochemicalRepository.findById(id);
			if (foundAgroChemical.isPresent()) {

				AgriCommodityAgrochemical agriCommodityAgrochemical = foundAgroChemical.get();
				agriCommodityAgrochemical.setStatus(APIConstants.STATUS_REJECTED);

				agriCommodityAgrochemical = agriCommodityAgrochemicalRepository.save(agriCommodityAgrochemical);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_COMMODITY_AGROCHEMICAL, agriCommodityAgrochemical.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-AgrochemicalMaster" + APIConstants.RESPONSE_REJECT_SUCCESS, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-AgrochemicalMaster" + APIConstants.RESPONSE_REJECT_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<AgriCommodityAgrochemical> foundAgrochemical = agriCommodityAgrochemicalRepository.findById(id);

			if (foundAgrochemical.isPresent()) {

				AgriCommodityAgrochemical agroChemical = foundAgrochemical.get();
				agroChemical.setStatus(APIConstants.STATUS_ACTIVE);

				agroChemical = agriCommodityAgrochemicalRepository.save(agroChemical);

				approvalUtil.finalApprove(DBConstants.TBL_AGRI_COMMODITY_AGROCHEMICAL, agroChemical.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-AgrochemicalMaster" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");

			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-AgrochemicalMaster" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}

	public ResponseMessage deleteAgriAgrochemicalMasterById(int id) {
		try {
			Optional<AgriCommodityAgrochemical> foundAgroChemical = agriCommodityAgrochemicalRepository.findById(id);
			if (foundAgroChemical.isPresent()) {

				AgriCommodityAgrochemical agrochemicalMaster = foundAgroChemical.get();
				agrochemicalMaster.setStatus(APIConstants.STATUS_DELETED);

				agrochemicalMaster = agriCommodityAgrochemicalRepository.save(agrochemicalMaster);

				approvalUtil.delete(DBConstants.TBL_AGRI_COMMODITY_AGROCHEMICAL, agrochemicalMaster.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-AgrochemicalMaster" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-AgrochemicalMaster" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// deleteAgriAgrochemicalMasterById
	
	
	public List<AgriCommodityAgrochemical> getAllAgriStressByStressType(int commodityId, int stressTypeId) {
		return agriCommodityAgrochemicalRepository.findAllByStressTypeId(commodityId,stressTypeId);
	}

	public AgriCommodityAgrochemical findAgriAgrochemicalMasterById(int id) {
		try {
			Optional<AgriCommodityAgrochemical> foundAgrochemicalMaster = agriCommodityAgrochemicalRepository.findById(id);
			if (foundAgrochemicalMaster.isPresent()) {

				AgriCommodityAgrochemical agriCommodityAgrochemical = foundAgrochemicalMaster.get();

				agriCommodityAgrochemical = getData(agriCommodityAgrochemical);

				return agriCommodityAgrochemical;
			} else {
				throw new DoesNotExistException("Agri-AgrochemicalMaster" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findAgriAgrochemicalMasterById

	private AgriCommodityAgrochemical getData(AgriCommodityAgrochemical agriCommodityAgrochemical) {
		// TODO Auto-generated method stub

		List<AgriAgrochemicalStress> StressList = agriAgrochemicalStressRepository
				.findAllByAgrochemicalId(agriCommodityAgrochemical.getId());

		Set<AgriStress> stressList = new HashSet<>();

		for (AgriAgrochemicalStress agStress : StressList) {

			Optional<AgriStress> stress = agriStressRepository.findById(agStress.getStressID());
			if (stress.isPresent()) {
				stressList.add(stress.get());
			}
		}
		agriCommodityAgrochemical.setStressNameList(stressList);
		return agriCommodityAgrochemical;
	}

	public List<AgriAgrochemicalInfDto> findAgriAgrochemicalMasterByAgrochemicalTypeId(int id) {
		try {
			List<AgriAgrochemicalInfDto> agriAgrochemicalMasterList = agriCommodityAgrochemicalRepository
					.findAllByAgrochemicalTypeId(id);

			return agriAgrochemicalMasterList;
		} catch (Exception e) {
			throw e;
		}
	}// findAgriAgrochemicalMasterByAgrochemicalTypeId

	public List<AgriCommodityAgrochemical> findAgriAgrochemicalMasterByCommodityId(int id) {
		try {
			List<AgriCommodityAgrochemical> agriCommodityAgrochemicalList = agriCommodityAgrochemicalRepository
					.findAllByCommodityId(id);

			return agriCommodityAgrochemicalList;
		} catch (Exception e) {
			throw e;
		}
	}// findAgriAgrochemicalMasterByCommodityId

	public List<AgriDistrictCommodityStressInfDto> getByCommodityIdAndStressTypeId(int commodityId, int stressTypeId) {

		try {
//			List<AgriCommodityStress> agriCommodityStressList = stressRepository.findByCommodityIdAndStressTypeId(commodityId,
//					stressTypeId);
			
			List<AgriDistrictCommodityStressInfDto> agriCommodityStressList = stressRepository.getStressByCommodityIdAndStressTypeId(commodityId,
					stressTypeId);

			return agriCommodityStressList;
		} catch (Exception e) {
			throw e;
		}
	}// getByCommodityIdAndStressTypeId

}// AgriAgrochemicalMasterService