package com.krishi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "stress_symptoms")
public class StressSymptoms {

	@Id
	private int id;

	@Column(name = "description")
	private String symptomDesc;

	@Column(name = "image")
	private String image;

	@Column(name = "is_important")
	private boolean isImportant;

	@Column(name = "stress_id")
	private int stressId;
	@Column(name = "commodity_id")
	private int commodityId;

//	@Column(name = "phenophase_id")
//	private int phenophaseId;

	@Column(name = "plant_part_id")
	private int plantPartId;

	@Column(name = "status")
	private int status;

	/** added sync processes - Ujwal :Start */
	@Column(name = "stress_type_id")
	private Integer stressTypeId;

//	@Column(name = "stress_stage_id")
//	private Integer stressStageId;

	@Column(name = "generic_image_id")
	private Integer genericImageId;

	public Integer getStressTypeId() {
		return stressTypeId;
	}

	public void setStressTypeId(Integer stressTypeId) {
		this.stressTypeId = stressTypeId;
	}

	/*public Integer getStressStageId() {
		return stressStageId;
	}

	public void setStressStageId(Integer stressStageId) {
		this.stressStageId = stressStageId;
	}*/

	public Integer getGenericImageId() {
		return genericImageId;
	}

	public void setGenericImageId(Integer genericImageId) {
		this.genericImageId = genericImageId;
	}

	/** added sync processes - Ujwal :End */

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSymptomDesc() {
		return symptomDesc;
	}

	public void setSymptomDesc(String symptomDesc) {
		this.symptomDesc = symptomDesc;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public boolean isImportant() {
		return isImportant;
	}

	public void setImportant(boolean isImportant) {
		this.isImportant = isImportant;
	}

	public int getStressId() {
		return stressId;
	}

	public void setStressId(int stressId) {
		this.stressId = stressId;
	}

	public int getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(int commodityId) {
		this.commodityId = commodityId;
	}

/*
	public int getPhenophaseId() {
		return phenophaseId;
	}

	public void setPhenophaseId(int phenophaseId) {
		this.phenophaseId = phenophaseId;
	}
*/
	public int getPlantPartId() {
		return plantPartId;
	}

	public void setPlantPartId(int plantPartId) {
		this.plantPartId = plantPartId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + commodityId;
		result = prime * result + id;
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + (isImportant ? 1231 : 1237);
//		result = prime * result + phenophaseId;
		result = prime * result + plantPartId;
		result = prime * result + status;
		result = prime * result + stressId;
		result = prime * result + ((symptomDesc == null) ? 0 : symptomDesc.hashCode());
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
		StressSymptoms other = (StressSymptoms) obj;
		if (commodityId != other.commodityId)
			return false;
		if (id != other.id)
			return false;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		if (isImportant != other.isImportant)
			return false;
//		if (phenophaseId != other.phenophaseId)
//			return false;
		if (plantPartId != other.plantPartId)
			return false;
		if (status != other.status)
			return false;
		if (stressId != other.stressId)
			return false;
		if (symptomDesc == null) {
			if (other.symptomDesc != null)
				return false;
		} else if (!symptomDesc.equals(other.symptomDesc))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StressSymptoms [id=" + id + ", symptomDesc=" + symptomDesc + ", image=" + image + ", isImportant="
				+ isImportant + ", stressId=" + stressId + ", commodityId=" + commodityId + ", phenophaseId="
				/*+ phenophaseId + ", plantPartId="*/ + plantPartId + ", status=" + status + "]";
	}

	
}
