package com.drkrishi.prm.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class Villagetask {

	private int task_Id;

	private Date completedDate;

	private int isCompleted;

	private Village village;

	private PR_Village_Assigment prVillageAssigment;

	public Villagetask() {
	}

	public int getTask_Id() {
		return this.task_Id;
	}

	public void setTask_Id(int task_Id) {
		this.task_Id = task_Id;
	}

	public Date getCompletedDate() {
		return this.completedDate;
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}

	public int getIsCompleted() {
		return this.isCompleted;
	}

	public void setIsCompleted(int isCompleted) {
		this.isCompleted = isCompleted;
	}

	public Village getVillagetask() {
		return village;
	}

	public void setVillage(Village village) {
		this.village = village;
	}

	public PR_Village_Assigment getPrVillageAssigment() {
		return this.prVillageAssigment;
	}

	public void setPrVillageAssigment(PR_Village_Assigment prVillageAssigment) {
		this.prVillageAssigment = prVillageAssigment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((completedDate == null) ? 0 : completedDate.hashCode());
		result = prime * result + isCompleted;
		result = prime * result + ((prVillageAssigment == null) ? 0 : prVillageAssigment.hashCode());
		result = prime * result + task_Id;
		result = prime * result + ((village == null) ? 0 : village.hashCode());
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
		Villagetask other = (Villagetask) obj;
		if (completedDate == null) {
			if (other.completedDate != null)
				return false;
		} else if (!completedDate.equals(other.completedDate))
			return false;
		if (isCompleted != other.isCompleted)
			return false;
		if (prVillageAssigment == null) {
			if (other.prVillageAssigment != null)
				return false;
		} else if (!prVillageAssigment.equals(other.prVillageAssigment))
			return false;
		if (task_Id != other.task_Id)
			return false;
		if (village == null) {
			if (other.village != null)
				return false;
		} else if (!village.equals(other.village))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Villagetask [task_Id=" + task_Id + ", completedDate=" + completedDate + ", isCompleted=" + isCompleted
				+ ", village=" + village + ", prVillageAssigment=" + prVillageAssigment + "]";
	}

	public Villagetask(int task_Id, Date completedDate, int isCompleted, Village village,
			PR_Village_Assigment prVillageAssigment) {
		super();
		this.task_Id = task_Id;
		this.completedDate = completedDate;
		this.isCompleted = isCompleted;
		this.village = village;
		this.prVillageAssigment = prVillageAssigment;
	}
	


}