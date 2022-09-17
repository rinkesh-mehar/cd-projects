package com.krishi.fls.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.*;

@Entity
@Table(name="task_type")
public class TaskType implements Serializable {

    /** Primary key. */
    protected static final String PK = "id";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique=true, nullable=false, precision=10)
    private int id;
    @Column(nullable=false, length=255)
    private String name;
//    @Column(nullable=false, precision=10)
    @Transient
    private int duration;
    @Column(name="parent_task_type_id", nullable=false, precision=10)
    private int parentTaskTypeId;
    @Column(name="user_roles", nullable=false, length=255)
    private String userRoles;

    /** Default constructor. */
    public TaskType() {
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
     * Access method for duration.
     *
     * @return the current value of duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Setter method for duration.
     *
     * @param aDuration the new value for duration
     */
    public void setDuration(int aDuration) {
        duration = aDuration;
    }

    /**
     * Access method for parentTaskTypeId.
     *
     * @return the current value of parentTaskTypeId
     */
    public int getParentTaskTypeId() {
        return parentTaskTypeId;
    }

    /**
     * Setter method for parentTaskTypeId.
     *
     * @param aParentTaskTypeId the new value for parentTaskTypeId
     */
    public void setParentTaskTypeId(int aParentTaskTypeId) {
        parentTaskTypeId = aParentTaskTypeId;
    }

    /**
     * Access method for userRoles.
     *
     * @return the current value of userRoles
     */
    public String getUserRoles() {
        return userRoles;
    }

    /**
     * Setter method for userRoles.
     *
     * @param aUserRoles the new value for userRoles
     */
    public void setUserRoles(String aUserRoles) {
        userRoles = aUserRoles;
    }

    /**
     * Compares the key for this instance with another TaskType.
     *
     * @param other The object to compare to
     * @return True if other object is instance of class TaskType and the key objects are equal
     */
    private boolean equalKeys(Object other) {
        if (this==other) {
            return true;
        }
        if (!(other instanceof TaskType)) {
            return false;
        }
        TaskType that = (TaskType) other;
        if (this.getId() != that.getId()) {
            return false;
        }
        return true;
    }

    /**
     * Compares this instance with another TaskType.
     *
     * @param other The object to compare to
     * @return True if the objects are the same
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof TaskType)) return false;
        return this.equalKeys(other) && ((TaskType)other).equalKeys(this);
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
        StringBuffer sb = new StringBuffer("[TaskType |");
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
