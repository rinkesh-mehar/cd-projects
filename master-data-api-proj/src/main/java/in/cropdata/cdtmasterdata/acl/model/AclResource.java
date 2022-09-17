package in.cropdata.cdtmasterdata.acl.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity(name = "acl_resource")
public class AclResource {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private int id;
	
	@Column(name = "ParentID", nullable = false, columnDefinition = "int default 0")
	private int parentId;
	
//	@Column(name = "GroupID")
//	private int groupId;
//	
//	@Transient
//	private String groupName;
	
	@Column(name = "ResourceURL")
	private String resourceUrl;
	
	@Column(name = "ResourceName")
	private String resourceName;
	
	@Column(name = "ResourceGroupID")
	private String resourceGroupId;

}
