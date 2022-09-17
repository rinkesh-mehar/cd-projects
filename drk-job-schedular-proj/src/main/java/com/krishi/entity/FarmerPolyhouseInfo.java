package com.krishi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.krishi.model.EntityModel;

import java.io.Serializable;

@Entity
@Table(name="farmer_polyhouse_info")
public class FarmerPolyhouseInfo implements Serializable, EntityModel {

    /** Primary key. */
    protected static final String PK = "id";

    @Id
   	@Column(name="id")
   	private String id;
    @Column(name="farmer_id", precision=10)
    private String farmerId;
    @Column(name="has_polyhouse", length=3)
    private boolean hasPolyhouse;
    @Column(name="allied_activity", length=50)
    private String alliedActivity;
    @Column(name="cattle_count", precision=10)
    private int cattleCount;
    @Column(name="pond_count", precision=10)
    private int pondCount;
    @Column(name="shed_count", precision=10)
    private int shedCount;

    /** Default constructor. */
    public FarmerPolyhouseInfo() {
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
     * Access method for hasPolyhouse.
     *
     * @return true if and only if hasPolyhouse is currently true
     */
    public boolean getHasPolyhouse() {
        return hasPolyhouse;
    }

    /**
     * Setter method for hasPolyhouse.
     *
     * @param aHasPolyhouse the new value for hasPolyhouse
     */
    public void setHasPolyhouse(boolean aHasPolyhouse) {
        hasPolyhouse = aHasPolyhouse;
    }

    /**
     * Access method for alliedActivity.
     *
     * @return the current value of alliedActivity
     */
    public String getAlliedActivity() {
        return alliedActivity;
    }

    /**
     * Setter method for alliedActivity.
     *
     * @param aAlliedActivity the new value for alliedActivity
     */
    public void setAlliedActivity(String aAlliedActivity) {
        alliedActivity = aAlliedActivity;
    }

    /**
     * Access method for cattleCount.
     *
     * @return the current value of cattleCount
     */
    public int getCattleCount() {
        return cattleCount;
    }

    /**
     * Setter method for cattleCount.
     *
     * @param aCattleCount the new value for cattleCount
     */
    public void setCattleCount(int aCattleCount) {
        cattleCount = aCattleCount;
    }

    /**
     * Access method for pondCount.
     *
     * @return the current value of pondCount
     */
    public int getPondCount() {
        return pondCount;
    }

    /**
     * Setter method for pondCount.
     *
     * @param aPondCount the new value for pondCount
     */
    public void setPondCount(int aPondCount) {
        pondCount = aPondCount;
    }

    /**
     * Access method for shedCount.
     *
     * @return the current value of shedCount
     */
    public int getShedCount() {
        return shedCount;
    }

    /**
     * Setter method for shedCount.
     *
     * @param aShedCount the new value for shedCount
     */
    public void setShedCount(int aShedCount) {
        shedCount = aShedCount;
    }



}
