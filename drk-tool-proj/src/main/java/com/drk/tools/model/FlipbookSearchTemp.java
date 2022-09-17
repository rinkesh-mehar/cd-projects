package com.drk.tools.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FlipbookSearchTemp {
	private int ID;
	private int stateCode;
	private int regionId;
	private int commodityId;
//	private int phenophaseId;
	private int stresstypeid;
//	private int stressStageId;
	private int plantpartid;
	private int symptomid;

	@Override
	public String toString() {
		return ID + "," + stateCode + "," + regionId + "," + commodityId + "," /*+ phenophaseId + ","*/ + stresstypeid + ","
				+ /*stressStageId + "," + */ plantpartid + "," + symptomid;
	}
}
