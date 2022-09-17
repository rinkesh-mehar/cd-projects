/**
 * 
 */
package in.cropdata.toolsuite.drk.dto.cases;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Vivek Gajbhiye - Cropdata
 *
 * 27-Nov-2019
 */
@Data
@AllArgsConstructor
public class FileResponse {
    private String name;
    private String uri;
    private String type;
    private long size;
}
