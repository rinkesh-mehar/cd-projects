package in.cropdata.cdtmasterdata.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity(name = "agri_variety_stress_tolerant_stress")
public class AgriVarietyTolerantStress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "VarietyStressID")
	private int varietyStressID;

	@Column(name = "StressID")
	private int stressID;

}
