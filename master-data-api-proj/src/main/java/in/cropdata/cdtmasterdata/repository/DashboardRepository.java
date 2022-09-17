package in.cropdata.cdtmasterdata.repository;

import in.cropdata.cdtmasterdata.dto.interfaces.DashboardDto;
import in.cropdata.cdtmasterdata.model.Band;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author RinkeshKM
 * @Date 14/09/20
 */

@Repository
public interface DashboardRepository extends JpaRepository<Band, Integer> {

    @Query(value = "select count(*) from drkrishi.task t\n" +
            "    inner join drkrishi.farmer f on f.id = t.entity_id\n" +
            "    inner join drkrishi.village v on f.village_id = v.id\n" +
            "where t.status = 0 and v.region_id = ?1\n", nativeQuery = true)
    Integer findPendingLead(Integer regionId);

    @Query(value = "select id as commId, name as CommodityName from drkrishi.commodity c where c.id in (?1) order by c.name", nativeQuery = true)
    List<DashboardDto> findCommodityNameIdById(List<Integer> commodityId);


    /**
     * Dashboard Header queries
     *
     * @return
     */

    @Query(value = "select b.currentCropArea, a.totalCropArea from\n" +
            "    (select sum(f.crop_area) as totalCropArea from drkrishi.farmer f) a,\n" +
            "    (select sum(f.crop_area) as currentCropArea from drkrishi.farmer f where f.created_date >= curdate()-1) b\n", nativeQuery = true)
    DashboardDto findCropArea();

    @Query(value = "select sum(f.crop_area) as CropArea, date (f.created_date) as Date from drkrishi.farmer f\n" +
            "    where f.created_date is not null\n" +
            "    group by f.created_date\n" +
            "    order by f.created_date\n" +
            "    desc limit 7", nativeQuery = true)
    List<Map<String, String>> findLastSevenDayAreaRegistered();

    @Query(value = "select a.TotalLeads, b.CurrentLeads from\n" +
            "(select count(f.id) as TotalLeads from drkrishi.farmer f) a,\n" +
            "(select count(f.id) as CurrentLeads from drkrishi.farmer f where f.created_date >= curdate()-1) b\n", nativeQuery = true)
    DashboardDto findLeadCollected();

    @Query(value = "select b.CurrentVerifiedLeadCount, a.TotalVerifiedLeadCount from\n" +
            "       (select count(t.id) as TotalVerifiedLeadCount from drkrishi.farmer f\n" +
            "            inner join drkrishi.task t on t.entity_id = f.id\n" +
            "        where t.CreatedAt < curdate() -1\n" +
            "           and t.status = 2) a,\n" +
            "       (select count(t.id) as CurrentVerifiedLeadCount from drkrishi.farmer f\n" +
            "            inner join drkrishi.task t on t.entity_id = f.id\n" +
            "        where t.CreatedAt >= curdate()-1\n" +
            "           and t.status = 2\n" +
            "       )b", nativeQuery = true)
    List<Map<String, String>> findCurrentVerifiedLead();

    @Query(value = "select count(t.id) as VefiedLeadCount, DATE(t.CreatedAt) as Date from drkrishi.farmer f\n" +
            "    inner join drkrishi.task t on t.entity_id = f.id\n" +
            "where t.CreatedAt is not null\n" +
            "  and t.status = 2\n" +
            "    group by t.CreatedAt\n" +
            "    order by t.CreatedAt desc\n" +
            "    limit 30", nativeQuery = true)
    List<Map<String, String>> findPastVerifiedLead();


    /**
     * Queries for  commodity area dashboard chart
     */
    @Query(value = "select c.id from drkrishi.commodity c order by c.name", nativeQuery = true)
    List<Integer> findCommodityIds();

    @Query(value = "select c.name from drkrishi.commodity c order by c.name", nativeQuery = true)
    List<String> findCommodityName();

    @Query(value = "select sum(f.farm_size) from drkrishi.farmer f where find_in_set(?1, f.major_crop)", nativeQuery = true)
    Integer findLandHoldingByCommodity(Integer commodityId);

    @Query(value = "    select sum(f.farm_size) as FarmSize\n" +
            "     , c.name from drkrishi.farmer f\n" +
            "    inner join drkrishi.village v on f.village_id = v.id\n" +
            "    inner join drkrishi.region r on v.region_id = r.id\n" +
            "    inner join drkrishi.commodity c on c.id = ?1\n" +
            "where find_in_set(?1, f.major_crop)\n" +
            "  and r.id in (?2)", nativeQuery = true)
    Map<String, String> findLandHoldingByCommodityForEdit(Integer commodityId, List<Integer> regionName);

    @Query(value = "select r.id, r.name from drkrishi.region r where r.status = 1", nativeQuery = true)
    List<Map<String, String>> findAllActiveRegion();

    @Query(value = "select c.name from drkrishi.commodity c where c.id in (?1)", nativeQuery = true)
    List<String> findCommodityNameById(List<Integer> commodityId);

    @Query(value = "select c.id, c.name from drkrishi.commodity c order by c.name", nativeQuery = true)
    List<Map<String, String>> findCommodityNameId();

    @Query(value = "select r.name, r.id\n" +
            "            from drkrishi.farmer f\n" +
            "            inner join drkrishi.village v on f.village_id = v.id\n" +
            "            inner join drkrishi.region r on v.region_id = r.id\n" +
            "            inner join drkrishi.task t on f.id = t.entity_id\n" +
            "               where t.task_date = curdate() - 1\n" +
            "            group by r.name, r.id order by r.name;", nativeQuery = true)
    List<Map<String, String>> findRegionOfLead();

}
