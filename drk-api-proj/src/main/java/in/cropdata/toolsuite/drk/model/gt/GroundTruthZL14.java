package in.cropdata.toolsuite.drk.model.gt;

import java.math.BigInteger;
import java.util.List;

import lombok.Data;

@Data
public class GroundTruthZL14 {

	private int regionId;
	private int caseId;
	private int villageCode;
	private BigInteger villageTileId;
	private List<Stress> stress;
}
