package com.drkrishi.rlt.model;

import java.util.List;

import com.drkrishi.rlt.entity.CaseDetails;
import com.drkrishi.rlt.entity.TaskAerialPhoto;

public class CaseDetailsModel {
	
	private CaseDetails caseDetails;
	private List<TaskAerialPhoto> taskAerialPhotos;
	private String shapeFile;
	private MasterModel masterData;
	
	public CaseDetails getCaseDetails() {
		return caseDetails;
	}
	public void setCaseDetails(CaseDetails caseDetails) {
		this.caseDetails = caseDetails;
	}
	public List<TaskAerialPhoto> getTaskAerialPhotos() {
		return taskAerialPhotos;
	}
	public void setTaskAerialPhotos(List<TaskAerialPhoto> taskAerialPhotos) {
		this.taskAerialPhotos = taskAerialPhotos;
	}
	public String getShapeFile() {
		return shapeFile;
	}
	public void setShapeFile(String shapeFile) {
		this.shapeFile = shapeFile;
	}
	public MasterModel getMasterData() {
		return masterData;
	}
	public void setMasterData(MasterModel masterData) {
		this.masterData = masterData;
	}
	
}
