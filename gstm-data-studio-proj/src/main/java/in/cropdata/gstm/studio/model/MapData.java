package in.cropdata.gstm.studio.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * Used for fetching map data used for converting into GeoJSON.
 * 
 * @author PranaySK
 * @since 1.0
 */

@Data
@JsonInclude(value = Include.NON_NULL)
public class MapData implements Serializable {

	private static final long serialVersionUID = 1L;

	private String tileId;
	private Integer seasonId;
	private String seasonName;
	private BigDecimal pyar;
	private BigDecimal pvi;
	private BigDecimal latitude;
	private BigDecimal longitude;
	private BigDecimal minX;
	private BigDecimal minY;
	private BigDecimal maxX;
	private BigDecimal maxY;
	private String color;
}
