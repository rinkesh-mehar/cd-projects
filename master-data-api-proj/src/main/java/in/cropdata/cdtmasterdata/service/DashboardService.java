package in.cropdata.cdtmasterdata.service;

import in.cropdata.cdtmasterdata.dao.DashboardDao;
import in.cropdata.cdtmasterdata.dto.Dashboard;
import in.cropdata.cdtmasterdata.dto.DashboardHeader;
import in.cropdata.cdtmasterdata.dto.interfaces.DashboardDto;
import in.cropdata.cdtmasterdata.repository.DashboardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author RinkeshKM
 * @Date 14/09/20
 */

@Service
public class DashboardService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardService.class);


    @Autowired
    DashboardRepository dashboardRepository;

    @Autowired
    DashboardDao dashboardDao;


    /**
     * @param dashboardEdit
     * @return list of lead data with verified, rejected values
     * @apiNote this API use to calculate the value of verified and rejected leads
     * based on region and commodity and date
     */
    public List<Dashboard> getLeadData(final Dashboard dashboardEdit) {
        List<Dashboard> dashboardLeadData = new ArrayList<>();

        List<Integer> commodityIdList = new ArrayList<>();
        List<String> commodityNameList = new ArrayList<>();

        if (dashboardEdit.getEditFlag() == 0) {

            dashboardLeadData = dashboardDao.getLeadData();
            LOGGER.info("Fetched Details Of Initial Lead Data");
        } else if (dashboardEdit.getEditFlag() == 1) {

            String filterString = null;

            if ((dashboardEdit.getRegionIds() != null && dashboardEdit.getRegionIds().size() != 0)
                    && (dashboardEdit.getCommodityIds() != null && dashboardEdit.getCommodityIds().size() != 0))
            {
                filterString = " f.major_crop in (" + dashboardEdit.getCommodityIds().toString().replaceAll("(^\\[|\\]$)", "") + ") " +
                        " and v.region_id in (" + dashboardEdit.getRegionIds().toString().replaceAll("(^\\[|\\]$)", "") + ") ";
            } else if ((dashboardEdit.getRegionIds() == null || dashboardEdit.getRegionIds().size() == 0)
                    && (dashboardEdit.getCommodityIds() != null || dashboardEdit.getCommodityIds().size() != 0))
            {
                filterString = " f.major_crop in (" + dashboardEdit.getCommodityIds().toString().replaceAll("(^\\[|\\]$)", "") + ")";
            } else if ((dashboardEdit.getRegionIds() != null || dashboardEdit.getRegionIds().size() != 0)
                    && (dashboardEdit.getCommodityIds() == null || dashboardEdit.getCommodityIds().size() == 0))
            {
                filterString = " v.region_id in (" + dashboardEdit.getRegionIds().toString().replaceAll("(^\\[|\\]$)", "") + ") ";
            }

            dashboardLeadData = dashboardDao.getFilteredLeadData(filterString);
            LOGGER.info("Fetched Details Of Filtered Lead Data");
        }
        List<Dashboard> finalLeadData = new ArrayList<>();

        for (Dashboard data : dashboardLeadData) {

            String[] commodityId = data.getCommodityId().split(",");

            List<Integer> commodityIdFetchList = new ArrayList<>();
            for (String id : commodityId) {
            	if(!id.isEmpty()) {
                commodityIdFetchList.add(Integer.parseInt(id.strip()));
            	}
            }

            List<Integer> commodityIdListTemp = new ArrayList<>();
            List<String> commodityNameListTemp = new ArrayList<>();

            List<DashboardDto> filterCommodityNameList = dashboardRepository.findCommodityNameIdById(commodityIdFetchList);

            data.setPendingLead(dashboardRepository.findPendingLead(data.getRegionId()));

            for (final DashboardDto DashDTOData : filterCommodityNameList) {
                commodityIdListTemp.add(DashDTOData.getCommId());
                commodityNameListTemp.add(DashDTOData.getCommodityName());
            }

            data.setCommodityIds(commodityIdListTemp);
            data.setCommodityNames(commodityNameListTemp);

            commodityIdList.addAll(commodityIdListTemp);
            commodityNameList.addAll(commodityNameListTemp);

            finalLeadData.add(data);
        }

        if (finalLeadData.size() > 0 && finalLeadData != null) {

            commodityIdList = commodityIdList.stream().distinct().collect(Collectors.toList());
            commodityNameList = commodityNameList.stream().distinct().collect(Collectors.toList());

            finalLeadData.get(0).setCommodityIds(commodityIdList);
            finalLeadData.get(0).setCommodityNames(commodityNameList);
        }

        return finalLeadData;
    }

    public Dashboard getCommodityArea(Dashboard dash) {

        /**
         * Fetching Details for Commodity Area Chart
         */
        DashboardDto cropArea = dashboardRepository.findCropArea();

        Dashboard dashboard = new Dashboard();

        BeanUtils.copyProperties(cropArea, dashboard);

        List<Integer> commodityIdList = dashboardRepository.findCommodityIds();

        List<String> commodityNameList = dashboardRepository.findCommodityName();

        /**
         * Fetching details for Dashboard Headers Card
         */

        List<Integer> areaCommodityList = new ArrayList<>();

        if (dash.getEditFlag() == 0) {

            for (final Integer id : commodityIdList) {
                Integer landSize = dashboardRepository.findLandHoldingByCommodity(id);
                areaCommodityList.add(landSize);
            }
        }

        if (dash.getEditFlag() == 1) {

            List<Map<String, String>> commodityAreaListMap = new ArrayList<>();

            for (final Integer id : dash.getCommodityIds()) {
                Map<String, String> fetchedAreaCommodityList = dashboardRepository.findLandHoldingByCommodityForEdit(id, dash.getRegionIds());

                if (fetchedAreaCommodityList.get("name") != null && fetchedAreaCommodityList.get("FarmSize") != null) {
                    commodityAreaListMap.add(fetchedAreaCommodityList);
                }
            }
            dashboard.setCommodityAreaList(commodityAreaListMap);
        }

        List<Map<String, String>> commodityIdMap = dashboardRepository.findCommodityNameId();

        dashboard.setCommodityIdName(commodityIdMap);

        dashboard.setCommodityNames(commodityNameList);
        dashboard.setLandSize(areaCommodityList);
        dashboard.setAllRegionList(dashboardRepository.findAllActiveRegion());

        return dashboard;
    }

    public DashboardHeader getHeaderData(DashboardHeader dashboard) {

        DashboardHeader dashboardHeader = new DashboardHeader();

        String filterString = "";

        if (dashboard.getEditFlag() == 1) {
            filterString = " where v.region_id IN (" + dashboard.getRegionIds().toString().replaceAll("(^\\[|\\]$)", "") + ")";
        }

        LOGGER.info("--Fetching Info Of Unverified Lead--");

        LOGGER.info("Fetching Details Of Unverified Leads Count");
        DashboardHeader unverifiedLeadsCount = dashboardDao.getUnverifiedLeadsCount(filterString);

        LOGGER.info("Fetching Details Of Unverified Leads Area Count");
        DashboardHeader unverifiedLeadsAreaCount = dashboardDao.getUnverifiedLeadsAreaCount(filterString);

        LOGGER.info("Fetching Details Of Unverified Leads For All Matched Region");
        List<DashboardHeader> unverifiedLeadsRegion = dashboardDao.getUnverifiedLeadsByRegion(filterString);

        LOGGER.info("Fetching Details Of Area Of Unverified Leads For All Matched Region");
        List<DashboardHeader> unverifiedLeadsArea = dashboardDao.getUnverifiedLeadsArea(filterString);


        Map<String, String> unverifiedLeadMap = new HashMap<>();
        Map<String, String> unverifiedLeadAreaMap = new HashMap<>();


        for (DashboardHeader data : unverifiedLeadsRegion) {
            unverifiedLeadMap.put(data.getFirstValue(), data.getSecondValue());
        }

        for (DashboardHeader data : unverifiedLeadsArea) {
            unverifiedLeadAreaMap.put(data.getFirstValue(), data.getSecondValue());
        }

        dashboardHeader.setCurrentUnverifiedLeads(unverifiedLeadsCount.getFirstValue());
        dashboardHeader.setTotalUnverifiedLeads(unverifiedLeadsCount.getSecondValue());

        if (unverifiedLeadsAreaCount.getSecondValue() != null) {
            dashboardHeader.setTotalUnverifiedLeadsArea(unverifiedLeadsAreaCount.getSecondValue().substring(0, unverifiedLeadsAreaCount.getSecondValue().lastIndexOf(".")));
        } else {
            dashboardHeader.setTotalUnverifiedLeadsArea("0");
        }
        if (unverifiedLeadsAreaCount.getFirstValue() != null) {
            dashboardHeader.setCurrentUnverifiedLeadsArea(unverifiedLeadsAreaCount.getFirstValue().substring(0, unverifiedLeadsAreaCount.getFirstValue().lastIndexOf(".")));
        } else {
            dashboardHeader.setCurrentUnverifiedLeadsArea("0");
        }


        List<Map<String, String>> leadUnverifiedRegionList = new ArrayList<>();

        unverifiedLeadMap.forEach((key, value) -> {
            Map<String, String> tempMap = new HashMap<>();
            tempMap.put("count", key);
            tempMap.put("name", value);
            leadUnverifiedRegionList.add(tempMap);
        });

        List<Map<String, String>> leadUnverifiedAreaList = new ArrayList<>();

        unverifiedLeadAreaMap.forEach((key, value) -> {
            Map<String, String> tempMap = new HashMap<>();
            tempMap.put("count", key);
            tempMap.put("name", value);
            leadUnverifiedAreaList.add(tempMap);
        });

        dashboardHeader.setLeadUnverifiedList(leadUnverifiedRegionList);
        dashboardHeader.setLeadUnverifiedAreaList(leadUnverifiedAreaList);

        LOGGER.info("--Fetched Info Of Unverified Lead--");

        LOGGER.info("--Fetching Info of Verified Lead--");

        LOGGER.info("Fetching Details Of Verified Leads Count");
        DashboardHeader verifiedLeadsCount = dashboardDao.getVerifiedLeadsCount(filterString);

        if (verifiedLeadsCount != null) {
            dashboardHeader.setCurrentVerifiedLeads(verifiedLeadsCount.getFirstValue());
            dashboardHeader.setTotalVerifiedLeads(verifiedLeadsCount.getSecondValue());
        }

        LOGGER.info("Fetching Details Of Verified Leads For All Matched Region");
        List<DashboardHeader> verifiedLeadsRegion = dashboardDao.getVerifiedLeadsByRegion(filterString);

        Map<String, String> verifiedLeadMap = new HashMap<>();

        for (DashboardHeader data : verifiedLeadsRegion) {
            verifiedLeadMap.put(data.getFirstValue(), data.getSecondValue());
        }

        List<Map<String, String>> leadVerifiedList = new ArrayList<>();

        verifiedLeadMap.forEach((key, value) -> {
            Map<String, String> tempMap = new HashMap<>();
            tempMap.put("count", key);
            tempMap.put("name", value);
            leadVerifiedList.add(tempMap);
        });

        dashboardHeader.setLeadVerifiedList(leadVerifiedList);

        LOGGER.info("Fetching Details Of Verified Leads Area Count");
        DashboardHeader verifiedLeadsAreaCount = dashboardDao.getVerifiedLeadsAreaCount(filterString);

        if (verifiedLeadsAreaCount.getFirstValue() != null) {
            dashboardHeader.setCurrentVerifiedLeadsArea(verifiedLeadsAreaCount.getFirstValue().substring(0, verifiedLeadsAreaCount.getFirstValue().lastIndexOf(".")));
        } else {
            dashboardHeader.setCurrentVerifiedLeadsArea("0");
        }
        if (verifiedLeadsAreaCount.getSecondValue() != null) {
            dashboardHeader.setTotalVerifiedLeadsArea(verifiedLeadsAreaCount.getSecondValue().substring(0, verifiedLeadsAreaCount.getSecondValue().lastIndexOf(".")));
        } else {
            dashboardHeader.setTotalVerifiedLeadsArea("0");
        }

        LOGGER.info("Fetching Details Of Verified Leads Area For All Matched Region");
        List<DashboardHeader> verifiedLeadsArea = dashboardDao.getVerifiedLeadsArea(filterString);

        Map<String, String> verifiedLeadAreaMap = new HashMap<>();

        for (DashboardHeader data : verifiedLeadsArea) {
            verifiedLeadAreaMap.put(data.getFirstValue(), data.getSecondValue());
        }

        List<Map<String, String>> leadVerifiedAreaList = new ArrayList<>();

        verifiedLeadAreaMap.forEach((key, value) -> {
            Map<String, String> tempMap = new HashMap<>();
            tempMap.put("count", key);
            tempMap.put("name", value);
            leadVerifiedAreaList.add(tempMap);
        });

        dashboardHeader.setLeadVerifiedCropAreaList(leadVerifiedAreaList);

        LOGGER.info("fetched info of unverified lead");

        LOGGER.info("Fetching All Region Details");

        List<Map<String, String>> listOfAllRegion = dashboardRepository.findRegionOfLead();

        dashboardHeader.setAllRegionList(listOfAllRegion);

        return dashboardHeader;
    }

}
