package in.cropdata.farmers.app.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public interface FarmerAddressBookDTO {

	String getFarmerID();
	
    Integer getRegionID();
    
    Integer getStateCode();
    
    Integer getDistrictCode();
    
    Integer getVillageCode();
    
    String getSubRegionID();

}
