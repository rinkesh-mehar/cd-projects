/**
 * 
 */
package in.cropdata.cdtmasterdata.website.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

/**
 * @author vivek-cropdata
 *
 */
@Data
public class FileUploadDTO {

	MultipartFile file;

}
