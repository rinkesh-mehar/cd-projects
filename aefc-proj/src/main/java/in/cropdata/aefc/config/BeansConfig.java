package in.cropdata.aefc.config;

import com.sendgrid.SendGrid;
import in.cropdata.aefc.properties.AppProperties;
import in.cropdata.aefc.utils.ResponseMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author RinkeshKM
 * @date 05/12/21
 */


@Configuration
public class BeansConfig {

    @Autowired
    private AppProperties appProperties;

    @Bean
    public SendGrid getSendGrid() {
        return new SendGrid(appProperties.getSendgridApiKey());
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ResponseMessageUtil responseMessageUtil() {
        return new ResponseMessageUtil();
    }
}
