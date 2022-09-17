package com.drkrishi.rlt.service;

import com.drkrishi.rlt.entity.Task;
import com.drkrishi.rlt.model.ResponseMessage;

public interface DiagnosisApprovalService {
	
	public ResponseMessage getDiagnosisApprovalList(Integer userId, String taskStatus, String startDate, String endDate, String barcode);

	public ResponseMessage updateAssignee(Task task);

}
