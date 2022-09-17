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
import in.cropdata.cdtmasterdata.dto.interfaces.AgriQuantityLossChartInfDto;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriStandardQuantityChartInfDto;
import in.cropdata.cdtmasterdata.model.AgriQuantityLossChart;
import in.cropdata.cdtmasterdata.service.AgriQuantityLossChartService;

@RestController
@RequestMapping("/agri/quantity-loss-chart")
public class AgriQuantityLossChartController {

	@Autowired
	private AgriQuantityLossChartService agriQuantityLossChartService;

	@GetMapping("/list")
	public List<AgriQuantityLossChartInfDto> getAllAgriQuantityLossChart() {
		return agriQuantityLossChartService.getAllAgriQuantityLossChart();
	}// getAllAgriMaximumYeldLossPercent

	@GetMapping()
	public Page<AgriQuantityLossChartInfDto> getAgriQuantityLossChartByPaginated(@RequestParam("page") int page,
																				 @RequestParam("size") int size,
																				 @RequestParam("isValid") int isValid,
																				 @RequestParam(required = false, defaultValue = "") String searchText,
																				 @RequestParam(required = false,defaultValue = "0") String missing)
	{                                                                             
		return agriQuantityLossChartService.getAgriQuantityLossChartByPaginated(page, size, searchText, isValid,missing);
	}// getAllAgriStandardQuantityChartPaginated

	@PostMapping("/add")
	public ResponseMessage addAgriQuantityLossChart(@RequestBody AgriQuantityLossChart agriQuantityLossChart) {
		return agriQuantityLossChartService.addAgriQuantityLossChart(agriQuantityLossChart);
	}// addAgriMaximumYeldLossPercent

	@PutMapping("/{id}/update")
	public ResponseMessage updateAgriQuantityLossChart(@PathVariable int id,
			@RequestBody AgriQuantityLossChart ageiAgriMaximumYeldLossPercent) {
		return agriQuantityLossChartService.updateAgriQuantityLossChart(id, ageiAgriMaximumYeldLossPercent);
	}// updateAgriMaximumYeldLossPercentById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApprove(@PathVariable int id) {
		return agriQuantityLossChartService.primaryApproveById(id);
	}// primaryApprove

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApprove(@PathVariable int id) {
		return agriQuantityLossChartService.finalApproveById(id);
	}// finalApprove

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriQuantityLossChart(@PathVariable int id) {
		return agriQuantityLossChartService.deleteAgriQuantityLossChart(id);
	}// deleteAgriMaximumYeldLossPercent

	@PutMapping("/{id}/reject")
	public ResponseMessage rejectAgriQuantityLossChart(@PathVariable int id){
		return agriQuantityLossChartService.rejectById(id);
	}

	@GetMapping("/{id}")
	public AgriQuantityLossChart getAgriQuantityLossChartById(@PathVariable int id) {
		return agriQuantityLossChartService.getAgriQuantityLossChartById(id);
	}// findAgriMaximumYeldLossPercentById
	
	@PostMapping("/move-to-master/{id}")
	public ResponseMessage moveToMaster(@PathVariable Integer id) {
		return agriQuantityLossChartService.moveToMaster(id);
	}
}
