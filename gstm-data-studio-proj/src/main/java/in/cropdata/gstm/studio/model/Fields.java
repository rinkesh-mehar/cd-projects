package in.cropdata.gstm.studio.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * Used for defining fields in analytics data.
 * 
 * @author PranaySK
 * @since 1.0
 */

@Data
@JsonInclude(value = Include.NON_NULL)
public class Fields implements Serializable {

	private static final long serialVersionUID = 1L;

	private String avgIrrigation;
	private String avgNsaTgaPer;
	private String avgAverageLhSize;
	private String totalProduction;
}
