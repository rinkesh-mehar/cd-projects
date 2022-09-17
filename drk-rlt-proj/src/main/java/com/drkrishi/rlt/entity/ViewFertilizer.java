package com.drkrishi.rlt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "view_fertilizer")
public class ViewFertilizer {
	
	@Id
	@Column(name = "task_id")
	private String taskId;
	
	@Column(name = "fertilizer_application_method_name")
	private String fertilizerApplicationMethodName;
	
	@Column(name = "fertilizer_name")
	private String fertilizerName;
	
	@Column(name = "unit_of_measurement_name")
	private String unitOfMeasurementName;
	
	@Column(name = "fertilizer_dose")
	private Integer fertilizerDose;
	
	@Column(name = "fertilizer_split_dose")
	private String fertilizerSplitDose;
	
	@Column(name = "fertilizer_week_of_applcation")
	private Integer fertilizerWeekOfApplcation;
	
	@Column(name = "fertilizer_year_of_applcation")
	private Integer fertilizerYearOfApplcation;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getFertilizerApplicationMethodName() {
		return fertilizerApplicationMethodName;
	}

	public void setFertilizerApplicationMethodName(String fertilizerApplicationMethodName) {
		this.fertilizerApplicationMethodName = fertilizerApplicationMethodName;
	}

	public String getFertilizerName() {
		return fertilizerName;
	}

	public void setFertilizerName(String fertilizerName) {
		this.fertilizerName = fertilizerName;
	}

	public String getUnitOfMeasurementName() {
		return unitOfMeasurementName;
	}

	public void setUnitOfMeasurementName(String unitOfMeasurementName) {
		this.unitOfMeasurementName = unitOfMeasurementName;
	}

	public Integer getFertilizerDose() {
		return fertilizerDose;
	}

	public void setFertilizerDose(Integer fertilizerDose) {
		this.fertilizerDose = fertilizerDose;
	}

	public String getFertilizerSplitDose() {
		return fertilizerSplitDose;
	}

	public void setFertilizerSplitDose(String fertilizerSplitDose) {
		this.fertilizerSplitDose = fertilizerSplitDose;
	}

	public Integer getFertilizerWeekOfApplcation() {
		return fertilizerWeekOfApplcation;
	}

	public void setFertilizerWeekOfApplcation(Integer fertilizerWeekOfApplcation) {
		this.fertilizerWeekOfApplcation = fertilizerWeekOfApplcation;
	}

	public Integer getFertilizerYearOfApplcation() {
		return fertilizerYearOfApplcation;
	}

	public void setFertilizerYearOfApplcation(Integer fertilizerYearOfApplcation) {
		this.fertilizerYearOfApplcation = fertilizerYearOfApplcation;
	}
	
}
