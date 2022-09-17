package com.krishi.fls.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="rights")
@IdClass(RightsId.class)
public class Rights {
	private static final SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	@Id
	@Column(name="id")
	private String id;

	@Id
	@Column(name="version_number")
	private Integer versionNumber;
	
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
	private Double allowableVarianceQtyPosPer;
	
	@Column(name="allowable_variance_qty_neg_per")
	private Double allowableVarianceQtyNegPer;
	
	@Column(name="estimated_quality")
	private String estimatedQuality;
	
	@Column(name="current_quality")
	private String currentQuality;
	
	@Column(name="allowable_variance_quality")
	private String allowableVarianceQuality;
	
	@Column(name="mbep")
	private Double mbep;
	
	@Column(name="domestic_restriction")
	private String domesticRestriction;
	
	@Column(name="international_restriction")
	private String internationalRestriction;
	
	@Column(name="delivery_date_time")
	private Date deliveryDateTime;
	
	@Column(name="logistic_hub_id")
	private String logisticHubId;
	
	@Column(name="logistic_hub_address")
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
	private Integer recordCreatedBy;
	
	@Column(name="record_date_time")
	private Date recordDateTime;
	
	@Column(name="status")
	private String status;
	
	@Column(name="status_receiving_date")
	private Date statusReceivingDate;
	
	@Column(name="comments")
	private String comments;
	
	@Column(name="lot_id")
	private String lotId;

	@Column(name="is_verified")
	private Integer isVerified;
	
	@Column(name="transaction_id")
	private String transactionId;
	
	@Column(name="phenophase_id")
	private Integer phenophaseId;
	
	@Column(name="stage")
	private String stage;
	
	@Column(name="due_amount")
	private Float dueAmount;

	@Column(name = "right_sign_url")
	private String rightSignUrl;

	public Rights() {
		super();
	}



	public Rights(String id, Integer versionNumber, String caseId, Float currentQuantity, Float estimatedQuantity,
			Float standardQuantity, Float allowableVarianceQtyPos, Float allowableVarianceQtyNeg,
			Double allowableVarianceQtyPosPer, Double allowableVarianceQtyNegPer, String estimatedQuality,
			String currentQuality, String allowableVarianceQuality, Double mbep, String domesticRestriction,
			String internationalRestriction, Date deliveryDateTime, String logisticHubId, String logisticHubAddress,
			String farmerDefault, String splitField, String geographicallyAdjustent, String riskReport,
			String rightCertificate, Integer recordCreatedBy, Date recordDateTime, String status,
			Date statusReceivingDate, String comments, String lotId, Integer isVerified, String transactionId,
			Integer phenophaseId, String rightSignUrl) {
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
		this.statusReceivingDate = statusReceivingDate;
		this.comments = comments;
		this.lotId = lotId;
		this.isVerified = isVerified;
		this.transactionId = transactionId;
		this.phenophaseId = phenophaseId;
		this.rightSignUrl = rightSignUrl;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public Integer getVersionNumber() {
		return versionNumber;
	}



	public void setVersionNumber(Integer versionNumber) {
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



	public Double getAllowableVarianceQtyPosPer() {
		return allowableVarianceQtyPosPer;
	}



	public void setAllowableVarianceQtyPosPer(Double allowableVarianceQtyPosPer) {
		this.allowableVarianceQtyPosPer = allowableVarianceQtyPosPer;
	}



	public Double getAllowableVarianceQtyNegPer() {
		return allowableVarianceQtyNegPer;
	}



	public void setAllowableVarianceQtyNegPer(Double allowableVarianceQtyNegPer) {
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



	public Double getMbep() {
		return mbep;
	}



	public void setMbep(Double mbep) {
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



	public void setDeliveryDateTime(String deliveryDateTime) {
		//this.deliveryDateTime = deliveryDateTime;
		try {
			this.deliveryDateTime=sd.parse(deliveryDateTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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



	public Integer getRecordCreatedBy() {
		return recordCreatedBy;
	}



	public void setRecordCreatedBy(Integer recordCreatedBy) {
		this.recordCreatedBy = recordCreatedBy;
	}



	public Date getRecordDateTime() {
		return recordDateTime;
	}



	public void setRecordDateTime(String recordDateTime) {
		//this.recordDateTime = recordDateTime;
		try {
			this.recordDateTime=sd.parse(recordDateTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public Date getStatusReceivingDate() {
		return statusReceivingDate;
	}



	public void setStatusReceivingDate(Date statusReceivingDate) {
		this.statusReceivingDate = statusReceivingDate;
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



	public Integer getIsVerified() {
		return isVerified;
	}



	public void setIsVerified(Integer isVerified) {
		this.isVerified = isVerified;
	}



	public String getTransactionId() {
		return transactionId;
	}



	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}



	public Integer getPhenophaseId() {
		return phenophaseId;
	}



	public void setPhenophaseId(Integer phenophaseId) {
		this.phenophaseId = phenophaseId;
	}



	public String getStage() {
		return stage;
	}



	public void setStage(String stage) {
		this.stage = stage;
	}



	public Float getDueAmount() {
		return dueAmount;
	}



	public void setDueAmount(Float dueAmount) {
		this.dueAmount = dueAmount;
	}

	public String getRightSignUrl() {
		return rightSignUrl;
	}

	public void setRightSignUrl(String rightSignUrl) {
		this.rightSignUrl = rightSignUrl;
	}

	@Override
	public Rights clone() throws CloneNotSupportedException {
		return (Rights) super.clone();
	}	
	
}