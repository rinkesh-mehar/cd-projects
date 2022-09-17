package in.cropdata.cdtmasterdata.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity(name = "agri_agrochemical_stress")
public class AgriAgrochemicalStress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "AgrochemicalID")
	private int agrochemicalId;

	@Column(name = "StressID")
	private int stressID;
}
