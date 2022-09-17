package com.krishi.model;

import java.util.List;

import com.krishi.entity.*;
//import com.krishi.entity.Season;


public class MasterData {
	
	private List<Commodity> majorcropsgrownlist;
	
	private List<Language> speakinglanguagelist;
	
	private List<MobileType> mobiletypelist;
	
	private List<VipStatus> statuslist;
	
	private List<VipDesignation> designationlist;

	private List<ProxyRelationType> proxyRelationType;
	/**
	 * Added calling list in master -CDT-Ujwal
	 * */
	private List<CallingStatus> leadStatus;
	
	/** Added for POI : Start */
	private List<PoiDataModel> meetingpointlist;

	public List<PoiDataModel> getMeetingpointlist() {
		return meetingpointlist;
	}

	public void setMeetingpointlist(List<PoiDataModel> meetingpointlist) {
		this.meetingpointlist = meetingpointlist;
	}
	/** Added for POI : End */

	/** Added for farmer major crop: CDT-Ujwal- Start */
	private List<ViewFarmerCropInfo> farmerCropInfoList;

	public List<ProxyRelationType> getProxyRelationType() {
		return proxyRelationType;
	}

	public void setProxyRelationType(List<ProxyRelationType> proxyRelationType) {
		this.proxyRelationType = proxyRelationType;
	}

	//	public List<Season> seasonList;
	
//	public List<Season> getSeasonList() {
//		return seasonList;
//	}
//
//	public void setSeasonList(List<Season> seasonList) {
//		this.seasonList = seasonList;
//	}

	public List<ViewFarmerCropInfo> getFarmerCropInfoList() {
		return farmerCropInfoList;
	}

	public void setFarmerCropInfoList(List<ViewFarmerCropInfo> farmerCropInfoList) {
		this.farmerCropInfoList = farmerCropInfoList;
	}
	/** Added for farmer major crop: CDT-Ujwal- Start */
	
	public List<Commodity> getMajorcropsgrownlist() {
		return majorcropsgrownlist;
	}

	public void setMajorcropsgrownlist(List<Commodity> majorcropsgrownlist) {
		this.majorcropsgrownlist = majorcropsgrownlist;
	}

	public List<Language> getSpeakinglanguagelist() {
		return speakinglanguagelist;
	}

	public void setSpeakinglanguagelist(List<Language> speakinglanguagelist) {
		this.speakinglanguagelist = speakinglanguagelist;
	}

	public List<MobileType> getMobiletypelist() {
		return mobiletypelist;
	}

	public void setMobiletypelist(List<MobileType> mobiletypelist) {
		this.mobiletypelist = mobiletypelist;
	}

	public List<VipStatus> getStatuslist() {
		return statuslist;
	}

	public void setStatuslist(List<VipStatus> statuslist) {
		this.statuslist = statuslist;
	}

	public List<VipDesignation> getDesignationlist() {
		return designationlist;
	}

	public void setDesignationlist(List<VipDesignation> designationlist) {
		this.designationlist = designationlist;
	}

	public List<CallingStatus> getLeadStatus() {
		return leadStatus;
	}

	public void setLeadStatus(List<CallingStatus> leadStatus) {
		this.leadStatus = leadStatus;
	}
}
