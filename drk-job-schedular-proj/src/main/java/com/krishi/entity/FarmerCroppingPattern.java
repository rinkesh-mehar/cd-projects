package com.krishi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.krishi.model.EntityModel;

import java.io.Serializable;

@Entity
@Table(name="farmer_cropping_pattern")
public class FarmerCroppingPattern implements Serializable, EntityModel {

    /** Primary key. */
    protected static final String PK = "id";

    @Id
    @Column(unique=true)
    private String id;
    @Column(name="year")
    private int year;
    @Column(name="farmer_id")
    private String farmerId;
    
//    ACZ introduced changes : CDT Rinkesh KM
//    @Column(name="season_id")
//    private int seasonId;
    @Column(name="sowing_week")
    private Integer sowingWeek;
    @Column(name="commodity_id")
    private int commodityId;
    @Column(name="area")
    private int area;
    @Column(name="yield")
    private int yield;
    @Column(name="residue_dispose_type_id")
    private int residueDisposeTypeId;

    /** Default constructor. */
    public FarmerCroppingPattern() {
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
     * Access method for year.
     *
     * @return the current value of year
     */
    public int getYear() {
        return year;
    }

    /**
     * Setter method for year.
     *
     * @param aYear the new value for year
     */
    public void setYear(int aYear) {
        year = aYear;
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
     * Access method for seasonId.
     *
     * @return the current value of seasonId
     */
//    public int getSeasonId() {
//        return seasonId;
//    }

    /**
     * Setter method for seasonId.
     *
     * @param aSeasonId the new value for seasonId
     */
//    public void setSeasonId(int aSeasonId) {
//        seasonId = aSeasonId;
//    }

    public Integer getSowingWeek() {
        return sowingWeek;
    }

    public void setSowingWeek(Integer sowingWeek) {
        this.sowingWeek = sowingWeek;
    }
    
    /**
     * Access method for commodityId.
     *
     * @return the current value of commodityId
     */
    public int getCommodityId() {
        return commodityId;
    }

    /**
     * Setter method for commodityId.
     *
     * @param aCommodityId the new value for commodityId
     */
    public void setCommodityId(int aCommodityId) {
        commodityId = aCommodityId;
    }

    /**
     * Access method for area.
     *
     * @return the current value of area
     */
    public int getArea() {
        return area;
    }

    /**
     * Setter method for area.
     *
     * @param aArea the new value for area
     */
    public void setArea(int aArea) {
        area = aArea;
    }

    /**
     * Access method for yield.
     *
     * @return the current value of yield
     */
    public int getYield() {
        return yield;
    }

    /**
     * Setter method for yield.
     *
     * @param aYield the new value for yield
     */
    public void setYield(int aYield) {
        yield = aYield;
    }

    /**
     * Access method for residueDisposeTypeId.
     *
     * @return the current value of residueDisposeTypeId
     */
    public int getResidueDisposeTypeId() {
        return residueDisposeTypeId;
    }

    /**
     * Setter method for residueDisposeTypeId.
     *
     * @param aResidueDisposeTypeId the new value for residueDisposeTypeId
     */
    public void setResidueDisposeTypeId(int aResidueDisposeTypeId) {
        residueDisposeTypeId = aResidueDisposeTypeId;
    }

    /**
     * Compares the key for this instance with another FarmerCroppingPattern.
     *
     * @param other The object to compare to
     * @return True if other object is instance of class FarmerCroppingPattern and the key objects are equal
     */
    private boolean equalKeys(Object other) {
        if (this==other) {
            return true;
        }
        if (!(other instanceof FarmerCroppingPattern)) {
            return false;
        }
        FarmerCroppingPattern that = (FarmerCroppingPattern) other;
        if (this.getId() != that.getId()) {
            return false;
        }
        return true;
    }

    /**
     * Compares this instance with another FarmerCroppingPattern.
     *
     * @param other The object to compare to
     * @return True if the objects are the same
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof FarmerCroppingPattern)) return false;
        return this.equalKeys(other) && ((FarmerCroppingPattern)other).equalKeys(this);
    }

 
 

}
