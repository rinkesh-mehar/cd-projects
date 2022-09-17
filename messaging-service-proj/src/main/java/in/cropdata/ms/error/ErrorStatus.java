package in.cropdata.ms.error;

import lombok.Getter;

/**
 * @author Vivek Gajbhiye
 */
@Getter
public enum ErrorStatus {
    NOTIFICATION_SUCESSFUL("000","Notification sent successfully"),
    NEW_SUBSCRIPTION_ADD("000","New subscription added"),
    TOPIC_CREATE("000","Topic created successfully"),
    TOPIC_DELETE("000","Topic delete successfully"),
    SUBSCRIPTION_DELETE("AWSSNSERR-000","Subscription deleted"),
    SANDBOX_ADDED("AWSSNSERR-000","Phone number added to sandbox"),
    SANDBOX_PHONE_NUMBER_VERIFICATION("AWSSNSERR-000","Phone number verification successfully completed"),
    SMS_SEND_SUCCESSFULLY("AWSSNSERR-000","SMS send successfullt"),


    ERROR_SNS_SMS_SEND("AWSSNSERR-002","Unable to send sms"),
    SNS_TOPIC_FAILED("AWSSNSERR-004","Unable to create topic"),
    ERROR_CREATING_NOTIFICATION("AWSSNSERR-003","Unable to create notification"),
    ERROR_DELETE("AWSSNSERR-004","Unable to delete"),
    UNABLE_SANDBOX("AWSSNSERR-004","Unable to add phone number in sandbox"),
    ERROR_SANDBOX_PHONE_NUMBER_VERIFICATION("AWSSNSERR-006","Unable to verify phone number "),




    DATA_NOT_FOUND("AWSSNSERR-005","Unable to delete");

    private String reasonPhrase;
    private String errorCode;

    ErrorStatus(String errorCode, String reasonPhrase) {
        this.errorCode = errorCode;
        this.reasonPhrase = reasonPhrase;
    }


}
