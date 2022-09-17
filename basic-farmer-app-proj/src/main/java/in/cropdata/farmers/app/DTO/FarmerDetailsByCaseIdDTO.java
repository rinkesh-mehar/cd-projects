package in.cropdata.farmers.app.DTO;

import org.springframework.stereotype.Component;

@Component
public interface FarmerDetailsByCaseIdDTO {
	
	
	String getFarmerId();
	
	Integer getVillageId();
	
	Integer getGtTypeId();
	
	String getRoleName();
	
	String getGtLevel();

}
