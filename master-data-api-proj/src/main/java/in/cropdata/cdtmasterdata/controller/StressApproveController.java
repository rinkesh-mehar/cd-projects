package in.cropdata.cdtmasterdata.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.StressApproveDto;
import in.cropdata.cdtmasterdata.model.TaskStressModel;
import in.cropdata.cdtmasterdata.service.StressApproveService;

@RestController
@RequestMapping("/farmer/stress-approval")
public class StressApproveController {

    @Autowired
    StressApproveService stressApproveService;

    @GetMapping("/paginated-list")
    Page<StressApproveDto> getStressForApprovalByPagination(@RequestParam("page") Integer page,
                                                            @RequestParam("size") Integer size,
                                                            @RequestParam(required = false, defaultValue = "") String searchText) {
        return stressApproveService.getStressForApprovalByPagination(page, size, searchText);
    }

    @GetMapping("/details")
    StressApproveDto getCaseDetailsById(@RequestParam String taskStressDetailID) {
        return stressApproveService.getDetailsByTaskStressDetailID(taskStressDetailID);
    }

    @GetMapping("/symptoms")
    List<StressApproveDto> getSymptomBySpec(@RequestParam Integer commodityID, @RequestParam Integer varietyID,
                                            @RequestParam Integer phenophaseID, @RequestParam Integer districtID) {
        return stressApproveService.getSymptomBySpec(commodityID, varietyID, phenophaseID, districtID);
    }

    @GetMapping("/symptoms-details")
    StressApproveDto getSymptomDetailsBySymptom(@RequestParam Integer symptomID) {
        return stressApproveService.getSymptomDetailsBySymptom(symptomID);
    }

    @GetMapping("/stress-details")
    StressApproveDto getStressDetailsById(@RequestParam String id, @RequestParam Integer stressId, @RequestParam Integer symptomId) {
        return stressApproveService.getStressDetailsById(id, stressId, symptomId);
    }
    
    
	@PostMapping("/approve-symptoms")
	public  ResponseMessage approveSymptomsDetails(@RequestBody TaskStressModel data) {
		return stressApproveService.approveSymptomsDetails(data);
	}
    
    
    /*@PutMapping("/{id}/update")
    public ResponseMessage updateValidateStressById(@PathVariable Integer id, @RequestBody ValidateStressGroundTruth validateStressGroundTruth) {
        return validateStressService.updateValidateStressById(id, validateStressGroundTruth);
    }
    @PutMapping("/{id}/primary-approve")
    public ResponseMessage primaryApproveById(@PathVariable int id) {
        return validateStressService.primaryApproveById(id);
    }

    @PutMapping("/{id}/final-approve")
    public ResponseMessage finalApproveById(@PathVariable int id) {
        return validateStressService.finalApproveById(id);
    }

    @PutMapping("/{id}/reject")
    public ResponseMessage rejectById(@PathVariable int id) {
        return validateStressService.rejectById(id);
    }*/
}
