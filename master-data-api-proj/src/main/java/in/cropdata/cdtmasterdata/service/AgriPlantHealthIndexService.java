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
import in.cropdata.cdtmasterdata.dto.interfaces.AgriFieldActivityInfDto;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriHealthInfDto;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.AgriPlantHealthIndex;
import in.cropdata.cdtmasterdata.model.AgriPlantHealthIndexMissing;
import in.cropdata.cdtmasterdata.model.vo.AgriPlantHealthIndexForPhenophaseVo;
import in.cropdata.cdtmasterdata.model.vo.AgriPlantHealthIndexForVarietyVo;
import in.cropdata.cdtmasterdata.model.vo.AgriPlantHealthIndexVo;
import in.cropdata.cdtmasterdata.repository.AgriPlantHealthIndexMissingRepository;
import in.cropdata.cdtmasterdata.repository.AgriPlantHealthIndexRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AgriPlantHealthIndexService {

	@Autowired
	private AgriPlantHealthIndexRepository agriPlantHealthIndexRepository;
	
	@Autowired
	private AgriPlantHealthIndexMissingRepository agriPlantHealthIndexMissingRepository;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	@Autowired
	private AclHistoryUtil approvalUtil;

	public List<AgriPlantHealthIndex> getAllAgriPlantHealthIndexList() {
		try {
			List<AgriPlantHealthIndex> list = agriPlantHealthIndexRepository.findAll();
			return list;
		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriPlantHealthIndexList
	
	public Page<AgriPlantHealthIndexVo> getAgriPlantHealthIndexPagenatedList(int page, int size, String searchText,String missing) {
		try {
			searchText = "%" + searchText + "%";

//			System.out.println("searchText--> " + searchText);
				Page<AgriPlantHealthIndexVo> list;
				Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
				if("0".equals(missing)) {
					System.err.println("inisde if...");
			    list = agriPlantHealthIndexRepository.getAgriPlantHealthIndexPagenatedList(sortedByIdDesc, searchText);
				}else {
					System.err.println("inisde else...");
					list = agriPlantHealthIndexRepository.getAgriPlantHealthIndexPagenatedListMissing(sortedByIdDesc, searchText);
				}
			return list;
		} catch (Exception e) {
			throw e;
		}
	}

	public ResponseMessage addAllAgriPalntHealthIndex(AgriPlantHealthIndex agriPlantHealthIndex) {

		try {
			agriPlantHealthIndex = agriPlantHealthIndexRepository.save(agriPlantHealthIndex);

			approvalUtil.addRecord(DBConstants.TBL_AGRI_PLANT_HEALTH_INDEX, agriPlantHealthIndex.getId());

			return responseMessageUtil.sendResponse(true, "Agri-Plant-Health-Index" + APIConstants.RESPONSE_ADD_SUCCESS,
					"");

		} catch (Exception e) {

			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// addAllAgriPalntHealthIndex

	public ResponseMessage updateAgriPlantHealthIndexById(int id, AgriPlantHealthIndex AgriPlantHealthIndex) {

		try {
			Optional<AgriPlantHealthIndex> foundVareity = agriPlantHealthIndexRepository.findById(id);

			if (foundVareity.isPresent()) {
				AgriPlantHealthIndex.setId(id);

				AgriPlantHealthIndex = agriPlantHealthIndexRepository.save(AgriPlantHealthIndex);

				approvalUtil.updateRecord(DBConstants.TBL_AGRI_PLANT_HEALTH_INDEX, AgriPlantHealthIndex.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Plant-Health-Index" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Plant-Health-Index" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateAgriPlantHealthIndexById

	public ResponseMessage primaryApproveById(int id) {

		try {
			Optional<AgriPlantHealthIndex> foundAgriPlantHealthIndex = agriPlantHealthIndexRepository.findById(id);

			if (foundAgriPlantHealthIndex.isPresent()) {

				AgriPlantHealthIndex AgriPlantHealthIndex = foundAgriPlantHealthIndex.get();
				AgriPlantHealthIndex.setStatus(APIConstants.STATUS_APPROVED);

				AgriPlantHealthIndex = agriPlantHealthIndexRepository.save(AgriPlantHealthIndex);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_PLANT_HEALTH_INDEX, AgriPlantHealthIndex.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Plant-Health-Index" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Plant-Health-Index" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {

		try {
			Optional<AgriPlantHealthIndex> foundAgriPlantHealthIndex = agriPlantHealthIndexRepository.findById(id);

			if (foundAgriPlantHealthIndex.isPresent()) {

				AgriPlantHealthIndex AgriPlantHealthIndex = foundAgriPlantHealthIndex.get();
				AgriPlantHealthIndex.setStatus(APIConstants.STATUS_ACTIVE);

				AgriPlantHealthIndex = agriPlantHealthIndexRepository.save(AgriPlantHealthIndex);

				approvalUtil.finalApprove(DBConstants.TBL_AGRI_PLANT_HEALTH_INDEX, AgriPlantHealthIndex.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Plant-Health-Index" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Plant-Health-Index" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproverById

	public ResponseMessage deleteAgriPlantHealthIndexById(int id) {
		try {
			Optional<AgriPlantHealthIndex> found = agriPlantHealthIndexRepository.findById(id);
			if (found.isPresent()) {

				AgriPlantHealthIndex AgriPlantHealthIndex = found.get();
				AgriPlantHealthIndex.setStatus(APIConstants.STATUS_DELETED);

				AgriPlantHealthIndex = agriPlantHealthIndexRepository.save(AgriPlantHealthIndex);

				approvalUtil.delete(DBConstants.TBL_AGRI_PLANT_HEALTH_INDEX, AgriPlantHealthIndex.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Plant-Health-Index" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-Plant-Health-Index" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteAgriPlantHealthIndexById

	public AgriPlantHealthIndex findAgriPlantHealthIndexById(int id) {
		try {
			Optional<AgriPlantHealthIndex> foundPalntHealthIndex = agriPlantHealthIndexRepository.findById(id);
			if (foundPalntHealthIndex.isPresent()) {
				return foundPalntHealthIndex.get();
			} else {
				throw new DoesNotExistException("Agri-Plant-Health-Index" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findAgriPlantHealthIndexById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<AgriPlantHealthIndex> foundAgriPlantHealthIndex = agriPlantHealthIndexRepository.findById(id);

			if (foundAgriPlantHealthIndex.isPresent()) {

				AgriPlantHealthIndex AgriPlantHealthIndex = foundAgriPlantHealthIndex.get();
				AgriPlantHealthIndex.setStatus(APIConstants.STATUS_REJECTED);
				AgriPlantHealthIndex = agriPlantHealthIndexRepository.save(AgriPlantHealthIndex);

				approvalUtil.finalApprove(DBConstants.TBL_AGRI_PLANT_HEALTH_INDEX, AgriPlantHealthIndex.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Plant-Health-Index" + APIConstants.RESPONSE_REJECT_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "", "c" + APIConstants.RESPONSE_REJECT_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById
	
	public List<AgriPlantHealthIndexForVarietyVo> getVarietyListByCommodity(int commodityId) {

		try {
			List<AgriPlantHealthIndexForVarietyVo> varietyList = agriPlantHealthIndexRepository.getVarietyListByCommodity(commodityId);

			return varietyList;

		} catch (Exception e) {
			throw e;
		}
	}
	
	public List<AgriPlantHealthIndexForPhenophaseVo> getPhenophaseListByCommodityAndVariety(int commodityId,int varietyId) {

		try {
			List<AgriPlantHealthIndexForPhenophaseVo> phenophaseList = agriPlantHealthIndexRepository.getPhenophaseListByCommodityAndVariety(commodityId,varietyId);

			return phenophaseList;

		} catch (Exception e) {
			throw e;
		}
	}
	
	@Transactional
	public ResponseMessage moveToMaster(int id) {
		try {
			Optional<AgriPlantHealthIndexMissing> foundAgriPlantHealthIndexMissing = agriPlantHealthIndexMissingRepository.findById(id);

			if (foundAgriPlantHealthIndexMissing.isPresent()) {
				AgriPlantHealthIndex agriPlantHealthIndex = new AgriPlantHealthIndex();
				AgriPlantHealthIndexMissing agriPlantHealthIndexMissing = foundAgriPlantHealthIndexMissing.get();
				
				agriPlantHealthIndex.setName(agriPlantHealthIndexMissing.getName());
				agriPlantHealthIndex.setStateCode(agriPlantHealthIndexMissing.getStateCode());
				agriPlantHealthIndex.setCommodityID(agriPlantHealthIndexMissing.getCommodityID());
				agriPlantHealthIndex.setVarietyID(agriPlantHealthIndexMissing.getVarietyID());
				agriPlantHealthIndex.setPhenophaseID(agriPlantHealthIndexMissing.getPhenophaseID());
				agriPlantHealthIndex.setNormalValue(agriPlantHealthIndexMissing.getNormalValue());;
				agriPlantHealthIndex.setIdealValue(agriPlantHealthIndexMissing.getIdealValue());
				agriPlantHealthIndex.setStatus(agriPlantHealthIndexMissing.getStatus());

				AgriPlantHealthIndex savedAgriPlantHealthIndex = agriPlantHealthIndexRepository.save(agriPlantHealthIndex);
				
				agriPlantHealthIndexMissingRepository.deleteById(id);


				approvalUtil.addRecord(DBConstants.TBL_AGRI_PLANT_HEALTH_INDEX, savedAgriPlantHealthIndex.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Plant-Health-Index-Missing" + APIConstants.RESPONSE_MOVE_TO_MASTER_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Plant-Health-Index-Missing" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}

}
