package com.drkrishi.prm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

@Entity
@Table(name="tehsil")
public class TehsilEntity implements Serializable {

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
    public TehsilEntity() {
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
        if (!(other instanceof TehsilEntity)) {
            return false;
        }
        TehsilEntity that = (TehsilEntity) other;
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
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof TehsilEntity)) return false;
        return this.equalKeys(other) && ((TehsilEntity)other).equalKeys(this);
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
        result = 37*result + i;
        return result;
    }

    
    @Override
	public String toString() {
		return "TehsilEntity [id=" + id + ", districtId=" + districtId + ", code=" + code + ", name=" + name
				+ ", comment=" + comment + ", status=" + status + "]";
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
