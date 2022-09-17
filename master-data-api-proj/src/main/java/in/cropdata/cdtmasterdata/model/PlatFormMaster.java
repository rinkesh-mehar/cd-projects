package in.cropdata.cdtmasterdata.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.cdtmasterdata.model
 * @date 24/08/20
 * @time 6:42 PM
 * To change this template use File | Settings | File and Code Templates
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "platform_master")
@Data
public class PlatFormMaster
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "Name")
	private String name;
	
	@Column(name = "Logo")
	private String logo;
	
	@Column(name = "Description")
	private String description;
	
	@Column(name = "ViewOrder")
	private Integer viewOrder;
	
	@Transient
	private String logoFile;

	@Transient
	private Date createdAt;

	@Transient
	private Date updatedAt;

	@Transient
	private String status;
}
