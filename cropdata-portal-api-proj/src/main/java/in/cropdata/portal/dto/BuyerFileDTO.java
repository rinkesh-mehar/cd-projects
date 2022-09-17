package in.cropdata.portal.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Vivek Gajbhiye
 */
@Data
public class BuyerFileDTO {

    private MultipartFile panCard;
    private MultipartFile addharCard;
    private MultipartFile gstCertificate;
    private String data;
}
