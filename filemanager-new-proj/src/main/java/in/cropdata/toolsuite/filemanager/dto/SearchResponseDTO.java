package in.cropdata.toolsuite.filemanager.dto;

import lombok.Data;

@Data
public class SearchResponseDTO {
    
    private String filePath;

    private String _id;
    
    private String publicUrl;
    
    private String fileName;
}
