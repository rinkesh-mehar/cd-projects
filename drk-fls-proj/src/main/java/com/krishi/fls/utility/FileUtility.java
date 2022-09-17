package com.krishi.fls.utility;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.krishi.fls.model.Error;
import com.krishi.fls.model.Response;
import com.krishi.fls.properties.AppProperties;
import in.cropdata.toolsuite.sdk.FileManagerUtil;

import in.cropdata.toolsuite.sdk.dto.FileUploadResponseDTO;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class FileUtility {

    @Value("${azure.storage.environment}")
    private String env;

    @Autowired
    private FileManagerUtil fileManager;

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private BlobContainerClient containerClient;

    private static final Logger LOGGER = LogManager.getLogger(FileUtility.class);
    final static SimpleDateFormat SYNC_FILE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

   /* public File createAndUploadJsonFile(String scheduledTask, Integer id){
        Response response=new Response();
        File file = null;
        try {
            File dir = new File(appProperties.getUserDirPath());
            if(!dir.exists()) {
                LOGGER.info("directory not exist.");
                boolean isSuccess = dir.mkdir();
                if(!isSuccess) {
                    LOGGER.info("Unable to create folder.");
                    System.out.println("Unable to create folder.");
                }
            }
            String filename = SYNC_FILE_DATE_FORMAT.format(new Date())+"-"+id+".json";
            file = new File(dir, filename);
            try(FileWriter writer = new FileWriter(file)) {
                LOGGER.info("writing file.");
                writer.write(scheduledTask);
            } catch (IOException e) {
                e.printStackTrace();
                Error error = new Error();
                error.setErrorMesg("An Error Occurred, Please Try Again Later.");
                response.setError(error);

            }
        } catch (Exception e){

        }
        return file;
    }*/

    public String uploadFileInABS(String pathKey, File file) {
        FileUploadResponseDTO fileRepoDto = null;
        String contentType = "multipart/form-data";
        byte[] content = null;
        try {
            Path path = Paths.get(file.getAbsolutePath());
            Map<String, Object> pathKeyExist = this.fileManager.isPathKeyExist(pathKey);
            if (pathKeyExist.get("exist") != null) {
                Object exist = pathKeyExist.get("exist");
                if (exist.toString().equals("true")) {
                    LOGGER.info("inside if condition result is true :: ");
                    LOGGER.info("file absolutePath is : {}", file.getAbsolutePath());
                    content = Files.readAllBytes(path);
                    MultipartFile mltFile = new MockMultipartFile(file.getAbsolutePath(), file.getName(), contentType,
                            content);
                    try {
                        fileRepoDto = this.fileManager.uploadFile(pathKey, false, null, true, mltFile, null);
                    } catch (ResourceAccessException e) {
                        e.printStackTrace();
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("json file url------> {}", fileRepoDto.getPublicUrl());
        return fileRepoDto.getPublicUrl();

    }

    public Map<String, Object> readZipFile(MultipartFile zipFiles) throws IOException {
        Map<String, Object> stringObjectMap = new HashMap<>();
        Response response = new Response();
        try {
            ZipInputStream zis = new ZipInputStream(zipFiles.getInputStream());
            File temp = new File(String.valueOf(zis));
            IOUtils.copy(zipFiles.getInputStream(), new FileOutputStream(temp));
            Path path = this.unzip(temp.getPath(), this.appProperties.getZipExtract());
            System.out.println("file is ----->" + temp.getPath());
            File newFolder = new File((this.appProperties.getZipExtract() + "\\" + zipFiles.getOriginalFilename()).replace(".zip", ""));
//
            File f = new File(this.appProperties.getZipExtract());
            for (File folder : f.listFiles()) {
                if (folder.isDirectory()) {
                    deleteDir(folder);
                }
            }
            newFolder.delete();
            temp.deleteOnExit();
            stringObjectMap.put("success", true);
            stringObjectMap.put("status", 200);
            stringObjectMap.put("error", null);
        } catch (Exception e) {
            e.printStackTrace();
            stringObjectMap.put("success", false);
            stringObjectMap.put("status", 404);
            stringObjectMap.put("error", "Wrong file");
        }

        return stringObjectMap;
    }

    public Path unzip(final String zipFilePath, final String unzipLocation) throws IOException {

        Path filePath = null;
        if (!(Files.exists(Paths.get(unzipLocation)))) {
            Files.createDirectories(Paths.get(unzipLocation));
        }
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry = zipInputStream.getNextEntry();
            while (entry != null) {
                filePath = Paths.get(unzipLocation, entry.getName());
                System.out.println("file name is --------->" + filePath.getFileName());

                if (!entry.isDirectory()) {
                    unzipFiles(zipInputStream, filePath);
                    String fieName = filePath.getParent().toString();
                    try {
                        String seprateName =fieName.replace("/", " ");
                        String[] parts = seprateName.split(" ");
                        String folderName = parts[parts.length - 1];

                        LOGGER.info("Folder Name Is {}", folderName);
//                        this.uploadFileInABS(folderName, filePath.toFile());
                        this.uploadFileToBlob(folderName, filePath.toFile());
                    } catch (Exception e) {
                        LOGGER.info("error is {}", e.getMessage());
                        e.printStackTrace();
                    }

                } else {
                    Files.createDirectories(filePath);
                }

                zipInputStream.closeEntry();
                entry = zipInputStream.getNextEntry();
            }
        }

        return filePath;
    }

    public void unzipFiles(final ZipInputStream zipInputStream, final Path unzipFilePath) throws IOException {
        Files.createDirectories(unzipFilePath.getParent());
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(unzipFilePath.toAbsolutePath().toString()))) {
            byte[] bytesIn = new byte[1024];
            int read = 0;
            LOGGER.info("start unzip zip file");
            while ((read = zipInputStream.read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }
        }
    }

    private void deleteDir(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                deleteDir(f);
            }
        }
        file.delete();
    }

   /* public boolean splitFile() {
        Integer PART_SIZE =  1048576;
        String FILE_NAME = "C:\\Users\\Admin\\Desktop\\mmpk\\temp1\\300.mmpk";

        File inputFile = new File(FILE_NAME);
        FileInputStream inputStream;
        String newFileName;
        FileOutputStream filePart;
        int fileSize = (int) inputFile.length();
        int nChunks = 0, read = 0, readLength = PART_SIZE;
        byte[] byteChunkPart;
        try {
            inputStream = new FileInputStream(inputFile);
            while (fileSize > 0) {
                if (fileSize <= 5) {
                    readLength = fileSize;
                }
                byteChunkPart = new byte[readLength];
                read = inputStream.read(byteChunkPart, 0, readLength);
                fileSize -= read;
                assert (read == byteChunkPart.length);
                nChunks++;
                newFileName = FILE_NAME + ".part"
                        + Integer.toString(nChunks - 1);
                filePart = new FileOutputStream(new File(newFileName));
                filePart.write(byteChunkPart);
                filePart.flush();
                filePart.close();
                byteChunkPart = null;
                filePart = null;
            }
            inputStream.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return false;
    }*/
    /**
     *upload farmer images in ABS
     * */
    public String uploadFileToBlob(String fileToUploadName, File fileToUpload) {
        BlobClient blobClient = null;
        try {
            String fileName = env + File.separator +"drkrishi" +File.separator + "media" + File.separator + fileToUploadName + File.separator + fileToUpload.getName();
            LOGGER.info("Uploading file -> {} ", fileName);
            blobClient = containerClient.getBlobClient(fileName);
            blobClient.uploadFromFile(fileToUpload.getAbsolutePath(), true);

            LOGGER.info("uploaded file public URL is -> {}", blobClient.getBlobUrl());
            return URLDecoder.decode(blobClient.getBlobUrl(), StandardCharsets.UTF_8);

        } catch (Exception ex) {
            LOGGER.error("File upload failed -> {}", fileToUploadName, ex);
        }

        return "";
    }
}
