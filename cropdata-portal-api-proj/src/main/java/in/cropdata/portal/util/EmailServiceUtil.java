package in.cropdata.portal.util;

import java.io.IOException;
import java.util.Properties;

import in.cropdata.portal.dto.ResponseMessage;
import in.cropdata.portal.model.ApplicantApplicationDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.BccSettings;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.MailSettings;
import com.sendgrid.helpers.mail.objects.Personalization;
import com.sendgrid.helpers.mail.objects.Setting;

import in.cropdata.portal.constants.APIConstants;
import in.cropdata.portal.exceptions.InvalidDataException;
import in.cropdata.portal.properties.AppProperties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Utility class for processing email operations using SendGrid Mail API.
 * 
 * @since 1.0
 * @author PranaySK
 */

@Component
public class EmailServiceUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceUtil.class);

	@Autowired
	private SendGrid sendGrid;

	@Autowired
	private AppProperties properties;

	/**
	 * This method is used to send mail to intended recipient.
	 * 
	 * @param recipientMail the recipient mail id
	 * @param recipientName the recipient name
	 * 
	 * @return the response in string
	 * 
	 * @throws InvalidDataException
	 * 
	 * @since 1.0
	 * @author PranaySK
	 * 
	 * @apiNote 202 - mail sent successfully
	 */
	public String sendMail(final String recipientMail, String recipientName, String fromMailId, String fromMailerName, String mailTemplateId) {
		try {
			/** Validating data */
			if (recipientMail == null) {
				throw new InvalidDataException("Recipient mail can not be empty!");
			}
			recipientName = recipientName == null ? recipientMail : recipientName;
			LOGGER.info("Recipient -> <{}> <{}>", recipientName, recipientMail);
			/** Getting mail configurations */
			Mail mail = this.prepareMailWithTemplate(recipientMail, recipientName, fromMailId, fromMailerName, mailTemplateId);

			/** Preparing request */
			Request request = new Request();
			request.setMethod(Method.POST);
			request.setEndpoint(APIConstants.SENDGRID_API_ENDPOINT);
			request.setBody(mail.build());

			/** Calling SendGrid API to send mail */
			Response response = sendGrid.api(request);

			LOGGER.info("Mail response -> {} --> {}", response.getStatusCode(), response.getBody());
			/** Returning response */
			return Integer.toString(response.getStatusCode());

		} catch (IOException ex) {
			LOGGER.error("Error while sending mail! Something went wrong -> ", ex);
		}

		return null;
	}

	/**
	 * This method is used to get mail configurations.
	 * 
	 * @param recipientMail the recipient mail id
	 * @param recipientName the recipient name
	 * 
	 * @return the response in {@link Mail} object
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	private Mail prepareMailWithTemplate(final String recipientMail, final String recipientName, final String fromMailId, final String fromMailerName, final String mailTemplateId ) {
		LOGGER.info("recipientMail : " + recipientMail + " recipientName : " + recipientName + " fromMailId : " + fromMailId + " fromMailerName : " + fromMailerName + " mailTemplateId : " + mailTemplateId);
		Mail mail = new Mail();
		/** Setting from mail */
		Email fromEmail = new Email();
		fromEmail.setEmail(fromMailId);
		fromEmail.setName(fromMailerName);
		/** Setting to mail */
		Email toEmail = new Email();
		toEmail.setEmail(recipientMail);
		toEmail.setName(recipientName);
		/** Configuring personalization */
		Personalization personalization = new Personalization();
		personalization.addTo(toEmail);
		personalization.addDynamicTemplateData("user", recipientName);
		/** Adding mail configurations */
		mail.addPersonalization(personalization);
		mail.setFrom(fromEmail);
		mail.setTemplateId(mailTemplateId);
		mail.setMailSettings(this.getMailSettings());

		return mail;
	}

	/**
	 * This method is used to configure mail settings.
	 * 
	 * @return the response in {@link MailSettings} object
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	private MailSettings getMailSettings() {
		MailSettings mailSettings = new MailSettings();
		/** BCC settings */
		BccSettings bccSettings = new BccSettings();
		bccSettings.setEnable(true);
		mailSettings.setBccSettings(bccSettings);
		/** Bypass List Management settings */
		Setting bypassListManagement = new Setting();
		bypassListManagement.setEnable(true);
		mailSettings.setBypassListManagement(bypassListManagement);

		return mailSettings;
	}

	public ResponseMessage sendSimpleMessage(String to, String subject, String text) throws MessagingException
	{

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", properties.getMailHost());
		props.put("mail.smtp.port", properties.getMailPort());

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(properties.getMailUserName(), properties.getMailPassword());
					}
				});

		Message mailMessage = new MimeMessage(session);
		mailMessage.setFrom(new InternetAddress(properties.getMailUserName()));
		mailMessage.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
		mailMessage.setSubject(subject);
		mailMessage.setReplyTo(new InternetAddress[]{new InternetAddress(properties.getFromMailId())});
		mailMessage.setContent(text,"text/html");
		Transport.send(mailMessage);
		return null;
	}

	public String sendRegistrationMail(final String recipientMail, String recipientName, String fromMailId, String fromMailerName, String mailTemplateId, String referralCode, String env)
	{
		try
		{
			/** Validating data */
			if (recipientMail == null) {
				throw new InvalidDataException("Recipient mail can not be empty!");
			}
			recipientName = recipientName == null ? recipientMail : recipientName;
			LOGGER.info("Recipient -> <{}> <{}>", recipientName, recipientMail);

			Personalization personalization = new Personalization();

		personalization.addDynamicTemplateData("user", recipientName);
		personalization.addDynamicTemplateData("referralcode", referralCode);
		personalization.addDynamicTemplateData("env", env);

			/** Getting mail configurations */
			Mail mail = this.prepareMailForOtp(recipientMail, recipientName, fromMailId, fromMailerName, mailTemplateId, referralCode, env, personalization);

			/** Preparing request */
			Request request = new Request();
			request.setMethod(Method.POST);
			request.setEndpoint(APIConstants.SENDGRID_API_ENDPOINT);
			request.setBody(mail.build());

			/** Calling SendGrid API to send mail */
			Response response = sendGrid.api(request);

			LOGGER.info("Mail response -> {} --> {}", response.getStatusCode(), response.getBody());
			/** Returning response */
			return Integer.toString(response.getStatusCode());

		} catch (IOException ex) {
			LOGGER.error("Error while sending mail! Something went wrong -> ", ex);
		}

		return null;
	}

	public String sendReferenceUserRegistrationMail(final String recipientMail, String recipientName, String fromMailId, String fromMailerName, String mailTemplateId, ApplicantApplicationDetails applicantApplicationDetails, String env)
	{
		try
		{
			/** Validating data */
			if (recipientMail == null) {
				throw new InvalidDataException("Recipient mail can not be empty!");
			}
			recipientName = recipientName == null ? recipientMail : recipientName;
			LOGGER.info("Recipient -> <{}> <{}>", recipientName, recipientMail);

			Personalization personalization = new Personalization();

			personalization.addDynamicTemplateData("company_name", applicantApplicationDetails.getCompanyName());
			personalization.addDynamicTemplateData("referralcode", applicantApplicationDetails.getReference());
			personalization.addDynamicTemplateData("env", env);

			/** Getting mail configurations */
			Mail mail = this.prepareMailForOtp(recipientMail, recipientName, fromMailId, fromMailerName, mailTemplateId, applicantApplicationDetails.getReference(), env, personalization);

			/** Preparing request */
			Request request = new Request();
			request.setMethod(Method.POST);
			request.setEndpoint(APIConstants.SENDGRID_API_ENDPOINT);
			request.setBody(mail.build());

			/** Calling SendGrid API to send mail */
			Response response = sendGrid.api(request);

			LOGGER.info("Mail response -> {} --> {}", response.getStatusCode(), response.getBody());
			/** Returning response */
			return Integer.toString(response.getStatusCode());

		} catch (IOException ex) {
			LOGGER.error("Error while sending mail! Something went wrong -> ", ex);
		}

		return null;
	}

	private Mail prepareMailForOtp(final String recipientMail, final String recipientName, final String fromMailId,
								   final String fromMailerName, final String mailTemplateId, String referralCode, String env, Personalization personalization ) {
		LOGGER.info("recipientMail : " + recipientMail + " recipientName : " + recipientName + " fromMailId : " + fromMailId + " fromMailerName : " + fromMailerName + " mailTemplateId : " + mailTemplateId);
		Mail mail = new Mail();
		/** Setting from mail */
		Email fromEmail = new Email();
		fromEmail.setEmail(fromMailId);
		fromEmail.setName(fromMailerName);
		/** Setting to mail */
		Email toEmail = new Email();
		toEmail.setEmail(recipientMail);
		toEmail.setName(recipientName);
		/** Configuring personalization */
//		Personalization personalization = new Personalization();
		personalization.addTo(toEmail);
//		personalization.addDynamicTemplateData("user", recipientName);
//		personalization.addDynamicTemplateData("referralcode", referralCode);
//		personalization.addDynamicTemplateData("env", env);
		/** Adding mail configurations */
		mail.addPersonalization(personalization);
		mail.setFrom(fromEmail);
		mail.setTemplateId(mailTemplateId);
		mail.setMailSettings(this.getMailSettings());

		return mail;
	}
}
