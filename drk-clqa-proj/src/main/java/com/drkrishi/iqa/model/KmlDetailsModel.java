package com.drkrishi.iqa.model;

public class KmlDetailsModel {
	
	private String cropName;
	private Double correction;
	private Double area; 
	private String fileName;
	private Coordinate coordinates;
	
	public String getCropName() {
		return cropName;
	}
	public void setCropName(String cropName) {
		this.cropName = cropName;
	}
	public Double getCorrection() {
		return correction;
	}
	public void setCorrection(Double correction) {
		this.correction = correction;
	}
	public Double getArea() {
		return area;
	}
	public void setArea(Double area) {
		this.area = area;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Coordinate getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(Coordinate coordinates) {
		this.coordinates = coordinates;
	}
}
