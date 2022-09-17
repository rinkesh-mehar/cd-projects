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
@Table(name="drk_users_credentials")
public class DrkUsersCredentials implements Serializable {

    /** Primary key. */
    protected static final String PK = "id";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique=true, nullable=false, precision=10)
    private int id;
    @Column(name="created_by", length=255)
    private String createdBy;
    @Column(name="created_date_time")
    private Timestamp createdDateTime;
    @Column(name="is_otp", precision=10)
    private int isOtp;
    @Column(name="modified_date_time")
    private Timestamp modifiedDateTime;
    @Column(name="modified_by", length=255)
    private String modifiedBy;
    @Column(name="password_reset_date")
    private Timestamp passwordResetDate;
    @Column(name="system_password_expired", precision=10)
    private int systemPasswordExpired;
    @Column(name="user_password", length=255)
    private String userPassword;
     @Column(name="user_id", precision=10)
    private int userId;
     @Column(precision=10)
    private int invalidattempts;

    /** Default constructor. */
    public DrkUsersCredentials() {
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
     * Access method for isOtp.
     *
     * @return the current value of isOtp
     */
    public int getIsOtp() {
        return isOtp;
    }

    /**
     * Setter method for isOtp.
     *
     * @param aIsOtp the new value for isOtp
     */
    public void setIsOtp(int aIsOtp) {
        isOtp = aIsOtp;
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
     * Access method for passwordResetDate.
     *
     * @return the current value of passwordResetDate
     */
    public Timestamp getPasswordResetDate() {
        return passwordResetDate;
    }

    /**
     * Setter method for passwordResetDate.
     *
     * @param aPasswordResetDate the new value for passwordResetDate
     */
    public void setPasswordResetDate(Timestamp aPasswordResetDate) {
        passwordResetDate = aPasswordResetDate;
    }

    /**
     * Access method for systemPasswordExpired.
     *
     * @return the current value of systemPasswordExpired
     */
    public int getSystemPasswordExpired() {
        return systemPasswordExpired;
    }

    /**
     * Setter method for systemPasswordExpired.
     *
     * @param aSystemPasswordExpired the new value for systemPasswordExpired
     */
    public void setSystemPasswordExpired(int aSystemPasswordExpired) {
        systemPasswordExpired = aSystemPasswordExpired;
    }

    /**
     * Access method for userPassword.
     *
     * @return the current value of userPassword
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * Setter method for userPassword.
     *
     * @param aUserPassword the new value for userPassword
     */
    public void setUserPassword(String aUserPassword) {
        userPassword = aUserPassword;
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
     * Access method for invalidattempts.
     *
     * @return the current value of invalidattempts
     */
    public int getInvalidattempts() {
        return invalidattempts;
    }

    /**
     * Setter method for invalidattempts.
     *
     * @param aInvalidattempts the new value for invalidattempts
     */
    public void setInvalidattempts(int aInvalidattempts) {
        invalidattempts = aInvalidattempts;
    }

    /**
     * Compares the key for this instance with another DrkUsersCredentials.
     *
     * @param other The object to compare to
     * @return True if other object is instance of class DrkUsersCredentials and the key objects are equal
     */
    private boolean equalKeys(Object other) {
        if (this==other) {
            return true;
        }
        if (!(other instanceof DrkUsersCredentials)) {
            return false;
        }
        DrkUsersCredentials that = (DrkUsersCredentials) other;
        if (this.getId() != that.getId()) {
            return false;
        }
        return true;
    }

    /**
     * Compares this instance with another DrkUsersCredentials.
     *
     * @param other The object to compare to
     * @return True if the objects are the same
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof DrkUsersCredentials)) return false;
        return this.equalKeys(other) && ((DrkUsersCredentials)other).equalKeys(this);
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
        StringBuffer sb = new StringBuffer("[DrkUsersCredentials |");
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
