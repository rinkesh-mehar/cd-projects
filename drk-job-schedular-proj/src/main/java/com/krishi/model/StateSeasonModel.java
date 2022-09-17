package com.krishi.model;//package com.krishi.model;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.krishi.entity.StateSeason;
//
//@JsonIgnoreProperties(ignoreUnknown = true)
//public class StateSeasonModel {
//
//	@JsonProperty
//	private Integer ID;
//	@JsonProperty
//	private Integer StateCode;
//	@JsonProperty
//	private Integer SeasonID;
//	@JsonProperty
//	private Short StartWeek;
//	@JsonProperty
//	private Short EndWeek;
//	@JsonProperty
//	private String Status;
//
//	public StateSeasonModel() {
//		super();
//	}
//
//	public StateSeasonModel(Integer iD, Integer stateCode, Integer seasonID, Short startWeek, Short endWeek,
//			String status) {
//		super();
//		ID = iD;
//		StateCode = stateCode;
//		SeasonID = seasonID;
//		StartWeek = startWeek;
//		EndWeek = endWeek;
//		Status = status;
//	}
//
//	public Integer getID() {
//		return ID;
//	}
//
//	public void setID(Integer iD) {
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
//	public Integer getSeasonID() {
//		return SeasonID;
//	}
//
//	public void setSeasonID(Integer seasonID) {
//		SeasonID = seasonID;
//	}
//
//	public Short getStartWeek() {
//		return StartWeek;
//	}
//
//	public void setStartWeek(Short startWeek) {
//		StartWeek = startWeek;
//	}
//
//	public Short getEndWeek() {
//		return EndWeek;
//	}
//
//	public void setEndWeek(Short endWeek) {
//		EndWeek = endWeek;
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
//	public StateSeason getEntity() {
//		StateSeason stateSeason = new StateSeason();
//
//		stateSeason.setId(this.ID);
//		stateSeason.setStateId(StateCode);
//		stateSeason.setSeasonId(SeasonID);
//		stateSeason.setStartweek(StartWeek);
//		stateSeason.setEndweek(EndWeek);
//		stateSeason.setStatus(Status != null && Status.equalsIgnoreCase("Active") ? 1 : 0);
//		return stateSeason;
//	}
//}
