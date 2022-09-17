package in.cropdata.toolsuite.filemanager.utils;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {

	
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

	/**
	 * String getExtension(String fileName)
	 * 
	 * @param fileName -
	 * @return String - extension of file
	 */
	public static String getExtension(String fileName) {
		return fileName.split("\\.(?=[^\\.]+$)")[1];
	}

	/**
	 * String getFileName(String fileName)
	 * 
	 * @param fileName
	 * @return String file name without extension
	 */
	public static String getFileName(String fileName) {
		return fileName.split("\\.(?=[^\\.]+$)")[0];
	}

	public static String getFileNameFromAbsulatePath(String absulatePath) {
		return absulatePath.split(".+?/(?=[^/]+$)")[1];
	}

	public static String getParentDirFromAbsulatePath(String absulatePath) {
		return absulatePath.split(".+?/(?=[^/]+$)")[0];
	}

	/**
	 * 
	 * @param tempDir
	 * @return boolean
	 */
	public static boolean emptyDir(String tempDir) {
		logger.info("clean temp directory");
		File fin = new File(tempDir);
		if (fin != null && fin.exists()) {
			for (File file : fin.listFiles()) {
				//TODO need to handle through some other dependency (other than Actuator)
//				try {
//				    FileDeleteStrategy.FORCE.delete(file);
				    if (!file.delete()) {
					file.deleteOnExit();
				    }
//				} catch (IOException e) {
//				    logger.error("Temp Directory Cleaning Failed for file : " + file, e);
//				}
			}//for files in list
		}

		return true;
	}//emptyDir

}