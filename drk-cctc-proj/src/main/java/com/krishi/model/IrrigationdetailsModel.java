package com.krishi.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.krishi.entity.IrrigationMethod;
import com.krishi.entity.IrrigationSource;
import com.krishi.entity.ViewFieldMonitoringDetail;
import com.krishi.vo.ViewFieldMonitoringDetailVO;

public class IrrigationdetailsModel {
	
	private List<IrrigationSource> irrigationsource;
	
	private List<IrrigationMethod> irrigationmethod;
	
	private Integer numberofirrigations;
	
	private String weekofittigation;

	public IrrigationdetailsModel() {
		super();
	}
	

	public IrrigationdetailsModel(ViewFieldMonitoringDetailVO detail) {
		this.numberofirrigations = detail.getNumberOfIrrigation();
		//this.weekofittigation = detail.getWeekOfIrrigation();
		if(detail.getWeekOfIrrigation() != null && detail.getYearOfIrrigation() != null) {
			Calendar c = Calendar.getInstance();
		    c.set(Calendar.YEAR, detail.getYearOfIrrigation());
		    c.set(Calendar.WEEK_OF_YEAR, detail.getWeekOfIrrigation()+1);
		    int i = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
		    c.add(Calendar.DATE, -i - 7);
		    c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		    Date start = c.getTime();
		    c.add(Calendar.DATE, 6);
		    Date end = c.getTime();
		    SimpleDateFormat format = new SimpleDateFormat("dd MMM");
		    this.weekofittigation = detail.getWeekOfIrrigation()+" ("+format.format(start) + " - " + format.format(end)+")";
		}
	}


	public List<IrrigationSource> getIrrigationsource() {
		return irrigationsource;
	}

	public void setIrrigationsource(List<IrrigationSource> irrigationsource) {
		this.irrigationsource = irrigationsource;
	}

	public List<IrrigationMethod> getIrrigationmethod() {
		return irrigationmethod;
	}

	public void setIrrigationmethod(List<IrrigationMethod> irrigationmethod) {
		this.irrigationmethod = irrigationmethod;
	}

	public Integer getNumberofirrigations() {
		return numberofirrigations;
	}

	public void setNumberofirrigations(Integer numberofirrigations) {
		this.numberofirrigations = numberofirrigations;
	}


	public String getWeekofittigation() {
		return weekofittigation;
	}


	public void setWeekofittigation(String weekofittigation) {
		this.weekofittigation = weekofittigation;
	}
	
	

}
