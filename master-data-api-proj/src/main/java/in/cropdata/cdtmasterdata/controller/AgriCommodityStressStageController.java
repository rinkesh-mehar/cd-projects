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
import in.cropdata.cdtmasterdata.dto.interfaces.AgriCommodityStressStageInfDto;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriDistrictCommodityStressInfDto;
import in.cropdata.cdtmasterdata.model.AgriCommodityStressStage;
import in.cropdata.cdtmasterdata.model.vo.AgriStageVO;
import in.cropdata.cdtmasterdata.service.AgriCommodityStressStageService;

@RestController
@RequestMapping("/agri/commodity-stress-stage")
public class AgriCommodityStressStageController {

	@Autowired
	private AgriCommodityStressStageService agriStressStageService;

	@GetMapping("/list")
	public List<AgriCommodityStressStageInfDto> getAgriCommodityStressStageList() {
		return agriStressStageService.getAgriCommodityStressStageList();
	}// getAllAgriStressStage

	@GetMapping()
	public Page<AgriCommodityStressStageInfDto> getAllAgriCommodityStressStagePaginated(@RequestParam("page") int page,
																	  @RequestParam("size") int size,
																	  @RequestParam("isValid") int isValid,
																	  @RequestParam(required = false, defaultValue = "") String searchText,
																	  @RequestParam(required = false,defaultValue = "0") String missing)
	{
		return agriStressStageService.getAllAgriCommodityStressStagePaginated(page, size, searchText, isValid,missing);
	}// getAllAgriStressStagePaginated

	@PostMapping("/add")
	public ResponseMessage addAgriCommodityStressStage(@RequestBody AgriCommodityStressStage agriStressStage) {
		return agriStressStageService.addAgriCommodityStressStage(agriStressStage);
	}// addAgriStressStage

	@PutMapping("/{id}/update")
	public ResponseMessage updateAgriCommodityStressStageById(@PathVariable int id,
			@RequestBody AgriCommodityStressStage agriStressStage) {
		return agriStressStageService.updateAgriCommodityStressStageById(id, agriStressStage);
	}// updateAgriStressStageById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return agriStressStageService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return agriStressStageService.finalApproveById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriStressStageById(@PathVariable int id) {
		return agriStressStageService.deleteAgriCommodityStressStageById(id);
	}// deleteAgriStressStageById
	
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return agriStressStageService.rejectById(id);
	}// rejectById

	@GetMapping("/{id}")
	public AgriCommodityStressStage findAgriStressStageById(@PathVariable int id) {
		return agriStressStageService.findAgriCommodityStressStageById(id);
	}// findAgriStressStageById
	
	@GetMapping("/{commodityId}/{stressTypeId}")
	public List<AgriDistrictCommodityStressInfDto> getCommodityIdAndStressTypeId(@PathVariable int commodityId, @PathVariable int stressTypeId){
		return agriStressStageService.getByCommodityIdAndStressTypeId(commodityId, stressTypeId);
	}//getCommodityIdAndStressTypeId
	
	@PostMapping("/move-to-master/{id}")
	public ResponseMessage moveToMaster(@PathVariable Integer id) {
		return agriStressStageService.moveToMaster(id);
	}
	
	@GetMapping("/getStageByStressId")
	public List<AgriStageVO> getStageListByStressId(@RequestParam int stressId) {
		return agriStressStageService.getStageListByStressId(stressId);
	}

}// AgriStressStageController
