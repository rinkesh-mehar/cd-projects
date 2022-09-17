package in.cropdata.ms.controller;

import in.cropdata.ms.model.SesEmail;
import in.cropdata.ms.request.EmailRequest;
import in.cropdata.ms.response.Api;
import in.cropdata.ms.service.AmazonSESService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;

/**
 * @author Vivek Gajbhiye
 */
@RestController
@RequestMapping("/aws/ses/")
public class AmazonSESController {

    @Autowired
    AmazonSESService amazonSESService;

    @PostMapping("/create/domain/{domain}")
    public Api createDomainIdentity(@PathVariable String domain){
        return amazonSESService.createDomainIdentity(domain);
    }
    @PostMapping("/email/send")
    public Api sendEmail(@RequestBody SesEmail email){
        return amazonSESService.sendEmail(email);
    }
    @PostMapping("/email/attachement/send")
    public Api sendEmailAttachment(@ModelAttribute EmailRequest emailRequest) throws MessagingException, IOException {
        return amazonSESService.sendEmailAttachment(emailRequest.getEmail(),emailRequest.getAttachment());
    }
}
