package in.cropdata.cdtmasterdata.website.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ToolsDTO
{
    private MultipartFile logoFile;

    private String data;
}
