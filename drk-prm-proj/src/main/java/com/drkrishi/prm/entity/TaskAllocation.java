package com.drkrishi.prm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

@Entity
@Table(name="task_allocation")
public class TaskAllocation implements Serializable {

    /** Primary key. */
    protected static final String PK = "id";

    @Id
    @Column(unique=true, nullable=false, precision=10)
    private String id;
    @Column(name="task_date")
    private Date taskDate;
    @Column(name="task_time")
    private Time taskTime;
    @Column(name="task_type_id", nullable=false, precision=10)
    private int taskTypeId;
    @Column(name="assignee_id", precision=10)
    private int assigneeId;
    @Column(length=3)
    private int status;
    @Column(name="farmer_id", precision=10)
    private String farmerId;
    @Column(name="case_id", precision=10)
    private int caseId;

    /** Default constructor. */
    public TaskAllocation() {
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
     * Access method for taskDate.
     *
     * @return the current value of taskDate
     */
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
     * Access method for taskTime.
     *
     * @return the current value of taskTime
     */
    public Time getTaskTime() {
        return taskTime;
    }

    /**
     * Setter method for taskTime.
     *
     * @param aTaskTime the new value for taskTime
     */
    public void setTaskTime(Time aTaskTime) {
        taskTime = aTaskTime;
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
    public void setTaskTypeId(int aTaskTypeId) {
        taskTypeId = aTaskTypeId;
    }

    /**
     * Access method for assigneeId.
     *
     * @return the current value of assigneeId
     */
    public int getAssigneeId() {
        return assigneeId;
    }

    /**
     * Setter method for assigneeId.
     *
     * @param aAssigneeId the new value for assigneeId
     */
    public void setAssigneeId(int aAssigneeId) {
        assigneeId = aAssigneeId;
    }

    /**
     * Access method for status.
     *
     * @return true if and only if status is currently true
     */
    public int getStatus() {
        return status;
    }

    /**
     * Setter method for status.
     *
     * @param aStatus the new value for status
     */
    public void setStatus(int aStatus) {
        status = aStatus;
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
     * Access method for caseId.
     *
     * @return the current value of caseId
     */
    public int getCaseId() {
        return caseId;
    }

    /**
     * Setter method for caseId.
     *
     * @param aCaseId the new value for caseId
     */
    public void setCaseId(int aCaseId) {
        caseId = aCaseId;
    }

}
