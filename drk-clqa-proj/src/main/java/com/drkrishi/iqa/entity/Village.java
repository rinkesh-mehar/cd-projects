package com.drkrishi.iqa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

@Entity
@Table(name="village")
public class Village implements Serializable {

    /** Primary key. */
    protected static final String PK = "id";

    @Id
    @Column(unique=true, nullable=false)
    private int id;
    @Column(name="panchayat_id")
    private int panchayatId;
    @Column(precision=10)
    private int code;
    @Column(name="region_id")
    private int regionId;
    @Column(name="state_id")
    private int stateId;
    @Column(name="subregion_id")
    private String subregionId;
    @Column(length=200)
    private String name;
    @Column(precision=10)
    private int pincode;
    @Column(precision=19, scale=8)
    private BigDecimal latitude;
    @Column(precision=19, scale=8)
    private BigDecimal longitude;
    @Column(length=255)
    private String comment;
    @Column(nullable=false)
    private int status;

    /** Default constructor. */
    public Village() {
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
     * Access method for panchayatId.
     *
     * @return the current value of panchayatId
     */
    public int getPanchayatId() {
        return panchayatId;
    }

    /**
     * Setter method for panchayatId.
     *
     * @param aPanchayatId the new value for panchayatId
     */
    public void setPanchayatId(int aPanchayatId) {
        panchayatId = aPanchayatId;
    }

    /**
     * Access method for code.
     *
     * @return the current value of code
     */
    public int getCode() {
        return code;
    }

    /**
     * Setter method for code.
     *
     * @param aCode the new value for code
     */
    public void setCode(int aCode) {
        code = aCode;
    }

    /**
     * Access method for regionId.
     *
     * @return the current value of regionId
     */
    public int getRegionId() {
        return regionId;
    }

    /**
     * Setter method for regionId.
     *
     * @param aRegionId the new value for regionId
     */
    public void setRegionId(int aRegionId) {
        regionId = aRegionId;
    }

    /**
     * Access method for subregionId.
     *
     * @return the current value of subregionId
     */
    public String getSubregionId() {
        return subregionId;
    }

    /**
     * Setter method for subregionId.
     *
     * @param aSubregionId the new value for subregionId
     */
    public void setSubregionId(String aSubregionId) {
        subregionId = aSubregionId;
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
     * Access method for pincode.
     *
     * @return the current value of pincode
     */
    public int getPincode() {
        return pincode;
    }

    /**
     * Setter method for pincode.
     *
     * @param aPincode the new value for pincode
     */
    public void setPincode(int aPincode) {
        pincode = aPincode;
    }

    /**
     * Access method for latitude.
     *
     * @return the current value of latitude
     */
    public BigDecimal getLatitude() {
        return latitude;
    }

    /**
     * Setter method for latitude.
     *
     * @param aLatitude the new value for latitude
     */
    public void setLatitude(BigDecimal aLatitude) {
        latitude = aLatitude;
    }

    /**
     * Access method for longitude.
     *
     * @return the current value of longitude
     */
    public BigDecimal getLongitude() {
        return longitude;
    }

    /**
     * Setter method for longitude.
     *
     * @param aLongitude the new value for longitude
     */
    public void setLongitude(BigDecimal aLongitude) {
        longitude = aLongitude;
    }

    /**
     * Access method for comment.
     *
     * @return the current value of comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Setter method for comment.
     *
     * @param aComment the new value for comment
     */
    public void setComment(String aComment) {
        comment = aComment;
    }

    /**
     * Access method for status.
     *
     * @return the current value of status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Setter method for status.
     *
     * @param aStatus the new value for status
     */
    public void setStatus(int aStatus) {
        status = aStatus;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    /**
     * Compares the key for this instance with another Village.
     *
     * @param other The object to compare to
     * @return True if other object is instance of class Village and the key objects are equal
     */
    private boolean equalKeys(Object other) {
        if (this==other) {
            return true;
        }
        if (!(other instanceof Village)) {
            return false;
        }
        Village that = (Village) other;
        if (this.getId() != that.getId()) {
            return false;
        }
        return true;
    }

    /**
     * Compares this instance with another Village.
     *
     * @param other The object to compare to
     * @return True if the objects are the same
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Village)) return false;
        return this.equalKeys(other) && ((Village)other).equalKeys(this);
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
        StringBuffer sb = new StringBuffer("[Village |");
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
