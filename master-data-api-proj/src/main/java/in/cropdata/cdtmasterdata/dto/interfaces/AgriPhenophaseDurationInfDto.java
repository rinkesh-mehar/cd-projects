package in.cropdata.cdtmasterdata.dto.interfaces;

import java.util.Date;

public interface AgriPhenophaseDurationInfDto {

	 Integer getId();

	 Integer getCommodityId();

	 Integer getStateCode();

	 Integer getPhenophaseId();

	 Integer getSeasonId();

	 Integer getVarietyId();

	 Integer getPhenophaseStart();

	 Integer getPhenophaseEnd();

	 String getCommodity();

	 String getName();

	 String getState();

	 String getSeason();

	 String getVariety();

	 String getPhenophase();

	 Date getUpdatedAt();

	 Date getCreatedAt();

	 String getImageID();
	
	 String getImageURL();

	 String getStatus();

	 String getSpecificationUpper();

	 String getSpecificationLower();

	 String getSpecificationAverage();

	 Integer getWeatherParameterID();

	 String getFavourableWeatherParamName();

	 String getWeatherLabel();

	 Boolean getIsValid();

	String getErrorMessage();

}
