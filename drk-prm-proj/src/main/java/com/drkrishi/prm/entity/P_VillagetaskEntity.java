package com.drkrishi.prm.entity;

import javax.persistence.*;
import java.util.Date;



@Entity
@Table(name="prs_assignment")
public class P_VillagetaskEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name ="id")
	private int task_Id;

	@Temporal(TemporalType.DATE)
	@Column(name="completed_date")
	private Date completedDate;

	@Column(name="is_completed")
	private int isCompleted;

	@OneToOne
	@JoinColumn(name ="village_id")
	private VillageEntity villageEntity;

	//bi-directional many-to-one association to PR_Village_Assigment
	@ManyToOne
	@JoinColumn(name="prs_assignment_id")
	private PR_Village_AssigmentEntity prVillageAssigment;

	public P_VillagetaskEntity() {
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

	public VillageEntity getVillageEntity() {
		return villageEntity;
	}

	public void setVillageEntity(VillageEntity villageEntity) {
		this.villageEntity = villageEntity;
	}

	public PR_Village_AssigmentEntity getPrVillageAssigment() {
		return this.prVillageAssigment;
	}

	public void setPrVillageAssigment(PR_Village_AssigmentEntity prVillageAssigment) {
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
		result = prime * result + ((villageEntity == null) ? 0 : villageEntity.hashCode());
		return result;
	}

	

	@Override
	public String toString() {
		return "VillagetaskEntity [task_Id=" + task_Id + ", completedDate=" + completedDate + ", isCompleted="
				+ isCompleted + ", villageEntity=" + villageEntity + ", prVillageAssigment=" + prVillageAssigment + "]";
	}

	public P_VillagetaskEntity(int task_Id, Date completedDate, int isCompleted, VillageEntity villageEntity,
			PR_Village_AssigmentEntity prVillageAssigment) {
		super();
		this.task_Id = task_Id;
		this.completedDate = completedDate;
		this.isCompleted = isCompleted;
		this.villageEntity = villageEntity;
		this.prVillageAssigment = prVillageAssigment;
	}


	
}
