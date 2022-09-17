
package com.krishi.fls.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "poi")
public class Poi implements Serializable {

	/** Primary key. */
	protected static final String PK = "poiId";

	@Id
	@Column(name = "id")
	private String poiId;


	private Double lattitude;

	private Double longitude;

	private String name;

	@Column(name = "village_id")
	private Integer villageId;
	@Column(name = "poi_type_id")
	private Integer poiTypeId;
	public String getPoiId() {
		return poiId;
	}
	public void setPoiId(String poiId) {
		this.poiId = poiId;
	}
	public Double getLattitude() {
		return lattitude;
	}
	public void setLattitude(Double lattitude) {
		this.lattitude = lattitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getVillageId() {
		return villageId;
	}
	public void setVillageId(Integer villageId) {
		this.villageId = villageId;
	}
	
	public static String getPk() {
		return PK;
	}
	public Integer getPoiTypeId() {
		return poiTypeId;
	}
	public void setPoiTypeId(Integer poiTypeId) {
		this.poiTypeId = poiTypeId;
	}

	
}
