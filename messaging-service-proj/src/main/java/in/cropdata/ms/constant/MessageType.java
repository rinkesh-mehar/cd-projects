package in.cropdata.ms.constant;

import lombok.Getter;

/**
 * @author Vivek Gajbhiye
 */
@Getter
public enum MessageType {

    PROMOTIONAL_MESSAGE_TYPE("Promotional"),
    TRANSACTIONAL_MESSAGE_TYPE("Transactional");

    private String messageType;

    MessageType(String messageType) {
        this.messageType = messageType;
    }

}
