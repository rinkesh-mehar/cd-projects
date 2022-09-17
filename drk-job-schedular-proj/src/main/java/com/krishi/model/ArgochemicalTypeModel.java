package com.krishi.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishi.entity.ArgochemicalType;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ArgochemicalTypeModel implements Serializable {
	
	
		
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		@JsonProperty
		private Integer ID;
		@JsonProperty
		private String Name;
		@JsonProperty
		private String Status;
		
		public ArgochemicalTypeModel(Integer iD, String name, String status) {
			super();
			ID = iD;
			Name = name;
			
			Status = status;
		}
		public ArgochemicalTypeModel() {
			super();
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
			return "ArgochemicalType [ID=" + ID + ", Name=" + Name +  ", Status="
					+ Status + "]";
		}
		
		
		public ArgochemicalType getEntity() {
			ArgochemicalType argochemicalType = new ArgochemicalType();
			argochemicalType.setId(ID);
			argochemicalType.setName(Name);
			argochemicalType.setStatus(Status != null && Status.equalsIgnoreCase("Active") ? 1 : 0);
			return argochemicalType;
		}

}
