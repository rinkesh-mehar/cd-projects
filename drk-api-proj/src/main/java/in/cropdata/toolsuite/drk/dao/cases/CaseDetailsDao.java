/**
 * 
 */
package in.cropdata.toolsuite.drk.dao.cases;

import java.util.List;

import in.cropdata.toolsuite.drk.model.cases.CaseDetails;

/**
 * @author Vivek Gajbhiye - Cropdata
 *
 *         19-Mar-2020
 */
public interface CaseDetailsDao {

	public int[] batchUpdate(List<CaseDetails> caseDetails, int batchSize);

}
