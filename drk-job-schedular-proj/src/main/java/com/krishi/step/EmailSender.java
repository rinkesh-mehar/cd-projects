package com.krishi.step;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import com.krishi.properties.AppProperties;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.krishi.entity.Email;
import com.krishi.repository.EmailRepository;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Personalization;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender implements ItemProcessor<List<Email>, List<Email>>{

	@Autowired
	private AppProperties appProperties;

	@Autowired
	private SendGrid sendGrid;
	
	@Autowired
	private EmailRepository emailRepository;

	/*process the email and send to respective user*/
	@Override
	public List<Email> process(List<Email> item) throws Exception {
		for(Email email:item) {

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
			mailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(appProperties.getMailTo()));
			mailMessage.setSubject(email.getSubject());
			mailMessage.setReplyTo(new InternetAddress[]{new InternetAddress(appProperties.getFromMailId())});
			mailMessage.setContent(email.getBody(),"text/html");
			Transport.send(mailMessage);

			/*
			try {
			String[] tos = email.getToAddress().split(",");
			com.sendgrid.helpers.mail.objects.Email from = new com.sendgrid.helpers.mail.objects.Email(email.getFromAddress());
			com.sendgrid.helpers.mail.objects.Email to = new com.sendgrid.helpers.mail.objects.Email(tos[0]);
			Content content = new Content("text/plain", email.getBody());
			Mail mail = new Mail(from, email.getSubject(), to, content);
			if(tos.length > 1) {
				Personalization p = new Personalization();
				for(int i=1; i< tos.length; i++) {
					p.addTo(new com.sendgrid.helpers.mail.objects.Email(tos[i]));
				}
				mail.addPersonalization(p);
			}
			Request request = new Request();
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sendGrid.api(request);
			if(response != null) {
				email.setResponse(response.getBody());
			}
			if(response.getStatusCode() == 202) {
			email.setIsSent(true);
			email.setSentDate(new Timestamp(System.currentTimeMillis()));
			} else {
				if(email.getRetry() == null) {
					email.setRetry(1);
				} else {
					email.setRetry(email.getRetry()+1);
				}
			}
			emailRepository.saveAndFlush(email);
		} catch (Exception e) {
			if(email.getRetry() == null) {
				email.setRetry(1);
			} else {
				email.setRetry(email.getRetry()+1);
			}
		}*/
			
		}
		return item;
	}

}
