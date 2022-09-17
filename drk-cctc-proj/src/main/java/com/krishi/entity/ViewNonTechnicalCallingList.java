package com.krishi.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name="view_non_technical_calling_list")
public class ViewNonTechnicalCallingList {
	
	@JsonView(DataTablesOutput.View.class)
	private transient Integer serialNumber;
	
	@Id
	@JsonView(DataTablesOutput.View.class)
	private String taskId;
	
	@JsonView(DataTablesOutput.View.class)
	private String farmerName;
	
	@JsonIgnore
	private Integer villageId;
	
	@JsonView(DataTablesOutput.View.class)
	private String villageName;
	
	@JsonIgnore
	private Integer districtId;
	
	@JsonView(DataTablesOutput.View.class)
	private String districtName;
	
	@JsonIgnore
	private Integer regionId;
	
	@JsonView(DataTablesOutput.View.class)
	private String regionName;
	
	@JsonIgnore
	private Integer stateId;
	
	@JsonView(DataTablesOutput.View.class)
	private String stateName;
	
	@JsonView(DataTablesOutput.View.class)
	private String farmerPrimaryMobNumber;
	
	@JsonView(DataTablesOutput.View.class)
	private String farmerAlternativeMobNumber;
	
	@JsonView(DataTablesOutput.View.class)
	private String type;
	
	private String sellerType;
	
	public String getSellerType() {
		return sellerType;
	}

	public void setSellerType(String sellerType) {
		this.sellerType = sellerType;
	}

	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getFarmerName() {
		return farmerName;
	}

	public void setFarmerName(String farmerName) {
		this.farmerName = farmerName;
	}

	public Integer getVillageId() {
		return villageId;
	}

	public void setVillageId(Integer villageId) {
		this.villageId = villageId;
	}

	public String getVillageName() {
		return villageName;
	}

	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}

	public Integer getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getFarmerPrimaryMobNumber() {
		return farmerPrimaryMobNumber;
	}

	public void setFarmerPrimaryMobNumber(String farmerPrimaryMobNumber) {
		this.farmerPrimaryMobNumber = farmerPrimaryMobNumber;
	}

	public String getFarmerAlternativeMobNumber() {
		return farmerAlternativeMobNumber;
	}

	public void setFarmerAlternativeMobNumber(String farmerAlternativeMobNumber) {
		this.farmerAlternativeMobNumber = farmerAlternativeMobNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	

}
