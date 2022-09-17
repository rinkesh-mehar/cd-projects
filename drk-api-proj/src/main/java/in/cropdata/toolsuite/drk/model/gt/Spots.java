package in.cropdata.toolsuite.drk.model.gt;

import java.math.BigInteger;
import java.util.List;

import lombok.Data;

@Data
public class Spots {

	private BigInteger spotId;
	private String sampleId;
	private List<Stress> stress;

}
