package com.krishi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishi.entity.PhenophaseDuration;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PhenophaseDurationMasterDataModel {

	@JsonProperty
	private Integer ID;
	@JsonProperty
	private Integer StateCode;
//	@JsonProperty
//	private Integer SeasonID;
	@JsonProperty
	private Integer CommodityID;
	@JsonProperty
	private Integer VarietyID;
	@JsonProperty
	private Integer PhenophaseID;
	@JsonProperty
	private Integer StartDas;
	@JsonProperty
	private Integer EndDas;
	@JsonProperty
	private String Status;
	
	/** after acz introduce -CDT-Ujwal */
	@JsonProperty
	private Integer AczID;
	@JsonProperty
	private Integer SowingWeekStart;
	@JsonProperty
	private Integer SowingWeekEnd;
	@JsonProperty
	private Integer HarvestWeekStart;
	@JsonProperty
	private Integer HarvestWeekEnd;

	@JsonProperty
	private Integer PhenophaseOrder;

	@JsonProperty
	private Integer NoOfDaysForHarvestMonitoring;

	public Integer getPhenophaseOrder() {
		return PhenophaseOrder;
	}

	public void setPhenophaseOrder(Integer phenophaseOrder) {
		PhenophaseOrder = phenophaseOrder;
	}

	public Integer getNoOfDaysForHarvestMonitoring() {
		return NoOfDaysForHarvestMonitoring;
	}

	public void setNoOfDaysForHarvestMonitoring(Integer noOfDaysForHarvestMonitoring) {
		NoOfDaysForHarvestMonitoring = noOfDaysForHarvestMonitoring;
	}

	public Integer getAczID() {
		return AczID;
	}

	public void setAczID(Integer aczID) {
		AczID = aczID;
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

	public Integer getHarvestWeekStart() {
		return HarvestWeekStart;
	}

	public void setHarvestWeekStart(Integer harvestWeekStart) {
		HarvestWeekStart = harvestWeekStart;
	}

	public Integer getHarvestWeekEnd() {
		return HarvestWeekEnd;
	}

	public void setHarvestWeekEnd(Integer harvestWeekEnd) {
		HarvestWeekEnd = harvestWeekEnd;
	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public Integer getStateCode() {
		return StateCode;
	}

	public void setStateCode(Integer stateCode) {
		StateCode = stateCode;
	}
	
//	public Integer getSeasonID() {
//		return SeasonID;
//	}
//
//	public void setSeasonID(Integer seasonID) {
//		SeasonID = seasonID;
//	}

	public Integer getCommodityID() {
		return CommodityID;
	}

	public void setCommodityID(Integer commodityID) {
		CommodityID = commodityID;
	}

	public Integer getVarietyID() {
		return VarietyID;
	}

	public void setVarietyID(Integer varietyID) {
		VarietyID = varietyID;
	}

	public Integer getPhenophaseID() {
		return PhenophaseID;
	}

	public void setPhenophaseID(Integer phenophaseID) {
		PhenophaseID = phenophaseID;
	}

	public Integer getStartDas() {
		return StartDas;
	}

	public void setStartDas(Integer startDas) {
		StartDas = startDas;
	}

	public Integer getEndDas() {
		return EndDas;
	}

	public void setEndDas(Integer endDas) {
		EndDas = endDas;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public PhenophaseDuration getEntity() {

		PhenophaseDuration phenophaseDuration = new PhenophaseDuration();

		phenophaseDuration.setId(ID);
		phenophaseDuration.setEndDas(EndDas);
		phenophaseDuration.setPhenophaseId(PhenophaseID);
		phenophaseDuration.setStartDas(StartDas);
//		phenophaseDuration.setSeasonId(SeasonID);
		phenophaseDuration.setCommodityId(CommodityID);
		phenophaseDuration.setStateId(StateCode);
		phenophaseDuration.setVarietyId(VarietyID);
		phenophaseDuration.setStatus(Status != null && Status.equalsIgnoreCase("Active") ? 1 : 0);
		phenophaseDuration.setAczId(AczID);
		phenophaseDuration.setSowingWeekStart(SowingWeekStart);
		phenophaseDuration.setSowingWeekend(SowingWeekEnd);
		phenophaseDuration.setHarvestWeekStart(HarvestWeekStart);
		phenophaseDuration.setHarvestWeekEnd(HarvestWeekEnd);
		phenophaseDuration.setPhenophaseOrder(PhenophaseOrder);
		phenophaseDuration.setNoOfDaysForHarvestMonitoring(NoOfDaysForHarvestMonitoring);

		return phenophaseDuration;

	}

}
