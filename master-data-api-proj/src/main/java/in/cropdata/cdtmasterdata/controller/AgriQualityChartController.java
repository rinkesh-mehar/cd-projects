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
import in.cropdata.cdtmasterdata.dto.interfaces.AgriQualityChartInfDto;
import in.cropdata.cdtmasterdata.model.AgriQualityChart;
import in.cropdata.cdtmasterdata.service.AgriQualityChartService;

@RestController
@RequestMapping("/agri/quality-chart")
public class AgriQualityChartController {
	
	@Autowired
	private AgriQualityChartService agriQualityChartService;

	@GetMapping("/list")
	public List<AgriQualityChart> getAllAgriQualityChart() {
		return agriQualityChartService.getAllAgriQualityChart();
	}// getAllAgriQualityChart

	@GetMapping()
	public Page<AgriQualityChartInfDto> getAllAgriQualityChartPaginated(@RequestParam("page") int page,
			@RequestParam("size") int size, 
			@RequestParam(required = false, defaultValue = "") String searchText,@RequestParam(required = false,defaultValue = "0") String missing) {
		return agriQualityChartService.getAllAgriQualityChartPaginated(page, size,searchText,missing);
	}// getAllAgriQualityChartPaginated
	

	@PostMapping("/add")
	public ResponseMessage addAgriQualityChart(@RequestBody AgriQualityChart agriQualityChart) {
		return agriQualityChartService.addAgriQualityChart(agriQualityChart);
	}// addAgriQualityChart

	@PutMapping("/{id}/update")
	public ResponseMessage updateAgriQualityChartById(@PathVariable int id, @RequestBody AgriQualityChart agriQualityChart) {
		return agriQualityChartService.updateAgriQualityChartById(id, agriQualityChart);
	}// updateAgriQualityChartById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return agriQualityChartService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return agriQualityChartService.finalApproveById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriQualityChartById(@PathVariable int id) {
		return agriQualityChartService.deleteAgriQualityChartById(id);
	}// deleteAgriQualityChartById
	
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return agriQualityChartService.rejectById(id);
	}// rejectById

	@GetMapping("/{id}")
	public AgriQualityChart findAgriQualityChartById(@PathVariable int id) {
		return agriQualityChartService.findAgriQualityChartById(id);
	}// findAgriQualityChartById
	
	@PostMapping("/move-to-master/{id}")
	public ResponseMessage moveToMaster(@PathVariable Integer id) {
		return agriQualityChartService.moveToMaster(id);
	}

}
