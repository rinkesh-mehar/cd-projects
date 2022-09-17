package com.krishi.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishi.entity.Village;


@JsonIgnoreProperties(ignoreUnknown = true)
public class VillageModel implements Serializable {
	
	
	
	
	private static final long serialVersionUID = 1L;
	@JsonProperty
	private Integer ID;
	/*@JsonProperty
	private Integer StateCode;*/
	@JsonProperty
	private Integer DistrictCode;
	@JsonProperty
	private Integer TehsilCode;
	@JsonProperty
	private Integer PanchayatCode;
	@JsonProperty
	private Integer VillageCode;
	@JsonProperty
	private Integer RegionID;
	@JsonProperty
	private String SubRegionID;
	@JsonProperty
	private String Name;
	@JsonProperty
	private Integer PIN;
	@JsonProperty
	private BigDecimal Latitude;
	@JsonProperty
	private BigDecimal Longitude;
	@JsonProperty
	private String Status;
	@JsonProperty
	private Integer AczID;

	public Integer getAczID() {
		return AczID;
	}

	public void setAczID(Integer aczID) {
		this.AczID = aczID;
	}

	/** added synch processes - Ujwal : Start */
	/**  Ring Number */
	@JsonProperty
	private Integer RingNumber;
	
	public Integer getRingNumber() {
		return RingNumber;
	}
	public void setRingNumber(Integer ringNumber) {
		this.RingNumber = ringNumber;
	}
	/** added synch processes - Ujwal : End */
	
	public VillageModel() {
		super();
	}
	
	/** added ring number -Ujwal */
	public VillageModel(Integer iD, Integer districtCode, Integer tehsilCode, Integer panchayatCode,
			Integer villageCode, Integer regionID, String subRegionID, String name, Integer pIN, BigDecimal latitude,
			BigDecimal longitude, Integer ringNumber, String status, Integer aczID) {
		super();
		ID = iD;
//		StateCode = stateCode;
		DistrictCode = districtCode;
		TehsilCode = tehsilCode;
		PanchayatCode = panchayatCode;
		VillageCode = villageCode;
		RegionID = regionID;
		SubRegionID = subRegionID;
		RingNumber = ringNumber;
		Name = name;
		PIN = pIN;
		Latitude = latitude;
		Longitude = longitude;
		Status = status;
		AczID = aczID;
	}
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
//	public Integer getStateCode() {
//		return StateCode;
//	}
//	public void setStateCode(Integer stateCode) {
//		StateCode = stateCode;
//	}
	public Integer getDistrictCode() {
		return DistrictCode;
	}
	public void setDistrictCode(Integer districtCode) {
		DistrictCode = districtCode;
	}
	public Integer getTehsilCode() {
		return TehsilCode;
	}
	public void setTehsilCode(Integer tehsilCode) {
		TehsilCode = tehsilCode;
	}
	public Integer getPanchayatCode() {
		return PanchayatCode;
	}
	public void setPanchayatCode(Integer panchayatCode) {
		PanchayatCode = panchayatCode;
	}
	public Integer getVillageCode() {
		return VillageCode;
	}
	public void setVillageCode(Integer villageCode) {
		VillageCode = villageCode;
	}
	public Integer getRegionID() {
		return RegionID;
	}
	public void setRegionID(Integer regionID) {
		RegionID = regionID;
	}
	public String getSubRegionID() {
		return SubRegionID;
	}
	public void setSubRegionID(String subRegionID) {
		SubRegionID = subRegionID;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public Integer getPIN() {
		return PIN;
	}
	public void setPIN(Integer pIN) {
		PIN = pIN;
	}
	
	
	
	public BigDecimal getLatitude() {
		return Latitude;
	}
	public void setLatitude(BigDecimal latitude) {
		Latitude = latitude;
	}
	public BigDecimal getLongitude() {
		return Longitude;
	}
	public void setLongitude(BigDecimal longitude) {
		Longitude = longitude;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}

	@Override
	public String toString() {
		return "VillageModel{" +
				"ID=" + ID +
				", DistrictCode=" + DistrictCode +
				", TehsilCode=" + TehsilCode +
				", PanchayatCode=" + PanchayatCode +
				", VillageCode=" + VillageCode +
				", RegionID=" + RegionID +
				", SubRegionID='" + SubRegionID + '\'' +
				", Name='" + Name + '\'' +
				", PIN=" + PIN +
				", Latitude=" + Latitude +
				", Longitude=" + Longitude +
				", Status='" + Status + '\'' +
				", AczID=" + AczID +
				", RingNumber=" + RingNumber +
				'}';
	}

	/** added ring number -Ujwal */

	

	
	public Village getEntity(){
		
		Village village = new Village();

		village.setId(ID);
		village.setCode(VillageCode);
		village.setLatitude(Latitude);
		village.setLongitude(Longitude);
		village.setCode(VillageCode);
		village.setName(Name);
		village.setPincode(PIN);
		village.setPanchayatId(PanchayatCode);
		village.setRegionId(RegionID);
		village.setSubregionId(SubRegionID);
		village.setStatus(Status != null && Status.equalsIgnoreCase("Active") ? 1 : 0);
		village.setRingNumber(RingNumber);
		village.setAczId(AczID);
		return village;

		
	}


}
