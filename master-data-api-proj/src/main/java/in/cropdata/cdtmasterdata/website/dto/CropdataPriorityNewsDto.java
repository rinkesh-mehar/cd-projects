package in.cropdata.cdtmasterdata.website.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CropdataPriorityNewsDto {
	private Integer id;
	private String subject;
	private String publishedDate;
	private String status;
	private Integer priority;
}
