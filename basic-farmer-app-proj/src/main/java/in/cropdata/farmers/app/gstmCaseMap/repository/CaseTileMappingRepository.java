package in.cropdata.farmers.app.gstmCaseMap.repository;

import in.cropdata.farmers.app.gstmCaseMap.entity.CaseTileMappingZL20;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;

/**
 * @author RinkeshKM
 * @project BasicFarmersAppAPI
 * @created 23/03/2021 - 3:46 PM
 */
public interface CaseTileMappingRepository extends JpaRepository<CaseTileMappingZL20, Integer> {

    @Query(value = "SELECT TileID FROM gstm_case_map.case_tile_mapping_zl20 where CaseID =?1 LIMIT 1", nativeQuery = true)
    String getTileID(String caseId);
}
