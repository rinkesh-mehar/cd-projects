package in.cropdata.cdtmasterdata.properties;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class AppProperties {

	private static final Logger log = LoggerFactory.getLogger(AppProperties.class);

	@Value("${jwt.secret}")
	private String secret;

	@Value("${ts.file-manager.url}")
	private String fileManagerURL;

	@Value("${ts.file-manager.serviceID}")
	public String fileManagerServiceID;

	@Value("${ts.file-manager.mmpkPathKey}")
	private String mmpkPathKey;

	@Value("${ts.r-api.url}")
	private String rApiUrl;

	@Value("${ts.r-api.serviceID}")
	private URI rServiceID;

	@Value("${cdt.mail.username}")
	private String emailUserName;

	@Value("${cdt.mail.password}")
	private String emailPassword;

	@Value("${mail.sendgrid.from-mail}")
	public String fromMailId;

	@Value("${mail.sendgrid.from-mailer}")
	public String fromMailerName;

	@Value("${mail.sendgrid.rl-user-onboard-tid}")
	public String rlUserOnboardMailTemplateId;

	@Value("${mail.sendgrid.user-onboard-url}")
	public String rlUserOnboardUrl;

	@Value("${drk.bug.fix}")
	public String drkBugFix;

	@Value("${python.ui}")
	public String pythonUIUrl;
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

	public String getRApiURL() {
		if (rApiUrl != null && !rApiUrl.endsWith("/")) {
			rApiUrl += "/";
			log.info("rApiUrl {}", rApiUrl);
		}
		return rApiUrl;
	}
}
