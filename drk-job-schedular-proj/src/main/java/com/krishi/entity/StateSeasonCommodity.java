package com.krishi.entity;//package com.krishi.entity;
//
//import java.io.Serializable;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Transient;
//
//@Entity(name = "state_season_commodity")
//public class StateSeasonCommodity implements Serializable {
//
//	/** Primary key. */
//	protected static final String PK = "id";
//
//	@Id
//	@Column(unique = true, nullable = false, precision = 10)
//	private int id;
//
//	@Column(name = "state_season_id")
//	private int stateSeasonId;
//
//	@Column(name = "commodity_id")
//	private int commodityId;
//
//	@Column(name = "comment")
//	private String comment;
//
//	@Column(name = "status")
//	private int status;
//
//	@Transient
//	private Integer stateId;
//
//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}
//
//	public String getComment() {
//		return comment;
//	}
//
//	public void setComment(String comment) {
//		this.comment = comment;
//	}
//
//	public int getStateSeasonId() {
//		return stateSeasonId;
//	}
//
//	public void setStateSeasonId(int stateSeasonId) {
//		this.stateSeasonId = stateSeasonId;
//	}
//
//	public int getCommodityId() {
//		return commodityId;
//	}
//
//	public void setCommodityId(int commodityId) {
//		this.commodityId = commodityId;
//	}
//
//	public int getStatus() {
//		return status;
//	}
//
//	public void setStatus(int status) {
//		this.status = status;
//	}
//
//	public static String getPk() {
//		return PK;
//	}
//
//	public Integer getStateId() {
//		return stateId;
//	}
//
//	public void setStateId(Integer stateId) {
//		this.stateId = stateId;
//	}
//
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
//		result = prime * result + commodityId;
//		result = prime * result + id;
//		result = prime * result + ((stateId == null) ? 0 : stateId.hashCode());
//		result = prime * result + stateSeasonId;
//		result = prime * result + status;
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		StateSeasonCommodity other = (StateSeasonCommodity) obj;
//		if (comment == null) {
//			if (other.comment != null)
//				return false;
//		} else if (!comment.equals(other.comment))
//			return false;
//		if (commodityId != other.commodityId)
//			return false;
//		if (id != other.id)
//			return false;
//		if (stateId == null) {
//			if (other.stateId != null)
//				return false;
//		} else if (!stateId.equals(other.stateId))
//			return false;
//		if (stateSeasonId != other.stateSeasonId)
//			return false;
//		if (status != other.status)
//			return false;
//		return true;
//	}
//
//}
