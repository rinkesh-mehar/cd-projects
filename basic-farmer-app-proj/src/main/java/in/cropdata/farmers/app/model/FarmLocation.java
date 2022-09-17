/**
 * 
 */
package in.cropdata.farmers.app.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

/**
 * @author pallavi-waghmare
 *
 */
@Data
@ToString
public class FarmLocation {

	@JsonProperty(value = "data")
	public List<FarmCaseLocation> data;
	public String errorCode;
	public String message;
	public String status;

}
