package in.cropdata.ms.model;

import lombok.Data;

import java.util.Map;

/**
 * @author Vivek Gajbhiye
 */
@Data
public class Email {

    private String from;
    private String to;
    private String subject;
    private Map<String, Object> model;
}
