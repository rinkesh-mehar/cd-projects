package com.krishi.service;

import com.krishi.entity.*;
import com.krishi.model.LeadCallingDetailModel;
import com.krishi.model.MasterData;
import com.krishi.model.PoiDataModel;
import com.krishi.model.ResponseMessage;
import com.krishi.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SpotService {
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    Logger LOGGER = LoggerFactory.getLogger(SpotService.class);

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ViewLeadCallingDetailInfoRepository viewLeadCallingDetailInfoRepository;

    @Autowired
    private CommodityRepository commodityRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private MobileTypeRepository mobileTypeRepository;

    @Autowired
    private VipStatusRepository vipStatusRepository;

    @Autowired
    private VipDesignationRepository vipDesignationRepository;

    @Autowired
    private ViewFarmerCropInfoRepository viewFarmerCropInfoRepo;

    @Autowired
    private CallingStatusRepository callingStatusRepository;

    @Autowired
    private ViewNonTechnicalCallingListSpotRepository viewNonTechnicalCallingListSpotRepository;

    @Autowired
    private VehicleScheduleRepository vehicleScheduleRepository;

    @Autowired
    private ProxyRelationTypeRepository proxyRelationTypeRepository;

    @Transactional(readOnly = true)
    public ResponseMessage getLeadCallingDetail(String taskId) {
        ResponseMessage message = new ResponseMessage();
        Optional<Task> task = taskRepository.findById(taskId);
        Optional<ViewLeadCallingDetailInfo> info = viewLeadCallingDetailInfoRepository.findById(taskId);
        if(info.isEmpty()) {
            message.setStatus(206);
            message.setMessage("Task not found!");
            return message;
        }
        LeadCallingDetailModel model = new LeadCallingDetailModel(info.get());

        MasterData master = new MasterData();
        List<Commodity> commodities = commodityRepository.findByRegionId(info.get().getRegionId());
        master.setMajorcropsgrownlist(commodities);
        List<Language> languages = languageRepository.findByStatus(1);
        master.setSpeakinglanguagelist(languages);
        List<MobileType> mobileTypes = mobileTypeRepository.findByStatus(1);
        master.setMobiletypelist(mobileTypes);
        List<ProxyRelationType> proxyRelationType = proxyRelationTypeRepository.findByStatus(1);
        master.setProxyRelationType(proxyRelationType);
        List<VipStatus> vipStatus = vipStatusRepository.findByStatus(1);
        master.setStatuslist(vipStatus);
        List<VipDesignation> designationList = vipDesignationRepository.findByStatus(1);
        master.setDesignationlist(designationList);
        if(task.get().getEntityTypeId() == 4) {
            /** Added for POI : Start */
            List<PoiDataModel> meetingPointList = vipDesignationRepository.getMeetingPointListByTaskId(taskId);
            master.setMeetingpointlist(meetingPointList);
            /** Added for POI : End */
        } else {
            List<PoiDataModel> meetingPointList = vipDesignationRepository.getMeetingPointListByVillageId(info.get().getVillageId());
            master.setMeetingpointlist(meetingPointList);
        }

        /** added for farmer major crop-CDT-Ujwal: Start */
//		List<Season> seasonList = seasonRepository.findByStatus(1);
//		master.setSeasonList(seasonList);
        List<ViewFarmerCropInfo> farmerCropInfoList = viewFarmerCropInfoRepo.findByTaskId(taskId, 0);
        master.setFarmerCropInfoList(farmerCropInfoList);
        farmerCropInfoList.forEach(a -> {
            try {
                System.out.println("sowing date is " + a.getSowingDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        /** added for farmer major crop-CDT-Ujwal: End */

        /** Fetching only not selected commodity RinkeshKM*/
//        List<Integer> commodityIdList = farmerCropInfoList.stream().map(ViewFarmerCropInfo::getCommodityId).collect(Collectors.toList());
//        List<Commodity> commodities;
//        if (commodityIdList.size()>0){
//            commodities = commodityRepository.findByRegionIdAndCommodityId(info.get().getRegionId(),  commodityIdList);
//        } else {
//            commodities = commodityRepository.findByRegionId(info.get().getRegionId());
//        }
//        master.setMajorcropsgrownlist(commodities);


        master.setLeadStatus(callingStatusRepository.findCallingStatusForCrop(1));

        model.setMasterData(master);

        model.setSellerType(viewNonTechnicalCallingListSpotRepository.findSellerTypeNameByTaskId(taskId));

        if(info.get().getMajorCropList() != null && !info.get().getMajorCropList().trim().isBlank()) {
            List<Integer> cropIds = Arrays.asList(info.get().getMajorCropList().split(","))
                    .stream().map(i -> Integer.parseInt(i.trim()))
                    .collect(Collectors.toList());
            List<Commodity> crops = commodities.stream()
                    .filter(c -> cropIds.contains(c.getId()))
                    .collect(Collectors.toList());
            model.setMajorcropsgrown(crops);
        } else {
            model.setMajorcropsgrown(new ArrayList<>());
        }
        if(info.get().getSpeakingLanguageList() != null && !info.get().getSpeakingLanguageList().trim().isBlank()) {
            List<Integer> speakLangIds = Arrays.asList(info.get().getSpeakingLanguageList().split(","))
                    .stream().map(l -> Integer.parseInt(l.trim()))
                    .collect(Collectors.toList());
            List<Language> langs = languages.stream().filter(l -> speakLangIds.contains(l.getId()))
                    .collect(Collectors.toList());
            model.setSpeakinglanguage(langs);
        } else {
            model.setSpeakinglanguage(new ArrayList<>());
        }

        List<String> mlVisitList = new ArrayList<>();
        Calendar next30DaysCal = Calendar.getInstance();
        next30DaysCal.add(Calendar.DAY_OF_YEAR, 30);
        Calendar nextDayCal = Calendar.getInstance();
        nextDayCal.add(Calendar.DAY_OF_YEAR, 1);
        List<VehicleSchedule> visits = vehicleScheduleRepository.findByVillageIdAndVisitDateBetween(info.get().getVillageId(), new java.sql.Date(nextDayCal.getTimeInMillis()), new Date(next30DaysCal.getTimeInMillis()));
        visits.forEach(v -> mlVisitList.add(FORMAT.format(v.getVisitDate())));
        model.setMlvisit(mlVisitList);
        message.setMessage("success");
        message.setStatus(200);
        message.setData(model);
        return message;
    }

    public ResponseMessage getLeadCallingCommodityList() {
        ResponseMessage message = new ResponseMessage();
        try {
            List<Object[]> commodityList = viewNonTechnicalCallingListSpotRepository.findAllCommodityList();
            List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();
            for(Object[] commodity:commodityList) {
                Map<String, Object> data = new HashMap<String, Object>();
                data.put("commodityName", commodity[0]);
                data.put("commodityId", commodity[1]);
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
