package com.drk.tools.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Used for fetching Image data from DB.
 * 
 * @author PranaySK
 */

@Data
@AllArgsConstructor
public class ImageDetails {
	private Integer id;
	private String imageUrl;
}
