package in.cropdata.farmers.app.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_NULL)
public class  FarmerLatLongDTO {

	private Double latitude;
	private Double longitude;

}
