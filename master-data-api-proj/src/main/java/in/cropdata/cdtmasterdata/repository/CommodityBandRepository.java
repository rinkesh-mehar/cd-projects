package in.cropdata.cdtmasterdata.repository;

import in.cropdata.cdtmasterdata.model.Band;
import in.cropdata.cdtmasterdata.model.vo.AgriCommodityVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author RinkeshKM
 * @Date 30/07/20
 */

@Repository
public interface CommodityBandRepository extends JpaRepository<Band, Integer> {

    @Query(value = "select ab.ID, ab.Name, ab.Status from cdt_master_data.agri_band ab", nativeQuery = true)
    List<Band> findAllBand();

    @Transactional()
    @Modifying
    @Query(value = "Update cdt_master_data.agri_band ab SET ab.Status = 'Deleted' WHERE ab.id = :id", nativeQuery = true)
    Integer deleteBand(Integer id);


    @Query(value = "select ab.id as ID, ab.name as Name from cdt_master_data.agri_band ab where ab.id = ?1", nativeQuery = true)
    AgriCommodityVo getBandById(Integer id);

    @Query(value = "select ab.id from cdt_master_data.agri_band ab", nativeQuery = true)
    List<Integer> findAllId();
}
