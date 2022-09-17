package com.krishi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishi.entity.AgrochemicalBrand;
import com.krishi.entity.Fertilizer;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FertilizerModel {

	private static final long serialVersionUID = 1L;
	@JsonProperty
	private Integer ID;
	@JsonProperty
	private Integer StateCode;
	@JsonProperty
	private Integer AczID;
	@JsonProperty
	private Integer SowingWeekStart;
	@JsonProperty
	private Integer SowingWeekEnd;
	@JsonProperty
	private Integer DoseFactorID;
	@JsonProperty
	private Integer CommodityID;
	@JsonProperty
	private String Name;
	@JsonProperty
	private Integer UomID;
	@JsonProperty
	private Double Dose;
	@JsonProperty
	private String Note;
	@JsonProperty
	private String Status;

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

	public Integer getDoseFactorID() {
		return DoseFactorID;
	}

	public void setDoseFactorID(Integer doseFactorID) {
		DoseFactorID = doseFactorID;
	}

	public Integer getCommodityID() {
		return CommodityID;
	}

	public void setCommodityID(Integer commodityID) {
		CommodityID = commodityID;
	}

	public String getType() {
		return Name;
	}

	public void setType(String type) {
		Name = type;
	}

	public Integer getUomID() {
		return UomID;
	}

	public void setUomID(Integer uomID) {
		UomID = uomID;
	}

	public Double getDose() {
		return Dose;
	}

	public void setDose(Double dose) {
		Dose = dose;
	}

	public String getNote() {
		return Note;
	}

	public void setNote(String note) {
		Note = note;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public Fertilizer getEntity() {
		Fertilizer entity = new Fertilizer();

		entity.setId(ID);
		entity.setName(Name);
		entity.setStatus(Status != null && Status.equalsIgnoreCase("Active") ? 1 : 0);
		entity.setStateCode(StateCode);
		entity.setDoseFactorId(DoseFactorID);
		entity.setCommodityID(CommodityID);
		entity.setUomId(UomID);
		entity.setDose(Dose);
		entity.setNote(Note);
		entity.setAczId(AczID);
		entity.setSowingWeekStart(SowingWeekStart);
		entity.setSowingWeekEnd(SowingWeekEnd);

		return entity;
	}

}
