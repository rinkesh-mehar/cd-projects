package com.krishi.fls.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "stress_control_measures")
public class StressControlMeasure {

	@Id
	@Column(unique = true, nullable = false, precision = 10)
	private int id;

	@Column(name="name")
	private String Name;

	@Column(name="status")
	private Integer Status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public Integer getStatus() {
		return Status;
	}

	public void setStatus(Integer status) {
		Status = status;
	}

}