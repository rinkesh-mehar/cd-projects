package in.cropdata.configserver.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity(name = "application_libraries")
@Data
public class ApplicationLibraries {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@Column(name = "ApplicationID")
	private int applicationId;
	
	@Column(name = "EnvProfile")
	private String envProfile;
	
	@Column(name = "Label")
	private String label;
	
	@Column(name = "LibraryID")
	private int libraryId;
}
