package in.cropdata.ms.constant;

/**
 * @author Vivek Gajbhiye
 */
public class AWSConst {

    public static final String TOPIC_ARN = "arn:aws:sns:ap-south-1:047198061298:toolsuite";
    public static final String AWS_SNS_SMS_TYPE = "AWS.SNS.SMS.SMSType";

    /**
     * As the name says, these message types are used for promotional purposes only.
     * These messages are delivered between 9AM and 9PM and should only contain promotional material.
     */
    public static final String AWS_SNS_SMS_TYPE_PROMOTIONAL = "Promotional";

    /**
     * These messages are used for high value and critical notifications. For example, for OTP and phone number verification.
     * These type of messages cannot be used for promotional purposes as it violates the regulations set for transactional messages.
     */
    public static final String AWS_SNS_SMS_TYPE_TRANSACTIONAL = "Transactional";
    public static final String AWS_SNS_DATA_TYPE = "String";
    public static final Integer SMS_TIMEOUT = 3000;

}
