/**
 * 
 */
package in.cropdata.toolsuite.drk.dto.cases;

import lombok.Data;

/**
 * @author Vivek Gajbhiye - Cropdata
 *
 * 09-Mar-2020
 */
@Data
public class StandardQuantityPerAcreDTO {
	
	private double standardQua;

	/**
	 * @param standardQua
	 */
	public StandardQuantityPerAcreDTO(double standardQua) {
		this.standardQua = standardQua;
	}
	
	

}
