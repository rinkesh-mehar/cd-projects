package com.krishi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "agrochemical")
public class Argochemical {

	/**
	* 
	*/

	@Id
	@Column(unique = true, nullable = false, precision = 10)
	private Integer id;

	@Column(length = 45)
	private String name;

	@Column(name = "status", nullable = false, precision = 10)
	private Integer status;
	@Column(name = "agrochemical_type_id", nullable = false, precision = 10)
	private Integer agrochemicalTypeID;
	@Column(name = "stress_type_id", nullable = false, precision = 10)
	private Integer stressTypeID;
	@Column(name = "commodity_id", nullable = false, precision = 10)
	private Integer commodityID;
	@Column(name = "waiting_period", nullable = false, precision = 10)
	private Integer waitingPeriod;

	public Argochemical() {
		super();
	}

	public Argochemical(Integer id, String name, Integer status, Integer agrochemicalTypeID, Integer stressTypeID,
			Integer commodityID, Integer waitingPeriod) {
		super();
		this.id = id;
		this.name = name;
		this.status = status;
		this.agrochemicalTypeID = agrochemicalTypeID;
		this.stressTypeID = stressTypeID;
		this.commodityID = commodityID;
		this.waitingPeriod = waitingPeriod;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getAgrochemicalTypeID() {
		return agrochemicalTypeID;
	}

	public void setAgrochemicalTypeID(Integer agrochemicalTypeID) {
		this.agrochemicalTypeID = agrochemicalTypeID;
	}

	public Integer getStressTypeID() {
		return stressTypeID;
	}

	public void setStressTypeID(Integer stressTypeID) {
		this.stressTypeID = stressTypeID;
	}

	public Integer getCommodityID() {
		return commodityID;
	}

	public void setCommodityID(Integer commodityID) {
		this.commodityID = commodityID;
	}

	public Integer getWaitingPeriod() {
		return waitingPeriod;
	}

	public void setWaitingPeriod(Integer waitingPeriod) {
		this.waitingPeriod = waitingPeriod;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agrochemicalTypeID == null) ? 0 : agrochemicalTypeID.hashCode());
		result = prime * result + ((commodityID == null) ? 0 : commodityID.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((stressTypeID == null) ? 0 : stressTypeID.hashCode());
		result = prime * result + ((waitingPeriod == null) ? 0 : waitingPeriod.hashCode());
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
		Argochemical other = (Argochemical) obj;
		if (agrochemicalTypeID == null) {
			if (other.agrochemicalTypeID != null)
				return false;
		} else if (!agrochemicalTypeID.equals(other.agrochemicalTypeID))
			return false;
		if (commodityID == null) {
			if (other.commodityID != null)
				return false;
		} else if (!commodityID.equals(other.commodityID))
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
		if (stressTypeID == null) {
			if (other.stressTypeID != null)
				return false;
		} else if (!stressTypeID.equals(other.stressTypeID))
			return false;
		if (waitingPeriod == null) {
			if (other.waitingPeriod != null)
				return false;
		} else if (!waitingPeriod.equals(other.waitingPeriod))
			return false;
		return true;
	}

}
