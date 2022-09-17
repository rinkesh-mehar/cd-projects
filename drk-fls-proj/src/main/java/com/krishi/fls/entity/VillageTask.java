package com.krishi.fls.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "prs_task")
public class VillageTask implements Serializable {

	/** Primary key. */
	protected static final String PK = "taskId";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 10)
	private int taskId;
	@Column(name = "villages_id", precision = 10)
	private int villagesId;
	@Column(name = "prs_assignment_id", precision = 10)
	private int assignedId;
	@Column(name = "is_completed", precision = 10)
	private int isCompleted;
	@Column(name = "completed_date")
	private Date completedDate;

	/** Default constructor. */
	public VillageTask() {
		super();
	}

	/**
	 * Access method for taskId.
	 *
	 * @return the current value of taskId
	 */
	public int getTaskId() {
		return taskId;
	}

	/**
	 * Setter method for taskId.
	 *
	 * @param aTaskId the new value for taskId
	 */
	public void setTaskId(int aTaskId) {
		taskId = aTaskId;
	}

	/**
	 * Access method for villagesId.
	 *
	 * @return the current value of villagesId
	 */
	public int getVillagesId() {
		return villagesId;
	}

	/**
	 * Setter method for villagesId.
	 *
	 * @param aVillagesId the new value for villagesId
	 */
	public void setVillagesId(int aVillagesId) {
		villagesId = aVillagesId;
	}

	/**
	 * Access method for assignedId.
	 *
	 * @return the current value of assignedId
	 */
	public int getAssignedId() {
		return assignedId;
	}

	/**
	 * Setter method for assignedId.
	 *
	 * @param aAssignedId the new value for assignedId
	 */
	public void setAssignedId(int aAssignedId) {
		assignedId = aAssignedId;
	}

	/**
	 * Access method for isCompleted.
	 *
	 * @return the current value of isCompleted
	 */
	public int getIsCompleted() {
		return isCompleted;
	}

	/**
	 * Setter method for isCompleted.
	 *
	 * @param aIsCompleted the new value for isCompleted
	 */
	public void setIsCompleted(int aIsCompleted) {
		isCompleted = aIsCompleted;
	}

	/**
	 * Access method for completedDate.
	 *
	 * @return the current value of completedDate
	 */
	public Date getCompletedDate() {
		return completedDate;
	}

	/**
	 * Setter method for completedDate.
	 *
	 * @param aCompletedDate the new value for completedDate
	 */
	public void setCompletedDate(Date aCompletedDate) {
		completedDate = aCompletedDate;
	}

	/**
	 * Compares the key for this instance with another VillageTask.
	 *
	 * @param other The object to compare to
	 * @return True if other object is instance of class VillageTask and the key
	 *         objects are equal
	 */
	private boolean equalKeys(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof VillageTask)) {
			return false;
		}
		VillageTask that = (VillageTask) other;
		if (this.getTaskId() != that.getTaskId()) {
			return false;
		}
		return true;
	}

	/**
	 * Compares this instance with another VillageTask.
	 *
	 * @param other The object to compare to
	 * @return True if the objects are the same
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof VillageTask))
			return false;
		return this.equalKeys(other) && ((VillageTask) other).equalKeys(this);
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
		i = getTaskId();
		result = 37 * result + i;
		return result;
	}

	/**
	 * Returns a debug-friendly String representation of this instance.
	 *
	 * @return String representation of this instance
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("[VillageTask |");
		sb.append(" taskId=").append(getTaskId());
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
		ret.put("taskId", Integer.valueOf(getTaskId()));
		return ret;
	}

}
