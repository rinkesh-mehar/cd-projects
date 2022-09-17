package com.drkrishi.rlt.dao;

import java.util.List;
import java.util.Set;

import com.drkrishi.rlt.entity.StressSeverity;
import com.drkrishi.rlt.entity.StressSymptoms;

public interface MasterDao {
	public List<Object> getMasterStressDetails();
	public List<StressSeverity> getMasterStressSeverity();
	public List<Object> getMasterStressId();
	public List<StressSymptoms> getMasterStressSymptoms(int stressId);
	public List<Object> getMasterStressDetailsByType(String type, String commodityId, Set<String> stressType);
}
