/**
 * 
 */
package com.krishi.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CDT-Ujwal
 *
 */
@Entity
@Table(name = "village_to_rl_distance")
@JsonIgnoreProperties(ignoreUnknown = true)
public class VillageToRlDistance implements Serializable {
	

	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique = true)
	@JsonProperty("ID")
	private Integer id;

	@Column(name = "village_id")
	@JsonProperty("VillageID")
	private Integer villageID;
	
	@Column(name= "rl")
	@JsonProperty("RL")
	private Integer  rl;
	
	@Column(name = "distance")
	@JsonProperty("Distance")
	private Double distance;
	
	@Column(name = "status")
	@JsonProperty("Status")
	private Integer status;
	
	@Transient
	private Date created_at;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getVillageID() {
		return villageID;
	}

	public void setVillageID(Integer villageID) {
		this.villageID = villageID;
	}

	public Integer getRl() {
		return rl;
	}

	public void setRl(Integer rl) {
		this.rl = rl;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setStatus(String status) {
		this.status = ((status != null && status.equalsIgnoreCase("Active"))? 1 : 0);
	}
	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((created_at == null) ? 0 : created_at.hashCode());
		result = prime * result + ((distance == null) ? 0 : distance.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((rl == null) ? 0 : rl.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((villageID == null) ? 0 : villageID.hashCode());
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
		VillageToRlDistance other = (VillageToRlDistance) obj;
		if (created_at == null) {
			if (other.created_at != null)
				return false;
		} else if (!created_at.equals(other.created_at))
			return false;
		if (distance == null) {
			if (other.distance != null)
				return false;
		} else if (!distance.equals(other.distance))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (rl == null) {
			if (other.rl != null)
				return false;
		} else if (!rl.equals(other.rl))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (villageID == null) {
			if (other.villageID != null)
				return false;
		} else if (!villageID.equals(other.villageID))
			return false;
		return true;
	}
	
	
}
