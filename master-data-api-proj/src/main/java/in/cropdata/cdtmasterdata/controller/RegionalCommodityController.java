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
import in.cropdata.cdtmasterdata.dto.interfaces.RegionalCommodityDtoInf;
import in.cropdata.cdtmasterdata.model.RegionalCommodity;
import in.cropdata.cdtmasterdata.service.RegionalCommodityService;

@RestController
@RequestMapping("/regional/commodity")
public class RegionalCommodityController {
	
	@Autowired
	private RegionalCommodityService regionalCommodityService;
	
	@GetMapping("/list")
	public List<RegionalCommodity> getAllRegionalCommodity(){
		return regionalCommodityService.getAllAgriRegionalCommodity();
	}//getAllRegionalCommodity

	@GetMapping()
	public Page<RegionalCommodityDtoInf> getAllRegionalCommodityPaginated(@RequestParam("page") int page,
																		  @RequestParam("size") int size,
																		  @RequestParam("isValid") int isValid,
																		  @RequestParam(required = false, defaultValue = "") String searchText, 
																		  @RequestParam(required = false,defaultValue = "0") String missing)
	{
			
		return regionalCommodityService.getAllRegionalCommodityPaginated(page, size,missing,searchText, isValid);

	}// getAllRegionalCommodityPaginated
	
	@PostMapping("/add")
	public ResponseMessage addRegionalCommodity(@RequestBody RegionalCommodity regionalCommodity) {
		return regionalCommodityService.addregionCommodity(regionalCommodity);
	}//addRegionalCommodity
	
	@PutMapping("/{id}/update")
	public ResponseMessage updateRegionalCommodityById(@PathVariable int id,@RequestBody RegionalCommodity regionalCommodity) {
		return regionalCommodityService.updateregionCommodityById(id, regionalCommodity);
	}//updateRegionalCommodityById
	
	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return regionalCommodityService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return regionalCommodityService.finalApproveById(id);
	}// finalApproveById
	
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return regionalCommodityService.rejectById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteRegionalCommodityById(@PathVariable int id) {
		return regionalCommodityService.deleteregionCommodityById(id);
	}//deleteRegionalCommodityById
	
	@GetMapping("/{id}")
	public RegionalCommodity findRegionalCommodityById(@PathVariable int id) {
		return regionalCommodityService.findregionCommodityById(id);
	}//findRegionalCommodityById
	
	@PostMapping("/move-to-master/{id}")
	public ResponseMessage moveToMaster(@PathVariable Integer id) {
		return regionalCommodityService.moveToMaster(id);
	}

}
