package com.drkrishi.usermanagement.dao;

import com.drkrishi.usermanagement.entity.SmsMessage;
import com.drkrishi.usermanagement.entity.SmsTemplate;

public interface SMSDao {

	public SmsTemplate getSMSTemplate(String templateName);

	public boolean saveSMSMessage(SmsMessage smsMessage);


}
