package com.drkrishi.iqa.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="case_crop_info")
public class CaseCropInfo implements Serializable {

    /** Primary key. */
    protected static final String PK = "id";

    @Id
    @Column(name="id")
    private String id;
    @Column(name="case_id")
    private String caseId;
    @Column(name="seed_source_id")
    private int seedSourceId;
    @Column(name="variety_id")
    private int varietyId;
    @Column(name="crop_area")
    private float cropArea;
    @Column(name="seeds_sample_received")
    private boolean seedsSampleReceived;
    @Column(name="seeds_rates")
    private float seedsRates;
    @Column(name="season_id")
    private int seasonId;
    @Column(name="uom_id")
    private int uomId;
    @Column(name="spacing_row")
    private float spacingRow;
    @Column(name="spacing_plant")
    private float spacingPlant;
    @Column(name="corrected_sowing_week")
    private int correctedSowingWeek;
    @Column(name="corrected_sowing_year")
    private int correctedSowingYear;

    /** Default constructor. */
    public CaseCropInfo() {
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
     * Access method for caseId.
     *
     * @return the current value of caseId
     */
    public String getCaseId() {
        return caseId;
    }

    /**
     * Setter method for caseId.
     *
     * @param aCaseId the new value for caseId
     */
    public void setCaseId(String aCaseId) {
        caseId = aCaseId;
    }

    /**
     * Access method for seedSourceId.
     *
     * @return the current value of seedSourceId
     */
    public int getSeedSourceId() {
        return seedSourceId;
    }

    /**
     * Setter method for seedSourceId.
     *
     * @param aSeedSourceId the new value for seedSourceId
     */
    public void setSeedSourceId(int aSeedSourceId) {
        seedSourceId = aSeedSourceId;
    }

    /**
     * Access method for varietyId.
     *
     * @return the current value of varietyId
     */
    public int getVarietyId() {
        return varietyId;
    }

    /**
     * Setter method for varietyId.
     *
     * @param aVarietyId the new value for varietyId
     */
    public void setVarietyId(int aVarietyId) {
        varietyId = aVarietyId;
    }

    /**
     * Access method for cropArea.
     *
     * @return the current value of cropArea
     */
    public float getCropArea() {
        return cropArea;
    }

    /**
     * Setter method for cropArea.
     *
     * @param aCropArea the new value for cropArea
     */
    public void setCropArea(float aCropArea) {
        cropArea = aCropArea;
    }

    /**
     * Access method for seedsSampleReceived.
     *
     * @return true if and only if seedsSampleReceived is currently true
     */
    public boolean getSeedsSampleReceived() {
        return seedsSampleReceived;
    }

    /**
     * Setter method for seedsSampleReceived.
     *
     * @param aSeedsSampleReceived the new value for seedsSampleReceived
     */
    public void setSeedsSampleReceived(boolean aSeedsSampleReceived) {
        seedsSampleReceived = aSeedsSampleReceived;
    }

    /**
     * Access method for seedsRates.
     *
     * @return the current value of seedsRates
     */
    public float getSeedsRates() {
        return seedsRates;
    }

    /**
     * Setter method for seedsRates.
     *
     * @param aSeedsRates the new value for seedsRates
     */
    public void setSeedsRates(float aSeedsRates) {
        seedsRates = aSeedsRates;
    }

    /**
     * Access method for seasonId.
     *
     * @return the current value of seasonId
     */
    public int getSeasonId() {
        return seasonId;
    }

    /**
     * Setter method for seasonId.
     *
     * @param aSeasonId the new value for seasonId
     */
    public void setSeasonId(int aSeasonId) {
        seasonId = aSeasonId;
    }

    /**
     * Access method for uomId.
     *
     * @return the current value of uomId
     */
    public int getUomId() {
        return uomId;
    }

    /**
     * Setter method for uomId.
     *
     * @param aUomId the new value for uomId
     */
    public void setUomId(int aUomId) {
        uomId = aUomId;
    }

    /**
     * Access method for spacingRow.
     *
     * @return the current value of spacingRow
     */
    public float getSpacingRow() {
        return spacingRow;
    }

    /**
     * Setter method for spacingRow.
     *
     * @param aSpacingRow the new value for spacingRow
     */
    public void setSpacingRow(float aSpacingRow) {
        spacingRow = aSpacingRow;
    }

    /**
     * Access method for spacingPlant.
     *
     * @return the current value of spacingPlant
     */
    public float getSpacingPlant() {
        return spacingPlant;
    }

    /**
     * Setter method for spacingPlant.
     *
     * @param aSpacingPlant the new value for spacingPlant
     */
    public void setSpacingPlant(float aSpacingPlant) {
        spacingPlant = aSpacingPlant;
    }

    /**
     * Access method for sowingWeek.
     *
     * @return the current value of sowingWeek
     */
    public int getCorrectedSowingWeek() {
        return correctedSowingWeek;
    }

    public void setCorrectedSowingWeek(int correctedSowingWeek) {
        this.correctedSowingWeek = correctedSowingWeek;
    }

    public int getCorrectedSowingYear() {
        return correctedSowingYear;
    }

    public void setCorrectedSowingYear(int correctedSowingYear) {
        this.correctedSowingYear = correctedSowingYear;
    }
}
