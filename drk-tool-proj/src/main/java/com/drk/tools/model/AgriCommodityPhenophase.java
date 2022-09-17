package com.drk.tools.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AgriCommodityPhenophase {

	private int id;
	private int commodityId;
	private int phenophaseId;
	private String status;
	private Date createdAt;
	private Date updatedAt;

	@Override
	public String toString() {
		return id + ", " + commodityId + "," + phenophaseId + ", " + "'" + status + "'," + createdAt + "," + updatedAt
				+ " ";
	}

}
