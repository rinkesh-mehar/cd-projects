package com.krishi.model;

import com.krishi.entity.HarvestMonitoringTechnicalCalling;
import com.krishi.entity.ViewFieldMonitoringInfo;

import java.text.SimpleDateFormat;

public class HarvestMonitoringModal {

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

	public HarvestMonitoringModal() {
		super();
	}

	public HarvestMonitoringModal(HarvestMonitoringTechnicalCalling harvestMonitoringTechnicalCalling) {
		this.name = harvestMonitoringTechnicalCalling.getFarmerName();
		this.district = harvestMonitoringTechnicalCalling.getDistrictName();
		this.village = harvestMonitoringTechnicalCalling.getVillageName();
		this.region = harvestMonitoringTechnicalCalling.getRegionName();
		this.state = harvestMonitoringTechnicalCalling.getStateName();
//		if(harvestMonitoringTechnicalCalling.getNextMonitoringDate() != null) {
//			this.expecteddate = FORMAT.format(harvestMonitoringTechnicalCalling.getNextMonitoringDate());
//		}
		this.taskid = harvestMonitoringTechnicalCalling.getTaskId();
		this.farmerPrimaryMobNumber = harvestMonitoringTechnicalCalling.getFarmerPrimaryMobNumber();
		this.farmerAlternativeMobNumber = harvestMonitoringTechnicalCalling.getFarmerAlternativeMobNumber();
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
