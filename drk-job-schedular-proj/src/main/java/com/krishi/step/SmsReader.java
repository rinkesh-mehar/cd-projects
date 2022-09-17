package com.krishi.step;

import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.krishi.entity.Sms;
import com.krishi.repository.SmsRepository;

public class SmsReader implements ItemReader<List<Sms>> {
	
	@Autowired
	private SmsRepository smsRepository;
	
	private static int count = 0;

	/*read all newly created sms from database and send to SmsSender*/
	@Override
	@Transactional(readOnly = true)
	public List<Sms> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if(count > 0) {
			count = 0;
			return null;
		} else {
			List<Sms> smsList = smsRepository.findByNewSms();
			if(smsList.size() > 0) {
				count = 1;
				return smsList;
			} else {
				count = 0;
				return null;
			}
			
		}
	}

}
