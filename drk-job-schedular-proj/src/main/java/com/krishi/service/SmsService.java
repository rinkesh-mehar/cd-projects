package com.krishi.service;

import com.krishi.entity.Sms;

import java.util.List;

public interface SmsService {

	public String sendSms(String message, String mobile);
	
	public void autoAccountUnlockNotification(String userName, String mobileNumber, String username, String password);

	public boolean prepareSms();
}
