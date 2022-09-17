package in.cropdata.portal.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class JobApplicationDTO {

    private MultipartFile resumeFile;
    
    private Integer opportunityID;
    
    private String name;
    
    private String email;
    
    private String mobile;
    
    private String address;

//    private String data;
	
}
