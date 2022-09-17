package in.cropdata.farmers.app.masters.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface CityVillagePinDTO {

	@JsonProperty("ID")
	Integer getId();

	String getName();

	String getType();

	Integer getDistrictCode();

	String getDistrictName();

	Integer getCityCode();

	Integer getVillageCode();

	String getSearchText();

	Integer getStateCode();

	String getStateName();

	Integer getPanchayatCode();

	String getPanchayatName();

	String getVillageName();
	
	Integer getTehsilCode();

	String getTeshilName();

}
