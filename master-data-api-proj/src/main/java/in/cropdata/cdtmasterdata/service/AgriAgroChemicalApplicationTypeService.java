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
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.AgriAgroChemicalApplicationType;
import in.cropdata.cdtmasterdata.repository.AgriAgroChemicalApplicationTypeRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AgriAgroChemicalApplicationTypeService {

	@Autowired
	private AgriAgroChemicalApplicationTypeRepository agriAgroChemicalApplicationTypeRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<AgriAgroChemicalApplicationType> getAllAgriAgroChemicalApplicationType() {
		try {
			return agriAgroChemicalApplicationTypeRepository.findAll(Sort.by("name"));
		} catch (Exception e) {
			throw e;
		}

	}// getAllAgriAgroChemicalApplicationType
	
	public Page<AgriAgroChemicalApplicationType> getPeginatedAgriAgroChemicalApplicationTypeList(int page, int size, String searchText) {

		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());

			Page<AgriAgroChemicalApplicationType> agroChemicalApplicationTypeList = agriAgroChemicalApplicationTypeRepository.getPeginatedAgriAgroChemicalApplicationTypeList(sortedByIdDesc,
					searchText);

			return agroChemicalApplicationTypeList;

		} catch (Exception e) {
			throw e;
		}
	}

	public ResponseMessage addAgriAgroChemicalApplicationType(
			AgriAgroChemicalApplicationType agriAgroChemicalApplicationType) {
		try {
			Optional<AgriAgroChemicalApplicationType> foundAgroChemicalApplicationType = agriAgroChemicalApplicationTypeRepository
					.findByName(agriAgroChemicalApplicationType.getName());

			if (foundAgroChemicalApplicationType.isPresent()) {

				return responseMessageUtil.sendResponse(false, "",
						APIConstants.RESPONSE_ALREADY_EXIST + agriAgroChemicalApplicationType.getName());

			} else {

				agriAgroChemicalApplicationType = agriAgroChemicalApplicationTypeRepository
						.save(agriAgroChemicalApplicationType);

				approvalUtil.addRecord(DBConstants.TBL_AGRI_AGROCHEMICAL_APPLICATION_TYPE,
						agriAgroChemicalApplicationType.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-AgroChemical-Application" + APIConstants.RESPONSE_ADD_SUCCESS, "");

			}
		} catch (Exception e) {

			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
//			throw new RuntimeException(e.getMessage());
		}
	}// addAgriAgroChemicalApplicationType

	public ResponseMessage updateAgriAgroChemicalApplicationTypeById(int id,
																	 AgriAgroChemicalApplicationType agriAgroChemicalApplicationType)
	{
		try
		{
			Optional<AgriAgroChemicalApplicationType> foundAgroChemicalApplicationType = agriAgroChemicalApplicationTypeRepository
					.findById(id);

			if (foundAgroChemicalApplicationType.isPresent())
			{
				if (agriAgroChemicalApplicationTypeRepository.findByName(agriAgroChemicalApplicationType.getName()).isEmpty())
				{
					agriAgroChemicalApplicationType.setId(id);
					agriAgroChemicalApplicationType = agriAgroChemicalApplicationTypeRepository
							.save(agriAgroChemicalApplicationType);

					approvalUtil.updateRecord(DBConstants.TBL_AGRI_AGROCHEMICAL_APPLICATION_TYPE,
							agriAgroChemicalApplicationType.getId());

					return responseMessageUtil.sendResponse(true,
							"Agri-Agro-Chemical-Application-Type" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
				} else
				{
					return responseMessageUtil.sendResponse(false, "",
							"Agri-Agro-Chemical-Application-Type" + APIConstants.RESPONSE_ALREADY_EXIST);
				}
			} else
			{
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Agro-Chemical-Application-Type" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e)
		{
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateAgriAgroChemicalApplicationTypeById

	public ResponseMessage primaryApproveById(int id) {

		try {
			Optional<AgriAgroChemicalApplicationType> foundAgroChemicalApplicationType = agriAgroChemicalApplicationTypeRepository
					.findById(id);

			if (foundAgroChemicalApplicationType.isPresent()) {

				AgriAgroChemicalApplicationType agriAgroChemicalApplicationType = foundAgroChemicalApplicationType
						.get();
				agriAgroChemicalApplicationType.setStatus(APIConstants.STATUS_APPROVED);

				agriAgroChemicalApplicationType = agriAgroChemicalApplicationTypeRepository
						.save(agriAgroChemicalApplicationType);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_AGROCHEMICAL_APPLICATION_TYPE,
						agriAgroChemicalApplicationType.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Agro-Chemical-Application-Type" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");

			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-Agro-Chemical-Application-Type" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<AgriAgroChemicalApplicationType> foundAgroChemicalApplicationType = agriAgroChemicalApplicationTypeRepository
					.findById(id);

			if (foundAgroChemicalApplicationType.isPresent()) {

				AgriAgroChemicalApplicationType agriAgroChemicalApplicationType = foundAgroChemicalApplicationType
						.get();
				agriAgroChemicalApplicationType.setStatus(APIConstants.STATUS_ACTIVE);

				agriAgroChemicalApplicationType = agriAgroChemicalApplicationTypeRepository
						.save(agriAgroChemicalApplicationType);

				approvalUtil.finalApprove(DBConstants.TBL_AGRI_AGROCHEMICAL_APPLICATION_TYPE,
						agriAgroChemicalApplicationType.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Agro-Chemical-Application-Type" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Agro-Chemical-Application-Type" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteAgriAgroChemicalApplicationTypeById(int id) {
		try {
			Optional<AgriAgroChemicalApplicationType> foundAgroChemicalApplicationType = agriAgroChemicalApplicationTypeRepository
					.findById(id);

			if (foundAgroChemicalApplicationType.isPresent()) {

				AgriAgroChemicalApplicationType agriAgroChemicalApplicationType = foundAgroChemicalApplicationType
						.get();
				agriAgroChemicalApplicationType.setStatus(APIConstants.STATUS_DELETED);

				agriAgroChemicalApplicationType = agriAgroChemicalApplicationTypeRepository
						.save(agriAgroChemicalApplicationType);

				approvalUtil.delete(DBConstants.TBL_AGRI_AGROCHEMICAL_APPLICATION_TYPE,
						agriAgroChemicalApplicationType.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Agro-Chemical-Application-Type" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Agro-Chemical-Application-Type" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteAgriAgroChemicalApplicationTypeById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<AgriAgroChemicalApplicationType> foundAgroChemicalApplicationType = agriAgroChemicalApplicationTypeRepository
					.findById(id);

			if (foundAgroChemicalApplicationType.isPresent()) {

				AgriAgroChemicalApplicationType agriAgroChemicalApplicationType = foundAgroChemicalApplicationType
						.get();
				agriAgroChemicalApplicationType.setStatus(APIConstants.STATUS_REJECTED);

				agriAgroChemicalApplicationType = agriAgroChemicalApplicationTypeRepository
						.save(agriAgroChemicalApplicationType);

				approvalUtil.delete(DBConstants.TBL_AGRI_AGROCHEMICAL_APPLICATION_TYPE,
						agriAgroChemicalApplicationType.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Agro-Chemical-Application-Type" + APIConstants.RESPONSE_REJECT_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Agro-Chemical-Application-Type" + APIConstants.RESPONSE_REJECT_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

	public AgriAgroChemicalApplicationType findAgriAgroChemicalApplicationTypeById(int id) {
		try {
			Optional<AgriAgroChemicalApplicationType> foundAgroChemicalApplicationType = agriAgroChemicalApplicationTypeRepository
					.findById(id);
			if (foundAgroChemicalApplicationType.isPresent()) {
				return foundAgroChemicalApplicationType.get();
			} else {

				throw new DoesNotExistException(
						"Agri-Agro-Chemical-Application-Type" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findAgriAgroChemicalApplicationTypeById
}
