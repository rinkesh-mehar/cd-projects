package in.cropdata.toolsuite.drk.model.cases;

import java.io.Serializable;
import java.math.BigInteger;

import lombok.Data;

@Data
public class CaseCompositeKey implements Serializable {

    private BigInteger caseId;
    private Integer regionID;

}