package com.krishi.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishi.entity.ArgochemicalCompany;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ArgochemicalCompanyModel implements Serializable {
	
	
		
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		@JsonProperty
		private Integer ID;
		@JsonProperty
		private String Name;
		@JsonProperty
		private String Description;
		@JsonProperty
		private String Status;
		
		public ArgochemicalCompanyModel(Integer iD, String name, String description, String status) {
			super();
			ID = iD;
			Name = name;
			Description = description;
			Status = status;
		}
		public ArgochemicalCompanyModel() {
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
		public String getDescription() {
			return Description;
		}
		public void setDescription(String description) {
			Description = description;
		}
		public String getStatus() {
			return Status;
		}
		public void setStatus(String status) {
			Status = status;
		}
		
		@Override
		public String toString() {
			return "ArgochemicalCompany [ID=" + ID + ", Name=" + Name + ", Description=" + Description + ", Status="
					+ Status + "]";
		}
		
		public ArgochemicalCompany getEntity() {
		
			ArgochemicalCompany argochemicalCompany = new ArgochemicalCompany();
		
			argochemicalCompany.setId(ID);
			argochemicalCompany.setName(Name);
			argochemicalCompany.setStatus(Status != null && Status.equalsIgnoreCase("Active") ? 1 : 0);

			return argochemicalCompany;
			
		}
		
		

}
