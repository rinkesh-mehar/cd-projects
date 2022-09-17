/**
 * 
 */
package in.cropdata.toolsuite.drk.dto;

import java.util.Map;

import lombok.Data;

/**
 * @author vivek-cropdata
 *
 */
@Data
public class QualitySpreadDTO {
	
	private boolean success;
	private String error;
	private String message;
	private Map<String, Object> data;

}
