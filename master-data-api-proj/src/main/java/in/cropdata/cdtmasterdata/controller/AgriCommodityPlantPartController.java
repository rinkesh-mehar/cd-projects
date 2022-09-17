package in.cropdata.cdtmasterdata.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriCommodityPlantPartInfDto;
import in.cropdata.cdtmasterdata.model.AgriCommodityPlantPart;
import in.cropdata.cdtmasterdata.model.vo.AgriPlantPartVO;
import in.cropdata.cdtmasterdata.service.AgriCommodityPlantPartService;

@RestController
@RequestMapping("/agri/commodity-plant-part")
public class AgriCommodityPlantPartController {

	@Autowired
	private AgriCommodityPlantPartService agriCommodityPlantPartService;

	@GetMapping("/list")
	public List<AgriCommodityPlantPartInfDto> getAllAgriCommodityPlantPart() {
		return agriCommodityPlantPartService.getAllAgriCommodityPlantPart();
	}// getAllAgriCommodityPlantPart

	@GetMapping()
	public Page<AgriCommodityPlantPartInfDto> getAllCommodityPlantPartPaginated(@RequestParam("page") int page,
																				@RequestParam("isValid") int isValid,
																				@RequestParam("size") int size,
																				@RequestParam(required = false, defaultValue = "") String searchText,
																				@RequestParam(required = false,defaultValue = "0") String missing)
	{
		return agriCommodityPlantPartService.getAllCommodityPlantPartPaginated(page, size, searchText, isValid,missing);
	}// getCommodityPlantPartPaginated

	
	@PostMapping("/add")
	public ResponseMessage addAgriCommodityPlantPart(@ModelAttribute AgriPlantPartVO plantPartVo) {
	    return agriCommodityPlantPartService.addAgriCommodityPlantPart(plantPartVo);
	}// addAgriCommodityPlantPart

	@PutMapping("/{id}/update")
	public ResponseMessage updateAgriCommodityPlantPartById(@PathVariable int id,
			@ModelAttribute AgriPlantPartVO plantPartVo) {
		System.out.println(plantPartVo.getImage());
		return agriCommodityPlantPartService.updateAgriCommodityPlantPartById(id, plantPartVo);
	}// updateAgriCommodityPlantPartById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return agriCommodityPlantPartService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return agriCommodityPlantPartService.finalApproveById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriCommodityPlantPartById(@PathVariable int id) {
		return agriCommodityPlantPartService.deleteAgriCommodityPlantPartById(id);
	}// deleteAgriCommodityPlantPartById
	
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return agriCommodityPlantPartService.rejectById(id);
	}// rejectById

	@GetMapping("/{id}")
	public AgriCommodityPlantPart findAgriCommodityPlantPartById(@PathVariable int id) {
		return agriCommodityPlantPartService.findAgriCommodityPlantPartById(id);
	}// findAgriCommodityPlantPartById
	
	@PostMapping("/move-to-master/{id}")
	public ResponseMessage moveToMaster(@PathVariable Integer id) {
		return agriCommodityPlantPartService.moveToMaster(id);
	}
	

}// AgriPlantPartController
