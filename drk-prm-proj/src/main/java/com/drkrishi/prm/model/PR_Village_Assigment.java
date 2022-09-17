package com.drkrishi.prm.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class PR_Village_Assigment {

	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;

	private int assigment_Id;

	private Date created_Date;

	private int weekNumber;
	
	private int month;

	private int year;

	private DRKrishiUser drkrishiUser1;

	private DRKrishiUser drkrishiUser2;
private int status;

	public PR_Village_Assigment() {
	}

	public int getAssigment_Id() {
		return this.assigment_Id;
	}

	public void setAssigment_Id(int assigment_Id) {
		this.assigment_Id = assigment_Id;
	}

	public Date getCreated_Date() {
		return this.created_Date;
	}

	public void setCreated_Date(Date created_Date) {
		this.created_Date = created_Date;
	}

	public int getWeekNumber() {
		return this.weekNumber;
	}

	public void setWeekNumber(int weekNumber) {
		this.weekNumber = weekNumber;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return this.year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public DRKrishiUser getDrkrishiUser1() {
		return this.drkrishiUser1;
	}

	public void setDrkrishiUser1(DRKrishiUser drkrishiUser1) {
		this.drkrishiUser1 = drkrishiUser1;
	}

	public DRKrishiUser getDrkrishiUser2() {
		return this.drkrishiUser2;
	}

	public void setDrkrishiUser2(DRKrishiUser drkrishiUser2) {
		this.drkrishiUser2 = drkrishiUser2;
	}


	public Villagetask addVillagetask(Villagetask villagetask) {
		villagetask.setPrVillageAssigment(this);

		return villagetask;
	}

	public Villagetask removeVillagetask(Villagetask villagetask) {
		villagetask.setPrVillageAssigment(null);

		return villagetask;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + assigment_Id;
		result = prime * result + ((created_Date == null) ? 0 : created_Date.hashCode());
		result = prime * result + ((drkrishiUser1 == null) ? 0 : drkrishiUser1.hashCode());
		result = prime * result + ((drkrishiUser2 == null) ? 0 : drkrishiUser2.hashCode());
		result = prime * result + weekNumber;
		result = prime * result + month;
		result = prime * result + year;
		result = prime * result + status;
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
		PR_Village_Assigment other = (PR_Village_Assigment) obj;
		if (assigment_Id != other.assigment_Id)
			return false;
		if (created_Date == null) {
			if (other.created_Date != null)
				return false;
		} else if (!created_Date.equals(other.created_Date))
			return false;
		if (drkrishiUser1 == null) {
			if (other.drkrishiUser1 != null)
				return false;
		} else if (!drkrishiUser1.equals(other.drkrishiUser1))
			return false;
		if (drkrishiUser2 == null) {
			if (other.drkrishiUser2 != null)
				return false;
		} else if (!drkrishiUser2.equals(other.drkrishiUser2))
			return false;
		if (weekNumber != other.weekNumber)
			return false;
		if (month != other.month)
			return false;
		if (year != other.year)
			return false;
		if(status!=1)
			return false;
		return true;
	}

	

	@Override
	public String toString() {
		return "PR_Village_Assigment [assigment_Id=" + assigment_Id + ", created_Date=" + created_Date + ", weekNumber="
				+ weekNumber + ", month=" + month + ", year=" + year + ", drkrishiUser1=" + drkrishiUser1
				+ ", drkrishiUser2=" + drkrishiUser2 + ", status=" + status + ", getAssigment_Id()=" + getAssigment_Id()
				+ ", getCreated_Date()=" + getCreated_Date() + ", getWeekNumber()=" + getWeekNumber() + ", getMonth()="
				+ getMonth() + ", getYear()=" + getYear() + ", getStatus()=" + getStatus() + ", getDrkrishiUser1()="
				+ getDrkrishiUser1() + ", getDrkrishiUser2()=" + getDrkrishiUser2() + ", hashCode()=" + hashCode()
				+ ", getClass()=" + getClass() + ", toString()=" + super.toString() + "]";
	}

	public PR_Village_Assigment(int assigment_Id, Date created_Date, int weekNumber, int month, int year,
			DRKrishiUser drkrishiUser1, DRKrishiUser drkrishiUser2) {
		super();
		this.assigment_Id = assigment_Id;
		this.created_Date = created_Date;
		this.weekNumber = weekNumber;
		this.month = month;
		this.year = year;
		this.drkrishiUser1 = drkrishiUser1;
		this.drkrishiUser2 = drkrishiUser2;
	}


	
	
}