package in.cropdata.toolsuite.filemanager.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class FileUploadWrapper {

	private MultipartFile file;

	private String metadata;

}