package com.drk.tools.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

//import com.azure.storage.blob.BlobClient;
//import com.azure.storage.blob.BlobContainerClient;
import com.drk.tools.config.AppConfig;
import com.drk.tools.constants.ErrorConstants;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.drk.tools.exceptions.DirectoryNotFoundException;
import com.drk.tools.model.ImageDetails;

import in.cropdata.toolsuite.sdk.FileManagerUtil;
import in.cropdata.toolsuite.sdk.dto.DirectoryResponseDTO;
import in.cropdata.toolsuite.sdk.dto.FileUploadResponseDTO;

/**
 * Utility class for handling file operations.
 *
 * @author PranaySK
 * @Date 17-Sep-2020
 */

@Component
public class FileUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);

	@Autowired
	private FileManagerUtil fileManagerUtil;

	@Autowired
	private ResourceLoader resourceLoader;

	@Autowired
	private AppConfig appConfig;

	@Autowired
	private FileManagerToolUtil fileManagerToolUtil;

//	@Autowired
//	private BlobContainerClient blobContainerClient;
	/**
	 * To check if provided directory is present or not. If not present then create
	 * a new directory with provided path.
	 * 
	 * @param dirPath the directory path
	 * 
	 * @return
	 * 
	 * @author PranaySK
	 */
	public static void checkDirExists(String dirPath) {
		File dirFile = new File(dirPath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
			LOGGER.info("Directory created for -> {}", dirPath);
		}
	}

	/**
	 * To check if given file already exists in the path provided with the filename.
	 * If file exists then delete it.
	 * 
	 * @param sourceFileNameWithPath the source filename along with directory path
	 * 
	 * @return
	 * 
	 * @author PranaySK
	 */
	public static void checkAndDeleteExistingFile(String sourceFileNameWithPath) {
		File zipFile = new File(sourceFileNameWithPath);
		if (zipFile.exists()) {
			zipFile.delete();
		}
	}

	/**
	 * To check if given file or directory already exists in the path provided with the filename.
	 * If file exists then delete it.
	 *
	 * @param file the source file along with directory path
	 *
	 * @return
	 *
	 * @author Rinkesh Mehar
	 */
	public static void checkAndDeleteExistingFile(File file) {
		if (file.exists()) {
			file.delete();
		}
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
	 * To create ZIP file from the provided directory.
	 * 
	 * @param sourceDirPath the source directory which is to be zipped
	 * @param zipDirPath    the directory in which the created ZIP file to be kept
	 * 
	 * @return {@link MultipartFile} the ZIP file
	 * 
	 * @author PranaySK
	 */
	public File createZipOfDir(String sourceDirPath, String zipDirPath) throws IOException {
		try {
			LOGGER.info("Creating new zip file...");
			Path zipFilePath = Files.createFile(Paths.get(zipDirPath));
			Path sourcePath = Paths.get(sourceDirPath);
			try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(zipFilePath));
					Stream<Path> pathStream = Files.walk(sourcePath);) {
				pathStream.filter(path -> !Files.isDirectory(path)).forEach(path -> {
					ZipEntry zipEntry = new ZipEntry(sourcePath.relativize(path).toString());
					try {
						zos.putNextEntry(zipEntry);
						Files.copy(path, zos);
						zos.closeEntry();
					} catch (IOException ioe) {
						LOGGER.error(ioe.getMessage(), ioe);
					}
				});
			}
			LOGGER.info("Zip file created -> {}", zipDirPath);
			return zipFilePath.toFile();

		} catch (Exception ex) {
			throw new DirectoryNotFoundException("Error while creating all media zip file - " + ex.getMessage());
		}
	}

	/**
	 * To create ZIP file from the provided directory by matching the files with
	 * filename present in the directory.
	 * 
	 * @param sourceDirPath          the source directory in which the files to be
	 *                               searched
	 * @param zipDirPathWithFileName the directory in which the created ZIP file to
	 *                               be kept
	 * @param imageDetails           the list of {@link ImageDetails} which contains
	 *                               the ID to be matched with the filename
	 * @return {@link MultipartFile} the ZIP file
	 * 
	 * @author PranaySK
	 */
	public File createZipOfDirFiles(String sourceDirPath, String zipDirPathWithFileName,
			final List<ImageDetails> imageDetails) throws IOException {
		try {
			LOGGER.info("Creating new zip file...");
			Path zipFilePath = Files.createFile(Paths.get(zipDirPathWithFileName));
			Path sourcePath = Paths.get(sourceDirPath);
			try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(zipFilePath));
					Stream<Path> pathStream = Files.walk(sourcePath);) {
				pathStream.filter(path -> !Files.isDirectory(path)).forEach(path -> imageDetails.forEach(img -> {
					try {
						if (path.getFileName().toString().split("\\.")[0].equals(img.getId().toString())) {
							ZipEntry zipEntry = new ZipEntry(sourcePath.relativize(path).toString());
							zos.putNextEntry(zipEntry);
							Files.copy(path, zos);
						}
						zos.closeEntry();
					} catch (IOException ioe) {
						LOGGER.error(ioe.getMessage(), ioe);
					}
				}));
			}
			LOGGER.info("Zip file created -> {}", zipDirPathWithFileName);
			return zipFilePath.toFile();

		} catch (Exception ex) {
			throw new DirectoryNotFoundException("Error while creating 24H media zip file - " + ex.getMessage());
		}
	}

	/**
	 * To write image files in the provided directory.
	 * 
	 * @param imgFolderName the source directory in which the image files to be
	 *                      written
	 * @param imageDetails  the list of {@link ImageDetails} which contains the ID
	 *                      to be used as the filename for images and the image file
	 *                      URL to be used as a resource to get the file contents
	 * @return boolean
	 * 
	 * @author PranaySK
	 */
	public boolean writeImgFilesOnDisk(String imgFolderName, List<ImageDetails> imageDetails) {
		boolean isWritten = false;
		try {
			/** check if directory is present, create otherwise */
			FileUtils.checkDirExists(imgFolderName);
			/** Write the images on disk storage */
			for (final ImageDetails imgDetail : imageDetails) {
				Resource imgResource = resourceLoader.getResource(imgDetail.getImageUrl());
				String filePath = imgFolderName.concat(imgDetail.getId().toString()).concat(".jpeg");
				FileUtils.checkAndDeleteExistingFile(filePath);
				File temp = new File(filePath);
				IOUtils.copy(imgResource.getInputStream(), new FileOutputStream(temp));
				isWritten = true;
				LOGGER.info("creating file on resource {}", temp.getName());
			}
		} catch (Exception ex) {
			LOGGER.error("Error while writing image files on disk -> {}", ex.getMessage());
		}

		return isWritten;
	}

	/**
	 * To upload the provided ZIP file in Azure Blob and return the uploaded file
	 * details.
	 *
	 * @param file the source file {@link MultipartFile} to be uploaded
	 * @return {@link FileUploadResponseDTO}
	 *
	 * @author RinkeshKM
	 */
	public Map<String, Object> createPartitionedZip(String zipName, String imageDirectoryPath, List<ImageDetails> imageDetails, Integer regionId) {
		Map<String, Object> responseMap = new HashMap<>();
		final String SUCCESS = "success";
		final String MESSAGE = "message";
		final String ERROR = "error";
		String baseZipDirPath = appConfig.getZipFilesDir(); // path.dir.zip
		/** check if directory is present, create otherwise */
		FileUtils.checkDirExists(baseZipDirPath);
		String baseMediaDirPath = appConfig.getMediaFilesDir(); // path.dir.media

		int partitionSize = 8;
		if (imageDirectoryPath.equals("commodity_plant_part")) {
			partitionSize = 2;
		}
		int partitionEndSize;
		if (imageDetails.size() > partitionSize){
			partitionEndSize = imageDetails.size() / partitionSize;
		} else {
			partitionEndSize = imageDetails.size();
			partitionSize = 1;
		}
		int splitSize = partitionEndSize;
		int partitionInitialSize = 0;
		LOGGER.info("Partition Initial Size Is {}", partitionInitialSize);
		LOGGER.info("Partition End Size Is {}", partitionEndSize);
		for (int i = 1; i <= partitionSize; i++) {
			if ((partitionSize == 8 && i == 8) || (partitionSize ==2 && i == 2) ){
				partitionEndSize = imageDetails.size();
			}
			List<ImageDetails> partSymptomImages = imageDetails.subList(partitionInitialSize, partitionEndSize);
			try {
				String zipFileNameWithDir = baseZipDirPath.concat(zipName).concat(regionId + "_").concat(String.valueOf(i)).concat(".zip");
				FileUtils.checkAndDeleteExistingFile(zipFileNameWithDir);
				File mediaFile = this.createZipOfDirFiles(baseMediaDirPath.concat(imageDirectoryPath), zipFileNameWithDir, partSymptomImages);
				LOGGER.info("media file is {}", mediaFile);
				partitionInitialSize = partitionEndSize;
				partitionEndSize = partitionEndSize + splitSize;
				if (partSymptomImages.size()>0) {
					LOGGER.info("Start Image ID -> {}", partSymptomImages.get(0).getId());
					LOGGER.info("End Image ID -> {}", partSymptomImages.get(partSymptomImages.size()-1).getId());
				}
				LOGGER.info("Partition Initial Size Is -> {}", partitionInitialSize);
				LOGGER.info("Partition End Size Is -> {}", partitionEndSize);
				LOGGER.info("Split Size Is -> {}", splitSize);

				FileUploadResponseDTO responseDTO = this.uploadUserZipFileWithPath(mediaFile, imageDirectoryPath);
				LOGGER.info("uploaded file url -> [{}] --- [{}]", responseDTO.getId(), responseDTO.getPublicUrl());
				responseMap.put(SUCCESS, "true");
				responseMap.put(MESSAGE, "Zip File Created For All Images");
			} catch (IOException e) {
				e.printStackTrace();
				responseMap.put(SUCCESS, "false");
				responseMap.put(ERROR, ErrorConstants.ZIP_FILE_CREATION_FAILED);
				responseMap.put(MESSAGE, "Zip file creation failed for all " + zipName );
			}
		}
		return responseMap;
	}


	public FileUploadResponseDTO generateZipAndUploadFlipbookSymptomAll(String zipName, String imageDirectoryPath, List<ImageDetails> imageDetails){

		String baseZipDirPath = appConfig.getZipFilesDir();
		/** check if directory is present, create otherwise */
		FileUtils.checkDirExists(baseZipDirPath);
		String baseMediaDirPath = appConfig.getMediaFilesDir();

		FileUploadResponseDTO responseDTO = null;
		try {
			String zipFileNameWithDir = baseZipDirPath.concat(zipName).concat(".zip");
			FileUtils.checkAndDeleteExistingFile(zipFileNameWithDir);
			File mediaFile = this.createZipOfDirFiles(baseMediaDirPath.concat(imageDirectoryPath), zipFileNameWithDir, imageDetails);
			LOGGER.info("media file is {}", mediaFile);
			responseDTO = this.uploadUserZipFileWithPath(mediaFile, imageDirectoryPath);
			LOGGER.info("uploaded file url -> [{}] --- [{}]", responseDTO.getId(), responseDTO.getPublicUrl());

		}catch (Exception e){
			e.printStackTrace();
		}

		return responseDTO;
	}

	/**
	 * To upload the provided ZIP file in Azure Blob and return the uploaded file
	 * details.
	 * 
	 * @param file the source file {@link MultipartFile} to be uploaded
	 * @return {@link FileUploadResponseDTO}
	 * 
	 * @author PranaySK
	 */
	public FileUploadResponseDTO uploadUserZipFile(MultipartFile file) throws IOException {
		final String[] supportedExtn = { ".zip" };
		String imageFileName = file.getOriginalFilename();
		FileUploadResponseDTO fileUploadResponse = null;
		String fileExtension = Objects.requireNonNull(imageFileName);
		LOGGER.info("Filename -> {}", imageFileName);
		if (Arrays.stream(supportedExtn).anyMatch(fileExtension::contains)) {
			final String pathKey = "zip-files";
			Map<String, Object> pathKeyResponse = fileManagerUtil.isPathKeyExist(pathKey);
			if (pathKeyResponse != null) {
//				boolean result = (boolean) pathKeyResponse.get("exist");
//				LOGGER.info("result -> {}", result);
				if (pathKeyResponse.get("exist").equals("true")) {
					LOGGER.info("pathkey exists, upload the file...");
					fileUploadResponse = this.fileManagerUtil.uploadZipFile(pathKey, false, imageFileName, true, file,
							null);
				} else {
					final String uploadDirPath = pathKey;
					LOGGER.info("pathkey does not exist, create directory and then upload the file...");
					String moduleName = "ts";
					DirectoryResponseDTO createDirResp = this.fileManagerUtil.createDirectory(moduleName, pathKey,
							uploadDirPath, null);
					if (createDirResp != null && createDirResp.isSuccess()) {
						fileUploadResponse = this.fileManagerUtil.uploadZipFile(pathKey, false, imageFileName, true,
								file, null);
					}
				}
			}
		} else {
			throw new NoSuchFileException("File Format Not Supported!");
		}

		return fileUploadResponse;
	}

	/**
	 * To upload the provided ZIP file in Azure Blob and return the uploaded file
	 * details.
	 * 
	 * @param file the source file {@link File} to be uploaded
	 * @return {@link FileUploadResponseDTO}
	 * 
	 * @author PranaySK
	 */
	public FileUploadResponseDTO uploadUserZipFileWithPath(File file) throws IOException {
		final String[] supportedExtn = { ".zip" };
		String imageFileName = file.getName();
		FileUploadResponseDTO fileUploadResponse = null;
		String fileExtension = Objects.requireNonNull(imageFileName);
		LOGGER.info("Filename -> {}", imageFileName);
		if (Arrays.stream(supportedExtn).anyMatch(fileExtension::contains)) {
			final String pathKey = "zip-files";
			Map<String, Object> pathKeyResponse = fileManagerUtil.isPathKeyExist(pathKey);
			if (pathKeyResponse != null) {
//				boolean result = (boolean) pathKeyResponse.get("exist");
//				LOGGER.info("result -> {}", result);
				if (pathKeyResponse.get("exist").equals("true"))
				{
					LOGGER.info("pathkey exists, upload the file...");
					fileUploadResponse = this.fileManagerUtil.uploadZipFileWithPath(pathKey, false, imageFileName, true,
							file.getAbsolutePath(), null);
				} else {
					final String uploadDirPath = pathKey;
					LOGGER.info("pathkey does not exist, create directory and then upload the file...");
					String moduleName = "ts";
					DirectoryResponseDTO createDirResp = this.fileManagerUtil.createDirectory(moduleName, pathKey,
							uploadDirPath, null);
					if (createDirResp != null && createDirResp.isSuccess()) {
						fileUploadResponse = this.fileManagerUtil.uploadZipFileWithPath(pathKey, false, imageFileName,
								true, file.getAbsolutePath(), null);
					}
				}
			}
		} else {
			throw new NoSuchFileException("File Format Not Supported!");
		}

		return fileUploadResponse;
	}

	/**
	 * To upload the provided ZIP with respect to file path in Azure Blob and return the uploaded file
	 * details.
	 *
	 * @param file the source file {@link File} to be uploaded
	 * @return {@link FileUploadResponseDTO}
	 *
	 * @author RinkeshKM
	 */
	public FileUploadResponseDTO uploadUserZipFileWithPath(File file, String filePath) throws IOException {
		final String[] supportedExtn = { ".zip" };
		String imageFileName = file.getName();
		FileUploadResponseDTO fileUploadResponse = null;
		String fileExtension = Objects.requireNonNull(imageFileName);
		LOGGER.info("Filename -> {}", imageFileName);
		if (Arrays.stream(supportedExtn).anyMatch(fileExtension::contains)) {
			final String pathKey = "zip-files/".concat(filePath);

			return fileManagerToolUtil.uploadFile(file, filePath);

//			Map<String, Object> pathKeyResponse = fileManagerUtil.isPathKeyExist(pathKey);
//			if (pathKeyResponse != null) {
//				boolean result = (boolean) pathKeyResponse.get("exist");
//				LOGGER.info("result -> {}", result);
//				if (result) {
//					LOGGER.info("pathkey exists, uploading the file...");
//					fileUploadResponse = this.fileManagerUtil.uploadFile(pathKey, false, imageFileName, true,
//							fileManagerUtil.getMultipartFile(file), null);
//				} else {
//					LOGGER.info("pathkey does not exist, create directory and then upload the file...");
//					String moduleName = "ts";
//					DirectoryResponseDTO createDirResp = this.fileManagerUtil.createDirectory(moduleName, pathKey,
//							pathKey, null);
//					if (createDirResp != null && createDirResp.isSuccess()) {
//						fileUploadResponse = this.fileManagerUtil.uploadZipFileWithPath(pathKey, false, imageFileName,
//								true, file.getAbsolutePath(), null);
//					}
//				}
//			}
		} else {
			throw new NoSuchFileException("File Format Not Supported!");
		}

//		return fileUploadResponse;
	}

}
