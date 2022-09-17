/**
 * 
 */
package in.cropdata.toolsuite.drk.model.cases;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Vivek Gajbhiye - Cropdata
 *
 *         30-Nov-2019
 */
@Data
@AllArgsConstructor
public class KMLData implements Serializable{
	@NotEmpty
	private MultipartFile zipFile;
	@NotEmpty
	private String data;

}
