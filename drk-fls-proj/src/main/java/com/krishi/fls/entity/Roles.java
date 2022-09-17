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

@Entity
@Table(name="roles")
public class Roles implements Serializable {

    /** Primary key. */
    protected static final String PK = "id";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique=true, nullable=false, precision=10)
    private int id;
    @Column(name="ecosystem_id", nullable=false, precision=10)
    private int ecosystemId;
    @Column(nullable=false, length=50)
    private String code;
    @Column(nullable=false, length=255)
    private String name;
    @Column(name="is_manager", nullable=false, length=3)
    private boolean isManager;
    @Column(name="reporting_to", precision=10)
    private int reportingTo;

    /** Default constructor. */
    public Roles() {
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
     * Access method for ecosystemId.
     *
     * @return the current value of ecosystemId
     */
    public int getEcosystemId() {
        return ecosystemId;
    }

    /**
     * Setter method for ecosystemId.
     *
     * @param aEcosystemId the new value for ecosystemId
     */
    public void setEcosystemId(int aEcosystemId) {
        ecosystemId = aEcosystemId;
    }

    /**
     * Access method for code.
     *
     * @return the current value of code
     */
    public String getCode() {
        return code;
    }

    /**
     * Setter method for code.
     *
     * @param aCode the new value for code
     */
    public void setCode(String aCode) {
        code = aCode;
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
     * Access method for isManager.
     *
     * @return true if and only if isManager is currently true
     */
    public boolean getIsManager() {
        return isManager;
    }

    /**
     * Setter method for isManager.
     *
     * @param aIsManager the new value for isManager
     */
    public void setIsManager(boolean aIsManager) {
        isManager = aIsManager;
    }

    /**
     * Access method for reportingTo.
     *
     * @return the current value of reportingTo
     */
    public int getReportingTo() {
        return reportingTo;
    }

    /**
     * Setter method for reportingTo.
     *
     * @param aReportingTo the new value for reportingTo
     */
    public void setReportingTo(int aReportingTo) {
        reportingTo = aReportingTo;
    }

    /**
     * Compares the key for this instance with another Roles.
     *
     * @param other The object to compare to
     * @return True if other object is instance of class Roles and the key objects are equal
     */
    private boolean equalKeys(Object other) {
        if (this==other) {
            return true;
        }
        if (!(other instanceof Roles)) {
            return false;
        }
        Roles that = (Roles) other;
        if (this.getId() != that.getId()) {
            return false;
        }
        return true;
    }

    /**
     * Compares this instance with another Roles.
     *
     * @param other The object to compare to
     * @return True if the objects are the same
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Roles)) return false;
        return this.equalKeys(other) && ((Roles)other).equalKeys(this);
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
        StringBuffer sb = new StringBuffer("[Roles |");
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
