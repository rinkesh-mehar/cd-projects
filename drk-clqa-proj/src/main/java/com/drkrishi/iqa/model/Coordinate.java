package com.drkrishi.iqa.model;

public class Coordinate {
	
	private String message;
	private Double xMin;
	private Double xMax;
	private Double yMin;
	private Double yMax;
	
	public Double getxMin() {
		return xMin;
	}
	public void setxMin(Double xMin) {
		this.xMin = xMin;
	}
	public Double getxMax() {
		return xMax;
	}
	public void setxMax(Double xMax) {
		this.xMax = xMax;
	}
	public Double getyMin() {
		return yMin;
	}
	public void setyMin(Double yMin) {
		this.yMin = yMin;
	}
	public Double getyMax() {
		return yMax;
	}
	public void setyMax(Double yMax) {
		this.yMax = yMax;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
