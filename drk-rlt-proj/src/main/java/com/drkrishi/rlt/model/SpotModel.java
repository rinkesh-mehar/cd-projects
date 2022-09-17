package com.drkrishi.rlt.model;

import java.util.List;

import com.drkrishi.rlt.entity.StressCase;

public class SpotModel {
	
	private Integer userId;
	private String name;
	private List<Stress> stresses;
	private Health health;
	private Boolean isSpotBenchmark;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Stress> getStresses() {
		return stresses;
	}
	public void setStresses(List<Stress> stresses) {
		this.stresses = stresses;
	}
	public Health getHealth() {
		return health;
	}
	public void setHealth(Health health) {
		this.health = health;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Boolean getIsSpotBenchmark() {
		return isSpotBenchmark;
	}
	public void setIsSpotBenchmark(Boolean isSpotBenchmark) {
		this.isSpotBenchmark = isSpotBenchmark;
	}
}
