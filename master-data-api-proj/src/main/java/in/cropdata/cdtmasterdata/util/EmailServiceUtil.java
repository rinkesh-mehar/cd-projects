package in.cropdata.cdtmasterdata.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sendgrid.BccSettings;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.MailSettings;
import com.sendgrid.Method;
import com.sendgrid.Personalization;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.Setting;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.exceptions.InvalidDataException;
import in.cropdata.cdtmasterdata.properties.AppProperties;

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
	 * @param recipientMail   the recipient mail id
	 * @param recipientName   the recipient name
	 * @param mailRedirectUrl the redirect link to be added in mail
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
	public String sendMail(final String recipientMail, String recipientName, final String mailRedirectUrl) {
		try {
			/** Validating data */
			if (recipientMail == null) {
				throw new InvalidDataException("Recipient mail can not be empty!");
			}
			recipientName = recipientName == null ? recipientMail : recipientName;
			LOGGER.info("Recipient -> <{}> <{}>", recipientName, recipientMail);
			/** Getting mail configurations */
			Mail mail = this.prepareMailWithTemplate(recipientMail, recipientName, mailRedirectUrl);

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
	 * @param recipientMail   the recipient mail id
	 * @param recipientName   the recipient name
	 * @param mailRedirectUrl the redirect link to be added in mail
	 * 
	 * @return the response in {@link Mail} object
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	private Mail prepareMailWithTemplate(final String recipientMail, final String recipientName,
			final String mailRedirectUrl) {
		Mail mail = new Mail();
		/** Setting from mail */
		Email fromEmail = new Email();
		fromEmail.setEmail(properties.getFromMailId());
		fromEmail.setName(properties.getFromMailerName());
		/** Setting to mail */
		Email toEmail = new Email();
		toEmail.setEmail(recipientMail);
		toEmail.setName(recipientName);
		/** Configuring personalization */
		Personalization personalization = new Personalization();
		personalization.addTo(toEmail);
		personalization.addDynamicTemplateData("user", recipientName);
		personalization.addDynamicTemplateData("redirectUrl", mailRedirectUrl);
		/** Adding mail configurations */
		mail.addPersonalization(personalization);
		mail.setFrom(fromEmail);
		mail.setTemplateId(properties.getRlUserOnboardMailTemplateId());
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

}
