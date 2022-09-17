package in.cropdata.toolsuite.filemanager.model;

import java.util.Date;

import org.bson.BasicBSONObject;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "search_cache")
@Data
public class SearchCache extends BasicBSONObject {

    private String fileName;
    
    private String fileType;

    private String searchQuery;
    
    private String dirPath;

    private String moduleName;
    
    private String publicUrl;

    private long createdAt;

}