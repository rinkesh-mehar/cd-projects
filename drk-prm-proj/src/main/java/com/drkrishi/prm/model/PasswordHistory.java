package com.drkrishi.prm.model;

public class PasswordHistory {

	private int id;

	private String user_Email_Id;

	private String user_Passwords;

	public PasswordHistory() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser_Email_Id() {
		return this.user_Email_Id;
	}

	public void setUser_Email_Id(String user_Email_Id) {
		this.user_Email_Id = user_Email_Id;
	}

	public String getUser_Passwords() {
		return this.user_Passwords;
	}

	public void setUser_Passwords(String user_Passwords) {
		this.user_Passwords = user_Passwords;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((user_Email_Id == null) ? 0 : user_Email_Id.hashCode());
		result = prime * result + ((user_Passwords == null) ? 0 : user_Passwords.hashCode());
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
		PasswordHistory other = (PasswordHistory) obj;
		if (id != other.id)
			return false;
		if (user_Email_Id == null) {
			if (other.user_Email_Id != null)
				return false;
		} else if (!user_Email_Id.equals(other.user_Email_Id))
			return false;
		if (user_Passwords == null) {
			if (other.user_Passwords != null)
				return false;
		} else if (!user_Passwords.equals(other.user_Passwords))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PasswordHistory [id=" + id + ", user_Email_Id=" + user_Email_Id + ", user_Passwords=" + user_Passwords
				+ "]";
	}

	public PasswordHistory(int id, String user_Email_Id, String user_Passwords) {
		super();
		this.id = id;
		this.user_Email_Id = user_Email_Id;
		this.user_Passwords = user_Passwords;
	}

}