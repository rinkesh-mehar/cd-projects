/**
 * 
 */
package in.cropdata.toolsuite.drk.dao.cases;

import java.math.BigInteger;
import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.cropdata.toolsuite.drk.dto.cases.CaseDetailsDTO;
import in.cropdata.toolsuite.drk.model.cases.CaseDetails;

/**
 * @author Vivek Gajbhiye - Cropdata
 *
 *         23-Nov-2019
 */
@Repository
public interface KMLFileDao extends JpaRepository<CaseDetails, Integer> {

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "update gstm_transitory.case_details set case_details.CorrectedSowingDate=?1,case_details.HarvestWeek=?2,case_details.YieldPercent=?3,case_details.CurrentQuantity=?4  where case_details.CaseID=?5", nativeQuery = true)
	public int updateCaseDetails(Date correctedSowingDate, int harvestWeek, double yield, double currentQuantity,
			int caseId);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "update gstm_transitory.case_details set case_details.Status='Inactive',case_details.Reason=?1 where case_details.CaseID=?2", nativeQuery = true)
	public void updateStatusAndReason(String reason, BigInteger caseId);

	@Query(value = "select cd.CommodityID,cd.VarietyID,cd.StateCode from gstm_transitory.case_details cd where cd.CaseID=?1", nativeQuery = true)
	public CaseDetailsDTO getByCaseId(int caseId);

	@Query(value = "select sqc.StandardQuantityPerAcre from cdt_master_data.agri_standard_quantity_chart sqc where sqc.CommodityID=?1 and sqc.VarietyID=?2 and sqc.StateCode=?3", nativeQuery = true)
	public Double getStanderdQua(int commodityId, int varietyId, int stateCode);

	public Optional<CaseDetails> findByCaseId(BigInteger caseId);

}
