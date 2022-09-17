package in.cropdata.cdtmasterdata.website.service;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
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
import org.springframework.transaction.annotation.Transactional;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.exceptions.InvalidDataException;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;
import in.cropdata.cdtmasterdata.website.dto.BuyerPreApplicationDto;
import in.cropdata.cdtmasterdata.website.model.ApplicantApplicationDetails;
import in.cropdata.cdtmasterdata.website.model.ApplicantCommoditiesOfInterest;
import in.cropdata.cdtmasterdata.website.model.ApplicantDetails;
import in.cropdata.cdtmasterdata.website.model.ApplicantOtherBusinessDetails;
import in.cropdata.cdtmasterdata.website.model.vo.BuyerPreApplicationExportToExcelVO;
import in.cropdata.cdtmasterdata.website.model.vo.BuyerPreApplicationGetByIdVO;
import in.cropdata.cdtmasterdata.website.model.vo.BuyerPreApplicationListVO;
import in.cropdata.cdtmasterdata.website.repository.ApplicantApplicationDetailsRepository;
import in.cropdata.cdtmasterdata.website.repository.ApplicantCommoditiesOfInterestRepository;
import in.cropdata.cdtmasterdata.website.repository.ApplicantDetailsRepository;
import in.cropdata.cdtmasterdata.website.repository.ApplicantOtherBusinessDetailsRepository;

@Service
public class BuyerPreApplicationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BuyerPreApplicationService.class);

	private static final String SERVER_ERROR_MSG = "Server Error : ";

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	@Autowired
	private ApplicantDetailsRepository applicantDetailsRepository;

	@Autowired
	private ApplicantApplicationDetailsRepository applicantApplicationDetailsRepository;

	@Autowired
	private ApplicantCommoditiesOfInterestRepository applicantCommoditiesOfInterestRepository;

//	@Autowired
//	private ApplicantContactDetailsRepository applicantContactDetailsRepository;
//
//	@Autowired
//	private ApplicantOfficeSpaceDetailsRepository applicantOfficeSpaceDetailsRepository;

	@Autowired
	private ApplicantOtherBusinessDetailsRepository applicantOtherBusinessDetailsRepository;
	
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = { "ID", "Application Type", "Origin", "Applicant Type", "Reference", "Company Name", "Nature of Business"
			  					, "Non-Agriculture Business Name", "Present Buisness / Occupation", "Date of Incorporation/Registration"
			  					, "Income Tax Permanent Account No. (PAN)", "Corporate Identity No. (CIN)", "GST No.", "Company Registration Number"
			  					, "Value Added Tax No.(VAT)", "Currency", "Average Revenue (Last 3 Years)", "Contact Person Name", "Designation"
			  					, "Other Designation", "Telephone Number", "Mobile Number", "Email Address", "Company Website", "Commodities of Interest"
			  					, "Country Name", "State Name", "City Name", "Building Name", "Street Name", "Postal Code"};
	static String SHEET = "Pre-Application";

	public Page<BuyerPreApplicationListVO> getBuyerPreApplicationListByPagenation(Integer page, Integer size,
			String searchText) {
		try {
			LOGGER.info("getting all buyer pre-application info - paginated...");
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());

			return applicantDetailsRepository.getBuyerPreApplicationListByPagenation(sortedByIdDesc, searchText);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Buyer Pre-Application Data Found For Searched Text -> " + searchText);
		}
	}

	public BuyerPreApplicationGetByIdVO getBuyerPreApplicationById(Integer id) {

		try {

			LOGGER.info("getting buyer pre-application by Id...");
			return applicantDetailsRepository.getBuyerPreApplicationById(id);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("Buyer Pre-Application Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
		}

	}

	@Transactional
	public ResponseMessage updatePreBuyerApplication(Integer id, BuyerPreApplicationDto buyerPreApplicationDto) {
		try {
			LOGGER.info("Inside updatePreBuyerApplication method...");

			// 1) Updating Applicant Details
			Optional<ApplicantDetails> applicantDetailsOptional = applicantDetailsRepository.findById(id);
			if (applicantDetailsOptional.isPresent()) {
				ApplicantDetails applicantDetails = applicantDetailsOptional.get();
				applicantDetails.setId(id);
				if(buyerPreApplicationDto.getName() == null || "".equals(buyerPreApplicationDto.getName())) {
					
					applicantDetails.setName(null);
				}else {
					applicantDetails.setName(buyerPreApplicationDto.getName());
				}
				if(buyerPreApplicationDto.getDesignationID() == null) {
					applicantDetails.setDesignationID(null);
				}else {
					applicantDetails.setDesignationID(buyerPreApplicationDto.getDesignationID());
				
				}
				if(buyerPreApplicationDto.getDesignationID() != null && buyerPreApplicationDto.getDesignationID() == 6) {
					applicantDetails.setOtherDesignationName(buyerPreApplicationDto.getOtherDesignationName());
				}else {
					applicantDetails.setOtherDesignationName(null);
				}
				if(buyerPreApplicationDto.getTelephoneNumber() == null || "".equals(buyerPreApplicationDto.getTelephoneNumber())) {
					applicantDetails.setTelephoneNumber(null);
				}else {
					applicantDetails.setTelephoneNumber(buyerPreApplicationDto.getTelephoneNumber());
				
				}
				if(buyerPreApplicationDto.getMobileNumber() == null || "".equals(buyerPreApplicationDto.getMobileNumber())) {
					applicantDetails.setMobileNumber(null);	
				}else {
				applicantDetails.setMobileNumber(buyerPreApplicationDto.getMobileNumber());
				}
				if(buyerPreApplicationDto.getEmailAddress() == null || "".equals(buyerPreApplicationDto.getEmailAddress())) {
					applicantDetails.setEmailAddress(null);
				}else {
				applicantDetails.setEmailAddress(buyerPreApplicationDto.getEmailAddress());
				}
//				applicantDetails.setRegisteredAddress(buyerPreApplicationDto.getRegisteredAddress());
				if(buyerPreApplicationDto.getNationality() == null || "".equals(buyerPreApplicationDto.getNationality())) {
					applicantDetails.setNationality(null);
				}else {
					applicantDetails.setNationality(buyerPreApplicationDto.getNationality());
				}
//				applicantDetails.setLocationOfInterest(buyerPreApplicationDto.getLocationOfInterest());
				applicantDetails.setApplicantTypeID(buyerPreApplicationDto.getApplicantTypeID());
				if(buyerPreApplicationDto.getWebsite() == null || "".equals(buyerPreApplicationDto.getWebsite())) {
					applicantDetails.setWebsite(null);
				}else {
				applicantDetails.setWebsite(buyerPreApplicationDto.getWebsite());
				}

			applicantDetailsRepository.save(applicantDetails);
				LOGGER.error("Applicant Details Updated...");
			}

			// 2) Updating Applicant Application Details
			ApplicantApplicationDetails applicantApplicationDetails = applicantApplicationDetailsRepository
					.findByIdAndApplicantID(buyerPreApplicationDto.getApplicantApplicationId(),id); // here 2nd parameter 'id' = ApplicantID
			if (applicantApplicationDetails != null) {
				applicantApplicationDetails.setId(buyerPreApplicationDto.getApplicantApplicationId());
				applicantApplicationDetails.setApplicantID(id);
				applicantApplicationDetails.setApplicationTypeID(buyerPreApplicationDto.getApplicationTypeID());
				applicantApplicationDetails.setCompanyName(buyerPreApplicationDto.getCompanyName());
				applicantApplicationDetails.setDateOfIncorporation(buyerPreApplicationDto.getDateOfIncorporation());
				if("Indian".equals(buyerPreApplicationDto.getNationality())) {
					applicantApplicationDetails.setPanNumber(buyerPreApplicationDto.getPanNumber());
				}else {
					applicantApplicationDetails.setPanNumber(null);
				}
					
				if("Indian".equals(buyerPreApplicationDto.getNationality()) && buyerPreApplicationDto.getApplicantTypeID() == 6) {
					applicantApplicationDetails.setCinNumber(null);
				}else if("Indian".equals(buyerPreApplicationDto.getNationality()) && buyerPreApplicationDto.getApplicantTypeID() != 6){
					applicantApplicationDetails.setCinNumber(buyerPreApplicationDto.getCinNumber());
				}else if("International".equals(buyerPreApplicationDto.getNationality())) {
					applicantApplicationDetails.setCinNumber(null);
				}
					
				if("Indian".equals(buyerPreApplicationDto.getNationality())) {
					applicantApplicationDetails.setGstNumber(buyerPreApplicationDto.getGstNumber());
				}else {
					applicantApplicationDetails.setGstNumber(null);
				}
					
				if("International".equals(buyerPreApplicationDto.getNationality())) {
					applicantApplicationDetails.setCompanyRegistrationNumber(buyerPreApplicationDto.getCompanyRegistrationNumber());
				}else {
					applicantApplicationDetails.setCompanyRegistrationNumber(null);
				}
					
				if("International".equals(buyerPreApplicationDto.getNationality())) {
					applicantApplicationDetails.setVat(buyerPreApplicationDto.getVat());
				}else {
					applicantApplicationDetails.setVat(null);
				}
				
				applicantApplicationDetails.setBuildingName(buyerPreApplicationDto.getBuildingName());
				applicantApplicationDetails.setStreetName(buyerPreApplicationDto.getStreetName());
				applicantApplicationDetails.setPostalCode(buyerPreApplicationDto.getPostalCode());
				applicantApplicationDetails.setCountry(buyerPreApplicationDto.getCountry());
				applicantApplicationDetails.setState(buyerPreApplicationDto.getState());
				applicantApplicationDetails.setCity(buyerPreApplicationDto.getCity());
//				applicantApplicationDetails.setBusinessAddress(buyerPreApplicationDto.getBusinessAddress());
				if(buyerPreApplicationDto.getReference() == null || "".equals(buyerPreApplicationDto.getReference())) {
					applicantApplicationDetails.setReference(null);
				}else {
					applicantApplicationDetails.setReference(buyerPreApplicationDto.getReference());
				}

			applicantApplicationDetailsRepository.save(applicantApplicationDetails);
				LOGGER.info("Applicant Application Details Updated...");
			}

			// 3) Updating Applicant Commodities Of Interest
					
			Integer[] existingCommoditiesId = applicantCommoditiesOfInterestRepository.findCommoditiesIdByApplicantApplicationID(buyerPreApplicationDto.getApplicantApplicationId());
			
			Integer[] commoditiesId = buyerPreApplicationDto.getCommoditiesId();
			
			// deleting the commoditiesId which exist in DB but de-selected while editing the form. 
			 
			for (int i = 0; i < existingCommoditiesId.length; i++) 
	        { 
	            int j; 
	              
	            for (j = 0; j < commoditiesId.length; j++) 
	                if (existingCommoditiesId[i] == commoditiesId[j]) 
	                    break; 
	  
	            if (j == commoditiesId.length) {
	            	LOGGER.info(existingCommoditiesId[i] + " * "); 
							applicantCommoditiesOfInterestRepository.deleteCommoditiesOfInterest(buyerPreApplicationDto.getApplicantApplicationId(),existingCommoditiesId[i]);
	            }
	        } 
			
			for (Integer commodityId : commoditiesId) {
				
				ApplicantCommoditiesOfInterest applicantCommoditiesOfInterestUpdate = applicantCommoditiesOfInterestRepository.findByApplicantApplicationIDAndCommodityID(buyerPreApplicationDto.getApplicantApplicationId(),commodityId);
				// Update if already exist else create new record
				if(applicantCommoditiesOfInterestUpdate != null) {
					LOGGER.info("Update commodityId...{} " + commodityId); 
					applicantCommoditiesOfInterestUpdate.setId(applicantCommoditiesOfInterestUpdate.getId());
					applicantCommoditiesOfInterestUpdate.setApplicantApplicationID(buyerPreApplicationDto.getApplicantApplicationId());
					applicantCommoditiesOfInterestUpdate.setCommodityID(commodityId);
					applicantCommoditiesOfInterestRepository.save(applicantCommoditiesOfInterestUpdate);
					
				}else {
					LOGGER.info("Insert commodityId...{} " + commodityId); 
					ApplicantCommoditiesOfInterest applicantCommoditiesOfInterestInsert = new ApplicantCommoditiesOfInterest();
					applicantCommoditiesOfInterestInsert.setApplicantApplicationID(buyerPreApplicationDto.getApplicantApplicationId());
					applicantCommoditiesOfInterestInsert.setCommodityID(commodityId);
					applicantCommoditiesOfInterestRepository.save(applicantCommoditiesOfInterestInsert);
				}				
			}
			LOGGER.info("Applicant Commodities Of Interest Updated...");

			// 4) Updating Applicant Contact Details
//			ApplicantContactDetails applicantContactDetails = applicantContactDetailsRepository
//					.findByApplicantIDAndApplicationAplicantID(id,buyerPreApplicationDto.getApplicantApplicationId()); // here 1st parameter 'id' = ApplicantID
//			if (applicantApplicationDetails != null) {
//			applicantContactDetails.setApplicantID(id);
//			applicantContactDetails.setApplicationAplicantID(buyerPreApplicationDto.getApplicantApplicationId());
//			if(buyerPreApplicationDto.getCompanyAuthSignName() == null || "".equals(buyerPreApplicationDto.getCompanyAuthSignName())) {
//				applicantContactDetails.setCompanyAuthSignName(null);
//			}else {
//			applicantContactDetails.setCompanyAuthSignName(buyerPreApplicationDto.getCompanyAuthSignName());
//			}
//			if(buyerPreApplicationDto.getCompanyAuthContactNumber() == null || "".equals(buyerPreApplicationDto.getCompanyAuthContactNumber())) {
//				applicantContactDetails.setCompanyAuthContactNumber(null);
//			}else {
//				applicantContactDetails.setCompanyAuthContactNumber(buyerPreApplicationDto.getCompanyAuthContactNumber());
//			}
//			if(buyerPreApplicationDto.getCompanyAuthSignEmail() == null || "".equals(buyerPreApplicationDto.getCompanyAuthSignEmail())) {
//				applicantContactDetails.setCompanyAuthSignEmail(null);
//			}else {
//				applicantContactDetails.setCompanyAuthSignEmail(buyerPreApplicationDto.getCompanyAuthSignEmail());
//			}
//			if(buyerPreApplicationDto.getCompanyFaxNumber() == null || "".equals(buyerPreApplicationDto.getCompanyFaxNumber())) {
//				applicantContactDetails.setCompanyFaxNumber(null);
//			}else {
//				applicantContactDetails.setCompanyFaxNumber(buyerPreApplicationDto.getCompanyFaxNumber());
//			}
//			if(buyerPreApplicationDto.getCompanyTelephoneNumber() == null || "".equals(buyerPreApplicationDto.getCompanyTelephoneNumber())) {
//				applicantContactDetails.setCompanyTelephoneNumber(null);
//			}else {
//				applicantContactDetails.setCompanyTelephoneNumber(buyerPreApplicationDto.getCompanyTelephoneNumber());
//			}
//			if(buyerPreApplicationDto.getCompanyMobileNumber() == null || "".equals(buyerPreApplicationDto.getCompanyMobileNumber())) {
//				applicantContactDetails.setCompanyMobileNumber(null);
//			}else {
//				applicantContactDetails.setCompanyMobileNumber(buyerPreApplicationDto.getCompanyMobileNumber());
//			}
//			applicantContactDetails.setCompanyRepresentativeName(buyerPreApplicationDto.getCompanyRepresentativeName());
//			applicantContactDetails.setCompanyRepresentativeContactNumber(
//					buyerPreApplicationDto.getCompanyRepresentativeContactNumber());
//			applicantContactDetails
//					.setCompanyRepresentativeEmailID(buyerPreApplicationDto.getCompanyRepresentativeEmailID());
//
//			applicantContactDetailsRepository.save(applicantContactDetails);
//			LOGGER.error("Applicant Contact Details Updated...");
//			}

			// 5) Updating Applicant Office Space Details
//			ApplicantOfficeSpaceDetails applicantOfficeSpaceDetails = applicantOfficeSpaceDetailsRepository
//					.findByApplicantIDAndApplicationAplicantID(id,buyerPreApplicationDto.getApplicantApplicationId()); // here 1st parameter 'id' = ApplicantID
//			if (applicantOfficeSpaceDetails != null) {
//			applicantOfficeSpaceDetails.setApplicantID(id);
//			applicantOfficeSpaceDetails.setApplicationAplicantID(buyerPreApplicationDto.getApplicantApplicationId());
//			if(buyerPreApplicationDto.getOfficeSpace() == null  || "".equals(buyerPreApplicationDto.getOfficeSpace())) {
//				applicantOfficeSpaceDetails.setOfficeSpace(null);
//			}else {
//				applicantOfficeSpaceDetails.setOfficeSpace(buyerPreApplicationDto.getOfficeSpace());
//			}
//			if(buyerPreApplicationDto.getUsableArea() == null) {
//				applicantOfficeSpaceDetails.setUsableArea(null);
//			}else {
//				applicantOfficeSpaceDetails.setUsableArea(buyerPreApplicationDto.getUsableArea());
//			}
//			if(buyerPreApplicationDto.getOfficeAddress() == null || "".equals(buyerPreApplicationDto.getOfficeAddress())) {
//				applicantOfficeSpaceDetails.setOfficeAddress(null);
//			}else {
//				applicantOfficeSpaceDetails.setOfficeAddress(buyerPreApplicationDto.getOfficeAddress());
//			}
//			if(buyerPreApplicationDto.getInfrastructureFacilitiesInOffice() == null || "".equals(buyerPreApplicationDto.getInfrastructureFacilitiesInOffice())) {
//				applicantOfficeSpaceDetails.setInfrastructureFacilitiesInOffice(null);
//			}else {
//				applicantOfficeSpaceDetails.setInfrastructureFacilitiesInOffice(buyerPreApplicationDto.getInfrastructureFacilitiesInOffice());
//			}
//
//			applicantOfficeSpaceDetailsRepository.save(applicantOfficeSpaceDetails);
//			LOGGER.info("Applicant Office Space Details Updated...");
//			}

			// 6) Updating Applicant Other Business Details
			ApplicantOtherBusinessDetails applicantOtherBusinessDetails = applicantOtherBusinessDetailsRepository
					.findByApplicantIDAndApplicationAplicantID(id,buyerPreApplicationDto.getApplicantApplicationId()); // here 1st parameter 'id' = ApplicantID
			if (applicantOtherBusinessDetails != null) {
			applicantOtherBusinessDetails.setApplicantID(id);
			applicantOtherBusinessDetails.setApplicationAplicantID(buyerPreApplicationDto.getApplicantApplicationId());
//			if(buyerPreApplicationDto.getFirmName() == null || "".equals(buyerPreApplicationDto.getFirmName())) {
//				applicantOtherBusinessDetails.setFirmName(null);
//			}else {
//				applicantOtherBusinessDetails.setFirmName(buyerPreApplicationDto.getFirmName());
//			}
//			if(buyerPreApplicationDto.getProduct() == null || "".equals(buyerPreApplicationDto.getProduct())) {
//				applicantOtherBusinessDetails.setProduct(null);
//			}else {
//				applicantOtherBusinessDetails.setProduct(buyerPreApplicationDto.getProduct());
//			}
//			if(buyerPreApplicationDto.getOtherBusinessSamePremises() == null || "".equals(buyerPreApplicationDto.getOtherBusinessSamePremises())) {
//				applicantOtherBusinessDetails.setOtherBusinessSamePremises(null);
//			}else {
//				applicantOtherBusinessDetails.setOtherBusinessSamePremises(buyerPreApplicationDto.getOtherBusinessSamePremises());
//			}
//			if(buyerPreApplicationDto.getFirmTypeID() == null) {
//				applicantOtherBusinessDetails.setFirmTypeID(null);
//			}else {
//				applicantOtherBusinessDetails.setFirmTypeID(buyerPreApplicationDto.getFirmTypeID());
//			}

//			if(buyerPreApplicationDto.getYearOfEstablishment() == null || "".equals(buyerPreApplicationDto.getYearOfEstablishment())) {
//				applicantOtherBusinessDetails.setYearOfEstablishment(null);
//			}else {
//				applicantOtherBusinessDetails.setYearOfEstablishment(buyerPreApplicationDto.getYearOfEstablishment());
//			}
//			if(buyerPreApplicationDto.getPresentNetWorth() == null) {
//				applicantOtherBusinessDetails.setPresentNetWorth(null);
//			}else {
//				applicantOtherBusinessDetails.setPresentNetWorth(buyerPreApplicationDto.getPresentNetWorth());
//			}
//			if(buyerPreApplicationDto.getCity() == null || "".equals(buyerPreApplicationDto.getCity())) {
//				applicantOtherBusinessDetails.setCity(null);
//			}else {
//				applicantOtherBusinessDetails.setCity(buyerPreApplicationDto.getCity());
//			}
			
			applicantOtherBusinessDetails.setNatureOfBusiness(buyerPreApplicationDto.getNatureOfBusiness());
			
			if("Non-Agriculture".equals(buyerPreApplicationDto.getNatureOfBusiness())) {
				applicantOtherBusinessDetails.setNonAgriculturalBusinessName(buyerPreApplicationDto.getNonAgriculturalBusinessName());
			}else {
				applicantOtherBusinessDetails.setNonAgriculturalBusinessName(null);
			}
			
			applicantOtherBusinessDetails.setCurrencyID(buyerPreApplicationDto.getCurrencyID());
			applicantOtherBusinessDetails.setBusinessTypeID(buyerPreApplicationDto.getBusinessTypeID());
			applicantOtherBusinessDetails.setAverageRevenue(buyerPreApplicationDto.getAverageRevenue());

			applicantOtherBusinessDetailsRepository.save(applicantOtherBusinessDetails);
			LOGGER.info("Applicant Other Business Details Updated...");
			}

			return responseMessageUtil.sendResponse(true,
					"Buyer Pre-Application" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + ex.getMessage());
		}

	}
	
	public List<Integer> listOfPageNoOfPreApplication() {
		
		try {
		List<Integer> listOfPageNo = new ArrayList<>();
		
		Integer totalCount = applicantDetailsRepository.totalPreApplicationCount();
		
		for(int i = 1; i<= totalCount; i++) {
			listOfPageNo.add(i);
		}
		
		return listOfPageNo;

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Records Found.");
		}

	}
	
	public ByteArrayInputStream exportToExcelPreApplication(Integer page, Integer size) {
		
		LOGGER.info("getting  buyer pre-application to esxcel...");
		
		Pageable sortedByIdAsc = PageRequest.of(page, size, Sort.by("id").descending());
	    Page<BuyerPreApplicationExportToExcelVO> buyerPreApplicationList = applicantDetailsRepository.exportToExcelPreApplication(sortedByIdAsc);

	    ByteArrayInputStream in = esportToExcel(buyerPreApplicationList);
	    return in;
	  }
	
	public ByteArrayInputStream esportToExcel(Page<BuyerPreApplicationExportToExcelVO> buyerPreApplicationList){
		
		LOGGER.info("generating excel for buyer pre-application...");
		
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
		      for (BuyerPreApplicationExportToExcelVO buyerPreApplication : buyerPreApplicationList) {
		        Row row = sheet.createRow(rowIdx++);
		        
		        int columnCount = 0;
		        
		        row.createCell(columnCount++).setCellValue(buyerPreApplication.getId());
		        row.createCell(columnCount++).setCellValue(buyerPreApplication.getApplicationType());
		        row.createCell(columnCount++).setCellValue(buyerPreApplication.getOrigin());
		        row.createCell(columnCount++).setCellValue(buyerPreApplication.getApplicantType());
		        row.createCell(columnCount++).setCellValue(buyerPreApplication.getReference() == null? "" : buyerPreApplication.getReference());
		        row.createCell(columnCount++).setCellValue(buyerPreApplication.getCompanyName());
		        row.createCell(columnCount++).setCellValue(buyerPreApplication.getNatureOfBusiness());
		        row.createCell(columnCount++).setCellValue(buyerPreApplication.getNonAgriculturalBusinessName() == null? "" : buyerPreApplication.getNonAgriculturalBusinessName());
		        row.createCell(columnCount++).setCellValue(buyerPreApplication.getPresentBusiness());
		        row.createCell(columnCount++).setCellValue(buyerPreApplication.getDateOfIncorporation());
		        row.createCell(columnCount++).setCellValue(buyerPreApplication.getPanNumber() == null? "" : buyerPreApplication.getPanNumber());
		        row.createCell(columnCount++).setCellValue(buyerPreApplication.getCinNumber() == null? "" : buyerPreApplication.getCinNumber());
		        row.createCell(columnCount++).setCellValue(buyerPreApplication.getGstNumber()== null? "" : buyerPreApplication.getGstNumber());
		        row.createCell(columnCount++).setCellValue(buyerPreApplication.getCompanyRegistrationNumber()== null? "" : buyerPreApplication.getCompanyRegistrationNumber());
		        row.createCell(columnCount++).setCellValue(buyerPreApplication.getVat() == null? "" : buyerPreApplication.getVat());
		        row.createCell(columnCount++).setCellValue(buyerPreApplication.getCurrencyCode());
		        row.createCell(columnCount++).setCellValue(buyerPreApplication.getAverageRevenue());
		        row.createCell(columnCount++).setCellValue(buyerPreApplication.getContactPersonName());
		        row.createCell(columnCount++).setCellValue(buyerPreApplication.getDesignation());
		        row.createCell(columnCount++).setCellValue(buyerPreApplication.getOtherDesignationName() == null? "" : buyerPreApplication.getOtherDesignationName());
		        row.createCell(columnCount++).setCellValue(buyerPreApplication.getTelephoneNumber());
		        row.createCell(columnCount++).setCellValue(buyerPreApplication.getMobileNumber());
		        row.createCell(columnCount++).setCellValue(buyerPreApplication.getEmailAddress());
		        row.createCell(columnCount++).setCellValue(buyerPreApplication.getWebsite());
		        row.createCell(columnCount++).setCellValue(buyerPreApplication.getCommodity());
		        row.createCell(columnCount++).setCellValue(buyerPreApplication.getCountry());
		        row.createCell(columnCount++).setCellValue(buyerPreApplication.getState());
		        row.createCell(columnCount++).setCellValue(buyerPreApplication.getCity());
		        row.createCell(columnCount++).setCellValue(buyerPreApplication.getBuildingName());
		        row.createCell(columnCount++).setCellValue(buyerPreApplication.getStreetName());
		        row.createCell(columnCount++).setCellValue(buyerPreApplication.getPostalCode());
		        
		      }

		      workbook.write(out);
		      return new ByteArrayInputStream(out.toByteArray());
		    } catch (IOException e) {
		      throw new InvalidDataException("fail to import data to Excel file: " + e.getMessage());
		    }		
	}
	
	public Integer[] getCommodityIdsByApplicantApplicationId(Integer applicantApplicationId) {

		try {

			LOGGER.info("getting commodity by applicant application Id..." + applicantApplicationId);
			return applicantDetailsRepository.getCommodityIdsByApplicantApplicationId(applicantApplicationId);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("Commodity Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + applicantApplicationId);
		}

	}
	
	public Integer getApplicantApplicationIdByApplicantId(Integer applicantId) {

		try {

			LOGGER.info("getting applicant application Id...");
			return applicantDetailsRepository.getApplicantApplicationIdByApplicantId(applicantId);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("Applicant Application Details Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + applicantId);
		}

	}
}
