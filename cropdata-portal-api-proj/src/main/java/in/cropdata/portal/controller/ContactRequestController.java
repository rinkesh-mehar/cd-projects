/**
 * 
 */
package in.cropdata.portal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.portal.dto.ResponseMessage;
import in.cropdata.portal.exceptions.InvalidDataException;
import in.cropdata.portal.model.ContactRequest;
import in.cropdata.portal.service.ContactRequestService;

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

}
