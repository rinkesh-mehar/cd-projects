package com.krishi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.krishi.model.EntityModel;

import java.io.Serializable;

@Entity
@Table(name = "poi")
public class Poi implements Serializable, EntityModel {

    /** Primary key. */
    protected static final String PK = "poiId";

    @Id
    @Column(name="id")
    private String poiId;
    
    @Column(nullable=false, length=22)
    private double lattitude;
    
    @Column(nullable=false, length=22)
    private double longitude;
    
    @Column(length=255)
    private String name;
    
    @Column(name="village_id", precision=10)
    private int villageId;

	@Column(name="poi_type_id", precision=10)
    private int poiTypeId;

	
	
    public String getPoiId() {
		return poiId;
	}

	public void setPoiId(String poiId) {
		this.poiId = poiId;
	}

	public Poi() {
		super();
		// TODO Auto-generated constructor stub
	}

	public double getLattitude() {
		return lattitude;
	}

	public void setLattitude(double lattitude) {
		this.lattitude = lattitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getVillageId() {
		return villageId;
	}

	public void setVillageId(int villageId) {
		this.villageId = villageId;
	}

	public int getPoiTypeId() {
		return poiTypeId;
	}

	public void setPoiTypeId(int poiTypeId) {
		this.poiTypeId = poiTypeId;
	}

	public Poi(String poiId, double lattitude, double longitude, String name, int villageId, int poiTypeId) {
		
		this.poiId = poiId;
		this.lattitude = lattitude;
		this.longitude = longitude;
		this.name = name;
		this.villageId = villageId;
		this.poiTypeId = poiTypeId;
	}
    

	@Override
	public String toString() {
		return "Poi [poiId=" + poiId + ", lattitude=" + lattitude + ", longitude=" + longitude + ", name=" + name
				+ ", villageId=" + villageId + ", poiTypeId=" + poiTypeId + "]";
	}

	@Override
	public String getId() {
		return poiId;
	}

	@Override
	public void setId(String id) {
		this.poiId = id;
	}


}
