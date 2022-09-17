package in.cropdata.cdtmasterdata.region.model;
import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

import java.util.List;

@Data
public class MmpkUploadWrapper {

//    private List<MultipartFile> file;

    private MultipartFile csvFile;
    
    private MultipartFile image;

//    private MultipartFile mmpkFile;
 
    private String data;
}
