package in.cropdata.cdtmasterdata.website.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.InvalidDataException;
import in.cropdata.cdtmasterdata.website.dto.DepartmentDto;
import in.cropdata.cdtmasterdata.website.model.Department;
import in.cropdata.cdtmasterdata.website.service.DepartmentService;

/**
 * Controller for processing API requests.
 * @author OM
 */

@RestController
@RequestMapping("/site/department")
public class DepartmentController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

	@Autowired
	private DepartmentService departmentService;
	
	/** Department Handling APIs */

	@GetMapping("/list")
	public List<DepartmentDto> getDepartmentList() {

		return departmentService.getDepartmentList();
	}

	@GetMapping("/paginatedList")
	public Page<DepartmentDto> getDepartmentListByPagenation(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {
		LOGGER.info("Inside Department Pagenation List...");

		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return departmentService.getDepartmentListByPagenation(page, size, searchText);
	}
	
	@PostMapping("/add")
	public ResponseEntity<ResponseMessage> addDepartment(@RequestBody Department department) {

		LOGGER.info("Add Department From Controller...");
		return new ResponseEntity<>(departmentService.addDepartment(department), HttpStatus.CREATED);
	}
	
	@GetMapping("id/{id}")
	public Department getDepartmentById(@PathVariable(required = true) Integer id) {

		return departmentService.getDepartmentById(id);

	}
	
	@PutMapping("update/{id}")
	public ResponseEntity<ResponseMessage> updateDepartment(@PathVariable(required = true) Integer id,
			@RequestBody Department department) {

		if (id == null) {
			throw new InvalidDataException("Department Id can not be null!");
		}

		if (department == null) {
			throw new InvalidDataException("Department data can not be null!");
		}

		LOGGER.info("Updating Department From Controller for ID -> {}", id);
		return new ResponseEntity<>(departmentService.updateDepartment(id, department), HttpStatus.OK);
	}
	
	@PutMapping("/deactive/{id}")
	public ResponseMessage deactiveDepartment(@PathVariable int id) {
		return departmentService.deactiveDepartment(id);
	}
	
	@PutMapping("/active/{id}")
	public ResponseMessage activeDepartmentById(@PathVariable int id) {
		return departmentService.activeDepartment(id);
	}
	
	@GetMapping("/opportunityCount/{departmentId}")
	public Integer opportunityCountByDepartment(@PathVariable int departmentId) {
		return departmentService.opportunityCountByDepartment(departmentId);
	}
	
	@PutMapping("/delete/{id}")
	public ResponseMessage deleteDepartmentById(@PathVariable int id) {
		return departmentService.deleteDepartment(id);
	}
}
