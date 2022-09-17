package in.cropdata.cdtmasterdata.website.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.PlatFormMaster;
import in.cropdata.cdtmasterdata.properties.AppProperties;
import in.cropdata.cdtmasterdata.repository.PlatFormRepository;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;
import in.cropdata.cdtmasterdata.website.dto.CropdataPriorityNewsDto;
import in.cropdata.cdtmasterdata.website.dto.NewsDTO;
import in.cropdata.cdtmasterdata.website.dto.NewsDataDto;
import in.cropdata.cdtmasterdata.website.dto.ReportDownloadRequestDto;
import in.cropdata.cdtmasterdata.website.dto.ReportUploadDTO;
import in.cropdata.cdtmasterdata.website.dto.ReportsDto;
import in.cropdata.cdtmasterdata.website.model.News;
import in.cropdata.cdtmasterdata.website.model.ReportDownloadRequest;
import in.cropdata.cdtmasterdata.website.model.Reports;
import in.cropdata.cdtmasterdata.website.repository.NewsRepository;
import in.cropdata.cdtmasterdata.website.repository.ReportDownloadRequestRepository;
import in.cropdata.cdtmasterdata.website.repository.ReportsRepository;
import in.cropdata.toolsuite.sdk.FileManagerUtil;
import in.cropdata.toolsuite.sdk.dto.DirectoryResponseDTO;
import in.cropdata.toolsuite.sdk.dto.FileUploadResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Service to process the data received from request and perform necessary
 * operations.
 *
 * @author PranaySK
 * @since 1.0
 */

@Service
public class NewsReportsService
{

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsReportsService.class);

    @Autowired
    AppProperties appProperties;

    @Autowired
    private FileManagerUtil fileManagerUtil;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private PlatFormRepository platFormRepository;

    @Autowired
    private ReportsRepository reportsRepository;

    @Autowired
    private ReportDownloadRequestRepository reportDownloadRequestRepository;

    @Autowired
    private ResponseMessageUtil responseMessageUtil;

	private static final String SERVER_ERROR_MSG = "Server Error : ";

	public List<PlatFormMaster> getPlatForm(){
	    try
        {
            LOGGER.info("getting all platForm info...");
            return platFormRepository.findAll(Sort.by("name"));
        } catch (Exception ex){
	        LOGGER.error(SERVER_ERROR_MSG, ex);
	        throw new DoesNotExistException("No PlatForm found!");
        }
    }

	public List<News> getAllNews() {
		try {
			LOGGER.info("getting all news info...");
			var sort = new Sort(Sort.Direction.DESC, "PublishedDate");
			return newsRepository.findAll(sort);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No News Data Found!");
		}
	}

	public Page<NewsDataDto> getAllNewsPaginated(Integer page, Integer size, String searchText) {
		try {
			LOGGER.info("getting all news info - paginated...");
			searchText = "%" + searchText + "%";
			Pageable sortByIDDsc = PageRequest.of(page, size, Sort.by("ID").descending());

			return newsRepository.findAllWithSearch(sortByIDDsc, searchText);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No News Data Found For Searched Text -> " + searchText);
		}
	}

	public News getSelectedNewsDetails(Integer newsId) {
		try {
			return newsRepository.findById(newsId).orElse(null);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("News Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + newsId);
		}
	}

    public ResponseMessage storeNewsDetail(NewsDTO newsDTO)
    {
        try
        {
            LOGGER.info("saving news info...");
            ObjectMapper objectMapper = new ObjectMapper();
            News newsDetail = objectMapper.readValue(newsDTO.getData(), News.class);
            
			if (newsDetail.getIsLatestNews() != null)
			{
				if (newsDetail.getIsLatestNews())
				{
					newsDetail.setLatestNews("Yes");
				} else
				{
					newsDetail.setLatestNews("No");
				}
			} else {
				newsDetail.setLatestNews("No");
			}
			if(newsDetail.getPlatformId() == 5) {
				LOGGER.info("Inside if...");
				
				if (newsDTO.getImage() != null)
		        {
				FileUploadResponseDTO manageFile = this.getManageFile(newsDTO.getImage());
				newsDetail.setImageUrl(manageFile.getPublicUrl());
		        }
				
            	if("Yes".equals(newsDetail.getLatestNews())) {
            		LOGGER.info("Yes...");
            		Integer maxPriority = newsRepository.getCropdataLatestNewsMaxPriority();
            		newsDetail.setPriority((maxPriority+1));
            	}else {
            		LOGGER.info("No...");
            		Integer maxPriority = newsRepository.getCropdataNewsMaxPriority();
            		newsDetail.setPriority((maxPriority+1));
            	}
            }else {
            	LOGGER.info("Inside else...");
            	newsDetail.setImageUrl(newsDetail.getExternalImageUrl());
            	newsDetail.setPriority(null);
            }
//            if (newsDTO.getImage() != null)
//            {
//                FileUploadResponseDTO manageFile = this.getManageFile(newsDTO.getImage());
//                newsDetail.setImageUrl(manageFile.getPublicUrl());
//            }
            newsRepository.save(newsDetail);

			return responseMessageUtil.sendResponse(true, "News" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + ex.getMessage());
		}
	}

    @Transactional
    public ResponseMessage updateSelectedNewsDetails(Integer newsId, NewsDTO newsDTO)
    {
        try
        {
            Optional<News> dbNewsDetail = newsRepository.findById(newsId);

            if (dbNewsDetail.isPresent())
            {
                LOGGER.info("updating news info...");
                ObjectMapper objectMapper = new ObjectMapper();
                News newsDetail = objectMapper.readValue(newsDTO.getData(), News.class);
                if (newsDetail.getIsLatestNews() != null)
				{
					if (newsDetail.getIsLatestNews())
					{
						newsDetail.setLatestNews("Yes");
					} else
					{
						newsDetail.setLatestNews("No");
					}
				} else {
					newsDetail.setLatestNews("No");
				}
                
                if(newsDetail.getPlatformId() == 5) {
    				LOGGER.info("Inside if...");
    				
    				if (newsDTO.getImage() != null)
    		        {
    				FileUploadResponseDTO manageFile = this.getManageFile(newsDTO.getImage());
    				newsDetail.setImageUrl(manageFile.getPublicUrl());
    		        }
                }else {
                	LOGGER.info("Inside else...");
                	newsDetail.setImageUrl(newsDetail.getExternalImageUrl());
                	
                }
//                if (newsDTO.getImage() != null)
//                {
//                    FileUploadResponseDTO manageFile = this.getManageFile(newsDTO.getImage());
//                    newsDetail.setImageUrl(manageFile.getPublicUrl());
//                } else {
//                	newsDetail.setImageUrl(dbNewsDetail.get().getImageUrl());
//				}
                Integer existingPlatformId = dbNewsDetail.get().getPlatformId();
                Integer existingPriority = dbNewsDetail.get().getPriority();
                String existingLatestNews = dbNewsDetail.get().getLatestNews();
                
                LOGGER.info("existingPlatformId : " + existingPlatformId);
                LOGGER.info("currentPlatformId : " + newsDetail.getPlatformId());
                LOGGER.info("existingPriority : " + existingPriority);
                LOGGER.info("existingLatestNews : " + dbNewsDetail.get().getLatestNews());
                LOGGER.info("currentLatestNews : " + newsDetail.getLatestNews());
                
                if(newsDetail.getPlatformId() != 5 && existingPlatformId != 5) {
                	LOGGER.info("Updating news other than Cropdata news and Cropdata Latest News...");
                	newsDetail.setId(newsId);
                    newsRepository.save(newsDetail);
                	
                }else if((newsDetail.getPlatformId() == 5 && "Yes".equals(newsDetail.getLatestNews())) 
                    	&& (existingPlatformId == 5 && "Yes".equals(existingLatestNews))) {
                        LOGGER.info("Updating Cropdata Latest News...");
                        newsDetail.setId(newsId);
                        newsRepository.save(newsDetail);
                            	
                }else if((newsDetail.getPlatformId() == 5 && "No".equals(newsDetail.getLatestNews())) 
                          && (existingPlatformId == 5 && "No".equals(existingLatestNews))) {
                          LOGGER.info("Updating Cropdata News...");
                          newsDetail.setId(newsId);
                          newsRepository.save(newsDetail);
                                        	
                }else if((newsDetail.getPlatformId() == 5 && "Yes".equals(newsDetail.getLatestNews())) && (existingPlatformId == 5 && "No".equals(existingLatestNews))) {
                	LOGGER.info("Updating Cropdata News to Cropdata Latest News...");
                    newsRepository.modifyCropdataLatestNewsPriority();
                    
                    newsDetail.setId(newsId);
                	newsDetail.setPriority(1);
                    newsRepository.save(newsDetail);
                    
                    newsRepository.rearrangeCropdataNewsPriority(existingPriority);
                    
                }else if((newsDetail.getPlatformId() == 5 && "No".equals(newsDetail.getLatestNews())) && (existingPlatformId == 5 && "Yes".equals(existingLatestNews))) {
                	LOGGER.info("Updating Cropdata Latest News to Cropdata News...");                    
                    newsRepository.modifyCropdataNewsPriority();
                    
                    newsDetail.setId(newsId);
                	newsDetail.setPriority(1);
                    newsRepository.save(newsDetail);
                    
                    newsRepository.rearrangeCropdataLatestNewsPriority(existingPriority);
                	
                }else if((newsDetail.getPlatformId() == 5 && "Yes".equals(newsDetail.getLatestNews())) && existingPlatformId != 5) {
                	LOGGER.info("Updating news other than Cropdata news and Cropdata Latest News to Cropdata Latest News..."); 
                	newsRepository.modifyCropdataLatestNewsPriority();
                    
                    newsDetail.setId(newsId);
                	newsDetail.setPriority(1);
                    newsRepository.save(newsDetail);
                }else if((newsDetail.getPlatformId() == 5 && "No".equals(newsDetail.getLatestNews())) && existingPlatformId != 5) {
                	LOGGER.info("Updating news other than Cropdata news and Cropdata Latest News to Cropdata News...");
                	newsRepository.modifyCropdataNewsPriority();
                     
                    newsDetail.setId(newsId);
                 	newsDetail.setPriority(1);
                    newsRepository.save(newsDetail);
                }else if(newsDetail.getPlatformId() != 5 && (existingPlatformId == 5 && "Yes".equals(existingLatestNews))) {
                	LOGGER.info("Updating Cropdata Latest News to news other than Cropdata news and Cropdata Latest News...");
                	newsDetail.setId(newsId);
                 	newsDetail.setPriority(null);
                    newsRepository.save(newsDetail);
                    
                    newsRepository.rearrangeCropdataLatestNewsPriority(existingPriority);
                	
                }else if(newsDetail.getPlatformId() != 5 && (existingPlatformId == 5 && "No".equals(existingLatestNews))) {
                	LOGGER.info("Updating Cropdata News to news other than Cropdata news and Cropdata Latest News...");
                	newsDetail.setId(newsId);
                 	newsDetail.setPriority(null);
                    newsRepository.save(newsDetail);
                    
                    newsRepository.rearrangeCropdataNewsPriority(existingPriority);
                	
                }
                
                
				return responseMessageUtil.sendResponse(true, "News" + APIConstants.RESPONSE_UPDATE_SUCCESS + newsId,
						"");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"News Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + newsId);
			}

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + ex.getMessage());
		}
	}

	public List<Reports> getAllReports() {
		try {
			LOGGER.info("getting all reports info...");
			return reportsRepository.findAll();

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Report Data Found!");
		}
	}

	public Page<ReportsDto> getAllReportsPaginated(Integer page, Integer size, String searchText) {
		try {
			LOGGER.info("getting all reports info - paginated...");
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());

			return reportsRepository.findAllWithSearch(sortedByIdDesc, searchText);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Report Data Found For Searched Text -> " + searchText);
		}
	}

	public Reports getSelectedReportDetails(Integer reportId) {
		try {
			return reportsRepository.findById(reportId).orElse(null);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("Report Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + reportId);
		}
	}

	public ResponseMessage storeReportDetail(ReportUploadDTO reportUploadDTO) {
		try {
			LOGGER.info("saving report detail...");
            ObjectMapper objectMapper = new ObjectMapper();
            Reports reportDetail = objectMapper.readValue(reportUploadDTO.getData(), Reports.class);
            if (reportDetail.getSetAsAuthenticate() != null)
			{
				if (reportDetail.getSetAsAuthenticate())
				{
					reportDetail.setAuthenticate("Yes");
				} else
				{
					reportDetail.setAuthenticate("No");
				}
			}
            if (reportUploadDTO.getReportFile() != null)
            {
                FileUploadResponseDTO manageFile = this.getReportFile(reportUploadDTO.getReportFile());
                reportDetail.setFileUrl(manageFile.getPublicUrl());
            }
			reportsRepository.save(reportDetail);

			return responseMessageUtil.sendResponse(true, "Report" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (NoSuchFileException ex) {
			LOGGER.error("No Such File Exception : ", ex);
			return responseMessageUtil.sendResponse(false, "", ex.getMessage());
		}catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + ex.getMessage());
		}
	}

	public ResponseMessage updateSelectedReportDetails(Integer reportId, ReportUploadDTO reportUploadDTO) {
		try {
			Optional<Reports> dbReportDetail = reportsRepository.findById(reportId);

			if (dbReportDetail.isPresent()) {
				LOGGER.info("updating report detail...");
				ObjectMapper objectMapper = new ObjectMapper();
	            Reports reportDetail = objectMapper.readValue(reportUploadDTO.getData(), Reports.class);
	            LOGGER.info("URL..." + reportDetail.getFileUrl());
	            if (reportDetail.getSetAsAuthenticate() != null)
				{
					if (reportDetail.getSetAsAuthenticate())
					{
						reportDetail.setAuthenticate("Yes");
					} else
					{
						reportDetail.setAuthenticate("No");
					}
				}
	            if (reportUploadDTO.getReportFile() != null)
	            {
	                FileUploadResponseDTO manageFile = this.getReportFile(reportUploadDTO.getReportFile());
	                reportDetail.setFileUrl(manageFile.getPublicUrl());
	            }
				reportDetail.setId(reportId);
				reportsRepository.save(reportDetail);

				return responseMessageUtil.sendResponse(true,
						"Report" + APIConstants.RESPONSE_UPDATE_SUCCESS + reportId, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Report Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + reportId);
			}

		} catch (NoSuchFileException ex) {
			LOGGER.error("No Such File Exception : ", ex);
			return responseMessageUtil.sendResponse(false, "", ex.getMessage());
		}catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + ex.getMessage());
		}
	}

	public ResponseMessage downloadReportAndSaveReportRequest(Integer reportId,
			ReportDownloadRequestDto reportDownloadRequestDto) {
		try {
			ReportDownloadRequest reportDownloadRequest = new ReportDownloadRequest();
			BeanUtils.copyProperties(reportDownloadRequestDto, reportDownloadRequest);

			if (reportsRepository.existsById(reportId)) {
				
				Reports reports = reportsRepository.findById(reportId).get();
//				reportDownloadRequest.setPlatformID(reports.getPlatformID());

				LOGGER.info("Saving Report Request...");
				reportDownloadRequestRepository.save(reportDownloadRequest);

				return responseMessageUtil.sendResponse(true, reports.getFileUrl(), "");

			} else {
				LOGGER.info("Report Not Exist...");
				return responseMessageUtil.sendResponse(false, "",
						"Report Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + reportId);
			}
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + ex.getMessage());
		}
    }

    private FileUploadResponseDTO getManageFile(MultipartFile file) throws IOException
    {
		LocalDateTime localDate = LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String formattedDate = localDate.format(myFormatObj);
		String afterFormat=  formattedDate.replace("-","");
		String formatDate = afterFormat.replace(":","");
		String currentDate = formatDate.replace(" ","");

        String image = Objects.requireNonNull(file.getOriginalFilename());
		String i = image.substring(0, image.indexOf(".")).replaceAll("[^a-zA-Z0-9]", " ").replace(" ", "");
		LOGGER.info(" i :: {}", i);
        FileUploadResponseDTO fileUploadResponse = null;
        String[] SUPPORTED_EXTN = {".jpg", ".png", ".jpeg"};
        String fileExtension = Objects.requireNonNull(image);
		String currentImage = i.concat("-").concat(currentDate).concat(".").concat(image.substring(image.lastIndexOf(".")+1));
        LOGGER.info(" file :: {}", fileExtension);
        if (Arrays.stream(SUPPORTED_EXTN).anyMatch(fileExtension.toLowerCase()::contains))
        {
            String pathKey = "news";
            Map<String, Object> pathKeyResponse = fileManagerUtil.isPathKeyExist(pathKey);
            if (pathKeyResponse != null)
            {
                boolean result = (boolean) pathKeyResponse.get("exist");
                LOGGER.info("result :: {}",  result);
                if (result)
                {
                    LOGGER.info("inside if condition result is true ::  ");
                    fileUploadResponse = this.fileManagerUtil.uploadFile(pathKey, false,
							currentImage, true, file, null);
                } else
                {
                    String moduleName = "cropdata-portal";
                    String dirPath = pathKey;
                    LOGGER.info("inside else condition result is false ::  ");
                    DirectoryResponseDTO createDirResp = this.fileManagerUtil.createDirectory(moduleName,
                            pathKey, dirPath, null);
                    if (createDirResp != null && createDirResp.isSuccess())
                    {
                        fileUploadResponse = this.fileManagerUtil.uploadFile(pathKey, false,
								currentImage, true, file, null);
                        LOGGER.info("Create directory");
                    }
                }
            }
        } else {
            throw new NoSuchFileException("File Format Not supported!");
        }

        return fileUploadResponse;
    }
    
    private FileUploadResponseDTO getReportFile(MultipartFile file) throws IOException
	{
		FileUploadResponseDTO fileUploadResponse = null;
		LOGGER.info(" file :: " + file.getOriginalFilename());
		
		LocalDateTime localDate = LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String formattedDate = localDate.format(myFormatObj);
		String afterFormat=  formattedDate.replace("-","");
		String formatDate = afterFormat.replace(":","");
		String currentDate = formatDate.replace(" ","");
		
		String[] SUPPORTED_EXTN = { ".jpeg", ".jpg", ".png", ".pdf", ".doc", ".docx", ".xls", ".xlsx" };
//		String fileExtension = Objects.requireNonNull(file.getOriginalFilename())
//				.substring(file.getOriginalFilename().lastIndexOf('.')); 
//		String report = Objects.requireNonNull(file.getOriginalFilename()).replace(" ", "");
		String report = Objects.requireNonNull(file.getOriginalFilename());
		LOGGER.info(" report :: {}", report);
		String reportWithoutExtension = report.substring(0, report.lastIndexOf(".")).replaceAll("[^a-zA-Z0-9]", " ").replace(" ", "");
		LOGGER.info(" fileWithoutExtension :: {}", reportWithoutExtension);
		String fileExtension = Objects.requireNonNull(report).substring(report.lastIndexOf('.'));
		String currentReport = reportWithoutExtension.concat("-").concat(currentDate).concat(".").concat(report.substring(report.lastIndexOf(".")+1));
		LOGGER.info(" currentReport :: {}", currentReport);
		LOGGER.info(" fileExtension :: {}", fileExtension);
		String pathKey = "report";
		if (Arrays.stream(SUPPORTED_EXTN).anyMatch(fileExtension::contains)) {
			Map<String, Object> pathKeyResponse = fileManagerUtil.isPathKeyExist(pathKey);
			LOGGER.info("pathKey ::  " + pathKeyResponse.get("exist"));
			if (pathKeyResponse.get("exist") != null) {
				LOGGER.info("pathKeyResponse ::  " + pathKeyResponse);
				Object exist = pathKeyResponse.get("exist");
				LOGGER.info("result ::  " + exist);

				if (exist.toString().equals("true")) {
					LOGGER.info("inside if condition result is true ::  ");
					fileUploadResponse = this.fileManagerUtil.uploadFile(pathKey, false, currentReport, true,
							file, null);
				} else {
					String moduleName = "cropdata-portal";
					String dirPath = pathKey;
					LOGGER.info("inside else condition result is false ::  ");
					DirectoryResponseDTO createDirResp = this.fileManagerUtil.createDirectory(moduleName, pathKey, dirPath,
							null);
					if (createDirResp != null && createDirResp.isSuccess()) {
						fileUploadResponse = this.fileManagerUtil.uploadFile(pathKey, false, currentReport,
								true, file, null);
						LOGGER.info("Create directory");
					}
				}
			}
		}else {
			LOGGER.info("file format not matched");
			throw new NoSuchFileException("File Format Not supported! (Supported Format : .jpeg, .jpg, .png, .pdf, .doc, .docx, .xls, .xlsx)");
		}
		return fileUploadResponse;
    }
    
    public ResponseMessage deactiveReport(int id) {
		try {
			Optional<Reports> optionalReport = reportsRepository.findById(id);
			if (optionalReport.isPresent()) {

				reportsRepository.deactiveReport(id);

				return responseMessageUtil.sendResponse(true, "Report" + APIConstants.RESPONSE_DEACTIVE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Report" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
    
    public ResponseMessage activeReport(int id) {
		try {
			Optional<Reports> optionalReport = reportsRepository.findById(id);
			if (optionalReport.isPresent()) {

				reportsRepository.activeReport(id);

				return responseMessageUtil.sendResponse(true, "Report" + APIConstants.RESPONSE_ACTIVE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Report" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}

    public ResponseMessage deactiveNews(int id) {
		try {
			Optional<News> optionalNews = newsRepository.findById(id);
			if (optionalNews.isPresent()) {

				newsRepository.deactiveNews(id);

				return responseMessageUtil.sendResponse(true, "News" + APIConstants.RESPONSE_DEACTIVE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"News" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
    
    public ResponseMessage activeNews(int id) {
		try {
			Optional<News> optionalNews = newsRepository.findById(id);
			if (optionalNews.isPresent()) {

				newsRepository.activeNews(id);

				return responseMessageUtil.sendResponse(true, "News" + APIConstants.RESPONSE_ACTIVE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"News" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
    
    public List<NewsDataDto> cropdataPriorityNewsList() {
		try {
			LOGGER.info("getting all cropdata news info...");
			return newsRepository.cropdataPriorityNewsList();

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Cropdata News Data Found!");
		}
	}
    
    public ResponseMessage updateCropdataPriorityNews(List<CropdataPriorityNewsDto> newsList) {
		try {
			
			for(CropdataPriorityNewsDto cropdataPriorityNewsDto : newsList) {
				LOGGER.info("News Id...{} " + cropdataPriorityNewsDto.getId() + " News Priority...{} " + cropdataPriorityNewsDto.getPriority());
				Optional<News> newsDetail = newsRepository.findById(cropdataPriorityNewsDto.getId());
				if (newsDetail.isPresent()) {		
					LOGGER.info("Updating Cropdata News Priority...");
					News news = newsDetail.get();
					news.setId(cropdataPriorityNewsDto.getId());
					news.setPriority(cropdataPriorityNewsDto.getPriority());
					newsRepository.save(news);
			}
			
		}
			return responseMessageUtil.sendResponse(true, "Cropdata News Priority Updated Successfully",
					"");
		}catch (Exception ex) {
			return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + ex.getMessage());
		}
	}
    
    public List<NewsDataDto> cropdataLatestNewsPriorityList() {
		try {
			LOGGER.info("getting all cropdata latest news info...");
			return newsRepository.cropdataLatestNewsPriorityList();

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Cropdata Latest News Data Found!");
		}
	}
    
    public ResponseMessage updateCropdataLatestNewsPriority(List<CropdataPriorityNewsDto> newsList) {
		try {
			
			for(CropdataPriorityNewsDto cropdataPriorityNewsDto : newsList) {
				LOGGER.info("News Id...{} " + cropdataPriorityNewsDto.getId() + " News Priority...{} " + cropdataPriorityNewsDto.getPriority());
				Optional<News> newsDetail = newsRepository.findById(cropdataPriorityNewsDto.getId());
				if (newsDetail.isPresent()) {		
					LOGGER.info("Updating Cropdata Latest News Priority...");
					News news = newsDetail.get();
					news.setId(cropdataPriorityNewsDto.getId());
					news.setPriority(cropdataPriorityNewsDto.getPriority());
					newsRepository.save(news);
			}
			
		}
			return responseMessageUtil.sendResponse(true, "Cropdata Latest News Priority Updated Successfully",
					"");
		}catch (Exception ex) {
			return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + ex.getMessage());
		}
	}
    
}
