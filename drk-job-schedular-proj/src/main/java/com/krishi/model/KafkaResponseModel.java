package com.krishi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/*Kafka Response*/
public class KafkaResponseModel {
	
	@JsonProperty
	private KafkaRightsModel data;

	public KafkaRightsModel getData() {
		return data;
	}

	public void setData(KafkaRightsModel data) {
		this.data = data;
	}



}
