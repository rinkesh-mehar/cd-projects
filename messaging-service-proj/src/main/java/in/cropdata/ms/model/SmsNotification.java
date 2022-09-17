package in.cropdata.ms.model;

import in.cropdata.ms.constant.MessageType;
import lombok.Data;

/**
 * @author Vivek Gajbhiye
 */
@Data
public class SmsNotification {

    private String message;
    private String phone;
}
