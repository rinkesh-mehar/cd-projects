/**
 * 
 */
package in.cropdata.cdtmasterdata.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

/**
 * @author cropdata-Aniket Naik
 *
 */
@Data
@Entity(name = "general_bank_branch_missing")
public class GeneralBankBranchMissing {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "BankID ")
	private int bankId;
	
	@Transient
	private String bank;
	
	@Column(name = "Name")
	private String  name;

	@Column(name = "IFSCCode ")
	private String ifscCode ;
	
	@Column(name = "DistrictCode")
	private Integer districtCode;

//	@Column(name = "UpdatedAt")
	@Transient
	private Date updatedAt;
	
//	@Column(name = "CreatedAt")
	@Transient
	private Date createdAt;
	
	@Column(name = "Status")
	private String status;
	

}
