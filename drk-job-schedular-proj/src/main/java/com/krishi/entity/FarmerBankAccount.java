package com.krishi.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.krishi.model.EntityModel;

@Entity
@Table(name = "farmer_bank_account")
public class FarmerBankAccount implements EntityModel {

	private static final SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
	@Id
	@Column(name = "id")
	private String id;
	/*@Column(name = "farmer_id")
	private String farmerId;*/
	@Column(name = "account_name")
	private String accountName;
	@Column(name = "bank_name")
	private String bankName;
	@Column(name = "acc_number")
	private String accNumber;
	@Column(name = "branch_name")
	private String branchName;
	@Column(name = "ifsc")
	private String ifsc;
	@Column(name = "is_pennydropped")
	private Boolean isPennydropped;
	@Column(name = "cms_reference_no")
	private String cmsReferenceNo;
	@Column(name = "pennydrop_status")
	private String pennydropStatus;
	@Column(name = "pennydrop_date")
	private java.util.Date pennydropDate;
	@Column(name = "pennydrop_remarks")
	private String pennydropRemarks;
	@Column(name = "dummy")
	private String pennydropDummy;
	@Column(name = "imps_status")
	private String impsStatus;
	@Column(name = "liquidation_date")
	private java.util.Date liquidationDate;
	
	/** added bank branch id - Ujwal : Start */
	@Column(name = "bank_branch_id")
	private Integer branchId;

	@Column(name = "passbook_image_url")
	private String passbookImageUrl;

	@Column(name = "cancelled_cheque_url")
	private String cancelledChequeUrl;

	@Column(name="case_id")
	private String caseId;
	
	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	/** added bank branch id - Ujwal : End */
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPennydropDummy() {
		return pennydropDummy;
	}

	public void setPennydropDummy(String pennydropDummy) {
		this.pennydropDummy = pennydropDummy;
	}

	public void setPennydropDate(java.util.Date pennydropDate) {
		this.pennydropDate = pennydropDate;
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

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccNumber() {
		return accNumber;
	}

	public void setAccNumber(String accNumber) {
		this.accNumber = accNumber;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getIfsc() {
		return ifsc;
	}

	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}

	public Boolean getIsPennydropped() {
		return isPennydropped;
	}

	public void setIsPennydropped(Boolean isPennydropped) {
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

	public java.util.Date getPennydropDate() {
		return pennydropDate;
	}

	public void setPennydropDate(String pennydropDate) {
		try {
			this.pennydropDate = sd.parse(pennydropDate);
		} catch (ParseException e) { // TODO
			e.printStackTrace();
		}
	}

	public String getPennydropRemarks() {
		return pennydropRemarks;
	}

	public void setPennydropRemarks(String pennydropRemarks) {
		this.pennydropRemarks = pennydropRemarks;
	}

	public String getImpsStatus() {
		return impsStatus;
	}

	public void setImpsStatus(String impsStatus) {
		this.impsStatus = impsStatus;
	}

	public java.util.Date getLiquidationDate() {
		return liquidationDate;
	}

	public void setLiquidationDate(java.util.Date liquidationDate) {
		this.liquidationDate = liquidationDate;
	}

	/*public String getFarmerId() {
		return farmerId;
	}

	public void setFarmerId(String farmerId) {
		this.farmerId = farmerId;
	}*/

	@Override
	public String toString() {
		return "FarmerBankAccount[" +
				"id='" + id + '\'' +
				", accountName='" + accountName + '\'' +
				", bankName='" + bankName + '\'' +
				", accNumber='" + accNumber + '\'' +
				", branchName='" + branchName + '\'' +
				", ifsc='" + ifsc + '\'' +
				", isPennydropped=" + isPennydropped +
				", cmsReferenceNo='" + cmsReferenceNo + '\'' +
				", pennydropStatus='" + pennydropStatus + '\'' +
				", pennydropDate=" + pennydropDate +
				", pennydropRemarks='" + pennydropRemarks + '\'' +
				", pennydropDummy='" + pennydropDummy + '\'' +
				", impsStatus='" + impsStatus + '\'' +
				", liquidationDate=" + liquidationDate +
				", branchId=" + branchId +
				", passbookImageUrl='" + passbookImageUrl + '\'' +
				", cancelledChequeUrl='" + cancelledChequeUrl + '\'' +
				", caseId='" + caseId + '\'' +
				']';
	}
}