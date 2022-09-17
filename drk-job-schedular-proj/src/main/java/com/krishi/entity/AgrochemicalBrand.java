package com.krishi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="agrochemical_brand")
public class AgrochemicalBrand {
	
	@Id
    @Column(unique=true, nullable=false, precision=10)
	private Integer id;
	
	private Integer agrochemicalCompanyId;
	
	private Integer agrochemicalId;
	
	private String name;
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agrochemicalCompanyId == null) ? 0 : agrochemicalCompanyId.hashCode());
		result = prime * result + ((agrochemicalId == null) ? 0 : agrochemicalId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		AgrochemicalBrand other = (AgrochemicalBrand) obj;
		if (agrochemicalCompanyId == null) {
			if (other.agrochemicalCompanyId != null)
				return false;
		} else if (!agrochemicalCompanyId.equals(other.agrochemicalCompanyId))
			return false;
		if (agrochemicalId == null) {
			if (other.agrochemicalId != null)
				return false;
		} else if (!agrochemicalId.equals(other.agrochemicalId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}
}
