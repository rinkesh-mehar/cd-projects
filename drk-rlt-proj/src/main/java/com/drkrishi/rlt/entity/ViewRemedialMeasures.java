package com.drkrishi.rlt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "view_remedial_measures")
public class ViewRemedialMeasures {

	@Id
	@Column(name = "task_id")
	private String taskId;
	
	@Column(name = "agrochemical_name")
	private String agrochemicalName;
	
	@Column(name = "agrochemical_brand_name")
	private String agrochemicalBrandName;
	
	@Column(name = "unit_of_measurement_name")
	private String unitOfMeasurementName;
	
	@Column(name = "agrochemical_dose")
	private Integer agrochemicalDose;
	
	@Column(name = "agrochemical_week_of_applcation")
	private Integer agrochemicalWeekOfApplcation;
	
	@Column(name = "agrochemical_year_of_applcation")
	private Integer agrochemicalYearOfApplcation;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Integer getAgrochemicalDose() {
		return agrochemicalDose;
	}

	public void setAgrochemicalDose(Integer agrochemicalDose) {
		this.agrochemicalDose = agrochemicalDose;
	}

	public Integer getAgrochemicalWeekOfApplcation() {
		return agrochemicalWeekOfApplcation;
	}

	public void setAgrochemicalWeekOfApplcation(Integer agrochemicalWeekOfApplcation) {
		this.agrochemicalWeekOfApplcation = agrochemicalWeekOfApplcation;
	}

	public String getAgrochemicalName() {
		return agrochemicalName;
	}

	public void setAgrochemicalName(String agrochemicalName) {
		this.agrochemicalName = agrochemicalName;
	}

	public String getAgrochemicalBrandName() {
		return agrochemicalBrandName;
	}

	public void setAgrochemicalBrandName(String agrochemicalBrandName) {
		this.agrochemicalBrandName = agrochemicalBrandName;
	}

	public String getUnitOfMeasurementName() {
		return unitOfMeasurementName;
	}

	public void setUnitOfMeasurementName(String unitOfMeasurementName) {
		this.unitOfMeasurementName = unitOfMeasurementName;
	}

	public Integer getAgrochemicalYearOfApplcation() {
		return agrochemicalYearOfApplcation;
	}

	public void setAgrochemicalYearOfApplcation(Integer agrochemicalYearOfApplcation) {
		this.agrochemicalYearOfApplcation = agrochemicalYearOfApplcation;
	}
	
	
	
}
