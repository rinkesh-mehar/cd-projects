package com.krishi.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.krishi.entity.Sms;
import com.krishi.entity.SmsTemplate;
import com.krishi.repository.SmsRepository;
import com.krishi.repository.SmsTemplateRepository;
import com.krishi.repository.StaticDataRepository;

@Service
public class SmsServiceImpl implements SmsService {

	@Autowired
	private RestTemplate restTemplate;

	private MultiValueMap<String, String> map;
	
	private HttpHeaders headers;
	
	@Autowired
	private StaticDataRepository staticDataRepository;
	
	@Autowired
	private SmsTemplateRepository smsTemplateRepository;
	
	@Autowired
	private SmsRepository smsRepository;

	private static int count = 0;

	@Override
	public String sendSms(String message, String mobile) {
			if(map == null) {
				map = new LinkedMultiValueMap<String, String>();
				map.add("sender", staticDataRepository.findByKey("sender").getValue());
				map.add("messagetype", staticDataRepository.findByKey("messagetype").getValue());
			}
			if(headers == null) {
				headers = new HttpHeaders();
				headers.set("apikey", staticDataRepository.findByKey("apikey").getValue());
//				headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			}
			map.set("message", message);
//			map.set("numbers", "91"+mobile);
			map.set("numbers", "91"+mobile);
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		String response = restTemplate.postForObject("https://api.pinnacle.in/index.php/sms/send", request, String.class);
		return response;
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void autoAccountUnlockNotification(String userName, String mobileNumber, String username, String password) {
		SmsTemplate template = smsTemplateRepository.findByNameAndIsActive("AccountUnLocked", true);
		if(template != null) {
			String message = template.getMessage().replaceAll("(?i)\\[name\\]", userName)
			.replaceAll("(?i)\\[username\\]", mobileNumber)
			.replaceAll("(?i)\\[password\\]", password);
			Sms sms = new Sms();
			sms.setIsSent(false);
			sms.setMessage(message);
			sms.setMobileNumber(mobileNumber);
			sms.setStatus(true);
			sms.setCreatedBy("0");
			sms.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			smsRepository.saveAndFlush(sms);
		}
	}

	@Override
//	@Transactional(readOnly = true)
	public boolean prepareSms() {
		if(count > 0) {
			count = 0;
			return false;
		} else {
			List<Sms> smsList = smsRepository.findByNewSms();
			if(smsList.size() > 0) {
				count = 1;
				return this.prepareSms(smsList);
			} else {
				count = 0;
				return false;
			}

		}
	}

	private boolean prepareSms(List<Sms> smsList){
		for(Sms sms: smsList) {
			try {
				String response = this.sendSms(sms.getMessage(), sms.getMobileNumber());
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

			smsRepository.saveAndFlush(sms);
		}
		return true;
	}

}
