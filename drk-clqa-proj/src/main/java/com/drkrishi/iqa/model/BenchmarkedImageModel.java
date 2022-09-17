package com.drkrishi.iqa.model;

public class BenchmarkedImageModel {

	private String id;
	private String symptom;
//	private String imageName;
	private String imageUrl;
	private String spotId;
	private String side;
	private String taskSpotId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSymptom() {
		return symptom;
	}
	public void setSymptom(String symptom) {
		this.symptom = symptom;
	}
//	public String getImageName() {
//		return imageName;
//	}
//	public void setImageName(String imageName) {
//		this.imageName = imageName;
//	}


	public String getTaskSpotId() {
		return taskSpotId;
	}

	public void setTaskSpotId(String taskSpotId) {
		this.taskSpotId = taskSpotId;
	}

	public String getSpotId() {
		return spotId;
	}

	public void setSpotId(String spotId) {
		this.spotId = spotId;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
