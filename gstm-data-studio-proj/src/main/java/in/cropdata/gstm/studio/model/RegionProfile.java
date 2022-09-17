package in.cropdata.gstm.studio.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * Used for setting region profile in analytics data.
 * 
 * @author PranaySK
 * @since 1.0
 */

@Data
@JsonInclude(value = Include.NON_NULL)
public class RegionProfile implements Serializable {

	private static final long serialVersionUID = 1L;

	private Fields fields;
	private Values values;
	private Aggregate aggregate;
}
