package in.cropdata.cdtmasterdata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service("mailService")
public class EmailSenderService {

	@Autowired
	private JavaMailSenderImpl mailSender;

	@Async
	public void sendEmail(SimpleMailMessage email) {
		try {
		mailSender.send(email);
		}catch (MailSendException e) {
			e.printStackTrace();
		}
	}
}