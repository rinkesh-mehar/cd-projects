package in.cropdata.cdtmasterdata.service;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.constants.DBConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriDistrictCommodityStressInfDto;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.AgriStress;
import in.cropdata.cdtmasterdata.repository.AgriStressRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgriStressService {

    private static final Logger log = LoggerFactory.getLogger(AgriStressService.class);

    @Autowired
    private AgriStressRepository agriStressRepository;

    @Autowired
    private AclHistoryUtil approvalUtil;

    @Autowired
    private ResponseMessageUtil responseMessageUtil;

    public List<AgriStress> getAllAgriStress() {
        try {

            return agriStressRepository.findAll(Sort.by("name"));

        } catch (Exception e) {
            throw e;
        }

    }// getAllAgriStress

    public Page<AgriDistrictCommodityStressInfDto> getAllStressPaginated(int page, int size, String searchText) {

        try {
            searchText = "%" + searchText + "%";
            Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());

            Page<AgriDistrictCommodityStressInfDto> StressList = agriStressRepository.getStressList(sortedByIdDesc, searchText);

            log.debug("Stress List :" + StressList);

            return StressList;
        } catch (Exception e) {
            throw e;
        }

    }// getAllStressPaginated

    public ResponseMessage primaryApproveById(int id) {

        try {
            Optional<AgriStress> foundStress = agriStressRepository.findById(id);

            if (foundStress.isPresent()) {

                AgriStress Stress = foundStress.get();

                Stress.setStatus(APIConstants.STATUS_APPROVED);

                Stress = agriStressRepository.save(Stress);

                approvalUtil.primaryApprove(DBConstants.TBL_AGRI_COMMODITY_STRESS, Stress.getId());

                return responseMessageUtil.sendResponse(true,
                        "Agri-Stress" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");

            } else {
                return responseMessageUtil.sendResponse(false, "",
                        "Agri-Stress" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
            }
        } catch (Exception e) {
            return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
        }
    }// primaryApproveById

    public ResponseMessage finalApproveById(int id) {
        try {
            Optional<AgriStress> foundStress = agriStressRepository.findById(id);

            if (foundStress.isPresent()) {

                AgriStress Stress = foundStress.get();

                Stress.setStatus(APIConstants.STATUS_ACTIVE);

                Stress = agriStressRepository.save(Stress);

                approvalUtil.finalApprove(DBConstants.TBL_AGRI_STRESS, Stress.getId());

                return responseMessageUtil.sendResponse(true,
                        "Agri-Stress" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");

            } else {
                return responseMessageUtil.sendResponse(false, "",
                        "Agri-Stress" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
            }
        } catch (Exception e) {
            return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
        }
    }// finalApproveById

    public ResponseMessage deleteAgriStressById(int id) {
        try {
            Optional<AgriStress> foundStress = agriStressRepository.findById(id);
            if (foundStress.isPresent()) {

                AgriStress Stress = foundStress.get();

                Stress.setStatus(APIConstants.STATUS_REJECTED);

                Stress = agriStressRepository.save(Stress);

                approvalUtil.delete(DBConstants.TBL_AGRI_STRESS, Stress.getId());

                return responseMessageUtil.sendResponse(true, "Agri-Stress" + APIConstants.RESPONSE_DELETE_SUCCESS + id,
                        "");

            } else {

                return responseMessageUtil.sendResponse(false, "",
                        "Agri-Stress" + APIConstants.RESPONSE_DELETE_ERROR + id);
            }
        } catch (Exception e) {
            return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
        }
    }// deleteAgriStressById

    public ResponseMessage rejectById(int id) {
        try {
            Optional<AgriStress> foundStress = agriStressRepository.findById(id);
            if (foundStress.isPresent()) {

                AgriStress stress = foundStress.get();
                stress.setStatus(APIConstants.STATUS_REJECTED);

                stress = agriStressRepository.save(stress);

                approvalUtil.delete(DBConstants.TBL_AGRI_STRESS, stress.getId());

                return responseMessageUtil.sendResponse(true,
                        "Agri-Stress" + APIConstants.RESPONSE_REJECT_SUCCESS + id, "");
            } else {
                return responseMessageUtil.sendResponse(false, "",
                        "Agri-Stress" + APIConstants.RESPONSE_REJECT_ERROR + id);
            }
        } catch (Exception e) {
            return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
        }
    }// rejectById

    public AgriStress findAgriStressById(int id) {
        try {
            Optional<AgriStress> foundStress = agriStressRepository.findById(id);
            if (foundStress.isPresent()) {
                return foundStress.get();
            } else {
                throw new DoesNotExistException("Agri-Stress" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
            }
        } catch (Exception e) {
            throw e;
        }
    }// findAgriStressById


    public List<AgriDistrictCommodityStressInfDto> getAllAgriStressByStressType(int commodityId, int stressTypeId) {
        return agriStressRepository.findAllByStressTypeId(commodityId, stressTypeId);
    }

    public ResponseMessage addAgriStress(AgriStress agriStress) {
        try {

            agriStressRepository.save(agriStress);

            return responseMessageUtil.sendResponse(true,
                    "Agri Stress" + APIConstants.RESPONSE_ADD_SUCCESS, "");

        } catch (Exception e) {
            return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
        }
    }

    public ResponseMessage updateAgriStressById(int id, AgriStress agriStress) {

        try {

            Optional<AgriStress> foundGroup = agriStressRepository.findById(id);

            if (foundGroup.isPresent()) {

                agriStress.setId(id);

                agriStressRepository.save(agriStress);

                return responseMessageUtil.sendResponse(true,
                        "Agri Stress" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");

            } else {
                return responseMessageUtil.sendResponse(false, "",
                        "Agri Stress" + APIConstants.RESPONSE_UPDATE_ERROR + id);
            }
        } catch (Exception e) {
            return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
        }
    }
}