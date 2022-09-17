package in.cropdata.portal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.cropdata.portal.constants.APIConstants;
import in.cropdata.portal.dto.ResponseMessage;
import in.cropdata.portal.model.ContactRequest;
import in.cropdata.portal.repository.ContactRequestRepository;
import in.cropdata.portal.util.ResponseMessageUtil;

@Service
public class ContactRequestService {

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

}
