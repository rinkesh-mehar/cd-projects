package com.krishi.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="language")
public class Language implements Serializable {

    /** Primary key. */
    protected static final String PK = "id";

    @Id
    @Column(unique=true, nullable=false, precision=10)
    private int id;
    @Column(name="name",length=255)
    private String language;
    
    @Column(precision=10)
    private Integer status;

    /** Default constructor. */
    public Language() {
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
     * Access method for language.
     *
     * @return the current value of language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Setter method for language.
     *
     * @param aLanguage the new value for language
     */
    public void setLanguage(String aLanguage) {
        language = aLanguage;
    }
    
    
    
    

   

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
     * Compares the key for this instance with another Language.
     *
     * @param other The object to compare to
     * @return True if other object is instance of class Language and the key objects are equal
     */
    private boolean equalKeys(Object other) {
        if (this==other) {
            return true;
        }
        if (!(other instanceof Language)) {
            return false;
        }
        Language that = (Language) other;
        if (this.getId() != that.getId()) {
            return false;
        }
        return true;
    }

    /**
     * Compares this instance with another Language.
     *
     * @param other The object to compare to
     * @repackage com.krishi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StateSeasonModel {

	@JsonProperty
	private Integer ID;
	@JsonProperty
	private Integer StateCode;
	@JsonProperty
	private Integer SeasonID;
	@JsonProperty
	private Short StartWeek;
	@JsonProperty
	private Short EndWeek;
	@JsonProperty
	private String Status;

	public StateSeasonModel() {
		super();
	}

	public StateSeasonModel(Integer iD, Integer stateCode, Integer seasonID, Short startWeek, Short endWeek,
			String status) {
		super();
		ID = iD;
		StateCode = stateCode;
		SeasonID = seasonID;
		StartWeek = startWeek;
		EndWeek = endWeek;
		Status = status;
	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public Integer getStateCode() {
		return StateCode;
	}

	public void setStateCode(Integer stateCode) {
		StateCode = stateCode;
	}

	public Integer getSeasonID() {
		return SeasonID;
	}

	public void setSeasonID(Integer seasonID) {
		SeasonID = seasonID;
	}

	public Short getStartWeek() {
		return StartWeek;
	}

	public void setStartWeek(Short startWeek) {
		StartWeek = startWeek;
	}

	public Short getEndWeek() {
		return EndWeek;
	}

	public void setEndWeek(Short endWeek) {
		EndWeek = endWeek;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

}
turn True if the objects are the same
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
        StringBuffer sb = new StringBuffer("[Language |");
        sb.append(" id=").append(getId());
        sb.append("]");
        return sb.toString();
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((language == null) ? 0 : language.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		Language other = (Language) obj;
		if (id != other.id)
			return false;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
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
