/**
 * 
 */
package in.cropdata.toolsuite.filemanager.controller;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microsoft.azure.storage.StorageException;

import in.cropdata.toolsuite.filemanager.dto.DirectoryResponseDTO;
import in.cropdata.toolsuite.filemanager.dto.FileUploadResponseDTO;
import in.cropdata.toolsuite.filemanager.exceptions.InvalidMetadataException;
import in.cropdata.toolsuite.filemanager.exceptions.PathKeyNotFoundException;
import in.cropdata.toolsuite.filemanager.model.FileUploadWrapper;
import in.cropdata.toolsuite.filemanager.service.FileManagerService;

/**
 * @author Siddhant Rangari
 * @author Vivek Gajbhiye
 * @author Pranay Khobragade
 */

@RestController
@CrossOrigin("*")
public class FileManagerController {

	@Autowired
	private FileManagerService azureService;

	@PostMapping("/create-directory")
	public ResponseEntity<DirectoryResponseDTO> createDirectory(@RequestParam(required = true) String moduleName,
			@RequestParam(required = true) String pathKey, @RequestParam(required = true) String dirPath,
			@RequestBody(required = false) Map<String, Object> metadata) {
		DirectoryResponseDTO response = azureService.createDirectory(moduleName, pathKey, dirPath, metadata);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PostMapping("/upload")
	public ResponseEntity<FileUploadResponseDTO> uploadFile(@RequestParam(required = false) String pathKey,
			@RequestParam(required = false, defaultValue = "false") boolean renameToID,
			@RequestParam(required = false) String fileName,
			@RequestParam(required = false, defaultValue = "false") boolean overwrite,
			@ModelAttribute FileUploadWrapper fileWrapper) throws URISyntaxException, StorageException {
		if (fileWrapper != null && fileWrapper.getFile() == null) {
			throw new InvalidMetadataException("please send required parameters.");
		}
		FileUploadResponseDTO response = azureService.uploadFile(pathKey, renameToID, fileName, overwrite, fileWrapper);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PostMapping("/upload-zip-file")
	public ResponseEntity<FileUploadResponseDTO> uploadZipFile(@RequestParam(required = true) String pathKey,
			@RequestParam(required = false, defaultValue = "false") boolean renameToId,
			@RequestParam(required = false) String fileName,
			@RequestParam(required = false, defaultValue = "false") boolean overwrite,
			@ModelAttribute FileUploadWrapper fileWrapper) {

		if (Objects.isNull(pathKey)) {
			throw new InvalidMetadataException("pathKey is required.");
		}
		if (fileWrapper == null || fileWrapper.getFile() == null) {
			throw new InvalidMetadataException("file is required.");
		}
		FileUploadResponseDTO response = azureService.uploadZipFile(pathKey, renameToId, fileName, overwrite,
				fileWrapper);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	/**
	 * Uploads file to the Azure Blob by reading the file from the provided
	 * directory.
	 * 
	 * FileUploadResponseDTO uploadZipFileWithPath(String pathKey, String fileName,
	 * boolean overwrite, String absoluteFilePath, String zipFileMetadataJson)
	 * 
	 * @param pathKey          for retrieving the directory path
	 * @param fileName         the filename
	 * @param overwrite        to update the existing file metadata
	 * @param absoluteFilePath the directory path along with the filename to read
	 *                         the file to be uploaded
	 * @param metadataJson     the file metadata
	 * 
	 * @return FileUploadResponseDTO
	 */
	@PostMapping("/upload-zip-file-with-path")
	public ResponseEntity<FileUploadResponseDTO> uploadZipFile(@RequestParam(required = true) String pathKey,
			@RequestParam(required = false) String fileName,
			@RequestParam(required = false, defaultValue = "true") boolean overwrite,
			@RequestParam(required = true) String absoluteFilePath,
			@RequestParam(required = false) String metadataJson) {
		if (Objects.isNull(pathKey)) {
			throw new InvalidMetadataException("pathKey is required.");
		}
		if (Objects.isNull(absoluteFilePath)) {
			throw new InvalidMetadataException("absoluteFilePath is required.");
		}

		FileUploadResponseDTO response = azureService.uploadZipFileWithPath(pathKey, fileName, overwrite,
				absoluteFilePath, metadataJson);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping("/is-path-key-exist")
	public ResponseEntity<Map<String, Object>> isPathKeyExist(@RequestParam(required = false) String pathKey) {
		if (pathKey == null) {
			throw new PathKeyNotFoundException("path Key is required parameter.");
		}
		Map<String, Object> isExist = azureService.isPathKeyExist(pathKey);
		return new ResponseEntity<>(isExist, HttpStatus.OK);
	}

	@GetMapping("/is-file-exist")
	public ResponseEntity<Map<String, Object>> isFileExist(@RequestParam(required = false) String id,
			@RequestParam(required = false) String fileName) {
		Map<String, Object> isExist = azureService.isFileExist(id, fileName);
		return new ResponseEntity<>(isExist, HttpStatus.OK);
	}

	@GetMapping("/is-zip-file-exist")
	public ResponseEntity<Map<String, Object>> isZipFileExist(@RequestParam(required = false) String id,
			@RequestParam(required = false) String fileName,
			@RequestParam(required = false, defaultValue = "master-data") String moduleName) {

		return new ResponseEntity<>(azureService.isZipFileExist(id, fileName, moduleName), HttpStatus.OK);
	}

	@PostMapping("/update-directory-metadata")
	public ResponseEntity<Map<String, Object>> updateDirectoryMetadata(@RequestParam(required = false) String pathKey,
			@RequestBody(required = false) Map<String, Object> metadata) {
		if (pathKey == null) {
			throw new PathKeyNotFoundException("path Key is required parameter.");
		}

		if (metadata == null) {
			throw new InvalidMetadataException("metadata is required parameter.");
		}

		Map<String, Object> isExist = azureService.updateDirectoryMetadata(pathKey, metadata);
		return new ResponseEntity<>(isExist, HttpStatus.OK);
	}

	@PostMapping("/update-file-metadata")
	public ResponseEntity<Map<String, Object>> updateFileMetadata(@RequestParam(required = false) String id,
			@RequestBody(required = false) Map<String, Object> metadata) {
		if (id == null) {
			String msg = "id is required parameter.";
			throw new PathKeyNotFoundException(msg);
		}

		if (metadata == null) {
			throw new InvalidMetadataException("metadata is required parameter.");
		}

		Map<String, Object> isExist = azureService.updateFileMetadata(id, metadata);
		return new ResponseEntity<>(isExist, HttpStatus.OK);
	}

	@PostMapping("/update-search-cache-metadata")
	public ResponseEntity<Map<String, Object>> updateSearchCacheMetadata(@RequestParam(required = false) String id,
			@RequestBody(required = false) Map<String, Object> metadata) {
		if (id == null) {
			String msg = "id is required parameter.";
			throw new PathKeyNotFoundException(msg);
		}

		if (metadata == null) {
			throw new InvalidMetadataException("metadata is required parameter.");
		}

		Map<String, Object> isExist = azureService.updateSearchCacheMetadata(id, metadata);
		return new ResponseEntity<>(isExist, HttpStatus.OK);
	}

	@PostMapping("/update-zip-file-metadata")
	public ResponseEntity<Map<String, Object>> updateZipFileMetadata(@RequestParam(required = false) String id,
			@RequestBody(required = false) Map<String, Object> metadata) {
		if (id == null) {
			String msg = "id is required parameter.";
			throw new PathKeyNotFoundException(msg);
		}

		if (metadata == null) {
			throw new InvalidMetadataException("metadata is required parameter.");
		}

		Map<String, Object> isExist = azureService.updateZipFileMetadata(id, metadata);
		return new ResponseEntity<>(isExist, HttpStatus.OK);
	}

	@PostMapping("/search-and-get-file")
	public Map<String, Object> search(@RequestParam(required = true) String moduleName,
			@RequestBody Map<String, Object> searchQueryMap,
			@RequestParam(required = false, defaultValue = "0") int age) {

		return azureService.searchAndGetFile(moduleName, searchQueryMap, age);
	}

	@PostMapping("/search-and-get-zip-file")
	public Map<String, Object> search(@RequestParam(required = true) String moduleName,
			@RequestBody Map<String, Object> searchQueryMap,
			@RequestParam(required = false, defaultValue = "0") long timestamp,
			@RequestParam(required = true) String target) {

		return azureService.searchAndGetFile(moduleName, searchQueryMap, timestamp, target);
	}

	@PostMapping("/file-search-and-get-file")
	public Map<String, Object> fileSearch(@RequestParam(required = true) String moduleName,
			@RequestBody Map<String, Object> searchQueryMap,
			@RequestParam(required = false, defaultValue = "0") int age, @RequestParam(required = true) String target) {

		return azureService.fileSearchAndGetFile(moduleName, searchQueryMap, age, target);
	}

	@PostMapping("/only-file-upload")
	public String onlyUploadFile(@ModelAttribute FileUploadWrapper fileToUpload, @RequestParam String dirPath,@RequestParam String moduleName) {
		return azureService.uploadFileToBlob(fileToUpload.getFile(), dirPath, moduleName);
	}

}
