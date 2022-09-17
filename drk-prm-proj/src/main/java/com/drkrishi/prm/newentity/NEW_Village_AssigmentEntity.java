package com.drkrishi.prm.newentity;

import com.drkrishi.prm.entity.DRKrishiUserEntity;

import javax.persistence.*;
import java.util.Date;





@Entity
@Table(name="prs_assignment")
public class NEW_Village_AssigmentEntity {
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int assigment_Id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Created_Date")
	private Date created_Date;

	@Column(name="week_number")
	private int weekNumber;
	
	
	@Column(name="month")
	private int month;

	private int year;

	//bi-directional many-to-one association to DRKrishi_User
	
	@Column(name="Created_User_Id")
	private int drkrishiUser1;

	//bi-directional many-to-one association to DRKrishi_User
	@ManyToOne
	@JoinColumn(name="prs_Id")
	private DRKrishiUserEntity drkrishiUser2;
	private Integer status;

	public NEW_Village_AssigmentEntity() {
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

	public int getDrkrishiUser1() {
		return this.drkrishiUser1;
	}

	public void setDrkrishiUser1(int drkrishiUser1) {
		this.drkrishiUser1 = drkrishiUser1;
	}

	public DRKrishiUserEntity getDrkrishiUser2() {
		return this.drkrishiUser2;
	}

	public void setDrkrishiUser2(DRKrishiUserEntity drkrishiUser2) {
		this.drkrishiUser2 = drkrishiUser2;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + assigment_Id;
		result = prime * result + ((created_Date == null) ? 0 : created_Date.hashCode());		
		result = prime * result + ((drkrishiUser2 == null) ? 0 : drkrishiUser2.hashCode());
	//	result = prime * result + ((villagetasks == null) ? 0 : villagetasks.hashCode());
		result = prime * result + weekNumber;
		result = prime * result + month;
		result = prime * result + year;
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
		NEW_Village_AssigmentEntity other = (NEW_Village_AssigmentEntity) obj;
		if (assigment_Id != other.assigment_Id)
			return false;
		if (created_Date == null) {
			if (other.created_Date != null)
				return false;
		} else if (!created_Date.equals(other.created_Date))
			return false;		
		if (drkrishiUser2 == null) {
			if (other.drkrishiUser2 != null)
				return false;
		} else if (!drkrishiUser2.equals(other.drkrishiUser2))
			return false;
		/*
		 * if (villagetasks == null) { if (other.villagetasks != null) return false; }
		 * else if (!villagetasks.equals(other.villagetasks)) return false;
		 */
		if (weekNumber != other.weekNumber)
			return false;
		if (month != other.month)
			return false;
		if (year != other.year)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PR_Village_AssigmentEntity [assigment_Id=" + assigment_Id + ", created_Date=" + created_Date
				+ ", weekNumber=" + weekNumber+ ", month=" + month + ", year=" + year + ", drkrishiUser1=" + drkrishiUser1
				+ ", drkrishiUser2=" + drkrishiUser2 + "]";
	}

	public NEW_Village_AssigmentEntity(int assigment_Id, Date created_Date, int weekNumber, int month, int year,
			int drkrishiUser1, DRKrishiUserEntity drkrishiUser2) {
		
		this.assigment_Id = assigment_Id;
		this.created_Date = created_Date;
		this.weekNumber = weekNumber;
		this.month = month;
		this.year = year;
		this.drkrishiUser1 = drkrishiUser1;
		this.drkrishiUser2 = drkrishiUser2;
	}

}
