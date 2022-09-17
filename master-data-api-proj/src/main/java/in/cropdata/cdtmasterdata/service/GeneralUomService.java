package in.cropdata.cdtmasterdata.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.constants.DBConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriCommodityInfo;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.GeneralUom;
import in.cropdata.cdtmasterdata.repository.GeneralUomRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class GeneralUomService {

	@Autowired
	private GeneralUomRepository generalUomRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GeneralUomService.class);

	public List<GeneralUom> getAllGeneralUom() {
		try {
			List<GeneralUom> list = generalUomRepository.findAllByOrderByNameAsc();

			return list;
		} catch (Exception e) {
			throw e;
		}
	}// getAllGeneralUom
	
	public Page<GeneralUom> getPeginatedGeneralUomList(int page, int size, String searchText) {

		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());

			Page<GeneralUom> uomList = generalUomRepository.getPeginatedGeneralUomList(sortedByIdDesc,
					searchText);

			return uomList;

		} catch (Exception e) {
			throw e;
		}
	}

	public ResponseMessage addGeneralUom(GeneralUom generalUom) {

		try
		{
			/**
			 * Checking the Uom name is already present or not
			 * and not allowing uom name with White Space
			 */
			generalUom.setName(generalUom.getName().strip());
			if (generalUomRepository.findByName(generalUom.getName()).isEmpty())
			{
				generalUom = generalUomRepository.save(generalUom);

				approvalUtil.addRecord(DBConstants.TBL_GENERAL_UOM, generalUom.getId());

				return responseMessageUtil.sendResponse(true, "General-Uom" + APIConstants.RESPONSE_ADD_SUCCESS, "");
			} else
			{
				return responseMessageUtil.sendResponse(false,
						"", "General-Uom".concat(APIConstants.RESPONSE_ALREADY_EXIST).concat(generalUom.getName()));
			}
		} catch (Exception e)
		{
			LOGGER.error("Server Error : ", e);
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAllGeneralUom

	public ResponseMessage updateGeneralUomById(int id, GeneralUom generalUom) {
		try {
			Optional<GeneralUom> foundUom = generalUomRepository.findById(id);

			if (foundUom.isPresent()) {

				generalUom.setId(id);
				generalUom = generalUomRepository.save(generalUom);

				approvalUtil.updateRecord(DBConstants.TBL_GENERAL_UOM, generalUom.getId());

				return responseMessageUtil.sendResponse(true, "General-Uom" + APIConstants.RESPONSE_UPDATE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"General-Uom" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateGeneralUomById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<GeneralUom> foundUom = generalUomRepository.findById(id);

			if (foundUom.isPresent()) {

				GeneralUom generalUom = foundUom.get();
				generalUom.setStatus(APIConstants.STATUS_APPROVED);

				generalUom = generalUomRepository.save(generalUom);

				approvalUtil.primaryApprove(DBConstants.TBL_GENERAL_UOM, generalUom.getId());

				return responseMessageUtil.sendResponse(true,
						"General-Uom" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"General-Uom" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<GeneralUom> foundUom = generalUomRepository.findById(id);

			if (foundUom.isPresent()) {

				GeneralUom generalUom = foundUom.get();
				generalUom.setStatus(APIConstants.STATUS_ACTIVE);

				generalUom = generalUomRepository.save(generalUom);

				approvalUtil.finalApprove(DBConstants.TBL_GENERAL_UOM, generalUom.getId());

				return responseMessageUtil.sendResponse(true,
						"General-Uom" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"General-Uom" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteGeneralUomById(int id) {
		try {
			Optional<GeneralUom> foundUom = generalUomRepository.findById(id);

			if (foundUom.isPresent()) {

				GeneralUom generalUom = foundUom.get();
				generalUom.setStatus(APIConstants.STATUS_DELETED);

				generalUom = generalUomRepository.save(generalUom);

				approvalUtil.delete(DBConstants.TBL_GENERAL_UOM, generalUom.getId());

				return responseMessageUtil.sendResponse(true, "General-Uom" + APIConstants.RESPONSE_DELETE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"General-Uom" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteGeneralUomById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<GeneralUom> foundUom = generalUomRepository.findById(id);

			if (foundUom.isPresent()) {

				GeneralUom generalUom = foundUom.get();
				generalUom.setStatus(APIConstants.STATUS_REJECTED);

				generalUom = generalUomRepository.save(generalUom);

				approvalUtil.delete(DBConstants.TBL_GENERAL_UOM, generalUom.getId());

				return responseMessageUtil.sendResponse(true, "General-Uom" + APIConstants.RESPONSE_REJECT_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"General-Uom" + APIConstants.RESPONSE_REJECT_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

	public GeneralUom findGeneralUomById(int id) {
		try {
			Optional<GeneralUom> foundUom = generalUomRepository.findById(id);
			if (foundUom.isPresent()) {
				return foundUom.get();
			} else {
				throw new DoesNotExistException("General-Uom" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findGeneralUomById

}// GeneralUomService
