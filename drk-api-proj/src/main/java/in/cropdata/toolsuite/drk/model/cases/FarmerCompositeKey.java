package in.cropdata.toolsuite.drk.model.cases;

import java.io.Serializable;
import java.math.BigInteger;

import lombok.Data;

@Data
public class FarmerCompositeKey implements Serializable {

    private BigInteger farmerId;
    private Integer regionId;

}