package com.krishi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.krishi.model.EntityModel;

@Entity
@Table(name = "case_coordinates")
public class CaseCoordinates implements EntityModel {
	@Id
	@Column(name = "id")
	private String id;
	@Column(name = "access_points")
	private String accessPoints;

	@Column(name = "parking_point")
	private String parkingPoint;

	@Column(name = "entry_point")
	private String entryPoint;
	
	@Column(name = "case_id")
	private String caseId;


	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getAccessPoints() {
		return accessPoints;
	}

	public void setAccessPoints(String accessPoints) {
		this.accessPoints = accessPoints;
	}

	public String getParkingPoint() {
		return parkingPoint;
	}

	public void setParkingPoint(String parkingPoint) {
		this.parkingPoint = parkingPoint;
	}

	public String getEntryPoint() {
		return entryPoint;
	}

	public void setEntryPoint(String entryPoint) {
		this.entryPoint = entryPoint;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public CaseCoordinates() {
		super();
		// TODO Auto-generated constructor stub
	}
}
