/**
 * 
 */
package com.drk.tools.dto;

import java.util.List;

import lombok.Data;

/**
 * @author vivek-cropdata
 *
 */
@Data
public class PyPayLoadBody {

	private Integer commodityID;
	private List<LiLatLngs> liLatLngs;

}
