package in.cropdata.toolsuite.drk.model.tileassignment;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class FileUploadWrapper {
	@NotEmpty
	private MultipartFile image;

	@NotEmpty
	private String metadata;
}
