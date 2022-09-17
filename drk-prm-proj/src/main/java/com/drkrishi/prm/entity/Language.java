package com.drkrishi.prm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

@Entity
@Table(name="language")
public class Language implements Serializable {

    /** Primary key. */
    protected static final String PK = "id";

    @Id
    @Column(unique=true, nullable=false, precision=10)
    private int id;
    @Column(length=255)
    private String language;

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
     * @return True if the objects are the same
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Language)) return false;
        return this.equalKeys(other) && ((Language)other).equalKeys(this);
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

    

    @Override
	public String toString() {
		return "Language [id=" + id + ", language=" + language + "]";
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
