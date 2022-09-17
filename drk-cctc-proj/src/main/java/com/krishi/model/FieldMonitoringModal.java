package com.krishi.model;

import java.text.SimpleDateFormat;

import com.krishi.entity.ViewFieldMonitoringInfo;

public class FieldMonitoringModal {
	
	private static final SimpleDateFormat FORMAT = new SimpleDateFormat("dd-MM-yyyy");
	
	private String taskid;
	
	private String caseid;
	
	private String name;
	
	private String district;
	
	private String village;
	
	private String region;
	
	private String state;
	
	private String expecteddate;

	private String farmerAlternativeMobNumber;

	private String farmerPrimaryMobNumber;

	public FieldMonitoringModal() {
		super();
	}
	
	public FieldMonitoringModal(ViewFieldMonitoringInfo viewFieldMonitoringInfo) {
		this.caseid = viewFieldMonitoringInfo.getCaseId();
		this.name = viewFieldMonitoringInfo.getFarmerName();
		this.district = viewFieldMonitoringInfo.getDistrictName();
		this.village = viewFieldMonitoringInfo.getVillageName();
		this.region = viewFieldMonitoringInfo.getRegionName();
		this.state = viewFieldMonitoringInfo.getStateName();
		if(viewFieldMonitoringInfo.getNextMonitoringDate() != null) {
			this.expecteddate = FORMAT.format(viewFieldMonitoringInfo.getNextMonitoringDate());
		}
		this.taskid = viewFieldMonitoringInfo.getTaskId();
		this.farmerPrimaryMobNumber = viewFieldMonitoringInfo.getFarmerPrimaryMobNumber();
		this.farmerAlternativeMobNumber = viewFieldMonitoringInfo.getFarmerAlternativeMobNumber();
	}

	public String getFarmerAlternativeMobNumber() {
		return farmerAlternativeMobNumber;
	}

	public void setFarmerAlternativeMobNumber(String farmerAlternativeMobNumber) {
		this.farmerAlternativeMobNumber = farmerAlternativeMobNumber;
	}

	public String getFarmerPrimaryMobNumber() {
		return farmerPrimaryMobNumber;
	}

	public void setFarmerPrimaryMobNumber(String farmerPrimaryMobNumber) {
		this.farmerPrimaryMobNumber = farmerPrimaryMobNumber;
	}

	public String getCaseid() {
		return caseid;
	}

	public void setCaseid(String caseid) {
		this.caseid = caseid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getExpecteddate() {
		return expecteddate;
	}

	public void setExpecteddate(String expecteddate) {
		this.expecteddate = expecteddate;
	}

	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}
}
