package com.drkrishi.prm.model;

import java.util.List;

public class PrsEditResponseModel {
	private Integer assigmentId;
	private int drkrishiUser1;
	private List<String> datasid;
	private Integer prscout;
	private int month;
	private int regionID;
	private int stateId;
	private List<VillageModel> village;
	private int weekNumber;
	private int year;

	public PrsEditResponseModel() {
		super();
	}

	

	public Integer getAssigmentId() {
		return assigmentId;
	}



	public void setAssigmentId(Integer assigmentId) {
		this.assigmentId = assigmentId;
	}



	public int getDrkrishiUser1() {
		return drkrishiUser1;
	}

	public void setDrkrishiUser1(int drkrishiUser1) {
		this.drkrishiUser1 = drkrishiUser1;
	}

	public List<String> getDatasid() {
		return datasid;
	}

	public void setDatasid(List<String> prmTilesList) {
		this.datasid = prmTilesList;
	}

	public Integer getPrscout() {
		return prscout;
	}

	public void setPrscout(Integer prscout) {
		this.prscout = prscout;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getRegionID() {
		return regionID;
	}

	public void setRegionID(int regionID) {
		this.regionID = regionID;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public List<VillageModel> getVillage() {
		return village;
	}

	public void setVillage(List<VillageModel> village) {
		this.village = village;
	}

	public int getWeekNumber() {
		return weekNumber;
	}

	public void setWeekNumber(int weekNumber) {
		this.weekNumber = weekNumber;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}



	@Override
	public String toString() {
		return "PrsEditResponseModel [assigmentId=" + assigmentId + ", drkrishiUser1=" + drkrishiUser1 + ", datasid="
				+ datasid + ", prscout=" + prscout + ", month=" + month + ", regionID=" + regionID + ", stateId="
				+ stateId + ", village=" + village + ", weekNumber=" + weekNumber + ", year=" + year + "]";
	}

	

}
