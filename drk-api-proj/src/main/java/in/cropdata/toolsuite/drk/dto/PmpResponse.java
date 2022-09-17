/**
 * 
 */
package in.cropdata.toolsuite.drk.dto;

import java.util.List;

import lombok.Data;

/**
 * @author vivek-cropdata
 *
 */
@Data
public class PmpResponse {

	private boolean success;
	private String error;
	private String message;
	private List<PmpDiscoveryDTO> data;

}
