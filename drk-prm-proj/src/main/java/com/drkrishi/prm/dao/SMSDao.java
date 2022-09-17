package com.drkrishi.prm.dao;

import com.drkrishi.prm.entity.Sms;
import com.drkrishi.prm.entity.SmsTemplate;

public interface SMSDao {

	public SmsTemplate getSMSTemplate(String templateName);

	public boolean saveSMSMessage(Sms smsMessage);
	
	
}
