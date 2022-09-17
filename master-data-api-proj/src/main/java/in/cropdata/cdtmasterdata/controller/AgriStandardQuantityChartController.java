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
import in.cropdata.cdtmasterdata.dto.interfaces.AgriStandardQuantityChartInfDto;
import in.cropdata.cdtmasterdata.model.AgriStandardQuantityChart;
import in.cropdata.cdtmasterdata.service.AgriStandardQuantityChartService;

@RestController
@RequestMapping("/agri/standard-quantity-chart")
public class AgriStandardQuantityChartController {
	
	@Autowired
	private AgriStandardQuantityChartService agriStandardQuantityChartService;
	
	@GetMapping("/list")
	public List<AgriStandardQuantityChartInfDto> getAllAgriStandardQuantityChart() {
		return agriStandardQuantityChartService.getAllAgriStandardQuantityChart();
	}// getAllAgriStandardQuantityChart

	@GetMapping()
	public Page<AgriStandardQuantityChartInfDto> getAgriStandardQuantityChartByPaginated(@RequestParam("page") int page,
																						 @RequestParam("isValid") int isValid,
																						 @RequestParam("size") int size,
																						 @RequestParam(required = false, defaultValue = "") String searchText,
																						 @RequestParam(required = false, defaultValue = "0") String missing)
	{
		return agriStandardQuantityChartService.getAgriStandardQuantityChartByPaginated(page, size, isValid, searchText,missing);
	}// getAgriStandardQuantityChartByPaginated

	@PostMapping("/add")
	public ResponseMessage addAgriStandardQuantityChart(@RequestBody AgriStandardQuantityChart agriStandardQuantityChart) {
		return agriStandardQuantityChartService.addAgriStandardQuantityChart(agriStandardQuantityChart);
	}// addAgriStandardQuantityChart

	@PutMapping("/{id}/update")
	public ResponseMessage updatAgriStandardQuantityChart(@PathVariable int id,
			@RequestBody AgriStandardQuantityChart agriStandardQuantityChart) {
		return agriStandardQuantityChartService.updateAgriStandardQuantityChart(id, agriStandardQuantityChart);
	}// updateAgriStandardQuantityChart

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return agriStandardQuantityChartService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return agriStandardQuantityChartService.finalApproveById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriStandardQuantityChart(@PathVariable int id) {
		return agriStandardQuantityChartService.deleteAgriStandardQuantityChart(id);
	}// deleteAgriStandardQuantityChart

	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return agriStandardQuantityChartService.rejectById(id);
	}// rejectById

	@GetMapping("/{id}")
	public AgriStandardQuantityChart getAgriStandardQuantityChartById(@PathVariable int id) {
		return agriStandardQuantityChartService.getAgriStandardQuantityChartById(id);
	}// getAgriStandardQuantityChartById
	
	@PostMapping("/move-to-master/{id}")
	public ResponseMessage moveToMaster(@PathVariable Integer id) {
		return agriStandardQuantityChartService.moveToMaster(id);
	}
	
	}
