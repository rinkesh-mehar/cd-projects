package in.cropdata.ms.model;

import lombok.Data;

/**
 * @author Vivek Gajbhiye
 */
@Data
public class SesEmail {

    private String sender;
    private String recipient;
    private String subject;
    private String bodyHTML;
    private String display;
}
