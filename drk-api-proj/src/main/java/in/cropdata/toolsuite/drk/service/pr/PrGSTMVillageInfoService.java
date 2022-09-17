package in.cropdata.toolsuite.drk.service.pr;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import in.cropdata.toolsuite.drk.model.pr.CCTCSchedules;
import in.cropdata.toolsuite.drk.model.pr.PrVillageCommoditySchedule;
import in.cropdata.toolsuite.drk.model.pr.PrVillageInfo;
import in.cropdata.toolsuite.drk.model.pr.PrVillageInformationSummary;
import in.cropdata.toolsuite.drk.model.pr.PrVillageSubRegionWiseScheduleSummary;

public interface PrGSTMVillageInfoService {

	public Map<String, Object> saveVilageInfo(List<PrVillageInfo> prVillageInfoList);

	public Map<String, Object> saveVillageCCTCSchedulesInfo(List<CCTCSchedules> prCCTCSchedulesInfoList);

	public Map<String, Object> saveCommodityVillageSchedulesInfo(
			List<PrVillageCommoditySchedule> prVillageCommodityScheduleList);

	public Map<String, Object> SubRegionWiseVillageSchedulesSummaryInfo(
			List<PrVillageSubRegionWiseScheduleSummary> prVillageCommodityScheduleSummary);

	public Map<String, Object> addVillageInformationSummary(
			List<PrVillageInformationSummary> prVillageInformationSummary);

}
