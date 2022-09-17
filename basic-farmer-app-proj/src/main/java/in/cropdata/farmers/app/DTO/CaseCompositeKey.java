package in.cropdata.farmers.app.DTO;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
public class CaseCompositeKey implements Serializable {


    private BigInteger caseId;
    private Integer regionID;
}
