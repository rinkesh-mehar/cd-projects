package in.cropdata.cdtmasterdata.website.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.cdtmasterdata.website.vo
 * @date 20/08/20
 * @time 12:29 PM
 * To change this template use File | Settings | File and Code Templates
 */
@Data
public class NewsDTO
{
    private MultipartFile image;

    private String data;
}
