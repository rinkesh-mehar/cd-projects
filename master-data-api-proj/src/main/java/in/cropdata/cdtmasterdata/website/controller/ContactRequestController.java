/**
 * 
 */
package in.cropdata.cdtmasterdata.website.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.InvalidDataException;
import in.cropdata.cdtmasterdata.website.model.ContactRequest;
import in.cropdata.cdtmasterdata.website.model.vo.ContactRequestVO;
import in.cropdata.cdtmasterdata.website.service.ContactRequestService;

/**
 * @author cropdata-kunal
 *
 */
@RestController
//@CrossOrigin("*")
@RequestMapping("/site/contact-request")
public class ContactRequestController {

	private static final Logger logger = LoggerFactory.getLogger(ContactRequestController.class);

	@Autowired
	ContactRequestService contactRequestService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessage> addContact(@RequestBody ContactRequest data) {

		if (data == null) {
			throw new InvalidDataException("Contact Request data can not be null!");
		}

		logger.info("Contact Request Details...");
		return new ResponseEntity<>(contactRequestService.addContact(data), HttpStatus.CREATED);
	}

	@GetMapping("/paginatedList")
	public Page<ContactRequestVO> getAllContactRequestByPagenation(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {

		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return contactRequestService.getAllContactRequestByPagenation(page, size, searchText);
	}
	
}
