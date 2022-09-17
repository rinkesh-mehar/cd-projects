package in.cropdata.toolsuite.drk.dao.gt;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.toolsuite.drk.dto.gt.GroundTruthZLInfDto;
import in.cropdata.toolsuite.drk.model.cases.CaseDetails;
import in.cropdata.toolsuite.drk.model.gt.GT_ZL19;

public interface GT_ZL19_Repository extends JpaRepository<GT_ZL19, Integer> {

	@Query(value = "select * from gstm_transitory.ground_truth_zl19 spot\n"
			+ "INNER JOIN cdt_master_data.geo_village village ON (spot.VillageCode = village.VillageCode)\n"
			+ "where village.PanchayatCode = ?1", nativeQuery = true)
	List<GT_ZL19> getPanchayatwiseFarmData(Integer panchayatCode);

	@Query(value = "select farmLevel.RegionID,farmLevel.VillageCode,farmLevel.CaseID,farmLevel.TileID as TileGroup,\n"
			+ "group_concat(ifnull(farmLevel.StressID,0)) as StressIDGroup,\n"
			+ "group_concat(ifnull(farmLevel.ServerityID,0)) as SeverityIDGroup \n"
			+ "from gstm_transitory.ground_truth_zl19 farmLevel\n"
			+ "INNER JOIN cdt_master_data.geo_village village ON (farmLevel.VillageCode = village.VillageCode)\n"
			+ "where village.PanchayatCode = ?1\n" + "group by RegionID,VillageCode,CaseID, TileID", nativeQuery = true)
	List<GroundTruthZLInfDto> getPanchayatwiseCompleteFarmData(Integer panchayatCode);

	@Query(value = "select * from gstm_transitory.ground_truth_zl19 spot where CaseID = ?1", nativeQuery = true)
	List<GT_ZL19> getCasewiseFarmData(Integer caseID);

	@Query(value = "select CaseID from case_details where CaseID = ?1", nativeQuery = true)
	List<BigInteger> caseDetailsExist(long caseId);

}
