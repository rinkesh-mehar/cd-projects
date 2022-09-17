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
import in.cropdata.cdtmasterdata.dto.interfaces.AgriQuantityLossChartInfDto;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.AgriQuantityLossChart;
import in.cropdata.cdtmasterdata.model.AgriQuantityLossChartMissing;
import in.cropdata.cdtmasterdata.model.GeneralBankBranch;
import in.cropdata.cdtmasterdata.model.GeneralBankBranchMissing;
import in.cropdata.cdtmasterdata.repository.AgriQuantityLossChartMissingRepository;
import in.cropdata.cdtmasterdata.repository.AgriQuantityLossChartRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;
 
@Service
public class AgriQuantityLossChartService {

	@Autowired
	private AgriQuantityLossChartRepository agriQuantityLossChartRepository;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	@Autowired
	private AclHistoryUtil approvalUtil;
	
	@Autowired
	private AgriQuantityLossChartMissingRepository agriQuantityLossChartMissingRepository;

	public List<AgriQuantityLossChartInfDto> getAllAgriQuantityLossChart() {

		try {
			List<AgriQuantityLossChartInfDto> QuantityLossChartList = agriQuantityLossChartRepository
					.getQuantityLossChartData();

			return QuantityLossChartList;

		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriQuantityLossChart

	public Page<AgriQuantityLossChartInfDto> getAgriQuantityLossChartByPaginated(int page, int size,
			String searchText, int isValid, String missing) {

		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			Page<AgriQuantityLossChartInfDto> QuantityLossChartList;
			if ("0".equals(missing)) {
			if (isValid == 0) {
				QuantityLossChartList = agriQuantityLossChartRepository.getAgriQuantityChartLossInvalidated(sortedByIdDesc, searchText);
			} else {
				QuantityLossChartList = agriQuantityLossChartRepository.getAgriQuantityChartLoss(sortedByIdDesc, searchText);
			}
		}else {
			if (isValid == 0) {
				QuantityLossChartList = agriQuantityLossChartRepository.getAgriQuantityChartLossMissingInvalidated(sortedByIdDesc, searchText);
			} else {
				QuantityLossChartList = agriQuantityLossChartRepository.getAgriQuantityChartMissingLoss(sortedByIdDesc, searchText);
			}
		}

			return QuantityLossChartList;

		} catch (Exception e) {
			throw e;
		}

	}

	public ResponseMessage addAgriQuantityLossChart(AgriQuantityLossChart agriQuantityLossChart) {

		try {
			agriQuantityLossChart = agriQuantityLossChartRepository.save(agriQuantityLossChart);

			approvalUtil.addRecord(DBConstants.TBL_AGRI_COMMODITY_PHENOPHASE, agriQuantityLossChart.getId());

			return responseMessageUtil.sendResponse(true,
					"Agri-Loss-Quantity-Chart" + APIConstants.RESPONSE_ADD_SUCCESS, "");
		} catch (Exception e) {
			e.printStackTrace();
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// addAllgetAllAgriQuantityLossChart

	public ResponseMessage updateAgriQuantityLossChart(int id, AgriQuantityLossChart agriQuantityLossChart) {

		try {

			Optional<AgriQuantityLossChart> foundQuantityLossChart = agriQuantityLossChartRepository.findById(id);

			if (foundQuantityLossChart.isPresent()) {

				agriQuantityLossChart.setId(id);
				agriQuantityLossChart = agriQuantityLossChartRepository.save(agriQuantityLossChart);

				approvalUtil.updateRecord(DBConstants.TBL_AGRI_QUANTITY_LOSS_CHART, agriQuantityLossChart.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Loss-Quantity-Chart" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Loss-Quantity-Chart" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateAllAgriQuantityLossChart

	public ResponseMessage primaryApproveById(int id) {

		try {

			Optional<AgriQuantityLossChart> foundQuantityLossChart = agriQuantityLossChartRepository.findById(id);
			if (foundQuantityLossChart.isPresent()) {

				AgriQuantityLossChart QuantityLossChart = foundQuantityLossChart.get();

				QuantityLossChart.setStatus(APIConstants.STATUS_APPROVED);

				QuantityLossChart = agriQuantityLossChartRepository.save(QuantityLossChart);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_QUANTITY_LOSS_CHART, QuantityLossChart.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Loss-Quantity-Chart" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Loss-Quantity-Chart" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {

		try {

			Optional<AgriQuantityLossChart> foundQuantityLossChart = agriQuantityLossChartRepository.findById(id);
			if (foundQuantityLossChart.isPresent()) {

				AgriQuantityLossChart QuantityLossChart = foundQuantityLossChart.get();

				QuantityLossChart.setStatus(APIConstants.STATUS_ACTIVE);

				QuantityLossChart = agriQuantityLossChartRepository.save(QuantityLossChart);

				approvalUtil.finalApprove(DBConstants.TBL_AGRI_QUANTITY_LOSS_CHART, QuantityLossChart.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Loss-Quantity-Chart" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");

			} else {

				return responseMessageUtil.sendResponse(false, "",
						"AAgri-Loss-Quantity-Chart" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);

			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}

	public ResponseMessage deleteAgriQuantityLossChart(int id) {

		try {

			Optional<AgriQuantityLossChart> foundQuantityLossChart = agriQuantityLossChartRepository.findById(id);
			if (foundQuantityLossChart.isPresent()) {

				AgriQuantityLossChart QuantityLossChart = foundQuantityLossChart.get();
				QuantityLossChart.setStatus(APIConstants.STATUS_DELETED);

				approvalUtil.delete(DBConstants.TBL_AGRI_QUANTITY_LOSS_CHART, QuantityLossChart.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Loss-Quantity-Chart" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");

			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-Loss-Quantity-Chart" + APIConstants.RESPONSE_DELETE_ERROR + id);

			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteAllAgriQuantityLossChart

	public ResponseMessage rejectById(int id) {

		try {

			Optional<AgriQuantityLossChart> foundQuantityLossChart = agriQuantityLossChartRepository.findById(id);
			if (foundQuantityLossChart.isPresent()) {

				AgriQuantityLossChart QuantityLossChart = foundQuantityLossChart.get();
				QuantityLossChart.setStatus(APIConstants.STATUS_REJECTED);

				approvalUtil.delete(DBConstants.TBL_AGRI_QUANTITY_LOSS_CHART, QuantityLossChart.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Loss-Quantity-Chart" + APIConstants.RESPONSE_REJECT_SUCCESS + id, "");

			} else {

				return responseMessageUtil.sendResponse(false, "",
						"AAgri-Loss-Quantity-Chart" + APIConstants.RESPONSE_REJECT_ERROR + id);

			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

	public AgriQuantityLossChart getAgriQuantityLossChartById(int id) {

		try {

			Optional<AgriQuantityLossChart> foundQuantityLossChart = agriQuantityLossChartRepository.findById(id);

			if (foundQuantityLossChart.isPresent()) {

				return foundQuantityLossChart.get();

			} else {
				throw new DoesNotExistException(
						"Agri-Commodity-Phenophase" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriQuantityLossChartById

    @Transactional
	public ResponseMessage moveToMaster(int id) {

		try {
			Optional<AgriQuantityLossChartMissing> foundAgriQuantityLossChartMissing = agriQuantityLossChartMissingRepository.findById(id);

			if (foundAgriQuantityLossChartMissing.isPresent()) {
				AgriQuantityLossChart agriQunatityLossChart = new AgriQuantityLossChart();
				AgriQuantityLossChartMissing agriQunatityLossChartMissing = foundAgriQuantityLossChartMissing.get();
				
				agriQunatityLossChart.setId(agriQunatityLossChartMissing.getId());
				agriQunatityLossChart.setCommodityId(agriQunatityLossChartMissing.getCommodityId());
				agriQunatityLossChart.setPhenophaseId(agriQunatityLossChartMissing.getPhenophaseId());
				agriQunatityLossChart.setStressId(agriQunatityLossChartMissing.getStressId());
				agriQunatityLossChart.setMinBandValue(agriQunatityLossChartMissing.getMinBandValue());
				agriQunatityLossChart.setMaxBandValue(agriQunatityLossChartMissing.getMaxBandValue());
				agriQunatityLossChart.setMinQuantityCorrectionPercent(agriQunatityLossChartMissing.getMinQuantityCorrectionPercent());
				agriQunatityLossChart.setMaxQuantityCorrectionPercent(agriQunatityLossChartMissing.getMaxQuantityCorrectionPercent());
				agriQunatityLossChart.setStatus(agriQunatityLossChartMissing.getStatus());
//	
				AgriQuantityLossChart agriQtyLossChart = agriQuantityLossChartRepository.save(agriQunatityLossChart);
//				
				agriQuantityLossChartMissingRepository.deleteById(id);

				approvalUtil.addRecord(DBConstants.TBL_AGRI_QUANTITY_LOSS_CHART, agriQtyLossChart.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-QuantityLossChart-Missing" + APIConstants.RESPONSE_MOVE_TO_MASTER_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-QuantityLossChart-Missing" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
		
	}

}
