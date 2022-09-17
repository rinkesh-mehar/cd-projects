package com.krishi.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishi.entity.Argochemical;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ArgochemicalModel implements Serializable {
	
	
		
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
		@JsonProperty
		private Integer AgrochemicalTypeID;
		@JsonProperty
		private Integer StressTypeID;
		@JsonProperty
		private Integer WaitingPeriod;
	
		@JsonProperty
		private Integer CommodityID;
		

		
		
		
		
		public ArgochemicalModel() {
			super();
		}
		public ArgochemicalModel(Integer iD, String name, String status, Integer agrochemicalTypeID,
				Integer stressTypeID, Integer waitingPeriod ,Integer commodityID) {
			super();
			ID = iD;
			Name = name;
			Status = status;
			AgrochemicalTypeID = agrochemicalTypeID;
			StressTypeID = stressTypeID;
			WaitingPeriod = waitingPeriod;
			CommodityID = commodityID;
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
		public Integer getAgrochemicalTypeID() {
			return AgrochemicalTypeID;
		}
		public void setAgrochemicalTypeID(Integer agrochemicalTypeID) {
			AgrochemicalTypeID = agrochemicalTypeID;
		}
		public Integer getStressTypeID() {
			return StressTypeID;
		}
		public void setStressTypeID(Integer stressTypeID) {
			StressTypeID = stressTypeID;
		}
		public Integer getWaitingPeriod() {
			return WaitingPeriod;
		}
		public void setWaitingPeriod(Integer waitingPeriod) {
			WaitingPeriod = waitingPeriod;
		}
		
		
		public Integer getCommodityID() {
			return CommodityID;
		}
		public void setCommodityID(Integer commodityID) {
			CommodityID = commodityID;
		}
		@Override
		public String toString() {
			return "ArgochemicalModel [ID=" + ID + ", Name=" + Name + ", Status=" + Status + ", AgrochemicalTypeID="
					+ AgrochemicalTypeID + ", StressTypeID=" + StressTypeID + ", WaitingPeriod=" + WaitingPeriod
					+ ", CommodityID=" + CommodityID + "]";
		}
		
		
		
		public Argochemical getEntity() {
			Argochemical argochemical = new Argochemical();
			
			argochemical.setId(ID);
			argochemical.setAgrochemicalTypeID(AgrochemicalTypeID);
			argochemical.setCommodityID(CommodityID);
			argochemical.setStressTypeID(StressTypeID);
			argochemical.setWaitingPeriod(WaitingPeriod);
			argochemical.setName(Name);
			argochemical.setStatus(Status != null && Status.equalsIgnoreCase("Active") ? 1 : 0);
			
			return argochemical;
		}
}
