package com.krishi.fls.dao;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.krishi.fls.entity.CaseType;
import com.krishi.fls.entity.District;
import com.krishi.fls.entity.Farmer;
import com.krishi.fls.entity.Panchayat;
import com.krishi.fls.entity.Poi;
import com.krishi.fls.entity.Region;
import com.krishi.fls.entity.State;
import com.krishi.fls.entity.TaskAllocation;
import com.krishi.fls.entity.TaskType;
import com.krishi.fls.entity.Tehsil;
import com.krishi.fls.entity.Village;
import com.krishi.fls.model.HistoricalData;

@Component
@Repository
public interface FlsDao {

	public List<Farmer> getFlsTaskData(int regionId);

	public Village findVillageById(int villageId);

	public Region findRegionById(int regionId);

	public State findStateById(int stateId);

	public List<TaskAllocation> getTaskAllocation(int regionId);

	public void updateTaskAllocation(List<TaskAllocation> allocations);

	Farmer getFarmer(String id);

	public List<TaskType> tasktype();

	public List<CaseType> casetype();

	public Set<Poi> findPoiByVillageIds(List<Integer> villageIds);

	public String findStaticValueByKey(String key);
	
	public HistoricalData getHistorydata(Integer userId);
	
	public List<TaskAllocation> getTask(Integer userId, Integer regionId);
}
