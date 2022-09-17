/**
 * 
 */
package in.cropdata.toolsuite.drk.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @author Vivek Gajbhiye - Cropdata
 *
 *         29-Nov-2019
 */
@Data
@Component
public class GstmProperties {

    @Value("${zip.exctract.file}")
    private String zipExctrzct;

}
