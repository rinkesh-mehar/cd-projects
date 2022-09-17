package com.drkrishi.rlt.model;

import java.util.List;

public class RecommendationStress {
	private Integer id;
	private String name;
	private Double severity;
	private List<Integer> selectedRecommendations;
	private List<Recommendation> recommendations;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getSeverity() {
		return severity;
	}
	public void setSeverity(Double severity) {
		this.severity = severity;
	}
	public List<Recommendation> getRecommendations() {
		return recommendations;
	}
	public void setRecommendations(List<Recommendation> recommendations) {
		this.recommendations = recommendations;
	}
	public List<Integer> getSelectedRecommendations() {
		return selectedRecommendations;
	}
	public void setSelectedRecommendations(List<Integer> selectedRecommendations) {
		this.selectedRecommendations = selectedRecommendations;
	}
	
}
