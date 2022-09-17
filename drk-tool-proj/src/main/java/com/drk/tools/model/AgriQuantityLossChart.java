package com.drk.tools.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AgriQuantityLossChart {

	private int id;
	private int commodityId;
//	private int phenophaseId;
	private int stressId;
	private float minBandValue;
	private float maxBandValue;
	private float minQuantityCorrectionPercent;
	private float maxQuantityCorrectionPercent;
	private String status;
	private Date createdAt;
	private Date updatedAt;

	@Override
	public String toString() {
		return id + ", " + commodityId + ", " /*+ phenophaseId + ","*/ + stressId + "," + minBandValue
				+ "," + maxBandValue + "," + minQuantityCorrectionPercent + "," + maxQuantityCorrectionPercent + ","
				+ "'" + status + "'," + createdAt + "," + updatedAt + " ";
	}

}
