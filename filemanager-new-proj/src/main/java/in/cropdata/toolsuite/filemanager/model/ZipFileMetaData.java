/**
 * 
 */
package in.cropdata.toolsuite.filemanager.model;

import org.bson.BasicBSONObject;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * @author vivek-cropdata
 *
 */

@Data
@Document(collection = "zip_file_metadata")
public class ZipFileMetaData extends BasicBSONObject {
	private static final long serialVersionUID = 1L;

	private String pathKey;
	private String dirPath;
	private String moduleName;
	private String fileType;
	private String fileName;
	private String searchQuery;
	private String publicUrl;
	private long createdAt;
}
