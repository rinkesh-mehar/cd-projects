package com.krishi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishi.entity.Panchayat;

public class PanchayatModel {
	
	@JsonProperty
	private Integer ID;
	
	@JsonProperty
	private Integer StateCode;
	
	@JsonProperty
	private Integer DistrictCode;
	
	@JsonProperty
	private Integer TehsilCode;
	
	@JsonProperty
	private Integer PanchayatCode;
	
	@JsonProperty
	private String Name;
	
	@JsonProperty
	private String Status;

	public PanchayatModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PanchayatModel(Integer iD, Integer stateCode, Integer districtCode, Integer tehsilCode,
			Integer panchayatCode, String name, String status) {
		super();
		ID = iD;
		StateCode = stateCode;
		DistrictCode = districtCode;
		TehsilCode = tehsilCode;
		PanchayatCode = panchayatCode;
		Name = name;
		Status = status;
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

	public Integer getPanchayatCode() {
		return PanchayatCode;
	}

	public void setPanchayatCode(Integer panchayatCode) {
		PanchayatCode = panchayatCode;
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
	
	
	public Panchayat getEntity() {
		Panchayat p = new Panchayat();
		p.setId(ID);
		p.setName(Name);
		p.setStatus(Status != null && Status.equalsIgnoreCase("Active") ? 1 : 0);
		p.setCode(PanchayatCode);
		p.setTehsilId(TehsilCode);
		return p;
	}
	
}
