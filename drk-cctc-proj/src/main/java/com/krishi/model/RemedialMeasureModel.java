package com.krishi.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.krishi.entity.ViewFieldMonitoringDetail;
import com.krishi.vo.ViewFieldMonitoringDetailVO;

public class RemedialMeasureModel {
	
	private String agrochemicalname;
	private String brand;
	private String uom;
	private Integer dose;
	private String weekofapplication;
	
	public RemedialMeasureModel() {
		super();
	}
	
	
	
	public RemedialMeasureModel(ViewFieldMonitoringDetailVO detail) {
		this.brand = detail.getAgrochemicalBrandName();
		this.uom = detail.getAgrochemicalUnitName();
		this.dose = detail.getAgrochemicalDose();
		//this.weekofapplication = detail.getAgrochemicalWeekOfApplcation();
		this.agrochemicalname = detail.getAgrochemicalName();
		if(detail.getAgrochemicalWeekOfApplcation() != null && detail.getAgrochemicalYearOfApplcation() != null) {
			Calendar c = Calendar.getInstance();
		    c.set(Calendar.YEAR, detail.getAgrochemicalYearOfApplcation());
		    c.set(Calendar.WEEK_OF_YEAR, detail.getAgrochemicalWeekOfApplcation()+1);
		    int i = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
		    c.add(Calendar.DATE, -i - 7);
		    c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		    Date start = c.getTime();
		    c.add(Calendar.DATE, 6);
		    Date end = c.getTime();
		    SimpleDateFormat format = new SimpleDateFormat("dd MMM");
		    this.weekofapplication = detail.getAgrochemicalWeekOfApplcation()+" ("+format.format(start) + " - " + format.format(end)+")";
		}
	}

	public String getAgrochemicalname() {
		return agrochemicalname;
	}

	public void setAgrochemicalname(String agrochemicalname) {
		this.agrochemicalname = agrochemicalname;
	}

	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public Integer getDose() {
		return dose;
	}
	public void setDose(Integer dose) {
		this.dose = dose;
	}

	public String getWeekofapplication() {
		return weekofapplication;
	}

	public void setWeekofapplication(String weekofapplication) {
		this.weekofapplication = weekofapplication;
	}
	
	
}
