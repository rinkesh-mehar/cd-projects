package in.cropdata.ms.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import in.cropdata.ms.model.Email;
import in.cropdata.ms.model.SesEmail;
import in.cropdata.ms.response.Api;
import in.cropdata.ms.response.ApiResponse;
import in.cropdata.ms.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.ses.model.SesException;
import software.amazon.awssdk.services.sesv2.SesV2Client;
import software.amazon.awssdk.services.sesv2.model.*;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sesv2.model.RawMessage;
import software.amazon.awssdk.services.sesv2.SesV2Client;
import software.amazon.awssdk.services.sesv2.model.EmailContent;
import software.amazon.awssdk.services.sesv2.model.SendEmailRequest;
import software.amazon.awssdk.services.sesv2.model.SesV2Exception;
import javax.activation.DataHandler;
import javax.activation.DataSource;
//import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.Properties;


/**
 * @author Vivek Gajbhiye
 */
@Service
public class AmazonSESService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AmazonSESService.class);

    @Autowired
    SesV2Client sesV2Client;

    @Autowired
    ApiResponse apiResponse;

    @Autowired
    ObjectMapper objectMapper;

    public Api createRecipientIdentity(String emailIdentity){
        try{
            CreateEmailIdentityRequest request = CreateEmailIdentityRequest.builder()
                    .emailIdentity(emailIdentity)
                    .build();
            CreateEmailIdentityResponse response = sesV2Client.createEmailIdentity(request);
            if(response.sdkHttpResponse().isSuccessful()){
                return apiResponse.response(true,"Email identity created",null);
            }else{
                return apiResponse.response(false,"Unable to create email identity",null);
            }
        }catch (SesException e){
            LOGGER.error("aws ses error {}", e.getMessage());
            return apiResponse.response(false,e.getMessage(),null);
        }
    }

    public Api sendEmail(SesEmail email){
        StringBuilder sb = new StringBuilder();
        sb.append(email.getDisplay()).append(" <").append(email.getSender()).append(">");
        Destination destination = Destination.builder()
                .toAddresses(email.getRecipient())
                .build();

        Content content = Content.builder()
                .data(email.getBodyHTML())
                .build();

        Content sub = Content.builder()
                .data(email.getSubject())
                .build();

        Body body = Body.builder()
                .html(content)
                .build();

        Message msg = Message.builder()
                .subject(sub)
                .body(body)
                .build();
        EmailContent emailContent = EmailContent.builder()
                .simple(msg)
                .build();

        SendEmailRequest emailRequest = SendEmailRequest.builder()
                .destination(destination)
                .content(emailContent)
                .fromEmailAddress(sb.toString())
                .build();
        try {
            LOGGER.info("Attempting to send an email through Amazon SES");
            SendEmailResponse response = sesV2Client.sendEmail(emailRequest);
            if(response.sdkHttpResponse().isSuccessful()){
                return apiResponse.response(true,"Email send",null);
            }else{
                return apiResponse.response(false,"unable to send email",null);
            }

        } catch (SesV2Exception e) {
            return apiResponse.response(false,e.getMessage(),null);
        }
    }


    public Api createDomainIdentity(String domain){
        try {
            DkimSigningAttributes dkim = DkimSigningAttributes.builder()
                    .nextSigningKeyLength(DkimSigningKeyLength.RSA_2048_BIT)
                    .build();
            CreateEmailIdentityRequest request = CreateEmailIdentityRequest.builder()
                    .emailIdentity(domain)
                    .dkimSigningAttributes(dkim)
                    .build();
            CreateEmailIdentityResponse emailIdentity = sesV2Client.createEmailIdentity(request);
            if(emailIdentity.sdkHttpResponse().isSuccessful()){
                return apiResponse.response(true,"Domain created",null);
            }else{
                return apiResponse.response(false,"unable to create domain ",null);
            }
        }catch (SesV2Exception e) {
            LOGGER.error("error occured while creating domain {}", e.getMessage());
            return apiResponse.response(false,e.getMessage(),null);
        }
    }
    public Api sendEmailAttachment(String emailSes, MultipartFile attachment) throws MessagingException, IOException {
        SesEmail email = objectMapper.readValue(emailSes, SesEmail.class);
        StringBuilder sb = new StringBuilder();
        sb.append(email.getDisplay()).append(" <").append(email.getSender()).append(">");
        File theFile = FileUtils.multipartToFile(attachment);
        byte[] fileContent = Files.readAllBytes(theFile.toPath());
        Session session = Session.getDefaultInstance(new Properties());
        MimeMessage message = new MimeMessage(session);
        message.setSubject(email.getSubject(), "UTF-8");
        message.setFrom(new InternetAddress(sb.toString()));
        message.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(email.getRecipient()));
        MimeMultipart msgBody = new MimeMultipart("alternative");
        MimeBodyPart wrap = new MimeBodyPart();
        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(email.getBodyHTML(), "text/html; charset=UTF-8");
        msgBody.addBodyPart(htmlPart);
        wrap.setContent(msgBody);
        MimeMultipart msg = new MimeMultipart("mixed");
        message.setContent(msg);
        msg.addBodyPart(wrap);
        MimeBodyPart att = new MimeBodyPart();
        DataSource fds = new ByteArrayDataSource(fileContent, "application/form-data");
        att.setDataHandler(new DataHandler(fds));
        att.setFileName(theFile.getName());
        msg.addBodyPart(att);
        try {

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            message.writeTo(outputStream);

            ByteBuffer buf = ByteBuffer.wrap(outputStream.toByteArray());
            byte[] arr = new byte[buf.remaining()];
            buf.get(arr);

            SdkBytes data = SdkBytes.fromByteArray(arr);
            RawMessage rawMessage = RawMessage.builder()
                    .data(data)
                    .build();

            EmailContent emailContent = EmailContent.builder()
                    .raw(rawMessage)
                    .build();

            SendEmailRequest request = SendEmailRequest.builder()
                    .content(emailContent)
                    .build();

            sesV2Client.sendEmail(request);
            return apiResponse.response(true,"Email send successfully ",null);
        } catch (SesV2Exception e ) {
            return apiResponse.response(false,e.getMessage(),null);
        }
    }






}
