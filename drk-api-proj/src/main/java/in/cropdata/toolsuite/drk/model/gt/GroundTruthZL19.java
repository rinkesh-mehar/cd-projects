package in.cropdata.toolsuite.drk.model.gt;

import java.math.BigInteger;
import java.util.List;

import lombok.Data;

@Data
public class GroundTruthZL19 {

	private int regionId;
	private int caseId;
	private int villageCode;
	private BigInteger fieldTileId;
	private List<Stress> stress;
}
