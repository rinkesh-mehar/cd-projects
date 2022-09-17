package in.cropdata.cdtmasterdata.website.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.InvalidDataException;
import in.cropdata.cdtmasterdata.website.dto.JobApplicationDto;
import in.cropdata.cdtmasterdata.website.model.vo.JobApplicationVo;
import in.cropdata.cdtmasterdata.website.service.JobApplicationService;

/**
 * Controller for processing API requests.
 * @author OM
 */

@RestController
@RequestMapping("/site/job-application")
public class JobApplicationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(JobApplicationController.class);

	@Autowired
	private JobApplicationService jobApplicationService;

	/** Job Application Handling APIs */
	
	@PostMapping("/apply-job/{opportunityID}")
	public ResponseEntity<ResponseMessage> saveJobApplication(@PathVariable Integer opportunityID,
			@ModelAttribute JobApplicationVo jobApplicationVo) {

		if (opportunityID == null) {
			throw new InvalidDataException("Opportunity Id can not be null!");
		}

		if (jobApplicationVo == null) {
			throw new InvalidDataException("Job Application Data can not be null!");
		}

		LOGGER.info("Saving Job Application...");

		return new ResponseEntity<>(jobApplicationService.saveJobApplication(opportunityID, jobApplicationVo),
				HttpStatus.CREATED);

	}

	@GetMapping("/paginatedList")
	public Page<JobApplicationDto> getAllJobApplicationByPagenation(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {

		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return jobApplicationService.getAllJobApplicationByPagenation(page, size, searchText);
	}
	
	@GetMapping("/id/{id}")
	public JobApplicationDto getJobApplicationById(@PathVariable(required = true) Integer id) {

		return jobApplicationService.getJobApplicationById(id);
	}
	
	@PutMapping("/shortlist/{id}")
	public ResponseMessage shortlistApplication(@PathVariable int id) {
		return jobApplicationService.shortlistApplication(id);
	}
	
	@PutMapping("/hold/{id}")
	public ResponseMessage holdApplication(@PathVariable int id) {
		return jobApplicationService.holdApplication(id);
	}
	
	@PutMapping("/schedule-interview/{interviewScheduleDate}/{id}")
	public ResponseMessage scheduleInterview(@PathVariable String interviewScheduleDate, @PathVariable int id) {
		return jobApplicationService.scheduleInterview(interviewScheduleDate,id);
	}
	
	@PutMapping("/reject/{id}")
	public ResponseMessage rejectApplication(@PathVariable int id) {
		return jobApplicationService.rejectApplication(id);
	}
	
	@PutMapping("/interview-selection/{id}")
	public ResponseMessage interviewSelection(@PathVariable int id) {
		return jobApplicationService.interviewSelection(id);
	}
}
