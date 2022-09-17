package com.krishi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity
public class EnrollmentPlace {

	@Id
	@Column(name = "id")
	private Integer ID;

	@Column(name = "name")
	private String Name;

	@Column(name = "status")
	private Integer Status;

	public EnrollmentPlace() {
		super();
	}

	public EnrollmentPlace(Integer iD, String name, Integer status) {
		super();
		ID = iD;
		Name = name;
		Status = status;
	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public Integer getStatus() {
		return Status;
	}

	public void setStatus(int i) {
		Status = i;
	}

	@Override
	public String toString() {
		return "EnrollmentPlace [ID=" + ID + ", Name=" + Name + ", Status=" + Status + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ID == null) ? 0 : ID.hashCode());
		result = prime * result + ((Name == null) ? 0 : Name.hashCode());
		result = prime * result + ((Status == null) ? 0 : Status.hashCode());
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
		EnrollmentPlace other = (EnrollmentPlace) obj;
		if (ID == null) {
			if (other.ID != null)
				return false;
		} else if (!ID.equals(other.ID))
			return false;
		if (Name == null) {
			if (other.Name != null)
				return false;
		} else if (!Name.equals(other.Name))
			return false;
		if (Status == null) {
			if (other.Status != null)
				return false;
		} else if (!Status.equals(other.Status))
			return false;
		return true;
	}
	
	

}
