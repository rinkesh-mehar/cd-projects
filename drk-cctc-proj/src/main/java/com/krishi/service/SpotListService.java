package com.krishi.service;

import com.krishi.model.ResponseMessage;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public interface SpotListService {
    ResponseMessage getSpotNonTechnicalCallingList(@Valid DataTablesInput input, Integer stateId, Integer regionId, Integer districtId, Integer villageId, Integer commodityId, Integer areaId);

    ResponseMessage getLeadCallingStateList();

    ResponseMessage getLeadCallingCommodityList();

    ResponseMessage getLeadCallingRegionList(Integer stateId);

    ResponseMessage getLeadCallingDistrictList(Integer regionId);

    ResponseMessage getLeadCallingVillageList(Integer districtId);
}
