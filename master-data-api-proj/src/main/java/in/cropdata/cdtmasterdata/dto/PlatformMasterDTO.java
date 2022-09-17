package in.cropdata.cdtmasterdata.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PlatformMasterDTO
{
    private MultipartFile logoFile;

    private String data;
}
