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
@Table(name="farmer_machinery_info")
public class FarmerMachineryInfo implements Serializable {

    /** Primary key. */
    protected static final String PK = "id";


    @Id
	@Column(name="id")
	private String id;
    @Column(name="farmer_id", precision=10)
    private String farmerId;
    @Column(name="machinery_id", precision=10)
    private int machineryId;
    @Column(precision=10)
    private int count;

    /** Default constructor. */
    public FarmerMachineryInfo() {
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
     * Access method for machineryId.
     *
     * @return the current value of machineryId
     */
    public int getMachineryId() {
        return machineryId;
    }

    /**
     * Setter method for machineryId.
     *
     * @param aMachineryId the new value for machineryId
     */
    public void setMachineryId(int aMachineryId) {
        machineryId = aMachineryId;
    }

    /**
     * Access method for count.
     *
     * @return the current value of count
     */
    public int getCount() {
        return count;
    }

    /**
     * Setter method for count.
     *
     * @param aCount the new value for count
     */
    public void setCount(int aCount) {
        count = aCount;
    }

 


}
