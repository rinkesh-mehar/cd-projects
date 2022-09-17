package com.drk.tools.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AgriStressType {
	private int Id;
	private String name;

	@Override
	public String toString() {
		return Id + ",'" + name + "'";
	}

}
