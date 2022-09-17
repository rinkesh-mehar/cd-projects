package com.drkrishi.prm.service;

import com.drkrishi.prm.model.*;

import java.util.List;

public interface PR_Village_AssigmentService {
	
	public ResponseModel save_Village_AssigmentService(PRModel assigment);

	public List<PR_Village_Assigment> getPR_Village_AssigmentByPRMId(int prmId);

	public List<PR_Village_Assigment> getPR_Village_AllAssigmentByPRMId();

	public List<Villagetask> getAssignmentListByPrmId(int prmId);
	
	public List<Villagetask> getAssignmentListByPrmIdSearch(SearchPrmAssigntask search);

	public List<Integer> getAssignedVillageByWeek(int week, int year);
	
	public PrsEditResponseModel getAssignmentDetailsForEdit(int assgnmentId);

	public ResponseModel deleteAssignmentTask(int assgnmentId);

}
