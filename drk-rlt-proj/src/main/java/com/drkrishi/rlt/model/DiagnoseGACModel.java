package com.drkrishi.rlt.model;

import java.util.List;

public class DiagnoseGACModel {

	private GeneralInformation generalInformation;
	private String rlAerialImagePath;
	private List<String> ariealPhotos;
	private List<Comment> comments;

	public GeneralInformation getGeneralInformation() {
		return generalInformation;
	}
	public void setGeneralInformation(GeneralInformation generalInformation) {
		this.generalInformation = generalInformation;
	}
	public String getRlAerialImagePath() {
		return rlAerialImagePath;
	}
	public void setRlAerialImagePath(String rlAerialImagePath) {
		this.rlAerialImagePath = rlAerialImagePath;
	}
	public List<String> getAriealPhotos() {
		return ariealPhotos;
	}
	public void setAriealPhotos(List<String> ariealPhotos) {
		this.ariealPhotos = ariealPhotos;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
}
