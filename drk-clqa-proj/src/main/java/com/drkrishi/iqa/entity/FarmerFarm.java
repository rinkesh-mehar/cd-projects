package com.drkrishi.iqa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="farmer_farm")
public class FarmerFarm {

    @Id
    @Column(name="id")
    private String id;
    @Column(name="farmer_id", precision=10)
    private String farmerId;
    @Column(length=255)
    private String name;
    @Column(name="village_id", precision=10)
    private Integer villageId;
    @Column(name="has_own_land", length=3)
    private Integer hasOwnLand;
    @Column(name="has_leased_land", length=3)
    private Integer hasLeasedLand;
    @Column(name="own_land", length=12)
    private Float ownLand;
    @Column(name="leased_out_land", length=12)
    private Float leasedOutLand;
    @Column(name="leased_in_land", length=12)
    private Float leasedInLand;
    @Column(name="own_land_documents")
    private String ownLandDocuments;
    @Column(name="leased_land_documents")
    private String leasedLandDocuments;
    
    
	public FarmerFarm() {
		super();
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getFarmerId() {
		return farmerId;
	}


	public void setFarmerId(String farmerId) {
		this.farmerId = farmerId;
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


	public Integer getHasOwnLand() {
		return hasOwnLand;
	}


	public void setHasOwnLand(Integer hasOwnLand) {
		this.hasOwnLand = hasOwnLand;
	}


	public Integer getHasLeasedLand() {
		return hasLeasedLand;
	}


	public void setHasLeasedLand(Integer hasLeasedLand) {
		this.hasLeasedLand = hasLeasedLand;
	}


	public Float getOwnLand() {
		return ownLand;
	}


	public void setOwnLand(Float ownLand) {
		this.ownLand = ownLand;
	}


	public Float getLeasedOutLand() {
		return leasedOutLand;
	}


	public void setLeasedOutLand(Float leasedOutLand) {
		this.leasedOutLand = leasedOutLand;
	}


	public Float getLeasedInLand() {
		return leasedInLand;
	}


	public void setLeasedInLand(Float leasedInLand) {
		this.leasedInLand = leasedInLand;
	}


	public String getOwnLandDocuments() {
		return ownLandDocuments;
	}


	public void setOwnLandDocuments(String ownLandDocuments) {
		this.ownLandDocuments = ownLandDocuments;
	}


	public String getLeasedLandDocuments() {
		return leasedLandDocuments;
	}


	public void setLeasedLandDocuments(String leasedLandDocuments) {
		this.leasedLandDocuments = leasedLandDocuments;
	}
}
