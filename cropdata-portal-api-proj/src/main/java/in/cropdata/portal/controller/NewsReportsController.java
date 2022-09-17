package in.cropdata.portal.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.portal.dto.ReportDownloadRequestDTO;
import in.cropdata.portal.dto.ResponseMessage;
import in.cropdata.portal.exceptions.InvalidDataException;
import in.cropdata.portal.model.News;
import in.cropdata.portal.model.Reports;
import in.cropdata.portal.service.NewsReportsService;
import in.cropdata.portal.vo.NewsVO;
import in.cropdata.portal.vo.ReportsVO;

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
	public Map<String, List<News>> getAllNewsDetails() {
		return service.getAllNews();
	}

	@GetMapping("/news/list")
	public Page<NewsVO> getAllNewsPaginated(@RequestParam("page") Integer page, @RequestParam("size") Integer size,
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

	/** Reports Handling APIs */

	@GetMapping("/reports")
	public Map<String, List<Reports>> getAllReportsDetails() {
		return service.getAllReports();
	}

	@GetMapping("/reports/list")
	public Page<ReportsVO> getAllReportsPaginated(@RequestParam("page") Integer page,
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

	@PostMapping("/download-report/{reportId}")
	public ResponseEntity<ResponseMessage> downloadReportAndSaveReportRequest(@PathVariable Integer reportId,
			@RequestBody ReportDownloadRequestDTO reportDownloadRequestDto) {

		if (reportId == null) {
			throw new InvalidDataException("Report Id can not be null!");
		}

		if (reportDownloadRequestDto == null) {
			throw new InvalidDataException("Reports Download Request Data can not be null!");
		}
		
		if (reportDownloadRequestDto.getName() == null || reportDownloadRequestDto.getName() == "") {
			throw new InvalidDataException("Name can not be null!");
		}
		
		if (reportDownloadRequestDto.getEmail() == null || reportDownloadRequestDto.getEmail() == "") {
			throw new InvalidDataException("Email can not be null!");
		}
		
		if (reportDownloadRequestDto.getMobile() == null || reportDownloadRequestDto.getMobile() == "") {
			throw new InvalidDataException("Mobile Number can not be null!");
		}

		return new ResponseEntity<>(service.downloadReportAndSaveReportRequest(reportId, reportDownloadRequestDto),
				HttpStatus.CREATED);
	}
}
