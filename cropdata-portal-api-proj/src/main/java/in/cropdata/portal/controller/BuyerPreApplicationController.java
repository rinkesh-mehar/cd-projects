package in.cropdata.portal.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import in.cropdata.portal.dto.*;
import in.cropdata.portal.model.TermsAndConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import in.cropdata.portal.exceptions.InvalidDataException;
import in.cropdata.portal.service.BuyerPreApplicationService;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/site/buyer-pre-application")
public class BuyerPreApplicationController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BuyerPreApplicationController.class);

	@Autowired
	private BuyerPreApplicationService buyerPreApplicationService;

	@GetMapping("/logout")
	public ResponseMessage logoutRequest(@RequestHeader String auth){
		return this.buyerPreApplicationService.logoutRequest(auth.substring(7));
	}

	@PostMapping("/add")
	public ResponseEntity<ResponseMessage> addPreBuyerApplication(@RequestBody BuyerPreApplicationDto buyerPreApplicationDto) {
		
		LOGGER.info("Inside addPreBuyerApplication method of Controller...");

		if (buyerPreApplicationDto == null) {
			throw new InvalidDataException("Buyer Pre-Application Data can not be null!");
		}
		
		if (buyerPreApplicationDto.getApplicationTypeID() == null) {
			throw new InvalidDataException("Application Type can not be null!");
		}
		
		if (buyerPreApplicationDto.getNationality() == null || "".equals(buyerPreApplicationDto.getNationality())) {
			throw new InvalidDataException("Origin can not be null!");
		}
		
		if (buyerPreApplicationDto.getApplicantTypeID() == null) {
			throw new InvalidDataException("Applicant Type can not be null!");
		}
		
		
		if (buyerPreApplicationDto.getCompanyName() == null  || "".equals(buyerPreApplicationDto.getCompanyName())) {
			throw new InvalidDataException("Company Name can not be null!");
		}
		
		if (buyerPreApplicationDto.getNatureOfBusiness() == null  || "".equals(buyerPreApplicationDto.getNatureOfBusiness())) {
			throw new InvalidDataException("Nature Of Business can not be null!");
		}
		
		if ("Non-Agriculture".equals(buyerPreApplicationDto.getNatureOfBusiness()) && (buyerPreApplicationDto.getNonAgriculturalBusinessName() == null || "".equals(buyerPreApplicationDto.getNonAgriculturalBusinessName()))) {
			throw new InvalidDataException("Non-Agriculture Business Name can not be null if Nature of Business is selected as Non-Agriculture!");
		}
		
		if (buyerPreApplicationDto.getBusinessTypeID() == null) {
			throw new InvalidDataException("Business Type can not be null!");
		}
		
		if (buyerPreApplicationDto.getDateOfIncorporation() == null  || "".equals(buyerPreApplicationDto.getDateOfIncorporation())) {
			throw new InvalidDataException("Date of Incorporation can not be null!");
		}
		
		if ("Indian".equals(buyerPreApplicationDto.getNationality()) && (buyerPreApplicationDto.getPanNumber() == null || "".equals(buyerPreApplicationDto.getPanNumber()))) {
			throw new InvalidDataException("Pan Number can not be null!");
		}
		
//		if (buyerPreApplicationDto.getPanNumber().length() != 10) {
//			throw new InvalidDataException("Length of PAN number Should be 10");
//		}
		
		if ("Indian".equals(buyerPreApplicationDto.getNationality()) && buyerPreApplicationDto.getApplicantTypeID() != 6 && (buyerPreApplicationDto.getCinNumber() == null || "".equals(buyerPreApplicationDto.getCinNumber()))) {
			throw new InvalidDataException("CIN Number can not be null!");
		}
		
//		if (buyerPreApplicationDto.getApplicantTypeID() !=6 && buyerPreApplicationDto.getCinNumber().length() != 21) {
//			throw new InvalidDataException("Length of CIN number Should be 21");
//		}
		
		if ("Indian".equals(buyerPreApplicationDto.getNationality()) && (buyerPreApplicationDto.getGstNumber() == null || "".equals(buyerPreApplicationDto.getGstNumber()))) {
			throw new InvalidDataException("GST Number can not be null!");
		}
		
//		if (buyerPreApplicationDto.getGstNumber().length() != 15) {
//			throw new InvalidDataException("Length of GST number Should be 15");
//		}
		
		if ("International".equals(buyerPreApplicationDto.getNationality()) && (buyerPreApplicationDto.getCompanyRegistrationNumber() == null || "".equals(buyerPreApplicationDto.getCompanyRegistrationNumber()))) {
			throw new InvalidDataException("Company Registration Number can not be null!");
		}
		
		if ("International".equals(buyerPreApplicationDto.getNationality()) && (buyerPreApplicationDto.getVat() == null || "".equals(buyerPreApplicationDto.getVat()))) {
			throw new InvalidDataException("VAT Number can not be null!");
		}
		
		if (buyerPreApplicationDto.getCurrencyID() == null) {
			throw new InvalidDataException("Currency can not be null!");
		}
		
		if (buyerPreApplicationDto.getAverageRevenue() == null  || "".equals(buyerPreApplicationDto.getAverageRevenue())) {
			throw new InvalidDataException("Average Revenue can not be null!");
		}
		
		if (buyerPreApplicationDto.getCommoditiesId() == null) {
			throw new InvalidDataException("Commodities of Interest can not be null!");
		}
		

		if (buyerPreApplicationDto.getName() == null || "".equals(buyerPreApplicationDto.getName())) {
			throw new InvalidDataException("Contact Person Name can not be null!");
		}
		
		if (buyerPreApplicationDto.getDesignationID() == null) {
			throw new InvalidDataException("Designation can not be null!");
		}
		
		if (buyerPreApplicationDto.getDesignationID() == 6 && (buyerPreApplicationDto.getOtherDesignationName() == null || "".equals(buyerPreApplicationDto.getOtherDesignationName()))) {
			throw new InvalidDataException("Other Designation can not be null if Designation is selected as Other!");
		}
		
		if (buyerPreApplicationDto.getTelephoneNumber() == null || "".equals(buyerPreApplicationDto.getTelephoneNumber())) {
			throw new InvalidDataException("Telephone Number can not be null!");
		}
		
		if (buyerPreApplicationDto.getMobileNumber() == null || "".equals(buyerPreApplicationDto.getMobileNumber())) {
			throw new InvalidDataException("Mobile Number can not be null!");
		}
		
		if (buyerPreApplicationDto.getEmailAddress() == null || "".equals(buyerPreApplicationDto.getEmailAddress())) {
			throw new InvalidDataException("Email Address can not be null!");
		}
		
		if (buyerPreApplicationDto.getBuildingName() == null || "".equals(buyerPreApplicationDto.getBuildingName())) {
			throw new InvalidDataException("Building Name can not be null!");
		}
		
		if (buyerPreApplicationDto.getStreetName() == null || "".equals(buyerPreApplicationDto.getStreetName())) {
			throw new InvalidDataException("Street Name can not be null!");
		}
		
		if (buyerPreApplicationDto.getPostalCode() == null || "".equals(buyerPreApplicationDto.getPostalCode())) {
			throw new InvalidDataException("Postal Code can not be null!");
		}
		
		if (buyerPreApplicationDto.getCountry() == null || "".equals(buyerPreApplicationDto.getCountry())) {
			throw new InvalidDataException("Country can not be null!");
		}
		
		if (buyerPreApplicationDto.getState() == null || "".equals(buyerPreApplicationDto.getState())) {
			throw new InvalidDataException("State can not be null!");
		}
		
		if (buyerPreApplicationDto.getCity() == null || "".equals(buyerPreApplicationDto.getCity())) {
			throw new InvalidDataException("City can not be null!");
		}
		
//		if (buyerPreApplicationDto.getLocationOfInterest() == null) {
//			throw new InvalidDataException("City/Location Of Interest can not be null!");
//		}
//		
//		if (buyerPreApplicationDto.getRegisteredAddress() == null) {
//			throw new InvalidDataException("Registered Address can not be null!");
//		}
//		
//		if (buyerPreApplicationDto.getBusinessAddress() == null) {
//			throw new InvalidDataException("Business Address can not be null!");
//		}
//		
//		if (buyerPreApplicationDto.getCompanyRepresentativeName() == null) {
//			throw new InvalidDataException("Company Representative Name can not be null!");
//		}
//		
//		if (buyerPreApplicationDto.getCompanyRepresentativeContactNumber() == null) {
//			throw new InvalidDataException("Company Representative Contact Number can not be null!");
//		}
//		
//		if (buyerPreApplicationDto.getCompanyRepresentativeEmailID() == null) {
//			throw new InvalidDataException("Company Representative Email ID can not be null!");
//		}
//		
//		if (buyerPreApplicationDto.getOfficeSpace() == null) {
//			throw new InvalidDataException("Own/rent Office Space can not be null!");
//		}
//		
//		if ("Yes".equals(buyerPreApplicationDto.getOfficeSpace()) && buyerPreApplicationDto.getUsableArea() == null) {
//			throw new InvalidDataException("Usable Area can not be null if office space own/rent is selected!");
//		}
//		
//		if ("Yes".equals(buyerPreApplicationDto.getOfficeSpace()) && buyerPreApplicationDto.getOfficeAddress() == null) {
//			throw new InvalidDataException("Office Address can not be null if office space own/rent is selected!");
//		}
//		
//		if ("Yes".equals(buyerPreApplicationDto.getOtherBusinessInSamePremises()) && buyerPreApplicationDto.getBussinessTypeID() == null) {
//			throw new InvalidDataException("Bussiness Type ID can not be null if other business in same premises is selected!");
//		}
//		
//		if ("Yes".equals(buyerPreApplicationDto.getOtherBusinessInSamePremises()) && buyerPreApplicationDto.getFirmName() == null) {
//			throw new InvalidDataException("Firm Name can not be null if other business in same premises is selected!");
//		}
//		
//		if ("Yes".equals(buyerPreApplicationDto.getOtherBusinessInSamePremises()) && buyerPreApplicationDto.getProduct() == null) {
//			throw new InvalidDataException("Product can not be null if other business in same premises is selected!");
//		}
//		
//		if ("Yes".equals(buyerPreApplicationDto.getOtherBusinessInSamePremises()) && buyerPreApplicationDto.getFirmTypeID() == null) {
//			throw new InvalidDataException("Firm Type ID can not be null if other business in same premises is selected!");
//		}
//		
//		if ("Yes".equals(buyerPreApplicationDto.getOtherBusinessInSamePremises()) && buyerPreApplicationDto.getNatureOfBussiness() == null) {
//			throw new InvalidDataException("Nature Of Bussiness can not be null if other business in same premises is selected!");
//		}
//		
//		if ("Yes".equals(buyerPreApplicationDto.getOtherBusinessInSamePremises()) && buyerPreApplicationDto.getYearOfEstablishment() == null) {
//			throw new InvalidDataException("Year Of Establishment can not be null if other business in same premises is selected!");
//		}
//		
//		if ("Yes".equals(buyerPreApplicationDto.getOtherBusinessInSamePremises()) && buyerPreApplicationDto.getPresentNetWorth() == null) {
//			throw new InvalidDataException("Present Net Worth can not be null if other business in same premises is selected!");
//		}
//		
//		if ("Yes".equals(buyerPreApplicationDto.getOtherBusinessInSamePremises()) && buyerPreApplicationDto.getCurrencyID() == null) {
//			throw new InvalidDataException("Currency Id can not be null if other business in same premises is selected!");
//		}
//		
//		if ("Yes".equals(buyerPreApplicationDto.getOtherBusinessInSamePremises()) && buyerPreApplicationDto.getCity() == null) {
//			throw new InvalidDataException("City can not be null if other business in same premises is selected!");
//		}

		return new ResponseEntity<>(buyerPreApplicationService.addPreBuyerApplication(buyerPreApplicationDto),
				HttpStatus.CREATED);

	}


//	@GetMapping("/applicant/data")
//	public Map<String, Object> getApplicantDetails(@RequestHeader String auth){
//		return this.buyerPreApplicationService.getApplicantDataByToken(auth.substring(7));
//	}
	@GetMapping("/applicant/data")
	public Map<String, Object> getApplicantSteps(@RequestHeader String auth){
		return this.buyerPreApplicationService.getApplicantSteps(auth.substring(7));
	}

	@PostMapping("/step/one")
	public ResponseMessage step1(@RequestBody StepOne stepOne, HttpServletRequest request){
		String auth = request.getHeader("auth").substring(7);
		return buyerPreApplicationService.stepOne(stepOne,auth);
	}
//	@PostMapping("/step/two")
	@RequestMapping(value ="/step/two",method =RequestMethod.POST)
	public ResponseMessage step2(@ModelAttribute StepTwo stepTwo,HttpServletRequest request) throws JsonProcessingException, ParseException {
		String auth = request.getHeader("auth").substring(7);
		LOGGER.info("data {}", stepTwo);

		return buyerPreApplicationService.stepTwo(stepTwo,auth);
//		return null;
	}

	@RequestMapping(value ="/step/test",method =RequestMethod.POST)
	public ResponseEntity<?> step2Test(@ModelAttribute StepTwo stepTwo,HttpServletRequest request) throws JsonProcessingException, ParseException {
		String auth = request.getHeader("auth").substring(7);
//		LOGGER.info("data {}", stepTwo);
		List<String> nameList = new ArrayList<>();

//		for(String name: stepTwo.getDirectorsProprietorPartnerFirstName()){
//			nameList.add(name);
//		}
		LOGGER.info("nameList {}", nameList);
		return new ResponseEntity<>(stepTwo,HttpStatus.OK);
//		return null;
	}
	@PostMapping("/step/three")
	public ResponseMessage step3(@RequestBody StepThree stepThree, HttpServletRequest request){
		String auth = request.getHeader("auth").substring(7);
		return buyerPreApplicationService.stepThree(stepThree,auth);
	}

	@GetMapping("/tAndcUrl")
	public List<TermsAndConditions> getTAndCUrl(@RequestParam("platformId") Integer platformId) {

		return buyerPreApplicationService.getTAndCUrl(platformId);
	}

	
}
