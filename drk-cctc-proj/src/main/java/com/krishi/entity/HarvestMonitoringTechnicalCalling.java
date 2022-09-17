package com.krishi.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name="harvest_monitoring_technical_calling")
public class HarvestMonitoringTechnicalCalling {
	
	@Id
	private String taskId;
	
	private Date taskDate;
	
	private Integer assigneeId;
	
	private Integer taskTypeId;
	
	private String farmerName;
	
	private String villageName;

	private String villageId;

	private String panchayatId;

	private String panchayatName;

	private String tehsilId;

	private String tehsilName;

	private String districtId;

	private String districtName;

	private String stateId;

	private String stateName;

	private Integer taskStatus;
	
	private String regionName;

	private Integer regionId;

	private String farmerAlternativeMobNumber;

	private String farmerPrimaryMobNumber;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Date getTaskDate() {
		return taskDate;
	}

	public void setTaskDate(Date taskDate) {
		this.taskDate = taskDate;
	}

	public Integer getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(Integer assigneeId) {
		this.assigneeId = assigneeId;
	}

	public Integer getTaskTypeId() {
		return taskTypeId;
	}

	public void setTaskTypeId(Integer taskTypeId) {
		this.taskTypeId = taskTypeId;
	}

	public String getFarmerName() {
		return farmerName;
	}

	public void setFarmerName(String farmerName) {
		this.farmerName = farmerName;
	}

	public String getVillageName() {
		return villageName;
	}

	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}

	public String getVillageId() {
		return villageId;
	}

	public void setVillageId(String villageId) {
		this.villageId = villageId;
	}

	public String getPanchayatId() {
		return panchayatId;
	}

	public void setPanchayatId(String panchayatId) {
		this.panchayatId = panchayatId;
	}

	public String getPanchayatName() {
		return panchayatName;
	}

	public void setPanchayatName(String panchayatName) {
		this.panchayatName = panchayatName;
	}

	public String getTehsilId() {
		return tehsilId;
	}

	public void setTehsilId(String tehsilId) {
		this.tehsilId = tehsilId;
	}

	public String getTehsilName() {
		return tehsilName;
	}

	public void setTehsilName(String tehsilName) {
		this.tehsilName = tehsilName;
	}

	public String getDistrictId() {
		return districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getStateId() {
		return stateId;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public Integer getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(Integer taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
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
}
