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
import in.cropdata.cdtmasterdata.model.AgriSourceOfWater;
import in.cropdata.cdtmasterdata.repository.AgriSourceOfWaterRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AgriSourceOfWaterService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AgriDisposalMethodService.class);
	private static final String SERVER_ERROR_MSG = "Server Error : ";

	@Autowired
	private AgriSourceOfWaterRepository agriSourceOfWaterRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<AgriSourceOfWater> getAllAgriSourceOfWater() {
		try {
			List<AgriSourceOfWater> list = agriSourceOfWaterRepository.findAll();

			return list;
		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriSourceOfWater
	
	public Page<AgriSourceOfWater> getSourceOfWaterListByPagenation(Integer page, Integer size, String searchText){
		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			return agriSourceOfWaterRepository.getSourceOfWaterListByPagenation(sortedByIdDesc, searchText);
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Agri-SourceOfWater Data Found For Searched Text -> " + searchText);
		}
	}

	public ResponseMessage addAgriSourceOfWater(AgriSourceOfWater agriSourceOfWater)
	{

		try
		{
			if (agriSourceOfWaterRepository.findByName(agriSourceOfWater.getName()).isEmpty())
			{
				agriSourceOfWater = agriSourceOfWaterRepository.save(agriSourceOfWater);

				approvalUtil.updateRecord(DBConstants.TBL_AGRI_SOURCE_OF_WATER, agriSourceOfWater.getId());

				return responseMessageUtil.sendResponse(true, "Agri-SourceOfWater" + APIConstants.RESPONSE_ADD_SUCCESS, "");
			} else
			{
				return responseMessageUtil.sendResponse(false, "",
						"Agri-SourceOfWater" + APIConstants.RESPONSE_ALREADY_EXIST);
			}
		} catch (Exception e)
		{
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAgriSourceOfWater

	public ResponseMessage updateAgriSourceOfWaterById(int id, AgriSourceOfWater agriSourceOfWater)
	{
		try
		{
			Optional<AgriSourceOfWater> foundSourceOfWater = agriSourceOfWaterRepository.findById(id);

			if (foundSourceOfWater.isPresent())
			{
				if ((agriSourceOfWaterRepository.findByName(agriSourceOfWater.getName())).isEmpty())
				{
					agriSourceOfWater.setId(id);
					agriSourceOfWater = agriSourceOfWaterRepository.save(agriSourceOfWater);

					approvalUtil.addRecord(DBConstants.TBL_AGRI_SOURCE_OF_WATER, agriSourceOfWater.getId());

					return responseMessageUtil.sendResponse(true,
							"Agri-SourceOfWater" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
				} else
				{
					return responseMessageUtil.sendResponse(false, "",
							"Agri-SourceOfWater" + APIConstants.RESPONSE_ALREADY_EXIST);
				}
			} else
			{
				return responseMessageUtil.sendResponse(false, "",
						"Agri-SourceOfWater" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e)
		{
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateAgriSourceOfWaterById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<AgriSourceOfWater> foundSourceOfWater = agriSourceOfWaterRepository.findById(id);

			if (foundSourceOfWater.isPresent()) {

				AgriSourceOfWater agriSourceOfWater = foundSourceOfWater.get();
				agriSourceOfWater.setStatus(APIConstants.STATUS_APPROVED);

				agriSourceOfWater = agriSourceOfWaterRepository.save(agriSourceOfWater);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_SOURCE_OF_WATER, agriSourceOfWater.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-SourceOfWater" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-SourceOfWater" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<AgriSourceOfWater> foundSourceOfWater = agriSourceOfWaterRepository.findById(id);

			if (foundSourceOfWater.isPresent()) {

				AgriSourceOfWater agriSourceOfWater = foundSourceOfWater.get();
				agriSourceOfWater.setStatus(APIConstants.STATUS_ACTIVE);

				agriSourceOfWater = agriSourceOfWaterRepository.save(agriSourceOfWater);

				approvalUtil.finalApprove(DBConstants.TBL_AGRI_SOURCE_OF_WATER, agriSourceOfWater.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-SourceOfWater" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-SourceOfWater" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteAgriSourceOfWaterById(int id) {
		try {
			Optional<AgriSourceOfWater> foundSourceOfWater = agriSourceOfWaterRepository.findById(id);

			if (foundSourceOfWater.isPresent()) {

				AgriSourceOfWater agriSourceOfWater = foundSourceOfWater.get();
				agriSourceOfWater.setStatus(APIConstants.STATUS_DELETED);

				agriSourceOfWater = agriSourceOfWaterRepository.save(agriSourceOfWater);

				approvalUtil.delete(DBConstants.TBL_AGRI_SOURCE_OF_WATER, agriSourceOfWater.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-SourceOfWater" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-SourceOfWater" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteAgriSourceOfWaterById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<AgriSourceOfWater> foundSourceOfWater = agriSourceOfWaterRepository.findById(id);

			if (foundSourceOfWater.isPresent()) {

				AgriSourceOfWater agriSourceOfWater = foundSourceOfWater.get();
				agriSourceOfWater.setStatus(APIConstants.STATUS_REJECTED);

				agriSourceOfWater = agriSourceOfWaterRepository.save(agriSourceOfWater);

				approvalUtil.delete(DBConstants.TBL_AGRI_SOURCE_OF_WATER, agriSourceOfWater.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-SourceOfWater" + APIConstants.RESPONSE_REJECT_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-SourceOfWater" + APIConstants.RESPONSE_REJECT_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

	public AgriSourceOfWater findAgriSourceOfWaterById(int id) {
		try {
			Optional<AgriSourceOfWater> foundSourceOfWater = agriSourceOfWaterRepository.findById(id);
			if (foundSourceOfWater.isPresent()) {
				return foundSourceOfWater.get();
			} else {

				throw new DoesNotExistException("Agri-SourceOfWater" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findAgriSourceOfWaterById
}// AgriSourceOfWaterService
