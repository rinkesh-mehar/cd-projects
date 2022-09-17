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
import in.cropdata.cdtmasterdata.dto.interfaces.AgriCommodityPhenophaseInfDto;
import in.cropdata.cdtmasterdata.model.AgriCommodityPhenophase;
import in.cropdata.cdtmasterdata.model.vo.AgriPhenophaseVo;
import in.cropdata.cdtmasterdata.service.AgriCommodityPhenophaseService;

@RestController
@RequestMapping("/agri/commodity-phenophase")
public class AgriCommodityPhenophaseController {

	@Autowired
	private AgriCommodityPhenophaseService agriCommodityPhenophaseService;

	@GetMapping("/list")
	public List<AgriCommodityPhenophaseInfDto> getAllAgriCommodityPhenophase() {
		return agriCommodityPhenophaseService.getAllAgriCommodityPhenophase();
	}// getAllAgriCommodityPhenophase

	@GetMapping()
	public Page<AgriCommodityPhenophaseInfDto> getAgriCommodityPhenophaseByPaginated(@RequestParam("page") int page,
			@RequestParam("size") int size, @RequestParam String searchText,@RequestParam(required = false,defaultValue = "0") String missing) {
		return agriCommodityPhenophaseService.getAgriCommodityPhenophaseByPaginated(page, size,searchText,missing);
	}// getAgriCommodityPhenophaseByPaginated

	@PostMapping("/add")
	public ResponseMessage addAgriCommodityPhenophase(@RequestBody AgriCommodityPhenophase agriCommodityPhenophase) {
		return agriCommodityPhenophaseService.addAgriCommodityPhenophase(agriCommodityPhenophase);
	}// addAgriCommodityPhenophase

	@PutMapping("/{id}/update")
	public ResponseMessage updatAgriCommodityPhenophase(@PathVariable int id,
			@RequestBody AgriCommodityPhenophase agriCommodityPhenophase) {
		return agriCommodityPhenophaseService.updateAgriCommodityPhenophase(id, agriCommodityPhenophase);
	}// updateAgriCommodityPhenophase

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return agriCommodityPhenophaseService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return agriCommodityPhenophaseService.finalApproveById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriCommodityPhenophase(@PathVariable int id) {
		return agriCommodityPhenophaseService.deleteAgriCommodityPhenophase(id);
	}// deleteAgriCommodityPhenophase

	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return agriCommodityPhenophaseService.rejectById(id);
	}// rejectById

	@GetMapping("/{id}")
	public AgriCommodityPhenophase getAgriCommodityPhenophaseById(@PathVariable int id) {
		return agriCommodityPhenophaseService.getAgriCommodityPhenophaseById(id);
	}// getAgriCommodityPhenophaseById
	
	@GetMapping("/{commodityId}/phenophase")
	public List<AgriPhenophaseVo> getByCommodityId(@PathVariable int commodityId){
//		System.out.println("Phenophase");
		return agriCommodityPhenophaseService.getByCommodityId(commodityId);
	
	}//getByCommodityId
	
	@PostMapping("/move-to-master/{id}")
	public ResponseMessage moveToMaster(@PathVariable Integer id) {
		return agriCommodityPhenophaseService.moveToMaster(id);
	}
	

}
