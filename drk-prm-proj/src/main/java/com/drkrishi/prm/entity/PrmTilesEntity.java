package com.drkrishi.prm.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="prs_tiles")
public class PrmTilesEntity implements Serializable {
	    @Id
	    @GeneratedValue(strategy=GenerationType.IDENTITY)
	    private int id;
	    @ManyToOne
		@JoinColumn(name="prs_assignment_id")
		private PR_Village_AssigmentEntity prsAssignmentId;
	    @Column(name="tiles_id")
	    private String tilesId;
	    @Column(name="status")
	    private int status;
	/*
	 * @Column(name="Created_Date") private Date createdDate;
	 * 
	 * @Column(name="Modified_Date") private Date ModifiedDate;
	 * 
	 * @Column(name="Created_by") private int CreatedBy;
	 * 
	 * @Column(name="Modified_by") private int ModifiedBy ;
	 */     
	    
	    public PrmTilesEntity() {
			super();
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public PR_Village_AssigmentEntity getPrsAssignmentId() {
			return prsAssignmentId;
		}

		public void setPrsAssignmentId(PR_Village_AssigmentEntity prsAssignmentId) {
			this.prsAssignmentId = prsAssignmentId;
		}

		public String getTilesId() {
			return tilesId;
		}

		public void setTilesId(String tilesId) {
			this.tilesId = tilesId;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		@Override
		public String toString() {
			return "PrmTilesEntity [id=" + id + ", prsAssignmentId=" + prsAssignmentId + ", tilesId=" + tilesId
					+ ", status=" + status + ", getId()=" + getId() + ", getPrsAssignmentId()=" + getPrsAssignmentId()
					+ ", getTilesId()=" + getTilesId() + ", getStatus()=" + getStatus() + ", getClass()=" + getClass()
					+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
		}
}