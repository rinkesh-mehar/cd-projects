package com.krishi.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.entity.ViewFieldMonitoringInfo;

public interface ViewFieldMonitoringInfoRepository extends JpaRepository<ViewFieldMonitoringInfo, Integer>{

	List<ViewFieldMonitoringInfo> findByTaskTypeIdAndTaskStatusAndAssigneeIdAndTaskDateLessThanEqual(Integer taskTypeId, Integer taskStatus, Integer assigneeId,
			Date taskDate);

}
