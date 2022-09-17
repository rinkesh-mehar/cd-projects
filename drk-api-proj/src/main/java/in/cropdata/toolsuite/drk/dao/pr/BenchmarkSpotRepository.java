package in.cropdata.toolsuite.drk.dao.pr;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.toolsuite.drk.dto.tileassignment.BenchMarkSpotInfDto;
import in.cropdata.toolsuite.drk.model.pr.BenchmarkSpots;

public interface BenchmarkSpotRepository extends JpaRepository<BenchmarkSpots, Integer> {

	@Query(value = "select * from case_details where CaseID =?1 limit 1", nativeQuery = true)
	BenchMarkSpotInfDto getSpotDataListFromCaseDetails(long caseID);

	@Query(value = "select * from benchmark_spots_zl20 where CreatedAt < ?1", nativeQuery = true)
	List<BenchmarkSpots> getAllOldSpotData(String sevenDaysOldDate);

	@Query(value = "SELECT RegionId as RegionID, group_concat(SpotId) as SpotID \n"
			+ "FROM benchmark_spots_zl20\n"
			+ "group by RegionId where SubregionId =?1 and CommodityID =?2 and PhenophaseId =?3", nativeQuery = true)
	BenchMarkSpotInfDto getBenchmarkSpotList(int subRegionId, int commodityId, int phenophaseId);

	@Query(value = "select avg(ndvi) from ndvi_details_zl20 where TileId in (?1)", nativeQuery = true)
	Long getAverageNDVI(List<Integer> newSpotList);

}
