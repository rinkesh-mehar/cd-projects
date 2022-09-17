package com.drkrishi.usermanagement.entity;


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
@Table(name = "audit_trail")
public class AuditTrialEntity implements Serializable {

/** Primary key. */
protected static final String PK = "id";


@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
@Column(unique = true, nullable = false, precision = 10)
private Integer id;

@Column(length = 255, name = "action")
private String action;

@Column(name = "audit_date_time")
private Timestamp auditDateTime;

@Column(name = "old_values", length = 255)
private String oldValues;

@Column(name = "new_values", length = 255)
private String newValues;

@Column(name = "transaction_table", length = 255)
private String transactionTable;

@Column(name = "user_id", precision = 10)
private int userId1;

/** Default constructor. */
public AuditTrialEntity() {
super();
}

/**
* Access method for id.
*
* @return the current value of id
*/
public Integer getId() {
return id;
}

/**
* Setter method for id.
*
* @param aId the new value for id
*/
public void setId(Integer Id) {
id = Id;
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

public String getOldValues() {
return oldValues;
}

public void setOldValues(String oldValues) {
this.oldValues = oldValues;
}

public String getNewValues() {
return newValues;
}

public void setNewValues(String newValues) {
this.newValues = newValues;
}

/**
* Compares the key for this instance with another AuditTrialEntity.
*
* @param other The object to compare to
* @return True if other object is instance of class AuditTrialEntity and the key
* objects are equal
*/
private boolean equalKeys(Object other) {
if (this == other) {
return true;
}
if (!(other instanceof AuditTrialEntity)) {
return false;
}
AuditTrialEntity that = (AuditTrialEntity) other;
if (this.getId() != that.getId()) {
return false;
}
return true;
}

/**
* Compares this instance with another AuditTrialEntity.
*
* @param other The object to compare to
* @return True if the objects are the same
*/
@Override
public boolean equals(Object other) {
if (!(other instanceof AuditTrialEntity))
return false;
return this.equalKeys(other) && ((AuditTrialEntity) other).equalKeys(this);
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
	return "AuditTrialEntity [id=" + id + ", action=" + action + ", auditDateTime=" + auditDateTime + ", oldValues="
			+ oldValues + ", newValues=" + newValues + ", transactionTable=" + transactionTable + ", userId1=" + userId1
			+ ", getId()=" + getId() + ", getAction()=" + getAction() + ", getAuditDateTime()=" + getAuditDateTime()
			+ ", getTransactionTable()=" + getTransactionTable() + ", getUserId1()=" + getUserId1()
			+ ", getOldValues()=" + getOldValues() + ", getNewValues()=" + getNewValues() + ", hashCode()=" + hashCode()
			+ ", getPrimaryKey()=" + getPrimaryKey() + ", getClass()=" + getClass() + ", toString()=" + super.toString()
			+ "]";
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

public AuditTrialEntity(Integer id, String action, Timestamp auditDateTime, String oldValues, String newvalue,
		String transactionTable, int userId1) {
	super();
	this.id = id;
	this.action = action;
	this.auditDateTime = auditDateTime;
	this.oldValues = oldValues;
	this.newValues = newvalue;
	this.transactionTable = transactionTable;
	this.userId1 = userId1;
}
}