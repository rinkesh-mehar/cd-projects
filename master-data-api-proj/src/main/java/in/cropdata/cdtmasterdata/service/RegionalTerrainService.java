package in.cropdata.cdtmasterdata.service;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.GeneralTerrainTypeDto;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.GeneralTerrainType;
import in.cropdata.cdtmasterdata.repository.GeneralTerrainRepository;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author RinkeshKM
 * @Date 09/11/20
 */

@Service
public class RegionalTerrainService {

    @Autowired
    GeneralTerrainRepository generalTerrainRepository;

    @Autowired
    private ResponseMessageUtil responseMessageUtil;

    public Page<GeneralTerrainTypeDto> getAllRegionalTerrainPaginated(int page, int size, String searchText, int isValid) {
        try {
            searchText = "%" + searchText + "%";
            Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());

            Page<GeneralTerrainTypeDto> generalTerrainTypeDto;

            if (isValid == 0) {
                generalTerrainTypeDto = generalTerrainRepository.getGeneralTerrainListInvalidated(sortedByIdDesc, searchText);
            } else {
                generalTerrainTypeDto = generalTerrainRepository.getGeneralTerrainList(sortedByIdDesc, searchText);
            }

            return generalTerrainTypeDto;
        } catch (Exception e) {
            throw e;
        }
    }

    public ResponseMessage addTerrain(GeneralTerrainType generalTerrainType) {

        try {

            Optional<GeneralTerrainType> foundTerrainType = generalTerrainRepository.findByTerrainTypeAndRegionId(generalTerrainType.getTerrainType(), generalTerrainType.getRegionId());

            foundTerrainType.ifPresent(terrainType -> generalTerrainType.setId(terrainType.getId()));
            generalTerrainRepository.save(generalTerrainType);

//            approvalUtil.addRecord(DBConstants.TBL_REGIONAL_Terrain, regionalTerrain.getId());

            return responseMessageUtil.sendResponse(true, "Terrain Type" + APIConstants.RESPONSE_ADD_SUCCESS, "");

        } catch (Exception e) {
            return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
        }
    }// addAllRegionalTerrain


    public ResponseMessage updateTerrainById(int id, GeneralTerrainType regionalTerrain) {
        try {
            Optional<GeneralTerrainType> foundTerrain = generalTerrainRepository.findById(id);

            if (foundTerrain.isPresent()) {

                regionalTerrain.setId(id);
                regionalTerrain = generalTerrainRepository.save(regionalTerrain);

//                approvalUtil.updateRecord(DBConstants.TBL_REGIONAL_Terrain, regionalTerrain.getId());

                return responseMessageUtil.sendResponse(true,
                        "Region-Terrain" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
            } else {
                return responseMessageUtil.sendResponse(false, "",
                        "Region-Terrain" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
            }
        } catch (Exception e) {
            return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
        }
    }// updateRegionalTerrainById


    public GeneralTerrainType findTerrainById(int id) {

        try {
            Optional<GeneralTerrainType> foundTerrain = generalTerrainRepository.findById(id);

            if (foundTerrain.isPresent()) {
                return foundTerrain.get();
            } else {
                throw new DoesNotExistException("Region-Terrain" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
            }
        } catch (Exception e) {
            throw e;
        }
    }// findRegionalTerrainById

    public ResponseMessage updateRegionTerrainById(int id, GeneralTerrainType regionTerrain) {
        try {
            Optional<GeneralTerrainType> foundTerrain = generalTerrainRepository.findById(id);

            if (foundTerrain.isPresent()) {

                regionTerrain.setId(id);
                regionTerrain = generalTerrainRepository.save(regionTerrain);

//                approvalUtil.updateRecord(DBConstants.TBL_REGIONAL_Terrain, regionTerrain.getId());

                return responseMessageUtil.sendResponse(true,
                        "Region-Terrain" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
            } else {
                return responseMessageUtil.sendResponse(false, "",
                        "Region-Terrain" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
            }
        } catch (Exception e) {
            return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
        }
    }// updateRegionalTerrainById

    public ResponseMessage primaryApproveById(int id) {
        try {
            Optional<GeneralTerrainType> foundTerrain = generalTerrainRepository.findById(id);

            if (foundTerrain.isPresent()) {

                GeneralTerrainType regionalTerrain = foundTerrain.get();
                regionalTerrain.setStatus(APIConstants.STATUS_APPROVED);

                regionalTerrain = generalTerrainRepository.save(regionalTerrain);

//                approvalUtil.primaryApprove(DBConstants.TBL_REGIONAL_Terrain, regionalTerrain.getId());

                return responseMessageUtil.sendResponse(true,
                        "Region-Terrain" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
            } else {
                return responseMessageUtil.sendResponse(false, "",
                        "Region-Terrain" + APIConstants.RESPONSE_NO_RECORD_FOUND);
            }
        } catch (Exception e) {
            return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
        }
    }// primaryApproveById

    public ResponseMessage rejectById(int id) {
        try {
            Optional<GeneralTerrainType> foundTerrain = generalTerrainRepository.findById(id);

            if (foundTerrain.isPresent()) {

                GeneralTerrainType regionalTerrain = foundTerrain.get();
                regionalTerrain.setStatus(APIConstants.STATUS_REJECTED);
                regionalTerrain = generalTerrainRepository.save(regionalTerrain);

//                approvalUtil.finalApprove(DBConstants.TBL_REGIONAL_Terrain, regionalTerrain.getId());

                return responseMessageUtil.sendResponse(true, "Regional-Terrain " + APIConstants.RESPONSE_REJECT_SUCCESS, "");
            } else {
                return responseMessageUtil.sendResponse(false, "", "Regional-Terrain " + APIConstants.RESPONSE_REJECT_ERROR);
            }
        } catch (Exception e) {
            return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
        }
    }// rejectById

    public ResponseMessage finalApproveById(int id) {
        try {
            Optional<GeneralTerrainType> foundTerrain = generalTerrainRepository.findById(id);

            if (foundTerrain.isPresent()) {

                GeneralTerrainType regionalTerrain = foundTerrain.get();
                regionalTerrain.setStatus(APIConstants.STATUS_ACTIVE);

                regionalTerrain = generalTerrainRepository.save(regionalTerrain);

//                approvalUtil.finalApprove(DBConstants.TBL_REGIONAL_Terrain, regionalTerrain.getId());

                return responseMessageUtil.sendResponse(true,
                        "Region-Terrain" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
            } else {
                return responseMessageUtil.sendResponse(false, "",
                        "Region-Terrain" + APIConstants.RESPONSE_NO_RECORD_FOUND);
            }
        } catch (Exception e) {
            return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
        }
    }// finalApproveById
}
