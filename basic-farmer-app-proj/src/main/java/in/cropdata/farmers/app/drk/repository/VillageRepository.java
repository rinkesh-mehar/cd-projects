package in.cropdata.farmers.app.drk.repository;

import in.cropdata.farmers.app.drk.model.Village;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author RinkeshKM
 * @Date 16/04/21
 */
@Repository
public interface VillageRepository extends JpaRepository<Village, Integer> {

    @Query(value = "select v.region_id from drkrishi_ts.village v where v.id=?1", nativeQuery = true)
    Integer findRegionIDByVillage(Integer villageID);
}
