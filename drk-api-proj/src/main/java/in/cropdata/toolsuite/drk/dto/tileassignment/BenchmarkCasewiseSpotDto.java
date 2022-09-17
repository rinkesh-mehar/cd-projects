package in.cropdata.toolsuite.drk.dto.tileassignment;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class BenchmarkCasewiseSpotDto {

	@NotBlank(message = "Field caseID cannot be NULL or Blank.")
	private Integer caseID;

	@NotBlank(message = "Field spotID cannot be NULL or Blank.")
	private Long spotID;
	
	@NotBlank(message = "Field regionID cannot be NULL or Blank.")
	private Integer regionID;
	
	@NotBlank(message = "Field subRegionID cannot be NULL or Blank.")
	private Integer subRegionID;

}
