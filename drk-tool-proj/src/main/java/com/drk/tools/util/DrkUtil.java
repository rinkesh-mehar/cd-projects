package com.drk.tools.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @author cropdata-Aniket Naik
 */

@Component
public class DrkUtil {

    @Autowired
    @Qualifier("masterDataJdbcTemplate")
    JdbcTemplate jdbcTemplate;

    public boolean checkNull(String key) {
        boolean flag = false;
        if (key == null || key.isEmpty() || key.isBlank()) {
            flag = true;
        }
        return flag;
    }

    public boolean validateApiKey(String apiKey) {

        boolean flag = false;
        try {
            String query = "SELECT count(*) FROM cdt_master_data.drkrishi_source ds where ds.ApiKey='" + apiKey + "'";
            Integer count = jdbcTemplate.queryForObject(query, Integer.class);
            if (count > 0) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }


}
