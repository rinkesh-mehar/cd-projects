package in.cropdata.toolsuite.drk.service.tileassignment;

import in.cropdata.toolsuite.drk.model.masterdata.ParameterBandDetails;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author RinkeshKM
 * @project DRK
 * @created 16/02/2021 - 5:15 PM
 */

@Service
public class WebPortalService {
    Logger LOGGER = LoggerFactory.getLogger(WebPortalService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Map<String, Object> validateQualityBand(ParameterBandDetails parameterBandDetails) throws NotFoundException {

        List<String> bandIDList = new ArrayList<>();
        List<Integer> parameterIDList = new ArrayList<>();
        List<Float> parameterValueList = new ArrayList<>();
        Integer finalBandID = null;

        for (ParameterBandDetails parameterBandDetail : parameterBandDetails.getAgriQualityParameterList()) {
            parameterIDList.add(parameterBandDetail.getParameterID());
            parameterValueList.add(parameterBandDetail.getValue());

            bandIDList.add(this.findBandID(parameterBandDetails.getZonalCommodityID(), parameterBandDetail.getParameterID(),
                    parameterBandDetail.getValue(), parameterBandDetails.getSowingWeek()));
        }

        Map<String, Integer> bandIDCountMap = new HashMap<>();
        for (String bandID : bandIDList) {
            String[] splittedBandIDList = bandID.split(",");

            LOGGER.info("band id is {}", bandID);

            for (String splittedBandID : splittedBandIDList) {
                if (bandIDCountMap.containsKey(splittedBandID)) {
                    Integer bandCount = bandIDCountMap.get(splittedBandID);
                    bandIDCountMap.put(splittedBandID, ++bandCount);
                } else {
                    bandIDCountMap.put(splittedBandID, 1);
                }
            }
        }

        int matchFlag = 0;
        for (Map.Entry<String, Integer> mapEntry : bandIDCountMap.entrySet()) {

            if (parameterBandDetails.getAgriQualityParameterList().size() == mapEntry.getValue()) {
                LOGGER.info("Band ID is -> {}", mapEntry.getKey());

                matchFlag = 1;
                finalBandID = Integer.parseInt(mapEntry.getKey());
                break;
            }
        }

        if (matchFlag == 0) {
            LinkedList<Float> sumOfBandAndParameterWise = findSumOfBandAndParameterWise(parameterBandDetails.getZonalCommodityID(), parameterIDList);

            sumOfBandAndParameterWise.forEach(x -> System.out.println("summed value of parameter with respect to band-> " + x));

            double sumOfAllEvaluatedValue = parameterValueList.stream().mapToDouble(Float::floatValue).sum();
            LOGGER.info("sum of all evaluated value --> {}", sumOfAllEvaluatedValue);

            float closestValue = findClosest(sumOfBandAndParameterWise, sumOfAllEvaluatedValue);
            LOGGER.info("closest value -> {}", closestValue);

            finalBandID = sumOfBandAndParameterWise.indexOf(closestValue) + 1;
            LOGGER.info("final band ID is -> {}", finalBandID);
        }
        Map<String, Object> responseMap = new HashMap<>();

        try {
            String bandName = jdbcTemplate.queryForObject("select ab.Name from agri_band ab where ab.ID = "+finalBandID+"",
                    String.class);
            responseMap.put("bandName", bandName);
        } catch (Exception e){
            e.printStackTrace();
        }
        responseMap.put("bandID", finalBandID);
        responseMap.put("success", true);
        responseMap.put("message", "Band Found Successfully");

        return responseMap;
    }

    private String findBandID(Integer zonalCommodityID, Integer parameterID, Float value, Integer sowingWeek) throws NotFoundException {
        String bandID = null;

        try {
            bandID = jdbcTemplate.queryForObject("select group_concat(distinct (BandID)) from cdt_master_data.zonal_quality_parameter_range  aqpr\n" +
                    "    inner join cdt_master_data.zonal_commodity zc on zc.ID = aqpr.ZonalCommodityID\n" +
                    "    where aqpr.ZonalCommodityID =  " + zonalCommodityID + " and (" + sowingWeek + " between zc.SowingWeekStart and zc.SowingWeekEnd)\n" +
                    "    and aqpr.ParameterID = " + parameterID + " and (" + value + "  between aqpr.Min AND aqpr.Max)", String.class);

            if (bandID == null) {
                throw new NotFoundException("Band Not Found For Parameter id " + parameterID);
            }

        } catch (Exception e) {
            if (e instanceof NotFoundException){
                e.getLocalizedMessage();
                throw new NotFoundException("Band Not Found For Parameter id " + parameterID);
            }
            LOGGER.info("Exception in fetching value of band");
        }

        return bandID;
    }

    private LinkedList<Float> findSumOfBandAndParameterWise(Integer zonalCommodityID, List<Integer> parameterIDs) throws NotFoundException {

        Float sumOfBand;
        LinkedList<Float> sumOfParameterList = new LinkedList<>();
        String trimmedParameterList = parameterIDs.toString().replaceAll("(^\\[|\\]$)", "");
        Integer maxBandID;

        maxBandID = jdbcTemplate.queryForObject("select Max(BandID) from cdt_master_data.zonal_quality_parameter_range aqpr\n" +
                "    inner join cdt_master_data.zonal_commodity zc on zc.ID = aqpr.ZonalCommodityID\n" +
                "    where aqpr.ZonalCommodityID = " + zonalCommodityID + " and aqpr.ParameterID = " + parameterIDs.get(0), Integer.class);

        if (maxBandID != null) {

            try {
                for (int bandID = 1; bandID <= maxBandID; bandID++) {

                    sumOfBand = jdbcTemplate.queryForObject("select sum(aqpr.Max) from cdt_master_data.zonal_quality_parameter_range aqpr\n" +
                            "    inner join cdt_master_data.zonal_commodity zc on zc.ID = aqpr.ZonalCommodityID \n" +
                            "    where aqpr.ZonalCommodityID = " + zonalCommodityID + "\n" +
                            "      and aqpr.BandID = " + bandID + "\n" +
                            "and aqpr.ParameterID in (" + trimmedParameterList + ")", Float.class);

                    sumOfParameterList.add(sumOfBand);
                }
            } catch (Exception e) {
                LOGGER.info("Exception in fetching sum of parameters");
            }
        } else {
            throw new NotFoundException("Band ID Not Found");
        }

        return sumOfParameterList;
    }


    /**
     * Returns element closest to target in sumOfParameterList
     */
    public static float findClosest(LinkedList<Float> sumOfParameterList, double target)
    {
        int n = sumOfParameterList.size();

        // Corner cases
        if (target <= sumOfParameterList.get(0))
            return sumOfParameterList.get(0);
        if (target >= sumOfParameterList.get(n - 1))
            return sumOfParameterList.get(n - 1);

        // Doing binary search
        int i = 0, j = n, mid = 0;
        while (i < j) {
            mid = (i + j) / 2;

            if (sumOfParameterList.get(mid) == target)
                return sumOfParameterList.get(mid);

            /* If target is less than array element,
               then search in left */
            if (target < sumOfParameterList.get(mid)) {

                // If target is greater than previous
                // to mid, return closest of two
                if (mid > 0 && target > sumOfParameterList.get(mid - 1))
                    return getClosest(sumOfParameterList.get(mid - 1),
                            sumOfParameterList.get(mid), target);

                /* Repeat for left half */
                j = mid;
            }

            // If target is greater than mid
            else {
                if (mid < n - 1 && target < sumOfParameterList.get(mid + 1))
                    return getClosest(sumOfParameterList.get(mid),
                            sumOfParameterList.get(mid + 1), target);
                i = mid + 1; // update i
            }
        }

        // Only single element left after search
        return sumOfParameterList.get(mid);
    }

    // Method to compare which one is the more close
    // We find the closest by taking the difference
    //  between the target and both values. It assumes
    // that val2 is greater than val1 and target lies
    // between these two.
    public static float getClosest(float val1, float val2,
                                   double target)
    {
        if (target - val1 >= val2 - target)
            return val2;
        else
            return val1;
    }

}
