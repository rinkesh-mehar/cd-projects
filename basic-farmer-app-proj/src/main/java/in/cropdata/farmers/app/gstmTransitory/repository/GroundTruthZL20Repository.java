package in.cropdata.farmers.app.gstmTransitory.repository;

import in.cropdata.farmers.app.gstmTransitory.entity.GroundTruthZL20;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

/**
 * @author RinkeshKM
 * @project BasicFarmersAppAPI
 * @created 23/03/2021 - 4:06 PM
 */

@Repository
public interface GroundTruthZL20Repository extends JpaRepository<GroundTruthZL20, Integer> {

    @Modifying
    @Transactional
    @Query(value = "insert into gstm_transitory.ground_truth_zl20 (StateCode, TileID, VillageCode, CaseID, StressID,\n" +
            "                                               WeekNo, Year, SymptomID, Status) values\n" +
            "(?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9)\n" +
            " ON DUPLICATE KEY UPDATE TileID = values(TileID)", nativeQuery = true)
    void saveGroundTruthZL20(Integer stateCode, String tileID, Integer villageCode, String caseID, Integer stressID,
                             Integer weekNo, Integer year, Integer symptomID, String status);

    @Modifying
    @Transactional
    @Query(value = "delete gstm_transitory.ground_truth_zl20 from gstm_transitory.ground_truth_zl20\n" +
            "    inner join drkrishi_ts.case_crop_info cci on cci.case_id = ground_truth_zl20.CaseID\n" +
            "    inner join drkrishi_ts.farm_case fc on fc.id = cci.case_id\n" +
            "    inner join drkrishi_ts.farmer_farm ff on ff.id = fc.farm_id\n" +
            "    inner join drkrishi_ts.farmer f on f.id = ff.farmer_id\n" +
            "where ground_truth_zl20.CaseID =?1\n" +
            "    and ground_truth_zl20.SymptomID =?2\n" +
            "    and f.auth_token =?3", nativeQuery = true)
    Integer deleteByCaseIDAndSymptomID(String caseID, Integer symptomID, String authToken);

}
