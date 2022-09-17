package com.krishi.model;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.krishi.entity.ViewFieldMonitoringDetail;
import com.krishi.vo.ViewFieldMonitoringDetailVO;

public class FertilizerModel {
	
	private String typeofapplication;
	
	private String name;
	
	private String uom;
	
	private Integer dose;
	
	private List<String> splitdose;
	
	private String weekofapplication;
	
	

	public FertilizerModel() {
		super();
	}
	
	public FertilizerModel(ViewFieldMonitoringDetailVO detail) {
		this.typeofapplication = detail.getFertilizerTypeOfApplication();
		this.name = detail.getFertilizerName();
		this.uom = detail.getFertilizerUnitName();
		this.dose = detail.getFertilizerDose();
		if(detail.getFertilizerSplitDose() != null) {
			this.splitdose = Arrays.asList(detail.getFertilizerSplitDose().split(","));
		}
		//this.weekofapplication = detail.getFertilizerWeekOfApplcation();
		if(detail.getFertilizerWeekOfApplcation() != null && detail.getFertilizerYearOfApplcation() != null) {
			Calendar c = Calendar.getInstance();
		    c.set(Calendar.YEAR, detail.getFertilizerYearOfApplcation());
		    c.set(Calendar.WEEK_OF_YEAR, detail.getFertilizerWeekOfApplcation()+1);
		    int i = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
		    c.add(Calendar.DATE, -i - 7);
		    c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		    Date start = c.getTime();
		    c.add(Calendar.DATE, 6);
		    Date end = c.getTime();
		    SimpleDateFormat format = new SimpleDateFormat("dd MMM");
		    this.weekofapplication = detail.getFertilizerWeekOfApplcation()+" ("+format.format(start) + " - " + format.format(end)+")";
		}
	}



	public String getTypeofapplication() {
		return typeofapplication;
	}

	public void setTypeofapplication(String typeofapplication) {
		this.typeofapplication = typeofapplication;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	

	public List<String> getSplitdose() {
		return splitdose;
	}

	public void setSplitdose(List<String> splitdose) {
		this.splitdose = splitdose;
	}

	public String getWeekofapplication() {
		return weekofapplication;
	}

	public void setWeekofapplication(String weekofapplication) {
		this.weekofapplication = weekofapplication;
	}

	
	
}
