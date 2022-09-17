package com.krishi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mbep")
public class MbepEntity {

	@Id
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "state_code")
	private Integer stateCode;

	@Column(name = "region_id")
	private Integer regionId;
	
	@Column(name = "commodity_id")
	private Integer commodityId;

	@Column(name = "variety_id")
	private Integer varietyId;

	@Column(name = "price")
	private Double price;
	
	@Column(name = "band_id")
	private Integer bandId;

	@Column(name = "crop_type")
	private String cropType;

	@Column(name = "status")
	private Integer status;	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getVarietyId() {
		return varietyId;
	}

	public void setVarietyId(Integer varietyId) {
		this.varietyId = varietyId;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getStateCode() {
		return stateCode;
	}

	public void setStateCode(Integer stateCode) {
		this.stateCode = stateCode;
	}

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public Integer getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(Integer commodityId) {
		this.commodityId = commodityId;
	}

	public Integer getBandId() {
		return bandId;
	}

	public void setBandId(Integer bandId) {
		this.bandId = bandId;
	}

	public Integer getStatus() {
		return status;
	}

	public String getCropType() {
		return cropType;
	}

	public void setCropType(String cropType) {
		this.cropType = cropType;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((varietyId == null) ? 0 : varietyId.hashCode());
		result = prime * result + ((cropType == null) ? 0 : cropType.hashCode());
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
		MbepEntity other = (MbepEntity) obj;
		if (!id.equals(other.id))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (varietyId == null) {
			if (other.varietyId != null)
				return false;
		} else if (!varietyId.equals(other.varietyId))
			return false;
		if (cropType == null) {
			if (other.cropType != null)
				return false;
		} else if (!cropType.equals(other.cropType))
			return false;
		return true;
	}

}
