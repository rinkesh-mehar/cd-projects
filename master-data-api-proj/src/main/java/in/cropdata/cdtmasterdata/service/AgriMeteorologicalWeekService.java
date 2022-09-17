package in.cropdata.cdtmasterdata.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.constants.DBConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.AgriMeteorologicalWeek;
import in.cropdata.cdtmasterdata.repository.AgriMeteorologicalWeekRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AgriMeteorologicalWeekService {

	@Autowired
	private AgriMeteorologicalWeekRepository agriMeteorologicalWeekRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<AgriMeteorologicalWeek> getAllAgriMeteorologicalWeek() {
		try {
			List<AgriMeteorologicalWeek> list = agriMeteorologicalWeekRepository.findAll();

			return list;
		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriMeteorologicalWeek

	public ResponseMessage addAgriMeteorologicalWeek(AgriMeteorologicalWeek agriMeteorologicalWeek) {

		try {
			agriMeteorologicalWeek = agriMeteorologicalWeekRepository.save(agriMeteorologicalWeek);

			approvalUtil.addRecord(DBConstants.TBL_AGRI_METEOROLOGICAL_WEEK, agriMeteorologicalWeek.getId());

			return responseMessageUtil.sendResponse(true, "Agri-Meteorological-Week" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// addAgriMeteorologicalWeek

	public ResponseMessage updateAgriMeteorologicalWeekById(int id, AgriMeteorologicalWeek agriMeteorologicalWeek) {
		try {
			Optional<AgriMeteorologicalWeek> foundMeteorological = agriMeteorologicalWeekRepository.findById(id);

			if (foundMeteorological.isPresent()) {

				agriMeteorologicalWeek.setId(id);
				agriMeteorologicalWeekRepository.save(agriMeteorologicalWeek);

				approvalUtil.updateRecord(DBConstants.TBL_AGRI_METEOROLOGICAL_WEEK, agriMeteorologicalWeek.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Meteorological-Week" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-Meteorological-Week" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateAgriMeteorologicalWeekById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<AgriMeteorologicalWeek> foundMeteorological = agriMeteorologicalWeekRepository.findById(id);

			if (foundMeteorological.isPresent()) {

				AgriMeteorologicalWeek agriMeteorologicalWeek = foundMeteorological.get();
				agriMeteorologicalWeek.setStatus(APIConstants.STATUS_APPROVED);

				agriMeteorologicalWeek = agriMeteorologicalWeekRepository.save(agriMeteorologicalWeek);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_METEOROLOGICAL_WEEK, agriMeteorologicalWeek.getId());
				return responseMessageUtil.sendResponse(true,
						"Agri-Meteorological-Week" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Meteorological-Week" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<AgriMeteorologicalWeek> foundMeteorological = agriMeteorologicalWeekRepository.findById(id);

			if (foundMeteorological.isPresent()) {

				AgriMeteorologicalWeek agriMeteorologicalWeek = foundMeteorological.get();
				agriMeteorologicalWeek.setStatus(APIConstants.STATUS_ACTIVE);

				agriMeteorologicalWeek = agriMeteorologicalWeekRepository.save(agriMeteorologicalWeek);

				return responseMessageUtil.sendResponse(true,
						"Agri-Meteorological-Week" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Meteorological-Week" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteAgriMeteorologicalWeekById(int id) {
		try {
			Optional<AgriMeteorologicalWeek> foundMeteorological = agriMeteorologicalWeekRepository.findById(id);
			if (foundMeteorological.isPresent()) {

				AgriMeteorologicalWeek agriMeteorologicalWeek = foundMeteorological.get();
				agriMeteorologicalWeek.setStatus(APIConstants.STATUS_DELETED);
				agriMeteorologicalWeek = agriMeteorologicalWeekRepository.save(agriMeteorologicalWeek);

				approvalUtil.delete(DBConstants.TBL_AGRI_METEOROLOGICAL_WEEK, agriMeteorologicalWeek.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Meteorological-Week" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Meteorological-Week" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// deleteAgriMeteorologicalWeekById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<AgriMeteorologicalWeek> foundMeteorological = agriMeteorologicalWeekRepository.findById(id);
			if (foundMeteorological.isPresent()) {

				AgriMeteorologicalWeek agriMeteorologicalWeek = foundMeteorological.get();
				agriMeteorologicalWeek.setStatus(APIConstants.STATUS_REJECTED);
				agriMeteorologicalWeek = agriMeteorologicalWeekRepository.save(agriMeteorologicalWeek);

				approvalUtil.delete(DBConstants.TBL_AGRI_METEOROLOGICAL_WEEK, agriMeteorologicalWeek.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Meteorological-Week" + APIConstants.RESPONSE_REJECT_SUCCESS + id, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Meteorological-Week" + APIConstants.RESPONSE_REJECT_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// rejectById

	public AgriMeteorologicalWeek findAgriMeteorologicalWeekById(int id) {
		try {
			Optional<AgriMeteorologicalWeek> foundMeteorological = agriMeteorologicalWeekRepository.findById(id);
			if (foundMeteorological.isPresent()) {
				return foundMeteorological.get();

			} else {

				throw new DoesNotExistException("Agri-Meteorological-Week" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findAgriMeteorologicalWeekById
}
