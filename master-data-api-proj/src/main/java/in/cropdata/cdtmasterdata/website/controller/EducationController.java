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
import in.cropdata.cdtmasterdata.website.dto.EducationDto;
import in.cropdata.cdtmasterdata.website.model.Education;
import in.cropdata.cdtmasterdata.website.service.EducationService;

/**
 * Controller for processing API requests.
 * @author OM
 */

@RestController
@RequestMapping("/site/education")
public class EducationController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EducationController.class);

	@Autowired
	private EducationService educationService;
	
	/** Education Handling APIs */

	@GetMapping("/list")
	public List<EducationDto> getEducationList() {

		return educationService.getEducationList();
	}
	
	@GetMapping("/paginatedList")
	public Page<EducationDto> getEducationListByPagenation(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {

		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return educationService.getEducationListByPagenation(page, size, searchText);
	}
	
	@PostMapping("/add")
	public ResponseEntity<ResponseMessage> addEducation(@RequestBody Education education) {

		LOGGER.info("Add Education From Controller...");
		return new ResponseEntity<>(educationService.addEducation(education), HttpStatus.CREATED);
	}
	
	@GetMapping("id/{id}")
	public Education getEducationById(@PathVariable(required = true) Integer id) {

		return educationService.getEducationById(id);

	}
	
	@PutMapping("update/{id}")
	public ResponseEntity<ResponseMessage> updateEducation(@PathVariable(required = true) Integer id,
			@RequestBody Education education) {

		if (id == null) {
			throw new InvalidDataException("Education Id can not be null!");
		}

		if (education == null) {
			throw new InvalidDataException("Education data can not be null!");
		}

		LOGGER.info("Updating Education From Controller for ID -> {}", id);
		return new ResponseEntity<>(educationService.updateEducation(id, education), HttpStatus.OK);
	}
	
	@PutMapping("/deactive/{id}")
	public ResponseMessage deactiveEducation(@PathVariable int id) {
		return educationService.deactiveEducation(id);
	}
	
	@PutMapping("/active/{id}")
	public ResponseMessage activeEducation(@PathVariable int id) {
		return educationService.activeEducation(id);
	}
	
	@GetMapping("/opportunityCount/{educationId}")
	public Integer opportunityCountByEducation(@PathVariable int educationId) {
		return educationService.opportunityCountByEducation(educationId);
	}
	
	@PutMapping("/delete/{id}")
	public ResponseMessage deleteEducation(@PathVariable int id) {
		return educationService.deleteEducation(id);
	}
}
