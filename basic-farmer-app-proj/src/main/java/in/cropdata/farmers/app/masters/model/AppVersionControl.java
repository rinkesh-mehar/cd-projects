/**
 * 
 */
package in.cropdata.farmers.app.masters.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Data;

/**
 * @author pallavi-waghmare
 *
 */

@Entity
@Data
@Table(name = "app_version_control", schema = "cdt_master_data") 
public class AppVersionControl {
 
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@Column(name ="AppName")
	private String appName;
	
	@Column(name ="AppKey")
	private String appKey;
}
