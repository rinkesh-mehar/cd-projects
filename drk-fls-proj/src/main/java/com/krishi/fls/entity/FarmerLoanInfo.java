package com.krishi.fls.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="farmer_loan_info")
public class FarmerLoanInfo implements Serializable {

    /** Primary key. */
    protected static final String PK = "id";

    @Id
	@Column(name="id")
	private String id;
    @Column(name="farmer_id", precision=10)
    private String farmerId;
    @Column(name="source_id", precision=10)
    private int sourceId;
    @Column(precision=10)
    private int amount;
    @Column(name="purpose_id", precision=10)
    private int purposeId;

    /** Default constructor. */
    public FarmerLoanInfo() {
        super();
    }

    /**
     * Access method for id.
     *
     * @return the current value of id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter method for id.
     *
     * @param aId the new value for id
     */
    public void setId(String aId) {
        id = aId;
    }

    /**
     * Access method for farmerId.
     *
     * @return the current value of farmerId
     */
    public String getFarmerId() {
        return farmerId;
    }

    /**
     * Setter method for farmerId.
     *
     * @param aFarmerId the new value for farmerId
     */
    public void setFarmerId(String aFarmerId) {
        farmerId = aFarmerId;
    }

    /**
     * Access method for sourceId.
     *
     * @return the current value of sourceId
     */
    public int getSourceId() {
        return sourceId;
    }

    /**
     * Setter method for sourceId.
     *
     * @param aSourceId the new value for sourceId
     */
    public void setSourceId(int aSourceId) {
        sourceId = aSourceId;
    }

    /**
     * Access method for amount.
     *
     * @return the current value of amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Setter method for amount.
     *
     * @param aAmount the new value for amount
     */
    public void setAmount(int aAmount) {
        amount = aAmount;
    }

    /**
     * Access method for purposeId.
     *
     * @return the current value of purposeId
     */
    public int getPurposeId() {
        return purposeId;
    }

    /**
     * Setter method for purposeId.
     *
     * @param aPurposeId the new value for purposeId
     */
    public void setPurposeId(int aPurposeId) {
        purposeId = aPurposeId;
    }

    /**
     * Compares the key for this instance with another FarmerLoanInfo.
     *
     * @param other The object to compare to
     * @return True if other object is instance of class FarmerLoanInfo and the key objects are equal
     */
    private boolean equalKeys(Object other) {
        if (this==other) {
            return true;
        }
        if (!(other instanceof FarmerLoanInfo)) {
            return false;
        }
        FarmerLoanInfo that = (FarmerLoanInfo) other;
        if (this.getId() != that.getId()) {
            return false;
        }
        return true;
    }


}
