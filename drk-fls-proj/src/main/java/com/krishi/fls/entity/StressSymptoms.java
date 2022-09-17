package com.krishi.fls.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="stress_symptoms")
public class StressSymptoms {
	
	@Id
	private Integer id;

	@Column(name="description")
	private String desc;
	
	private String image;
	
	private Integer isImportant;
	
	private Integer stressId;
	
	private Integer commodityId;
	
	/*private int phenophaseId;*/
	
	private Integer plantPartId;
	
	private Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getIsImportant() {
		return isImportant;
	}

	public void setIsImportant(Integer isImportant) {
		this.isImportant = isImportant;
	}

	public Integer getStressId() {
		return stressId;
	}

	public void setStressId(Integer stressId) {
		this.stressId = stressId;
	}

	public Integer getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(Integer commodityId) {
		this.commodityId = commodityId;
	}

	/*public int getPhenophaseId() {
		return phenophaseId;
	}

	public void setPhenophaseId(int phenophaseId) {
		this.phenophaseId = phenophaseId;
	}
*/
	public Integer getPlantPartId() {
		return plantPartId;
	}

	public void setPlantPartId(Integer plantPartId) {
		this.plantPartId = plantPartId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	

}
