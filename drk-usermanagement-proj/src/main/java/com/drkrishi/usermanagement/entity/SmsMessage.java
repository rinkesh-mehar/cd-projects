package com.drkrishi.usermanagement.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sms")
public class SmsMessage{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false, precision=10)
	private int id;

	@Column(name="mobile_number")
	private String mobile_number;

	@Column(name="message")
	private String message;

	@Column(name="is_sent")
	private int is_sent;

	@Column(name="status")
	private int status;

	@Column(name="retry")
	private int retry;

	@Column(name="created_by")
	private String created_by;

	@Column(name="created_date")
	private Timestamp created_date;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMobile_number() {
		return mobile_number;
	}

	public void setMobile_number(String mobile_number) {
		this.mobile_number = mobile_number;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getIs_sent() {
		return is_sent;
	}

	public void setIs_sent(int is_sent) {
		this.is_sent = is_sent;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getRetry() {
		return retry;
	}

	public void setRetry(int retry) {
		this.retry = retry;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public Timestamp getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Timestamp created_date) {
		this.created_date = created_date;
	}

	public SmsMessage() {}

	public SmsMessage(int id, String mobile_number, String message, int is_sent, int status, int retry,
			String created_by, Timestamp created_date) {

		this.id = id;
		this.mobile_number = mobile_number;
		this.message = message;
		this.is_sent = is_sent;
		this.status = status;
		this.retry = retry;
		this.created_by = created_by;
		this.created_date = created_date;
	}


}
