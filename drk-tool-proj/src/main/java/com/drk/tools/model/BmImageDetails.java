package com.drk.tools.model;

import lombok.Data;
import org.springframework.core.io.Resource;

import java.util.Map;

/**
 * Used for reading the data from the excel sheet.
 * 
 * @author PranaySK
 * @since 1.0
 */

@Data
public class BmImageDetails {
	private String commodity;
//	private String phenophase;
	private String plantPart;
	private String stressType;
	private String stress;
//	private String stressStage;
	private Map<String, Resource> images;
}
