package com.krishi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "health_questionnaire")
public class HealthQuestionnaire {

	@Id
	@Column(name = "id", unique = true, nullable = false, precision = 10)
	private Integer id;

	@Column(name = "commodity_id")
	private Integer commodityId;

	@Column(name = "phenophase_id")
	private Integer phenophaseId;

	@Column(name = "name")
	private String name;

	@Column(name = "input_type")
	private String inputType;

	@Column(name = "input_values")
	private String inputValues;

	@Column(name = "status")
	private Integer status;

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

	public Integer getPhenophaseId() {
		return phenophaseId;
	}

	public void setPhenophaseId(Integer phenophaseId) {
		this.phenophaseId = phenophaseId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public String getInputValues() {
		return inputValues;
	}

	public void setInputValues(String inputValues) {
		this.inputValues = inputValues;
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
		result = prime * result + ((commodityId == null) ? 0 : commodityId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((inputType == null) ? 0 : inputType.hashCode());
		result = prime * result + ((inputValues == null) ? 0 : inputValues.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((phenophaseId == null) ? 0 : phenophaseId.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		HealthQuestionnaire other = (HealthQuestionnaire) obj;
		if (commodityId == null) {
			if (other.commodityId != null)
				return false;
		} else if (!commodityId.equals(other.commodityId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (inputType == null) {
			if (other.inputType != null)
				return false;
		} else if (!inputType.equals(other.inputType))
			return false;
		if (inputValues == null) {
			if (other.inputValues != null)
				return false;
		} else if (!inputValues.equals(other.inputValues))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (phenophaseId == null) {
			if (other.phenophaseId != null)
				return false;
		} else if (!phenophaseId.equals(other.phenophaseId))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

}
