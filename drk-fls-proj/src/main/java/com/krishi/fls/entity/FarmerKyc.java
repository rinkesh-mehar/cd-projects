package com.krishi.fls.entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "farmer_kyc")
public class FarmerKyc implements Serializable {

	/** Primary key. */
	protected static final String PK = "id";

	private static final SimpleDateFormat FORMAT = new SimpleDateFormat("dd-MM-yyyy");

	@Id
	private String id;
	@Column(name = "doc_type_id", nullable = false, precision = 10)
	private int docTypeId;
	@Column(name = "farmer_id", precision = 10)
	private String farmerId;
	@Column(name = "proxy_name", length = 50)
	private String proxyName;
	@Column(name = "proxy_relation_id", precision = 10)
	private int proxyRelationId;
	@Column(precision = 10)
	private int gender;
	private Date dob;
	@Column(name = "permanent_address", length = 255)
	private String permanentAddress;
	@Column(name = "doc_number", length = 20)
	private String docNumber;
	@Column(name = "doc_photo", length = 255)
	private String docPhoto;
	@Column(name = "farmer_photo", length = 50)
	private String farmerPhoto;

	/** Default constructor. */
	public FarmerKyc() {
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
	 * Access method for docTypeId.
	 *
	 * @return the current value of docTypeId
	 */
	public int getDocTypeId() {
		return docTypeId;
	}

	/**
	 * Setter method for docTypeId.
	 *
	 * @param aDocTypeId the new value for docTypeId
	 */
	public void setDocTypeId(int aDocTypeId) {
		docTypeId = aDocTypeId;
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
	 * Access method for proxyName.
	 *
	 * @return the current value of proxyName
	 */
	public String getProxyName() {
		return proxyName;
	}

	/**
	 * Setter method for proxyName.
	 *
	 * @param aProxyName the new value for proxyName
	 */
	public void setProxyName(String aProxyName) {
		proxyName = aProxyName;
	}

	/**
	 * Access method for proxyRelationId.
	 *
	 * @return the current value of proxyRelationId
	 */
	public int getProxyRelationId() {
		return proxyRelationId;
	}

	/**
	 * Setter method for proxyRelationId.
	 *
	 * @param aProxyRelationId the new value for proxyRelationId
	 */
	public void setProxyRelationId(int aProxyRelationId) {
		proxyRelationId = aProxyRelationId;
	}

	/**
	 * Access method for gender.
	 *
	 * @return the current value of gender
	 */
	public int getGender() {
		return gender;
	}

	/**
	 * Setter method for gender.
	 *
	 * @param aGender the new value for gender
	 */
	public void setGender(int aGender) {
		gender = aGender;
	}

	/**
	 * Access method for dob.
	 *
	 * @return the current value of dob
	 */
	public Date getDob() {
		return dob;
	}

	/**
	 * Setter method for dob.
	 *
	 * @param aDob the new value for dob
	 */
	public void setDob(Date aDob) {
		dob = aDob;
	}

	/**
	 * Access method for permanentAddress.
	 *
	 * @return the current value of permanentAddress
	 */
	public String getPermanentAddress() {
		return permanentAddress;
	}

	/**
	 * Setter method for permanentAddress.
	 *
	 * @param aPermanentAddress the new value for permanentAddress
	 */
	public void setPermanentAddress(String aPermanentAddress) {
		permanentAddress = aPermanentAddress;
	}

	/**
	 * Access method for docNumber.
	 *
	 * @return the current value of docNumber
	 */
	public String getDocNumber() {
		return docNumber;
	}

	/**
	 * Setter method for docNumber.
	 *
	 * @param aDocNumber the new value for docNumber
	 */
	public void setDocNumber(String aDocNumber) {
		docNumber = aDocNumber;
	}

	/**
	 * Access method for docPhoto.
	 *
	 * @return the current value of docPhoto
	 */
	public String getDocPhoto() {
		return docPhoto;
	}

	/**
	 * Setter method for docPhoto.
	 *
	 * @param aDocPhoto the new value for docPhoto
	 */
	public void setDocPhoto(String aDocPhoto) {
		docPhoto = aDocPhoto;
	}

	/**
	 * Access method for farmerPhoto.
	 *
	 * @return the current value of farmerPhoto
	 */
	public String getFarmerPhoto() {
		return farmerPhoto;
	}

	/**
	 * Setter method for farmerPhoto.
	 *
	 * @param aFarmerPhoto the new value for farmerPhoto
	 */
	public void setFarmerPhoto(String aFarmerPhoto) {
		farmerPhoto = aFarmerPhoto;
	}

	/**
	 * Compares the key for this instance with another FarmerKyc.
	 *
	 * @param other The object to compare to
	 * @return True if other object is instance of class FarmerKyc and the key
	 *         objects are equal
	 */
	private boolean equalKeys(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FarmerKyc)) {
			return false;
		}
		FarmerKyc that = (FarmerKyc) other;
		if (this.getId() != that.getId()) {
			return false;
		}
		return true;
	}

	public void setDobDate(String date) {
		try {
			this.dob = new Date(FORMAT.parse(date).getTime());
		} catch (ParseException e) {
			this.dob = null;
		}
	}
	// dobDate

}
