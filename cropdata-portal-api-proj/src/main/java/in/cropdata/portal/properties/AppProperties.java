package in.cropdata.portal.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class AppProperties {

	private static final Logger log = LoggerFactory.getLogger(AppProperties.class);

	@Value("${ts.file-manager.url}")
	private String fileManagerURL;

	@Value("${ts.file-manager.serviceID}")
	private String fileManagerServiceID;

	/**
	 * @return the fileManagerURL
	 */
	public String getFileManagerURL() {
		if (fileManagerURL != null && !fileManagerURL.endsWith("/")) {
			fileManagerURL += "/";
			log.info("fileManagerURL {} ", fileManagerURL);
		}
		return fileManagerURL;
	}

	/** email properties */

	@Value("${mail.sendgrid.buyer-reg-tid}")
	private String buyerRegMailTemplateId;
	
	@Value("${mail.sendgrid.partnership-reg-tid}")
	private String partenershipRegMailTemplateId;

	@Value("${mail.sendgrid.from-mail}")
	private String fromMailId;

	@Value("${mail.sendgrid.from-mailer}")
	private String fromMailerName;

	@Value("${mail.sendgrid.successfull-reg-tid}")
	private String partenershipSuccessfullMailTemplateId;

	@Value("${spring.mail.host}")
	private String mailHost;

	@Value("${spring.mail.port}")
	private Integer mailPort;

	@Value("${spring.mail.username}")
	private String mailUserName;

	@Value("${spring.mail.password}")
	private String mailPassword;

	@Value("${spring.mail.properties.mail.smtp.auth}")
	private String smtpAuth;

	@Value("${spring.mail.properties.mail.smtp.connectiontimeout}")
	private String smtpConnectionTimeOut;

	@Value("${spring.mail.properties.mail.smtp.starttls.enable}")
	private String smtpStarttlsEnable;

	@Value("${portal.env}")
	private String portalEnv;

//	@Value("${mail.sendgrid.reference-user-tid}")
	private String referenceUserMailTemplateId = "";
}
