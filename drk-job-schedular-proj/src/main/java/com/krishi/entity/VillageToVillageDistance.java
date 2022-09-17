package com.krishi.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CDT-Ujwal
 *
 */
@Entity
@Table(name = "village_to_village_distance")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(content = Include.NON_EMPTY, value = Include.NON_NULL)
public class VillageToVillageDistance implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique = true)
	@JsonProperty("id")
	private Integer id;

	@Column(name = "village_one")
	@JsonProperty("village_one")
	private Integer villageOne;

	@Column(name = "village_two")
	@JsonProperty("village_two")
	private Integer villageTwo;

	@Column(name = "distance")
	@JsonProperty("distance")
	private Double distance;

	@Column(name = "status")
	@JsonProperty("status")
	private Integer status;

	@Transient
	private String createdAt;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getVillageOne() {
		return villageOne;
	}

	public void setVillageOne(Integer villageOne) {
		this.villageOne = villageOne;
	}

	public Integer getVillageTwo() {
		return villageTwo;
	}

	public void setVillageTwo(Integer villageTwo) {
		this.villageTwo = villageTwo;
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

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "VillageToVillageDistance [id=" + id + ", villageOne=" + villageOne + ", villageTwo=" + villageTwo
				+ ", distance=" + distance + ", status=" + status + ", createdAt=" + createdAt + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((distance == null) ? 0 : distance.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((villageOne == null) ? 0 : villageOne.hashCode());
		result = prime * result + ((villageTwo == null) ? 0 : villageTwo.hashCode());
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
		VillageToVillageDistance other = (VillageToVillageDistance) obj;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
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
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (villageOne == null) {
			if (other.villageOne != null)
				return false;
		} else if (!villageOne.equals(other.villageOne))
			return false;
		if (villageTwo == null) {
			if (other.villageTwo != null)
				return false;
		} else if (!villageTwo.equals(other.villageTwo))
			return false;
		return true;
	}

	
}
