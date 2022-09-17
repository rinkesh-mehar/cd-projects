package in.cropdata.farmers.app.masters.model.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import in.cropdata.farmers.app.masters.model.StressControlRecommendation;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class MasterRecommendation {
    public String controlMeasure;
    public List<StressControlRecommendation> recommendations;
}
