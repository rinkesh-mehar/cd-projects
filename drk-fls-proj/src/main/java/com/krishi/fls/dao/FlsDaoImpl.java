package com.krishi.fls.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.krishi.fls.entity.BankDetails;
import com.krishi.fls.entity.CaseCoordinates;
import com.krishi.fls.entity.CaseCropInfo;
import com.krishi.fls.entity.CaseFieldDetails;
import com.krishi.fls.entity.CaseKml;
import com.krishi.fls.entity.CaseType;
import com.krishi.fls.entity.FarmCase;
import com.krishi.fls.entity.Farmer;
import com.krishi.fls.entity.FarmerFarm;
import com.krishi.fls.entity.FarmerGeneralInfo;
import com.krishi.fls.entity.FlsTask;
import com.krishi.fls.entity.GovtOfficial;
import com.krishi.fls.entity.Poi;
import com.krishi.fls.entity.Region;
import com.krishi.fls.entity.Rights;
import com.krishi.fls.entity.State;
import com.krishi.fls.entity.StaticData;
import com.krishi.fls.entity.TaskAllocation;
import com.krishi.fls.entity.TaskType;
import com.krishi.fls.entity.Village;
import com.krishi.fls.entity.VillageAdditionalInfo;
import com.krishi.fls.entity.VillageInfo;
import com.krishi.fls.entity.Vip;
import com.krishi.fls.model.HistoricalData;
import com.krishi.fls.repository.BankDetailsRepository;
import com.krishi.fls.repository.CaseCoordinateRepository;
import com.krishi.fls.repository.CaseCropRepository;
import com.krishi.fls.repository.CaseDetailRepository;
import com.krishi.fls.repository.CaseKmlRepository;
import com.krishi.fls.repository.FarmCaseRepository;
import com.krishi.fls.repository.FarmerCropInfoRepository;
import com.krishi.fls.repository.FarmerFarmRepository;
import com.krishi.fls.repository.FarmerGeneralInfoRepository;
import com.krishi.fls.repository.FarmerRepository;
import com.krishi.fls.repository.FlsTaskRepository;
import com.krishi.fls.repository.GovtOfficalRepository;
import com.krishi.fls.repository.PoiRepository;
import com.krishi.fls.repository.RegionRepository;
import com.krishi.fls.repository.RightsRepository;
import com.krishi.fls.repository.StateRepository;
import com.krishi.fls.repository.StaticDataRepository;
import com.krishi.fls.repository.TaskAllocationRepositry;
import com.krishi.fls.repository.TasktypeRepository;
import com.krishi.fls.repository.VillageAdditionalInfoRepository;
import com.krishi.fls.repository.VillageInfoRepository;
import com.krishi.fls.repository.VillageRepository;
import com.krishi.fls.repository.VipRepository;
import com.krishi.fls.utility.DistanceUtility;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * @author janardhan
 *
 */

@Component
@Repository
public class FlsDaoImpl implements FlsDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(FlsDaoImpl.class);
	@Autowired
	private EntityManager em;
	@Autowired
	private FarmerRepository farmerRepo;

	@Autowired
	private VillageRepository villageRepo;

	@Autowired
	private RegionRepository regionRepo;

	@Autowired
	private StateRepository stateRepo;

	@Autowired
	private TaskAllocationRepositry taskAllocationRepo;

	@Autowired
	private TasktypeRepository taskTypeRepo;

	@Autowired
	private PoiRepository poiRepo;

	@Autowired
	private StaticDataRepository staticDataRepo;

	@Autowired
	private FarmerFarmRepository farmerFarmRepo;

	@Autowired
	private FarmCaseRepository farmerCaseRepo;

	@Autowired
	private VillageInfoRepository villageInfoRepo;

	@Autowired
	private VipRepository vipRepository;

	@Autowired
	private GovtOfficalRepository govtOfficalRepo;
	
	@Autowired
	private CaseCropRepository caseCropRepo;
	
	@Autowired
	private CaseDetailRepository caseDetailRepo;
	
	@Autowired
	private CaseKmlRepository caseKmlRepo;
	
	@Autowired
	private CaseCoordinateRepository caseCoordinateRepo;
	
	@Autowired
	private RightsRepository rightRepo;
	
	@Autowired
	private BankDetailsRepository bankDetailsRepo; 
	
	@Autowired
	private FarmerGeneralInfoRepository farmerGeneralInfoRepo;
	
	@Autowired
	private FlsTaskRepository flsTaskRepo;
	
	@Autowired
	private VillageAdditionalInfoRepository villageAdditionalInfoRepository;
	
	@Autowired
	private FarmerCropInfoRepository cropInfoRepository;

	/**
	 * @param regionId
	 * @return list of Task
	 */
	public List<Farmer> getFlsTaskData(int regionId) {
		List<Farmer> farmers = farmerRepo.findbyRegion(regionId);
		return farmers;
	}

	@Override
	public Farmer getFarmer(String id) {
		Farmer farmer = farmerRepo.findById(id).get();
		return farmer;
	}

	@Override
	public Village findVillageById(int villageId) {
		Village village = villageRepo.findById(villageId).get();
		return village;
	}

	@Override
	public Region findRegionById(int regionId) {
		Region region = regionRepo.findById(regionId).get();
		return region;
	}

	@Override
	public State findStateById(int stateId) {
		State state = stateRepo.findById(stateId).get();
		return state;
	}

	@Override
	public List<TaskType> tasktype() {
		List<TaskType> taskType = taskTypeRepo.getcompleteTask();
		return taskType;
	}

	@Override
	public List<CaseType> casetype() {
		List<CaseType> caseType = taskTypeRepo.getcompleteCaseType();
		return caseType;
	}

	@Transactional(readOnly = true)
	@Override
	public Set<Poi> findPoiByVillageIds(List<Integer> villageIds) {
		return poiRepo.findDistinctByVillageIdIn(villageIds);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.krishi.fls.dao.FlsDao#getTaskAllocation(int)
	 */
	
	@Override
	public List<TaskAllocation> getTask(Integer userId, Integer regionId){

		
		/*
		 * List<TaskAllocation> taskAllocations =
		 * taskAllocationRepo.getExistingFlsTaskByRegionIdAndAssigneeId(regionId,
		 * userId);
		 */
		 
		
		  List<TaskAllocation> taskAllocations =
		  taskAllocationRepo.getExistingFlsTaskByAssigneeId(userId, regionId);
		 
		 if(taskAllocations == null || taskAllocations.size() == 0 ) { 
			flsTaskRepo.updateFlsTaskByRegionId(regionId, userId); 
			/*
			 * taskAllocations =
			 * taskAllocationRepo.getFlsTaskByRegionIdAndAssigneeId(regionId,userId);
			 */
			taskAllocations = taskAllocationRepo.getFlsTaskByAssigneeId(userId, regionId);
			taskAllocations.forEach(item -> {
				item.setAssigneeId(userId);
				item.setStatus(1);
			});
			updateTaskAllocation(taskAllocations);
			 } 
		return taskAllocations;
	}
	
	
	@Override
	public List<TaskAllocation> getTaskAllocation(int regionId) {
		List<StaticData> list = staticDataRepo.findByDataKeyIn(List.of("flspriority1", "flspriority2", "flspriority3", "flsCount"));
		StaticData priority1Data = list.stream().filter(s -> s.getDataKey().equals("flspriority1"))
				.findFirst().orElseThrow(() -> new RuntimeException("flspriority1 not found"));
		StaticData priority2Data = list.stream().filter(s -> s.getDataKey().equals("flspriority2"))
				.findFirst().orElseThrow(() -> new RuntimeException("flspriority2 not found"));
		StaticData priority3Data = list.stream().filter(s -> s.getDataKey().equals("flspriority3"))
				.findFirst().orElseThrow(() -> new RuntimeException("flspriority3 not found"));
		StaticData flsCountData = list.stream().filter(s -> s.getDataKey().equals("flsCount"))
				.findFirst().orElseThrow(() -> new RuntimeException("flsCount not found"));
		
		

		int flsCount = Integer.parseInt(flsCountData.getDataValue());
		List<TaskAllocation> taskAllocations = new ArrayList<TaskAllocation>();
		
		List<TaskAllocation> priortyTask = taskAllocationRepo.getAgrigotaTaskData(
				getPriorityArray(priority1Data.getDataValue()), regionId);
		Region region = regionRepo.findById(regionId).get();
		if (priortyTask != null && priortyTask.size() > 0) {
			priortyTask = sortedPriority(priortyTask, region);
			taskAllocations.addAll(priortyTask);
		}
		
		if (taskAllocations.size() < flsCount) {
			priortyTask = taskAllocationRepo.getDrkTaskData(getPriorityArray(priority2Data.getDataValue()), 
					regionId);
			if (priortyTask != null && priortyTask.size() > 0) {
				priortyTask = sortedPriority(priortyTask, region);
				taskAllocations.addAll(priortyTask);
			}
		}
		
		if (taskAllocations.size() < flsCount) {
			priortyTask = taskAllocationRepo.getWillingDrkTaskData(getPriorityArray(priority3Data.getDataValue()),
					regionId);
			if (priortyTask != null && priortyTask.size() > 0) {
				priortyTask = sortedPriority(priortyTask, region);
				taskAllocations.addAll(priortyTask);
			}
		}
		// priorityConditions
		return taskAllocations.stream().limit(flsCount).collect(Collectors.toList());
	}
	
private void addVillageTask(Village village, TaskAllocation task, Map<Integer, List<TaskAllocation>> villageTaskMap, List<Map<Integer, Map<String, Double>>> villageMapList) {
	Map<String, Double> map = new HashMap<>();
	Map<Integer, Map<String, Double>> villageMap = new HashMap<>();
	map.put("lat", village.getLatitude().doubleValue());
	map.put("lon", village.getLongitude().doubleValue());
	villageMap.put(village.getId(), map);
	villageMapList.add(villageMap);
	List<TaskAllocation> list = new ArrayList<>();
	list.add(task);
	villageTaskMap.put(village.getId(), list);
}
	
	private List<TaskAllocation> sortedPriority(List<TaskAllocation> priorityTask, Region region) {
		
		Map<Integer, List<TaskAllocation>> villageTaskMap = new HashMap<>();
		List<Map<Integer, Map<String, Double>>> villageMapList = new ArrayList<Map<Integer, Map<String, Double>>>();
		
		for (TaskAllocation task : priorityTask) {
			if(task.getEntityTypeId() == 4) {
				Farmer farmer = farmerRepo.findById(task.getEntityId()).get();
				Village village = villageRepo.findById(farmer.getVillageId()).get();
				if(villageTaskMap.containsKey(village.getId())) {
					villageTaskMap.get(village.getId()).add(task);
				} else {
					addVillageTask(village, task, villageTaskMap, villageMapList);
				}
			} else if(task.getEntityTypeId() == 1) {
				FarmCase farmcase=farmerCaseRepo.getBycaseID(task.getEntityId());	
				FarmerFarm farmerFarm=farmerFarmRepo.findbyfarmId(farmcase.getFarmId());
				Farmer farmer = farmerRepo.findById(farmerFarm.getFarmerId()).get();
				Village village = villageRepo.findById(farmer.getVillageId()).get();
				if(villageTaskMap.containsKey(village.getId())) {
					villageTaskMap.get(village.getId()).add(task);
				} else {
					addVillageTask(village, task, villageTaskMap, villageMapList);
				}
			} else {
				System.out.println("Unknown task type");
			}
			
		}
		
		DistanceUtility distanceUtility = new DistanceUtility();
		List<Map<Integer, Map<String, Double>>> sortedlist = distanceUtility.getSortedVillageByDistance(region.getLatitude().doubleValue(),region.getLongitude().doubleValue(),villageMapList);
		return sortedlist.stream().flatMap(l -> l.keySet().stream()).flatMap(v -> villageTaskMap.get(v).stream()).collect(Collectors.toList());
	}

//	private List<TaskAllocation> sortedPriority(List<TaskAllocation> priortyTask, Region region) {
//		Set<Farmer> farmers = new HashSet<Farmer>();
//		Set<Village> villages = new HashSet<Village>();
//		
//		getFarmers(priortyTask, farmers);
//		
//		getVillages(farmers, villages);
//		List<Map<Integer, Map<String, Double>>> newlist = new ArrayList<Map<Integer, Map<String, Double>>>();
//		for(Village village:villages) {
//			Map<String, Double> latLong = new HashMap<String, Double>();
//			latLong.put("lat",village.getLatitude().doubleValue());
//			latLong.put("lon",village.getLongitude().doubleValue());
//			Map<Integer, Map<String, Double>> villageData = new HashMap<Integer, Map<String,Double>>();
//			villageData.put(village.getId(), latLong);
//			newlist.add(villageData);
//		}
//		
//		DistanceUtility distanceUtility = new DistanceUtility();
//		List<Map<Integer, Map<String, Double>>> sortedlist = distanceUtility.getSortedVillageByDistance(region.getLatitude().doubleValue(),region.getLongitude().doubleValue(),newlist);
//		List<TaskAllocation> priority = new ArrayList<TaskAllocation>();
//		for(Map<Integer, Map<String, Double>> data : sortedlist) {
//			for(Farmer farmer :farmers) {
//				for(Entry<Integer, Map<String, Double>> id:data.entrySet()) {
//					if(id.getKey() == farmer.getVillageId()) {
//						for(TaskAllocation ta :priortyTask) {
//							if(ta.getEntityId() == farmer.getFarmerId()) {
//								priority.add(ta);
//							}
//						}
//					}
//				}
//			}
//			
//		}
//		return priority;
//	}

	private List<Integer> getPriorityArray(String p) {
		return Stream.of(p.split(",")).map(String::trim).map(Integer::parseInt).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void updateTaskAllocation(List<TaskAllocation> allocations) {
		taskAllocationRepo.saveAll(allocations);
	}

	@Transactional(readOnly = true)
	@Override
	public String findStaticValueByKey(String key) {
		List<StaticData> list = staticDataRepo.findByDataKeyIn(List.of(key));
		StaticData data = list.stream().findFirst()
				.orElseThrow(() -> new RuntimeException(key+" not found"));
		return data.getDataValue();
	}

	@Override
	public HistoricalData getHistorydata(Integer userId) {
		HistoricalData historicalData = new HistoricalData();
		List<TaskAllocation> taskallocationList = taskTypeRepo.getAllTask();
		List<String> farmerId = taskallocationList.stream().filter(t -> t.getTaskTypeId() == 1).map(t -> t.getEntityId())
				.collect(Collectors.toList());
		List<String> caseId = taskallocationList.stream().filter(t -> (t.getTaskTypeId() == 5 || t.getTaskTypeId() == 4)   && t.getAssigneeId().equals(userId)).map(t -> t.getEntityId())
				.collect(Collectors.toList());
/** handle farm data based on taskTypeId*/
		List<String> farmIds = taskallocationList.stream().filter(t -> (t.getTaskTypeId() == 3)).map(t -> t.getEntityId())
				.collect(Collectors.toList());

		/** handle entityTypeID 2*/
		List<String> farmerIds = taskallocationList.stream().filter(t -> t.getTaskTypeId() == 2).map(t -> t.getEntityId())
				.collect(Collectors.toList());

		farmerId.addAll(farmerIds);
		List<FarmCase> farmCase = farmerCaseRepo.findAllById(caseId);
		historicalData.setFarmCase(farmCase);
		List<String> farmId = farmCase.stream().map(t -> t.getFarmId()).collect(Collectors.toList());

		farmId.addAll(farmIds);
		List<FarmerFarm> farmerFarm = farmerFarmRepo.findAllById(farmId);
		farmerFarm.forEach(f -> farmerId.add(f.getFarmerId()));
		historicalData.setFarmerFarm(farmerFarm);

		List<Farmer> farmer = farmerRepo.findAllById(farmerId);
//		historicalData.setFarmer(farmer);
		List<Object[]> bankDetailsList = farmerCaseRepo.getFarmerBankDetails(farmerId.size()>0?farmerId:null);
		List<Map<String, Object>> bankList = new ArrayList<>();
		if (bankDetailsList != null && (!bankDetailsList.isEmpty())){
			for (Object[] bankDetails: bankDetailsList) {
				Map<String, Object> farmerBankDetails = new HashMap<>();
				farmerBankDetails.put("farmerId", bankDetails[0]);
				farmerBankDetails.put("branchId", bankDetails[1]);
				farmerBankDetails.put("branchName", bankDetails[2]);
				farmerBankDetails.put("bankName", bankDetails[3]);
				farmerBankDetails.put("accountName", bankDetails[4]);
				farmerBankDetails.put("accNumber", bankDetails[5]);
				farmerBankDetails.put("passbookImageUrl", bankDetails[6]);
				farmerBankDetails.put("cancelledChequeUrl", bankDetails[7]);
				farmerBankDetails.put("bankInfo", bankDetails[8]);

				bankList.add(farmerBankDetails);
			}
		}
		historicalData.setFarmerBankDetails(bankList);
//		List<BankDetails> bankDetails = bankDetailsRepo.findByFarmerIdIn(farmerId);
//		historicalData.setFarmerBankAccount(bankDetails);
		
		List<CaseCropInfo> caseCropInfo = caseCropRepo.findByCaseIdIn(caseId);
		historicalData.setCaseCropInfo(caseCropInfo);
		List<CaseFieldDetails> caseFieldDetails = caseDetailRepo.findByCaseIdIn(caseId);
		historicalData.setCaseFieldDetails(caseFieldDetails);
		List<CaseKml> caseKml = caseKmlRepo.findByFarmCaseIdIn(caseId);
		historicalData.setCaseKml(caseKml);
		List<Rights> rights=rightRepo.findByCaseIdIn(caseId);
		List<FarmerGeneralInfo> farmerGenarelInfo=farmerGeneralInfoRepo.findByFarmerIdIn(farmerId);
		historicalData.setRights(rights);
		historicalData.setFarmerGeneralInfo(farmerGenarelInfo);
		try {
			StaticData data=staticDataRepo.findByDataKey("outputzip");
			createkmlZip(caseKml,userId,data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<CaseCoordinates> caseCoordinates = caseCoordinateRepo.findByCaseIdIn(caseId);
		
		historicalData.setCaseCoordinates(caseCoordinates);

		List<Integer> villageId = farmer.stream().map(t -> t.getVillageId()).collect(Collectors.toList());
		List<VillageInfo> villageInfo = villageInfoRepo.findByVillageIdIn(villageId);

		List<Farmer> farmers = farmerRepo.findByVillageIdIn(villageId);
//		historicalData.setFarmer(farmers);
		
		/** added major crop -CDT-Ujwal- Start*/ 
			if (farmers != null && farmers.size() > 0) {
				for (Farmer f : farmers) {
					try {
						f.setMajorCrop(cropInfoRepository.getMajorCommoditybyFarmerId(f.getFarmerId()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		historicalData.setFarmer(farmers);

		/** added major crop -CDT-Ujwal- End*/ 
		
		historicalData.setVillageInfos(villageInfo);
		List<Vip> vip = vipRepository.findByVillageIdIn(villageId);
		historicalData.setVip(vip);

		Set<Poi> poi = poiRepo.findDistinctByVillageIdIn(villageId);
		historicalData.setPoi(poi);

		Set<GovtOfficial> govtOffical = govtOfficalRepo.findDistinctByVillageIdIn(villageId);
		historicalData.setGovtOffical(govtOffical);
		StaticData basePath=staticDataRepo.findByDataKey("basePath");
		StaticData flsZip=staticDataRepo.findByDataKey("flszip");
		historicalData.setZipFilePath(basePath.getDataValue()+flsZip.getDataValue()+"/Kml-"+userId+".zip");
		List<VillageAdditionalInfo> villageAdditionalInfos = villageAdditionalInfoRepository.findByVillageIdIn(villageId);
		historicalData.setVillageAdditionalInfo(villageAdditionalInfos);
		return historicalData;
	}
	
	private void createkmlZip(List<CaseKml> caseKml,Integer userId,StaticData data ) throws IOException
	{
		FileOutputStream fos;
		try {
			StaticData basePath=staticDataRepo.findByDataKey("docBasePath");
			 StaticData kmlPath=staticDataRepo.findByDataKey("kmlFilePath");
					 
			 StaticData outputFilePath=staticDataRepo.findByDataKey("flszip");
			fos = new FileOutputStream(basePath.getDataValue()+outputFilePath.getDataValue()+"/Kml-"+userId+".zip");
			 ZipOutputStream zipOS = new ZipOutputStream(fos);
			 Set<String> paths = caseKml.stream().map(a -> a.getKmlFilePath()).collect(Collectors.toSet());
				for(String path:paths)
				{
					
					String fileName=basePath.getDataValue()+kmlPath.getDataValue() +"/"+path;
					System.out.println("compeletefilePath-----------------"+fileName);
					writeToZipFile(fileName, zipOS);
		            

				}

	            zipOS.close();
	            fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
       
	}
	
	public static void writeToZipFile(String path, ZipOutputStream zipStream)
            throws FileNotFoundException, IOException {
		
		        System.out.println("Writing file : '" + path + "' to zip file");

        File aFile = new File(path);
        FileInputStream fis = new FileInputStream(aFile);
        ZipEntry zipEntry = new ZipEntry(path);
        System.out.println("zipentryPath-----------------"+path);
        zipStream.putNextEntry(zipEntry);

        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipStream.write(bytes, 0, length);
        }

        zipStream.closeEntry();
        fis.close();
    }

	/**
	 * @param taskAllocations
	 * @param farmers
	 */
	private void getFarmers(List<TaskAllocation> taskAllocations, Set<Farmer> farmers) {
		Farmer farmer=null;
		for (TaskAllocation taskAllocation : taskAllocations) {
			if(taskAllocation.getTaskTypeId() == 4) {
				farmer = getFarmer(taskAllocation.getEntityId());
			} else {
				FarmCase farmcase=farmerCaseRepo.getBycaseID(taskAllocation.getEntityId());	
				FarmerFarm farmerFarm=farmerFarmRepo.findbyfarmId(farmcase.getFarmId());
			 farmer = getFarmer(farmerFarm.getFarmerId());
			}
			farmers.add(farmer);
		}
	}
	
	/**
	 * @param farmers
	 * @param villages
	 */
	private void getVillages(Set<Farmer> farmers, Set<Village> villages) {
		for (Farmer farmer : farmers) {
			Village village = findVillageById(farmer.getVillageId());
			villages.add(village);
		}
	}

}
