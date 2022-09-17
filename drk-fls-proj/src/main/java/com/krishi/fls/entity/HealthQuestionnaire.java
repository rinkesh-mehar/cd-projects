// Generated with g9.

package com.krishi.fls.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "health_questionnaire")
public class HealthQuestionnaire implements Serializable {

	/** Primary key. */
	protected static final String PK = "id";

	@Id
	@Column(unique = true, nullable = false, precision = 10)
	private int id;

	@Column(name = "acz_id")
	private Integer aczId;

	@Column(name = "variety_id")
	private Integer varietyId;

	@Column(name = "commodity_id", precision = 10)
	private int commodityId;
	@Column(name = "phenophase_id", precision = 10)
	private int phenophaseId;
	@Column(length = 255)
	private String name;
	@Column(name = "input_type", length = 100)
	private String inputType;
	@Column(name = "input_values")
	private String inputValues;

	public Integer getAczId() {
		return aczId;
	}

	public void setAczId(Integer aczId) {
		this.aczId = aczId;
	}

	public Integer getVarietyId() {
		return varietyId;
	}

	public void setVarietyId(Integer varietyId) {
		this.varietyId = varietyId;
	}

	/** Default constructor. */
	public HealthQuestionnaire() {
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
	 * Access method for commodityId.
	 *
	 * @return the current value of commodityId
	 */
	public int getCommodityId() {
		return commodityId;
	}

	/**
	 * Setter method for commodityId.
	 *
	 * @param aCommodityId the new value for commodityId
	 */
	public void setCommodityId(int aCommodityId) {
		commodityId = aCommodityId;
	}

	/**
	 * Access method for phenophaseId.
	 *
	 * @return the current value of phenophaseId
	 */
	public int getPhenophaseId() {
		return phenophaseId;
	}

	/**
	 * Setter method for phenophaseId.
	 *
	 * @param aPhenophaseId the new value for phenophaseId
	 */
	public void setPhenophaseId(int aPhenophaseId) {
		phenophaseId = aPhenophaseId;
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
	 * Access method for inputType.
	 *
	 * @return the current value of inputType
	 */
	public String getInputType() {
		return inputType;
	}

	/**
	 * Setter method for inputType.
	 *
	 * @param aInputType the new value for inputType
	 */
	public void setInputType(String aInputType) {
		inputType = aInputType;
	}

	/**
	 * Access method for inputValues.
	 *
	 * @return the current value of inputValues
	 */
	public String getInputValues() {
		return inputValues;
	}

	/**
	 * Setter method for inputValues.
	 *
	 * @param aInputValues the new value for inputValues
	 */
	public void setInputValues(String aInputValues) {
		inputValues = aInputValues;
	}

	/**
	 * Compares the key for this instance with another HealthQuestionnaire.
	 *
	 * @param other The object to compare to
	 * @return True if other object is instance of class HealthQuestionnaire and the
	 *         key objects are equal
	 */
	private boolean equalKeys(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof HealthQuestionnaire)) {
			return false;
		}
		HealthQuestionnaire that = (HealthQuestionnaire) other;
		if (this.getId() != that.getId()) {
			return false;
		}
		return true;
	}

	/**
	 * Compares this instance with another HealthQuestionnaire.
	 *
	 * @param other The object to compare to
	 * @return True if the objects are the same
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof HealthQuestionnaire))
			return false;
		return this.equalKeys(other) && ((HealthQuestionnaire) other).equalKeys(this);
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
		StringBuffer sb = new StringBuffer("[HealthQuestionnaire |");
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
