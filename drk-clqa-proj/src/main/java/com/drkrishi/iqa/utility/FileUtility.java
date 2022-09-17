package com.drkrishi.iqa.utility;

import com.azure.storage.blob.BlobClient;
//import com.azure.storage.blob.BlobContainerClient;
import in.cropdata.toolsuite.sdk.FileManagerUtil;
import in.cropdata.toolsuite.sdk.dto.FileUploadResponseDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * @author cropdata-ujwal
 * @date 07/08/21
 * @time 12:24 PM
 */

public class FileUtility {

    private static final Logger LOGGER = LogManager.getLogger(FileUtility.class);
    @Value("${azure.storage.environment}")
    private String env;

    @Autowired
    private FileManagerUtil fileManager;

//    @Autowired
//    private BlobContainerClient containerClient;


    /**
     *upload farmer images in ABS
     * */
    public String uploadFileToBlob(String fileToUploadName, File fileToUpload) {
        BlobClient blobClient = null;
        try {
            String fileName = env + File.separator +"drkrishi" +File.separator + "media" + File.separator + fileToUploadName + File.separator + fileToUpload.getName();
            LOGGER.info("Uploading file -> {} ", fileName);
//            blobClient = containerClient.getBlobClient(fileName);
            FileUploadResponseDTO fileRepoDto;

            fileRepoDto = this.fileManager.uploadFile(env + File.separator +"drkrishi" +File.separator + "media" + File.separator + fileToUploadName + File.separator,
                    false, fileToUpload.getName(), true,
                    fileManager.getMultipartFile(fileToUpload), null);

            LOGGER.info("File Name {}", fileRepoDto.getPublicUrl());

            return fileRepoDto.getPublicUrl();

//            blobClient.uploadFromFile(fileToUpload.getAbsolutePath(), true);

//            LOGGER.info("uploaded file public URL is -> {}", blobClient.getBlobUrl());
//            return URLDecoder.decode(blobClient.getBlobUrl(), StandardCharsets.UTF_8);

        } catch (Exception ex) {
            LOGGER.error("File upload failed -> {}", fileToUploadName, ex);
            return null;
        }

    }
}
