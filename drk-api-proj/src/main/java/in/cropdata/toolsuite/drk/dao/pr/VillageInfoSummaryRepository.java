package in.cropdata.toolsuite.drk.dao.pr;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.toolsuite.drk.model.pr.PrVillageInformationSummary;

public interface VillageInfoSummaryRepository extends JpaRepository<PrVillageInformationSummary, Integer> {

	@Query(value = "select * from gstm_transitory.village_information_summary where SubRegion = ?1", nativeQuery = true)
	PrVillageInformationSummary getBySubRegion(long subRegion);

}
