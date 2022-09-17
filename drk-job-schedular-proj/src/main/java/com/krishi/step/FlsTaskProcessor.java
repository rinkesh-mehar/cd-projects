package com.krishi.step;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.krishi.entity.FarmCase;
import com.krishi.entity.Farmer;
import com.krishi.entity.FarmerFarm;
import com.krishi.entity.FlsTask;
import com.krishi.entity.Region;
import com.krishi.entity.StaticData;
import com.krishi.entity.TaskAllocation;
import com.krishi.entity.Village;
import com.krishi.repository.FarmCaseRepository;
import com.krishi.repository.FarmerFarmRepository;
import com.krishi.repository.FarmerRepository;
import com.krishi.repository.FlsTaskRepository;
import com.krishi.repository.RegionRepository;
import com.krishi.repository.StaticDataRepository;
import com.krishi.repository.TaskAllocationRepositry;
import com.krishi.repository.VillageRepository;
import com.krishi.utility.DistanceUtility;

public class FlsTaskProcessor implements Tasklet {

	@Autowired
	private StaticDataRepository staticDataRepo;

	@Autowired
	private TaskAllocationRepositry taskAllocationRepo;

	@Autowired
	private RegionRepository regionRepo;

	@Autowired
	private FarmerRepository farmerRepo;

	@Autowired
	private VillageRepository villageRepo;

	@Autowired
	private FarmerFarmRepository farmerFarmRepo;

	@Autowired
	private FarmCaseRepository farmerCaseRepo;
	
	@Autowired
	private FlsTaskRepository flsTaskRepo;

	@PersistenceContext
	private EntityManager em;
	
	/*take fls task from task table, priorities based on distance and insert into fls_task table*/
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		List<Region> regions = regionRepo.findAll();
//		flsTaskRepo.truncateFlsTask();
		for(Region region : regions) {
			List<TaskAllocation> tasks = null;
			int batchNumber = 0;
			
			do {
				batchNumber++;
				Set<String> taskIds = flsTaskRepo.getFlsTaskByRegionId(region.getRegionId());
				tasks = getTaskAllocation(region.getRegionId(), taskIds);
				List<FlsTask> flsTasks = new ArrayList<FlsTask>();
				for (TaskAllocation task : tasks) {
					FlsTask flsTask = new FlsTask();
					flsTask.setTaskId(task.getId());
					flsTask.setRegionId(region.getRegionId());
					flsTask.setBatchNumber(batchNumber);
					flsTasks.add(flsTask);
				}
				if(flsTasks != null && flsTasks.size() > 0) {
					flsTaskRepo.saveAll(flsTasks);
				}
			} while (tasks != null && tasks.size() > 0);
			
		}
		return RepeatStatus.FINISHED;
	}

	private List<TaskAllocation> getTaskAllocation(Integer regionId, Set<String> taskIds) {
		List<StaticData> list = staticDataRepo
				.findByKeyIn(List.of("flspriority1", "flspriority2", "flspriority3", "flsCount"));
		StaticData priority1Data = list.stream().filter(s -> s.getKey().equals("flspriority1")).findFirst()
				.orElseThrow(() -> new RuntimeException("flspriority1 not found"));
		StaticData priority2Data = list.stream().filter(s -> s.getKey().equals("flspriority2")).findFirst()
				.orElseThrow(() -> new RuntimeException("flspriority2 not found"));
		StaticData priority3Data = list.stream().filter(s -> s.getKey().equals("flspriority3")).findFirst()
				.orElseThrow(() -> new RuntimeException("flspriority3 not found"));
		StaticData flsCountData = list.stream().filter(s -> s.getKey().equals("flsCount")).findFirst()
				.orElseThrow(() -> new RuntimeException("flsCount not found"));

		int flsCount = Integer.parseInt(flsCountData.getValue());
		List<TaskAllocation> taskAllocations = new ArrayList<TaskAllocation>();
		
		String priorityData = priority1Data.getValue()+ ","+priority2Data.getValue()+ ","+priority3Data.getValue();
		
		List<TaskAllocation> priortyTask = null;
		if (taskIds != null && taskIds.size() > 0) {
			priortyTask = taskAllocationRepo.getTaskDataWithTask(getPriorityArray(priorityData),
					regionId, taskIds);
		} else {
			priortyTask = taskAllocationRepo.getTaskData(getPriorityArray(priorityData), regionId);
		}
		 
		Region region = regionRepo.findById(regionId).get();
		if (priortyTask != null && priortyTask.size() > 0) {
			priortyTask = sortedPriority(priortyTask, region);
			taskAllocations.addAll(priortyTask);
		}

//		if (taskAllocations.size() < flsCount) {
//			if (taskIds != null && taskIds.size() > 0) {
//				priortyTask = taskAllocationRepo.getDrkTaskDataWithTask(getPriorityArray(priority2Data.getValue()), regionId, taskIds);
//			} else {
//				priortyTask = taskAllocationRepo.getDrkTaskData(getPriorityArray(priority2Data.getValue()), regionId);
//			}
//			if (priortyTask != null && priortyTask.size() > 0) {
//				priortyTask = sortedPriority(priortyTask, region);
//				taskAllocations.addAll(priortyTask);
//			}
//		}

//		if (taskAllocations.size() < flsCount) {
//			
//			if (taskIds != null && taskIds.size() > 0) {
//				priortyTask = taskAllocationRepo.getWillingDrkTaskDataWithTask(getPriorityArray(priority3Data.getValue()),
//						regionId, taskIds);
//			} else {
//				priortyTask = taskAllocationRepo.getWillingDrkTaskData(getPriorityArray(priority3Data.getValue()),
//						regionId);
//			}
//			if (priortyTask != null && priortyTask.size() > 0) {
//				priortyTask = sortedPriority(priortyTask, region);
//				taskAllocations.addAll(priortyTask);
//			}
//		}
		
		// priorityConditions
		return taskAllocations.stream().limit(flsCount).collect(Collectors.toList());
	}

	private List<Integer> getPriorityArray(String p) {
		return Stream.of(p.split(",")).map(String::trim).map(Integer::parseInt).collect(Collectors.toList());
	}

	private List<TaskAllocation> sortedPriority(List<TaskAllocation> priorityTask, Region region) {

		Map<Integer, List<TaskAllocation>> villageTaskMap = new HashMap<>();
		List<Map<Integer, Map<String, Double>>> villageMapList = new ArrayList<Map<Integer, Map<String, Double>>>();

		for (TaskAllocation task : priorityTask) {
			if (task.getEntityTypeId() == 4) {
				Farmer farmer = farmerRepo.findById(task.getEntityId()).get();
				Village village = villageRepo.findById(farmer.getVillageId()).get();
				if (villageTaskMap.containsKey(village.getId())) {
					villageTaskMap.get(village.getId()).add(task);
				} else {
					addVillageTask(village, task, villageTaskMap, villageMapList);
				}
			} else if (task.getEntityTypeId() == 1) {
				FarmCase farmcase = farmerCaseRepo.getBycaseID(task.getEntityId());
				FarmerFarm farmerFarm = farmerFarmRepo.findbyfarmId(farmcase.getFarmId());
				Farmer farmer = farmerRepo.findById(farmerFarm.getFarmerId()).get();
				Village village = villageRepo.findById(farmer.getVillageId()).get();
				if (villageTaskMap.containsKey(village.getId())) {
					villageTaskMap.get(village.getId()).add(task);
				} else {
					addVillageTask(village, task, villageTaskMap, villageMapList);
				}
			} else {
				System.out.println("Unknown task type");
			}
		}
		DistanceUtility distanceUtility = new DistanceUtility();
		List<Map<Integer, Map<String, Double>>> sortedlist = distanceUtility.getSortedVillageByDistance(
				region.getLatitude().doubleValue(), region.getLongitude().doubleValue(), villageMapList);
		return sortedlist.stream().flatMap(l -> l.keySet().stream()).flatMap(v -> villageTaskMap.get(v).stream())
				.collect(Collectors.toList());
	}

	private void addVillageTask(Village village, TaskAllocation task, Map<Integer, List<TaskAllocation>> villageTaskMap,
			List<Map<Integer, Map<String, Double>>> villageMapList) {
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
}
