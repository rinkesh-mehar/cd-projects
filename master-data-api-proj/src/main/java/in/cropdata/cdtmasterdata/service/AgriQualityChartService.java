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
import in.cropdata.cdtmasterdata.dto.interfaces.AgriQualityChartInfDto;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.AgriQualityChart;
import in.cropdata.cdtmasterdata.model.AgriQualityChartMissing;
import in.cropdata.cdtmasterdata.repository.AgriQualityChartMissingRepository;
import in.cropdata.cdtmasterdata.repository.AgriQualityChartRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AgriQualityChartService {

	@Autowired
	private AgriQualityChartRepository agriQualityChartRepository;
	
	@Autowired
	private AgriQualityChartMissingRepository agriQualityChartMissingRepository;

	@Autowired
	private AclHistoryUtil approvaUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<AgriQualityChart> getAllAgriQualityChart() {

		try {
			List<AgriQualityChart> list = agriQualityChartRepository.findAll();
			
			return list;

		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriQualityChart

	
	public Page<AgriQualityChartInfDto> getAllAgriQualityChartPaginated(int page, int size, String searchText,String missing) {

		try
		{
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			
			Page<AgriQualityChartInfDto> qualityChartList = null;
			
			if("0".equals(missing)) {
				qualityChartList = agriQualityChartRepository.getAgriQualityChartList(sortedByIdDesc, searchText);
			}else {
				qualityChartList = agriQualityChartRepository.getAgriQualityChartListMissing(sortedByIdDesc, searchText);
			}

			return qualityChartList;

		} catch (Exception e) {
			throw e;
		}
	}//getAllAgriQualityChartPaginated
	
	public ResponseMessage addAgriQualityChart(AgriQualityChart agriQualityChart) {

		try {
			agriQualityChart = agriQualityChartRepository.save(agriQualityChart);

			approvaUtil.addRecord(DBConstants.TBL__AGRI_QUALITY_CHART, agriQualityChart.getId());

			return responseMessageUtil.sendResponse(true, "Agri-Quality-Chart" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAgriQualityChart

	public ResponseMessage updateAgriQualityChartById(int id, AgriQualityChart agriQualityChart) {
		try {
			Optional<AgriQualityChart> foundVareity = agriQualityChartRepository.findById(id);

			if (foundVareity.isPresent()) {
				agriQualityChart.setId(id);
				agriQualityChart = agriQualityChartRepository.save(agriQualityChart);

				approvaUtil.updateRecord(DBConstants.TBL__AGRI_QUALITY_CHART, agriQualityChart.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Quality-Chart" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Quality-Chart" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateAgriQualityChartById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<AgriQualityChart> foundQualityChart = agriQualityChartRepository.findById(id);
			if (foundQualityChart.isPresent()) {
				AgriQualityChart QualityChart = foundQualityChart.get();
				QualityChart.setStatus(APIConstants.STATUS_APPROVED);

				QualityChart = agriQualityChartRepository.save(QualityChart);

				approvaUtil.primaryApprove(DBConstants.TBL__AGRI_QUALITY_CHART, QualityChart.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Quality-Chart" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-Quality-Chart" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {

		try {

			Optional<AgriQualityChart> foundQualityChart = agriQualityChartRepository.findById(id);
			if (foundQualityChart.isPresent()) {
				AgriQualityChart QualityChart = foundQualityChart.get();
				QualityChart.setStatus(APIConstants.STATUS_ACTIVE);

				QualityChart = agriQualityChartRepository.save(QualityChart);

				approvaUtil.finalApprove(DBConstants.TBL__AGRI_QUALITY_CHART, QualityChart.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Quality-Chart" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Quality-Chart" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteAgriQualityChartById(int id) {
		try {
			Optional<AgriQualityChart> foundQualityChart = agriQualityChartRepository.findById(id);
			if (foundQualityChart.isPresent()) {
				AgriQualityChart QualityChart = foundQualityChart.get();
				QualityChart.setStatus(APIConstants.STATUS_DELETED);

				QualityChart = agriQualityChartRepository.save(QualityChart);

				approvaUtil.delete(DBConstants.TBL_AGRI_QUANTITY_LOSS_CHART, QualityChart.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Quality-Chart" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-Quality-Chart" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}

	public ResponseMessage rejectById(int id) {
		try {
			Optional<AgriQualityChart> foundQualityChart = agriQualityChartRepository.findById(id);
			if (foundQualityChart.isPresent()) {
				AgriQualityChart QualityChart = foundQualityChart.get();
				QualityChart.setStatus(APIConstants.STATUS_REJECTED);

				QualityChart = agriQualityChartRepository.save(QualityChart);

				approvaUtil.delete(DBConstants.TBL__AGRI_QUALITY_CHART, QualityChart.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Quality-Chart" + APIConstants.RESPONSE_REJECT_SUCCESS, "");
			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-Quality-Chart" + APIConstants.RESPONSE_REJECT_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// rejectById

	public AgriQualityChart findAgriQualityChartById(int id) {

		try {
			Optional<AgriQualityChart> foundQualityChart = agriQualityChartRepository.findById(id);

			if (foundQualityChart.isPresent()) {
				return foundQualityChart.get();

			} else {
				throw new DoesNotExistException("Agri-Quality-Chart" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findAgriQualityChartById
	
	@Transactional
	public ResponseMessage moveToMaster(int id) {
		try {
			Optional<AgriQualityChartMissing> foundAgriQualityChartMissing = agriQualityChartMissingRepository.findById(id);

			if (foundAgriQualityChartMissing.isPresent()) {
				AgriQualityChart agriQualityChart = new AgriQualityChart();
				AgriQualityChartMissing agriQualityChartMissing = foundAgriQualityChartMissing.get();
				agriQualityChart.setCommodityId(agriQualityChartMissing.getCommodityId());
				agriQualityChart.setPhenophaseId(agriQualityChartMissing.getPhenophaseId());
				agriQualityChart.setHealthParameterId(agriQualityChartMissing.getHealthParameterId());
				agriQualityChart.setGradeI(agriQualityChartMissing.getGradeI());
				agriQualityChart.setGradeII(agriQualityChartMissing.getGradeII());
				agriQualityChart.setGradeIII(agriQualityChartMissing.getGradeIII());
				agriQualityChart.setMingradeI(agriQualityChartMissing.getMingradeI());
				agriQualityChart.setMaxgradeI(agriQualityChartMissing.getMaxgradeI());
				agriQualityChart.setMingradeII(agriQualityChartMissing.getMingradeII());
				agriQualityChart.setMaxgradeII(agriQualityChartMissing.getMaxgradeII());
				agriQualityChart.setMingradeIII(agriQualityChartMissing.getMingradeIII());
				agriQualityChart.setMaxgradeIII(agriQualityChartMissing.getMaxgradeIII());
				agriQualityChart.setStatus(agriQualityChartMissing.getStatus());
				AgriQualityChart savedAgriQualityChart = agriQualityChartRepository.save(agriQualityChart);
				
				agriQualityChartMissingRepository.deleteById(id);


				approvaUtil.addRecord(DBConstants.TBL__AGRI_QUALITY_CHART, savedAgriQualityChart.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Quality-Chart" + APIConstants.RESPONSE_MOVE_TO_MASTER_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Quality-Chart" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}
}//AgriQualityChartService
