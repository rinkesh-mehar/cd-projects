package com.drkrishi.prm.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;

@Entity
@Table(name="email")
public class Email implements Serializable {

    /** Primary key. */
    protected static final String PK = "id";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique=true, nullable=false, precision=19)
    private long id;
    @Column(name="email_date", nullable=false)
    private Timestamp emailDate;
    @Column(name="from_address", length=100)
    private String fromAddress;
    @Column(name="from_mail_display", length=100)
    private String fromMailDisplay;
    @Column(name="to_address", nullable=false, length=5000)
    private String toAddress;
    @Column(name="cc_address", length=5000)
    private String ccAddress;
    @Column(name="bcc_address", length=5000)
    private String bccAddress;
    @Column(length=1000)
    private String subject;
    private String body;
    @Column(name="is_sent", length=1)
    private boolean isSent;
    @Column(nullable=false, length=3)
    private boolean status;
    @Column(length=3)
    private boolean retry;
    @Column(length=5000)
    private String response;
    @Column(name="sent_date")
    private Timestamp sentDate;
    @Column(name="created_by", length=50)
    private String createdBy;
    @Column(name="created_date", nullable=false)
    private Timestamp createdDate;

    /** Default constructor. */
    public Email() {
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
     * Access method for emailDate.
     *
     * @return the current value of emailDate
     */
    public Timestamp getEmailDate() {
        return emailDate;
    }

    /**
     * Setter method for emailDate.
     *
     * @param aEmailDate the new value for emailDate
     */
    public void setEmailDate(Timestamp aEmailDate) {
        emailDate = aEmailDate;
    }

    /**
     * Access method for fromAddress.
     *
     * @return the current value of fromAddress
     */
    public String getFromAddress() {
        return fromAddress;
    }

    /**
     * Setter method for fromAddress.
     *
     * @param aFromAddress the new value for fromAddress
     */
    public void setFromAddress(String aFromAddress) {
        fromAddress = aFromAddress;
    }

    /**
     * Access method for fromMailDisplay.
     *
     * @return the current value of fromMailDisplay
     */
    public String getFromMailDisplay() {
        return fromMailDisplay;
    }

    /**
     * Setter method for fromMailDisplay.
     *
     * @param aFromMailDisplay the new value for fromMailDisplay
     */
    public void setFromMailDisplay(String aFromMailDisplay) {
        fromMailDisplay = aFromMailDisplay;
    }

    /**
     * Access method for toAddress.
     *
     * @return the current value of toAddress
     */
    public String getToAddress() {
        return toAddress;
    }

    /**
     * Setter method for toAddress.
     *
     * @param aToAddress the new value for toAddress
     */
    public void setToAddress(String aToAddress) {
        toAddress = aToAddress;
    }

    /**
     * Access method for ccAddress.
     *
     * @return the current value of ccAddress
     */
    public String getCcAddress() {
        return ccAddress;
    }

    /**
     * Setter method for ccAddress.
     *
     * @param aCcAddress the new value for ccAddress
     */
    public void setCcAddress(String aCcAddress) {
        ccAddress = aCcAddress;
    }

    /**
     * Access method for bccAddress.
     *
     * @return the current value of bccAddress
     */
    public String getBccAddress() {
        return bccAddress;
    }

    /**
     * Setter method for bccAddress.
     *
     * @param aBccAddress the new value for bccAddress
     */
    public void setBccAddress(String aBccAddress) {
        bccAddress = aBccAddress;
    }

    /**
     * Access method for subject.
     *
     * @return the current value of subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Setter method for subject.
     *
     * @param aSubject the new value for subject
     */
    public void setSubject(String aSubject) {
        subject = aSubject;
    }

    /**
     * Access method for body.
     *
     * @return the current value of body
     */
    public String getBody() {
        return body;
    }

    /**
     * Setter method for body.
     *
     * @param aBody the new value for body
     */
    public void setBody(String aBody) {
        body = aBody;
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
     * Access method for response.
     *
     * @return the current value of response
     */
    public String getResponse() {
        return response;
    }

    /**
     * Setter method for response.
     *
     * @param aResponse the new value for response
     */
    public void setResponse(String aResponse) {
        response = aResponse;
    }

    /**
     * Access method for sentDate.
     *
     * @return the current value of sentDate
     */
    public Timestamp getSentDate() {
        return sentDate;
    }

    /**
     * Setter method for sentDate.
     *
     * @param aSentDate the new value for sentDate
     */
    public void setSentDate(Timestamp aSentDate) {
        sentDate = aSentDate;
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
     * Compares the key for this instance with another Email.
     *
     * @param other The object to compare to
     * @return True if other object is instance of class Email and the key objects are equal
     */
    private boolean equalKeys(Object other) {
        if (this==other) {
            return true;
        }
        if (!(other instanceof Email)) {
            return false;
        }
        Email that = (Email) other;
        if (this.getId() != that.getId()) {
            return false;
        }
        return true;
    }

    /**
     * Compares this instance with another Email.
     *
     * @param other The object to compare to
     * @return True if the objects are the same
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Email)) return false;
        return this.equalKeys(other) && ((Email)other).equalKeys(this);
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
		return "Email [id=" + id + ", emailDate=" + emailDate + ", fromAddress=" + fromAddress + ", fromMailDisplay="
				+ fromMailDisplay + ", toAddress=" + toAddress + ", ccAddress=" + ccAddress + ", bccAddress="
				+ bccAddress + ", subject=" + subject + ", body=" + body + ", isSent=" + isSent + ", status=" + status
				+ ", retry=" + retry + ", response=" + response + ", sentDate=" + sentDate + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + "]";
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
