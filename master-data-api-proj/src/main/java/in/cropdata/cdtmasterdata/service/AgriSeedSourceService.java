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
import in.cropdata.cdtmasterdata.model.AgriSeedSource;
import in.cropdata.cdtmasterdata.repository.AgriSeedSourceRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AgriSeedSourceService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AgriDisposalMethodService.class);
	private static final String SERVER_ERROR_MSG = "Server Error : ";

	@Autowired
	private AgriSeedSourceRepository agriSeedSourceRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<AgriSeedSource> getAllAgriSeedSource() {
		try {
			List<AgriSeedSource> list = agriSeedSourceRepository.findAll();

			return list;
		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriSeedSource
	
	public Page<AgriSeedSource> getSeedSourceListByPagenation(Integer page, Integer size, String searchText){
		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			return agriSeedSourceRepository.getSeedSourceListByPagenation(sortedByIdDesc, searchText);
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Agri-SeedSource Data Found For Searched Text -> " + searchText);
		}
	}

	public ResponseMessage addAgriSeedSource(AgriSeedSource agriSeedSource)
	{

		try
		{
			if ((agriSeedSourceRepository.findByName(agriSeedSource.getName())).isEmpty())
			{
				agriSeedSource = agriSeedSourceRepository.save(agriSeedSource);

				approvalUtil.addRecord(DBConstants.TBL_AGRI_SEED_SOURCE, agriSeedSource.getId());

				return responseMessageUtil.sendResponse(true, "Agri-SeedSource" +
						APIConstants.RESPONSE_ADD_SUCCESS, "");
			} else
			{
				return responseMessageUtil.sendResponse(false, "", "Agri-SeedSource" +
						APIConstants.RESPONSE_ALREADY_EXIST);
			}
		} catch (Exception e)
		{
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAgriSeedSource

	public ResponseMessage updateAgriSeedSourceById(int id, AgriSeedSource agriSeedSource)
	{
		try
		{
			Optional<AgriSeedSource> foundSeedSource = agriSeedSourceRepository.findById(id);

			if (foundSeedSource.isPresent())
			{
				if ((agriSeedSourceRepository.findByName(agriSeedSource.getName())).isEmpty())
				{
					agriSeedSource.setId(id);
					agriSeedSource = agriSeedSourceRepository.save(agriSeedSource);

					approvalUtil.updateRecord(DBConstants.TBL_AGRI_SEED_SOURCE, agriSeedSource.getId());

					return responseMessageUtil.sendResponse(true,
							"Agri-SeedSource" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
				} else
				{
					return responseMessageUtil.sendResponse(false, "",
							"Agri-SeedSource" + APIConstants.RESPONSE_ALREADY_EXIST);
				}
			} else
			{
				return responseMessageUtil.sendResponse(false, "",
						"Agri-SeedSource" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e)
		{
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateAgriSeedSourceById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<AgriSeedSource> foundSeedSource = agriSeedSourceRepository.findById(id);

			if (foundSeedSource.isPresent()) {

				AgriSeedSource agriSeedSource = foundSeedSource.get();
				agriSeedSource.setStatus(APIConstants.STATUS_APPROVED);

				agriSeedSource = agriSeedSourceRepository.save(agriSeedSource);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_SEED_SOURCE, agriSeedSource.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-SeedSource" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-SeedSource" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<AgriSeedSource> foundSeedSource = agriSeedSourceRepository.findById(id);

			if (foundSeedSource.isPresent()) {

				AgriSeedSource agriSeedSource = foundSeedSource.get();
				agriSeedSource.setStatus(APIConstants.STATUS_ACTIVE);

				agriSeedSource = agriSeedSourceRepository.save(agriSeedSource);

				approvalUtil.finalApprove(DBConstants.TBL_AGRI_SEED_SOURCE, agriSeedSource.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-SeedSource" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-SeedSource" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteAgriSeedSourceById(int id) {
		try {
			Optional<AgriSeedSource> foundSeedSource = agriSeedSourceRepository.findById(id);

			if (foundSeedSource.isPresent()) {

				AgriSeedSource agriSeedSource = foundSeedSource.get();
				agriSeedSource.setStatus(APIConstants.STATUS_DELETED);

				agriSeedSource = agriSeedSourceRepository.save(agriSeedSource);

				approvalUtil.delete(DBConstants.TBL_AGRI_SEED_SOURCE, agriSeedSource.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-SeedSource" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-SeedSource" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteAgriSeedSourceById
	
	public ResponseMessage rejectById(int id) {
		try {
			Optional<AgriSeedSource> foundSeedSource = agriSeedSourceRepository.findById(id);
			
			if (foundSeedSource.isPresent()) {
				
				AgriSeedSource agriSeedSource = foundSeedSource.get();
				agriSeedSource.setStatus(APIConstants.STATUS_REJECTED);
				
				agriSeedSource = agriSeedSourceRepository.save(agriSeedSource);
				
				approvalUtil.delete(DBConstants.TBL_AGRI_SEED_SOURCE, agriSeedSource.getId());
				
				return responseMessageUtil.sendResponse(true,
						"Agri-SeedSource" + APIConstants.RESPONSE_REJECT_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-SeedSource" + APIConstants.RESPONSE_REJECT_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

	public AgriSeedSource findAgriSeedSourceById(int id) {
		try {
			Optional<AgriSeedSource> foundSeedSource = agriSeedSourceRepository.findById(id);

			if (foundSeedSource.isPresent()) {
				return foundSeedSource.get();

			} else {
				throw new DoesNotExistException("Agri-SeedSource" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findAgriSeedSourceById
}// AgriSeedSourceService
