package com.krishi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishi.entity.Tehsil;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TehsilModel {

	@JsonProperty
	private Integer ID;
	@JsonProperty
	private Integer DistrictCode;
	@JsonProperty
	private Integer TehsilCode;
	@JsonProperty
	private String Name;
	@JsonProperty
	private String Status;

	public TehsilModel() {
		super();
	}

	public TehsilModel(Integer iD, Integer districtCode, Integer tehsilCode, String name, String status) {
		super();
		ID = iD;
		DistrictCode = districtCode;
		TehsilCode = tehsilCode;
		Name = name;
		Status = status;
	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public Integer getDistrictCode() {
		return DistrictCode;
	}

	public void setDistrictCode(Integer districtCode) {
		DistrictCode = districtCode;
	}

	public Integer getTehsilCode() {
		return TehsilCode;
	}

	public void setTehsilCode(Integer tehsilCode) {
		TehsilCode = tehsilCode;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	@Override
	public String toString() {
		return "Tehsil [ID=" + ID + ", DistrictCode=" + DistrictCode + ", TehsilCode=" + TehsilCode + ", Name=" + Name
				+ ", Status=" + Status + "]";
	}

	public Tehsil getEntity() {
		Tehsil tehsil = new Tehsil();

		tehsil.setId(ID);
		tehsil.setCode(TehsilCode);
		tehsil.setDistrictId(DistrictCode);
		tehsil.setName(Name);
		tehsil.setStatus(Status != null && Status.equalsIgnoreCase("Active") ? 1 : 0);

		return tehsil;
	}

}
