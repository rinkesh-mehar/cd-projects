package com.drkrishi.prm.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;

@Entity
@Table(name="sms")
public class Sms implements Serializable {

   

	/** Primary key. */
    protected static final String PK = "id";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique=true, nullable=false, precision=19)
    private long id;
    @Column(name="mobile_number", nullable=false, length=12)
    private String mobileNumber;
    @Column(nullable=false, length=500)
    private String message;
    @Column(name="is_sent", length=1)
    private boolean isSent;
    @Column(nullable=false, length=3)
    private boolean status;
    @Column(length=3)
    private boolean retry;
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
    public boolean getIsSent() {
        return isSent;
    }

    /**
     * Setter method for isSent.
     *
     * @param aIsSent the new value for isSent
     */
    public void setIsSent(boolean aIsSent) {
        isSent = aIsSent;
    }

    /**
     * Access method for status.
     *
     * @return true if and only if status is currently true
     */
    public boolean getStatus() {
        return status;
    }

    /**
     * Setter method for status.
     *
     * @param aStatus the new value for status
     */
    public void setStatus(boolean aStatus) {
        status = aStatus;
    }

    /**
     * Access method for retry.
     *
     * @return true if and only if retry is currently true
     */
    public boolean getRetry() {
        return retry;
    }

    /**
     * Setter method for retry.
     *
     * @param aRetry the new value for retry
     */
    public void setRetry(boolean aRetry) {
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

    

    @Override
	public String toString() {
		return "Sms [id=" + id + ", mobileNumber=" + mobileNumber + ", message=" + message + ", isSent=" + isSent
				+ ", status=" + status + ", retry=" + retry + ", createdBy=" + createdBy + ", createdDate="
				+ createdDate + "]";
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

    
    
    public Sms(long id, String mobileNumber, String message, boolean isSent, boolean status, boolean retry,
			String createdBy, Timestamp createdDate) {
		
		this.id = id;
		this.mobileNumber = mobileNumber;
		this.message = message;
		this.isSent = isSent;
		this.status = status;
		this.retry = retry;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}
    
}
