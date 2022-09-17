package in.cropdata.cdtmasterdata.acl.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import in.cropdata.cdtmasterdata.dto.interfaces.Restriction;
import lombok.Data;

@Data
@Entity(name = "acl_roles")
public class AclRole {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@Column(name = "Name")
	private String name;
	
	@Column(name = "Description")
	private String description;
	
	@Transient
	private List<Restriction> restrictions = new ArrayList<>();
	
}
