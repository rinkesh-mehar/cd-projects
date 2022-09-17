package in.cropdata.cdtmasterdata.dao;

import in.cropdata.cdtmasterdata.dto.Dashboard;
import in.cropdata.cdtmasterdata.dto.DashboardHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author RinkeshKM
 * @Date 22/09/20
 */

@Component
public class DashboardDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardDao.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Dashboard> getLeadData() {
        return jdbcTemplate.query("select a.TotalLeads, b.verifiedLeads, a.TotalLeads - b.verifiedLeads as RejectedLeads, a.RegionName,\n" +
                "                                           a.region_id as RegionId, a.commodityId\n" +
                "                                    from (select count(distinct f.id) as TotalLeads, v.region_id, r.name as RegionName\n" +
                "                                               , group_concat(distinct f.major_crop) as commodityId\n" +
                "                                          from drkrishi.farmer f\n" +
                "                                                   inner join drkrishi.village v on f.village_id = v.id\n" +
                "                                                   inner join drkrishi.region r on v.region_id = r.id\n" +
                "                                          group by v.region_id, r.name) a,\n" +
                "                                         (select count(t.id) as VerifiedLeads, v.region_id\n" +
                "                                          from drkrishi.farmer f\n" +
                "                                                   inner join drkrishi.task t on f.id = t.entity_id\n" +
                "                                                   inner join drkrishi.village v on f.village_id = v.id\n" +
                "                                          group by v.region_id\n" +
                "                                         ) b\n" +
                "                                    where a.region_id = b.region_id", new BeanPropertyRowMapper<>(Dashboard.class));
    }

    public List<Dashboard> getFilteredLeadData(String filterString) {
        return jdbcTemplate.query("select a.TotalLeads, b.verifiedLeads, a.TotalLeads - b.verifiedLeads as RejectedLeads, a.RegionName,\n" +
                "           a.region_id as RegionId, a.commodityId\n" +
                "    from (select count(distinct f.id) as TotalLeads, v.region_id, r.name as RegionName\n" +
                "               , group_concat(distinct f.major_crop) as commodityId\n" +
                "          from drkrishi.farmer f\n" +
                "                   inner join drkrishi.village v on f.village_id = v.id\n" +
                "                   inner join drkrishi.region r on v.region_id = r.id\n" +
                "            where \n" + filterString +
                "          group by v.region_id, r.name) a,\n" +
                "         (select count(t.id) as VerifiedLeads, v.region_id\n" +
                "          from drkrishi.farmer f\n" +
                "                   inner join drkrishi.task t on f.id = t.entity_id\n" +
                "                   inner join drkrishi.village v on f.village_id = v.id\n" +
                "         where \n" + filterString +
                "          group by v.region_id\n" +
                "         ) b\n" +
                "    where a.region_id = b.region_id", new BeanPropertyRowMapper<>(Dashboard.class));

    }

    /**
     * Header Queries For Fetching Data OF The Dashboard Headers
     *
     * @param regionId
     * @return Specified Value With Respective Region
     */

    public DashboardHeader getUnverifiedLeadsCount(String regionId) {
        try {

            return jdbcTemplate.queryForObject("select b.CurrentUnverified, a.TotalUnverifiedLeads from\n" +
                    "(\n" +
                    "    select count(f.id) as CurrentUnverified from drkrishi.farmer f\n" +
                    "     inner join drkrishi.task t on  f.id=t.entity_id and t.task_date = curdate()-1\n" +
                    "     inner join drkrishi.village v on f.village_id=v.id\n" +
                    regionId +
                    "    ) b,\n" +
                    "(    select count(f.id) as TotalUnverifiedLeads from drkrishi.farmer f\n" +
                    "         inner join drkrishi.task t on f.id=t.entity_id\n" +
                    "         inner join drkrishi.village v on f.village_id=v.id\n" +
                    regionId +
                    "    ) a", (rs, rowNum) -> new DashboardHeader(rs.getString("CurrentUnverified"), rs.getString("TotalUnverifiedLeads")));
        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage());
            return new DashboardHeader("0", "0");
        }
    }

    public List<DashboardHeader> getUnverifiedLeadsByRegion(String regionId) {
        return jdbcTemplate.query("select count(f.id) as CurrentLeads, r.name as RegionName from drkrishi.farmer f\n" +
                "                inner join drkrishi.task t on f.id=t.entity_id and t.task_date = curdate()-1\n" +
                "                inner join drkrishi.village v on f.village_id=v.id\n" +
                "                inner join drkrishi.region r on v.region_id = r.id\n" +
                                 regionId +
                "            and DATE(f.created_at) = DATE_SUB(CURRENT_DATE(), INTERVAL 1 DAY)\n" +
                "            group by v.region_id, f.created_at order by v.region_id ASC", (rs, rowNum) -> new DashboardHeader(rs.getString("CurrentLeads"), rs.getString("RegionName")));
    }

    public DashboardHeader getUnverifiedLeadsAreaCount(String regionId) {
        try {

            return jdbcTemplate.queryForObject("select a.UnverifiedCropAreaCurrentCount, b.UnverifiedCropAreaTotalCount from\n" +
                    "    (select sum(crop_area) as UnverifiedCropAreaCurrentCount from drkrishi.farmer f\n" +
                    "         inner join drkrishi.task t on f.id=t.entity_id and task_date = curdate()-1\n" +
                    "         inner join drkrishi.village v on f.village_id=v.id\n" +
                    regionId +
                    "    ) a,\n" +
                    "(select sum(crop_area) UnverifiedCropAreaTotalCount from drkrishi.farmer f\n" +
                    "    inner join drkrishi.task t on  f.id=t.entity_id\n" +
                    "    inner join drkrishi.village v on f.village_id=v.id\n" +
                    regionId +
                    " ) b;", (rs, rowNum) -> new DashboardHeader(rs.getString("UnverifiedCropAreaCurrentCount"), rs.getString("UnverifiedCropAreaTotalCount")));
        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage());
            return new DashboardHeader("0", "0");
        }
    }

    public List<DashboardHeader> getUnverifiedLeadsArea(String regionId) {
        return jdbcTemplate.query("select sum(crop_area) UnverifiedCropArea, r.name RegionName from drkrishi.farmer f\n" +
                "   inner join drkrishi.task t on f.id=t.entity_id and t.task_date = curdate()-1\n" +
                "   inner join drkrishi.village v on f.village_id=v.id\n" +
                "    inner join drkrishi.region r  on v.region_id = r.id\n" +
                regionId +
                " and DATE(f.created_at) = DATE_SUB(CURRENT_DATE(), INTERVAL 1 DAY) group by v.region_id\n" +
                "order by v.region_id ASC", (rs, rowNum) -> new DashboardHeader(rs.getString("UnverifiedCropArea"), rs.getString("RegionName")));
    }


    public DashboardHeader getVerifiedLeadsCount(String regionId) {
        try {
            return jdbcTemplate.queryForObject("select a.CurrentVerifiedLeads as CurrentVerifiedLeads, b.TotalVerifiedLeads as TotalVerifiedLeads from\n" +
                    "                                    (select count(f.id) as CurrentVerifiedLeads from drkrishi.farmer f\n" +
                    "                                        inner join drkrishi.task t on f.id=t.entity_id and t.status in (1,2,3)\n" +
                    "                                        and t.task_date = curdate()-1\n" +
                    "                                            inner join drkrishi.village v on f.village_id=v.id\n" +
                     regionId +
                    "                        ) a,\n" +
                    "                        (select count(f.id) as TotalVerifiedLeads from drkrishi.farmer f\n" +
                    "                            inner join drkrishi.task t on f.id=t.entity_id\n" +
                    "                            and t.status in (1,2,3)\n" +
                    "                               inner join drkrishi.village v on f.village_id=v.id\n" +
                     regionId +
                    "                        ) b", (rs, rowNum) -> new DashboardHeader(rs.getString("CurrentVerifiedLeads"), rs.getString("TotalVerifiedLeads")));

        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage());
            return new DashboardHeader("0", "0");
        }
    }

    public List<DashboardHeader> getVerifiedLeadsByRegion(String regionId) {
        return jdbcTemplate.query("select count(f.id) VerifiedLeads, r.name RegionName from drkrishi.farmer f\n" +
                "    inner join drkrishi.task t on  f.id=t.entity_id\n" +
                "                               and t.status in (1,2,3)\n" +
                "                               and t.task_date = curdate()-1\n" +
                "    inner join drkrishi.village v on f.village_id=v.id\n" +
                "    inner join drkrishi.region r on v.region_id = r.id\n" +
                regionId +
                "and DATE(f.created_at) = DATE_SUB(CURRENT_DATE(), INTERVAL 1 DAY)\n" +
                "group by v.region_id order by v.region_id ASC;\n", (rs, rowNum) -> new DashboardHeader(rs.getString("VerifiedLeads"), rs.getString("RegionName")));
    }

    public DashboardHeader getVerifiedLeadsAreaCount(String regionId) {
        try {

            return jdbcTemplate.queryForObject("select a.VerifiedCropAreaCurrentCount, b.VerifiedCropAreaTotalCount from\n" +
                    "    (select sum(crop_area) VerifiedCropAreaCurrentCount\n" +
                    "     from drkrishi.farmer f inner join drkrishi.task t on f.id=t.entity_id and t.status\n" +
                    "         in (1,2,3) and t.task_date = curdate()-1\n" +
                    "                            inner join drkrishi.village v on f.village_id=v.id\n" +
                    regionId +
                    "    ) a,\n" +
                    "    (select sum(crop_area) VerifiedCropAreaTotalCount from drkrishi.farmer f\n" +
                    "                 inner join drkrishi.task t on f.id=t.entity_id and t.status in (1,2,3)\n" +
                    "                 inner join drkrishi.village v on f.village_id=v.id\n" +
                    regionId +
                    "    ) b", (rs, rowNum) -> new DashboardHeader(rs.getString("VerifiedCropAreaCurrentCount"), rs.getString("VerifiedCropAreaTotalCount")));
        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage());
            return new DashboardHeader("0", "0");
        }
    }

    public List<DashboardHeader> getVerifiedLeadsArea(String regionId) {
        return jdbcTemplate.query("select sum(crop_area) verifiedCropArea, r.name RegionName from drkrishi.farmer f\n" +
                "   inner join drkrishi.task t on f.id=t.entity_id and t.task_date = curdate()-1 and t.status in (1,2,3)\n" +
                "   inner join drkrishi.village v on f.village_id=v.id\n" +
                "    inner join drkrishi.region r  on v.region_id = r.id\n" +
                regionId +
                " group by v.region_id\n" +
                "order by v.region_id ASC", (rs, rowNum) -> new DashboardHeader(rs.getString("verifiedCropArea"), rs.getString("RegionName")));
    }

}
