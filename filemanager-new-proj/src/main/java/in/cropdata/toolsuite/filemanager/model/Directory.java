package in.cropdata.toolsuite.filemanager.model;

import org.bson.BasicBSONObject;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "directories")
@Data
public class Directory extends BasicBSONObject {

    @Indexed(unique = true)
    private String pathKey;

    private String moduleName;
//
    private String dirPath;

    private String dirPathNew;

}