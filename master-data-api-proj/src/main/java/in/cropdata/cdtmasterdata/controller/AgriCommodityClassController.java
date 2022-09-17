package in.cropdata.cdtmasterdata.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import in.cropdata.cdtmasterdata.model.AgriCommodityClass;
import in.cropdata.cdtmasterdata.service.AgriCommodityClassService;

@RestController
@RequestMapping("/agri/commodity-class")
public class AgriCommodityClassController {

	@Autowired
	private AgriCommodityClassService agriCommodityClassService;

	@GetMapping("/list")
	public List<AgriCommodityClass> getAllAgriCommodityClass() {
		return agriCommodityClassService.getAllAgriCommodityClass();
	}// getAllAgriCommodityClass
	
	@GetMapping("/paginatedList")
	public Page<AgriCommodityClass> getCommodityClassListByPagenation(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {
		
		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return agriCommodityClassService.getCommodityClassListByPagenation(page, size, searchText);
	}


	@PostMapping("/add")
	public ResponseMessage addAgriCommodityClass(@RequestBody AgriCommodityClass agriCommodityClass) {
		return agriCommodityClassService.addAgriCommodityClass(agriCommodityClass);
	}// addAllAgriCommodityClass

	@PutMapping("/{id}/update")
	public ResponseMessage updateAgriCommodityClassById(@PathVariable int id,
			@RequestBody AgriCommodityClass agriCommodityClass) {
		return agriCommodityClassService.updateAgriCommodityClassById(id, agriCommodityClass);
	}// updateAgriCommodityClassById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return agriCommodityClassService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return agriCommodityClassService.finalApproveById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriCommodityClassById(@PathVariable int id) {
		return agriCommodityClassService.deleteAgriCommodityClassById(id);
	}// deleteAgriCommodityClassById

	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return agriCommodityClassService.rejectById(id);
	}// rejectById

	@GetMapping("/{id}")
	public AgriCommodityClass findAgriCommodityClassById(@PathVariable int id) {
		return agriCommodityClassService.findAgriCommodityClassById(id);
	}// findAgriCommodityClassById

}
