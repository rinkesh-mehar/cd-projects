package com.drkrishi.rlt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "stress_recommendation" )
public class StressRecommendation {

	@Id
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "commodity_id")
	private Integer commodityId;
	
	@Column(name = "stress_id")
	private Integer stressId;
	
	@Column(name = "control_measure_id")
	private Integer controlMeasureId;
	
	@Column(name = "instruction")
	private String instruction;
	
	@Column(name = "agrochemical_id")
	private Integer agrochemicalId;
	
	@Column(name = "agrochemical_application_id")
	private Integer agrochemicalApplicationId;
	  
	@Column(name = "dose")
	private String dose;
	
	@Column(name = "dose_uom_id")
	private Integer doseUomId;
	
	@Column(name = "water")
	private String water;
	
	@Column(name = "water_uom_id")
	private Integer waterUomId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(Integer commodityId) {
		this.commodityId = commodityId;
	}

	public Integer getStressId() {
		return stressId;
	}

	public void setStressId(Integer stressId) {
		this.stressId = stressId;
	}

	public Integer getControlMeasureId() {
		return controlMeasureId;
	}

	public void setControlMeasureId(Integer controlMeasureId) {
		this.controlMeasureId = controlMeasureId;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public Integer getAgrochemicalId() {
		return agrochemicalId;
	}

	public void setAgrochemicalId(Integer agrochemicalId) {
		this.agrochemicalId = agrochemicalId;
	}

	public Integer getAgrochemicalApplicationId() {
		return agrochemicalApplicationId;
	}

	public void setAgrochemicalApplicationId(Integer agrochemicalApplicationId) {
		this.agrochemicalApplicationId = agrochemicalApplicationId;
	}

	public String getDose() {
		return dose;
	}

	public void setDose(String dose) {
		this.dose = dose;
	}

	public Integer getDoseUomId() {
		return doseUomId;
	}

	public void setDoseUomId(Integer doseUomId) {
		this.doseUomId = doseUomId;
	}

	public String getWater() {
		return water;
	}

	public void setWater(String water) {
		this.water = water;
	}

	public Integer getWaterUomId() {
		return waterUomId;
	}

	public void setWaterUomId(Integer waterUomId) {
		this.waterUomId = waterUomId;
	}
	
}
