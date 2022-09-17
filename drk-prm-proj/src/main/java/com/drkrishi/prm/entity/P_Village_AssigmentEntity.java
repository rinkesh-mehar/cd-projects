package com.drkrishi.prm.entity;

import javax.persistence.*;
import java.util.Date;



@Entity
@Table(name="prs_assignment")
public class P_Village_AssigmentEntity {

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
	
	@Column(name="created_user_id")
	private int created_user_id;

	

	//bi-directional many-to-one association to DRKrishi_User
	@ManyToOne
	@JoinColumn(name="prs_Id")
	private DRKrishiUserEntity drkrishiUser2;

	/*
	 * //bi-directional many-to-one association to Villagetask
	 * 
	 * @OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL, orphanRemoval =
	 * true, mappedBy="prVillageAssigment") private List<VillagetaskEntity>
	 * villagetasks;
	 */

	public P_Village_AssigmentEntity() {
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


	public DRKrishiUserEntity getDrkrishiUser2() {
		return this.drkrishiUser2;
	}

	public void setDrkrishiUser2(DRKrishiUserEntity drkrishiUser2) {
		this.drkrishiUser2 = drkrishiUser2;
	}


	@Override
	public String toString() {
		return "PR_Village_AssigmentEntity [assigment_Id=" + assigment_Id + ", created_Date=" + created_Date
				+ ", weekNumber=" + weekNumber+ ", month=" + month + ", year=" + year + ", created_user_id=" + created_user_id
				+ ", drkrishiUser2=" + drkrishiUser2 + "]";
	}

	public P_Village_AssigmentEntity(int assigment_Id, Date created_Date, int weekNumber, int month, int year,
			int created_user_id, DRKrishiUserEntity drkrishiUser2) {
		
		this.assigment_Id = assigment_Id;
		this.created_Date = created_Date;
		this.weekNumber = weekNumber;
		this.month = month;
		this.year = year;
		this.created_user_id = created_user_id;
		this.drkrishiUser2 = drkrishiUser2;
	}

	public int getCreated_user_id() {
		return created_user_id;
	}

	public void setCreated_user_id(int created_user_id) {
		this.created_user_id = created_user_id;
	}
	

}
