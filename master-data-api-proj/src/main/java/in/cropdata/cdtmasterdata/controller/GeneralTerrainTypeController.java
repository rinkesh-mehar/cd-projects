package in.cropdata.cdtmasterdata.controller;

import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.GeneralTerrainTypeDto;
import in.cropdata.cdtmasterdata.model.GeneralTerrainType;
import in.cropdata.cdtmasterdata.service.RegionalTerrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author RinkeshKM
 * @Date 09/11/20
 */

@RestController
@RequestMapping("/regional/general-terrain")
public class GeneralTerrainTypeController {

    @Autowired
    RegionalTerrainService regionalTerrainService;

    @GetMapping()
    public Page<GeneralTerrainTypeDto> getAllRegionalSeasonPaginatade(@RequestParam("page") int page,
                                                                      @RequestParam("size") int size,
                                                                      @RequestParam("isValid") int isValid,
                                                                      @RequestParam(required = false, defaultValue = "") String searchText)
    {
        return regionalTerrainService.getAllRegionalTerrainPaginated(page, size, searchText, isValid);
    }// getAllRegionalSeasonPaginatade

    @PostMapping("/add")
    public ResponseMessage addRegionSeason(@RequestBody GeneralTerrainType generalTerrainType) {
        return regionalTerrainService.addTerrain(generalTerrainType);
    }// addAllRegionSeason

    @GetMapping("/{id}")
    public GeneralTerrainType findRegionSeasonById(@PathVariable int id) {
        return regionalTerrainService.findTerrainById(id);
    }

    @PutMapping("/{id}/update")
    public ResponseMessage updateRegionSeasonById(@PathVariable int id, @RequestBody GeneralTerrainType regionSeason) {
        return regionalTerrainService.updateRegionTerrainById(id, regionSeason);
    }// updateRegionSeasonById

    @PutMapping("/{id}/primary-approve")
    public ResponseMessage primaryApproveById(@PathVariable int id) {
        return regionalTerrainService.primaryApproveById(id);
    }// primaryApproveById

    @PutMapping("/{id}/reject")
    public ResponseMessage rejectById(@PathVariable int id) {
        return regionalTerrainService.rejectById(id);
    }// finalApproveById

    @PutMapping("/{id}/final-approve")
    public ResponseMessage finalApproveById(@PathVariable int id) {
        return regionalTerrainService.finalApproveById(id);
    }// finalApproveById
}
