package in.cropdata.cdtmasterdata.model.vo;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class AgriDistrictCommodityStressVO {

    MultipartFile image;

    String data;
    
}
