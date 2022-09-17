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
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.AgriAgroChemicalType;
import in.cropdata.cdtmasterdata.repository.AgriAgroChemicalTypeRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AgriAgroChemicalTypeService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AgriAgroChemicalTypeService.class);
	private static final String SERVER_ERROR_MSG = "Server Error : ";

	@Autowired
	private AgriAgroChemicalTypeRepository agriAgroChemicalTypeRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<AgriAgroChemicalType> getAllAgriAgroChemicalType() {
		return agriAgroChemicalTypeRepository.findAll(Sort.by("name"));
	}// getAllAgroChemicalType
	
	public Page<AgriAgroChemicalType> getAgroChemicalTypeListByPagenation(Integer page, Integer size, String searchText){
		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			return agriAgroChemicalTypeRepository.getAgroChemicalTypeListByPagenation(sortedByIdDesc, searchText);
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Department Data Found For Searched Text -> " + searchText);
		}
	}

	public ResponseMessage addAgriAgroChemicalType(AgriAgroChemicalType agriAgroChemicalType)
	{
		try
		{
			if (agriAgroChemicalTypeRepository.findByName(agriAgroChemicalType.getName()).isEmpty())
			{

				agriAgroChemicalType = agriAgroChemicalTypeRepository.save(agriAgroChemicalType);

				approvalUtil.addRecord(DBConstants.TBL_AGRI_AGROCHEMICAL_TYPE, agriAgroChemicalType.getId());

				return responseMessageUtil.sendResponse(true, "Agri-AgroChemical-Type" +
						APIConstants.RESPONSE_ADD_SUCCESS, "");
			} else
			{
				return responseMessageUtil.sendResponse(false, "", "Agri-AgroChemical-Type" +
						APIConstants.RESPONSE_ALREADY_EXIST);
			}

		} catch (Exception e)
		{
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAgriAgroChemicalType

	public ResponseMessage updateAgriAgroChemicalTypeById(int id, AgriAgroChemicalType agriAgroChemicalType)
	{

		try
		{
			Optional<AgriAgroChemicalType> foundAgroChemicalType = agriAgroChemicalTypeRepository.findById(id);

			if (foundAgroChemicalType.isPresent())
			{
				if ((agriAgroChemicalTypeRepository.findByName(agriAgroChemicalType.getName())).isEmpty())
				{

					agriAgroChemicalType.setId(id);

					agriAgroChemicalType = agriAgroChemicalTypeRepository.save(agriAgroChemicalType);

					approvalUtil.updateRecord(DBConstants.TBL_AGRI_AGROCHEMICAL_TYPE, agriAgroChemicalType.getId());

					return responseMessageUtil.sendResponse(true,
							"Agri-AgroChemical-Type" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
				} else
				{
					return responseMessageUtil.sendResponse(false, "",
							"Agri-AgroChemical-Type" + APIConstants.RESPONSE_ALREADY_EXIST + id);
				}
			} else
			{
				return responseMessageUtil.sendResponse(false, "",
						"Agri-AgroChemical-Type" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e)
		{
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateAgriAgroChemicalTypeById

	public ResponseMessage primaryApproveById(int id) {

		try {
			Optional<AgriAgroChemicalType> foundAgroChemicalType = agriAgroChemicalTypeRepository.findById(id);

			if (foundAgroChemicalType.isPresent()) {

				AgriAgroChemicalType agriAgroChemicalType = foundAgroChemicalType.get();
				agriAgroChemicalType.setStatus(APIConstants.STATUS_APPROVED);

				agriAgroChemicalType = agriAgroChemicalTypeRepository.save(agriAgroChemicalType);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_AGROCHEMICAL_TYPE, agriAgroChemicalType.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-AgroChemical-Type" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-AgroChemical-Type" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {

		try {
			Optional<AgriAgroChemicalType> foundAgroChemicalType = agriAgroChemicalTypeRepository.findById(id);

			if (foundAgroChemicalType.isPresent()) {

				AgriAgroChemicalType agriAgroChemicalType = foundAgroChemicalType.get();
				agriAgroChemicalType.setStatus(APIConstants.STATUS_ACTIVE);

				agriAgroChemicalType = agriAgroChemicalTypeRepository.save(agriAgroChemicalType);

				approvalUtil.finalApprove(DBConstants.TBL_AGRI_AGROCHEMICAL_TYPE, agriAgroChemicalType.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-AgroChemical-Type" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-AgroChemical-Type" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproverById

	public ResponseMessage deleteAgriAgroChemicalTypeById(int id) {

		try {
			Optional<AgriAgroChemicalType> foundAgroChemicalType = agriAgroChemicalTypeRepository.findById(id);
			if (foundAgroChemicalType.isPresent()) {

				AgriAgroChemicalType agriAgroChemicalType = foundAgroChemicalType.get();
				agriAgroChemicalType.setStatus(APIConstants.STATUS_DELETED);

				agriAgroChemicalType = agriAgroChemicalTypeRepository.save(agriAgroChemicalType);

				approvalUtil.delete(DBConstants.TBL_AGRI_AGROCHEMICAL_TYPE, agriAgroChemicalType.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-AgroChemical-Type" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-AgroChemical-Type" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteAgriAgroChemicalById

	public ResponseMessage rejectById(int id) {

		try {
			Optional<AgriAgroChemicalType> foundAgroChemicalType = agriAgroChemicalTypeRepository.findById(id);
			if (foundAgroChemicalType.isPresent()) {

				AgriAgroChemicalType agriAgroChemicalType = foundAgroChemicalType.get();
				agriAgroChemicalType.setStatus(APIConstants.STATUS_REJECTED);

				agriAgroChemicalType = agriAgroChemicalTypeRepository.save(agriAgroChemicalType);

				approvalUtil.delete(DBConstants.TBL_AGRI_AGROCHEMICAL_TYPE, agriAgroChemicalType.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-AgroChemical-Type" + APIConstants.RESPONSE_REJECT_SUCCESS + id, "");
			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-AgroChemical-Type" + APIConstants.RESPONSE_REJECT_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

	public AgriAgroChemicalType findAgriAgroChemicalTypeById(int id) {
		try {
			Optional<AgriAgroChemicalType> foundAgroChemicalType = agriAgroChemicalTypeRepository.findById(id);
			if (foundAgroChemicalType.isPresent()) {
				return foundAgroChemicalType.get();
			} else {
				throw new DoesNotExistException("Agri-AgroChemical-Type" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findAgriAgroChemicalTypeById

}
