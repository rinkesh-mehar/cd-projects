package in.cropdata.aefc.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.aefc.aefc.entity
 * @date 05/12/21
 * @time 5:58 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class JwtRequest implements Serializable
{
    private static final long serialVersionUID = 5926468583005150707L;

    private String emailId;
    private String mobile;
    private String password;
}
