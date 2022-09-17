package com.drk.tools.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GeoState {
	private int Id;
	private int stateCode;
	private String name;

	@Override
	public String toString() {
		return Id + ", " + stateCode + ", '" + name + "'";
	}

}
