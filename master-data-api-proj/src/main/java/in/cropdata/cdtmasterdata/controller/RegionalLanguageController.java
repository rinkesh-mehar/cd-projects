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
import in.cropdata.cdtmasterdata.dto.interfaces.RegionalLanguageDtoInf;
import in.cropdata.cdtmasterdata.model.RegionalLanguage;
import in.cropdata.cdtmasterdata.service.RegionalLanguageService;

@RestController
@RequestMapping("/regional/language")
public class RegionalLanguageController {
	
	@Autowired
	private RegionalLanguageService regionalLanguageService;
	
	@GetMapping("/list")
	public List<RegionalLanguage> getAllRegionalLanguage(){
		return regionalLanguageService.getAllAgriRegionalLanguage();
	}//getAllRegionalLanguage

	@GetMapping()
	public Page<RegionalLanguageDtoInf> getAllRegionalLanguagePaginated(@RequestParam("page") int page,
																		@RequestParam("size") int size,
																		@RequestParam("isValid") int isValid,
																		@RequestParam(required = false, defaultValue = "") String searchText,
																		@RequestParam(required = false,defaultValue = "0") String missing)
	{
		return regionalLanguageService.getAllRegionalLanguagePaginated(page, size,missing,searchText,isValid);
	}// getAllRegionalLanguagePaginated
	
	@PostMapping("/add")
	public ResponseMessage addRegionalLanguage(@RequestBody RegionalLanguage regionalLanguage) {
		return regionalLanguageService.addRegionalLanguage(regionalLanguage);
	}//addRegionalLanguage
	
	@PutMapping("/{id}/update")
	public ResponseMessage updateRegionalLanguageById(@PathVariable int id,@RequestBody RegionalLanguage regionalLanguage) {
		return regionalLanguageService.updateRegionalLanguageById(id, regionalLanguage);
	}//updateRegionalLanguageById
	
	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return regionalLanguageService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return regionalLanguageService.finalApproveById(id);
	}// finalApproveById
	
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return regionalLanguageService.rejectById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteRegionalLanguageById(@PathVariable int id) {
		return regionalLanguageService.deleteRegionalLanguageById(id);
	}//deleteRegionalLanguageById
	
	@GetMapping("/{id}")
	public RegionalLanguage findRegionalLanguageById(@PathVariable int id) {
		return regionalLanguageService.findRegionalLanguageById(id);
	}//findRegionalLanguageById
	
	@PostMapping("/move-to-master/{id}")
	public ResponseMessage moveToMaster(@PathVariable Integer id) {
		return regionalLanguageService.moveToMaster(id);
	}

}
