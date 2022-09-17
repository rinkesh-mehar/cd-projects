package com.drkrishi.rlt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="quality_commodity_parameter")
public class QualityCommodityParameter {

	@Id
    @Column(unique=true, nullable=false, precision=10)
    private Integer id;

	@Column(name = "parameter_id")
	private Integer parameterId;

	@Column(name = "commodity_id")
	private Integer commodityId;

	@Column(name = "min_value")
	private Integer minValue;

	@Column(name = "max_value")
	private Integer maxValue;

	public QualityCommodityParameter() {
		super();
	}

	public QualityCommodityParameter(Integer id, Integer parameterId, Integer commodityId, Integer minValue, Integer maxValue) {
		this.id = id;
		this.parameterId = parameterId;
		this.commodityId = commodityId;
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getName() {
		return parameterId;
	}

	public void setName(Integer name) {
		this.parameterId = name;
	}

	public Integer getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(Integer commodityId) {
		this.commodityId = commodityId;
	}

	public Integer getMinValue() {
		return minValue;
	}

	public void setMinValue(Integer minValue) {
		this.minValue = minValue;
	}

	public Integer getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Integer maxValue) {
		this.maxValue = maxValue;
	}
}
