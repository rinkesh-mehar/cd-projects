package com.drkrishi.prm.model;

public class RelationShip {

	private int relationShip_id;

	private String relation_title;

	public RelationShip() {
	}

	public int getRelationShip_id() {
		return this.relationShip_id;
	}

	public void setRelationShip_id(int relationShip_id) {
		this.relationShip_id = relationShip_id;
	}

	public String getRelation_title() {
		return this.relation_title;
	}

	public void setRelation_title(String relation_title) {
		this.relation_title = relation_title;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + relationShip_id;
		result = prime * result + ((relation_title == null) ? 0 : relation_title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RelationShip other = (RelationShip) obj;
		if (relationShip_id != other.relationShip_id)
			return false;
		if (relation_title == null) {
			if (other.relation_title != null)
				return false;
		} else if (!relation_title.equals(other.relation_title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RelationShip [relationShip_id=" + relationShip_id + ", relation_title=" + relation_title + "]";
	}

	public RelationShip(int relationShip_id, String relation_title) {
		super();
		this.relationShip_id = relationShip_id;
		this.relation_title = relation_title;
	}

}