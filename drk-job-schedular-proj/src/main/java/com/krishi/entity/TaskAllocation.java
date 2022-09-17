package com.krishi.entity;

import java.io.Serializable;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="task")
public class TaskAllocation implements Serializable {

    /** Primary key. */
    protected static final String PK = "id";
    private static final SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    @Id
    @Column(name="id")
    private String id;
    @Column(name="task_date")
    private Date taskDate;
    @Column(name="task_type_id")
    private int taskTypeId;
    @Column(name="assignee_id", precision=10)
    private Integer assigneeId;
    @Column(length=3)
    private Integer status;
    @Column(name="entity_type_id", precision=10)
    private int entityTypeId;
	@Column(name="barcode", precision=10)
    private String barCode;
    @Column(name="entity_id", precision=10)
    private String entityId;
    @Column(name="start_time")
    private Time startTime;
    @Column(name="end_time")
    private Time endTime;

    


	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		try {
			Date date = sd.parse(startTime);
			this.startTime = new Time(date.getTime());
		} catch (ParseException e) {
			this.startTime = null;
		}
		
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		try {
			Date date = sd.parse(endTime);
			this.startTime = new Time(date.getTime());
		} catch (ParseException e) {
			this.startTime = null;
		}
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

    /** Default constructor. */
    public TaskAllocation() {
        super();
    }

    public Date getTaskDate() {
        return taskDate;
    }

    /**
     * Setter method for taskDate.
     *
     * @param aTaskDate the new value for taskDate
     */
    public void setTaskDate(Date aTaskDate) {
        taskDate = aTaskDate;
    }

    
    /**
     * Access method for taskTypeId.
     *
     * @return the current value of taskTypeId
     */
    public int getTaskTypeId() {
        return taskTypeId;
    }

    /**
     * Setter method for taskTypeId.
     *
     * @param aTaskTypeId the new value for taskTypeId
     */
    public void setTaskTypeId(Integer aTaskTypeId) {
        taskTypeId = aTaskTypeId;
    }

    /**
     * Access method for assigneeId.
     *
     * @return the current value of assigneeId
     */
    public Integer getAssigneeId() {
        return assigneeId;
    }

    /**
     * Setter method for assigneeId.
     *
     * @param aAssigneeId the new value for assigneeId
     */
    public void setAssigneeId(Integer aAssigneeId) {
        assigneeId = aAssigneeId;
    }

    /**
     * Access method for status.
     *
     * @return true if and only if status is currently true
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * Setter method for status.
     *
     * @param aStatus the new value for status
     */
    public void setStatus(Integer aStatus) {
        status = aStatus;
    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getEntityTypeId() {
		return entityTypeId;
	}

	public void setEntityTypeId(int entityTypeId) {
		this.entityTypeId = entityTypeId;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public void setTaskTypeId(int taskTypeId) {
		this.taskTypeId = taskTypeId;
	}

	/**
     * Compares the key for this instance with another TaskAllocation.
     *
     * @param other The object to compare to
     * @return True if other object is instance of class TaskAllocation and the key objects are equal
     */
    private boolean equalKeys(Object other) {
        if (this==other) {
            return true;
        }
        if (!(other instanceof TaskAllocation)) {
            return false;
        }
        TaskAllocation that = (TaskAllocation) other;
        if (this.getId() != that.getId()) {
            return false;
        }
        return true;
    }

    /**
     * Compares this instance with another TaskAllocation.
     *
     * @param other The object to compare to
     * @return True if the objects are the same
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof TaskAllocation)) return false;
        return this.equalKeys(other) && ((TaskAllocation)other).equalKeys(this);
    }


    /**
     * Returns a debug-friendly String representation of this instance.
     *
     * @return String representation of this instance
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("[TaskAllocation |");
        sb.append(" id=").append(getId());
        sb.append("]");
        return sb.toString();
    }

    /**
     * Return all elements of the primary key.
     *
     * @return Map of key names to values
     */
    @JsonIgnore
    public Map<String, Object> getPrimaryKey() {
        Map<String, Object> ret = new LinkedHashMap<String, Object>(6);
        ret.put("id", Integer.valueOf(getId()));
        return ret;
    }

}
