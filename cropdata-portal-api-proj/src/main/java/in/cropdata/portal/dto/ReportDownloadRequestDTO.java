package in.cropdata.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDownloadRequestDTO {
	private Integer reportId;
	private String name;
	private String email;
	private String mobile;
	private String otherIndustry;
	private String organization;
	private Integer industryID;
}
