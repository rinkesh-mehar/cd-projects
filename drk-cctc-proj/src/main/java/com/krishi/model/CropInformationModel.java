package com.krishi.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.krishi.entity.ViewFieldMonitoringDetail;
import com.krishi.vo.ViewFieldMonitoringDetailVO;

public class CropInformationModel {

	private String crop;
	
	private String variety;
	
	private Double croppingarea;

//	private String season;

	private String sowingweek;
	
	private String seedsource;
	
	private Boolean seedsamplereceived;
	
	private Double seedrate;
	
	private String uom;
	
	private Double spacingrow;
	
	private Double spacingplant;
	
	

	public CropInformationModel() {
		super();
	}


	public CropInformationModel(ViewFieldMonitoringDetailVO detail) {
		this.crop = detail.getCropName();
		this.variety = detail.getVarietyName();
		this.croppingarea = detail.getCropArea();
//		this.season = detail.getSeasonName();
		//this.sowingweek = detail.getSowingWeek();
		this.seedsource = detail.getSeedName();
		this.seedsamplereceived = detail.getSeedsSampleReceived();
		this.seedrate = detail.getSeedsRates();
		this.uom = detail.getSeedUnitName();
		this.spacingrow = detail.getSpacingRow();
		this.spacingplant = detail.getSpacingPlant();
		if(detail.getSowingWeek() != null && detail.getSowingYear() != null) {
			Calendar c = Calendar.getInstance();
			c.set(Calendar.YEAR, detail.getSowingYear());
			c.set(Calendar.WEEK_OF_YEAR, detail.getSowingWeek()+1);
			int i = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
			c.add(Calendar.DATE, -i - 7);
			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			Date start = c.getTime();
			c.add(Calendar.DATE, 6);
			Date end = c.getTime();
			SimpleDateFormat format = new SimpleDateFormat("dd MMM");
			this.sowingweek = detail.getSowingWeek()+" ("+format.format(start) + " - " + format.format(end)+")";
		}
	}

	public String getCrop() {
		return crop;
	}

	public void setCrop(String crop) {
		this.crop = crop;
	}

	public String getVariety() {
		return variety;
	}

	public void setVariety(String variety) {
		this.variety = variety;
	}

	public Double getCroppingarea() {
		return croppingarea;
	}

	public void setCroppingarea(Double croppingarea) {
		this.croppingarea = croppingarea;
	}

//	public String getSeason() {
//		return season;
//	}
//
//	public void setSeason(String season) {
//		this.season = season;
//	}

	public String getSowingweek() {
		return sowingweek;
	}


	public void setSowingweek(String sowingweek) {
		this.sowingweek = sowingweek;
	}


	public String getSeedsource() {
		return seedsource;
	}

	public void setSeedsource(String seedsource) {
		this.seedsource = seedsource;
	}

	public Boolean getSeedsamplereceived() {
		return seedsamplereceived;
	}

	public void setSeedsamplereceived(Boolean seedsamplereceived) {
		this.seedsamplereceived = seedsamplereceived;
	}

	public Double getSeedrate() {
		return seedrate;
	}

	public void setSeedrate(Double seedrate) {
		this.seedrate = seedrate;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public Double getSpacingrow() {
		return spacingrow;
	}

	public void setSpacingrow(Double spacingrow) {
		this.spacingrow = spacingrow;
	}

	public Double getSpacingplant() {
		return spacingplant;
	}

	public void setSpacingplant(Double spacingplant) {
		this.spacingplant = spacingplant;
	}


	
	
}
