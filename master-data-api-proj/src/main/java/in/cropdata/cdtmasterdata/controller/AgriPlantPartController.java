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
import in.cropdata.cdtmasterdata.dto.interfaces.AgriPlantPartInfo;
import in.cropdata.cdtmasterdata.model.AgriPlantPart;
import in.cropdata.cdtmasterdata.service.AgriPlantPartService;

@RestController
@RequestMapping("/agri/plant-part")
public class AgriPlantPartController {

	@Autowired
	private AgriPlantPartService agriPlantPartService;

	@GetMapping("/list")
	public List<AgriPlantPart> getAllAgriPlantPart() {
		return agriPlantPartService.getAllAgriPlantPart();
	}// getAllAgriPlantPart

	@GetMapping()
	public Page<AgriPlantPartInfo> getAllAgriPlantPartPeginated(@RequestParam("page") int page,
			@RequestParam("size") int size, 
			@RequestParam(required = false, defaultValue = "") String searchText) {
		return agriPlantPartService.getAllAgriPlantPartPeginated(page, size,searchText);
	}// getAllAgriPlantPartPeginated

	@PostMapping("/add")
	public ResponseMessage addAgriPlantPart(@RequestBody AgriPlantPart agriPlantPart) {
		return agriPlantPartService.addAgriPlantPart(agriPlantPart);
	}// addAgriPlantPart

	@PutMapping("/{id}/update")
	public ResponseMessage updateAgriPlantPartById(@PathVariable int id, @RequestBody AgriPlantPart agriPlantPart) {
		return agriPlantPartService.updateAgriPlantPartById(id, agriPlantPart);
	}// updateAgriPlantPartById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return agriPlantPartService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return agriPlantPartService.finalApproveById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriPlantPartById(@PathVariable int id) {
		return agriPlantPartService.deleteAgriPlantPartById(id);
	}// deleteAgriPlantPartById

	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return agriPlantPartService.rejectById(id);
	}// rejectById

	@GetMapping("/{id}")
	public AgriPlantPart findAgriPlantPartById(@PathVariable int id) {
		return agriPlantPartService.findAgriPlantPartById(id);
	}// findAgriPlantPartById

}// AgriPlantPartController
