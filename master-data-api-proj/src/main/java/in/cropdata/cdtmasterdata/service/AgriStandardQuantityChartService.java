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
import in.cropdata.cdtmasterdata.dto.interfaces.AgriStandardQuantityChartInfDto;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.AgriQuantityLossChart;
import in.cropdata.cdtmasterdata.model.AgriQuantityLossChartMissing;
import in.cropdata.cdtmasterdata.model.AgriStandardQuantityChart;
import in.cropdata.cdtmasterdata.model.AgriStandardQuantityChartMissing;
import in.cropdata.cdtmasterdata.repository.AgriStandardQuantityChartMissingRepository;
import in.cropdata.cdtmasterdata.repository.AgriStandardQuantityChartRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AgriStandardQuantityChartService {

	@Autowired
	private AgriStandardQuantityChartRepository agriStandardQuantityChartRepository;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	@Autowired
	private AclHistoryUtil approvalUtil;
	
	@Autowired
    private AgriStandardQuantityChartMissingRepository agriStandardQuantityChartMissingRepository;
	
	public List<AgriStandardQuantityChartInfDto> getAllAgriStandardQuantityChart() {

		try {
			List<AgriStandardQuantityChartInfDto> StandardQuantityChartList = agriStandardQuantityChartRepository
					.getStandardQuantityChartList();

			return StandardQuantityChartList;

		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriStandardQuantityChart

	public Page<AgriStandardQuantityChartInfDto> getAgriStandardQuantityChartByPaginated(int page, int size,
			int isValid, String searchText, String missing) {

		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			Page<AgriStandardQuantityChartInfDto> StandardQuantityChartList;
			if ("0".equals(missing)) {
				if (isValid == 0) {
					StandardQuantityChartList = agriStandardQuantityChartRepository
							.getStandardQuantityChartListInvalidated(sortedByIdDesc, searchText);
				} else {
					StandardQuantityChartList = agriStandardQuantityChartRepository
							.getStandardQuantityChartList(sortedByIdDesc, searchText);
				}
			} else {
				if (isValid == 0) {
					StandardQuantityChartList = agriStandardQuantityChartRepository
							.getStandardQuantityChartMissingListInvalidated(sortedByIdDesc, searchText);
				} else {
					StandardQuantityChartList = agriStandardQuantityChartRepository
							.getStandardQuantityChartMissingList(sortedByIdDesc, searchText);
				}
			}
			return StandardQuantityChartList;

		} catch (Exception e) {
			throw e;
		}

	}

	/**
	 * @param agriStandardQuantityChart First verify given data are exist or not base on
	 *                                  state-code,commodityID and varietyID
	 * @return return response bind arguments(isSuccess, message and error)
	 */
	public ResponseMessage addAgriStandardQuantityChart(AgriStandardQuantityChart agriStandardQuantityChart)
	{

		try
		{
			if (((agriStandardQuantityChartRepository.verifyData(agriStandardQuantityChart.getStateCode(),
					agriStandardQuantityChart.getCommodityId(), agriStandardQuantityChart.getVarietyId())) == null))
			{

				agriStandardQuantityChart = agriStandardQuantityChartRepository.save(agriStandardQuantityChart);

				approvalUtil.addRecord(DBConstants.TBL_AGRI_STANDARD_QUANTITY_CHART, agriStandardQuantityChart.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Standard-Quantity-Chart" + APIConstants.RESPONSE_ADD_SUCCESS, "");
			} else
			{
				return responseMessageUtil.sendResponse(false,
						"", "Agri-Standard-Quantity-Chart Already Exist");
			}
		} catch (Exception e)
		{
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// addAllgetAllAgriStandardQuantityChart

	public ResponseMessage updateAgriStandardQuantityChart(int id, AgriStandardQuantityChart agriStandardQuantityChart) {

		try {

			Optional<AgriStandardQuantityChart> foundStandardQuantityChart = agriStandardQuantityChartRepository.findById(id);

			if (foundStandardQuantityChart.isPresent()) {

				agriStandardQuantityChart.setId(id);
				agriStandardQuantityChart = agriStandardQuantityChartRepository.save(agriStandardQuantityChart);

				approvalUtil.updateRecord(DBConstants.TBL_AGRI_STANDARD_QUANTITY_CHART, agriStandardQuantityChart.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Standard-Quantity-Chart" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Standard-Quantity-Chart" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateAllAgriStandardQuantityChart

	public ResponseMessage primaryApproveById(int id) {

		try {

			Optional<AgriStandardQuantityChart> foundStandardQuantityChart = agriStandardQuantityChartRepository.findById(id);
			if (foundStandardQuantityChart.isPresent()) {

				AgriStandardQuantityChart StandardQuantityChart = foundStandardQuantityChart.get();

				StandardQuantityChart.setStatus(APIConstants.STATUS_APPROVED);

				StandardQuantityChart = agriStandardQuantityChartRepository.save(StandardQuantityChart);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_STANDARD_QUANTITY_CHART, StandardQuantityChart.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Standard-Quantity-Chart" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Standard-Quantity-Chart" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {

		try {

			Optional<AgriStandardQuantityChart> foundStandardQuantityChart = agriStandardQuantityChartRepository.findById(id);
			if (foundStandardQuantityChart.isPresent()) {

				AgriStandardQuantityChart StandardQuantityChart = foundStandardQuantityChart.get();

				StandardQuantityChart.setStatus(APIConstants.STATUS_ACTIVE);

				StandardQuantityChart = agriStandardQuantityChartRepository.save(StandardQuantityChart);

				approvalUtil.finalApprove(DBConstants.TBL_AGRI_STANDARD_QUANTITY_CHART, StandardQuantityChart.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Standard-Quantity-Chart" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");

			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-Standard-Quantity-Chart" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);

			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}

	public ResponseMessage deleteAgriStandardQuantityChart(int id) {

		try {

			Optional<AgriStandardQuantityChart> foundStandardQuantityChart = agriStandardQuantityChartRepository.findById(id);
			if (foundStandardQuantityChart.isPresent()) {

				AgriStandardQuantityChart StandardQuantityChart = foundStandardQuantityChart.get();
				StandardQuantityChart.setStatus(APIConstants.STATUS_DELETED);

				approvalUtil.delete(DBConstants.TBL_AGRI_STANDARD_QUANTITY_CHART, StandardQuantityChart.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Standard-Quantity-Chart" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");

			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-Standard-Quantity-Chart" + APIConstants.RESPONSE_DELETE_ERROR + id);

			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteAllAgriStandardQuantityChart

	public ResponseMessage rejectById(int id) {

		try {

			Optional<AgriStandardQuantityChart> foundStandardQuantityChart = agriStandardQuantityChartRepository.findById(id);
			if (foundStandardQuantityChart.isPresent()) {

				AgriStandardQuantityChart StandardQuantityChart = foundStandardQuantityChart.get();
				StandardQuantityChart.setStatus(APIConstants.STATUS_REJECTED);

				approvalUtil.delete(DBConstants.TBL_AGRI_STANDARD_QUANTITY_CHART, StandardQuantityChart.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Standard-Quantity-Chart" + APIConstants.RESPONSE_REJECT_SUCCESS + id, "");

			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-Standard-Quantity-Chart" + APIConstants.RESPONSE_REJECT_ERROR + id);

			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

	public AgriStandardQuantityChart getAgriStandardQuantityChartById(int id) {

		try {

			Optional<AgriStandardQuantityChart> foundStandardQuantityChart = agriStandardQuantityChartRepository.findById(id);

			if (foundStandardQuantityChart.isPresent()) {

				return foundStandardQuantityChart.get();

			} else {
				throw new DoesNotExistException(
						"Agri-Standard-Quantity-Chart" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriStandardQuantityChartById

	@Transactional
	public ResponseMessage moveToMaster(int id) {

		try {
			Optional<AgriStandardQuantityChartMissing> foundAgriStandardQuantityChartMissing = agriStandardQuantityChartMissingRepository.findById(id);

			if (foundAgriStandardQuantityChartMissing.isPresent()) {
				AgriStandardQuantityChart agriStandardQuantityChart = new AgriStandardQuantityChart();
				AgriStandardQuantityChartMissing agriQunatityStandardChartMissing = foundAgriStandardQuantityChartMissing.get();
				
				agriStandardQuantityChart.setId(agriQunatityStandardChartMissing.getId());
				agriStandardQuantityChart.setStateCode(agriQunatityStandardChartMissing.getStateCode());
				agriStandardQuantityChart.setCommodityId(agriQunatityStandardChartMissing.getCommodityId());
				agriStandardQuantityChart.setVarietyId(agriQunatityStandardChartMissing.getVarietyId());
				agriStandardQuantityChart.setStandardQuantityPerAcre(agriQunatityStandardChartMissing.getStandardQuantityPerAcre());
				agriStandardQuantityChart.setStandardPositiveVariancePerAcre(agriQunatityStandardChartMissing.getStandardPositiveVariancePerAcre());
				agriStandardQuantityChart.setStandardNegativeVariancePerAcre(agriQunatityStandardChartMissing.getStandardNegativeVariancePerAcre());
				agriStandardQuantityChart.setStandardPositiveVariancePercent(agriQunatityStandardChartMissing.getStandardPositiveVariancePercent());
				agriStandardQuantityChart.setStandardNegativeVariancePercent(agriQunatityStandardChartMissing.getStandardNegativeVariancePercent());
				agriStandardQuantityChart.setStatus(agriQunatityStandardChartMissing.getStatus());
				
				AgriStandardQuantityChart agriStandardQtyChart = agriStandardQuantityChartRepository.save(agriStandardQuantityChart);
//				
				agriStandardQuantityChartMissingRepository.deleteById(id);

				approvalUtil.addRecord(DBConstants.TBL_AGRI_STANDARD_QUANTITY_CHART, agriStandardQtyChart.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-StandardQuantityChart-Missing" + APIConstants.RESPONSE_MOVE_TO_MASTER_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-StandardQuantityChart-Missing" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
		
	}


}
