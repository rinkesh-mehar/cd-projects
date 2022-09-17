package com.drkrishi.rlt.entity;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "view_comment")
public class ViewComment {

	@Id
	@Column( name = "comment_id")
	private String commentId;
	
	@Column( name = "comment_date")
	private Date commentDate;
	
	@Column( name = "comment_time")
	private Time commentTime;
	
	@Column( name = "comment")
	private String comment;
	
	@Column( name = "first_name")
	private String firstName;
	
	@Column( name = "last_name")
	private String lastName;
	
	@Column( name = "role_name")
	private String roleName;
	
	@Column( name = "middle_name")
	private String middleName;
	
	@Column( name = "task_id")
	private String taskId;
	
	@Column( name = "task_type_id")
	private String taskTypeId;
	
	@Column( name = "status")
	private Integer status;

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public Date getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	public Time getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Time commentTime) {
		this.commentTime = commentTime;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskTypeId() {
		return taskTypeId;
	}

	public void setTaskTypeId(String taskTypeId) {
		this.taskTypeId = taskTypeId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
