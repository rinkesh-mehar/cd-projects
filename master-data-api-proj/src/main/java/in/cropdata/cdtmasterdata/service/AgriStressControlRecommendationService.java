package in.cropdata.cdtmasterdata.service;

import java.util.List;
import java.util.Optional;

import in.cropdata.cdtmasterdata.model.AgriCommodityPlantPart;
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
import in.cropdata.cdtmasterdata.dto.interfaces.AgriQuantityLossChartInfDto;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriStressControlRecommendationInfDto;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.AgriControlMeasures;
import in.cropdata.cdtmasterdata.model.AgriStressControlRecommendation;
import in.cropdata.cdtmasterdata.model.AgriStressControlRecommendationMissing;
import in.cropdata.cdtmasterdata.repository.AgriStressControlRecommendationMissingRepository;
import in.cropdata.cdtmasterdata.repository.AgriStressControlRecommendationRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AgriStressControlRecommendationService {

	@Autowired
	private AgriStressControlRecommendationRepository agriStressControlRecommendationRepository;
	
	@Autowired
	private AgriStressControlRecommendationMissingRepository agriStressControlRecommendationMissingRepository;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;
	
	@Autowired
	private AclHistoryUtil approvalUtil;

	public ResponseMessage addAgriStressControlRecommendation(
			AgriStressControlRecommendation agriStressControlRecommendation) {
		try {
			agriStressControlRecommendationRepository.save(agriStressControlRecommendation);

			return responseMessageUtil.sendResponse(true,
					"Stress Control Recommendation" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addStressControlRecommendation

	public List<AgriStressControlRecommendationInfDto> getAllAgriStressControlRecommendation() {
		return agriStressControlRecommendationRepository.getAgriStressRecommendation();
	}// getAllStressControlRecommendation
	
	public Page<AgriStressControlRecommendationInfDto> getAllAgriStressControlRecommendationPaginated(int page, int size, String  searchText, int isValid,String missing,String stateCode,String districtCode,String commodityId,String controlMeasureId,String stressId,String recomendationID,String agrochemicalId,String filter) {

		try {
			searchText = "%"+searchText+"%";
			
//		System.out.println("searchText--> " + searchText);
		
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").ascending());
			Page<AgriStressControlRecommendationInfDto> recommendationList = null;
			if("1".equals(filter)) {
				System.err.println("filter : " + filter + " stateCode : " + stateCode);
				if("".equals(stateCode)) {
					stateCode = null;
				}
				if("".equals(districtCode)) {
					districtCode = null;
				}
				if("".equals(commodityId)) {
					commodityId = null;
				}
				if("".equals(controlMeasureId)) {
					controlMeasureId = null;
				}
				if("".equals(stressId)) {
					stressId = null;
				}
				if("".equals(recomendationID)) {
					recomendationID = null;
				}
				if("".equals(agrochemicalId)) {
					agrochemicalId = null;
				}
				recommendationList = agriStressControlRecommendationRepository.getAgriStressControlRecommendationByMultiSearchFilters(sortedByIdDesc, stateCode,districtCode,commodityId,controlMeasureId,stressId,recomendationID,agrochemicalId);
				
			}else if("0".equals(missing)) {
			if (isValid == 0) {
				recommendationList = agriStressControlRecommendationRepository.getAgriStressControlRecommendationInvalidated(sortedByIdDesc, searchText);
			} else {
				System.err.println("sortedByIdDesc : " + sortedByIdDesc);
				recommendationList = agriStressControlRecommendationRepository.getAgriStressControlRecommendation(sortedByIdDesc,searchText);
			}
			}else {
				if (isValid == 0) {
					
					recommendationList = agriStressControlRecommendationRepository.getAgriStressControlRecommendationMissingInvalidated(sortedByIdDesc, searchText);
				} else {
					System.err.println("sortedByIdDesc missing : " + sortedByIdDesc);
					recommendationList = agriStressControlRecommendationRepository.getAgriStressControlRecommendationMissing(sortedByIdDesc,searchText);
				}
			}

			return recommendationList;
		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriStressControlRecommendationPaginated

	public ResponseMessage updateAgriStressControlRecommendationById(int id,
			AgriStressControlRecommendation agriStressControlRecommendation) {

		try {

			Optional<AgriStressControlRecommendation> foundGroup = agriStressControlRecommendationRepository
					.findById(id);

			if (foundGroup.isPresent()) {

				agriStressControlRecommendation.setId(id);
				agriStressControlRecommendationRepository.save(agriStressControlRecommendation);

				return responseMessageUtil.sendResponse(true,
						"Stress Control Recommendation" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");

			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Stress Control Recommendation" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateStressControlRecommendationById
	
	public ResponseMessage primaryApproveById(int id) {

		try {
			Optional<AgriStressControlRecommendation> foundAgriStressControlRecommendation = agriStressControlRecommendationRepository.findById(id);

			if (foundAgriStressControlRecommendation.isPresent()) {

				AgriStressControlRecommendation stressControlRecommendation = foundAgriStressControlRecommendation.get();
				stressControlRecommendation.setStatus(APIConstants.STATUS_APPROVED);

				stressControlRecommendation = agriStressControlRecommendationRepository.save(stressControlRecommendation);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_STRESS_CONTROL_RECOMMENDATION, stressControlRecommendation.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Stress Control Recommendation" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Stress Control Recommendation" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {

		try {
			Optional<AgriStressControlRecommendation> foundAgriStressControlRecommendation = agriStressControlRecommendationRepository.findById(id);

			if (foundAgriStressControlRecommendation.isPresent()) {

				AgriStressControlRecommendation stressControlRecommendation = foundAgriStressControlRecommendation.get();
				stressControlRecommendation.setStatus(APIConstants.STATUS_ACTIVE);

				stressControlRecommendation = agriStressControlRecommendationRepository.save(stressControlRecommendation);

				approvalUtil.finalApprove(DBConstants.TBL_AGRI_STRESS_CONTROL_RECOMMENDATION, stressControlRecommendation.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Stress Control Recommendation" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Stress Control Recommendation" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproverById

	public ResponseMessage deleteAgriStressControlRecommendationById(int id) {

		try {
			agriStressControlRecommendationRepository.deleteById(id);

			return responseMessageUtil.sendResponse(true,
					"Stress Control Recommendation" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "",
					"Stress Control Recommendation" + APIConstants.RESPONSE_DELETE_ERROR + id);
		}
	}// deleteStressControlRecommendationById

	public AgriStressControlRecommendation findAgriStressControlRecommendationById(int id) {
		try {
			Optional<AgriStressControlRecommendation> foundStressControlRecommendation = agriStressControlRecommendationRepository
					.findById(id);
			if (foundStressControlRecommendation.isPresent()) {
				return foundStressControlRecommendation.get();
			} else {
				throw new DoesNotExistException(
						"Stress Control Recommendation" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findStressControlRecommendationById


	/**
	 * @param id //rejected the stress control recommendation base on id
	 * @return        // return isSuccess, message and id response
	 */
	public ResponseMessage rejectById(int id)
	{

		try
		{
			Optional<AgriStressControlRecommendation> stressControlRecommendation = agriStressControlRecommendationRepository
					.findById(id);
			if (stressControlRecommendation.isPresent())
			{
				AgriStressControlRecommendation agriStressControlRecommendation = stressControlRecommendation.get();
				agriStressControlRecommendation.setStatus(APIConstants.STATUS_REJECTED);
				agriStressControlRecommendation = agriStressControlRecommendationRepository.save(agriStressControlRecommendation);
				approvalUtil.delete(DBConstants.TBL_AGRI_STRESS_CONTROL_RECOMMENDATION, agriStressControlRecommendation.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri_stress-control-recommendation" + APIConstants.RESPONSE_REJECT_SUCCESS + " " + id, "");
			} else
			{
				return responseMessageUtil.sendResponse(false, "",
						"Agri_stress-control-recommendation" + APIConstants.RESPONSE_REJECT_ERROR + " " + id);
			}
		} catch (Exception e)
		{
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}
	
	@Transactional
	public ResponseMessage moveToMaster(int id) {
		try {
			Optional<AgriStressControlRecommendationMissing> foundMissingAgriStressControlRecommendationMissing = agriStressControlRecommendationMissingRepository.findById(id);

			if (foundMissingAgriStressControlRecommendationMissing.isPresent()) {
				AgriStressControlRecommendation agriStressControlRecommendation = new AgriStressControlRecommendation();
				AgriStressControlRecommendationMissing regionalAgriStressControlRecommendationMissing = foundMissingAgriStressControlRecommendationMissing.get();
				
				agriStressControlRecommendation.setCommodityId(regionalAgriStressControlRecommendationMissing.getCommodityId());
				agriStressControlRecommendation.setStressControlMeasureId(regionalAgriStressControlRecommendationMissing.getStressControlMeasureId());
//				agriStressControlRecommendation.setStressTypeId(regionalAgriStressControlRecommendationMissing.getStressTypeId());
				agriStressControlRecommendation.setStressId(regionalAgriStressControlRecommendationMissing.getStressId());
//				agriStressControlRecommendation.setInstructions(regionalAgriStressControlRecommendationMissing.getInstructions());
				agriStressControlRecommendation.setAgroChemicalInstructions(regionalAgriStressControlRecommendationMissing.getAgroChemicalInstructions());
				agriStressControlRecommendation.setAgrochemicalId(regionalAgriStressControlRecommendationMissing.getAgrochemicalId());
				agriStressControlRecommendation.setDosePerHectare(regionalAgriStressControlRecommendationMissing.getDosePerHectare());
				agriStressControlRecommendation.setPerHectareUomId(regionalAgriStressControlRecommendationMissing.getPerHectareUomId());
				agriStressControlRecommendation.setDosePerAcre(regionalAgriStressControlRecommendationMissing.getDosePerAcre());
				agriStressControlRecommendation.setPerAcreUomId(regionalAgriStressControlRecommendationMissing.getPerAcreUomId());
				agriStressControlRecommendation.setWaterPerHectare(regionalAgriStressControlRecommendationMissing.getWaterPerHectare());
				agriStressControlRecommendation.setPerHectareWaterUomId(regionalAgriStressControlRecommendationMissing.getPerHectareWaterUomId());
				agriStressControlRecommendation.setWaterPerAcre(regionalAgriStressControlRecommendationMissing.getWaterPerAcre());
				agriStressControlRecommendation.setPerAcreWaterUomId(regionalAgriStressControlRecommendationMissing.getPerAcreWaterUomId());
				agriStressControlRecommendation.setAgrochemApplicationId(regionalAgriStressControlRecommendationMissing.getAgrochemApplicationId());
				agriStressControlRecommendation.setStatus(regionalAgriStressControlRecommendationMissing.getStatus());
				
				AgriStressControlRecommendation savedRegionalCommodity = agriStressControlRecommendationRepository.save(agriStressControlRecommendation);
				
				agriStressControlRecommendationMissingRepository.deleteById(id);


				approvalUtil.addRecord(DBConstants.TBL_AGRI_STRESS_CONTROL_RECOMMENDATION, savedRegionalCommodity.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri_stress-control-recommendation-Missing" + APIConstants.RESPONSE_MOVE_TO_MASTER_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri_stress-control-recommendation-Missing" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}

}
