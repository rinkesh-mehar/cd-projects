package com.krishi.model;

import java.util.List;

import com.krishi.entity.CaseNdvi;

public class NdviImageModel {
	
	private String pathname;
	
	private List<CaseNdvi> images;

	public String getPathname() {
		return pathname;
	}

	public void setPathname(String pathname) {
		this.pathname = pathname;
	}

	public List<CaseNdvi> getImages() {
		return images;
	}

	public void setImages(List<CaseNdvi> images) {
		this.images = images;
	}
	
	

}
