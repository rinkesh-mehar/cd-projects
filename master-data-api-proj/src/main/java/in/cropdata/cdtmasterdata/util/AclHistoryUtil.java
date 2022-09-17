package in.cropdata.cdtmasterdata.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import in.cropdata.cdtmasterdata.acl.model.AclHistory;
import in.cropdata.cdtmasterdata.repository.AclHistoryRepository;

public class AclHistoryUtil {

	@Autowired
	AclHistoryRepository aclHistoryRepository;

	public void addRecord(String tableName, int rowId) {
		AclHistory AclHistory = new AclHistory();
		AclHistory.setTableName(tableName);
		AclHistory.setRowId(rowId);
		AclHistory.setCreatedAt(new Date());
		AclHistory.setCreatedBy(0);
		aclHistoryRepository.save(AclHistory);
	}

	public void updateRecord(String tableName, int rowId) {
		AclHistory AclHistory = new AclHistory();
		AclHistory.setTableName(tableName);
		AclHistory.setRowId(rowId);
		AclHistory.setUpdatedAt(new Date());
		AclHistory.setUpdatedBy(0);

		aclHistoryRepository.save(AclHistory);
	}

	public void primaryApprove(String tableName, int rowId) {
		AclHistory AclHistory = new AclHistory();
		AclHistory.setTableName(tableName);
		AclHistory.setRowId(rowId);
		AclHistory.setPrimaryApprovalStatus("Approved");
		AclHistory.setPrimaryApprovedAt(new Date());
		AclHistory.setPrimaryApproverId(0);
		aclHistoryRepository.save(AclHistory);
	}

	public void finalApprove(String tableName, int rowId) {
		AclHistory AclHistory = new AclHistory();
		AclHistory.setTableName(tableName);
		AclHistory.setRowId(rowId);
		AclHistory.setFinalApproverId(0);
		AclHistory.setFinalApprovalStatus("Approved");
		AclHistory.setFinalApprovedAt(new Date());
		aclHistoryRepository.save(AclHistory);
	}

	public void delete(String tableName, int rowId) {
		AclHistory AclHistory = new AclHistory();
		AclHistory.setTableName(tableName);
		AclHistory.setRowId(rowId);
		aclHistoryRepository.save(AclHistory);
	}
	
	public void reject(String tableName, int rowId) {
		AclHistory AclHistory = new AclHistory();
		AclHistory.setTableName(tableName);
		AclHistory.setRowId(rowId);
		aclHistoryRepository.save(AclHistory);
	}

}
