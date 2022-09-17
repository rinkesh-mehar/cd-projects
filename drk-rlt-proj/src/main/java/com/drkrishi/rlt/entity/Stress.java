package com.drkrishi.rlt.entity;

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
@Table(name="stress")
public class Stress implements Serializable {

    /** Primary key. */
    protected static final String PK = "id";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique=true, nullable=false, precision=10)
    private int id;
    @Column(length=255)
    private String name;
    @Column(name="commodity_id", precision=10)
    private int commodityId;
    @Column(name="phenophase_id", precision=10)
    private int phenophaseId;
    @Column(name="plant_part_id", precision=10)
    private int plantPartId;
    @Column(name="stress_type_id", precision=10)
    private int stressTypeId;
    @Column(name = "stress_id")
    private int stressId;

    /** Default constructor. */
    public Stress() {
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
     * Access method for commodityId.
     *
     * @return the current value of commodityId
     */
    public int getCommodityId() {
        return commodityId;
    }

    /**
     * Setter method for commodityId.
     *
     * @param aCommodityId the new value for commodityId
     */
    public void setCommodityId(int aCommodityId) {
        commodityId = aCommodityId;
    }

    /**
     * Access method for phenophaseId.
     *
     * @return the current value of phenophaseId
     */
    public int getPhenophaseId() {
        return phenophaseId;
    }

    /**
     * Setter method for phenophaseId.
     *
     * @param aPhenophaseId the new value for phenophaseId
     */
    public void setPhenophaseId(int aPhenophaseId) {
        phenophaseId = aPhenophaseId;
    }

    /**
     * Access method for plantPartId.
     *
     * @return the current value of plantPartId
     */
    public int getPlantPartId() {
        return plantPartId;
    }

    /**
     * Setter method for plantPartId.
     *
     * @param aPlantPartId the new value for plantPartId
     */
    public void setPlantPartId(int aPlantPartId) {
        plantPartId = aPlantPartId;
    }

    /**
     * Access method for stressTypeId.
     *
     * @return the current value of stressTypeId
     */
    public int getStressTypeId() {
        return stressTypeId;
    }

    /**
     * Setter method for stressTypeId.
     *
     * @param aStressTypeId the new value for stressTypeId
     */
    public void setStressTypeId(int aStressTypeId) {
        stressTypeId = aStressTypeId;
    }

    public int getStressId() {
        return stressId;
    }

    public void setStressId(int stressId) {
        this.stressId = stressId;
    }

    /**
     * Compares the key for this instance with another Stress.
     *
     * @param other The object to compare to
     * @return True if other object is instance of class Stress and the key objects are equal
     */
    private boolean equalKeys(Object other) {
        if (this==other) {
            return true;
        }
        if (!(other instanceof Stress)) {
            return false;
        }
        Stress that = (Stress) other;
        if (this.getId() != that.getId()) {
            return false;
        }
        return true;
    }

    /**
     * Compares this instance with another Stress.
     *
     * @param other The object to compare to
     * @return True if the objects are the same
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Stress)) return false;
        return this.equalKeys(other) && ((Stress)other).equalKeys(this);
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

    /**
     * Returns a debug-friendly String representation of this instance.
     *
     * @return String representation of this instance
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("[Stress |");
        sb.append(" id=").append(getId());
        sb.append("]");
        return sb.toString();
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
