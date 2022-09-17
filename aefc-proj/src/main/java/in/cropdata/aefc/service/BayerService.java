package in.cropdata.aefc.service;

import in.cropdata.aefc.DTO.BayerLoginOtpDTO;
import in.cropdata.aefc.constants.APIConstants;
import in.cropdata.aefc.constants.ErrorConstants;
import in.cropdata.aefc.controller.AuthenticationController;
import in.cropdata.aefc.entity.ApplicantApplicationDetail;
import in.cropdata.aefc.entity.ApplicantDetails;
import in.cropdata.aefc.entity.ApplicantOtherBusinessDetails;
import in.cropdata.aefc.entity.JwtRequest;
import in.cropdata.aefc.model.RegisterBuyer;
import in.cropdata.aefc.properties.AppProperties;
import in.cropdata.aefc.repository.ApplicantApplicationDetailRepository;
import in.cropdata.aefc.repository.ApplicantDetailsRepository;
import in.cropdata.aefc.repository.ApplicantOtherBusinessDetailsRepository;
import in.cropdata.aefc.util.AutoMailer;
import in.cropdata.aefc.util.CryptoUtils;
import in.cropdata.aefc.util.EmailServiceUtil;
import in.cropdata.aefc.util.OTPGenerator;
import in.cropdata.aefc.utils.AESEncryption;
import in.cropdata.aefc.utils.ResponseMessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.aefc.aefc.service
 * @date 05/12/21
 * @time 6:01 PM
 */
@Service
public class BayerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BayerService.class);

    @Autowired
    private AESEncryption aesEncryption;

    @Autowired
    private AuthenticationController authenticationController;

    @Autowired
    private ApplicantApplicationDetailRepository applicantApplicationDetailRepository;

    @Autowired
    private ApplicantDetailsRepository applicantDetailsRepository;

    @Autowired
    private ApplicantOtherBusinessDetailsRepository applicantOtherBusinessDetailsRepository;

    @Autowired
    private EmailServiceUtil emailServiceUtil;

    @Autowired
    private ResponseMessageUtil responseMessageUtil;

    @Autowired
    private AutoMailer autoMailer;

    @Autowired
    private OTPGenerator otpGenerator;

    @Autowired
    private AppProperties appProperties;

    @Transactional
    public Map<String, Object> registerBuyer(RegisterBuyer registerBuyer) {
        Map<String, Object> responseMap = new HashMap<>();
        Optional<ApplicantDetails> fetchedApplicantByMob = applicantDetailsRepository.findAllByMobileNumber(registerBuyer.getMobileNumber());

        if(fetchedApplicantByMob.isPresent()) {
            responseMap.put("message", APIConstants.BUYER_ALREADY_REGISTERED);
            responseMap.put("status", false);
            return responseMap;
        }

        /**
         * @apiNote saving details after checking
         * @since 1.0
         */

        try {
            String generatedOTP = otpGenerator.generateOTP(4);

            String encryptedString = CryptoUtils.encrypt(registerBuyer.getMobileNumber()+registerBuyer.getEmailAddress());
            ApplicantDetails applicantDetails = new ApplicantDetails();
            applicantDetails.setMobileNumber(registerBuyer.getMobileNumber());
            applicantDetails.setEmailAddress(registerBuyer.getEmailAddress());
            applicantDetails.setIsEmailVerified("No");
            applicantDetails.setIsMobileVerified("No");
            applicantDetails.setOtp(aesEncryption.encrypt(generatedOTP));
            long expiryTime = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(appProperties.getOtpExpiryTime());
            applicantDetails.setTimeout(new Timestamp(expiryTime));
            applicantDetails.setEmailVerificationLink(encryptedString);
            ApplicantDetails savedApplicantDetails = applicantDetailsRepository.save(applicantDetails);

            ApplicantApplicationDetail applicantApplicationDetail = new ApplicantApplicationDetail();
            applicantApplicationDetail.setCompanyName(registerBuyer.getCompanyName());
            applicantApplicationDetail.setApplicantID(savedApplicantDetails.getID());
            ApplicantApplicationDetail savedApplicationApplicantDetails = applicantApplicationDetailRepository.save(applicantApplicationDetail);

            ApplicantOtherBusinessDetails applicantOtherBusinessDetails = new ApplicantOtherBusinessDetails();
            applicantOtherBusinessDetails.setNatureOfBusiness(registerBuyer.getNatureOfBusiness());
            applicantOtherBusinessDetails.setApplicantID(savedApplicantDetails.getID());
            applicantOtherBusinessDetails.setApplicationAplicantID(savedApplicationApplicantDetails.getId());
            ApplicantOtherBusinessDetails savedApplicantOtherBusinessDetails = applicantOtherBusinessDetailsRepository.save(applicantOtherBusinessDetails);

            String generated_OTP_message = "Thanks for showing interest in CropData. OTP for mobile verification is " + generatedOTP + " valid for 15 minutes.";

            autoMailer.sendSMS(registerBuyer.getMobileNumber(), generated_OTP_message);

            String redirectUrl = appProperties.getBuyerRegistrationUrl()
                    + encryptedString + "&mob=" + registerBuyer.getMobileNumber() +
                    "&email=" + registerBuyer.getEmailAddress();

            String mailResponse = emailServiceUtil.sendMail(registerBuyer.getEmailAddress(), "", appProperties.getFromMailId(),
                    appProperties.getFromMailerName(), appProperties.getMailTemplateId(), redirectUrl);

            LOGGER.info("sent email response {}", mailResponse);

            responseMap.put("message", "Please Verify Email and Mobile Number");
            responseMap.put("status", true);
            return responseMap;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public Map<String, Object> resendEmail(String emailAddress,String mobileNumber){

        ApplicantDetails byMobileNumberAndEmailAddress = applicantDetailsRepository.findByMobileNumberAndEmailAddress(mobileNumber, emailAddress);
        Map<String, Object> responseMap = new HashMap<>();
        if(byMobileNumberAndEmailAddress!=null){
            String encryptedString = CryptoUtils.encrypt(emailAddress+mobileNumber);
            byMobileNumberAndEmailAddress.setID(byMobileNumberAndEmailAddress.getID());
            byMobileNumberAndEmailAddress.setEmailVerificationLink(encryptedString);
            ApplicantDetails savedApplicantDetails = applicantDetailsRepository.save(byMobileNumberAndEmailAddress);
            String redirectUrl = appProperties.getBuyerRegistrationUrl()
                    + encryptedString + "&mob=" + mobileNumber +
                    "&email=" + emailAddress;

            String mailResponse = emailServiceUtil.sendMail(emailAddress, "", appProperties.getFromMailId(),
                    appProperties.getFromMailerName(), appProperties.getMailTemplateId(), redirectUrl);

            LOGGER.info("sent email response {}", mailResponse);
            responseMap.put("status", true);
            responseMap.put("message", APIConstants.RESEND_REQUEST);
            return responseMap;
        }else{
            responseMap.put("status", false);
            responseMap.put("message", APIConstants.UNAUTHORIZED_USER);
            return responseMap;
        }
    }

    public Map<String, Object> verifyEmail(String code) {
        HashMap<String, Object> responseMap = new HashMap<>();
/*
        String userEmailDecrypted = CryptoUtils.decrypt(userEmail);
        LOGGER.info("Getting User Info for -> {}", userEmail);

        Integer userIdDecrypted = Integer.parseInt(userId);*/
        Optional<ApplicantDetails> fetchedApplicantDetailsById = applicantDetailsRepository.findByEmailVerificationLink(code);

        if (fetchedApplicantDetailsById.isPresent()) {

            if (fetchedApplicantDetailsById.get().getIsEmailVerified().equalsIgnoreCase("Yes")) {
                responseMap.put("status", true);
                responseMap.put("message", APIConstants.EMAIL_ALREADY_VERIFIED);
                return responseMap;
            }

            Integer applicantDetails = applicantDetailsRepository.updateEmailStatus(fetchedApplicantDetailsById.get().getEmailAddress());

            responseMap.put("status", true);
            responseMap.put("message", APIConstants.EMAIL_VERIFICATION_SUCCESSFUL);
            return responseMap;

        } else {
            responseMap.put("status", false);
            responseMap.put("message", APIConstants.UNAUTHORIZED_USER);
            return responseMap;
        }
    }

    public Map<String, Object> validateOTP(ApplicantDetails details) throws Exception {

        aesEncryption.encrypt(details.getOtp());
        Map<String, Object> responseMap = new LinkedHashMap<>();
        if (details.getOtp() == null || details.getOtp().equals(""))
        {
            return this.getOTP(details.getMobileNumber());
        } else
        {
            try
            {
                Optional<ApplicantDetails> fetched_OTP_Detail = applicantDetailsRepository.findAllByMobileNumber(details.getMobileNumber());
                if (fetched_OTP_Detail != null && fetched_OTP_Detail.isPresent())
                {
                    if (!aesEncryption.decrypt(fetched_OTP_Detail.get().getOtp()).equals(details.getOtp()))
                    {
                        responseMap.put("status", false);
                        responseMap.put("message", "Invalid OTP.");
                        responseMap.put("errorCode", ErrorConstants.INVALID_OTP);
                    } else
                    {
                        BayerLoginOtpDTO farmerLoginDto = applicantDetailsRepository.verifyOtp(details.getMobileNumber(), aesEncryption.encrypt(details.getOtp()), appProperties.getOtpExpiryTime());
                        if (farmerLoginDto != null)
                        {
                            fetched_OTP_Detail.get().setIsMobileVerified("Yes");
                            applicantDetailsRepository.save(fetched_OTP_Detail.get());
                            if (farmerLoginDto.getIsEmailVerified().equals("No"))
                            {
                                responseMap.put("status", true);
                                responseMap.put("IsEmailVerified", "No");
                                responseMap.put("EmailId",fetched_OTP_Detail.get().getEmailAddress());
                                responseMap.put("IsMobileVerified", fetched_OTP_Detail.get().getIsMobileVerified());
//                                responseMap.put("errorCode", ErrorConstants.USER_NOT_VERIFIED);
                            } else
                            {
                                JwtRequest jwtRequest = new JwtRequest();
                                jwtRequest.setMobile(fetched_OTP_Detail.get().getMobileNumber());
                                /** Generating JWT token against farmer mobile no */
                                final Map<String, Object> authenticationToken = authenticationController
                                        .createAuthenticationToken(jwtRequest);

                                ApplicantDetails bayerDetails = (ApplicantDetails) authenticationToken.get("user");
                                Map<String, Object> map = new HashMap<>();


                                responseMap.put("status", true);
                                responseMap.put("message", "Successfully Generate Auth Token");
                                responseMap.put("authToken", bayerDetails.getAuthToken());
//                            responseMap.put("bayerName", bayerDetails.getFirstName() + " " + bayerDetails.getLastName());
                            }
                        } else
                        {
                            responseMap.put("status", false);
                            responseMap.put("message", "OTP Is Expired");
                            responseMap.put("errorCode", ErrorConstants.OTP_EXPIRED);
                        }
                    }
                } else
                {
                    responseMap.put("status", false);
                    responseMap.put("message", "Invalid Mobile Number OR Contact Admin");
                    responseMap.put("errorCode", ErrorConstants.INVALID_MOBILE_NUMBER);
                }

            } catch (Exception ex)
            {
                LOGGER.error("Error while validate OTP -> {}", ex.getMessage());
            }
        }
        return responseMap;
    }

    public Map<String, Object> getOTP(String mobileNo) throws IOException
    {
        Map<String, Object> responseMap = new LinkedHashMap<>();
        try
        {
            if (mobileNo != null && mobileNo != "")
            {

                if (mobileNo.length() != 10)
                {
                    responseMap.put("status", false);
                    responseMap.put("errorCode", ErrorConstants.INVALID_MOBILE_NUMBER);
                    responseMap.put("message", "Please Enter 10 Digit Mobile Number");

                } else
                {
                    Optional<ApplicantDetails> farmerFound = applicantDetailsRepository.findAllByMobileNumber(mobileNo);

                    if (farmerFound.isPresent())
                    {
                        Optional<ApplicantDetails> fetched_OTP_Detail = applicantDetailsRepository.findAllByMobileNumber(mobileNo);
                        String generatedOTP = otpGenerator.generateOTP(4);


                        long expiryTime = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(appProperties.getOtpExpiryTime());

                        if (fetched_OTP_Detail != null && fetched_OTP_Detail.isPresent())
                        {
                            ApplicantDetails user = fetched_OTP_Detail.get();
                            user.setOtp(aesEncryption.encrypt(generatedOTP));
//                            user.setCreatedAt(new Timestamp(currentTime));
                            user.setTimeout(new Timestamp(expiryTime));
//                            user.setIsMobileVerified("Yes");
                            applicantDetailsRepository.save(user);
                        }
                        /*else
                        {
                            ApplicantDetails userOtp = new ApplicantDetails();
                            userOtp.setMobileNumber(mobileNo);
                            userOtp.setOtp(aesEncryption.encrypt(generatedOTP));
//                            userOtp.setCreatedAt(new Timestamp(currentTime));
                            userOtp.setTimeout(new Timestamp(expiryTime));
                            applicantDetailsRepository.save(userOtp);
                        }*/
                        LOGGER.info("OTP is {}", generatedOTP);
                        String generated_OTP_message = "Thanks for showing interest in CropData. OTP for mobile verification is " + generatedOTP + " valid for 15 minutes";
                        // # Your CropData (Dr.Krishi) one time OTP is:
                        responseMap.put("status", true);
//                        responseMap.put("otp", generated_OTP_message);
                        responseMap.put("message", "OTP Generated Successfully");
                        responseMap.put("expiredTime", "5:00");
                        TimeUnit.SECONDS.sleep(1);
                        /** Calling SMS sender for sending OTP */
                        autoMailer.sendSMS(mobileNo, generated_OTP_message);
                    } else
                    {
                        responseMap.put("status", false);
                        responseMap.put("errorCode", ErrorConstants.USER_NOT_FOUND);
                        responseMap.put("message", "You are not registered with CropData.\nPlease register yourself");
                    }
                }

            } else
            {
                responseMap.put("status", false);
                responseMap.put("errorCode", ErrorConstants.INVALID_MOBILE_NUMBER);
                responseMap.put("message", "Please Enter Mobile Number");
            }

        } catch (Exception e)
        {
            e.printStackTrace();
            LOGGER.error("Error while getting OTP -> {} ", e.getMessage());
        }
        return responseMap;
    }

    public Map<String, Object> verifiedUserDetails(String loginMobileNo)
    {
        Map<String, Object> responseMap = new LinkedHashMap<>();
        try
        {
            Optional<ApplicantDetails> userDetails = applicantDetailsRepository.findAllByMobileNumber(loginMobileNo);
            if (userDetails.isPresent())
            {
                responseMap.put("IsEmailVerified", userDetails.get().getIsEmailVerified().equals("Yes") ? "Yes" : "No");
                responseMap.put("IsMobileVerified", userDetails.get().getIsMobileVerified().equals("Yes") ? "Yes" : "No");
                responseMap.put("EmailId", userDetails.get().getEmailAddress());
            } else
            {
                responseMap.put("status", false);
                responseMap.put("errorCode", ErrorConstants.USER_NOT_FOUND);
                responseMap.put("message", "You are not registered with CropData.\nPlease register yourself");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            LOGGER.error("Error while getting OTP -> {} ", e.getMessage());
        }
        return responseMap;
    }

}
