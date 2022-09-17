package com.krishi.model;

import java.text.SimpleDateFormat;

import com.krishi.entity.ViewFieldMonitoringDetail;
import com.krishi.vo.ViewFieldMonitoringDetailVO;

public class ScheduleModel {
	
	private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	private String primarynumber;
	private String secondarynumber;
	private String dateschedule;
	
	public ScheduleModel() {
		super();
	}
	
	public ScheduleModel(ViewFieldMonitoringDetailVO detail) {
		this.primarynumber = detail.getFarmerPrimaryMobNumber();
		this.secondarynumber = detail.getFarmerAlternativeMobNumber();
		if(detail.getNextMonitoringDate() != null) {
			this.dateschedule = FORMAT.format(detail.getNextMonitoringDate());
		}
	}
	public String getPrimarynumber() {
		return primarynumber;
	}

	public void setPrimarynumber(String primarynumber) {
		this.primarynumber = primarynumber;
	}

	public String getSecondarynumber() {
		return secondarynumber;
	}

	public void setSecondarynumber(String secondarynumber) {
		this.secondarynumber = secondarynumber;
	}

	public String getDateschedule() {
		return dateschedule;
	}
	public void setDateschedule(String dateschedule) {
		this.dateschedule = dateschedule;
	}

}
