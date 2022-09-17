package com.drk.tools.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AgriStressStage {
	private int Id;
	private int commodityId;
	private int startphenophaseId;
	private int endphenophaseId;
	private int stresstypeId;
	private int stressId;
	private String name;
	private String description;

	@Override
	public String toString() {
		return Id + "," + commodityId + "," + startphenophaseId + "," + endphenophaseId + "," + stresstypeId + ","
				+ stressId + ",'" + name + "','" + description + "'";
	}

}
