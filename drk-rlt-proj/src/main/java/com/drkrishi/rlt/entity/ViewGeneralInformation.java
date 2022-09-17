package com.drkrishi.rlt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "view_general_information" )
public class ViewGeneralInformation {
	
	@Id
	@Column( name = "task_id")
	private String taskId;
	
	@Column( name = "farmer_name")
	private String farmerName;
	
	@Column( name = "primary_mob_number")
	private String primaryMobNumber;
	
	@Column( name = "village_name")
	private String villageName;
	
	@Column( name = "tehsil_name")
	private String tehsilName;
	
	@Column( name = "district_name")
	private String districtName;
	
	@Column( name = "state_name")
	private String stateName;

	/** added new field - Start */
	@Column(name = "state_code")
	private Integer stateCode;

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

	public String getPrimaryMobNumber() {
		return primaryMobNumber;
	}

	public void setPrimaryMobNumber(String primaryMobNumber) {
		this.primaryMobNumber = primaryMobNumber;
	}

	public String getVillageName() {
		return villageName;
	}

	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}

	public String getTehsilName() {
		return tehsilName;
	}

	public void setTehsilName(String tehsilName) {
		this.tehsilName = tehsilName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public Integer getStateCode() {
		return stateCode;
	}

	public void setStateCode(Integer stateCode) {
		this.stateCode = stateCode;
	}
}
