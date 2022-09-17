package in.cropdata.ms.config;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.sesv2.SesV2Client;
import software.amazon.awssdk.services.sns.SnsClient;

/**
 * @author Vivek Gajbhiye
 */
@Configuration
public class AWSConfig {

    Region region = Region.AP_SOUTH_1;
//    @Value("${cloud.aws.region.static}")
//    private String region;

    @Value("${cloud.aws.credentials.access-key}")
    private String awsAccessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String awsSecretKey;

    private AwsBasicCredentials awsCreds(){
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(
                awsAccessKey,
                awsSecretKey);
        return awsCreds;
    }

    @Bean
    public SnsClient snsClient(){
       return SnsClient.builder()
                .region(region)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds()))
                .build();
    }

    @Bean
    public SesClient sesClient(){

        return SesClient.builder()
                .region(region)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds()))
                .build();
    }

    @Bean
    public SesV2Client sesv2Client(){
        return SesV2Client.builder()
                .region(region)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds()))
                .build();
    }

    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper om = new ObjectMapper();
//        om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        om.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return om;
    }

}
