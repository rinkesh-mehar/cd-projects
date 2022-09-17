package com.krishi.fls.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "state_season_commodity")
public class StateSeasonCommodity implements Serializable {

	@Id
	private int id;

	@Column(name = "state_season_id")
	private int stateSeasonId;

	@Column(name = "commodity_id")
	private int commodityId;

	@Column(name = "comment")
	private String comment;

	@Column(name = "status")
	private int status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getStateSeasonId() {
		return stateSeasonId;
	}

	public void setStateSeasonId(int stateSeasonId) {
		this.stateSeasonId = stateSeasonId;
	}

	public int getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(int commodityId) {
		this.commodityId = commodityId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
