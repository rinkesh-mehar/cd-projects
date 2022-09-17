package com.drkrishi.prm.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;

@Entity
@Table(name = "sms_template")
public class SmsTemplate implements Serializable {

	/** Primary key. */
	protected static final String PK = "id";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false, precision = 19)
	private long id;
	@Column(nullable = false, length = 50)
	private String name;
	@Column(nullable = false, length = 5000)
	private String message;
	@Column(name = "is_active", nullable = false, length = 1)
	private boolean isActive;
	@Column(name = "created_date", nullable = false)
	private Timestamp createdDate;
	@Column(name = "created_by", nullable = false, length = 50)
	private String createdBy;
	@Column(name = "modified_date")
	private Timestamp modifiedDate;
	@Column(name = "modified_by", length = 50)
	private String modifiedBy;

	/** Default constructor. */
	public SmsTemplate() {
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
	 * Access method for isActive.
	 *
	 * @return true if and only if isActive is currently true
	 */
	public boolean getIsActive() {
		return isActive;
	}

	/**
	 * Setter method for isActive.
	 *
	 * @param aIsActive the new value for isActive
	 */
	public void setIsActive(boolean aIsActive) {
		isActive = aIsActive;
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
	 * Access method for modifiedDate.
	 *
	 * @return the current value of modifiedDate
	 */
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * Setter method for modifiedDate.
	 *
	 * @param aModifiedDate the new value for modifiedDate
	 */
	public void setModifiedDate(Timestamp aModifiedDate) {
		modifiedDate = aModifiedDate;
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
	 * Compares the key for this instance with another SmsTemplate.
	 *
	 * @param other The object to compare to
	 * @return True if other object is instance of class SmsTemplate and the key
	 *         objects are equal
	 */
	private boolean equalKeys(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SmsTemplate)) {
			return false;
		}
		SmsTemplate that = (SmsTemplate) other;
		if (this.getId() != that.getId()) {
			return false;
		}
		return true;
	}

	/**
	 * Compares this instance with another SmsTemplate.
	 *
	 * @param other The object to compare to
	 * @return True if the objects are the same
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof SmsTemplate))
			return false;
		return this.equalKeys(other) && ((SmsTemplate) other).equalKeys(this);
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
		i = (int) (getId() ^ (getId() >>> 32));
		result = 37 * result + i;
		return result;
	}

	
	@Override
	public String toString() {
		return "SmsTemplate [id=" + id + ", name=" + name + ", message=" + message + ", isActive=" + isActive
				+ ", createdDate=" + createdDate + ", createdBy=" + createdBy + ", modifiedDate=" + modifiedDate
				+ ", modifiedBy=" + modifiedBy + "]";
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
