package in.cropdata.portal.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.cropdata.portal.constants.APIConstants;
import in.cropdata.portal.dto.JobApplicationDTO;
import in.cropdata.portal.dto.ResponseMessage;
import in.cropdata.portal.exceptions.DoesNotExistException;
import in.cropdata.portal.exceptions.InvalidDataException;
import in.cropdata.portal.model.JobApplication;
import in.cropdata.portal.repository.JobApplicationReopsitory;
import in.cropdata.portal.util.ResponseMessageUtil;
import in.cropdata.portal.vo.JobApplicationVO;
import in.cropdata.toolsuite.sdk.FileManagerUtil;
import in.cropdata.toolsuite.sdk.dto.DirectoryResponseDTO;
import in.cropdata.toolsuite.sdk.dto.FileUploadResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class JobApplicationService
{

	private static final Logger LOGGER = LoggerFactory.getLogger(JobApplicationService.class);

	@Autowired
	private JobApplicationReopsitory jobApplicationReopsitory;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	@Autowired
	private FileManagerUtil fileManagerUtil;


	private static final String SERVER_ERROR_MSG = "Server Error : ";

	public List<JobApplication> getAllJobApplication() {
		try {
			LOGGER.info("getting all Job Application info...");


			return jobApplicationReopsitory.findAll();

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Job Application Data Found!");
		}
	}


	public Page<JobApplicationVO> getAllJobApplicationByPagenation(Integer page, Integer size, String searchText) {
		try {
			LOGGER.info("getting all Job Application info - paginated...");
			searchText = "%" + searchText + "%";
			Pageable sortedByIDAsce = PageRequest.of(page, size, Sort.by("JobApplicationId").ascending());

			return jobApplicationReopsitory.getAllJobApplicationByPagenation(sortedByIDAsce, searchText);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Job Application Data Found For Searched Text -> " + searchText);
		}
	}
	
	public JobApplicationVO getJobApplicationById(Integer id) {

		try {

			LOGGER.info("getting Job Application by Id...");
			return jobApplicationReopsitory.getJobApplicationById(id);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("Job Application Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
		}

	}

	public ResponseMessage saveJobApplication(JobApplicationDTO jobApplicationDto) {
		try {

			if (jobApplicationDto != null) {
				LOGGER.info("Job Application Data ------> {}", jobApplicationDto);

//				ObjectMapper objectMapper = new ObjectMapper();
//				JobApplication jobApplication = objectMapper.readValue(jobApplicationDto.getData(),
//						JobApplication.class);
				JobApplication jobApplication = new JobApplication();
						
				BeanUtils.copyProperties(jobApplicationDto, jobApplication);

				jobApplication.setAppliedCount(1);

				FileUploadResponseDTO manageFile = this.getResumeFile(jobApplicationDto.getResumeFile());
//				if(manageFile != null) {
					if (manageFile.getPublicUrl() != null) {

						LOGGER.info("Saving Job Application Request...");
//						jobApplication.setOpportunityID(opportunityId);
						LOGGER.info("resume file url length : " + manageFile.getPublicUrl().length());
						if(manageFile.getPublicUrl().length() <= 200) {
							LOGGER.info("Inside if...");
							jobApplication.setResumeUrl(manageFile.getPublicUrl());
							jobApplicationReopsitory.save(jobApplication);
						}else {
							LOGGER.info("Inside else...");
							throw new InvalidDataException("Resume file name is too long. Please rename the resume file name!");
						}
						

					}
//				}else {
//					return responseMessageUtil.sendResponse(false, "",
//							"Select proper format of Job Application(Supported formats : .doc, .docx, .pdf, .jpeg, .jpg, .png).");
//				}
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Job Application " + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
			return responseMessageUtil.sendResponse(true, APIConstants.RESPONSE_ADD_SUCCESS,
					"");
		} catch (NoSuchFileException ex) {
			LOGGER.error("No Such File Exception : ", ex);
			return responseMessageUtil.sendResponse(false, "", ex.getMessage());
		}catch (DataIntegrityViolationException ex) {
//			LOGGER.error(SERVER_ERROR_MSG, ex);
//			return responseMessageUtil.sendResponse(false, "","You have already applied For this Opportunity!");
			try {
				JobApplication jobApplication = jobApplicationReopsitory.findByOpportunityIDAndEmailAndMobile(jobApplicationDto.getOpportunityID(), jobApplicationDto.getEmail(), jobApplicationDto.getMobile());
				LOGGER.info("JobApplication..." + jobApplication);
				BeanUtils.copyProperties(jobApplicationDto, jobApplication);

				jobApplication.setAppliedCount(jobApplication.getAppliedCount()+1);

				FileUploadResponseDTO manageFile = this.getResumeFile(jobApplicationDto.getResumeFile());
					if (manageFile.getPublicUrl() != null) {

						LOGGER.info("Saving Job Application Request From Catch...");
						LOGGER.info("resume file url length : " + manageFile.getPublicUrl().length());
						if(manageFile.getPublicUrl().length() <= 200) {
							LOGGER.info("Inside if From Catch...");
							jobApplication.setResumeUrl(manageFile.getPublicUrl());
							jobApplicationReopsitory.save(jobApplication);
						}else {
							LOGGER.info("Inside else From Catch...");
							throw new InvalidDataException("Resume file name is too long. Please rename the resume file name!");
						}
					}
				jobApplicationReopsitory.save(jobApplication);
				LOGGER.info("Job Application Saved From Catch...");
				return responseMessageUtil.sendResponse(true, APIConstants.RESPONSE_ADD_SUCCESS,
						"");
			}catch (NoSuchFileException noSuchFileException) {
				LOGGER.error("No Such File Exception : ", noSuchFileException);
				return responseMessageUtil.sendResponse(false, "", noSuchFileException.getMessage());
			}catch (InvalidDataException invalidDataException) {
				LOGGER.error(SERVER_ERROR_MSG, invalidDataException);
				return responseMessageUtil.sendResponse(false, "",invalidDataException.getMessage());
			}catch (Exception exception) {
				LOGGER.error(SERVER_ERROR_MSG, exception);
				return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + exception.getMessage());
			}
			
		}catch (InvalidDataException ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			return responseMessageUtil.sendResponse(false, "",ex.getMessage());
		}catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + ex.getMessage());
		}

	}
	private FileUploadResponseDTO getResumeFile(MultipartFile file) throws IOException
	{
		FileUploadResponseDTO fileUploadResponse = null;
		LOGGER.info(" file :: " + file.getOriginalFilename());
		
		LocalDateTime localDate = LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String formattedDate = localDate.format(myFormatObj);
		String afterFormat=  formattedDate.replace("-","");
		String formatDate = afterFormat.replace(":","");
		String currentDate = formatDate.replace(" ","");
		
		String[] SUPPORTED_EXTN = { ".doc", ".docx", ".pdf", ".jpeg", ".jpg", ".png"};
//		String fileExtension = Objects.requireNonNull(file.getOriginalFilename())
//				.substring(file.getOriginalFilename().lastIndexOf('.'));
		String resume = Objects.requireNonNull(file.getOriginalFilename());
		String resumeWithoutExtension = resume.substring(0, resume.lastIndexOf(".")).replaceAll("[^a-zA-Z0-9]", " ").replace(" ", "");
		LOGGER.info(" fileWithoutExtension :: {}", resumeWithoutExtension);
		String fileExtension = Objects.requireNonNull(resume).substring(resume.lastIndexOf('.'));
		String currentResume = resumeWithoutExtension.concat("-").concat(currentDate).concat(".").concat(resume.substring(resume.lastIndexOf(".")+1));
		LOGGER.info(" currentResume :: {}", currentResume);
		LOGGER.info(" fileExtension :: {}", fileExtension);
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
					fileUploadResponse = this.fileManagerUtil.uploadFile(pathKey, false, currentResume, true,
							file, null);
				} else {
					String moduleName = "master-data";
					String dirPath = pathKey;
					LOGGER.info("inside else condition result is false ::  ");
					DirectoryResponseDTO createDirResp = this.fileManagerUtil.createDirectory(moduleName, pathKey, dirPath,
							null);
					if (createDirResp != null && createDirResp.isSuccess()) {
						fileUploadResponse = this.fileManagerUtil.uploadFile(pathKey, false, currentResume,
								true, file, null);
						LOGGER.info("Create directory");
					}
				}
			}
		}else {
			LOGGER.info("file format not matched");
			throw new NoSuchFileException("File Format Not supported!");
		}
		return fileUploadResponse;
	}
}
