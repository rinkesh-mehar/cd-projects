package in.cropdata.cdtmasterdata.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AgriFieldActivityDTO
{
    private MultipartFile imageFile;

    private String data;
}
