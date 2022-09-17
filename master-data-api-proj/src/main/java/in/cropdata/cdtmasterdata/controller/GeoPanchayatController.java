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
import in.cropdata.cdtmasterdata.dto.interfaces.GeoPanchayatInfDto;
import in.cropdata.cdtmasterdata.model.GeoPanchayat;
import in.cropdata.cdtmasterdata.service.GeoPanchayatService;

@RestController
@RequestMapping("/geo/panchayat")
public class GeoPanchayatController {

	@Autowired
	private GeoPanchayatService geoPanchayatService;

	@GetMapping("/list")
	public List<GeoPanchayatInfDto> getAllGeoPanchayat() {
		return geoPanchayatService.getAllGeoPanchayat();
	}// getGeoPanchayat

	@GetMapping()
	public Page<GeoPanchayatInfDto> getGeoPanchayatPaginated(@RequestParam("page") int page,
			@RequestParam("size") int size, 
			@RequestParam(required = false, defaultValue = "") String searchText) {
		return geoPanchayatService.getAllGeoPanchayatPaginated(page, size, searchText);
	}// getGeoPanchayatPaginated

	@PostMapping("/add")
	public ResponseMessage addGeoPanchayat(@RequestBody GeoPanchayat geoPanchayat) {
		return geoPanchayatService.addGeoPanchayat(geoPanchayat);
	}// addAllGeoPanchayat

	@PutMapping("/{id}/update")
	public ResponseMessage updateGeoPanchayatById(@PathVariable int id, @RequestBody GeoPanchayat geoPanchayat) {
		return geoPanchayatService.updateGeoPanchayatById(id, geoPanchayat);
	}// updateGeoPanchayatById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return geoPanchayatService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return geoPanchayatService.finalApproveById(id);
	}// finalApproveById

	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return geoPanchayatService.rejectById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteGeoPanchayatById(@PathVariable int id) {
		return geoPanchayatService.deleteGeoPanchayatById(id);
	}// deleteGeoPanchayatById

	@GetMapping("/{id}")
	public GeoPanchayat findGeoPanchayatById(@PathVariable int id) {
		return geoPanchayatService.findGeoPanchayatById(id);
	}

}// GeoPanchayatController
