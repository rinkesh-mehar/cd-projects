package in.cropdata.gstm.studio.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * Used for fetching color codes of range values.
 * 
 * @author PranaySK
 * @since 1.0
 */

@Data
@JsonInclude(value = Include.NON_NULL)
public class RangeColorCode implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer bandId;
	private String rangeMinValue;
	private String rangeMaxValue;
	private String colorCode;
}
