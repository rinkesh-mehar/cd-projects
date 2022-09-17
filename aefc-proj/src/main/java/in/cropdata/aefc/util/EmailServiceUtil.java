package in.cropdata.aefc.util;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.*;
import in.cropdata.aefc.constants.APIConstants;
import in.cropdata.aefc.exception.InvalidDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Utility class for processing email operations using SendGrid Mail API.
 *
 * @author RinkeshKM
 * @since 1.0
 */

@Component
public class EmailServiceUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceUtil.class);

    @Autowired
    private SendGrid sendGrid;

    /**
     * This method is used to send mail to intended recipient.
     *
     * @param recipientMail the recipient mail id
     * @param recipientName the recipient name
     * @return the response in string
     * @throws InvalidDataException
     * @author RinkeshKM
     * @apiNote 202 - mail sent successfully
     * @since 1.0
     */
    public String sendMail(final String recipientMail, String recipientName, String fromMailId, String fromMailerName, String mailTemplateId, String redirectUrl) {
        try {
            /** Validating data */
            if (recipientMail == null) {
                throw new InvalidDataException("Recipient mail can not be empty!");
            }
            recipientName = recipientName == null ? recipientMail : recipientName;
            LOGGER.info("Recipient -> <{}> <{}>", recipientName, recipientMail);
            /** Getting mail configurations */
            Mail mail = this.prepareMailWithTemplate(recipientMail, recipientName, fromMailId, fromMailerName, mailTemplateId, redirectUrl);

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
     * @return the response in {@link Mail} object
     * @author RinkeshKM
     * @since 1.0
     */
    private Mail prepareMailWithTemplate(final String recipientMail, final String recipientName, final String fromMailId,
                                         final String fromMailerName, final String mailTemplateId, final String mailRedirectUrl) {
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
        personalization.addDynamicTemplateData("redirectUrl", mailRedirectUrl);
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
     * @author RinkeshKM
     * @since 1.0
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
