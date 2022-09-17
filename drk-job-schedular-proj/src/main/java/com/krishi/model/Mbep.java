package com.krishi.model;

import java.util.List;

import com.krishi.entity.MbepEntity;

public class Mbep {

	private List<MbepEntity> mbepEntity;

	public List<MbepEntity> getMbepEntity() {
		return mbepEntity;
	}

	public void setMbepEntity(List<MbepEntity> mbepEntity) {
		this.mbepEntity = mbepEntity;
	}

	public boolean isNotNull() {
		if (mbepEntity != null) {
			return true;
		} else {
			return false;
		}

	}

}
