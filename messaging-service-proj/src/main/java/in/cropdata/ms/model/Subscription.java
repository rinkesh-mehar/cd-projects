package in.cropdata.ms.model;

import lombok.Data;

/**
 * @author Vivek Gajbhiye
 */
@Data
public class Subscription {

    private String topicArn;
    private String protocol;
    private String endpoint;
}
