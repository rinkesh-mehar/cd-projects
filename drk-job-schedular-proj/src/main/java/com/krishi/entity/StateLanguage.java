package com.krishi.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="state_language")
public class StateLanguage implements Serializable {

    /** Primary key. */
    protected static final String PK = "id";

    @Id
    @Column(unique=true, nullable=false, precision=10)
    private int id;
    @Column(name="state_id",precision=10)
    private int code;
    @Column(name="language_id", precision=10)
    private int languageId;
    
    @Column(precision = 10)
	private int status;

    /** Default constructor. */
    public StateLanguage() {
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
     * Access method for code.
     *
     * @return the current value of code
     */
    public int getCode() {
        return code;
    }

    /**
     * Setter method for code.
     *
     * @param aCode the new value for code
     */
    public void setCode(int aCode) {
        code = aCode;
    }

    /**
     * Access method for languageId.
     *
     * @return the current value of languageId
     */
    public int getLanguageId() {
        return languageId;
    }

    /**
     * Setter method for languageId.
     *
     * @param aLanguageId the new value for languageId
     */
    public void setLanguageId(int aLanguageId) {
        languageId = aLanguageId;
    }

    
    public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	/**
     * Compares the key for this instance with another StateLanguage.
     *
     * @param other The object to compare to
     * @return True if other object is instance of class StateLanguage and the key objects are equal
     */
    private boolean equalKeys(Object other) {
        if (this==other) {
            return true;
        }
        if (!(other instanceof StateLanguage)) {
            return false;
        }
        StateLanguage that = (StateLanguage) other;
        if (this.getId() != that.getId()) {
            return false;
        }
        return true;
    }

    /**
     * Compares this instance with another StateLanguage.
     *
     * @param other The object to compare to
     * @return True if the objects are the same
     */
   

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
        StringBuffer sb = new StringBuffer("[StateLanguage |");
        sb.append(" id=").append(getId());
        sb.append("]");
        return sb.toString();
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + code;
		result = prime * result + id;
		result = prime * result + languageId;
		result = prime * result + status;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StateLanguage other = (StateLanguage) obj;
		if (code != other.code)
			return false;
		if (id != other.id)
			return false;
		if (languageId != other.languageId)
			return false;
		if (status != other.status)
			return false;
		return true;
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
