package in.cropdata.toolsuite.filemanager.utils;

import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;

import in.cropdata.toolsuite.filemanager.model.Directory;
import in.cropdata.toolsuite.filemanager.model.FileMetadata;
import in.cropdata.toolsuite.filemanager.model.SearchCache;
import in.cropdata.toolsuite.filemanager.model.ZipFileMetaData;

public class ModelMapperUtil {

	/**
	 * 
	 * @param t - JSON object as a String
	 * @return FileMetadata
	 */
	public static FileMetadata metadataJsonToFileMetadata(String t) {

		FileMetadata map = new FileMetadata();

		if (t != null && !t.equals("")) {
			JSONObject jObject = new JSONObject(t);
			Iterator<?> keys = jObject.keys();

			while (keys.hasNext()) {
				try {
					String key = (String) keys.next();
					Object value = jObject.get(key);
					if (value != null) {
						map.put(key, value);
					}
				} catch (Exception e) {
					throw new RuntimeException("JSON Parse Exception");
				}

			}
		}
		return map;
	}// metadataJsonToFileMetadata

	/**
	 * It converts metadata string into {@link ZipFileMetaData} object.
	 * 
	 * @param metadata - JSON object as a String
	 * 
	 * @return {@link ZipFileMetaData}
	 * 
	 * @author PranaySK
	 */
	public static ZipFileMetaData metadataJsonToZipFileMetaData(String metadata) {
		ZipFileMetaData zipFileMetaData = new ZipFileMetaData();

		if (metadata != null && !metadata.equals("")) {
			JSONObject jObject = new JSONObject(metadata);
			Iterator<?> keys = jObject.keys();

			while (keys.hasNext()) {
				try {
					String key = (String) keys.next();
					Object value = jObject.get(key);
					if (value != null) {
						zipFileMetaData.put(key, value);
					}
				} catch (Exception e) {
					throw new RuntimeException("JSON Parse Exception");
				}

			}
		}
		return zipFileMetaData;
	}// metadataJsonToZipFileMetaData

	/**
	 * 
	 * @param t - JSON object as a String
	 * @return FileMetadata
	 */
	public static Directory metadataJsonToDirectoryMetadata(String t) {

		Directory map = new Directory();

		if (t != null && !t.equals("")) {
			JSONObject jObject = new JSONObject(t);
			Iterator<?> keys = jObject.keys();

			while (keys.hasNext()) {
				String key = (String) keys.next();
				String value = String.valueOf(jObject.get(key));
				if (value != null && !value.isEmpty()) {
					map.put(key, value);
				}
			}
		}
		return map;
	}// metadataJsonToDirectoryMetadata

	/**
	 * 
	 * @param t   - JSON object as a String
	 * @param map - Directory
	 * @return FileMetadata
	 */
	public static Directory metadataJsonToDirectoryMetadata(String t, Directory map) {

		if (t != null && !t.equals("") && map != null) {
			JSONObject jObject = new JSONObject(t);
			Iterator<?> keys = jObject.keys();

			while (keys.hasNext()) {
				String key = (String) keys.next();
				String value = String.valueOf(jObject.get(key));
				if (value != null && !value.isEmpty()) {
					map.put(key, value);
				}
			}
		}
		return map;
	}// metadataJsonToFileMetadata

	/**
	 * 
	 * @param t   - JSON object as a String
	 * @param map - FileMetadata
	 * @return FileMetadata
	 */
	public static FileMetadata metadataJsonToFileMetadata(String t, FileMetadata map) {

		if (t != null && !t.equals("") && map != null) {
			JSONObject jObject = new JSONObject(t);
			Iterator<?> keys = jObject.keys();

			while (keys.hasNext()) {
				String key = (String) keys.next();
				String value = String.valueOf(jObject.get(key));
				if (value != null && !value.isEmpty()) {
					map.put(key, value);
				}
			}
		}
		return map;
	}// metadataJsonToFileMetadata

	/**
	 * It maps given metadata to the {@link ZipFileMetaData} object.
	 * 
	 * @param metadata    - JSON object as a String
	 * @param zipMetaData - ZipFileMetaData object
	 * 
	 * @return {@link ZipFileMetaData}
	 * 
	 * @author PranaySK
	 */
	public static ZipFileMetaData metadataJsonToZipFileMetaData(String metadata, ZipFileMetaData zipMetaData) {
		if (metadata != null && !metadata.equals("") && zipMetaData != null) {
			JSONObject jObject = new JSONObject(metadata);
			Iterator<?> keys = jObject.keys();
			while (keys.hasNext()) {
				String key = (String) keys.next();
				String value = String.valueOf(jObject.get(key));
				if (value != null && !value.isEmpty()) {
					zipMetaData.put(key, value);
				}
			}
		}
		return zipMetaData;
	}// metadataJsonToZipFileMetaData

	/**
	 * 
	 * @param dirMap    - Map<String, Object>
	 * @param directory - Directory
	 * @return {@link Directory}
	 */
	public static Directory metadataJsonToDirectoryMetadata(Map<String, Object> dirMap, Directory directory) {

		if (dirMap != null && directory != null) {
			directory.putAll(dirMap);
		}
		return directory;
	}// metadataJsonToDirectoryMetadata

	/**
	 * 
	 * @param fileMetadataMap - Map<String, Object>
	 * @param fileMetadata    - FileMetadata
	 * @return {@link FileMetadata}
	 */
	public static FileMetadata metadataJsonToFileMetadata(Map<String, Object> fileMetadataMap,
			FileMetadata fileMetadata) {

		if (fileMetadataMap != null && fileMetadata != null) {
			fileMetadata.putAll(fileMetadataMap);
		}
		return fileMetadata;
	}// metadataJsonToFileMetadata

	public static SearchCache metadataJsonToSearchCacheMetadata(Map<String, Object> searchCacheMetadataMap,
			SearchCache searchCache) {
		if (searchCache != null && searchCacheMetadataMap != null) {
			searchCache.putAll(searchCacheMetadataMap);
		}
		return searchCache;
	}// searchCacheMetadataMap

	public static ZipFileMetaData metadataJsonToZipFileMetaData(Map<String, Object> zipFileMetaDataMap,
			ZipFileMetaData zipFileMetaData) {
		if (zipFileMetaData != null && zipFileMetaDataMap != null) {
			zipFileMetaData.putAll(zipFileMetaDataMap);
		}
		return zipFileMetaData;
	}// metadataJsonToZipFileMetaData

	/**
	 * 
	 * @param metadataMap - Map<String,Object>
	 * @return {@link Directory}
	 */
	public static Directory metadataJsonToDirectoryMetadata(Map<String, Object> metadataMap) {

		Directory dirMap = new Directory();

		if (metadataMap != null) {
			dirMap.putAll(metadataMap);
		}
		return dirMap;
	}// metadataJsonToDirectoryMetadata

	/**
	 * 
	 * @param fileMetadataMap - Map<String,Object>
	 * @return {@link FileMetadata}
	 */
	public static FileMetadata metadataJsonToFileMetadata(Map<String, Object> metadataMap) {

		FileMetadata fileMetadataMap = new FileMetadata();

		if (metadataMap != null) {
			fileMetadataMap.putAll(metadataMap);
		}
		return fileMetadataMap;
	}// metadataJsonToFileMetadata

}// ModelMapperUtil
