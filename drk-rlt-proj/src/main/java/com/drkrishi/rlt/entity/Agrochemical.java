package com.drkrishi.rlt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "agrochemical" )
public class Agrochemical {
	
	@Id
	@Column( name = "id" )
	private Integer id;
	
	@Column( name = "agrochemical_type_id" )
	private Integer agrochemicalTypeId;
	
	@Column( name = "commodity_id" )
	private Integer commodityId;
	
	@Column( name = "stress_type_id" )
	private Integer stressTypeId;
	
	@Column( name = "name" )
	private String name;
	
	@Column( name = "waiting_period" )
	private Integer waitingPeriod;
	
	@Column( name = "status" )
	private Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAgrochemicalTypeId() {
		return agrochemicalTypeId;
	}

	public void setAgrochemicalTypeId(Integer agrochemicalTypeId) {
		this.agrochemicalTypeId = agrochemicalTypeId;
	}

	public Integer getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(Integer commodityId) {
		this.commodityId = commodityId;
	}

	public Integer getStressTypeId() {
		return stressTypeId;
	}

	public void setStressTypeId(Integer stressTypeId) {
		this.stressTypeId = stressTypeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getWaitingPeriod() {
		return waitingPeriod;
	}

	public void setWaitingPeriod(Integer waitingPeriod) {
		this.waitingPeriod = waitingPeriod;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
