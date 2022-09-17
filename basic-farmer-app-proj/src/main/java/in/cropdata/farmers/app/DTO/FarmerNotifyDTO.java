package in.cropdata.farmers.app.DTO;

import javax.persistence.Id;

public interface FarmerNotifyDTO {
	
	@Id
	 String getCaseId();
	
	 String getRunningText();

	 String getCommodityName();

	 String getCommodityPhoto();
	
	 String getDeviceToken();

}
