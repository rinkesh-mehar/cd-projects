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

import in.cropdata.cdtmasterdata.dto.RegionalConnectivityDTO;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.InvalidDataException;
import in.cropdata.cdtmasterdata.model.RegionalConnectivity;
import in.cropdata.cdtmasterdata.service.RegionalConnectivityService;

@RestController
@RequestMapping("/regional/connectivity")
public class RegionalConnectivityController {

	@Autowired
	private RegionalConnectivityService regionalConnectivityService;

	@GetMapping("/list")
	public List<RegionalConnectivityDTO> getRegionalConnectivityList() {

		return regionalConnectivityService.getRegionalConnectivityList();
	}
	
	@GetMapping("/paginatedList")
	public Page<RegionalConnectivityDTO> getRegionalConnectivityListByPagenation(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {
		
		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return regionalConnectivityService.getRegionalConnectivityListByPagenation(page, size, searchText);
	}


	@PostMapping("/addRegionalConnect")
	public ResponseMessage addRegionalConnect(@RequestBody 
			           RegionalConnectivity regionalConnectivity) {

		ResponseMessage response = null;
		try {
			response = regionalConnectivityService.saveRegionalConnectivityData(regionalConnectivity);
		} catch (Exception e) {
			e.getMessage();
		}

		return response;
	}

	@GetMapping("/getRegionalData/{id}")
	private RegionalConnectivity getDataById(@PathVariable int id) {
            System.out.println(" RegionalConnect id: " + id);
		return regionalConnectivityService.getRegionalConnectById(id);
	}
	
	
	@PutMapping("/updateRegionalData/{id}")
	private ResponseMessage updateRegionalData(@PathVariable int id,
			      @RequestBody RegionalConnectivity regionalConnectivity) {
		return regionalConnectivityService.updateRegionalConnect(id, regionalConnectivity);
	}
	
	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return regionalConnectivityService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return regionalConnectivityService.finalApproveById(id);
	}// finalApproveById
	
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return regionalConnectivityService.rejectById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteRegionSeasonById(@PathVariable int id) {
		return regionalConnectivityService.deleteRegionalConnectivityById(id);
	}
	
	
	

}
