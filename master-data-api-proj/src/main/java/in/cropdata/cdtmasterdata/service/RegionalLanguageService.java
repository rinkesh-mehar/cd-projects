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
import in.cropdata.cdtmasterdata.dto.interfaces.RegionalLanguageDtoInf;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.FarmerLanguage;
import in.cropdata.cdtmasterdata.model.GeoRegion;
import in.cropdata.cdtmasterdata.model.GeoState;
import in.cropdata.cdtmasterdata.model.RegionalLanguage;
import in.cropdata.cdtmasterdata.model.RegionalLanguageMissing;
import in.cropdata.cdtmasterdata.repository.FarmerLanguageRepository;
import in.cropdata.cdtmasterdata.repository.GeoRegionRepository;
import in.cropdata.cdtmasterdata.repository.GeoStateRepository;
import in.cropdata.cdtmasterdata.repository.RegionalLanguageMissingRepository;
import in.cropdata.cdtmasterdata.repository.RegionalLanguageRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class RegionalLanguageService {

	@Autowired
	private RegionalLanguageRepository regionalLanguageRepository;
	
	@Autowired
	private RegionalLanguageMissingRepository regionalLanguageMissingRepository;

	@Autowired
	private GeoStateRepository geoStateRepository;

	@Autowired
	private GeoRegionRepository geoRegionRepository;

	@Autowired
	private FarmerLanguageRepository farmerLanguageRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<RegionalLanguage> getAllAgriRegionalLanguage() {

		try {
			List<RegionalLanguage> languageList = regionalLanguageRepository.findAll();

			for (RegionalLanguage language : languageList) {

				language = getData(language);

			} // for
			return languageList;
		} catch (Exception e) {
			throw e;
		}
	}


	public Page<RegionalLanguageDtoInf> getAllRegionalLanguagePaginated(int page, int size,String missing, String searchText, int isValid) {


		try {
			searchText = "%" + searchText + "%";

//		System.out.println("searchText--> " + searchText);
			Page<RegionalLanguageDtoInf> languageList;
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());

			if("0".equals(missing)) {
				if (isValid == 0) {
	 				languageList= regionalLanguageRepository.getRegionalLanguageInvalidated(sortedByIdDesc,searchText);
	 			} else {
	 				languageList= regionalLanguageRepository.getRegionalLanguage(sortedByIdDesc,searchText);
	 			}

			}else {
				if (isValid == 0) {
	 				languageList= regionalLanguageRepository.getRegionalLanguageMissingInvalidated(sortedByIdDesc,searchText);
	 			} else {
	 				languageList= regionalLanguageRepository.getRegionalLanguageMissing(sortedByIdDesc,searchText);
	 			}
			}

			return languageList;
		} catch (Exception e) {
			throw e;
		}
	}// getAllRegionalLanguagePaginated

	private RegionalLanguage getData(RegionalLanguage language) {
		try {

			GeoState state = geoStateRepository.findByStateCode(language.getStateCode());
			if (state != null) {
				language.setState(state.getName());
			}

			if (language.getRegionId() != null) {

				Optional<GeoRegion> foundRegion = geoRegionRepository.findByRegionId(language.getRegionId());
				if (foundRegion.isPresent()) {
					GeoRegion region = foundRegion.get();
					language.setRegion(region.getName());
				}
			}

			Optional<FarmerLanguage> farmerLanguage = farmerLanguageRepository.findById(language.getLanguageId());
			if (farmerLanguage.isPresent()) {
				language.setLanguage(farmerLanguage.get().getLanguage());
			}
			return language;

		} catch (Exception e) {
			throw e;
		}

	}

	public ResponseMessage addRegionalLanguage(RegionalLanguage regionalLanguage) {

		try
		{
			if (regionalLanguageRepository.findByStateCodeAndLanguageId(
					regionalLanguage.getStateCode(), regionalLanguage.getLanguageId()
			).isEmpty())
			{

				regionalLanguage = regionalLanguageRepository.save(regionalLanguage);

				approvalUtil.addRecord(DBConstants.TBL_REGIONAL_LANGUAGE, regionalLanguage.getId());

				return responseMessageUtil.sendResponse(
						true, "Regional-Language".concat(APIConstants.RESPONSE_ADD_SUCCESS), "");
			} else
			{
				return responseMessageUtil.sendResponse(
						false, "", "Regional-Language".concat(APIConstants.RESPONSE_ALREADY_EXIST).concat("This Language")
				);
			}

		} catch (Exception e)
		{

			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addRegionalLanguage

	public ResponseMessage updateRegionalLanguageById(int id, RegionalLanguage regionalLanguage) {

		try {
			Optional<RegionalLanguage> foundLanguage = regionalLanguageRepository.findById(id);

			if (foundLanguage.isPresent()) {

				RegionalLanguage update = foundLanguage.get();

				if (regionalLanguage.getStateCode() > 0) {
					update.setStateCode(regionalLanguage.getStateCode());
				}

				if (regionalLanguage.getRegionId() != null && regionalLanguage.getRegionId() > 0) {
					update.setRegionId(regionalLanguage.getRegionId());
				}

				if (regionalLanguage.getLanguageId() > 0) {
					update.setLanguageId(regionalLanguage.getLanguageId());
				}

				if (regionalLanguage.getStatus() != null) {
					update.setStatus(regionalLanguage.getStatus());
				}

				regionalLanguage = regionalLanguageRepository.save(update);

				approvalUtil.updateRecord(DBConstants.TBL_REGIONAL_LANGUAGE, regionalLanguage.getId());

				return responseMessageUtil.sendResponse(true,
						"Regional-Language" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Regional-Language" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// updateRegionalLanguageById

	public ResponseMessage primaryApproveById(int id) {

		try {
			Optional<RegionalLanguage> foundLanguage = regionalLanguageRepository.findById(id);

			if (foundLanguage.isPresent()) {

				RegionalLanguage regionalLanguage = foundLanguage.get();
				regionalLanguage.setStatus(APIConstants.STATUS_APPROVED);

				regionalLanguage = regionalLanguageRepository.save(regionalLanguage);

				approvalUtil.primaryApprove(DBConstants.TBL_REGIONAL_LANGUAGE, regionalLanguage.getId());

				return responseMessageUtil.sendResponse(true,
						"Regional-Language" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Regional-Language" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {

		try {
			Optional<RegionalLanguage> foundLanguage = regionalLanguageRepository.findById(id);

			if (foundLanguage.isPresent()) {

				RegionalLanguage regionalLanguage = foundLanguage.get();
				regionalLanguage.setStatus(APIConstants.STATUS_ACTIVE);

				regionalLanguage = regionalLanguageRepository.save(regionalLanguage);

				approvalUtil.finalApprove(DBConstants.TBL_REGIONAL_LANGUAGE, regionalLanguage.getId());

				return responseMessageUtil.sendResponse(true,
						"Regional-Language" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Regional-Language" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproverById

	public ResponseMessage deleteRegionalLanguageById(int id) {
		try {
			Optional<RegionalLanguage> foundLanguage = regionalLanguageRepository.findById(id);
			if (foundLanguage.isPresent()) {

				RegionalLanguage regionalLanguage = foundLanguage.get();
				regionalLanguage.setStatus(APIConstants.STATUS_DELETED);

				regionalLanguage = regionalLanguageRepository.save(regionalLanguage);

				approvalUtil.delete(DBConstants.TBL_REGIONAL_LANGUAGE, regionalLanguage.getId());

				return responseMessageUtil.sendResponse(true,
						"Regional-Language" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Regional-Language" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteRegionalLanguageById

	public RegionalLanguage findRegionalLanguageById(int id) {
		try {
			Optional<RegionalLanguage> foundLanguage = regionalLanguageRepository.findById(id);
			if (foundLanguage.isPresent()) {
				return foundLanguage.get();
			} else {
				throw new DoesNotExistException("Regional-Language" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findRegionalLanguageById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<RegionalLanguage> foundLanguage = regionalLanguageRepository.findById(id);

			if (foundLanguage.isPresent()) {

				RegionalLanguage regionalLanguage = foundLanguage.get();
				regionalLanguage.setStatus(APIConstants.STATUS_REJECTED);
				regionalLanguage = regionalLanguageRepository.save(regionalLanguage);

				approvalUtil.finalApprove(DBConstants.TBL_REGIONAL_LANGUAGE, regionalLanguage.getId());

				return responseMessageUtil.sendResponse(true,
						"Regional-Language " + APIConstants.RESPONSE_REJECT_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Regional-Language " + APIConstants.RESPONSE_REJECT_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById
	
	@Transactional
	public ResponseMessage moveToMaster(int id) {
		try {
			Optional<RegionalLanguageMissing> foundMissingLanguage = regionalLanguageMissingRepository.findById(id);

			if (foundMissingLanguage.isPresent()) {
				RegionalLanguage regionalLanguage = new RegionalLanguage();
				RegionalLanguageMissing regionalLanguageMissing = foundMissingLanguage.get();
				
				regionalLanguage.setStateCode(regionalLanguageMissing.getStateCode());
				regionalLanguage.setRegionId(regionalLanguageMissing.getRegionId());
				regionalLanguage.setLanguageId(regionalLanguageMissing.getLanguageId());
				regionalLanguage.setStatus(regionalLanguageMissing.getStatus());
				
				RegionalLanguage savedRegionalLanguage = regionalLanguageRepository.save(regionalLanguage);
				
				regionalLanguageMissingRepository.deleteById(id);


				approvalUtil.addRecord(DBConstants.TBL_REGIONAL_LANGUAGE, savedRegionalLanguage.getId());

				return responseMessageUtil.sendResponse(true,
						"Region-Commodity-Missing" + APIConstants.RESPONSE_MOVE_TO_MASTER_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Region-Commodity-Missing" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}
	
}
