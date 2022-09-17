package com.krishi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LanguageModel {

	private static final long serialVersionUID = 1L;
	@JsonProperty
	private int ID;
	@JsonProperty
	private String Language;
	@JsonProperty
	private String Status;

	public LanguageModel() {
		super();
	}

	public LanguageModel(int iD, String language, String status) {
		super();
		ID = iD;
		Language = language;
		Status = status;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getLanguage() {
		return Language;
	}

	public void setLanguage(String language) {
		Language = language;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "LanguageModel [ID=" + ID + ", Language=" + Language + ", Status=" + Status + "]";
	}


	public com.krishi.entity.Language getEntity(){
		com.krishi.entity.Language entity = new com.krishi.entity.Language();
		entity.setId(ID);
		entity.setLanguage(Language);
		entity.setStatus(Status != null && Status.equalsIgnoreCase("Active") ? 1 : 0);
		return entity;
	}
}
