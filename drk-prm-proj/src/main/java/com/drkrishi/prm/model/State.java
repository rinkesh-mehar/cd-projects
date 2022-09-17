package com.drkrishi.prm.model;

public class State {

	private int stateId;

	private String comment;

	private String stateName;

	private int status;
	
	private int stateCode;
	
	private int countryCode;

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStateCode() {
		return stateCode;
	}

	public void setStateCode(int stateCode) {
		this.stateCode = stateCode;
	}

	public int getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(int countryCode) {
		this.countryCode = countryCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + countryCode;
		result = prime * result + stateCode;
		result = prime * result + stateId;
		result = prime * result + ((stateName == null) ? 0 : stateName.hashCode());
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
		State other = (State) obj;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (countryCode != other.countryCode)
			return false;
		if (stateCode != other.stateCode)
			return false;
		if (stateId != other.stateId)
			return false;
		if (stateName == null) {
			if (other.stateName != null)
				return false;
		} else if (!stateName.equals(other.stateName))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "State [stateId=" + stateId + ", comment=" + comment + ", stateName=" + stateName + ", status="
				+ status + ", stateCode=" + stateCode + ", countryCode=" + countryCode + "]";
	}

	public State(int stateId, String comment, String stateName, int status, int stateCode, int countryCode) {
		super();
		this.stateId = stateId;
		this.comment = comment;
		this.stateName = stateName;
		this.status = status;
		this.stateCode = stateCode;
		this.countryCode = countryCode;
	}

	public State() {
	}

	
}