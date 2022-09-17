package com.krishi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "stress_control_measures")
public class StressControlMeasure {

	@Id
	@Column(unique = true, nullable = false, precision = 10)
	private int id;

	@Column(name = "name")
	private String Name;

	@Column(name = "status")
	private Integer Status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public void setStatus(Integer status) {
		Status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Name == null) ? 0 : Name.hashCode());
		result = prime * result + ((Status == null) ? 0 : Status.hashCode());
		result = prime * result + id;
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
		StressControlMeasure other = (StressControlMeasure) obj;
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
		if (id != other.id)
			return false;
		return true;
	}

}