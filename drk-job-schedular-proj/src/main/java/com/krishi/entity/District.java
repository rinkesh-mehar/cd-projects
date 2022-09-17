package com.krishi.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "district")
public class District implements Serializable {

	/** Primary key. */
	protected static final String PK = "id";

	@Id
	@Column(unique = true, nullable = false, precision = 10)
	private int id;
	@Column(length = 255)
	private String comment;
	@Column(length = 255)
	private String name;
	@Column(name = "district_code", nullable = false, precision = 10)
	private int districtCode;
	@Column(nullable = false, precision = 10)
	private int status;
	@Column(name = "state_id", precision = 10)
	private int stateId;

	/** Default constructor. */
	public District() {
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
	 * Access method for comment.
	 *
	 * @return the current value of comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Setter method for comment.
	 *
	 * @param aComment the new value for comment
	 */
	public void setComment(String aComment) {
		comment = aComment;
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
	 * Access method for districtCode.
	 *
	 * @return the current value of districtCode
	 */
	public int getDistrictCode() {
		return districtCode;
	}

	/**
	 * Setter method for districtCode.
	 *
	 * @param aDistrictCode the new value for districtCode
	 */
	public void setDistrictCode(int aDistrictCode) {
		districtCode = aDistrictCode;
	}

	/**
	 * Access method for status.
	 *
	 * @return the current value of status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * Setter method for status.
	 *
	 * @param aStatus the new value for status
	 */
	public void setStatus(int aStatus) {
		status = aStatus;
	}

	/**
	 * Access method for stateId.
	 *
	 * @return the current value of stateId
	 */
	public int getStateId() {
		return stateId;
	}

	/**
	 * Setter method for stateId.
	 *
	 * @param aStateId the new value for stateId
	 */
	public void setStateId(int aStateId) {
		stateId = aStateId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + districtCode;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + stateId;
		result = prime * result + status;
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
		District other = (District) obj;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (districtCode != other.districtCode)
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (stateId != other.stateId)
			return false;
		if (status != other.status)
			return false;
		return true;
	}

}
