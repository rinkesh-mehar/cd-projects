package com.drkrishi.rlt.model;

public class Stress {

	private String id;
	private Integer stressId;
	private Integer desc;
	private String type;
	private ViewPosition left;
	private ViewPosition front;
	private ViewPosition right;
	private boolean status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getDesc() {
		return desc;
	}

	public void setDesc(Integer desc) {
		this.desc = desc;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ViewPosition getLeft() {
		return left;
	}

	public void setLeft(ViewPosition left) {
		this.left = left;
	}

	public ViewPosition getFront() {
		return front;
	}

	public void setFront(ViewPosition front) {
		this.front = front;
	}

	public ViewPosition getRight() {
		return right;
	}

	public void setRight(ViewPosition right) {
		this.right = right;
	}

	public Integer getStressId() {
		return stressId;
	}

	public void setStressId(Integer stressId) {
		this.stressId = stressId;
	}

	
	@Override
	public String toString() {
		return "Stress [ stressId=" + stressId + ", desc=" + desc
				+ ", type=" + type + ", left=" + left + ", front=" + front + ", right=" + right + ", status=" + status
				+ "]";
	}

}
