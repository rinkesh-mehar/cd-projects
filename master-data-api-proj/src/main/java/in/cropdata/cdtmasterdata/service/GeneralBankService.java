package in.cropdata.cdtmasterdata.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.constants.DBConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.GeneralBankInfDto;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.GeneralBank;
import in.cropdata.cdtmasterdata.repository.GeneralBankCategoryRepository;
import in.cropdata.cdtmasterdata.repository.GeneralBankRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class GeneralBankService {
	
	@Autowired
	private GeneralBankRepository generalBankRepository;
	
	@Autowired
	private GeneralBankCategoryRepository generalBankCategoryRepository;
	
	@Autowired
	private ResponseMessageUtil responseMessageUtil;
	
	@Autowired
	private AclHistoryUtil approvalUtil;
	
	public List<GeneralBankInfDto> getAllGeneralBank(){
		try
		{
			final List<GeneralBankInfDto> generalBankList = generalBankRepository.generalBankList();

			if (generalBankList.size() > 0 && !generalBankList.isEmpty())
			{
				return generalBankList;
			} else
			{
				throw new DoesNotExistException("General Bank : " + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e)
		{
			throw e;
		}
	}

	public List<GeneralBankInfDto> getAllActiveBank()
	{
		try
		{
			final List<GeneralBankInfDto> generalBankActiveList = generalBankRepository.getAllActiveBank();

			if (generalBankActiveList.size() > 0 && !generalBankActiveList.isEmpty())
			{
				return generalBankActiveList;
			} else
			{
				throw new DoesNotExistException("Active General Bank :" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e)
		{
			throw e;
		}
	}
	
	
	public Page<GeneralBankInfDto> getAllGeneralBankPaginated(int page, int size, String  searchText, int isValid) {

		try {
			searchText = "%"+searchText+"%";
			
//		System.out.println("searchText--> " + searchText);
			Page<GeneralBankInfDto> bankList;

			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			if (isValid == 0) {
				bankList = generalBankRepository.findAllByBankListInvalidated(sortedByIdDesc,searchText);
			} else {
				bankList = generalBankRepository.findAllByBankList(sortedByIdDesc,searchText);
			}

			return bankList;
		} catch (Exception e) {
			throw e;
		}
	}// getAllGaneralBankPeginated
	
	
	private GeneralBank getData(GeneralBank generalBank) {
		try {
			Optional<GeneralBank> foundBank = generalBankRepository
					.findById(generalBank.getBankCategoryId());
			if (foundBank.isPresent()) {
				GeneralBank geneBank = foundBank.get();
				generalBank.setBankCategory(geneBank.getName());
			}
			return generalBank;

		} catch (Exception e) {
			throw e;
		}
	}
	
	public ResponseMessage addGeneralBank(GeneralBank generalBank) {

		try
		{
			/**
			 * Checking the general bank with name, is it Already Exist or Not.
			 * Not saving general bank with White Spaces.
			 */
			generalBank.setName(generalBank.getName().strip());

			if (generalBankRepository.findByName(generalBank.getName()).isEmpty())
			{
				generalBank = generalBankRepository.save(generalBank);

				approvalUtil.addRecord(DBConstants.TBL_GENERAL_BANK, generalBank.getId());

				return responseMessageUtil.sendResponse(true, "General-Bank" + APIConstants.RESPONSE_ADD_SUCCESS,
						"");
			} else
			{
				return responseMessageUtil.sendResponse(
						false, "",
						"General-Bank".concat(APIConstants.RESPONSE_ALREADY_EXIST).concat(generalBank.getName()));
			}
		} catch (Exception e)
		{
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addGeneralBank
	
	public ResponseMessage updateGeneralBankById(int id, GeneralBank generalBank) {

		try {
			Optional<GeneralBank> foundBank = generalBankRepository.findById(id);

			if (foundBank.isPresent()) {

				GeneralBank update = foundBank.get();

				if (generalBank.getBankCategoryId() > 0) {
					update.setBankCategoryId(generalBank.getBankCategoryId());
				}
				
				if (generalBank.getName() != null) {
					update.setName(generalBank.getName());
				}
				
				if (generalBank.getStatus() != null) {
					update.setStatus(generalBank.getStatus());
				}
				
				generalBank = generalBankRepository.save(update);

				approvalUtil.updateRecord(DBConstants.TBL_GENERAL_BANK, generalBank.getId());

				return responseMessageUtil.sendResponse(true,
						"General-Bank" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"General-Bank" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// updateGeneralBankById

	public ResponseMessage primaryApproveById(int id) {

		try {
			Optional<GeneralBank> foundBank = generalBankRepository.findById(id);

			if (foundBank.isPresent()) {

				GeneralBank generalBank = foundBank.get();
				generalBank.setStatus(APIConstants.STATUS_APPROVED);

				generalBank = generalBankRepository.save(generalBank);

				approvalUtil.primaryApprove(DBConstants.TBL_GENERAL_BANK, generalBank.getId());

				return responseMessageUtil.sendResponse(true,
						"General-Bank" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"General-Bank" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {

		try {
			Optional<GeneralBank> foundBank = generalBankRepository.findById(id);

			if (foundBank.isPresent()) {

				GeneralBank generalBank = foundBank.get();
				generalBank.setStatus(APIConstants.STATUS_ACTIVE);

				generalBank = generalBankRepository.save(generalBank);

				approvalUtil.finalApprove(DBConstants.TBL_GENERAL_BANK, generalBank.getId());

				return responseMessageUtil.sendResponse(true,
						"General-Bank" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"General-Bank" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproverById

	public ResponseMessage deleteGeneralBankById(int id) {
		try {
			Optional<GeneralBank> foundBank = generalBankRepository.findById(id);
			if (foundBank.isPresent()) {

				GeneralBank generalBank = foundBank.get();
				generalBank.setStatus(APIConstants.STATUS_DELETED);

				generalBank = generalBankRepository.save(generalBank);

				approvalUtil.delete(DBConstants.TBL_GENERAL_BANK, generalBank.getId());

				return responseMessageUtil.sendResponse(true,
						"General-Bank" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {

				return responseMessageUtil.sendResponse(false, "",
						"General-Bank" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteGeneralBankById

	public GeneralBank findGeneralBankById(int id) {
		try {
			Optional<GeneralBank> foundBank = generalBankRepository.findById(id);
			if (foundBank.isPresent()) {
				return foundBank.get();
			} else {
				throw new DoesNotExistException("General-Bank" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findGeneralBankById
	
	public ResponseMessage rejectGeneralBankById(int id) {
		try {
			Optional<GeneralBank> foundBank = generalBankRepository.findById(id);
			if (foundBank.isPresent()) {

				GeneralBank generalBank = foundBank.get();
				generalBank.setStatus(APIConstants.STATUS_REJECTED);

				generalBank = generalBankRepository.save(generalBank);

				approvalUtil.delete(DBConstants.TBL_GENERAL_BANK, generalBank.getId());

				return responseMessageUtil.sendResponse(true,
						"General-Bank" + APIConstants.RESPONSE_REJECT_SUCCESS + id, "");
			} else {

				return responseMessageUtil.sendResponse(false, "",
						"General-Bank" + APIConstants.RESPONSE_REJECT_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}

}
