package in.cropdata.cdtmasterdata.website.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.InvalidDataException;
import in.cropdata.cdtmasterdata.model.PlatFormMaster;
import in.cropdata.cdtmasterdata.website.dto.OpportunitiesDto;
import in.cropdata.cdtmasterdata.website.model.Opportunities;
import in.cropdata.cdtmasterdata.website.service.OpportunitiesService;

@RestController
@RequestMapping("/site/opportunities")
public class OpportunitiesController {

	private static final Logger LOGGER = LoggerFactory.getLogger(OpportunitiesController.class);

	@Autowired
	private OpportunitiesService opportunitiesService;
	

	@GetMapping("/list")
	public List<OpportunitiesDto> getAllOpportunities() {

		return opportunitiesService.getAllOpportunities();

	}

	@GetMapping("/paginatedList")
	public Page<OpportunitiesDto> getAllOpportunitiesByPagenation(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {

		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return opportunitiesService.getAllOpportunitiesByPagenation(page, size, searchText);
	}

	@PostMapping("/add")
	public ResponseEntity<ResponseMessage> addOppotunity(@RequestBody Opportunities opportunities) {

		LOGGER.info("Inside Add Opportunity...");
		return new ResponseEntity<>(opportunitiesService.addOppotunity(opportunities), HttpStatus.CREATED);
	}

	@GetMapping("id/{id}")
	public OpportunitiesDto getOpportunityById(@PathVariable(required = true) Integer id) {

		return opportunitiesService.getOpportunitiesById(id);

	}

	@PutMapping("update/{id}")
	public ResponseEntity<ResponseMessage> updateNewsDetails(@PathVariable(required = true) Integer id,
			@RequestBody Opportunities opportunity) {
		
		LOGGER.info("Inside Updating Opportunity detail...{}");

		if (id == null) {
			throw new InvalidDataException("Opportunity Id can not be null!");
		}

		if (opportunity == null) {
			throw new InvalidDataException("Opportunity data can not be null!");
		}

		LOGGER.info("Updating Opportunity detail for ID -> {}", id);
		return new ResponseEntity<>(opportunitiesService.UpdateOpportunity(id, opportunity), HttpStatus.OK);
	}
	
	@GetMapping("/platform")
	public List<PlatFormMaster> getPlatForms(){
		return opportunitiesService.getPlatForm();
	}
	
	@PutMapping("/close/{id}")
	public ResponseMessage closeOpportunityById(@PathVariable int id) {
		return opportunitiesService.closeOpportunityById(id);
	}
	
	@PutMapping("/active/{id}")
	public ResponseMessage activeOpportunityById(@PathVariable int id) {
		return opportunitiesService.activeOpportunityById(id);
	}
	
	@GetMapping("education-ids/{opportunityId}")
	public Integer[] getEducationIdsByOpportunityId(@PathVariable(required = true) Integer opportunityId) {

		return opportunitiesService.getEducationIdsByOpportunityId(opportunityId);

	}
	
}
