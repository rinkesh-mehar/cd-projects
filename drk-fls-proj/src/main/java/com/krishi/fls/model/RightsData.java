package com.krishi.fls.model;

import java.util.List;

import com.krishi.fls.entity.Rights;
import com.krishi.fls.entity.ViewRight;

public class RightsData {
	
	private List<Rights> rightsList;
	
	private List<ViewRight> viewRightList;

	public List<Rights> getRightsList() {
		return rightsList;
	}

	public void setRightsList(List<Rights> rightsList) {
		this.rightsList = rightsList;
	}

	public List<ViewRight> getViewRightList() {
		return viewRightList;
	}

	public void setViewRightList(List<ViewRight> viewRightList) {
		this.viewRightList = viewRightList;
	}
	

}
