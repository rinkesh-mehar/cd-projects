package in.cropdata.toolsuite.drk.service.pr.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.cropdata.toolsuite.drk.dao.pr.CCTCSchedulesRepository;
import in.cropdata.toolsuite.drk.dao.pr.CommodityVillageSchedulesRepository;
import in.cropdata.toolsuite.drk.dao.pr.PrVillageInfoRepository;
import in.cropdata.toolsuite.drk.dao.pr.SubRegionWiseVillageSchedulesSummaryRepository;
import in.cropdata.toolsuite.drk.dao.pr.VillageInfoSummaryRepository;
import in.cropdata.toolsuite.drk.dto.gt.VillageInfoInfDto;
import in.cropdata.toolsuite.drk.exceptions.DataInsertionException;
import in.cropdata.toolsuite.drk.exceptions.DuplicateDataException;
import in.cropdata.toolsuite.drk.exceptions.InvalidDataException;
import in.cropdata.toolsuite.drk.model.pr.CCTCSchedules;
import in.cropdata.toolsuite.drk.model.pr.PrVillageCommoditySchedule;
import in.cropdata.toolsuite.drk.model.pr.PrVillageInfo;
import in.cropdata.toolsuite.drk.model.pr.PrVillageInformationSummary;
import in.cropdata.toolsuite.drk.model.pr.PrVillageSubRegionWiseScheduleSummary;
import in.cropdata.toolsuite.drk.service.pr.PrGSTMVillageInfoService;

@Service
public class PrGSTMVillageInfoServiceImpl implements PrGSTMVillageInfoService {

	@Autowired
	private PrVillageInfoRepository prVillageInfoRepository;

	@Autowired
	private CCTCSchedulesRepository cctcSchedulesRepository;

	@Autowired
	private CommodityVillageSchedulesRepository commodityVillageSchedulesRepository;

	@Autowired
	private VillageInfoSummaryRepository villageInfoSummaryRepo;

	@Autowired
	private SubRegionWiseVillageSchedulesSummaryRepository subRegionWiseVillageSchedulesSummaryRepository;

	@Override
	public Map<String, Object> saveVilageInfo(List<PrVillageInfo> prVillageInfoList) {

		try {

			Map<String, Object> resMap = new HashMap<String, Object>();

			if (prVillageInfoList.size() > 0 && !prVillageInfoList.isEmpty()) {

				Map<String, Object> map = validateListVillageInfo(prVillageInfoList);

				if (map.containsValue(false)) {
					throw new DuplicateDataException(map.get("Message").toString());
				}

				List<PrVillageInfo> vInfoList = prVillageInfoRepository.saveAll(prVillageInfoList);

				List<VillageInfoInfDto> farmerRegisteredCountList = prVillageInfoRepository
						.getFarmersRegistererdCountList();

				for (VillageInfoInfDto infDto : farmerRegisteredCountList) {

					String[] villageGroup = infDto.getVillageGroup().split(",");
					String[] registeredFarmerGroup = infDto.getRegisteredFarmerGroup().split(",");

					String farmerRegisteredSubRegionWise = "";
					int totalFarmersSubRegionWise = 0;
					boolean flag = false;
					for (int i = 0; i < villageGroup.length; i++) {

						Optional<PrVillageInfo> villageInfo = prVillageInfoRepository.getBySubRegionIdAndVillageCode(
								infDto.getSubRegionID(), Integer.parseInt(villageGroup[i]));

						if (villageInfo.isPresent()) {

							PrVillageInfo oldVillageInfo = villageInfo.get();
							totalFarmersSubRegionWise += oldVillageInfo.getTotalFarmers();
							farmerRegisteredSubRegionWise += registeredFarmerGroup[i];
							flag = true;
						}
					}

					if (flag) {

						PrVillageInformationSummary infoSummary = new PrVillageInformationSummary();
						infoSummary.setSubRegion(infDto.getSubRegionID());
						infoSummary.setTotalTask(totalFarmersSubRegionWise);
						if (totalFarmersSubRegionWise != 0) {
							infoSummary.setTaskCompletionPercent(new BigDecimal(((Integer
									.valueOf(farmerRegisteredSubRegionWise != "" ? farmerRegisteredSubRegionWise : "0"))
									* 100) / totalFarmersSubRegionWise));
						} else {
							infoSummary.setTaskCompletionPercent(BigDecimal.ZERO);
						}

						villageInfoSummaryRepo.save(infoSummary);
					}
				}

				if (vInfoList != null && vInfoList.size() > 0 && !vInfoList.isEmpty()) {

					resMap.put("success", true);
					resMap.put("message", vInfoList.size() + " village information data has been processed.");

					return resMap;

				} else {
					throw new DataInsertionException("Failed to Insert Record..No record has been inserted");
				}
			} else {
				throw new InvalidDataException("Invalid Data. Input Data can not be empty.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	private Map<String, Object> validateListVillageInfo(List<PrVillageInfo> prVillageInfoList) {

		try {

			Map<String, Object> map = new HashMap<>();
			List<String> existingRecordCheckList = new ArrayList<>();
			map.put("Status", true);
			for (PrVillageInfo info : prVillageInfoList) {

				String value = (Integer.valueOf(info.getSubRegionId()).toString()
						.concat(Integer.valueOf(info.getVillageCode()).toString())).trim();

				if (existingRecordCheckList.contains(value)) {

					map.put("Status", false);
					map.put("Message", "Duplicate Record For SubRegion " + info.getSubRegionId() + " And VillageCode "
							+ info.getVillageCode());
					break;

				} else {
					existingRecordCheckList.add(value);
				}
			}
			return map;
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public Map<String, Object> saveVillageCCTCSchedulesInfo(List<CCTCSchedules> prCCTCSchedulesInfoList) {

		try {

			Map<String, Object> resMap = new HashMap<String, Object>();

			if (prCCTCSchedulesInfoList.size() > 0 && !prCCTCSchedulesInfoList.isEmpty()) {

				List<CCTCSchedules> list = cctcSchedulesRepository.saveAll(prCCTCSchedulesInfoList);

				if (list.size() > 0 && !list.isEmpty()) {

					resMap.put("success", true);
					resMap.put("message", list.size() + " Village CCTC Schedules Info data has been processed.");
					return resMap;

				} else {
					throw new DataInsertionException("Failed to Insert Record..No record has been inserted");
				}

			} else {
				throw new InvalidDataException("Invalid Data. Input Data can not be empty.");
			}
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Map<String, Object> saveCommodityVillageSchedulesInfo(
			List<PrVillageCommoditySchedule> prVillageCommodityScheduleList) {

		try {

			Map<String, Object> resMap = new HashMap<String, Object>();

			if (prVillageCommodityScheduleList.size() > 0 && !prVillageCommodityScheduleList.isEmpty()) {

				List<PrVillageCommoditySchedule> list = commodityVillageSchedulesRepository
						.saveAll(prVillageCommodityScheduleList);

				if (list.size() > 0 && !list.isEmpty()) {
					resMap.put("success", true);
					resMap.put("message", list.size() + " Commodity Village Schedules Info data has been processed.");
					return resMap;
				} else {
					throw new DataInsertionException("Failed to Insert Record..No record has been inserted");
				}
			} else {
				throw new InvalidDataException("Invalid Data. Input Data can not be empty.");
			}
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Map<String, Object> SubRegionWiseVillageSchedulesSummaryInfo(
			List<PrVillageSubRegionWiseScheduleSummary> prVillageCommodityScheduleSummary) {

		try {

			Map<String, Object> resMap = new HashMap<String, Object>();

			if (prVillageCommodityScheduleSummary.size() > 0 && !prVillageCommodityScheduleSummary.isEmpty()) {

				List<PrVillageSubRegionWiseScheduleSummary> list = subRegionWiseVillageSchedulesSummaryRepository
						.saveAll(prVillageCommodityScheduleSummary);

				if (list.size() > 0 && !list.isEmpty()) {

					resMap.put("success", true);
					resMap.put("message",
							list.size() + " SubRegionWise Village Schedules Summary Info data has been processed.");
					return resMap;

				} else {
					throw new DataInsertionException("Failed to Insert Record..No record has been inserted");
				}
			} else {
				throw new InvalidDataException("Invalid Data. Input Data can not be empty.");
			}
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Map<String, Object> addVillageInformationSummary(
			List<PrVillageInformationSummary> prVillageInformationSummary) {

		try {

			Map<String, Object> resMap = new HashMap<String, Object>();

			if (prVillageInformationSummary.size() > 0 && !prVillageInformationSummary.isEmpty()) {

				List<PrVillageInformationSummary> list = villageInfoSummaryRepo.saveAll(prVillageInformationSummary);

				if (list.size() > 0 && !list.isEmpty()) {

					resMap.put("success", true);
					resMap.put("message", list.size() + " Village Information Summary Info data has been processed.");
					return resMap;

				} else {

					throw new DataInsertionException("Failed to Insert Record..No record has been inserted");
				}
			} else {
				throw new InvalidDataException("Invalid Data. Input Data can not be empty.");
			}
		} catch (Exception e) {
			throw e;
		}
	}

}
