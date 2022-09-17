// Generated with g9.

package com.drkrishi.rlt.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="case_kml")
public class CaseKml implements Serializable {

    /** Primary key. */
    protected static final String PK = "id";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique=true, nullable=false, precision=10)
    private int id;
    @Column(name="kml_file_path", nullable=false, length=255)
    private String kmlFilePath;
    @Column(name="farm_case_id", nullable=false, precision=10)
    private int farmCaseId;
    @Column(name="captured_on")
    private Timestamp capturedOn;
    @Column(name="captured_by", nullable=false, precision=10)
    private int capturedBy;

    /** Default constructor. */
    public CaseKml() {
        super();
    }

    /**
     * Access method for id.
     *
     * @return the current value of id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter method for id.
     *
     * @param aId the new value for id
     */
    public void setId(int aId) {
        id = aId;
    }

    /**
     * Access method for kmlFilePath.
     *
     * @return the current value of kmlFilePath
     */
    public String getKmlFilePath() {
        return kmlFilePath;
    }

    /**
     * Setter method for kmlFilePath.
     *
     * @param aKmlFilePath the new value for kmlFilePath
     */
    public void setKmlFilePath(String aKmlFilePath) {
        kmlFilePath = aKmlFilePath;
    }

    /**
     * Access method for farmCaseId.
     *
     * @return the current value of farmCaseId
     */
    public int getFarmCaseId() {
        return farmCaseId;
    }

    /**
     * Setter method for farmCaseId.
     *
     * @param aFarmCaseId the new value for farmCaseId
     */
    public void setFarmCaseId(int aFarmCaseId) {
        farmCaseId = aFarmCaseId;
    }

    /**
     * Access method for capturedOn.
     *
     * @return the current value of capturedOn
     */
    public Timestamp getCapturedOn() {
        return capturedOn;
    }

    /**
     * Setter method for capturedOn.
     *
     * @param aCapturedOn the new value for capturedOn
     */
    public void setCapturedOn(Timestamp aCapturedOn) {
        capturedOn = aCapturedOn;
    }

    /**
     * Access method for capturedBy.
     *
     * @return the current value of capturedBy
     */
    public int getCapturedBy() {
        return capturedBy;
    }

    /**
     * Setter method for capturedBy.
     *
     * @param aCapturedBy the new value for capturedBy
     */
    public void setCapturedBy(int aCapturedBy) {
        capturedBy = aCapturedBy;
    }

    /**
     * Compares the key for this instance with another CaseKml.
     *
     * @param other The object to compare to
     * @return True if other object is instance of class CaseKml and the key objects are equal
     */
    private boolean equalKeys(Object other) {
        if (this==other) {
            return true;
        }
        if (!(other instanceof CaseKml)) {
            return false;
        }
        CaseKml that = (CaseKml) other;
        if (this.getId() != that.getId()) {
            return false;
        }
        return true;
    }

    /**
     * Compares this instance with another CaseKml.
     *
     * @param other The object to compare to
     * @return True if the objects are the same
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof CaseKml)) return false;
        return this.equalKeys(other) && ((CaseKml)other).equalKeys(this);
    }

    /**
     * Returns a hash code for this instance.
     *
     * @return Hash code
     */
    @Override
    public int hashCode() {
        int i;
        int result = 17;
        i = getId();
        result = 37*result + i;
        return result;
    }

    /**
     * Returns a debug-friendly String representation of this instance.
     *
     * @return String representation of this instance
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("[CaseKml |");
        sb.append(" id=").append(getId());
        sb.append("]");
        return sb.toString();
    }

    /**
     * Return all elements of the primary key.
     *
     * @return Map of key names to values
     */
    public Map<String, Object> getPrimaryKey() {
        Map<String, Object> ret = new LinkedHashMap<String, Object>(6);
        ret.put("id", Integer.valueOf(getId()));
        return ret;
    }

}
