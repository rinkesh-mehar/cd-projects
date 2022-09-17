// Generated with g9.

package com.krishi.fls.entity;

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
@Table(name="drkrishi_users")
public class DrkrishiUsers implements Serializable {

    /** Primary key. */
    protected static final String PK = "userId";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="user_id", unique=true, nullable=false, precision=10)
    private int userId;
    @Column(name="created_date_time")
    private Timestamp createdDateTime;
    @Column(name="created_by", length=255)
    private String createdBy;
    @Column(name="modified_by", length=255)
    private String modifiedBy;
    @Column(name="modified_date_time")
    private Timestamp modifiedDateTime;
    @Column(name="user_email_address", length=255)
    private String userEmailAddress;
    @Column(name="user_last_name", length=255)
    private String userLastName;
    @Column(name="user_middle_name", length=255)
    private String userMiddleName;
    @Column(name="user_mobile_number", length=255)
    private String userMobileNumber;
    @Column(name="user_region", precision=10)
    private int userRegion;
    @Column(name="user_state", precision=10)
    private int userState;
    @Column(name="user_first_name", length=255)
    private String userFirstName;
    @Column(name="reporting_to", precision=10)
    private int reportingTo;
    @Column(name="reporting_role_id", precision=10)
    private int reportingRoleId;

    /** Default constructor. */
    public DrkrishiUsers() {
        super();
    }

    /**
     * Access method for userId.
     *
     * @return the current value of userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Setter method for userId.
     *
     * @param aUserId the new value for userId
     */
    public void setUserId(int aUserId) {
        userId = aUserId;
    }

    /**
     * Access method for createdDateTime.
     *
     * @return the current value of createdDateTime
     */
    public Timestamp getCreatedDateTime() {
        return createdDateTime;
    }

    /**
     * Setter method for createdDateTime.
     *
     * @param aCreatedDateTime the new value for createdDateTime
     */
    public void setCreatedDateTime(Timestamp aCreatedDateTime) {
        createdDateTime = aCreatedDateTime;
    }

    /**
     * Access method for createdBy.
     *
     * @return the current value of createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Setter method for createdBy.
     *
     * @param aCreatedBy the new value for createdBy
     */
    public void setCreatedBy(String aCreatedBy) {
        createdBy = aCreatedBy;
    }

    /**
     * Access method for modifiedBy.
     *
     * @return the current value of modifiedBy
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * Setter method for modifiedBy.
     *
     * @param aModifiedBy the new value for modifiedBy
     */
    public void setModifiedBy(String aModifiedBy) {
        modifiedBy = aModifiedBy;
    }

    /**
     * Access method for modifiedDateTime.
     *
     * @return the current value of modifiedDateTime
     */
    public Timestamp getModifiedDateTime() {
        return modifiedDateTime;
    }

    /**
     * Setter method for modifiedDateTime.
     *
     * @param aModifiedDateTime the new value for modifiedDateTime
     */
    public void setModifiedDateTime(Timestamp aModifiedDateTime) {
        modifiedDateTime = aModifiedDateTime;
    }

    /**
     * Access method for userEmailAddress.
     *
     * @return the current value of userEmailAddress
     */
    public String getUserEmailAddress() {
        return userEmailAddress;
    }

    /**
     * Setter method for userEmailAddress.
     *
     * @param aUserEmailAddress the new value for userEmailAddress
     */
    public void setUserEmailAddress(String aUserEmailAddress) {
        userEmailAddress = aUserEmailAddress;
    }

    /**
     * Access method for userLastName.
     *
     * @return the current value of userLastName
     */
    public String getUserLastName() {
        return userLastName;
    }

    /**
     * Setter method for userLastName.
     *
     * @param aUserLastName the new value for userLastName
     */
    public void setUserLastName(String aUserLastName) {
        userLastName = aUserLastName;
    }

    /**
     * Access method for userMiddleName.
     *
     * @return the current value of userMiddleName
     */
    public String getUserMiddleName() {
        return userMiddleName;
    }

    /**
     * Setter method for userMiddleName.
     *
     * @param aUserMiddleName the new value for userMiddleName
     */
    public void setUserMiddleName(String aUserMiddleName) {
        userMiddleName = aUserMiddleName;
    }

    /**
     * Access method for userMobileNumber.
     *
     * @return the current value of userMobileNumber
     */
    public String getUserMobileNumber() {
        return userMobileNumber;
    }

    /**
     * Setter method for userMobileNumber.
     *
     * @param aUserMobileNumber the new value for userMobileNumber
     */
    public void setUserMobileNumber(String aUserMobileNumber) {
        userMobileNumber = aUserMobileNumber;
    }

    /**
     * Access method for userRegion.
     *
     * @return the current value of userRegion
     */
    public int getUserRegion() {
        return userRegion;
    }

    /**
     * Setter method for userRegion.
     *
     * @param aUserRegion the new value for userRegion
     */
    public void setUserRegion(int aUserRegion) {
        userRegion = aUserRegion;
    }

    /**
     * Access method for userState.
     *
     * @return the current value of userState
     */
    public int getUserState() {
        return userState;
    }

    /**
     * Setter method for userState.
     *
     * @param aUserState the new value for userState
     */
    public void setUserState(int aUserState) {
        userState = aUserState;
    }

    /**
     * Access method for userFirstName.
     *
     * @return the current value of userFirstName
     */
    public String getUserFirstName() {
        return userFirstName;
    }

    /**
     * Setter method for userFirstName.
     *
     * @param aUserFirstName the new value for userFirstName
     */
    public void setUserFirstName(String aUserFirstName) {
        userFirstName = aUserFirstName;
    }

    /**
     * Access method for reportingTo.
     *
     * @return the current value of reportingTo
     */
    public int getReportingTo() {
        return reportingTo;
    }

    /**
     * Setter method for reportingTo.
     *
     * @param aReportingTo the new value for reportingTo
     */
    public void setReportingTo(int aReportingTo) {
        reportingTo = aReportingTo;
    }

    /**
     * Access method for reportingRoleId.
     *
     * @return the current value of reportingRoleId
     */
    public int getReportingRoleId() {
        return reportingRoleId;
    }

    /**
     * Setter method for reportingRoleId.
     *
     * @param aReportingRoleId the new value for reportingRoleId
     */
    public void setReportingRoleId(int aReportingRoleId) {
        reportingRoleId = aReportingRoleId;
    }

    /**
     * Compares the key for this instance with another DrkrishiUsers.
     *
     * @param other The object to compare to
     * @return True if other object is instance of class DrkrishiUsers and the key objects are equal
     */
    private boolean equalKeys(Object other) {
        if (this==other) {
            return true;
        }
        if (!(other instanceof DrkrishiUsers)) {
            return false;
        }
        DrkrishiUsers that = (DrkrishiUsers) other;
        if (this.getUserId() != that.getUserId()) {
            return false;
        }
        return true;
    }

    /**
     * Compares this instance with another DrkrishiUsers.
     *
     * @param other The object to compare to
     * @return True if the objects are the same
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof DrkrishiUsers)) return false;
        return this.equalKeys(other) && ((DrkrishiUsers)other).equalKeys(this);
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
        i = getUserId();
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
        StringBuffer sb = new StringBuffer("[DrkrishiUsers |");
        sb.append(" userId=").append(getUserId());
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
        ret.put("userId", Integer.valueOf(getUserId()));
        return ret;
    }

}
