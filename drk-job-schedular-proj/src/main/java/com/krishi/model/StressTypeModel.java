package com.krishi.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishi.entity.StressType;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StressTypeModel  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty
	Integer ID;
	@JsonProperty
	String Name ;
	@JsonProperty
	String Status;
	
	
	
	
	public StressTypeModel() {
		super();
	}
	
	
	public StressTypeModel(Integer iD, String name, String status) {
		super();
		ID = iD;
		Name = name;
		Status = status;
	}


	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	@Override
	public String toString() {
		return "StressType [ID=" + ID + ", Name=" + Name + ", Status=" + Status + "]";
	}
	
	public StressType getEntity() {
		StressType stressType = new StressType();
		stressType.setId(ID);
		stressType.setName(Name);
		stressType.setStatus(Status != null && Status.equalsIgnoreCase("Active") ? 1 : 0);
		return stressType;
	}

}
