package in.cropdata.cdtmasterdata.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.constants.DBConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.AgriMspMbep;
import in.cropdata.cdtmasterdata.repository.AgriMspMbepRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AgriMspMbepService {

	@Autowired
	private AgriMspMbepRepository agriMspMbepRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<AgriMspMbep> getAllAgriMspMbep() {

		try {
			List<AgriMspMbep> list = agriMspMbepRepository.getAllAgriMspMbep();

			return list;
		} catch (Exception e) {
			throw e;
		}

	}// getAllAgriMspMbep

//	public Page<AgriMspMbepInfo> getAllAgriMspMbepPaginated(int page, int size, String searchText) {
//
//		try {
//			searchText = "%"+searchText+"%";
//			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").ascending());
//
//			Page<AgriMspMbepInfo> MspMbepList = agriMspMbepRepository.getPageAgriMspMbepList(sortedByIdDesc,searchText);
//
//			return MspMbepList;
//
//		} catch (Exception e) {
//			throw e;
//		}
//	}// getAllAgriMspMbepPaginated

	public ResponseMessage addAgriMspMbep(AgriMspMbep agriMspMbep) {

		try {
			agriMspMbep = agriMspMbepRepository.save(agriMspMbep);

			approvalUtil.addRecord(DBConstants.TBL_AGRI_BENCHMARK_VARIETY, agriMspMbep.getId());

			return responseMessageUtil.sendResponse(true, "Agri-Benchmark-Variety" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAgriMspMbep

	public ResponseMessage updateAgriMspMbepById(int id, AgriMspMbep agriMspMbep) {
		try {
			Optional<AgriMspMbep> foundMspMbep = agriMspMbepRepository.findById(id);

			if (foundMspMbep.isPresent()) {

				agriMspMbep.setId(id);
				agriMspMbepRepository.save(agriMspMbep);

				approvalUtil.updateRecord(DBConstants.TBL_AGRI_BENCHMARK_VARIETY, agriMspMbep.getId());

				return responseMessageUtil.sendResponse(true, "Agri-Benchmark-Variety" + APIConstants.RESPONSE_UPDATE_SUCCESS + id,
						"");
			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-Benchmark-Variety" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateAgriMspMbepById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<AgriMspMbep> foundMspMbep = agriMspMbepRepository.findById(id);

			if (foundMspMbep.isPresent()) {

				AgriMspMbep MspMbep = foundMspMbep.get();
				MspMbep.setStatus(APIConstants.STATUS_APPROVED);

				MspMbep = agriMspMbepRepository.save(MspMbep);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_BENCHMARK_VARIETY, MspMbep.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Benchmark-Variety" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Benchmark-Variety" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalyApproveById(int id) {
		try {
			Optional<AgriMspMbep> foundMspMbep = agriMspMbepRepository.findById(id);

			if (foundMspMbep.isPresent()) {

				AgriMspMbep MspMbep = foundMspMbep.get();
				MspMbep.setStatus(APIConstants.STATUS_ACTIVE);

				MspMbep = agriMspMbepRepository.save(MspMbep);

				approvalUtil.finalApprove(DBConstants.TBL_AGRI_BENCHMARK_VARIETY, MspMbep.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Benchmark-Variety" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Benchmark-Variety" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage deleteAgriMspMbepById(int id) {
		try {
			Optional<AgriMspMbep> foundMspMbep = agriMspMbepRepository.findById(id);

			if (foundMspMbep.isPresent()) {

				AgriMspMbep MspMbep = foundMspMbep.get();
				MspMbep.setStatus(APIConstants.STATUS_DELETED);

				MspMbep = agriMspMbepRepository.save(MspMbep);

				approvalUtil.delete(DBConstants.TBL_AGRI_BENCHMARK_VARIETY, MspMbep.getId());

				return responseMessageUtil.sendResponse(true, "Agri-Benchmark-Variety" + APIConstants.RESPONSE_DELETE_SUCCESS + id,
						"Agri-Benchmark-Variety" + APIConstants.RESPONSE_DELETE_ERROR + id);
			} else {
				return responseMessageUtil.sendResponse(false, "Agri-Benchmark-Variety" + APIConstants.RESPONSE_DELETE_SUCCESS + id,
						"Agri-Benchmark-Variety" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteAgriMspMbepById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<AgriMspMbep> foundMspMbep = agriMspMbepRepository.findById(id);

			if (foundMspMbep.isPresent()) {

				AgriMspMbep MspMbep = foundMspMbep.get();
				MspMbep.setStatus(APIConstants.STATUS_REJECTED);

				MspMbep = agriMspMbepRepository.save(MspMbep);

				approvalUtil.delete(DBConstants.TBL_AGRI_BENCHMARK_VARIETY, MspMbep.getId());

				return responseMessageUtil.sendResponse(true, "Agri-Benchmark-Variety" + APIConstants.RESPONSE_REJECT_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Benchmark-Variety" + APIConstants.RESPONSE_REJECT_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

	public AgriMspMbep findAgriMspMbepById(int id) {
		try {
			Optional<AgriMspMbep> foundMspMbep = agriMspMbepRepository.findById(id);
			if (foundMspMbep.isPresent()) {
				return foundMspMbep.get();

			} else {

				throw new DoesNotExistException("Agri-Benchmark-Variety" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findAgriMspMbepById

}
