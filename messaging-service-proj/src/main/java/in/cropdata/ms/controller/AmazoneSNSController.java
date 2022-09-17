package in.cropdata.ms.controller;


import in.cropdata.ms.model.Notification;
import in.cropdata.ms.model.SmsNotification;
import in.cropdata.ms.response.Api;
import in.cropdata.ms.service.AmazonSNSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.sns.model.SMSSandboxPhoneNumber;
import software.amazon.awssdk.services.sns.model.Subscription;
import software.amazon.awssdk.services.sns.model.Topic;

import java.util.List;
import java.util.Map;

/**
 * @author Vivek Gajbhiye
 */
@RestController
@RequestMapping("/aws/sns/")
public class AmazoneSNSController {

    @Autowired
    private AmazonSNSService amazonSNSService;

    @PostMapping("/subscription")
    public Api addNewSubscription(@RequestBody in.cropdata.ms.model.Subscription subscription){
        return amazonSNSService.createSubscription(subscription);
    }
    @PostMapping("/subscription/delete/{subscriptionArn}")
    public Api addNewSubscription(@PathVariable String subscriptionArn){
        return amazonSNSService.unsubscribe(subscriptionArn);
    }
    @PostMapping("/notification/{topicArn}")
    public Api notification(@RequestBody Notification notification,@PathVariable String topicArn){
        return amazonSNSService.notification(notification,topicArn);
    }
    @PostMapping("/topic/create/{topic}")
    public Api smsNotification(@PathVariable String topic){
        return amazonSNSService.createTopic(topic);
    }
    @PostMapping("/topic/delete/{arn}")
    public Api deleteTopic(@PathVariable String arn){
        return amazonSNSService.deleteTopic(arn);
    }
    @GetMapping("/topic/list")
    public List<Topic> getTopics(){
        return amazonSNSService.listTopics();
    }
    @GetMapping("/subscribers/{topicArn}")
    public List<Subscription> subscribers(String topicArn){
        return amazonSNSService.subscribersList(topicArn);
    }

    @PostMapping("/sandbox/addPhoneNumber/{phoneNumber}/{languageCode}")
    public Api addPhoneNumber(@PathVariable String phoneNumber,@PathVariable String languageCode){
        return amazonSNSService.createSMSSandboxPhoneNumber(phoneNumber,languageCode);
    }
    @PostMapping("/sandbox/verify/{phoneNumber}/{otp}")
    public Api verifyPhoneNumber(@PathVariable String phoneNumber,@PathVariable String otp){
        return amazonSNSService.verifySmsSandboxPhoneNumber(phoneNumber,otp);
    }

    @GetMapping("/sms/settings/{topicArn}")
    public Map<Object, Object> smsSettings(@PathVariable String topicArn){
        return amazonSNSService.smsSettings(topicArn);
    }
    @GetMapping("/sms/opted/{phoneNumber}")
    public Api checkIfPhoneNumberIsOptedOut(@PathVariable String phoneNumber){
        return amazonSNSService.checkIfPhoneNumberIsOptedOut(phoneNumber);
    }
    @GetMapping("/sms/opted/phoneNumbers")
    public List<String> optedOutPhoneNumbers(){
        return amazonSNSService.optedOutPhoneNumbers();
    }

    @PostMapping("/sms/send")
    public Api publishSms(@RequestBody SmsNotification smsNotification){
        return amazonSNSService.publishSms(smsNotification);
    }
    @GetMapping("/sandbox/phoneNumbers")
    public List<SMSSandboxPhoneNumber> listSmsSandboxPhoneNumbers(){
        return amazonSNSService.listSmsSandboxPhoneNumbers();
    }
}
