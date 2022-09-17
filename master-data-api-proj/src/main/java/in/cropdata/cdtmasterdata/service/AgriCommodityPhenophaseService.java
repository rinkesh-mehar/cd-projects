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
import in.cropdata.cdtmasterdata.dto.interfaces.AgriCommodityPhenophaseInfDto;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.AgriCommodityPhenophase;
import in.cropdata.cdtmasterdata.model.AgriCommodityPhenophaseMissing;
import in.cropdata.cdtmasterdata.model.vo.AgriPhenophaseVo;
import in.cropdata.cdtmasterdata.repository.AgriCommodityPhenophaseMissingRepository;
import in.cropdata.cdtmasterdata.repository.AgriCommodityPhenophaseRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AgriCommodityPhenophaseService {

	@Autowired
	private AgriCommodityPhenophaseRepository agriCommodityPhenophaseRepository;
	
	@Autowired
	private AgriCommodityPhenophaseMissingRepository agriCommodityPhenophaseMissingRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<AgriCommodityPhenophaseInfDto> getAllAgriCommodityPhenophase() {

		try {
			List<AgriCommodityPhenophaseInfDto> commodityPhenophaseList = agriCommodityPhenophaseRepository
					.getCommodityPhenophaseList();

			return commodityPhenophaseList;

		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriCommodityPhenophase

	public Page<AgriCommodityPhenophaseInfDto> getAgriCommodityPhenophaseByPaginated(int page, int size,
			String searchText,String missing) {

		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());

			Page<AgriCommodityPhenophaseInfDto> commodityPhenophaseList =null;
			
			if("0".equals(missing)) {
				commodityPhenophaseList = agriCommodityPhenophaseRepository
						.getCommodityPhenophaseList(sortedByIdDesc, searchText);
			}else {
				commodityPhenophaseList = agriCommodityPhenophaseRepository
						.getCommodityPhenophaseListMissing(sortedByIdDesc, searchText);
			}

			return commodityPhenophaseList;

		} catch (Exception e) {
			throw e;
		}

	}

	public ResponseMessage addAgriCommodityPhenophase(AgriCommodityPhenophase agriCommodityPhenophase) {

		try {
			agriCommodityPhenophase = agriCommodityPhenophaseRepository.save(agriCommodityPhenophase);

			approvalUtil.addRecord(DBConstants.TBL_AGRI_COMMODITY_PHENOPHASE, agriCommodityPhenophase.getId());

			return responseMessageUtil.sendResponse(true,
					"Agri-Commodity-Phenophase" + APIConstants.RESPONSE_ADD_SUCCESS, "");
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// addAllgetAllAgriCommodityPhenophase

	public ResponseMessage updateAgriCommodityPhenophase(int id, AgriCommodityPhenophase agriCommodityPhenophase) {

		try {

			Optional<AgriCommodityPhenophase> foundCommodityPhenophase = agriCommodityPhenophaseRepository.findById(id);

			if (foundCommodityPhenophase.isPresent()) {

				agriCommodityPhenophase.setId(id);
				agriCommodityPhenophase = agriCommodityPhenophaseRepository.save(agriCommodityPhenophase);

				approvalUtil.updateRecord(DBConstants.TBL_AGRI_COMMODITY_PHENOPHASE, agriCommodityPhenophase.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Commodity-Phenophase" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Commodity-Phenophase" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateAllAgriCommodityPhenophase

	public ResponseMessage primaryApproveById(int id) {

		try {

			Optional<AgriCommodityPhenophase> foundCommodityPhenophase = agriCommodityPhenophaseRepository.findById(id);
			if (foundCommodityPhenophase.isPresent()) {

				AgriCommodityPhenophase commodityPhenophase = foundCommodityPhenophase.get();

				commodityPhenophase.setStatus(APIConstants.STATUS_APPROVED);

				commodityPhenophase = agriCommodityPhenophaseRepository.save(commodityPhenophase);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_COMMODITY_PHENOPHASE, commodityPhenophase.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Commodity-Phenophase" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Commodity-Phenophase" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {

		try {

			Optional<AgriCommodityPhenophase> foundCommodityPhenophase = agriCommodityPhenophaseRepository.findById(id);
			if (foundCommodityPhenophase.isPresent()) {

				AgriCommodityPhenophase commodityPhenophase = foundCommodityPhenophase.get();

				commodityPhenophase.setStatus(APIConstants.STATUS_ACTIVE);

				commodityPhenophase = agriCommodityPhenophaseRepository.save(commodityPhenophase);

				approvalUtil.finalApprove(DBConstants.TBL_AGRI_COMMODITY_PHENOPHASE, commodityPhenophase.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Commodity-Phenophase" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");

			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-Commodity-Phenophase" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);

			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}

	public ResponseMessage deleteAgriCommodityPhenophase(int id) {

		try {

			Optional<AgriCommodityPhenophase> foundCommodityPhenophase = agriCommodityPhenophaseRepository.findById(id);
			if (foundCommodityPhenophase.isPresent()) {

				AgriCommodityPhenophase commodityPhenophase = foundCommodityPhenophase.get();
				commodityPhenophase.setStatus(APIConstants.STATUS_DELETED);

				approvalUtil.delete(DBConstants.TBL_AGRI_COMMODITY_PHENOPHASE, commodityPhenophase.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Commodity-Phenophase" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");

			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-Commodity-Phenophase" + APIConstants.RESPONSE_DELETE_ERROR + id);

			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteAllAgriCommodityPhenophase

	public ResponseMessage rejectById(int id) {

		try {

			Optional<AgriCommodityPhenophase> foundCommodityPhenophase = agriCommodityPhenophaseRepository.findById(id);
			if (foundCommodityPhenophase.isPresent()) {

				AgriCommodityPhenophase commodityPhenophase = foundCommodityPhenophase.get();
				commodityPhenophase.setStatus(APIConstants.STATUS_REJECTED);

				approvalUtil.delete(DBConstants.TBL_AGRI_COMMODITY_PHENOPHASE, commodityPhenophase.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Commodity-Phenophase" + APIConstants.RESPONSE_REJECT_SUCCESS + id, "");

			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-Commodity-Phenophase" + APIConstants.RESPONSE_REJECT_ERROR + id);

			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

	public AgriCommodityPhenophase getAgriCommodityPhenophaseById(int id) {

		try {

			Optional<AgriCommodityPhenophase> foundCommodityPhenophase = agriCommodityPhenophaseRepository.findById(id);

			if (foundCommodityPhenophase.isPresent()) {

				return foundCommodityPhenophase.get();

			} else {
				throw new DoesNotExistException(
						"Agri-Commodity-Phenophase" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriCommodityPhenophaseById

	public List<AgriPhenophaseVo> getByCommodityId(int commodityId) {

		return agriCommodityPhenophaseRepository.findByCommodityId(commodityId);

	}//getByCommodityId
	
	@Transactional
	public ResponseMessage moveToMaster(int id) {
		try {
			Optional<AgriCommodityPhenophaseMissing> foundAgriCommodityPhenophaseMissing = agriCommodityPhenophaseMissingRepository.findById(id);

			if (foundAgriCommodityPhenophaseMissing.isPresent()) {
				AgriCommodityPhenophase agriCommodityPhenophase = new AgriCommodityPhenophase();
				AgriCommodityPhenophaseMissing agriCommodityPhenophaseMissing = foundAgriCommodityPhenophaseMissing.get();				
				agriCommodityPhenophase.setCommodityId(agriCommodityPhenophaseMissing.getCommodityId());
				agriCommodityPhenophase.setPhenophaseId(agriCommodityPhenophaseMissing.getPhenophaseId());
				agriCommodityPhenophase.setStatus(agriCommodityPhenophaseMissing.getStatus());
				AgriCommodityPhenophase savedAgriCommodityPhenophase = agriCommodityPhenophaseRepository.save(agriCommodityPhenophase);
				agriCommodityPhenophaseMissingRepository.deleteById(id);


				approvalUtil.addRecord(DBConstants.TBL_AGRI_COMMODITY_PHENOPHASE, savedAgriCommodityPhenophase.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Commodity-Phenophase" + APIConstants.RESPONSE_MOVE_TO_MASTER_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Commodity-Phenophase" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}
}
