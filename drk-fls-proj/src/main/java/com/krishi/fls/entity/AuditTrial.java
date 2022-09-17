
package com.krishi.fls.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "audit_trial")
public class AuditTrial implements Serializable {

	/** Primary key. */
	protected static final String PK = "id";

	@Id
	@Column(unique = true, nullable = false, precision = 10)
	private int id;
	@Column(length = 255)
	private String action;
	@Column(name = "audit_date_time")
	private Timestamp auditDateTime;
	@Column(name = "audited_values")
	private byte[] auditedValues;
	@Column(name = "transaction_table", length = 255)
	private String transactionTable;
	@Column(name = "user_id1", precision = 10)
	private int userId1;

	/** Default constructor. */
	public AuditTrial() {
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
	 * Access method for action.
	 *
	 * @return the current value of action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * Setter method for action.
	 *
	 * @param aAction the new value for action
	 */
	public void setAction(String aAction) {
		action = aAction;
	}

	/**
	 * Access method for auditDateTime.
	 *
	 * @return the current value of auditDateTime
	 */
	public Timestamp getAuditDateTime() {
		return auditDateTime;
	}

	/**
	 * Setter method for auditDateTime.
	 *
	 * @param aAuditDateTime the new value for auditDateTime
	 */
	public void setAuditDateTime(Timestamp aAuditDateTime) {
		auditDateTime = aAuditDateTime;
	}

	/**
	 * Access method for auditedValues.
	 *
	 * @return the current value of auditedValues
	 */
	public byte[] getAuditedValues() {
		return auditedValues;
	}

	/**
	 * Setter method for auditedValues.
	 *
	 * @param aAuditedValues the new value for auditedValues
	 */
	public void setAuditedValues(byte[] aAuditedValues) {
		auditedValues = aAuditedValues;
	}

	/**
	 * Access method for transactionTable.
	 *
	 * @return the current value of transactionTable
	 */
	public String getTransactionTable() {
		return transactionTable;
	}

	/**
	 * Setter method for transactionTable.
	 *
	 * @param aTransactionTable the new value for transactionTable
	 */
	public void setTransactionTable(String aTransactionTable) {
		transactionTable = aTransactionTable;
	}

	/**
	 * Access method for userId1.
	 *
	 * @return the current value of userId1
	 */
	public int getUserId1() {
		return userId1;
	}

	/**
	 * Setter method for userId1.
	 *
	 * @param aUserId1 the new value for userId1
	 */
	public void setUserId1(int aUserId1) {
		userId1 = aUserId1;
	}

	/**
	 * Compares the key for this instance with another AuditTrial.
	 *
	 * @param other The object to compare to
	 * @return True if other object is instance of class AuditTrial and the key
	 *         objects are equal
	 */
	private boolean equalKeys(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AuditTrial)) {
			return false;
		}
		AuditTrial that = (AuditTrial) other;
		if (this.getId() != that.getId()) {
			return false;
		}
		return true;
	}

	/**
	 * Compares this instance with another AuditTrial.
	 *
	 * @param other The object to compare to
	 * @return True if the objects are the same
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof AuditTrial))
			return false;
		return this.equalKeys(other) && ((AuditTrial) other).equalKeys(this);
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

	/**
	 * Returns a debug-friendly String representation of this instance.
	 *
	 * @return String representation of this instance
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("[AuditTrial |");
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
