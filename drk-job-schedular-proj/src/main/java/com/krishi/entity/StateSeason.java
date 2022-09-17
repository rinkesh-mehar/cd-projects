package com.krishi.entity;//
//package com.krishi.entity;
//
//import java.io.Serializable;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//@Entity
//@Table(name = "state_season")
//public class StateSeason implements Serializable {
//
//	/** Primary key. */
//	protected static final String PK = "id";
//
//	@Id
//	@Column(unique = true, nullable = false, precision = 10)
//	private int id;
//	@Column(name = "state_id", nullable = false, precision = 10)
//	private int stateId;
//	@Column(name = "season_id", nullable = false, precision = 10)
//	private int seasonId;
//	@Column(precision = 3)
//	private Short startweek;
//	@Column(precision = 3)
//	private Short endweek;
//	@Column(length = 255)
//	private String comment;
//	@Column(precision = 10)
//	private Integer status;
//
//	/** Default constructor. */
//	public StateSeason() {
//		super();
//	}
//
//	/**
//	 * Access method for id.
//	 *
//	 * @return the current value of id
//	 */
//	public int getId() {
//		return id;
//	}
//
//	/**
//	 * Setter method for id.
//	 *
//	 * @param aId the new value for id
//	 */
//	public void setId(int aId) {
//		id = aId;
//	}
//
//	/**
//	 * Access method for stateId.
//	 *
//	 * @return the current value of stateId
//	 */
//	public int getStateId() {
//		return stateId;
//	}
//
//	/**
//	 * Setter method for stateId.
//	 *
//	 * @param aStateId the new value for stateId
//	 */
//	public void setStateId(int aStateId) {
//		stateId = aStateId;
//	}
//
//	/**
//	 * Access method for seasonId.
//	 *
//	 * @return the current value of seasonId
//	 */
//	public int getSeasonId() {
//		return seasonId;
//	}
//
//	/**
//	 * Setter method for seasonId.
//	 *
//	 * @param aSeasonId the new value for seasonId
//	 */
//	public void setSeasonId(int aSeasonId) {
//		seasonId = aSeasonId;
//	}
//
//	/**
//	 * Access method for startweek.
//	 *
//	 * @return the current value of startweek
//	 */
//
//	/**
//	 * Setter method for comment.
//	 *
//	 * @param aComment the new value for comment
//	 */
//	public void setComment(String aComment) {
//		comment = aComment;
//	}
//
//	public Short getStartweek() {
//		return startweek;
//	}
//
//	public void setStartweek(Short startweek) {
//		this.startweek = startweek;
//	}
//
//	public Short getEndweek() {
//		return endweek;
//	}
//
//	public void setEndweek(Short endweek) {
//		this.endweek = endweek;
//	}
//
//	public String getComment() {
//		return comment;
//	}
//
//	public void setStatus(Integer status) {
//		this.status = status;
//	}
//
//	/**
//	 * Access method for status.
//	 *
//	 * @return the current value of status
//	 */
//	public int getStatus() {
//		return status;
//	}
//
//	/**
//	 * Setter method for status.
//	 *
//	 * @param aStatus the new value for status
//	 */
//	public void setStatus(int aStatus) {
//		status = aStatus;
//	}
//
//	/**
//	 * Compares the key for this instance with another StateSeason.
//	 *
//	 * @param other The object to compare to
//	 * @return True if other object is instance of class StateSeason and the key
//	 *         objects are equal
//	 */
//	private boolean equalKeys(Object other) {
//		if (this == other) {
//			return true;
//		}
//		if (!(other instanceof StateSeason)) {
//			return false;
//		}
//		StateSeason that = (StateSeason) other;
//		if (this.getId() != that.getId()) {
//			return false;
//		}
//		return true;
//	}
//
//	/**
//	 * Compares this instance with another StateSeason.
//	 *
//	 * @param other The object to compare to
//	 * @return True if the objects are the same
//	 */
//	
//	/**
//	 * Returns a hash code for this instance.
//	 *
//	 * @return Hash code
//	 */
//	
//	/**
//	 * Returns a debug-friendly String representation of this instance.
//	 *
//	 * @return String representation of this instance
//	 */
//	@Override
//	public String toString() {
//		StringBuffer sb = new StringBuffer("[StateSeason |");
//		sb.append(" id=").append(getId());
//		sb.append("]");
//		return sb.toString();
//	}
//
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
//		result = prime * result + ((endweek == null) ? 0 : endweek.hashCode());
//		result = prime * result + id;
//		result = prime * result + seasonId;
//		result = prime * result + ((startweek == null) ? 0 : startweek.hashCode());
//		result = prime * result + stateId;
//		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
//		StateSeason other = (StateSeason) obj;
//		if (comment == null) {
//			if (other.comment != null)
//				return false;
//		} else if (!comment.equals(other.comment))
//			return false;
//		if (endweek == null) {
//			if (other.endweek != null)
//				return false;
//		} else if (!endweek.equals(other.endweek))
//			return false;
//		if (id != other.id)
//			return false;
//		if (seasonId != other.seasonId)
//			return false;
//		if (startweek == null) {
//			if (other.startweek != null)
//				return false;
//		} else if (!startweek.equals(other.startweek))
//			return false;
//		if (stateId != other.stateId)
//			return false;
//		if (status == null) {
//			if (other.status != null)
//				return false;
//		} else if (!status.equals(other.status))
//			return false;
//		return true;
//	}
//
//	/**
//	 * Return all elements of the primary key.
//	 *
//	 * @return Map of key names to values
//	 */
//	public Map<String, Object> getPrimaryKey() {
//		Map<String, Object> ret = new LinkedHashMap<String, Object>(6);
//		ret.put("id", Integer.valueOf(getId()));
//		return ret;
//	}
//
//}
