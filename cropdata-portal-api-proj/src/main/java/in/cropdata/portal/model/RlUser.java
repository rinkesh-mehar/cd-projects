/**
 * 
 */
package in.cropdata.portal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * @author vivek-cropdata
 *
 */
@Data
@Entity
@Table(name = "rl_users", schema = "cdt_website")
@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RlUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "SourceID")
	private Integer sourceID;

	@Column(name = "RegionID")
	private Integer regionId;

	@Column(name = "RoleName")
	private String roleName;

	@Column(name = "Name")
	private String name;

	@Column(name = "Email")
	private String email;

	@Column(name = "MobileNumber")
	private String mobileNumber;

	@Column(name = "UserImageUrl")
	private String userImageUrl;

	@Column(name = "AadharNo")
	private String addharNo;

	@Column(name = "AadharImageUrl")
	private String addharImageUrl;

	@Column(name = "DrivingLicenseImageUrl")
	private String drivingLicenceUrl;

	@Column(name = "Pan")
	private String pan;

	@Column(name = "PanImageUrl")
	private String panImageUrl;

	@Column(name = "AggrementAccepted")
	private String aggrementAccepted;
	
	@Column(name = "RefreshCount")
	private Integer refreshCount;

	@Column(name = "Status")
	private String status;

}
