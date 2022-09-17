package in.cropdata.cdtmasterdata.website.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.cdtmasterdata.exceptions.InvalidDataException;
import in.cropdata.cdtmasterdata.website.model.vo.ReportDownloadRequestVO;
import in.cropdata.cdtmasterdata.website.service.ReportDownloadRequestService;

@RestController
@RequestMapping("/site/report-download-request")
public class ReportDownloadRequestController {
	
	@Autowired
	ReportDownloadRequestService reportDownloadRequestService;
	
	private static final Logger logger = LoggerFactory.getLogger(ReportDownloadRequestController.class);
	
	@GetMapping("/paginatedList")
	public Page<ReportDownloadRequestVO> getReportDownloadRequestList(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {
		logger.info("Contact Request Details...");

		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return reportDownloadRequestService.getReportDownloadRequestList(page, size, searchText);
	}
	
	@GetMapping("/total-page-no")
	public List<Integer> listOfPageNoOfReportDownloadRequest() {
		return reportDownloadRequestService.listOfPageNoOfReportDownloadRequest();
	}
	
	@GetMapping("/download")
	  public ResponseEntity<Resource> exportToExcelReportDownloadRequest(@RequestParam("page") Integer page) {
		
		if (page == null) {
			throw new InvalidDataException("page can not be null!");
		}
		
//	    String filename = "Report Request.xlsx";
	    InputStreamResource file = new InputStreamResource(reportDownloadRequestService.exportToExcelReportDownloadRequest(page-1, 200));

	    return ResponseEntity.ok()
//	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
	        .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
	        .body(file);
		
		
	  }

}
