package com.drkrishi.usermanagement.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="prs_assignment")
public class PrVillageAssigment implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Primary key. */
    protected static final String PK = "id";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false, precision=10)
    private int assigmentId;
    
    @Column(name="created_date")
    private Timestamp createdDate;
    
    @Column(precision=10)
    private int month;
    
    @Column(name="week_number", precision=10)
    private int weekNumber;
    
    @Column(nullable=false, precision=10)
    private int year;
    
    @Column(name="created_user_id", precision=10)
    private int createdUserId;
    
    @Column(name="prs_id", precision=10)
    private int prsId;

    /** Default constructor. */
    public PrVillageAssigment() {
        super();
    }

    /**
     * Access method for assigmentId.
     *
     * @return the current value of assigmentId
     */
    public int getAssigmentId() {
        return assigmentId;
    }

    /**
     * Setter method for assigmentId.
     *
     * @param aAssigmentId the new value for assigmentId
     */
    public void setAssigmentId(int aAssigmentId) {
        assigmentId = aAssigmentId;
    }

    /**
     * Access method for createdDate.
     *
     * @return the current value of createdDate
     */
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    /**
     * Setter method for createdDate.
     *
     * @param aCreatedDate the new value for createdDate
     */
    public void setCreatedDate(Timestamp aCreatedDate) {
        createdDate = aCreatedDate;
    }

    /**
     * Access method for month.
     *
     * @return the current value of month
     */
    public int getMonth() {
        return month;
    }

    /**
     * Setter method for month.
     *
     * @param aMonth the new value for month
     */
    public void setMonth(int aMonth) {
        month = aMonth;
    }

    /**
     * Access method for weekNumber.
     *
     * @return the current value of weekNumber
     */
    public int getWeekNumber() {
        return weekNumber;
    }

    /**
     * Setter method for weekNumber.
     *
     * @param aWeekNumber the new value for weekNumber
     */
    public void setWeekNumber(int aWeekNumber) {
        weekNumber = aWeekNumber;
    }

    /**
     * Access method for year.
     *
     * @return the current value of year
     */
    public int getYear() {
        return year;
    }

    /**
     * Setter method for year.
     *
     * @param aYear the new value for year
     */
    public void setYear(int aYear) {
        year = aYear;
    }

    /**
     * Access method for createdUserId.
     *
     * @return the current value of createdUserId
     */
    public int getCreatedUserId() {
        return createdUserId;
    }

    /**
     * Setter method for createdUserId.
     *
     * @param aCreatedUserId the new value for createdUserId
     */
    public void setCreatedUserId(int aCreatedUserId) {
        createdUserId = aCreatedUserId;
    }

    /**
     * Access method for prsId.
     *
     * @return the current value of prsId
     */
    public int getPrsId() {
        return prsId;
    }

    /**
     * Setter method for prsId.
     *
     * @param aPrsId the new value for prsId
     */
    public void setPrsId(int aPrsId) {
        prsId = aPrsId;
    }

    /**
     * Compares the key for this instance with another PrVillageAssigment.
     *
     * @param other The object to compare to
     * @return True if other object is instance of class PrVillageAssigment and the key objects are equal
     */
    private boolean equalKeys(Object other) {
        if (this==other) {
            return true;
        }
        if (!(other instanceof PrVillageAssigment)) {
            return false;
        }
        PrVillageAssigment that = (PrVillageAssigment) other;
        if (this.getAssigmentId() != that.getAssigmentId()) {
            return false;
        }
        return true;
    }

    /**
     * Compares this instance with another PrVillageAssigment.
     *
     * @param other The object to compare to
     * @return True if the objects are the same
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof PrVillageAssigment)) return false;
        return this.equalKeys(other) && ((PrVillageAssigment)other).equalKeys(this);
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
        i = getAssigmentId();
        result = 37*result + i;
        return result;
    }

	@Override
	public String toString() {
		return "PrVillageAssigment [assigmentId=" + assigmentId + ", createdDate=" + createdDate + ", month=" + month
				+ ", weekNumber=" + weekNumber + ", year=" + year + ", createdUserId=" + createdUserId + ", prsId="
				+ prsId + "]";
	}

}
