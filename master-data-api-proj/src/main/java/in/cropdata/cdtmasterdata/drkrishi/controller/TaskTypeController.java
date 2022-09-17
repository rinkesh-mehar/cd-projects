/**
 * 
 */
package in.cropdata.cdtmasterdata.drkrishi.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.cdtmasterdata.drkrishi.service.TaskTypeService;

/**
 * @author pallavi-waghmare
 *
 */
@RestController
@RequestMapping("/task-type")
public class TaskTypeController {
	
	@Autowired
	private TaskTypeService taskTypeService;
	
	@GetMapping("/list")
	public List<Map<String,Object>> getTaskTypeList(){
		return this.taskTypeService.getTaskTypeList();
		
	}

}
