/**
 * 
 */
package in.cropdata.farmers.app.DTO;

import javax.persistence.Id;

import lombok.Data;

/**
 * @author cropdata-Aniket Naik
 *
 */
@Data
public class FarmerRegistrationDTO {

	@Id
	private String id;

	private String farmerName;

	private String primaryMobNumber;

	private Integer stateCode; //compulsory

	private Integer districtCode; //compulsory

	private Integer cityCode;

	private Integer tehsilCode;

	private Integer panchayatCode;

	private Integer villageCode;

	private Integer pincode;

	private String addressLine1;

	private String addressLine2;
	
	private Double farmSize; 
	
	private Double longitude;
	
	private Double latitude;

}
