package com.drkrishi.usermanagement.model;

import java.util.Date;

public class RoleModel {

	private int Id;
	private String UsersEmailaddress;
	private int UserStatus;
	private String UserPassword;
	private String Createdby;
	private Date CreatedDateTime;
	private String ModifiedBy;
	private Date ModifiedDateTime;
	private String ReportingTo;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getUsersEmailaddress() {
		return UsersEmailaddress;
	}

	public void setUsersEmailaddress(String usersEmailaddress) {
		UsersEmailaddress = usersEmailaddress;
	}

	public int getUserStatus() {
		return UserStatus;
	}

	public void setUserStatus(int userStatus) {
		UserStatus = userStatus;
	}

	public String getUserPassword() {
		return UserPassword;
	}

	public void setUserPassword(String userPassword) {
		UserPassword = userPassword;
	}

	public String getCreatedby() {
		return Createdby;
	}

	public void setCreatedby(String createdby) {
		Createdby = createdby;
	}

	public Date getCreatedDateTime() {
		return CreatedDateTime;
	}

	public void setCreatedDateTime(Date createdDateTime) {
		CreatedDateTime = createdDateTime;
	}

	public String getModifiedBy() {
		return ModifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		ModifiedBy = modifiedBy;
	}

	public Date getModifiedDateTime() {
		return ModifiedDateTime;
	}

	public void setModifiedDateTime(Date modifiedDateTime) {
		ModifiedDateTime = modifiedDateTime;
	}

	public String getReportingTo() {
		return ReportingTo;
	}

	public void setReportingTo(String reportingTo) {
		ReportingTo = reportingTo;
	}

}
