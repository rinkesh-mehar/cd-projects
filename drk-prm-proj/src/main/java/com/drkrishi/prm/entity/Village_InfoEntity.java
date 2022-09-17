package com.drkrishi.prm.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="village_info")
public class Village_InfoEntity implements Serializable {

    /** Primary key. */
    protected static final String PK = "villageInfoId";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="village_info_id", unique=true, nullable=false, precision=10)
    private String villageInfoId;
    @Column(name="average_yeild_crop", length=22)
    private double averageYeildCrop;
    @Column(name="commodity_id", nullable=false, precision=10)
    private int commodityId;
    @Column(name="common_varities", precision=10)
    private int commonVarities;
    @Column(name="harvest_week", precision=10)
    private int harvestWeek;
    @Column(name="sowing_area_commodity", length=12)
    private float sowingAreaCommodity;
    @Column(name="sowing_week", precision=10)
    private int sowingWeek;
    @Column(name="varitiy_id", nullable=false, precision=10)
    private int varitiyId;
    @Column(name="village_id", precision=10)
    private int villageId;

    /** Default constructor. */
    public Village_InfoEntity() {
        super();
    }

    /**
     * Access method for villageInfoId.
     *
     * @return the current value of villageInfoId
     */
    public String getVillageInfoId() {
        return villageInfoId;
    }

    /**
     * Setter method for villageInfoId.
     *
     * @param aVillageInfoId the new value for villageInfoId
     */
    public void setVillageInfoId(String aVillageInfoId) {
        villageInfoId = aVillageInfoId;
    }

    /**
     * Access method for averageYeildCrop.
     *
     * @return the current value of averageYeildCrop
     */
    public double getAverageYeildCrop() {
        return averageYeildCrop;
    }

    /**
     * Setter method for averageYeildCrop.
     *
     * @param aAverageYeildCrop the new value for averageYeildCrop
     */
    public void setAverageYeildCrop(double aAverageYeildCrop) {
        averageYeildCrop = aAverageYeildCrop;
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
     * Access method for commonVarities.
     *
     * @return the current value of commonVarities
     */
    public int getCommonVarities() {
        return commonVarities;
    }

    /**
     * Setter method for commonVarities.
     *
     * @param aCommonVarities the new value for commonVarities
     */
    public void setCommonVarities(int aCommonVarities) {
        commonVarities = aCommonVarities;
    }

    /**
     * Access method for harvestWeek.
     *
     * @return the current value of harvestWeek
     */
    public int getHarvestWeek() {
        return harvestWeek;
    }

    /**
     * Setter method for harvestWeek.
     *
     * @param aHarvestWeek the new value for harvestWeek
     */
    public void setHarvestWeek(int aHarvestWeek) {
        harvestWeek = aHarvestWeek;
    }

    /**
     * Access method for sowingAreaCommodity.
     *
     * @return the current value of sowingAreaCommodity
     */
    public float getSowingAreaCommodity() {
        return sowingAreaCommodity;
    }

    /**
     * Setter method for sowingAreaCommodity.
     *
     * @param aSowingAreaCommodity the new value for sowingAreaCommodity
     */
    public void setSowingAreaCommodity(float aSowingAreaCommodity) {
        sowingAreaCommodity = aSowingAreaCommodity;
    }

    /**
     * Access method for sowingWeek.
     *
     * @return the current value of sowingWeek
     */
    public int getSowingWeek() {
        return sowingWeek;
    }

    /**
     * Setter method for sowingWeek.
     *
     * @param aSowingWeek the new value for sowingWeek
     */
    public void setSowingWeek(int aSowingWeek) {
        sowingWeek = aSowingWeek;
    }

    /**
     * Access method for varitiyId.
     *
     * @return the current value of varitiyId
     */
    public int getVaritiyId() {
        return varitiyId;
    }

    /**
     * Setter method for varitiyId.
     *
     * @param aVaritiyId the new value for varitiyId
     */
    public void setVaritiyId(int aVaritiyId) {
        varitiyId = aVaritiyId;
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
}
