package com.drk.tools.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AgriCommodity {

	private int Id;
	private String name;
	private String scientificName;

	@Override
	public String toString() {
		return Id + "," + "'" + name + "','" + scientificName + "'";
	}

}
