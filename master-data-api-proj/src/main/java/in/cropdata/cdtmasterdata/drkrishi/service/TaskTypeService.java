package in.cropdata.cdtmasterdata.drkrishi.service;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import in.cropdata.cdtmasterdata.drkrishi.repository.TaskTypeRepository;

@Service
public class TaskTypeService {

	@Autowired
	private TaskTypeRepository taskTypeRepository;

	public List<Map<String, Object>> getTaskTypeList() {
		return this.taskTypeRepository.getTaskTypeList();
	}

}
