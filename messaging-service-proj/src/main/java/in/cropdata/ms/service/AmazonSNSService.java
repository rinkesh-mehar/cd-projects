package in.cropdata.ms.service;




import in.cropdata.ms.error.ErrorStatus;
import in.cropdata.ms.exception.DataNotFoundException;

import in.cropdata.ms.model.Notification;
import in.cropdata.ms.model.SmsNotification;
import in.cropdata.ms.response.Api;
import in.cropdata.ms.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Vivek Gajbhiye
 */
@Service
public class AmazonSNSService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AmazonSNSService.class);

    @Autowired
    SnsClient snsClient;
    @Autowired
    private ApiResponse apiResponse;



    public Api createTopic(String topicName){
        try {
            Map<String,String> map = new HashMap<>();
            CreateTopicRequest request = CreateTopicRequest.builder()
                    .name(topicName)
                    .build();

            CreateTopicResponse response = snsClient.createTopic(request);
            map.put("topicArn",response.topicArn());
            return apiResponse.response(true,map,ErrorStatus.TOPIC_CREATE);
        } catch (SnsException e) {
            LOGGER.error("error occured while creating sns topic {}",e.getMessage());
            return apiResponse.response(true,ErrorStatus.SNS_TOPIC_FAILED);
        }
    }

    public Api deleteTopic(String topicArn){
        try {
            DeleteTopicRequest request = DeleteTopicRequest.builder()
                    .topicArn(topicArn)
                    .build();
            DeleteTopicResponse response = snsClient.deleteTopic(request);
            if(response.sdkHttpResponse().isSuccessful()){
                return apiResponse.response(true,ErrorStatus.TOPIC_DELETE);
            }else{
                return apiResponse.response(false,ErrorStatus.ERROR_DELETE);
            }
        }catch (SnsException e){
            return apiResponse.response(false,"Unable to delete topic ",e.getMessage());
        }

    }
//
    public List<Topic> listTopics(){
        try {
            ListTopicsRequest request = ListTopicsRequest.builder().build();
            ListTopicsResponse response = snsClient.listTopics(request);
            if(response.sdkHttpResponse().isSuccessful()){
                List<Topic> topics = response.topics();
                return topics;
            }else{
                throw new DataNotFoundException(ErrorStatus.DATA_NOT_FOUND);
            }
        }catch (SnsException  e){
            LOGGER.error("error occured while creating sns topic {}",e.getMessage());
            throw new DataNotFoundException(ErrorStatus.DATA_NOT_FOUND);
        }
    }
//
    public List<Subscription> subscribersList(String topicArn){
        try {
            ListSubscriptionsRequest request = ListSubscriptionsRequest.builder()
                    .build();

            ListSubscriptionsResponse response = snsClient.listSubscriptions(request);
            if(response.sdkHttpResponse().isSuccessful()){
                return response.subscriptions();
            }else{
                throw new DataNotFoundException(ErrorStatus.DATA_NOT_FOUND);
            }

        } catch (SnsException e) {
            throw new DataNotFoundException(ErrorStatus.DATA_NOT_FOUND);
        }
    }

    public Api createSubscription(in.cropdata.ms.model.Subscription subscription){
        try {
            SubscribeRequest subscribeRequest = SubscribeRequest.builder()
                    .topicArn(subscription.getTopicArn())
                    .protocol(subscription.getProtocol())
                    .endpoint(subscription.getEndpoint())
                    .build();
            SubscribeResponse subscribe = snsClient.subscribe(subscribeRequest);
            if(subscribe.sdkHttpResponse().isSuccessful()){
                Map<String, String> map = new HashMap<>();
                map.put("subscriptionArn",subscribe.subscriptionArn());
                return apiResponse.response(true, map,ErrorStatus.NEW_SUBSCRIPTION_ADD);
            }else{
                return apiResponse.response(false, ErrorStatus.ERROR_CREATING_NOTIFICATION);
            }
        }catch(SnsException e){
            return apiResponse.response(false, e.awsErrorDetails().errorMessage(), null);
        }
    }

    public Api unsubscribe(String subscriptionArn){
        try {
            UnsubscribeRequest request = UnsubscribeRequest.builder()
                    .subscriptionArn(subscriptionArn)
                    .build();

            UnsubscribeResponse response = snsClient.unsubscribe(request);
            if(response.sdkHttpResponse().isSuccessful()){
                return apiResponse.response(true, ErrorStatus.SUBSCRIPTION_DELETE);
            }else {
                return apiResponse.response(false, "Unable to delete subscription",null);
            }
        } catch (SnsException e) {
            return apiResponse.response(false, e.awsErrorDetails().errorMessage(), null);
        }
    }

    public Api notification(Notification notification,String topicArn){
        try {
            PublishRequest request = PublishRequest.builder()
                    .message(notification.getMessage())
                    .subject(notification.getSubject())
                    .topicArn(topicArn)
                    .build();

            PublishResponse response = snsClient.publish(request);
            if(response.sdkHttpResponse().isSuccessful()){
                return apiResponse.response(true, ErrorStatus.NOTIFICATION_SUCESSFUL);
            }else{
                return apiResponse.response(false, ErrorStatus.ERROR_CREATING_NOTIFICATION);
            }

        } catch (SnsException e) {
            return apiResponse.response(false, e.awsErrorDetails().errorMessage(), null);
        }
    }

    public Api createSMSSandboxPhoneNumber(String phoneNumber,String code){
        try {
            CreateSmsSandboxPhoneNumberRequest request = CreateSmsSandboxPhoneNumberRequest.builder()
                    .phoneNumber(phoneNumber)
                    .languageCode(code)
                    .build();
            CreateSmsSandboxPhoneNumberResponse response = snsClient.createSMSSandboxPhoneNumber(request);
            if(response.sdkHttpResponse().isSuccessful()){
                return apiResponse.response(true, ErrorStatus.SANDBOX_ADDED);
            }else{
                return apiResponse.response(true, ErrorStatus.UNABLE_SANDBOX);
            }
        }catch (SnsException e) {
            return apiResponse.response(false, e.awsErrorDetails().errorMessage(), null);
        }
    }

    public Api verifySmsSandboxPhoneNumber(String phoneNumber,String otp){
        try{
            VerifySmsSandboxPhoneNumberRequest request = VerifySmsSandboxPhoneNumberRequest.builder()
                    .phoneNumber(phoneNumber)
                    .oneTimePassword(otp)
                    .build();
            VerifySmsSandboxPhoneNumberResponse response = snsClient.verifySMSSandboxPhoneNumber(request);
            if(response.sdkHttpResponse().isSuccessful()){
                return apiResponse.response(true, ErrorStatus.SANDBOX_PHONE_NUMBER_VERIFICATION);
            }else{
                return apiResponse.response(false, ErrorStatus.ERROR_SANDBOX_PHONE_NUMBER_VERIFICATION);
            }
        }catch (SnsException e) {
            return apiResponse.response(false, e.awsErrorDetails().errorMessage(), null);
        }

    }

    public Map<Object,Object> smsSettings(String topicArn){
        Map<Object, Object> smsSettings = new HashMap<>();
        try {
            GetSubscriptionAttributesRequest request = GetSubscriptionAttributesRequest.builder()
                    .subscriptionArn(topicArn)
                    .build();
            GetSubscriptionAttributesResponse response = snsClient.getSubscriptionAttributes(request);
            Map<String, String> map = response.attributes();
            Iterator iter = map.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                smsSettings.put(entry.getKey(),entry.getValue());
            }
            return smsSettings;

        } catch (SnsException e) {
            smsSettings.put("status",false);
            smsSettings.put("message",e.awsErrorDetails().errorMessage());
            return smsSettings;
        }
    }

    public Api checkIfPhoneNumberIsOptedOut(String phoneNumber){
        try {
            CheckIfPhoneNumberIsOptedOutRequest request = CheckIfPhoneNumberIsOptedOutRequest.builder()
                    .phoneNumber(phoneNumber)
                    .build();

            CheckIfPhoneNumberIsOptedOutResponse response = snsClient.checkIfPhoneNumberIsOptedOut(request);
            if(response.isOptedOut()){
                return apiResponse.response(true,phoneNumber + " has Opted Out of receiving sns messages.",null);
            }else{
                return apiResponse.response(true,phoneNumber + " has not Opted Out of receiving sns messages.",null);
            }
        } catch (SnsException e) {
            return apiResponse.response(false, e.awsErrorDetails().errorMessage(), null);
        }
    }

    public List<String> optedOutPhoneNumbers(){
        try {

            ListPhoneNumbersOptedOutRequest request = ListPhoneNumbersOptedOutRequest.builder().build();
            ListPhoneNumbersOptedOutResponse response = snsClient.listPhoneNumbersOptedOut(request);
            if(response.hasPhoneNumbers()){
                return response.phoneNumbers();
            }else{
                throw new DataNotFoundException(ErrorStatus.DATA_NOT_FOUND);
            }

        } catch (SnsException e) {
            throw new DataNotFoundException(ErrorStatus.DATA_NOT_FOUND);
        }
    }

    public Api publishSms(SmsNotification smsNotification){
        try {
            PublishRequest request = PublishRequest.builder()
                    .message(smsNotification.getMessage())
                    .phoneNumber(smsNotification.getPhone())
                    .build();

            PublishResponse response = snsClient.publish(request);
            if(response.sdkHttpResponse().isSuccessful()){
                return apiResponse.response(true,ErrorStatus.SMS_SEND_SUCCESSFULLY);
            }else{
                return apiResponse.response(false,ErrorStatus.ERROR_SNS_SMS_SEND);
            }
        } catch (SnsException e) {
            return apiResponse.response(false, e.awsErrorDetails().errorMessage(), null);
        }
    }

    public List<SMSSandboxPhoneNumber> listSmsSandboxPhoneNumbers(){
        try {
            ListSmsSandboxPhoneNumbersRequest request = ListSmsSandboxPhoneNumbersRequest.builder()
                    .build();
            ListSmsSandboxPhoneNumbersResponse response = snsClient.listSMSSandboxPhoneNumbers(request);
            if(response.hasPhoneNumbers()){
                return response.phoneNumbers();
            }else{
                throw new DataNotFoundException(ErrorStatus.DATA_NOT_FOUND);
            }
        }catch(SnsException e){
            throw new DataNotFoundException(ErrorStatus.DATA_NOT_FOUND);
        }
    }



}
