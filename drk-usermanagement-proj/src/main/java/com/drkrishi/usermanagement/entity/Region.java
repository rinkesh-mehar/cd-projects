package com.drkrishi.usermanagement.entity;


import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "region")
public class Region implements Serializable {

    /** Primary key. */
    protected static final String PK = "regionId";

    @Id
    @Column(name="id", unique=true, nullable=false, precision=10)
    private int regionId;
    
    @Column(name="tile_id", nullable=false, precision=10)
    private int tileId;
    @Column(name="state_id", precision=10)
    private int stateId;
    @Column(length=12)
    private float latitude;
    @Column(length=12)
    private float longitude;
    @Column(name="name", length=100)
    private String regionName;
    private String description;
    @Column(length=255)
    private String comment;
    @Column(nullable=false, precision=10)
    private int status;

    /** Default constructor. */
    public Region() {
        super();
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
     * Access method for tileId.
     *
     * @return the current value of tileId
     */
    public int getTileId() {
        return tileId;
    }

    /**
     * Setter method for tileId.
     *
     * @param aTileId the new value for tileId
     */
    public void setTileId(int aTileId) {
        tileId = aTileId;
    }

    /**
     * Access method for stateId.
     *
     * @return the current value of stateId
     */
    public int getStateId() {
        return stateId;
    }

    /**
     * Setter method for stateId.
     *
     * @param aStateId the new value for stateId
     */
    public void setStateId(int aStateId) {
        stateId = aStateId;
    }

    /**
     * Access method for latitude.
     *
     * @return the current value of latitude
     */
    public float getLatitude() {
        return latitude;
    }

    /**
     * Setter method for latitude.
     *
     * @param aLatitude the new value for latitude
     */
    public void setLatitude(float aLatitude) {
        latitude = aLatitude;
    }

    /**
     * Access method for longitude.
     *
     * @return the current value of longitude
     */
    public float getLongitude() {
        return longitude;
    }

    /**
     * Setter method for longitude.
     *
     * @param aLongitude the new value for longitude
     */
    public void setLongitude(float aLongitude) {
        longitude = aLongitude;
    }

    /**
     * Access method for regionName.
     *
     * @return the current value of regionName
     */
    public String getRegionName() {
        return regionName;
    }

    /**
     * Setter method for regionName.
     *
     * @param aRegionName the new value for regionName
     */
    public void setRegionName(String aRegionName) {
        regionName = aRegionName;
    }

    /**
     * Access method for description.
     *
     * @return the current value of description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter method for description.
     *
     * @param aDescription the new value for description
     */
    public void setDescription(String aDescription) {
        description = aDescription;
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

    /**
     * Compares the key for this instance with another Region.
     *
     * @param other The object to compare to
     * @return True if other object is instance of class Region and the key objects are equal
     */
    private boolean equalKeys(Object other) {
        if (this==other) {
            return true;
        }
        if (!(other instanceof Region)) {
            return false;
        }
        Region that = (Region) other;
        if (this.getRegionId() != that.getRegionId()) {
            return false;
        }
        return true;
    }

    /**
     * Compares this instance with another Region.
     *
     * @param other The object to compare to
     * @return True if the objects are the same
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Region)) return false;
        return this.equalKeys(other) && ((Region)other).equalKeys(this);
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
        i = getRegionId();
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
        StringBuffer sb = new StringBuffer("[Region |");
        sb.append(" regionId=").append(getRegionId());
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
        ret.put("regionId", Integer.valueOf(getRegionId()));
        return ret;
    }

}
