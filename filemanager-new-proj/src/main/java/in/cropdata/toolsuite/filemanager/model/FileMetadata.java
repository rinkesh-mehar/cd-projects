package in.cropdata.toolsuite.filemanager.model;

import org.bson.BasicBSONObject;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Document(collection = "file_metadata")
@Data
public class FileMetadata extends BasicBSONObject {

    // @Indexed(unique = true)
    // @JsonIgnore
    private String pathKey;

    @JsonIgnore
    private String dirPath;

    @JsonIgnore
    private String fileOrigionalName;
    
    @JsonIgnore
    private String moduleName;
    
    @JsonIgnore
    private String fileExtension;
    
    @JsonIgnore
    private String publicUrl;
}