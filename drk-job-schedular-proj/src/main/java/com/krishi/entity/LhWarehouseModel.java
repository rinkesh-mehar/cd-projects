package com.krishi.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CDT-Ujwal
 */
@Entity
@Table(name = "lh_warehouse")
public class LhWarehouseModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique = true)
	@JsonProperty("ID")
	private Integer id;
	
	@Column(name = "name")
	@JsonProperty("Name")
	private String name;
	
	@Column(name = "address")
	@JsonProperty("Address")
	private String address;
	
	@Column(name = "region_id")
	@JsonProperty("RegionID")
	private Integer regionId;
	
	@Column(name = "state_code")
	@JsonProperty("StateCode")
	private Integer stateCode;
	
	@Column(name = "district_code")
	@JsonProperty("DistrictCode")
	private Integer districtCode;
	
	@Column(name = "latitude")
	@JsonProperty("Latitude")
	private Double latitude;
	
	@Column(name = "longitude")
	@JsonProperty("Longitude")
	private Double longitude;
	
	@Column(name = "status")
	@JsonProperty("Status")
	private Integer status;

	@Column(name = "lenght_ft")
	@JsonProperty("LenghtFT")
	private Double lenghtFt;

	@Column(name = "height_ft")
	@JsonProperty("HeightFT")
	private Double heightFt;

	@Column(name = "breadth_ft")
	@JsonProperty("BreadthFT")
	private Double breadthFt;

	@Column(name = "capacity_mt")
	@JsonProperty("CapacityMT")
	private Double capacityMt;

	@Column(name = "standard_vehicle_size")
	@JsonProperty("StandardVehicleSize")
	private Double standardVehicleSize;

	@Column(name = "bag_handling_time")
	@JsonProperty("BagHandlingTime")
	private Date bagHandlingTime;

	@Column(name = "vehicle_handling_time")
	@JsonProperty("VehicleHandlingTime")
	private Date vehicleHandlingTime;

	@Column(name = "right_handling_time")
	@JsonProperty("RightHandlingTime")
	private Date rightHandlingTime;

	@Column(name = "standard_bag_volume")
	@JsonProperty("StandardBagVolume")
	private Date standardBagVolume;

	public Double getLenghtFt() {
		return lenghtFt;
	}

	public void setLenghtFt(Double lenghtFt) {
		this.lenghtFt = lenghtFt;
	}

	public Double getHeightFt() {
		return heightFt;
	}

	public void setHeightFt(Double heightFt) {
		this.heightFt = heightFt;
	}

	public Double getBreadthFt() {
		return breadthFt;
	}

	public void setBreadthFt(Double breadthFt) {
		this.breadthFt = breadthFt;
	}

	public Double getCapacityMt() {
		return capacityMt;
	}

	public void setCapacityMt(Double capacityMt) {
		this.capacityMt = capacityMt;
	}

	public Double getStandardVehicleSize() {
		return standardVehicleSize;
	}

	public void setStandardVehicleSize(Double standardVehicleSize) {
		this.standardVehicleSize = standardVehicleSize;
	}

	public Date getBagHandlingTime() {
		return bagHandlingTime;
	}

	public void setBagHandlingTime(Date bagHandlingTime) {
		this.bagHandlingTime = bagHandlingTime;
	}

	public Date getVehicleHandlingTime() {
		return vehicleHandlingTime;
	}

	public void setVehicleHandlingTime(Date vehicleHandlingTime) {
		this.vehicleHandlingTime = vehicleHandlingTime;
	}

	public Date getRightHandlingTime() {
		return rightHandlingTime;
	}

	public void setRightHandlingTime(Date rightHandlingTime) {
		this.rightHandlingTime = rightHandlingTime;
	}

	public Date getStandardBagVolume() {
		return standardBagVolume;
	}

	public void setStandardBagVolume(Date standardBagVolume) {
		this.standardBagVolume = standardBagVolume;
	}

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public Integer getStateCode() {
		return stateCode;
	}

	public void setStateCode(Integer stateCode) {
		this.stateCode = stateCode;
	}

	public Integer getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(Integer districtCode) {
		this.districtCode = districtCode;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = ((status != null && status.equalsIgnoreCase("Active"))? 1 : 0);
	}

	

	@Override
	public String toString() {
		return "LhWarehouseModel [id=" + id + ", name=" + name + ", address=" + address + ", regionId=" + regionId
				+ ", stateCode=" + stateCode + ", districtCode=" + districtCode + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", status=" + status + ", lenghtFt=" + lenghtFt + ", heightFt="
				+ heightFt + ", breadthFt=" + breadthFt + ", capacityMt=" + capacityMt + ", standardVehicleSize="
				+ standardVehicleSize + ", bagHandlingTime=" + bagHandlingTime + ", vehicleHandlingTime="
				+ vehicleHandlingTime + ", rightHandlingTime=" + rightHandlingTime + ", standardBagVolume="
				+ standardBagVolume + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((bagHandlingTime == null) ? 0 : bagHandlingTime.hashCode());
		result = prime * result + ((breadthFt == null) ? 0 : breadthFt.hashCode());
		result = prime * result + ((capacityMt == null) ? 0 : capacityMt.hashCode());
		result = prime * result + ((districtCode == null) ? 0 : districtCode.hashCode());
		result = prime * result + ((heightFt == null) ? 0 : heightFt.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result + ((lenghtFt == null) ? 0 : lenghtFt.hashCode());
		result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((regionId == null) ? 0 : regionId.hashCode());
		result = prime * result + ((rightHandlingTime == null) ? 0 : rightHandlingTime.hashCode());
		result = prime * result + ((standardBagVolume == null) ? 0 : standardBagVolume.hashCode());
		result = prime * result + ((standardVehicleSize == null) ? 0 : standardVehicleSize.hashCode());
		result = prime * result + ((stateCode == null) ? 0 : stateCode.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((vehicleHandlingTime == null) ? 0 : vehicleHandlingTime.hashCode());
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
		LhWarehouseModel other = (LhWarehouseModel) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (bagHandlingTime == null) {
			if (other.bagHandlingTime != null)
				return false;
		} else if (!bagHandlingTime.equals(other.bagHandlingTime))
			return false;
		if (breadthFt == null) {
			if (other.breadthFt != null)
				return false;
		} else if (!breadthFt.equals(other.breadthFt))
			return false;
		if (capacityMt == null) {
			if (other.capacityMt != null)
				return false;
		} else if (!capacityMt.equals(other.capacityMt))
			return false;
		if (districtCode == null) {
			if (other.districtCode != null)
				return false;
		} else if (!districtCode.equals(other.districtCode))
			return false;
		if (heightFt == null) {
			if (other.heightFt != null)
				return false;
		} else if (!heightFt.equals(other.heightFt))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (lenghtFt == null) {
			if (other.lenghtFt != null)
				return false;
		} else if (!lenghtFt.equals(other.lenghtFt))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (regionId == null) {
			if (other.regionId != null)
				return false;
		} else if (!regionId.equals(other.regionId))
			return false;
		if (rightHandlingTime == null) {
			if (other.rightHandlingTime != null)
				return false;
		} else if (!rightHandlingTime.equals(other.rightHandlingTime))
			return false;
		if (standardBagVolume == null) {
			if (other.standardBagVolume != null)
				return false;
		} else if (!standardBagVolume.equals(other.standardBagVolume))
			return false;
		if (standardVehicleSize == null) {
			if (other.standardVehicleSize != null)
				return false;
		} else if (!standardVehicleSize.equals(other.standardVehicleSize))
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
		if (vehicleHandlingTime == null) {
			if (other.vehicleHandlingTime != null)
				return false;
		} else if (!vehicleHandlingTime.equals(other.vehicleHandlingTime))
			return false;
		return true;
	}

	

	
}
