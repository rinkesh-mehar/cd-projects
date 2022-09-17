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
import in.cropdata.cdtmasterdata.dto.interfaces.AgriHsCodeInfDto;
import in.cropdata.cdtmasterdata.model.AgriHsCode;
import in.cropdata.cdtmasterdata.service.AgriHsCodeService;

@RestController
@RequestMapping("/agri/hs-code")
public class AgriHsCodeController {

	@Autowired
	private AgriHsCodeService agriHsCodeService;

	@GetMapping("/list")
	public List<AgriHsCodeInfDto> getAllAgriHsCode() {
		return agriHsCodeService.getAllAgriHsCode();

	}// getAllAgriHsCode

	@GetMapping("/{commodityId}/list")
	public List<AgriHsCode> getAllAgriHsCode(@PathVariable int commodityId) {
		return agriHsCodeService.getAllAgriHsCodeByCommodity(commodityId);
	}// getAllAgriHsCode

	@GetMapping()
	public Page<AgriHsCodeInfDto> getAllHsCodeByPaginated(@RequestParam("page") int page,
														  @RequestParam("isValid") int isValid,
														  @RequestParam("size") int size,
														  @RequestParam(required = false, defaultValue = "") String searchText,
														  @RequestParam(required = false,defaultValue = "0") String missing)
	{
		return agriHsCodeService.getAllAgriHsCodePaginated(page, size, searchText, isValid,missing);
	}

	@PostMapping("/add")
	public ResponseMessage addAgriHsCode(@RequestBody AgriHsCode agriHsCode) {
		return agriHsCodeService.addAgriHsCode(agriHsCode);
	}// addAgriHsCode

	@PutMapping("/{id}/update")
	public ResponseMessage updateAgriHsCodeById(@PathVariable int id, @RequestBody AgriHsCode agriHsCode) {
		return agriHsCodeService.updateAgriHsCodeById(id, agriHsCode);
	}// updateAgriHsCodeById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return agriHsCodeService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return agriHsCodeService.finalyApproveById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriHsCodeById(@PathVariable int id) {
		return agriHsCodeService.deleteAgriHsCodeById(id);
	}// deleteAgriHsCodeById
	
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return agriHsCodeService.rejectById(id);
	}// rejectById

	@GetMapping("/{id}")
	public AgriHsCode findAgriHsCodeById(@PathVariable int id) {
		return agriHsCodeService.findAgriHsCodeById(id);
	}// findAgriHsCodeById
	
	@PostMapping("/move-to-master/{id}")
	public ResponseMessage moveToMaster(@PathVariable Integer id) {
		return agriHsCodeService.moveToMaster(id);
	}
}
