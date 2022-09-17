/**
 * 
 */
package in.cropdata.toolsuite.filemanager.service;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

//import com.azure.storage.blob.BlobClient;
//import com.azure.storage.blob.BlobContainerClient;
//import com.microsoft.azure.storage.StorageException;
//import com.microsoft.azure.storage.blob.BlobProperties;
//import com.microsoft.azure.storage.blob.CloudBlobContainer;
//import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

import in.cropdata.toolsuite.filemanager.dto.DirectoryResponseDTO;
import in.cropdata.toolsuite.filemanager.dto.FileUploadResponseDTO;
import in.cropdata.toolsuite.filemanager.dto.SearchResponseDTO;
import in.cropdata.toolsuite.filemanager.exceptions.DuplicatePathKeyException;
import in.cropdata.toolsuite.filemanager.exceptions.FileNotFoundException;
import in.cropdata.toolsuite.filemanager.exceptions.FileUploadException;
import in.cropdata.toolsuite.filemanager.exceptions.InvalidMetadataException;
import in.cropdata.toolsuite.filemanager.exceptions.ModuleNotFoundException;
import in.cropdata.toolsuite.filemanager.exceptions.PathKeyNotFoundException;
import in.cropdata.toolsuite.filemanager.model.Directory;
import in.cropdata.toolsuite.filemanager.model.FileMetadata;
import in.cropdata.toolsuite.filemanager.model.FileUploadWrapper;
import in.cropdata.toolsuite.filemanager.model.SearchCache;
import in.cropdata.toolsuite.filemanager.model.ZipFileMetaData;
import in.cropdata.toolsuite.filemanager.properties.Properties;
import in.cropdata.toolsuite.filemanager.repository.DirectoryRepository;
import in.cropdata.toolsuite.filemanager.repository.FileMetadataRepository;
import in.cropdata.toolsuite.filemanager.repository.SearchCacheRepository;
import in.cropdata.toolsuite.filemanager.repository.ZipFileMetaDataRepository;
import in.cropdata.toolsuite.filemanager.utils.ApiUtil;
import in.cropdata.toolsuite.filemanager.utils.FileUtil;
import in.cropdata.toolsuite.filemanager.utils.JsonUtil;
import in.cropdata.toolsuite.filemanager.utils.ModelMapperUtil;

/**
 * @author Siddhant Rangari
 * @author Vivek Gajbhiye
 * @author Pranay Khobragade
 */

@Service
public class FileManagerService {

	private static final Logger logger = LoggerFactory.getLogger(FileManagerService.class);

	@Autowired
	private Properties properties;

//	@Autowired
//	private BlobContainerClient containerClient;
//
//	@Autowired
//    CloudBlobContainer blobContainer;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private MongoOperations mongoOperations;

	@Autowired
	private DirectoryRepository directoryRepository;

	@Autowired
	private FileMetadataRepository fileMetadataRepository;

	@Autowired
	private SearchCacheRepository searchCacheRepository;

	@Autowired
	private ZipFileMetaDataRepository zipFileMetaDataRepository;

	/**
	 * Creates the directory in azure and inserts its metadata in MongoDB.
	 *
	 * @param moduleName the module name
	 * @param pathKey    the path key
	 * @param dirPath    the dir path
	 * @param metadata   the metadata
	 * 
	 * @return DirectoryResponseDTO having created directory response
	 */
	public DirectoryResponseDTO createDirectory(String moduleName, String pathKey, String dirPath,
			Map<String, Object> metadata) {

		DirectoryResponseDTO responseMessage = null;
		if (moduleName != null) {
			responseMessage = new DirectoryResponseDTO();
			// insert metadata into MongoDB's directories collection.
			// check pathKey existence in directories collection
			Directory directory = checkPathKeyExistenceInDirectoriesCollection(pathKey);
			if (directory != null) {
				String errorMsg = "Path Key is Duplicate";
				DuplicatePathKeyException exception = new DuplicatePathKeyException(errorMsg);
				logger.error(errorMsg, exception);
				throw exception;
			}

			if (metadata != null) {
				logger.info("Parsing Directory Metadata: {}", metadata);
				directory = ModelMapperUtil.metadataJsonToDirectoryMetadata(metadata);
			} else {
				directory = new Directory();
			}

			directory.put("moduleName", moduleName);
			directory.put("pathKey", pathKey);
			directory.put("dirPath", dirPath);
			long unixTimestamp = Instant.now().getEpochSecond();
			directory.put("createdAt", unixTimestamp);

			// insert into DB
			directoryRepository.save(directory);
			logger.info("Directory metadata saved : {}", directory);

			responseMessage.setSuccess(true);
			responseMessage.setDirPath(dirPath);
			String dir = moduleName + File.separator + dirPath;
			logger.info("Directory Created : {}", dir);

		} else {
			throw new ModuleNotFoundException("Module name is required");
		}
		return responseMessage;
	}// createDirectory

	/**
	 * Check path key existence in directories collection.
	 *
	 * @author Siddhant Rangari
	 * 
	 * @param pathKey the path key
	 * @return Directory
	 */
	private Directory checkPathKeyExistenceInDirectoriesCollection(String pathKey) {
		return directoryRepository.findByPathKey(pathKey);
	}

	/**
	 * This method is used for searching the MONGO DB collections to get the
	 * response according to the provided search query.
	 * 
	 * @param moduleName  the parent directory
	 * @param searchQuery the search query using which the result to be found
	 * @return List of SearchResponseDTO
	 */
	public List<SearchResponseDTO> search(final String moduleName, Map<String, Object> searchQuery) {
		List<SearchResponseDTO> foundList = new ArrayList<>();
		if (searchQuery != null && moduleName != null) {
			// convert searchQuery String to Map and put the moduleName into the Map
			searchQuery.put("moduleName", moduleName);
			// generate Query from the Map
			String queryJson = JsonUtil.objectToJSON(searchQuery);
			logger.info("Generated Query : {}", queryJson);

			logger.info("Converting Query to BSON");
			// convert queryJson to BSON object
			Bson bson = BasicDBObject.parse(queryJson);

			logger.info("Searching through Query : {}", queryJson);
			// get file_metadata collection reference
			MongoCollection<Document> fileCollection = mongoTemplate.getCollection("file_metadata");

			// get all documents from file_metadata collection via BSON object
			FindIterable<Document> docs = fileCollection.find(bson);
			logger.info("Retrieved Results : {}", docs);
			// iterate through all documents and add to the foundList
			for (Document doc : docs) {
				Object id = doc.get("_id");
				String azurePath = doc.getString("dirPath") + File.separator + doc.getString("fileName") + "."
						+ doc.getString("fileExtension");
				// create searchDTO object
				SearchResponseDTO responseDTO = new SearchResponseDTO();
				responseDTO.set_id(id.toString());
				responseDTO.setFilePath(azurePath);
				responseDTO.setPublicUrl(doc.getString("publicUrl"));
				responseDTO.setFileName(doc.getString("fileName") + "." + doc.getString("fileExtension"));
				// add to the foundList
				foundList.add(responseDTO);
			}
		} // if searchQuery & moduleName not null
		logger.info("Returning results from Search : {}", foundList);
		return foundList;
	}

	/**
	 * Uploads File to the Azure Blob.
	 * 
	 * FileUploadResponseDTO uploadFile(String dirPath, String pathKey,
	 * FileUploadWrapper fileUploadWrapper)
	 * 
	 * @param pathKey           - for retrieving the directory path.
	 * @param fileName
	 * @param overwrite
	 * @return FileUploadResponseDTO
	 * @throws URISyntaxException
	 */
	public FileUploadResponseDTO uploadFile(String pathKey, boolean renameToId, String fileName, boolean overwrite,
			FileUploadWrapper fileUploadWrapper) throws URISyntaxException {
		String moduleName;
		Directory directory;
		String dirPath;
//		BlobClient blobClient = null;
//		CloudBlockBlob cloudBlockBlob = null;

		if (pathKey != null && !pathKey.isEmpty()) {
			// get directory from pathKey
			directory = getDirPathFromPathKey(pathKey);
			if (directory != null) {
				// assign dirPath and moduleName
				dirPath = (String) directory.get("dirPath");// directory.get("moduleName") + File.separator +
				moduleName = (String) directory.get("moduleName");
			} else {// if not exist
				String errorMsg = "pathKey does not exist : " + pathKey;
				PathKeyNotFoundException exception = new PathKeyNotFoundException(errorMsg);
				logger.error(errorMsg, exception);
				throw exception;
			}
			logger.info("Directory by pathKey: {} \t Module : {}", directory, moduleName);
		} else {
			String errorMsg = "pathKey and dirPath both are missing.";
			FileUploadException exception = new FileUploadException(errorMsg);
			logger.error(errorMsg, exception);
			throw exception;
		}

		FileMetadata metadata = null;
		// retrieving metadata from request or else assign empty object
		if (fileUploadWrapper != null && fileUploadWrapper.getMetadata() != null) {
			logger.info("File-Metadata: {}", fileUploadWrapper.getMetadata());
			metadata = ModelMapperUtil.metadataJsonToFileMetadata(fileUploadWrapper.getMetadata());
			logger.info("FileMeta: {}", metadata.values());
		} else {
			metadata = new FileMetadata();
		}

		MultipartFile file = null;
		if (fileUploadWrapper != null) {
			file = fileUploadWrapper.getFile();
		}

		FileUploadResponseDTO responseMessage = new FileUploadResponseDTO();
		String filePath = null;
		if (fileName == null || StringUtils.isEmpty(fileName)) {
			fileName = file.getOriginalFilename();
		}
		// if overwrite attribute is given as true then get existing metadata object
		// from mongodb
		if (overwrite) {
			Query query = new Query(Criteria.where("moduleName").is(moduleName).and("dirPath").is(dirPath)
					.and("fileName").is(FileUtil.getFileName(fileName)));
			logger.info("query -> {}", query);
			metadata = mongoTemplate.findOne(query, FileMetadata.class, "file_metadata");
			if (metadata == null) {
				metadata = new FileMetadata();
			}
			metadata = ModelMapperUtil.metadataJsonToFileMetadata(fileUploadWrapper.getMetadata(), metadata);
		} else {// prepare fileName by increasing count
			List<FileMetadata> existFileList = fileMetadataRepository.findByDirPathAndFileOrigionalName(dirPath,
					file.getOriginalFilename());
			if (existFileList != null && !existFileList.isEmpty()) {
				fileName = FileUtil.getFileName(fileName) + "-" + existFileList.size() + "."
						+ FileUtil.getExtension(fileName);
			}
		}

		logger.info("MetaData: {} \t Directory: {}", metadata, directory);
		if (metadata != null && directory != null) {
			metadata.put("pathKey", directory.get("pathKey"));
			metadata.put("dirPath", dirPath);
			metadata.put("moduleName", moduleName);
			metadata.put("fileType", file.getContentType());
			metadata.put("fileName", FileUtil.getFileName(fileName));

			logger.error("FileName: {} \t NameWE: {} \t OName: {}", fileName, FileUtil.getFileName(fileName),
					file.getOriginalFilename());
			metadata.put("fileExtension", FileUtil.getExtension(fileName));
			metadata.put("fileOrigionalName", file.getOriginalFilename());
			long unixTimestamp = Instant.now().getEpochSecond();
			metadata.put("createdAt", unixTimestamp);
			logger.info("adding metadata to mongoDB : {}", metadata);

			FileMetadata saved = null;
			String _id = null;

			// if overwrite is true then using mongoTemoplate to update the existing
			// metadata.
			if (overwrite) {
				mongoTemplate.save(metadata);
				_id = ((ObjectId) metadata.get("_id")).toHexString();
				responseMessage.setId(_id);
			} else {
				saved = fileMetadataRepository.save(metadata);
				_id = ((ObjectId) saved.get("_id")).toHexString();
				responseMessage.setId(_id);
			}
			logger.info("Metadata Stored : {}", metadata);
			try {
				if (renameToId) {
					fileName = _id + "." + FileUtil.getExtension(fileName);
					metadata.put("fileName", FileUtil.getFileName(fileName));
				}

				if (!dirPath.endsWith(File.separator)) {
					filePath = moduleName + File.separator + dirPath + File.separator + fileName;
				} else {
					filePath = moduleName + File.separator + dirPath + fileName;
				}

				File fileuploadToCloud = FileManagerService.multipartToFile(file, file.getOriginalFilename());
				// prepare fileName to be uploaded in blob container
				// ('env/moduleName/diPath/fileName')
				fileName = moduleName + File.separator + dirPath + File.separator
						+ fileName;
				
				String blobUrl = "";
				
				if("image/svg+xml".equals(file.getContentType())) {
					
					logger.info("{}...If ContentType is image/svg+xml..{}");
					
					// get file reference (CloudBlockBlob)
//					cloudBlockBlob = blobContainer.getBlockBlobReference(fileName);
					
					//set content type property of azure blob
//					BlobProperties blobProperties = cloudBlockBlob.getProperties();
//					blobProperties.setContentType("image/svg+xml");
					
					logger.info("Uploading File : " + filePath);
					
					// Upload the file
					//cloudBlockBlob.upload(new FileInputStream(fileuploadToCloud), fileuploadToCloud.length());
//					cloudBlockBlob.uploadFromFile(fileuploadToCloud.getAbsolutePath());
					
					logger.info("getting Blob URL from azure");
					//get blobUrl from cloudBlockBlob
//					blobUrl = cloudBlockBlob.getUri().toString();
					
					logger.info("Blob URL: " + blobUrl.toString());
				

				}else {
					
					logger.info("{}...If ContentType is not image/svg+xml..{}");
					
					// get file reference (BlobClient)
//					blobClient = containerClient.getBlobClient(fileName);

					logger.info("Uploading File : {}", filePath);
					// Upload the file
//					blobClient.uploadFromFile(fileuploadToCloud.getAbsolutePath(), overwrite);

					logger.info("Getting Blob URL from azure");
					// get blobUrl from blobClient
//					blobUrl = blobClient.getBlobUrl();
					blobUrl = ApiUtil.decode(blobUrl);
					logger.info("Azure Blob URL -> {}", blobUrl);
					
				}
				
				// add blobUrl to metadata
				metadata.put("publicUrl", blobUrl);
				// set blobUrl to publicUrl in responseMessage
				responseMessage.setPublicUrl(blobUrl);
				// save metadata
				mongoTemplate.save(metadata);

				logger.info("File {} uploaded successfully.\t ID: {}", filePath, _id);
				responseMessage.setSuccess(true);
			} catch (IOException e) {
				logger.error("File Upload Failed : {}", file.getOriginalFilename(), e);
				fileMetadataRepository.deleteById(saved.getObjectId("_id"));
				logger.warn("metadata removed for id : {}", _id);
				responseMessage.setSuccess(false);
			}
		} else {
			logger.error("Failed to add Metadata for file : {}", filePath);
		}

		return responseMessage;
	}// uploadFile

	/**
	 * Uploads File to the Azure Blob.
	 * 
	 * FileUploadResponseDTO uploadZipFile(String dirPath, String pathKey,
	 * FileUploadWrapper fileUploadWrapper)
	 * 
	 * @param pathKey           for retrieving the directory path.
	 * @param renameToId        to rename file with its generated id from MongoDB
	 * @param fileName          the filename
	 * @param overwrite         to update the existing file metadata
	 * @param FileUploadWrapper holds file to be uploaded and metadata of that file
	 * 
	 * @return FileUploadResponseDTO
	 */
//	public FileUploadResponseDTO uploadZipFile(String pathKey, boolean renameToId, String fileName, boolean overwrite,
//			FileUploadWrapper fileUploadWrapper) {
//		String moduleName;
//		String dirPath;
//		BlobClient blobClient = null;
//
//		// get directory from pathKey
//		Directory directory = this.getDirPathFromPathKey(pathKey);
//		if (directory != null) {
//			// assign dirPath and moduleName
//			dirPath = (String) directory.get("dirPath");
//			moduleName = (String) directory.get("moduleName");
//		} else {
//			// if not exist
//			String errorMsg = "pathKey does not exist : " + pathKey;
//			PathKeyNotFoundException exception = new PathKeyNotFoundException(errorMsg);
//			logger.error(errorMsg, exception);
//			throw exception;
//		}
//		logger.info("Directory by pathKey: {} \t Module : {}", directory.toMap(), moduleName);
//
//		ZipFileMetaData zipMetadata = null;
//		// retrieving metadata from request or else assign empty object
//		if (fileUploadWrapper != null && fileUploadWrapper.getMetadata() != null) {
//			logger.info("Zip File Metadata -> {}", fileUploadWrapper.getMetadata().replaceAll("\\r?\\n|\\t", ""));
//			zipMetadata = ModelMapperUtil.metadataJsonToZipFileMetaData(fileUploadWrapper.getMetadata());
//			logger.info("zipMetadata -> {}", zipMetadata.values());
//		}
//
//		MultipartFile file = fileUploadWrapper.getFile();
//		if (fileName == null || StringUtils.isEmpty(fileName)) {
//			fileName = file.getOriginalFilename();
//		}
//
//		// if overwrite attribute is given as true then get existing metadata object
//		// from MongoDB
//		if (overwrite) {
//			Query query = new Query(Criteria.where("moduleName").is(moduleName).and("dirPath").is(dirPath)
//					.and("fileName").is(FileUtil.getFileName(fileName)));
//			logger.info("query -> {}", query);
//			zipMetadata = mongoTemplate.findOne(query, ZipFileMetaData.class, "zip_file_metadata");
//			if (zipMetadata == null) {
//				zipMetadata = new ZipFileMetaData();
//			}
//			zipMetadata = ModelMapperUtil.metadataJsonToZipFileMetaData(fileUploadWrapper.getMetadata(), zipMetadata);
//		} else {
//			// prepare fileName by increasing count
//			List<ZipFileMetaData> existZipFileList = zipFileMetaDataRepository.findByDirPathAndFileName(dirPath,
//					file.getName());
//			if (!existZipFileList.isEmpty()) {
//				fileName = FileUtil.getFileName(fileName) + "-" + existZipFileList.size() + "."
//						+ FileUtil.getExtension(fileName);
//			}
//		}
//
//		logger.info("Zip MetaData: {}", zipMetadata);
//		FileUploadResponseDTO responseDTO = new FileUploadResponseDTO();
//		if (zipMetadata != null) {
//			zipMetadata.put("pathKey", directory.get("pathKey"));
//			zipMetadata.put("dirPath", dirPath);
//			zipMetadata.put("moduleName", moduleName);
//			zipMetadata.put("fileType", file.getContentType());
//			zipMetadata.put("fileName", FileUtil.getFileName(fileName));
//			zipMetadata.put("fileExtension", FileUtil.getExtension(fileName));
//			long unixTimestamp = Instant.now().getEpochSecond();
//			zipMetadata.put("createdAt", unixTimestamp);
//			logger.info("Saving metadata to mongoDB : {}", zipMetadata.toMap());
//
//			String fileId = null;
//			String filePath = null;
//			ZipFileMetaData savedZipData = null;
//			// if overwrite is true then using mongoTemoplate to update the existing
//			// metadata
//			if (overwrite) {
//				mongoTemplate.save(zipMetadata);
//				fileId = ((ObjectId) zipMetadata.get("_id")).toHexString();
//				responseDTO.setId(fileId);
//			} else {
//				savedZipData = zipFileMetaDataRepository.save(zipMetadata);
//				fileId = ((ObjectId) savedZipData.get("_id")).toHexString();
//				responseDTO.setId(fileId);
//			}
//			try {
//				if (renameToId) {
//					fileName = fileId + "." + FileUtil.getExtension(fileName);
//					zipMetadata.put("fileName", FileUtil.getFileName(fileName));
//				}
//				if (!dirPath.endsWith(File.separator)) {
//					filePath = moduleName + File.separator + dirPath + File.separator + fileName;
//				} else {
//					filePath = moduleName + File.separator + dirPath + fileName;
//				}
//
//				File fileUploadToCloud = FileManagerService.multipartToFile(file, file.getName());
//				// prepare fileName to be uploaded in blob container
//				// ('env/moduleName/diPath/fileName')
//				fileName = moduleName + File.separator + dirPath + File.separator
//						+ fileName;
//				// get file reference (BlobClient)
//				blobClient = containerClient.getBlobClient(fileName);
//				// Upload the file
//				logger.info("Uploading File -> {}", filePath);
//				blobClient.uploadFromFile(fileUploadToCloud.getAbsolutePath(), overwrite);
//				// get blobUrl from blobClient
//				String blobUrl = blobClient.getBlobUrl();
//				blobUrl = ApiUtil.decode(blobUrl);
//				logger.info("Azure Blob URL -> {}", blobUrl);
//				// add blobUrl to metadata
//				zipMetadata.put("publicUrl", blobUrl);
//				// set blobUrl to publicUrl in responseMessage
//				responseDTO.setPublicUrl(blobUrl);
//				// save metadata
//				mongoTemplate.save(zipMetadata);
//
//				logger.info("File -> [{}] uploaded successfully with ID -> [{}]", filePath, fileId);
//				responseDTO.setSuccess(true);
//
//			} catch (IOException e) {
//				logger.error("File Upload Failed -> [{}]", file.getOriginalFilename(), e);
//				fileMetadataRepository.deleteById(savedZipData.getObjectId("_id"));
//				logger.warn("metadata removed for id -> [{}]", fileId);
//				responseDTO.setSuccess(false);
//			}
//		}
//
//		return responseDTO;
//	}// uploadZipFile

	/**
	 * Uploads file to the Azure Blob by reading the file from the provided
	 * directory.
	 * 
	 * FileUploadResponseDTO uploadZipFileWithPath(String pathKey, String fileName,
	 * boolean overwrite, String absoluteFilePath, String zipFileMetadataJson)
	 * 
	 * @param pathKey             for retrieving the directory path
	 * @param fileName            the filename
	 * @param overwrite           to update the existing file metadata
	 * @param absoluteFilePath    the directory path along with the filename to read
	 *                            the file to be uploaded
	 * @param zipFileMetadataJson the file metadata
	 * 
	 * @return FileUploadResponseDTO
	 */
//	public FileUploadResponseDTO uploadZipFileWithPath(String pathKey, String fileName, boolean overwrite,
//			String absoluteFilePath, String zipFileMetadataJson) {
//		String moduleName = null;
//		String dirPath = null;
//		// get directory from pathKey
//		Directory directory = this.getDirPathFromPathKey(pathKey);
//		if (directory != null) {
//			// assign dirPath and moduleName
//			dirPath = (String) directory.get("dirPath");
//			moduleName = (String) directory.get("moduleName");
//		} else {
//			// if not exist
//			String errorMsg = "pathKey does not exist : " + pathKey;
//			PathKeyNotFoundException exception = new PathKeyNotFoundException(errorMsg);
//			logger.error(errorMsg, exception);
//			throw exception;
//		}
//		logger.info("Directory by pathKey: {} and Module : {}", directory.toMap(), moduleName);
//
//		FileUploadResponseDTO responseDTO = new FileUploadResponseDTO();
//		try {
//			Path inputFilePath = Paths.get(absoluteFilePath);
//			File inputFile = inputFilePath.toFile();
//			MultipartFile file = this.getMultipartFileFromFile(inputFile);
//			if (fileName == null || StringUtils.isEmpty(fileName)) {
//				fileName = file.getOriginalFilename();
//			}
//
//			ZipFileMetaData zipMetadata = null;
//			// if overwrite attribute is given as true then get existing metadata object
//			// from MongoDB
//			if (overwrite) {
//				Query query = new Query(Criteria.where("moduleName").is(moduleName).and("dirPath").is(dirPath)
//						.and("fileName").is(FileUtil.getFileName(fileName)));
//				logger.info("query -> {}", query);
//				zipMetadata = mongoTemplate.findOne(query, ZipFileMetaData.class, "zip_file_metadata");
//				if (zipMetadata == null) {
//					zipMetadata = new ZipFileMetaData();
//				}
//				zipMetadata = ModelMapperUtil.metadataJsonToZipFileMetaData(zipFileMetadataJson, zipMetadata);
//			}
//
//			logger.info("Zip MetaData: {}", zipMetadata);
//			if (zipMetadata != null) {
//				zipMetadata.put("pathKey", directory.get("pathKey"));
//				zipMetadata.put("dirPath", dirPath);
//				zipMetadata.put("moduleName", moduleName);
//				zipMetadata.put("fileType", Files.probeContentType(inputFilePath));
//				zipMetadata.put("fileName", FileUtil.getFileName(fileName));
//				zipMetadata.put("fileExtension", FileUtil.getExtension(fileName));
//				long unixTimestamp = Instant.now().getEpochSecond();
//				zipMetadata.put("createdAt", unixTimestamp);
//				logger.info("Saving metadata to mongoDB : {}", zipMetadata.toMap());
//
//				/**
//				 * Prepare fileName to be uploaded in blob container
//				 * ('env/moduleName/diPath/fileName')
//				 */
//				fileName = moduleName + File.separator + dirPath + File.separator
//						+ fileName;
//				logger.info("Uploading File -> {}", fileName);
//				// get file reference (BlobClient)
//				BlobClient blobClient = containerClient.getBlobClient(fileName);
//				// Upload the file
//				blobClient.uploadFromFile(inputFile.getAbsolutePath(), overwrite);
//				// get blobUrl from blobClient
//				String blobUrl = blobClient.getBlobUrl();
//				blobUrl = ApiUtil.decode(blobUrl);
//				logger.info("Azure Blob URL -> {}", blobUrl);
//				// add blobUrl to metadata
//				zipMetadata.put("publicUrl", blobUrl);
//				// set blobUrl to publicUrl in responseMessage
//				responseDTO.setPublicUrl(blobUrl);
//				/**
//				 * Save metadata. If overwrite is true then using mongoTemoplate update the
//				 * existing metadata. Otherwise using repository to save the metadata.
//				 */
//				String fileId = null;
//				ZipFileMetaData savedZipData = null;
//				if (overwrite) {
//					savedZipData = mongoTemplate.save(zipMetadata);
//					fileId = ((ObjectId) savedZipData.get("_id")).toHexString();
//					responseDTO.setId(fileId);
//				} else {
//					savedZipData = zipFileMetaDataRepository.save(zipMetadata);
//					fileId = ((ObjectId) savedZipData.get("_id")).toHexString();
//					responseDTO.setId(fileId);
//				}
//
//				logger.info("File -> [{}] uploaded successfully with ID -> [{}]", fileName, fileId);
//				responseDTO.setSuccess(true);
//			}
//		} catch (IOException ioe) {
//			logger.error("File Upload Failed -> [{}]", absoluteFilePath, ioe);
//			responseDTO.setSuccess(false);
//		}
//
//		return responseDTO;
//	}// uploadZipFileWithPath

	/**
	 * getDirPathFromPathKey(String pathKey)
	 * 
	 * @param pathKey
	 * @return String
	 */
	private Directory getDirPathFromPathKey(String pathKey) {
		if (pathKey != null) {
			return directoryRepository.findByPathKey(pathKey);
		}
		return null;
	}

	/**
	 * Throw path key does not exist exception if the pathKey is wrong or not
	 * available in directories collection.
	 *
	 * @author Siddhant Rangari
	 */
	/*
	 * private void throwPathKeyDoesNotExist(String pathKey) { String errorMsg =
	 * "pathKey does not exist : " + pathKey; if (pathKey == null ||
	 * StringUtils.isEmpty(pathKey)) { errorMsg = "pathKey cannot be empty.!!!"; }
	 * PathKeyNotFoundException exception = new PathKeyNotFoundException(errorMsg);
	 * logger.error(errorMsg, exception); throw exception; }
	 */

	public static File multipartToFile(MultipartFile multipart, String fileName) throws IOException {
		File convFile = new File(System.getProperty("java.io.tmpdir") + File.separator + fileName);
		multipart.transferTo(convFile);
		return convFile;
	}

	/**
	 * To get {@link MultipartFile} file from the provided input {@link File} file.
	 * It converts input file to Multipart file file using
	 * {@link CommonsMultipartFile}.
	 * 
	 * @param inputFile the source file to be converted
	 * 
	 * @return {@link MultipartFile}
	 * 
	 * @author PranaySK
	 */
	public MultipartFile getMultipartFileFromFile(File inputFile) throws IOException {
		FileItem fileItem = new DiskFileItem(inputFile.getName(), Files.probeContentType(inputFile.toPath()), false,
				inputFile.getName(), (int) inputFile.length(), inputFile.getParentFile());
		try (InputStream inStream = new FileInputStream(inputFile);
				OutputStream outStream = fileItem.getOutputStream()) {
			inStream.transferTo(outStream);
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid file: " + e.getLocalizedMessage(), e);
		}

		return new CommonsMultipartFile(fileItem);
	}

	/**
	 * Map<String, Object> updateDirectoryMetadata(String pathKey, String metadata)
	 * 
	 * @param pathKey
	 * @param metadata
	 * 
	 * @return Map<String, Object> - metadata object
	 */
	public Map<String, Object> updateDirectoryMetadata(String pathKey, Map<String, Object> metadata) {

		if (metadata == null) {
			throw new InvalidMetadataException("Metadata is not valid.");
		}

		Map<String, Object> map = new HashMap<>();
		map.put("success", false);
		try {
			logger.info("checking pathkey: {}", pathKey);
			Directory directory = directoryRepository.findByPathKey(pathKey);
			if (directory != null) {
				map.put("success", true);

				directory = ModelMapperUtil.metadataJsonToDirectoryMetadata(metadata, directory);
				mongoTemplate.save(directory);

				String _id = ((ObjectId) directory.get("_id")).toHexString();
				directory.put("_id", _id);
				map.put("metadata", directory);
			} else {
				String errorMsg = "pathKey does not exist : " + pathKey;
				PathKeyNotFoundException exception = new PathKeyNotFoundException(errorMsg);
				logger.error(errorMsg, exception);
				throw exception;
			}
		} catch (Exception e) {
			logger.error("Failed to Check Directory", e);
		}
		return map;
	}// updateDirectoryMetadata

	public boolean isJSONValid(String test) {
		try {
			new JSONObject(test);
		} catch (JSONException ex) {
			try {
				new JSONArray(test);
			} catch (JSONException ex1) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Map<String, Object> updateFileMetadata(String _id, String metadata)
	 * 
	 * @param metadata
	 * 
	 * @return Map<String, Object> - metadata object
	 */
	public Map<String, Object> updateFileMetadata(String id, Map<String, Object> metadata) {

		if (metadata == null) {
			throw new InvalidMetadataException("Metadata is not valid.");
		}

		Map<String, Object> map = new HashMap<>();
		map.put("success", false);
		try {
			logger.info("checking _id: {}", id);
			Optional<FileMetadata> fileMetadataFound = fileMetadataRepository.findById(new ObjectId(id));
			if (fileMetadataFound.isPresent()) {
				map.put("success", true);
				FileMetadata fileMetadata = ModelMapperUtil.metadataJsonToFileMetadata(metadata,
						fileMetadataFound.get());
				mongoTemplate.save(fileMetadata);

				String _id = ((ObjectId) fileMetadata.get("_id")).toHexString();
				fileMetadata.put("_id", _id);
				map.put("metadata", fileMetadata);
			} else {
				String errorMsg = "File does not exist with _id : " + id;
				FileNotFoundException exception = new FileNotFoundException(errorMsg);
				logger.error(errorMsg, exception);
				throw exception;
			}
		} catch (Exception e) {
			logger.error("Failed to Update File metadata", e);
		}
		return map;
	}// updateFileMetadata

	/**
	 * Map<String, Object> updateSearchCacheMetadata(String _id, String metadata)
	 * 
	 * @param metadata
	 * 
	 * @return Map<String, Object> - metadata object
	 */
	public Map<String, Object> updateSearchCacheMetadata(String id, Map<String, Object> metadata) {

		if (metadata == null) {
			throw new InvalidMetadataException("Metadata is not valid.");
		}

		Map<String, Object> map = new HashMap<>();
		map.put("success", false);
		try {
			logger.info("checking _id: {}", id);
			Optional<SearchCache> fileMetadataFound = this.searchCacheRepository.findById(new ObjectId(id));
			if (fileMetadataFound.isPresent()) {
				map.put("success", true);
				SearchCache searchCache = ModelMapperUtil.metadataJsonToSearchCacheMetadata(metadata,
						fileMetadataFound.get());
				mongoTemplate.save(searchCache);

				String _id = ((ObjectId) searchCache.get("_id")).toHexString();
				searchCache.put("_id", _id);
				map.put("metadata", searchCache);
			} else {
				String errorMsg = "File does not exist with _id : " + id;
				FileNotFoundException exception = new FileNotFoundException(errorMsg);
				logger.error(errorMsg, exception);
				throw exception;
			}
		} catch (Exception e) {
			logger.error("Failed to Check File", e);
		}
		return map;
	}// updateSearchCacheMetadata

	/**
	 * Map<String, Object> updateZipFileMetadata(String _id, String metadata)
	 * 
	 * @param metadata
	 * 
	 * @return Map<String, Object> - metadata object
	 */
	public Map<String, Object> updateZipFileMetadata(String id, Map<String, Object> metadata) {
		if (metadata == null) {
			throw new InvalidMetadataException("Metadata is not valid.");
		}
		Map<String, Object> map = new HashMap<>();
		map.put("success", false);
		try {
			logger.info("checking _id: {}", id);
			Optional<ZipFileMetaData> fileMetadataFound = this.zipFileMetaDataRepository.findById(new ObjectId(id));
			if (fileMetadataFound.isPresent()) {
				map.put("success", true);
				ZipFileMetaData zipFileMetaData = ModelMapperUtil.metadataJsonToZipFileMetaData(metadata,
						fileMetadataFound.get());
				mongoTemplate.save(zipFileMetaData);

				String _id = ((ObjectId) zipFileMetaData.get("_id")).toHexString();
				zipFileMetaData.put("_id", _id);
				map.put("metadata", zipFileMetaData);
			} else {
				String errorMsg = "File does not exist with _id : " + id;
				FileNotFoundException exception = new FileNotFoundException(errorMsg);
				logger.error(errorMsg, exception);
				throw exception;
			}
		} catch (Exception e) {
			logger.error("Failed to update zip file", e);
		}
		return map;
	}// updateZipFileMetadata

	/**
	 * Map<String, Object> isFileExist(String id,String fileName)
	 * 
	 * @param id
	 * @param fileName
	 * @return Map<String, Object> - metadata object
	 */
	public Map<String, Object> isFileExist(String id, String fileName) {
		Map<String, Object> map = new HashMap<>();
		map.put("exist", false);
		map.put("success", false);
		FileMetadata fileMetadata = null;
		if (id != null && !StringUtils.isEmpty(id)) {
			try {
				logger.info("checking file with: {}", id);
				Query fileQuery = query(where("_id").is(id));
				fileMetadata = mongoOperations.findOne(fileQuery, FileMetadata.class);
			} catch (Exception e) {
				logger.error("Failed to check File Existence", e);
			}
		} else {
			if (fileName != null && !StringUtils.isEmpty(fileName)) {
				try {
					logger.info("checking file with : {}", fileName);
					Query fileQuery = query(where("fileName").is(fileName));
					fileMetadata = mongoOperations.findOne(fileQuery, FileMetadata.class);
				} catch (Exception e) {
					logger.error("Failed to check File Existence", e);
				}
			}
		} // else

		if (fileMetadata != null) {
			map.put("exist", true);
			map.put("success", true);
			String _id = ((ObjectId) fileMetadata.get("_id")).toHexString();
			fileMetadata.put("_id", _id);
			map.put("metadata", fileMetadata);
		}

		return map;
	}// isFileExist

	/**
	 * This method is used to search the existence of ZIP file in ZipFileMetaData
	 * collection.
	 * 
	 * @param id         the id of ZIP file
	 * @param fileName   the name of ZIP file
	 * @param moduleName the module name
	 * @return Map of metadata object
	 */
	public Map<String, Object> isZipFileExist(String id, String fileName, String moduleName) {
		Map<String, Object> map = new HashMap<>();
		map.put("exist", false);
		map.put("success", false);
		ZipFileMetaData zipFileMetaData = null;
		try {
			if (id != null && !StringUtils.isEmpty(id)) {
				logger.info("checking file with: {}", id);
				Optional<ZipFileMetaData> fileMetadataFound = this.zipFileMetaDataRepository.findById(new ObjectId(id));
				if (fileMetadataFound.isPresent()) {
					map.put("success", true);
					zipFileMetaData = fileMetadataFound.get();
				}
			} else if (fileName != null && !StringUtils.isEmpty(fileName)) {
				logger.info("checking file with : {}", fileName);
				Optional<ZipFileMetaData> fileMetadataFound = this.zipFileMetaDataRepository
						.findByFileNameAndModuleName(fileName, moduleName);
				if (fileMetadataFound.isPresent()) {
					map.put("success", true);
					zipFileMetaData = fileMetadataFound.get();
				}
			} else {
				String errorMsg = "File does not exist";
				FileNotFoundException exception = new FileNotFoundException(errorMsg);
				logger.error(errorMsg, exception);
				throw exception;
			}
		} catch (Exception e) {
			logger.error("Failed to check File Existence", e);
		}

		if (zipFileMetaData != null) {
			map.put("exist", true);
			map.put("success", true);
			String fileId = ((ObjectId) zipFileMetaData.get("_id")).toHexString();
			zipFileMetaData.put("_id", fileId);
			map.put("metadata", zipFileMetaData);
		}

		return map;
	}// isZipFileExist

	/**
	 * Map<String, Object> isPathKeyExist(String pathKey)
	 * 
	 * @param pathKey
	 * @return Map<String, Object> - metadata object
	 */
	public Map<String, Object> isPathKeyExist(String pathKey) {
		Map<String, Object> map = new HashMap<>();
		map.put("exist", false);
		try {
			logger.info("checking pathkey: {}", pathKey);
			Directory directory = directoryRepository.findByPathKey(pathKey);
			if (directory != null) {
				map.put("success", true);
				map.put("exist", true);
				String id = ((ObjectId) directory.get("_id")).toHexString();
				directory.put("_id", id);
				map.put("metadata", directory);
			}
		} catch (Exception e) {
			map.put("success", false);
			logger.error("Failed to Check Directory", e);
		}
		return map;
	}// isPathKeyExist

	/**
	 * It Searches the files in MongoDB through the searchQuery and download those
	 * found files from azure make a ZIP of these files and uploads that ZIP to
	 * azure and return the _id of that file.
	 * 
	 * @param moduleName
	 * @param minutes
	 * @return FileUploadResponseDTO containes the uploaded files _id and publicUrl
	 *
	 * @author Siddhant Rangari
	 */
	public Map<String, Object> searchAndGetFile(String moduleName, Map<String, Object> searchQueryMap, int minutes) {

		if (moduleName != null && searchQueryMap != null) {
			// convert queryMap to json string
			String searchQuery = JsonUtil.objectToJSON(searchQueryMap);
			// if the search data is available for the same search query with the given
			// minutes then retrieve and return that result.
			if (minutes > 0) {
				// create old time epoch seconds by substracting the given minutes
				long oldTime = Instant.now().minus(minutes, ChronoUnit.MINUTES).getEpochSecond();
				logger.info("Searching old results with timestamp : {}", oldTime);
				// search in mongoDBs search-cache collection
				List<SearchCache> foundData = searchCacheRepository
						.findByModuleNameAndSearchQueryAndCreatedAtGreaterThanOrderByCreatedAtDesc(moduleName,
								searchQuery, oldTime);

				boolean searchFound = foundData != null && !foundData.isEmpty();
				logger.info("Search result within the given time found : {}", searchFound);
				if (searchFound) {
					// return the foundDatas files ID and publicUrl
					SearchCache searchCache = foundData.get(0);
					String _id = ((ObjectId) searchCache.get("_id")).toHexString();
					Map<String, Object> res = new HashMap<>();
					res.put("success", true);
					res.put("_id", _id);
					res.put("publicUrl", searchCache.getString("publicUrl"));
					return res;
				} // if searchData found
			} // if minutes>0

			// get searched files
			List<SearchResponseDTO> searchedFiles = search(moduleName, searchQueryMap);
			logger.info("FOUND SEARCH : {}", searchedFiles);
			if (searchedFiles != null && !searchedFiles.isEmpty()) {
				// add URLs to the list
				byte[] buf = new byte[2048];
				// Create the ZIP file
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ZipOutputStream out = new ZipOutputStream(baos);
				// iterate and download all searched files to a ZIP
				for (SearchResponseDTO searchDTO : searchedFiles) {
					// create an InputStream with the publicUrl of the file
					InputStream inputStream;
					try {
						if (searchDTO.getPublicUrl() != null) {
							inputStream = new URL(searchDTO.getPublicUrl()).openStream();
							// create BifferedInputStrea object
							BufferedInputStream bis = new BufferedInputStream(inputStream);
							// Add ZIP entry to output stream
							File file = new File(searchDTO.getFileName());
							String entryname = file.getName();
							out.putNextEntry(new ZipEntry(entryname));
							int bytesRead;
							while ((bytesRead = bis.read(buf)) != -1) {
								out.write(buf, 0, bytesRead);
							}
							out.closeEntry();
							bis.close();
							inputStream.close();
						} // if url not null
					} catch (MalformedURLException e) {
						logger.error("MalformedURLException occured during add ZipEntry: ", e);
					} catch (IOException e) {
						logger.error("IOException occured during add ZipEntry:", e);
					}
				} // for all searched Files

				// flush and close all streams
				try {
					out.flush();
					baos.flush();
					out.close();
					baos.close();
				} catch (IOException e) {
					logger.error("IOException occured during add ZipEntry: ", e);
				}

				// uploads the stream of the zip file to the azure.
				if (searchedFiles != null && !searchedFiles.isEmpty()) {

				} // searchedFiles not null and size > 0

				// get currentTimeMillis
				String currentTimeMillis = String.valueOf(System.currentTimeMillis());

				// milliseconds as file name
				String fileName = currentTimeMillis + ".zip";

				String dirPath = "search-cache";
				// prepare fileName to be uploaded in blob container
				// ('env/moduleName/diPath/fileName')
				fileName = moduleName + File.separator + dirPath + File.separator
						+ fileName;

				// get file reference (BlobClient)
//				BlobClient blobClient = containerClient.getBlobClient(fileName);

				logger.info("Uploading File : {}", dirPath);
				// create input stream from baos
				InputStream zipInputStream = new ByteArrayInputStream(baos.toByteArray());

				// Upload ZIP files Stream to azure
//				blobClient.upload(zipInputStream, baos.size());

				logger.info("getting Blob URL from azure");
				// get blobUrl from blobClient
//				String blobUrl = blobClient.getBlobUrl();
//				logger.info("Blob URL: {}", blobUrl);

				// create SearchCache object to store in mongoDB
				SearchCache searchCache = new SearchCache();
				// add blobUrl to metadata
//				searchCache.put("publicUrl", ApiUtil.decode(blobUrl));
				searchCache.put("searchQuery", searchQuery);
				searchCache.put("moduleName", moduleName);
				searchCache.put("dirPath", dirPath);
				searchCache.put("createdAt", Instant.now().getEpochSecond());
				searchCache.put("fileName", FileUtil.getFileName(fileName));
				searchCache.put("fileExtension", FileUtil.getExtension(fileName));
				// saves the searchCache object in mogoDB's search_cache collection
				logger.info("Saving search metadata into search_");
				searchCache = searchCacheRepository.save(searchCache);

				// return the saved files ID and publicUrl
				String _id = ((ObjectId) searchCache.get("_id")).toHexString();
				Map<String, Object> res = new HashMap<>();
				res.put("success", true);
				res.put("_id", _id);
				res.put("publicUrl", searchCache.getString("publicUrl"));
				return res;

			} else {// no data found for the searchQuery
				logger.info("No data found for the given searchQuery: {}", searchQuery);
				Map<String, Object> responseMap = new HashMap<>();
				responseMap.put("success", false);
				responseMap.put("error", "No data found for the given searchQuery: " + searchQuery);
				return responseMap;
			}
		} else {
			// throw exception for missing parameters
			String errorMsg = "moduleName and searchQuery cannot be null.";
			RuntimeException exception = new RuntimeException(errorMsg);
			logger.error(errorMsg, exception);
			throw exception;
		}
	}// searchAndGetFile

	/**
	 * It Searches the files in MongoDB's zip_file_metadata collection through the
	 * searchQuery and download those found files from azure make a ZIP of these
	 * files and uploads that ZIP to azure and return the _id and publicUrl of that
	 * file.
	 * 
	 * @param moduleName
	 * @param minutes
	 * @return FileUploadResponseDTO containes the uploaded files _id and publicUrl
	 *
	 * @author Siddhant Rangari
	 */
	public Map<String, Object> fileSearchAndGetFile(String moduleName, Map<String, Object> searchQueryMap,
			int minutes) {

		if (moduleName != null && searchQueryMap != null) {

			// convert queryMap to json string
			String searchQuery = JsonUtil.objectToJSON(searchQueryMap);

			// if the search data is available for the same search query with the given
			// minutes then retrieve and return that result.
			if (minutes > 0) {
				// create old time epoch seconds by substracting the given minutes
				long oldTime = Instant.now().minus(minutes, ChronoUnit.MINUTES).getEpochSecond();
				logger.info("Searching old results with timestamp : " + oldTime);
				// search in mongoDBs search-cache collection
				List<FileMetadata> foundData = fileMetadataRepository.findByModuleNameAndFileExtension(moduleName,
						searchQuery);

				boolean searchFound = foundData != null && foundData.size() > 0;
				logger.info("Search result within the given time found : " + searchFound);
				if (searchFound) {
					// return the foundDatas files ID and publicUrl

					FileMetadata fileMetadata = foundData.get(0);
					String _id = ((ObjectId) fileMetadata.get("_id")).toHexString();
					Map<String, Object> res = new HashMap<>();
					res.put("success", true);
					res.put("_id", _id);
					res.put("publicUrl", fileMetadata.getString("publicUrl"));
					return res;
				} // if searchData found

			} // if minutes>0

			// get searched files
			List<SearchResponseDTO> searchedFiles = search(moduleName, searchQueryMap);
			logger.info("FOUND SEARCH : " + searchedFiles);
			if (searchedFiles != null && searchedFiles.size() > 0) {

				// add URLs to the list
				byte[] buf = new byte[2048];
				// Create the ZIP file
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ZipOutputStream out = new ZipOutputStream(baos);

				// iterate and download all searched files to a ZIP
				for (SearchResponseDTO searchDTO : searchedFiles) {

					// create an InputStream with the publicUrl of the file
					InputStream inputStream;
					try {
						if (searchDTO.getPublicUrl() != null) {
							inputStream = new URL(searchDTO.getPublicUrl()).openStream();
							// create BifferedInputStrea object
							BufferedInputStream bis = new BufferedInputStream(inputStream);

							// Add ZIP entry to output stream.
							File file = new File(searchDTO.getFileName());
							String entryname = file.getName();
							out.putNextEntry(new ZipEntry(entryname));
							int bytesRead;
							while ((bytesRead = bis.read(buf)) != -1) {
								out.write(buf, 0, bytesRead);
							}
							out.closeEntry();
							bis.close();
							inputStream.close();
						} // if url not null
					} catch (MalformedURLException e) {
						logger.error("MalformedURLException occured during add ZipEntry: ", e);
					} catch (IOException e) {
						logger.error("IOException occured during add ZipEntry: ", e);
					}
				} // for all searched Files

				// flush and close all streams
				try {
					out.flush();
					baos.flush();
					out.close();
					baos.close();
				} catch (IOException e) {
					logger.error("IOException occured during add ZipEntry: ", e);
				}

				// uploads the stream of the zip file to the azure.
				if (searchedFiles != null && searchedFiles.size() > 0) {

				} // searchedFiles not null and size > 0

				// get currentTimeMillis
				String currentTimeMillis = String.valueOf(System.currentTimeMillis());

				// milliseconds as file name
				String fileName = currentTimeMillis + ".zip";

				String dirPath = "search-cache";
				// prepare fileName to be uploaded in blob container
				// ('env/moduleName/diPath/fileName')
				fileName = moduleName + File.separator + dirPath + File.separator
						+ fileName;

				// get file reference (BlobClient)
//				BlobClient blobClient = containerClient.getBlobClient(fileName);

				logger.info("Uploading File : " + dirPath);

				// create input stream from baos
				InputStream zipInputStream = new ByteArrayInputStream(baos.toByteArray());

				// Upload ZIP files Stream to azure
//				blobClient.upload(zipInputStream, baos.size());

				logger.info("getting Blob URL from azure");
				// get blobUrl from blobClient
//				String blobUrl = blobClient.getBlobUrl();
//				logger.info("Blob URL: " + blobUrl);

				// create SearchCache object to store in mongoDB
				SearchCache searchCache = new SearchCache();

				// add blobUrl to metadata
//				searchCache.put("publicUrl", ApiUtil.decode(blobUrl));

				searchCache.put("searchQuery", searchQuery);
				searchCache.put("moduleName", moduleName);
				searchCache.put("dirPath", dirPath);
				searchCache.put("createdAt", Instant.now().getEpochSecond());
				searchCache.put("fileName", FileUtil.getFileName(fileName));
				searchCache.put("fileExtension", FileUtil.getExtension(fileName));

				// saves the searchCache object in mogoDB's search_cache collection
				logger.info("Saving search metadata into search_");
				searchCache = searchCacheRepository.save(searchCache);

				// return the saved files ID and publicUrl
				String _id = ((ObjectId) searchCache.get("_id")).toHexString();
				Map<String, Object> res = new HashMap<>();
				res.put("success", true);
				res.put("_id", _id);
				res.put("publicUrl", searchCache.getString("publicUrl"));
				return res;

			} else {// no data found for the searchQuery
				logger.info("No data found for the given searchQuery: " + searchQuery);
				Map<String, Object> responseMap = new HashMap<String, Object>();
				responseMap.put("success", false);
				responseMap.put("error", "No data found for the given searchQuery: " + searchQuery);
				return responseMap;
			} // else
		} else {// throw exception for missing parameters
			String errorMsg = "moduleName and searchQuery cannot be null.";
			RuntimeException exception = new RuntimeException(errorMsg);
			logger.error(errorMsg, exception);
			throw exception;
		}
	}// searchAndGetFile

	/**
	 * It Searches the files in MongoDB through the searchQuery and download those
	 * found files from azure make a ZIP of these files and uploads that ZIP to
	 * azure and return the _id of that file.
	 * 
	 * @param moduleName
	 * @param searchQuery
	 * @param minutes
	 * @param target
	 * @return Map contains the uploaded files _id and publicUrl
	 *
	 * @author Vivek Gajbhiye
	 */
//	public Map<String, Object> searchAndGetFile(String moduleName, Map<String, Object> searchQueryMap, long timestamp,
//			String target) {
//
//		if (moduleName != null && searchQueryMap != null) {
//			// convert queryMap to JSON string
//			String searchQuery = JsonUtil.objectToJSON(searchQueryMap);
//			// if the search data is available for the same search query with the given
//			// minutes then retrieve and return that result.
//			if (timestamp > 0) {
//				// create old time epoch seconds by subtracting the given minutes
//				long oldTime = Instant.now().minus(timestamp, ChronoUnit.MINUTES).getEpochSecond();
//				logger.info("Searching old results with timestamp : {}", oldTime);
//				// search in mongoDBs zip_file_metadata collection
//				List<ZipFileMetaData> foundData = zipFileMetaDataRepository
//						.findByModuleNameAndSearchQueryAndCreatedAtGreaterThanOrderByCreatedAtDesc(moduleName,
//								searchQuery, oldTime);
//				boolean searchFound = foundData != null && !foundData.isEmpty();
//				logger.info("Search result within the given time found : {}", searchFound);
//				if (searchFound) {
//					// return the foundDatas files ID and publicUrl
//					ZipFileMetaData zipFileMetaData = foundData.get(0);
//					String id = ((ObjectId) zipFileMetaData.get("_id")).toHexString();
//					Map<String, Object> res = new HashMap<>();
//					res.put("success", true);
//					res.put("_id", id);
//					res.put("publicUrl", zipFileMetaData.getString("publicUrl"));
//					return res;
//				} // if searchData found
//			}
//
//			// get searched files
//			List<SearchResponseDTO> searchedFiles = search(moduleName, searchQueryMap);
//			logger.info("FOUND SEARCH : {}", searchedFiles);
//			if (searchedFiles != null && !searchedFiles.isEmpty()) {
//				// add URLs to the list
//				byte[] buf = new byte[2048];
//				// Create the ZIP file
//				ByteArrayOutputStream baos = new ByteArrayOutputStream();
//				ZipOutputStream zipOutputStream = new ZipOutputStream(baos);
//				// iterate and download all searched files to a ZIP
//				for (SearchResponseDTO searchDTO : searchedFiles) {
//					// create an InputStream with the publicUrl of the file
//					try (InputStream inputStream = new URL(searchDTO.getPublicUrl()).openStream();
//							BufferedInputStream bis = new BufferedInputStream(inputStream);) {
//						if (searchDTO.getPublicUrl() != null) {
//							// Add ZIP entry to output stream.
//							File file = new File(searchDTO.getFileName());
//							String entryname = file.getName();
//							zipOutputStream.putNextEntry(new ZipEntry(entryname));
//							int bytesRead;
//							while ((bytesRead = bis.read(buf)) != -1) {
//								zipOutputStream.write(buf, 0, bytesRead);
//							}
//							zipOutputStream.closeEntry();
//						} // if url not null
//					} catch (MalformedURLException e) {
//						logger.error("MalformedURLException occured during add ZipEntry: ", e);
//					} catch (IOException e) {
//						logger.error("IOException occured during add ZipEntry: ", e);
//					}
//				} // for all searched Files
//
//				// flush and close all streams
//				try {
//					zipOutputStream.flush();
//					baos.flush();
//					zipOutputStream.close();
//					baos.close();
//				} catch (Exception e) {
//					logger.error("Exception occured during ZipEntry cleanup: ", e);
//				}
//
//				// get currentTimeMillis
//				String currentTimeMillis = String.valueOf(System.currentTimeMillis());
//				// milliseconds as file name
//				String fileName = currentTimeMillis + ".zip";
//				// prepare fileName to be uploaded in blob container
//				// ('env/moduleName/diPath/fileName')
//				fileName = moduleName + File.separator + target + File.separator
//						+ fileName;
//				// get file reference (BlobClient)
//				BlobClient blobClient = containerClient.getBlobClient(fileName);
//				logger.info("Uploading file to : {}", target);
//				// create input stream from baos
//				InputStream inputStream = new ByteArrayInputStream(baos.toByteArray());
//				// Upload ZIP files Stream to azure
//				blobClient.upload(inputStream, baos.size());
//				logger.info("getting Blob URL from azure");
//				// get blobUrl from blobClient
//				String blobUrl = blobClient.getBlobUrl();
//				logger.info("Blob URL: {}", blobUrl);
//
//				ZipFileMetaData zipFileMetaData = new ZipFileMetaData();
//
//				zipFileMetaData.put("publicUrl", ApiUtil.decode(blobUrl));
//				zipFileMetaData.put("searchQuery", searchQuery);
//				zipFileMetaData.put("moduleName", moduleName);
//				zipFileMetaData.put("dirPath", target);
//				zipFileMetaData.put("createdAt", Instant.now().getEpochSecond());
//				zipFileMetaData.put("fileName", FileUtil.getFileName(fileName));
//				zipFileMetaData.put("fileExtension", FileUtil.getExtension(fileName));
//				zipFileMetaData = zipFileMetaDataRepository.save(zipFileMetaData);
//
//				// return the saved files ID and publicUrl
//				String fileId = ((ObjectId) zipFileMetaData.get("_id")).toHexString();
//				Map<String, Object> res = new HashMap<>();
//				res.put("success", true);
//				res.put("_id", fileId);
//				res.put("publicUrl", zipFileMetaData.getString("publicUrl"));
//				return res;
//
//			} else {// no data found for the searchQuery
//				logger.info("No data found for the given searchQuery: {}", searchQuery);
//				Map<String, Object> responseMap = new HashMap<>();
//				responseMap.put("success", false);
//				responseMap.put("error", "No data found for the given searchQuery: " + searchQuery);
//				return responseMap;
//			} // else
//		} else {// throw exception for missing parameters
//			String errorMsg = "moduleName and searchQuery cannot be null.";
//			RuntimeException exception = new RuntimeException(errorMsg);
//			logger.error(errorMsg, exception);
//			throw exception;
//		}
//	}// searchAndGetFile

	/**
	 * It Searches the files in MongoDB through the searchQuery and download those
	 * found files from azure make a ZIP of these files and uploads that ZIP to
	 * azure and return the _id of that file.
	 * 
	 * @param moduleName
	 * @param searchQuery
	 * @param minutes
	 * @param target
	 * @return FileUploadResponseDTO containes the uploaded files _id and publicUrl
	 *
	 * @author Vivek Gajbhiye
	 */
//	public Map<String, Object> fileSearchAndGetFile(String moduleName, Map<String, Object> searchQueryMap,
//			long timestamp, String target) {
//
//		if (moduleName != null && searchQueryMap != null) {
//
//			// convert queryMap to json string
//			String searchQuery = JsonUtil.objectToJSON(searchQueryMap);
//
//			// if the search data is available for the same search query with the given
//			// minutes then retrieve and return that result.
//			if (timestamp > 0) {
//				// create old time epoch seconds by substracting the given minutes
//				long oldTime = Instant.now().minus(timestamp, ChronoUnit.MINUTES).getEpochSecond();
//				logger.info("Searching old results with timestamp : " + oldTime);
//				// search in mongoDBs search-cache collection
//				List<FileMetadata> foundData = fileMetadataRepository.findByModuleNameAndFileExtension(moduleName,
//						searchQuery);
//
//				boolean searchFound = foundData != null && foundData.size() > 0;
//				logger.info("Search result within the given time found : " + searchFound);
//				if (searchFound) {
//					// return the foundDatas files ID and publicUrl
//
//					FileMetadata fileMetadata = foundData.get(0);
//					String _id = ((ObjectId) fileMetadata.get("_id")).toHexString();
//					Map<String, Object> res = new HashMap<>();
//					res.put("success", true);
//					res.put("_id", _id);
//					res.put("publicUrl", fileMetadata.getString("publicUrl"));
//					return res;
//				} // if searchData found
//
//			} // if minutes>0
//
//			// get searched files
//			List<SearchResponseDTO> searchedFiles = search(moduleName, searchQueryMap);
//			logger.info("FOUND SEARCH : " + searchedFiles);
//			if (searchedFiles != null && searchedFiles.size() > 0) {
//
//				// add URLs to the list
//				byte[] buf = new byte[2048];
//				// Create the ZIP file
//				ByteArrayOutputStream baos = new ByteArrayOutputStream();
//				ZipOutputStream out = new ZipOutputStream(baos);
//
//				// iterate and download all searched files to a ZIP
//				for (SearchResponseDTO searchDTO : searchedFiles) {
//
//					// create an InputStream with the publicUrl of the file
//					InputStream inputStream;
//					try {
//						if (searchDTO.getPublicUrl() != null) {
//							inputStream = new URL(searchDTO.getPublicUrl()).openStream();
//							// create BifferedInputStrea object
//							BufferedInputStream bis = new BufferedInputStream(inputStream);
//
//							// Add ZIP entry to output stream.
//							File file = new File(searchDTO.getFileName());
//							String entryname = file.getName();
//							out.putNextEntry(new ZipEntry(entryname));
//							int bytesRead;
//							while ((bytesRead = bis.read(buf)) != -1) {
//								out.write(buf, 0, bytesRead);
//							}
//							out.closeEntry();
//							bis.close();
//							inputStream.close();
//						} // if url not null
//					} catch (MalformedURLException e) {
//						logger.error("MalformedURLException occured during add ZipEntry: ", e);
//					} catch (IOException e) {
//						logger.error("IOException occured during add ZipEntry: ", e);
//					}
//				} // for all searched Files
//
//				// flush and close all streams
//				try {
//					out.flush();
//					baos.flush();
//					out.close();
//					baos.close();
//				} catch (IOException e) {
//					logger.error("IOException occured during add ZipEntry: ", e);
//				}
//
//				// uploads the stream of the zip file to the azure.
//				if (searchedFiles != null && searchedFiles.size() > 0) {
//
//				} // searchedFiles not null and size > 0
//
//				// get currentTimeMillis
//				String currentTimeMillis = String.valueOf(System.currentTimeMillis());
//
//				// milliseconds as file name
//				String fileName = currentTimeMillis + ".zip";
//
//				// prepare fileName to be uploaded in blob container
//				// ('env/moduleName/diPath/fileName')
//				fileName = moduleName + File.separator + target + File.separator
//						+ fileName;
//
//				// get file reference (BlobClient)
//				BlobClient blobClient = containerClient.getBlobClient(fileName);
//
//				logger.info("Uploading File : " + target);
//
//				// create input stream from baos
//				InputStream zipInputStream = new ByteArrayInputStream(baos.toByteArray());
//
//				// Upload ZIP files Stream to azure
//				blobClient.upload(zipInputStream, baos.size());
//
//				logger.info("getting Blob URL from azure");
//				// get blobUrl from blobClient
//				String blobUrl = blobClient.getBlobUrl();
//				logger.info("Blob URL: " + blobUrl);
//
//				ZipFileMetaData zipFileMetaData = new ZipFileMetaData();
//
//				zipFileMetaData.put("publicUrl", ApiUtil.decode(blobUrl));
//				zipFileMetaData.put("searchQuery", searchQuery);
//				zipFileMetaData.put("moduleName", moduleName);
//				zipFileMetaData.put("dirPath", target);
//				zipFileMetaData.put("createdAt", Instant.now().getEpochSecond());
//				zipFileMetaData.put("fileName", FileUtil.getFileName(fileName));
//				zipFileMetaData.put("fileExtension", FileUtil.getExtension(fileName));
//				zipFileMetaData = zipFileMetaDataRepository.save(zipFileMetaData);
//
//				// return the saved files ID and publicUrl
//				String id = ((ObjectId) zipFileMetaData.get("_id")).toHexString();
//				Map<String, Object> res = new HashMap<>();
//				res.put("success", true);
//				res.put("_id", id);
//				res.put("publicUrl", zipFileMetaData.getString("publicUrl"));
//				return res;
//
//			} else {
//				// no data found for the searchQuery
//				logger.info("No data found for the given searchQuery: {}", searchQuery);
//				Map<String, Object> responseMap = new HashMap<>();
//				responseMap.put("success", false);
//				responseMap.put("error", "No data found for the given searchQuery: " + searchQuery);
//				return responseMap;
//			}
//		} else {
//			// throw exception for missing parameters
//			String errorMsg = "moduleName and searchQuery cannot be null.";
//			RuntimeException exception = new RuntimeException(errorMsg);
//			logger.error(errorMsg, exception);
//			throw exception;
//		}
//	}// searchAndGetFile
	
//	public String uploadFileToBlob(MultipartFile file, String dirPath,String moduleName) {
//		BlobClient blobClient = null;
//		try {
//			File multipartToFile = multipartToFile(file, file.getName());
//			String fileName = moduleName + File.separator + dirPath;
//			blobClient = containerClient.getBlobClient(fileName);
//			blobClient.uploadFromFile(multipartToFile.getAbsolutePath(), true);
//			return URLDecoder.decode(blobClient.getBlobUrl(), StandardCharsets.UTF_8);
//
//		} catch (Exception ex) {
//			throw new FileUploadException(ex.getMessage());
//		}
//	}

}// class AzureService
