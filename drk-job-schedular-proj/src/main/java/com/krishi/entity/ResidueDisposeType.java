package com.krishi.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "residue_dispose_type")
public class ResidueDisposeType implements Serializable {

	/** Primary key. */
	protected static final String PK = "id";

	@Id
	@Column(unique = true, nullable = false, precision = 10)
	private int id;
	@Column(length = 45)
	private String name;
	@Column(nullable = false, precision = 10)
	private int status;

	/** Default constructor. */
	public ResidueDisposeType() {
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
	public void setId(int Id) {
		id = Id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * Access method for name.
	 *
	 * @return the current value of name
	 */

	/**
	 * Compares the key for this instance with another ResidueDisposeType.
	 *
	 * @param other The object to compare to
	 * @return True if other object is instance of class ResidueDisposeType and the
	 *         key objects are equal
	 */
	private boolean equalKeys(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ResidueDisposeType)) {
			return false;
		}
		ResidueDisposeType that = (ResidueDisposeType) other;
		if (this.getId() != that.getId()) {
			return false;
		}
		return true;
	}

	/**
	 * Compares this instance with another ResidueDisposeType.
	 *
	 * @param other The object to compare to
	 * @return True if the objects are the same
	 */

	/**
	 * Returns a hash code for this instance.
	 *
	 * @return Hash code
	 */

	/**
	 * Returns a debug-friendly String representation of this instance.
	 *
	 * @return String representation of this instance
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("[ResidueDisposeType |");
		sb.append(" id=").append(getId());
		sb.append("]");
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		ResidueDisposeType other = (ResidueDisposeType) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (status != other.status)
			return false;
		return true;
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
