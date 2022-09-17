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
@Table(name="stress_symptoms")
public class StressSymptoms implements Serializable {

    /** Primary key. */
    protected static final String PK = "id";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique=true, nullable=false, precision=10)
    private int id;
    @Column(name="description", length=255)
    private String symptomDesc;
    @Column(length=255)
    private String image;
    @Column(name="is_important", nullable=false, length=3)
    private boolean isImportant;
    
    @Column(name="stress_id", precision=10)
    private int stressId;

    @Column(name="commodity_id", precision=10)
    private int commodityId;
    
    @Column(name="plant_part_id", precision=10)
    private int plantPartId;
    
    @Column(name="status", precision=10)
    private int status;
    
    
    
    /** Default constructor. */
    public StressSymptoms() {
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
     * Access method for symptomDesc.
     *
     * @return the current value of symptomDesc
     */
    public String getSymptomDesc() {
        return symptomDesc;
    }

    /**
     * Setter method for symptomDesc.
     *
     * @param aSymptomDesc the new value for symptomDesc
     */
    public void setSymptomDesc(String aSymptomDesc) {
        symptomDesc = aSymptomDesc;
    }

    /**
     * Access method for image.
     *
     * @return the current value of image
     */
    public String getImage() {
        return image;
    }

    /**
     * Setter method for image.
     *
     * @param aImage the new value for image
     */
    public void setImage(String aImage) {
        image = aImage;
    }

    /**
     * Access method for isImportant.
     *
     * @return true if and only if isImportant is currently true
     */
    public boolean getIsImportant() {
        return isImportant;
    }

    /**
     * Setter method for isImportant.
     *
     * @param aIsImportant the new value for isImportant
     */
    public void setIsImportant(boolean aIsImportant) {
        isImportant = aIsImportant;
    }

    /**
     * Access method for stressId.
     *
     * @return the current value of stressId
     */
    public int getStressId() {
        return stressId;
    }

    
    public int getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(int commodityId) {
		this.commodityId = commodityId;
	}

	public int getPlantPartId() {
		return plantPartId;
	}

	public void setPlantPartId(int plantPartId) {
		this.plantPartId = plantPartId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setImportant(boolean isImportant) {
		this.isImportant = isImportant;
	}

	/**
     * Setter method for stressId.
     *
     * @param aStressId the new value for stressId
     */
    public void setStressId(int aStressId) {
        stressId = aStressId;
    }

    /**
     * Compares the key for this instance with another StressSymptoms.
     *
     * @param other The object to compare to
     * @return True if other object is instance of class StressSymptoms and the key objects are equal
     */
    private boolean equalKeys(Object other) {
        if (this==other) {
            return true;
        }
        if (!(other instanceof StressSymptoms)) {
            return false;
        }
        StressSymptoms that = (StressSymptoms) other;
        if (this.getId() != that.getId()) {
            return false;
        }
        return true;
    }

    /**
     * Compares this instance with another StressSymptoms.
     *
     * @param other The object to compare to
     * @return True if the objects are the same
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof StressSymptoms)) return false;
        return this.equalKeys(other) && ((StressSymptoms)other).equalKeys(this);
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
        StringBuffer sb = new StringBuffer("[StressSymptoms |");
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
