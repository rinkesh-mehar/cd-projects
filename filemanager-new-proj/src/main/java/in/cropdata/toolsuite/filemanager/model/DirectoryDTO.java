/**
 * 
 */
package in.cropdata.toolsuite.filemanager.model;

import com.microsoft.azure.storage.file.CloudFileDirectory;

import lombok.Data;

/**
 * @author cropdata-user
 *
 */
@Data
public class DirectoryDTO {

	private String moduleName;
	private String dirPath;
	private CloudFileDirectory cloudFileDirectory;

}
