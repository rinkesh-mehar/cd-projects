package in.cropdata.portal.gstmDataModels.service;

import in.cropdata.portal.exceptions.DoesNotExistException;
import in.cropdata.portal.gstmDataModels.dto.WinnerMarketDTO;
import in.cropdata.portal.gstmDataModels.model.WinnerMarket;
import in.cropdata.portal.gstmDataModels.repository.WinnermarketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.portal.gstmDataModels.service
 * @date 13/08/20
 * @time 4:09 PM
 * To change this template use File | Settings | File and Code Templates
 */

@Service
public class WinnerMarketService
{
    public static final Logger logger = LoggerFactory.getLogger(WinnerMarketService.class);
    @Autowired
    WinnermarketRepository winnermarketRepository;
    /**
     * It is use for get winner market record base on commodity name
     *
     * @return winner market record
     */
    public WinnerMarketDTO getWinnerMarket(Integer commodityId) throws Exception
    {
            WinnerMarketDTO winnerMarketDTO = winnermarketRepository.getWinnerMarketByCommodity(commodityId);
            if (winnerMarketDTO == null)
            {
                throw new DoesNotExistException("No Markets Arrival!");
            } else
            {
               return winnerMarketDTO;
            }
    }

    /**
     * It is hold Id's and base on Id's get list of records
     *
     * @return Treading view list
     */
    public Object getTreadingViewRecords(Integer stateCode, Integer districtCode,
                                                            Integer commodityId, Integer varietyId,
                                                            Integer marketId)
    {
        if (stateCode != null && districtCode != null && commodityId != null && varietyId != null &&
                marketId != null)
        {
            Map<String, Object> getRecords = new HashMap<>();
            List<WinnerMarketDTO> getRecord = winnermarketRepository.getTreadingViewRecord(stateCode, districtCode,
                    commodityId, varietyId, marketId);

            if (getRecord != null && getRecord.size() >= 3)
            {
                getRecord.sort(Comparator.comparing(WinnerMarketDTO::getArrivalDate));
                logger.info("sorting list is ---> {}", getRecord.toString());

                String[] date = new String[getRecord.size()];
                String[] modal = new String[getRecord.size()];
                String[] min = new String[getRecord.size()];
                String[] max = new String[getRecord.size()];

                for (int i = 0; i < getRecord.size(); i++)
                {
                    DateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
                    String formattedDate = formatter.format(getRecord.get(i).getArrivalDate());

                    date[i] = formattedDate;
                    modal[i] = getRecord.get(i).getModalPrice();
                    min[i] = getRecord.get(i).getMinPrice();
                    max[i] = getRecord.get(i).getMaxPrice();
                }
                getRecords.put("commodityName", getRecord.get(0).getCommodityName());
                getRecords.put("date", date);
                getRecords.put("min", min);
                getRecords.put("max", max);
                getRecords.put("modal", modal);
            } else {
                throw new DoesNotExistException("No Sufficient Records!");
            }
            return getRecords;

        } else
        {
            throw new NullPointerException("Please insert value");
        }
    }

    public List<WinnerMarket> getMarketRecord(Integer commodity)
    {

        List<WinnerMarketDTO> getRecords = winnermarketRepository.getListOfMarketRecords(commodity);

        getRecords.sort(Comparator.comparing(WinnerMarketDTO::getDates).reversed());

        if (getRecords.size() != 0){
            return getMarkets(getRecords);

        } else {
            throw new DoesNotExistException("No Markets Arrival!");
        }
    }

    public Object getMarketRecordsByCommodityAndStateCode(Integer commodity, Integer stateCode){

        List<WinnerMarketDTO> getRecords = null;

            getRecords = winnermarketRepository.getMarketListByCommodityAndState(commodity,stateCode);

        getRecords.sort(Comparator.comparing(WinnerMarketDTO::getDates).reversed());

            if (getRecords.size() != 0){

                return getMarkets(getRecords);
            } else {
                throw new DoesNotExistException("No Markets Arrival!");
            }


    }

    private List<WinnerMarket> getMarkets(List<WinnerMarketDTO> getRecords)
    {

         List<WinnerMarket> records = new ArrayList<>();
        if (getRecords.size() == 0)
        {
            throw new DoesNotExistException("Records Not Found!");
        } else
        {
            int count = 0;
            Map<String,String> marketMap = new HashMap<>();
            Date date = new Date();
            DateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
            String currentDate = formatter.format(date);
            for (int i = 0; i< getRecords.size(); i++){
                String d = formatter.format(getRecords.get(i).getDates());
                if (currentDate.equals(d))
                {
                    count++;
                    WinnerMarket winnerMarket = new WinnerMarket();
                    getUniqueMarketRecords(getRecords, records, formatter, i, winnerMarket);

                    marketMap.put((getRecords.get(i).getStateName() + "-" + getRecords.get(i).getDistrictName() + "-" +
                            getRecords.get(i).getCommodityName() + "-" + getRecords.get(i).getVarietyName() + "-" +
                            getRecords.get(i).getMarketName()), "data");

                } else
                {

                    WinnerMarket winnerMarket = new WinnerMarket();
                    String key = getRecords.get(i).getStateName() + "-" + getRecords.get(i).getDistrictName() + "-" +
                            getRecords.get(i).getCommodityName() + "-" + getRecords.get(i).getVarietyName() + "-" +
                            getRecords.get(i).getMarketName();

                    if (marketMap.containsKey(key)){

                        /** If Key is Match then it not add in list*/
                    } else
                    {
                        if (count == 81){
                            logger.info("Duplicate Records found");
                        }
                        marketMap.put(key,"data");
                        count++;
                        getUniqueMarketRecords(getRecords, records, formatter, i, winnerMarket);
                    }
                }
            }
            logger.info("total Records-------> {}", count);
            return records;
        }
    }

    private void getUniqueMarketRecords(List<WinnerMarketDTO> getRecords, List<WinnerMarket> records, DateFormat formatter, int i, WinnerMarket winnerMarket)
    {
        BeanUtils.copyProperties(getRecords.get(i), winnerMarket);

        String formattedDate = formatter.format(winnerMarket.getDates());
        winnerMarket.setArrivalDate(formattedDate);
        winnerMarket.setDates(null);
        winnerMarket.setCommodityID(getRecords.get(i).getCommodityID());

        if (getRecords.get(i).getMaxPriceChangePercentage() != null)
        {
            winnerMarket.setMaxPriceChangePercentage(getRecords.get(i).getMaxPriceChangePercentage());
        } else
        {
            winnerMarket.setMaxPriceChangePercentage("-");
        }
        if (getRecords.get(i).getMinPriceChangePercentage() != null)
        {
            winnerMarket.setMinPriceChangePercentage(getRecords.get(i).getMinPriceChangePercentage());
        } else
        {
            winnerMarket.setMinPriceChangePercentage("-");
        }
        if (getRecords.get(i).getModalPriceChangePercentage() != null)
        {
            winnerMarket.setModalPriceChangePercentage(getRecords.get(i).getModalPriceChangePercentage());
        } else
        {
            winnerMarket.setModalPriceChangePercentage("-");
        }
        records.add(winnerMarket);
    }
}
