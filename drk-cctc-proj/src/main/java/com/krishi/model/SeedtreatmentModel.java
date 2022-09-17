package com.krishi.model;

import java.util.List;

import com.krishi.entity.SeedTreatmentAgent;
import com.krishi.entity.ViewFieldMonitoringDetail;
import com.krishi.vo.ViewFieldMonitoringDetailVO;

public class SeedtreatmentModel {
	
    private Boolean seedtreatmentoption;
	
	private String agent;
	
	private String uom;
	
	private Integer dose;
	

	public SeedtreatmentModel() {
		super();
	}

	public SeedtreatmentModel(ViewFieldMonitoringDetailVO detail) {
		this.seedtreatmentoption = detail.getSeedTreatment();
		this.uom = detail.getSeedTreatmentUnitName();
		this.dose = detail.getSeedTreatmentDose();
	}

	public Boolean getSeedtreatmentoption() {
		return seedtreatmentoption;
	}

	public void setSeedtreatmentoption(Boolean seedtreatmentoption) {
		this.seedtreatmentoption = seedtreatmentoption;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public Integer getDose() {
		return dose;
	}

	public void setDose(Integer dose) {
		this.dose = dose;
	}
	
}
