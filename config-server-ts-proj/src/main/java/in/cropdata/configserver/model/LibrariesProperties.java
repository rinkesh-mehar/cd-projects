package in.cropdata.configserver.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
@Data
@Entity(name = "libraries_properties")
public class LibrariesProperties {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "LibraryID")
	private int libraryId;

	@Column(name = "AttrKey")
	private String attrKey;

	@Column(name = "AttrValue")
	private String attrValue;

	@Column(name = "UpdatedAt")
	private Date updatedAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CreatedAt")
	private Date createdAt;

	@Column(name = "Status")
	private String status;
}
