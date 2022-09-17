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
@Table(name="stress_type")
public class StressType implements Serializable {

    /** Primary key. */
    protected static final String PK = "id";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique=true, nullable=false, precision=10)
    private int id;
    @Column(nullable=false, length=255)
    private String name;
    @Column(name="is_important", nullable=false, length=3)
    private boolean isImportant;

    /** Default constructor. */
    public StressType() {
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
     * Compares the key for this instance with another StressType.
     *
     * @param other The object to compare to
     * @return True if other object is instance of class StressType and the key objects are equal
     */
    private boolean equalKeys(Object other) {
        if (this==other) {
            return true;
        }
        if (!(other instanceof StressType)) {
            return false;
        }
        StressType that = (StressType) other;
        if (this.getId() != that.getId()) {
            return false;
        }
        return true;
    }

    /**
     * Compares this instance with another StressType.
     *
     * @param other The object to compare to
     * @return True if the objects are the same
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof StressType)) return false;
        return this.equalKeys(other) && ((StressType)other).equalKeys(this);
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
        StringBuffer sb = new StringBuffer("[StressType |");
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
