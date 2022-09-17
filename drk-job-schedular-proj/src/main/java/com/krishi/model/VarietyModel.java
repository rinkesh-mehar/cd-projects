package com.krishi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishi.entity.Variety;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VarietyModel {

	@JsonProperty
	private Integer ID;

	@JsonProperty
	private Integer CommodityID;

	@JsonProperty
	private String Name;

	@JsonProperty
	private String HsCode;

	@JsonProperty
	private String Status;
	
	@JsonProperty
	private String Uom;
	
	@JsonProperty
	private String DomesticRestrictions;
	
	@JsonProperty
	private String InternationalRestrictions;
	

	public float getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public float getCommodityID() {
		return CommodityID;
	}

	public void setCommodityID(Integer commodityID) {
		CommodityID = commodityID;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getHsCode() {
		return HsCode;
	}

	public void setHsCode(String hsCode) {
		HsCode = hsCode;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}
	
	public String getUom() {
		return Uom;
	}

	public void setUom(String uom) {
		Uom = uom;
	}

	public String getDomesticRestrictions() {
		return DomesticRestrictions;
	}

	public void setDomesticRestrictions(String domesticRestrictions) {
		DomesticRestrictions = domesticRestrictions;
	}

	public String getInternationalRestrictions() {
		return InternationalRestrictions;
	}

	public void setInternationalRestrictions(String internationalRestrictions) {
		InternationalRestrictions = internationalRestrictions;
	}

	public Variety getEntity() {
		Variety entity = new Variety();

		entity.setId(ID);
		entity.setName(Name);
		entity.setCommodityId(CommodityID);
		entity.setHscode(HsCode);
		entity.setStatus(Status != null && Status.equalsIgnoreCase("Active") ? 1 : 0);
		entity.setUom(Uom);
		entity.setDomesticRestrictions(DomesticRestrictions);
		entity.setInternationalRestrictions(InternationalRestrictions);
		return entity;
	}

}
