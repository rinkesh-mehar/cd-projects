package com.drk.tools.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AgriVariety {

	private int Id;
	private int commodityID;
	private String name;
	private String varietyCode;

	@Override
	public String toString() {
		return Id + ","+commodityID + "," + "'" + name + "','" + varietyCode + "'";
	}
}
