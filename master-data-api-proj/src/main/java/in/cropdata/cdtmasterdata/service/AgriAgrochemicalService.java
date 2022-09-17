package in.cropdata.cdtmasterdata.service;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.constants.DBConstants;
import in.cropdata.cdtmasterdata.dto.AgriAgrochemicalDTO;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriAgrochemicalInfDto;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriDistrictCommodityStressInfDto;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.AgriAgrochemical;
import in.cropdata.cdtmasterdata.model.AgriAgrochemicalStress;
import in.cropdata.cdtmasterdata.model.AgriCommodityAgrochemical;
import in.cropdata.cdtmasterdata.model.AgriDistrictCommodityStress;
import in.cropdata.cdtmasterdata.repository.*;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AgriAgrochemicalService {

    @Autowired
    private AgriAgrochemicalRepository agirAgriAgrochemicalRepository;

    @Autowired
    private AgriAgroChemicalTypeRepository agriAgroChemicalTypeRepository;

    @Autowired
    private AclHistoryUtil approvalUtil;

    @Autowired
    private AgriDistrictCommodityStressRepository stressRepository;

    @Autowired
    private AgriCommodityRepositoy agriCommodityRepositoy;

    @Autowired
    private AgriAgrochemicalStressRepository agriAgrochemicalStressRepository;

    @Autowired
    private ResponseMessageUtil responseMessageUtil;

    public List<AgriAgrochemicalDTO> getAllAgriAgrochemicalMaster() {
        try {
            return agirAgriAgrochemicalRepository.getAgriAgrochemicalList();

        } catch (Exception e) {
            throw e;
        }
    }// getAllAgriAgrochemicalMaster

    public Page<AgriAgrochemicalInfDto> getAllAgrochemicalPaginated(int page, int size, String searchText, int isValid) {

        try {
            searchText = "%" + searchText + "%";

//		System.out.println("searchText--> " + searchText);

            Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());

            return agirAgriAgrochemicalRepository.getAllAgrochemical(sortedByIdDesc, searchText);

        } catch (Exception e) {
            throw e;
        }
    }// getAllAgrochemicalPaginated

    public ResponseMessage addAgriAgrochemicalMaster(AgriAgrochemical agriCommodityAgrochemical) {

        try {
            AgriAgrochemical agriCommodityAgrochemicalSaved = agirAgriAgrochemicalRepository
                    .save(agriCommodityAgrochemical);

            // ---------------Save StressNameList--------------------------

            approvalUtil.addRecord(DBConstants.TBL_AGRI_COMMODITY_AGROCHEMICAL, agriCommodityAgrochemical.getId());

            return responseMessageUtil.sendResponse(true, "Agri-AgrochemicalMaster" + APIConstants.RESPONSE_ADD_SUCCESS,
                    "");

        } catch (Exception e) {
            return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
        }
    }// addAllAgriAgrochemicalMaster

    public ResponseMessage updateAgriAgrochemicalMasterById(int id, AgriAgrochemical agriCommodityAgrochemical) {

        try {
            Optional<AgriAgrochemical> foundVareity = agirAgriAgrochemicalRepository.findById(id);
            if (foundVareity.isPresent()) {

                agriCommodityAgrochemical.setId(id);
                agirAgriAgrochemicalRepository.save(agriCommodityAgrochemical);
                // -------------Save StressNameList----------------------------

                approvalUtil.updateRecord(DBConstants.TBL_AGRI_COMMODITY_AGROCHEMICAL, agriCommodityAgrochemical.getId());

                return responseMessageUtil.sendResponse(true,
                        "Agri-AgrochemicalMaster" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");

            } else {
                return responseMessageUtil.sendResponse(false, "",
                        "Agri-AgrochemicalMaster" + APIConstants.RESPONSE_UPDATE_ERROR + id);
            }
        } catch (Exception e) {
            return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
        }
    }// updateAgriAgrochemicalMasterById

    public ResponseMessage primaryApproveById(int id) {
        try {
            Optional<AgriAgrochemical> foundAgroChemical = agirAgriAgrochemicalRepository.findById(id);
            if (foundAgroChemical.isPresent()) {

                AgriAgrochemical agriCommodityAgrochemical = foundAgroChemical.get();
                agriCommodityAgrochemical.setStatus(APIConstants.STATUS_APPROVED);

                agriCommodityAgrochemical = agirAgriAgrochemicalRepository.save(agriCommodityAgrochemical);

                approvalUtil.primaryApprove(DBConstants.TBL_AGRI_COMMODITY_AGROCHEMICAL, agriCommodityAgrochemical.getId());

                return responseMessageUtil.sendResponse(true,
                        "Agri-AgrochemicalMaster" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");

            } else {
                return responseMessageUtil.sendResponse(false, "",
                        "Agri-AgrochemicalMaster" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
            }
        } catch (Exception e) {
            return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
        }

    }// primaryApproveById

    public ResponseMessage rejectById(int id) {
        try {
            Optional<AgriAgrochemical> foundAgroChemical = agirAgriAgrochemicalRepository.findById(id);
            if (foundAgroChemical.isPresent()) {

                AgriAgrochemical agriCommodityAgrochemical = foundAgroChemical.get();
                agriCommodityAgrochemical.setStatus(APIConstants.STATUS_REJECTED);

                agriCommodityAgrochemical = agirAgriAgrochemicalRepository.save(agriCommodityAgrochemical);

                approvalUtil.primaryApprove(DBConstants.TBL_AGRI_COMMODITY_AGROCHEMICAL, agriCommodityAgrochemical.getId());

                return responseMessageUtil.sendResponse(true,
                        "Agri-AgrochemicalMaster" + APIConstants.RESPONSE_REJECT_SUCCESS, "");

            } else {
                return responseMessageUtil.sendResponse(false, "",
                        "Agri-AgrochemicalMaster" + APIConstants.RESPONSE_REJECT_ERROR);
            }
        } catch (Exception e) {
            return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
        }

    }// primaryApproveById

    public ResponseMessage finalApproveById(int id) {
        try {
            Optional<AgriAgrochemical> foundAgrochemical = agirAgriAgrochemicalRepository.findById(id);

            if (foundAgrochemical.isPresent()) {

                AgriAgrochemical agroChemical = foundAgrochemical.get();
                agroChemical.setStatus(APIConstants.STATUS_ACTIVE);

                agroChemical = agirAgriAgrochemicalRepository.save(agroChemical);

                approvalUtil.finalApprove(DBConstants.TBL_AGRI_COMMODITY_AGROCHEMICAL, agroChemical.getId());

                return responseMessageUtil.sendResponse(true,
                        "Agri-AgrochemicalMaster" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");

            } else {

                return responseMessageUtil.sendResponse(false, "",
                        "Agri-AgrochemicalMaster" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
            }
        } catch (Exception e) {
            return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
        }
    }

    public ResponseMessage deleteAgriAgrochemicalMasterById(int id) {
        try {
            Optional<AgriAgrochemical> foundAgroChemical = agirAgriAgrochemicalRepository.findById(id);
            if (foundAgroChemical.isPresent()) {

                AgriAgrochemical agrochemicalMaster = foundAgroChemical.get();
                agrochemicalMaster.setStatus(APIConstants.STATUS_DELETED);

                agrochemicalMaster = agirAgriAgrochemicalRepository.save(agrochemicalMaster);

                approvalUtil.delete(DBConstants.TBL_AGRI_COMMODITY_AGROCHEMICAL, agrochemicalMaster.getId());

                return responseMessageUtil.sendResponse(true,
                        "Agri-AgrochemicalMaster" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");

            } else {
                return responseMessageUtil.sendResponse(false, "",
                        "Agri-AgrochemicalMaster" + APIConstants.RESPONSE_DELETE_ERROR + id);
            }
        } catch (Exception e) {
            return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
        }

    }// deleteAgriAgrochemicalMasterById


    public List<AgriCommodityAgrochemical> getAllAgriStressByStressType(int commodityId, int stressTypeId) {
//		return agirAgriAgrochemicalRepository.findAllByStressTypeId(commodityId,stressTypeId);
        return null;
    }

    public AgriAgrochemical findAgriAgrochemicalMasterById(int id) {
        try {
            Optional<AgriAgrochemical> foundAgrochemicalMaster = agirAgriAgrochemicalRepository.findById(id);
            if (foundAgrochemicalMaster.isPresent()) {

                AgriAgrochemical agriCommodityAgrochemical = foundAgrochemicalMaster.get();

//				agriCommodityAgrochemical = getData(agriCommodityAgrochemical);

                return agriCommodityAgrochemical;
            } else {
                throw new DoesNotExistException("Agri-AgrochemicalMaster" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
            }
        } catch (Exception e) {
            throw e;
        }
    }// findAgriAgrochemicalMasterById

//	private AgriCommodityAgrochemical getData(AgriAgrochemical agriCommodityAgrochemical) {
//		// TODO Auto-generated method stub
//
//		List<AgriAgrochemicalStress> StressList = agriAgrochemicalStressRepository
//				.findAllByAgrochemicalId(agriCommodityAgrochemical.getId());
//
//		Set<AgriCommodityStress> stressList = new HashSet<>();
//
//		for (AgriAgrochemicalStress agStress : StressList) {
//
//			Optional<AgriCommodityStress> stress = stressRepository.findById(agStress.getStressID());
//			if (stress.isPresent()) {
//				stressList.add(stress.get());
//			}
//		}
//		agriCommodityAgrochemical.setStressNameList(stressList);
//		return agriCommodityAgrochemical;
//	}

    public List<AgriAgrochemicalInfDto> findAgriAgrochemicalMasterByAgrochemicalTypeId(int id) {
        try {
//			List<AgriAgrochemicalInfDto> agriAgrochemicalMasterList = agirAgriAgrochemicalRepository
//					.findAllByAgrochemicalTypeId(id);
//
//			return agriAgrochemicalMasterList;
            return null;
        } catch (Exception e) {
            throw e;
        }
    }// findAgriAgrochemicalMasterByAgrochemicalTypeId

    public List<AgriCommodityAgrochemical> findAgriAgrochemicalMasterByCommodityId(int id) {
        try {
//			List<AgriCommodityAgrochemical> agriCommodityAgrochemicalList = agirAgriAgrochemicalRepository
//					.findAllByCommodityId(id);

//			return agriCommodityAgrochemicalList;
            return null;
        } catch (Exception e) {
            throw e;
        }
    }// findAgriAgrochemicalMasterByCommodityId

    public List<AgriDistrictCommodityStressInfDto> getByCommodityIdAndStressTypeId(int commodityId, int stressTypeId) {

        try {
//            List<AgriCommodityStress> agriCommodityStressList = stressRepository.findByCommodityIdAndStressTypeId(commodityId,
//                    stressTypeId);
            
                    List<AgriDistrictCommodityStressInfDto> agriCommodityStressList = stressRepository.getStressByCommodityIdAndStressTypeId(commodityId,
                            stressTypeId);

            return agriCommodityStressList;
        } catch (Exception e) {
            throw e;
        }
    }// getByCommodityIdAndStressTypeId

}// AgriAgrochemicalMasterService