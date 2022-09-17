/**
 * 
 */
package in.cropdata.toolsuite.drk.dto.ndvi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author admin-pc
 *
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CaseNdvi {
	
//	private Integer id;
	private String caseId;
	private String ndviUrl;
	private Integer week;
	private Integer year;
	/**
	 * @param caseId
	 * @param ndviUrl
	 * @param week
	 * @param year
	 */
	public CaseNdvi(String caseId, String ndviUrl, Integer week, Integer year) {
		super();
		this.caseId = caseId;
		this.ndviUrl = ndviUrl;
		this.week = week;
		this.year = year;
	}
	
	

}
