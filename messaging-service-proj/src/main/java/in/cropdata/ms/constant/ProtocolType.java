package in.cropdata.ms.constant;

import lombok.Data;
import lombok.Getter;

/**
 * @author Vivek Gajbhiye
 */
public enum ProtocolType {


    SMS("sms"),
    EMAIL("email");

    private String protocol;

    ProtocolType(String protocol) {
        this.protocol = protocol;
    }

    public String getProtocol() {
        return protocol;
    }
}
