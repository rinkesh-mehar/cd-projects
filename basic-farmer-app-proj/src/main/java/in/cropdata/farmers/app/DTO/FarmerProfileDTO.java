package in.cropdata.farmers.app.DTO;

import org.springframework.stereotype.Component;

@Component
public interface FarmerProfileDTO {
	
	String getFarmername();
	
	String getFarmerId();
	
	String getFarmerPrimaryMobileno();
	
	Integer getVillageCode();
	
	String getVillageName();
	
	Integer getPanchayatCode();
	
	String getPanchayatName();
	
	Integer getTehsilCode();
	
	String getTehsilName();
	
	Integer getDistrictCode();
	
	String getDistrictName();
	
	Integer getStateCode();
	
	String getStateName();
	
	String getDocTypeId();
	
	String getDocUrl();
	
	String getDocTypeName();
	
	Integer getCityCode();
	
	String getCityName();
	
	Integer getPincode();
	
	String getAddressLine1();
	
	String getAddressLine2();
	
	Double getFarmSize();

}
