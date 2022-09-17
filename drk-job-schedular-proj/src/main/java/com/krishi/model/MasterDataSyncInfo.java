package com.krishi.model;

public class MasterDataSyncInfo {

	private int pageCount = 0;
	private int totalRecordsCount = 0;
	private int totalNewRecords = 0;
	private int totalUpdatedRecords = 0;
	private String tableName;
	private boolean isSuccess = false;
	private String errorMessage;

	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public int getTotalRecordsCount() {
		return totalRecordsCount;
	}

	public void setTotalRecordsCount(int totalRecordsCount) {
		this.totalRecordsCount = totalRecordsCount;
	}

	public int getTotalNewRecords() {
		return totalNewRecords;
	}

	public void setTotalNewRecords(int totalNewRecords) {
		this.totalNewRecords = totalNewRecords;
	}

	public int getTotalUpdatedRecords() {
		return totalUpdatedRecords;
	}

	public void setTotalUpdatedRecords(int totalUpdatedRecords) {
		this.totalUpdatedRecords = totalUpdatedRecords;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	
}
