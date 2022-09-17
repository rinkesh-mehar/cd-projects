package com.drkrishi.usermanagement.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sms_template")
public class SmsTemplate {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false, precision=10)
	private int id;

	@Column(name="name")
	private String name;

	@Column(name="message")
	private String message;

	@Column(name="is_active")
	private int is_active;

	@Column(name="created_date")
	private Timestamp created_date;

	@Column(name="created_by")
	private String created_by;

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public int getIs_active() {
		return is_active;
	}


	public void setIs_active(int is_active) {
		this.is_active = is_active;
	}


	public Timestamp getCreated_date() {
		return created_date;
	}


	public void setCreated_date(Timestamp created_date) {
		this.created_date = created_date;
	}


	public String getCreated_by() {
		return created_by;
	}


	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}


	public Timestamp getModified_date() {
		return modified_date;
	}


	public void setModified_date(Timestamp modified_date) {
		this.modified_date = modified_date;
	}


	public String getModified_by() {
		return modified_by;
	}


	public void setModified_by(String modified_by) {
		this.modified_by = modified_by;
	}


	@Column(name="modified_date")
	private Timestamp modified_date;

	@Column(name="modified_by")
	private String modified_by;

	public SmsTemplate() {}

	public SmsTemplate(int id, String name, String message, int is_active, Timestamp created_date, String created_by,
			Timestamp modified_date, String modified_by) {
		this.id = id;
		this.name = name;
		this.message = message;
		this.is_active = is_active;
		this.created_date = created_date;
		this.created_by = created_by;
		this.modified_date = modified_date;
		this.modified_by = modified_by;
	}


}
