package com.krishi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder.In;

import org.springframework.batch.core.configuration.annotation.JobScope;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "fertilizer")
public class Fertilizer {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", unique = true, nullable = false, precision = 10)
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "status")
	private Integer status;

	@Column(name = "state_id")
	private Integer stateCode;

	@Column(name = "acz_id")
	private Integer aczId;

	@Column(name = "sowing_start_week")
	private Integer sowingWeekStart;

	@Column(name = "sowing_end_week")
	private Integer sowingWeekEnd;
	
	@Column(name = "dose_factor_id")
	private Integer doseFactorId;

	@Column(name = "commodity_id")
	private Integer commodityID;

	@Column(name = "uom_id")
	private Integer uomId;

	@Column(name = "dose")
	private Double dose;

	@Column(name = "note")
	private String note;

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

	public Integer getStateCode() {
		return stateCode;
	}

	public void setStateCode(Integer stateCode) {
		this.stateCode = stateCode;
	}

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

	public Integer getSowingWeekEnd() {
		return sowingWeekEnd;
	}

	public void setSowingWeekEnd(Integer sowingWeekEnd) {
		this.sowingWeekEnd = sowingWeekEnd;
	}

	public Integer getDoseFactorId() {
		return doseFactorId;
	}

	public void setDoseFactorId(Integer doseFactorId) {
		this.doseFactorId = doseFactorId;
	}

	public Integer getCommodityID() {
		return commodityID;
	}

	public void setCommodityID(Integer commodityID) {
		this.commodityID = commodityID;
	}

	public Integer getUomId() {
		return uomId;
	}

	public void setUomId(Integer uomId) {
		this.uomId = uomId;
	}

	public Double getDose() {
		return dose;
	}

	public void setDose(Double dose) {
		this.dose = dose;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commodityID == null) ? 0 : commodityID.hashCode());
		result = prime * result + ((dose == null) ? 0 : dose.hashCode());
		result = prime * result + ((doseFactorId == null) ? 0 : doseFactorId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((note == null) ? 0 : note.hashCode());
		result = prime * result + ((aczId == null) ? 0 : aczId.hashCode());
		result = prime * result + ((sowingWeekStart == null) ? 0 : sowingWeekStart.hashCode());
		result = prime * result + ((sowingWeekEnd == null) ? 0 : sowingWeekEnd.hashCode());
		result = prime * result + ((stateCode == null) ? 0 : stateCode.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((uomId == null) ? 0 : uomId.hashCode());
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
		Fertilizer other = (Fertilizer) obj;
		if (commodityID == null) {
			if (other.commodityID != null)
				return false;
		} else if (!commodityID.equals(other.commodityID))
			return false;
		if (dose == null) {
			if (other.dose != null)
				return false;
		} else if (!dose.equals(other.dose))
			return false;
		if (doseFactorId == null) {
			if (other.doseFactorId != null)
				return false;
		} else if (!doseFactorId.equals(other.doseFactorId))
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
		if (note == null) {
			if (other.note != null)
				return false;
		} else if (!note.equals(other.note))
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
		if (sowingWeekEnd == null) {
			if (other.sowingWeekEnd != null)
				return false;
		} else if (!sowingWeekEnd.equals(other.sowingWeekEnd))
			return false;
		if (stateCode == null) {
			if (other.stateCode != null)
				return false;
		} else if (!stateCode.equals(other.stateCode))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (uomId == null) {
			if (other.uomId != null)
				return false;
		} else if (!uomId.equals(other.uomId))
			return false;
		return true;
	}
	
	

}