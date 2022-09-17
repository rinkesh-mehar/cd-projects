package in.cropdata.cdtmasterdata.website.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDownloadRequestDto {
	private Integer reportId;
	private String name;
	private String email;
	private String mobile;
	private String industry;
	private String organization;
}
