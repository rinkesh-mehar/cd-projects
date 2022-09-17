package in.cropdata.cdtmasterdata.website.controller;

import java.util.List;

import in.cropdata.cdtmasterdata.model.PlatFormMaster;
import in.cropdata.cdtmasterdata.website.dto.CropdataPriorityNewsDto;
import in.cropdata.cdtmasterdata.website.dto.NewsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.InvalidDataException;
import in.cropdata.cdtmasterdata.website.dto.NewsDataDto;
import in.cropdata.cdtmasterdata.website.dto.ReportDownloadRequestDto;
import in.cropdata.cdtmasterdata.website.dto.ReportUploadDTO;
import in.cropdata.cdtmasterdata.website.dto.ReportsDto;
import in.cropdata.cdtmasterdata.website.model.News;
import in.cropdata.cdtmasterdata.website.model.Reports;
import in.cropdata.cdtmasterdata.website.service.NewsReportsService;

/**
 * Controller for processing API requests.
 * 
 * @since 1.0
 * @author PranaySK
 */

@RestController
@RequestMapping("/site")
public class NewsReportsController {

	private static final Logger LOGGER = LoggerFactory.getLogger(NewsReportsController.class);

	@Autowired
	private NewsReportsService service;

	/** News Handling APIs */

	@GetMapping("/news")
	public List<News> getAllNewsDetails() {
		return service.getAllNews();
	}

	@GetMapping("/platform")
	public List<PlatFormMaster> getPlatForms(){
		return service.getPlatForm();
	}

	@GetMapping("/news/list")
	public Page<NewsDataDto> getAllNewsPaginated(@RequestParam("page") Integer page, @RequestParam("size") Integer size,
												 @RequestParam(required = false, defaultValue = "") String searchText) {

		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return service.getAllNewsPaginated(page, size, searchText);
	}

	@GetMapping("/news/{id}")
	public News getNewsDetails(@PathVariable(name = "id", required = true) Integer newsId) {

		if (newsId == null) {
			throw new InvalidDataException("News Id can not be null!");
		}

		LOGGER.info("Get Selected News for ID -> {}", newsId);
		return service.getSelectedNewsDetails(newsId);
	}

	@PostMapping("/news")
	public ResponseEntity<ResponseMessage> addNewsDetails(@ModelAttribute NewsDTO newsDetail) {

		if (newsDetail == null) {
			throw new InvalidDataException("News data can not be null!");
		}

		LOGGER.info("Adding News Details...");
		return new ResponseEntity<>(service.storeNewsDetail(newsDetail), HttpStatus.CREATED);
	}

	@PutMapping("/news/{id}")
	public ResponseEntity<ResponseMessage> updateNewsDetails(@PathVariable(name = "id", required = true) Integer newsId,
			@ModelAttribute NewsDTO newsDetail) {

		if (newsId == null) {
			throw new InvalidDataException("News Id can not be null!");
		}

		if (newsDetail == null) {
			throw new InvalidDataException("News data can not be null!");
		}

		LOGGER.info("Updating News detail for ID -> {}", newsId);
		return new ResponseEntity<>(service.updateSelectedNewsDetails(newsId, newsDetail), HttpStatus.OK);
	}

	/** Reports Handling APIs */

	@GetMapping("/reports")
	public List<Reports> getAllReportsDetails() {
		return service.getAllReports();
	}

	@GetMapping("/reports/list")
	public Page<ReportsDto> getAllReportsPaginated(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {

		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return service.getAllReportsPaginated(page, size, searchText);
	}

	@GetMapping("/reports/{id}")
	public Reports getReportDetail(@PathVariable(name = "id", required = true) Integer reportId) {

		if (reportId == null) {
			throw new InvalidDataException("Report Id can not be null!");
		}

		LOGGER.info("Get Selected Report for ID -> {}", reportId);
		return service.getSelectedReportDetails(reportId);
	}

	@PostMapping("/reports")
	public ResponseEntity<ResponseMessage> addReportsDetails(@ModelAttribute ReportUploadDTO reportUploadDTO) {

		if (reportUploadDTO == null) {
			throw new InvalidDataException("Reports Data can not be null!");
		}

		LOGGER.info("Adding Report Details...");
		return new ResponseEntity<>(service.storeReportDetail(reportUploadDTO), HttpStatus.CREATED);
	}

	@PutMapping("/reports/{id}")
	public ResponseEntity<ResponseMessage> updateReportDetails(
			@PathVariable(name = "id", required = true) Integer reportId, @ModelAttribute ReportUploadDTO reportUploadDTO) {

		if (reportId == null) {
			throw new InvalidDataException("Report Id can not be null!");
		}

		if (reportUploadDTO == null) {
			throw new InvalidDataException("Reports Data can not be null!");
		}

		LOGGER.info("Updating Report detail for ID -> {}", reportId);
		return new ResponseEntity<>(service.updateSelectedReportDetails(reportId, reportUploadDTO), HttpStatus.OK);
	}

	//Portal API
	@PostMapping("/download-report/{reportId}")
	public ResponseEntity<ResponseMessage> downloadReportAndSaveReportRequest(@PathVariable Integer reportId,
			@RequestBody ReportDownloadRequestDto reportDownloadRequestDto) {

		if (reportId == null) {
			throw new InvalidDataException("Report Id can not be null!");
		}

		if (reportDownloadRequestDto == null) {
			throw new InvalidDataException("Reports Download Request Data can not be null!");
		}

		return new ResponseEntity<>(service.downloadReportAndSaveReportRequest(reportId, reportDownloadRequestDto),
				HttpStatus.CREATED);

	}
	
	@PutMapping("/deactive-report/{id}")
	public ResponseMessage deactiveReport(@PathVariable int id) {
		return service.deactiveReport(id);
	}

	@PutMapping("/active-report/{id}")
	public ResponseMessage activeReport(@PathVariable int id) {
		return service.activeReport(id);
	}
	
	@PutMapping("/deactive-news/{id}")
	public ResponseMessage deactiveNews(@PathVariable int id) {
		return service.deactiveNews(id);
	}

	@PutMapping("/active-news/{id}")
	public ResponseMessage activeNews(@PathVariable int id) {
		return service.activeNews(id);
	}
	
	@GetMapping("/cropdata-priority-news")
	public List<NewsDataDto> cropdataPriorityNewsList() {
		return service.cropdataPriorityNewsList();
	}
	
	@PutMapping("/update-cropdata-priority-news")
	public ResponseEntity<ResponseMessage> updateCropdataPriorityNews(@RequestBody List<CropdataPriorityNewsDto> newsList) {
		
		if (newsList == null || newsList.size() == 0) {
			throw new InvalidDataException("News data can not be null!");
		}
		
		LOGGER.info("Updating Priority From Controller -> {}");
		return new ResponseEntity<>(service.updateCropdataPriorityNews(newsList), HttpStatus.OK);
		
	}
	
	@GetMapping("/cropdata-latest-news-priority")
	public List<NewsDataDto> cropdataLatestNewsPriorityList() {
		return service.cropdataLatestNewsPriorityList();
	}
	
	@PutMapping("/update-cropdata-latest-news-priority")
	public ResponseEntity<ResponseMessage> updateCropdataLatestNewsPriority(@RequestBody List<CropdataPriorityNewsDto> newsList) {
		
		if (newsList == null || newsList.size() == 0) {
			throw new InvalidDataException("News data can not be null!");
		}
		
		LOGGER.info("Updating Priority From Controller -> {}");
		return new ResponseEntity<>(service.updateCropdataLatestNewsPriority(newsList), HttpStatus.OK);
		
	}
}
