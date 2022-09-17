/**
 * 
 */
package in.cropdata.toolsuite.drk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author vivek-cropdata
 *
 */
@Data
@AllArgsConstructor
public class PmpDiscoveryDTO {
	
	private Integer regionId;
	private Integer commodityId;
	private Integer varietyId;
	private Integer bandId;
	private String band;
	private String pmp;
	private String status;

}
