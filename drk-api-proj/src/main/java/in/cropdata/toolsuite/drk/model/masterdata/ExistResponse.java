package in.cropdata.toolsuite.drk.model.masterdata;

import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class ExistResponse {

	private boolean exist;

	Map<String, Object> metadata;

}