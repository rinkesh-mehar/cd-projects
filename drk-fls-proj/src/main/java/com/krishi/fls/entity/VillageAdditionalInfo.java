package com.krishi.fls.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "village_additional_info")
public class VillageAdditionalInfo {
	@Id
	private String id;
	
	@Column(name = "village_id")
	private Integer villageId;
	
	@Column(name = "farmer_count")
	private Integer farmerCount;

	@Override
	public String toString() {
		return "VillageAdditionalInfo [id=" + id + ", villageId=" + villageId + ", farmerCount=" + farmerCount + "]";
	}

	public VillageAdditionalInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getVillageId() {
		return villageId;
	}

	public void setVillageId(Integer villageId) {
		this.villageId = villageId;
	}

	public Integer getFarmerCount() {
		return farmerCount;
	}

	public void setFarmerCount(Integer farmerCount) {
		this.farmerCount = farmerCount;
	}
	
}
