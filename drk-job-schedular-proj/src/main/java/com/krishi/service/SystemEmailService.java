package com.krishi.service;

import java.util.List;
import java.util.Map;

import com.krishi.entity.Email;
import com.krishi.model.MasterDataSyncInfo;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public interface SystemEmailService {
	
	void saveEmail(String templateName, Email email, Map<String, String> placeholders);
	
	public void sendDataSyncStatusMail(List<MasterDataSyncInfo> logs, Long startTime, Long endTime);

	public void sendRightDataStatusMail(List<String> failedRightIds);

	public void sendSimpleMessage(String to, String subject, String text) throws MessagingException;

}
