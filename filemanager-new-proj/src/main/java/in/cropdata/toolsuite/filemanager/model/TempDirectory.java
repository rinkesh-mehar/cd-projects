package in.cropdata.toolsuite.filemanager.model;

import java.util.Date;

import org.bson.BasicBSONObject;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "temp_directory")
@Data
public class TempDirectory extends BasicBSONObject {

    private String dirPath;

    private Date addedOn;

    private String searchQuery;

    private String moduleName;

}