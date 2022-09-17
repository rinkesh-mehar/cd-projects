package in.cropdata.configserver.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
@Data
@Entity(name = "libraries")
public class Libraries {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "Name")
	private String name;

	@Column(name = "UpdatedAt")
	private Date updatedAt;

	@Column(name = "CreatedAt")
	private Date createdAt;

	@Column(name = "Status")
	private String status;
}
