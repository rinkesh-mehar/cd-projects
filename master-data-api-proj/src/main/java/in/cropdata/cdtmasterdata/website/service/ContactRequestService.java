package in.cropdata.cdtmasterdata.website.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;
import in.cropdata.cdtmasterdata.website.model.ContactRequest;
import in.cropdata.cdtmasterdata.website.model.vo.ContactRequestVO;
import in.cropdata.cdtmasterdata.website.repository.ContactRequestRepository;

@Service
public class ContactRequestService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ContactRequestService.class);
	

	@Autowired
	ContactRequestRepository contactRequestRepository;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	private static final String SERVER_ERROR_MSG = "Server Error : ";

	public ResponseMessage addContact(ContactRequest contact) {
		try {

			contactRequestRepository.save(contact);

			return responseMessageUtil.sendResponse(true, "Contact Request" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception ex) {
			return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + ex.getMessage());
		}
	}

	public Page<ContactRequestVO> getAllContactRequestByPagenation(Integer page, Integer size, String searchText) {
		try {
			LOGGER.info("getting all contact request info - paginated...");
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());

			return contactRequestRepository.getAllContactRequestByPagenation(sortedByIdDesc, searchText);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Contact Request Data Found For Searched Text -> " + searchText);
		}
	}
	
}
