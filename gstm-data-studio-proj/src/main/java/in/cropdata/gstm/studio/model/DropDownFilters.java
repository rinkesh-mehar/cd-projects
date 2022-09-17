package in.cropdata.gstm.studio.model;

import java.io.Serializable;

import lombok.Data;

/**
 * Used for fetching DropDown data from DB to show filters in view page.
 * 
 * @author PranaySK
 * @since 1.0
 */

@Data
public class DropDownFilters implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
}
