package com.drkrishi.rlt.model;

public class GeneralInformation {

	private String state;
	private String tehsil;
	private String distict;
	private String villageName;
	private String farmerName;
	private String farmerMobileNumber;
	/** added new field- start */
	private Integer  stateCode;


	public Integer getStateCode() {
		return stateCode;
	}
	public void setStateCode(Integer stateCode) {
		this.stateCode = stateCode;
	}
	/** added new field- end */
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getTehsil() {
		return tehsil;
	}
	public void setTehsil(String tehsil) {
		this.tehsil = tehsil;
	}
	public String getDistict() {
		return distict;
	}
	public void setDistict(String distict) {
		this.distict = distict;
	}
	public String getVillageName() {
		return villageName;
	}
	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}
	public String getFarmerName() {
		return farmerName;
	}
	public void setFarmerName(String farmerName) {
		this.farmerName = farmerName;
	}
	public String getFarmerMobileNumber() {
		return farmerMobileNumber;
	}
	public void setFarmerMobileNumber(String farmerMobileNumber) {
		this.farmerMobileNumber = farmerMobileNumber;
	}

	@Override
	public String toString() {
		return "GeneralInformation [state=" + state + ", tehsil=" + tehsil + ", distict=" + distict + ", villageName="
				+ villageName + ", farmerName=" + farmerName + ", farmerMobileNumber=" + farmerMobileNumber
				+ ", stateCode=" + stateCode + "]";
	}
}
