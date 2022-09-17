package com.drkrishi.prm.model;

public class VillageModel {

	private Integer villageCode;
	private String villageName;

	public VillageModel() {
		super();
	}

	public Integer getVillageCode() {
		return villageCode;
	}

	public void setVillageCode(Integer villageCode) {
		this.villageCode = villageCode;
	}

	public String getVillageName() {
		return villageName;
	}

	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}

	@Override
	public String toString() {
		return "VillageModel [villageCode=" + villageCode + ", villageName=" + villageName + ", getVillageCode()="
				+ getVillageCode() + ", getVillageName()=" + getVillageName() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

}
