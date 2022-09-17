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
import in.cropdata.cdtmasterdata.model.AgriSourceOfWater;
import in.cropdata.cdtmasterdata.service.AgriSourceOfWaterService;

@RestController
@RequestMapping("/agri/source-of-water")
public class AgriSourceOfWaterController {

	@Autowired
	private AgriSourceOfWaterService agriSourceOfWaterService;

	@GetMapping("/list")
	public List<AgriSourceOfWater> getAllAgriSourceOfWater() {
		return agriSourceOfWaterService.getAllAgriSourceOfWater();
	}// getAllAgriSourceOfWater
	
	@GetMapping("/paginatedList")
	public Page<AgriSourceOfWater> getSourceOfWaterListByPagenation(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {
		
		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return agriSourceOfWaterService.getSourceOfWaterListByPagenation(page, size, searchText);
	}

	@PostMapping("/add")
	public ResponseMessage addAgriSourceOfWater(@RequestBody AgriSourceOfWater agriSourceOfWater) {
		return agriSourceOfWaterService.addAgriSourceOfWater(agriSourceOfWater);
	}// addAgriSourceOfWater

	@PutMapping("/{id}/update")
	public ResponseMessage updateAgriSourceOfWaterById(@PathVariable int id,
			@RequestBody AgriSourceOfWater agriSourceOfWater) {
		return agriSourceOfWaterService.updateAgriSourceOfWaterById(id, agriSourceOfWater);
	}// updateAgriSourceOfWaterById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return agriSourceOfWaterService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return agriSourceOfWaterService.finalApproveById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriSourceOfWaterById(@PathVariable int id) {
		return agriSourceOfWaterService.deleteAgriSourceOfWaterById(id);
	}// deleteAgriSourceOfWaterById
	
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return agriSourceOfWaterService.rejectById(id);
	}// rejectById

	@GetMapping("/{id}")
	public AgriSourceOfWater findAgriSourceOfWaterById(@PathVariable int id) {
		return agriSourceOfWaterService.findAgriSourceOfWaterById(id);
	}// findAgriSourceOfWaterById

}// AgriSourceOfWaterController
