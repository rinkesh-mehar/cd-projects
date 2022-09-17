package in.cropdata.farmers.app.DTO;

import java.sql.Timestamp;

public interface FarmerLoginOtpDTO {
	
	Integer getID();
    String getPrimaryMobileNumber();
    Integer getOtp();
    Timestamp getCreatedAt();

}
