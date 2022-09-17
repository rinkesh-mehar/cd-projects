package in.cropdata.cdtmasterdata.service;

import java.util.List;
import java.util.Optional;

import in.cropdata.cdtmasterdata.dto.interfaces.AgriFertilizerInfDto;
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
import in.cropdata.cdtmasterdata.dto.interfaces.RegionalBankInfDto;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.GeneralBank;
import in.cropdata.cdtmasterdata.model.GeoState;
import in.cropdata.cdtmasterdata.model.RegionalBank;
import in.cropdata.cdtmasterdata.model.RegionalBankMissing;
import in.cropdata.cdtmasterdata.repository.GeneralBankRepository;
import in.cropdata.cdtmasterdata.repository.GeoStateRepository;
import in.cropdata.cdtmasterdata.repository.RegionalBankMissingRepository;
import in.cropdata.cdtmasterdata.repository.RegionalBankRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class RegionalBankService {
	
	@Autowired
	private RegionalBankRepository regionalBankRepository;
	
	@Autowired
	private RegionalBankMissingRepository regionalBankMissingRepository;
	
	@Autowired
	private GeoStateRepository geoStateRepository;
	
	@Autowired
	private GeneralBankRepository generalBankRepository;
	
	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<RegionalBank> getAllAgriRegionalBank() {

		try {
			List<RegionalBank> BankList = regionalBankRepository.findAll();

//			for (RegionalBank Bank : BankList) {
//
//				Bank = getData(Bank);
//
//			} // for
			return BankList;
		} catch (Exception e) {
			throw e;
		}
	}
	

public Page<RegionalBankInfDto> getAllRegionalBankPaginated(int page, int size,String missing, String  searchText, int isValid) {


		try {
			searchText = "%"+searchText+"%";
			
//		System.out.println("searchText--> " + searchText);
			Page<RegionalBankInfDto> regionalBankInfDtos;


			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
		
			if("0".equals(missing)) {
				if (isValid == 0) {
					regionalBankInfDtos = regionalBankRepository.getRegionalBankInvalidated(sortedByIdDesc,searchText);
				} else{
					regionalBankInfDtos = regionalBankRepository.getRegionalBank(sortedByIdDesc,searchText);
				}

			}else {
				if (isValid == 0) {
					regionalBankInfDtos = regionalBankRepository.getRegionalBankMissingInvalidated(sortedByIdDesc,searchText);
				} else{
					regionalBankInfDtos = regionalBankRepository.getRegionalBankMissing(sortedByIdDesc,searchText);
				}
			}

			return regionalBankInfDtos;
		} catch (Exception e) {
			throw e;
		}
	}// getAllRegionalBankPaginated

	private RegionalBank getData(RegionalBank Bank) {
		try {
			
			GeoState state = geoStateRepository.findByStateCode(Bank.getStateCode());
			if(state != null) {
				Bank.setState(state.getName());
			}

			Optional<GeneralBank> GeneBank = generalBankRepository.findById(Bank.getBankId());
			if (GeneBank.isPresent()) {
			    Bank.setBank(GeneBank.get().getName());
			}
			return Bank;

		} catch (Exception e) {
			throw e;
		}

	}

	public ResponseMessage addRegionalBank(RegionalBank regionalBank) {

		try {

			if (regionalBankRepository.findByBankIdAndStateCode(regionalBank.getBankId(), regionalBank.getStateCode()).isEmpty())
			{
				regionalBank = regionalBankRepository.save(regionalBank);

				approvalUtil.addRecord(DBConstants.TBL_REGIONAL_BANK, regionalBank.getId());

				return responseMessageUtil.sendResponse(true,
						"Regional-Bank".concat(APIConstants.RESPONSE_ADD_SUCCESS), "");

			} else
			{
				return responseMessageUtil.sendResponse(false, "",
						"Regional-Bank".concat(APIConstants.RESPONSE_ALREADY_EXIST).concat("This Bank"));
			}
		} catch (Exception e) {

			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addRegionalBank

	public ResponseMessage updateRegionalBankById(int id, RegionalBank regionalBank) {

		try {
			Optional<RegionalBank> foundBank = regionalBankRepository.findById(id);

			if (foundBank.isPresent()) {

				RegionalBank update = foundBank.get();

				if (regionalBank.getStateCode() > 0) {
					update.setStateCode(regionalBank.getStateCode());
				}
				
				if (regionalBank.getBankId() > 0) {
					update.setBankId(regionalBank.getBankId());
				}
				
				if (regionalBank.getStatus() != null) {
					update.setStatus(regionalBank.getStatus());
				}
				
				regionalBank = regionalBankRepository.save(update);

				approvalUtil.updateRecord(DBConstants.TBL_REGIONAL_BANK, regionalBank.getId());

				return responseMessageUtil.sendResponse(true,
						"Regional-Bank" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Regional-Bank" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// updateRegionalBankById

	public ResponseMessage primaryApproveById(int id) {

		try {
			Optional<RegionalBank> foundBank = regionalBankRepository.findById(id);

			if (foundBank.isPresent()) {

				RegionalBank regionalBank = foundBank.get();
				regionalBank.setStatus(APIConstants.STATUS_APPROVED);

				regionalBank = regionalBankRepository.save(regionalBank);

				approvalUtil.primaryApprove(DBConstants.TBL_REGIONAL_BANK, regionalBank.getId());

				return responseMessageUtil.sendResponse(true,
						"Regional-Bank" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Regional-Bank" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {

		try {
			Optional<RegionalBank> foundBank = regionalBankRepository.findById(id);

			if (foundBank.isPresent()) {

				RegionalBank regionalBank = foundBank.get();
				regionalBank.setStatus(APIConstants.STATUS_ACTIVE);

				regionalBank = regionalBankRepository.save(regionalBank);

				approvalUtil.finalApprove(DBConstants.TBL_REGIONAL_BANK, regionalBank.getId());

				return responseMessageUtil.sendResponse(true,
						"Regional-Bank" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Regional-Bank" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproverById

	public ResponseMessage deleteRegionalBankById(int id) {
		try {
			Optional<RegionalBank> foundBank = regionalBankRepository.findById(id);
			if (foundBank.isPresent()) {

				RegionalBank regionalBank = foundBank.get();
				regionalBank.setStatus(APIConstants.STATUS_DELETED);

				regionalBank = regionalBankRepository.save(regionalBank);

				approvalUtil.delete(DBConstants.TBL_REGIONAL_BANK, regionalBank.getId());

				return responseMessageUtil.sendResponse(true,
						"Regional-Bank" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Regional-Bank" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteRegionalBankById

	public RegionalBank findRegionalBankById(int id) {
		try {
			Optional<RegionalBank> foundBank = regionalBankRepository.findById(id);
			if (foundBank.isPresent()) {
				return foundBank.get();
			} else {
				throw new DoesNotExistException("Regional-Bank" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findRegionalBankById
	
	public ResponseMessage rejectById(int id) {
		try {
			Optional<RegionalBank> foundBank = regionalBankRepository.findById(id);

			if (foundBank.isPresent()) {

				RegionalBank regionalBank = foundBank.get();
				regionalBank.setStatus(APIConstants.STATUS_REJECTED);
				regionalBank = regionalBankRepository.save(regionalBank);

				approvalUtil.finalApprove(DBConstants.TBL_REGIONAL_BANK, regionalBank.getId());

				return responseMessageUtil.sendResponse(true, "Regional-Bank " + APIConstants.RESPONSE_REJECT_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "", "Regional-Bank " + APIConstants.RESPONSE_REJECT_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
		
	}
	
	@Transactional
	public ResponseMessage moveToMaster(int id) {
		try {
			Optional<RegionalBankMissing> foundMissingBank = regionalBankMissingRepository.findById(id);

			if (foundMissingBank.isPresent()) {
				RegionalBank regionalBank = new RegionalBank();
				RegionalBankMissing regionalCommodityMissing = foundMissingBank.get();
				
				regionalBank.setBankId(regionalCommodityMissing.getBankId());
				regionalBank.setStateCode(regionalCommodityMissing.getStateCode());
				regionalBank.setStatus(regionalCommodityMissing.getStatus());
				
				RegionalBank savedRegionalBank = regionalBankRepository.save(regionalBank);
				
				regionalBankMissingRepository.deleteById(id);


				approvalUtil.addRecord(DBConstants.TBL_REGIONAL_BANK, savedRegionalBank.getId());

				return responseMessageUtil.sendResponse(true,
						"Region-Bank-Missing" + APIConstants.RESPONSE_MOVE_TO_MASTER_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Region-Bank-Missing" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}

}
