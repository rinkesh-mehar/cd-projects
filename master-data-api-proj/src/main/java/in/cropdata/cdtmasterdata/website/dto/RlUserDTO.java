package in.cropdata.cdtmasterdata.website.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.cdtmasterdata.website.dto
 * @date 21/09/20
 * @time 12:17 PM
 */
@Data
public class RlUserDTO {
	private String data;

	private String bankDetails;

	private MultipartFile userProfileImage;

	private MultipartFile userAadharImage;

	private MultipartFile userPanImage;

	private MultipartFile userDLImage;

	private MultipartFile accountImage;
}
