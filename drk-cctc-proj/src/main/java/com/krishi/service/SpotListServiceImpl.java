package com.krishi.service;


import com.krishi.entity.AgriLandHoldingSize;
import com.krishi.entity.ViewNonTechnicalCallingListSpot;
import com.krishi.model.ResponseMessage;
import com.krishi.repository.AgriLandHoldingSizeRepository;
import com.krishi.repository.ViewNonTechnicalCallingListSpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.Column;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.*;

@Service
public class SpotListServiceImpl implements  SpotListService{

    @Autowired
    ViewNonTechnicalCallingListSpotRepository viewNonTechnicalCallingListSpotRepository;

    @Autowired
    private AgriLandHoldingSizeRepository agriLandHoldingSizeRepository;

    @Override
    public ResponseMessage getSpotNonTechnicalCallingList(@Valid DataTablesInput input, Integer stateId, Integer regionId, Integer districtId, Integer villageId, Integer commodityId, Integer areaId) {
        ResponseMessage response = new ResponseMessage();
        try {
            List<Column> columns = input.getColumns();
            for(Column c:columns) {
                if(c.getData().equalsIgnoreCase("serialNumber")
                        || c.getName().equalsIgnoreCase("serialNumber")) {
                    columns.remove(c);
                    break;
                }
            }

            Optional<AgriLandHoldingSize> agriLandHoldingSize = agriLandHoldingSizeRepository.findById(areaId);

            Specification<ViewNonTechnicalCallingListSpot> geoSpecification = (Specification<ViewNonTechnicalCallingListSpot>) (root, query, criteriaBuilder) -> {
                if(villageId != 0) {
                    return criteriaBuilder.and(criteriaBuilder.equal(root.get("villageId"), villageId));
                } else if(districtId != 0) {
                    return criteriaBuilder.and(criteriaBuilder.equal(root.get("districtId"), districtId));
                } else if(regionId != 0) {
                    return criteriaBuilder.and(criteriaBuilder.equal(root.get("regionId"), regionId));
                } else if(stateId != 0) {
                    return criteriaBuilder.and(criteriaBuilder.equal(root.get("stateId"), stateId));
                } else if (areaId != 0) {
                    return criteriaBuilder.and(criteriaBuilder.between(root.get("cropArea"), agriLandHoldingSize.get().getMinValue(), agriLandHoldingSize.get().getMaxValue()));
                } else if(commodityId != 0) {
                    return criteriaBuilder.and(criteriaBuilder.equal(root.get("commodityId"), commodityId));
                } else {
                    return null;
                }
            };
            DataTablesOutput<ViewNonTechnicalCallingListSpot> data = viewNonTechnicalCallingListSpotRepository.findAll(input, geoSpecification);
            int count = input.getStart();
            for(ViewNonTechnicalCallingListSpot d: data.getData()) {
                d.setSerialNumber(++count);
            }
            response.setData(data);
            response.setMessage("success");
            response.setStatus(200);
        } catch(Exception e) {
            e.printStackTrace();
            response.setMessage("An Error Occurred, Please Try Again Later.");
            response.setStatus(500);
        }
        return response;
    }


    @Override
    public ResponseMessage getLeadCallingStateList() {
        ResponseMessage message = new ResponseMessage();
        try {
            List<Object[]> stateList = viewNonTechnicalCallingListSpotRepository.findAllStateList();
            List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();
            for(Object[] state:stateList) {
                Map<String, Object> data = new HashMap<String, Object>();
                data.put("stateId", state[0]);
                data.put("stateName", state[1]);
                response.add(data);
            }

            message.setData(response);
            message.setStatus(200);
            message.setMessage("Success");
        } catch (Exception e) {
            e.printStackTrace();
            message.setStatus(500);
            message.setMessage("An Error Occurred, Please Try Again Later.");
        }
        return message;
    }

    @Override
    public ResponseMessage getLeadCallingCommodityList() {
        ResponseMessage message = new ResponseMessage();
        try {
            List<Object[]> commodityList = viewNonTechnicalCallingListSpotRepository.findAllCommodityList();
            List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();
            for(Object[] commodity:commodityList) {
                Map<String, Object> data = new HashMap<String, Object>();
                data.put("commodityId", commodity[0]);
                data.put("commodityName", commodity[1]);
                response.add(data);
            }

            message.setData(response);
            message.setStatus(200);
            message.setMessage("Success");
        } catch (Exception e) {
            e.printStackTrace();
            message.setStatus(500);
            message.setMessage("An Error Occurred, Please Try Again Later.");
        }
        return message;
    }

    @Override
    public ResponseMessage getLeadCallingRegionList(Integer stateId) {
        ResponseMessage message = new ResponseMessage();
        try {
            List<Object[]> regionList = viewNonTechnicalCallingListSpotRepository.findAllRegionList(stateId);
            List<Map<String, Object>> response = new ArrayList<>();
            for(Object[] region:regionList) {
                Map<String, Object> data = new HashMap<>();
                data.put("regionId", region[0]);
                data.put("regionName", region[1]);
                response.add(data);
            }
            message.setData(response);
            message.setStatus(200);
            message.setMessage("Success");
        } catch (Exception e) {
            e.printStackTrace();
            message.setStatus(500);
            message.setMessage("An Error Occurred, Please Try Again Later.");
        }
        return message;
    }

    @Override
    public ResponseMessage getLeadCallingDistrictList(Integer regionId) {
        ResponseMessage message = new ResponseMessage();
        try {
            List<Object[]> districtList = viewNonTechnicalCallingListSpotRepository.findAllDistrictList(regionId);
            List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();
            for(Object[] district:districtList) {
                Map<String, Object> data = new HashMap<String, Object>();
                data.put("districtId", district[0]);
                data.put("districtName", district[1]);
                response.add(data);
            }
            message.setData(response);
            message.setStatus(200);
            message.setMessage("Success");
        } catch (Exception e) {
            e.printStackTrace();
            message.setStatus(500);
            message.setMessage("An Error Occurred, Please Try Again Later.");
        }
        return message;
    }

    @Override
    public ResponseMessage getLeadCallingVillageList(Integer districtId) {
        ResponseMessage message = new ResponseMessage();
        try {
            List<Object[]> villageList = viewNonTechnicalCallingListSpotRepository.findAllVillageList(districtId);
            List<Map<String, Object>> response = new ArrayList<>();
            for(Object[] village:villageList) {
                Map<String, Object> data = new HashMap<>();
                data.put("villageId", village[0]);
                data.put("villageName", village[1]);
                response.add(data);
            }
            message.setData(response);
            message.setStatus(200);
            message.setMessage("Success");
        } catch (Exception e) {
            e.printStackTrace();
            message.setStatus(500);
            message.setMessage("An Error Occurred, Please Try Again Later.");
        }
        return message;
    }

}
