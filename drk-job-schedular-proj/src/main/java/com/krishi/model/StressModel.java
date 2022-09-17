package com.krishi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishi.entity.Stress;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StressModel {
	
	@JsonProperty
	private Integer ID;
	
	@JsonProperty
	private Integer StressID;
	
	@JsonProperty
	private Integer CommodityID;
	
	@JsonProperty
	private Integer StressTypeID;
	
	@JsonProperty
	private Integer StartDas;
	
	@JsonProperty
	private Integer EndDas;
	
	@JsonProperty
	private String Name;
	
//	@JsonProperty
//	private String ScientificName;
	
	@JsonProperty
	private String Status;
	
	/** after acz introduce -CDT-Ujwal- Start*/
	@JsonProperty
	private Integer AczID;
	
	@JsonProperty
	private Integer SowingWeekStart;
	
	@JsonProperty
	private Integer SowingWeekEnd;

	
	public Integer getAczID() {
		return AczID;
	}

	public void setAczID(Integer aczID) {
		AczID = aczID;
	}

	public Integer getSowingWeekStart() {
		return SowingWeekStart;
	}

	public void setSowingWeekStart(Integer SowingWeekStart) {
		this.SowingWeekStart = SowingWeekStart;
	}

	public Integer getSowingWeekEnd() {
		return SowingWeekEnd;
	}

	public void setSowingWeekEnd(Integer SowingWeekEnd) {
		this.SowingWeekEnd = SowingWeekEnd;
	}
	/** after acz introduce -CDT-Ujwal- End*/
	
	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public Integer getStressID() {
		return StressID;
	}

	public void setStressId(Integer StressID) {
		this.StressID = StressID;
	}

	public Integer getCommodityID() {
		return CommodityID;
	}

	public void setCommodityID(Integer commodityID) {
		CommodityID = commodityID;
	}

	public Integer getStressTypeID() {
		return StressTypeID;
	}

	public void setStressTypeID(Integer stressTypeID) {
		StressTypeID = stressTypeID;
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

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

//	public String getScientificName() {
//		return ScientificName;
//	}
//
//	public void setScientificName(String scientificName) {
//		ScientificName = scientificName;
//	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}
	
	public Stress getEntity() {
		Stress stress = new Stress();
		stress.setId(ID);
		stress.setName(Name);
//		stress.setSciName(ScientificName);
		stress.setStartDas(StartDas);
		stress.setEndDas(EndDas);
		stress.setStressTypeId(StressTypeID);
		stress.setCommodityId(CommodityID);
		stress.setStatus(Status != null && Status.equalsIgnoreCase("Active") ? 1 : 0);
		stress.setAczId(AczID);
		stress.setSowingWeekStart(SowingWeekStart);
		stress.setSowingWeekend(SowingWeekEnd);
		stress.setStressId(StressID);
		return stress;
	}

}
