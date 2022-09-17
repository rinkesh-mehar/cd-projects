package in.cropdata.toolsuite.drk.model.cases;

import java.io.Serializable;
import java.math.BigInteger;

import lombok.Data;

@Data
public class FarmCompositeKey implements Serializable {

    private BigInteger farmId;
    private Integer regionId;

}