package com.krishi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="view_non_technical_calling_list_spot")
public class ViewNonTechnicalCallingListSpot {
	
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

	@JsonView(DataTablesOutput.View.class)
	private String cropType;

	@JsonView(DataTablesOutput.View.class)
	private String commodityName;

	@JsonView(DataTablesOutput.View.class)
	private String commodityId;

	@JsonView(DataTablesOutput.View.class)
	private String cropArea;

	private String sellerType;

	private Integer ordering;

	public String getCropArea() {
		return cropArea;
	}

	public void setCropArea(String cropArea) {
		this.cropArea = cropArea;
	}

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

	public String getCropType() {
		return cropType;
	}

	public void setCropType(String cropType) {
		this.cropType = cropType;
	}

	public Integer getOrdering() {
		return ordering;
	}

	public void setOrdering(Integer ordering) {
		this.ordering = ordering;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public String getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(String commodityId) {
		this.commodityId = commodityId;
	}
}
