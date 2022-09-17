package in.cropdata.cdtmasterdata.acl.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity(name = "acl_history")
@Data
public class AclHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@Column(name = "TableName")
	private String tableName;
	
	@Column(name = "RowID")
	private int rowId;
	
	@Column(name = "PrimaryApproverID")
	private int primaryApproverId;
	
	@Column(name = "PrimaryApprovalStatus")
	private String primaryApprovalStatus;
	
	@Column(name = "PrimaryApprovedAt")
	private Date primaryApprovedAt;
	
	@Column(name = "FinalApproverID")
	private int finalApproverId;
	
	@Column(name = "FinalApprovalStatus")
	private String finalApprovalStatus;
	
	@Column(name ="FinalApprovedAt")
	private Date finalApprovedAt;

	@Column(name = "CreatedBy")
	private int createdBy;
	
	@Column(name = "CreatedAt")
	private Date createdAt;
	
	@Column(name = "UpdatedBy")
	private int updatedBy;
	
	@Column(name = "UpdatedAt")
	private Date updatedAt;

}
