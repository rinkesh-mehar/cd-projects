package in.cropdata.cdtmasterdata.dto.interfaces;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public interface AgriVarietyQualityInfDto {

	 Integer getID();

	 Integer getStatsCode();

	 String getState();

	 Integer getStateCode();

	 Integer getRegionId();

	 String getRegion();

	 Integer getCommodityId();

	 String getCommodity();

	 Integer getVarietyId();

	 String getVariety();

	 String getCurrentQuality();

	 String getEstimatedQuality();

//	 float getAllowableVarianceInQualityGradePositive();
//
//	 float getAllowableVarianceInQualityGradeNegative();

	 String getAllowableVarianceInQuality();

	 String getStatus();

	 String getCurrentQualityBandID();

	 String getEstimatedQualityBandID();

	 String getAllowableVarianceInQualityBandID();

	 String getEstimatedQualityBandName();

	 String getCurrentQualityBandName();

	 String getAllowableVarianceInQualityName();

	 Boolean getIsValid();

	String getErrorMessage();
}
