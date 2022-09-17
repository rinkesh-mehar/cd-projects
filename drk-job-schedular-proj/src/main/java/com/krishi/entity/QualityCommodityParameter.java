package com.krishi.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CDT-Ujwal
 */

@Entity
@Table(name = "quality_commodity_parameter")
@JsonIgnoreProperties(ignoreUnknown = true)
public class QualityCommodityParameter implements Serializable {

	@Id
	@Column(name = "id")
	@JsonProperty("ID")
	private Integer id;
	
	@Column(name = "commodity_id")
	@JsonProperty("CommodityID")
	private Integer commodityId;
	
	@Column(name = "parameter_id")
	@JsonProperty("ParameterID")
	private Integer parameterId;
	
	@Column(name = "min_value")
	@JsonProperty("Min")
	private Double minValue;
	
	@Column(name = "max_value")
	@JsonProperty("Max")
	private Double maxValue;

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

	public Integer getParameterId() {
		return parameterId;
	}

	public void setParameterId(Integer parameterId) {
		this.parameterId = parameterId;
	}

	public Double getMinValue() {
		return minValue;
	}

	public void setMinValue(Double minValue) {
		this.minValue = minValue;
	}

	public Double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Double maxValue) {
		this.maxValue = maxValue;
	}

	@Override
	public String toString() {
		return "QualityCommodityParameter [id=" + id + ", commodityId=" + commodityId + ", parameterId=" + parameterId
				+ ", minValue=" + minValue + ", maxValue=" + maxValue + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commodityId == null) ? 0 : commodityId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((maxValue == null) ? 0 : maxValue.hashCode());
		result = prime * result + ((minValue == null) ? 0 : minValue.hashCode());
		result = prime * result + ((parameterId == null) ? 0 : parameterId.hashCode());
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
		QualityCommodityParameter other = (QualityCommodityParameter) obj;
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
		if (maxValue == null) {
			if (other.maxValue != null)
				return false;
		} else if (!maxValue.equals(other.maxValue))
			return false;
		if (minValue == null) {
			if (other.minValue != null)
				return false;
		} else if (!minValue.equals(other.minValue))
			return false;
		if (parameterId == null) {
			if (other.parameterId != null)
				return false;
		} else if (!parameterId.equals(other.parameterId))
			return false;
		return true;
	}
	
	
	
}
