package in.cropdata.gstm.studio.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * Used for setting region in analytics data.
 * 
 * @author PranaySK
 * @since 1.0
 */

@Data
@JsonInclude(value = Include.NON_NULL)
public class Region implements Serializable {

	private static final long serialVersionUID = 1L;

	private String in;
	private String district;
	private String state;
	private List<Integer> years;
	private String level;
}
