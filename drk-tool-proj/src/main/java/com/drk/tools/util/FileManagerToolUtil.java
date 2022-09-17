package com.drk.tools.util;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.drk.tools.config.AppConfig;
import com.drk.tools.constants.ErrorConstants;
import com.drk.tools.exceptions.FileUploadFailedException;
import in.cropdata.toolsuite.sdk.dto.FileUploadResponseDTO;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Component
public class FileManagerToolUtil {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private AppConfig appConfig;

//    @Autowired
//    private BlobContainerClient blobContainerClient;

    Logger LOGGER = org.slf4j.LoggerFactory.getLogger(FileManagerToolUtil.class);
    public String fileManagerUrl;

    public FileUploadResponseDTO uploadFile(File file, String filePath) throws IOException {
        FileUploadResponseDTO fileUploadResponseDTO = new FileUploadResponseDTO();

        try {
            BlobClient blobClient = null;
//
//            String fileName = appConfig.azureModuleName + File.separator +"master-data" +File.separator + "zip-files" + File.separator + filePath + File.separator + file.getName();
//            LOGGER.info("Uploading file -> {} ", fileName);
//            blobClient = blobContainerClient.getBlobClient(fileName);
//            blobClient.uploadFromFile(file.getAbsolutePath(), true);
//
//            LOGGER.info("uploaded file public URL is -> {}", blobClient.getBlobUrl());
//            String publicUrl =  URLDecoder.decode(blobClient.getBlobUrl(), StandardCharsets.UTF_8);
//
//            fileUploadResponseDTO.setPublicUrl(publicUrl);
//            fileUploadResponseDTO.setMessage("File Uploaded Successfully");
//            fileUploadResponseDTO.setSuccess(true);
//
//            LOGGER.info("Returned Response -> {} for Uploaded File -> {} ", fileUploadResponseDTO.toString(), file.getName());
        } catch (Exception e){
            e.printStackTrace();
            fileUploadResponseDTO.setSuccess(false);
            fileUploadResponseDTO.setMessage("Failed To Upload File");
            throw new FileUploadFailedException(ErrorConstants.FILE_UPLOAD_FAILED, "File Upload Failed..");
        }

        return fileUploadResponseDTO;
    }
}
