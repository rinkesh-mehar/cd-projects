package com.drkrishi.prm.model;

import java.sql.Timestamp;
import java.util.Arrays;

public class AuditTrial {

	private String action;

	private Timestamp audit_Date_Time;

	private byte[] audited_Values;

	private int id;

	private String transaction_Table;

	private DRKrishiUser drkrishiUser;

	public AuditTrial() {
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Timestamp getAudit_Date_Time() {
		return this.audit_Date_Time;
	}

	public void setAudit_Date_Time(Timestamp audit_Date_Time) {
		this.audit_Date_Time = audit_Date_Time;
	}

	public byte[] getAudited_Values() {
		return this.audited_Values;
	}

	public void setAudited_Values(byte[] audited_Values) {
		this.audited_Values = audited_Values;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTransaction_Table() {
		return this.transaction_Table;
	}

	public void setTransaction_Table(String transaction_Table) {
		this.transaction_Table = transaction_Table;
	}

	public DRKrishiUser getDrkrishiUser() {
		return this.drkrishiUser;
	}

	public void setDrkrishiUser(DRKrishiUser drkrishiUser) {
		this.drkrishiUser = drkrishiUser;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((audit_Date_Time == null) ? 0 : audit_Date_Time.hashCode());
		result = prime * result + Arrays.hashCode(audited_Values);
		result = prime * result + ((drkrishiUser == null) ? 0 : drkrishiUser.hashCode());
		result = prime * result + id;
		result = prime * result + ((transaction_Table == null) ? 0 : transaction_Table.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuditTrial other = (AuditTrial) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (audit_Date_Time == null) {
			if (other.audit_Date_Time != null)
				return false;
		} else if (!audit_Date_Time.equals(other.audit_Date_Time))
			return false;
		if (!Arrays.equals(audited_Values, other.audited_Values))
			return false;
		if (drkrishiUser == null) {
			if (other.drkrishiUser != null)
				return false;
		} else if (!drkrishiUser.equals(other.drkrishiUser))
			return false;
		if (id != other.id)
			return false;
		if (transaction_Table == null) {
			if (other.transaction_Table != null)
				return false;
		} else if (!transaction_Table.equals(other.transaction_Table))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AuditTrial [action=" + action + ", audit_Date_Time=" + audit_Date_Time + ", audited_Values="
				+ Arrays.toString(audited_Values) + ", id=" + id + ", transaction_Table=" + transaction_Table
				+ ", drkrishiUser=" + drkrishiUser + "]";
	}

	public AuditTrial(String action, Timestamp audit_Date_Time, byte[] audited_Values, int id, String transaction_Table,
			DRKrishiUser drkrishiUser) {
		super();
		this.action = action;
		this.audit_Date_Time = audit_Date_Time;
		this.audited_Values = audited_Values;
		this.id = id;
		this.transaction_Table = transaction_Table;
		this.drkrishiUser = drkrishiUser;
	}

}