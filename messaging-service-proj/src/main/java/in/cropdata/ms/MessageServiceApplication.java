package in.cropdata.ms;


import in.cropdata.ms.service.AmazonSESService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import software.amazon.awssdk.services.sns.SnsClient;


@SpringBootApplication
public class MessageServiceApplication  {

    @Autowired
    SnsClient snsClient;

    @Autowired
    AmazonSESService amazonSESService;

    public static void main(String[] args) {
        SpringApplication.run(MessageServiceApplication.class, args);
    }

}
