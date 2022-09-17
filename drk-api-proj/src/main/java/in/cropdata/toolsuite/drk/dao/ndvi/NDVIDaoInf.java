package in.cropdata.toolsuite.drk.dao.ndvi;

import org.springframework.stereotype.Repository;

@Repository
public interface NDVIDaoInf {

	Long getAverageNDVI(String spotID);

}
