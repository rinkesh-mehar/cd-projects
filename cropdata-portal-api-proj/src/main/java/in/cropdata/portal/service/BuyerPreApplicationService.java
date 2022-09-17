package in.cropdata.portal.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


import in.cropdata.portal.dto.*;
import in.cropdata.portal.exceptions.DoesNotExistException;
import in.cropdata.portal.exceptions.UnauthorizeException;
import in.cropdata.portal.model.*;
import in.cropdata.portal.repository.*;
import in.cropdata.portal.util.CryptoUtils;
import in.cropdata.portal.vo.BuyerPreApplicationMastersVO;
import in.cropdata.portal.worldData.model.Cities;
import in.cropdata.portal.worldData.model.Countries;
import in.cropdata.portal.worldData.model.States;
import in.cropdata.portal.worldData.repository.CitiesRepository;
import in.cropdata.portal.worldData.repository.CountriesRepository;
import in.cropdata.portal.worldData.repository.StatesRepository;
import in.cropdata.portal.worldData.service.GeoService;
import in.cropdata.toolsuite.sdk.FileManagerUtil;
import in.cropdata.toolsuite.sdk.dto.FileUploadResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.cropdata.portal.constants.APIConstants;
import in.cropdata.portal.exceptions.InvalidDataException;
import in.cropdata.portal.properties.AppProperties;
import in.cropdata.portal.util.EmailServiceUtil;
import in.cropdata.portal.util.ResponseMessageUtil;
import org.springframework.web.multipart.MultipartFile;


@Service
public class BuyerPreApplicationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BuyerPreApplicationService.class);

    private static final String SERVER_ERROR_MSG = "Server Error : ";

    @Autowired
    private AppProperties properties;

    @Autowired
    private FileManagerUtil fileManagerUtil;

    @Autowired
    private ResponseMessageUtil responseMessageUtil;

    @Autowired
    private EmailServiceUtil emailServiceUtil;

    @Autowired
    private ApplicantDetailsRepository applicantDetailsRepository;

    @Autowired
    private ApplicantApplicationDetailsRepository applicantApplicationDetailsRepository;

    @Autowired
    private ApplicantCommoditiesOfInterestRepository applicantCommoditiesOfInterestRepository;

    @Autowired
    private ApplicantBankDetailsRepository applicantBankDetailsRepository;

//	@Autowired
//	private ApplicantContactDetailsRepository applicantContactDetailsRepository;

//	@Autowired
//	private ApplicantOfficeSpaceDetailsRepository applicantOfficeSpaceDetailsRepository;

    @Autowired
    private ApplicantOtherBusinessDetailsRepository applicantOtherBusinessDetailsRepository;

    @Autowired
    private ApplicantContactDetailsRepository applicantContactDetailsRepository;

    @Autowired
    private ApplicationSignatoryDetailsRepository applicationSignatoryDetailsRepository;

    @Autowired
    private TermsAndConditionsRepository termsAndConditionsRepository;

    @Autowired
    private CountriesRepository countriesRepository;

    @Autowired
    private EmailTemplateRepository emailTemplateRepository;


    @Autowired
    private AgriCommodityRepository agriCommodityRepository;

    @Autowired
    private BuyerPreApplicationMastersRepository buyerPreApplicationMastersRepository;

    @Autowired
    private CitiesRepository citiesRepository;

    @Autowired
    private StatesRepository statesRepository;


    public ResponseMessage logoutRequest(String auth) {
        Optional<ApplicantDetails> byMobileNumber = this.applicantDetailsRepository.findByAuthToken(auth);
        if (byMobileNumber.isPresent()) {
            ApplicantDetails applicantDetails = byMobileNumber.get();
            applicantDetails.setId(applicantDetails.getId());
            applicantDetails.setAuthToken(null);
            this.applicantDetailsRepository.save(applicantDetails);
            return responseMessageUtil.sendResponse(true, "Logout Successfully", null);
        } else {
            return responseMessageUtil.sendResponse(false, "Something went wrong", null);
        }
    }

    public Map<String, Object> getApplicantSteps(String token){
        Map<String, Object> stepOne = new HashMap<String, Object>();
        Map<String, Object> stepTwo = new HashMap<String, Object>();
        Map<String, Object> stepThree = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<String, Object>();
        Map<String, Object> res = new HashMap<String, Object>();
        Map<String, Object> files = new HashMap<String, Object>();
        Optional<ApplicantDetails> byAuthToken = this.applicantDetailsRepository.findByAuthToken(token);
        ApplicantDetailsInf applicantDetailsInf = this.applicantDetailsRepository.getApplicantData(token);
        if(byAuthToken.isPresent()){
            CitiesDTO cityByName = null;


            // StepOne Data
            ApplicantDetails applicantDetails = byAuthToken.get();


            Optional<ApplicantOtherBusinessDetails> foundOtherBusinessByApplicantId = this.applicantOtherBusinessDetailsRepository.findByApplicantID(applicantDetails.getId());
            if(foundOtherBusinessByApplicantId.isPresent()){
                ApplicantOtherBusinessDetails applicantOtherBusinessDetails = foundOtherBusinessByApplicantId.get();
                stepOne.put("natureOfBusiness",applicantOtherBusinessDetails.getNatureOfBusiness()==null?"":applicantOtherBusinessDetails.getNatureOfBusiness());
                stepOne.put("nonAgricultureBusinessName",applicantOtherBusinessDetails.getNonAgriculturalBusinessName()==null?"":applicantOtherBusinessDetails.getNonAgriculturalBusinessName());
            }
            if(applicantDetails.getRegisteredAddressID()!=null){
                Optional<ApplicantContactDetails> applicantContactDetailsById = this.applicantContactDetailsRepository.findById(Integer.valueOf(applicantDetails.getRegisteredAddressID()));
                if(applicantContactDetailsById.isPresent()){
                    ApplicantContactDetails applicantContactDetails = applicantContactDetailsById.get();
                    stepOne.put("registeredBuildingName",applicantContactDetails.getBuildingName()==null?"":applicantContactDetails.getBuildingName());
                    stepOne.put("registeredStreetName",applicantContactDetails.getStreetName()==null?"":applicantContactDetails.getStreetName());
                    stepOne.put("registeredPostalCode",applicantContactDetails.getPostalCode()==null?"":applicantContactDetails.getPostalCode());
                    stepOne.put("registeredCountryCode",applicantContactDetails.getCountryCode()==null?"":applicantContactDetails.getCountryCode());
                    stepOne.put("registeredState",applicantContactDetails.getState()==null?"":applicantContactDetails.getState());
                    stepOne.put("registeredCity",applicantContactDetails.getCity()==null?"":applicantContactDetails.getCity());
//                    CitiesDTO cityByName = null;
                    if(applicantContactDetails.getCity().contains(",")){
                        String[] city = applicantContactDetails.getCity().split(",");
                        cityByName = this.citiesRepository.getByNameAndCountry(city[0]);
                        stepOne.put("registeredCityId",cityByName.getID()==null?"":cityByName.getID());
                    }else{
                        stepOne.put("registeredCityId","");
                    }
                    States byStateName = null;
//                    stepOne.put("registeredCityId",cityByName.getID()==null?"":cityByName.getID());
                    Countries countryCode1 = countriesRepository.getCountryCode(applicantContactDetails.getCountryCode());
                    if(applicantContactDetails.getState().contains(",")){
                        String[] split = applicantContactDetails.getState().split(",");
                        byStateName = this.statesRepository.statename(split[0]);
                        stepOne.put("registeredStateCode",byStateName.getCode()==null?"":byStateName.getCode());
                    }else{
                        stepOne.put("registeredStateCode","");
                    }


                    stepOne.put("registeredCountry",countryCode1.getName()==null?"":countryCode1.getName());

                }
            }

            ApplicantApplicationDetails applicantApplicationDetails = null;
            Optional<ApplicantApplicationDetails> applicantApplicationDetailsByID = applicantApplicationDetailsRepository.findByApplicantID(applicantDetails.getId());
            if(applicantApplicationDetailsByID.isPresent()){
                applicantApplicationDetails = applicantApplicationDetailsByID.get();
                stepThree.put("agreementAccepted",applicantApplicationDetails.getAgreementAccepted()==null?"":applicantApplicationDetails.getAgreementAccepted());
                stepOne.put("reference",applicantApplicationDetails.getParentReference()==null?"":applicantApplicationDetails.getParentReference());
                stepOne.put("businessTypeID",applicantApplicationDetails.getBussinessTypeID()==null?0:applicantApplicationDetails.getBussinessTypeID());
                stepOne.put("currentOrInterestedProducts",applicantApplicationDetails.getCurrentOrInterestedProducts()==null?"":applicantApplicationDetails.getCurrentOrInterestedProducts());
                stepOne.put("applicationType",applicantApplicationDetails.getApplicationTypeID()==null?"":applicantApplicationDetails.getApplicationTypeID());
                stepOne.put("reference",applicantApplicationDetails.getParentReference()==null?"":applicantApplicationDetails.getParentReference());
                List<Integer> applicantApplicationIDAndCommodityID = this.applicantCommoditiesOfInterestRepository.getApplicantApplicationIDAndCommodityID(applicantApplicationDetails.getId());
//                List<Map<String,Object>> commodityNameList = this.agriCommodityRepository.getCommodityNameList(applicantApplicationIDAndCommodityID);
                stepOne.put("commodityInterest",applicantApplicationIDAndCommodityID);
                stepOne.put("companyName",applicantApplicationDetails.getCompanyName());

                // StepTwo
                stepTwo.put("cinNo",applicantApplicationDetails.getCinNumber()==null ? "" : applicantApplicationDetails.getCinNumber());
                stepTwo.put("udyogAadharNo",applicantApplicationDetails.getUdyogAdhaarNumber()==null?"":applicantApplicationDetails.getUdyogAdhaarNumber());
                stepTwo.put("gstNo",applicantApplicationDetails.getGstNumber()==null?"":applicantApplicationDetails.getGstNumber());
                stepTwo.put("tanNo",applicantApplicationDetails.getTanNo()==null?"":applicantApplicationDetails.getTanNo());
                stepTwo.put("panNo",applicantApplicationDetails.getPanNumber()==null?"":applicantApplicationDetails.getPanNumber());
                stepTwo.put("incomeTaxNo",applicantApplicationDetails.getIncomeTaxNumber()==null?"":applicantApplicationDetails.getIncomeTaxNumber());
                stepTwo.put("vatNo",applicantApplicationDetails.getVat()==null?"":applicantApplicationDetails.getVat());
                stepTwo.put("dateOfIncorporationOrLicense",applicantApplicationDetails.getDateOfIncorporation()==null?"":applicantApplicationDetails.getDateOfIncorporation());
                stepTwo.put("dateOfIncorporationOrRegistration",applicantApplicationDetails.getDateOfIncorporationRegistration()==null?"":applicantApplicationDetails.getDateOfIncorporationRegistration());
                stepTwo.put("licenseExpiryDate",applicantApplicationDetails.getLiscenceExpiryDate()==null?"":applicantApplicationDetails.getLiscenceExpiryDate());
                stepTwo.put("cinNoExpiryDate",applicantApplicationDetails.getCinLiscenceExpiryDate()==null?"":applicantApplicationDetails.getCinLiscenceExpiryDate());
                stepTwo.put("companyRegistrationNo",applicantApplicationDetails.getCompanyRegistrationNumber()==null?"":applicantApplicationDetails.getCompanyRegistrationNumber());
                stepTwo.put("marketLicenseNo",applicantApplicationDetails.getMarketLicenseNumber()==null?"":applicantApplicationDetails.getMarketLicenseNumber());

                if(applicantApplicationDetails.getPanUrl()!=null){
                    List<String> panFile = Arrays.asList(applicantApplicationDetails.getPanUrl().split("\\s*,\\s*"));
                    files.put("panFileList",panFile);
                }
                if(applicantApplicationDetails.getTanUrl()!=null){
                    List<String> tanFile = Arrays.asList(applicantApplicationDetails.getTanUrl().split("\\s*,\\s*"));
                    files.put("tanFileList",tanFile);
                }if(applicantApplicationDetails.getCinUrl()!=null){
                    List<String> cinFile = Arrays.asList(applicantApplicationDetails.getCinUrl().split("\\s*,\\s*"));
                    files.put("cinFileList",cinFile);
                }if(applicantApplicationDetails.getUdyouAadharUrl()!=null){
                    List<String> udyogAadharFile = Arrays.asList(applicantApplicationDetails.getUdyouAadharUrl().split("\\s*,\\s*"));
                    files.put("udyogAadharFileList",udyogAadharFile);
                }if(applicantApplicationDetails.getGstUrl()!=null){
                    List<String> gstCertificateFile = Arrays.asList(applicantApplicationDetails.getGstUrl().split("\\s*,\\s*"));
                    files.put("gstCertificateFileList",gstCertificateFile);
                }if(applicantApplicationDetails.getVatUrl()!=null){
                    List<String> vatDocument = Arrays.asList(applicantApplicationDetails.getVatUrl().split("\\s*,\\s*"));
                    files.put("vatDocumentList",vatDocument);
                }if(applicantApplicationDetails.getIncomeTaxUrl()!=null){
                    List<String> incomeTaxDocument = Arrays.asList(applicantApplicationDetails.getIncomeTaxUrl().split("\\s*,\\s*"));
                    files.put("incomeTaxDocumentList",incomeTaxDocument);
                }if(applicantApplicationDetails.getCompanyRegistrationDocumentUrl()!=null){
                    List<String> companyRegistrationDocument = Arrays.asList(applicantApplicationDetails.getCompanyRegistrationDocumentUrl().split("\\s*,\\s*"));
                    files.put("companyRegistrationDocumentList",companyRegistrationDocument);
                }if(applicantApplicationDetails.getMarketLicenseDocumentUrl()!=null){
                    List<String> marketLicenseFile = Arrays.asList(applicantApplicationDetails.getMarketLicenseDocumentUrl().split("\\s*,\\s*"));
                    files.put("marketLicenseFileList",marketLicenseFile);
                }
                stepTwo.put("files",files);

            }
            if(applicantApplicationDetails.getBusinessAddressID()!=null){
                Optional<ApplicantContactDetails> applicantContactBussinessDetailsById = this.applicantContactDetailsRepository.findById(Integer.valueOf(applicantApplicationDetails.getBusinessAddressID()));
                if(applicantContactBussinessDetailsById.isPresent()){
                    ApplicantContactDetails applicantContactBusinessDetails = applicantContactBussinessDetailsById.get();
                    stepOne.put("bussinessBuildingName",applicantContactBusinessDetails.getBuildingName()==null?"":applicantContactBusinessDetails.getBuildingName());
                    stepOne.put("bussinessStreetName",applicantContactBusinessDetails.getStreetName()==null?"":applicantContactBusinessDetails.getStreetName());
                    stepOne.put("businessPostalCode",applicantContactBusinessDetails.getPostalCode()==null?"":applicantContactBusinessDetails.getPostalCode());
                    stepOne.put("businessCountryCode",applicantContactBusinessDetails.getCountryCode()==null?"":applicantContactBusinessDetails.getCountryCode());
                    stepOne.put("businessState",applicantContactBusinessDetails.getState()==null?"":applicantContactBusinessDetails.getState());
                    stepOne.put("businessCity",applicantContactBusinessDetails.getCity()==null?"":applicantContactBusinessDetails.getCity());
                    Countries countryCode2 = countriesRepository.getCountryCode(applicantContactBusinessDetails.getCountryCode());

                    if(applicantContactBusinessDetails.getCity().contains(",")){
                        String[] cindex = applicantContactBusinessDetails.getCity().split(",");
                       cityByName = this.citiesRepository.getByNameAndCountry(cindex[0]);
                        stepOne.put("businessCityId",cityByName.getID()==null?"":cityByName.getID());
                    }else{
//                        cityByName = this.citiesRepository.getByNameAndCountry(applicantContactBusinessDetails.getCity());
                        stepOne.put("businessCityId","");
                    }


//                    CitiesDTO cityByName = this.citiesRepository.getByNameAndCountry(cindex[0]);

//                    stepOne.put("registeredCountry",countryCode2.getName()==null?"":countryCode2.getName());
                    stepOne.put("businessCountry",countryCode2.getName()==null?"":countryCode2.getName());
                    States BusbyStateName = null;
                    if(applicantContactBusinessDetails.getState().contains(",")){
                        String[] split1 = applicantContactBusinessDetails.getState().split(",");
                        BusbyStateName= this.statesRepository.statename(split1[0]);
                        stepOne.put("businessStateCode",BusbyStateName.getCode()==null?"":BusbyStateName.getCode());
                    }else{
//                        BusbyStateName= this.statesRepository.findByName(applicantContactBusinessDetails.getState());
                        stepOne.put("businessStateCode","");
                    }



                }
            }
            Optional<ApplicantBankDetails> foundBankByApplicantID = this.applicantBankDetailsRepository.findByApplicantID(applicantDetails.getId());

            if(foundBankByApplicantID.isPresent()) {
                ApplicantBankDetails applicantBankDetails = foundBankByApplicantID.get();
                stepThree.put("name",applicantBankDetails.getName()==null?"":applicantBankDetails.getName());
                stepThree.put("branch",applicantBankDetails.getBranch()==null?"":applicantBankDetails.getBranch());
                stepThree.put("accountType",applicantBankDetails.getAccountType()==null?"":applicantBankDetails.getAccountType());
                stepThree.put("accountNumber",applicantBankDetails.getAccountNumber()==null?"":applicantBankDetails.getAccountNumber());
                stepThree.put("ifscCode",applicantBankDetails.getIfscCode()==null?"":applicantBankDetails.getIfscCode());

            }
            stepOne.put("emailAddress",applicantDetails.getEmailAddress()==null?"":applicantDetails.getEmailAddress());
            stepOne.put("mobileNumber",applicantDetails.getMobileNumber()==null?"":applicantDetails.getMobileNumber());
            stepOne.put("website",applicantDetails.getWebsite()==null?"":applicantDetails.getWebsite());
            stepOne.put("applicantType",applicantDetails.getApplicantTypeID()==null?"":applicantDetails.getApplicantTypeID());


            List<DirectorSignatoryDetailsINF> directorsProprietorDetails = this.applicationSignatoryDetailsRepository.getByApplicationID(applicantDetails.getId()).stream()
                            .filter(d -> d.getDirectorsProprietorPartnerDesignation()!=7).collect(Collectors.toList());
            List<DirectorSignatoryDetailsINF> adminDetails = this.applicationSignatoryDetailsRepository.getByApplicationID(applicantDetails.getId()).stream()
                            .filter(d -> d.getDirectorsProprietorPartnerDesignation()==7).collect(Collectors.toList());
            for (DirectorSignatoryDetailsINF admin: adminDetails){
                stepTwo.put("adminFirstName",admin.getDirectorsProprietorPartnerFirstName()==null?"":admin.getDirectorsProprietorPartnerFirstName());
                stepTwo.put("adminMiddleName",admin.getDirectorsProprietorPartnerMiddleName()==null?"":admin.getDirectorsProprietorPartnerMiddleName());
                stepTwo.put("adminLastName",admin.getDirectorsProprietorPartnerLastName()==null?"":admin.getDirectorsProprietorPartnerLastName());
                stepTwo.put("adminEmail",admin.getDirectorsProprietorPartnerEmail()==null?"":admin.getDirectorsProprietorPartnerEmail());
                stepTwo.put("adminMobileNo",admin.getDirectorsProprietorPartnerMobileNo()==null?"":admin.getDirectorsProprietorPartnerMobileNo());
                stepTwo.put("adminMobileNoPrefix",admin.getDirectorsProprietorPartnerMobileNoPrefix()==null?"":admin.getDirectorsProprietorPartnerMobileNoPrefix());
                stepTwo.put("adminTelephoneNoPrefix",admin.getDirectorsProprietorPartnerTelephoneNoPrefix()==null?"":admin.getDirectorsProprietorPartnerTelephoneNoPrefix());
                stepTwo.put("adminTelephoneNo",admin.getDirectorsProprietorPartnerTelephoneNo()==null?"":admin.getDirectorsProprietorPartnerTelephoneNo());
            }

            stepTwo.put("director",directorsProprietorDetails);

            stepTwo.put("isdCode",applicantDetails.getIsdCode()==null?"":applicantDetails.getIsdCode());
            Countries countryCode = null;
            if (applicantDetails.getCountryCode() != null) {
                countryCode = countriesRepository.getCountryCode(applicantDetails.getCountryCode());
            } else {
                throw new DoesNotExistException("CountryCode not found");
            }

            data.put("companyName", applicantDetailsInf.getCompanyName());
            data.put("email", applicantDetailsInf.getEmail());
            data.put("mobileNo", applicantDetailsInf.getMobileNo());
            data.put("userIsdCode", applicantDetailsInf.getIsdCode() != null ? applicantDetailsInf.getIsdCode() : null);
            data.put("countryCode", applicantDetailsInf.getCountryCode());
            data.put("countryName", countryCode.getName() != null ? countryCode.getName() : null);
            data.put("isdCode", countryCode.getCountryCode() != null ? countryCode.getCountryCode() : null);
            data.put("parentReferralCode", applicantDetailsInf.getParentReference() != null ? applicantDetailsInf.getParentReference() : null);
            data.put("step", applicantDetailsInf.getStep());
            res.put("step1",stepOne);
            res.put("step2",stepTwo);
            res.put("step3",stepThree);
            res.put("data",data);

            return res;
        }else{
            return null;
        }
    }

    public Map<String, Object> getApplicantDataByToken(String token) {
        Optional<ApplicantDetails> byAuthToken = applicantDetailsRepository.findByAuthToken(token);
        Map<String, Object> objectMap = null;
        if (byAuthToken.isPresent()) {
            ApplicantDetailsInf applicantDetailsInf = this.applicantDetailsRepository.getApplicantData(token);
            Countries countryCode = null;
            if (applicantDetailsInf.getCountryCode() != null) {

                countryCode = countriesRepository.getCountryCode(applicantDetailsInf.getCountryCode());
            } else {
                throw new DoesNotExistException("CountryCode not found");
            }
            objectMap = new HashMap<>();
            objectMap.put("companyName", applicantDetailsInf.getCompanyName());
            objectMap.put("email", applicantDetailsInf.getEmail());
            objectMap.put("mobileNo", applicantDetailsInf.getMobileNo());
            objectMap.put("userIsdCode", applicantDetailsInf.getIsdCode() != null ? applicantDetailsInf.getIsdCode() : null);
            objectMap.put("countryCode", applicantDetailsInf.getCountryCode());
            objectMap.put("countryName", countryCode.getName() != null ? countryCode.getName() : null);
            objectMap.put("isdCode", countryCode.getCountryCode() != null ? countryCode.getCountryCode() : null);
            objectMap.put("parentReferralCode", applicantDetailsInf.getParentReference() != null ? applicantDetailsInf.getParentReference() : null);
            objectMap.put("step", applicantDetailsInf.getStep());

            return objectMap;
        } else {
            throw new UnauthorizeException("Applicant Unthorise");
        }
    }

    @Transactional
    public ResponseMessage stepOne(StepOne stepOne, String jwtToken) {
//		Integer byAuthToken = applicantDetailsRepository.findByAuthToken(jwtToken);
        Optional<ApplicantDetails> byAuthToken = applicantDetailsRepository.findByAuthToken(jwtToken);
        if (byAuthToken.isPresent()) {
            try {
                Map<String, Object> objectMap = null;
                ApplicantDetails details = byAuthToken.get();
                ApplicantApplicationDetails applicantApplicationDetails = new ApplicantApplicationDetails();
//                ApplicantContactDetails saveOrUpdate = null;

                if(details.getRegisteredAddressID()!=null && !details.getRegisteredAddressID().isEmpty() && !details.getRegisteredAddressID().isBlank()){
                    Optional<ApplicantContactDetails> foundRegisterContactDetails = this.applicantContactDetailsRepository.findById(Integer.valueOf(details.getRegisteredAddressID()));
                    ApplicantContactDetails updateContactDetails = foundRegisterContactDetails.get();
                    updateContactDetails.setId(Integer.valueOf(details.getRegisteredAddressID()));
                    updateContactDetails.setBuildingName(stepOne.getRegisteredBuildingName());
                    updateContactDetails.setPostalCode(stepOne.getRegisteredPostalCode());
                    updateContactDetails.setStreetName(stepOne.getRegisteredStreetName());
                    updateContactDetails.setState(stepOne.getRegisteredState());
                    updateContactDetails.setCountry(stepOne.getCountry());
                    updateContactDetails.setCity(stepOne.getRegisteredCity());
                    updateContactDetails.setCountryCode(stepOne.getRegisteredCountryCode());
                    ApplicantContactDetails saveOrUpdate = this.applicantContactDetailsRepository.save(updateContactDetails);
                    details.setRegisteredAddressID(String.valueOf(saveOrUpdate.getId()));
                }else{
                    ApplicantContactDetails contactDetails = new ApplicantContactDetails();
                    contactDetails.setBuildingName(stepOne.getRegisteredBuildingName());
                    contactDetails.setPostalCode(stepOne.getRegisteredPostalCode());
                    contactDetails.setStreetName(stepOne.getRegisteredStreetName());
                    contactDetails.setState(stepOne.getRegisteredState());
                    contactDetails.setCountry(stepOne.getCountry());
                    contactDetails.setCity(stepOne.getRegisteredCity());
                    contactDetails.setCountryCode(stepOne.getRegisteredCountryCode());
                    ApplicantContactDetails saveOrUpdate = this.applicantContactDetailsRepository.save(contactDetails);
                    details.setRegisteredAddressID(String.valueOf(saveOrUpdate.getId()));
                }

                // Applicant Details
                details.setId(details.getId());
//				applicantDetails.setCompanyName(details.get)
//				details.setAuthToken(details.getAuthToken());
                details.setApplicantTypeID(stepOne.getApplicantType());
//				details.setEmailAddress(stepOne.getEmailAddress());
//				details.setMobileNumber(stepOne.getMobileNumber());
                if(stepOne.getWebsite()!=null && !stepOne.getWebsite().isEmpty() && !stepOne.getWebsite().isBlank()){
                    details.setWebsite(stepOne.getWebsite());
                }else{
                    details.setWebsite(null);
                }


                // contact details id will be add in details  RegisteredAddressID
                if(details.getStepsCompleted()==0){
                    details.setStepsCompleted(1);
                }


                /** set countryCode in step one -- 15-12-21*/
//				details.setCountryCode(stepOne.getCountryCode());
                ApplicantDetails save1 = this.applicantDetailsRepository.save(details);


                if (save1 != null) {
                    Optional<ApplicantApplicationDetails> byApplicantID = applicantApplicationDetailsRepository.findByApplicantID(save1.getId());
                    ApplicantApplicationDetails applicationDetails = byApplicantID.get();
//                    ApplicantContactDetails contactDetailsSaved1 = null;
                    // Applicant Contact Detail

                    if (stepOne.getSameAsRegisteredAddress().equals("Yes")){
                        if(applicationDetails.getBusinessAddressID()!=null && !applicationDetails.getBusinessAddressID().isEmpty() && !applicationDetails.getBusinessAddressID().isBlank()){
                            Optional<ApplicantContactDetails> foundByContactDetailsID = applicantContactDetailsRepository.findById(Integer.valueOf(applicationDetails.getBusinessAddressID()));
                            if(foundByContactDetailsID.isPresent()){
                                ApplicantContactDetails contactDetails = foundByContactDetailsID.get();
                                contactDetails.setId(contactDetails.getId());
                                contactDetails.setCountry(stepOne.getBusinessCountryCode());
                                contactDetails.setBuildingName(stepOne.getBussinessBuildingName());
                                contactDetails.setPostalCode(stepOne.getBusinessPostalCode());
                                contactDetails.setStreetName(stepOne.getBussinessStreetName());
                                contactDetails.setState(stepOne.getBusinessState());
                                contactDetails.setCity(stepOne.getBusinessCity());
                                contactDetails.setCountry(stepOne.getBusinessCountry());
                                contactDetails.setCountryCode(stepOne.getBusinessCountryCode());
                                ApplicantContactDetails contactDetailsSaved1 = this.applicantContactDetailsRepository.save(contactDetails);
                                applicationDetails.setBusinessAddressID(String.valueOf(contactDetailsSaved1.getId()));
                            }
                        }
                        else {
                            ApplicantContactDetails contactDetails1 = new ApplicantContactDetails();
                            contactDetails1.setCountry(stepOne.getBusinessCountryCode());
                            contactDetails1.setBuildingName(stepOne.getBussinessBuildingName());
                            contactDetails1.setPostalCode(stepOne.getBusinessPostalCode());
                            contactDetails1.setStreetName(stepOne.getBussinessStreetName());
                            contactDetails1.setState(stepOne.getBusinessState());
                            contactDetails1.setCity(stepOne.getBusinessCity());
                            contactDetails1.setCountry(stepOne.getBusinessCountry());
                            contactDetails1.setCountryCode(stepOne.getBusinessCountryCode());
                            ApplicantContactDetails contactDetailsSaved1 = this.applicantContactDetailsRepository.save(contactDetails1);
                            applicationDetails.setBusinessAddressID(String.valueOf(contactDetailsSaved1.getId()));
                        }
                    }else{
                        ApplicantContactDetails contactDetails1 = new ApplicantContactDetails();
                        contactDetails1.setCountry(stepOne.getBusinessCountryCode());
                        contactDetails1.setBuildingName(stepOne.getBussinessBuildingName());
                        contactDetails1.setPostalCode(stepOne.getBusinessPostalCode());
                        contactDetails1.setStreetName(stepOne.getBussinessStreetName());
                        contactDetails1.setState(stepOne.getBusinessState());
                        contactDetails1.setCity(stepOne.getBusinessCity());
                        contactDetails1.setCountry(stepOne.getBusinessCountry());
                        contactDetails1.setCountryCode(stepOne.getBusinessCountryCode());
                        ApplicantContactDetails contactDetailsSaved1 = this.applicantContactDetailsRepository.save(contactDetails1);
                        applicationDetails.setBusinessAddressID(String.valueOf(contactDetailsSaved1.getId()));
                    }


//                    if(applicationDetails.getBusinessAddressID()!=null && !applicationDetails.getBusinessAddressID().isEmpty() && !applicationDetails.getBusinessAddressID().isBlank()){
//                        Optional<ApplicantContactDetails> foundByContactDetailsID = applicantContactDetailsRepository.findById(Integer.valueOf(applicationDetails.getBusinessAddressID()));
//                        if(foundByContactDetailsID.isPresent()){
//                            ApplicantContactDetails contactDetails = foundByContactDetailsID.get();
//                            contactDetails.setId(contactDetails.getId());
//                            contactDetails.setCountry(stepOne.getBusinessCountryCode());
//                            contactDetails.setBuildingName(stepOne.getBussinessBuildingName());
//                            contactDetails.setPostalCode(stepOne.getBusinessPostalCode());
//                            contactDetails.setStreetName(stepOne.getBussinessStreetName());
//                            contactDetails.setState(stepOne.getBusinessState());
//                            contactDetails.setCity(stepOne.getBusinessCity());
//                            contactDetails.setCountry(stepOne.getBusinessCountry());
//                            contactDetails.setCountryCode(stepOne.getBusinessCountryCode());
//                            ApplicantContactDetails contactDetailsSaved1 = this.applicantContactDetailsRepository.save(contactDetails);
//                            applicationDetails.setBusinessAddressID(String.valueOf(contactDetailsSaved1.getId()));
//                        }else{
//                            ApplicantContactDetails contactDetails1 = new ApplicantContactDetails();
//                            contactDetails1.setCountry(stepOne.getBusinessCountryCode());
//                            contactDetails1.setBuildingName(stepOne.getBussinessBuildingName());
//                            contactDetails1.setPostalCode(stepOne.getBusinessPostalCode());
//                            contactDetails1.setStreetName(stepOne.getBussinessStreetName());
//                            contactDetails1.setState(stepOne.getBusinessState());
//                            contactDetails1.setCity(stepOne.getBusinessCity());
//                            contactDetails1.setCountry(stepOne.getBusinessCountry());
//                            contactDetails1.setCountryCode(stepOne.getBusinessCountryCode());
//                            ApplicantContactDetails contactDetailsSaved1 = this.applicantContactDetailsRepository.save(contactDetails1);
//                            applicationDetails.setBusinessAddressID(String.valueOf(contactDetailsSaved1.getId()));
//                        }
//                    }
//                    else{
//                        if (stepOne.getSameAsRegisteredAddress().equals("Yes")) {
//                            ApplicantContactDetails contactDetails1 = new ApplicantContactDetails();
//                            contactDetails1.setCountry(stepOne.getBusinessCountryCode());
//                            contactDetails1.setBuildingName(stepOne.getBussinessBuildingName());
//                            contactDetails1.setPostalCode(stepOne.getBusinessPostalCode());
//                            contactDetails1.setStreetName(stepOne.getBussinessStreetName());
//                            contactDetails1.setState(stepOne.getBusinessState());
//                            contactDetails1.setCity(stepOne.getBusinessCity());
//                            contactDetails1.setCountry(stepOne.getBusinessCountry());
//                            contactDetails1.setCountryCode(stepOne.getBusinessCountryCode());
//                            ApplicantContactDetails contactDetailsSaved1 = this.applicantContactDetailsRepository.save(contactDetails1);
//                            applicationDetails.setBusinessAddressID(String.valueOf(contactDetailsSaved1.getId()));
//                        } else {
//                            ApplicantContactDetails contactDetails1 = new ApplicantContactDetails();
//                            contactDetails1.setCountry(stepOne.getBusinessCountryCode());
//                            contactDetails1.setBuildingName(stepOne.getBussinessBuildingName());
//                            contactDetails1.setPostalCode(stepOne.getBusinessPostalCode());
//                            contactDetails1.setStreetName(stepOne.getBussinessStreetName());
//                            contactDetails1.setState(stepOne.getBusinessState());
//                            contactDetails1.setCity(stepOne.getBusinessCity());
//                            contactDetails1.setCountry(stepOne.getBusinessCountry());
//                            contactDetails1.setCountryCode(stepOne.getBusinessCountryCode());
//                            ApplicantContactDetails contactDetailsSaved1 = this.applicantContactDetailsRepository.save(contactDetails1);
//                            applicationDetails.setBusinessAddressID(String.valueOf(contactDetailsSaved1.getId()));
//                        }
//                    }


                    if (byApplicantID.isPresent()) {
                        applicationDetails.setId(applicationDetails.getId());
                        applicationDetails.setApplicantID(save1.getId());
                        applicationDetails.setApplicationTypeID(stepOne.getApplicationTypeID());
//						applicationDetails.setCompanyName(stepOne.getCompanyName());
                        if(stepOne.getReference()==null || stepOne.getReference().isEmpty() || stepOne.getReference().isBlank()) {

                        }else{
                            applicationDetails.setParentReference(stepOne.getReference());
                        }

//                        if (stepOne.getSameAsRegisteredAddress().equals("Yes")) {
//
//                        } else {
//                            applicationDetails.setBusinessAddressID(String.valueOf(contactDetailsSaved1.getId()));
//                        }

                        // contact details id will be add in applicationDetails  BussinessAddressID
                        applicationDetails.setBussinessTypeID(stepOne.getBusinessTypeID());
                        applicationDetails.setCurrentOrInterestedProducts(stepOne.getCurrentOrInterestedProducts());

                        List<Integer> dbList = this.applicantCommoditiesOfInterestRepository.getApplicantApplicationIDAndCommodityID(applicationDetails.getId());
                        List<Integer> comodityList =  Arrays.asList(stepOne.getCommodityInterest());
                        List<Integer> insert = comodityList.stream().filter(e -> !dbList.contains(e)).collect (Collectors.toList());
                        List<Integer> delete = dbList.stream().filter(e -> !comodityList.contains(e)).collect (Collectors.toList());
                        delete.forEach(e -> this.applicantCommoditiesOfInterestRepository.updateCommoditiesOfInterest(applicationDetails.getId(), e));

                        /** insert commodityList with applicantApplicationDetailsId -- 10-12-21*/
                        List<ApplicantCommoditiesOfInterest> applicantCommoditiesOfInterestList = new ArrayList<>();
//                        Integer[] commoditiesId = stepOne.getCommodityInterest();
                        for (Integer commodityId : insert) {
                            ApplicantCommoditiesOfInterest applicantCommoditiesOfInterest = new ApplicantCommoditiesOfInterest();
                            if (commodityId != 0) {
                                applicantCommoditiesOfInterest.setApplicantApplicationID(applicationDetails.getId());
                                applicantCommoditiesOfInterest.setCommodityID(commodityId);
                                applicantCommoditiesOfInterestList.add(applicantCommoditiesOfInterest);
                            }

                        }
                        applicantCommoditiesOfInterestRepository.saveAll(applicantCommoditiesOfInterestList);
                        LOGGER.info("Applicant Commodities Of Interest Saved...");
//						applicationDetails.setNationality(stepOne.getNationality());
                        ApplicantApplicationDetails save = this.applicantApplicationDetailsRepository.save(applicationDetails);
                        Optional<ApplicantOtherBusinessDetails> foundBusinessByApplicantID = this.applicantOtherBusinessDetailsRepository.findByApplicantID(save1.getId());
                        if(foundBusinessByApplicantID.isPresent()){
                            ApplicantOtherBusinessDetails businessDetails = foundBusinessByApplicantID.get();
                            businessDetails.setId(businessDetails.getId());
                            businessDetails.setNatureOfBusiness(stepOne.getNatureOfBusiness());
                            businessDetails.setNonAgriculturalBusinessName(stepOne.getNonAgricultureBusinessName());
                            businessDetails.setApplicantID(save1.getId());
                            businessDetails.setApplicationAplicantID(save.getId());
                            this.applicantOtherBusinessDetailsRepository.save(businessDetails);
                        }else{
                            ApplicantOtherBusinessDetails businessDetails = new ApplicantOtherBusinessDetails();
                            businessDetails.setNatureOfBusiness(stepOne.getNatureOfBusiness());
                            businessDetails.setNonAgriculturalBusinessName(stepOne.getNonAgricultureBusinessName());
                            businessDetails.setApplicantID(save1.getId());
                            businessDetails.setApplicationAplicantID(save.getId());
                            this.applicantOtherBusinessDetailsRepository.save(businessDetails);
                        }


                        if (save != null) {
                            /** update response format -- 15-12-21*/
                            objectMap = new HashMap<>();
//							objectMap.put("countryCode", stepOne.getCountryCode());
                            return responseMessageUtil.sendResponseWithData(true, "Step 1 Data Saved Successfully", objectMap);
                        } else {
                            return responseMessageUtil.sendResponse(false, "Data not saved", null);
                        }
                    } else {

                        applicantApplicationDetails.setApplicantID(save1.getId());
                        applicantApplicationDetails.setApplicationTypeID(stepOne.getApplicationTypeID());
//						applicantApplicationDetails.setCompanyName(stepOne.getCompanyName());
//                        applicationDetails.setBusinessAddressID(String.valueOf(contactDetailsSaved1.getId()));
                        applicantApplicationDetails.setBussinessTypeID(stepOne.getBusinessTypeID());
                        applicantApplicationDetails.setCurrentOrInterestedProducts(stepOne.getCurrentOrInterestedProducts());
//						applicantApplicationDetails.setNationality(stepOne.getNationality());
                        if(stepOne.getReference()==null || stepOne.getReference().isEmpty() || stepOne.getReference().isBlank()) {

                        }else{
                            applicantApplicationDetails.setParentReference(stepOne.getReference());
                        }
                        ApplicantApplicationDetails save = this.applicantApplicationDetailsRepository.save(applicantApplicationDetails);
                        if (save != null) {
                            /** update response format -- 15-12-21*/
                            objectMap = new HashMap<>();
//							objectMap.put("countryCode", stepOne.getCountryCode());
                            return responseMessageUtil.sendResponseWithData(true, "Step 1 Data Saved Successfully", objectMap);
                        } else {
                            return responseMessageUtil.sendResponse(false, "Data not saved", null);
                        }
                    }
                } else {
                    return responseMessageUtil.sendResponse(false, "something went wrong ", null);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return responseMessageUtil.sendResponse(false, "something went wrong ", null);
            }
        } else {
            throw new DoesNotExistException("Applicant Not found");
//			return responseMessageUtil.sendResponse(false,"applicant not found",null);
        }


    }

    @Transactional
    public ResponseMessage stepTwo(StepTwo stepTwo, String jwtToken) throws ParseException {

//		ObjectMapper objectMapper = new ObjectMapper();
//		StepTwo stepTwo = objectMapper.readValue(buyerFileDTO.getData(), StepTwo.class);
        Optional<ApplicantDetails> byAuthToken = applicantDetailsRepository.findByAuthToken(jwtToken);

        try {
            if (byAuthToken.isPresent()) {
                try {
                    List<String> panCardUrlList = new ArrayList<>();
                    List<String> gstCertificateUrlList = new ArrayList<>();
                    List<String> cinUrlList = new ArrayList<>();
                    List<String> addharCardUrlList = new ArrayList<>();
                    List<String> tanUrlList = new ArrayList<>();
                    List<String> marketLicenseDocumentUrlList = new ArrayList<>();
                    List<String> companyRegistrationDocumentUrlList = new ArrayList<>();
                    List<String> incomeTaxUrlList = new ArrayList<>();
                    List<String> vatUrlList = new ArrayList<>();

                    if (stepTwo.getPanFile() == null || stepTwo.getPanFile().isEmpty()) {
                    } else {
                        for (MultipartFile file : stepTwo.getPanFile()) {
                            String panurl = fileUrl("buyer", file);
                            panCardUrlList.add(panurl);
                        }
                    }
                    if (stepTwo.getGstCertificateFile() == null || stepTwo.getGstCertificateFile().isEmpty()) {

                    } else {
                        for (MultipartFile file : stepTwo.getGstCertificateFile()) {
                            gstCertificateUrlList.add(fileUrl("buyer", file));
                        }
                    }
                    if (stepTwo.getUdyogAadharFile() == null || stepTwo.getUdyogAadharFile().isEmpty()) {

                    } else {
                        for (MultipartFile file : stepTwo.getUdyogAadharFile()) {
                            addharCardUrlList.add(fileUrl("buyer", file));
                        }
                    }
                    if (stepTwo.getTanFile() == null || stepTwo.getTanFile().isEmpty()) {

                    } else {
                        for (MultipartFile file : stepTwo.getTanFile()) {
                            tanUrlList.add(fileUrl("buyer", file));
                        }
                    }
                    if (stepTwo.getCinFile() == null || stepTwo.getCinFile().isEmpty()) {

                    } else {
                        for (MultipartFile file : stepTwo.getCinFile()) {
                            cinUrlList.add(fileUrl("buyer", file));
                        }
                    }
                    if (stepTwo.getVatDocument() == null || stepTwo.getVatDocument().isEmpty()) {

                    } else {
                        /** change store multiple vat document files for other country user*/
                        for (MultipartFile file : stepTwo.getVatDocument()){
                            vatUrlList.add(fileUrl("buyer", file));
                        }
//                        vatUrl = fileUrl("buyer", stepTwo.getVatDocument()); // opy
                    }
                    if (stepTwo.getIncomeTaxDocument() == null || stepTwo.getIncomeTaxDocument().isEmpty()) {

                    } else {
                        /** change store multiple income taxDocument files for other country user*/
                        for (MultipartFile file : stepTwo.getIncomeTaxDocument()){
                            incomeTaxUrlList.add(fileUrl("buyer", file));
                        }
//                        incomeTaxUrl = fileUrl("buyer", stepTwo.getIncomeTaxDocument()); // opy
                    }
                    if (stepTwo.getCompanyRegistrationDocument() == null || stepTwo.getCompanyRegistrationDocument().isEmpty()) {

                    } else {
                        /** change store multiple Company Registration Document files for other country user*/
                        for (MultipartFile file : stepTwo.getCompanyRegistrationDocument()){
                            companyRegistrationDocumentUrlList.add(fileUrl("buyer", file));
                        }
//                        companyRegistrationDocumentUrl = fileUrl("buyer", stepTwo.getCompanyRegistrationDocument()); // opy
                    }if (stepTwo.getMarketLicenseFile() == null || stepTwo.getMarketLicenseFile().isEmpty()) {

                    } else {
                        for (MultipartFile file : stepTwo.getMarketLicenseFile()) {
                            marketLicenseDocumentUrlList.add(fileUrl("buyer", file));
                        }
                    }

                    String panCardUrl = java.net.URLDecoder.decode(String.join(",", panCardUrlList), StandardCharsets.UTF_8.name());
                    String gstCertificateUrl = java.net.URLDecoder.decode(String.join(",", gstCertificateUrlList), StandardCharsets.UTF_8.name());
                    String cinUrl = java.net.URLDecoder.decode(String.join(",", cinUrlList), StandardCharsets.UTF_8.name());
                    String addharCardUrl = java.net.URLDecoder.decode(String.join(",", addharCardUrlList), StandardCharsets.UTF_8.name());
                    String tanUrl = java.net.URLDecoder.decode(String.join(",", tanUrlList), StandardCharsets.UTF_8.name());
                    String marketLicenseDocumentUrl = java.net.URLDecoder.decode(String.join(",", marketLicenseDocumentUrlList), StandardCharsets.UTF_8.name());
                    String vatUrl = java.net.URLDecoder.decode(String.join(",", vatUrlList), StandardCharsets.UTF_8.name());
                    String incomeTaxUrl = java.net.URLDecoder.decode(String.join(",", incomeTaxUrlList), StandardCharsets.UTF_8.name());
                    String companyRegistrationDocumentUrl = java.net.URLDecoder.decode(String.join(",", companyRegistrationDocumentUrlList), StandardCharsets.UTF_8.name());

                    ApplicantDetails details = byAuthToken.get();
                    details.setId(details.getId());
                    if(details.getStepsCompleted()==1){
                        details.setStepsCompleted(2);
                    }else{
                        details.setStepsCompleted(2);
                    }
//                    details.setStepsCompleted(2);
                    ApplicantDetails applicantDetailsSaved = this.applicantDetailsRepository.save(details);
                    if (applicantDetailsSaved != null) {
                        Optional<ApplicantApplicationDetails> byApplicantID = applicantApplicationDetailsRepository.findByApplicantID(applicantDetailsSaved.getId());
                        ApplicantApplicationDetails applicantApplicationDetails = byApplicantID.get();
                        applicantApplicationDetails.setId(applicantApplicationDetails.getId());

                        if (!cinUrl.isEmpty() && !cinUrl.isBlank()) {
                            applicantApplicationDetails.setCinUrl(cinUrl);
                        }
                        if (!addharCardUrl.isEmpty() && !addharCardUrl.isBlank()) {
                            applicantApplicationDetails.setUdyouAadharUrl(addharCardUrl);
                        }
                        if (!tanUrl.isEmpty() && !tanUrl.isBlank()) {
                            applicantApplicationDetails.setTanUrl(tanUrl);
                        }
                        if (!vatUrl.isEmpty() && !vatUrl.isBlank()) {
                            applicantApplicationDetails.setVatUrl(vatUrl);
                        }
                        if (!incomeTaxUrl.isEmpty() && !incomeTaxUrl.isBlank()) {
                            applicantApplicationDetails.setIncomeTaxUrl(incomeTaxUrl);
                        }
                        if (!companyRegistrationDocumentUrl.isEmpty() && !companyRegistrationDocumentUrl.isBlank()) {
                            applicantApplicationDetails.setCompanyRegistrationDocumentUrl(companyRegistrationDocumentUrl);
                        }
                        if (!panCardUrl.isEmpty() && !panCardUrl.isBlank()) {
                            applicantApplicationDetails.setPanUrl(panCardUrl);
                        }
                        if (!gstCertificateUrl.isEmpty() && !gstCertificateUrl.isBlank()) {
                            applicantApplicationDetails.setGstUrl(gstCertificateUrl);
                        }if (!marketLicenseDocumentUrl.isEmpty() && !marketLicenseDocumentUrl.isBlank()) {
                            applicantApplicationDetails.setMarketLicenseDocumentUrl(marketLicenseDocumentUrl);
                        }

                        if(stepTwo.getCinNo()!=null && !stepTwo.getCinNo().isEmpty() && !stepTwo.getCinNo().isBlank()){
                            applicantApplicationDetails.setCinNumber(stepTwo.getCinNo().trim());
                        }if(stepTwo.getUdyogAadharNo()!=null && !stepTwo.getUdyogAadharNo().isEmpty() && !stepTwo.getUdyogAadharNo().isBlank()){
                            applicantApplicationDetails.setUdyogAdhaarNumber(stepTwo.getUdyogAadharNo().trim());
                        }if(stepTwo.getPanNo()!=null && !stepTwo.getPanNo().isEmpty() && !stepTwo.getPanNo().isBlank()){
                            applicantApplicationDetails.setPanNumber(stepTwo.getPanNo().trim());
                        }if(stepTwo.getGstNo()!=null && !stepTwo.getGstNo().isEmpty() && !stepTwo.getGstNo().isBlank()){
                            applicantApplicationDetails.setGstNumber(stepTwo.getGstNo().trim());
                        }if(stepTwo.getTanNo()!=null && !stepTwo.getTanNo().isEmpty() && !stepTwo.getTanNo().isBlank()){
                            applicantApplicationDetails.setTanNo(stepTwo.getTanNo().trim());
                        }if(stepTwo.getIncomeTaxNo()!=null && !stepTwo.getIncomeTaxNo().isEmpty() && !stepTwo.getIncomeTaxNo().isBlank()){
                            applicantApplicationDetails.setIncomeTaxNumber(stepTwo.getIncomeTaxNo().trim());
                        }if(stepTwo.getVatNo()!=null && !stepTwo.getVatNo().isEmpty() && !stepTwo.getVatNo().isBlank()){
                            applicantApplicationDetails.setVat(stepTwo.getVatNo());
                        }if(stepTwo.getCompanyRegistrationNo()!=null && !stepTwo.getCompanyRegistrationNo().isEmpty() && !stepTwo.getCompanyRegistrationNo().isBlank()){
                            applicantApplicationDetails.setCompanyRegistrationNumber(stepTwo.getCompanyRegistrationNo());
                        }if(stepTwo.getMarketLicenseNo()!=null && !stepTwo.getMarketLicenseNo().isEmpty() && !stepTwo.getMarketLicenseNo().isBlank()){
                            applicantApplicationDetails.setMarketLicenseNumber(stepTwo.getMarketLicenseNo());
                        }
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

                        if (stepTwo.getDateOfIncorporationOrLicense() == null || stepTwo.getDateOfIncorporationOrLicense().isEmpty()) {
                        } else {
                            Date da1 = formatter.parse(stepTwo.getDateOfIncorporationOrLicense().trim());
                            applicantApplicationDetails.setDateOfIncorporation(da1);
                        }
                        if (stepTwo.getCinNoExpiryDate() == null || stepTwo.getCinNoExpiryDate().isEmpty()) {
                        } else {
                            Date da2 = formatter.parse(stepTwo.getCinNoExpiryDate().trim());
                            applicantApplicationDetails.setCinLiscenceExpiryDate(da2);
                        }
                        if (stepTwo.getDateOfIncorporationOrRegistration() == null || stepTwo.getDateOfIncorporationOrRegistration().isEmpty()) {
                        } else {
                            Date da3 = formatter.parse(stepTwo.getDateOfIncorporationOrRegistration().trim());
                            applicantApplicationDetails.setDateOfIncorporationRegistration(da3);
                        }
                        if (stepTwo.getLicenseExpiryDate() == null || stepTwo.getLicenseExpiryDate().isEmpty()) {
                        } else {
                            Date da4 = formatter.parse(stepTwo.getLicenseExpiryDate().trim());
                            applicantApplicationDetails.setLiscenceExpiryDate(da4);
                        }


                        ApplicantApplicationDetails save = this.applicantApplicationDetailsRepository.save(applicantApplicationDetails);
                        if (save != null) {

                            ApplicationSignatoryDetails adminDetails = new ApplicationSignatoryDetails();
                            Integer integer = this.applicationSignatoryDetailsRepository.deleteSig(details.getId());
                            Optional<ApplicationSignatoryDetails> foundApplicantBySignatry = this.applicationSignatoryDetailsRepository.findByApplicantID(details.getId());

                            if (foundApplicantBySignatry.isPresent()) {

                                ApplicationSignatoryDetails applicationSignatoryDetails = foundApplicantBySignatry.get();

//                                applicationSignatoryDetails.setId(applicationSignatoryDetails.getId());

                                for (int i = 0; i < stepTwo.getDirectorCount(); i++) {

//                                    applicationSignatoryDetails.setId(applicationSignatoryDetails.getId());
                                    LOGGER.info("i value {}",i);
//                                    List<ApplicationSignatoryDetails> applicationSignatoryDetails = new ArrayList<>();
//                                    ApplicationSignatoryDetails signatoryDetails = new ApplicationSignatoryDetails();

//                                    for (int f = i; f < stepTwo.getDirectorsProprietorPartnerFirstName().size(); f++) {
//
//                                        String fname = null;
//                                        String mname = null;
//                                        String lname=null;
//                                        if(stepTwo.getDirectorsProprietorPartnerMiddleName().isEmpty() || stepTwo.getDirectorsProprietorPartnerMiddleName()==null){
//
//                                        }else{
//                                            for (int j = i; j < stepTwo.getDirectorsProprietorPartnerMiddleName().size(); j++) {
//                                                mname = stepTwo.getDirectorsProprietorPartnerMiddleName().get(i);
//                                            }
//                                        }
//                                        for (int k = i; k < stepTwo.getDirectorsProprietorPartnerLastName().size(); k++) {
//                                            lname = stepTwo.getDirectorsProprietorPartnerLastName().get(i);
//                                        }
//                                        applicationSignatoryDetails.setCompanyAuthSignName(stepTwo.getDirectorsProprietorPartnerFirstName().get(i)+" "+mname+" "+lname);
//                                    }
                                    for (int f = i; f < stepTwo.getDirectorsProprietorPartnerFirstName().size(); f++) {
                                        applicationSignatoryDetails.setCompanyAuthSignFirstName(stepTwo.getDirectorsProprietorPartnerFirstName().get(i));
                                    }for (int f = i; f < stepTwo.getDirectorsProprietorPartnerMiddleName().size(); f++) {
                                        applicationSignatoryDetails.setCompanyAuthSignMiddleName(stepTwo.getDirectorsProprietorPartnerMiddleName().get(i));
                                    }for (int f = i; f < stepTwo.getDirectorsProprietorPartnerLastName().size(); f++) {
                                        applicationSignatoryDetails.setCompanyAuthSignLastName(stepTwo.getDirectorsProprietorPartnerLastName().get(i));
                                    }
                                    for (int f = i; f < stepTwo.getDirectorsProprietorPartnerMobileNo().size(); f++) {
                                        applicationSignatoryDetails.setCompanyMobileNumber(stepTwo.getDirectorsProprietorPartnerMobileNo().get(i));
                                    }
                                    for (int f = i; f < stepTwo.getDirectorsProprietorPartnerEmail().size(); f++) {
                                        applicationSignatoryDetails.setCompanyAuthSignEmail(stepTwo.getDirectorsProprietorPartnerEmail().get(i));
                                    }
                                    for (int f = i; f < stepTwo.getDirectorsProprietorPartnerTelephoneNoPrefix().size(); f++) {
                                        applicationSignatoryDetails.setTelephoneIsdCode(stepTwo.getDirectorsProprietorPartnerTelephoneNoPrefix().get(i));
                                    }
                                    for (int f = i; f < stepTwo.getDirectorsProprietorPartnerTelephoneNo().size(); f++) {
                                        applicationSignatoryDetails.setCompanyTelephoneNumber(stepTwo.getDirectorsProprietorPartnerTelephoneNo().get(i));
                                    }
                                    for (int f = i; f < stepTwo.getDirectorsProprietorPartnerMobileNoPrefix().size(); f++) {
                                        applicationSignatoryDetails.setIsdCode(stepTwo.getDirectorsProprietorPartnerMobileNoPrefix().get(i));
                                    }
                                    for (int f = i; f < stepTwo.getDirectorsProprietorPartnerMobileNo().size(); f++) {
                                        applicationSignatoryDetails.setCompanyMobileNumber(stepTwo.getDirectorsProprietorPartnerMobileNo().get(i));
                                    }
                                    for (int f = i; f < stepTwo.getDirectorsProprietorPartnerDesignation().size(); f++) {
                                        applicationSignatoryDetails.setCompanyAuthSignDesignationID(stepTwo.getDirectorsProprietorPartnerDesignation().get(i));
                                    }for (int f = i; f < stepTwo.getDirectorsProprietorPartnerOtherDesignation().size(); f++) {
                                        applicationSignatoryDetails.setOtherDesignation(stepTwo.getDirectorsProprietorPartnerOtherDesignation().get(i));
                                    }
                                    applicationSignatoryDetails.setApplicantID(applicantApplicationDetails.getId());

                                    this.applicationSignatoryDetailsRepository.save(applicationSignatoryDetails);
                                }
                            } else {


                                for (int i = 0; i < stepTwo.getDirectorCount(); i++) {
                                    LOGGER.info("i value {}",i);
                                    List<ApplicationSignatoryDetails> applicationSignatoryDetails = new ArrayList<>();
                                    ApplicationSignatoryDetails signatoryDetails = new ApplicationSignatoryDetails();
//                                    for (int f = i; f < stepTwo.getDirectorsProprietorPartnerFirstName().size(); f++) {
//                                        String fname = null;
//                                        String mname = null;
//                                        String lname=null;
//                                        if(stepTwo.getDirectorsProprietorPartnerMiddleName().isEmpty() || stepTwo.getDirectorsProprietorPartnerMiddleName()==null){
//
//                                        }else{
//                                            for (int j = i; j < stepTwo.getDirectorsProprietorPartnerMiddleName().size(); j++) {
//                                                mname = stepTwo.getDirectorsProprietorPartnerMiddleName().get(i);
//                                            }
//                                        }
//                                        for (int k = i; k < stepTwo.getDirectorsProprietorPartnerLastName().size(); k++) {
//                                            lname = stepTwo.getDirectorsProprietorPartnerLastName().get(i);
//                                        }
//                                        signatoryDetails.setCompanyAuthSignName(stepTwo.getDirectorsProprietorPartnerFirstName().get(i)+" "+mname+" "+lname);
//                                    }
                                    for (int f = i; f < stepTwo.getDirectorsProprietorPartnerFirstName().size(); f++) {
                                        signatoryDetails.setCompanyAuthSignFirstName(stepTwo.getDirectorsProprietorPartnerFirstName().get(i));
                                    }for (int f = i; f < stepTwo.getDirectorsProprietorPartnerMiddleName().size(); f++) {
                                        signatoryDetails.setCompanyAuthSignMiddleName(stepTwo.getDirectorsProprietorPartnerMiddleName().get(i));
                                    }for (int f = i; f < stepTwo.getDirectorsProprietorPartnerLastName().size(); f++) {
                                        signatoryDetails.setCompanyAuthSignLastName(stepTwo.getDirectorsProprietorPartnerLastName().get(i));
                                    }
                                    for (int f = i; f < stepTwo.getDirectorsProprietorPartnerMobileNo().size(); f++) {
                                        signatoryDetails.setCompanyMobileNumber(stepTwo.getDirectorsProprietorPartnerMobileNo().get(i));
                                    }
                                    for (int f = i; f < stepTwo.getDirectorsProprietorPartnerEmail().size(); f++) {
                                        signatoryDetails.setCompanyAuthSignEmail(stepTwo.getDirectorsProprietorPartnerEmail().get(i));
                                    }
                                    for (int f = i; f < stepTwo.getDirectorsProprietorPartnerTelephoneNoPrefix().size(); f++) {
                                        signatoryDetails.setTelephoneIsdCode(stepTwo.getDirectorsProprietorPartnerTelephoneNoPrefix().get(i));
                                    }
                                    for (int f = i; f < stepTwo.getDirectorsProprietorPartnerTelephoneNo().size(); f++) {
                                        signatoryDetails.setCompanyTelephoneNumber(stepTwo.getDirectorsProprietorPartnerTelephoneNo().get(i));
                                    }
                                    for (int f = i; f < stepTwo.getDirectorsProprietorPartnerMobileNoPrefix().size(); f++) {
                                        signatoryDetails.setIsdCode(stepTwo.getDirectorsProprietorPartnerMobileNoPrefix().get(i));
                                    }
                                    for (int f = i; f < stepTwo.getDirectorsProprietorPartnerMobileNo().size(); f++) {
                                        signatoryDetails.setCompanyMobileNumber(stepTwo.getDirectorsProprietorPartnerMobileNo().get(i));
                                    }
                                    for (int f = i; f < stepTwo.getDirectorsProprietorPartnerDesignation().size(); f++) {
                                        signatoryDetails.setCompanyAuthSignDesignationID(stepTwo.getDirectorsProprietorPartnerDesignation().get(i));
                                    }for (int f = i; f < stepTwo.getDirectorsProprietorPartnerOtherDesignation().size(); f++) {
                                        signatoryDetails.setOtherDesignation(stepTwo.getDirectorsProprietorPartnerOtherDesignation().get(i));
                                    }
                                    signatoryDetails.setApplicantID(applicantApplicationDetails.getId());

                                    this.applicationSignatoryDetailsRepository.save(signatoryDetails);
                                }
                                // admin info

//                                String adminName = stepTwo.getAdminFirstName() + " " + stepTwo.getAdminMiddleName() + " " + stepTwo.getAdminLastName();
                                adminDetails.setApplicantID(save.getApplicantID());
                                adminDetails.setCompanyAuthSignFirstName(stepTwo.getAdminFirstName());
                                adminDetails.setCompanyAuthSignMiddleName(stepTwo.getAdminMiddleName());
                                adminDetails.setCompanyAuthSignLastName(stepTwo.getAdminLastName());
                                adminDetails.setCompanyMobileNumber(stepTwo.getAdminMobileNo());
                                adminDetails.setCompanyAuthSignEmail(stepTwo.getAdminEmail());
//								signatoryDetails.setCompanyTelephoneNumber(stepTwo.getAdmin);
//								adminDetails.setIsdCode(stepTwo.getIsdCode());
                                adminDetails.setCompanyAuthSignDesignationID(7);
                                if (!stepTwo.getAdminMobileNoPrefix().isEmpty() || !stepTwo.getAdminMobileNoPrefix().isBlank()) {
                                    adminDetails.setIsdCode(stepTwo.getAdminMobileNoPrefix().trim());
                                }
                                if (!stepTwo.getAdminTelephoneNoPrefix().isEmpty() || !stepTwo.getAdminTelephoneNoPrefix().isBlank()) {
                                    adminDetails.setTelephoneIsdCode(stepTwo.getAdminTelephoneNoPrefix().trim());
                                }
                                if (!stepTwo.getAdminTelephoneNo().isEmpty() || !stepTwo.getAdminTelephoneNo().isBlank()) {
                                    adminDetails.setCompanyTelephoneNumber(stepTwo.getAdminTelephoneNo().trim());
                                }
                                ApplicationSignatoryDetails saveAdminDetails = this.applicationSignatoryDetailsRepository.save(adminDetails);

                                if (saveAdminDetails != null) {
                                    return responseMessageUtil.sendResponse(true, "Step 2 Data saved successfully", null);
                                } else {
                                    return responseMessageUtil.sendResponse(false, "applicant not found", null);
                                }
                            }


                        } else {
                            return responseMessageUtil.sendResponse(false, "applicant not found", null);
                        }
                    } else {
                        return responseMessageUtil.sendResponse(false, "applicant not found", null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return responseMessageUtil.sendResponse(false, e.getMessage(), null);
                }

            } else {
                return responseMessageUtil.sendResponse(false, "applicant not found", null);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            return responseMessageUtil.sendResponse(false, e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            return responseMessageUtil.sendResponse(false, e.getMessage(), null);
        }

    return null;
    }


    @Transactional
    public ResponseMessage stepThree(StepThree stepThree, String jwtToken) {
        Optional<ApplicantDetails> byAuthToken = applicantDetailsRepository.findByAuthToken(jwtToken);
        if (byAuthToken.isPresent()) {
            try {
                ApplicantDetails applicantDetails = byAuthToken.get();
                Optional<ApplicantBankDetails> byApplicantID = this.applicantBankDetailsRepository.findByApplicantID(applicantDetails.getId());
                Optional<ApplicantApplicationDetails> byApplicantApplicationDetails = this.applicantApplicationDetailsRepository.findByApplicantID(applicantDetails.getId());

                ApplicantBankDetails details = new ApplicantBankDetails();
                if (byApplicantID.isPresent()) {
                    ApplicantBankDetails applicantBankDetails = byApplicantID.get();
                    applicantBankDetails.setId(applicantBankDetails.getId());
                    applicantBankDetails.setApplicantApplicationID(byApplicantApplicationDetails.get().getId());
                    applicantBankDetails.setApplicantID(applicantDetails.getId());
                    applicantBankDetails.setName(stepThree.getName());
                    applicantBankDetails.setBranch(stepThree.getBranch());
                    applicantBankDetails.setAccountType(stepThree.getAccountType());
                    applicantBankDetails.setAccountNumber(stepThree.getAccountNumber());
                    applicantBankDetails.setIsOperational(stepThree.getIsOperational());
                    applicantBankDetails.setIfscCode(stepThree.getIfscCode());
                    applicantDetails.setId(applicantDetails.getId());
//					applicantDetails.setMembershipType(stepThree.getMembershipType());
                    if(applicantDetails.getStepsCompleted()==2){
                        applicantDetails.setStepsCompleted(3);
                    }else{
                        applicantDetails.setStepsCompleted(3);
                    }

                    if (byApplicantApplicationDetails.isPresent()) {
                        ApplicantApplicationDetails applicationApplicationDetails = byApplicantApplicationDetails.get();
                        applicationApplicationDetails.setAgreementAccepted(stepThree.getAgreementAccepted());
                        String generatedReferenceCode = CryptoUtils.referalCoder(applicantDetails.getId());
                        applicationApplicationDetails.setReference(generatedReferenceCode);
                        this.applicantApplicationDetailsRepository.save(applicationApplicationDetails);
                    }


                    ApplicantDetails saveApplicantDetails = this.applicantDetailsRepository.save(applicantDetails);
                    this.applicantDetailsRepository.isApplicantActive(applicantDetails.getId());
                    ApplicantBankDetails applicantBankDetailsSaved = this.applicantBankDetailsRepository.save(applicantBankDetails);
                    if (applicantBankDetailsSaved != null) {
//                        String emailResponse = emailServiceUtil.sendMail(applicantDetails.getEmailAddress(), applicantDetails.getFirstName(), properties.getFromMailId(), properties.getFromMailerName(), properties.getPartenershipSuccessfullMailTemplateId());
                        return responseMessageUtil.sendResponse(true, "Step 3 Saved successfully", null);
                    } else {
                        return responseMessageUtil.sendResponse(false, "Something went wrong", null);
                    }


                } else {
                    String generatedReferenceCode = null;
                    ApplicantApplicationDetails applicationApplicationDetails = new ApplicantApplicationDetails();
                    details.setApplicantID(applicantDetails.getId());
                    details.setName(stepThree.getName());
                    details.setBranch(stepThree.getBranch());
                    details.setApplicantApplicationID(byApplicantApplicationDetails.get().getId());
                    details.setAccountType(stepThree.getAccountType());
                    details.setAccountNumber(stepThree.getAccountNumber());
//					details.setIsOperational(stepThree.getIsOperational());
                    details.setIfscCode(stepThree.getIfscCode());
                    applicantDetails.setId(applicantDetails.getId());
//					applicantDetails.setMembershipType(stepThree.getMembershipType());
                    applicantDetails.setStepsCompleted(3);
                    ApplicantDetails saveApplicantDetails = this.applicantDetailsRepository.save(applicantDetails);
                    if (byApplicantApplicationDetails.isPresent()) {
                        applicationApplicationDetails = byApplicantApplicationDetails.get();
                        generatedReferenceCode = CryptoUtils.referalCoder(applicantDetails.getId());
                        applicationApplicationDetails.setReference(generatedReferenceCode);
                        applicationApplicationDetails.setAgreementAccepted(stepThree.getAgreementAccepted());
                        this.applicantApplicationDetailsRepository.save(applicationApplicationDetails);
                    }
                    this.applicantDetailsRepository.isApplicantActive(applicantDetails.getId());

                    ApplicantBankDetails applicantBankDetailsSaved = this.applicantBankDetailsRepository.save(details);

                    /**send mail for new user -- Ujwal -- 10-12-21*/

                        /** find and check parentReference and it is franchise or not -- Ujwal*/
                            if (applicationApplicationDetails.getParentReference() != null && !(applicationApplicationDetails.getParentReference().isEmpty())){
                                Optional<ApplicantApplicationDetails> parentApplicantApplicationDetails = applicantApplicationDetailsRepository.findApplicantApplicationDetailsByReference(applicationApplicationDetails.getParentReference());
                                if (parentApplicantApplicationDetails.get().getApplicationTypeID() == APIConstants.FRANCHISE){

                                    String emailResponse = emailServiceUtil.sendReferenceUserRegistrationMail(applicantDetails.getEmailAddress(), "", properties.getFromMailId(), properties.getFromMailerName(),properties.getReferenceUserMailTemplateId(),parentApplicantApplicationDetails.get(), properties.getPortalEnv());
                                }
                            }

//                        Optional<EmailTemplate> emailTemplate = emailTemplateRepository.findAllByName(APIConstants.SUCCESSFULL_DOCUMENT_TEMPLATE);
//                        ResponseMessage emailResponse = emailServiceUtil.sendSimpleMessage(applicantDetails.getEmailAddress(), emailTemplate.get().getSubject(), emailTemplate.get().getBody().replace("{{referralcode}}", generatedReferenceCode).replace("{{portal.env}}",properties.getPortalEnv()));
                        String emailResponse = emailServiceUtil.sendRegistrationMail(applicantDetails.getEmailAddress(), "",properties.getFromMailId(),properties.getFromMailerName(),properties.getPartenershipSuccessfullMailTemplateId(),generatedReferenceCode, properties.getPortalEnv());

                    return responseMessageUtil.sendResponse(true, "Step 3 Data Saved successfully", null);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return responseMessageUtil.sendResponse(false, e.getMessage(), null);
            }
        } else {
            return responseMessageUtil.sendResponse(false, "applicant not found", null);
        }
    }


    private String fileUrl(String pathKey, MultipartFile file) throws IOException {
        Map<String, Object> pathKeyExist = this.fileManagerUtil.isPathKeyExist(pathKey);
        String url = "";
        if (pathKeyExist.get("exist") != null) {
            if (pathKeyExist.get("exist").toString().equals("true")) {
                FileUploadResponseDTO fileUploadResponseDTO = this.fileManagerUtil.uploadFile(pathKey, false, file.getOriginalFilename(), true,
                        file, null);
                url = fileUploadResponseDTO.getPublicUrl();
            }
        }
        return url;
    }

    @Transactional
    public ResponseMessage addPreBuyerApplication(BuyerPreApplicationDto buyerPreApplicationDto) {
        try {
            LOGGER.info("Inside addPreBuyerApplication method of Service...");

            // Saving Applicant Details

            ApplicantDetails applicantDetails = new ApplicantDetails();


//			applicantDetails.setName(buyerPreApplicationDto.getName());
            applicantDetails.setDesignationID(buyerPreApplicationDto.getDesignationID());
            if (buyerPreApplicationDto.getDesignationID() != null && buyerPreApplicationDto.getDesignationID() == 6) {
                applicantDetails.setOtherDesignationName(buyerPreApplicationDto.getOtherDesignationName());
            } else {
                applicantDetails.setOtherDesignationName(null);
            }
            if (buyerPreApplicationDto.getTelephoneNumber() == null || "".equals(buyerPreApplicationDto.getTelephoneNumber())) {
                applicantDetails.setTelephoneNumber(null);
            } else {
                applicantDetails.setTelephoneNumber(buyerPreApplicationDto.getTelephoneNumber());

            }
            if (buyerPreApplicationDto.getMobileNumber() == null || "".equals(buyerPreApplicationDto.getMobileNumber())) {
                applicantDetails.setMobileNumber(null);
            } else {
                applicantDetails.setMobileNumber(buyerPreApplicationDto.getMobileNumber());
            }
            if (buyerPreApplicationDto.getEmailAddress() == null || "".equals(buyerPreApplicationDto.getEmailAddress())) {
                applicantDetails.setEmailAddress(null);
            } else {
                applicantDetails.setEmailAddress(buyerPreApplicationDto.getEmailAddress());
            }
//			applicantDetails.setRegisteredAddress(buyerPreApplicationDto.getRegisteredAddress());
            if (buyerPreApplicationDto.getNationality() == null || "".equals(buyerPreApplicationDto.getNationality())) {
                applicantDetails.setNationality(null);
            } else {
                applicantDetails.setNationality(buyerPreApplicationDto.getNationality());
            }
//			applicantDetails.setLocationOfInterest(buyerPreApplicationDto.getLocationOfInterest());
            applicantDetails.setApplicantTypeID(buyerPreApplicationDto.getApplicantTypeID());

            if (buyerPreApplicationDto.getWebsite() == null || "".equals(buyerPreApplicationDto.getWebsite())) {
                applicantDetails.setWebsite(null);
            } else {
                applicantDetails.setWebsite(buyerPreApplicationDto.getWebsite());
            }

            ApplicantDetails savedApplicantDetails = applicantDetailsRepository.save(applicantDetails);
            LOGGER.info("Applicant Details Saved...");

            // Saving Applicant Application Details

            ApplicantApplicationDetails applicantApplicationDetails = new ApplicantApplicationDetails();
            applicantApplicationDetails.setApplicantID(savedApplicantDetails.getId());
            applicantApplicationDetails.setApplicationTypeID(buyerPreApplicationDto.getApplicationTypeID());
            applicantApplicationDetails.setCompanyName(buyerPreApplicationDto.getCompanyName());
//			applicantApplicationDetails.setDateOfIncorporation(buyerPreApplicationDto.getDateOfIncorporation());
            if ("Indian".equals(buyerPreApplicationDto.getNationality())) {
                applicantApplicationDetails.setPanNumber(buyerPreApplicationDto.getPanNumber());
            } else {
                applicantApplicationDetails.setPanNumber(null);
            }

            if ("Indian".equals(buyerPreApplicationDto.getNationality()) && buyerPreApplicationDto.getApplicantTypeID() == 6) {
                applicantApplicationDetails.setCinNumber(null);
            } else if ("Indian".equals(buyerPreApplicationDto.getNationality()) && buyerPreApplicationDto.getApplicantTypeID() != 6) {
                applicantApplicationDetails.setCinNumber(buyerPreApplicationDto.getCinNumber());
            } else if ("International".equals(buyerPreApplicationDto.getNationality())) {
                applicantApplicationDetails.setCinNumber(null);
            }

            if ("Indian".equals(buyerPreApplicationDto.getNationality())) {
                applicantApplicationDetails.setGstNumber(buyerPreApplicationDto.getGstNumber());
            } else {
                applicantApplicationDetails.setGstNumber(null);
            }

            if ("International".equals(buyerPreApplicationDto.getNationality())) {
                applicantApplicationDetails.setCompanyRegistrationNumber(buyerPreApplicationDto.getCompanyRegistrationNumber());
            } else {
                applicantApplicationDetails.setCompanyRegistrationNumber(null);
            }

            if ("International".equals(buyerPreApplicationDto.getNationality())) {
                applicantApplicationDetails.setVat(buyerPreApplicationDto.getVat());
            } else {
                applicantApplicationDetails.setVat(null);
            }

//			applicantApplicationDetails.setBuildingName(buyerPreApplicationDto.getBuildingName());
//			applicantApplicationDetails.setStreetName(buyerPreApplicationDto.getStreetName());
//			applicantApplicationDetails.setPostalCode(buyerPreApplicationDto.getPostalCode());
//			applicantApplicationDetails.setCountry(buyerPreApplicationDto.getCountry());
//			applicantApplicationDetails.setState(buyerPreApplicationDto.getState());
//			applicantApplicationDetails.setCity(buyerPreApplicationDto.getCity());


//			applicantApplicationDetails.setBusinessAddress(buyerPreApplicationDto.getBusinessAddress());
            if (buyerPreApplicationDto.getReference() == null || "".equals(buyerPreApplicationDto.getReference())) {
                applicantApplicationDetails.setReference(null);
            } else {
                applicantApplicationDetails.setReference(buyerPreApplicationDto.getReference());
            }

            ApplicantApplicationDetails savedApplicantApplicationDetails = applicantApplicationDetailsRepository.save(applicantApplicationDetails);
            LOGGER.info("Applicant Application Details Saved...");

            // Saving Applicant Commodities Of Interest


            List<ApplicantCommoditiesOfInterest> applicantCommoditiesOfInterestList = new ArrayList<>();
            Integer[] commoditiesId = buyerPreApplicationDto.getCommoditiesId();
            for (Integer commodityId : commoditiesId) {
                ApplicantCommoditiesOfInterest applicantCommoditiesOfInterest = new ApplicantCommoditiesOfInterest();
                applicantCommoditiesOfInterest.setApplicantApplicationID(savedApplicantApplicationDetails.getId());
                applicantCommoditiesOfInterest.setCommodityID(commodityId);
                applicantCommoditiesOfInterestList.add(applicantCommoditiesOfInterest);
            }
            applicantCommoditiesOfInterestRepository.saveAll(applicantCommoditiesOfInterestList);
            LOGGER.info("Applicant Commodities Of Interest Saved...");

            // Saving Applicant Contact Details

//			ApplicantContactDetails applicantContactDetails = new ApplicantContactDetails();
//			applicantContactDetails.setApplicantID(savedApplicantDetails.getId());
//			applicantContactDetails.setApplicationAplicantID(savedApplicantApplicationDetails.getId());
//			
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
//			applicantContactDetails.setCompanyRepresentativeContactNumber(buyerPreApplicationDto.getCompanyRepresentativeContactNumber());
//			applicantContactDetails.setCompanyRepresentativeEmailID(buyerPreApplicationDto.getCompanyRepresentativeEmailID());
//			
//			applicantContactDetailsRepository.save(applicantContactDetails);
//			LOGGER.info("Applicant Contact Details Saved...");

            // Saving Applicant Office Space Details

//			ApplicantOfficeSpaceDetails applicantOfficeSpaceDetails = new ApplicantOfficeSpaceDetails();
//			applicantOfficeSpaceDetails.setApplicantID(savedApplicantDetails.getId()); 
//			applicantOfficeSpaceDetails.setApplicationAplicantID(savedApplicantApplicationDetails.getId()); 
//			
//			if(buyerPreApplicationDto.getOfficeSpace() == null  || "".equals(buyerPreApplicationDto.getOfficeSpace())) {
//				applicantOfficeSpaceDetails.setOfficeSpace(null);
//			}else {
//				applicantOfficeSpaceDetails.setOfficeSpace(buyerPreApplicationDto.getOfficeSpace());
//			}
//			
//			if(buyerPreApplicationDto.getUsableArea() == null) {
//				applicantOfficeSpaceDetails.setUsableArea(null);
//			}else {
//				applicantOfficeSpaceDetails.setUsableArea(buyerPreApplicationDto.getUsableArea());
//			}
//			
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
//			LOGGER.info("Applicant Office Space Details Saved...");


            // Applicant Other Business Details

            ApplicantOtherBusinessDetails applicantOtherBusinessDetails = new ApplicantOtherBusinessDetails();
            applicantOtherBusinessDetails.setApplicantID(savedApplicantDetails.getId());
            applicantOtherBusinessDetails.setApplicationAplicantID(savedApplicantApplicationDetails.getId());

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
//			if(buyerPreApplicationDto.getOtherBusinessInSamePremises() == null || "".equals(buyerPreApplicationDto.getOtherBusinessInSamePremises())) {
//				applicantOtherBusinessDetails.setOtherBusinessSamePremises(null);
//			}else {
//				applicantOtherBusinessDetails.setOtherBusinessSamePremises(buyerPreApplicationDto.getOtherBusinessInSamePremises());
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

            if ("Non-Agriculture".equals(buyerPreApplicationDto.getNatureOfBusiness())) {
                applicantOtherBusinessDetails.setNonAgriculturalBusinessName(buyerPreApplicationDto.getNonAgriculturalBusinessName());
            } else {
                applicantOtherBusinessDetails.setNonAgriculturalBusinessName(null);
            }

            applicantOtherBusinessDetails.setCurrencyID(buyerPreApplicationDto.getCurrencyID());
            applicantOtherBusinessDetails.setBusinessTypeID(buyerPreApplicationDto.getBusinessTypeID());
            applicantOtherBusinessDetails.setAverageRevenue(buyerPreApplicationDto.getAverageRevenue());

            applicantOtherBusinessDetailsRepository.save(applicantOtherBusinessDetails);
            LOGGER.info("Applicant Other Business Details Saved...");

            //Sending Mail
            try {
                LOGGER.info("Buyer Pre-Application Registered. Sending Email...");
                String emailResponse = emailServiceUtil.sendMail(buyerPreApplicationDto.getEmailAddress(), buyerPreApplicationDto.getName(), properties.getFromMailId(), properties.getFromMailerName(), properties.getBuyerRegMailTemplateId());
                LOGGER.info("Email Sent Successfully...{}", emailResponse);
            } catch (InvalidDataException ex) {
                LOGGER.error("Error Validating Email...{}", ex.getMessage());
            }

            return responseMessageUtil.sendResponse(true, "Buyer Pre-Application" + APIConstants.RESPONSE_ADD_SUCCESS, "");

        } catch (Exception ex) {
            LOGGER.error(SERVER_ERROR_MSG, ex);
            return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + ex.getMessage());
        }

    }

    public List<TermsAndConditions> getTAndCUrl(Integer platformId) {
        if (platformId != null) {
            List<TermsAndConditions> getData = termsAndConditionsRepository.getTermsConditionUrl(platformId);
            if (getData.isEmpty()) {
                throw new DoesNotExistException("Data not found");
            } else {
                return getData;
            }
        } else {
            throw new DoesNotExistException("PlatformId missing");
        }
    }
}
