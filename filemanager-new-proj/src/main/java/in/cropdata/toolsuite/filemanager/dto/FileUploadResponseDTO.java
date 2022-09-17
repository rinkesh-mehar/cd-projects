package in.cropdata.toolsuite.filemanager.dto;

import lombok.Data;

@Data
public class FileUploadResponseDTO {
    private boolean success;   
    private String id;
    private String publicUrl;
}
