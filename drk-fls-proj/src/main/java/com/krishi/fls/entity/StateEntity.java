package com.krishi.fls.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity(name="state")
public class StateEntity implements Serializable {

    /** Primary key. */
    protected static final String PK = "stateId";

    @Id
    @Column(name="id", unique=true, nullable=false, precision=10)
    private int stateId;
    @Column(length=255)
    private String comment;
    @Column(name="name", length=255)
    private String stateName;
    @Column(precision=10)
    private int status;
    @Column(name="state_code", precision=10)
    private int stateCode;
    @Column(name="country_code", precision=10)
    private int countryCode;

    /** Default constructor. */
    public StateEntity() {
        super();
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
     * Access method for stateName.
     *
     * @return the current value of stateName
     */
    public String getStateName() {
        return stateName;
    }

    /**
     * Setter method for stateName.
     *
     * @param aStateName the new value for stateName
     */
    public void setStateName(String aStateName) {
        stateName = aStateName;
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
     * Access method for stateCode.
     *
     * @return the current value of stateCode
     */
    public int getStateCode() {
        return stateCode;
    }

    /**
     * Setter method for stateCode.
     *
     * @param aStateCode the new value for stateCode
     */
    public void setStateCode(int aStateCode) {
        stateCode = aStateCode;
    }

    /**
     * Access method for countryCode.
     *
     * @return the current value of countryCode
     */
    public int getCountryCode() {
        return countryCode;
    }

    /**
     * Setter method for countryCode.
     *
     * @param aCountryCode the new value for countryCode
     */
    public void setCountryCode(int aCountryCode) {
        countryCode = aCountryCode;
    }

    /**
     * Compares the key for this instance with another State.
     *
     * @param other The object to compare to
     * @return True if other object is instance of class State and the key objects are equal
     */
    private boolean equalKeys(Object other) {
        if (this==other) {
            return true;
        }
        if (!(other instanceof StateEntity)) {
            return false;
        }
        StateEntity that = (StateEntity) other;
        if (this.getStateId() != that.getStateId()) {
            return false;
        }
        return true;
    }

    /**
     * Compares this instance with another State.
     *
     * @param other The object to compare to
     * @return True if the objects are the same
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof StateEntity)) return false;
        return this.equalKeys(other) && ((StateEntity)other).equalKeys(this);
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
        i = getStateId();
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
        StringBuffer sb = new StringBuffer("[State |");
        sb.append(" stateId=").append(getStateId());
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
        ret.put("stateId", Integer.valueOf(getStateId()));
        return ret;
    }

}
