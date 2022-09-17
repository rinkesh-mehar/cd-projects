package com.drkrishi.prm.model;

public class District{

	private int district_Id;

	private String comments;

	private String district_Name;
	
	private int districtCode;

	private int status;

	private State state;

	public int getDistrict_Id() {
		return district_Id;
	}

	public void setDistrict_Id(int district_Id) {
		this.district_Id = district_Id;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getDistrict_Name() {
		return district_Name;
	}

	public void setDistrict_Name(String district_Name) {
		this.district_Name = district_Name;
	}

	public int getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(int districtCode) {
		this.districtCode = districtCode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comments == null) ? 0 : comments.hashCode());
		result = prime * result + districtCode;
		result = prime * result + district_Id;
		result = prime * result + ((district_Name == null) ? 0 : district_Name.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + status;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		District other = (District) obj;
		if (comments == null) {
			if (other.comments != null)
				return false;
		} else if (!comments.equals(other.comments))
			return false;
		if (districtCode != other.districtCode)
			return false;
		if (district_Id != other.district_Id)
			return false;
		if (district_Name == null) {
			if (other.district_Name != null)
				return false;
		} else if (!district_Name.equals(other.district_Name))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "District [district_Id=" + district_Id + ", comments=" + comments + ", district_Name=" + district_Name
				+ ", districtCode=" + districtCode + ", status=" + status + ", state=" + state + "]";
	}

	public District(int district_Id, String comments, String district_Name, int districtCode, int status, State state) {
		super();
		this.district_Id = district_Id;
		this.comments = comments;
		this.district_Name = district_Name;
		this.districtCode = districtCode;
		this.status = status;
		this.state = state;
	}

	public District() {
		
	}

	

}