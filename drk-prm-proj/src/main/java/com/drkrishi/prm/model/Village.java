package com.drkrishi.prm.model;

import com.drkrishi.prm.entity.RegionEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

public class Village {

	
@JsonInclude(JsonInclude.Include.NON_NULL)

//	private int villageId;
	
//	private int panchayatId;

//	private int villageCode;
	
//	private RegionEntity region;
	
//	private int subregionId;
	
	private String villageName;
	
//	private int pincode;
	
//	private float latitude;
	
//	private float longitude;
	
//	private String comments;

//	private int status;

	/*public int getVillageId() {
		return villageId;
	}

	public void setVillageId(int villageId) {
		this.villageId = villageId;
	}*/

	/*public int getPanchayatId() {
		return panchayatId;
	}

	public void setPanchayatId(int panchayatId) {
		this.panchayatId = panchayatId;
	}*/

	/*public int getVillageCode() {
		return villageCode;
	}

	public void setVillageCode(int villageCode) {
		this.villageCode = villageCode;
	}*/

	/*
	 * public RegionEntity getRegion() { return region; }
	 * 
	 * public void setRegion(RegionEntity region) { this.region = region; }
	 */

	/*public int getSubregionId() {
		return subregionId;
	}

	public void setSubregionId(int subregionId) {
		this.subregionId = subregionId;
	}*/

	public String getVillageName() {
		return villageName;
	}

	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}

	/*public int getPincode() {
		return pincode;
	}

	public void setPincode(int pincode) {
		this.pincode = pincode;
	}*/

	/*public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	} */

	/*
	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	} */

	/*public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	} */

	/*public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}*/

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		//result = prime * result + ((comments == null) ? 0 : comments.hashCode());
//		result = prime * result + Float.floatToIntBits(latitude);
//		result = prime * result + Float.floatToIntBits(longitude);
		//result = prime * result + panchayatId;
//		result = prime * result + pincode;
		//result = prime * result + ((region == null) ? 0 : region.hashCode());
		//result = prime * result + status;
//		result = prime * result + subregionId;
//		result = prime * result + villageCode;
//		result = prime * result + villageId;
		result = prime * result + ((villageName == null) ? 0 : villageName.hashCode());
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
		Village other = (Village) obj;
//		if (comments == null) {
//			if (other.comments != null)
//				return false;
//		} else if (!comments.equals(other.comments))
//			return false;
//		if (Float.floatToIntBits(latitude) != Float.floatToIntBits(other.latitude))
//			return false;
//		if (Float.floatToIntBits(longitude) != Float.floatToIntBits(other.longitude))
//			return false;
//		if (panchayatId != other.panchayatId)
//			return false;
//		if (pincode != other.pincode)
//			return false;
		/*
		 * if (region == null) { if (other.region != null) return false; } else if
		 * (!region.equals(other.region)) return false;
		 */
//		if (status != other.status)
//			return false;
//		if (subregionId != other.subregionId)
//			return false;
//		if (villageCode != other.villageCode)
//			return false;
//		if (villageId != other.villageId)
//			return false;
		if (villageName == null) {
			if (other.villageName != null)
				return false;
		} else if (!villageName.equals(other.villageName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Village [villageCode=" + ", region=" + null +  ", villageName=" + villageName + "]";
	}

	public Village(int villageId, int panchayatId, int villageCode, RegionEntity region, int subregionId,
			String villageName, int pincode, float latitude, float longitude, String comments, int status) {
		super();
//		this.villageId = villageId;
		//this.panchayatId = panchayatId;
//		this.villageCode = villageCode;
	//	this.region = region;
//		this.subregionId = subregionId;
		this.villageName = villageName;
//		this.pincode = pincode;
//		this.latitude = latitude;
//		this.longitude = longitude;
	//	this.comments = comments;
//		this.status = status;
	}

	public Village() {
		
	}

	

}