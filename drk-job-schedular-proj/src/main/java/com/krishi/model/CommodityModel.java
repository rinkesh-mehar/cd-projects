package com.krishi.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishi.entity.Commodity;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommodityModel  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

		@JsonProperty
		private Integer ID;
		@JsonProperty
		private String Name;
		@JsonProperty
		private String ScientificName;
		@JsonProperty
		private String Status;
		@JsonProperty
		private String IsFocusCrop;
		
		public CommodityModel() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		
		
		public CommodityModel(Integer iD, String name, String scientificName, String status, String isFocusCrop) {
			super();
			ID = iD;
			Name = name;
			ScientificName = scientificName;
			Status = status;
			IsFocusCrop = isFocusCrop;
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
		public String getScientificName() {
			return ScientificName;
		}
		public void setScientificName(String scientificName) {
			ScientificName = scientificName;
		}
		public String getIsFocusCrop() {
			return IsFocusCrop;
		}
		public void setIsFocusCrop(String isFocusCrop) {
			IsFocusCrop = isFocusCrop;
		}
		public String getStatus() {
			return Status;
		}
		public void setStatus(String status) {
			Status = status;
		}
		
		@Override
		public String toString() {
			return "CommodityModel [ID=" + ID + ", Name=" + Name + ", ScientificName=" + ScientificName + ", Status="
					+ Status + ", IsFocusCrop=" + IsFocusCrop + "]";
		}

		
		public Commodity getEntity() {
			Commodity commodityEntity = new Commodity();
			commodityEntity.setStatus(Status != null && Status.equalsIgnoreCase("Active") ? 1 : 0);
			commodityEntity.setId(ID);
			commodityEntity.setName(Name);
			commodityEntity.setIsFocusCrop(IsFocusCrop != null && IsFocusCrop.equalsIgnoreCase("Yes") ? 1 : 0);
			commodityEntity.setScientificName(ScientificName);
			return commodityEntity;
		}

}
