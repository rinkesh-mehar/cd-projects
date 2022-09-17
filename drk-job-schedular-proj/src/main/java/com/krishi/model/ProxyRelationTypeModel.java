package com.krishi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishi.entity.ProxyRelationType;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProxyRelationTypeModel {
	@JsonProperty
	private Integer ID;
	@JsonProperty
	private String ProxyRelationType;
	@JsonProperty
	private String Status;

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public String getProxyRelationType() {
		return ProxyRelationType;
	}

	public void setProxyRelationType(String proxyRelationType) {
		ProxyRelationType = proxyRelationType;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public ProxyRelationType getEntity() {
		ProxyRelationType entity = new ProxyRelationType();
		entity.setId(ID);
		entity.setProxyRelationType(ProxyRelationType);
		entity.setStatus(Status != null && Status.equalsIgnoreCase("Active") ? 1 : 0);
		return entity;

	}
}
