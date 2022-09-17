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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="task_stress_details")
public class TaskStressDetails implements Serializable {

    /** Primary key. */
    protected static final String PK = "id";

    @Id
    @Column(unique=true, nullable=false, precision=10)
    private String id;
    @Column(name="task_id", nullable=false, precision=10)
    private String taskId;
    @Column(name="symptom_id", precision=10)
    private int symptomId;
    @Column(name="left_photo", length=255)
    private String leftPhoto;
    @Column(name="left_incident", length=3)
    private Integer leftIncident;
    @Column(name="left_severity", length=3)
    private Integer leftSeverity;
    @Column(name="is_benchmarked_left")
    private boolean isBenchmarkedLeft;
    @Column(name="front_photo", length=255)
    private String frontPhoto;
    @Column(name="front_incident", length=3)
    private Integer frontIncident;
    @Column(name="front_severity", length=3)
    private Integer frontSeverity;
    @Column(name="is_benchmarked_front", length=3)
    private boolean isBenchmarkedFront;
    @Column(name="right_photo", length=255)
    private String rightPhoto;
    @Column(name="right_incident", length=3)
    private Integer rightIncident;
    @Column(name="right_severity", length=3)
    private Integer rightSeverity;
    @Column(name="is_benchmarked_right")
    private boolean isBenchmarkedRight;
    
    @Column(name="spot_id")
    private String spotId;

    @Column(name = "average_severity")
    private Double averageSeverity;


    /** Default constructor. */
    public TaskStressDetails() {
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
     * Access method for taskId.
     *
     * @return the current value of taskId
     */
    public String getTaskId() {
        return taskId;
    }
    

    /**
     * Setter method for taskId.
     *
     * @param aTaskId the new value for taskId
     */
    public void setTaskId(String aTaskId) {
        taskId = aTaskId;
    }

    /**
     * Access method for symptomId.
     *
     * @return the current value of symptomId
     */
    public int getSymptomId() {
        return symptomId;
    }

    /**
     * Setter method for symptomId.
     *
     * @param aSymptomId the new value for symptomId
     */
    public void setSymptomId(int aSymptomId) {
        symptomId = aSymptomId;
    }

    /**
     * Access method for leftPhoto.
     *
     * @return the current value of leftPhoto
     */
    public String getLeftPhoto() {
        return leftPhoto;
    }

    /**
     * Setter method for leftPhoto.
     *
     * @param aLeftPhoto the new value for leftPhoto
     */
    public void setLeftPhoto(String aLeftPhoto) {
        leftPhoto = aLeftPhoto;
    }

    /**
     * Access method for leftIncident.
     *
     * @return true if and only if leftIncident is currently true
     */
    public Integer getLeftIncident() {
        return leftIncident;
    }

    /**
     * Setter method for leftIncident.
     *
     * @param aLeftIncident the new value for leftIncident
     */
    public void setLeftIncident(Integer aLeftIncident) {
        leftIncident = aLeftIncident;
    }

    /**
     * Access method for leftSeverity.
     *
     * @return true if and only if leftSeverity is currently true
     */
    public Integer getLeftSeverity() {
        return leftSeverity;
    }

    /**
     * Setter method for leftSeverity.
     *
     * @param aLeftSeverity the new value for leftSeverity
     */
    public void setLeftSeverity(Integer aLeftSeverity) {
        leftSeverity = aLeftSeverity;
    }

    /**
     * Access method for frontPhoto.
     *
     * @return the current value of frontPhoto
     */
    public String getFrontPhoto() {
        return frontPhoto;
    }

    /**
     * Setter method for frontPhoto.
     *
     * @param aFrontPhoto the new value for frontPhoto
     */
    public void setFrontPhoto(String aFrontPhoto) {
        frontPhoto = aFrontPhoto;
    }

    /**
     * Access method for frontIncident.
     *
     * @return true if and only if frontIncident is currently true
     */
    public Integer getFrontIncident() {
        return frontIncident;
    }

    /**
     * Setter method for frontIncident.
     *
     * @param aFrontIncident the new value for frontIncident
     */
    public void setFrontIncident(Integer aFrontIncident) {
        frontIncident = aFrontIncident;
    }

    /**
     * Access method for frontSeverity.
     *
     * @return true if and only if frontSeverity is currently true
     */
    public Integer getFrontSeverity() {
        return frontSeverity;
    }

    /**
     * Setter method for frontSeverity.
     *
     * @param aFrontSeverity the new value for frontSeverity
     */
    public void setFrontSeverity(Integer aFrontSeverity) {
        frontSeverity = aFrontSeverity;
    }

    /**
     * Access method for rightPhoto.
     *
     * @return the current value of rightPhoto
     */
    public String getRightPhoto() {
        return rightPhoto;
    }

    /**
     * Setter method for rightPhoto.
     *
     * @param aRightPhoto the new value for rightPhoto
     */
    public void setRightPhoto(String aRightPhoto) {
        rightPhoto = aRightPhoto;
    }

    /**
     * Access method for rightIncident.
     *
     * @return true if and only if rightIncident is currently true
     */
    public Integer getRightIncident() {
        return rightIncident;
    }

    /**
     * Setter method for rightIncident.
     *
     * @param aRightIncident the new value for rightIncident
     */
    public void setRightIncident(Integer aRightIncident) {
        rightIncident = aRightIncident;
    }

    /**
     * Access method for rightSeverity.
     *
     * @return true if and only if rightSeverity is currently true
     */
    public Integer getRightSeverity() {
        return rightSeverity;
    }

    /**
     * Setter method for rightSeverity.
     *
     * @param aRightSeverity the new value for rightSeverity
     */
    public void setRightSeverity(Integer aRightSeverity) {
        rightSeverity = aRightSeverity;
    }

    public boolean isBenchmarkedLeft() {
		return isBenchmarkedLeft;
	}

	public void setBenchmarkedLeft(boolean isBenchmarkedLeft) {
		this.isBenchmarkedLeft = isBenchmarkedLeft;
	}

	public boolean isBenchmarkedFront() {
		return isBenchmarkedFront;
	}

	public void setBenchmarkedFront(boolean isBenchmarkedFront) {
		this.isBenchmarkedFront = isBenchmarkedFront;
	}

	public boolean isBenchmarkedRight() {
		return isBenchmarkedRight;
	}

	public void setBenchmarkedRight(boolean isBenchmarkedRight) {
		this.isBenchmarkedRight = isBenchmarkedRight;
	}

	public String getSpotId() {
		return spotId;
	}

	public void setSpotId(String spotId) {
		this.spotId = spotId;
	}

    public Double getAverageSeverity() {
        return averageSeverity;
    }

    public void setAverageSeverity(Double averageSeverity) {
        this.averageSeverity = averageSeverity;
    }

    /**
     * Compares the key for this instance with another TaskStressDetails.
     *
     * @param other The object to compare to
     * @return True if other object is instance of class TaskStressDetails and the key objects are equal
     */
    private boolean equalKeys(Object other) {
        if (this==other) {
            return true;
        }
        if (!(other instanceof TaskStressDetails)) {
            return false;
        }
        TaskStressDetails that = (TaskStressDetails) other;
        if (this.getId() != that.getId()) {
            return false;
        }
        return true;
    }

    /**
     * Compares this instance with another TaskStressDetails.
     *
     * @param other The object to compare to
     * @return True if the objects are the same
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof TaskStressDetails)) return false;
        return this.equalKeys(other) && ((TaskStressDetails)other).equalKeys(this);
    }

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
        StringBuffer sb = new StringBuffer("[TaskStressDetails |");
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
