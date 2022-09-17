/**
 * 
 */
package com.drk.tools.dto;

import java.util.List;

import lombok.Data;

/**
 * @author vivek-cropdata
 *
 */
@Data
public class PyResponseDTO {
	
	private String errorCode;
	private String message;
	private boolean status;
	private List<SpotPriorityDTO> data;

}
