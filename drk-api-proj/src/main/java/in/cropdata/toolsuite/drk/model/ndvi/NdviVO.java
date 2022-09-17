package in.cropdata.toolsuite.drk.model.ndvi;

import java.math.BigInteger;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NdviVO {
	
    	
	private String url;
	
	@JsonAlias(value = {"CaseId","CaseID"})
	private List<BigInteger> caseId;
	
	@JsonAlias(value = {"Year","year"})
	private Integer year;
	
	@JsonAlias(value = {"Week","week"})
	private Integer week;

	private Integer ndvi;

}