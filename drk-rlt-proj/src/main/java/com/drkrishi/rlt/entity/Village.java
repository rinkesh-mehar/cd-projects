// Generated with g9.

package com.drkrishi.rlt.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "village")
public class Village implements Serializable {

	@Id
	@Column(unique = true, nullable = false, precision = 10)
	private int id;
	@Column(name = "panchayat_id", precision = 10)
	private int panchayatId;
	@Column(precision = 10)
	private int code;
	@Column(name = "region_id", precision = 10)
	private int regionId;
	@Column(name = "subregion_id", precision = 10)
	private int subregionId;
	@Column(length = 200)
	private String name;
	@Column(precision = 10)
	private int pincode;
	@Column(precision = 19, scale = 8)
	private BigDecimal latitude;
	@Column(precision = 19, scale = 8)
	private BigDecimal longitude;
	@Column(length = 255)
	private String comment;
	@Column(nullable = false, precision = 10)
	private int status;
	@Column(name = "farmer_count", precision = 10)
	private int farmerCount;

	public Village() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int aId) {
		id = aId;
	}

	public int getPanchayatId() {
		return panchayatId;
	}

	public void setPanchayatId(int aPanchayatId) {
		panchayatId = aPanchayatId;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int aCode) {
		code = aCode;
	}

	public int getRegionId() {
		return regionId;
	}

	public void setRegionId(int aRegionId) {
		regionId = aRegionId;
	}

	public int getSubregionId() {
		return subregionId;
	}

	public void setSubregionId(int aSubregionId) {
		subregionId = aSubregionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String aName) {
		name = aName;
	}

	public int getPincode() {
		return pincode;
	}

	public void setPincode(int aPincode) {
		pincode = aPincode;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal aLatitude) {
		latitude = aLatitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal aLongitude) {
		longitude = aLongitude;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String aComment) {
		comment = aComment;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int aStatus) {
		status = aStatus;
	}

	public int getFarmerCount() {
		return farmerCount;
	}

	public void setFarmerCount(int farmerCount) {
		this.farmerCount = farmerCount;
	}

}
