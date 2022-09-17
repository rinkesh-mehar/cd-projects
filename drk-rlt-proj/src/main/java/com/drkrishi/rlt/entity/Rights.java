package com.drkrishi.rlt.entity;



import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="rights")
public class Rights {

	@Id
	@Column(name="id")
	private String id;
	
	public Rights() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Column(name="version_number")
	private int versionNumber;
	
	@Column(name="case_id")
	private String caseId;
	
	@Column(name="current_quantity")
	private Float currentQuantity;
	
	@Column(name="estimated_quantity")
	private Float estimatedQuantity;
	
	@Column(name="standard_quantity")
	private Float standardQuantity;
	
	@Column(name="allowable_variance_qty_pos")
	private Float allowableVarianceQtyPos;
	
	@Column(name="allowable_variance_qty_neg")
	private Float allowableVarianceQtyNeg;
	
	@Column(name="allowable_variance_qty_pos_per")
	private Float allowableVarianceQtyPosPer;
	
	@Column(name="allowable_variance_qty_neg_per")
	private Float allowableVarianceQtyNegPer;
	
	@Column(name="estimated_quality")
	private String estimatedQuality;
	
	@Column(name="current_quality")
	private String currentQuality;
	
	@Column(name="allowable_variance_quality")
	private String allowableVarianceQuality;
	
	@Column(name="mbep")
	private double mbep;
	
	@Column(name="domestic_restriction")
	private String domesticRestriction;
	
	@Column(name="international_restriction")
	private String internationalRestriction;
	
	@Column(name="delivery_date")
	private Date deliveryDateTime;
	
	@Column(name="logistic_hub_id")
	private String logisticHubId;
	
	@Column(name="logistic_hubAddress")
	private String logisticHubAddress;
	
	@Column(name="farmer_default")
	private String farmerDefault;
	
	@Column(name="split_field")
	private String splitField;
	
	@Column(name="geographically_adjustent")
	private String geographicallyAdjustent;
	
	@Column(name="risk_report")
	private String riskReport;
	
	@Column(name="right_certificate")
	private String rightCertificate;
	
	@Column(name="record_created_by")
	private int recordCreatedBy;
	
	@Column(name="record_date_time")
	private Date recordDateTime;
	
	@Column(name="status")
	private String status;
	
	@Column(name="comments")
	private String comments;
	
	@Column(name="lot_id")
	private String lotId;

	@Column(name="is_verified")
	private boolean isVerified;
	
	@Column(name="transaction_id")
	private String transactionId;
	
	@Column(name="phenophase_id")
	private int phenophaseId;
	
	@Column(name="created_date")
	private Date createdDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(int versionNumber) {
		this.versionNumber = versionNumber;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public Float getCurrentQuantity() {
		return currentQuantity;
	}

	public void setCurrentQuantity(Float currentQuantity) {
		this.currentQuantity = currentQuantity;
	}

	public Float getEstimatedQuantity() {
		return estimatedQuantity;
	}

	public void setEstimatedQuantity(Float estimatedQuantity) {
		this.estimatedQuantity = estimatedQuantity;
	}

	public Float getStandardQuantity() {
		return standardQuantity;
	}

	public void setStandardQuantity(Float standardQuantity) {
		this.standardQuantity = standardQuantity;
	}

	public Float getAllowableVarianceQtyPos() {
		return allowableVarianceQtyPos;
	}

	public void setAllowableVarianceQtyPos(Float allowableVarianceQtyPos) {
		this.allowableVarianceQtyPos = allowableVarianceQtyPos;
	}

	public Float getAllowableVarianceQtyNeg() {
		return allowableVarianceQtyNeg;
	}

	public void setAllowableVarianceQtyNeg(Float allowableVarianceQtyNeg) {
		this.allowableVarianceQtyNeg = allowableVarianceQtyNeg;
	}

	public Float getAllowableVarianceQtyPosPer() {
		return allowableVarianceQtyPosPer;
	}

	public void setAllowableVarianceQtyPosPer(Float allowableVarianceQtyPosPer) {
		this.allowableVarianceQtyPosPer = allowableVarianceQtyPosPer;
	}

	public Float getAllowableVarianceQtyNegPer() {
		return allowableVarianceQtyNegPer;
	}

	public void setAllowableVarianceQtyNegPer(Float allowableVarianceQtyNegPer) {
		this.allowableVarianceQtyNegPer = allowableVarianceQtyNegPer;
	}

	public String getEstimatedQuality() {
		return estimatedQuality;
	}

	public void setEstimatedQuality(String estimatedQuality) {
		this.estimatedQuality = estimatedQuality;
	}

	public String getCurrentQuality() {
		return currentQuality;
	}

	public void setCurrentQuality(String currentQuality) {
		this.currentQuality = currentQuality;
	}

	public String getAllowableVarianceQuality() {
		return allowableVarianceQuality;
	}

	public void setAllowableVarianceQuality(String allowableVarianceQuality) {
		this.allowableVarianceQuality = allowableVarianceQuality;
	}

	public double getMbep() {
		return mbep;
	}

	public void setMbep(double mbep) {
		this.mbep = mbep;
	}

	public String getDomesticRestriction() {
		return domesticRestriction;
	}

	public void setDomesticRestriction(String domesticRestriction) {
		this.domesticRestriction = domesticRestriction;
	}

	public String getInternationalRestriction() {
		return internationalRestriction;
	}

	public void setInternationalRestriction(String internationalRestriction) {
		this.internationalRestriction = internationalRestriction;
	}

	public Date getDeliveryDateTime() {
		return deliveryDateTime;
	}

	public void setDeliveryDateTime(Date deliveryDateTime) {
		this.deliveryDateTime = deliveryDateTime;
	}

	public String getLogisticHubId() {
		return logisticHubId;
	}

	public void setLogisticHubId(String logisticHubId) {
		this.logisticHubId = logisticHubId;
	}

	public String getLogisticHubAddress() {
		return logisticHubAddress;
	}

	public void setLogisticHubAddress(String logisticHubAddress) {
		this.logisticHubAddress = logisticHubAddress;
	}

	public String getFarmerDefault() {
		return farmerDefault;
	}

	public void setFarmerDefault(String farmerDefault) {
		this.farmerDefault = farmerDefault;
	}

	public String getSplitField() {
		return splitField;
	}

	public void setSplitField(String splitField) {
		this.splitField = splitField;
	}

	public String getGeographicallyAdjustent() {
		return geographicallyAdjustent;
	}

	public void setGeographicallyAdjustent(String geographicallyAdjustent) {
		this.geographicallyAdjustent = geographicallyAdjustent;
	}

	public String getRiskReport() {
		return riskReport;
	}

	public void setRiskReport(String riskReport) {
		this.riskReport = riskReport;
	}

	public String getRightCertificate() {
		return rightCertificate;
	}

	public void setRightCertificate(String rightCertificate) {
		this.rightCertificate = rightCertificate;
	}

	public int getRecordCreatedBy() {
		return recordCreatedBy;
	}

	public void setRecordCreatedBy(int recordCreatedBy) {
		this.recordCreatedBy = recordCreatedBy;
	}

	public Date getRecordDateTime() {
		return recordDateTime;
	}

	public void setRecordDateTime(Date recordDateTime) {
		this.recordDateTime = recordDateTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getLotId() {
		return lotId;
	}

	public void setLotId(String lotId) {
		this.lotId = lotId;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public int getPhenophaseId() {
		return phenophaseId;
	}

	public void setPhenophaseId(int phenophaseId) {
		this.phenophaseId = phenophaseId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Rights(String id, int versionNumber, String caseId, Float currentQuantity, Float estimatedQuantity,
			Float standardQuantity, Float allowableVarianceQtyPos, Float allowableVarianceQtyNeg,
			Float allowableVarianceQtyPosPer, Float allowableVarianceQtyNegPer, String estimatedQuality,
			String currentQuality, String allowableVarianceQuality, double mbep, String domesticRestriction,
			String internationalRestriction, Date deliveryDateTime, String logisticHubId, String logisticHubAddress,
			String farmerDefault, String splitField, String geographicallyAdjustent, String riskReport,
			String rightCertificate, String aerialPhoto, int recordCreatedBy, Date recordDateTime, String status,
			String comments, String lotId, boolean isVerified, String transactionId, int phenophaseId,
			Date createdDate) {
		super();
		this.id = id;
		this.versionNumber = versionNumber;
		this.caseId = caseId;
		this.currentQuantity = currentQuantity;
		this.estimatedQuantity = estimatedQuantity;
		this.standardQuantity = standardQuantity;
		this.allowableVarianceQtyPos = allowableVarianceQtyPos;
		this.allowableVarianceQtyNeg = allowableVarianceQtyNeg;
		this.allowableVarianceQtyPosPer = allowableVarianceQtyPosPer;
		this.allowableVarianceQtyNegPer = allowableVarianceQtyNegPer;
		this.estimatedQuality = estimatedQuality;
		this.currentQuality = currentQuality;
		this.allowableVarianceQuality = allowableVarianceQuality;
		this.mbep = mbep;
		this.domesticRestriction = domesticRestriction;
		this.internationalRestriction = internationalRestriction;
		this.deliveryDateTime = deliveryDateTime;
		this.logisticHubId = logisticHubId;
		this.logisticHubAddress = logisticHubAddress;
		this.farmerDefault = farmerDefault;
		this.splitField = splitField;
		this.geographicallyAdjustent = geographicallyAdjustent;
		this.riskReport = riskReport;
		this.rightCertificate = rightCertificate;
		this.recordCreatedBy = recordCreatedBy;
		this.recordDateTime = recordDateTime;
		this.status = status;
		this.comments = comments;
		this.lotId = lotId;
		this.isVerified = isVerified;
		this.transactionId = transactionId;
		this.phenophaseId = phenophaseId;
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "Rights [id=" + id + ", versionNumber=" + versionNumber + ", caseId=" + caseId + ", currentQuantity="
				+ currentQuantity + ", estimatedQuantity=" + estimatedQuantity + ", standardQuantity="
				+ standardQuantity + ", allowableVarianceQtyPos=" + allowableVarianceQtyPos
				+ ", allowableVarianceQtyNeg=" + allowableVarianceQtyNeg + ", allowableVarianceQtyPosPer="
				+ allowableVarianceQtyPosPer + ", allowableVarianceQtyNegPer=" + allowableVarianceQtyNegPer
				+ ", estimatedQuality=" + estimatedQuality + ", currentQuality=" + currentQuality
				+ ", allowableVarianceQuality=" + allowableVarianceQuality + ", mbep=" + mbep + ", domesticRestriction="
				+ domesticRestriction + ", internationalRestriction=" + internationalRestriction + ", deliveryDateTime="
				+ deliveryDateTime + ", logisticHubId=" + logisticHubId + ", logisticHubAddress=" + logisticHubAddress
				+ ", farmerDefault=" + farmerDefault + ", splitField=" + splitField + ", geographicallyAdjustent="
				+ geographicallyAdjustent + ", riskReport=" + riskReport + ", rightCertificate=" + rightCertificate
				+ ", recordCreatedBy=" + recordCreatedBy + ", recordDateTime="
				+ recordDateTime + ", status=" + status + ", comments=" + comments + ", lotId=" + lotId
				+ ", isVerified=" + isVerified + ", transactionId=" + transactionId + ", phenophaseId=" + phenophaseId
				+ ", createdDate=" + createdDate + "]";
	}

	

	
}