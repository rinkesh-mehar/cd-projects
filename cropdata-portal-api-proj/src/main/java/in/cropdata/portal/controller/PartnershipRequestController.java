package in.cropdata.portal.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.portal.dto.ResponseMessage;
import in.cropdata.portal.exceptions.InvalidDataException;
import in.cropdata.portal.model.PartnershipRequest;
import in.cropdata.portal.service.PartnershipRequestService;

/**
 * @author cropdata-kunal
 *
 */
@RestController
//@CrossOrigin("*")
@RequestMapping("/site/partnership-request")
public class PartnershipRequestController {

	private static final Logger logger = LoggerFactory.getLogger(PartnershipRequestController.class);

	@Autowired
	PartnershipRequestService partnershipRequestService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessage> addPartnershipRequest(@RequestBody PartnershipRequest partnershipRequest) {

		if (partnershipRequest == null) {
			throw new InvalidDataException("Partnership Request data can not be null!");
		}

		logger.info("Partnership Request Details...");
		return new ResponseEntity<>(partnershipRequestService.addPartnershipRequest(partnershipRequest),
				HttpStatus.CREATED);
	}

	@GetMapping("/getIndustry")
	public CopyOnWriteArrayList<Map<String, Object>> getIndustries() {

		return partnershipRequestService.getIndustries();
	}

	@GetMapping("/getPartnershipProgram")
	public List<Map<String, Object>> getPartnershipProgram() {

		return partnershipRequestService.getPartnershipProgram();
	}

}