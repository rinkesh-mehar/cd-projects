package in.cropdata.cdtmasterdata.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Map;

/**
 * @author RinkeshKM
 * @Date 10/12/20
 */

@Data
@JsonInclude(content = JsonInclude.Include.NON_EMPTY, value = JsonInclude.Include.NON_NULL)
public class DRKBugDto {

    private String errorCode;

    private String message;

    private String status;

    private Boolean success;

    private Map<String, String> data;

}
