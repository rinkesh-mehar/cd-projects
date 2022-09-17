package in.cropdata.portal.gstmDataModels.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.portal.gstmDataModels.dto.WinnerMarketDTO;
import in.cropdata.portal.gstmDataModels.model.WinnerMarket;
import in.cropdata.portal.gstmDataModels.service.WinnerMarketService;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.portal.gstmDataModels.controller
 * @date 13/08/20
 * @time 4:16 PM
 * To change this template use File | Settings | File and Code Templates
 */
@RestController
@RequestMapping("/site")
public class WinnerMarketController
{
    @Autowired
    WinnerMarketService winnerMarketService;

    @GetMapping("/market")
    public WinnerMarketDTO getWinnerMarket(@RequestParam("Id") Integer commodityId) throws Exception
    {
        if (commodityId != null)
        {
            return winnerMarketService.getWinnerMarket(commodityId);
        } else
        {
            throw new IllegalArgumentException("Commodity ID can't be null");
        }
    }

    @GetMapping("/treading/view")
    public Object getTreadingView(@RequestParam("stateCode") Integer stateCode,
                                               @RequestParam(value = "districtCode", required = false) Integer districtCode,
                                               @RequestParam("commodityID") Integer commodityId,
                                               @RequestParam(value ="pricingAgriVarietyID", required = false) Integer varietyID,
                                               @RequestParam(value ="marketID", required = false) Integer marketID)
    {

        if (stateCode != null && districtCode != null && commodityId != null
                && varietyID != null && marketID != null)
        {
            return winnerMarketService.getTreadingViewRecords(stateCode, districtCode, commodityId,
                    varietyID, marketID);
        } else if(stateCode != null && commodityId != null && stateCode != 0)
        {
            return  winnerMarketService.getMarketRecordsByCommodityAndStateCode(commodityId, stateCode);

        } else if (stateCode == 0 && commodityId != null){

            return winnerMarketService.getMarketRecord(commodityId);
        } else
        {

            throw new IllegalArgumentException("Param value is null, Please check");
        }

        }


    @GetMapping("/market-records")
    public List<WinnerMarket> getMarketRecords(@RequestParam("Id") Integer commodityId)
    {
        if (commodityId != null)
        {
            return winnerMarketService.getMarketRecord(commodityId);
        } else
        {
            throw new IllegalArgumentException("Param value is null, Please check");
        }
    }

  /*  @GetMapping("/market/records")
    public List<WinnerMarket> getMarketByCommodityAndMarket(@RequestParam("commodityId") Integer commodityId,
                                                            @RequestParam("stateCode") Integer stateCode)
    {
        if (commodityId != null && stateCode != null)
        {
            return winnerMarketService.getMarketRecordsByCommodityAndStateCode(commodityId, stateCode);
        } else
        {
            throw new IllegalArgumentException("Param value is null, Please check");
        }
    }*/
}
