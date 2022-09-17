package com.krishi.fls.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="farmer_bank_account")
public class BankDetails {

	private static final SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
	@Id
	@Column(name = "id")
	private String id;
	
	public BankDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	/*@Column(name="farmer_id")
	private String farmerId;
*/
	@Column(name="case_id")
	private String caseId;
	
	@Column(name="account_name")
	private String accountName;
	
	/*@Column(name="bank_name")
	private String bankName;*/
	
	@Column(name="acc_number")
	private String accNumber;
	
	/*@Column(name="branch_name")
	private String branchName;*/
	
	/*@Column(name="ifsc")
	private String ifsc;*/
	
	@Column(name="is_pennydropped")
	private Integer isPennydropped;
	
	@Column(name="cms_reference_no")
	private String cmsReferenceNo;
	
	@Column(name="pennydrop_status")
	private String pennydropStatus;
	
	@Column(name = "pennydrop_date")
	private Date pennydropDate;
	
	
	@Column(name="pennydrop_remarks")
	private String pennydropRemarks;
	
	@Column(name="liquidation_date")
	private Date liquidationDate;
	
	@Column(name="imps_status")
	private String impsStatus;
	
	/*@Column(name="dummy")
	private String dummy;*/

	@Column(name = "bank_branch_id")
	private Integer branchId;

	@Column(name = "passbook_image_url")
	private String passbookImageUrl;

	@Column(name = "cancelled_cheque_url")
	private String cancelledChequeUrl;
	


	public Integer getIsPennydropped() {
		return isPennydropped;
	}

	public void setIsPennydropped(Integer isPennydropped) {
		this.isPennydropped = isPennydropped;
	}

	public String getCmsReferenceNo() {
		return cmsReferenceNo;
	}

	public void setCmsReferenceNo(String cmsReferenceNo) {
		this.cmsReferenceNo = cmsReferenceNo;
	}

	public String getPennydropStatus() {
		return pennydropStatus;
	}

	public void setPennydropStatus(String pennydropStatus) {
		this.pennydropStatus = pennydropStatus;
	}

	public Date getPennydropDate() {
		return pennydropDate;
	}

	public void setPennydropDate(String pennydeopDate) {
		try {
			this.pennydropDate = sd.parse(pennydeopDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getPennydropRemarks() {
		return pennydropRemarks;
	}

	public void setPennydropRemarks(String pennydropRemarks) {
		this.pennydropRemarks = pennydropRemarks;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}


	public String getAccNumber() {
		return accNumber;
	}

	public void setAccNumber(String accNumber) {
		this.accNumber = accNumber;
	}



	public Date getLiquidationDate() {
		return liquidationDate;
	}

	public void setLiquidationDate(Date liquidationDate) {
		this.liquidationDate = liquidationDate;
	}

	public String getImpsStatus() {
		return impsStatus;
	}

	public void setImpsStatus(String impsStatus) {
		this.impsStatus = impsStatus;
	}


	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getPassbookImageUrl() {
		return passbookImageUrl;
	}

	public void setPassbookImageUrl(String passbookImageUrl) {
		this.passbookImageUrl = passbookImageUrl;
	}

	public String getCancelledChequeUrl() {
		return cancelledChequeUrl;
	}

	public void setCancelledChequeUrl(String cancelledChequeUrl) {
		this.cancelledChequeUrl = cancelledChequeUrl;
	}

	public BankDetails(String id, String caseId, String accountName, Integer ispennyDropped, String passbookImageUrl, String cancelledChequeUrl,
					   String cmsReferenceNo, String pennydropStatus, Date pennydeopDate, String pennydropRemarks, Integer branchId) {
		super();
		this.id = id;
		this.caseId = caseId;
		this.accountName = accountName;
		this.isPennydropped = ispennyDropped;
		this.cmsReferenceNo = cmsReferenceNo;
		this.pennydropStatus = pennydropStatus;
		this.pennydropDate = pennydeopDate;
		this.pennydropRemarks = pennydropRemarks;
		this.branchId = branchId;
		this.passbookImageUrl = passbookImageUrl;
		this.cancelledChequeUrl = cancelledChequeUrl;
	}
	
	
}