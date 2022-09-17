package com.drkrishi.iqa.model;

import java.util.List;

public class StressModel {

	private Integer stressId;
	private String stressName;
	private List<BenchmarkedImageModel> stressImageModelList;
	
	public String getStressName() {
		return stressName;
	}
	public void setStressName(String stressName) {
		this.stressName = stressName;
	}

	public List<BenchmarkedImageModel> getStressImageModelList() {
		return stressImageModelList;
	}

	public void setStressImageModelList(List<BenchmarkedImageModel> stressImageModelList) {
		this.stressImageModelList = stressImageModelList;
	}

	public Integer getStressId() {
		return stressId;
	}

	public void setStressId(Integer stressId) {
		this.stressId = stressId;
	}
}
