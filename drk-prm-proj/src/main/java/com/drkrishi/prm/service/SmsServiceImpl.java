package com.drkrishi.prm.service;

import com.drkrishi.prm.dao.SMSDao;
import com.drkrishi.prm.entity.Sms;
import com.drkrishi.prm.entity.SmsTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;




@Service
public class SmsServiceImpl implements SmsService {

	private static final Logger LOGGER = LogManager.getLogger(SmsServiceImpl.class);
	
	@Autowired
	private SMSDao smsDao;
	
	// sent notification of task
	@Override
	public void prmTaskAssignmentNotification(String mobileNumber) {
		
		try {
			SmsTemplate smsTemplate = smsDao.getSMSTemplate("TaskAssignment");
			
			String message = smsTemplate.getMessage();
			
			System.out.println(" SMS MESSAGE:   " + message);
					
			Sms sms = new Sms(0, mobileNumber, message, false, false, false,"", new Timestamp(new Date().getTime()));
			
			smsDao.saveSMSMessage(sms);
			
		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
		}
		
	}
	
}
