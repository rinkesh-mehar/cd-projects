package in.cropdata.cdtmasterdata.website.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.exceptions.InvalidDataException;
import in.cropdata.cdtmasterdata.website.model.vo.ReportDownloadRequestVO;
import in.cropdata.cdtmasterdata.website.repository.ReportDownloadRequestRepository;

@Service
public class ReportDownloadRequestService {

private static final Logger LOGGER = LoggerFactory.getLogger(ReportDownloadRequestService.class);
	
	private static final String SERVER_ERROR_MSG = "Server Error : ";
	
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = { "ID", "Name", "Emai", "Mobile Number", "Industry", "Other Industry", "Organization", "Platform", "Title"};
	static String SHEET = "Report Request";

	@Autowired
	ReportDownloadRequestRepository reportDownloadRequestRepository;
	
	public Page<ReportDownloadRequestVO> getReportDownloadRequestList(Integer page, Integer size, String searchText) {
		try {
			LOGGER.info("getting all report download request info - paginated...");
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());

			return reportDownloadRequestRepository.getReportDownloadRequestList(sortedByIdDesc, searchText);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Report Download Request Data Found For Searched Text -> " + searchText);
		}
	}
	
public List<Integer> listOfPageNoOfReportDownloadRequest() {
		
		try {
		List<Integer> listOfPageNo = new ArrayList<>();
		
		Integer totalCount = reportDownloadRequestRepository.totalReportDownloadRequestCount();
		
		for(int i = 1; i<= totalCount; i++) {
			listOfPageNo.add(i);
		}
		
		return listOfPageNo;

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Records Found.");
		}

	}
	
	public ByteArrayInputStream exportToExcelReportDownloadRequest(Integer page, Integer size) {
		
		LOGGER.info("getting  report download request to esxcel...");
		
		Pageable sortedByIdAsc = PageRequest.of(page, size, Sort.by("id").descending());
	    Page<ReportDownloadRequestVO> reportDownloadRequestList = reportDownloadRequestRepository.exportToExcelReportDownloadRequest(sortedByIdAsc);

	    ByteArrayInputStream in = exportToExcel(reportDownloadRequestList);
	    return in;
	  }
	
	public ByteArrayInputStream exportToExcel(Page<ReportDownloadRequestVO> reportDownloadRequestList){
		
		LOGGER.info("generating report download request...");
		
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			
//				CreationHelper createHelper = workbook.getCreationHelper();
			
		      Sheet sheet = workbook.createSheet(SHEET);
		      
		      Font headerFont = workbook.createFont();
		      headerFont.setBold(true);
		      
		      CellStyle headerCellStyle = workbook.createCellStyle();
		      headerCellStyle.setFont(headerFont);

		      // Header
		      Row headerRow = sheet.createRow(0);

		      for (int col = 0; col < HEADERs.length; col++) {
		        Cell cell = headerRow.createCell(col);
		        cell.setCellValue(HEADERs[col]);
		        cell.setCellStyle(headerCellStyle);
		      }
		      
		   // CellStyle for Decimal
//		      CellStyle CellStyleforDecimal = workbook.createCellStyle();
//		      CellStyleforDecimal.setDataFormat(createHelper.createDataFormat().getFormat("#.00"));
		     

		      int rowIdx = 1;
		      for (ReportDownloadRequestVO reportDownloadRequest : reportDownloadRequestList) {
		        Row row = sheet.createRow(rowIdx++);
		        
		        int columnCount = 0;
		        
		        row.createCell(columnCount++).setCellValue(reportDownloadRequest.getID());
		        row.createCell(columnCount++).setCellValue(reportDownloadRequest.getName());
		        row.createCell(columnCount++).setCellValue(reportDownloadRequest.getEmail());
		        row.createCell(columnCount++).setCellValue(reportDownloadRequest.getMobile());
		        row.createCell(columnCount++).setCellValue(reportDownloadRequest.getIndustry() == null? "" : reportDownloadRequest.getIndustry());
		        row.createCell(columnCount++).setCellValue(reportDownloadRequest.getOtherIndustry() == null? "" : reportDownloadRequest.getOtherIndustry());
		        row.createCell(columnCount++).setCellValue(reportDownloadRequest.getOrganization() == null? "" : reportDownloadRequest.getOrganization());
		        row.createCell(columnCount++).setCellValue(reportDownloadRequest.getPlatform() == null? "" : reportDownloadRequest.getPlatform());
		        row.createCell(columnCount++).setCellValue(reportDownloadRequest.getTitle() == null? "" : reportDownloadRequest.getTitle());
		        
		      }

		      workbook.write(out);
		      return new ByteArrayInputStream(out.toByteArray());
		    } catch (IOException e) {
		      throw new InvalidDataException("fail to import data to Excel file: " + e.getMessage());
		    }		
	}
	
}
