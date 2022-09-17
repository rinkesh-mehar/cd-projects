package com.drkrishi.rlt.dao;

import java.sql.Date;
import java.util.List;

import com.drkrishi.rlt.entity.Commodity;
import com.drkrishi.rlt.entity.TaskHistory;
import com.drkrishi.rlt.entity.Users;
import com.drkrishi.rlt.entity.ViewTaskHistoryInfo;
import com.drkrishi.rlt.entity.ViewTaskInfo;

public interface DiagnosisApprovalDao {
	
	public Users getUserById(Integer userId);
	
	public List<ViewTaskInfo> getRlmPendingRecords(Integer userId, Integer regionId, Date startDate, Date endDate, String barcode);
	
	public List<ViewTaskHistoryInfo> getRlmApprovedReassignRecords(Integer regionId, Date startDate, Date endDate, Integer currentTaskTypeId);
	
	public List<Commodity> getCommodityListByIds(List<Integer> commodityIdList) ;
	
	public TaskHistory getLatestTaskHistoryByTaskId(String taskId);
	
	public TaskHistory getFirstTaskHistoryByTaskId(String taskId);

	public Integer updateAssignee(String id, Integer assigneeId);

}
