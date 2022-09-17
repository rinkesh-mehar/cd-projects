package com.drkrishi.usermanagement.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_credentials")
public class UserCredentials implements Serializable {

	/** Primary key. */
	protected static final String PK = "id";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false, precision = 10)
	private int id;

	@Column(name = "created_by", length = 255)
	private String createdBy;

	@Column(name = "created_date_time")
	private Date createdDateTime;

	@Column(name = "transaction_type", precision = 10)
	private int transaction_type;

	@Column(name = "modified_date_time")
	private Date modifiedDateTime;
	
	@Column(name = "modified_by", length = 255)
	private String modifiedBy;
	
	@Column(name = "password_reset_date")
	private Date passwordResetDate;
	
	@Column(name = "is_forced_pwd_change", precision = 10)
	private int is_force_pwd_change;
	
	@Column(name = "user_password", length = 255)
	private String userPassword;
	
	@Column(name = "user_id", precision = 10)
	private int userId;
	
	@Column(name = "invalid_attempts", precision = 10)
	private int invalidAttempts;

	

	/** Default constructor. */
	public UserCredentials() {
		super();
	}

	public UserCredentials(int id, int userId, String userPassword, int transaction_type, Date passwordResetDate,
			int is_force_pwd_change, int invalidAttempts, String createdBy,
			Date createdDateTime, Date modifiedDateTime, String modifiedBy) {
		this.id = id;
		this.userId = userId;
		this.userPassword = userPassword;
		this.transaction_type = transaction_type;
		this.passwordResetDate = passwordResetDate;
		this.is_force_pwd_change = is_force_pwd_change;
		this.invalidAttempts = invalidAttempts;
		this.createdBy = createdBy;
		this.createdDateTime = createdDateTime;
		this.modifiedDateTime = modifiedDateTime;
		this.modifiedBy = modifiedBy;
		
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
	public Date getCreatedDateTime() {
		return createdDateTime;
	}

	/**
	 * Setter method for createdDateTime.
	 *
	 * @param aCreatedDateTime the new value for createdDateTime
	 */
	public void setCreatedDateTime(Date aCreatedDateTime) {
		createdDateTime = aCreatedDateTime;
	}

	/**
	 * Access method for isOtp.
	 *
	 * @return the current value of isOtp
	 */
	

	/**
	 * Setter method for isOtp.
	 *
	 * @param aIsOtp the new value for isOtp
	 */
	
	/**
	 * Access method for modifiedDateTime.
	 *
	 * @return the current value of modifiedDateTime
	 */
	public Date getModifiedDateTime() {
		return modifiedDateTime;
	}

	public int getTransaction_type() {
		return transaction_type;
	}

	public void setTransaction_type(int transaction_type) {
		this.transaction_type = transaction_type;
	}

	public int getIs_force_pwd_change() {
		return is_force_pwd_change;
	}

	public void setIs_force_pwd_change(int is_force_pwd_change) {
		this.is_force_pwd_change = is_force_pwd_change;
	}

	/**
	 * Setter method for modifiedDateTime.
	 *
	 * @param aModifiedDateTime the new value for modifiedDateTime
	 */
	public void setModifiedDateTime(Date aModifiedDateTime) {
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
	public Date getPasswordResetDate() {
		return passwordResetDate;
	}

	/**
	 * Setter method for passwordResetDate.
	 *
	 * @param aPasswordResetDate the new value for passwordResetDate
	 */
	public void setPasswordResetDate(Date aPasswordResetDate) {
		passwordResetDate = aPasswordResetDate;
	}

	/**
	 * Access method for systemPasswordExpired.
	 *
	 * @return the current value of systemPasswordExpired
	 */
	

	/**
	 * Setter method for systemPasswordExpired.
	 *
	 * @param aSystemPasswordExpired the new value for systemPasswordExpired
	 */
	

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
	 * Access method for invalidAttempts.
	 *
	 * @return the current value of invalidAttempts
	 */
	public int getInvalidAttempts() {
		return invalidAttempts;
	}

	/**
	 * Setter method for invalidAttempts.
	 *
	 * @param aInvalidAttempts the new value for invalidAttempts
	 */
	public void setInvalidAttempts(int aInvalidAttempts) {
		invalidAttempts = aInvalidAttempts;
	}

	/**
	 * Compares the key for this instance with another UserCredentials.
	 *
	 * @param other The object to compare to
	 * @return True if other object is instance of class UserCredentials and the key
	 *         objects are equal
	 */
	private boolean equalKeys(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof UserCredentials)) {
			return false;
		}
		UserCredentials that = (UserCredentials) other;
		if (this.getId() != that.getId()) {
			return false;
		}
		return true;
	}

	/**
	 * Compares this instance with another UserCredentials.
	 *
	 * @param other The object to compare to
	 * @return True if the objects are the same
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof UserCredentials))
			return false;
		return this.equalKeys(other) && ((UserCredentials) other).equalKeys(this);
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
		result = 37 * result + i;
		return result;
	}
	
	@Override
	public String toString() {
		return "UserCredentials [id=" + id + ", createdBy=" + createdBy + ", createdDateTime=" + createdDateTime
				+ ", transaction_type=" + transaction_type + ", modifiedDateTime=" + modifiedDateTime + ", modifiedBy="
				+ modifiedBy + ", passwordResetDate=" + passwordResetDate + ", is_force_pwd_change="
				+ is_force_pwd_change + ", userPassword=" + userPassword + ", userId=" + userId + ", invalidAttempts="
				+ invalidAttempts + "]";
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
