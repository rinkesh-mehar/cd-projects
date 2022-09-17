package in.cropdata.cdtmasterdata.service;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.model.Band;
import in.cropdata.cdtmasterdata.model.vo.AgriCommodityVo;
import in.cropdata.cdtmasterdata.repository.CommodityBandRepository;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author RinkeshKM
 * @Date 30/07/20
 */

@Service
public class CommodityBandService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommodityBandService.class);

    @Autowired
    CommodityBandRepository commodityBandRepository;

    @Autowired
    ResponseMessageUtil responseMessageUtil;

    public List<Band> getAllBand() {
        return commodityBandRepository.findAll();
    }

    public AgriCommodityVo getBandById(Integer id) {
        return commodityBandRepository.getBandById(id);
    }

    public ResponseMessage addBand(Band bandDetail) {
        LOGGER.info("saving band detail...");

        try {
            commodityBandRepository.save(bandDetail);

            return responseMessageUtil.sendResponse(true, "Band" + APIConstants.RESPONSE_ADD_SUCCESS, "");

        } catch (Exception e) {
            return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
        }
    }

    public ResponseMessage updateBand(Band bandDetail, Integer id) {
        LOGGER.info("updating band detail...");

        try {

            Optional<Band> foundBand = commodityBandRepository.findById(id);

            if (foundBand.isPresent()) {
                bandDetail.setId(foundBand.get().getId());
                commodityBandRepository.save(bandDetail);
                return responseMessageUtil.sendResponse(true, "Band" + APIConstants.RESPONSE_ADD_SUCCESS, "");
            } else {

                return responseMessageUtil.sendResponse(false, "", "Band" + APIConstants.RESPONSE_UPDATE_ERROR + id);
            }

        } catch (Exception e) {
            return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
        }
    }

    public ResponseMessage deleteBand(Integer id) {
        LOGGER.info("updating band detail...");

        try {

            Optional<Band> foundBand = commodityBandRepository.findById(id);

            if (foundBand.isPresent()) {
                commodityBandRepository.deleteBand(id);
                return responseMessageUtil.sendResponse(true, "Band" + APIConstants.RESPONSE_DELETE_SUCCESS, "");
            } else {

                return responseMessageUtil.sendResponse(false, "", "Band" + APIConstants.RESPONSE_DELETE_ERROR + id);
            }

        } catch (Exception e) {
            return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
        }
    }
}
