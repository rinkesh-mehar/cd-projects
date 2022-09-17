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

import in.cropdata.cdtmasterdata.dto.AgriAgrochemicalDTO;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriAgrochemicalInfDto;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriDistrictCommodityStressInfDto;
import in.cropdata.cdtmasterdata.model.AgriCommodityAgrochemical;
import in.cropdata.cdtmasterdata.service.AgriCommodityAgrochemicalService;

@RestController
@RequestMapping("/agri/commodity/agrochemical")
public class AgriCommodityAgrochemicalController {

    @Autowired
    private AgriCommodityAgrochemicalService agriCommodityAgrochemicalService;

    @GetMapping("/list")
    public List<AgriAgrochemicalDTO> getAllAgriAgrochemicalMaster() {
        return agriCommodityAgrochemicalService.getAllAgriAgrochemicalMaster();
    }// getAllAgriAgrochemicalMaster

    @GetMapping()
    public Page<AgriAgrochemicalInfDto> getAllAgrochemicalPaginated(@RequestParam("page") int page,
                                                                    @RequestParam("size") int size,
                                                                    @RequestParam("isValid") int isValid,
                                                                    @RequestParam(required = false, defaultValue = "") String searchText)
    {
        return agriCommodityAgrochemicalService.getAllAgrochemicalPaginated(page, size, searchText, isValid);
    }//getAllAgrochemicalPaginated

    @GetMapping("/{commodityId}/{stressTypeId}/list")
    public List<AgriCommodityAgrochemical> getAllAgriStressByStressType(@PathVariable int commodityId, @PathVariable int stressTypeId) {
        return agriCommodityAgrochemicalService.getAllAgriStressByStressType(commodityId, stressTypeId);
    }// getAllAgriStress

    @PostMapping("/add")
    public ResponseMessage addAgriAgrochemicalMaster(@RequestBody AgriCommodityAgrochemical agriCommodityAgrochemical) {
        return agriCommodityAgrochemicalService.addAgriAgrochemicalMaster(agriCommodityAgrochemical);
    }// addAllAgriAgrochemicalMaster

    @PutMapping("/{id}/update")
    public ResponseMessage updateAgriAgrochemicalMasterById(@PathVariable int id,
                                                            @RequestBody AgriCommodityAgrochemical agriCommodityAgrochemical)
    {
        return agriCommodityAgrochemicalService.updateAgriAgrochemicalMasterById(id, agriCommodityAgrochemical);
    }// updateAgriAgrochemicalMasterById

    @PutMapping("/{id}/primary-approve")
    public ResponseMessage primaryApproveById(@PathVariable int id) {
        return agriCommodityAgrochemicalService.primaryApproveById(id);
    }// primaryApproveById


    @GetMapping("/{commodityId}/{stressTypeId}")
    public List<AgriDistrictCommodityStressInfDto> getByCommodityIdAndStressTypeId(@PathVariable int commodityId, @PathVariable int stressTypeId) {
        return agriCommodityAgrochemicalService.getByCommodityIdAndStressTypeId(commodityId, stressTypeId);
    }//getByCommodityIdAndStressTypeId

    @PutMapping("/{id}/final-approve")
    public ResponseMessage finalApproveById(@PathVariable int id) {
        return agriCommodityAgrochemicalService.finalApproveById(id);
    }// finalApproveById

    @PutMapping("/{id}/reject")
    public ResponseMessage rejectById(@PathVariable int id) {
        return agriCommodityAgrochemicalService.rejectById(id);
    }// rejectById

    @DeleteMapping("/{id}/delete")
    public ResponseMessage deleteAgriAgrochemicalMasterById(@PathVariable int id) {
        return agriCommodityAgrochemicalService.deleteAgriAgrochemicalMasterById(id);
    }// deleteAgriAgrochemicalMasterById

    @GetMapping("/{id}")
    public AgriCommodityAgrochemical findAgriAgrochemicalMasterById(@PathVariable int id) {
        return agriCommodityAgrochemicalService.findAgriAgrochemicalMasterById(id);
    }// findAgriAgrochemicalMasterById

    @GetMapping("/agrochemical-type/{id}")
    public List<AgriAgrochemicalInfDto> findAgriAgrochemicalMasterByAgrochemicalTypeId(@PathVariable int id) {
        return agriCommodityAgrochemicalService.findAgriAgrochemicalMasterByAgrochemicalTypeId(id);
    }// findAgriAgrochemicalMasterById
    
    @GetMapping("/{commodityId}/clist")
    public List<AgriCommodityAgrochemical> findAgriAgrochemicalMasterByCommodityId(@PathVariable int commodityId) {
        return agriCommodityAgrochemicalService.findAgriAgrochemicalMasterByCommodityId(commodityId);
    }

}// AgriAgrochemicalMasterController