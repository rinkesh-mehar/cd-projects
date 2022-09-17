package in.cropdata.ms.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Vivek Gajbhiye
 */
public class FileUtils {

    public  static File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
        File convFile = new File(multipart.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(multipart.getBytes());
        fos.close();
        return convFile;
    }
}
