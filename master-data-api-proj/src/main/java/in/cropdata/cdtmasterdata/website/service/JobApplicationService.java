package in.cropdata.cdtmasterdata.website.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;
import in.cropdata.cdtmasterdata.website.dto.JobApplicationDto;
import in.cropdata.cdtmasterdata.website.model.Department;
import in.cropdata.cdtmasterdata.website.model.JobApplication;
import in.cropdata.cdtmasterdata.website.model.vo.JobApplicationVo;
import in.cropdata.cdtmasterdata.website.repository.JobApplicationReopsitory;
import in.cropdata.toolsuite.sdk.FileManagerUtil;
import in.cropdata.toolsuite.sdk.dto.DirectoryResponseDTO;
import in.cropdata.toolsuite.sdk.dto.FileUploadResponseDTO;

@Service
public class JobApplicationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(JobApplicationService.class);

	@Autowired
	private JobApplicationReopsitory jobApplicationReopsitory;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;
	
	@Autowired
    private FileManagerUtil fileManagerUtil;

	private static final String SERVER_ERROR_MSG = "Server Error : ";

	public ResponseMessage saveJobApplication(Integer opportunityId, JobApplicationVo jobApplicationVo) {
		try {
		
			if (jobApplicationVo != null) {
				LOGGER.info("Job Application Data ------> {}", jobApplicationVo);

				ObjectMapper objectMapper = new ObjectMapper();
				JobApplication jobApplication = objectMapper.readValue(jobApplicationVo.getData(),
							JobApplication.class);
				
				

				FileUploadResponseDTO manageFile = this.getResumeFile(jobApplicationVo.getResumeFile());
				if(manageFile != null) {
				if (manageFile.getPublicUrl() != null) {
					
					LOGGER.info("Saving Job Application Request...");
					jobApplication.setOpportunityID(opportunityId);
					jobApplication.setResumeUrl(manageFile.getPublicUrl());
					jobApplicationReopsitory.save(jobApplication);
					
				} 
				}else {
					return responseMessageUtil.sendResponse(false, "",
							"Select proper format of Job Application.");
				}
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Job Application " + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
			return responseMessageUtil.sendResponse(true, "Job Application" + APIConstants.RESPONSE_ADD_SUCCESS,
					"");
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + ex.getMessage());
		}

	}

	private FileUploadResponseDTO getResumeFile(MultipartFile file) throws IOException {
		FileUploadResponseDTO fileUploadResponse = null;
		LOGGER.info(" file :: " + file.getOriginalFilename());
		String[] SUPPORTED_EXTN = { ".doc", ".docx", ".txt"};
		String fileExtension = Objects.requireNonNull(file.getOriginalFilename())
				.substring(file.getOriginalFilename().lastIndexOf('.'));
		String pathKey = "JobApplication";
		if (Arrays.stream(SUPPORTED_EXTN).anyMatch(fileExtension::contains)) {
		Map<String, Object> pathKeyResponse = fileManagerUtil.isPathKeyExist(pathKey);
		LOGGER.info("pathKey ::  " + pathKeyResponse.get("exist"));
		if (pathKeyResponse.get("exist") != null) {
			LOGGER.info("pathKeyResponse ::  " + pathKeyResponse);
			Object exist = pathKeyResponse.get("exist");
			LOGGER.info("result ::  " + exist);

			if (exist.toString().equals("true")) {
				LOGGER.info("inside if condition result is true ::  ");
				fileUploadResponse = this.fileManagerUtil.uploadFile(pathKey, false, file.getOriginalFilename(), true,
						file, null);
			} else {
				String moduleName = "master-data";
				String dirPath = pathKey;
				LOGGER.info("inside else condition result is false ::  ");
				DirectoryResponseDTO createDirResp = this.fileManagerUtil.createDirectory(moduleName, pathKey, dirPath,
						null);
				if (createDirResp != null && createDirResp.isSuccess()) {
					fileUploadResponse = this.fileManagerUtil.uploadFile(pathKey, false, file.getOriginalFilename(),
							true, file, null);
					LOGGER.info("Create directory");
				}
			}
		}
		}else {
			LOGGER.info("file format not matched");
		}
		return fileUploadResponse;
	}
	
	public Page<JobApplicationDto> getAllJobApplicationByPagenation(Integer page, Integer size, String searchText) {
		try {
			LOGGER.info("getting all Job Application info - paginated...");
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());

			return jobApplicationReopsitory.getAllJobApplicationByPagenation(sortedByIdDesc, searchText);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Job Application Data Found For Searched Text -> " + searchText);
		}
	}
	
	public JobApplicationDto getJobApplicationById(Integer id) {

		try {

			LOGGER.info("getting Job Application by Id...");
			return jobApplicationReopsitory.getJobApplicationById(id);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("Job Application Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
		}

	}
	
	public ResponseMessage shortlistApplication(int id) {
		try {
			Optional<JobApplication> optionalJobApplication = jobApplicationReopsitory.findById(id);
			if (optionalJobApplication.isPresent()) {

				jobApplicationReopsitory.shortlistApplication(id);

				return responseMessageUtil.sendResponse(true, "Application Shortlisted Successfully with ID " + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}
	
	public ResponseMessage holdApplication(int id) {
		try {
			Optional<JobApplication> optionalJobApplication = jobApplicationReopsitory.findById(id);
			if (optionalJobApplication.isPresent()) {

				jobApplicationReopsitory.holdApplication(id);

				return responseMessageUtil.sendResponse(true, "Application Hold Successfully with ID " + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}
	
	public ResponseMessage scheduleInterview(String interviewScheduleDate, int id) {
		try {
			Optional<JobApplication> optionalJobApplication = jobApplicationReopsitory.findById(id);
			if (optionalJobApplication.isPresent()) {

				jobApplicationReopsitory.scheduleInterview(interviewScheduleDate, id);

				return responseMessageUtil.sendResponse(true, "Interview Schedule Successfully with ID " + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}
	
	public ResponseMessage rejectApplication(int id) {
		try {
			Optional<JobApplication> optionalJobApplication = jobApplicationReopsitory.findById(id);
			if (optionalJobApplication.isPresent()) {

				jobApplicationReopsitory.rejectApplication(id);

				return responseMessageUtil.sendResponse(true, "Application Rejacted Successfully with ID " + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}
	
	public ResponseMessage interviewSelection(int id) {
		try {
			Optional<JobApplication> optionalJobApplication = jobApplicationReopsitory.findById(id);
			if (optionalJobApplication.isPresent()) {

				jobApplicationReopsitory.interviewSelection(id);

				return responseMessageUtil.sendResponse(true, "Application Selected Successfully with ID " + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}
	

}
