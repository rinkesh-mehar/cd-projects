package com.krishi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishi.entity.VarietyQuality;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VarietyQualityModel {

	@JsonProperty
	private Integer ID; 

	@JsonProperty
    private String AczID;

	@JsonProperty
	private Integer VarietyID;
	
	@JsonProperty
	private String CurrentQuality;
	
	@JsonProperty
	private String EstimatedQuality;
	
	@JsonProperty
	private String AllowableVarianceInQuality;
	
	@JsonProperty
	private Integer CurrentQualityID;
	
	@JsonProperty
	private Integer StateCode;
	
	@JsonProperty
	private Integer RegionID;
	
	@JsonProperty
	private Integer CommodityID;
	
	@JsonProperty
	private Integer SowingWeekStart;
	
	@JsonProperty
	private Integer SowingWeekEnd;
    
    @JsonProperty
    private String Status;
	
	public Integer getID() {
		return ID;
	}

	public void setID(Integer ID) {
        ID = ID;
    }

    public String getEstimatedQuality() {
        return EstimatedQuality;
    }

    public void setEstimatedQuality(String estimatedQuality) {
        EstimatedQuality = estimatedQuality;
    }

    public String getAllowableVarianceInQuality() {
        return AllowableVarianceInQuality;
    }

    public void setAllowableVarianceInQuality(String allowableVarianceInQuality) {
        AllowableVarianceInQuality = allowableVarianceInQuality;
    }

    public Integer getSowingWeekStart() {
        return SowingWeekStart;
    }

    public void setSowingWeekStart(Integer sowingWeekStart) {
        SowingWeekStart = sowingWeekStart;
    }

    public Integer getSowingWeekEnd() {
        return SowingWeekEnd;
    }

    public void setSowingWeekEnd(Integer sowingWeekEnd) {
        SowingWeekEnd = sowingWeekEnd;
    }

	public Integer getCurrentQualityID() {
		return CurrentQualityID;
	}

	public void setCurrentQualityID(Integer currentQualityID) {
		CurrentQualityID = currentQualityID;
	}


    public Integer getVarietyID() {
        return VarietyID;
    }

    public void setVarietyID(Integer varietyID) {
        VarietyID = varietyID;
    }

    public String getCurrentQuality() {
        return CurrentQuality;
    }

    public void setCurrentQuality(String currentQuality) {
        CurrentQuality = currentQuality;
    }

    public Integer getStateCode() {
        return StateCode;
    }

    public void setStateCode(Integer stateCode) {
        StateCode = stateCode;
    }

    public Integer getRegionID() {
        return RegionID;
    }

    public void setRegionID(Integer regionID) {
        RegionID = regionID;
    }

    public Integer getCommodityID() {
        return CommodityID;
    }

    public void setCommodityID(Integer commodityID) {
        CommodityID = commodityID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getAczID() {
        return AczID;
    }

    public void setAczID(String aczID) {
        AczID = aczID;
    }

    public VarietyQuality getEntity() {

        VarietyQuality entity = new VarietyQuality();
        entity.setId(ID);
        entity.setAczId(AczID);
        entity.setVarietyId(VarietyID);
        entity.setCurrentQuality(CurrentQuality);
        entity.setEstimatedQuality(EstimatedQuality);
        entity.setAllowableVarianceInQuality(AllowableVarianceInQuality);
        entity.setCurrentQualityId(CurrentQualityID);
        entity.setStateCode(StateCode);
        entity.setRegionId(RegionID);
        entity.setCommodityId(CommodityID);
        entity.setSowingWeekStart(SowingWeekStart);
        entity.setSowingWeekEnd(SowingWeekEnd);
        entity.setStatus(1);
        entity.setStatus(Status != null && Status.equalsIgnoreCase("Active") ? 1 : 0);
        return entity;

    }
    
    
    
}