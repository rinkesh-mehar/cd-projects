package com.krishi.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity(name = "stress")
public class Stress {

	@Id
	@Column(unique = true, nullable = false, precision = 10)
	private int id;

	@Column(length = 255)
	private String name;

	@Column(name = "stress_id")
	private Integer stressId;
//	private String sciName;

	@Column(name = "start_das")
	private Integer startDas;

	@Column(name = "end_das")
	private Integer endDas;

	@Column(name = "commodity_id", precision = 10)
	private Integer commodityId;

	@Column(name = "stress_type_id", precision = 10)
	private Integer stressTypeId;

	private Integer status;
	
	/** after acz introduce -CDT-Ujwal*/
	@Column(name = "acz_id")
	private Integer aczId;
	
	@Column(name = "sowing_start_week")
	private Integer sowingWeekStart;
	
	@Column(name = "sowing_end_week")
	private Integer sowingWeekend;

	public Integer getAczId() {
		return aczId;
	}

	public void setAczId(Integer aczId) {
		this.aczId = aczId;
	}

	public Integer getSowingWeekStart() {
		return sowingWeekStart;
	}

	public void setSowingWeekStart(Integer sowingWeekStart) {
		this.sowingWeekStart = sowingWeekStart;
	}

	public Integer getSowingWeekend() {
		return sowingWeekend;
	}

	public void setSowingWeekend(Integer sowingWeekend) {
		this.sowingWeekend = sowingWeekend;
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getStressId() {
		return stressId;
	}

	public void setStressId(Integer stressId) {
		this.stressId = stressId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public String getSciName() {
//		return sciName;
//	}
//
//	public void setSciName(String sciName) {
//		this.sciName = sciName;
//	}

	public Integer getCommodityId() {
		return commodityId;
	}

	public Integer getStartDas() {
		return startDas;
	}

	public void setStartDas(Integer startDas) {
		this.startDas = startDas;
	}

	public Integer getEndDas() {
		return endDas;
	}

	public void setEndDas(Integer endDas) {
		this.endDas = endDas;
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
		result = prime * result + ((endDas == null) ? 0 : endDas.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
//		result = prime * result + ((sciName == null) ? 0 : sciName.hashCode());
		result = prime * result + ((startDas == null) ? 0 : startDas.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((stressTypeId == null) ? 0 : stressTypeId.hashCode());
		result = prime * result + ((aczId == null) ? 0 : aczId.hashCode());
		result = prime * result + ((sowingWeekStart == null) ? 0 : sowingWeekStart.hashCode());
		result = prime * result + ((sowingWeekend == null) ? 0 : sowingWeekend.hashCode());
		result = prime * result + ((stressId == null) ? 0 : stressId.hashCode());
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
		Stress other = (Stress) obj;
		if (commodityId == null) {
			if (other.commodityId != null)
				return false;
		} else if (!commodityId.equals(other.commodityId))
			return false;
		if (endDas == null) {
			if (other.endDas != null)
				return false;
		} else if (!endDas.equals(other.endDas))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
//		if (sciName == null) {
//			if (other.sciName != null)
//				return false;
//		} else if (!sciName.equals(other.sciName))
//			return false;
		if (startDas == null) {
			if (other.startDas != null)
				return false;
		} else if (!startDas.equals(other.startDas))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (stressTypeId == null) {
			if (other.stressTypeId != null)
				return false;
		} else if (!stressTypeId.equals(other.stressTypeId))
			return false;
		if (aczId == null) {
			if (other.aczId != null)
				return false;
		} else if (!aczId.equals(other.aczId))
			return false;
		if (sowingWeekStart == null) {
			if (other.sowingWeekStart != null)
				return false;
		} else if (!sowingWeekStart.equals(other.sowingWeekStart))
			return false;
		if (sowingWeekend == null) {
			if (other.sowingWeekend != null)
				return false;
		} else if (!sowingWeekend.equals(other.sowingWeekend))
			return false;
		if (stressId == null) {
			if (other.stressId != null)
				return false;
		} else if (!stressId.equals(other.stressId))
			return false;
		return true;
	}

}
