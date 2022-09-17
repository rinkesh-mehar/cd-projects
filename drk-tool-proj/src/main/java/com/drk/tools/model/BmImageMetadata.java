package com.drk.tools.model;

import lombok.Data;

import java.math.BigInteger;

/**
 * Used for fetching data from DB related to the data read from the excel sheet.
 * 
 * @author PranaySK
 * @since 1.0
 */

@Data
public class BmImageMetadata {
	private Integer id;
//	private BigInteger caseId;
	private Integer commodityId;
	private Integer phenophaseId;
	private Integer plantPartId;
	private Integer stressTypeId;
	private Integer stressId;
//	private Integer stressStageId;
}
