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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "farmer_land_info")
public class FarmerLandInfo implements Serializable {

	/** Primary key. */
	protected static final String PK = "id";

	@Id
	@Column(name="id")
	private String id;
	@Column(name = "own_land", length = 12)
	private float ownLand;
	@Column(name = "leased_out_land", length = 12)
	private float leasedOutLand;
	@Column(name = "leased_in_land", length = 12)
	private float leasedInLand;
	@Column(name = "irrigated_land", length = 12)
	private float irrigatedLand;
	@Column(name = "farmer_id", length = 12)
	private String farmerId;

	/** Default constructor. */
	public FarmerLandInfo() {
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
	 * Access method for ownLand.
	 *
	 * @return the current value of ownLand
	 */
	public float getOwnLand() {
		return ownLand;
	}

	/**
	 * Setter method for ownLand.
	 *
	 * @param aOwnLand the new value for ownLand
	 */
	public void setOwnLand(float aOwnLand) {
		ownLand = aOwnLand;
	}

	/**
	 * Access method for leasedOutLand.
	 *
	 * @return the current value of leasedOutLand
	 */
	public float getLeasedOutLand() {
		return leasedOutLand;
	}

	/**
	 * Setter method for leasedOutLand.
	 *
	 * @param aLeasedOutLand the new value for leasedOutLand
	 */
	public void setLeasedOutLand(float aLeasedOutLand) {
		leasedOutLand = aLeasedOutLand;
	}

	/**
	 * Access method for leasedInLand.
	 *
	 * @return the current value of leasedInLand
	 */
	public float getLeasedInLand() {
		return leasedInLand;
	}

	/**
	 * Setter method for leasedInLand.
	 *
	 * @param aLeasedInLand the new value for leasedInLand
	 */
	public void setLeasedInLand(float aLeasedInLand) {
		leasedInLand = aLeasedInLand;
	}

	/**
	 * Access method for irrigatedLand.
	 *
	 * @return the current value of irrigatedLand
	 */
	public float getIrrigatedLand() {
		return irrigatedLand;
	}

	/**
	 * Setter method for irrigatedLand.
	 *
	 * @param aIrrigatedLand the new value for irrigatedLand
	 */
	public void setIrrigatedLand(float aIrrigatedLand) {
		irrigatedLand = aIrrigatedLand;
	}

	public String getFarmerId() {
		return farmerId;
	}

	public void setFarmerId(String farmerId) {
		this.farmerId = farmerId;
	}

	

}
