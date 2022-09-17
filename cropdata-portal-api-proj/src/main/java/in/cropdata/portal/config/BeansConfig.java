package in.cropdata.portal.config;

import in.cropdata.portal.properties.AppProperties;
import in.cropdata.toolsuite.sdk.FileManagerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.client.RestTemplate;

import in.cropdata.portal.util.ResponseMessageUtil;

import java.util.Properties;

@Configuration
public class BeansConfig {

	@Autowired
	@Lazy
	private RestTemplate restTemplate;

	@Autowired
	private AppProperties appProperties;

	@Bean
	public ResponseMessageUtil getMessageUtil() {
		return new ResponseMessageUtil();
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@Bean
	public FileManagerUtil getFileManagerUtil() {
		return new FileManagerUtil(appProperties.getFileManagerURL(), restTemplate);
	}

	@Bean
	public JavaMailSender getJavaMailSender(){
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(appProperties.getMailHost());
		mailSender.setPort(appProperties.getMailPort());
		mailSender.setUsername(appProperties.getMailUserName());
		mailSender.setPassword(appProperties.getMailPassword());

		Properties prop = mailSender.getJavaMailProperties();
		prop.put("mail.transport.protocol", "smtp");
		prop.put("mail.smtp.auth", appProperties.getSmtpAuth());
		prop.put("mail.smtp.starttls.enable", appProperties.getSmtpAuth());
		prop.put("mail.debug", "true");

		return mailSender;
	}
}
