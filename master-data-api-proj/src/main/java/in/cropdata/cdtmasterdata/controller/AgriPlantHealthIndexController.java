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
import in.cropdata.cdtmasterdata.model.AgriPlantHealthIndex;
import in.cropdata.cdtmasterdata.model.vo.AgriPlantHealthIndexForPhenophaseVo;
import in.cropdata.cdtmasterdata.model.vo.AgriPlantHealthIndexForVarietyVo;
import in.cropdata.cdtmasterdata.model.vo.AgriPlantHealthIndexVo;
import in.cropdata.cdtmasterdata.service.AgriPlantHealthIndexService;

@RestController
@RequestMapping("/agri/plant-health-index")
public class AgriPlantHealthIndexController {
	
	@Autowired
	private AgriPlantHealthIndexService agriPlantHealthIndexService;

	@GetMapping("/list")
	public List<AgriPlantHealthIndex> getAllAgriPlantHealthIndexList() {
		return agriPlantHealthIndexService.getAllAgriPlantHealthIndexList();
	}// getAllAgriPlantHealthIndexList
	
	@GetMapping("/pagenated-list")
	public Page<AgriPlantHealthIndexVo> getAgriPlantHealthIndexPagenatedList(@RequestParam("page") int page,
																			 @RequestParam("size") int size,
																			 @RequestParam(required = false, defaultValue = "") String searchText,
																			 @RequestParam(required = false,defaultValue = "0") String missing) {
		return agriPlantHealthIndexService.getAgriPlantHealthIndexPagenatedList(page, size, searchText,missing);
	}

	@PostMapping("/add")
	public ResponseMessage addAllAgriPalntHealthIndex(@RequestBody AgriPlantHealthIndex agriPlantHealthIndex) {
		return agriPlantHealthIndexService.addAllAgriPalntHealthIndex(agriPlantHealthIndex);
	}// addAllAgriPalntHealthIndex

	@PutMapping("/{id}/update")
	public ResponseMessage updateAgriPlantHealthIndexById(@PathVariable int id, @RequestBody AgriPlantHealthIndex agriPlantHealthIndex) {
		return agriPlantHealthIndexService.updateAgriPlantHealthIndexById(id, agriPlantHealthIndex);
	}// updateAgriPlantHealthIndexById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return agriPlantHealthIndexService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return agriPlantHealthIndexService.finalApproveById(id);
	}// finalApproveById

	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return agriPlantHealthIndexService.rejectById(id);
	}// rejectById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriPlantHealthIndexById(@PathVariable int id) {
		return agriPlantHealthIndexService.deleteAgriPlantHealthIndexById(id);
	}// deleteAgriPlantHealthIndexById

	@GetMapping("/{id}")
	public AgriPlantHealthIndex findAgriPlantHealthIndexById(@PathVariable int id) {
		return agriPlantHealthIndexService.findAgriPlantHealthIndexById(id);
	}// findAgriPlantHealthIndexById
	
	@GetMapping("/variety-list")
	public List<AgriPlantHealthIndexForVarietyVo> getVarietyListByCommodity(@RequestParam("commodityId") int commodityId) {
		return agriPlantHealthIndexService.getVarietyListByCommodity(commodityId);
	}
	
	@GetMapping("/phenophase-list")
	public List<AgriPlantHealthIndexForPhenophaseVo> getPhenophaseListByCommodityAndVariety(@RequestParam("commodityId") int commodityId,@RequestParam("varietyId") int varietyId) {
		return agriPlantHealthIndexService.getPhenophaseListByCommodityAndVariety(commodityId,varietyId);
	}
	
	@PostMapping("/move-to-master/{id}")
	public ResponseMessage moveToMaster(@PathVariable Integer id) {
		return agriPlantHealthIndexService.moveToMaster(id);
	}
	

}
