// Generated with g9.

package com.drkrishi.iqa.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="farm_case")
public class FarmCase implements Serializable {

    /** Primary key. */
    protected static final String PK = "id";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique=true, nullable=false, precision=10)
    private String id;
    @Column(name="farm_id", nullable=false, precision=10)
    private int farmId;
    
    @Column(name="case_sample_status")
    private String caseSampleStatus;

    /** Default constructor. */
    public FarmCase() {
        super();
    }

    
    public String getCaseSampleStatus() {
		return caseSampleStatus;
	}


	public void setCaseSampleStatus(String caseSampleStatus) {
		this.caseSampleStatus = caseSampleStatus;
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
     * Access method for farmId.
     *
     * @return the current value of farmId
     */
    public int getFarmId() {
        return farmId;
    }

    /**
     * Setter method for farmId.
     *
     * @param aFarmId the new value for farmId
     */
    public void setFarmId(int aFarmId) {
        farmId = aFarmId;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("[FarmCase |");
        sb.append(" id=").append(getId());
        sb.append("]");
        return sb.toString();
    }

   

}
