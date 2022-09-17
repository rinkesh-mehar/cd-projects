package com.krishi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "agrochemical_brand")
public class AgrochemicalBrand {
	
	@Id
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "agrochemical_company_id")
	private Integer agrochemicalCompanyId;
	
	@Column(name = "agrochemical_id")
	private Integer agrochemicalId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "status")
	private Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAgrochemicalCompanyId() {
		return agrochemicalCompanyId;
	}

	public void setAgrochemicalCompanyId(Integer agrochemicalCompanyId) {
		this.agrochemicalCompanyId = agrochemicalCompanyId;
	}

	public Integer getAgrochemicalId() {
		return agrochemicalId;
	}

	public void setAgrochemicalId(Integer agrochemicalId) {
		this.agrochemicalId = agrochemicalId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
