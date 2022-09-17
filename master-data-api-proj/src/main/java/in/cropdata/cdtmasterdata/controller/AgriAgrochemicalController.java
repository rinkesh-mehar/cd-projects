package in.cropdata.cdtmasterdata.controller;

import in.cropdata.cdtmasterdata.dto.AgriAgrochemicalDTO;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriAgrochemicalInfDto;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriDistrictCommodityStressInfDto;
import in.cropdata.cdtmasterdata.model.AgriAgrochemical;
import in.cropdata.cdtmasterdata.model.AgriCommodityAgrochemical;
import in.cropdata.cdtmasterdata.service.AgriAgrochemicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agri/agrochemical")
public class AgriAgrochemicalController {

    @Autowired
    private AgriAgrochemicalService agriAgrochemicalService;

    @GetMapping("/list")
    public List<AgriAgrochemicalDTO> getAllAgriAgrochemicalMaster() {
        return agriAgrochemicalService.getAllAgriAgrochemicalMaster();
    }// getAllAgriAgrochemicalMaster

    @GetMapping()
    public Page<AgriAgrochemicalInfDto> getAllAgrochemicalPaginated(@RequestParam("page") int page,
                                                                    @RequestParam("size") int size,
                                                                    @RequestParam("isValid") int isValid,
                                                                    @RequestParam(required = false, defaultValue = "") String searchText)
    {
        return agriAgrochemicalService.getAllAgrochemicalPaginated(page, size, searchText, isValid);
    }//getAllAgrochemicalPaginated

    @GetMapping("/{commodityId}/{stressTypeId}/list")
    public List<AgriCommodityAgrochemical> getAllAgriStressByStressType(@PathVariable int commodityId, @PathVariable int stressTypeId) {
        return agriAgrochemicalService.getAllAgriStressByStressType(commodityId, stressTypeId);
    }// getAllAgriStress

    @PostMapping("/add")
    public ResponseMessage addAgriAgrochemicalMaster(@RequestBody AgriAgrochemical agriCommodityAgrochemical) {
        return agriAgrochemicalService.addAgriAgrochemicalMaster(agriCommodityAgrochemical);
    }// addAllAgriAgrochemicalMaster

    @PutMapping("/{id}/update")
    public ResponseMessage updateAgriAgrochemicalMasterById(@PathVariable int id,
                                                            @RequestBody AgriAgrochemical agriCommodityAgrochemical)
    {
        return agriAgrochemicalService.updateAgriAgrochemicalMasterById(id, agriCommodityAgrochemical);
    }// updateAgriAgrochemicalMasterById

    @PutMapping("/{id}/primary-approve")
    public ResponseMessage primaryApproveById(@PathVariable int id) {
        return agriAgrochemicalService.primaryApproveById(id);
    }// primaryApproveById


    @GetMapping("/{commodityId}/{stressTypeId}")
    public List<AgriDistrictCommodityStressInfDto> getByCommodityIdAndStressTypeId(@PathVariable int commodityId, @PathVariable int stressTypeId) {
        return agriAgrochemicalService.getByCommodityIdAndStressTypeId(commodityId, stressTypeId);
    }//getByCommodityIdAndStressTypeId

    @PutMapping("/{id}/final-approve")
    public ResponseMessage finalApproveById(@PathVariable int id) {
        return agriAgrochemicalService.finalApproveById(id);
    }// finalApproveById

    @PutMapping("/{id}/reject")
    public ResponseMessage rejectById(@PathVariable int id) {
        return agriAgrochemicalService.rejectById(id);
    }// rejectById

    @DeleteMapping("/{id}/delete")
    public ResponseMessage deleteAgriAgrochemicalMasterById(@PathVariable int id) {
        return agriAgrochemicalService.deleteAgriAgrochemicalMasterById(id);
    }// deleteAgriAgrochemicalMasterById

    @GetMapping("/{id}")
    public AgriAgrochemical findAgriAgrochemicalMasterById(@PathVariable int id) {
        return agriAgrochemicalService.findAgriAgrochemicalMasterById(id);
    }// findAgriAgrochemicalMasterById

    @GetMapping("/agrochemical-type/{id}")
    public List<AgriAgrochemicalInfDto> findAgriAgrochemicalMasterByAgrochemicalTypeId(@PathVariable int id) {
        return agriAgrochemicalService.findAgriAgrochemicalMasterByAgrochemicalTypeId(id);
    }// findAgriAgrochemicalMasterById

}// AgriAgrochemicalMasterController