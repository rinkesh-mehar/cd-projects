package in.cropdata.gstm.studio.model;

import java.io.Serializable;
import java.util.List;

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
public class AnalyticsData implements Serializable {

	private static final long serialVersionUID = 1L;

	/** For storing analytics data */
	private String template;
	private String season;
	private Region region;
	private RegionProfile regionProfile;
	private List<FocusCrop> focusCrops;
	private List<FocusCrop> riskFreeCrops;
}