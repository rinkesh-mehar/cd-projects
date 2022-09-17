package com.drk.tools.model;

import lombok.Data;

/**
 * Used for capturing the response from add benchmark image API.
 * 
 * @author PranaySK
 * @since 1.0
 */

@Data
public class PostBmImageResponse {
	/** Response status */
	private boolean success;

	/** Response message */
	private String message;
}
