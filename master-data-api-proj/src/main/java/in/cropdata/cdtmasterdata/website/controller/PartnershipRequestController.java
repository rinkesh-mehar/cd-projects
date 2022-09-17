/**
 * 
 */
package in.cropdata.cdtmasterdata.website.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.InvalidDataException;
import in.cropdata.cdtmasterdata.website.model.PartnershipRequest;
import in.cropdata.cdtmasterdata.website.model.vo.PartnershipRequestVO;
import in.cropdata.cdtmasterdata.website.service.PartnershipRequestService;

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
	
	@GetMapping("/paginatedList")
	public Page<PartnershipRequestVO> getAllPartnershipRequestByPagenation(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {

		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return partnershipRequestService.getAllPartnershipRequestByPagenation(page, size, searchText);
	}
	
	@GetMapping("id/{id}")
	public PartnershipRequestVO getPartnershipRequestById(@PathVariable(required = true) Integer id) {

		return partnershipRequestService.getPartnershipRequestById(id);

	}

}
