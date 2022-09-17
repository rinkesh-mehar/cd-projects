package in.cropdata.toolsuite.drk.dao.gt;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.cropdata.toolsuite.drk.dto.gt.GroundTruthZLInfDto;
import in.cropdata.toolsuite.drk.model.gt.GT_ZL20;

@Repository
public interface GT_ZL20_Repository extends JpaRepository<GT_ZL20, Integer> {

	@Query(value = "select * from gstm_transitory.ground_truth_zl20 spot\n"
			+ "INNER JOIN cdt_master_data.geo_village village ON (spot.VillageCode = village.VillageCode)\n"
			+ "where village.PanchayatCode = ?1", nativeQuery = true)
	List<GT_ZL20> getPanchayatwiseSpotData(Integer panchayatCode);

	@Query(value = "select * from gstm_transitory.ground_truth_zl20 spot where CaseID = ?1", nativeQuery = true)
	List<GT_ZL20> getCasewiseSpotData(Integer caseID);

	@Query(value = "select RegionID,VillageCode,CaseID, \n" + "group_concat(TileID) as TileGroup,\n"
			+ "group_concat(ifnull(StressID,0)) as StressIDGroup,\n"
			+ "group_concat(ifnull(ServerityID,0)) as SeverityIDGroup \n" + "from (\n"
			+ "select spot.RegionID,spot.VillageCode,spot.CaseID, spot.TileID,\n"
			+ "group_concat(ifnull(spot.StressID, 0) separator '#') as StressID,\n"
			+ "group_concat(ifnull(spot.ServerityID, 0) separator '#') as ServerityID\n"
			+ "from gstm_transitory.ground_truth_zl20 spot \n"
			+ "INNER JOIN cdt_master_data.geo_village village ON (spot.VillageCode = village.VillageCode)\n"
			+ "where village.PanchayatCode = ?1\n" + "group by RegionID,VillageCode,CaseID, TileID\n" + ") as temp \n"
			+ "group by RegionID,VillageCode,CaseID", nativeQuery = true)
	List<GroundTruthZLInfDto> getPanchayatwiseCompleteSpotData(Integer panchayatCode);

}
