package com.krishi.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tehsil")
public class Tehsil implements Serializable {

    /** Primary key. */
    protected static final String PK = "id";

    @Id
    @Column(unique=true, nullable=false, precision=10)
    private int id;
    @Column(name="district_id", nullable=false, precision=10)
    private int districtId;
    @Column(precision=10)
    private int code;
    @Column(nullable=false, length=100)
    private String name;
    @Column(length=255)
    private String comment;
    @Column(nullable=false, precision=10)
    private int status;

    /** Default constructor. */
    public Tehsil() {
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
     * Access method for districtId.
     *
     * @return the current value of districtId
     */
    public int getDistrictId() {
        return districtId;
    }

    /**
     * Setter method for districtId.
     *
     * @param aDistrictId the new value for districtId
     */
    public void setDistrictId(int aDistrictId) {
        districtId = aDistrictId;
    }

    /**
     * Access method for code.
     *
     * @return the current value of code
     */
    public int getCode() {
        return code;
    }

    /**
     * Setter method for code.
     *
     * @param aCode the new value for code
     */
    public void setCode(int aCode) {
        code = aCode;
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
     * Compares the key for this instance with another Tehsil.
     *
     * @param other The object to compare to
     * @return True if other object is instance of class Tehsil and the key objects are equal
     */
    private boolean equalKeys(Object other) {
        if (this==other) {
            return true;
        }
        if (!(other instanceof Tehsil)) {
            return false;
        }
        Tehsil that = (Tehsil) other;
        if (this.getId() != that.getId()) {
            return false;
        }
        return true;
    }

    /**
     * Compares this instance with another Tehsil.
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
        StringBuffer sb = new StringBuffer("[Tehsil |");
        sb.append(" id=").append(getId());
        sb.append("]");
        return sb.toString();
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + code;
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + districtId;
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
		Tehsil other = (Tehsil) obj;
		if (code != other.code)
			return false;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (districtId != other.districtId)
			return false;
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
