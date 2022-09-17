package com.drk.tools.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FlipbookSymptom {
	private int Id;
	private int commodityId;
//	private int phenophaseId;
	private int plantPartId;
	private int stressTypeId;
	private int stressId;
//	private int stressStageId;
	private String symptom;
	private String GenericImage;
	private String StressName;

	@Override
	public String toString() {
		return Id + "," + commodityId + "," /*+ phenophaseId + ","*/ + plantPartId + "," + stressTypeId + "," + stressId
				/*+ ", " + stressStageId*/ + ",'" + symptom + "','" + GenericImage + "','" + StressName + "'";
	}

}
