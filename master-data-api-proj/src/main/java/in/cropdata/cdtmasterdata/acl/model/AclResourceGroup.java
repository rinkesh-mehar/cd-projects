package in.cropdata.cdtmasterdata.acl.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity(name = "acl_resource_groups")
public class AclResourceGroup {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private int id;
	
	@Column(name = "ResourceGroupName")
	private String resourceGroupName;
	
	@Column(name = "MenuPlacement")
	private int menuPlacement;

}
