package com.krishi.entity;

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

@Table(name="sms")
@Entity
public class Sms implements Serializable {

    /** Primary key. */
    protected static final String PK = "id";

    @Id
    @Column(unique=true, nullable=false, precision=19)
    private long id;
    @Column(name="mobile_number", nullable=false, length=12)
    private String mobileNumber;
    @Column(nullable=false, length=500)
    private String message;
    @Column(name="is_sent", length=1)
    private Boolean isSent;
    @Column(nullable=false, length=3)
    private Boolean status;
    @Column(length=3)
    private Integer retry;
    @Column(name="created_by", nullable=false, length=25)
    private String createdBy;
    @Column(name="created_date", nullable=false)
    private Timestamp createdDate;

    /** Default constructor. */
    public Sms() {
        super();
    }

    /**
     * Access method for id.
     *
     * @return the current value of id
     */
    public long getId() {
        return id;
    }

    /**
     * Setter method for id.
     *
     * @param aId the new value for id
     */
    public void setId(long aId) {
        id = aId;
    }

    /**
     * Access method for mobileNumber.
     *
     * @return the current value of mobileNumber
     */
    public String getMobileNumber() {
        return mobileNumber;
    }

    /**
     * Setter method for mobileNumber.
     *
     * @param aMobileNumber the new value for mobileNumber
     */
    public void setMobileNumber(String aMobileNumber) {
        mobileNumber = aMobileNumber;
    }

    /**
     * Access method for message.
     *
     * @return the current value of message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter method for message.
     *
     * @param aMessage the new value for message
     */
    public void setMessage(String aMessage) {
        message = aMessage;
    }

    /**
     * Access method for isSent.
     *
     * @return true if and only if isSent is currently true
     */
    public Boolean getIsSent() {
        return isSent;
    }

    /**
     * Setter method for isSent.
     *
     * @param aIsSent the new value for isSent
     */
    public void setIsSent(Boolean aIsSent) {
        isSent = aIsSent;
    }

    /**
     * Access method for status.
     *
     * @return true if and only if status is currently true
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * Setter method for status.
     *
     * @param aStatus the new value for status
     */
    public void setStatus(Boolean aStatus) {
        status = aStatus;
    }

    /**
     * Access method for retry.
     *
     * @return true if and only if retry is currently true
     */
    public Integer getRetry() {
        return retry;
    }

    /**
     * Setter method for retry.
     *
     * @param aRetry the new value for retry
     */
    public void setRetry(Integer aRetry) {
        retry = aRetry;
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
     * Access method for createdDate.
     *
     * @return the current value of createdDate
     */
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    /**
     * Setter method for createdDate.
     *
     * @param aCreatedDate the new value for createdDate
     */
    public void setCreatedDate(Timestamp aCreatedDate) {
        createdDate = aCreatedDate;
    }

    /**
     * Compares the key for this instance with another Sms.
     *
     * @param other The object to compare to
     * @return True if other object is instance of class Sms and the key objects are equal
     */
    private boolean equalKeys(Object other) {
        if (this==other) {
            return true;
        }
        if (!(other instanceof Sms)) {
            return false;
        }
        Sms that = (Sms) other;
        if (this.getId() != that.getId()) {
            return false;
        }
        return true;
    }

    /**
     * Compares this instance with another Sms.
     *
     * @param other The object to compare to
     * @return True if the objects are the same
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Sms)) return false;
        return this.equalKeys(other) && ((Sms)other).equalKeys(this);
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
        i = (int)(getId() ^ (getId()>>>32));
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
        StringBuffer sb = new StringBuffer("[Sms |");
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
        ret.put("id", Long.valueOf(getId()));
        return ret;
    }

}
