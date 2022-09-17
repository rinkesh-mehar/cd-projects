package in.cropdata.ms.request;

import in.cropdata.ms.model.SesEmail;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Vivek Gajbhiye
 */
@Data
public class EmailRequest {

    private String email;
    private MultipartFile attachment;
}
