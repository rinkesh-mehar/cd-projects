package com.drk.tools.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AgriStressName {
	private int Id;
	private int commodityId;
//	private int startphenophaseId;
	private int startDas;
//	private int endphenophaseId;
	private int endDas;
	private int stresstypeId;
	private String name;
//	private String scientificName;

	@Override
	public String toString() {
		return Id + "," + commodityId + "," + startDas + "," + endDas + "," + stresstypeId + ",'"
				+ name + /*"','" + scientificName +*/ "'";
	}

}
