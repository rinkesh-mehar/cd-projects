package com.krishi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishi.entity.Stress;
import com.krishi.entity.StressControlMeasure;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StressControlMeasureModel {

	
	@JsonProperty
	  private Integer ID;
	@JsonProperty
	  private String Name;
	@JsonProperty
	  private String Status;
	public float getID()
	{
		return ID;
	}
	public void setID(Integer iD)
	{
		ID = iD;
	}
	public String getName()
	{
		return Name;
	}
	public void setName(String name)
	{
		Name = name;
	}
	public String getStatus()
	{
		return Status;
	}
	public void setStatus(String status)
	{
		Status = status;
	}
	
	public StressControlMeasure getEntity() {
	
		StressControlMeasure entity= new StressControlMeasure();
		entity.setId(ID);
		entity.setName(Name);
		entity.setStatus(Status != null && Status.equalsIgnoreCase("Active") ? 1 : 0);
		
		return entity;
	}

	  
	  
}