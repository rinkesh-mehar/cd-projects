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
import in.cropdata.cdtmasterdata.dto.interfaces.AgriDistrictCommodityStressInfDto;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriCommodityStressStageInfDto;
import in.cropdata.cdtmasterdata.model.AgriDistrictCommodityStress;
import in.cropdata.cdtmasterdata.model.vo.AgriDistrictCommodityStressVO;
import in.cropdata.cdtmasterdata.service.AgriDistrictCommodityStressService;


@RestController
@RequestMapping("/agri/district-commodity-stress")
public class AgriDistrictCommodityStressController {

	@Autowired
	private AgriDistrictCommodityStressService agriDistrictCommodityStressService;

	@GetMapping("/list")
	public List<AgriDistrictCommodityStress> getDistrictCommodityStress() {
		return agriDistrictCommodityStressService.getDistrictCommodityStress();
	}// getAllAgriStress

	@GetMapping("/{commodityId}/{stressTypeId}/list")
	public List<AgriDistrictCommodityStressInfDto> getAllAgriStressByType(@PathVariable int commodityId,@PathVariable int stressTypeId) {
		return agriDistrictCommodityStressService.getAllAgriStressByStressType(commodityId,stressTypeId);
	}// getAllAgriStress
	
	@GetMapping("/commodity/getStressList")
	public List<AgriCommodityStressStageInfDto> getAllAgriStressByCommodity(@RequestParam("commodityId") int commodityId) {
		return agriDistrictCommodityStressService.getStressByCommodityId(commodityId);
	}// getAllAgriStress
	
	@GetMapping("/{commodityId}/{phenophaseId}")  //done
	public List<AgriDistrictCommodityStressInfDto> getAllStressByCommodity(@PathVariable int commodityId,@PathVariable int phenophaseId){
		return agriDistrictCommodityStressService.getAllStressByCommodityId(commodityId,phenophaseId);
	}//getAllStressByCommodityId

	@GetMapping()
	public Page<AgriDistrictCommodityStressInfDto> getDistrictCommodityStressPaginatedList(@RequestParam("page") int page,
														@RequestParam("size") int size,
														@RequestParam("isValid") int isValid,
														@RequestParam(required = false, defaultValue = "") String searchText)
	{
		return agriDistrictCommodityStressService.getDistrictCommodityStressPaginatedList(page, size, searchText, isValid);
	}// getAllStressPaginated

	@PostMapping("/add")
	public ResponseMessage addAgriDistrictCommodityStress(@ModelAttribute AgriDistrictCommodityStressVO agriDistrictCommodityStressVO) {
		return agriDistrictCommodityStressService.addAgriDistrictCommodityStress(agriDistrictCommodityStressVO);
	}// addAllAgriStress

	@PutMapping("/{id}/update")
	public ResponseMessage updateAgriDistrictCommodityStressById(@PathVariable int id, @ModelAttribute AgriDistrictCommodityStressVO agriDistrictCommodityStressVO) {
		return agriDistrictCommodityStressService.updateAgriDistrictCommodityStressById(id, agriDistrictCommodityStressVO);
	}// updateAgriStressById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return agriDistrictCommodityStressService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return agriDistrictCommodityStressService.finalApproveById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriStressById(@PathVariable int id) {
		return agriDistrictCommodityStressService.deleteAgriStressById(id);
	}// deleteAgriStressById

	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return agriDistrictCommodityStressService.rejectById(id);
	}// rejectById

	@GetMapping("/{id}")
	public AgriDistrictCommodityStress findAgriStressById(@PathVariable int id) {
		return agriDistrictCommodityStressService.findAgriStressById(id);
	}// findAgriStressById

//	@GetMapping("/flipbook-symptoms/list")
//	public List<FlipbookSymptoms> getAllFlipbookSymptoms() {
//		return agriStressService.getAllFlipbookSymptoms();
//	}// findAgriStressById

}// AgriStressController