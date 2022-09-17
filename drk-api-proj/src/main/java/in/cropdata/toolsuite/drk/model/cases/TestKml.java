package in.cropdata.toolsuite.drk.model.cases;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class TestKml {
	private String apiKey;
	private KMLData kmldata;
	private MultipartFile zipFile;

}
