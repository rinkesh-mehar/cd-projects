package com.drkrishi.prm.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * The persistent class for the DRKrishi_Users database table.
 * 
 */
@Entity
@Table(name="users")
@NamedQuery(name ="user",query= "SELECT d FROM DRKrishiUserEntity AS d, DrKrishiUserRoleEntity AS ur where d.status = 1 AND d.regionId = :regionId AND d.reportingTo = :userId AND d.id = ur.userId AND ur.roleId = 12")
public class DRKrishiUserEntity implements Serializable {

    /** Primary key. */
    protected static final String PK = "id";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique=true, nullable=false, precision=10)
    private int id;
    @Column(name="created_date_time")
    private Timestamp createdDateTime;
    @Column(name="created_by", length=255)
    private String createdBy;
    @Column(name="modified_by", length=255)
    private String modifiedBy;
    @Column(name="modified_date_time")
    private Timestamp modifiedDateTime;
    @Column(name="email_address", length=255)
    private String emailAddress;
    @Column(name="last_name", length=255)
    private String lastName;
    @Column(name="middle_name", length=255)
    private String middleName;
    @Column(name="mobile_number", length=255)
    private String mobileNumber;
    @Column(name="region_id", precision=10)
    private int regionId;
    @Column(name="state_id", precision=10)
    private int stateId;
    @Column(name="first_name", length=255)
    private String firstName;
    @Column(name="reporting_to", precision=10)
    private int reportingTo;
    @Column(length=500)
    private String comments;
    @Column(precision=10)
    private int status;

    /** Default constructor. */
    public DRKrishiUserEntity() {
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
     * Access method for createdDateTime.
     *
     * @return the current value of createdDateTime
     */
    public Timestamp getCreatedDateTime() {
        return createdDateTime;
    }

    /**
     * Setter method for createdDateTime.
     *
     * @param aCreatedDateTime the new value for createdDateTime
     */
    public void setCreatedDateTime(Timestamp aCreatedDateTime) {
        createdDateTime = aCreatedDateTime;
    }

    /**
     * Access method for createdBy.
     *
     * @return the current value of createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Setter method for createdBy.
     *
     * @param aCreatedBy the new value for createdBy
     */
    public void setCreatedBy(String aCreatedBy) {
        createdBy = aCreatedBy;
    }

    /**
     * Access method for modifiedBy.
     *
     * @return the current value of modifiedBy
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * Setter method for modifiedBy.
     *
     * @param aModifiedBy the new value for modifiedBy
     */
    public void setModifiedBy(String aModifiedBy) {
        modifiedBy = aModifiedBy;
    }

    /**
     * Access method for modifiedDateTime.
     *
     * @return the current value of modifiedDateTime
     */
    public Timestamp getModifiedDateTime() {
        return modifiedDateTime;
    }

    /**
     * Setter method for modifiedDateTime.
     *
     * @param aModifiedDateTime the new value for modifiedDateTime
     */
    public void setModifiedDateTime(Timestamp aModifiedDateTime) {
        modifiedDateTime = aModifiedDateTime;
    }

    /**
     * Access method for emailAddress.
     *
     * @return the current value of emailAddress
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Setter method for emailAddress.
     *
     * @param aEmailAddress the new value for emailAddress
     */
    public void setEmailAddress(String aEmailAddress) {
        emailAddress = aEmailAddress;
    }

    /**
     * Access method for lastName.
     *
     * @return the current value of lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter method for lastName.
     *
     * @param aLastName the new value for lastName
     */
    public void setLastName(String aLastName) {
        lastName = aLastName;
    }

    /**
     * Access method for middleName.
     *
     * @return the current value of middleName
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Setter method for middleName.
     *
     * @param aMiddleName the new value for middleName
     */
    public void setMiddleName(String aMiddleName) {
        middleName = aMiddleName;
    }

    /**
     * Access method for mobileNumber.
     *
     * @return the current value of mobileNumber
     */
    public String getMobileNumber() {
        return mobileNumber;
    }

    /**
     * Setter method for mobileNumber.
     *
     * @param aMobileNumber the new value for mobileNumber
     */
    public void setMobileNumber(String aMobileNumber) {
        mobileNumber = aMobileNumber;
    }

    /**
     * Access method for regionId.
     *
     * @return the current value of regionId
     */
    public int getRegionId() {
        return regionId;
    }

    /**
     * Setter method for regionId.
     *
     * @param aRegionId the new value for regionId
     */
    public void setRegionId(int aRegionId) {
        regionId = aRegionId;
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

    /**
     * Access method for firstName.
     *
     * @return the current value of firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter method for firstName.
     *
     * @param aFirstName the new value for firstName
     */
    public void setFirstName(String aFirstName) {
        firstName = aFirstName;
    }

    /**
     * Access method for reportingTo.
     *
     * @return the current value of reportingTo
     */
    public int getReportingTo() {
        return reportingTo;
    }

    /**
     * Setter method for reportingTo.
     *
     * @param aReportingTo the new value for reportingTo
     */
    public void setReportingTo(int aReportingTo) {
        reportingTo = aReportingTo;
    }

    
    public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	/**
     * Compares the key for this instance with another Users.
     *
     * @param other The object to compare to
     * @return True if other object is instance of class Users and the key objects are equal
     */
    private boolean equalKeys(Object other) {
        if (this==other) {
            return true;
        }
        if (!(other instanceof DRKrishiUserEntity)) {
            return false;
        }
        DRKrishiUserEntity that = (DRKrishiUserEntity) other;
        if (this.getId() != that.getId()) {
            return false;
        }
        return true;
    }

    /**
     * Compares this instance with another Users.
     *
     * @param other The object to compare to
     * @return True if the objects are the same
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof DRKrishiUserEntity)) return false;
        return this.equalKeys(other) && ((DRKrishiUserEntity)other).equalKeys(this);
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
		return "DRKrishiUserEntity [id=" + id + ", createdDateTime=" + createdDateTime + ", createdBy=" + createdBy
				+ ", modifiedBy=" + modifiedBy + ", modifiedDateTime=" + modifiedDateTime + ", emailAddress="
				+ emailAddress + ", lastName=" + lastName + ", middleName=" + middleName + ", mobileNumber="
				+ mobileNumber + ", regionId=" + regionId + ", stateId=" + stateId + ", firstName=" + firstName
				+ ", reportingTo=" + reportingTo + ", comments=" + comments + ", status=" + status + "]";
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
