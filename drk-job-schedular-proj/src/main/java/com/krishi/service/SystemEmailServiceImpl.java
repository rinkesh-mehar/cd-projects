package com.krishi.service;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.krishi.properties.AppProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.krishi.entity.EmailTemplate;
import com.krishi.entity.StaticData;
import com.krishi.model.MasterDataSyncInfo;
import com.krishi.repository.EmailRepository;
import com.krishi.repository.EmailTemplateRepository;
import com.krishi.repository.StaticDataRepository;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class SystemEmailServiceImpl implements SystemEmailService {

	private static final Logger LOGGER = LogManager.getLogger(SystemEmailServiceImpl.class);
	private static final SimpleDateFormat FORMAT = new SimpleDateFormat("dd-MM-YYYY hh:mm:s a");

	@Autowired
	private EmailTemplateRepository emailTemplateRepository;

	@Autowired
	private AppProperties appProperties;

	@Autowired
	private EmailRepository emailRepository;

	@Autowired
	StaticDataRepository staticDataRepository;
	
//    @Value("${email.env}")
	private String env = "email.env";

	@Override
	public void saveEmail(String templateName, com.krishi.entity.Email email, Map<String, String> placeholders) {
		EmailTemplate template = emailTemplateRepository.findByNameAndIsActive(templateName, true);
		if (template != null) {
			email.setStatus(1);
			email.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			email.setEmailDate(new Timestamp(System.currentTimeMillis()));
			email.setFromAddress("no-reply@Drkrishi.com");
			email.setFromMailDisplay("no-reply@Drkrishi.com");
			email.setSubject(template.getSubject());
			String actualEmail = placeholders.entrySet().stream().reduce(template.getBody(),
					(s, e) -> s.replaceAll("(?i)\\[" + e.getKey() + "\\]", e.getValue()), (s, s2) -> s);
			email.setBody(actualEmail);
			emailRepository.saveAndFlush(email);
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void sendDataSyncStatusMail(List<MasterDataSyncInfo> logs, Long startTime, Long endTime) {
		String envName = null;
		switch(env) {
		case "prod":envName="Production";break;
		case "dev":envName="Development";break;
		case "qa":envName="Testing";break;
		case "azureuat":envName="UAT";break;
		default:envName = env;
		}
		StringBuilder tableInfo = new StringBuilder();
	
		
		int i = 1;
		for (MasterDataSyncInfo info : logs) {
			if (info.isSuccess() == true) {
				tableInfo.append(i).append(". Table name: ").append(info.getTableName()).append(", Total Page: ")
						.append(info.getPageCount()).append(", Total Records: ")
						.append(info.getTotalRecordsCount()).append(", Total New Records: ")
						.append(info.getTotalNewRecords()).append(", Total Updated Records: ")
						.append(info.getTotalUpdatedRecords()).append("\n \n");
				
			} else {
				tableInfo.append(i).append(". Table Name: ").append(info.getTableName()).append(",  Error Message: ")
						.append(info.getErrorMessage()).append("\n \n");
			}
			++i;
		}
		
		EmailTemplate template = emailTemplateRepository.findByNameAndIsActive("masterdataSyncStatusEmail", true);
		if (template != null) {
			StaticData fromemail = staticDataRepository.findByKey("cdtfromemail");
			StaticData supportToEmail = staticDataRepository.findByKey("cdttoemail");
			if (template != null && fromemail != null && supportToEmail != null) {
				

				com.krishi.entity.Email email = new com.krishi.entity.Email();
				email.setCreatedBy("System");
				email.setBody(template.getBody());
				email.setCreatedDate(new Timestamp(System.currentTimeMillis()));
				email.setStatus(1);
				email.setEmailDate(new Timestamp(System.currentTimeMillis()));
				email.setFromAddress(fromemail.getValue());
				email.setFromMailDisplay(fromemail.getValue());
				email.setToAddress(supportToEmail.getValue());
				email.setSubject(template.getSubject());

				email.setBody(email.getBody().replace("[tablelist]", tableInfo.toString()));
				email.setBody(email.getBody().replace("[starttime]", FORMAT.format(startTime)));
				email.setBody(email.getBody().replace("[endtime]", FORMAT.format(endTime)));
				email.setBody(email.getBody().replace("[env]", envName));
				email.setSubject(email.getSubject().replace("[env]", envName));
				emailRepository.saveAndFlush(email);
		
			}
		}
	}
	
	public void sendRightDataStatusMail(List<String> failedRightIds) {

		System.out.println("Failed rights ids : "+failedRightIds.toString());
		StringBuffer msg = new StringBuffer();
		msg.append(" ");
		
		String envName = null;
		switch(env) {
		case "prod":envName="Production";break;
		case "dev":envName="Development";break;
		case "qa":envName="Testing";break;
		case "azureuat":envName="UAT";break;
		default:envName = env;
		}
		String subject = envName.toUpperCase()+": Right data not sent to Blockchain";
		
		msg.append("Due to missing field values, the following right records have not been sent to Blockchain. Please investigate and make proper corrections.\n\n");
		msg.append("RIGHT IDS: \n");
		for (int i = 0; i < failedRightIds.size(); i++) {
			msg.append((i + 1) + ". " + failedRightIds.get(i) + "\n");
		}

		Email from = new Email(staticDataRepository.findByKey("cdtfromemail").getValue());
		Email to = new Email(staticDataRepository.findByKey("cdttoemail").getValue());
		
		Content content = new Content("text/plain", msg.toString());

		Mail mail = new Mail(from, subject, to, content);

		SendGrid sg = new SendGrid(staticDataRepository.findByKey("SendGridKey").getValue());
		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sg.api(request);
		} catch (IOException ex) {
			ex.getStackTrace();
			LOGGER.error(ex);
		}
	}

	public void sendSimpleMessage(String to, String subject, String text) throws MessagingException {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", appProperties.getMailHost());
		props.put("mail.smtp.port", appProperties.getMailPort());

		Session session = Session.getInstance(props,
				new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(appProperties.getMailUserName(), appProperties.getMailPassword());
					}
				});

		Message mailMessage = new MimeMessage(session);
		mailMessage.setFrom(new InternetAddress(appProperties.getMailAddress()));
		mailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		mailMessage.setSubject(subject);
		mailMessage.setReplyTo(new InternetAddress[]{new InternetAddress(appProperties.getFromMailId())});
		mailMessage.setContent(text,"text/html");
		Transport.send(mailMessage);

	}
}
