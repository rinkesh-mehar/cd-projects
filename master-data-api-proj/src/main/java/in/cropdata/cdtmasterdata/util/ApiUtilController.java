package in.cropdata.cdtmasterdata.util;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import in.cropdata.cdtmasterdata.dto.ResponseMessage;

@RestController
@RequestMapping("/api/util")
public class ApiUtilController {

	private static final Logger logger = LoggerFactory.getLogger(ApiUtilController.class);

	@Autowired
	private ApiUtilService apiUtilService;

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/download-excel-format")
	public ResponseEntity<Resource> downloadExcelFormat(HttpServletRequest request, @RequestParam String tableName) {

		if (StringUtils.isNotBlankOrNull(tableName)) {

			String filePath = apiUtilService.downloadExcelFormat(tableName);
			// Load file as Resource
			Resource resource = new FileSystemResource(filePath);

			// Try to determine file's content type
			String contentType = null;
			try {
				contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
			} catch (IOException ex) {
				logger.info("Could not determine file type.");
				logger.error("File Download Error", ex);
			}

			// Fallback to the default content type if type could not be determined
			if (contentType == null) {
				contentType = "application/octet-stream";
			}
			return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + tableName + ".xlsx")
					.body(resource);

		} // if tableName not null
		throw new RuntimeException("Failed to download excel file.");
	}// downloadExcelFormat

	@PostMapping(value = "/upload-excel-file")
	public ResponseEntity<ResponseMessage> uploadExcelFileInDB(@RequestParam MultipartFile excelFile) {
		ResponseMessage response = apiUtilService.readExcel(excelFile);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}// uploadExcelFileInDB

	@PutMapping(value = "/update-status")
	public ResponseEntity<Map<String, Object>> updateStatus(@RequestBody ActionVO actionVO) {
		Map<String, Object> response = apiUtilService.updateStatus(actionVO);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}// updateStatus

}// ApiUtilController
