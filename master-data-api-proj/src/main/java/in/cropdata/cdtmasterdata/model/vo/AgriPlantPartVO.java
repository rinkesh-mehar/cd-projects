package in.cropdata.cdtmasterdata.model.vo;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class AgriPlantPartVO {

    MultipartFile image;

    String data;

}
