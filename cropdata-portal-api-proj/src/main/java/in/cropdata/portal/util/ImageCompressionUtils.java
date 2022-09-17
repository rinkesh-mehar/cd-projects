package in.cropdata.portal.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

import javax.imageio.ImageIO;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import in.cropdata.portal.exceptions.InvalidDataException;

/**
 * Utility class for handling image compression. It uses <b>org.imgscalr</b>
 * library to compress the provided images.
 * 
 * @author PranaySK
 * @Date 03-Oct-2020
 */

@Component
public class ImageCompressionUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(ImageCompressionUtils.class);

	private static final int PROFILE_IMAGE_WIDTH = 500;
	private static final int DOCUMENT_IMAGE_WIDTH = 600;

	/**
	 * Used for compression of profile image.
	 * 
	 * @param profileImage the profile image to be compressed
	 * 
	 * @return {@link MultipartFile}
	 * 
	 * @throws IOException
	 * 
	 * @author PranaySK
	 */
	public MultipartFile compressProfileImage(MultipartFile profileImage) {
		try {
			LOGGER.info("Profile file size before conversion -> {} KB", this.toKiloBytes(profileImage.getSize()));
			/** Read the image and resize it */
			BufferedImage image = Scalr.resize(ImageIO.read(profileImage.getInputStream()), Scalr.Method.AUTOMATIC,
					Scalr.Mode.AUTOMATIC, PROFILE_IMAGE_WIDTH, Scalr.OP_ANTIALIAS);
			/** Write the image to temporary file */
			File tempFile = new File(
					System.getProperty("java.io.tmpdir") + File.separator + profileImage.getOriginalFilename());
			ImageIO.write(image, profileImage.getContentType().split("/")[1], tempFile);
			/** Convert the temporary file to multipart file */
			MultipartFile compressedProfileImage = this.getMultipartFileFromFile(tempFile);
			LOGGER.info("Profile file size after conversion -> {} KB",
					this.toKiloBytes(compressedProfileImage.getSize()));
			/** delete the temporary file */
			tempFile.delete();
			/** return compressed file */
			return compressedProfileImage;

		} catch (IOException ex) {
			LOGGER.error(ex.getMessage(), ex);
			throw new InvalidDataException("Failed to compress document image!");
		}
	}

	/**
	 * Used for compression of document image.
	 * 
	 * @param documentImage the document image to be compressed
	 * 
	 * @return {@link MultipartFile}
	 * 
	 * @throws IOException
	 * 
	 * @author PranaySK
	 */
	public MultipartFile compressDocumentImage(MultipartFile documentImage) {
		try {
			LOGGER.info("Doc file size before conversion -> {} KB", this.toKiloBytes(documentImage.getSize()));
			/** Read the image and resize it */
			BufferedImage image = Scalr.resize(ImageIO.read(documentImage.getInputStream()), Scalr.Method.AUTOMATIC,
					Scalr.Mode.AUTOMATIC, DOCUMENT_IMAGE_WIDTH, Scalr.OP_ANTIALIAS);
			/** Write the image to temporary file */
			File tempFile = new File(
					System.getProperty("java.io.tmpdir") + File.separator + documentImage.getOriginalFilename());
			ImageIO.write(image, documentImage.getContentType().split("/")[1], tempFile);
			/** Convert the temporary file to multipart file */
			MultipartFile compressedDocumentImage = this.getMultipartFileFromFile(tempFile);
			LOGGER.info("Doc file size after conversion -> {} KB", this.toKiloBytes(compressedDocumentImage.getSize()));
			/** delete the temporary file */
			tempFile.delete();
			/** return compressed file */
			return compressedDocumentImage;

		} catch (IOException ex) {
			LOGGER.error(ex.getMessage(), ex);
			throw new InvalidDataException("Failed to compress document image!");
		}
	}

	/**
	 * To get {@link MultipartFile} file from the provided input {@link File} file.
	 * It converts input file to multi-part file file using
	 * {@link CommonsMultipartFile}.
	 * 
	 * @param inputFile the source file to be converted
	 * 
	 * @return {@link MultipartFile}
	 * 
	 * @author PranaySK
	 */
	private MultipartFile getMultipartFileFromFile(File inputFile) throws IOException {
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
	 * Used for getting size in kilo bytes.
	 * 
	 * @param sizeInBytes the size in bytes
	 * 
	 * @return the size in kilo bytes
	 * 
	 * @author PranaySK
	 */
	private long toKiloBytes(final long sizeInBytes) {
		return (sizeInBytes / 1024);
	}

}