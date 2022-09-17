package in.cropdata.gstm.studio.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * Used for fetching analytics data.
 * 
 * @author PranaySK
 * @since 1.0
 */

@Data
@JsonInclude(value = Include.NON_NULL)
public class AnalyticsParameters implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String tileId;
	private Integer seasonId;
	private String seasonName;
	private BigDecimal irrigation;
	private BigDecimal nsaTgaPer;
	private BigDecimal averageLhSize;
	private BigDecimal production;
	private BigDecimal pyar;
	private BigDecimal pvi;
	private String color;
	private Integer crop;
	private Integer cropCount;
	private String cropName;
}