package com.drkrishi.rlt.model;

import java.util.List;

public class DiagnoseModel {
	
	private String rlStressImagePath;
	private String rlHealthImagePath;
	private List<SpotModel> spots;
	private MasterModel masterData;
	
	public String getRlStressImagePath() {
		return rlStressImagePath;
	}
	public void setRlStressImagePath(String rlStressImagePath) {
		this.rlStressImagePath = rlStressImagePath;
	}
	public String getRlHealthImagePath() {
		return rlHealthImagePath;
	}
	public void setRlHealthImagePath(String rlHealthImagePath) {
		this.rlHealthImagePath = rlHealthImagePath;
	}
	public List<SpotModel> getSpots() {
		return spots;
	}
	public void setSpots(List<SpotModel> spots) {
		this.spots = spots;
	}
	public MasterModel getMasterData() {
		return masterData;
	}
	public void setMasterData(MasterModel masterData) {
		this.masterData = masterData;
	}
}
