package in.cropdata.aefc.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author RinkeshKM
 * @date 05/12/21
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ResponseMessage {

    private int statusCode;
    private boolean status;
    private String message;
    private Object data;

    public ResponseMessage() {
    }

}
