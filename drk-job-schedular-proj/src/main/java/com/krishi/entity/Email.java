package com.krishi.entity;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="email")
public class Email {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "email_date")
	private Timestamp emailDate;
	
	@Column(name = "from_address")
	private String fromAddress;
	
	@Column(name = "from_mail_display")
	private String fromMailDisplay;
	
	@Column(name = "to_address")
	private String toAddress;
	
	@Column(name = "cc_address")
	private String ccAddress;
	
	@Column(name = "bcc_address")
	private String bccAddress;
	
	private String subject;
	
	private String body;
	
	@Column(name = "is_sent")
	private Boolean isSent;
	
	private Integer status;
	
	private Integer retry;
	
	
	private String response;
	
	@Column(name = "sent_date")
	private Timestamp sentDate;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "created_date")
	private Timestamp createdDate;
	
	

	public Email() {
		super();
	}

	

	public Email(Integer id, Timestamp emailDate, String fromAddress, String fromMailDisplay, String toAddress,
			String ccAddress, String bccAddress, String subject, String body, Boolean isSent, Integer status,
			Integer retry, String response, Timestamp sentDate, String createdBy, Timestamp createdDate) {
		super();
		this.id = id;
		this.emailDate = emailDate;
		this.fromAddress = fromAddress;
		this.fromMailDisplay = fromMailDisplay;
		this.toAddress = toAddress;
		this.ccAddress = ccAddress;
		this.bccAddress = bccAddress;
		this.subject = subject;
		this.body = body;
		this.isSent = isSent;
		this.status = status;
		this.retry = retry;
		this.response = response;
		this.sentDate = sentDate;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public Timestamp getEmailDate() {
		return emailDate;
	}



	public void setEmailDate(Timestamp emailDate) {
		this.emailDate = emailDate;
	}



	public String getFromAddress() {
		return fromAddress;
	}



	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}



	public String getFromMailDisplay() {
		return fromMailDisplay;
	}



	public void setFromMailDisplay(String fromMailDisplay) {
		this.fromMailDisplay = fromMailDisplay;
	}



	public String getToAddress() {
		return toAddress;
	}



	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}



	public String getCcAddress() {
		return ccAddress;
	}



	public void setCcAddress(String ccAddress) {
		this.ccAddress = ccAddress;
	}



	public String getBccAddress() {
		return bccAddress;
	}



	public void setBccAddress(String bccAddress) {
		this.bccAddress = bccAddress;
	}



	public String getSubject() {
		return subject;
	}



	public void setSubject(String subject) {
		this.subject = subject;
	}



	public String getBody() {
		return body;
	}



	public void setBody(String body) {
		this.body = body;
	}



	public Boolean getIsSent() {
		return isSent;
	}



	public void setIsSent(Boolean isSent) {
		this.isSent = isSent;
	}



	public Integer getStatus() {
		return status;
	}



	public void setStatus(Integer status) {
		this.status = status;
	}



	public Integer getRetry() {
		return retry;
	}



	public void setRetry(Integer retry) {
		this.retry = retry;
	}



	public String getResponse() {
		return response;
	}



	public void setResponse(String response) {
		this.response = response;
	}



	public Timestamp getSentDate() {
		return sentDate;
	}



	public void setSentDate(Timestamp sentDate) {
		this.sentDate = sentDate;
	}



	public String getCreatedBy() {
		return createdBy;
	}



	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}



	public Timestamp getCreatedDate() {
		return createdDate;
	}



	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}



	
	

}
