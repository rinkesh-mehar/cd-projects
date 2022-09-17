package com.krishi.step;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.krishi.entity.Sms;
import com.krishi.repository.SmsRepository;

public class SmsWriter  implements ItemWriter<List<Sms>> {
	
	@Autowired
	private SmsRepository smsRepository;

	/*update the status in the database*/
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void write(List<? extends List<Sms>> list) throws Exception {
		for(List<Sms> smsList : list) {
			for(Sms sms : smsList) {
				smsRepository.saveAndFlush(sms);
			}
		}
	}

}
