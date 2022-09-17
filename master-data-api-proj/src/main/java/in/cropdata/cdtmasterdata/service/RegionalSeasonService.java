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
import in.cropdata.cdtmasterdata.dto.interfaces.RegionalSeasonInfDto;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.RegionalSeason;
import in.cropdata.cdtmasterdata.model.RegionalSeasonMissing;
import in.cropdata.cdtmasterdata.repository.RegionalSeasonMissingRepository;
import in.cropdata.cdtmasterdata.repository.RegionalSeasonRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class RegionalSeasonService {

	@Autowired
	private RegionalSeasonRepository regionSeasonRepository;
	
	@Autowired
	private RegionalSeasonMissingRepository regionalSeasonMissingRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<RegionalSeasonInfDto> getAllRegioSeason() {

		try {
			List<RegionalSeasonInfDto> list = regionSeasonRepository.getRegionalSeasonList();

			return list;
		} catch (Exception e) {
			throw e;
		}

	}// getAllRegioSeason


	public Page<RegionalSeasonInfDto> getAllRegionalSeasonPaginated(int page, int size,String missing, String searchText, int isValid) {
		try {
			searchText = "%"+searchText+"%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());

			Page<RegionalSeasonInfDto> seasonList;
			

			if("0".equals(missing)) {
				if (isValid == 0) {
					seasonList = regionSeasonRepository.getRegionalSeasonListInvalidated(sortedByIdDesc,searchText);
				} else {
					seasonList = regionSeasonRepository.getRegionalSeasonList(sortedByIdDesc,searchText);
				}
			}else {
				if (isValid == 0) {
					seasonList = regionSeasonRepository.getRegionalSeasonMissingListInvalidated(sortedByIdDesc,searchText);
				} else {
					seasonList = regionSeasonRepository.getRegionalSeasonMissingList(sortedByIdDesc,searchText);
				}
			}

			return seasonList;
		} catch (Exception e) {
			throw e;
		}
	}

	public ResponseMessage addRegionSeason(RegionalSeason regionSeason) {

		try {
			regionSeason = regionSeasonRepository.save(regionSeason);

			approvalUtil.addRecord(DBConstants.TBL_REGIONAL_SEASON, regionSeason.getId());

			return responseMessageUtil.sendResponse(true, "Region-Season" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAllRegionSeason

	public ResponseMessage updateRegionSeasonById(int id, RegionalSeason regionSeason) {
		try {
			Optional<RegionalSeason> foundSeason = regionSeasonRepository.findById(id);

			if (foundSeason.isPresent()) {

				regionSeason.setId(id);
				regionSeason = regionSeasonRepository.save(regionSeason);

				approvalUtil.updateRecord(DBConstants.TBL_REGIONAL_SEASON, regionSeason.getId());

				return responseMessageUtil.sendResponse(true,
						"Region-Season" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Region-Season" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateRegionSeasonById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<RegionalSeason> foundSeason = regionSeasonRepository.findById(id);

			if (foundSeason.isPresent()) {

				RegionalSeason regionSeason = foundSeason.get();
				regionSeason.setStatus(APIConstants.STATUS_APPROVED);

				regionSeason = regionSeasonRepository.save(regionSeason);

				approvalUtil.primaryApprove(DBConstants.TBL_REGIONAL_SEASON, regionSeason.getId());

				return responseMessageUtil.sendResponse(true,
						"Region-Season" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Region-Season" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<RegionalSeason> foundSeason = regionSeasonRepository.findById(id);

			if (foundSeason.isPresent()) {

				RegionalSeason regionSeason = foundSeason.get();
				regionSeason.setStatus(APIConstants.STATUS_ACTIVE);

				regionSeason = regionSeasonRepository.save(regionSeason);

				approvalUtil.finalApprove(DBConstants.TBL_REGIONAL_SEASON, regionSeason.getId());

				return responseMessageUtil.sendResponse(true,
						"Region-Season" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Region-Season" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteRegionSeasonById(int id) {
		try {
			Optional<RegionalSeason> foundSeason = regionSeasonRepository.findById(id);

			if (foundSeason.isPresent()) {

				RegionalSeason regionSeason = foundSeason.get();
				regionSeason.setStatus(APIConstants.STATUS_DELETED);

				regionSeason = regionSeasonRepository.save(regionSeason);

				approvalUtil.delete(DBConstants.TBL_REGIONAL_SEASON, regionSeason.getId());

				return responseMessageUtil.sendResponse(true,
						"Region-Season" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Region-Season" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteRegionSeasonById

	public RegionalSeason findRegionSeasonById(int id) {

		try {
			Optional<RegionalSeason> foundSeason = regionSeasonRepository.findById(id);

			if (foundSeason.isPresent()) {
				return foundSeason.get();
			} else {
				throw new DoesNotExistException("Region-Season" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findRegioSeasonById
	
	public ResponseMessage rejectById(int id) {
		try {
			Optional<RegionalSeason> foundSeason = regionSeasonRepository.findById(id);

			if (foundSeason.isPresent()) {

				RegionalSeason regionalSeason = foundSeason.get();
				regionalSeason.setStatus(APIConstants.STATUS_REJECTED);
				regionalSeason = regionSeasonRepository.save(regionalSeason);

				approvalUtil.finalApprove(DBConstants.TBL_REGIONAL_SEASON, regionalSeason.getId());

				return responseMessageUtil.sendResponse(true, "Regional-Season " + APIConstants.RESPONSE_REJECT_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "", "Regional-Season " + APIConstants.RESPONSE_REJECT_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById
	
	@Transactional
	public ResponseMessage moveToMaster(int id) {
		try {
			Optional<RegionalSeasonMissing> foundMissingSeason = regionalSeasonMissingRepository.findById(id);

			if (foundMissingSeason.isPresent()) {
				RegionalSeason regionalSeason = new RegionalSeason();
				RegionalSeasonMissing regionalSeasonMissing = foundMissingSeason.get();
				
				regionalSeason.setStateCode(regionalSeasonMissing.getStateCode());
				regionalSeason.setRegionId(regionalSeasonMissing.getRegionId());
				regionalSeason.setSeasonId(regionalSeasonMissing.getSeasonId());
				regionalSeason.setStartWeek(regionalSeasonMissing.getStartWeek());
				regionalSeason.setEndWeek(regionalSeasonMissing.getEndWeek());
				regionalSeason.setSeasonSpan(regionalSeasonMissing.getSeasonSpan());
				regionalSeason.setStatus(regionalSeasonMissing.getStatus());
				
				RegionalSeason savedRegionalCommodity = regionSeasonRepository.save(regionalSeason);
				
				regionalSeasonMissingRepository.deleteById(id);

				System.out.println("RCom Id : " + savedRegionalCommodity.getId());
				approvalUtil.addRecord(DBConstants.TBL_REGIONAL_SEASON, savedRegionalCommodity.getId());

				return responseMessageUtil.sendResponse(true,
						"Region-Season-Missing" + APIConstants.RESPONSE_MOVE_TO_MASTER_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Region-Season-Missing" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}

}// RegionSeasonService
