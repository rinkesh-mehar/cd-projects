package com.drk.tools.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AgriStandardQuantityChart {

	private int id;
	private int aczId;
	private int sowingWeekStart;
	private int sowingWeekEnd;
	private int stateCode;
	private int commodityId;
	private int varietyId;
	private float standardQuantityPerAcre;
	private float StandardPositiveVariancePerAcre;
	private float standardPositiveVariancePercent;
	private float standardNegativeVariancePerAcre;
	private float standardNegativeVariancePercent;
	private String status;
	private Date createdAt;
	private Date updatedAt;

	@Override
	public String toString() {
		return id + ", " + aczId + "," + sowingWeekStart + "," + sowingWeekEnd + "," + stateCode + "," + commodityId + "," + varietyId + ", " + standardQuantityPerAcre + ","
				+ StandardPositiveVariancePerAcre + "," + standardPositiveVariancePercent + ", "
				+ standardNegativeVariancePerAcre + ", " + "" + standardNegativeVariancePercent + ",'" + status + "',"
				+ createdAt + "," + updatedAt + " ";
	}

}