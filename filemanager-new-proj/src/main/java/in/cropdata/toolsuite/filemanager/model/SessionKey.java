package in.cropdata.toolsuite.filemanager.model;

import java.util.Date;

import org.bson.BasicBSONObject;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "session_keys")
@Data
public class SessionKey extends BasicBSONObject {

    @Indexed(unique = true)
    private String pathKey;

    private String filePath;

    private String dirPath;

    private String fileType;

    private Date addedOn;

}