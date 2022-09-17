package in.cropdata.toolsuite.drk.model.gt;

import java.util.List;

import lombok.Data;

@Data
public class GroundTruth {

	private int regionId;
	private long caseId;
	private List<Spots> spots;

}