package com.drk.tools.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GeoRegion {
	private int regionId;
	private int stateCode;
	private String name;
	private String description;

	@Override
	public String toString() {
		return regionId + "," + stateCode + ",'" + name + "','" + description + "'";
	}

}
