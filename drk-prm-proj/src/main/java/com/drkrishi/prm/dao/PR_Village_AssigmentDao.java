package com.drkrishi.prm.dao;

import com.drkrishi.prm.model.PRModel;
import com.drkrishi.prm.model.PR_Village_Assigment;
import com.drkrishi.prm.model.ResponseModel;
import com.drkrishi.prm.model.Villagetask;

import java.util.List;

public interface PR_Village_AssigmentDao {
	
	public ResponseModel save_Village_Assigment(PRModel assigment);

	public List<PR_Village_Assigment> getPR_Village_AssigmentByPRMId(int prmId);

	public List<PR_Village_Assigment> getPR_Village_AllAssigmentByPRMId();

	public List<Villagetask> getAssignmentListByPrmId(int prmId);

	public ResponseModel deletePrsAssignmentTask(int assgnmentId);

	
	
}
