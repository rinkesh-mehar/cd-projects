package com.drkrishi.iqa.model;

import java.util.List;

public class BenchmarkedImageDetailsModel {
	
	private String commodity;
	private String state;
	private String region;
	private String basePath;
//	private List<StressModel> stress;
	private List<TaskSpotModel> taskSpotModels;

	/*private List<SpotStressModel> spotStressModels;
	private SpotHealthModel spotHealthModels;*/

	public String getCommodity() {
		return commodity;
	}

	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	/*public List<StressModel> getStress() {
		return stress;
	}

	public void setStress(List<StressModel> stress) {
		this.stress = stress;
	}*/

	/*public List<SpotStressModel> getSpotStressModels() {
		return spotStressModels;
	}

	public void setSpotStressModels(List<SpotStressModel> spotStressModels) {
		this.spotStressModels = spotStressModels;
	}

	public SpotHealthModel getSpotHealthModels()
	{
		return spotHealthModels;
	}

	public void setSpotHealthModels(SpotHealthModel spotHealthModels)
	{
		this.spotHealthModels = spotHealthModels;
	}*/

	public List<TaskSpotModel> getTaskSpotModels()
	{
		return taskSpotModels;
	}

	public void setTaskSpotModels(List<TaskSpotModel> taskSpotModels)
	{
		this.taskSpotModels = taskSpotModels;
	}
}
