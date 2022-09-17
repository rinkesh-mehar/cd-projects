package in.cropdata.cdtmasterdata.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author RinkeshKM
 * @project CDT_Master_Data
 * @created 03/03/2021 - 11:56 AM
 */

@Component
public class ApiUtilDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiUtilDao.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<String> getAgriPhenophaseID(Integer phenophaseStart, Integer phenophaseEnd, Integer commodityID) {

        String query = "select id from agri_commodity_phenophase where PhenophaseID in (" + phenophaseStart + "," +
                " " + phenophaseEnd + ") and CommodityID = " + commodityID + ";";
        return jdbcTemplate.queryForList(query, String.class);
    }

    public String getPhenophase(List<String> phenophaseStartEnd, Integer commodityID) {
        String query = "select group_concat(PhenophaseID) phenophaseIDs from agri_commodity_phenophase where id between" +
                " " + phenophaseStartEnd.get(0) + " and " + phenophaseStartEnd.get(1) + " and CommodityID = " + commodityID + ";";

        System.out.println("Find phenophase Query is " + query);
        return jdbcTemplate.queryForObject(query, String.class);
    }

}
