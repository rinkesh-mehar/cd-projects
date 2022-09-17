package com.drk.tools.constants;

public class ApplicationConstants {
	public static final String UPLOADFILE_URL = "http://192.168.0.41:8764/upload";
	public static final String DIRECTORY_URL = "http://192.168.0.41:8764/is-path-key-exist?pathKey=drKrishiTools";

	public static final String SQLITE_QC_DB = "quantycalc.db";
	public static final String SQLITE_FLIP_DB = "flipbook.db";
//	public static final String SQLITE_FLIP_IMG_DB = "flipbook-images.db";

	public static final String SQLITE_QC_ZIP = "quantycalc.zip";
	public static final String SQLITE_FLIP_ZIP = "flipbook.zip";
//	public static final String SQLITE_FLIP_IMG_ZIP = "flipbook-images.zip";

	public static final String QTY_DIR_NAME = "syncfiles-quantycalc";
	public static final String FLIP_DIR_NAME = "syncfiles-flip";
	public static final String FLIP_IMG_DIR_NAME = "syncfiles-flipimg";

	public static final String FETCH_FILE_ID_URL = "http://192.168.0.41:8764/search";
	public static final String FILE_COLLECTION_URL = "http://192.168.0.41:8764/copy-to";
	public static final String FILE_COMPRESSION_URL = "http://192.168.0.41:8764/zip-dir";

	public static final String SUCCESS = "success";
	public static final String MESSAGE = "message";

	public static final String RESPONSE_INVALID_APIKEY = "Invalid Apikey !!";
	public static final String RESPONSE_REQUIRED_APIKEY = "Api Key is required to access this Resource";

}
