package in.cropdata.aefc.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.aefc.properties
 * @date 05/12/21
 * @time 6:54 PM
 */
@Component
@Data
public class AppProperties
{
    @Value("${jwt.secret.key}")
    private String jwtSecretKey;

    @Value("${sms.gateway-api-key}")
    private String smsGatewayApiKey;

    @Value("${sendgrid.apikey}")
    private String sendgridApiKey;

    @Value("${sms.gateway-url}")
    private String smsGatewayUrl;

    @Value("${buyer.registration.url}")
    private String buyerRegistrationUrl;

    @Value("${from.mailer-name}")
    private String fromMailerName;

    @Value("${mail.template.id}")
    private String mailTemplateId;

    @Value("${from.mail-id}")
    private String fromMailId;

    @Value("${otp-expiry.time}")
    private Integer otpExpiryTime;


}
