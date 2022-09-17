/**
 * 
 */
package in.cropdata.farmers.app.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.transaction.Synchronization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.cropdata.farmers.app.DTO.FarmerLoginOtpDTO;
import in.cropdata.farmers.app.DTO.FarmerProfileDTO;
import in.cropdata.farmers.app.constants.ErrorConstants;
import in.cropdata.farmers.app.controller.AuthenticationController;
import in.cropdata.farmers.app.drk.model.DrkFarmer;
import in.cropdata.farmers.app.drk.model.FarmerDeviceToken;
import in.cropdata.farmers.app.drk.model.FarmerLoginOtp;
import in.cropdata.farmers.app.drk.repository.DrkFarmerRepository;
import in.cropdata.farmers.app.drk.repository.FarmerDeviceTokenRepository;
import in.cropdata.farmers.app.drk.repository.FarmerLoginOtpRepository;
import in.cropdata.farmers.app.exception.MyExceptionHandler;
import in.cropdata.farmers.app.masters.repository.GeoVillageRepository;
import in.cropdata.farmers.app.model.JwtRequest;
import in.cropdata.farmers.app.model.ResponseMessage;
import in.cropdata.farmers.app.repository.FarmerLocationRepository;
import in.cropdata.farmers.app.repository.FarmerRepository;
import in.cropdata.farmers.app.utils.AESEncryption;
import in.cropdata.farmers.app.utils.AutoMailer;
import in.cropdata.farmers.app.utils.OTPGenerator;
import in.cropdata.farmers.app.utils.ResponseMessageUtil;


/**
 * @author pallavi-waghmare
 *
 */

@Service
public class FarmerLoginService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FarmerLoginService.class);

	@Autowired
	private FarmerLoginOtpRepository loginOtpRepository;
	@Autowired
	private DrkFarmerRepository drkFarmerRepository;
//	@Autowired
//	private FarmerRepository farmerRepository;
	@Autowired
	private OTPGenerator otpGenerator;
	@Autowired
	private AESEncryption aesEncryption;
	@Autowired
	private AutoMailer autoMailer;
	@Autowired
	private AuthenticationController authenticationController;
	@Autowired
	private ResponseMessageUtil responseMessageUtil;
	@Autowired
	private FarmerDeviceTokenRepository deviceTokenRepository;
//	@Autowired
//	private FarmerLocationRepository farmerLocationRepository;
	@Autowired
	private GeoVillageRepository geoVillageRepository;
	@Autowired
	private Environment environment;

	public  Map<String, Object>  getOTP(String mobileNo) throws IOException {
		Map<String, Object> responseMap = new LinkedHashMap<>();
		try {
			if (mobileNo != null && mobileNo != "") {
 
				if (mobileNo.length() != 10) {
					responseMap.put("status", false);
					responseMap.put("errorCode", ErrorConstants.INVALID_MOBILE_NUMBER);
					responseMap.put("message", "Please Enter 10 Digit Mobile Number");

				} else {
					Optional<DrkFarmer> farmerFound = drkFarmerRepository.findAllByPrimaryMobNumber(mobileNo);

					if (farmerFound.isPresent()) {
						Optional<FarmerLoginOtp> fetched_OTP_Detail = loginOtpRepository.findAllByMobileNo(mobileNo);
						String generatedOTP = otpGenerator.generateOTP(6);
						

						long currentTime = System.currentTimeMillis();
						long expiryTime = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5);

						if (fetched_OTP_Detail != null && fetched_OTP_Detail.isPresent()) {
							FarmerLoginOtp user = fetched_OTP_Detail.get();
							user.setOtp(aesEncryption.encrypt(generatedOTP));
							user.setCreatedAt(new Timestamp(currentTime));
							user.setTimeout(new Timestamp(expiryTime));
							loginOtpRepository.save(user);
						} else {
							FarmerLoginOtp farmerLoginOtp = new FarmerLoginOtp();
							farmerLoginOtp.setMobileNo(mobileNo);
							farmerLoginOtp.setOtp(aesEncryption.encrypt(generatedOTP));
							farmerLoginOtp.setCreatedAt(new Timestamp(currentTime));
							farmerLoginOtp.setTimeout(new Timestamp(expiryTime));
							loginOtpRepository.save(farmerLoginOtp);
						}
						LOGGER.info("OTP is {}", generatedOTP);
						
						String[] activeProfiles = environment.getActiveProfiles();
						
//						String profiles = "";
//						for (String profile : activeProfiles) {
////							System.out.print(profile);
//							profiles = profiles + profile;
//						}
						
						String messageId = "";
						
						if("dev".equals(activeProfiles[0])) {
							messageId = "0EP3qBUxVZt";
						}
						
						// Currently uat env is acting as prod env thats why messageId of uat env is same as prod env.
						
						if("uat".equals(activeProfiles[0])) {
							messageId = "plus;oiC4myVymB"; // '+' symbol is replace with 'plus;'
						}
						
						if("prod".equals(activeProfiles[0])) {
							messageId = "plus;oiC4myVymB"; // '+' symbol is replace with 'plus;'
						}
						
						String generated_OTP_message = "Your CropData (Dr.Krishi) one time OTP is: "+generatedOTP+", Message Id: " + messageId;
						// # Your CropData (Dr.Krishi) one time OTP is:
						responseMap.put("status", true);
						responseMap.put("otp", generated_OTP_message);
						responseMap.put("message", "OTP Generated Successfully");
						TimeUnit.SECONDS.sleep(1);
						/** Calling SMS sender for sending OTP */
						autoMailer.sendSMS(mobileNo, generated_OTP_message);
					} else {
						responseMap.put("status", false);
						responseMap.put("errorCode", ErrorConstants.FARMER_NOT_FOUND);
						responseMap.put("message", "Farmer Not Found");
					}
				}

			} else {
				responseMap.put("status", false);
				responseMap.put("errorCode", ErrorConstants.INVALID_MOBILE_NUMBER);
				responseMap.put("message", "Please Enter Mobile Number");
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error while getting OTP -> {} ", e.getMessage());
		}
		return responseMap;
	}
	
	 public String encode(String url)  
	    {  
	              try {  
	                   String encodeURL=URLEncoder.encode( url, "UTF-8" );  
	                   return encodeURL;  
	              } catch (UnsupportedEncodingException e) {  
	                   return "Issue while encoding" +e.getMessage();  
	              }  
	    }

	public  Map<String, Object> validateOTP(FarmerLoginOtp details) throws Exception {

		Map<String, Object> responseMap = new LinkedHashMap<>();
		try {
			Optional<FarmerLoginOtp> fetched_OTP_Detail = loginOtpRepository.findAllByMobileNo(details.getMobileNo());
			if (fetched_OTP_Detail != null && fetched_OTP_Detail.isPresent()) {
				if (!aesEncryption.decrypt(fetched_OTP_Detail.get().getOtp()).equals(details.getOtp())) {
					responseMap.put("status", false);
					responseMap.put("message", "Invalid OTP.");
					responseMap.put("errorCode", ErrorConstants.INVALID_OTP);
				}else {
					FarmerLoginOtpDTO farmerLoginDto = loginOtpRepository.verifyOtp(details.getMobileNo(),aesEncryption.encrypt(details.getOtp()), new Timestamp(System.currentTimeMillis()));
					if (farmerLoginDto != null) {
						JwtRequest jwtRequest = new JwtRequest();
						jwtRequest.setMobile(fetched_OTP_Detail.get().getMobileNo());
						/** Generating JWT token against farmer mobile no */
						final Map<String, Object> authenticationToken = authenticationController
								.createAuthenticationToken(jwtRequest);
						
						Optional<DrkFarmer> drkFarmer = drkFarmerRepository.findAllByPrimaryMobNumber(details.getMobileNo());
						
 
						if (drkFarmer.isPresent())
						{
							DrkFarmer fd =(DrkFarmer) authenticationToken.get("user");
							Map<String,Object> map = new HashMap<>();
							if(fd.getAddressId() !=null) {
								  map = drkFarmerRepository.getFarmerStateAndDistrictCodeForfarmerApp(fd.getId());
							}else {
								map = geoVillageRepository.getFarmerStateAndDistrictCodeForDrkrishi(fd.getVillageCode());
							}
							responseMap.put("status", true);
							responseMap.put("message", "Successfully Fetched Details of User");
							responseMap.put("authToken", fd.getAuthToken());
							responseMap.put("farmerName", fd.getFarmerName());
							responseMap.put("stateCode", map.get("StateCode"));
							responseMap.put("districtCode", map.get("DistrictCode"));
							responseMap.put("farmSize", fd.getFarmSize());
						}
					}else {
						responseMap.put("status", false);
						responseMap.put("message", "OTP Is Expired");
						responseMap.put("errorCode", ErrorConstants.OTP_EXPIRED);
					}
				}
			} else {
				responseMap.put("status", false);
				responseMap.put("message", "Invalid Mobile Number OR Contact Admin");
				responseMap.put("errorCode", ErrorConstants.INVALID_MOBILE_NUMBER);
			}

		} catch (Exception ex) {
			LOGGER.error("Error while validate OTP -> {}", ex.getMessage());
		}
		return responseMap;
	}

	public ResponseMessage updateAuthToken(String token, String mobile) {
		try {
			Optional<DrkFarmer> foundDrkFarmerDetail = this.drkFarmerRepository.findAllByPrimaryMobNumber(mobile);
			  if(foundDrkFarmerDetail.isPresent()){
				
				foundDrkFarmerDetail.get().setAuthToken(token);
				LOGGER.info("authenticate from android " + foundDrkFarmerDetail);

				this.drkFarmerRepository.save(foundDrkFarmerDetail.get());

				return responseMessageUtil.sendResponse(true, "", "");
			}else {
				return responseMessageUtil.sendResponse(false, "", "No Data Found, Mobile No. Does Not Exist");
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error: " + e.getMessage());
		}
	}

	public Map<String, Object> saveDeviceToken(String deviceToken, String authToken) {
		Map<String, Object> response = new LinkedHashMap<>();
		FarmerDeviceToken farmerDeviceToken = new FarmerDeviceToken();
		Optional<DrkFarmer> farmerFound = drkFarmerRepository.findByAuthToken(authToken);
		if(farmerFound.isPresent()) {
			DrkFarmer farmer = farmerFound.get();
			Optional<FarmerDeviceToken> checkExisting = deviceTokenRepository.checkExistingDeviceToken(farmer.getId(),deviceToken);
			if (!checkExisting.isPresent()) {
				farmerDeviceToken.setFarmerID(farmer.getId());
				farmerDeviceToken.setDeviceToken(deviceToken);
				deviceTokenRepository.save(farmerDeviceToken);
			}
			response.put("status", true);
			response.put("message", "Device token saved successfully");
			
		}else {
			response.put("status", false);
			response.put("errorCode", ErrorConstants.UNAUTHORIZED);
			response.put("message", "Unauthorized User");
		}
		return response;
	}
 
	public  Map<String, Object> farmerLogout(String authorization)  {
		Map<String, Object> responseMessage = new LinkedHashMap<>();
		try {
			Optional<DrkFarmer> farmerFound = drkFarmerRepository.findByAuthToken(authorization);
			if (farmerFound.isPresent()) {
				DrkFarmer farmer = farmerFound.get();
				farmer.setAuthToken(null);
				drkFarmerRepository.save(farmer);
				Optional<List<FarmerDeviceToken>> checkEntry = deviceTokenRepository.findByFarmerID(farmer.getId());
				if (checkEntry.isPresent()) {
					List<FarmerDeviceToken> delTokenList = checkEntry.get();
					delTokenList.forEach(t -> deviceTokenRepository.deleteByFarmerID(t.getFarmerID()));
				}
				responseMessage.put("status", true);
				responseMessage.put("message", "Logged Out Successfully.");
			}else {
				responseMessage.put("status", false);
				responseMessage.put("errorCode", ErrorConstants.UNAUTHORIZED);
				responseMessage.put("message", "Unauthorized User");
			}
		} catch (Exception e) {
			throw new MyExceptionHandler(ErrorConstants.LOGOUT_FAILS,"Failed to Logout" ,false);
		}
		return responseMessage;
	}

 

	public Map<String, Object> getFarmerProfile(String authToken) {
		Map<String, Object> responseMessage = new HashMap<>();
		Optional<DrkFarmer> farmerFound = drkFarmerRepository.findByAuthToken(authToken);
		Optional<FarmerProfileDTO> profileDTO = null;
		if(farmerFound.isPresent()) {
			DrkFarmer farmer = farmerFound.get();
			if(farmer.getAddressId() != null) {
				profileDTO = drkFarmerRepository.getFarmerProfileInfoFarmerApp(farmer.getId());
			}else {
				profileDTO = drkFarmerRepository.getFarmerProfileInfoDrkrishi(farmer.getId());
			}
			Map<String, Object> profileDetails = new HashMap<>();
			Map<String, Object> farmerAddress = new HashMap<>();
			List<String> kycDocs = new ArrayList<>();
			farmerAddress.put("villageName",Objects.isNull(profileDTO.get().getVillageName()) ? "" : profileDTO.get().getVillageName());
			farmerAddress.put("villageCode",Objects.isNull(profileDTO.get().getVillageCode()) ? 0 : profileDTO.get().getVillageCode());
			farmerAddress.put("panchayatName",Objects.isNull(profileDTO.get().getPanchayatName()) ? "" : profileDTO.get().getPanchayatName());
			farmerAddress.put("panchayatCode",Objects.isNull(profileDTO.get().getPanchayatCode()) ? 0 : profileDTO.get().getPanchayatCode());
			farmerAddress.put("tehsilName",Objects.isNull(profileDTO.get().getTehsilName()) ? "" : profileDTO.get().getTehsilName());
			farmerAddress.put("tehsilCode",Objects.isNull(profileDTO.get().getTehsilCode()) ? 0 : profileDTO.get().getTehsilCode());
			farmerAddress.put("districtName",Objects.isNull(profileDTO.get().getDistrictName()) ? "" : profileDTO.get().getDistrictName());
			farmerAddress.put("districtCode",Objects.isNull(profileDTO.get().getDistrictCode()) ? 0 : profileDTO.get().getDistrictCode());
			farmerAddress.put("stateName",Objects.isNull(profileDTO.get().getStateName()) ? "" : profileDTO.get().getStateName());
			farmerAddress.put("stateCode",Objects.isNull(profileDTO.get().getStateCode()) ? 0 : profileDTO.get().getStateCode());
			farmerAddress.put("cityCode",Objects.isNull(profileDTO.get().getCityCode()) ? 0 : profileDTO.get().getCityCode());
			farmerAddress.put("cityName",Objects.isNull(profileDTO.get().getCityName()) ? "" : profileDTO.get().getCityName());
			farmerAddress.put("pincode",Objects.isNull(profileDTO.get().getPincode()) ? "" : profileDTO.get().getPincode());
			farmerAddress.put("addressLine1",Objects.isNull(profileDTO.get().getAddressLine1()) ? "" : profileDTO.get().getAddressLine1());
			farmerAddress.put("addressLine2",Objects.isNull(profileDTO.get().getAddressLine2()) ? "" : profileDTO.get().getAddressLine2());
			farmerAddress.put("farmSize",Objects.isNull(profileDTO.get().getFarmSize()) ? 0.0d : profileDTO.get().getFarmSize());
			try {
				if (!Objects.isNull(profileDTO.get().getDocUrl())) {

					List<String> docURL = new ArrayList<>(Arrays.asList(profileDTO.get().getDocUrl().split(",")));
					for (int i = 0; i < docURL.size(); i++) {
						kycDocs.add(docURL.get(i)); 
					 }
				}
			} catch (Exception e) {
				 e.printStackTrace();
			}
			responseMessage.put("status", true);
			responseMessage.put("message", "Profile Details Delivered Successfully");
			profileDetails.put("farmerName", profileDTO.get().getFarmername());
			profileDetails.put("farmerPrimaryMobileNo", profileDTO.get().getFarmerPrimaryMobileno());
			profileDetails.put("farmerAddress", farmerAddress);
			profileDetails.put("kycDocs", kycDocs);
			responseMessage.put("profileDetails", profileDetails);
		}else{
			responseMessage.put("status", false);
			responseMessage.put("message", "Unauthorized User");
			responseMessage.put("errorCode", ErrorConstants.UNAUTHORIZED);
		}
		return responseMessage;
	}

}
