package in.cropdata.toolsuite.drk.dao.pr;

import org.springframework.data.jpa.repository.JpaRepository;

import in.cropdata.toolsuite.drk.model.pr.PrVillageSubRegionWiseScheduleSummary;

public interface SubRegionWiseVillageSchedulesSummaryRepository
		extends JpaRepository<PrVillageSubRegionWiseScheduleSummary, Integer> {

}
