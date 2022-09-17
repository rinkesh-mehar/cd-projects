package in.cropdata.cdtmasterdata.service;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.constants.DBConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriStressControlMeasuresInfDto;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriDistrictCommodityStressInfDto;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.AgriStressControlMeasures;
import in.cropdata.cdtmasterdata.model.AgriRecommendationMissing;
import in.cropdata.cdtmasterdata.repository.AgriRecommendationMissingRepository;
import in.cropdata.cdtmasterdata.repository.AgriStressControlMeasuresRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AgriStressControlMeasuresService {

	@Autowired
	private AgriStressControlMeasuresRepository agriRecommendationRepository;
	
	@Autowired
	private AgriRecommendationMissingRepository agriRecommendationMissingRepository;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	@Autowired
	private AclHistoryUtil approvalUtil;

	public ResponseMessage addAgriStressControlMeasure(AgriStressControlMeasures agriRecommendation) {
		try {
			agriRecommendationRepository.save(agriRecommendation);

			return responseMessageUtil.sendResponse(true, "Stress Controll Measures" + APIConstants.RESPONSE_ADD_SUCCESS,
					"");

		} catch (DataIntegrityViolationException e) {
			return responseMessageUtil.sendResponse(false, "", "Given Record Already exist!");
		}
		catch (Exception e){
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAgriRecommendation

	public List<AgriStressControlMeasuresInfDto> getAgriStressControlMeasures() {
		return agriRecommendationRepository.getAgriStressControlMeasures();
	}// getAllAgriRecommendation

	public Page<AgriStressControlMeasuresInfDto> getAllAgriStressControlMeasuresPaginated(int page, int size, String searchText, int isValid,String missing,String commodityId,String stressId,String stressSeverityId,String controlMeasureId,String filter) {

		try {
			searchText = "%" + searchText + "%";

//		System.out.println("searchText--> " + searchText);

			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").ascending());

			
			Page<AgriStressControlMeasuresInfDto> recommendationList = null;
			if("1".equals(filter)) {
				if("".equals(commodityId)) {
					commodityId = null;
				}
				if("".equals(stressId)) {
					stressId = null;
				}
				if("".equals(stressSeverityId)) {
					stressSeverityId = null;
				}
				if("".equals(controlMeasureId)) {
					controlMeasureId = null;
				}
				recommendationList = agriRecommendationRepository.getAgriStressControlMeasuresByMultiSearchFilters(sortedByIdDesc, commodityId,stressId,stressSeverityId,controlMeasureId);
				
			}else if("0".equals(missing)) {
			if (isValid == 0) {
				recommendationList = agriRecommendationRepository.getAgriStressControlMeasuresInvalidated(sortedByIdDesc, searchText);
			} else {
				recommendationList = agriRecommendationRepository.getAgriStressControlMeasures(sortedByIdDesc, searchText);
			}
			}else {
				if (isValid == 0) {
					recommendationList = agriRecommendationRepository.getAgriRecommendationMissingInvalidated(sortedByIdDesc, searchText);
				} else {
					recommendationList = agriRecommendationRepository.getAgriRecommendationMissing(sortedByIdDesc, searchText);
				}
			}

			return recommendationList;
		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriRecommendationPaginated

	public ResponseMessage updateAgriStressControlMeasuresById(int id, AgriStressControlMeasures agriRecommendation) {

		try {

			Optional<AgriStressControlMeasures> foundGroup = agriRecommendationRepository.findById(id);

			if (foundGroup.isPresent()) {

				agriRecommendation.setId(id);
				agriRecommendationRepository.save(agriRecommendation);

				return responseMessageUtil.sendResponse(true,
						"Stress Controll Measures" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");

			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Stress Controll Measures" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateAgriRecommendationById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<AgriStressControlMeasures> foundRecommondation = agriRecommendationRepository.findById(id);
			if (foundRecommondation.isPresent()) {

				AgriStressControlMeasures recommondation = foundRecommondation.get();
				recommondation.setStatus(APIConstants.STATUS_APPROVED);

				recommondation = agriRecommendationRepository.save(recommondation);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_STRESS_CONTROL_MEASURES, recommondation.getId());

				return responseMessageUtil.sendResponse(true,
						"Stress Controll Measures" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false,
						"Stress Controll Measures" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR, "");
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {

		try {

			Optional<AgriStressControlMeasures> foundRecommondation = agriRecommendationRepository.findById(id);
			if (foundRecommondation.isPresent()) {

				AgriStressControlMeasures recommondation = foundRecommondation.get();
				recommondation.setStatus(APIConstants.STATUS_ACTIVE);

				recommondation = agriRecommendationRepository.save(recommondation);

				approvalUtil.finalApprove(DBConstants.TBL_AGRI_STRESS_CONTROL_MEASURES, recommondation.getId());

				return responseMessageUtil.sendResponse(true,
						"Stress Controll Measures" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false,
						"Stress Controll Measures" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR, "");
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// finalApproveById

	public ResponseMessage deleteAgriStressControlMeasuresById(int id) {

		try {
			agriRecommendationRepository.deleteById(id);

			return responseMessageUtil.sendResponse(true,
					"Stress Controll Measures" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "",
					"Stress Controll Measures" + APIConstants.RESPONSE_DELETE_ERROR + id);
		}
	}// deleteAgriRecommendationById

	public AgriStressControlMeasures findAgriStressControlMeasuresById(int id) {
		try {
			Optional<AgriStressControlMeasures> foundRecommendation = agriRecommendationRepository.findById(id);
			if (foundRecommendation.isPresent()) {
				return foundRecommendation.get();
			} else {
				throw new DoesNotExistException("Stress Controll Measures" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findAgriRecommendationById

	public List<AgriDistrictCommodityStressInfDto> getStressByCommodity(int commodityId) {

		try {

			List<AgriDistrictCommodityStressInfDto> list = agriRecommendationRepository.getAllStressByCommodityId(commodityId);

			return list;

		} catch (Exception e) {
			throw e;
		}

	}

	/**
	 * @param id verify given data are exist or not,
	 *           if data exist then it rejected
	 * @return It return three arguments(isSuccess, message and error)
	 */
	public ResponseMessage rejectById(int id)
	{
		try
		{
			Optional<AgriStressControlMeasures> foundRecommendation = agriRecommendationRepository.findById(id);

			if (foundRecommendation.isPresent())
			{

				AgriStressControlMeasures agriRecommendation = foundRecommendation.get();
				agriRecommendation.setStatus(APIConstants.STATUS_REJECTED);
				agriRecommendation = agriRecommendationRepository.save(agriRecommendation);

				approvalUtil.finalApprove(DBConstants.TBL_AGRI_STRESS_CONTROL_MEASURES, agriRecommendation.getId());

				return responseMessageUtil.sendResponse(true, "Stress Controll Measures " + APIConstants.RESPONSE_REJECT_SUCCESS, "");
			} else
			{
				return responseMessageUtil.sendResponse(false, "", "Stress Controll Measures" + APIConstants.RESPONSE_REJECT_ERROR);
			}
		} catch (Exception e)
		{
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}
	
	@Transactional
	public ResponseMessage moveToMaster(int id) {
		try {
			Optional<AgriRecommendationMissing> foundMissingCommodity = agriRecommendationMissingRepository.findById(id);

			if (foundMissingCommodity.isPresent()) {
				AgriStressControlMeasures agriRecommendation = new AgriStressControlMeasures();
				AgriRecommendationMissing regionalCommodityMissing = foundMissingCommodity.get();
				
				agriRecommendation.setCommodityId(regionalCommodityMissing.getCommodityId());
				agriRecommendation.setStressId(regionalCommodityMissing.getStressId());
				agriRecommendation.setStressSeverityId(regionalCommodityMissing.getStressSeverityId());
				agriRecommendation.setStressControlMeasureId(regionalCommodityMissing.getStressControlMeasureId());
				agriRecommendation.setStatus(regionalCommodityMissing.getStatus());
				
				AgriStressControlMeasures savedAgriRecommendation = agriRecommendationRepository.save(agriRecommendation);
				
				agriRecommendationMissingRepository.deleteById(id);


				approvalUtil.addRecord(DBConstants.TBL_AGRI_RECOMMODATION, savedAgriRecommendation.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri Recommendation-Missing" + APIConstants.RESPONSE_MOVE_TO_MASTER_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri Recommendation-Missing" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}

}
