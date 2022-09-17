package in.cropdata.cdtmasterdata.controller;

import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriDistrictCommodityStressInfDto;
import in.cropdata.cdtmasterdata.model.AgriStress;
import in.cropdata.cdtmasterdata.service.AgriStressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agri/stress")
public class AgriStressController {

	@Autowired
	private AgriStressService agriStressService;

	@GetMapping("/list")
	public List<AgriStress> getAllAgriStress() {
		return agriStressService.getAllAgriStress();
	}// getAllAgriStress

	@GetMapping("/{commodityId}/{stressTypeId}/list")
	public List<AgriDistrictCommodityStressInfDto> getAllAgriStressByType(@PathVariable int commodityId,@PathVariable int stressTypeId) {
		return agriStressService.getAllAgriStressByStressType(commodityId,stressTypeId);
	}// getAllAgriStress
	
	@GetMapping()
	public Page<AgriDistrictCommodityStressInfDto> getAllStressPaginated(@RequestParam("page") int page,
														@RequestParam("size") int size,
														@RequestParam(required = false, defaultValue = "") String searchText)
	{
		return agriStressService.getAllStressPaginated(page, size, searchText);
	}// getAllStressPaginated

	@PostMapping("/add")
	public ResponseMessage addAgriStress(@RequestBody AgriStress agriStress) {
		return agriStressService.addAgriStress(agriStress);
	}// addAllAgriStress

	@PutMapping("/{id}/update")
	public ResponseMessage updateAgriStressById(@PathVariable int id, @RequestBody AgriStress agriStress) {
		return agriStressService.updateAgriStressById(id, agriStress);
	}// updateAgriStressById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return agriStressService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return agriStressService.finalApproveById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriStressById(@PathVariable int id) {
		return agriStressService.deleteAgriStressById(id);
	}// deleteAgriStressById

	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return agriStressService.rejectById(id);
	}// rejectById

	@GetMapping("/{id}")
	public AgriStress findAgriStressById(@PathVariable int id) {
		return agriStressService.findAgriStressById(id);
	}// findAgriStressById

//	@GetMapping("/flipbook-symptoms/list")
//	public List<FlipbookSymptoms> getAllFlipbookSymptoms() {
//		return agriStressService.getAllFlipbookSymptoms();
//	}// findAgriStressById

}// AgriStressController