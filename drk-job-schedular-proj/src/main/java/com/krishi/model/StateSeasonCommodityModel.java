package com.krishi.model;//package com.krishi.model;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//import org.springframework.stereotype.Repository;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.krishi.entity.HealthQuestionnaire;
//import com.krishi.entity.StateSeasonCommodity;
//import com.krishi.entity.Stress;
//import com.krishi.repository.StateSeasonRepository;
//
//@JsonIgnoreProperties(ignoreUnknown = true)
//public class StateSeasonCommodityModel {
//	@JsonProperty
//	private int ID;
//	@JsonProperty
//	private Integer StateCode;
//	@JsonProperty
//	private Integer RegionID;
//	@JsonProperty
//	private Integer SeasonID;
//	@JsonProperty
//	private Integer CommodityID;
//	@JsonProperty
//	private String Status;
//
//	@PersistenceContext
//	private EntityManager em;
//
//	StateSeasonRepository stateSeason;
//
//	public int getID() {
//		return ID;
//	}
//
//	public void setID(int iD) {
//		ID = iD;
//	}
//
//	public Integer getStateCode() {
//		return StateCode;
//	}
//
//	public void setStateCode(Integer stateCode) {
//		StateCode = stateCode;
//	}
//
//	public Integer getRegionID() {
//		return RegionID;
//	}
//
//	public void setRegionID(Integer regionID) {
//		RegionID = regionID;
//	}
//
//	public Integer getSeasonID() {
//		return SeasonID;
//	}
//
//	public void setSeasonID(Integer seasonID) {
//		SeasonID = seasonID;
//	}
//
//	public Integer getCommodityID() {
//		return CommodityID;
//	}
//
//	public void setCommodityID(Integer commodityID) {
//		CommodityID = commodityID;
//	}
//
//	public String getStatus() {
//		return Status;
//	}
//
//	public void setStatus(String status) {
//		Status = status;
//	}
//
//	public StateSeasonCommodity getEntity() {
//		StateSeasonCommodity entity = new StateSeasonCommodity();
//
//		entity.setId(ID);
//		entity.setStateId(StateCode);
//		entity.setStateSeasonId(SeasonID);
//		entity.setCommodityId(CommodityID);
//		entity.setStatus(Status != null && Status.equalsIgnoreCase("Active") ? 1 : 0);
//		return entity;
//	}
//
//}
