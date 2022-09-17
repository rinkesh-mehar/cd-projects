/**
 * 
 */
package in.cropdata.toolsuite.drk.model.cases;

import java.util.Date;

import lombok.Data;

/**
 * @author Vivek Gajbhiye - Cropdata
 *
 *         11-Jan-2020
 */
@Data
public class CaseDetailsResponse<T> {
	private int caseId;
	private Date correctedSowingDate;
	private int harvestWeek;
	private int subRegionID;
	private int seasonID;
	
	
	/**
	 * @param caseId
	 * @param correctedSowingDate
	 * @param harvestWeek
	 * @param subRegionID
	 * @param seasonID
	 */
	public CaseDetailsResponse(int caseId, Date correctedSowingDate, int harvestWeek, int subRegionID, int seasonID) {
		super();
		this.caseId = caseId;
		this.correctedSowingDate = correctedSowingDate;
		this.harvestWeek = harvestWeek;
		this.subRegionID = subRegionID;
		this.seasonID = seasonID;
	}


	/**
	 * @param caseId
	 */
	public CaseDetailsResponse(int caseId) {
		super();
		this.caseId = caseId;
	}
	
	
}
