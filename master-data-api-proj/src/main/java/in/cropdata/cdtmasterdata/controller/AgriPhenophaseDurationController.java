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
import in.cropdata.cdtmasterdata.dto.interfaces.AgriPhenophaseDurationInfDto;
import in.cropdata.cdtmasterdata.dto.interfaces.RegionalLanguageDtoInf;
import in.cropdata.cdtmasterdata.model.AgriPhenophaseDuration;
import in.cropdata.cdtmasterdata.model.vo.AgriPhenophaseDurationVO;
import in.cropdata.cdtmasterdata.service.AgriPhenophaseDurationService;

@RestController
@RequestMapping("/agri/phenophase-duration")
public class AgriPhenophaseDurationController {

	@Autowired
	private AgriPhenophaseDurationService agriPhenophaseDurationService;

	@GetMapping("/list")
	public List<AgriPhenophaseDurationInfDto> getAllAgriPhenophaseDuration() {
		return agriPhenophaseDurationService.getAllAgriphenophaseDuration();
	}// getAllAgriPhenophaseDuration

	@GetMapping()
	public Page<AgriPhenophaseDurationInfDto> getAllAgriPhenophaseDurationPaginated(
			@RequestParam("page") int page,
			@RequestParam("isValid") int isValid,
			@RequestParam("size") int size,
			@RequestParam(required = false, defaultValue = "") String searchText,
			@RequestParam(required = false,defaultValue = "0") String missing)
	{
		return agriPhenophaseDurationService.getAllAgriPhenophaseDurationPaginated(page, size, searchText,isValid,missing);
	}// getAllRegionalLanguagePaginated

	@PostMapping("/add")
	public ResponseMessage addAgriPhenophaseDuration(
			@ModelAttribute AgriPhenophaseDurationVO agriPhenophaseDurationVo) {
		return agriPhenophaseDurationService.addAgriphenophaseDuration(agriPhenophaseDurationVo);
	}

	@PutMapping("/{id}/update")
	public ResponseMessage updateAgriPhenophaseDurationById(@PathVariable int id,
			@ModelAttribute AgriPhenophaseDurationVO agriPhenophaseDurationVo) {
		return agriPhenophaseDurationService.updateAgriphenophaseDurationById(id, agriPhenophaseDurationVo);
	}// updateAgriPhenophaseDurationById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return agriPhenophaseDurationService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return agriPhenophaseDurationService.finalApproveById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriPhenophaseDurationById(@PathVariable int id) {
		return agriPhenophaseDurationService.deleteAgriphenophaseDurationById(id);
	}// deleteAgriPhenophaseDurationById

	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return agriPhenophaseDurationService.rejectById(id);
	}// rejectById

	@GetMapping("/{id}")
	public AgriPhenophaseDuration findAgriphenophaseDurationById(@PathVariable int id) {
		return agriPhenophaseDurationService.findAgriphenophaseDurationById(id);
	}// findAgriphenophaseDurationById
	
	@PostMapping("/move-to-master/{id}")
	public ResponseMessage moveToMaster(@PathVariable Integer id) {
		return agriPhenophaseDurationService.moveToMaster(id);
	}
	
	
}// AgriPhenophaseDurationController
