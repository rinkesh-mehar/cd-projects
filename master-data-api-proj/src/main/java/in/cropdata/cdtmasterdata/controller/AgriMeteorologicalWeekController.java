package in.cropdata.cdtmasterdata.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.model.AgriMeteorologicalWeek;
import in.cropdata.cdtmasterdata.service.AgriMeteorologicalWeekService;

@RestController
@RequestMapping("/agri/meteorological-week")
public class AgriMeteorologicalWeekController {

	@Autowired
	private AgriMeteorologicalWeekService agriMeteorologicalWeekService;
	
	@GetMapping("/list")
	public List<AgriMeteorologicalWeek> getAllAgriMeteorologicalWeek() {
		return agriMeteorologicalWeekService.getAllAgriMeteorologicalWeek();
	}// getAllagriMeteorologicalWeek

	@PostMapping("/add")
	public ResponseMessage addAgriMeteorologicalWeek(@RequestBody AgriMeteorologicalWeek agriMeteorologicalWeek) {
		return agriMeteorologicalWeekService.addAgriMeteorologicalWeek(agriMeteorologicalWeek);
	}// addagriMeteorologicalWeek

	@PutMapping("/{id}/update")
	public ResponseMessage updateAgriMeteorologicalWeekById(@PathVariable int id, @RequestBody AgriMeteorologicalWeek agriMeteorologicalWeek) {
		return agriMeteorologicalWeekService.updateAgriMeteorologicalWeekById(id, agriMeteorologicalWeek);
	}// updateagriMeteorologicalWeekById
	
	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return agriMeteorologicalWeekService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return agriMeteorologicalWeekService.finalApproveById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriMeteorologicalWeekById(@PathVariable int id) {
		return agriMeteorologicalWeekService.deleteAgriMeteorologicalWeekById(id);
	}// deleteagriMeteorologicalWeekById
	
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return agriMeteorologicalWeekService.rejectById(id);
	}// rejectById

	@GetMapping("/{id}")
	public AgriMeteorologicalWeek findAgriMeteorologicalWeekById(@PathVariable int id) {
		return agriMeteorologicalWeekService.findAgriMeteorologicalWeekById(id);
	}// findagriMeteorologicalWeekById
}//AgriMeteorologicalWeekController
