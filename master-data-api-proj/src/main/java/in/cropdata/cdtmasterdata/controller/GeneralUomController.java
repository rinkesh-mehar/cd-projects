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
import in.cropdata.cdtmasterdata.dto.interfaces.AgriCommodityInfo;
import in.cropdata.cdtmasterdata.model.GeneralUom;
import in.cropdata.cdtmasterdata.service.GeneralUomService;

@RestController
@RequestMapping("/general/uom")
public class GeneralUomController {

	@Autowired
	private GeneralUomService generalUomService;

	@GetMapping("/list")
	public List<GeneralUom> getAllGeneralUom() {
		return generalUomService.getAllGeneralUom();
	}// getAllGeneralUom
	
	@GetMapping("/paginatedList")
	public Page<GeneralUom> getPeginatedGeneralUomList(@RequestParam("page") int page,
			@RequestParam("size") int size, @RequestParam(required = false, defaultValue = "") String searchText) {
		return generalUomService.getPeginatedGeneralUomList(page, size, searchText);
	}

	@PostMapping("/add")
	public ResponseMessage addGeneralUom(@RequestBody GeneralUom generalUom) {
		return generalUomService.addGeneralUom(generalUom);
	}// addAllGeneralUom

	@PutMapping("/{id}/update")
	public ResponseMessage updateGeneralUomById(@PathVariable int id, @RequestBody GeneralUom generalUom) {
		return generalUomService.updateGeneralUomById(id, generalUom);
	}// updateGeneralUomById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return generalUomService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return generalUomService.finalApproveById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteGeneralUomById(@PathVariable int id) {
		return generalUomService.deleteGeneralUomById(id);
	}// deleteGeneralUomById

	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return generalUomService.rejectById(id);
	}// rejectById

	@GetMapping("/{id}")
	public GeneralUom findGeneralUomById(@PathVariable int id) {
		return generalUomService.findGeneralUomById(id);
	}// findGeneralUomById

}// GeneralUomController
