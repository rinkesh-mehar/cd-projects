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
import in.cropdata.cdtmasterdata.dto.interfaces.AgriBenchmarkVarietyInfo;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.AgriBenchmarkVariety;
import in.cropdata.cdtmasterdata.model.AgriBenchmarkVarietyMissing;
import in.cropdata.cdtmasterdata.repository.AgriBenchmarkVarietyMissingRepository;
import in.cropdata.cdtmasterdata.repository.AgriBenchmarkVarietyRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AgriBenchmarkVarietyService {
	
	@Autowired
	private AgriBenchmarkVarietyRepository agriBenchmarkVarietyRepository;
	
	@Autowired
	private AgriBenchmarkVarietyMissingRepository agriBenchmarkVarietyMissingRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<AgriBenchmarkVarietyInfo> getAllAgriBenchmarkVariety() {

		try {
			List<AgriBenchmarkVarietyInfo> list = agriBenchmarkVarietyRepository.getAllAgriBenchmarkVarietyList();

			return list;
		} catch (Exception e) {
			throw e;
		}

	}// getAllAgriBenchmarkVariety

	public Page<AgriBenchmarkVarietyInfo> getAllAgriBenchmarkVarietyPaginated(int page, int size, String searchText,String missing) {

		try {
			searchText = "%"+searchText+"%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());

			Page<AgriBenchmarkVarietyInfo> benchmarkVarietyList = null;
			
			if("0".equals(missing)) {
			benchmarkVarietyList = agriBenchmarkVarietyRepository.getPageAgriBenchmarkVarietyList(sortedByIdDesc,searchText);
			}else {
				benchmarkVarietyList = agriBenchmarkVarietyRepository.getPageAgriBenchmarkVarietyMissingList(sortedByIdDesc,searchText);
			}
			return benchmarkVarietyList;

		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriBenchmarkVarietyPaginated

	public ResponseMessage addAgriBenchmarkVariety(AgriBenchmarkVariety agriBenchmarkVariety) {

		try {
			agriBenchmarkVariety = agriBenchmarkVarietyRepository.save(agriBenchmarkVariety);

			approvalUtil.addRecord(DBConstants.TBL_AGRI_BENCHMARK_VARIETY, agriBenchmarkVariety.getId());

			return responseMessageUtil.sendResponse(true, "Agri-Benchmark-Variety" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAgriBenchmarkVariety

	public ResponseMessage updateAgriBenchmarkVarietyById(int id, AgriBenchmarkVariety agriBenchmarkVariety) {
		try {
			Optional<AgriBenchmarkVariety> foundBenchmarkVariety = agriBenchmarkVarietyRepository.findById(id);

			if (foundBenchmarkVariety.isPresent()) {

				agriBenchmarkVariety.setId(id);
				agriBenchmarkVarietyRepository.save(agriBenchmarkVariety);

				approvalUtil.updateRecord(DBConstants.TBL_AGRI_BENCHMARK_VARIETY, agriBenchmarkVariety.getId());

				return responseMessageUtil.sendResponse(true, "Agri-Benchmark-Variety" + APIConstants.RESPONSE_UPDATE_SUCCESS + id,
						"");
			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-Benchmark-Variety" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateAgriBenchmarkVarietyById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<AgriBenchmarkVariety> foundBenchmarkVariety = agriBenchmarkVarietyRepository.findById(id);

			if (foundBenchmarkVariety.isPresent()) {

				AgriBenchmarkVariety BenchmarkVariety = foundBenchmarkVariety.get();
				BenchmarkVariety.setStatus(APIConstants.STATUS_APPROVED);

				BenchmarkVariety = agriBenchmarkVarietyRepository.save(BenchmarkVariety);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_BENCHMARK_VARIETY, BenchmarkVariety.getId());

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
			Optional<AgriBenchmarkVariety> foundBenchmarkVariety = agriBenchmarkVarietyRepository.findById(id);

			if (foundBenchmarkVariety.isPresent()) {

				AgriBenchmarkVariety BenchmarkVariety = foundBenchmarkVariety.get();
				BenchmarkVariety.setStatus(APIConstants.STATUS_ACTIVE);

				BenchmarkVariety = agriBenchmarkVarietyRepository.save(BenchmarkVariety);

				approvalUtil.finalApprove(DBConstants.TBL_AGRI_BENCHMARK_VARIETY, BenchmarkVariety.getId());

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

	public ResponseMessage deleteAgriBenchmarkVarietyById(int id) {
		try {
			Optional<AgriBenchmarkVariety> foundBenchmarkVariety = agriBenchmarkVarietyRepository.findById(id);

			if (foundBenchmarkVariety.isPresent()) {

				AgriBenchmarkVariety BenchmarkVariety = foundBenchmarkVariety.get();
				BenchmarkVariety.setStatus(APIConstants.STATUS_DELETED);

				BenchmarkVariety = agriBenchmarkVarietyRepository.save(BenchmarkVariety);

				approvalUtil.delete(DBConstants.TBL_AGRI_BENCHMARK_VARIETY, BenchmarkVariety.getId());

				return responseMessageUtil.sendResponse(true, "Agri-Benchmark-Variety" + APIConstants.RESPONSE_DELETE_SUCCESS + id,
						"Agri-Benchmark-Variety" + APIConstants.RESPONSE_DELETE_ERROR + id);
			} else {
				return responseMessageUtil.sendResponse(false, "Agri-Benchmark-Variety" + APIConstants.RESPONSE_DELETE_SUCCESS + id,
						"Agri-Benchmark-Variety" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteAgriBenchmarkVarietyById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<AgriBenchmarkVariety> foundBenchmarkVariety = agriBenchmarkVarietyRepository.findById(id);

			if (foundBenchmarkVariety.isPresent()) {

				AgriBenchmarkVariety BenchmarkVariety = foundBenchmarkVariety.get();
				BenchmarkVariety.setStatus(APIConstants.STATUS_REJECTED);

				BenchmarkVariety = agriBenchmarkVarietyRepository.save(BenchmarkVariety);

				approvalUtil.delete(DBConstants.TBL_AGRI_BENCHMARK_VARIETY, BenchmarkVariety.getId());

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

	public AgriBenchmarkVariety findAgriBenchmarkVarietyById(int id) {
		try {
			Optional<AgriBenchmarkVariety> foundBenchmarkVariety = agriBenchmarkVarietyRepository.findById(id);
			if (foundBenchmarkVariety.isPresent()) {
				return foundBenchmarkVariety.get();

			} else {

				throw new DoesNotExistException("Agri-Benchmark-Variety" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findAgriBenchmarkVarietyById

	
	@Transactional
	public ResponseMessage moveToMaster(int id) {
		try {
			Optional<AgriBenchmarkVarietyMissing> foundMissingCommodity = agriBenchmarkVarietyMissingRepository.findById(id);

			if (foundMissingCommodity.isPresent()) {
				AgriBenchmarkVariety agriBenchmarkVariety = new AgriBenchmarkVariety();
				AgriBenchmarkVarietyMissing agriBenchmarkVarietyMissing = foundMissingCommodity.get();
				
				agriBenchmarkVariety.setStateCode(agriBenchmarkVarietyMissing.getStateCode());
				agriBenchmarkVariety.setRegionId(agriBenchmarkVarietyMissing.getRegionId());
				agriBenchmarkVariety.setSeasonId(agriBenchmarkVarietyMissing.getSeasonId());
				agriBenchmarkVariety.setCommodityId(agriBenchmarkVarietyMissing.getCommodityId());
				agriBenchmarkVariety.setVarietyId(agriBenchmarkVarietyMissing.getVarietyId());
				agriBenchmarkVariety.setIsAgmBenchmark(agriBenchmarkVarietyMissing.getIsDrkBenchmark());
				agriBenchmarkVariety.setIsAgmBenchmark(agriBenchmarkVarietyMissing.getIsAgmBenchmark());
				agriBenchmarkVariety.setStatus(agriBenchmarkVarietyMissing.getStatus());
				
				AgriBenchmarkVariety savedAgriBenchmarkVariety = agriBenchmarkVarietyRepository.save(agriBenchmarkVariety);
				
				agriBenchmarkVarietyMissingRepository.deleteById(id);


				approvalUtil.addRecord(DBConstants.TBL_AGRI_BENCHMARK_VARIETY, savedAgriBenchmarkVariety.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Benchmark-Variety-Missing" + APIConstants.RESPONSE_MOVE_TO_MASTER_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Benchmark-Variety-Missing" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}

}
