package in.cropdata.cdtmasterdata.controller;

import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.InvalidDataException;
import in.cropdata.cdtmasterdata.model.Band;
import in.cropdata.cdtmasterdata.model.vo.AgriCommodityVo;
import in.cropdata.cdtmasterdata.service.CommodityBandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author RinkeshKM
 * @Date 30/07/20
 */

@RestController()
@RequestMapping("/commodity")
public class CommodityBandController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommodityBandController.class);

    @Autowired
    CommodityBandService commodityBandService;

    @GetMapping("/get-band-list")
    public List<Band> getAllBand() {
        return commodityBandService.getAllBand();
    }


    @GetMapping("/getBandById/{id}")
    public AgriCommodityVo getBandById(@PathVariable Integer id) {
        return commodityBandService.getBandById(id);
    }


    @PostMapping("/addBand")
    public ResponseEntity<ResponseMessage> adParameterDetails(@RequestBody Band bandDetails) {

        if (bandDetails == null) {
            throw new InvalidDataException("Parameter data can not be null!");
        }

        LOGGER.info("Adding Parameter Details...");
        return new ResponseEntity<>(commodityBandService.addBand(bandDetails), HttpStatus.CREATED);
    }

    @PutMapping("/updateBand")
    public ResponseEntity<ResponseMessage> updateBand(@RequestBody Band bandDetail, Integer id) {
        if (bandDetail == null) {
            throw new InvalidDataException("Parameter data can not be null!");
        }
        return new ResponseEntity<>(commodityBandService.updateBand(bandDetail, id), HttpStatus.CREATED);
    }

    @PutMapping("/delete-band")
    public ResponseEntity<ResponseMessage> deleteBand(@RequestParam Integer id) {
        if (id == null || id == 0) {
            throw new InvalidDataException("Band id can not be null or zero!");
        }
        return new ResponseEntity<>(commodityBandService.deleteBand(id), HttpStatus.CREATED);
    }

}
