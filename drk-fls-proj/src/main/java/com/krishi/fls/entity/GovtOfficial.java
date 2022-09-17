

package com.krishi.fls.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="govt_official")
public class GovtOfficial implements Serializable {

    /** Primary key. */
    protected static final String PK = "govtOfficialId";

    @Id
    @Column(name="id")
    private String govtOfficialId;
    @Column(name="alternate_mob_number", precision=19)
    private String alternateMobNumber;
    @Column(name="govt_department_id", precision=10)
    private int govtDepartmentId;
    @Column(name="govt_designation_id", precision=10)
    private int govtDesignationId;
    @Column(length=255)
    private String name;
    @Column(name="primary_mob_number", precision=19)
    private String primaryMobNumber;
    @Column(name="village_id", nullable=false, precision=10)
    private int villageId;

    /** Default constructor. */
    public GovtOfficial() {
        super();
    }

    /**
     * Access method for govtOfficialId.
     *
     * @return the current value of govtOfficialId
     */
    public String getGovtOfficialId() {
        return govtOfficialId;
    }

    /**
     * Setter method for govtOfficialId.
     *
     * @param aGovtOfficialId the new value for govtOfficialId
     */
    public void setGovtOfficialId(String aGovtOfficialId) {
        govtOfficialId = aGovtOfficialId;
    }

    /**
     * Access method for alternateMobNumber.
     *
     * @return the current value of alternateMobNumber
     */
    public String getAlternateMobNumber() {
        return alternateMobNumber;
    }

    /**
     * Setter method for alternateMobNumber.
     *
     * @param aAlternateMobNumber the new value for alternateMobNumber
     */
    public void setAlternateMobNumber(String aAlternateMobNumber) {
        alternateMobNumber = aAlternateMobNumber;
    }

    /**
     * Access method for govtDepartmentId.
     *
     * @return the current value of govtDepartmentId
     */
    public int getGovtDepartmentId() {
        return govtDepartmentId;
    }

    /**
     * Setter method for govtDepartmentId.
     *
     * @param aGovtDepartmentId the new value for govtDepartmentId
     */
    public void setGovtDepartmentId(int aGovtDepartmentId) {
        govtDepartmentId = aGovtDepartmentId;
    }

    /**
     * Access method for govtDesignationId.
     *
     * @return the current value of govtDesignationId
     */
    public int getGovtDesignationId() {
        return govtDesignationId;
    }

    /**
     * Setter method for govtDesignationId.
     *
     * @param aGovtDesignationId the new value for govtDesignationId
     */
    public void setGovtDesignationId(int aGovtDesignationId) {
        govtDesignationId = aGovtDesignationId;
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
     * Access method for primaryMobNumber.
     *
     * @return the current value of primaryMobNumber
     */
    public String getPrimaryMobNumber() {
        return primaryMobNumber;
    }

    /**
     * Setter method for primaryMobNumber.
     *
     * @param aPrimaryMobNumber the new value for primaryMobNumber
     */
    public void setPrimaryMobNumber(String aPrimaryMobNumber) {
        primaryMobNumber = aPrimaryMobNumber;
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
