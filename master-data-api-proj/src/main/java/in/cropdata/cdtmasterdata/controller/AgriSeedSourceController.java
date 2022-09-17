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
import in.cropdata.cdtmasterdata.model.AgriSeedSource;
import in.cropdata.cdtmasterdata.service.AgriSeedSourceService;

@RestController
@RequestMapping("/agri/seed-source")
public class AgriSeedSourceController {
	
	@Autowired
	private AgriSeedSourceService agriSeedSourceService;
	
	@GetMapping("/list")
	public List<AgriSeedSource> getAllAgriSeedSource(){
		return agriSeedSourceService.getAllAgriSeedSource();
	}//getAllAgriSeedSource
	
	@GetMapping("/paginatedList")
	public Page<AgriSeedSource> getSeedSourceListByPagenation(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {
		
		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return agriSeedSourceService.getSeedSourceListByPagenation(page, size, searchText);
	}
	
	@PostMapping("/add")
	public ResponseMessage addAgriSeedSource(@RequestBody AgriSeedSource agriSeedSource) {
		return agriSeedSourceService.addAgriSeedSource(agriSeedSource);
	}//addAgriSeedSource
	
	@PutMapping("/{id}/update")
	public ResponseMessage updateAgriSeedSourceById(@PathVariable int id, @RequestBody AgriSeedSource agriSeedSource) {
		return agriSeedSourceService.updateAgriSeedSourceById(id, agriSeedSource);
	}//updateAgriSeedSourceById
	
	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return agriSeedSourceService.primaryApproveById(id);
	}//primaryApproveById
	
	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return agriSeedSourceService.finalApproveById(id);
	}//finalApproveById
	
	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriSeedSourceById(@PathVariable int id){
		return agriSeedSourceService.deleteAgriSeedSourceById(id);
	}//deleteAgriSeedSourceById
	
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return agriSeedSourceService.rejectById(id);
	}// rejectById
	
	@GetMapping("/{id}")
	public AgriSeedSource findAgriSeedSourceById(@PathVariable int id) {
		return agriSeedSourceService.findAgriSeedSourceById(id);
	}//findAgriSeedSourceById

}//AgriSeedSourceController
