package in.cropdata.portal.controller;

import in.cropdata.portal.dto.ResponseMessage;
import in.cropdata.portal.exceptions.InvalidDataException;
import in.cropdata.portal.model.JobApplication;
import in.cropdata.portal.dto.JobApplicationDTO;
import in.cropdata.portal.service.JobApplicationService;
import in.cropdata.portal.vo.JobApplicationVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.portal.controller
 * @date 07/08/20JobApplicationReopsitory
 * @time 5:59 PM
 * To change this template use File | Settings | File and Code Templates
 */
@RestController
@RequestMapping("/site/job-application")
public class JobApplicationController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(JobApplicationController.class);

    @Autowired
    private JobApplicationService jobApplicationService;

    @GetMapping("/paginatedList")
    public Page<JobApplicationVO> getAllJobApplicationByPagenation(@RequestParam("page") Integer page,
                                                                   @RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {

        if (page == null || size == null) {
            throw new InvalidDataException("page or size can not be null!");
        }

        return jobApplicationService.getAllJobApplicationByPagenation(page, size, searchText);
    }

    @GetMapping("/list")
    public List<JobApplication> getAllJobApplication() {

        return jobApplicationService.getAllJobApplication();

    }

    //Portal API
    @PostMapping("/apply-job")
    public ResponseEntity<ResponseMessage> saveJobApplication(@ModelAttribute JobApplicationDTO jobApplicationDto) {

//        if (opportunityID == null) {
//            throw new InvalidDataException("Opportunity Id can not be null!");
//        }

        if (jobApplicationDto == null) {
            throw new InvalidDataException("Job Application Data can not be null!");
        }
        
        if (jobApplicationDto.getName() == null || "".equals(jobApplicationDto.getName())) {
            throw new InvalidDataException("Name can not be null!");
        }

        if (jobApplicationDto.getEmail() == null || "".equals(jobApplicationDto.getEmail())) {
            throw new InvalidDataException("Emai can not be null!");
        }
        
        if (jobApplicationDto.getMobile() == null || "".equals(jobApplicationDto.getMobile())) {
            throw new InvalidDataException("Mobile Number can not be null!");
        }
     
        LOGGER.info("Saving Job Application..." + jobApplicationDto);

        return new ResponseEntity<>(jobApplicationService.saveJobApplication(jobApplicationDto),
                HttpStatus.CREATED);

    }
}
