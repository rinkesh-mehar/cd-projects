package in.cropdata.gstm.studio.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * Used for fetching configuration data for the given parameter.
 * 
 * @author PranaySK
 * @since 1.0
 */

@Data
@JsonInclude(value = Include.NON_NULL)
public class ParameterData implements Serializable {

	private static final long serialVersionUID = 1L;

	private String refrenceFieldName;
	private String aggregationMethod;
	private String orderingMethod;
	private String template;
	private String model;
	private Integer seasonId;
}
