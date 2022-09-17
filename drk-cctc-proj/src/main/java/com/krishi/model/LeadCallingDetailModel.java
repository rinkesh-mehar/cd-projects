package com.krishi.model;

import java.util.List;

import com.krishi.entity.Commodity;
import com.krishi.entity.Language;
import com.krishi.entity.ViewLeadCallingDetailInfo;

public class LeadCallingDetailModel {
	
	public String getCropType() {
		return cropType;
	}

	public void setCropType(String cropType) {
		this.cropType = cropType;
	}


	private String village;
	
	private String tehsil;
	
	private String district;
	
	private String state;
	
	private Boolean vip;
	
	private Integer status;
	
	private String designation;
	
	private String otherDesignation;
	
	private String name;
	
	private String fathername;
	
	private String primarymobilemo;
	
	private String alternatemobileno;
	
	private String referenceperson;
	
	private String referencepersonmobileno;
	
	private Boolean governmentidproof;
	
	private Boolean ownland;
	
	private Boolean irrigatedland;
	
	private Double farmsize;
	
	private List<Commodity> majorcropsgrown;
	
	private Double croppingarea;
	
	private Boolean willingnessoffarmer;
	
	private List<Language> speakinglanguage;
	
	private Integer mobiletype;
	
	private String prscoutname;
	
	private List<String> mlvisit;
	
	private MasterData masterData;
	
	private String cropType;
	
	
	private String farmerId;

	private String relationshipToReferenceName;

	private Integer regionId;

	public LeadCallingDetailModel(ViewLeadCallingDetailInfo info) {
		this.relationshipToReferenceName = info.getRelationshipToReferenceName();
		this.village = info.getVillageName();
		this.tehsil = info.getTehsilName();
		this.district = info.getDistrictName();
		this.state = info.getStateName();
		this.vip = info.getIsVip();
		this.farmerId = info.getFarmerId(); // added farmer id- CDT-Ujwal
		this.name = info.getFarmerName();
		this.fathername = info.getFarmerFatherHusbandName();
		this.primarymobilemo = info.getPrimaryMobNumber();
		this.alternatemobileno = info.getAlternativeMobNumber();
		this.referenceperson = info.getReferencePerson();
		this.referencepersonmobileno = info.getReferencePersonMobileNumber();
		this.governmentidproof = info.getHasGovernmentIdProof();
		this.ownland = info.getHasOwnLand();
		this.irrigatedland = info.getHasIrrigationLand();
		this.farmsize = info.getFarmSize();
		this.croppingarea = info.getCropArea();
		this.willingnessoffarmer = info.getWillingnessForCdt();
		this.designation = info.getVipDesignation();
		this.otherDesignation = info.getOtherDesignation();
		this.mobiletype = info.getMobileTypeId();
		this.status = info.getVipStatus();
		this.sellerType = info.getSellerType();
		this.regionId = info.getRegionId();
		if(info.getFarmerCreatedBy() != 0) {
			StringBuilder builder = new StringBuilder();
			if(info.getPrsFirstName() != null) {
				builder.append(info.getPrsFirstName());
			}
			if(info.getPrsMiddleName() != null) {
				builder.append(" ").append(info.getPrsMiddleName());
			}
			if(info.getPrsLastName() != null) {
				builder.append(" ").append(info.getPrsLastName());
			}
			this.prscoutname = builder.toString();
		} else {
			this.prscoutname = "System";
		}
	}

	public String getFarmerId() {
		return farmerId;
	}

	public void setFarmerId(String farmerId) {
		this.farmerId = farmerId;
	}
	
	
	private String sellerType;

	public String getSellerType() {
		return sellerType;
	}

	public void setSellerType(String sellerType) {
		this.sellerType = sellerType;
	}

	
	public LeadCallingDetailModel() {
		super();
	}
	


	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getTehsil() {
		return tehsil;
	}

	public void setTehsil(String tehsil) {
		this.tehsil = tehsil;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Boolean getVip() {
		return vip;
	}

	public void setVip(Boolean vip) {
		this.vip = vip;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFathername() {
		return fathername;
	}

	public void setFathername(String fathername) {
		this.fathername = fathername;
	}

	public String getPrimarymobilemo() {
		return primarymobilemo;
	}

	public void setPrimarymobilemo(String primarymobilemo) {
		this.primarymobilemo = primarymobilemo;
	}

	public String getAlternatemobileno() {
		return alternatemobileno;
	}

	public void setAlternatemobileno(String alternatemobileno) {
		this.alternatemobileno = alternatemobileno;
	}

	public String getReferenceperson() {
		return referenceperson;
	}

	public void setReferenceperson(String referenceperson) {
		this.referenceperson = referenceperson;
	}

	public String getReferencepersonmobileno() {
		return referencepersonmobileno;
	}

	public void setReferencepersonmobileno(String referencepersonmobileno) {
		this.referencepersonmobileno = referencepersonmobileno;
	}

	public Boolean getGovernmentidproof() {
		return governmentidproof;
	}

	public void setGovernmentidproof(Boolean governmentidproof) {
		this.governmentidproof = governmentidproof;
	}

	public Boolean getOwnland() {
		return ownland;
	}

	public void setOwnland(Boolean ownland) {
		this.ownland = ownland;
	}

	public Boolean getIrrigatedland() {
		return irrigatedland;
	}

	public void setIrrigatedland(Boolean irrigatedland) {
		this.irrigatedland = irrigatedland;
	}

	public Double getFarmsize() {
		return farmsize;
	}

	public void setFarmsize(Double farmsize) {
		this.farmsize = farmsize;
	}

	public Double getCroppingarea() {
		return croppingarea;
	}

	public void setCroppingarea(Double croppingarea) {
		this.croppingarea = croppingarea;
	}

	public Boolean getWillingnessoffarmer() {
		return willingnessoffarmer;
	}

	public void setWillingnessoffarmer(Boolean willingnessoffarmer) {
		this.willingnessoffarmer = willingnessoffarmer;
	}

	public Integer getMobiletype() {
		return mobiletype;
	}

	public void setMobiletype(Integer mobiletype) {
		this.mobiletype = mobiletype;
	}

	public String getPrscoutname() {
		return prscoutname;
	}

	public void setPrscoutname(String prscoutname) {
		this.prscoutname = prscoutname;
	}

	public List<String> getMlvisit() {
		return mlvisit;
	}

	public void setMlvisit(List<String> mlvisit) {
		this.mlvisit = mlvisit;
	}

	public MasterData getMasterData() {
		return masterData;
	}

	public void setMasterData(MasterData masterData) {
		this.masterData = masterData;
	}

	public List<Commodity> getMajorcropsgrown() {
		return majorcropsgrown;
	}

	public void setMajorcropsgrown(List<Commodity> majorcropsgrown) {
		this.majorcropsgrown = majorcropsgrown;
	}

	public List<Language> getSpeakinglanguage() {
		return speakinglanguage;
	}

	public void setSpeakinglanguage(List<Language> speakinglanguage) {
		this.speakinglanguage = speakinglanguage;
	}

	public String getOtherDesignation() {
		return otherDesignation;
	}

	public void setOtherDesignation(String otherDesignation) {
		this.otherDesignation = otherDesignation;
	}

	public String getRelationshipToReferenceName() {
		return relationshipToReferenceName;
	}

	public void setRelationshipToReferenceName(String relationshipToReferenceName) {
		this.relationshipToReferenceName = relationshipToReferenceName;
	}

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}
}
