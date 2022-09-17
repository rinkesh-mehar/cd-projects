package in.cropdata.portal.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

/**
 * Used for getting RL User Data.
 * 
 * @author PranaySK
 * @Date 03-09-2020
 */

@Data
public class RlUserInfoDTO {
	/** User Information */
	private String userId;
	private String firstName;
	private String lastName;
	private String mobileNo;
	private String aadharNo;
	private String panNo;
	private String agreementAccepted;
	private String bankName;
	private String accountNo;
	private String ifscCode;
	/** User Images */
	private MultipartFile profileImage;
	private MultipartFile aadharImage;
	private MultipartFile panCardImage;
	private MultipartFile dlImage;
	private MultipartFile accountImage;
}
