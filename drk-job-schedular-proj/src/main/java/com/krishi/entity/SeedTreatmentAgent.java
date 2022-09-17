package com.krishi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "seed_treatment_agent")
public class SeedTreatmentAgent {
	@Id
	@Column(unique = true)
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "variety_id")
	private Integer VarietyID;

	@Column(name = "dose")
	private Integer dose;

	@Column(name = "uom_id")
	private Integer uomId;

	@Column(name = "status")
	private Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getVarietyID() {
		return VarietyID;
	}

	public void setVarietyID(Integer varietyID) {
		VarietyID = varietyID;
	}

	public Integer getDose() {
		return dose;
	}

	public void setDose(Integer dose) {
		this.dose = dose;
	}

	public Integer getUomId() {
		return uomId;
	}

	public void setUomId(Integer uomId) {
		this.uomId = uomId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((VarietyID == null) ? 0 : VarietyID.hashCode());
		result = prime * result + ((dose == null) ? 0 : dose.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		SeedTreatmentAgent other = (SeedTreatmentAgent) obj;
		if (VarietyID == null) {
			if (other.VarietyID != null)
				return false;
		} else if (!VarietyID.equals(other.VarietyID))
			return false;
		if (dose == null) {
			if (other.dose != null)
				return false;
		} else if (!dose.equals(other.dose))
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
		if (uomId == null) {
			if (other.uomId != null)
				return false;
		} else if (!uomId.equals(other.uomId))
			return false;
		return true;
	}

}
