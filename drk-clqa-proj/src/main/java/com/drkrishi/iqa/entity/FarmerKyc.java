package com.drkrishi.iqa.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="farmer_kyc")
public class FarmerKyc{
    
    @Id  
    private String id;
    
    @Column(name="doc_type_id")
    private int docTypeId;
    
    @Column(name="farmer_id")
    private String farmerId;
    
    @Column(name="proxy_name")
    private String proxyName;
    
    @Column(name="proxy_relation_id")
    private int proxyRelationId;
    
    @Column(name="gender")
    private int gender;
    
    @Column(name="dob")
    private Date dob;
    
    @Column(name="permanent_address")
    private String permanentAddress;
    
    @Column(name="doc_number")
    private String docNumber;
    
    @Column(name="doc_photo")
    private String docPhoto;
    
    @Column(name="farmer_photo")
    private String farmerPhoto;
    
    @Column(name="is_verified")
    private Integer isVerified;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getDocTypeId() {
		return docTypeId;
	}
	public void setDocTypeId(int docTypeId) {
		this.docTypeId = docTypeId;
	}
	public String getFarmerId() {
		return farmerId;
	}
	public void setFarmerId(String farmerId) {
		this.farmerId = farmerId;
	}
	public String getProxyName() {
		return proxyName;
	}
	public void setProxyName(String proxyName) {
		this.proxyName = proxyName;
	}
	public int getProxyRelationId() {
		return proxyRelationId;
	}
	public void setProxyRelationId(int proxyRelationId) {
		this.proxyRelationId = proxyRelationId;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getPermanentAddress() {
		return permanentAddress;
	}
	public void setPermanentAddress(String permanentAddress) {
		this.permanentAddress = permanentAddress;
	}
	public String getDocNumber() {
		return docNumber;
	}
	public void setDocNumber(String docNumber) {
		this.docNumber = docNumber;
	}
	public String getDocPhoto() {
		return docPhoto;
	}
	public void setDocPhoto(String docPhoto) {
		this.docPhoto = docPhoto;
	}
	public String getFarmerPhoto() {
		return farmerPhoto;
	}
	public void setFarmerPhoto(String farmerPhoto) {
		this.farmerPhoto = farmerPhoto;
	}
	public Integer getIsVerified() {
		return isVerified;
	}
	public void setIsVerified(Integer isVerified) {
		this.isVerified = isVerified;
	}
	
}
