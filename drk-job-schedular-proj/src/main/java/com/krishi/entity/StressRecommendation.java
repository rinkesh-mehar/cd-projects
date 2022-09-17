package com.krishi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "stress_recommendation")
public class StressRecommendation {

	@Id
	@Column(name = "id", unique = true, nullable = false, precision = 10)
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

	@Column(name = "agrochemical_instruction")
	private String agrochemicalInstruction;

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

	@Column(name = "status")
	private Integer status;

	@Column(name = "sowing_week_start")
	private Integer sowingWeekStart;

	@Column(name = "acz_id")
	private Integer aczId;

	@Column(name = "sowing_week_end")
	private Integer sowingWeekEnd;

	@Column(name = "recommendation_id")
	private Integer recommendationId;

	@Column(name = "agrochemical_instruction_id")
	private Integer agrochemicalInstructionID;

	@Column(name = "agrochem_application_type_id")
	private Integer agrochemApplicationTypeID;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSowingWeekStart() {
		return sowingWeekStart;
	}

	public void setSowingWeekStart(Integer sowingWeekStart) {
		this.sowingWeekStart = sowingWeekStart;
	}

	public Integer getAczId() {
		return aczId;
	}

	public void setAczId(Integer aczId) {
		this.aczId = aczId;
	}

	public Integer getSowingWeekEnd() {
		return sowingWeekEnd;
	}

	public void setSowingWeekEnd(Integer sowingWeekEnd) {
		this.sowingWeekEnd = sowingWeekEnd;
	}

	public Integer getRecommendationId() {
		return recommendationId;
	}

	public void setRecommendationId(Integer recommendationId) {
		this.recommendationId = recommendationId;
	}

	public Integer getAgrochemicalInstructionID() {
		return agrochemicalInstructionID;
	}

	public void setAgrochemicalInstructionID(Integer agrochemicalInstructionID) {
		this.agrochemicalInstructionID = agrochemicalInstructionID;
	}

	public Integer getAgrochemApplicationTypeID() {
		return agrochemApplicationTypeID;
	}

	public void setAgrochemApplicationTypeID(Integer agrochemApplicationTypeID) {
		this.agrochemApplicationTypeID = agrochemApplicationTypeID;
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

	public String getAgrochemicalInstruction() {
		return agrochemicalInstruction;
	}

	public void setAgrochemicalInstruction(String agrochemicalInstruction) {
		this.agrochemicalInstruction = agrochemicalInstruction;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agrochemicalApplicationId == null) ? 0 : agrochemicalApplicationId.hashCode());
		result = prime * result + ((agrochemicalId == null) ? 0 : agrochemicalId.hashCode());
		result = prime * result + ((agrochemicalInstruction == null) ? 0 : agrochemicalInstruction.hashCode());
		result = prime * result + ((commodityId == null) ? 0 : commodityId.hashCode());
		result = prime * result + ((controlMeasureId == null) ? 0 : controlMeasureId.hashCode());
		result = prime * result + ((dose == null) ? 0 : dose.hashCode());
		result = prime * result + ((doseUomId == null) ? 0 : doseUomId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((instruction == null) ? 0 : instruction.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((stressId == null) ? 0 : stressId.hashCode());
		result = prime * result + ((water == null) ? 0 : water.hashCode());
		result = prime * result + ((waterUomId == null) ? 0 : waterUomId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StressRecommendation other = (StressRecommendation) obj;
		if (agrochemicalApplicationId == null) {
			if (other.agrochemicalApplicationId != null)
				return false;
		} else if (!agrochemicalApplicationId.equals(other.agrochemicalApplicationId))
			return false;
		if (agrochemicalId == null) {
			if (other.agrochemicalId != null)
				return false;
		} else if (!agrochemicalId.equals(other.agrochemicalId))
			return false;
		if (agrochemicalInstruction == null) {
			if (other.agrochemicalInstruction != null)
				return false;
		} else if (!agrochemicalInstruction.equals(other.agrochemicalInstruction))
			return false;
		if (commodityId == null) {
			if (other.commodityId != null)
				return false;
		} else if (!commodityId.equals(other.commodityId))
			return false;
		if (controlMeasureId == null) {
			if (other.controlMeasureId != null)
				return false;
		} else if (!controlMeasureId.equals(other.controlMeasureId))
			return false;
		if (dose == null) {
			if (other.dose != null)
				return false;
		} else if (!dose.equals(other.dose))
			return false;
		if (doseUomId == null) {
			if (other.doseUomId != null)
				return false;
		} else if (!doseUomId.equals(other.doseUomId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (instruction == null) {
			if (other.instruction != null)
				return false;
		} else if (!instruction.equals(other.instruction))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (stressId == null) {
			if (other.stressId != null)
				return false;
		} else if (!stressId.equals(other.stressId))
			return false;
		if (water == null) {
			if (other.water != null)
				return false;
		} else if (!water.equals(other.water))
			return false;
		if (waterUomId == null) {
			if (other.waterUomId != null)
				return false;
		} else if (!waterUomId.equals(other.waterUomId))
			return false;
		return true;
	}

}
