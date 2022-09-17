package in.cropdata.cdtmasterdata.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity(name = "agri_stress_symptoms")
public class AgriStressSymptom {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "SymptomID")
	private int symptomId;

	@Column(name = "StressID")
	private int stressId;
}
