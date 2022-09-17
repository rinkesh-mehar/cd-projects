package com.krishi.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.krishi.model.EntityModel;

@Entity
@Table(name="farmer_farm")
public class FarmerFarm implements Serializable, EntityModel {

    /** Primary key. */
    protected static final String PK = "id";

    @Id
    @Column(name="id")
    private String id;
    @Column(name="farmer_id", precision=10)
    private String farmerId;
    @Column(length=255)
    private String name;
    @Column(name="village_id", precision=10)
    private int villageId;
    @Column(name="has_own_land", length=3)
    private boolean hasOwnLand;
    @Column(name="has_leased_land", length=3)
    private boolean hasLeasedLand;
    @Column(name="own_land", length=12)
    private float ownLand;
    @Column(name="leased_out_land", length=12)
    private float leasedOutLand;
    @Column(name="leased_in_land", length=12)
    private float leasedInLand;
    @Column(name="own_land_documents")
    private String ownLandDocuments;
    @Column(name="leased_land_documents")
    private String leasedLandDocuments;

    /** Default constructor. */
    public FarmerFarm() {
        super();
    }

    /**
     * Access method for id.
     *
     * @return the current value of id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter method for id.
     *
     * @param aId the new value for id
     */
    public void setId(String aId) {
        id = aId;
    }

    /**
     * Access method for farmerId.
     *
     * @return the current value of farmerId
     */
    public String getFarmerId() {
        return farmerId;
    }

    /**
     * Setter method for farmerId.
     *
     * @param aFarmerId the new value for farmerId
     */
    public void setFarmerId(String aFarmerId) {
        farmerId = aFarmerId;
    }

    /**
     * Access method for name.
     *
     * @return the current value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for name.
     *
     * @param aName the new value for name
     */
    public void setName(String aName) {
        name = aName;
    }

    /**
     * Access method for villageId.
     *
     * @return the current value of villageId
     */
    public int getVillageId() {
        return villageId;
    }

    /**
     * Setter method for villageId.
     *
     * @param aVillageId the new value for villageId
     */
    public void setVillageId(int aVillageId) {
        villageId = aVillageId;
    }

    /**
     * Access method for hasOwnLand.
     *
     * @return true if and only if hasOwnLand is currently true
     */
    public boolean getHasOwnLand() {
        return hasOwnLand;
    }

    /**
     * Setter method for hasOwnLand.
     *
     * @param aHasOwnLand the new value for hasOwnLand
     */
    public void setHasOwnLand(boolean aHasOwnLand) {
        hasOwnLand = aHasOwnLand;
    }

    /**
     * Access method for hasLeasedLand.
     *
     * @return true if and only if hasLeasedLand is currently true
     */
    public boolean getHasLeasedLand() {
        return hasLeasedLand;
    }

    /**
     * Setter method for hasLeasedLand.
     *
     * @param aHasLeasedLand the new value for hasLeasedLand
     */
    public void setHasLeasedLand(boolean aHasLeasedLand) {
        hasLeasedLand = aHasLeasedLand;
    }

    /**
     * Access method for ownLand.
     *
     * @return the current value of ownLand
     */
    public float getOwnLand() {
        return ownLand;
    }

    /**
     * Setter method for ownLand.
     *
     * @param aOwnLand the new value for ownLand
     */
    public void setOwnLand(float aOwnLand) {
        ownLand = aOwnLand;
    }

    /**
     * Access method for leasedOutLand.
     *
     * @return the current value of leasedOutLand
     */
    public float getLeasedOutLand() {
        return leasedOutLand;
    }

    /**
     * Setter method for leasedOutLand.
     *
     * @param aLeasedOutLand the new value for leasedOutLand
     */
    public void setLeasedOutLand(float aLeasedOutLand) {
        leasedOutLand = aLeasedOutLand;
    }

    /**
     * Access method for leasedInLand.
     *
     * @return the current value of leasedInLand
     */
    public float getLeasedInLand() {
        return leasedInLand;
    }

    /**
     * Setter method for leasedInLand.
     *
     * @param aLeasedInLand the new value for leasedInLand
     */
    public void setLeasedInLand(float aLeasedInLand) {
        leasedInLand = aLeasedInLand;
    }

    /**
     * Access method for ownLandDocuments.
     *
     * @return the current value of ownLandDocuments
     */
    public String getOwnLandDocuments() {
        return ownLandDocuments;
    }

    /**
     * Setter method for ownLandDocuments.
     *
     * @param aOwnLandDocuments the new value for ownLandDocuments
     */
    public void setOwnLandDocuments(String aOwnLandDocuments) {
        ownLandDocuments = aOwnLandDocuments;
    }

    /**
     * Access method for leasedLandDocuments.
     *
     * @return the current value of leasedLandDocuments
     */
    public String getLeasedLandDocuments() {
        return leasedLandDocuments;
    }

    /**
     * Setter method for leasedLandDocuments.
     *
     * @param aLeasedLandDocuments the new value for leasedLandDocuments
     */
    public void setLeasedLandDocuments(String aLeasedLandDocuments) {
        leasedLandDocuments = aLeasedLandDocuments;
    }


}
