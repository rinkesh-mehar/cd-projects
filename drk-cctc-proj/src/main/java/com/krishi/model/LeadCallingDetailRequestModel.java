package com.krishi.model;

import java.util.ArrayList;
import java.util.List;

import com.krishi.entity.Commodity;
import com.krishi.entity.Language;

public class LeadCallingDetailRequestModel {
	
	private String taskid;
	
	private String fathername;
	
	private String alternatemobileno;
	
	private String referenceperson;
	
	private String referencepersonmobileno;
	
	private Boolean governmentidproof;
	
	private Boolean ownland;
	
	private Boolean irrigatedland;
	
	private Double farmsize;
	
	private List<Commodity> majorcropsgrown;

	private Double croppingarea;
	
	private List<Language> speakinglanguage;
	
	private Integer mobiletype;
	
	private Boolean willingnessoffarmer;
	
	private Boolean vip;
	
	private Integer status;
	
	private String designation;
	
	private String otherDesignation;
	
	private ArrayList<String> scheduledate;
	
	private String comments;
	
	private Integer userid;
	
	/** added for POI Meeting Poing - Pranay : Start */
	private String meetingpoint;

	private String sellerName;

	public String getMeetingpoint() {
		return meetingpoint;
	}

	public void setMeetingpoint(String meetingpoint) {
		this.meetingpoint = meetingpoint;
	}
	/** added for POI Meeting Poing - Pranay : End */

	/** added for major-crop list - Ujwal-CDT : Start*/
	public List<String> cropInfoId;

	public List<String> getCropInfoId() {
		return cropInfoId;
	}

	public void setCropInfoId(List<String> cropInfoId) {
		this.cropInfoId = cropInfoId;
	}

	/** added for major-crop list - Ujwal-CDT : End*/

	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public String getFathername() {
		return fathername;
	}

	public void setFathername(String fathername) {
		this.fathername = fathername;
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

	public List<Commodity> getMajorcropsgrown() {
		return majorcropsgrown;
	}

	public void setMajorcropsgrown(List<Commodity> majorcropsgrown) {
		this.majorcropsgrown = majorcropsgrown;
	}

	public Double getCroppingarea() {
		return croppingarea;
	}

	public void setCroppingarea(Double croppingarea) {
		this.croppingarea = croppingarea;
	}

	public List<Language> getSpeakinglanguage() {
		return speakinglanguage;
	}

	public void setSpeakinglanguage(List<Language> speakinglanguage) {
		this.speakinglanguage = speakinglanguage;
	}

	public Integer getMobiletype() {
		return mobiletype;
	}

	public void setMobiletype(Integer mobiletype) {
		this.mobiletype = mobiletype;
	}

	public Boolean getWillingnessoffarmer() {
		return willingnessoffarmer;
	}

	public void setWillingnessoffarmer(Boolean willingnessoffarmer) {
		this.willingnessoffarmer = willingnessoffarmer;
	}

	public Boolean getVip() {
		return vip;
	}

	public void setVip(Boolean vip) {
		this.vip = vip;
	}

	public ArrayList<String> getScheduledate() {
		return scheduledate;
	}

	public void setScheduledate(ArrayList<String> scheduledate) {
		this.scheduledate = scheduledate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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

	public String getOtherDesignation() {
		return otherDesignation;
	}

	public void setOtherDesignation(String otherDesignation) {
		this.otherDesignation = otherDesignation;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getSellerName()
	{
		return sellerName;
	}

	public void setSellerName(String sellerName)
	{
		this.sellerName = sellerName;
	}
}
