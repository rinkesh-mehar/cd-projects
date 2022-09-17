package in.cropdata.cdtmasterdata.acl.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity(name = "acl_restrictions")
public class AclRestriction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "RoleID")
	private int roleId;

	@Column(name = "ResourceID")
	private int subResourceGroupId;

	@Column(name = "SubResourceID")
	private int resourceId;

	@Transient
	private String resourceURI;

//    @Transient
//    private String group;

	@Column(name = "ResourceGroupID")
	private int resourceGroupId;
}
