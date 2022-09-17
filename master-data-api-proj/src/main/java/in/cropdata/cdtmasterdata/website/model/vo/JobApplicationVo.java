package in.cropdata.cdtmasterdata.website.model.vo;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class JobApplicationVo {

    private MultipartFile resumeFile;

    private String data;
	
}
