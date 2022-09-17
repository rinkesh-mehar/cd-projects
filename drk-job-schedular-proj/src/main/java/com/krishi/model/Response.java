package com.krishi.model;

/**
 * @author janardhan
 *
 */
public class Response {

 private String success;

 private Error error;
 
	/** added for status -CDT-Ujwal*/
 private Integer status;

 private String data;

 public Integer getStatus() {
	return status;
}

public void setStatus(Integer status) {
	this.status = status;
}

public String getSuccess() {
  return success;
 }

 public void setSuccess(String success) {
  this.success = success;
 }

 public Error getError() {
  return error;
 }

 public void setError(Error error) {
  this.error = error;
 }

 public String getData() {
  return data;
 }

 public void setData(String data) {
  this.data = data;
 }
}
