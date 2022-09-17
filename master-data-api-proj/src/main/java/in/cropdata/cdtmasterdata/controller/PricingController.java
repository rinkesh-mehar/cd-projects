package in.cropdata.cdtmasterdata.controller;

import java.util.List;
import java.util.Map;

import in.cropdata.cdtmasterdata.model.MSP;
import in.cropdata.cdtmasterdata.model.PricingBcSlopeRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.PricingMspGroupInfo;
import in.cropdata.cdtmasterdata.model.PricingMaster;
import in.cropdata.cdtmasterdata.service.PricingService;

@RestController
@RequestMapping("/pricing/msp-group")
public class PricingController {

    @Autowired
    private PricingService pricingService;

    @GetMapping("/list")
    public List<PricingMspGroupInfo> getAllPricingMspGroupList() {
        return pricingService.getAllPricingMspGroupList();
    }// getAllPricingMspGroupList

    @PostMapping("/add")
    public ResponseMessage addPricing(@RequestBody PricingMaster pricingMaster) {
        return pricingService.addPricing(pricingMaster);
    }// addAllAgriPalntHealthIndex

    @GetMapping("/get-pricing-paginated")
    public Map<String, Object> getAllPricingMspPaginated(@RequestParam Integer page) {
        return pricingService.getAllPricingMspPaginated(page);
    }

    @GetMapping("/get-state-region")
    public List<PricingMspGroupInfo> getAllStateAndRegion() {
        return pricingService.getAllStateAndRegion();
    }

    @GetMapping("/msp-mfp")
    public List<PricingMspGroupInfo> getMspMfp(@RequestParam(name = "sId") Integer stateCode,
                                               @RequestParam(name = "rId") Integer regionID,
                                               @RequestParam(name = "cId") Integer commodityID,
                                               @RequestParam(name = "flag") Integer flag)
    {
        return pricingService.getMspMfp(stateCode, regionID, commodityID, flag);
    }

    @GetMapping("/constants")
    public List<PricingMspGroupInfo> getConstants(@RequestParam(name = "sId") Integer stateCode,
                                                  @RequestParam(name = "rId") Integer regionID,
                                                  @RequestParam(name = "cId") Integer commodityID,
                                                  @RequestParam(name = "flag") Integer flag)
    {
        return pricingService.getConstants(stateCode, regionID, commodityID, flag);
    }

    @GetMapping("/mbep-pmp")
    public List<PricingMspGroupInfo> getMbepAndPmp(@RequestParam(name = "sId") Integer stateCode,
                                                   @RequestParam(name = "rId") Integer regionID,
                                                   @RequestParam(name = "cId") Integer commodityID,
                                                   @RequestParam(name = "flag") Integer flag)
    {
        return pricingService.getMbepAndPmp(stateCode, regionID, commodityID, flag);
    }

    @GetMapping("/price-spread")
    public List<PricingMspGroupInfo> getPriceSpread(@RequestParam(name = "sId") Integer stateCode,
                                                    @RequestParam(name = "rId") Integer regionID,
                                                    @RequestParam(name = "cId") Integer commodityID,
                                                    @RequestParam(name = "flag") Integer flag)
    {
        return pricingService.getPriceSpread(stateCode, regionID, commodityID, flag);
    }

    @GetMapping("/region")
    public List<PricingMspGroupInfo> getRegion(@RequestParam(name = "sId") Integer stateCode)
    {
        return pricingService.getRegion(stateCode);
    }

    @GetMapping("/variety")
    public List<PricingMspGroupInfo> getVariety(@RequestParam(name = "sId") Integer stateCode,
                                                @RequestParam(name = "cId") Integer commodityID)
    {
        return pricingService.getVariety(stateCode, commodityID);
    }

    @PostMapping("/edit-msp")
    public MSP getMspByStateMSPCommodity(@RequestBody MSP MSPdata) {
        return pricingService.getMspByStateMSPCommodity(MSPdata.getCommodityID(), MSPdata.getStateCodes(),
                MSPdata.getMsp());
    }

    @GetMapping("/getCommodities")
    public List<Map<String, Object>> getAllCommodities() {

        return pricingService.getAllCommodities();
    }

    @PutMapping("/{id}/update")
    public ResponseMessage updatePricingMspGroupById(@PathVariable int id, @RequestBody PricingMaster pricingMaster) {
        return pricingService.updatePricingMspGroupById(id, pricingMaster);
    }// updatePricingMspGroupById

    @PutMapping("/{id}/primary-approve")
    public ResponseMessage primaryApproveById(@PathVariable int id) {
        return pricingService.primaryApproveById(id);
    }// primaryApproveById

    @PutMapping("/{id}/final-approve")
    public ResponseMessage finalApproveById(@PathVariable int id) {
        return pricingService.finalApproveById(id);
    }// finalApproveById

    @PutMapping("/{id}/reject")
    public ResponseMessage rejectById(@PathVariable int id) {
        return pricingService.rejectById(id);
    }// rejectById

    @DeleteMapping("/{id}/delete")
    public ResponseMessage deletePricingMspGroupById(@PathVariable int id) {
        return pricingService.deletePricingMspGroupById(id);
    }// deletePricingMspGroupById

    @GetMapping("/{id}")
    public PricingMaster findPricingMspGroupById(@PathVariable int id) {
        return pricingService.findPricingMspGroupById(id);
    }// findPricingMspGroupById

/*	@GetMapping("/page/{screen}")
	public  List<Integer> getPageNumber(@PathVariable String screen) {
		return pricingService.listOfPages(screen);
	}*/

    @GetMapping("/download-mbep-pmp")
    public ResponseEntity<Resource> exportMbepPmpToExcel(@RequestParam("stateCode") Integer stateCode,
                                                         @RequestParam("regionID") Integer regionID,
                                                         @RequestParam("commodityID") Integer commodityID)
    {

        return this.pricingService.getMbepPmpSpreadInExcelFile(stateCode, regionID, commodityID);
    }

    @GetMapping("/download-constant")
    public ResponseEntity<Resource> exportConstantToExcel(@RequestParam("stateCode") Integer stateCode,
                                                          @RequestParam("regionID") Integer regionID,
                                                          @RequestParam("commodityID") Integer commodityID)
    {

        return this.pricingService.getConstantInExcelFile(stateCode, regionID, commodityID);
    }

    @GetMapping("/download-msp-mfp")
    public ResponseEntity<Resource> exportMspMfpExcel(@RequestParam("stateCode") Integer stateCode,
                                                      @RequestParam("regionID") Integer regionID,
                                                      @RequestParam("commodityID") Integer commodityID)
    {

        return this.pricingService.getMspMfpInExcelFile(stateCode, regionID, commodityID);
    }

    @GetMapping("/buyer-constant-paginated")
    public Page<PricingMspGroupInfo> getBuyerConstantPaginated(@RequestParam("page") int page,
                                                               @RequestParam("size") int size,
                                                               @RequestParam(required = false, defaultValue = "") String searchText)
    {
        return pricingService.getBuyerConstantPaginated(page, size, searchText);
    }

    @GetMapping("/buyer-constant")
    public List<PricingMspGroupInfo> getBuyerConstant()
    {
        return pricingService.getBuyerConstant();
    }

    @PostMapping("/add-buyer-constant")
    public ResponseMessage addRegionSeason(@RequestBody PricingBcSlopeRange pricingBcSlopeRange) {
        return pricingService.addBuyerConstant(pricingBcSlopeRange);
    }

    @GetMapping("/buyer-constant/{id}")
    public PricingBcSlopeRange findBuyerConstantById(@PathVariable int id) {
        return pricingService.findBuyerConstantById(id);
    }

    @PutMapping("/{id}/buyer-constant/update")
    public ResponseMessage updateBuyerConstantById(@PathVariable int id, @RequestBody PricingBcSlopeRange pricingBcSlopeRange) {
        return pricingService.updateBuyerConstantById(id, pricingBcSlopeRange);
    }
}
