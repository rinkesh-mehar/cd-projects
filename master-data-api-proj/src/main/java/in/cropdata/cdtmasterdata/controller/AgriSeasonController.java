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
import in.cropdata.cdtmasterdata.exceptions.InvalidDataException;
import in.cropdata.cdtmasterdata.model.AgriSeason;
import in.cropdata.cdtmasterdata.model.vo.AgriSeasonVO;
import in.cropdata.cdtmasterdata.service.AgriSeasonService;

@RestController
@RequestMapping("/agri/season")
public class AgriSeasonController {

	@Autowired
	private AgriSeasonService agriSeasonService;

	@GetMapping("/list")
	public List<AgriSeason> getAllAgriSeason() {
		return agriSeasonService.getAllAgriSeason();
	}// getAllAgriSeason
	
	@GetMapping("/season-list")
	public List<AgriSeasonVO> getSeasonList() {
		return agriSeasonService.getSeasonList();
	}
	
	@GetMapping("/paginatedList")
	public Page<AgriSeason> getSeasonListByPagenation(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {
		
		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return agriSeasonService.getSeasonListByPagenation(page, size, searchText);
	}

	@PostMapping("/add")
	public ResponseMessage addAgriSeason(@RequestBody AgriSeason agriSeason) {
		return agriSeasonService.addAgriSeason(agriSeason);
	}// addAgriSeason

	@PutMapping("/{id}/update")
	public ResponseMessage updateAgriSeasonById(@PathVariable int id, @RequestBody AgriSeason agriSeason) {
		return agriSeasonService.updateAgriSeasonById(id, agriSeason);
	}// updateAgriSeasonById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return agriSeasonService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return agriSeasonService.finalApproveById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriSeasonById(@PathVariable int id) {
		return agriSeasonService.deleteAgriSeasonById(id);
	}// deleteAgriSeasonById
	
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return agriSeasonService.rejectById(id);
	}// rejectById

	@GetMapping("/{id}")
	public AgriSeason findAgriSeasonById(@PathVariable int id) {
		return agriSeasonService.findAgriSeasonById(id);
	}// findAgriSeasonById
	
	@GetMapping("/season")
	public List<AgriSeason> getSeasonByStateCode(@RequestParam("stateCode") int stateCode) {
		return agriSeasonService.getSeasonByStateCode(stateCode);
	}// getSeasonByStateCode

}// AgriSeasonController
