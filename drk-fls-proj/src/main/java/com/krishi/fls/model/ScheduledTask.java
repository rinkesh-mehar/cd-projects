package com.krishi.fls.model;

import java.util.List;
import java.util.Set;

import com.krishi.fls.entity.CaseType;
import com.krishi.fls.entity.District;
import com.krishi.fls.entity.Farmer;
import com.krishi.fls.entity.Panchayat;
import com.krishi.fls.entity.Poi;
import com.krishi.fls.entity.Region;
import com.krishi.fls.entity.State;
import com.krishi.fls.entity.TaskAllocation;
import com.krishi.fls.entity.TaskFutureDates;
import com.krishi.fls.entity.TaskType;
import com.krishi.fls.entity.Tehsil;
import com.krishi.fls.entity.Village;

public class ScheduledTask {

	private List<TaskAllocation> taskAllocations;

	private Set<Farmer> farmer;

	private Set<Village> village;

	private Set<Region> region;

	private Set<State> state;

	private List<TaskType> taskType;

	private List<CaseType> caseType;

	private Set<Poi> poi;

	private Set<Panchayat> panchayat;
	private Set<Tehsil> tehsil;
	
	private Set<District> districts;
	
	private Set<TaskFutureDates> taskFutureDates;

	public Set<Panchayat> getPanchayat() {
		return panchayat;
	}

	public void setPanchayat(Set<Panchayat> panchayat) {
		this.panchayat = panchayat;
	}

	public Set<Tehsil> getTehsil() {
		return tehsil;
	}

	public void setTehsil(Set<Tehsil> tehsil) {
		this.tehsil = tehsil;
	}

	private String timeDuration;

	/*TODO : remove master data*/
	private MasterData masterData;

	public List<CaseType> getCaseType() {
		return caseType;
	}

	public void setCaseType(List<CaseType> caseType) {
		this.caseType = caseType;
	}

	public List<TaskType> getTaskType() {
		return taskType;
	}

	public void setTaskType(List<TaskType> taskType) {
		this.taskType = taskType;
	}

	public List<TaskAllocation> getTaskAllocations() {
		return taskAllocations;
	}

	public void setTaskAllocations(List<TaskAllocation> taskAllocations) {
		this.taskAllocations = taskAllocations;
	}

	public Set<State> getState() {
		return state;
	}

	public void setState(Set<State> state) {
		this.state = state;
	}

	public Set<Region> getRegion() {
		return region;
	}

	public void setRegion(Set<Region> region) {
		this.region = region;
	}

	public Set<Village> getVillage() {
		return village;
	}

	public void setVillage(Set<Village> village) {
		this.village = village;
	}

	public Set<Farmer> getFarmer() {
		return farmer;
	}

	public void setFarmer(Set<Farmer> farmer) {
		this.farmer = farmer;
	}

	public Set<Poi> getPoi() {
		return poi;
	}

	public void setPoi(Set<Poi> poi) {
		this.poi = poi;
	}

	public String getTimeDuration() {
		return timeDuration;
	}

	public void setTimeDuration(String timeDuration) {
		this.timeDuration = timeDuration;
	}

	public MasterData getMasterData() {
		return masterData;
	}

	public void setMasterData(MasterData masterData) {
		this.masterData = masterData;
	}

	public Set<District> getDistricts() {
		return districts;
	}

	public void setDistricts(Set<District> districts) {
		this.districts = districts;
	}

	@Override
	public String toString() {
		return "ScheduledTask [taskAllocations=" + taskAllocations + ", farmer=" + farmer + ", village=" + village
				+ ", region=" + region + ", state=" + state + ", taskType=" + taskType + ", caseType=" + caseType
				+ ", poi=" + poi + ", timeDuration=" + timeDuration + "]";
	}

	public Set<TaskFutureDates> getTaskFutureDates() {
		return taskFutureDates;
	}

	public void setTaskFutureDates(Set<TaskFutureDates> taskFutureDates) {
		this.taskFutureDates = taskFutureDates;
	}

}
