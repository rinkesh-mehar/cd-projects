package in.cropdata.cdtmasterdata.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.constants.DBConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriAgrochemicalBrandInfDto;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.AgriAgrochemicalBrand;
import in.cropdata.cdtmasterdata.model.AgriAgrochemicalBrandMissing;
import in.cropdata.cdtmasterdata.model.AgriCommodityAgrochemical;
import in.cropdata.cdtmasterdata.model.GeneralCompany;
import in.cropdata.cdtmasterdata.repository.AgriAgrochemicalBrandMissingRepository;
import in.cropdata.cdtmasterdata.repository.AgriAgrochemicalBrandRepository;
import in.cropdata.cdtmasterdata.repository.AgriCommodityAgrochemicalRepository;
import in.cropdata.cdtmasterdata.repository.GeneralCompanyRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AgriAgrochemicalBrandService {

    @Autowired
    private AgriAgrochemicalBrandRepository agriAgrochemicalBrandRepository;
    
    @Autowired
    private AgriAgrochemicalBrandMissingRepository agriAgrochemicalBrandMissingRepository;

    @Autowired
    private AgriCommodityAgrochemicalRepository agrochemicalRepository;

    @Autowired
    private AclHistoryUtil approvalUtil;

    @Autowired
    private ResponseMessageUtil responseMessageUtil;

    @Autowired
    private GeneralCompanyRepository companyRepository;

    public List<AgriAgrochemicalBrand> getAllAgriRemedialMeasures() {
        try {
            List<AgriAgrochemicalBrand> list = agriAgrochemicalBrandRepository.findAll();
            for (AgriAgrochemicalBrand agriAgrochemicalBrand : list) {
                Optional<GeneralCompany> foundCompany = companyRepository
                        .findById(agriAgrochemicalBrand.getCompanyId());
                if (foundCompany.isPresent()) {
                    agriAgrochemicalBrand.setCompanyName(foundCompany.get().getName());
                }

                Optional<AgriCommodityAgrochemical> foundAgroChemical = agrochemicalRepository
                        .findById(agriAgrochemicalBrand.getAgrochemicalId());
                if (foundAgroChemical.isPresent()) {
                    agriAgrochemicalBrand.setAgrochemical(foundAgroChemical.get().getName());
                    agriAgrochemicalBrand.setAgrochemicalTypeId(foundAgroChemical.get().getAgrochemicalTypeId());
                }
            }

            return list;
        } catch (Exception e) {
            throw e;
        }

    }// getAllAgriRemedialMeasures

    public Page<AgriAgrochemicalBrandInfDto> getAllAgrochemicalBrandPaginated(int page, int size, String searchText, int isValid,String missing) {

        try {
            searchText = "%" + searchText + "%";

//		System.out.println("searchText--> " + searchText);
            Page<AgriAgrochemicalBrandInfDto> agroBrandList;

            Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
            
            if("0".equals(missing)) {
            if (isValid == 0) {
                agroBrandList = agriAgrochemicalBrandRepository.getAllAgrochemicalBrandInvalidated(sortedByIdDesc, searchText);
            } else {
                agroBrandList = agriAgrochemicalBrandRepository.getAllAgrochemicalBrand(sortedByIdDesc, searchText);
            }
            }else {
            	if (isValid == 0) {
                    agroBrandList = agriAgrochemicalBrandRepository.getAllAgrochemicalBrandMissingInvalidated(sortedByIdDesc, searchText);
                } else {
                    agroBrandList = agriAgrochemicalBrandRepository.getAllAgrochemicalBrandMissing(sortedByIdDesc, searchText);
                }	
            }

            return agroBrandList;
        } catch (Exception e) {
            throw e;
        }
    }// getAllAgrochemicalBrandPaginated

//	public List<AgriRemedialMeasuresDto> getAllAgriRemedialMeasures() {
//
//		try {
//
//			List<AgriRemedialMeasuresInfDto> remedialList = agriAgrochemicalBrandRepository.getAllRemedialMeasuresList();
//
//			List<AgriRemedialMeasuresDto> remedialMeasuresList = new ArrayList<>();
//
//			if (remedialList.size() > 0 && !remedialList.isEmpty()) {
//
//				for (AgriRemedialMeasuresInfDto dto : remedialList) {
//
//					if (dto != null) {
//
//						AgriRemedialMeasuresDto remedialMeasuresDto = new AgriRemedialMeasuresDto();
//
//						remedialMeasuresDto.setId(dto.getId());
//						remedialMeasuresDto.setStateCode(dto.getStateCode());
//						remedialMeasuresDto.setStressTypeId(dto.getStressTypeId());
//						remedialMeasuresDto.setCompanyId(dto.getCompanyId());
//						remedialMeasuresDto.setBrandName(dto.getBrandName());
//						remedialMeasuresDto.setAgrchemicalTypeId(dto.getAgrchemicalTypeId());
//						remedialMeasuresDto.setRemedialStatus(dto.getRemedialStatus());
//						remedialMeasuresDto.setStatus(dto.getStatus());
//						remedialMeasuresDto.setStressTypeName(dto.getStressTypeName());
//						remedialMeasuresDto.setAgrochemicalTypeName(dto.getAgrochemicalTypename());
//
//						// below code for Stress
//
//						String[] StressIdArr = {};
//						String[] stressCommodityIdArr = {};
//						String[] StressTypeIdArr = {};
//						String[] StressNameArr = {};
//						String[] scientificNameArr = {};
//						String[] startArr = {};
//						String[] endArr = {};
////						String[] symptonsArr = {};
//
//						if (dto.getStressId() != null) {
//							StressIdArr = dto.getStressId().split(",");
//						}
//						if (dto.getStressCommodityId() != null) {
//							stressCommodityIdArr = dto.getStressCommodityId().split(",");
//						}
//						if (dto.getStressTypeId() != null) {
//							StressTypeIdArr = dto.getStressTypeId().split(",");
//						}
//						if (dto.getStressName() != null) {
//							StressNameArr = dto.getStressName().replace("\n", " ").split(",");
//						}
//						if (dto.getScientificName() != null) {
//							scientificNameArr = dto.getScientificName().replace("\n", " ").split(",");
//						}
//						if (dto.getStartPhenophaseId() != null) {
//							startArr = dto.getStartPhenophaseId().split(",");
//						}
//						if (dto.getEndPhenophaseId() != null) {
//							endArr = dto.getEndPhenophaseId().split(",");
//						}
////						if (dto.getSymptoms() != null) {
////							symptonsArr = dto.getSymptoms().replace("\n", " ").replace("\r", " ").split(",");
////						}
//
//						List<AgriStress> StressList = new ArrayList<>();
//
//						for (int i = 0; i < StressIdArr.length; i++) {
//
//							AgriStress stress = new AgriStress();
//
//							if (StressIdArr[i] != null) {
//								stress.setId(Integer.parseInt(StressIdArr[i]));
//							}
//							if (stressCommodityIdArr[i] != null) {
//								stress.setCommodityId(Integer.parseInt(stressCommodityIdArr[i]));
//							}
//							if (StressTypeIdArr[i] != null) {
//								stress.setStressTypeId(Integer.parseInt(StressTypeIdArr[i]));
//							}
//							if (StressNameArr[i] != null) {
//								stress.setName(StressNameArr[i]);
//							}
//							if (scientificNameArr[i] != null) {
//								stress.setScientificName(scientificNameArr[i]);
//							}
//							if (startArr[i] != null) {
//								stress.setStart(Integer.parseInt(startArr[i]));
//							}
//							if (endArr[i] != null) {
//								stress.setEnd(Integer.parseInt(endArr[i]));
//							}
////							if (symptonsArr[i] != null) {
////								stress.setSymptoms(symptonsArr[i]);
////							}
//
//							StressList.add(stress);
//
//						}
//
//						remedialMeasuresDto.setStressList(StressList);
//
//						// below code for agrochemical
//
//						String[] agrochemicalIdArr = {};
//						String[] agrochemicalTypeIdArr = {};
//						String[] agrochemicalNameArr = {};
//						String[] waitingPeriodArr = {};
//						String[] commodityIdArr = {};
//						String[] agroStressTypeIdArr = {};
//
//						if (dto.getAgrochemicalId() != null) {
//							agrochemicalIdArr = dto.getAgrochemicalId().split(",");
//						}
//						if (dto.getAgrochemicalTypeId() != null) {
//							agrochemicalTypeIdArr = dto.getAgrochemicalTypeId().split(",");
//						}
//						if (dto.getagrochemicalName() != null) {
//							agrochemicalNameArr = dto.getagrochemicalName().replace("\n", " ").split(",");
//						}
//						if (dto.getWaitingPeriod() != null) {
//							waitingPeriodArr = dto.getWaitingPeriod().split(",");
//						}
//						if (dto.getCommodityIdForAgrochemical() != null) {
//							commodityIdArr = dto.getCommodityIdForAgrochemical().split(",");
//						}
//						if (dto.getAgroStressTypeId() != null) {
//							agroStressTypeIdArr = dto.getAgroStressTypeId().split(",");
//						}
//
//						List<AgriAgrochemicalMaster> agrochemicalList = new ArrayList<>();
//
//						for (int i = 0; i < agrochemicalIdArr.length; i++) {
//
//							AgriAgrochemicalMaster agrochemical = new AgriAgrochemicalMaster();
//
//							if (agrochemicalIdArr[i] != null) {
//								agrochemical.setId(Integer.parseInt(agrochemicalIdArr[i]));
//							}
//							if (agrochemicalTypeIdArr[i] != null) {
//								agrochemical.setAgrochemicalTypeId(Integer.parseInt(agrochemicalTypeIdArr[i]));
//							}
//							if (commodityIdArr[i] != null) {
//								agrochemical.setCommodityId(Integer.parseInt(commodityIdArr[i]));
//							}
//							if (waitingPeriodArr[i] != null) {
//								agrochemical.setWaitingPeriod(Integer.parseInt(waitingPeriodArr[i]));
//							}
//							if (agrochemicalNameArr[i] != null) {
//								agrochemical.setName(agrochemicalNameArr[i]);
//							}
//							if (agroStressTypeIdArr[i] != null) {
//								agrochemical.setStressTypeId(Integer.parseInt(agroStressTypeIdArr[i]));
//							}
//
//							agrochemicalList.add(agrochemical);
//						}
//
//						remedialMeasuresDto.setAgrochemicalList(agrochemicalList);
//
//						remedialMeasuresList.add(remedialMeasuresDto);
//					}
//				}
//			}
//
//			return remedialMeasuresList;
//
//		} catch (Exception e) {
//			throw e;
//		}
//
//	}// getAllAgriRemedialMeasures

    public ResponseMessage addAgriRemedialMeasures(AgriAgrochemicalBrand agriAgrochemicalBrand) {

        try {
            AgriAgrochemicalBrand remedialMeasures = agriAgrochemicalBrandRepository.save(agriAgrochemicalBrand);

            approvalUtil.addRecord(DBConstants.TBL_AGRI_AGROCHEMICAL_BRAND, remedialMeasures.getId());

            return responseMessageUtil.sendResponse(true,
                    "Agri-Agrochemical-Brand : " + APIConstants.RESPONSE_ADD_SUCCESS, "");

        } catch (Exception e) {
            e.printStackTrace();
            return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
        }
    }// addAgriRemedialMeasures

    public ResponseMessage updateAgriRemedialMeasuresById(int id, AgriAgrochemicalBrand agriAgrochemicalBrand) {
        try {

            Optional<AgriAgrochemicalBrand> foundVareity = agriAgrochemicalBrandRepository.findById(id);

            if (foundVareity.isPresent()) {

                AgriAgrochemicalBrand agriRemedialUpdate = foundVareity.get();

                if (agriAgrochemicalBrand.getBrandName() != null) {
                    agriRemedialUpdate.setBrandName(agriAgrochemicalBrand.getBrandName());
                }
                if (agriAgrochemicalBrand.getCompanyId() > 0) {
                    agriRemedialUpdate.setCompanyId(agriAgrochemicalBrand.getCompanyId());
                }
                if (agriAgrochemicalBrand.getUpdatedAt() != null) {
                    agriRemedialUpdate.setUpdatedAt(agriAgrochemicalBrand.getUpdatedAt());
                }
                if (agriAgrochemicalBrand.getAgrochemicalStatus() != null) {
                    agriRemedialUpdate.setAgrochemicalStatus(agriAgrochemicalBrand.getAgrochemicalStatus());
                }

                if (agriAgrochemicalBrand.getAgrochemicalTypeId() > 0) {
                    agriRemedialUpdate.setAgrochemicalTypeId(agriAgrochemicalBrand.getAgrochemicalTypeId());
                }

                if (agriAgrochemicalBrand.getAgrochemicalId() > 0) {
                    agriRemedialUpdate.setAgrochemicalId(agriAgrochemicalBrand.getAgrochemicalId());
                }

                agriAgrochemicalBrand = agriAgrochemicalBrandRepository.save(agriRemedialUpdate);

                approvalUtil.updateRecord(DBConstants.TBL_AGRI_AGROCHEMICAL_BRAND, agriAgrochemicalBrand.getId());

                return responseMessageUtil.sendResponse(true,
                        "Agri-Agrochemical-Brand" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
            } else {
                return responseMessageUtil.sendResponse(false, "",
                        "Agri-Agrochemical-Brand" + APIConstants.RESPONSE_UPDATE_ERROR + id);
            }
        } catch (Exception e) {
            return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
        }
    }// updateAgriRemedialMeasuresById

    public ResponseMessage primaryApproveById(int id) {
        try {
            Optional<AgriAgrochemicalBrand> foundRemedialMeasures = agriAgrochemicalBrandRepository.findById(id);

            if (foundRemedialMeasures.isPresent()) {

                AgriAgrochemicalBrand remedialMeasures = foundRemedialMeasures.get();
                remedialMeasures.setStatus(APIConstants.STATUS_APPROVED);

                remedialMeasures = agriAgrochemicalBrandRepository.save(remedialMeasures);

                approvalUtil.primaryApprove(DBConstants.TBL_AGRI_AGROCHEMICAL_BRAND, remedialMeasures.getId());

                return responseMessageUtil.sendResponse(true,
                        "Agri-Agrochemical-Brand" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
            } else {
                return responseMessageUtil.sendResponse(false, "",
                        "Agri-Agrochemical-Brand" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
            }
        } catch (Exception e) {
            return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
        }
    }// primaryApproveById

    public ResponseMessage finalApproveById(int id) {
        try {
            Optional<AgriAgrochemicalBrand> foundRemedialMeasures = agriAgrochemicalBrandRepository.findById(id);

            if (foundRemedialMeasures.isPresent()) {

                AgriAgrochemicalBrand remedialMeasures = foundRemedialMeasures.get();

                remedialMeasures.setStatus(APIConstants.STATUS_ACTIVE);
                remedialMeasures = agriAgrochemicalBrandRepository.save(remedialMeasures);

                approvalUtil.primaryApprove(DBConstants.TBL_AGRI_AGROCHEMICAL_BRAND, remedialMeasures.getId());

                return responseMessageUtil.sendResponse(true,
                        "Agri-Agrochemical-Brand" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
            } else {
                return responseMessageUtil.sendResponse(false, "",
                        "Agri-Agrochemical-Brand" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
            }
        } catch (Exception e) {
            return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
        }
    }// finalApproveById

    public ResponseMessage deleteAgriRemedialMeasuresById(int id) {
        try {
            Optional<AgriAgrochemicalBrand> foundRemedialMeasures = agriAgrochemicalBrandRepository.findById(id);

            if (foundRemedialMeasures.isPresent()) {

                AgriAgrochemicalBrand remedialMeasures = foundRemedialMeasures.get();

                remedialMeasures.setStatus(APIConstants.STATUS_DELETED);
                remedialMeasures = agriAgrochemicalBrandRepository.save(remedialMeasures);

                approvalUtil.delete(DBConstants.TBL_AGRI_AGROCHEMICAL_BRAND, remedialMeasures.getId());

                return responseMessageUtil.sendResponse(true,
                        "Agri-Agrochemical-Brand" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");

            } else {
                return responseMessageUtil.sendResponse(false, "",
                        "Agri-Agrochemical-Brand" + APIConstants.RESPONSE_DELETE_ERROR + id);
            }
        } catch (Exception e) {
            return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
        }

    }// deleteAgriRemedialMeasuresById

    public ResponseMessage rejectById(int id) {
        try {
            Optional<AgriAgrochemicalBrand> foundRemedialMeasures = agriAgrochemicalBrandRepository.findById(id);

            if (foundRemedialMeasures.isPresent()) {

                AgriAgrochemicalBrand remedialMeasures = foundRemedialMeasures.get();

                remedialMeasures.setStatus(APIConstants.STATUS_REJECTED);
                remedialMeasures = agriAgrochemicalBrandRepository.save(remedialMeasures);

                approvalUtil.delete(DBConstants.TBL_AGRI_AGROCHEMICAL_BRAND, remedialMeasures.getId());

                return responseMessageUtil.sendResponse(true,
                        "Agri-Agrochemical-Brand" + APIConstants.RESPONSE_REJECT_SUCCESS + id, "");

            } else {
                return responseMessageUtil.sendResponse(false, "",
                        "Agri-Agrochemical-Brand" + APIConstants.RESPONSE_REJECT_ERROR + id);
            }
        } catch (Exception e) {
            return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
        }

    }// rejectById

    public AgriAgrochemicalBrand findAgriRemedialMeasuresById(int id) {

        try {
            Optional<AgriAgrochemicalBrand> foundRemedialMeasures = agriAgrochemicalBrandRepository.findById(id);
            if (foundRemedialMeasures.isPresent()) {
                AgriAgrochemicalBrand agriAgrochemicalBrand = foundRemedialMeasures.get();

                Optional<AgriCommodityAgrochemical> foundAgriAgrochemicalMaster = agrochemicalRepository
                        .findById(agriAgrochemicalBrand.getAgrochemicalId());
                if (foundAgriAgrochemicalMaster.isPresent()) {
                    agriAgrochemicalBrand
                            .setAgrochemicalTypeId(foundAgriAgrochemicalMaster.get().getAgrochemicalTypeId());
                }
                return agriAgrochemicalBrand;
            } else {
                throw new DoesNotExistException("Agri-Agrochemical-Brand" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
            }
        } catch (Exception e) {
            throw e;

        }
    }// findAgriRemedialMeasuresById
    
    @Transactional
	public ResponseMessage moveToMaster(int id) {
		try {
			Optional<AgriAgrochemicalBrandMissing> agriAgrochemicalBrandMissing = agriAgrochemicalBrandMissingRepository.findById(id);

			if (agriAgrochemicalBrandMissing.isPresent()) {
				AgriAgrochemicalBrand regionalCommodity = new AgriAgrochemicalBrand();
				AgriAgrochemicalBrandMissing regionalCommodityMissing = agriAgrochemicalBrandMissing.get();
				
				regionalCommodity.setAgrochemicalId(regionalCommodityMissing.getAgrochemicalId());
				regionalCommodity.setCompanyId(regionalCommodityMissing.getCompanyId());
				regionalCommodity.setBrandName(regionalCommodityMissing.getBrandName());
				regionalCommodity.setAgrochemicalStatus(regionalCommodityMissing.getAgrochemicalStatus());
				regionalCommodity.setStatus(regionalCommodityMissing.getStatus());
				
				AgriAgrochemicalBrand savedRegionalCommodity = agriAgrochemicalBrandRepository.save(regionalCommodity);
				
				agriAgrochemicalBrandMissingRepository.deleteById(id);


				approvalUtil.addRecord(DBConstants.TBL_AGRI_AGROCHEMICAL_BRAND, savedRegionalCommodity.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Agrochemical-Brand" + APIConstants.RESPONSE_MOVE_TO_MASTER_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Agrochemical-Brand" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}
    
}// AgriAgrochemicalBrandService