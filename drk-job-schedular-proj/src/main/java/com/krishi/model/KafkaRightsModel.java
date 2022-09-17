package com.krishi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
/*Kafka response*/
public class KafkaRightsModel {
	
	@JsonProperty
	private String rightsId;
	@JsonProperty
	private String errorCode;
	@JsonProperty
	private Integer dueSettled;
	@JsonProperty
	private String lotId;
	@JsonProperty
	private Double settlementAmount;
	@JsonProperty
	private Double amountCollected;
	
	
	public Double getSettlementAmount() {
		return settlementAmount;
	}
	public void setSettlementAmount(Double settlementAmount) {
		this.settlementAmount = settlementAmount;
	}
	public String getRightsId() {
		return rightsId;
	}
	public void setRightsId(String rightsId) {
		this.rightsId = rightsId;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public Integer getDueSettled() {
		return dueSettled;
	}
	public void setDueSettled(Integer dueSettled) {
		this.dueSettled = dueSettled;
	}
	public String getLotId() {
		return lotId;
	}
	public void setLotId(String lotId) {
		this.lotId = lotId;
	}
	public Double getAmountCollected() {
		return amountCollected;
	}
	public void setAmountCollected(Double amountCollected) {
		this.amountCollected = amountCollected;
	}

}
