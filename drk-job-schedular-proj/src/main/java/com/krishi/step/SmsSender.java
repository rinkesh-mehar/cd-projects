package com.krishi.step;

import java.util.List;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.krishi.entity.Sms;
import com.krishi.service.JobLogsService;
import com.krishi.service.SmsService;

public class SmsSender implements ItemProcessor<List<Sms>, List<Sms>> {
	
	@Autowired
	private SmsService smsService;
	//@Autowired
	//JobLogsService jobLogsService;

	/*send sms to respective user and update the status in the database*/
	@Override
	public List<Sms> process(List<Sms> smsList) throws Exception {
		for(Sms sms: smsList) {
			try {
				String response = smsService.sendSms(sms.getMessage(), sms.getMobileNumber());
				if(response != null && response.contains("success")) {
					sms.setIsSent(true);
				} else {
					if(sms.getRetry() == null) {
						sms.setRetry(1);
					} else {
						sms.setRetry(sms.getRetry() + 1);
					}
				}
			} catch(Exception e) {
				if(sms.getRetry() == null) {
					sms.setRetry(1);
				} else {
					sms.setRetry(sms.getRetry() + 1);
				}
				//jobLogsService.saveJobLogs(e.getMessage());
				e.printStackTrace();
			}
		}
		return smsList;
	}

}
